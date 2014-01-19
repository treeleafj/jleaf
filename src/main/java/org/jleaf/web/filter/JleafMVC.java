package org.jleaf.web.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jleaf.error.NotFindError;
import org.jleaf.web.controller.AbstractActionRequestAnalyze;
import org.jleaf.web.controller.ActionRequest;
import org.jleaf.web.controller.ActionRequestAnalyzeFactory;
import org.jleaf.web.controller.AnalyzeParam;
import org.jleaf.web.controller.AnalyzeResult;
import org.jleaf.web.controller.ControllerManager;
import org.jleaf.web.controller.annotation.Controller;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.intercept.Interceptor;
import org.jleaf.web.intercept.annotation.GlobalInterceptor;
import org.search.ClassData;
import org.search.ClassFilter;
import org.search.ClassSearcher;

/**
 * Jleaf框架主要执行步骤
 * 
 * @author leaf
 * @date 2014-1-4 上午12:49:55
 */
public class JleafMVC {
	
	private static Logger log = Logger.getLogger(JleafMVC.class);

	private static JleafMVC mvc = new JleafMVC();

	private JleafMVC() {

	}

	public static JleafMVC getInstance() {
		return mvc;
	}
	
	
	/**
	 * 扫描指定包路径下的控制器,拦截器
	 * @param ps 要扫描控制器和拦截器的包路径,例如 {"com.demo.*","com.demo2.*"}
	 */
	@SuppressWarnings("unchecked")
	public void scan(String[] ps){
		
		if(ps.length == 0){
			ps = new String[] {"*"};
		}
		
		final String[] packages = ps;
		
		ClassSearcher classSearcher = new ClassSearcher();
		try {
			List<ClassData> result = classSearcher.search(new ClassFilter() {
				
				public boolean filter(ClassData classData) {
					try {
						
						if (ClassSearcher.wildcardMatch(packages, classData.getClassName())) {
							
							Class<?> classz = Class.forName(classData.getClassName());
							
							if (classz.getAnnotation(Controller.class) != null) {
								return true;
							}else if(classz.getAnnotation(GlobalInterceptor.class) != null && Interceptor.class.isAssignableFrom(classz)){
								return true;
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return false;
				}
			});
			
			List<Class<? extends Interceptor>> interceptors = new ArrayList<Class<? extends Interceptor>>();
			
			for(ClassData data : result){
				Class<?> classz = Class.forName(data.getClassName());
				
				if (classz.getAnnotation(Controller.class) != null) {
					ControllerManager.add(classz);
				}else{
					interceptors.add((Class<? extends Interceptor>) classz);
				}
			}
			
			Collections.sort(interceptors, new Comparator<Class<? extends Interceptor>>() {

				public int compare(Class<? extends Interceptor> o1, Class<? extends Interceptor> o2) {
					try {
						GlobalInterceptor anno1 = (GlobalInterceptor) o1
								.getAnnotation(GlobalInterceptor.class);
						GlobalInterceptor anno2 = (GlobalInterceptor) o2
								.getAnnotation(GlobalInterceptor.class);
						return anno1.value() - anno2.value();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return 0;
				}
			});
			
			for(Class<? extends Interceptor> classz : interceptors){
				ControllerManager.addInterceptor(classz);
			}
			
		}catch(Exception e){
			throw new Error(e);
		}
		
	}

	/**
	 * 解析
	 * 
	 * @param analyzeParam
	 * @return
	 */
	public AnalyzeResult analyze(AnalyzeParam analyzeParam) {
		AbstractActionRequestAnalyze analyze = ActionRequestAnalyzeFactory
				.create(analyzeParam);
		AnalyzeResult analyzeResult = analyze.analyze();
		log.debug(analyzeResult);
		return analyzeResult;
	}

	/**
	 * 执行Jleaf框架的开始操作(解析->执行),如果不是需要解析的其他请求,则返回false
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public Result doAction(ActionRequest ar) {

		// 如果ControllerManager里包含了这个地址,则执行Action操作
		if (ControllerManager
				.contains(ar.getAnalyzeResult().getControllerUri())) {
			return ControllerManager.doAction(ar);
		}
		throw new NotFindError("未找到匹配的"
				+ ar.getAnalyzeResult().getControllerUri());
	}

}
