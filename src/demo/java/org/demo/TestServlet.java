package org.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jleaf.utils.LogFactory;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet{

	static Logger log = LogFactory.getLogger(TestServlet.class); 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("OK");
		resp.getWriter().write("OK");
		resp.getWriter().close();
	}
	
}
