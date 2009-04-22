package uk.co.unclealex.rokta.gwt.client.controller;

import uk.co.unclealex.rokta.gwt.client.model.Model;
import uk.co.unclealex.rokta.gwt.client.view.ErrorHandler;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ModelAsyncCallback<V, M extends Model<V>> implements AsyncCallback<V> {

	private M i_model;
	
	public ModelAsyncCallback(M model) {
		super();
		i_model = model;
	}

	public void onFailure(Throwable caught) {
		ErrorHandler.log(caught);
	}

	protected V transformReturnValue(V value) {
		return value;
	}

	public void onSuccess(V value) {
		getModel().setValue(transformReturnValue(value));
	}

	public M getModel() {
		return i_model;
	}

}
