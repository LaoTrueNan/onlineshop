package com.upc.gzq.converter;

import java.util.List;

public class Page<T> {
	/**
	 * 总共有多少页
	 */
	private int totalPages;
	/**
	 * 当前页码
	 */
	private int currentNo;
	/**
	 * 总共有多少条记录
	 */
	private int totalItems;
	/**
	 * 是否有上一页
	 */
	private boolean hasPrev;
	/**
	 * 是否有下一页
	 */
	private boolean hasNext;
	/**
	 * 查出的条目
	 */
	private List<T> dataList;
	/**
	 * 每页有多少条记录
	 */
	private int pageSize = 4;//默认每页显示4条记录
	/**
	 *  数据库检索开始的索引
	 */
	private int pageIndex;
	
	public int getTotalPages() {
		int t = getTotalItems()/getPageSize();
		if(getTotalItems()%getPageSize()!=0) {
			t+=1;
		}
		return t;
	}
	public int getCurrentNo() {
		return currentNo;
	}
	public void setCurrentNo(int currentNo) {
		this.currentNo = currentNo;
	}
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	public boolean isHasPrev() {
		return getCurrentNo()>1;
	}
	public boolean isHasNext() {
		return getCurrentNo()<getTotalPages();
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return (getCurrentNo()-1)*getPageSize();
	}
	
}
