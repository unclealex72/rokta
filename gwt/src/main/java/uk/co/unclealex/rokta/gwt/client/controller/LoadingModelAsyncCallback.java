package uk.co.unclealex.rokta.gwt.client.controller;

import uk.co.unclealex.rokta.gwt.client.model.CurrentlyLoadingModel;
import uk.co.unclealex.rokta.gwt.client.model.LoadingModel;

public class LoadingModelAsyncCallback<V> extends ModelAsyncCallback<V, LoadingModel<V>> {

	private CurrentlyLoadingModel i_currentlyLoadingModel;
	
	public LoadingModelAsyncCallback(CurrentlyLoadingModel currentlyLoadingModel, LoadingModel<V> model) {
		super(model);
		i_currentlyLoadingModel = currentlyLoadingModel;
		currentlyLoadingModel.setValue(true);
		model.setLoading(true);
	}

	public void onSuccess(V value) {
		super.onSuccess(value);
		getCurrentlyLoadingModel().setValue(false);
	}

	public CurrentlyLoadingModel getCurrentlyLoadingModel() {
		return i_currentlyLoadingModel;
	};
}
