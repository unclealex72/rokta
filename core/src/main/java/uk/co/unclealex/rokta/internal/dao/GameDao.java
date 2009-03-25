package uk.co.unclealex.rokta.internal.dao;

import java.util.SortedSet;

import uk.co.unclealex.hibernate.dao.KeyedDao;
import uk.co.unclealex.rokta.internal.model.Game;
import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

public interface GameDao extends KeyedDao<Game> {

	public SortedSet<Game> getGamesByFilter(GameFilter gameFilter);
	
	public Game getLastGame();
	
	public Game getLastGamePlayed(Person person);
}