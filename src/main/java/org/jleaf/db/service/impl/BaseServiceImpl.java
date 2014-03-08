package org.jleaf.db.service.impl;

import java.util.List;

import org.jleaf.db.dao.BaseDao;
import org.jleaf.db.dao.DBConfig;
import org.jleaf.db.service.BaseService;
import org.jleaf.format.query.QueryObject;
import org.jleaf.utils.ClassUtils;

/**
 * 基本增删改查实现
 *
 * @param <Entity>
 * @author leaf
 * @date 2014-1-31 下午7:22:43
 */
@SuppressWarnings("unchecked")
public class BaseServiceImpl<Entity> implements BaseService<Entity> {

    protected Class<Entity> entityClass;

    protected BaseDao dao;

    @SuppressWarnings("rawtypes")
    public BaseServiceImpl() {
        Class[] genericss = ClassUtils.getGenerics(this.getClass());
        this.entityClass = genericss[0];
        
        this.dao = DBConfig.getBaseDao();
    }

    public List<Entity> list(QueryObject qo) {
        List<Entity> list = (List<Entity>) dao.find(entityClass, qo);
        return list;
    }

    public void save(Entity obj) {
        dao.save(obj);
    }

    public Entity get(Long id) {
        return dao.get(entityClass, id);
    }

    public void update(Long id, Entity obj) {
        dao.update(id, obj);
    }

    public void remove(Long id) {
        dao.remove(entityClass, id);
    }

}
