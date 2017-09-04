package com.cheea.service.impl;

import java.util.List;

import com.cheea.dao.ClassDao;
import com.cheea.dao.TeacherDao;
import com.cheea.entity.Class;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.TeacherService;
import com.cheea.util.ClassRoles;
@ClassRoles(value=true)
public class TeacherServiceImpl implements TeacherService {

	@Override
	public List<Teacher> getAll() throws ServiceException, RutimeException,
			DataBaseException {
		TeacherDao dao=(TeacherDao)AutoObjectFactory.getInstance("TeacherDaoImpl");
		List<Teacher> list=dao.getAll();
		return list;
	}

	@Override
	public void addTeacher(Teacher teacher) throws ServiceException,
			RutimeException, DataBaseException {
		TeacherDao dao=(TeacherDao)AutoObjectFactory.getInstance("TeacherDaoImpl");
		dao.add(teacher);
	}

	@Override
	public void updateTeacher(Teacher teacher) throws ServiceException,
			RutimeException, DataBaseException {
		TeacherDao dao=(TeacherDao)AutoObjectFactory.getInstance("TeacherDaoImpl");
		dao.update(teacher);
	}

	@Override
	public void deleteTeacher(String id) throws ServiceException, RutimeException,
			DataBaseException {
		TeacherDao dao=(TeacherDao)AutoObjectFactory.getInstance("TeacherDaoImpl");
		String[] ids=id.split(",");
		for(int i=0;i<ids.length;i++){
			dao.delete(Integer.parseInt(ids[i].trim()));//循环删除，我表示无可奈何
		}
	}

}
