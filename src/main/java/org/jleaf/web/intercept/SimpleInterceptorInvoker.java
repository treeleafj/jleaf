package org.jleaf.web.intercept;

import java.util.List;

import org.jleaf.web.controller.result.Result;
import org.jleaf.web.controller.result.ResultUtils;

/**
 * leaf
 * 14-3-2 下午6:11.
 */
public class SimpleInterceptorInvoker implements InterceptorInvoker {

    private List<Interceptor> interceptors;

    private ActionInvocation ai;

    public SimpleInterceptorInvoker(List<Interceptor> interceptors, ActionInvocation ai) {
        this.interceptors = interceptors;
        this.ai = ai;
    }

    /**
     * 执行指定的全局Interceptor的begin,如果出现中止,会自动调用end
     *
     * @return 如果返回null说明都执行成功, 如果返回result的子类对象, 说明出现了终止的情况
     */
    @Override
    public Result invokeBegin() throws Exception {
        //执行前置Interceptor
        for (int i = 0; i < interceptors.size(); i++) {
            Interceptor interceptor = interceptors.get(i);
            boolean b = interceptor.begin(ai);
            if (!b) {//如果出现终止,则从当前执行到的Interceptor开始,往后执行end方法
                for (int j = i; j >= 0; j--) {
                    interceptor = interceptors.get(j);
                    interceptor.end(ai);
                }
                return ai.getResult() != null ? ai.getResult() : ResultUtils.NULL_RESULT;
            }
        }
        return null;
    }


    /**
     * 执行指定的全局Interceptor的end
     *
     * @throws Exception
     */
    @Override
    public void invokeEnd() throws Exception {
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            Interceptor interceptor = interceptors.get(i);
            interceptor.end(ai);
        }
    }
}
