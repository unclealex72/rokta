package uk.co.unclealex.rokta.pub.filter;

public class EqualsGameFilterVisitor extends GameFilterVistor<Boolean> {

	@Override
	public Boolean join(Boolean leftResult, Boolean rightResult) {
		return leftResult && rightResult;
	}

	@Override
	public Boolean visit(BeforeGameFilter beforeGameFilter) {
		return false;
	}

	@Override
	public Boolean visit(BetweenGameFilter betweenGameFilter) {
		return false;
	}

	@Override
	public Boolean visit(SinceGameFilter sinceGameFilter) {
		return false;
	}

	@Override
	public Boolean visit(MonthGameFilter monthGameFilter) {
		return false;
	}

	@Override
	public Boolean visit(WeekGameFilter weekGameFilter) {
		return false;
	}

	@Override
	public Boolean visit(YearGameFilter yearGameFilter) {
		return false;
	}

	@Override
	public Boolean visit(AllGameFilter allGameFilter) {
		return false;
	}

	@Override
	public Boolean visit(FirstGameOfTheDayFilter firstGameOfTheDayFilter) {
		return false;
	}

	@Override
	public Boolean visit(FirstGameOfTheWeekFilter firstGameOfTheWeekFilter) {
		return false;
	}

	@Override
	public Boolean visit(FirstGameOfTheMonthFilter firstGameOfTheMonthFilter) {
		return false;
	}

	@Override
	public Boolean visit(FirstGameOfTheYearFilter firstGameOfTheYearFilter) {
		return false;
	}

	@Override
	public Boolean visit(LastGameOfTheDayFilter lastGameOfTheDayFilter) {
		return false;
	}

	@Override
	public Boolean visit(LastGameOfTheWeekFilter lastGameOfTheWeekFilter) {
		return false;
	}

	@Override
	public Boolean visit(LastGameOfTheMonthFilter lastGameOfTheMonthFilter) {
		return false;
	}

	@Override
	public Boolean visit(LastGameOfTheYearFilter lastGameOfTheYearFilter) {
		return false;
	}

}
