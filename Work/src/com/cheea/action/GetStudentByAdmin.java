package com.cheea.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.cheea.entity.Student;
import com.cheea.entity.User;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.StudentService;
import com.cheea.service.UserService;

public class GetStudentByAdmin implements CommonAction {

	@Override
	public Object doService(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException,
			RutimeException, DataBaseException {
		StudentService service=(StudentService) AutoObjectFactory.getInstance("StudentServiceImpl");
		List<Student> list=service.getAll();
		JSONArray array=new JSONArray(list);
		try {
			PrintWriter out=response.getWriter();
			out.print(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
