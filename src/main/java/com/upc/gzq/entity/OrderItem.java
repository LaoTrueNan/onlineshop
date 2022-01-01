package com.upc.gzq.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderItem {
	private String iid;
	private String oid;
	private String pid;
	private String pname;
	private Integer pnum;
	private Timestamp updateTime;
}
