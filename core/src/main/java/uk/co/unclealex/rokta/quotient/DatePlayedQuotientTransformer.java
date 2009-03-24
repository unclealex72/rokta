package uk.co.unclealex.rokta.quotient;

import org.apache.commons.collections15.Transformer;
import org.joda.time.DateTime;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.quotient.visitor.DatePlayedQuotientTransformerVisitor;

public abstract class DatePlayedQuotientTransformer implements Transformer<Game, Long> {

	@Override
	public Long transform(Game game) {
		return transformDatePlayed(game.getDatePlayed());
	}
	
	public abstract long transformDatePlayed(DateTime dateTime);
	
	public abstract <T> T accept(DatePlayedQuotientTransformerVisitor<T> visitor);
}
