package uk.co.unclealex.rokta.filter;

import org.joda.time.DateTime;

public class BeforeGameFilter implements GameFilter {

	private DateTime i_before;
	
	public BeforeGameFilter() {
	}
	
	public BeforeGameFilter(DateTime before) {
		super();
		i_before = before;
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}
	
	@Override
	public String toString() {
		return "Filter: before " + getBefore(); 
	}

	public DateTime getBefore() {
		return i_before;
	}

	public void setBefore(DateTime before) {
		i_before = before;
	}
}
