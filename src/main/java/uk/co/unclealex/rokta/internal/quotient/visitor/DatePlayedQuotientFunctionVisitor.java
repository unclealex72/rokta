package uk.co.unclealex.rokta.internal.quotient.visitor;

import uk.co.unclealex.rokta.internal.quotient.DatePlayedQuotientFunction;
import uk.co.unclealex.rokta.internal.quotient.DayDatePlayedQuotientFunction;
import uk.co.unclealex.rokta.internal.quotient.InstantDatePlayedQuotientFunction;
import uk.co.unclealex.rokta.internal.quotient.MonthDatePlayedQuotientFunction;
import uk.co.unclealex.rokta.internal.quotient.WeekDatePlayedQuotientFunction;
import uk.co.unclealex.rokta.internal.quotient.YearDatePlayedQuotientFunction;

public abstract class DatePlayedQuotientFunctionVisitor<T> {

	public T visit(DatePlayedQuotientFunction datePlayedQuotientFunction) {
		throw new IllegalArgumentException(
				datePlayedQuotientFunction.getClass() + " is not a valid DatePlayedQuotientFunction.");
	}
	
	public abstract T visit(DayDatePlayedQuotientFunction dayDatePlayedQuotientFunction);
	
	public abstract T visit(WeekDatePlayedQuotientFunction weekDatePlayedQuotientFunction);
	
	public abstract T visit(MonthDatePlayedQuotientFunction monthDatePlayedQuotientFunction);
	
	public abstract T visit(YearDatePlayedQuotientFunction yearDatePlayedQuotientFunction);
	
	public abstract T visit(InstantDatePlayedQuotientFunction instantDatePlayedQuotientFunction);
}
