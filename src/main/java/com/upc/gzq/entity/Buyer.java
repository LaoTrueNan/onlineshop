package com.upc.gzq.entity;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class Buyer {
	private String bid;
	private String bname;
	private String bpassword;
	private Timestamp updateTime;
}
