package org.jleaf.format.json;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JsonData implements Serializable {

	private boolean success = true;
	private String msg;
	private Object result;
	
	public JsonData() {
		super();
	}

	public JsonData(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public JsonData(String msg, Object result) {
		super();
		this.msg = msg;
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
