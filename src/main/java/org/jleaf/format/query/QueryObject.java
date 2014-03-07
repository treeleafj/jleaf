package org.jleaf.format.query;

import java.util.ArrayList;
import java.util.List;

/**
 * 对查询进行封装
 *
 * @author leaf
 * @date 2014-2-1 下午2:57:19
 */
public class QueryObject extends PageObject {

    /**
     * 模糊匹配符号
     */
    public final static String VAGUE = "#vmstr#";

    /**
     * 排序方式 0升序, 1降序
     */
    protected int order = 0;

    /**
     * 排序字段
     */
    protected String orderBy;

    /**
     * 条件集合
     */
    protected List<Condition> conditions = new ArrayList<Condition>();

    public void init() {

    }

    public QueryObject addCondition(Condition whereCondition) {
        this.conditions.add(whereCondition);
        return this;
    }

    public QueryObject addCondition(String key, Object value, Operator expression) {
        return this.addCondition(new Condition(key, value, expression));
    }

    public QueryObject addCondition(String key, Object value) {
        return this.addCondition(new Condition(key, value, Operator.EQUAL));
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
}
