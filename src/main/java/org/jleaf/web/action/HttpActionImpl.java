package org.jleaf.web.action;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.jleaf.web.action.analyze.AnalyzeResult;

/**
 * leaf
 * 14-3-1 下午4:18.
 */
@SuppressWarnings({ "unchecked", "serial" })
public class HttpActionImpl implements HttpAction {

    private AnalyzeResult analyzeResult;

    private Map<String, Object> params;

    private Map<String, Object> session;

	public HttpActionImpl(AnalyzeResult analyzeResult, Map<String, Object> session) {
        this.analyzeResult = analyzeResult;
        this.params = analyzeResult.getParams();
        this.session = session;
    }

    public HttpActionImpl(AnalyzeResult analyzeResult,Map<String,Object> params,Map<String, Object> session) {
        this.analyzeResult = analyzeResult;
        this.params = params;
        this.session = session;
    }

    public <T> T toObj(Class<T> classz) {
        try {
            Object obj = classz.newInstance();
            BeanUtils.populate(obj, params);
            return (T) obj;
        } catch (Exception e) {
            throw new Error(e.getCause());
        }
    }

    public AnalyzeResult getAnalyzeResult() {
        return analyzeResult;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Map<String, Object> getSession() {
        return session;
    }

    public Object getSesAttr(String name) {
        return session.get(name);
    }

    public Object removeSesAttr(String name) {
        return session.remove(name);
    }

    public String getParam(String name) {
        Object p = params.get(name);
        if (p instanceof String[]) {
            return ((String[])p)[0];
        }
        return (String) p;
    }

    public String[] getParamByValues(String name) {
        return (String[]) params.get(name);
    }

    @Override
    public String toString() {
        return "analyzeResult=" + analyzeResult +
                ", params=" + params +
                ", session=" + session;
    }

}
