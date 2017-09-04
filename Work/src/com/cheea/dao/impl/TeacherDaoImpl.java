package com.cheea.dao.impl;

import java.util.List;

import com.cheea.dao.TeacherDao;
import com.cheea.entity.Class;
import com.cheea.entity.Teacher;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.util.ClassRoles;
import com.cheea.util.HibernateTemple;
@ClassRoles(value=true)
public class TeacherDaoImpl implements TeacherDao {

	@Override
	public List<Teacher> getAll() throws DataBaseException, RutimeException {
		List<Teacher> list=(List<Teacher>) HibernateTemple.query("select t from Teacher t)", null);
		return list;
	}

	@Override
	public void add(Teacher teacher) throws DataBaseException, RutimeException {
		HibernateTemple.save(teacher);
	}

	@Override
	public void update(Teacher teacher) throws DataBaseException,
			RutimeException {
		List<?> u=HibernateTemple.query("from Teacher where id=?",teacher.getId());
		Teacher tea=(Teacher) u.get(0);
		tea.setAge(teacher.getAge());
		tea.setCourseId(teacher.getCourseId());
		tea.setName(teacher.getName());
		tea.setPhone(teacher.getPhone());
		HibernateTemple.update(tea);
	}

	@Override
	public void delete(int id) throws DataBaseException, RutimeException {
		List<?> u=HibernateTemple.query("from Teacher where id=?",id);
		Teacher tea=(Teacher) u.get(0);
		HibernateTemple.delete(tea);
	}

}
