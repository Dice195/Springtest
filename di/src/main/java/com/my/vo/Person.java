package com.my.vo;

public class Person {
	
//	private String name; //상속은받되 접근을못함
	protected String name;
	
	public Person() {
		//매개변수없는 생성자 
	}
	public Person(String name) {
		this.name = name;
	}
	public void setName(String name) {	
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	
}

