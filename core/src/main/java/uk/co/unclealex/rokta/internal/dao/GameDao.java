package uk.co.unclealex.rokta.model.dao;

import java.util.SortedSet;

import uk.co.unclealex.hibernate.dao.KeyedDao;
import uk.co.unclealex.rokta.filter.GameFilter;
import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;

public interface GameDao extends KeyedDao<Game> {

	public SortedSet<Game> getGamesByFilter(GameFilter gameFilter);
	
	public Game getLastGame();
	
	public Game getLastGamePlayed(Person person);
}