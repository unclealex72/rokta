package uk.co.unclealex.rokta.filter;

public class WeekGameFilter implements GameFilter {

	private int i_week;
	private int i_year;

	public WeekGameFilter() {
	}
	
	public WeekGameFilter(int week, int year) {
		super();
		i_week = week;
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
	
	public int getWeek() {
		return i_week;
	}

	protected void setWeek(int week) {
		i_week = week;
	}	
}
