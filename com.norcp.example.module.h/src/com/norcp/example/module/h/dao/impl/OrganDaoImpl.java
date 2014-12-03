package com.norcp.example.module.h.dao.impl;

import com.norcp.example.module.h.dao.api.IOrganDao;
import com.norcp.example.module.h.domain.Organ;
import com.norcp.example.module.hibernate.GenericHibernateDaoSupport;

public class OrganDaoImpl extends GenericHibernateDaoSupport<Organ> implements IOrganDao {

	/**
	 * empty construct method.
	 */
	public OrganDaoImpl() {
		super(Organ.class);
	}
	
	public void saySession(){
		System.out.println(this.getHibernateTemplate().getSessionFactory().getCurrentSession().getTransaction());
	}

}
