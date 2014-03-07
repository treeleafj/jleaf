package org.jleaf.web.controller;

import java.io.Serializable;

/**
 * ActionMethod的集合
 * leaf
 * 14-3-2 下午10:58.
 */
public interface ActionMethods extends Serializable {

    /**
     * 通过name获得对应的ActionMethod
     */
    ActionMethod getActionMethod(String name);

}
