package com.cheea.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.cheea.entity.ReadyClass;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.ReadyClassService;
import com.cheea.util.Debug;

public class SearchReadyByAdmin implements CommonAction {

	@Override
	public Object doService(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException,
			RutimeException, DataBaseException {
		String name=request.getParameter("name");
		int type=Integer.parseInt(request.getParameter("type"));
		ReadyClassService service=(ReadyClassService) AutoObjectFactory.getInstance("ReadyClassServiceImpl");
		List<ReadyClass> list=null;
		switch (type) {
		case 1:
			list=service.search(name);
			break;
        case 2:
        	list=service.searchWithTeacher(name);
			break;
        case 3:
        	list=service.searchWithClass(name);
			break;	
		default:
			break;
		}
	    Debug.Print(name);
	    Debug.Print(type+"");
	    if(list!=null){
	    	Debug.Print("成功提取出数据！");
	    }
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
