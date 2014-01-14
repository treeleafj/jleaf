package org.demo.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Msg implements Serializable{

	private Long id;

	private String title;

	private String msg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
