package org.jleaf.web.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jleaf.search.ClassData;
import org.jleaf.search.ClassFilter;
import org.jleaf.search.ClassSearcher;
import org.jleaf.search.StringMatchUtils;
import org.jleaf.web.annotation.Clear;
import org.jleaf.web.annotation.ClearInterceptor;
import org.jleaf.web.annotation.Control;
import org.jleaf.web.annotation.GlobalInterceptor;
import org.jleaf.web.annotation.Interceptors;
import org.jleaf.web.controller.ActionMethod;
import org.jleaf.web.controller.Controller;
import org.jleaf.web.intercept.Interceptor;

/**
 * leaf
 * 14-3-4 下午11:50.
 */
public class AnnoUtils {

    /**
     * 扫描指定路径下的类
     *
     * @param basePath
     * @param packages
     * @return 返回标有@Control,或者继承了Interceptor且标有@GlobalInterceptor的类信息
     */
    public static List<ClassData> searchClassDatasByPackage(String basePath, final String[] packages) {

        ClassSearcher classSearcher = new ClassSearcher(basePath);
        try {
            List<ClassData> result = classSearcher.search(new ClassFilter() {

                public boolean filter(ClassData classData) {
                    try {
                        if (StringMatchUtils.wildcardMatch(packages, classData.getClassName())) {

                            Class<?> classz = Class.forName(classData.getClassName());

                            if (classz.getAnnotation(Control.class) != null) {//如果标有Controller注解
                                return true;
                            } else if (classz.getAnnotation(GlobalInterceptor.class) != null && Interceptor.class.isAssignableFrom(classz)) {//或者继承了Interceptor接口
                                return true;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return false;
                }
            });
            return result;
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /**
     * 通过@Interceptors注解,获得上面的Interceptor实例
     *
     * @param interceptorsAnno
     */
    private static List<Interceptor> getInterceptorsByInterceptorsAnno(Interceptors interceptorsAnno) {
        if (interceptorsAnno != null) {
            Class<? extends Interceptor>[] interceptorClasses = interceptorsAnno.value();
            List<Interceptor> list = new ArrayList<Interceptor>();
            for (Class<? extends Interceptor> classz : interceptorClasses) {
                try {
                    list.add(classz.newInstance());
                } catch (Exception e) {
                    throw new Error(e);
                }
            }
            return list;
        } else {
            return new ArrayList<Interceptor>();
        }
    }

    /**
     * 得到指定对象上的Interceptor
     *
     * @param controller Controller对象
     * @return
     */
    public static List<Interceptor> getInterceptorsByController(Controller controller) {
        Interceptors interceptorsAnno = controller.getInfo().getControllerClass().getAnnotation(Interceptors.class);
        return getInterceptorsByInterceptorsAnno(interceptorsAnno);
    }

    /**
     * 得到指定方法上的Interceptor
     *
     * @param actionMethod ActionMethod对象
     * @return
     */
    public static List<Interceptor> getInterceptorsByActionMethod(ActionMethod actionMethod) {
        Interceptors interceptorsAnno = actionMethod.getInfo().getMethod().getAnnotation(Interceptors.class);
        return getInterceptorsByInterceptorsAnno(interceptorsAnno);
    }

    /**
     * 得到指定方法上的Interceptor
     *
     * @param method Method对象
     * @return
     */
    public static List<Interceptor> getInterceptorsByMethod(Method method) {
        Interceptors interceptorsAnno = method.getAnnotation(Interceptors.class);
        return getInterceptorsByInterceptorsAnno(interceptorsAnno);
    }

    /**
     * 从ClassData集合中筛选出注解为Controller的类的类型
     *
     * @param result ClassData集合
     */
    public static List<Class<?>> getControllerClassesByClassDatas(List<ClassData> result) {
        List<Class<?>> list = new ArrayList<Class<?>>();
        for (ClassData data : result) {
            try {
                Class<?> classz = (Class<?>) Class.forName(data.getClassName());
                list.add(classz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 从ClassData集合中筛选出注解为Interceptor且继承了Interceptor接口的类,并实例化
     *
     * @param result ClassData集合
     */
    @SuppressWarnings("unchecked")
	public static List<Interceptor> getInterceptorsByClassDatas(List<ClassData> result) {
        List<Class<? extends Interceptor>> interceptorClasses = new ArrayList<Class<? extends Interceptor>>();
        for (ClassData data : result) {
            try {
                Class<? extends Interceptor> classz = (Class<? extends Interceptor>) Class.forName(data.getClassName());
                if (classz.getAnnotation(GlobalInterceptor.class) != null && Interceptor.class.isAssignableFrom(classz)) {
                    interceptorClasses.add(classz);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //全局Interceptor排序
        Collections.sort(interceptorClasses, new Comparator<Class<? extends Interceptor>>() {

            public int compare(Class<? extends Interceptor> o1, Class<? extends Interceptor> o2) {
                try {
                    GlobalInterceptor anno1 = (GlobalInterceptor) o1
                            .getAnnotation(GlobalInterceptor.class);
                    GlobalInterceptor anno2 = (GlobalInterceptor) o2
                            .getAnnotation(GlobalInterceptor.class);
                    return anno1.value() - anno2.value();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        List<Interceptor> interceptors = new ArrayList<Interceptor>();
        for (Class<? extends Interceptor> classz : interceptorClasses) {
            try {
                interceptors.add(classz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return interceptors;
    }

    /**
     * 通过@ClearInterceptor过滤Interceptor
     *
     * @return 返回过滤后的List, 如果没有, List将长度为0
     */
    public static List<Interceptor> getInterceptorsByClearInterceptor(ClearInterceptor clearInterceptorAnno, List<Interceptor> interceptors) {
        if (clearInterceptorAnno != null) {
            List<Interceptor> list = new ArrayList<Interceptor>();
            for (Interceptor interceptor : interceptors) {
                GlobalInterceptor gi = interceptor.getClass().getAnnotation(GlobalInterceptor.class);

                if (clearInterceptorAnno.value().length == 0) {//如果没有指定要clear掉哪些Interceptor,默认认为是要清除全部
                    if (gi == null || gi.clear() == Clear.NOTCALEAR) {//如果没有标有@GlobalInterceptor或者级别是NotClear
                        list.add(interceptor);
                    }
                } else if (!arraysContains(clearInterceptorAnno.value(), interceptor.getClass())) {
                    list.add(interceptor);
                }

            }
            return list;
        }
        return new ArrayList<Interceptor>(interceptors);
    }

    /**
     * 判断指定的对象是否存在于数组之中
     */
    public static boolean arraysContains(Object[] arrays, Object obj) {
        if (arrays == null) {
            return false;
        }
        for (Object item : arrays) {
            if (item.equals(obj)) {
                return true;
            }
        }
        return false;
    }
}
