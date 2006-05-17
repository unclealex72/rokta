package uk.co.unclealex.rokta.model.dao;

import org.hibernate.Session;

import uk.co.unclealex.rokta.util.HibernateUtils;

public class HibernateDAO {

	protected final Session getSession() {
		return HibernateUtils.getSession();
	}

}
