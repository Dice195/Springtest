package com.my.vo;

import java.io.Serializable;

public class Customer extends Person implements Serializable{
	private String id;
	private String pwd;

	
	public Customer(){
	}
	public Customer(String id, String pwd, String name){
//		super(name);		부모의 생성자 호출
		this.id = id;
		this.pwd = pwd;
		this.name = name;
//		setName(name);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public void printInfo() {
		System.out.println("id:" + id +", pwd:" + pwd + ", name:" + getName());
	}
}
