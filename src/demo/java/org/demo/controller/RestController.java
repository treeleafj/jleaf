package org.demo.controller;

import org.jleaf.web.action.HttpAction;
import org.jleaf.web.annotation.Control;
import org.jleaf.web.annotation.HttpMethod;
import org.jleaf.web.annotation.Method;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.controller.result.StringResult;

/**
 * leaf
 * 14-3-7 上午1:16.
 */
@Control("rest")
public class RestController {

    @Method(HttpMethod.GET)
    public Result get(HttpAction action){
        return new StringResult("=>get:" + action.getParam("id"));
    }

    @Method(HttpMethod.PUT)
    public Result save(HttpAction action){
        return new StringResult("=>save");
    }

    @Method(HttpMethod.POST)
    public Result update(HttpAction action){
        return new StringResult("=>update");
    }

    @Method(HttpMethod.DELETE)
    public Result delete(HttpAction action){
        return new StringResult("=>delete");
    }
}
