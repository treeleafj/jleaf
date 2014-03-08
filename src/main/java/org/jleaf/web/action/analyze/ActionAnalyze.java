package org.jleaf.web.action.analyze;

import java.io.Serializable;

import org.jleaf.config.GlobalConfig;

/**
 * Action请求解析
 *
 * @author leaf
 * @date 2014-1-2 下午9:25:38
 */
@SuppressWarnings("serial")
public abstract class ActionAnalyze implements Serializable {

    /**
     * 默认的后缀
     */
    public static String DEFAULTPostfix = GlobalConfig.getDefaultPostfix();

    private AnalyzeParam analyzeParam;

    public ActionAnalyze(AnalyzeParam analyzeParam) {
        this.analyzeParam = analyzeParam;
    }

    public abstract AnalyzeResult analyze();


    public AnalyzeParam getAnalyzeParam() {
        return analyzeParam;
    }

    public void setAnalyzeParam(AnalyzeParam analyzeParam) {
        this.analyzeParam = analyzeParam;
    }
}
