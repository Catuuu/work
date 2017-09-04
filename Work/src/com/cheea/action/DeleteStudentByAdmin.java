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
import com.cheea.util.Debug;

public class DeleteStudentByAdmin implements CommonAction {

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
		String sid=request.getParameter("id");
		//Debug.Print(sid);
		StudentService ser=(StudentService) AutoObjectFactory.getInstance("StudentServiceImpl");
		try {
             ser.deleteStudent(sid);
		} catch (Exception e) {
			out.print("fail");
		}
		out.print("ok");
		return null;
	}

}
