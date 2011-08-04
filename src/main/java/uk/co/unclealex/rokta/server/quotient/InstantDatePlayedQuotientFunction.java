package uk.co.unclealex.rokta.server.quotient;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.server.quotient.visitor.DatePlayedQuotientFunctionVisitor;

public class InstantDatePlayedQuotientFunction extends DatePlayedQuotientFunction {

	@Override
	public long transformDatePlayed(DateTime dateTime) {
		return dateTime.getMillis();
	}

	public <T> T accept(DatePlayedQuotientFunctionVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
