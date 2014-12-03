package org.com.norcp.example.module.a.service.impl;
import java.io.Serializable;
import java.util.List;

import org.com.norcp.example.module.a.dao.api.IUserDao;
import org.com.norcp.example.module.a.pojo.User;
import org.com.norcp.example.module.a.service.api.IUserService;

public class UserService implements IUserService {

	private IUserDao userDao;
	
	/**
	 * 
	 * get userDao
	 */
	public IUserDao getUserDao() {
		return this.userDao;
	}

	/**
	 * set userDao
	 * @param userDao
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}    

	public List<User> queryUsers() {
		return this.userDao.query();
	}
	
	public void createUser(User user) {
		this.userDao.create( user );
	}

	public User readUser(Serializable id) {
		return this.userDao.read( id );
	}

	public void updateUser(User user) {
		this.userDao.update( user );
	}

	public void deleteUser(User user) {
		this.userDao.delete( user );
	}

}