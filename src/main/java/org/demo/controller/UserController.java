package org.demo.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.demo.interceptor.ClassInterceptor;
import org.jleaf.utils.VerifyImage;
import org.jleaf.web.controller.ActionRequest;
import org.jleaf.web.controller.HttpMethod;
import org.jleaf.web.controller.annotation.Controller;
import org.jleaf.web.controller.annotation.Method;
import org.jleaf.web.controller.result.ForwardResult;
import org.jleaf.web.controller.result.IOCallBack;
import org.jleaf.web.controller.result.IOResult;
import org.jleaf.web.controller.result.ImageResult;
import org.jleaf.web.controller.result.JsonResult;
import org.jleaf.web.controller.result.JspResult;
import org.jleaf.web.controller.result.RedirectResult;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.controller.result.StringResult;
import org.jleaf.web.intercept.annotation.ClearInterceptor;
import org.jleaf.web.intercept.annotation.Interceptors;

@Controller
@Interceptors(ClassInterceptor.class)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserController {
	
	private Logger log = Logger.getLogger(this.getClass());

	@ClearInterceptor
	@Interceptors(ClassInterceptor.class)
	@Method(HttpMethod.GET)
	public Result index(ActionRequest ar) {
		log.debug(":index");
		Map map = new HashMap();
		map.put("a", "1");
		map.put("b", 2);
		map.put("c", new Date());
		return new JspResult("/index.jsp", map);
	}

	@Method(HttpMethod.GET)
	public Result forward(ActionRequest ar) {
		log.debug(":foward");
		return new ForwardResult("user/login", "post");
	}

	@Method(HttpMethod.NONE)
	public Result json(ActionRequest ar) {
		log.debug(":login");
		Map map = new HashMap();
		map.put("a", "1");
		map.put("b", 2);
		map.put("c", new Date());
		return new JsonResult(map);
	}

	public Result redirect(ActionRequest ar) {
		log.debug(":redirect");
		return new RedirectResult("/user/login");
	}

	public Result file(ActionRequest ar) {
		log.debug(":file");
		return new IOResult("测试.jsp",
				"F:/Java/JavaEE(Eclipse)/jleaf/src/main/webapp/index.jsp");
	}

	public Result file2(ActionRequest ar) {

		log.debug(":file2");

		return new IOResult("test2.jsp", new IOCallBack() {

			public void callback(OutputStream out) {
				try {
					FileInputStream in = new FileInputStream(
							"F:/Java/JavaEE(Eclipse)/jleaf/src/main/webapp/index.jsp");
					IOUtils.copy(in, out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Result image(ActionRequest ar) {

		log.debug(":image");

		return new ImageResult(new IOCallBack() {

			public void callback(OutputStream out) {
				VerifyImage img = new VerifyImage();
				String verifyStr = img.createVerify();
				try {
					img.createVerifyImage(verifyStr, out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Result string(ActionRequest ar){
		log.debug(":string");
		return new StringResult("访问成功!");
	}

	public Result session(ActionRequest ar){
		log.debug(":session:" + ar.getSession());
		ar.getSession().put("name", ar.getParam("name"));
		return new StringResult("data:" + ar.getSesAttr("name"));
	}
}
