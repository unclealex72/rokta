package uk.co.unclealex.rokta.model.dao;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import uk.co.unclealex.rokta.model.Game;
import uk.co.unclealex.rokta.model.Person;
import uk.co.unclealex.rokta.process.restriction.AllGameRestriction;
import uk.co.unclealex.rokta.process.restriction.BeforeGameRestriction;
import uk.co.unclealex.rokta.process.restriction.BetweenGameRestriction;
import uk.co.unclealex.rokta.process.restriction.GameRestriction;
import uk.co.unclealex.rokta.process.restriction.GameRestrictionVisitor;
import uk.co.unclealex.rokta.process.restriction.SinceGameRestriction;

public class HibernateGameDao extends HibernateStoreRemoveDao<Game> implements GameDao {

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
		Criteria criteria = getSession().createCriteria(Game.class);
		restriction.accept(new GetGamesByRestrictionVisitor(criteria));
		List<Game> games = criteria.list();
		SortedSet<Game> sortedGames = new TreeSet<Game>();
		sortedGames.addAll(games);
		return sortedGames;
	}

	public SortedSet<Game> getAllGames() {
		return getGamesByRestriction(new AllGameRestriction());
	}
	
	public Game getLastGame() {
		return (Game) getSession().getNamedQuery("game.getLast").list().iterator().next();
	}

	public Game getLastGamePlayed(final Person person) {
		return
			(Game) getSession().getNamedQuery("game.getLastForPerson").
			setEntity("person", person).list().iterator().next();
	}

	/* (non-Javadoc)
	 * @see uk.co.unclealex.rokta.model.dao.GameDao#getGamesSince(java.util.Date)
	 */
	public SortedSet<Game> getGamesSince(final Date since) {
		SortedSet<Game> games = new TreeSet<Game>();
		games.addAll(getSession().getNamedQuery("game.getAllSince").setParameter("datePlayed", since).list()); 
		return games;
	}

	public Game getGameById(long id) {
		return (Game) getSession().get(Game.class, id);
	}
}
