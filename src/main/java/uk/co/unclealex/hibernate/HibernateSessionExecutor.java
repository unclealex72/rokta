package uk.co.unclealex.hibernate;

import java.util.concurrent.Executor;

import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;

public class HibernateSessionExecutor implements Executor {

	private SessionFactory sessionFactory;
	private boolean asynchronous = true;
	
	@Override
	public void execute(final Runnable command) {
		Runnable sessionBoundCommand = new Runnable() {
			@Override
			public void run() {
				HibernateSessionBinder binder =
					new HibernateSessionBinder(getSessionFactory(), FlushMode.AUTO);
				binder.bind();
				try {
					command.run();
				}
				finally {
					binder.unbind();
				}
			}
		};
		Thread thread = new Thread(sessionBoundCommand);
		thread.start();
		if (!isAsynchronous()) {
			try {
				thread.join();
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean isAsynchronous() {
		return asynchronous;
	}

	public void setAsynchronous(boolean asynchronous) {
		this.asynchronous = asynchronous;
	}
}
