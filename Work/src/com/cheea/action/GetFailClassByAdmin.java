package com.cheea.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.cheea.entity.Class;
import com.cheea.entity.FailClass;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.ClassService;
import com.cheea.service.FailClassService;

public class GetFailClassByAdmin implements CommonAction {

	@Override
	public Object doService(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException,
			RutimeException, DataBaseException {
		FailClassService service=(FailClassService) AutoObjectFactory.getInstance("FailClassServiceImpl");
		List<FailClass> list=service.getAll();
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
