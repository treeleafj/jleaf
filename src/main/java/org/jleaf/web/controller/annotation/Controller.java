package org.jleaf.web.controller.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jleaf.web.controller.HttpMethod;

/**
 * 设定Controller的地址
 * @author leaf
 */
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
	
	/**
	 * uri地址
	 */ 
	String value() default "";

	/**
	 * 访问方式
	 */
	HttpMethod method() default HttpMethod.NONE;
	
	

}
