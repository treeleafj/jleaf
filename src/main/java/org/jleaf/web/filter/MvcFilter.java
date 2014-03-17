package org.jleaf.web.filter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jleaf.config.BootConfig;
import org.jleaf.config.GlobalConfig;
import org.jleaf.config.PropertiesUtils;
import org.jleaf.config.WebApplicationInfo;
import org.jleaf.utils.LogFactory;
import org.jleaf.web.action.Action;
import org.jleaf.web.action.ActionBuilder;
import org.jleaf.web.action.HttpActionBuilder;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.core.HttpMvcDispatcher;
import org.jleaf.web.core.MvcDispatcher;
import org.jleaf.web.utils.ActionInvoke;
import org.jleaf.web.utils.HttpServletRequestInvoke;
import org.jleaf.web.utils.HttpServletResponseInvoke;
import org.jleaf.web.utils.WebUtils;

/**
 * 主入口
 * leaf
 * 14-3-1 下午4:38.
 */
@SuppressWarnings("serial")
public class MvcFilter implements Filter, Serializable {

    private static final Logger log = LogFactory.getLogger(MvcFilter.class);

    private MvcDispatcher dispatcher;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        WebApplicationInfo.setPath(filterConfig.getServletContext().getRealPath(""));
        WebApplicationInfo.setServletContext(filterConfig.getServletContext());

        //初始化全局配置
        String path = filterConfig.getServletContext().getRealPath("/WEB-INF/classes/jleaf.properties");
        Map<String, String> config = PropertiesUtils.load(path);

        Map<String,String> mainConfig = GlobalConfig.getLocalConfigMap();
        for(Map.Entry<String,String> entry : config.entrySet()){
            mainConfig.put(entry.getKey(),entry.getValue());
        }

        GlobalConfig.setConfig(mainConfig);

        //初始化启动配置
        BootConfig bootConfig = new BootConfig(WebUtils.filterConfigToMap(filterConfig));

        dispatcher = new HttpMvcDispatcher(bootConfig);

        filterConfig.getServletContext().setAttribute(WebApplicationInfo.SERVLET_CONTEXT_DISPATCHER_NAME, dispatcher);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long t = System.currentTimeMillis();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        //如果不是静态资源,则进Dispathcer
        if (!WebUtils.isStaticResource(req.getServletPath())) {
            setReqAndResp(req, resp);
            ActionBuilder rb = new HttpActionBuilder(req);
            Action action = rb.build();
            ActionInvoke.setAction(action);
            try {
                Result result = dispatcher.execute(action);
                dispatcher.render(result);
            } finally {
                removeReqAndResp();
                ActionInvoke.remove();
            }
            log.debug("[" + action.getAnalyzeResult() + "] time:" + (System.currentTimeMillis() - t) + "ms");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    private void setReqAndResp(HttpServletRequest req, HttpServletResponse resp) {
        HttpServletRequestInvoke.setRequest(req);
        HttpServletResponseInvoke.setResponse(resp);
    }

    private void removeReqAndResp() {
        HttpServletRequestInvoke.remove();
        HttpServletResponseInvoke.remove();
    }

}
