package uk.co.unclealex.hibernate;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class HibernateSessionBinder {

	private boolean participate;
	private FlushMode flushMode;
	private SessionFactory sessionFactory;
		
	public HibernateSessionBinder(SessionFactory sessionFactory) {
		this(sessionFactory, null);
	}
	
	public HibernateSessionBinder(
			SessionFactory sessionFactory, FlushMode flushMode) {
		super();
		this.flushMode = flushMode;
		this.sessionFactory = sessionFactory;
	}

	public void bind() {
		setParticipate(false);
		SessionFactory sessionFactory = getSessionFactory();
		if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
			// Do not modify the Session: just set the participate flag.
			setParticipate(true);
		}
		else {
			Session session = getSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}	
	}
	
	public void unbind() {
		SessionFactory sessionFactory = getSessionFactory();
		if (!isParticipate()) {
			SessionHolder sessionHolder =
					(SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
			closeSession(sessionHolder.getSession(), sessionFactory);
		}		
	}
	
	protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		FlushMode flushMode = getFlushMode();
		if (flushMode != null) {
			session.setFlushMode(flushMode);
		}
		return session;
	}

	protected void closeSession(Session session, SessionFactory sessionFactory) {
		SessionFactoryUtils.closeSession(session);
	}

	public boolean isParticipate() {
		return participate;
	}
	public void setParticipate(boolean participate) {
		this.participate = participate;
	}
	
	public FlushMode getFlushMode() {
		return flushMode;
	}
	public void setFlushMode(FlushMode flushMode) {
		this.flushMode = flushMode;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Required
	protected void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
