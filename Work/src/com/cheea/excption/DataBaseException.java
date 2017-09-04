package com.cheea.excption;
/**
 * 数据库异常
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
		System.out.println("数据库异常！");
	}
}
