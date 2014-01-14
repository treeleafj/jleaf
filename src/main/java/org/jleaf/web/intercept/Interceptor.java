package org.jleaf.web.intercept;

/**
 * 拦截器
 * @author leaf
 * @date 2014-1-3 上午1:32:17
 */
public interface Interceptor {
	
	
	boolean intercept(ActionInvocation ai);
	

}
