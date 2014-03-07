package org.jleaf.web.core;

import org.jleaf.web.action.Action;
import org.jleaf.web.controller.result.Result;

/**
 * mvc调度器
 * leaf
 * 14-3-1 下午2:42.
 */
public interface MvcDispatcher extends Dispatcher {

    /**
     * 执行请求
     *
     * @param action
     * @return
     */
    Result execute(Action action);


    /**
     * 渲染结果
     *
     * @param result
     */
    void render(Result result);

}
