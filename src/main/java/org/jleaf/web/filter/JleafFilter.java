package org.jleaf.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jleaf.error.NotFindError;
import org.jleaf.utils.HttpServletRequestInvoke;
import org.jleaf.utils.HttpServletResponseInvoke;
import org.jleaf.utils.SessionAdapterMap;
import org.jleaf.utils.WebUtils;
import org.jleaf.web.controller.ActionRequest;
import org.jleaf.web.controller.AnalyzeParam;
import org.jleaf.web.controller.AnalyzeResult;
import org.jleaf.web.controller.result.Result;

/**
 * 请求封装从这里开始
 * 
 * @author leaf
 * @date 2014-1-2 下午2:41:01
 */
public class JleafFilter implements Filter {
	
	private Logger log = Logger.getLogger(this.getClass());

	public void init(FilterConfig filterConfig) throws ServletException {
		
		String scan = filterConfig.getInitParameter("scan");
		String scanJar = filterConfig.getInitParameter("scan-jar");
		if(!"false".equals(scan)){
			String packages = filterConfig.getInitParameter("package");
			if(packages != null){
				//扫描classes目录下注解
				JleafMVC.getInstance().scan(packages.split(","));
				
				if(!"false".equals(scanJar)){
					//扫描lib目录下注解
					String libDir = filterConfig.getServletContext().getRealPath("/WEB-INF/lib");
					JleafMVC.getInstance().scan(libDir,packages.split(","));
				}
			}
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// 如果不是静态资源文件
		if (!WebUtils.isStaticResource(req.getServletPath())) {

			long t = System.currentTimeMillis();

			AnalyzeParam analyzeParam = new AnalyzeParam(req, resp);

			AnalyzeResult analyzeResult = JleafMVC.getInstance().analyze(
					analyzeParam);// 解析用户请求

			setReqAndResp(req, resp);

			ActionRequest actionRequest = new ActionRequest(analyzeResult,
					req.getParameterMap(), new SessionAdapterMap(
							req.getSession()));
			try {
				Result result = JleafMVC.getInstance().doAction(actionRequest);
				if (result != null) {
					result.render(req, resp);
				}
			} catch (NotFindError e) {
				log.error("NotFindError:", e);
				chain.doFilter(request, response);
			} catch (Exception e) {
				throw new Error(e);
			} finally {
				removeReqAndResp();
			}

			log.info("Action执行完毕,用时:" + (System.currentTimeMillis() - t));

		} else {
			chain.doFilter(request, response);
		}

	}

	public void destroy() {

	}

	private void setReqAndResp(HttpServletRequest req, HttpServletResponse resp) {
		HttpServletRequestInvoke.setRequest(req);
		HttpServletResponseInvoke.setResponse(resp);
	}

	private void removeReqAndResp() {
		HttpServletRequestInvoke.remove();
		HttpServletResponseInvoke.remove();
	}

}
