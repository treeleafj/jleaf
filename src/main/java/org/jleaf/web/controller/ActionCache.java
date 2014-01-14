package org.jleaf.web.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.Interceptor;
import org.jleaf.web.intercept.annotation.ClearInterceptor;
import org.jleaf.web.intercept.annotation.Interceptors;

public class ActionCache {
	
	private static Logger log = Logger.getLogger(ControllerManager.class);

	private Class<?> classz;

	private Object obj;

	private Method method;

	private org.jleaf.web.controller.annotation.Method methodAno;

	private HttpMethod httpMethod;

	private List<Interceptor> intercepts;

	/**
	 * 是否清除全局Interceptor
	 */
	private boolean isClearOverallInterceptor = false;

	public ActionCache(Class<?> classz, Method method) throws Exception {

		this.classz = classz;
		this.obj = classz.newInstance();
		this.method = method;

		org.jleaf.web.controller.annotation.Method methodAno = method
				.getAnnotation(org.jleaf.web.controller.annotation.Method.class);
		this.methodAno = methodAno;

		if (methodAno != null) {
			this.httpMethod = methodAno.value();
		} else {
			this.httpMethod = HttpMethod.NONE;
		}

		// 判断是否清除所有全局Interceptor
		ClearInterceptor clearInterceptor = classz
				.getAnnotation(ClearInterceptor.class);
		if (clearInterceptor != null) {
			this.isClearOverallInterceptor = true;
		} else {
			clearInterceptor = method.getAnnotation(ClearInterceptor.class);
			if (clearInterceptor != null) {
				this.isClearOverallInterceptor = true;
			}
		}

		//解析私有的Interceptor
		Interceptors interceptorsAno = classz.getAnnotation(Interceptors.class);
		if(interceptorsAno != null){
			Class<? extends Interceptor>[] interceptClassz = interceptorsAno.value();
			intercepts = new ArrayList<Interceptor>();
			for(Class<? extends Interceptor> c : interceptClassz){
				intercepts.add(c.newInstance());
			}
		}
		
		if(method.getAnnotation(ClearInterceptor.class) != null){
			intercepts = null;
		}
		
		interceptorsAno = method.getAnnotation(Interceptors.class);
		if(interceptorsAno != null){
			Class<? extends Interceptor>[] interceptClassz = interceptorsAno.value();
			if(intercepts == null){
				intercepts = new ArrayList<Interceptor>();
			}
			for(Class<? extends Interceptor> c : interceptClassz){
				intercepts.add(c.newInstance());
			}
		}
	}

	public Class<?> getClassz() {
		return classz;
	}

	public Object getObj() {
		return obj;
	}

	public Method getMethod() {
		return method;
	}

	public org.jleaf.web.controller.annotation.Method getMethodAno() {
		return methodAno;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public boolean isClearOverallInterceptor() {
		return isClearOverallInterceptor;
	}

	public boolean invokeInterceptor(ActionRequest actionReq){
		if(this.intercepts != null){
			ActionInvocation ai = new ActionInvocation(this.intercepts, actionReq);
			boolean b = ai.invoke();
			log.debug("私有Interceptor执行完毕.");
			return b;
		}
		return true;
	}

}