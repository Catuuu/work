package com.cheea.service;

import java.util.List;

import com.cheea.entity.Course;
import com.cheea.entity.Student;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.util.Roles;

public interface CourseService {
	 @Roles(value=false)
     public List<Course> getAll() throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=false)
     public int Max() throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void addCourse(Course course) throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void updateCourse(Course course) throws ServiceException,RutimeException,DataBaseException;
	 
	 @Roles(value=true)
     public void deleteCourse(String id) throws ServiceException,RutimeException,DataBaseException;
}
