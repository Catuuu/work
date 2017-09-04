package com.cheea.service;

import java.util.List;

import com.cheea.entity.FailClass;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.util.Roles;

public interface FailClassService {
	@Roles(value=false)
    public List<FailClass> getAll() throws ServiceException,RutimeException,DataBaseException;
	
	@Roles(value=true)
	public void updateFail(FailClass f) throws ServiceException,RutimeException,DataBaseException;
}
