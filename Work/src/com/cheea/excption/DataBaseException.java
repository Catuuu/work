package com.cheea.excption;
/**
 * ���ݿ��쳣
 * @author yintao
 *
 */
public class DataBaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
		System.out.println("���ݿ��쳣��");
	}
}
