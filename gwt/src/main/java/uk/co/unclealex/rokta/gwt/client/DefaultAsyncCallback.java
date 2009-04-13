package uk.co.unclealex.rokta.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class DefaultAsyncCallback<T> implements AsyncCallback<T> {

	public final void onFailure(Throwable t) {
		ErrorHandler.log(t);
	}
}
