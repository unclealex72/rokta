package uk.co.unclealex.rokta.gwt.client.model;

import uk.co.unclealex.rokta.gwt.client.listener.Listener;

public interface Notifier<V> {

	public void addListener(Listener<V> listener);

	public void removeListener(Listener<V> listener);

}