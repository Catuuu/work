package com.cheea.dao.impl;

import java.util.List;

import com.cheea.dao.FailClassDao;
import com.cheea.entity.Course;
import com.cheea.entity.FailClass;
import com.cheea.entity.ReadyClass;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.util.ClassRoles;
import com.cheea.util.HibernateTemple;
@ClassRoles(value=true)
public class FailClassDaoImpl implements FailClassDao {

	@Override
	public List<FailClass> getAll() throws DataBaseException, RutimeException {
		List<FailClass> list=(List<FailClass>) HibernateTemple.query("from FailClass order by studentName)", null);
		return list;
	}

	@Override
	public void update(FailClass f) throws DataBaseException, RutimeException {
		List<?> u=HibernateTemple.query("from FailClass where id=?",f.getId());
		FailClass fail=(FailClass) u.get(0);
		HibernateTemple.delete(fail);
		ReadyClass r=ReadyClass.newInstance();
		r.setClassName(f.getClassName());
		r.setCourseName(f.getCourseName());
		r.setStudentName(f.getStudentName());
		r.setTeacherName(f.getTeacherName());
		r.setTime(f.getTime());
		HibernateTemple.save(r);
	}

}
