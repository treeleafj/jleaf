package org.jleaf.web.controller.result;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jleaf.config.WebApplicationInfo;
import org.jleaf.utils.WebUtils;
import org.jleaf.web.action.ActionBuilder;
import org.jleaf.web.action.HttpActionBuilder;
import org.jleaf.web.annotation.HttpMethod;
import org.jleaf.web.core.MvcDispatcher;

/**
 * 后台Forward方式跳转,会自动判断是跳转到静态资源还是动态Action
 */
public class ForwardResult extends Result {

    /**
     * 请求地址
     */
    private String address;

    /**
     * 请求的方式
     */
    private HttpMethod httpMethod;


    public ForwardResult(String address) {
        this.address = address;
    }

    public ForwardResult(String address, HttpMethod httpMethod) {
        this.address = address;
        this.httpMethod = httpMethod;
    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        //如果不是静态资源文件
        if (!WebUtils.isStaticResource(address)) {
            String method;
            if (httpMethod == null || httpMethod == HttpMethod.NONE) {
                method = req.getMethod();
            }else{
                method = httpMethod.toString();
            }

            MvcDispatcher dispatcher = (MvcDispatcher) req.getServletContext().getAttribute(WebApplicationInfo.SERVLET_CONTEXT_DISPATCHER_NAME);
            if (dispatcher == null) {
                throw new Exception("ServletContext Cant not find the Dispatcher");
            }
            ActionBuilder builder = new HttpActionBuilder(address, method, req);
            Result result = dispatcher.execute(builder.build());
            dispatcher.render(result);

        } else {
            RequestDispatcher rd = req.getRequestDispatcher(address);
            rd.forward(req, resp);
        }
    }

}
