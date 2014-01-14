package org.jleaf.web.controller;


/**
 * AbstractActionRequest 解析构造工厂
 * @author leaf
 * @date 2014-1-2 下午10:49:14
 */
public abstract class ActionRequestAnalyzeFactory {
	
	public static AbstractActionRequestAnalyze create(AnalyzeParam analyzeParam) {
		return new SimpleActionRequestAnalyze(analyzeParam.getUri(),analyzeParam.getHttpMethod());
	}

}
