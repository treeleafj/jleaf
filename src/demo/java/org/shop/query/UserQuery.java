package org.shop.query;

import org.jleaf.format.query.QueryObject;

public class UserQuery extends QueryObject {

	private String id;

	private String username;

	private String password;

	@Override
	public void init() {
		if (id != null) {
			this.addCondition("id", id);
		}

		if (username != null) {
			this.addCondition("username", username);
		}

		if (password != null) {
			this.addCondition("password", password);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
