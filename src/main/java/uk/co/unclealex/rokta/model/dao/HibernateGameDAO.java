package uk.co.unclealex.rokta.model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.util.HibernateUtils;

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
	
	public List<Game> getAllGames() {
		Session session = getSession();
		Query q = session.getNamedQuery("game.getAll");
		return q.list();
	}

	public List<Game> getAllGamesSince(Date date) {
		Session session = getSession();
		Query q = session.getNamedQuery("game.getAllSince").setDate("date", date);
		return q.list();
	}
}
