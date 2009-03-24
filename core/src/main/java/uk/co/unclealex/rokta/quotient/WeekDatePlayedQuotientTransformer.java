package uk.co.unclealex.rokta.quotient;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.quotient.visitor.DatePlayedQuotientTransformerVisitor;

public class WeekDatePlayedQuotientTransformer extends DatePlayedQuotientTransformer {

	@Override
	public long transformDatePlayed(DateTime dateTime) {
		return dateTime.getYear() * 100 + dateTime.getWeekOfWeekyear();
	}

	public <T> T accept(DatePlayedQuotientTransformerVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
