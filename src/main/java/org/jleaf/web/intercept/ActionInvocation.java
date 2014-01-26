package org.jleaf.web.intercept;

import java.util.List;

import org.jleaf.web.controller.ActionCache;
import org.jleaf.web.controller.ActionRequest;
import org.jleaf.web.controller.result.Result;

/**
 * 作为Interceptor的入参,包含了ActionRequest,和执行完成后返回的Result
 * 
 * @author leaf
 * @date 2014-1-26 下午2:44:38
 */
public class ActionInvocation {

	private List<Interceptor> list;

	private ActionCache actionCache;

	private ActionRequest actionRequest;

	private Result result;

	private int index = 0;

	public ActionInvocation() {
		super();
	}

	public ActionInvocation(List<Interceptor> list, ActionCache actionCache,
			ActionRequest actionRequest) {
		this.list = list;
		this.actionCache = actionCache;
		this.actionRequest = actionRequest;
	}

	public boolean invoke() {
		if (list != null && list.size() > 0 && index < list.size()) {
			return list.get(index++).intercept(this);
		} else {
			return true;
		}
	}

	public ActionCache getActionCache() {
		return actionCache;
	}

	public void setActionCache(ActionCache actionCache) {
		this.actionCache = actionCache;
	}

	public List<Interceptor> getList() {
		return list;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public ActionRequest getActionRequest() {
		return actionRequest;
	}

	public void setActionRequest(ActionRequest actionRequest) {
		this.actionRequest = actionRequest;
	}

}
