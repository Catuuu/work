package com.cheea.service;

import java.util.List;

import com.cheea.entity.FailClass;
import com.cheea.entity.ReadyClass;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.util.Roles;

public interface ReadyClassService {
	
	@Roles(value=false)
    public List<ReadyClass> getAll() throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=false)
    public List<ReadyClass> getAllPrint() throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=true)
    public void doPai() throws ServiceException,RutimeException,DataBaseException;
    
	@Roles(value=true)
    public void doClean() throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=false)
    public List<ReadyClass> search(String name) throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=false)
    public List<ReadyClass> searchWithTeacher(String name) throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=false)
    public List<ReadyClass> searchWithClass(String name) throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=false)
    public List<ReadyClass> searchPrint(String name) throws ServiceException,RutimeException,DataBaseException;
	

	@Roles(value=true)
	public void updateReady(ReadyClass f) throws ServiceException,RutimeException,DataBaseException;
}
