package org.jleaf.db.controller;

import org.jleaf.db.service.BaseService;
import org.jleaf.format.json.JsonData;
import org.jleaf.format.query.QueryObject;
import org.jleaf.utils.ClassUtils;
import org.jleaf.web.action.HttpAction;
import org.jleaf.web.controller.result.Result;

/**
 * 提供通用的增删改查操作
 * 
 * @param <Entity>
 * @author leaf
 * @date 2014-1-26 下午10:25:51
 */
@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public abstract class CrudController<Entity, Service extends BaseService<Entity>, QueryObj extends QueryObject>
		extends BaseController {

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
			this.service = (BaseService<Entity>) this.serviceClass
					.newInstance();
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public Result save(HttpAction action) {
		Entity obj = action.toObj(entityClass);
		getService().save(obj);
		return json(obj);
	}

	public Result remove(HttpAction action) {
		Long id = Long.valueOf(action.getParam("id"));
		getService().remove(id);
		return json(new JsonData(true, "removed"));
	}

	public Result update(HttpAction action) {
		Long id = Long.valueOf(action.getParam("id"));
		Entity obj = action.toObj(entityClass);
		getService().update(id, obj);
		return json(obj);
	}

	public Result list(HttpAction action) {
		QueryObject qo = action.toObj(getQueryObjClass());
		return json(getService().list(qo));
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
