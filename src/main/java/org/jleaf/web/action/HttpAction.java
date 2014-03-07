package org.jleaf.web.action;

import java.util.Map;

/**
 * Web的一次请求
 * 14-3-1 下午4:22.
 */
public interface HttpAction extends Action {
    /**
     * 获得Session
     *
     * @return
     */
    Map<String, Object> getSession();

    /**
     * 获得Session中的某个值
     *
     * @param name
     * @return
     */
    Object getSesAttr(String name);

    /**
     * 删除session中的某个值
     *
     * @param name
     * @return
     */
    Object removeSesAttr(String name);

    /**
     * 获得指定的参数
     *
     * @param name
     * @return
     */
    String[] getParamByValues(String name);

    /**
     * 将params转为指定对象
     *
     * @param classz
     * @param <T>
     * @return
     */
    <T> T toObj(Class<T> classz);
}
