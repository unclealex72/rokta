package uk.co.unclealex.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import uk.co.unclealex.hibernate.model.KeyedBean;

public abstract class HibernateKeyedDao<T extends KeyedBean<T>> extends HibernateKeyedReadOnlyDao<T> {

	public HibernateKeyedDao() {
		super();
	}
	
	public HibernateKeyedDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public void remove(T keyedBean) {
		Session session = getSession();
		session.delete(keyedBean);
		session.flush();
	}

	public void store(T keyedBean) {
		getSession().saveOrUpdate(keyedBean);
	}


}
