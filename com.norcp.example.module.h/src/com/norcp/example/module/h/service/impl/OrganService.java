package com.norcp.example.module.h.service.impl;

import java.io.Serializable;
import java.util.List;

import com.norcp.example.module.h.dao.api.IOrganDao;
import com.norcp.example.module.h.domain.Organ;
import com.norcp.example.module.h.service.api.IOrganService;

//@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class OrganService implements IOrganService {

	private IOrganDao organDao;

	/**
	 * 
	 * get organDao
	 */
	public IOrganDao getOrganDao() {
		return this.organDao;
	}

	/**
	 * set organDao
	 * 
	 * @param organDao
	 */
	public void setOrganDao(IOrganDao organDao) {
		this.organDao = organDao;
	}

	//@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Organ> queryOrgans() {
		return this.organDao.query();
	}

	public void createOrgan(Organ organ) {
		try {
			organDao.saySession();
			this.organDao.create(organ);
			if (organ.getName().equals("lisi"))
				throw new Exception();
			organ.setName("wangwu");
			organDao.saySession();
			this.updateOrgan(organ);
		} catch (Exception e) {
			throw new NullPointerException();
		}finally{
			for(Organ or : this.queryOrgans()){
				System.out.println(or.getName());
			}
			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	public Organ readOrgan(Serializable id) {
		return this.organDao.read(id);
	}

	public void updateOrgan(Organ organ) {
		this.organDao.update(organ);
	}

	public void deleteOrgan(Organ organ) {
		this.organDao.delete(organ);
	}

}