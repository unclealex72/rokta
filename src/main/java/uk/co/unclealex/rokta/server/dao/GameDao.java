package uk.co.unclealex.rokta.server.dao;

import java.util.Date;
import java.util.SortedSet;

import uk.co.unclealex.hibernate.dao.KeyedDao;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.model.Game;

public interface GameDao extends KeyedDao<Game> {

	public SortedSet<Game> getGamesByFilter(GameFilter gameFilter);

	public Date getDateLastGamePlayed();

	public Date getDateFirstGamePlayed();

	public Date getDateFirstGameInYearPlayed(int year);

	public Date getDateLastGameInYearPlayed(int year);

	public Game getLastGame();
}