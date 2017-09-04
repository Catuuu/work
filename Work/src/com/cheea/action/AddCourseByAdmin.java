package com.cheea.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheea.entity.Course;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.CourseService;
import com.cheea.service.TeacherService;

public class AddCourseByAdmin implements CommonAction {

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
		CourseService ser=(CourseService) AutoObjectFactory.getInstance("CourseServiceImpl");
		//int cid=Integer.parseInt(request.getParameter("cid"));
		int cid=ser.Max()+1;
		int time=Integer.parseInt(request.getParameter("time"));		
		try {
		   Course course=Course.newInstance();
           course.setCid(cid);
           course.setName(name);
           course.setSid(sid);
           course.setTime(time);
		   ser.addCourse(course);
		} catch (Exception e) {
			out.print("fail");
		}
		out.print("ok");
		return null;
	}

}
