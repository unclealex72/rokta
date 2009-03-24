package uk.co.unclealex.rokta.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextAwareSupport implements ApplicationContextAware {

	private ApplicationContext i_applicationContext;

	@SuppressWarnings("unchecked")
	public <T> T getBean(String name, Class<? extends T> clazz) {
		return (T) getApplicationContext().getBean(name);
	}
	
	public ApplicationContext getApplicationContext() {
		return i_applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		i_applicationContext = applicationContext;
	}

	
}
