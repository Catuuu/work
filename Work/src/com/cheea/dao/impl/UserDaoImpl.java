package com.cheea.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.cheea.dao.UserDao;
import com.cheea.entity.User;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.util.ClassRoles;
import com.cheea.util.Debug;
import com.cheea.util.HibernateTemple;
@ClassRoles(value=true)
public class UserDaoImpl implements UserDao {
    
	public static UserDaoImpl newInstance(){
		return new UserDaoImpl();
	}
	
	@Override
	public void addUser(User user) throws DataBaseException,RutimeException{
       HibernateTemple.save(user);
	}

	@Override
	public User selectUser(String s,String p) throws DataBaseException,
			RutimeException {
		List<User> list=(List<User>)HibernateTemple.query("from User where name=? and password=? and state>0",s,p);
		boolean flag=false;
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<User> findUser(int id) throws DataBaseException,
			RutimeException {
		return null;
	}

	@Override
	public void deleteUser(int id) throws DataBaseException, RutimeException {
		User user=User.newInstance();
		user.setId(id);
		HibernateTemple.delete(user);
	}

	@Override
	public List<User> getAll() throws DataBaseException, RutimeException {
		List<User> list=(List<User>) HibernateTemple.query("from User",null);
		return list;
	}

	@Override
	public void upUser(int id) throws DataBaseException, RutimeException {
		List<?> u=HibernateTemple.query("from User where id=?",id);
		User user=(User) u.get(0);
		user.setState(2);
		HibernateTemple.update(user);
	}

	@Override
	public void downUser(int id) throws DataBaseException, RutimeException {
		List<?> u=HibernateTemple.query("from User where id=?",id);
		User user=(User) u.get(0);
		user.setState(1);
		HibernateTemple.update(user);
	}

	@Override
	public void deleteUserByAdmin(int id) throws DataBaseException,
			RutimeException {
		List<?> u=HibernateTemple.query("from User where id=?",id);
		User user=(User) u.get(0);
		user.setState(0);
		HibernateTemple.update(user);
	}

}
