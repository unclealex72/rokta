package uk.co.unclealex.rokta.client.filter;

public abstract class GameFilterVistor<T> {

	public abstract T visit(BeforeGameFilter beforeGameFilter);

	public abstract T visit(BetweenGameFilter betweenGameFilter);

	public abstract T visit(SinceGameFilter sinceGameFilter);

	public abstract T visit(MonthGameFilter monthGameFilter);

	public abstract T visit(WeekGameFilter weekGameFilter);

	public abstract T visit(YearGameFilter yearGameFilter);

	public abstract T visit(AllGameFilter allGameFilter);
	
	public abstract T visit(FirstGameOfTheDayFilter firstGameOfTheDayFilter);
	
	public abstract T visit(FirstGameOfTheWeekFilter firstGameOfTheWeekFilter);
	
	public abstract T visit(FirstGameOfTheMonthFilter firstGameOfTheMonthFilter);
	
	public abstract T visit(FirstGameOfTheYearFilter firstGameOfTheYearFilter);
	
	public abstract T visit(LastGameOfTheDayFilter lastGameOfTheDayFilter);
	
	public abstract T visit(LastGameOfTheWeekFilter lastGameOfTheWeekFilter);
	
	public abstract T visit(LastGameOfTheMonthFilter lastGameOfTheMonthFilter);
	
	public abstract T visit(LastGameOfTheYearFilter lastGameOfTheYearFilter);

	public abstract T join(T leftResult, T rightResult);

	public T visit(GameFilter gameFilter) {
		throw new IllegalArgumentException(gameFilter.getClass() + " is not a known game filter type.");
	}
}
