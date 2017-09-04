package com.cheea.service.impl;

import java.util.List;

import com.cheea.dao.StudentDao;
import com.cheea.dao.UserDao;
import com.cheea.entity.Student;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.StudentService;
import com.cheea.util.ClassRoles;
@ClassRoles(value=true)
public class StudentServiceImpl implements StudentService {

	@Override
	public List<Student> getAll() throws ServiceException, RutimeException,
			DataBaseException {
		StudentDao dao=(StudentDao)AutoObjectFactory.getInstance("StudentDaoImpl");
		List<Student> list=dao.getAll();
		return list;
	}

	@Override
	public void addStudent(Student student) throws ServiceException, RutimeException,
			DataBaseException {
		StudentDao dao=(StudentDao)AutoObjectFactory.getInstance("StudentDaoImpl");
		dao.add(student);
	}

	@Override
	public void updateStudent(Student student) throws ServiceException,
			RutimeException, DataBaseException {
		StudentDao dao=(StudentDao)AutoObjectFactory.getInstance("StudentDaoImpl");
		dao.update(student);
	}

	@Override
	public void deleteStudent(String id) throws ServiceException,
			RutimeException, DataBaseException {
		StudentDao dao=(StudentDao)AutoObjectFactory.getInstance("StudentDaoImpl");
		String[] ids=id.split(",");
		for(int i=0;i<ids.length;i++){
			dao.delete(Integer.parseInt(ids[i].trim()));//循环删除，我表示无可奈何
		}
	}

	@Override
	public int Max() throws ServiceException, RutimeException,
			DataBaseException {
		StudentDao dao=(StudentDao)AutoObjectFactory.getInstance("StudentDaoImpl");
		return dao.selectMax();
	}

}
