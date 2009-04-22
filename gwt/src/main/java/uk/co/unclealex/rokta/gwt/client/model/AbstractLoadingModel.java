package uk.co.unclealex.rokta.gwt.client.model;

import java.util.ArrayList;
import java.util.List;

import uk.co.unclealex.rokta.gwt.client.listener.LoadingListener;

public abstract class AbstractLoadingModel<V> implements LoadingNotifier<V>, LoadingModel<V> {

	private V i_value;
	private boolean i_loading;
	private List<LoadingListener<V>> i_listeners = new ArrayList<LoadingListener<V>>();
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.gwt.client.model.LoadingNotifier#addListener(L)
	 */
	public void addListener(LoadingListener<V> listener) {
		getListeners().add(listener);
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.gwt.client.model.LoadingNotifier#removeListener(L)
	 */
	public void removeListener(LoadingListener<V> listener) {
		getListeners().remove(listener);
	}

	public void clear() {
		setValue(null);
	}
	
	public V getValue() {
		return i_value;
	}
	
	public void setValue(V value) {
		i_value = value;
		if (value != null) {
			for (LoadingListener<V> listener : getListeners()) {
				listener.onValueChanged(value);
			}
			setLoading(false);
		}
	}
	
	public boolean isLoading() {
		return i_loading;
	}
	
	public void setLoading(boolean loading) {
		i_loading = loading;
		for (LoadingListener<V> listener : getListeners()) {
			if (loading) {
				listener.onLoading();
			}
			else {
				listener.onLoaded();
			}
		}
	}
	
	protected List<LoadingListener<V>> getListeners() {
		return i_listeners;
	}
}
