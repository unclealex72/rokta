package uk.co.unclealex.rokta.predicate;

import uk.co.unclealex.rokta.encodable.EncodableFactory;

public interface GamePredicateFactory extends EncodableFactory<GamePredicate> {

	public static final char FIRST_GAME_OF_DAY_PREFIX = 'd';
	public static final char FIRST_GAME_OF_WEEK_PREFIX = 'w';
}
