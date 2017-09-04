package com.cheea.dao.impl;

import java.util.List;

import com.cheea.dao.StudentDao;
import com.cheea.entity.Student;
import com.cheea.entity.User;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.util.ClassRoles;
import com.cheea.util.HibernateTemple;
@ClassRoles(value=true)
public class StudentDaoImpl implements StudentDao {
    
	@Override
	public List<Student> getAll() throws DataBaseException, RutimeException {
		List<Student> list=(List<Student>) HibernateTemple.query("from Student group by className)", null);
		return list;
	}

	@Override
	public void add(Student student) throws DataBaseException, RutimeException {
		HibernateTemple.save(student);
	}

	@Override
	public void update(Student student) throws DataBaseException,
			RutimeException {
		List<?> u=HibernateTemple.query("from Student where sid=?",student.getSid());
		for(int i=0;i<u.size();i++){
			Student stu=(Student) u.get(i);
			stu.setClassName(student.getClassName());
			stu.setNumber(student.getNumber());
			HibernateTemple.update(stu);
		}	
	}

	@Override
	public void delete(int sid) throws DataBaseException,
			RutimeException {
		HibernateTemple.deleteAll("Delete FROM Student Where sid=?" ,sid);
	}

	@Override
	public int selectMax() throws DataBaseException, RutimeException {
		List<Integer> u=(List<Integer>) HibernateTemple.query("select max(sid) from Student",null);
		return u.get(0);
	}

}
