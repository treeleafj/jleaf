package org.jleaf.web.controller;

import java.io.Serializable;

import org.jleaf.web.controller.result.Result;

/**
 * leaf
 * 14-3-2 下午10:59.
 */
public interface ActionMethod extends Serializable {

    Controller getController();


    ActionMethodInfo getInfo();

    /*
     * 执行Controller中的ActionMethod
     * @param action
     * @return
     */
    Result invoke(Object controller, Object... params);

}
