package com.cheea.service;

import java.util.List;

import com.cheea.entity.Class;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.util.Roles;

public interface ClassService {
	 @Roles(value=false)
     public List<Class> getAll() throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void addClass(Class c) throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void updateClass(Class c) throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void deleteClass(String id) throws ServiceException,RutimeException,DataBaseException;
}
