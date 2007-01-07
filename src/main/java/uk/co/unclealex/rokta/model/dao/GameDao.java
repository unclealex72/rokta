package uk.co.unclealex.rokta.model.dao;

import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.process.restriction.GameRestriction;

public interface GameDao extends StoreRemoveDao<Game> {

	public SortedSet<Game> getGamesByRestriction(GameRestriction restriction);
	
	public SortedSet<Game> getAllGames();
	
	public Game getLastGame();
	
	public Game getLastGamePlayed(Person person);

	public SortedSet<Game> getGamesSince(Date since);
}