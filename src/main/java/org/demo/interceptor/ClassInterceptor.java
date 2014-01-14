package org.demo.interceptor;

import org.apache.log4j.Logger;
import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.Interceptor;

public class ClassInterceptor implements Interceptor {
	
	private Logger log = Logger.getLogger(this.getClass());

	public boolean intercept(ActionInvocation ai) {
		log.debug("=>ClassInterceptor.");
		return ai.invoke();
	}

}