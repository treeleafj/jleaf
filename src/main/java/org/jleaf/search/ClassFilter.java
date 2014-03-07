package org.jleaf.search;

/**
 * 类过滤器
 *
 * @author leaf
 * @date 2014-1-19 下午4:58:30
 */
public interface ClassFilter {
    /**
     * 返回false代表过滤掉
     *
     * @param classData
     * @return
     */
    boolean filter(ClassData classData);
}