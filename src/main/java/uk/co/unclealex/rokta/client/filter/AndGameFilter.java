package uk.co.unclealex.rokta.client.filter;

public class AndGameFilter extends AbstractGameFilter {

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
	public boolean isContinuous() {
		return getLeftGameFilter().isContinuous() && getRightGameFilter().isContinuous();
	}

	@Override
	public String[] toStringArgs() {
		return new String[] { getLeftGameFilter().toString(), getRightGameFilter().toString() };
	}
	
	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.join(getLeftGameFilter().accept(gameFilterVisitor), getRightGameFilter().accept(gameFilterVisitor));
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
