package org.jleaf.web.utils;

import org.jleaf.web.annotation.HttpMethod;

/**
 * leaf
 */
public abstract class HttpMethodUtils {

    /**
     * 判断用户的访问方式是否能够登录
     *
     * @param userMethod 用户访问方式
     * @param appMethod  应用设置的访问方式
     * @return
     */
    public static boolean canAccess(HttpMethod userMethod, HttpMethod appMethod) {
        return appMethod == HttpMethod.NONE || userMethod == appMethod;
    }

}
