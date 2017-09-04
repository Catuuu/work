package com.cheea.service;

import java.util.List;

import com.cheea.entity.Student;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.util.Roles;

public interface TeacherService {
	 @Roles(value=false)
     public List<Teacher> getAll() throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void addTeacher(Teacher teacher) throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void updateTeacher(Teacher teacher) throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void deleteTeacher(String id) throws ServiceException,RutimeException,DataBaseException;
}
