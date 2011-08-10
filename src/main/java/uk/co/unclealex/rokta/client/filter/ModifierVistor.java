package uk.co.unclealex.rokta.client.filter;

public interface ModifierVistor<T> {

	T visit(Modifier modifier);
	
	T visit(FirstGameOfTheYearModifier firstGameOfTheYearModifier);
	T visit(FirstGameOfTheMonthModifier firstGameOfTheMonthModifier);
	T visit(FirstGameOfTheWeekModifier firstGameOfTheWeekModifier);
	T visit(FirstGameOfTheDayModifier firstGameOfTheDayModifier);

	T visit(LastGameOfTheYearModifier lastGameOfTheYearModifier);
	T visit(LastGameOfTheMonthModifier lastGameOfTheMonthModifier);
	T visit(LastGameOfTheWeekModifier lastGameOfTheWeekModifier);
	T visit(LastGameOfTheDayModifier lastGameOfTheDayModifier);
	
	T visit(NoOpModifier noOpModifier);
}
