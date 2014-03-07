package org.jleaf.web.action;

import java.io.Serializable;
import java.util.Map;

import org.jleaf.web.action.analyze.AnalyzeResult;

/**
 * 一次请求
 */
public interface Action extends Serializable {

    /**
     * 获得解析结果
     *
     * @return
     */
    AnalyzeResult getAnalyzeResult();

    /**
     * 获得参数
     *
     * @return
     */
    Map<String, Object> getParams();

    /**
     * 获得指定的参数
     *
     * @param name
     * @return
     */
    String getParam(String name);

}
