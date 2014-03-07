package org.jleaf.web.annotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Method {

    /**
     * 访问方式
     */
    HttpMethod value() default HttpMethod.NONE;

}
