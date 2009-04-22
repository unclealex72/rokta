package uk.co.unclealex.rokta.gwt.client.model;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeModel implements Transient {

	private Collection<Object> i_models = new ArrayList<Object>();
	
	protected void add(Object model) {
		getModels().add(model);
	}
	
	public void clear() {
		for (Object model : getModels()) {
			if (model instanceof Transient) {
				((Transient) model).clear();
			}
		}
	}
	
	public Collection<Object> getModels() {
		return i_models;
	}
}
