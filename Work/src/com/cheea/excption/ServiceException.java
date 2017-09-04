package com.cheea.excption;
/**
 * 服务异常(例如用户不存在)
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
		System.out.println("服务异常！");
	}

}
