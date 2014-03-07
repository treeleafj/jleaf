package org.jleaf.db.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.jleaf.db.dao.BaseDao;
import org.jleaf.db.dao.WhereCollection;
import org.jleaf.db.utils.EntityManagerUtils;
import org.jleaf.format.query.Condition;
import org.jleaf.format.query.Operator;
import org.jleaf.format.query.QueryObject;

/**
 * 基于JPA实现的基本Dao
 *
 * @author leaf
 * @date 2014-2-6 下午10:15:54
 */
public class JPADaoImpl implements BaseDao {

    protected Transform operators = new JPATransform();

    /**
     * 解析QueryObject
     *
     * @param qo
     * @return
     */
    public WhereCollection analyzeQueryObject(QueryObject qo) {

        qo.init();

        List<Condition> conditions = qo.getConditions();
        List<Object> params = new ArrayList<Object>();

        StringBuilder sb = new StringBuilder();
        sb.append(" 1=1 ");

        for (Condition c : conditions) {

            Object value = c.getValue();
            //如果是模糊符号,则将QueryObject.VAGUE换成 '%'
            if (Operator.LIKE.equals(c.getExpression())) {
                if (value != null && value instanceof String) {
                    value = value.toString().replaceAll(QueryObject.VAGUE, "%");
                }
            }
            params.add(value);

            sb.append("and ");
            sb.append(c.getKey());
            sb.append(" ");
            sb.append(operators.transform(c.getExpression()));
            sb.append(" ? ");
        }

        if (qo.getOrderBy() != null) {
            String order = qo.getOrder() == 0 ? " asc " : " desc ";
            String orderSql = " order by " + qo.getOrderBy() + order;
            sb.append(orderSql);
        }

        WhereCollection wc = new WhereCollection();
        wc.setParams(params);
        wc.setSql(sb.toString());

        return wc;
    }

    /**
     * 查询
     */
    public List<?> find(Class<?> classz, QueryObject qo) {
        EntityManager entityManager = EntityManagerUtils.getEntityManager();

        WhereCollection wc = analyzeQueryObject(qo);

        String hql = "select obj from " + classz.getSimpleName()
                + " obj where " + wc.getSql();
        Query query = entityManager.createQuery(hql);
        for (int i = 0; i < wc.getParams().size(); i++) {
            query.setParameter(i + 1, wc.getParams().get(i));
        }

        return query.getResultList();
    }

    /**
     * 统计总数
     */
    public Long findCount(Class<?> classz, QueryObject qo) {
        EntityManager entityManager = EntityManagerUtils.getEntityManager();

        WhereCollection wc = analyzeQueryObject(qo);

        String hql = "select count(obj) from " + classz.getSimpleName()
                + " obj where " + wc.getSql();
        Query query = entityManager.createQuery(hql);
        for (int i = 0; i < wc.getParams().size(); i++) {
            query.setParameter(i + 1, wc.getParams().get(i));
        }
        Long count = (Long) query.getResultList().get(0);
        return count;
    }

    /**
     * 更新
     */
    public void update(Serializable id, Object obj) {
        Object entity = get(obj.getClass(), id);
        if (entity != null) {
            try {
                BeanUtils.copyProperties(entity, obj);
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    /**
     * 删除
     */
    public void remove(Class<?> classz, Serializable id) {
        Object entity = get(classz, id);
        if (entity != null) {
            EntityManager entityManager = EntityManagerUtils.getEntityManager();
            entityManager.remove(entity);
        }
    }

    /**
     * 保存
     */
    public void save(Object obj) {
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        entityManager.persist(obj);
    }

    /**
     * 通过ID得到对象
     */
    public <T> T get(Class<T> classz, Serializable id) {
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        return entityManager.find(classz, id);
    }

}
