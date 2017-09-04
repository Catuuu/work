package com.cheea.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheea.entity.Student;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.StudentService;

public class UpdateStudentByAdmin implements CommonAction {

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
		int sid=Integer.parseInt(request.getParameter("sid"));
		int number=Integer.parseInt(request.getParameter("number"));
		StudentService ser=(StudentService) AutoObjectFactory.getInstance("StudentServiceImpl");
		try {
			Student stu=Student.newInstance();
			stu.setClassName(name);
			stu.setSid(sid);
			stu.setNumber(number);
			ser.updateStudent(stu);
		} catch (Exception e) {
			out.print("fail");
		}
		out.print("ok");
		return null;
	}

}
