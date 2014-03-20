package org.shop.service;

import org.jleaf.db.service.BaseService;
import org.shop.domain.User;

public interface UserService extends BaseService<User> {
	
	/**
	 * 获取用户
	 * @param username 用户名
	 * @param password 密码
	 * @return 若用户存在,则返回该用户,不然返回null
	 */
	User get(String username,String password);

}
