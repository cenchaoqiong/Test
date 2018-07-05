package com.XmlTest.bean;

import java.io.Serializable;
//封装公共的page_kehu对象，并让kehu对象继承page_kehu对象
public class Page_kehu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 //每页显示数量  
	private int limit;
	 //页码 
	private int page;
	//sql语句起始索引 
	private int offset;
	
	private String Name;
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	

}
