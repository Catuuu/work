package com.cheea.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.UserService;

public class DeleteUserByAdmin implements CommonAction {

	@Override
	public Object doService(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException,
			RutimeException, DataBaseException {
		int id=Integer.parseInt(request.getParameter("id"));
		UserService ser=(UserService) AutoObjectFactory.getInstance("UserServiceImpl");
		String[] a={"ok"};
		try {
			ser.deleteUserByAdmin(id);
		} catch (Exception e) {
			e.printStackTrace();
			String[] b={"wrong"};
			a=b;
		}
		return a;
	}

}
