package uk.co.unclealex.rokta.gwt.client.controller;

import uk.co.unclealex.rokta.gwt.client.model.LoadingModel;

public class LoadingModelAsyncCallback<V> extends ModelAsyncCallback<V, LoadingModel<V>> {

	public LoadingModelAsyncCallback(LoadingModel<V> model) {
		super(model);
		model.setLoading(true);
	}

}
