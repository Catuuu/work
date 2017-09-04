package com.cheea.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cheea.dao.UserDao;
import com.cheea.entity.User;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.UserService;
import com.cheea.service.impl.UserServiceImpl;
import com.cheea.util.Debug;
/**
 * 登陆action String[] 代表数组 String 代表普通请求转发
 * @author yintao
 *
 */
public class LoginAction implements CommonAction{

	public String[] doService(HttpServletRequest request,
			HttpServletResponse response) throws ServiceException, RutimeException, DataBaseException {
		
		UserService ser=(UserService) AutoObjectFactory.getInstance("UserServiceImpl");
		String name=request.getParameter("name");
		String password=request.getParameter("pwd");
		User flag=ser.Login(name,password);
		String[] a={"失败"};
	    if(flag!=null&&flag.getState()>1){
	    	HttpSession session=request.getSession();
	    	User user=new User();
	    	user.setName(name);
	    	user.setPassword(password);
	    	user.setState(flag.getState());
			session.setAttribute("user",user);//将user放入session
	    	String[] b={"admin"};
	    	a=b;
	    }else if(flag!=null&&flag.getState()==1){
	    	HttpSession session=request.getSession();
	    	User user=new User();
	    	user.setName(name);
	    	user.setPassword(password);
	    	user.setState(1);
			session.setAttribute("user",user);//将user放入session
	    	String[] b={"user"};
	    	a=b;
	    }
		return a;
	}

}
