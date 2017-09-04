package com.cheea.excption;
/**
 * 运行时异常
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
		System.out.println("运行时异常！");
	}
	

}
