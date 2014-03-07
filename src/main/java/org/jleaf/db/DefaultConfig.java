package org.jleaf.db;

import org.jleaf.db.dao.BaseDao;
import org.jleaf.db.dao.impl.MongoDBDaoImpl;

public class DefaultConfig {

    /**
     * 默认的DAO实现类
     */
    private static Class<? extends BaseDao> baseDaoClass = MongoDBDaoImpl.class;

    public static Class<? extends BaseDao> getBaseDaoClass() {
        return baseDaoClass;
    }

    public static void setBaseDaoClass(Class<? extends BaseDao> baseDaoClass) {
        DefaultConfig.baseDaoClass = baseDaoClass;
    }

}
