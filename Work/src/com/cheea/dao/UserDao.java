package com.cheea.dao;

import java.util.List;

import org.hibernate.Session;

import com.cheea.entity.User;
import com.cheea.excption.DataBaseException;
import com.cheea.excption.RutimeException;
import com.cheea.excption.ServiceException;
import com.cheea.util.Roles;

public interface UserDao {
	/**
	 * 添加用户方法
	 * @param user 用户对象
	 * @throws DataBaseException 数据库异常
	 * @throws RutimeException 运行时异常
	 */
   public void addUser(User user) throws DataBaseException,RutimeException;
   
   public User selectUser(String s,String p) throws DataBaseException,RutimeException;
   
   public List<User> findUser(int id) throws DataBaseException,RutimeException;
   
   public void deleteUser(int id) throws DataBaseException,RutimeException;
   
   public void deleteUserByAdmin(int id) throws DataBaseException,RutimeException;
   
   public List<User> getAll() throws DataBaseException,RutimeException;
   
   public void upUser(int id) throws DataBaseException,RutimeException;
   
   public void downUser(int id) throws DataBaseException,RutimeException;
}
