package com.cheea.entity;

/**
 * �������ڵ�ʵ��
 * 
 * @author yintao
 * 
 */
public class Classes {
	private int id;
	private int classNumber;// �������ɵİ༶����
	private int allNumber;// ��ͬ�����İ༶��
	private Classes leftClasses;// ������
	private Classes rightClasses;// ������
    
	public static Classes newInstance(){//��̬����
		return new Classes();
	}
	
	public int getAllNumber() {
		return allNumber;
	}

	public void setAllNumber(int allNumber) {
		this.allNumber = allNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}

	public Classes getLeftClasses() {
		return leftClasses;
	}

	public void setLeftClasses(Classes leftClasses) {
		this.leftClasses = leftClasses;
	}

	public Classes getRightClasses() {
		return rightClasses;
	}

	public void setRightClasses(Classes rightClasses) {
		this.rightClasses = rightClasses;
	}

}
