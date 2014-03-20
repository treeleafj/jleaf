package org.shop.domain;

import java.util.Date;

/**
 * 权限
 * @author leaf
 * @date 2014-3-15 下午3:52:35
 */
public class Auth {

	private String id;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 对方IP
	 */
	private String ip;

	/**
	 * 对应的用户名
	 */
	private String username;

	/**
	 * 到期时间
	 */
	private Date timeLimitDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getTimeLimitDate() {
		return timeLimitDate;
	}

	public void setTimeLimitDate(Date timeLimitDate) {
		this.timeLimitDate = timeLimitDate;
	}

}
