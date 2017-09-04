package com.cheea.dao.impl;

import java.util.List;

import com.cheea.dao.ClassDao;
import com.cheea.entity.Class;
import com.cheea.entity.Student;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.util.ClassRoles;
import com.cheea.util.HibernateTemple;
@ClassRoles(value=true)
public class ClassDaoImpl implements ClassDao {

	@Override
	public List<Class> getAll() throws DataBaseException, RutimeException {
		List<Class> list=(List<Class>) HibernateTemple.query("select c from Class c)", null);
		return list;
	}

	@Override
	public void add(Class c) throws DataBaseException, RutimeException {
		HibernateTemple.save(c);
	}

	@Override
	public void update(Class c) throws DataBaseException,
			RutimeException {
		List<?> u=HibernateTemple.query("from Class where id=?",c.getId());
		Class cal=(Class) u.get(0);
        cal.setClassName(c.getClassName());
        cal.setClassNumber(c.getClassNumber());
        cal.setState(c.getState());
		HibernateTemple.update(cal);
	}

	@Override
	public void delete(int id) throws DataBaseException, RutimeException {
		List<?> u=HibernateTemple.query("from Class where id=?",id);
		Class c=(Class) u.get(0);
		HibernateTemple.delete(c);
	}

}
