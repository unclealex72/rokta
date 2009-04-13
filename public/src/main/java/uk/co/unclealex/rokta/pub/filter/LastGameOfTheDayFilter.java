package uk.co.unclealex.rokta.pub.filter;

public class LastGameOfTheDayFilter extends AbstractGameFilter {

	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}

	@Override
	public String[] toStringArgs() {
		return new String[0];
	}
	
	@Override
	public boolean isContinuous() {
		return false;
	}
}
