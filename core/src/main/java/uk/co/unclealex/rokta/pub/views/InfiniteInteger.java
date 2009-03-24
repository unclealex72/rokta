/**
 * 
 */
package uk.co.unclealex.rokta.views;

import java.io.Serializable;

/**
 * @author alex
 *
 */
public class InfiniteInteger implements Serializable, Comparable<InfiniteInteger> {
	
	public static InfiniteInteger INFINITY = new InfiniteInteger();
	
	private int i_value;
	private boolean i_infinite;
	
	private InfiniteInteger() {
		i_infinite = true;
	}
	
	public InfiniteInteger(int value) {
		i_infinite = false;
		i_value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof InfiniteInteger) {
			InfiniteInteger other = (InfiniteInteger) obj;
			return (isInfinite() && other.isInfinite()) || getValue() == other.getValue();
		}
		else {
			return false;
		}
	}
	
	public int compareTo(InfiniteInteger o) {
		if (isInfinite() && !o.isInfinite()) {
			return 1;
		}
		else if (!isInfinite() && o.isInfinite()) {
			return -1;
		}
		else if (isInfinite() && o.isInfinite()) {
			return 0;
		}
		else {
			return new Integer(getValue()).compareTo(o.getValue());
		}
	}
	
	@Override
	public int hashCode() {
		return isInfinite()?0:getValue();
	}
	
	/**
	 * @return the infinite
	 */
	public boolean isInfinite() {
		return i_infinite;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return i_value;
	}
	
	
}
