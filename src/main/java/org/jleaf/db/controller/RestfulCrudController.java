package org.jleaf.db.controller;

import org.jleaf.db.service.BaseService;
import org.jleaf.format.json.JsonData;
import org.jleaf.format.query.QueryObject;
import org.jleaf.utils.ClassUtils;
import org.jleaf.web.action.HttpAction;
import org.jleaf.web.controller.result.Result;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class RestfulCrudController<Entity, Service extends BaseService<Entity>, QueryObj extends QueryObject>
		extends BaseController {

	private Class<Entity> entityClass;

	private Class<Service> serviceClass;

	private Class<QueryObj> queryObjClass;

	private BaseService<Entity> service;

	private QueryObject queryObj;
	
	private String simpleBeanName;

	public RestfulCrudController() {

		Class[] classzs = ClassUtils.getGenerics(this.getClass());

		this.entityClass = classzs[0];
		this.serviceClass = classzs[1];
		this.queryObjClass = classzs[2];
		
		this.simpleBeanName = ClassUtils.getSimpleBeanName(this.entityClass);

		try {
			this.service = (BaseService<Entity>) this.serviceClass
					.newInstance();
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	public Result index(HttpAction action) {
		QueryObject qo = action.toObj(getQueryObjClass());
		return json(getService().list(qo));
	}
	
	public Result edit(HttpAction action){
		String id = action.getParam("id");
		if(id != null){
			Entity entity = getService().get(Long.valueOf(id));
			if(entity != null){
				return jsp(this.simpleBeanName + "/edit.jsp",entity);
			}
		}
		return notFound();
	}
	
	public Result create(HttpAction action){
		return jsp(this.simpleBeanName + "/create.jsp");
	}
	
	public Result get(HttpAction action) {
		String id = action.getParam("id");
		if(id != null){
			return json(getService().get(Long.valueOf(id)));
		}
		return notFound();
	}

	public Result save(HttpAction action) {
		Entity obj = action.toObj(entityClass);
		getService().save(obj);
		return json(obj);
	}

	public Result delete(HttpAction action) {
		String id = action.getParam("id");
		if(id != null){
			getService().remove(Long.valueOf(id));
			return json(new JsonData(true, "removed"));
		}
		return notFound();
	}

	public Result update(HttpAction action) {
		String id = action.getParam("id");
		if(id != null){
			Entity obj = action.toObj(entityClass);
			getService().update(Long.valueOf(id), obj);
			return json(obj);
		}
		return notFound();
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
