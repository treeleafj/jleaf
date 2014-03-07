package org.demo.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.demo.service.UserService;
import org.demo.service.impl.UserServiceImpl;
import org.jleaf.utils.VerifyImage;
import org.jleaf.web.action.HttpAction;
import org.jleaf.web.annotation.ClearInterceptor;
import org.jleaf.web.annotation.Control;
import org.jleaf.web.annotation.HttpMethod;
import org.jleaf.web.annotation.Method;
import org.jleaf.web.controller.result.ForwardResult;
import org.jleaf.web.controller.result.IOCallBack;
import org.jleaf.web.controller.result.IOResult;
import org.jleaf.web.controller.result.ImageResult;
import org.jleaf.web.controller.result.JsonResult;
import org.jleaf.web.controller.result.JspResult;
import org.jleaf.web.controller.result.RedirectResult;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.controller.result.StringResult;

@Control
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserController {

    private Logger log = Logger.getLogger(this.getClass());

    UserService userService = new UserServiceImpl();

    @ClearInterceptor
    @Method(HttpMethod.GET)
    public Result index(HttpAction ar) {
        log.debug(":index");
        Map map = new HashMap();
        map.put("a", "1");
        map.put("b", 2);
        map.put("c", new Date());
        return new JspResult("/index.jsp", map);
    }

    @Method(HttpMethod.GET)
    public Result forward(HttpAction ar) {
        log.debug(":foward");
        return new ForwardResult("user/json", HttpMethod.POST);
    }

    @Method(HttpMethod.GET)
    public Result json(HttpAction ar) {
        log.debug(":json");
        Map map = new HashMap();
        map.put("a", "1");
        map.put("b", 2);
        map.put("c", new Date());
        return new JsonResult(map);
    }

    public Result redirect(HttpAction ar) {
        log.debug(":redirect");
        return new RedirectResult("/user/login");
    }

    public Result file(HttpAction ar) {
        log.debug(":file");
        return new IOResult("测试.jsp",
                "F:/Java/JavaEE(Eclipse)/jleaf/src/main/webapp/index.jsp");
    }

    public Result file2(HttpAction ar) {

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

    public Result image(HttpAction ar) {

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

    @Method(HttpMethod.HEAD)
    public Result string(HttpAction ar) {
        log.debug(":string");
        return new StringResult("访问成功!");
    }

    public Result session(HttpAction ar) {
        log.debug(":session:" + ar.getSession());
        ar.getSession().put("name", ar.getParam("name"));
        return new StringResult("data:" + ar.getSesAttr("name"));
    }

}
