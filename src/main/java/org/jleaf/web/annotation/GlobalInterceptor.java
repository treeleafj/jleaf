package org.jleaf.web.annotation;

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
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalInterceptor {

    /**
     * Interceptor执行的顺序,数值越小越优先
     */
    public int value();

    /**
     * 生存力度
     */
    Clear clear() default Clear.NONE;
}
