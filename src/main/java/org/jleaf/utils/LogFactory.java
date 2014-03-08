package org.jleaf.utils;

import org.apache.log4j.Logger;

/**
 * 日志工厂
 */
public abstract class LogFactory {

    public static Logger getLogger(Class<?> classz) {
        return Logger.getLogger(classz);
    }


    public static Logger getLogger(String name) {
        return Logger.getLogger(name);
    }

}
