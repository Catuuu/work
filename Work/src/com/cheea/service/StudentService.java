package com.cheea.service;

import java.util.List;

import com.cheea.entity.Student;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.util.Roles;

public interface StudentService {
	 
	 @Roles(value=false)
     public List<Student> getAll() throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=false)
     public int Max() throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void addStudent(Student student) throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void updateStudent(Student student) throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void deleteStudent(String id) throws ServiceException,RutimeException,DataBaseException;
}
