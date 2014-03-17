package org.jleaf.db.controller;

import org.jleaf.db.service.BaseService;
import org.jleaf.format.json.JsonData;
import org.jleaf.format.query.QueryObject;
import org.jleaf.web.action.HttpAction;
import org.jleaf.web.controller.result.Result;

@SuppressWarnings("serial")
public abstract class SenchaRestfulCrudController<Entity, Service extends BaseService<Entity>, QueryObj extends QueryObject> extends RestfulCrudController<Entity, BaseService<Entity>, QueryObject>{

	@Override
	public Result index(HttpAction action) {
		QueryObject qo = action.toObj(getQueryObjClass());
		JsonData jsonData = new JsonData(true, null, getService().list(qo));
		return json(jsonData);
	}
	
}
