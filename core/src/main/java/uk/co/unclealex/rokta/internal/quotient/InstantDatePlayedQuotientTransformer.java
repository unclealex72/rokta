package uk.co.unclealex.rokta.quotient;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.quotient.visitor.DatePlayedQuotientTransformerVisitor;

public class InstantDatePlayedQuotientTransformer extends DatePlayedQuotientTransformer {

	@Override
	public long transformDatePlayed(DateTime dateTime) {
		return dateTime.getMillis();
	}

	public <T> T accept(DatePlayedQuotientTransformerVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
