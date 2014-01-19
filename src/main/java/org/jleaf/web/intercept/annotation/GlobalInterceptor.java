package org.jleaf.web.intercept.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 全局Interceptor标志
 * 
 * @author leaf
 * @date 2014-1-19 下午4:59:42
 */
@Target({ TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalInterceptor {

	/**
	 * 顺序
	 */
	public int value();
}
