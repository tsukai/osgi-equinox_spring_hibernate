package com.norcp.example.module.hibernate;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class GenericHibernateDaoSupport<E> extends HibernateDaoSupport {
	private final Class<E> clazz;
	public GenericHibernateDaoSupport(Class<E> clazz) {
		this.clazz = clazz;
	}
	
	public void create(E e){
		getHibernateTemplate().save(e);
	}
	
	public E read(Serializable id){
		return (E)getHibernateTemplate().get(clazz, id);
	}
	
	public void update(E e){
		getHibernateTemplate().update(e);
	}
	
	public void delete(E e){
		getHibernateTemplate().delete(e);
	}
	
	public List<E> query(){
		return getHibernateTemplate().loadAll(clazz);
	}
}
