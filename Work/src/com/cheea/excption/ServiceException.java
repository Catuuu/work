package com.cheea.excption;
/**
 * �����쳣(�����û�������)
 * @author yintao
 *
 */
public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
		System.out.println("�����쳣��");
	}

}
