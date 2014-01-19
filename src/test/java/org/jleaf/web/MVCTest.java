package org.jleaf.web;

import java.util.HashMap;
import java.util.Map;

import org.demo.controller.UserController;
import org.demo.interceptor.BaseInitInterceptor;
import org.jleaf.web.controller.ActionRequest;
import org.jleaf.web.controller.AnalyzeResult;
import org.jleaf.web.controller.ControllerManager;
import org.jleaf.web.controller.HttpMethod;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.filter.JleafMVC;
import org.junit.Test;

@SuppressWarnings({"rawtypes","unchecked","unused"})
public class MVCTest {
	
	static{
		
		/**
		  因为使用maven的test,导致test生成的class文件夹目录跟src生成的class目录不一致,且内存中使用的test的目录地址,
		 所以进行JUnit测试时,需要自己手动把Controller和Interceptor添加进ControllerManager
		不让就得自己手动去调用扫描的方法扫描一次classs,如果要扫描jar包的话,再去调一次起lib目录扫
		JleafMVC.getInstance().scan("F:/Java/project/jleaf/jleaf/src/main/webapp/WEB-INF/classes",new String[]{"org.demo.*"});
		*/
		JleafMVC.getInstance().getControllerManager().add(UserController.class);
		
		JleafMVC.getInstance().getControllerManager().addInterceptor(BaseInitInterceptor.class);
		
	}

	@Test
	public void test() {
		long t = System.currentTimeMillis();
		for(int i = 0; i < 1000; i++){
			actionJson();
			actionIndex();
		}
		System.out.println("执行完毕,总用时:" + (System.currentTimeMillis() - t));
	}
	
	public void actionJson(){
		Map params = new HashMap();//作为request的paramemter
		Map session = new HashMap();//作为session
		
		long t = System.currentTimeMillis();
		
		AnalyzeResult analyzeResult = new AnalyzeResult("user", "json", "", HttpMethod.GET);
		
		ActionRequest actionRequest = new ActionRequest(analyzeResult, params, session);
		
		Result result = JleafMVC.getInstance().doAction(actionRequest);
		System.out.println("执行完毕:" + (System.currentTimeMillis() - t));
	}
	
	public void actionIndex(){
		Map params = new HashMap();//作为request的paramemter
		Map session = new HashMap();//作为session
		
		long t = System.currentTimeMillis();
		
		AnalyzeResult analyzeResult = new AnalyzeResult("user", "index", "", HttpMethod.GET);
		
		ActionRequest actionRequest = new ActionRequest(analyzeResult, params, session);
		
		Result result = JleafMVC.getInstance().doAction(actionRequest);
		System.out.println("执行完毕:" + (System.currentTimeMillis() - t));
	}

}
