package org.demo.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jleaf.web.action.ActionContext;
import org.jleaf.web.annotation.Clear;
import org.jleaf.web.annotation.GlobalInterceptor;
import org.jleaf.web.controller.result.JsonResult;
import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.Interceptor;

@SuppressWarnings("serial")
@GlobalInterceptor(value = 2, clear = Clear.NOTCALEAR)
public class BaseInitInterceptor implements Interceptor {

    private Logger log = Logger.getLogger(this.getClass());

    @Override
    public boolean begin(ActionInvocation ai) {
        log.warn("=>BaseInitInterceptor.");

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
    	if(ai.isException()){//如果有异常,则将异常转为json格式输出,因为默认是将异常信息全部字符串输出的
    		Throwable t = ai.getThrowable();
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("msg", t.toString());
    		ai.setResult(new JsonResult(map));
    	}
        log.debug("base:end");
    }

}
