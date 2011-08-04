package uk.co.unclealex.rokta.server.dao;

import java.util.SortedSet;

import uk.co.unclealex.hibernate.dao.KeyedDao;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;

public interface GameDao extends KeyedDao<Game> {

	public SortedSet<Game> getGamesByFilter(GameFilter gameFilter);

	public Game getLastGameInYear(int year);
	public Game getLastGame();
	
	public Game getLastGamePlayed(Person person);

	public Game getFirstGameInYear(int year);
	public Game getFirstGame();
}