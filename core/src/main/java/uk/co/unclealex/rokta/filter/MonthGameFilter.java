package uk.co.unclealex.rokta.filter;


public class MonthGameFilter implements GameFilter {

	private int i_month;
	private int i_year;
	
	public MonthGameFilter(int month, int year) {
		super();
		i_month = month;
		i_year = year;
	}

	public MonthGameFilter() {
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
	
	public int getMonth() {
		return i_month;
	}

	protected void setMonth(int month) {
		i_month = month;
	}
}
