package com.cheea.entity;

/**
 * ����ʵ��
 * 
 * @author yintao
 * 
 */
public class Class implements Comparable<Class>{
	private int id;
	private String className;// ��������
	private int classNumber;// ��������
	private int state;// �Ƿ������ȥ�� 0 �ѱ����� 1δ������

	public Class() {
	}
    
	public static Class newInstance(){//��̬����
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
        		return -1;//��������
        	}else{
        		return 1;
        	}    
        } 
	}

}
