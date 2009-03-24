package uk.co.unclealex.rokta.quotient.visitor;

import uk.co.unclealex.rokta.quotient.DatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.quotient.DayDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.quotient.MonthDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.quotient.WeekDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.quotient.YearDatePlayedQuotientTransformer;

public abstract class DatePlayedQuotientTransformerVisitor<T> {

	public T visit(DatePlayedQuotientTransformer datePlayedQuotientTransformer) {
		throw new IllegalArgumentException(
				datePlayedQuotientTransformer.getClass() + " is not a valid DatePlayedQuotientTransformer.");
	}
	
	public abstract T visit(DayDatePlayedQuotientTransformer dayDatePlayedQuotientTransformer);
	
	public abstract T visit(WeekDatePlayedQuotientTransformer weekDatePlayedQuotientTransformer);
	
	public abstract T visit(MonthDatePlayedQuotientTransformer monthDatePlayedQuotientTransformer);
	
	public abstract T visit(YearDatePlayedQuotientTransformer yearDatePlayedQuotientTransformer);
}
