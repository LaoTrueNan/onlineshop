package com.upc.gzq.VO;

import java.sql.Timestamp;
import java.util.List;

import com.upc.gzq.entity.Product;

import lombok.Data;

public class OrderVO {
	private String oid;
	private String status;
	private Timestamp updateTime;
	private List<Product> productList;
	private Double amount;
	private Integer pnum;
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getPnum() {
		return getProductList().size();
	}
}
