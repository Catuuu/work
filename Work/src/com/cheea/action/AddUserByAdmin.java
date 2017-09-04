package com.cheea.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheea.entity.User;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.UserService;

public class AddUserByAdmin implements CommonAction {

	@Override
	public Object doService(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException,
			RutimeException, DataBaseException {
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		String gender=request.getParameter("gender");
		String age=request.getParameter("age");
		String phone=request.getParameter("phone");
		User user=User.newInstance();
		user.setName(name);
		user.setPassword(password);
		user.setGender(gender);
		user.setAge(age);
		user.setPhone(phone);
		user.setState(1);
		UserService ser=(UserService) AutoObjectFactory.getInstance("UserServiceImpl");
		String a="success";
		try {
			ser.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			a="fail";
		}
		return a;
	}

}
