package org.jleaf.web.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jleaf.utils.LogFactory;
import org.jleaf.utils.SessionAdapterMap;
import org.jleaf.web.action.analyze.ActionAnalyze;
import org.jleaf.web.action.analyze.ActionAnalyzeFactory;
import org.jleaf.web.action.analyze.AnalyzeParam;
import org.jleaf.web.action.analyze.AnalyzeResult;

/**
 * leaf
 * 14-3-1 下午4:29.
 */
public class HttpActionBuilder implements ActionBuilder {

    private final static Logger log = LogFactory.getLogger(HttpActionBuilder.class);

    private HttpServletRequest request;

    private String uri;

    private String httpMethod;

    public HttpActionBuilder(String uri, String httpMethod, HttpServletRequest request) {
        this.request = request;
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    public HttpActionBuilder(HttpServletRequest request) {
        this.request = request;
        this.uri = request.getRequestURI();
        this.httpMethod = request.getMethod();
    }

    @Override
    public Action build() {
        AnalyzeParam analyzeParam = new AnalyzeParam(uri, httpMethod, request);
        ActionAnalyze analyze = ActionAnalyzeFactory.create(analyzeParam);
        AnalyzeResult analyzeResult = analyze.analyze();// 解析用户请求
        HttpAction wr = new HttpActionImpl(analyzeResult, new SessionAdapterMap(request.getSession()));
        return wr;
    }
}
