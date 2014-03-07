package org.jleaf.web.annotation;

/**
 * 生存力度
 *
 * @author leaf
 * @date 2014-1-24 上午1:04:46
 */
public enum Clear {

    NONE,//默认的等级,会被@ClearInterceptor清除掉

    NOTCALEAR;//不会被@ClearInterceptor掉
}
