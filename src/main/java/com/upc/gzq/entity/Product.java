package com.upc.gzq.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Product {
	private String pid;
	private String pname;
	private Integer pstock;
	private Double pprice;
	private Integer ptype;
	private Integer pstatus;
	private String ppicture = "img/basicprofile.jpg";
	private Timestamp updateTime;
}
