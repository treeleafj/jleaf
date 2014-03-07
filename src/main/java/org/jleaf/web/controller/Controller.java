package org.jleaf.web.controller;

import java.io.Serializable;

import org.jleaf.web.action.Action;
import org.jleaf.web.controller.result.Result;

/**
 * Control
 * leaf
 * 14-3-1 下午6:30.
 */
public interface Controller extends Serializable {

    /**
     * 执行Action
     *
     * @param action
     * @return
     */
    Result invoke(Action action);

    Object getController();

    ControllerInfo getInfo();

    ActionMethods getActionMethods();
}
