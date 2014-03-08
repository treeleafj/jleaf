package org.jleaf.web.action;

import java.io.Serializable;

/**
 * leaf
 * 14-3-1 下午5:07.
 */
@SuppressWarnings("serial")
public class ActionInvoke implements Serializable {


    private static ThreadLocal<Action> action = new ThreadLocal<Action>();

    public static Action getAction() {
        return action.get();
    }

    public static void setAction(Action action) {
        ActionInvoke.action.set(action);
    }

    public static void remove() {
        action.remove();
    }
}
