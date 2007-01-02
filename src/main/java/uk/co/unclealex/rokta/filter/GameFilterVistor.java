package uk.co.unclealex.rokta.filter;

public interface GameFilterVistor {

	public void visit(BeforeGameFilter beforeGameFilter);

	public void visit(BetweenGameFilter betweenGameFilter);

	public void visit(SinceGameFilter sinceGameFilter);

	public void visit(MonthGameFilter monthGameFilter);

	public void visit(WeekGameFilter weekGameFilter);

	public void visit(YearGameFilter yearGameFilter);

	public void visit(PredicateGameFilter predicateGameFilter);

	public void visit(AllGameFilter allGameFilter);
	
}
