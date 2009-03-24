package uk.co.unclealex.rokta.filter;


public class YearGameFilter implements GameFilter {

	private int i_year;

	public YearGameFilter() {
	}

	public YearGameFilter(int year) {
		i_year = year;
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public <T> T accept(GameFilterVistor<T> gameFilterVisitor) {
		return gameFilterVisitor.visit(this);
	}

	public int getYear() {
		return i_year;
	}

	protected void setYear(int year) {
		i_year = year;
	}
}
