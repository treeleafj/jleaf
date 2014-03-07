package org.jleaf.web.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jleaf.config.BootConfig;
import org.jleaf.config.WebApplicationInfo;
import org.jleaf.error.RenderError;
import org.jleaf.search.ClassData;
import org.jleaf.utils.LogFactory;
import org.jleaf.web.action.Action;
import org.jleaf.web.action.ActionContext;
import org.jleaf.web.annotation.ClearInterceptor;
import org.jleaf.web.controller.Controller;
import org.jleaf.web.controller.ControllerImpl;
import org.jleaf.web.controller.Controllers;
import org.jleaf.web.controller.ControllersFactory;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.controller.result.ResultUtils;
import org.jleaf.web.intercept.Interceptor;
import org.jleaf.web.utils.AnnoUtils;

/**
 * leaf
 * 14-3-1 下午4:12.
 */
public class HttpMvcDispatcher implements MvcDispatcher {

    private final static Logger log = LogFactory.getLogger(HttpMvcDispatcher.class);

    /**
     * 启动参数
     */
    private BootConfig bootConfig;

    /**
     * Controller集合
     */
    private Controllers controllers = ControllersFactory.getControllers();

    /**
     * 全局Interceptor
     */
    private List<Interceptor> globalInterceptor = new ArrayList<Interceptor>();

    public HttpMvcDispatcher(BootConfig bootConfig) {
        this.bootConfig = bootConfig;
        this.init();
    }


    /**
     * 初始化
     */
    private void init() {

        if (this.bootConfig.isScan()) {

            try {
                this.scan(WebApplicationInfo.getPath() + "/WEB-INF/classes", bootConfig.getPackages()); //扫描classes目录下注解
                log.debug("search class end");
                if (bootConfig.isScanLib()) {
                    String libDir = WebApplicationInfo.getPath() + "/WEB-INF/lib";
                    this.scan(libDir, bootConfig.getPackages()); //扫描lib目录下注解
                    log.debug("search lib jar end");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 扫描指定包路径下的文件
     *
     * @param basePath 扫描的路径
     * @param ps       要扫描控制器和拦截器的包路径,例如 {"com.demo.*","com.demo2.*"}
     */
    private void scan(String basePath, String[] ps) throws Exception {

        final String[] packages = ps;

        List<ClassData> classDatas = AnnoUtils.searchClassDatasByPackage(basePath, ps);

        this.globalInterceptor.addAll(AnnoUtils.getInterceptorsByClassDatas(classDatas));
        log.debug("add global interceptors,count:" + this.globalInterceptor.size());

        List<Class<?>> controllerClasses = AnnoUtils.getControllerClassesByClassDatas(classDatas);
        for (Class<?> classz : controllerClasses) {
            ClearInterceptor clearInterceptor = classz.getAnnotation(ClearInterceptor.class);
            //得到该Controller需要触发的Interceptor
            List<Interceptor> list = AnnoUtils.getInterceptorsByClearInterceptor(clearInterceptor, this.globalInterceptor);
            this.controllers.add(new ControllerImpl(classz, list));
        }
        log.debug("add controller,count:" + controllerClasses.size());
    }

    /**
     * 执行Action
     *
     * @param action
     * @return 返回Result对象
     */
    @Override
    public Result execute(Action action) {
        // 如果ControllerManager里包含了这个地址,则执行Action操作
        Controller controller = controllers.getController(action);
        if (controller != null) {
            return controller.invoke(action);
        }
        log.warn("cont not found the Controller uri:" + action.getAnalyzeResult().getUri());
        return ResultUtils.NOTFOUND_RESULT;
    }

    /**
     * 渲染结果
     *
     * @param result
     */
    @Override
    public void render(Result result) {
        if (result != null) {
            try {
                result.render(ActionContext.getRequest(), ActionContext.getResponse());
            } catch (Exception e) {
                throw new RenderError(e);
            }
        }
    }
}
