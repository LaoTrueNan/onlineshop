package com.upc.gzq.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Admin {
	private String aid;
	private String username;
	private String password;
	private Timestamp updateTime;
	public Admin(String aid, String username, String password, Timestamp updateTime) {
		super();
		this.aid = aid;
		this.username = username;
		this.password = password;
		this.updateTime = updateTime;
	}
	
	
}
