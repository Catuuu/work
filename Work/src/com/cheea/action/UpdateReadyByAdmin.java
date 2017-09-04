package com.cheea.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheea.entity.FailClass;
import com.cheea.entity.ReadyClass;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.FailClassService;
import com.cheea.service.ReadyClassService;

public class UpdateReadyByAdmin implements CommonAction {

	@Override
	public Object doService(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException,
			RutimeException, DataBaseException {
		String student=request.getParameter("student");
		int id=Integer.parseInt(request.getParameter("id"));
		String teacher=request.getParameter("teacher");
		String time=request.getParameter("time");
		String course=request.getParameter("course");
		String className=request.getParameter("className");
		PrintWriter out=null;
		try {
			out=response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ReadyClassService ser=(ReadyClassService) AutoObjectFactory.getInstance("ReadyClassServiceImpl");
		try {
			ReadyClass f=ReadyClass.newInstance();
            f.setId(id);
            f.setClassName(className);
            f.setCourseName(course);
            f.setStudentName(student);
            f.setTeacherName(teacher);
            f.setTime(time);
            ser.updateReady(f);
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
		out.print("ok");
		return null;
	}

}
