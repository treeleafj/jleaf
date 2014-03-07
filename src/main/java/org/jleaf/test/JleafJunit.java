package org.jleaf.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jleaf.config.BootConfig;
import org.jleaf.utils.LogFactory;
import org.jleaf.web.action.HttpActionImpl;
import org.jleaf.web.action.analyze.AnalyzeResult;
import org.jleaf.web.annotation.HttpMethod;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.core.MvcDispatcher;
import org.junit.Before;

/**
 * leaf
 * 14-3-7 下午11:22.
 */
public class JleafJunit {

    private static Logger log = LogFactory.getLogger(JleafJunit.class);

    private static MvcDispatcher dispatcher;

    @Before
    public void before() {
        dispatcher = JunitDispacher.getInstance();
    }

    protected Result action(String uri) {
        return this.action(uri, "", HttpMethod.NONE, new HashMap<String, Object>(), new HashMap<String, Object>());
    }

    protected Result action(String uri,HttpMethod httpMethod) {
        return this.action(uri, "", httpMethod, new HashMap<String, Object>(), new HashMap<String, Object>());
    }

    protected Result action(String uri, Map<String, Object> params) {
        return this.action(uri, "", HttpMethod.NONE, params, new HashMap<String, Object>());
    }

    protected Result action(String uri, HttpMethod method, Map<String, Object> params) {
        return this.action(uri, "", method, params, new HashMap<String, Object>());
    }

    protected Result action(String uri, String postfix, HttpMethod method, Map<String, Object> params, Map<String, Object> session) {
        AnalyzeResult analyzeResult = new AnalyzeResult(uri, postfix, method, params);
        HttpActionImpl action = new HttpActionImpl(analyzeResult, session);
        return dispatcher.execute(action);
    }


    public static String getPath() {
        return JunitDispacher.getPath();
    }

    public static void setPath(String path) {
        JunitDispacher.setPath(path);
    }

    public static BootConfig getBootConfig() {
        return JunitDispacher.getBootConfig();
    }

    public static void setBootConfig(BootConfig bootConfig) {
        JunitDispacher.setBootConfig(bootConfig);
    }

    public static void setBootConfig(Map<String,String> config) {
        JunitDispacher.setBootConfig(new BootConfig(config));
    }
}
