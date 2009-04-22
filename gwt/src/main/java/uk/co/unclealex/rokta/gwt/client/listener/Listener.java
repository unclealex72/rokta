package uk.co.unclealex.rokta.gwt.client.listener;

import java.io.Serializable;

public interface Listener<V> extends Serializable {

	public void onValueChanged(V value);
}
