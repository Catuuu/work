package com.cheea.entity;
/**
 * 课程实体
 * @author yintao
 *
 */
public class Course {
	private int id;
	private int cid;//课程代号
	private String name;
	private int time;// 学时
	private int sid;// 属于班级
    
	public static Course newInstance() {// 静态方法
		return new Course();
	}
	
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

}
