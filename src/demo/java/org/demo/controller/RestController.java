package org.demo.controller;

import org.jleaf.web.action.HttpAction;
import org.jleaf.web.annotation.Control;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.controller.result.StringResult;

/**
 * leaf
 * 14-3-7 上午1:16.
 */
@Control("rest")
public class RestController {
	
	public Result index(HttpAction action){
		return new StringResult("=>index");
	}
	
	public Result edit(HttpAction action){
		return new StringResult("=>edit:" + action.getParam("id"));
	}
	
	public Result create(HttpAction action){
		return new StringResult("=>create");
	}

    public Result get(HttpAction action){
        return new StringResult("=>get:" + action.getParam("id"));
    }

    public Result save(HttpAction action){
        return new StringResult("=>save");
    }

    public Result update(HttpAction action){
        return new StringResult("=>update");
    }

    public Result delete(HttpAction action){
        return new StringResult("=>delete:" + action.getParam("id"));
    }
}
