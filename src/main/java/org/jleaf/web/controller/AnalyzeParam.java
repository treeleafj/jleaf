package org.jleaf.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 解析器的入参
 * 
 * @author leaf
 * @date 2014-1-7 上午1:02:11
 */
public class AnalyzeParam {

	/**
	 * Controller的URI
	 */
	private String uri;

	/**
	 * http的method
	 */
	private String httpMethod;

	private HttpServletRequest request;

	private HttpServletResponse response;

	public AnalyzeParam(String uri, String httpMethod,
			HttpServletRequest request, HttpServletResponse response) {
		super();
		this.uri = uri;
		this.httpMethod = httpMethod;
		this.request = request;
		this.response = response;
	}

	public AnalyzeParam(HttpServletRequest request, HttpServletResponse response) {
		super();
		this.request = request;
		this.response = response;
		this.uri = request.getServletPath();
		this.httpMethod = request.getMethod();
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String method) {
		this.httpMethod = method;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}
