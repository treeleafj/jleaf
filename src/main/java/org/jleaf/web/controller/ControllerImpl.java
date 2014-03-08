package org.jleaf.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jleaf.error.ResultError;
import org.jleaf.utils.LogFactory;
import org.jleaf.web.action.Action;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.controller.result.ResultUtils;
import org.jleaf.web.intercept.Interceptor;
import org.jleaf.web.utils.AnnoUtils;

/**
 * leaf
 * 14-3-2 下午11:10.
 */
@SuppressWarnings("serial")
public class ControllerImpl implements Controller {

    private final static Logger log = LogFactory.getLogger(Controller.class);

    /**
     * 当method为空时,默认执行的方法名
     */
    private final static String DEFAULT_METHOD_NAME = "index";

    /**
     * Controller的信息
     */
    private ControllerInfo info;

    /**
     * Control
     */
    private Object controller;

    /**
     * method集合
     */
    private ActionMethods actionMehtods;

    /**
     * Controller要执行的Interceptor(包含全局的和Controller上标有@Interceptors的)
     */
    private List<Interceptor> interceptors;

    /**
     * @param classz             Controller的类型
     * @param globalInterceptors 全局拦截器
     */
    public ControllerImpl(Class<?> classz, List<Interceptor> globalInterceptors) throws IllegalAccessException, InstantiationException {
        this(classz.newInstance(), globalInterceptors);
    }

    /**
     * @param controller         Controller的实例
     * @param globalInterceptors 全局拦截器
     */
    public ControllerImpl(Object controller, List<Interceptor> globalInterceptors) {
        this.controller = controller;
        this.info = new ControllerInfo(controller);
        this.interceptors = new ArrayList<Interceptor>(globalInterceptors);
        this.interceptors.addAll(AnnoUtils.getInterceptorsByController(this));
        this.actionMehtods = new ActionMethodsImpl(this, this.interceptors);
    }

    /**
     * 通过请求的RequestUri得到要访问的method名
     */
    private String getMethodNameByRequestUri(String requestUri){
        int index = requestUri.lastIndexOf("/");
        if(index >= 0 && requestUri.length() > index + 1){
            String methodName = requestUri.substring(index + 1);
            return methodName;
        }
        return DEFAULT_METHOD_NAME;
    }

    /**
     * 获得当前要用到的Controlle(如果非单例,每次都会new一个出来)
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object getCurrentController() {
        if (!this.info.isSingleton()) { //如果不是单例,则再次new一个出来
            try {
                return controller.getClass().newInstance();
            } catch (Exception e) {
                throw new Error(e);
            }
        } else {
            return this.controller;
        }
    }

    /**
     * 执行Action
     *
     * @param action Action
     * @return 返回执行后的结果
     */
    @Override
    public Result invoke(Action action) {

        String methodName = getMethodNameByRequestUri(action.getAnalyzeResult().getUri());

        ActionMethod am = actionMehtods.getActionMethod(methodName);

        if (am == null) {
            log.debug("cant not found the method:" + methodName);
            return ResultUtils.NOTFOUND_RESULT;//返回404
        }

        Object _controller = getCurrentController();

        Result result = am.invoke(_controller, action);//执行方法

        if (result instanceof Result) {
            return result;
        } else if (result == null) {
            return ResultUtils.NULL_RESULT;//什么都不返回
        }
        throw new ResultError("return result is not implement Result");
    }

    @Override
    public Object getController() {
        return controller;
    }

    @Override
    public ControllerInfo getInfo() {
        return info;
    }

    @Override
    public ActionMethods getActionMethods() {
        return this.actionMehtods;
    }

}
