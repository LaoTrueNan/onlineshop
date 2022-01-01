package com.upc.gzq.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderMaster {
	private String oid;
	private Double amount;
	private String bid;
	private Integer status=0;
	private Timestamp updateTime;
}
