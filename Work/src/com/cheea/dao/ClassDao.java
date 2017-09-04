package com.cheea.dao;

import java.util.List;

import com.cheea.entity.Class;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;

public interface ClassDao {
	public List<Class> getAll()throws DataBaseException,RutimeException;
	
    public void add(Class c)throws DataBaseException,RutimeException;
    
    public void update(Class c)throws DataBaseException,RutimeException;
    
    public void delete(int id)throws DataBaseException,RutimeException;
}
