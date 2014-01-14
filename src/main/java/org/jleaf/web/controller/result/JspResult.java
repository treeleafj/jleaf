package org.jleaf.web.controller.result;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JspResult extends Result {
	
	private String jsp;
	
	private Object obj;
	
	public JspResult(Object obj){
		this.obj = obj;
	}
	
	public JspResult(String jsp,Object obj){
		this.jsp = jsp;
		this.obj = obj;
	}

	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		req.setAttribute(getName(obj), obj);
		RequestDispatcher rd = req.getRequestDispatcher(jsp);
		rd.forward(req, resp);
	}
	
	private String getName(Object obj){
		if(obj != null){
			String name = obj.getClass().getSimpleName();
			return name.substring(0,1).toLowerCase() + name.substring(1);
		}
		return "obj";
	}

}
