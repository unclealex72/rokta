package uk.co.unclealex.rokta.model.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;

public class HibernateGameDao extends HibernateDaoSupport implements GameDao {

	public void store(Game game) {
		getHibernateTemplate().save(game);
	}
	
	public SortedSet<Game> getAllGames() {
		return new TreeSet<Game>(getHibernateTemplate().findByNamedQuery("game.getAll"));
	}

	public SortedSet<Game> fetchAllGames() {
		return new TreeSet<Game>(getHibernateTemplate().findByNamedQuery("game.fetchAll"));
	}

	public SortedSet<Game> getAllGamesSince(Date date) {
		return new TreeSet<Game>(getHibernateTemplate().findByNamedQueryAndNamedParam("game.getAllSince", "date", date));
	}

	public Game getLastGame() {
		return (Game) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						List<Game> games = session.getNamedQuery("game.getLast").list();
            return games.iterator().next();
					}
				});
	}

	public Game getLastGamePlayed(final Person person) {
		return (Game) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
            List<Game> games = session.getNamedQuery("game.getLastForPerson").setEntity("person", person).list(); 
						return games.iterator().next();
					}
				});
	}
}
