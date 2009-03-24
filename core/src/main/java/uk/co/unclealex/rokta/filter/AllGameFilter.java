package uk.co.unclealex.rokta.filter;


public class AllGameFilter implements GameFilter {

	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}
	
	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public String toString() {
		return "Filter: All games";
	}
}
