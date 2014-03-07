package org.jleaf.web.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设定Controller的地址
 *
 * @author leaf
 */
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Control {

    /**
     * uri地址
     */
    String value() default "";

    /**
     * 访问方式
     */
    HttpMethod method() default HttpMethod.NONE;

    /**
     * 是否单例,默认true
     *
     * @return
     */
    boolean isSingleton() default true;

}
