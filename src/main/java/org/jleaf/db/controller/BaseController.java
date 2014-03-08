package org.jleaf.db.controller;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import org.jleaf.web.annotation.HttpMethod;
import org.jleaf.web.controller.result.ForwardResult;
import org.jleaf.web.controller.result.IOCallBack;
import org.jleaf.web.controller.result.IOResult;
import org.jleaf.web.controller.result.JsonResult;
import org.jleaf.web.controller.result.JspResult;
import org.jleaf.web.controller.result.RedirectResult;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.controller.result.ResultUtils;
import org.jleaf.web.controller.result.StringResult;

@SuppressWarnings("serial")
public class BaseController implements Serializable{

	protected Result jsp(String jsp) {
		return new JspResult(jsp);
	}

	protected Result jsp(String jsp, Object obj) {
		return new JspResult(jsp, obj);
	}

	protected Result json(Object obj) {
		return new JsonResult(obj);
	}
	
	protected Result string(String s) {
		return new StringResult(s);
	}

	protected Result forward(String address) {
		return new ForwardResult(address);
	}

	protected Result forward(String address, HttpMethod httpMethod) {
		return new ForwardResult(address, httpMethod);
	}

	protected Result redirect(String address) {
		return new RedirectResult(address);
	}
	
	protected Result notFound() {
		return ResultUtils.NOTFOUND_RESULT;
	}

	protected Result io(InputStream in) {
		return new IOResult(in);
	}

	public Result io(IOCallBack ioCallBack) {
		return new IOResult(ioCallBack);
	}

	public Result io(String filename, InputStream in) {
		return new IOResult(filename, in);
	}

	public Result io(String filename, File file) {
		return new IOResult(filename, file);
	}

	public Result io(String filename, String file) {
		return new IOResult(filename, file);
	}

	public Result io(String filename, IOCallBack ioCallBack) {
		return new IOResult(filename, ioCallBack);
	}

}
