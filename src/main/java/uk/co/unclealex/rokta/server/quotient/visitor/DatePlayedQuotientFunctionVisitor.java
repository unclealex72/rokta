package uk.co.unclealex.rokta.server.quotient.visitor;

import uk.co.unclealex.rokta.server.quotient.DatePlayedQuotientFunction;
import uk.co.unclealex.rokta.server.quotient.DayDatePlayedQuotientFunction;
import uk.co.unclealex.rokta.server.quotient.InstantDatePlayedQuotientFunction;
import uk.co.unclealex.rokta.server.quotient.MonthDatePlayedQuotientFunction;
import uk.co.unclealex.rokta.server.quotient.WeekDatePlayedQuotientFunction;
import uk.co.unclealex.rokta.server.quotient.YearDatePlayedQuotientFunction;

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
