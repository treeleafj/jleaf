package org.demo.interceptor;

import org.apache.log4j.Logger;
import org.jleaf.utils.LogFactory;
import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.Interceptor;

/**
 * leaf
 * 14-3-4 下午10:30.
 */
public class Msg2Interceptor implements Interceptor {

    private final static Logger log = LogFactory.getLogger(Msg2Interceptor.class);

    @Override
    public boolean begin(ActionInvocation ai) throws Exception {
        log.debug("Msg2Interceptor2:begin");
        return true;
    }

    @Override
    public void end(ActionInvocation ai) throws Exception {
        log.debug("Msg2Interceptor2:end");
    }
}