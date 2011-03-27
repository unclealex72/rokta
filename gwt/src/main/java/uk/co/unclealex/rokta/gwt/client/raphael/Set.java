package uk.co.unclealex.rokta.gwt.client.raphael;

public class Set extends RaphaelObject<Set> {

	protected Set() {
		// Required protected no-args constructor.
	}
	
	/**
	 * Push a new object into the set.
	 * @param r The new object to push
	 * @return this
	 */
	public final native Set push(RaphaelObject<?> r)
	/*-{ return this.push(r); }-*/;
}
