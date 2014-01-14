package org.jleaf.web.controller;


/**
 * 解析结果
 */
public class AnalyzeResult {

	/**
	 * 请求的Controller
	 */
	private String controllerUri;

	/**
	 * 请求的后缀(有可能是空窜,".do",".action"之类)
	 */
	private String postfix;

	private String method;

	private HttpMethod httpMethod;

	public AnalyzeResult(String controllerUri, String method,String postfix,
			HttpMethod httpMethod) {
		super();
		this.controllerUri = controllerUri;
		this.method = method;
		this.postfix = postfix;
		this.httpMethod = httpMethod;
	}

	public String getControllerUri() {
		return controllerUri;
	}

	public String getMethod() {
		return method;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getPostfix() {
		return postfix;
	}
	
	@Override
	public String toString() {
		return "uri:" + controllerUri + ",method:" + method + ",httpmethod:"
				+ httpMethod + ",postfix:" + postfix;
	}

}
