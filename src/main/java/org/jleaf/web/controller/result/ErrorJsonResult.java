package org.jleaf.web.controller.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ErrorJsonResult extends JsonResult {

	public ErrorJsonResult(Object obj) {
		super(obj);
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		super.render(req, resp);
	}

}
