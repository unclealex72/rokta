package uk.co.unclealex.hibernate;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class SessionDuringStartupSessionFactoryBean implements FactoryBean<SessionFactory>, ApplicationListener<ApplicationEvent> {

	private static final Logger log = LoggerFactory.getLogger(SessionDuringStartupSessionFactoryBean.class);
	
	private SessionFactory sessionFactory;
	
	@PostConstruct
	public void initialise() {
		SessionFactory sessionFactory = getSessionFactory();
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
	}
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			try {
				SessionFactory sessionFactory = getSessionFactory();
				SessionHolder sessionHolder =
					(SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
				SessionFactoryUtils.closeSession(sessionHolder.getSession());
			}
			catch (IllegalStateException e) {
				log.warn("Could not find a session to unbind.", e);
			}
		}
	}
	
	@Override
	public SessionFactory getObject() {
		return getSessionFactory();
	}

	@Override
	public Class<? extends SessionFactory> getObjectType() {
		SessionFactory sessionFactory = getSessionFactory();
		return (sessionFactory != null) ? sessionFactory.getClass() : SessionFactory.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
