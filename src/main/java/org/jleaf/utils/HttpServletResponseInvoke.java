package org.jleaf.utils;

import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseInvoke {

	private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

	public static HttpServletResponse getResponse() {
		return response.get();
	}

	public static void setResponse(HttpServletResponse response) {
		HttpServletResponseInvoke.response.set(response);
	}
	
	public static void remove(){
		HttpServletResponseInvoke.response.remove();
	}

}
