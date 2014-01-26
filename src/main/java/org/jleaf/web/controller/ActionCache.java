package org.jleaf.web.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.BasicDoActionInterceptor;
import org.jleaf.web.intercept.Interceptor;
import org.jleaf.web.intercept.annotation.ClearInterceptor;
import org.jleaf.web.intercept.annotation.ClearLevel;
import org.jleaf.web.intercept.annotation.LiveLevel;
import org.jleaf.web.intercept.annotation.GlobalInterceptor;
import org.jleaf.web.intercept.annotation.Interceptors;

/**
 * 存放Action的信息
 * @author leaf
 * @date 2014-1-26 下午2:50:22
 */
public class ActionCache {

	private Class<?> controllerClassz;

	private Object controller;

	private Method method;

	private org.jleaf.web.controller.annotation.Method methodAno;

	private HttpMethod httpMethod;

	private List<Interceptor> intercepts = new ArrayList<Interceptor>();//私有Interceptor
	
	private List<Interceptor> globalInterceptor;//全局Interceptor

	/**
	 * 是否清除全局Interceptor
	 */
	private boolean isClearOverallInterceptor = false;

	public ActionCache(Class<?> classz, Method method,List<Interceptor> globalInterceptor) throws Exception {

		this.controllerClassz = classz;
		this.controller = classz.newInstance();
		this.method = method;

		org.jleaf.web.controller.annotation.Method methodAno = method
				.getAnnotation(org.jleaf.web.controller.annotation.Method.class);
		this.methodAno = methodAno;

		if (methodAno != null) {
			this.httpMethod = methodAno.value();
		} else {
			this.httpMethod = HttpMethod.NONE;
		}

		// 判断是否清除全局Interceptor
		ClearInterceptor clearInterceptor = classz.getAnnotation(ClearInterceptor.class);
		if (clearInterceptor == null) {
			clearInterceptor = method.getAnnotation(ClearInterceptor.class);
		}
		if(clearInterceptor != null){
			this.isClearOverallInterceptor = true;
		}
			

		//解析私有的Interceptor
		Interceptors interceptorsAno = classz.getAnnotation(Interceptors.class);
		if(interceptorsAno != null){
			Class<? extends Interceptor>[] interceptClassz = interceptorsAno.value();
			for(Class<? extends Interceptor> c : interceptClassz){
				intercepts.add(c.newInstance());
			}
		}
		
		if(method.getAnnotation(ClearInterceptor.class) != null){
			intercepts.clear();
		}
		
		interceptorsAno = method.getAnnotation(Interceptors.class);
		if(interceptorsAno != null){
			Class<? extends Interceptor>[] interceptClassz = interceptorsAno.value();
			for(Class<? extends Interceptor> c : interceptClassz){
				intercepts.add(c.newInstance());
			}
		}
		
		//解析全局的Interceptor
		if(this.isClearOverallInterceptor){
			List<Interceptor> newGlobalInterceptor = new ArrayList<Interceptor>();
			for(Interceptor interceptor : globalInterceptor){
				GlobalInterceptor giAno = interceptor.getClass().getAnnotation(GlobalInterceptor.class);
				if(giAno != null){
					if(giAno.liveLevel() == LiveLevel.NOTCALEAR || (giAno.liveLevel() == LiveLevel.HEIGHT && clearInterceptor.clearLevel() != ClearLevel.CLEARALL)){
						newGlobalInterceptor.add(interceptor);
					}
				}
					
			}
			this.globalInterceptor = newGlobalInterceptor;
		}else{
			this.globalInterceptor = globalInterceptor;
		}
		
		
		this.globalInterceptor.addAll(this.intercepts);
		this.globalInterceptor.add(new BasicDoActionInterceptor());
	}
	

	public Class<?> getControllerClass() {
		return controllerClassz;
	}

	public Object getController() {
		return controller;
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

	/**
	 * 是否带有@ClearInterceptor
	 */
	public boolean isClearOverallInterceptor() {
		return isClearOverallInterceptor;
	}

	/**
	 * 执行Interceptor
	 * @param actionReq
	 * @return ActionInvocation
	 */
	public ActionInvocation invokeInterceptor(ActionRequest actionReq){
		ActionInvocation ai = new ActionInvocation(globalInterceptor, this, actionReq);
		ai.invoke();//执行所有Interceptor
		return ai;
	}

}