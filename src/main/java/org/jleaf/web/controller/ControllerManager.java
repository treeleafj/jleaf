package org.jleaf.web.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jleaf.error.NotFindError;
import org.jleaf.web.controller.annotation.Controller;
import org.jleaf.web.controller.result.NullResult;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.Interceptor;

/**
 * Contoller管理器
 * 
 * @author leaf
 * @date 2014-1-3 上午1:33:38
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ControllerManager {
	
	private static final Result NULL_RESULT = new NullResult();

	private static Logger log = Logger.getLogger(ControllerManager.class);

	/**
	 * actionCache缓存Map
	 */
	private static Map<String, ActionCache> actionCaches = new HashMap();

	/**
	 * 控制器
	 */
	private static Map<String, ControllerData> controllers = new HashMap();
	
	/**
	 * 全局拦截器
	 */
	private static List<Interceptor> overallInterceptor = new LinkedList<Interceptor>();

	/**
	 * 判断是否包含指定URI的Controller
	 */
	public static boolean contains(String controllerUri) {
		return contains(controllerUri, HttpMethod.NONE);
	}

	/**
	 * 判断是否包含指定URI和httpmethod的Controller
	 */
	public static boolean contains(String controllerUri, HttpMethod httpMethod) {
		ControllerData data = controllers.get(controllerUri);
		if (data != null
				&& (httpMethod == HttpMethod.NONE || data.getControllerAno()
						.method() == httpMethod)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 执行Action
	 * 
	 * @param actionReq
	 * @return
	 */
	public static Result doAction(ActionRequest actionReq) {
		try{
			ActionCache action = getActionCache(actionReq);
			if(action == null){
				AnalyzeResult aResult = actionReq.getAnalyzeResult();
				throw new NotFindError("未找到匹配的"
						+ aResult.getControllerUri() + "." + aResult.getMethod()
						+ ":" + aResult.getHttpMethod());
			}else{
				
				if(!action.isClearOverallInterceptor()){
					boolean b = doOverallInterceptor(actionReq);//执行全局的Interceptor
					if(b == false){
						return NULL_RESULT;
					}
				}
				
				action.invokeInterceptor(actionReq);//执行私有的Interceptor
				
				log.debug("执行method");
				
				return (Result)action.getMethod().invoke(action.getObj(), actionReq);
			}
		}catch(NotFindError e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new Error(e.fillInStackTrace());
		}
	}

	/**
	 * 获得缓存的ActionCache
	 * @param actionReq
	 * @return
	 * @throws Exception
	 */
	public static ActionCache getActionCache(ActionRequest actionReq)
			throws Exception {

		AnalyzeResult aResult = actionReq.getAnalyzeResult();

		String key = aResult.getControllerUri() + "." + aResult.getMethod()
				+ "." + aResult.getPostfix();

		ActionCache actionCache = actionCaches.get(key);
		if (actionCache == null) {
			//注意线程安全
			synchronized(ControllerManager.class){
				if (actionCache == null) {
					log.debug("未找到ActionCache,进行初始化.");
					actionCache = newInstance(actionReq);
					actionCaches.put(key, actionCache);//缓存
				}
				actionCache = actionCaches.get(key);
			}
		}
		
		if(actionCache.getHttpMethod() == HttpMethod.NONE || actionCache.getHttpMethod() == aResult.getHttpMethod()){
			return actionCache;
		}
		return null;
	}
	
	/**
	 * 创建ActionCache对象
	 * @param actionReq
	 * @return
	 * @throws Exception
	 */
	public static ActionCache newInstance(ActionRequest actionReq) throws Exception{
		AnalyzeResult aResult = actionReq.getAnalyzeResult();
		
		ControllerData cd = ControllerManager.getControllerData(aResult
				.getControllerUri());

		Class classz = cd.getControllerClass();

		Method method = classz.getMethod(aResult.getMethod(),
				ActionRequest.class);
		ActionCache actionCache = new ActionCache(classz, method);
		return actionCache;
	}
	

	/**
	 * 执行所有全局的Interceptor
	 * @param actionReq
	 */
	public static boolean doOverallInterceptor(ActionRequest actionReq) {
	
		if (overallInterceptor.size() > 0) {
			ActionInvocation ai = new ActionInvocation(overallInterceptor, actionReq);
			boolean b = ai.invoke();
			log.debug("全局Interceptor执行完毕.");
			return b;
		}
		
		return true;
	}

	/**
	 * 获得ControllerData
	 * 
	 * @param controllerUri
	 */
	public static ControllerData getControllerData(String controllerUri) {
		return controllers.get(controllerUri);
	}

	/**
	 * 添加一个Controller
	 * 
	 * @param classz
	 *            Controller的类型
	 */
	public static void add(Class classz) {

		Controller c = (Controller) classz.getAnnotation(Controller.class);

		if (c != null) {
			String uri = c.value();
			if (uri == null || uri.trim().length() == 0) {
				uri = getControllerDefaultName(classz.getSimpleName());
			}

			if (!contains(uri)) {
				ControllerData controllerData = new ControllerData(classz, c);
				controllers.put(uri, controllerData);
			} else {
				throw new Error("重复添加Controller,地址为:" + uri);
			}
		} else {
			throw new Error("未添加@Controller注解的Class无法添加进ControllerManager");
		}
	}

	/**
	 * 添加一个Interceptor
	 */
	public static void addInterceptor(Class<? extends Interceptor> interceptor) {
		try {
			overallInterceptor.add(interceptor.newInstance());
		} catch (Exception e) {
			throw new Error(e.fillInStackTrace());
		}
	}
	
	/**
	 * 添加一个Interceptor
	 */
	public static void addInterceptor(Interceptor interceptor) {
		overallInterceptor.add(interceptor);
	}

	/**
	 * 根据Controller的类名得出 默认的uri
	 * 
	 * @param className
	 */
	private static String getControllerDefaultName(String className) {

		if (!className.equals("Controller") && className.endsWith("Controller")) {
			int index = className.lastIndexOf("Controller");
			className = className.substring(0, index);
		} else if (!className.equals("Action") && className.endsWith("Action")) {
			int index = className.lastIndexOf("Action");
			className = className.substring(0, index);
		}

		if (className.length() == 1) {
			className = className.toLowerCase();
		} else {
			className = className.substring(0, 1).toLowerCase()
					+ className.substring(1);
		}

		return className;

	}
}
