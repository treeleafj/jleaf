package org.jleaf.web.filter;

import org.apache.log4j.Logger;
import org.jleaf.error.NotFindError;
import org.jleaf.web.controller.AbstractActionRequestAnalyze;
import org.jleaf.web.controller.ActionRequest;
import org.jleaf.web.controller.ActionRequestAnalyzeFactory;
import org.jleaf.web.controller.AnalyzeParam;
import org.jleaf.web.controller.AnalyzeResult;
import org.jleaf.web.controller.ControllerManager;
import org.jleaf.web.controller.result.Result;

/**
 * Jleaf框架主要执行步骤
 * @author leaf
 * @date 2014-1-4 上午12:49:55
 */
public class JleafMVC {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 解析
	 * @param analyzeParam
	 * @return
	 */
	public AnalyzeResult analyze(AnalyzeParam analyzeParam){
		AbstractActionRequestAnalyze analyze = ActionRequestAnalyzeFactory.create(analyzeParam);
		AnalyzeResult analyzeResult = analyze.analyze();
		log.debug(analyzeResult);
		return analyzeResult;
	}
	
	/**
	 * 执行Jleaf框架的开始操作(解析->执行),如果不是需要解析的其他请求,则返回false
	 * @param req
	 * @param resp
	 * @return
	 */
	public Result doAction(ActionRequest ar){
		
		//如果ControllerManager里包含了这个地址,则执行Action操作
		if(ControllerManager.contains(ar.getAnalyzeResult().getControllerUri())){
			return ControllerManager.doAction(ar);
		}
		throw new NotFindError("未找到匹配的" + ar.getAnalyzeResult().getControllerUri());
	}

}
