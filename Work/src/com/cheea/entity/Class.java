package com.cheea.entity;

/**
 * 教室实体
 * 
 * @author yintao
 * 
 */
public class Class implements Comparable<Class>{
	private int id;
	private String className;// 教室名称
	private int classNumber;// 教室人数
	private int state;// 是否申请出去了 0 已被申请 1未被申请

	public Class() {
	}
    
	public static Class newInstance(){//静态方法
		return new Class();
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

	public int getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public int compareTo(Class o) {
		if (null == o) return 1;   
        else {   
        	if(this.getClassNumber()>=o.getClassNumber()){
        		return -1;//反向排序
        	}else{
        		return 1;
        	}    
        } 
	}

}
