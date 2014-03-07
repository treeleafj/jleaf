package org.jleaf.web.intercept;

import java.io.Serializable;

/**
 * 拦截器
 *
 * @author leaf
 * @date 2014-1-3 上午1:32:17
 */
public interface Interceptor extends Serializable {

    /**
     * 开始
     *
     * @param ai
     * @return 返回是否继续下去
     */
    boolean begin(ActionInvocation ai) throws Exception;

    /**
     * 结束
     *
     * @param ai
     */
    void end(ActionInvocation ai) throws Exception;

}
