package org.jleaf.db.service;

import java.io.Serializable;
import java.util.List;

import org.jleaf.format.query.QueryObject;

public interface BaseService<T> {

    /**
     * 通过ID获得某一对象
     *
     * @param id
     * @return
     */
    T get(Serializable id);

    /**
     * 根据条件查询
     *
     * @param qo
     * @return
     */
    List<T> list(QueryObject qo);

    /**
     * 保存
     *
     * @param obj
     */
    void save(T obj);

    /**
     * 更新
     *
     * @param id
     * @param obj
     */
    void update(Serializable id, T obj);

    /**
     * 删除
     *
     * @param id
     */
    void remove(Serializable id);

}
