package org.jleaf.db.dao;

import java.io.Serializable;
import java.util.List;

import org.jleaf.format.query.QueryObject;

public interface BaseDao {

    /**
     * 通过注解ID获得对象
     *
     * @param classz 类型
     * @param id     主键ID
     * @return 查到的对象
     */
    <T> T get(Class<T> classz, Serializable id);

    /**
     * 查询
     */
    List<?> find(Class<?> classz, QueryObject qo);

    /**
     * 统计总数
     */
    Long findCount(Class<?> classz, QueryObject qo);

    /**
     * 更新对象
     *
     * @param id  主键ID
     * @param obj 要更新的数据
     */
    void update(Serializable id, Object obj);

    /**
     * 删除对象
     *
     * @param classz 类型
     * @param id     主键ID
     */
    void remove(Class<?> classz, Serializable id);

    /**
     * 保存对象
     *
     * @param obj
     */
    void save(Object obj);

}
