package uk.co.unclealex.rokta.server.quotient.visitor;

import uk.co.unclealex.rokta.server.quotient.DatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.DayDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.InstantDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.MonthDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.WeekDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.YearDatePlayedQuotientTransformer;

public abstract class DatePlayedQuotientTransformerVisitor<T> {

	public T visit(DatePlayedQuotientTransformer datePlayedQuotientTransformer) {
		throw new IllegalArgumentException(
				datePlayedQuotientTransformer.getClass() + " is not a valid DatePlayedQuotientTransformer.");
	}
	
	public abstract T visit(DayDatePlayedQuotientTransformer dayDatePlayedQuotientTransformer);
	
	public abstract T visit(WeekDatePlayedQuotientTransformer weekDatePlayedQuotientTransformer);
	
	public abstract T visit(MonthDatePlayedQuotientTransformer monthDatePlayedQuotientTransformer);
	
	public abstract T visit(YearDatePlayedQuotientTransformer yearDatePlayedQuotientTransformer);
	
	public abstract T visit(InstantDatePlayedQuotientTransformer instantDatePlayedQuotientTransformer);
}
