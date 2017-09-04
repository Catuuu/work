package com.cheea.service.impl;

import java.util.List;

import com.cheea.dao.UserDao;
import com.cheea.dao.impl.UserDaoImpl;
import com.cheea.entity.User;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.UserService;
import com.cheea.util.ClassRoles;
import com.cheea.util.Dynatic;
import com.cheea.util.HibernateUtil;
import com.cheea.util.Roles;
@ClassRoles(value=true)
public class UserServiceImpl implements UserService {
	
	@Override
	public void addUser(User user) throws ServiceException, RutimeException, DataBaseException {
		UserDao dao=(UserDao)AutoObjectFactory.getInstance("UserDaoImpl");
		dao.addUser(user);
	}
    
	@Override
	public void deleteUser(int id) throws ServiceException, RutimeException,
			DataBaseException {
		UserDao dao=(UserDao)AutoObjectFactory.getInstance("UserDaoImpl");
		dao.deleteUser(id);
	}

	@Override
	public User Login(String name,String password) throws ServiceException, RutimeException,
			DataBaseException {
		UserDao dao=(UserDao)AutoObjectFactory.getInstance("UserDaoImpl");
		User flag=dao.selectUser(name,password);
		return flag;
	}

	@Override
	public List<User> getAll() throws ServiceException, RutimeException,
			DataBaseException {
		UserDao dao=(UserDao)AutoObjectFactory.getInstance("UserDaoImpl");
		return dao.getAll();
	}

	@Override
	public void upUserByAdmin(int id) throws ServiceException,
			RutimeException, DataBaseException {
		UserDao dao=(UserDao)AutoObjectFactory.getInstance("UserDaoImpl");
		dao.upUser(id);
	}

	@Override
	public void downUserByAdmin(int id) throws ServiceException,
			RutimeException, DataBaseException {
		UserDao dao=(UserDao)AutoObjectFactory.getInstance("UserDaoImpl");
		dao.downUser(id);
	}

	@Override
	public void deleteUserByAdmin(int id) throws ServiceException,
			RutimeException, DataBaseException {
		UserDao dao=(UserDao)AutoObjectFactory.getInstance("UserDaoImpl");
		dao.deleteUserByAdmin(id);
	}


}
