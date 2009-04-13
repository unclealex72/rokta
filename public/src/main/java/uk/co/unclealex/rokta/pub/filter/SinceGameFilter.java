package uk.co.unclealex.rokta.pub.filter;

import java.util.Date;


public class SinceGameFilter extends AbstractGameFilter {

	private Date i_since;
	
	public SinceGameFilter() {
	}
	
	public SinceGameFilter(Date since) {
		super();
		i_since = since;
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public String[] toStringArgs() {
		return new String[] { makeDateArgument(getSince()) };
	}
	
	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}

	public Date getSince() {
		return i_since;
	}

	public void setSince(Date since) {
		i_since = since;
	}

}
