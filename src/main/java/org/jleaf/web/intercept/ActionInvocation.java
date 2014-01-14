package org.jleaf.web.intercept;

import java.util.List;

import org.jleaf.web.controller.ActionRequest;

public class ActionInvocation {

	private List<Interceptor> list;

	private ActionRequest actionRequest;

	private int index = 0;

	public ActionInvocation() {
		super();
	}

	public ActionInvocation(List<Interceptor> list, ActionRequest actionRequest) {
		super();
		this.list = list;
		this.actionRequest = actionRequest;
	}

	public boolean invoke() {
		if (list != null && list.size() > 0 && index < list.size()) {
			return list.get(index++).intercept(this);
		} else {
			return true;
		}
	}

	public ActionRequest getActionRequest() {
		return actionRequest;
	}

	public void setActionRequest(ActionRequest actionRequest) {
		this.actionRequest = actionRequest;
	}

	public List<Interceptor> getList() {
		return list;
	}

}
