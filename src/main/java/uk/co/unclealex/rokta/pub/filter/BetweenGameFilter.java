package uk.co.unclealex.rokta.pub.filter;

import java.util.Date;

public class BetweenGameFilter extends AbstractGameFilter {

	private Date i_from;
	private Date i_to;

	public BetweenGameFilter() {
	}
	
	public BetweenGameFilter(Date from, Date to) {
		super();
		i_from = from;
		i_to = to;
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public String[] toStringArgs() {
		return new String[] { makeDateArgument(getFrom()), makeDateArgument(getTo()) };
	}
	
	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}

	public Date getFrom() {
		return i_from;
	}

	public Date getTo() {
		return i_to;
	}

	protected void setFrom(Date from) {
		i_from = from;
	}

	protected void setTo(Date to) {
		i_to = to;
	}
}
