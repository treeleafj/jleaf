package org.jleaf.web.controller;

import java.lang.reflect.Method;

import org.jleaf.web.annotation.HttpMethod;

/**
 * leaf
 * 14-3-5 上午12:59.
 */
public class ActionMethodInfo implements Info {

    private String name;

    private HttpMethod httpMethod = HttpMethod.NONE;

    private Method method;

    public ActionMethodInfo(Method method) {
        this.method = method;
        this.name = this.method.getName();
        org.jleaf.web.annotation.Method mAnno = this.method.getAnnotation(org.jleaf.web.annotation.Method.class);
        if (mAnno != null) {
            this.httpMethod = mAnno.value();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
