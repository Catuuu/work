package com.cheea.dao;

import java.util.List;

import com.cheea.entity.Class;
import com.cheea.entity.Course;
import com.cheea.entity.Student;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;

public interface CourseDao {
	
	public List<Course> getAll()throws DataBaseException,RutimeException;
	
	public void add(Course course)throws DataBaseException,RutimeException;
    
    public void update(Course course)throws DataBaseException,RutimeException;
    
    public void delete(int id)throws DataBaseException,RutimeException;
    
    public int selectMax()throws DataBaseException,RutimeException;
}
