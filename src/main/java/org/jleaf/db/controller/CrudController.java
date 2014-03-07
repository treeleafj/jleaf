package org.jleaf.db.controller;

import org.jleaf.db.service.BaseService;
import org.jleaf.format.json.JsonData;
import org.jleaf.format.query.QueryObject;
import org.jleaf.utils.ClassUtils;
import org.jleaf.web.action.Action;
import org.jleaf.web.action.HttpAction;
import org.jleaf.web.controller.result.JsonResult;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.controller.result.StringResult;

/**
 * 提供通用的增删改查操作
 *
 * @param <Entity>
 * @author leaf
 * @date 2014-1-26 下午10:25:51
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class CrudController<Entity, Service extends BaseService<Entity>, QueryObj extends QueryObject> {

    private Class<Entity> entityClass;

    private Class<Service> serviceClass;

    private Class<QueryObj> queryObjClass;

    private BaseService<Entity> service;

    private QueryObject queryObj;

    public CrudController() {

        Class[] classzs = ClassUtils.getGenerics(this.getClass());

        this.entityClass = classzs[0];
        this.serviceClass = classzs[1];
        this.queryObjClass = classzs[2];

        try {
            this.service = (BaseService<Entity>) this.serviceClass.newInstance();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public Result save(HttpAction ar) {
        Entity obj = ar.toObj(entityClass);
        getService().save(obj);
        return new JsonResult(obj);
    }

    public Result remove(HttpAction ar) {
        Long id = Long.valueOf(ar.getParam("id"));
        getService().remove(id);
        return new JsonResult(new JsonData(true, "removed"));
    }

    public Result update(HttpAction ar) {
        Long id = Long.valueOf(ar.getParam("id"));
        Entity obj = ar.toObj(entityClass);
        getService().update(id, obj);
        return new JsonResult(obj);
    }

    public Result list(HttpAction ar) {
        QueryObject qo = ar.toObj(getQueryObjClass());
        return new JsonResult(getService().list(qo));
    }

    public static Result testStatic(Action action) {

        return new StringResult("123");
    }

    public static Result testAction(Action action) {

        return new StringResult("456");
    }

    public Class<Entity> getEntityClass() {
        return entityClass;
    }

    public Class<Service> getServiceClass() {
        return serviceClass;
    }

    public Class<QueryObj> getQueryObjClass() {
        return queryObjClass;
    }

    public BaseService<Entity> getService() {
        return service;
    }

    public QueryObject getQueryObj() {
        return queryObj;
    }


}
