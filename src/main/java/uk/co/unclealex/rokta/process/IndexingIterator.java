package uk.co.unclealex.rokta.process;

import java.util.Iterator;

public class IndexingIterator<E> implements Iterator<E> {

	private Iterator<E> i_iterator;
	private int i_index = -1;
	
	public IndexingIterator(Iterator<E> iterator) {
		i_iterator = iterator;
	}

	public boolean hasNext() {
		return i_iterator.hasNext();
	}

	public E next() {
		i_index++;
		return i_iterator.next();
	}

	public void remove() {
		i_index--;
		i_iterator.remove();
	}

	public int getIndex() {
		return i_index;
	}
}
