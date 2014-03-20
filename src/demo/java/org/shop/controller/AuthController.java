package org.shop.controller;

import java.util.Date;
import java.util.HashMap;

import org.jleaf.db.controller.BaseController;
import org.jleaf.format.json.JsonData;
import org.jleaf.web.action.ActionContext;
import org.jleaf.web.action.HttpAction;
import org.jleaf.web.annotation.Control;
import org.jleaf.web.controller.result.Result;
import org.shop.domain.Auth;
import org.shop.domain.User;
import org.shop.service.AuthService;
import org.shop.service.UserService;
import org.shop.service.impl.AuthServiceImpl;
import org.shop.service.impl.UserServiceImpl;

@SuppressWarnings("serial")
@Control
public class AuthController extends BaseController{
	
	private AuthService authService = new AuthServiceImpl();
	private UserService userSerivce = new UserServiceImpl();
	
	public Result index(HttpAction action){
		return json(new JsonData(true,"",new HashMap(){
			
			{
				this.put("id", "1233");
				this.put("name", "4354634");
				this.put("price", "8456123");
			}
			
		}));
	}
	
	public Result save(HttpAction action) {
		String username = action.getParam("username");
		String password = action.getParam("password");
		User user = userSerivce.get(username,password);
		if(user != null){
			Auth obj = action.toObj(Auth.class);
			Date now = new Date();
			obj.setCreateDate(now);
			obj.setIp(ActionContext.getRequest().getRemoteAddr());
			Date timeLimitDate = new Date();
			timeLimitDate.setDate(now.getDate() + 3);//3天有效期
			obj.setTimeLimitDate(timeLimitDate);
			authService.save(obj);
			JsonData data = new JsonData(null,obj);
			return json(data);
		}else{
			JsonData data = new JsonData(false, "用户名或密码错误");
			return json(data);
		}
	}
}
