package com.cheea.entity;

/**
 * ѧ���༶ʵ��
 * 
 * @author yintao
 * 
 */
public class Student implements Comparable<Student>{
	private int id;
	private int sid;//�༶id
	private String className;// �༶����
	private int number;// �༶����
	private int timeId;// ʱ��Ƭid
	private int courseId;// �γ�id

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getTimeId() {
		return timeId;
	}

	public void setTimeId(int timeId) {
		this.timeId = timeId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public static Student newInstance() {// ��̬����
		return new Student();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public int compareTo(Student o) {
		if (null == o) return 1;   
        else {   
        	if(this.getNumber()>=o.getNumber()){
        		return -1;//��������
        	}else{
        		return 1;
        	}    
        }   
	}
	
}
