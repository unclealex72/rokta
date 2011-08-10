package uk.co.unclealex.rokta.client.filter;

public interface GameFilterVisitor<T> {

	T visit(BeforeGameFilter beforeGameFilter);
	T visit(BetweenGameFilter betweenGameFilter);
	T visit(SinceGameFilter sinceGameFilter);
	T visit(MonthGameFilter monthGameFilter);
	T visit(WeekGameFilter weekGameFilter);
	T visit(YearGameFilter yearGameFilter);
	T visit(AllGameFilter allGameFilter);
	T visit(GameFilter gameFilter);
}
