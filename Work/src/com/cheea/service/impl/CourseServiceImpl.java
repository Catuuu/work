package com.cheea.service.impl;

import java.util.List;

import com.cheea.dao.CourseDao;
import com.cheea.dao.TeacherDao;
import com.cheea.entity.Course;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.factory.AutoObjectFactory;
import com.cheea.service.CourseService;
import com.cheea.util.ClassRoles;
@ClassRoles(value=true)
public class CourseServiceImpl implements CourseService {

	@Override
	public List<Course> getAll() throws ServiceException, RutimeException,
			DataBaseException {
		CourseDao dao=(CourseDao)AutoObjectFactory.getInstance("CourseDaoImpl");
		List<Course> list=dao.getAll();
		return list;
	}

	@Override
	public void addCourse(Course course) throws ServiceException,
			RutimeException, DataBaseException {
		CourseDao dao=(CourseDao)AutoObjectFactory.getInstance("CourseDaoImpl");
        dao.add(course);
	}

	@Override
	public void updateCourse(Course course) throws ServiceException,
			RutimeException, DataBaseException {
		CourseDao dao=(CourseDao)AutoObjectFactory.getInstance("CourseDaoImpl");
        dao.update(course);
	}

	@Override
	public void deleteCourse(String id) throws ServiceException, RutimeException,
			DataBaseException {
		CourseDao dao=(CourseDao)AutoObjectFactory.getInstance("CourseDaoImpl");
		String[] ids=id.split(",");
		for(int i=0;i<ids.length;i++){
			dao.delete(Integer.parseInt(ids[i].trim()));//循环删除，我表示无可奈何
		}
	}

	@Override
	public int Max() throws ServiceException, RutimeException,
			DataBaseException {
		CourseDao dao=(CourseDao)AutoObjectFactory.getInstance("CourseDaoImpl");
		return dao.selectMax();
	}

}
