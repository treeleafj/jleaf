package org.jleaf.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.jleaf.utils.ClassUtils;
import org.jleaf.web.annotation.Control;
import org.jleaf.web.annotation.HttpMethod;

/**
 * leaf
 * 14-3-2 上午3:30.
 */
@SuppressWarnings("serial")
public class ControllerInfo implements Info {

    /**
     * Controller的名字,对应uri
     */
    private String name;

    /**
     * Controller的类型
     */
    private Class<?> controllerClass;

    /**
     * 访问方式
     */
    private HttpMethod httpMethod = HttpMethod.NONE;

    /**
     * 是否单例
     */
    private boolean isSingleton = true;

    public ControllerInfo(Object obj) {

        this.controllerClass = obj.getClass();

        Control controlAno = this.controllerClass.getAnnotation(Control.class);
        if (controlAno != null) {
            if (StringUtils.isNotBlank(controlAno.value())) {
                this.name = controlAno.value();
            } else {
                this.name = getSimpleControllerName(this.controllerClass);
            }

            this.isSingleton = controlAno.isSingleton();
            this.httpMethod = controlAno.method();

        } else {
            this.name = getSimpleControllerName(this.controllerClass);
        }
    }


    private String getSimpleControllerName(Class<?> controller) {
        String name = ClassUtils.getSimpleBeanName(controller);
        if (name.endsWith("Controller")) {
            name = name.substring(0, name.lastIndexOf("Controller"));
        }
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean isSingleton) {
        this.isSingleton = isSingleton;
    }
}
