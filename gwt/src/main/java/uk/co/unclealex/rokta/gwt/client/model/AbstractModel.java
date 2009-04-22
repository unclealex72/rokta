package uk.co.unclealex.rokta.gwt.client.model;

import java.util.ArrayList;
import java.util.List;

import uk.co.unclealex.rokta.gwt.client.listener.Listener;

public abstract class AbstractModel<V> implements Notifier<V>, Model<V> {

	private V i_value;
	private List<Listener<V>> i_listeners = new ArrayList<Listener<V>>();
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.gwt.client.model.Notifier#addListener(L)
	 */
	public void addListener(Listener<V> listener) {
		getListeners().add(listener);
	}
	
	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.gwt.client.model.Notifier#removeListener(L)
	 */
	public void removeListener(Listener<V> listener) {
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
			for (Listener<V> listener : getListeners()) {
				listener.onValueChanged(value);
			}
		}
	}
	
	protected List<Listener<V>> getListeners() {
		return i_listeners;
	}
}
