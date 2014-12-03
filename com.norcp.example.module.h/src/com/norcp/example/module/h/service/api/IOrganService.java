package com.norcp.example.module.h.service.api;
import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.norcp.example.module.h.domain.Organ;
//@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public interface IOrganService {
	
	/**
	 * @return
	 */
	public List<Organ> queryOrgans();
	
	/**
     * 
     * Create an domain Organ.
     */
	public void createOrgan(Organ organ);
    
    /**
     * 
     * Read Organ by specified id.
     */
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Organ readOrgan(Serializable id);
    /**
     * 
     * Update an Organ.
     */
	public void updateOrgan(Organ organ);

    /**
     * 
     * Delete an Organ.
     */
	public void deleteOrgan(Organ organ);	

}
