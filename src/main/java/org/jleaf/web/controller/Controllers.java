package org.jleaf.web.controller;

import java.io.Serializable;

import org.jleaf.web.action.Action;

/**
 * leaf
 * Controller的集合
 * 14-3-1 下午6:27.
 */
public interface Controllers extends Serializable {

    /**
     * 得到Controller
     *
     * @param action
     * @return
     */
    Controller getController(Action action);

    /**
     * 添加Controller
     */
    void add(Controller controller);
}
