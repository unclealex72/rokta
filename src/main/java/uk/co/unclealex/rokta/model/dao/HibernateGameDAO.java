package uk.co.unclealex.rokta.model.dao;

import java.util.Collection;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.Query;
import org.hibernate.Session;

import uk.co.unclealex.rokta.model.Game;

public class HibernateGameDAO extends HibernateDAO implements GameDAO {

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.GameDAO#store(uk.co.unclealex.rokta.model.Game)
	 */
	public void store(Game game) {
		Session session = getSession();
		session.beginTransaction();
		session.save(game);
		session.flush();
		session.getTransaction().commit();
	}
	
	public SortedSet<Game> getAllGames() {
		Session session = getSession();
		Query q = session.getNamedQuery("game.getAll");
		return sortGames(q.list());
	}

	public SortedSet<Game> getAllGamesSince(Date date) {
		Session session = getSession();
		Query q = session.getNamedQuery("game.getAllSince").setDate("date", date);
		return sortGames(q.list());
	}
	
	private SortedSet<Game> sortGames(Collection<Game> games) {
		SortedSet<Game> sortedGames = new TreeSet<Game>();
		sortedGames.addAll(games);
		return sortedGames;
	}


}
