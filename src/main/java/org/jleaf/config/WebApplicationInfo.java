package org.jleaf.config;

import java.io.Serializable;

import javax.servlet.ServletContext;

/**
 * 应用信息
 * leaf
 * 14-3-1 下午11:32.
 */
public class WebApplicationInfo implements Serializable {

    public final static String SERVLET_CONTEXT_DISPATCHER_NAME = "jleaf_dispatcher";

    public static String getPath() {
        return path;
    }

    /**
     * 应用根路径
     */
    private static String path;

    private static ServletContext servletContext;

    public static void setPath(String path) {
        WebApplicationInfo.path = path;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static void setServletContext(ServletContext servletContext) {
        WebApplicationInfo.servletContext = servletContext;
    }
}
