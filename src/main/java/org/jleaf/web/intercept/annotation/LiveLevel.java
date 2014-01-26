package org.jleaf.web.intercept.annotation;

/**
 * 生存力度
 * @author leaf
 * @date 2014-1-24 上午1:04:46
 */
public enum LiveLevel {
	NONE,//默认被ClearLevel.NONE清除
	HEIGHT,//只会被ClearLevel.CLEARALL清除
	NOTCALEAR;//不会被@ClearInterceptor掉
}
