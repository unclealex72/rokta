package uk.co.unclealex.rokta.spring;

import org.springframework.context.ApplicationContext;

public interface ApplicationContextAware {

	public ApplicationContext getApplicationContext();
	public void setApplicationContext(ApplicationContext applicationContext);
}
