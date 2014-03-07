package org.jleaf.db.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.jleaf.format.query.Operator;

public class JPATransform implements Transform {

    private static Map<Operator, String> operators = new HashMap<Operator, String>();

    static {
        operators.put(Operator.EQUAL, "=");
        operators.put(Operator.GT, ">");
        operators.put(Operator.GTE, ">=");
        operators.put(Operator.LT, "<");
        operators.put(Operator.LTE, "<=");
        operators.put(Operator.LIKE, "like");
        operators.put(Operator.IN, "in");
    }

    public Object transform(Operator operator) {
        return operators.get(operator);
    }

}
