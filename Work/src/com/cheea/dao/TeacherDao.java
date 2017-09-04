package com.cheea.dao;

import java.util.List;

import com.cheea.entity.Class;
import com.cheea.entity.Student;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;

public interface TeacherDao {
	
	public List<Teacher> getAll()throws DataBaseException,RutimeException;
	
	public void add(Teacher teacher)throws DataBaseException,RutimeException;
    
    public void update(Teacher teacher)throws DataBaseException,RutimeException;
    
    public void delete(int id)throws DataBaseException,RutimeException;
}
