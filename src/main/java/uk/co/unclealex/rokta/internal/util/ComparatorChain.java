package uk.co.unclealex.rokta.internal.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ComparatorChain<T> implements Comparator<T> {

	private final List<Comparator<T>> i_comparators;
	
	public ComparatorChain(Comparator<T>... comparators) {
		i_comparators = Arrays.asList(comparators);
	}
	@Override
	public int compare(T o1, T o2) {
		for (Comparator<T> comparator : getComparators()) {
			int cmp = comparator.compare(o1, o2);
			if (cmp != 0) {
				return cmp;
			}
		}
		return 0;
	}

	public List<Comparator<T>> getComparators() {
		return i_comparators;
	}
}
