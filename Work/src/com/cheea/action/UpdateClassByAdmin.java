package com.cheea.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheea.entity.Class;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.ClassService;
import com.cheea.service.TeacherService;

public class UpdateClassByAdmin implements CommonAction {

	@Override
	public Object doService(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException,
			RutimeException, DataBaseException {
		PrintWriter out=null;
		try {
			out=response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String name=request.getParameter("name");
		int number=Integer.parseInt(request.getParameter("number"));
		int state=Integer.parseInt(request.getParameter("state"));
		int id=Integer.parseInt(request.getParameter("id"));
		ClassService ser=(ClassService) AutoObjectFactory.getInstance("ClassServiceImpl");
		try {
			Class c=Class.newInstance();
			c.setClassName(name);
			c.setClassNumber(number);
			c.setState(state);
			c.setId(id);
			ser.updateClass(c);
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		out.print("ok");
		return null;
	}

}
