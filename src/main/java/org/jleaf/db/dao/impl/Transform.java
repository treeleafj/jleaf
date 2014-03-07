package org.jleaf.db.dao.impl;

import org.jleaf.format.query.Operator;

public interface Transform {

    Object transform(Operator operator);

}
