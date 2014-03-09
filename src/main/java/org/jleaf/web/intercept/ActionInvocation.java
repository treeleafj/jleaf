package org.jleaf.web.intercept;

import org.jleaf.web.action.Action;
import org.jleaf.web.controller.ControllerInfo;
import org.jleaf.web.controller.result.Result;

/**
 * 作为Interceptor的入参
 * 
 * @author leaf
 * @date 2014-1-26 下午2:44:38
 */
public class ActionInvocation {

	private ControllerInfo controllerInfo;

	private Object controller;

	private Action action;

	private Result result;

	private boolean isException = false;

	private Throwable throwable;

	public ActionInvocation() {
	}

	public ActionInvocation(ControllerInfo controllerInfo, Action action) {
		this.controllerInfo = controllerInfo;
		this.action = action;
	}

	public ActionInvocation(ControllerInfo controllerInfo, Action action,
			Object controller) {
		this.controllerInfo = controllerInfo;
		this.action = action;
		this.controller = controller;
	}

	public ControllerInfo getControllerInfo() {
		return controllerInfo;
	}

	public void setControllerInfo(ControllerInfo controllerInfo) {
		this.controllerInfo = controllerInfo;
	}

	public Object getController() {
		return controller;
	}

	public void setController(Object controller) {
		this.controller = controller;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public boolean isException() {
		return isException;
	}

	public void setException(boolean isException) {
		this.isException = isException;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

}
