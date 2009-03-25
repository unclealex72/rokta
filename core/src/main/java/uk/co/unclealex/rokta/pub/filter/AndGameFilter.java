package uk.co.unclealex.rokta.pub.filter;

public class AndGameFilter implements GameFilter {

	private GameFilter i_leftGameFilter;
	private GameFilter i_rightGameFilter;
	
	public AndGameFilter() {
	}
	
	public AndGameFilter(GameFilter leftGameFilter, GameFilter rightGameFilter) {
		super();
		i_leftGameFilter = leftGameFilter;
		i_rightGameFilter = rightGameFilter;
	}

	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.join(getLeftGameFilter().accept(gameFilterVisitor), getRightGameFilter().accept(gameFilterVisitor));
	}

	@Override
	public boolean isContinuous() {
		return getLeftGameFilter().isContinuous() && getRightGameFilter().isContinuous();
	}
	
	public GameFilter getLeftGameFilter() {
		return i_leftGameFilter;
	}

	public void setLeftGameFilter(GameFilter leftGameFilter) {
		i_leftGameFilter = leftGameFilter;
	}

	public GameFilter getRightGameFilter() {
		return i_rightGameFilter;
	}

	public void setRightGameFilter(GameFilter rightGameFilter) {
		i_rightGameFilter = rightGameFilter;
	}

}
