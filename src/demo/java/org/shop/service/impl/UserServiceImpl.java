package org.shop.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jleaf.db.service.impl.BaseServiceImpl;
import org.shop.domain.User;
import org.shop.query.UserQuery;
import org.shop.service.UserService;

public class UserServiceImpl extends BaseServiceImpl<User> implements
		UserService {

	@Override
	public User get(String username, String password) {
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			return null;
		}
		UserQuery userQuery = new UserQuery();
		userQuery.setUsername(username);
		userQuery.setPassword(password);
		List<User> users = list(userQuery);
		if(users.size() == 1){
			return users.get(0);
		}
		return null;
	}

}
