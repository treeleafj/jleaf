package org.jleaf.web.utils;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HttpServletResponseInvoke implements Serializable {

    private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

    public static HttpServletResponse getResponse() {
        return response.get();
    }

    public static void setResponse(HttpServletResponse response) {
        HttpServletResponseInvoke.response.set(response);
    }

    public static void remove() {
        HttpServletResponseInvoke.response.remove();
    }

}
