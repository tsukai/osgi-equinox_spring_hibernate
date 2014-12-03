package org.com.norcp.example.module.a.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.com.norcp.example.module.a.dao.api.IUserDao;
import org.com.norcp.example.module.a.pojo.User;

public class UserDaoImpl implements IUserDao {
	
	/**
	 * empty construct method.
	 */
	public UserDaoImpl() {

	}

	public List<User> query() {
		List<User> users = new ArrayList<User>();
		User user1 = new User();
		user1.setName("Eclipse RCP  user");
		User user2 = new User();
		user2.setName("Spring OSGI user");
		users.add(user1);
		users.add(user2);
		return users;
	}

	public void create(User user) {
		user.setName("spring");
		System.out.println("create user");
	}

	public User read(Serializable id) {
		User user = new User();
		user.setName("spring");
		System.out.println("read user");
		return user;
	}

	public void update(User user) {
		user.setName("new spring");
		System.out.println("update user");
	}

	public void delete(User user) {
		System.out.println("delete user");
	}
}
