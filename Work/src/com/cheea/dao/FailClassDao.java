package com.cheea.dao;

import java.util.List;
import com.cheea.entity.FailClass;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;

public interface FailClassDao {
	
	public List<FailClass> getAll()throws DataBaseException,RutimeException;
	
	public void update(FailClass f)throws DataBaseException,RutimeException;
}
