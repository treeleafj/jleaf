package org.jleaf.web.controller.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jleaf.web.utils.HttpHeadUtils;

@SuppressWarnings("serial")
public class ErrorResult extends Result {

	private Throwable throwable;
	
	private String msg;
	
	public ErrorResult(String msg){
		this.msg = msg;
	}

	public ErrorResult(Throwable throwable) {
		this.throwable = throwable;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		resp.setCharacterEncoding(HttpHeadUtils.DEFAULT_CHART_SET);
		if(this.throwable != null){
			this.throwable.printStackTrace(resp.getWriter());
		}else{
			resp.getWriter().print(msg);
		}
		resp.getWriter().close();
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

}
