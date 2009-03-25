package uk.co.unclealex.rokta.pub.filter;

public class FirstGameOfTheDayFilter implements GameFilter {

	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}

	@Override
	public boolean isContinuous() {
		return false;
	}
}
