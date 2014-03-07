package org.jleaf.web.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jleaf.web.intercept.Interceptor;

@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClearInterceptor {

    /**
     * 要清除的Interceptor,如果没有将清除全部
     *
     * @return
     */
    Class<? extends Interceptor>[] value() default {};

}
