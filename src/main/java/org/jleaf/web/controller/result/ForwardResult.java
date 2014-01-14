package org.jleaf.web.controller.result;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jleaf.utils.WebUtils;
import org.jleaf.web.controller.ActionRequest;
import org.jleaf.web.controller.AnalyzeParam;
import org.jleaf.web.controller.AnalyzeResult;
import org.jleaf.web.filter.JleafMVC;

public class ForwardResult extends Result{
	
	private static JleafMVC mvc = new JleafMVC();
	
	private String address;
	private String method;
	

	public ForwardResult(String address) {
		super();
		this.address = address;
	}
	
	public ForwardResult(String address,String method){
		this.address = address;
		this.method = method;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		
		//如果不是静态资源文件
		if (!WebUtils.isStaticResource(address)) {
			
			if(method == null || method.trim().length() == 0){
				method = req.getMethod();
			}
			
			AnalyzeParam analyzeParam = new AnalyzeParam(address,method,req,resp);

			AnalyzeResult analyzeResult = mvc.analyze(analyzeParam);//解析Request
			
			ActionRequest actionRequest = new ActionRequest(analyzeResult, req.getParameterMap(), WebUtils.ses2Map(req.getSession()));
			
			Result result = mvc.doAction(actionRequest);
			if (result != null) {
				result.render(req, resp);
			}
		}else{
			RequestDispatcher rd = req.getRequestDispatcher(address);
			rd.forward(req, resp);
			
		}
		
	}

}
