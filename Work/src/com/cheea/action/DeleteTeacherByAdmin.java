package com.cheea.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.StudentService;
import com.cheea.service.TeacherService;

public class DeleteTeacherByAdmin implements CommonAction {

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
		String id=request.getParameter("id");
		TeacherService ser=(TeacherService) AutoObjectFactory.getInstance("TeacherServiceImpl");
		try {
             ser.deleteTeacher(id);
		} catch (Exception e) {
			out.print("fail");
		}
		out.print("ok");
		return null;
	}

}
