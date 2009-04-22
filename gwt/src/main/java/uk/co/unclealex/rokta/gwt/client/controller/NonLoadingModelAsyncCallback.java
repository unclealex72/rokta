package uk.co.unclealex.rokta.gwt.client.controller;

import uk.co.unclealex.rokta.gwt.client.model.Model;

public class NonLoadingModelAsyncCallback<V> extends ModelAsyncCallback<V, Model<V>> {

	public NonLoadingModelAsyncCallback(Model<V> model) {
		super(model);
	}

}
