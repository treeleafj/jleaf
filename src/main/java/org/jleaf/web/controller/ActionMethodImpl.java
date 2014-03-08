package org.jleaf.web.controller;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.jleaf.utils.LogFactory;
import org.jleaf.web.action.Action;
import org.jleaf.web.action.analyze.AnalyzeResult;
import org.jleaf.web.annotation.HttpMethod;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.controller.result.ResultUtils;
import org.jleaf.web.intercept.ActionInvocation;
import org.jleaf.web.intercept.Interceptor;
import org.jleaf.web.intercept.InterceptorInvoker;
import org.jleaf.web.intercept.SimpleInterceptorInvoker;
import org.jleaf.web.utils.HttpMethodUtils;

/**
 * leaf
 * 14-3-2 下午10:56.
 */
@SuppressWarnings("serial")
public class ActionMethodImpl implements ActionMethod {

    private final static Logger log = LogFactory.getLogger(ActionMethodImpl.class);

    private Controller controller;

    private ActionMethodInfo info;

    private List<Interceptor> interceptors;

    public ActionMethodImpl(Controller controller, Method method, List<Interceptor> interceptors) {
        this.controller = controller;
        this.interceptors = interceptors;
        this.info = new ActionMethodInfo(method);
    }

    /**
     * 执行Controller中的ActionMethod
     *
     * @param controller
     * @param params
     * @return
     */
    @Override
    public Result invoke(Object controller, Object... params) {

        Action action = (Action) params[0];

        ActionInvocation ai = new ActionInvocation(this.controller.getInfo(), action, controller);

        InterceptorInvoker interceptorInvoker = new SimpleInterceptorInvoker(this.interceptors, ai);

        try {
            Result result = interceptorInvoker.invokeBegin();

            if (result != null) {
                log.debug("stop action by golbal interceptor");
                return result;
            }

            AnalyzeResult analyzeResult = action.getAnalyzeResult();

            //判断访问方式
            if(this.info.getHttpMethod() != HttpMethod.NONE){
                if(!HttpMethodUtils.canAccess(analyzeResult.getHttpMethod(),this.info.getHttpMethod())){
                    log.debug("user " + analyzeResult.getHttpMethod() +
                            " request cant not enter " +
                            this.info.getHttpMethod() + " method");
                    return ResultUtils.NOTFOUND_RESULT;
                }
            }

            if (!HttpMethodUtils.canAccess(analyzeResult.getHttpMethod(), this.controller.getInfo().getHttpMethod())) {
                log.debug("user " + analyzeResult.getHttpMethod() +
                        " request cant not enter " +
                        this.info.getHttpMethod() + " method");
                return ResultUtils.NOTFOUND_RESULT;
            }

            result = (Result) info.getMethod().invoke(controller, params);

            ai.setResult(result);

            interceptorInvoker.invokeEnd();//执行所有全局Interceptor的end

            return result;

        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public ActionMethodInfo getInfo() {
        return info;
    }

    public Controller getController() {
        return controller;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }
}
