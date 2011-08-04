package uk.co.unclealex.rokta.client.filter;

import java.util.Date;

public class BeforeGameFilter extends AbstractGameFilter {

	private Date i_before;
	
	public BeforeGameFilter() {
	}
	
	public BeforeGameFilter(Date before) {
		super();
		i_before = before;
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public String[] toStringArgs() {
		return new String[] { makeDateArgument(getBefore()) };
	}
	
	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}
	
	public Date getBefore() {
		return i_before;
	}

	public void setBefore(Date before) {
		i_before = before;
	}
}
