package uk.co.unclealex.rokta.pub.filter;

import org.joda.time.DateTime;

public class BetweenGameFilter implements GameFilter {

	private DateTime i_from;
	private DateTime i_to;

	public BetweenGameFilter() {
	}
	
	public BetweenGameFilter(DateTime from, DateTime to) {
		super();
		i_from = from;
		i_to = to;
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}

	public DateTime getFrom() {
		return i_from;
	}

	public DateTime getTo() {
		return i_to;
	}

	protected void setFrom(DateTime from) {
		i_from = from;
	}

	protected void setTo(DateTime to) {
		i_to = to;
	}
}
