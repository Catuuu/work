package com.cheea.excption;
/**
 * ����ʱ�쳣
 * @author yintao
 *
 */
public class RutimeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
		System.out.println("����ʱ�쳣��");
	}
	

}
