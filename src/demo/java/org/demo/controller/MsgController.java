package org.demo.controller;

import org.demo.entity.Msg;
import org.demo.interceptor.BaseInitInterceptor;
import org.demo.interceptor.Msg2Interceptor;
import org.demo.interceptor.MsgInterceptor;
import org.demo.query.MsgQuery;
import org.demo.service.impl.MsgServiceImpl;
import org.jleaf.db.controller.CrudController;
import org.jleaf.web.action.Action;
import org.jleaf.web.action.HttpAction;
import org.jleaf.web.annotation.ClearInterceptor;
import org.jleaf.web.annotation.Control;
import org.jleaf.web.annotation.Interceptors;
import org.jleaf.web.controller.result.Result;
import org.jleaf.web.controller.result.StringResult;

@Control
@ClearInterceptor()
public class MsgController extends CrudController<Msg, MsgServiceImpl, MsgQuery> {

    @Interceptors({MsgInterceptor.class, Msg2Interceptor.class})
    public Result test1(Action action) {
        return new StringResult("123");
    }

    @ClearInterceptor(BaseInitInterceptor.class)
    public Result test2(HttpAction action) {
        return new StringResult("456");
    }

    @Interceptors(BaseInitInterceptor.class)
    public Result test3(HttpAction action){
        return new StringResult("789");
    }

}
