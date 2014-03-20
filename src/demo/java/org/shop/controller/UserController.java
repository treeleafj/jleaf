package org.shop.controller;

import org.jleaf.db.controller.RestfulCrudController;
import org.jleaf.format.query.QueryObject;
import org.jleaf.web.annotation.Control;
import org.shop.domain.User;
import org.shop.service.impl.UserServiceImpl;

@Control
@SuppressWarnings("serial")
public class UserController extends RestfulCrudController<User, UserServiceImpl, QueryObject>{
	

}
