package com.cheea.entity;
/**
 * �γ�ʵ��
 * @author yintao
 *
 */
public class Course {
	private int id;
	private int cid;//�γ̴���
	private String name;
	private int time;// ѧʱ
	private int sid;// ���ڰ༶
    
	public static Course newInstance() {// ��̬����
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
