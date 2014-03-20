package org.shop.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jleaf.web.action.ActionContext;
import org.jleaf.web.annotation.Clear;
import org.jleaf.web.annotation.GlobalInterceptor;
import org.jleaf.web.controller.result.ErrorJsonResult;
import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.Interceptor;

@GlobalInterceptor(value=0,clear=Clear.NOTCALEAR)
@SuppressWarnings("serial")
public class BasicInterceptor implements  Interceptor{

	@Override
	public boolean begin(ActionInvocation ai) throws Exception {
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
	public void end(ActionInvocation ai) throws Exception {
		if(ai.isException()){
			if("json".equals(ai.getAction().getAnalyzeResult().getPostfix())){
				Throwable t = ai.getThrowable();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("msg", t.toString());
				ai.setResult(new ErrorJsonResult(map));
			}
    	}
	}

}
