package org.demo.controller;

import org.demo.entity.Msg;
import org.demo.query.MsgQuery;
import org.demo.service.impl.MsgServiceImpl;
import org.jleaf.db.controller.CrudController;
import org.jleaf.web.annotation.Control;

@Control
public class MsgController extends CrudController<Msg, MsgServiceImpl, MsgQuery> {

}
