package uk.co.unclealex.rokta.server.util;

import java.util.Comparator;

public class ReverseComparator<T extends Comparable<T>> implements Comparator<T> {

	@Override
	public int compare(T o1, T o2) {
		return o2.compareTo(o1);
	}

}
