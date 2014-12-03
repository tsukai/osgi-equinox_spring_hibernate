package org.com.norcp.example.module.a.dao.api;

import java.io.Serializable;
import java.util.List;

import org.com.norcp.example.module.a.pojo.User;


public interface IUserDao {
	
	/**
	 * @return
	 */
	public List<User> query();
	
	/**
     * 
     * Create an domain User.
     */
	public void create(User user);
    
    /**
     * 
     * Read User by specified id.
     */
	public User read(Serializable id);
    /**
     * 
     * Update an User.
     */
	public void update(User user);

    /**
     * 
     * Delete an User.
     */
	public void delete(User user);	
}
