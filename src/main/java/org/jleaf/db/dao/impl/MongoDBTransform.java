package org.jleaf.db.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.jleaf.format.query.Operator;

import com.mongodb.QueryOperators;

public class MongoDBTransform implements Transform {

    private static Map<Operator, String> operators = new HashMap<Operator, String>();

    static {
        operators.put(Operator.GT, QueryOperators.GT);
        operators.put(Operator.GTE, QueryOperators.GTE);
        operators.put(Operator.LT, QueryOperators.LT);
        operators.put(Operator.LTE, QueryOperators.LTE);
//		operators.put(Operator.LIKE, QueryOperators.);
        operators.put(Operator.IN, QueryOperators.IN);
    }

    public Object transform(Operator operator) {
        return operators.get(operator);
    }

}
