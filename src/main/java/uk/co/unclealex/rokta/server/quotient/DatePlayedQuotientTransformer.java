package uk.co.unclealex.rokta.server.quotient;

import org.apache.commons.collections15.Transformer;
import org.joda.time.DateTime;

import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.quotient.visitor.DatePlayedQuotientTransformerVisitor;

public abstract class DatePlayedQuotientTransformer implements Transformer<Game, Long> {

	@Override
	public Long transform(Game game) {
		return transformDatePlayed(new DateTime(game.getDatePlayed()));
	}
	
	public abstract long transformDatePlayed(DateTime dateTime);
	
	public abstract <T> T accept(DatePlayedQuotientTransformerVisitor<T> visitor);
}
