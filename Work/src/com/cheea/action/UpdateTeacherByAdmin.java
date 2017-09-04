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

public class UpdateTeacherByAdmin implements CommonAction {

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
		String phone=request.getParameter("phone");
		String age=request.getParameter("age");
		int courseId=Integer.parseInt(request.getParameter("courseId"));
		int id=Integer.parseInt(request.getParameter("id"));
		TeacherService ser=(TeacherService) AutoObjectFactory.getInstance("TeacherServiceImpl");
		try {
			Teacher teacher=Teacher.newInstance();
            teacher.setId(id);
            teacher.setAge(age);
            teacher.setName(name);
            teacher.setPhone(phone);
            teacher.setCourseId(courseId);
			ser.updateTeacher(teacher);
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		out.print("ok");
		return null;
	}

}
