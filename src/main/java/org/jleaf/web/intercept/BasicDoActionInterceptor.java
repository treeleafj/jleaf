package org.jleaf.web.intercept;

import org.jleaf.web.controller.ActionCache;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.intercept.annotation.GlobalInterceptor;
import org.jleaf.web.intercept.annotation.LiveLevel;

/**
 * 最底层的执行Controller中method的Interceptor
 * @author leaf
 * @date 2014-1-26 下午2:52:35
 */
@GlobalInterceptor(value=999999999,liveLevel=LiveLevel.NOTCALEAR)
public class BasicDoActionInterceptor implements Interceptor{

	public boolean intercept(ActionInvocation ai) {
		ActionCache ac = ai.getActionCache();
		try {
			Result result = (Result) ac.getMethod().invoke(ac.getController(), ai.getActionRequest());
			ai.setResult(result);
			return ai.invoke();
		} catch (Exception e) {
			throw new Error(e);
		}
	}

}
