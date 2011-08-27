package uk.co.unclealex.rokta.client.filter;

import java.util.Date;


public class SinceGameFilter extends DateGameFilter {

	protected SinceGameFilter() {
		super();
	}
	
	SinceGameFilter(Modifier modifier, Date since) {
		super(modifier, since);
	}

	@Override
	public <T> T accept(GameFilterVisitor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}
}
