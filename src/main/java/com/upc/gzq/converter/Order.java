package com.upc.gzq.converter;

import java.sql.Timestamp;
import java.util.List;

import com.upc.gzq.entity.OrderItem;

import lombok.Data;

@Data
public class Order {
	private String oid;
	private Double amount;
	private String bid;
	private Integer status;
	private Timestamp updateTime;
	private List<OrderItem> orderItems;
}
