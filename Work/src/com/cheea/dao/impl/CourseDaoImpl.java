package com.cheea.dao.impl;

import java.util.List;

import com.cheea.dao.CourseDao;
import com.cheea.entity.Course;
import com.cheea.entity.Student;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.util.ClassRoles;
import com.cheea.util.HibernateTemple;
@ClassRoles(value=true)
public class CourseDaoImpl implements CourseDao {

	@Override
	public List<Course> getAll() throws DataBaseException, RutimeException {
		List<Course> list=(List<Course>) HibernateTemple.query("select t from Course t)", null);
		return list;
	}

	@Override
	public void add(Course course) throws DataBaseException, RutimeException {
		HibernateTemple.save(course);

	}

	@Override
	public void update(Course course) throws DataBaseException, RutimeException {
		List<?> u=HibernateTemple.query("from Course where id=?",course.getId());
		Course c=(Course) u.get(0);
        c.setCid(course.getCid());
        c.setName(course.getName());
        c.setSid(course.getSid());
        c.setTime(course.getTime());
	    HibernateTemple.update(c);	
	}

	@Override
	public void delete(int id) throws DataBaseException, RutimeException {
		List<?> u=HibernateTemple.query("from Course where id=?",id);
	    Course c=(Course) u.get(0);
		HibernateTemple.delete(c);
		//HibernateTemple.deleteAll("Delete from Course where id in (?)", id);
	}

	@Override
	public int selectMax() throws DataBaseException, RutimeException {
		List<Integer> u=(List<Integer>) HibernateTemple.query("select max(cid) from Course",null);
		return u.get(0);
	}

}
