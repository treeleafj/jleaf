package org.shop.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.jleaf.format.json.JsonData;
import org.jleaf.web.action.ActionContext;
import org.jleaf.web.annotation.GlobalInterceptor;
import org.jleaf.web.controller.result.ErrorJsonResult;
import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.Interceptor;
import org.shop.domain.Auth;
import org.shop.service.AuthService;
import org.shop.service.impl.AuthServiceImpl;

/**
 * auth的认证拦截
 * @author leaf
 * @date 2014-3-16 下午4:37:02
 */
@GlobalInterceptor(2)
@SuppressWarnings("serial")
public class AuthInterceptor implements Interceptor{
	
	private AuthService authService = new AuthServiceImpl();

	@Override
	public boolean begin(ActionInvocation ai) throws Exception {
		String authStr = ai.getAction().getParam("_auth");
		
		if(StringUtils.isBlank(authStr)){
			Cookie [] cookies = ActionContext.getRequest().getCookies();
			if(cookies != null){
				for(Cookie c : cookies){
					if("_auth".equals(c.getName())){
						authStr = c.getValue();
						break;
					}
				}
			}
		}
		
		if(StringUtils.isNotBlank(authStr)){
			Auth auth = authService.get(authStr);
			if(auth != null){
				if(auth.getTimeLimitDate() == null || new Date().compareTo(auth.getTimeLimitDate()) >= 0){
					System.out.println("认证通过,用户名:" + auth.getUsername() + ",但超过了时间!");
					authService.remove(auth.getId());
					JsonData data = new JsonData(false,"权限认证过期");
					ai.setResult(new ErrorJsonResult(data));
					return false;
				}else{
					System.out.println("认证通过,用户名:" + auth.getUsername());
				}
			}else{
				JsonData data = new JsonData(false,"权限认证错误");
				ai.setResult(new ErrorJsonResult(data));
				return false;
			}
		}
		return true;
	}

	@Override
	public void end(ActionInvocation ai) throws Exception {
		
	}

}
