package org.com.norcp.example.module.a.service.api;
import java.io.Serializable;
import java.util.List;

import org.com.norcp.example.module.a.pojo.User;

public interface IUserService {
	
	/**
	 * @return
	 */
	public List<User> queryUsers();
	
	/**
     * 
     * Create an domain User.
     */
	public void createUser(User user);
    
    /**
     * 
     * Read User by specified id.
     */
	public User readUser(Serializable id);
    /**
     * 
     * Update an User.
     */
	public void updateUser(User user);

    /**
     * 
     * Delete an User.
     */
	public void deleteUser(User user);	

}
