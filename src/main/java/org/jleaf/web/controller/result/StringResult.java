package org.jleaf.web.controller.result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jleaf.web.utils.HttpHeadUtils;

/**
 * 字符串结果
 * 
 * @author leaf
 * @date 2014-1-4 上午1:19:45
 */
@SuppressWarnings("serial")
public class StringResult extends Result {

	private String s;

	public StringResult(String s) {
		super();
		this.s = s;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		resp.setContentType(HttpHeadUtils.CONTENT_TYPE_TEXT);
		resp.setCharacterEncoding(HttpHeadUtils.DEFAULT_CHART_SET);
		resp.getWriter().print(s);
		resp.getWriter().close();
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

}
