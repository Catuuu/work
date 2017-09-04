package com.cheea.service;

import java.util.List;

import com.cheea.entity.User;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.util.Roles;

public interface UserService {
	@Roles(value=true)
    public void addUser(User user) throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=true)
	public void deleteUser(int id) throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=false)
	public User Login(String name,String password) throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=false)
	public List<User> getAll() throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=true)
	public void upUserByAdmin(int id) throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=true)
	public void downUserByAdmin(int id) throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=true)
	public void deleteUserByAdmin(int id) throws ServiceException,RutimeException,DataBaseException;
}
