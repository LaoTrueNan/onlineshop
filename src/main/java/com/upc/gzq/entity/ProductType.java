package com.upc.gzq.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ProductType {
	
	private String tid;
	private String tname;
	private Integer tcode;
	private Timestamp updateTime;
//	public ProductType(String tid, String tname, Integer tcode, Timestamp updateTime) {
//		super();
//		this.tid = tid;
//		this.tname = tname;
//		this.tcode = tcode;
//		this.updateTime = updateTime;
//	}
	
}

