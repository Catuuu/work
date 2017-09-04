package com.cheea.dao;

import java.util.List;

import com.cheea.entity.Student;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;

public interface StudentDao {
	 
     public List<Student> getAll()throws DataBaseException,RutimeException;
     
     public void add(Student student)throws DataBaseException,RutimeException;
     
     public void update(Student student)throws DataBaseException,RutimeException;
     
     public void delete(int sid)throws DataBaseException,RutimeException;
     
     public int selectMax()throws DataBaseException,RutimeException;
}
