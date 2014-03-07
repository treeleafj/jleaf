package org.jleaf.format.query;

/**
 * 查询操作符
 *
 * @author leaf
 * @date 2014-2-9 下午5:56:31
 */
public enum Operator {

    /**
     * 等于
     */
    EQUAL,
    /**
     * 大于
     */
    GT,
    /**
     * 大于等于
     */
    GTE,
    /**
     * 小于
     */
    LT,
    /**
     * 小于等于
     */
    LTE,

    /**
     * like模糊
     */
    LIKE,

    NE,
    IN,
    NIN,
    MOD
}
