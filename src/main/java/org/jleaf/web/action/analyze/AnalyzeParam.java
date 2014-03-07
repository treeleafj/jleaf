package org.jleaf.web.action.analyze;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析器的入参
 *
 * @author leaf
 * @date 2014-1-7 上午1:02:11
 */
public class AnalyzeParam implements Serializable {

    /**
     * Controller的URI
     */
    private String uri;

    /**
     * http的method
     */
    private String httpMethod;

    private HttpServletRequest request;

    public AnalyzeParam(String uri, String httpMethod,
                        HttpServletRequest request) {
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.request = request;
    }

    public AnalyzeParam(HttpServletRequest request) {
        this.request = request;
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

}
