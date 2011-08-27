package uk.co.unclealex.rokta.client.filter;

import java.util.Date;

public class WeekGameFilter extends DateGameFilter {

	protected WeekGameFilter() {
	}
	
	WeekGameFilter(Modifier modifier, Date date) {
		super(modifier, date);
	}

	@Override
	public <T> T accept(GameFilterVisitor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}
}
