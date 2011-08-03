package uk.co.unclealex.rokta.internal.spring;

import java.util.ResourceBundle;

import org.springframework.beans.factory.FactoryBean;

public class ResourceBundleFactory implements FactoryBean<ResourceBundle> {

	private String i_bundleName;
	
	@Override
	public ResourceBundle getObject() {
		String bundleName = getBundleName();
		return ResourceBundle.getBundle(bundleName);
	}

	@Override
	public Class<?> getObjectType() {
		return ResourceBundle.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String getBundleName() {
		return i_bundleName;
	}

	public void setBundleName(String bundleName) {
		i_bundleName = bundleName;
	}

}
