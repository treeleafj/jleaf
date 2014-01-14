package org.demo.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.demo.controller.UserController;
import org.demo.interceptor.BaseInitInterceptor;
import org.jleaf.web.controller.ControllerManager;

@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		ControllerManager.add(UserController.class);

		ControllerManager.addInterceptor(BaseInitInterceptor.class);

	}

}
