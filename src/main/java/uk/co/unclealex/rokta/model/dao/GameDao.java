package uk.co.unclealex.rokta.model.dao;

import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;

public interface GameDao {

	public void store(Game game);

	public SortedSet<Game> getAllGames();
	
	public SortedSet<Game> getAllGamesSince(Date date);
	
	public Game getLastGame();
	
	public Game getLastGamePlayed(Person person);
}