package com.cheea.service.impl;

import java.util.List;

import com.cheea.dao.ClassDao;
import com.cheea.dao.TeacherDao;
import com.cheea.entity.Class;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.ClassService;
import com.cheea.util.ClassRoles;
@ClassRoles(value=true)
public class ClassServiceImpl implements ClassService {

	@Override
	public List<Class> getAll() throws ServiceException, RutimeException,
			DataBaseException {
		ClassDao dao=(ClassDao)AutoObjectFactory.getInstance("ClassDaoImpl");
		List<Class> list=dao.getAll();
		return list;
	}

	@Override
	public void addClass(Class c) throws ServiceException, RutimeException,
			DataBaseException {
		ClassDao dao=(ClassDao)AutoObjectFactory.getInstance("ClassDaoImpl");
		dao.add(c);
	}

	@Override
	public void updateClass(Class c) throws ServiceException, RutimeException,
			DataBaseException {
		ClassDao dao=(ClassDao)AutoObjectFactory.getInstance("ClassDaoImpl");
		dao.update(c);
	}

	@Override
	public void deleteClass(String id) throws ServiceException, RutimeException,
			DataBaseException {
		ClassDao dao=(ClassDao)AutoObjectFactory.getInstance("ClassDaoImpl");
		String[] ids=id.split(",");
		for(int i=0;i<ids.length;i++){
			dao.delete(Integer.parseInt(ids[i].trim()));//循环删除，我表示无可奈何
		}
	}

}
