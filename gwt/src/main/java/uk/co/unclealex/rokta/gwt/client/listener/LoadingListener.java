package uk.co.unclealex.rokta.gwt.client.listener;

public interface LoadingListener<V> extends Listener<V> {

	public void onLoading();
	
	public void onLoaded();
}
