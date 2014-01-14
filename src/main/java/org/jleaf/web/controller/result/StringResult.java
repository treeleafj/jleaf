package org.jleaf.web.controller.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jleaf.web.Config;

/**
 * 字符串结果
 * @author leaf
 * @date 2014-1-4 上午1:19:45
 */
public class StringResult extends Result {
	
	private String s;

	public StringResult(String s) {
		super();
		this.s = s;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		resp.setContentType(Config.CONTENT_TYPE_TEXT);
		resp.setCharacterEncoding(Config.DEFAULT_CHART_SET);
		resp.getWriter().print(s);
		resp.getWriter().close();
	}

}
