package com.cheea.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheea.entity.User;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.UserService;
import com.cheea.util.Debug;
/**
 * 获取所有用户信息
 * @author yintao
 *
 */
public class GetUserByAdmin implements CommonAction {

	@Override
	public Object doService(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException,
			RutimeException, DataBaseException {
		UserService service=(UserService) AutoObjectFactory.getInstance("UserServiceImpl");
		List<User> list=service.getAll();
		request.setAttribute("List",list);
		return "success";
	}

}
