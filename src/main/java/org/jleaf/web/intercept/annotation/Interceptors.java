package org.jleaf.web.intercept.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jleaf.web.intercept.Interceptor;

@Target({ TYPE, METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Interceptors {
	Class<? extends Interceptor>[] value() default {};
}
