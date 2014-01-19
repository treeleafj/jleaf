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
		ControllerManager.add(UserController.class);
		
		ControllerManager.addInterceptor(BaseInitInterceptor.class);
	}

	@Test
	public void test() {
		long t = System.currentTimeMillis();
		for(int i = 0; i < 1000; i++){
			actionLogin();
			actionIndex();
		}
		System.out.println("执行完毕,总用时:" + (System.currentTimeMillis() - t));
	}
	
	public void actionLogin(){
		Map params = new HashMap();
		Map session = new HashMap();
		
		long t = System.currentTimeMillis();
		
		AnalyzeResult analyzeResult = new AnalyzeResult("user", "login", "", HttpMethod.GET);
		
		ActionRequest actionRequest = new ActionRequest(analyzeResult, params, session);
		
		Result result = JleafMVC.getInstance().doAction(actionRequest);
		System.out.println("执行完毕:" + (System.currentTimeMillis() - t));
	}
	
	public void actionIndex(){
		Map params = new HashMap();
		Map session = new HashMap();
		
		long t = System.currentTimeMillis();
		
		AnalyzeResult analyzeResult = new AnalyzeResult("user", "index", "", HttpMethod.GET);
		
		ActionRequest actionRequest = new ActionRequest(analyzeResult, params, session);
		
		Result result = JleafMVC.getInstance().doAction(actionRequest);
		System.out.println("执行完毕:" + (System.currentTimeMillis() - t));
	}

}
