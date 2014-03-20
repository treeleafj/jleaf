package org.jleaf.web.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jleaf.utils.BeanInfoUtils;
import org.jleaf.web.action.Action;
import org.jleaf.web.annotation.ClearInterceptor;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.intercept.Interceptor;
import org.jleaf.web.utils.AnnoUtils;

/**
 * ActionMethod的集合 leaf 14-3-2 下午11:01.
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class ActionMethodsImpl implements ActionMethods {

	private List<Interceptor> interceptors;

	private Controller controller;

	private Map<String, ActionMethod> actionMethodMap = new HashMap<String, ActionMethod>();

	/**
	 * @param controller
	 *            Control
	 * @param interceptors
	 *            Controller要执行的所有拦截器
	 */
	public ActionMethodsImpl(Controller controller,
			List<Interceptor> interceptors) {
		this.controller = controller;
		this.interceptors = interceptors;

		Method[] ms = controller.getInfo().getControllerClass().getMethods();
		for (Method m : ms) {
			if (isActionMethod(m)) {
				ClearInterceptor clearInterceptor = m
						.getAnnotation(ClearInterceptor.class);
				// 过滤上一层的Interceptor
				List<Interceptor> list = AnnoUtils
						.getInterceptorsByClearInterceptor(clearInterceptor,
								interceptors);
				// 添加方法上标注的Interceptor
				list.addAll(AnnoUtils.getInterceptorsByMethod(m));

				ActionMethod am = new ActionMethodImpl(controller, m, list);
				this.actionMethodMap.put(am.getInfo().getName(), am);
			}
		}
	}

	/**
	 * 判断一个方法是否可以作为Action方法
	 * 必须入参只有1个且是Action或其子类, 然后必须是public的非static方法
	 * @param m
	 *            要判断的方法
	 * @return 是否可以
	 */
	protected boolean isActionMethod(Method m) {
		return BeanInfoUtils.getParamLength(m) == 1
				&& Action.class.isAssignableFrom(m.getParameterTypes()[0])
				&& Result.class.isAssignableFrom(m.getReturnType())
				&& !BeanInfoUtils.isStatic(m)
				&& BeanInfoUtils.isPublic(m);
	}

	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	@Override
	public ActionMethod getActionMethod(String name) {
		return actionMethodMap.get(name);
	}

	public Controller getController() {
		return controller;
	}

	@Override
	public Map getActionMethods() {
		return actionMethodMap;
	}
}
