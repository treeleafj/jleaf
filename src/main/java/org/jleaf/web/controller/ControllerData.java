package org.jleaf.web.controller;

import org.jleaf.web.controller.annotation.Controller;

/**
 * 存放Controller的数据
 * 
 * @author leaf
 * @date 2014-1-3 下午4:36:45
 */
@SuppressWarnings("rawtypes")
public class ControllerData {

	private Class controllerClass;

	private Controller controllerAno;

	public ControllerData(Class controllerClass, Controller controllerAno) {
		super();
		this.controllerClass = controllerClass;
		this.controllerAno = controllerAno;
	}

	public Class getControllerClass() {
		return controllerClass;
	}

	public void setControllerClass(Class controllerClass) {
		this.controllerClass = controllerClass;
	}

	public Controller getControllerAno() {
		return controllerAno;
	}

	public void setControllerAno(Controller controllerAno) {
		this.controllerAno = controllerAno;
	}

}
