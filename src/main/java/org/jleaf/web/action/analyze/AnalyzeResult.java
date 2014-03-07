package org.jleaf.web.action.analyze;


import java.io.Serializable;
import java.util.Map;

import org.jleaf.web.annotation.HttpMethod;

/**
 * 解析结果
 */
public class AnalyzeResult implements Serializable {

    /**
     * 请求的uri
     */
    private String uri;

    /**
     * 请求的后缀(有可能是空窜,".do",".action",".json",".xml"之类)
     */
    private String postfix;

    /**
     * 请求的方式
     */
    private HttpMethod httpMethod;

    /**
     * 前端传过来的参数
     */
    private Map params;

    public AnalyzeResult(String uri, String postfix,
                         HttpMethod httpMethod, Map params) {
        this.uri = uri;
        this.postfix = postfix;
        this.httpMethod = httpMethod;
        this.params = params;
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPostfix() {
        return postfix;
    }

    public Map getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "uri:" + uri + ",httpmethod:"
                + httpMethod + ",postfix:" + postfix;
    }

}
