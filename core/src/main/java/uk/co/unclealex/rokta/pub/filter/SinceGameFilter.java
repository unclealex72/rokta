package uk.co.unclealex.rokta.pub.filter;

import org.joda.time.DateTime;

public class SinceGameFilter implements GameFilter {

	private DateTime i_since;
	
	public SinceGameFilter() {
	}
	
	public SinceGameFilter(DateTime since) {
		super();
		i_since = since;
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}

	public DateTime getSince() {
		return i_since;
	}

	public void setSince(DateTime since) {
		i_since = since;
	}

}
