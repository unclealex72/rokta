package uk.co.unclealex.rokta.server.quotient;

import org.joda.time.DateTime;

import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.quotient.visitor.DatePlayedQuotientFunctionVisitor;

import com.google.common.base.Function;

public abstract class DatePlayedQuotientFunction implements Function<Game, Long> {

	@Override
	public Long apply(Game game) {
		return transformDatePlayed(new DateTime(game.getDatePlayed()));
	}
	
	public abstract long transformDatePlayed(DateTime dateTime);
	
	public abstract <T> T accept(DatePlayedQuotientFunctionVisitor<T> visitor);
}
