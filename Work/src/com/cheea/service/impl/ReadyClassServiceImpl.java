package com.cheea.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cheea.dao.CourseDao;
import com.cheea.dao.ReadyClassDao;
import com.cheea.entity.ReadyClass;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.util.ClassRoles;
import com.cheea.util.Debug;
import com.cheea.util.ToString;

@ClassRoles(value=true)
public class ReadyClassServiceImpl implements com.cheea.service.ReadyClassService {

	@Override
	public void doPai() throws ServiceException, DataBaseException, RutimeException {
		ReadyClassDao dao=(ReadyClassDao)AutoObjectFactory.getInstance("ReadyClassDaoImpl");
        dao.doPai();
	}

	@Override
	public void doClean() throws ServiceException, RutimeException,
			DataBaseException {
		ReadyClassDao dao=(ReadyClassDao)AutoObjectFactory.getInstance("ReadyClassDaoImpl");
		dao.doClean();
	}

	@Override
	public List<ReadyClass> getAll() throws ServiceException, RutimeException,
			DataBaseException {
		ReadyClassDao dao=(ReadyClassDao)AutoObjectFactory.getInstance("ReadyClassDaoImpl");
		List<ReadyClass> list=dao.getAll();
		List<ReadyClass> l=new ArrayList<ReadyClass>(); 
		for(int i=0;i<list.size();i++){
			ReadyClass re=list.get(i);
			int t=Integer.parseInt(re.getTime().trim());
			String time=ToString.doString(t);
			re.setTime(time);
			l.add(re);
		}
		return l;
	}

	@Override
	public List<ReadyClass> search(String name) throws ServiceException, RutimeException,
			DataBaseException {
		ReadyClassDao dao=(ReadyClassDao)AutoObjectFactory.getInstance("ReadyClassDaoImpl");
		List<ReadyClass> list=dao.doFind(name);
		List<ReadyClass> l=new ArrayList<ReadyClass>(); 
		for(int i=0;i<list.size();i++){
			ReadyClass re=list.get(i);
			int t=Integer.parseInt(re.getTime().trim());
			String time=ToString.doString(t);
			re.setTime(time);
			l.add(re);
		}
		return l;
	}

	@Override
	public List<ReadyClass> getAllPrint() throws ServiceException,
			RutimeException, DataBaseException {
		ReadyClassDao dao=(ReadyClassDao)AutoObjectFactory.getInstance("ReadyClassDaoImpl");
		return dao.getAll();
	}

	@Override
	public List<ReadyClass> searchPrint(String name) throws ServiceException,
			RutimeException, DataBaseException {
		ReadyClassDao dao=(ReadyClassDao)AutoObjectFactory.getInstance("ReadyClassDaoImpl");
		return dao.doFind(name);
	}

	@Override
	public List<ReadyClass> searchWithTeacher(String name)
			throws ServiceException, RutimeException, DataBaseException {
		ReadyClassDao dao=(ReadyClassDao)AutoObjectFactory.getInstance("ReadyClassDaoImpl");
		List<ReadyClass> list=dao.doFindWithTeacher(name);
		List<ReadyClass> l=new ArrayList<ReadyClass>(); 
		for(int i=0;i<list.size();i++){
			ReadyClass re=list.get(i);
			int t=Integer.parseInt(re.getTime().trim());
			String time=ToString.doString(t);
			re.setTime(time);
			l.add(re);
		}
		return l;
	}

	@Override
	public List<ReadyClass> searchWithClass(String name)
			throws ServiceException, RutimeException, DataBaseException {
		ReadyClassDao dao=(ReadyClassDao)AutoObjectFactory.getInstance("ReadyClassDaoImpl");
		List<ReadyClass> list=dao.doFindWithClass(name);
		List<ReadyClass> l=new ArrayList<ReadyClass>(); 
		for(int i=0;i<list.size();i++){
			ReadyClass re=list.get(i);
			int t=Integer.parseInt(re.getTime().trim());
			String time=ToString.doString(t);
			re.setTime(time);
			l.add(re);
		}
		return l;
	}

	@Override
	public void updateReady(ReadyClass f) throws ServiceException,
			RutimeException, DataBaseException {
		ReadyClassDao dao=(ReadyClassDao)AutoObjectFactory.getInstance("ReadyClassDaoImpl");
		dao.update(f);
	}

}
