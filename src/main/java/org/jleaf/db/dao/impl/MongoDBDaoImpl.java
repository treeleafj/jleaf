package org.jleaf.db.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.jleaf.db.dao.BaseDao;
import org.jleaf.db.utils.MongoDBUtils;
import org.jleaf.format.query.Condition;
import org.jleaf.format.query.Operator;
import org.jleaf.format.query.QueryObject;
import org.jleaf.utils.FastBeanUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * mongoDB实现的DAO
 *
 * @author leaf
 * @date 2014-2-9 下午5:25:48
 */
public class MongoDBDaoImpl implements BaseDao {

    Transform transform = new MongoDBTransform();

    @SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(MongoDBDaoImpl.class);

    @SuppressWarnings("unchecked")
	public <T> T get(Class<T> classz, Serializable id) {
        DBObject dbObj = MongoDBUtils.get(id, classz.getSimpleName());
        if (dbObj != null) {
            try {
                T obj = classz.newInstance();
                FastBeanUtils.fastPopulate(obj, dbObj.toMap());
                return obj;
            } catch (Exception e) {
                throw new Error(e);
            }
        }
        return null;
    }

    /**
     * 解析查询参数
     *
     * @param qo
     * @return
     */
    public DBObject analyzeQueryObject(QueryObject qo) {
        qo.init();
        DBObject params = new BasicDBObject();
        for (Condition c : qo.getConditions()) {
            Operator operator = c.getExpression();
            if (Operator.EQUAL.equals(operator)) {
                params.put(c.getKey(), c.getValue());
            } else if (Operator.LIKE.equals(operator)) {
                if (c != null && c.getValue() instanceof String) {
                    String v = c.getValue().toString();
                    v = v.replaceAll(QueryObject.VAGUE, ".*");
                    Pattern pattern = Pattern.compile(v, Pattern.CASE_INSENSITIVE);
                    params.put(c.getKey(), pattern);
                }
            } else {
                params.put(c.getKey(), new BasicDBObject(transform.transform(operator).toString(), c.getValue()));
            }
        }
        return params;
    }

    @SuppressWarnings("unchecked")
	public List<?> find(Class<?> classz, QueryObject qo) {
        List<Object> list = new ArrayList<Object>();
        DBObject params = analyzeQueryObject(qo);
        DBObject order = null;
        if (qo.getOrderBy() != null) {
            order = new BasicDBObject(qo.getOrderBy(), qo.getOrder() > 0 ? -1 : 1);
        }
        DBCursor cursor = null;
        try{
	        cursor = MongoDBUtils.find(params, qo.getStart(), qo.getLimit(), order, classz.getSimpleName());
	        while (cursor.hasNext()) {
	            DBObject dbObj = cursor.next();
	            try {
	                Object obj = classz.newInstance();
	                FastBeanUtils.fastPopulate(obj, dbObj.toMap());
	                list.add(obj);
	            } catch (Exception e) {
	                throw new Error(e);
	            }
	        }
        }finally{
        	if(cursor != null){
        		cursor.close();
        	}
        }
        return list;
    }

    public Long findCount(Class<?> classz, QueryObject qo) {
        return null;
    }

    @SuppressWarnings("rawtypes")
	public void update(Serializable id, Object obj) {
        Object entity = get(obj.getClass(), id);
        if (entity != null) {
            try {
            	Map map = PropertyUtils.describe(obj);
            	map.remove("class");
                DBObject dbObj = new BasicDBObject(map);
                DBObject query = new BasicDBObject();
                query.put("id", id);
                MongoDBUtils.update(query, dbObj, obj.getClass().getSimpleName());
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    public void remove(Class<?> classz, Serializable id) {
        DBObject queryObj = new BasicDBObject();
        queryObj.put("id", id);
        MongoDBUtils.remove(queryObj, classz.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public void save(Object obj) {
        try {
        	PropertyUtils.setSimpleProperty(obj, "id", new ObjectId().toString());
            Map<String, Object> map = PropertyUtils.describe(obj);
            map.remove("class");
            DBObject dbObj = new BasicDBObject(map);
            MongoDBUtils.insert(dbObj, obj.getClass().getSimpleName());
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
