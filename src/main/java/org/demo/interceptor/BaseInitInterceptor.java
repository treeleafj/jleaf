package org.demo.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jleaf.utils.WebUtils;
import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.Interceptor;
import org.jleaf.web.intercept.annotation.GlobalInterceptor;

@GlobalInterceptor(2)
public class BaseInitInterceptor implements Interceptor {
	
	private Logger log = Logger.getLogger(this.getClass());

	public boolean intercept(ActionInvocation ai) {
		
		log.debug("=>BaseInitInterceptor.");
		
		HttpServletRequest request = WebUtils.getRequest();
		if(request != null){
			String basePath = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()
					+ "/";
			request.setAttribute("base", basePath);
		}
		return ai.invoke();
	}

}
