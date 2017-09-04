package com.cheea.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheea.entity.Student;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.StudentService;
import com.cheea.service.TeacherService;

public class AddTeacherByAdmin implements CommonAction {

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
		int courseId=Integer.parseInt(request.getParameter("courseId"));
		String age=request.getParameter("age");
		String phone=request.getParameter("phone");		
		TeacherService ser=(TeacherService) AutoObjectFactory.getInstance("TeacherServiceImpl");
		try {
		   Teacher tea=Teacher.newInstance();
		   tea.setName(name);
		   tea.setCourseId(courseId);
		   tea.setPhone(phone);
		   tea.setAge(age);
		   ser.addTeacher(tea);
		} catch (Exception e) {
			out.print("fail");
		}
		out.print("ok");
		return null;
	}

}
