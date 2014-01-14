package org.jleaf.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestInvoke {

	private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();

	public static HttpServletRequest getRequest() {
		return request.get();
	}

	public static void setRequest(HttpServletRequest request) {
		HttpServletRequestInvoke.request.set(request);
	}
	
	public static void remove(){
		HttpServletRequestInvoke.request.remove();
	}

}
