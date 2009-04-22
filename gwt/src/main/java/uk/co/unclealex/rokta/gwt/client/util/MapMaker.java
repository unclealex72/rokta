package uk.co.unclealex.rokta.gwt.client.util;

import java.util.LinkedHashMap;

public class MapMaker<K, V> extends LinkedHashMap<K, V> {

	public MapMaker<K, V> add(K key, V value) {
		super.put(key, value);
		return this;
	}
}
