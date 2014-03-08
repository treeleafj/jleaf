package org.jleaf.web.controller;

import java.io.Serializable;
import java.util.List;

import org.jleaf.web.controller.result.Result;
import org.jleaf.web.intercept.Interceptor;

/**
 * leaf
 * 14-3-2 下午10:59.
 */
public interface ActionMethod extends Serializable {

    Controller getController();

    ActionMethodInfo getInfo();
    
    List<Interceptor> getInterceptors();

    /*
     * 执行Controller中的ActionMethod
     * @param action
     * @return
     */
    Result invoke(Object controller, Object... params);

}
