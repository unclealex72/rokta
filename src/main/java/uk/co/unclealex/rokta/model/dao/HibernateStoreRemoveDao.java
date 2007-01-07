package uk.co.unclealex.rokta.model.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateStoreRemoveDao<T> extends HibernateDaoSupport implements StoreRemoveDao<T> {

	public void remove(T obj) {
		getHibernateTemplate().delete(obj);
	}

	public void store(T obj) {
		getHibernateTemplate().save(obj);
	}

}
