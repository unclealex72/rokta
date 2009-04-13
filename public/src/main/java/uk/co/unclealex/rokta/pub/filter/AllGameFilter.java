package uk.co.unclealex.rokta.pub.filter;


public class AllGameFilter extends AbstractGameFilter {

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public String[] toStringArgs() {
		return new String[0];
	}
	
	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}	
}
