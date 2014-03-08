package org.jleaf.web.action.analyze;

import org.jleaf.web.annotation.HttpMethod;
import org.jleaf.web.utils.WebUtils;

/**
 * 简单实现Action请求解析
 *
 * @author leaf
 * @date 2014-1-2 下午9:27:00
 */
@SuppressWarnings("serial")
public class HttpActionAnalyze extends ActionAnalyze {

    public HttpActionAnalyze(AnalyzeParam analyzeParam) {
        super(analyzeParam);
    }

    @Override
    public AnalyzeResult analyze() {

        HttpMethod hm = WebUtils.analyzeHttpMehotd(getAnalyzeParam().getHttpMethod());
        String postfix;
        String uri = getAnalyzeParam().getUri();
        // 先去掉开头的 '/'
        if (uri.charAt(0) == '/') {
            uri = uri.substring(1);
        }

        //截取最后的.***后缀
        int dotIndex = uri.lastIndexOf(".");
        if (dotIndex >= 0) {
            postfix = uri.substring(dotIndex + 1);
            uri = uri.substring(0, dotIndex);
        }else{
            postfix = DEFAULTPostfix;
        }

        return new AnalyzeResult(uri, postfix, hm, getAnalyzeParam().getRequest().getParameterMap());
    }

}
