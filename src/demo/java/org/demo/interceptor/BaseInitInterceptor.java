package org.demo.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jleaf.web.action.ActionContext;
import org.jleaf.web.annotation.Clear;
import org.jleaf.web.annotation.GlobalInterceptor;
import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.Interceptor;

@SuppressWarnings("serial")
@GlobalInterceptor(value = 2, clear = Clear.NOTCALEAR)
public class BaseInitInterceptor implements Interceptor {

    private Logger log = Logger.getLogger(this.getClass());

    @Override
    public boolean begin(ActionInvocation ai) {
        log.debug("=>BaseInitInterceptor.");

        HttpServletRequest request = ActionContext.getRequest();
        if (request != null) {
            String basePath = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + request.getContextPath()
                    + "/";
            request.setAttribute("base", basePath);
        }
        return true;
    }

    @Override
    public void end(ActionInvocation ai) {
        log.debug("base:end");
    }

}
