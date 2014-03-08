package org.jleaf.web.controller;

import java.io.Serializable;

/**
 * leaf
 * 14-3-1 下午7:09.
 */
@SuppressWarnings("serial")
public abstract class ControllersFactory implements Serializable {

    public static Controllers getControllers() {
        return new ControllersImpl();
    }

}
