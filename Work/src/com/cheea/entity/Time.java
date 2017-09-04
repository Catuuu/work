package com.cheea.entity;

/**
 * 时间片实体
 * 
 * @author yintao
 * 
 */
public class Time {
	private int id;
	private String name;
	private int sid;
    
	public static Time newInstance(){
		return new Time();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

}
