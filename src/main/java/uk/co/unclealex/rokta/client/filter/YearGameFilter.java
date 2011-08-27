package uk.co.unclealex.rokta.client.filter;

import java.util.Date;

public class YearGameFilter extends DateGameFilter {

	protected YearGameFilter() {
		super();
	}
	
	YearGameFilter(Modifier modifier, Date date) {
		super(modifier, date);
	}
	
	@Override
	public <T> T accept(GameFilterVisitor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}
}
