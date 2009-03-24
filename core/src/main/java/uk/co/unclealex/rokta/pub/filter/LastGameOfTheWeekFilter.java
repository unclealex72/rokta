package uk.co.unclealex.rokta.filter;

public class LastGameOfTheWeekFilter implements GameFilter {

	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}

	@Override
	public boolean isContinuous() {
		return false;
	}
}
