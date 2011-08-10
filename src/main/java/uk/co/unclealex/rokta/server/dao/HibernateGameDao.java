package uk.co.unclealex.rokta.server.dao;

import java.util.Date;
import java.util.SortedSet;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.server.model.Game;

@Transactional
public class HibernateGameDao extends HibernateKeyedDao<Game> implements GameDao {

	private GameFilterSupport i_gameFilterSupport;
	
	@Override
	public SortedSet<Game> getGamesByFilter(GameFilter gameFilter) {
		Query query = getGameFilterSupport().createQuery(gameFilter, "from Game g", new EmptyQueryParameters());
		return asSortedSet(query);
	}

	@Override
	public Date getDateLastGamePlayed() {
		return uniqueResult(getSession().createQuery("select max(datePlayed) from Game"), Date.class);
	}

	@Override
	public Game getLastGame() {
		return uniqueResult(getSession().createQuery(
				"from Game where datePlayed = (select max(datePlayed) from Game)"));
	}

	@Override
	public Date getDateFirstGamePlayed() {
		return uniqueResult(getSession().createQuery("select min(datePlayed) from Game"), Date.class);
	}

	@Override
	public Date getDateLastGameInYearPlayed(int year) {
		Query query = getSession().createQuery("select max(datePlayed) from Game where yearPlayed = :year");
		query.setInteger("year", year);
		return uniqueResult(query, Date.class);
	}

	@Override
	public Date getDateFirstGameInYearPlayed(int year) {
		Query query = getSession().createQuery("select min(datePlayed) from Game where yearPlayed = :year");
		query.setInteger("year", year);
		return uniqueResult(query, Date.class);
	}

	@Override
	public Game createExampleBean() {
		return new Game();
	}

	public GameFilterSupport getGameFilterSupport() {
		return i_gameFilterSupport;
	}

	public void setGameFilterSupport(GameFilterSupport gameFilterSupport) {
		i_gameFilterSupport = gameFilterSupport;
	}

}
