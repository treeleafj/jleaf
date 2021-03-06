package org.jleaf.web.utils;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class HttpServletRequestInvoke implements Serializable {

    private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();

    public static HttpServletRequest getRequest() {
        return request.get();
    }

    public static void setRequest(HttpServletRequest request) {
        HttpServletRequestInvoke.request.set(request);
    }

    public static void remove() {
        HttpServletRequestInvoke.request.remove();
    }

}
