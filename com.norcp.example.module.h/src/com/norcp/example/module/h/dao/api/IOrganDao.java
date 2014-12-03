package com.norcp.example.module.h.dao.api;

import java.io.Serializable;
import java.util.List;

import com.norcp.example.module.h.domain.Organ;

public interface IOrganDao {
	
	/**
	 * @return
	 */
	public List<Organ> query();
	
	/**
     * 
     * Create an domain Organ.
     */
	public void create(Organ group);
    
    /**
     * 
     * Read Organ by specified id.
     */
	public Organ read(Serializable id);
    /**
     * 
     * Update an Organ.
     */
	public void update(Organ group);

    /**
     * 
     * Delete an Organ.
     */
	public void delete(Organ group);	
	
	public void saySession();
}
