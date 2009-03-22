package uk.co.unclealex.rokta.predicate;

import java.util.SortedSet;

import org.apache.commons.collections15.Predicate;

import uk.co.unclealex.rokta.encodable.Encodable;
import uk.co.unclealex.rokta.model.Game;

public interface GamePredicate extends Encodable<GamePredicate> {

	public Predicate<Game> createPredicate(SortedSet<Game> games);
	public String getDescription();
	
	public void accept(GamePredicateVisitor visitor);
}
