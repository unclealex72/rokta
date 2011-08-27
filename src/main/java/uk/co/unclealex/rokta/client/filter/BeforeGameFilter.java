package uk.co.unclealex.rokta.client.filter;

import java.util.Date;

public class BeforeGameFilter extends DateGameFilter {

	protected BeforeGameFilter() {
		super();
	}
	
	BeforeGameFilter(Modifier modifier, Date before) {
		super(modifier, before);
	}

	@Override
	public <T> T accept(GameFilterVisitor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}	
}
