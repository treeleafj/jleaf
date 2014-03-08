package org.jleaf.web.action;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jleaf.utils.HttpServletRequestInvoke;
import org.jleaf.utils.HttpServletResponseInvoke;
import org.jleaf.web.action.analyze.AnalyzeResult;

/**
 * leaf
 * 14-3-1 下午4:55.
 */
@SuppressWarnings("serial")
public abstract class ActionContext implements Serializable {

    public static HttpServletRequest getRequest() {
        return HttpServletRequestInvoke.getRequest();
    }

    public static HttpServletResponse getResponse() {
        return HttpServletResponseInvoke.getResponse();
    }

    public static Action getAction() {
        return ActionInvoke.getAction();
    }

    public static AnalyzeResult getAnalyzeResult() {
        return getAction().getAnalyzeResult();
    }
}
