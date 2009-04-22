package uk.co.unclealex.rokta.gwt.client.model;

import uk.co.unclealex.rokta.gwt.client.listener.LoadingListener;

public interface LoadingNotifier<V> {

	public void addListener(LoadingListener<V> listener);

	public void removeListener(LoadingListener<V> listener);

}