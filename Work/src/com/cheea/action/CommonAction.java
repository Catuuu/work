package com.cheea.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
/**
 * ͨ��action�ӿ�
 * @author yintao
 *
 */
public interface CommonAction {
   public Object doService(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException, RutimeException, DataBaseException;
}
