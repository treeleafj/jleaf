package org.jleaf.web.intercept;

import org.jleaf.web.controller.result.Result;

/**
 * leaf
 * 14-3-2 下午6:10.
 */
public interface InterceptorInvoker {


    Result invokeBegin() throws Exception;


    void invokeEnd() throws Exception;

}
