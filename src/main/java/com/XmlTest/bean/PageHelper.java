package com.XmlTest.bean;

import java.util.ArrayList;
import java.util.List;
//封装返回数据实体类
public class PageHelper<T> {
	//实体类集合  
	private List<T> rows =new ArrayList<T>();
	//数据总条数 
	private int total;
	
//	public PageHelper(){
//		super();
//	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "PageHelper [rows=" + rows + ", total=" + total + "]";
	}
	
	

}
