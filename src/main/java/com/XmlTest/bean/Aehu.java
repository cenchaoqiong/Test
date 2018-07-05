package com.XmlTest.bean;

import java.io.Serializable;

public class Aehu extends Page_kehu implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
    private Integer pname;
    private String dno;
    private String sex;
    private Integer age;
    private Integer phone;
	public Integer getPname() {
		return pname;
	}
	public void setPname(Integer pname) {
		this.pname = pname;
	}
	public String getDno() {
		return dno;
	}
	public void setDno(String dno) {
		this.dno = dno;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "kehu [pname=" + pname + ", dno=" + dno + ", sex=" + sex + ", age=" + age + ", phone=" + phone + "]";
	}
	   
}
