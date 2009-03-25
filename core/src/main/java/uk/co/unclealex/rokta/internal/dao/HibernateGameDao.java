package uk.co.unclealex.rokta.internal.dao;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.ComparatorUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.comparators.ComparableComparator;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.internal.model.Game;
import uk.co.unclealex.rokta.internal.model.Person;
import uk.co.unclealex.rokta.internal.quotient.DatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.internal.quotient.DayDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.internal.quotient.MonthDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.internal.quotient.QuotientPredicate;
import uk.co.unclealex.rokta.internal.quotient.WeekDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.internal.quotient.YearDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.pub.filter.AllGameFilter;
import uk.co.unclealex.rokta.pub.filter.BeforeGameFilter;
import uk.co.unclealex.rokta.pub.filter.BetweenGameFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheDayFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheMonthFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheWeekFilter;
import uk.co.unclealex.rokta.pub.filter.FirstGameOfTheYearFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilterVistor;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheDayFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheMonthFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheWeekFilter;
import uk.co.unclealex.rokta.pub.filter.LastGameOfTheYearFilter;
import uk.co.unclealex.rokta.pub.filter.MonthGameFilter;
import uk.co.unclealex.rokta.pub.filter.SinceGameFilter;
import uk.co.unclealex.rokta.pub.filter.WeekGameFilter;
import uk.co.unclealex.rokta.pub.filter.YearGameFilter;

@Repository
@Transactional
public class HibernateGameDao extends HibernateKeyedDao<Game> implements GameDao {

	@Override
	public SortedSet<Game> getGamesByFilter(GameFilter gameFilter) {
		return gameFilter.accept(new GamesByFilterVisitor());
	}

	public Game getLastGame() {
		return (Game) getSession().getNamedQuery("game.getLast").list().iterator().next();
	}

	public Game getLastGamePlayed(final Person person) {
		return
			(Game) getSession().getNamedQuery("game.getLastForPerson").
			setEntity("person", person).list().iterator().next();
	}

	@Override
	public Game createExampleBean() {
		return new Game();
	}
	
	protected class GamesByFilterVisitor extends GameFilterVistor<SortedSet<Game>> {

		@Override
		public SortedSet<Game> visit(BeforeGameFilter beforeGameFilter) {
			return gamesBetween(null, beforeGameFilter.getBefore());
		}

		@Override
		public SortedSet<Game> visit(BetweenGameFilter betweenGameFilter) {
			return gamesBetween(betweenGameFilter.getFrom(), betweenGameFilter.getTo());
		}

		@Override
		public SortedSet<Game> visit(SinceGameFilter sinceGameFilter) {
			return gamesBetween(sinceGameFilter.getSince(), null);
		}

		@Override
		public SortedSet<Game> visit(MonthGameFilter monthGameFilter) {
			DateTime startDate = new DateTime(monthGameFilter.getYear(), monthGameFilter.getMonth(), 1, 0, 0, 0, 0);
			DateTime endDate = startDate.plusMonths(1).minusMillis(1);
			return gamesBetween(startDate, endDate);
		}

		@Override
		public SortedSet<Game> visit(WeekGameFilter weekGameFilter) {
			DateTime startDate = new DateTime(weekGameFilter.getYear(), 1, 1, 0, 0, 0, 0).withWeekOfWeekyear(weekGameFilter.getWeek());
			DateTime endDate = startDate.plusWeeks(1).minusMillis(1);
			return gamesBetween(startDate, endDate);
		}

		@Override
		public SortedSet<Game> visit(YearGameFilter yearGameFilter) {
			DateTime startDate = new DateTime(yearGameFilter.getYear(), 1, 1, 0, 0, 0, 0);
			DateTime endDate = startDate.plusYears(1).minusMillis(1);
			return gamesBetween(startDate, endDate);
		}

		@Override
		public SortedSet<Game> visit(AllGameFilter allGameFilter) {
			return getAll();
		}

		public SortedSet<Game> gamesBetween(DateTime startDate, DateTime endDate) {
			Criteria criteria = getSession().createCriteria(Game.class);
			if (startDate == null && endDate != null) {
				criteria.add(Restrictions.le("datePlayed", endDate));
			}
			else if (startDate != null && endDate == null) {
				criteria.add(Restrictions.ge("datePlayed", startDate));
			}
			else if (startDate != null && endDate != null) {
				criteria.add(Restrictions.between("datePlayed", startDate, endDate));
	
			}
			return asSortedSet(criteria);
		}
		
		@Override
		public SortedSet<Game> visit(FirstGameOfTheDayFilter firstGameOfTheDayFilter) {
			return gamesMatching(new DayDatePlayedQuotientTransformer(), false);
		}
		
		@Override
		public SortedSet<Game> visit(FirstGameOfTheWeekFilter firstGameOfTheWeekFilter) {
			return gamesMatching(new WeekDatePlayedQuotientTransformer(), false);
		}
		
		@Override
		public SortedSet<Game> visit(FirstGameOfTheMonthFilter firstGameOfTheMonthFilter) {
			return gamesMatching(new MonthDatePlayedQuotientTransformer(), false);
		}
		
		@Override
		public SortedSet<Game> visit(FirstGameOfTheYearFilter firstGameOfTheYearFilter) {
			return gamesMatching(new YearDatePlayedQuotientTransformer(), false);
		}
		
		@Override
		public SortedSet<Game> visit(LastGameOfTheDayFilter lastGameOfTheDayFilter) {
			return gamesMatching(new DayDatePlayedQuotientTransformer(), true);
		}
		
		@Override
		public SortedSet<Game> visit(LastGameOfTheWeekFilter lastGameOfTheWeekFilter) {
			return gamesMatching(new WeekDatePlayedQuotientTransformer(), true);
		}
		
		@Override
		public SortedSet<Game> visit(LastGameOfTheMonthFilter lastGameOfTheMonthFilter) {
			return gamesMatching(new MonthDatePlayedQuotientTransformer(), true);
		}
		
		@Override
		public SortedSet<Game> visit(LastGameOfTheYearFilter lastGameOfTheYearFilter) {
			return gamesMatching(new YearDatePlayedQuotientTransformer(), true);
		}
		
		public SortedSet<Game> gamesMatching(DatePlayedQuotientTransformer transformer, boolean reverseList) {
			return gamesMatching(new QuotientPredicate<Game, Long>(transformer), reverseList);
		}
		
		public SortedSet<Game> gamesMatching(Predicate<Game> predicate, boolean reverseList) {
			SortedSet<Game> allGames;
			if (reverseList) {
				allGames = new TreeSet<Game>(ComparatorUtils.reversedComparator(ComparableComparator.getInstance()));
				allGames.addAll(getAll());
			}
			else {
				allGames = getAll();
			}
			SortedSet<Game> matchingGames = new TreeSet<Game>();
			CollectionUtils.select(allGames, predicate, matchingGames);
			return matchingGames;
		}
		
		@Override
		public SortedSet<Game> join(SortedSet<Game> leftResult, SortedSet<Game> rightResult) {
			SortedSet<Game> intersection = new TreeSet<Game>(leftResult);
			intersection.retainAll(rightResult);
			return intersection;
		}
	}	
}
