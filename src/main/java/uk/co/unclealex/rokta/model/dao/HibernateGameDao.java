package uk.co.unclealex.rokta.model.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.process.restriction.SinceGameRestriction;
import uk.co.unclealex.rokta.process.restriction.AllGameRestriction;
import uk.co.unclealex.rokta.process.restriction.BeforeGameRestriction;
import uk.co.unclealex.rokta.process.restriction.BetweenGameRestriction;
import uk.co.unclealex.rokta.process.restriction.GameRestriction;
import uk.co.unclealex.rokta.process.restriction.GameRestrictionVisitor;

public class HibernateGameDao extends HibernateDaoSupport implements GameDao {

	public void store(Game game) {
		getHibernateTemplate().save(game);
	}

	private class GetGamesByRestrictionVisitor extends GameRestrictionVisitor {
		private Criteria i_criteria;

		public GetGamesByRestrictionVisitor(Criteria criteria) {
			i_criteria = criteria;
		}
		
		@Override
		public void visit(BeforeGameRestriction restriction) {
			i_criteria.add(Restrictions.le("datePlayed", restriction.getLatestDate()));
		}

		@Override
		public void visit(SinceGameRestriction restriction) {
			i_criteria.add(Restrictions.ge("datePlayed", restriction.getEarliestDate()));
		}

		@Override
		public void visit(BetweenGameRestriction restriction) {
			i_criteria.add(Restrictions.between(
					"datePlayed", restriction.getEarliestDate(), restriction.getLatestDate()));
		}

		@Override
		public void visit(AllGameRestriction restriction) {
		}
	}
	
	public SortedSet<Game> getGamesByRestriction(final GameRestriction restriction) {
		List<Game> games = getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
						Criteria criteria = session.createCriteria(Game.class);
						restriction.accept(new GetGamesByRestrictionVisitor(criteria));
						return criteria.list();
					}
				});
		SortedSet<Game> sortedGames = new TreeSet<Game>();
		sortedGames.addAll(games);
		return sortedGames;
	}

	public SortedSet<Game> getAllGames() {
		return getGamesByRestriction(new AllGameRestriction());
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

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.GameDao#getGamesSince(java.util.Date)
	 */
	public SortedSet<Game> getGamesSince(final Date since) {
		SortedSet<Game> games = new TreeSet<Game>();
		games.addAll(getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session) throws HibernateException, SQLException {
            return session.getNamedQuery("game.getAllSince").setParameter("datePlayed", since).list(); 
					}
				}));
		return games;
	}
}
