package com.cheea.entity;

/**
 * 二叉树节点实体
 * 
 * @author yintao
 * 
 */
public class Classes {
	private int id;
	private int classNumber;// 可以容纳的班级人数
	private int allNumber;// 相同人数的班级数
	private Classes leftClasses;// 左子树
	private Classes rightClasses;// 右子树
    
	public static Classes newInstance(){//静态方法
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
