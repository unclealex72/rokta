package uk.co.unclealex.rokta.client.filter;

import java.util.Date;

public class BetweenGameFilter extends AbstractGameFilter<BetweenGameFilter> {

	private Date i_from;
	private Date i_to;

	protected BetweenGameFilter() {
		super();
	}
	
	public BetweenGameFilter(Modifier modifier, Date from, Date to) {
		super(modifier);
		i_from = from;
		i_to = to;
	}

	@Override
	protected boolean isEqual(BetweenGameFilter other) {
		return getFrom().equals(other.getFrom()) && getTo().equals(other.getTo());
	}
	
	@Override
	public <T> T accept(GameFilterVisitor<T> gameFilterVisitor) {
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
