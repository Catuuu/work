package com.cheea.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.cheea.entity.Student;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.StudentService;
import com.cheea.service.UserService;

public class AddStudentByAdmin implements CommonAction {

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
		StudentService ser=(StudentService) AutoObjectFactory.getInstance("StudentServiceImpl");
		//int sid=Integer.parseInt(request.getParameter("sid"));
		int sid=ser.Max()+1;
		int number=Integer.parseInt(request.getParameter("number"));
		int[] a={11,12,13,14,21,22,23,24,31,32,33,34,41,42,43,44,51,52,53,54};
		try {
			for(int i=0;i<a.length;i++){
				Student stu=Student.newInstance();
				stu.setClassName(name);
				stu.setSid(sid);
				stu.setNumber(number);
				stu.setCourseId(0);
				stu.setTimeId(a[i]);
				ser.addStudent(stu);
			}
		} catch (Exception e) {
			out.print("fail");
		}
		out.print("ok");
		return null;
	}

}
