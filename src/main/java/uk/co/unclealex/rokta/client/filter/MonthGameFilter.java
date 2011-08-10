package uk.co.unclealex.rokta.client.filter;

import java.util.Date;

public class MonthGameFilter extends DateGameFilter {

	protected MonthGameFilter() {
		super();
	}
	
	public MonthGameFilter(Modifier modifier, Date date) {
		super(modifier, date);
	}

	@Override
	public <T> T accept(GameFilterVisitor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}
}
