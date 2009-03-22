package uk.co.unclealex.rokta.model.dao;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernatePerformanceManager extends HibernateDaoSupport implements PerformanceManager {

	public void flush() {
		getSession().flush();
	}

}
