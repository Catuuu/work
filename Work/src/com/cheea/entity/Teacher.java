package com.cheea.entity;
/**
 * 教师实体
 * @author yintao
 *
 */
public class Teacher {
	private int id;
	private String name;
	private String age;
	private String phone;
	private int courseId;
    
	public static Teacher newInstance() {// 静态方法
		return new Teacher();
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

}
