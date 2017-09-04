package com.cheea.dao;

import java.util.List;

import com.cheea.entity.FailClass;
import com.cheea.entity.ReadyClass;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;

public interface ReadyClassDao {
	 
	 public List<ReadyClass> getAll()throws DataBaseException,RutimeException;
	 
     public void doPai() throws DataBaseException,RutimeException;
     
     public void doClean() throws DataBaseException,RutimeException;
     
     public List<ReadyClass> doFind(String name) throws DataBaseException,RutimeException;
     
     public List<ReadyClass> doFindWithTeacher(String name) throws DataBaseException,RutimeException;
     
     public List<ReadyClass> doFindWithClass(String name) throws DataBaseException,RutimeException;
     
     public void update(ReadyClass f)throws DataBaseException,RutimeException;
}
