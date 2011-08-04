package uk.co.unclealex.rokta.server.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.ComparatorUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.comparators.ComparableComparator;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uk.co.unclealex.hibernate.dao.HibernateKeyedDao;
import uk.co.unclealex.rokta.client.filter.AllGameFilter;
import uk.co.unclealex.rokta.client.filter.BeforeGameFilter;
import uk.co.unclealex.rokta.client.filter.BetweenGameFilter;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheDayFilter;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheMonthFilter;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheWeekFilter;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheYearFilter;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.filter.GameFilterVistor;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheDayFilter;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheMonthFilter;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheWeekFilter;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheYearFilter;
import uk.co.unclealex.rokta.client.filter.MonthGameFilter;
import uk.co.unclealex.rokta.client.filter.SinceGameFilter;
import uk.co.unclealex.rokta.client.filter.WeekGameFilter;
import uk.co.unclealex.rokta.client.filter.YearGameFilter;
import uk.co.unclealex.rokta.server.model.Game;
import uk.co.unclealex.rokta.server.model.Person;
import uk.co.unclealex.rokta.server.quotient.DatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.DayDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.MonthDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.QuotientPredicate;
import uk.co.unclealex.rokta.server.quotient.WeekDatePlayedQuotientTransformer;
import uk.co.unclealex.rokta.server.quotient.YearDatePlayedQuotientTransformer;

@Repository
@Transactional
public class HibernateGameDao extends HibernateKeyedDao<Game> implements GameDao {

	@Override
	public SortedSet<Game> getGamesByFilter(GameFilter gameFilter) {
		return gameFilter.accept(new GamesByFilterVisitor());
	}

	@Override
	public Game getFirstGame() {
		return getFirstOrLastGame(true, null);
	}
	
	@Override
	public Game getFirstGameInYear(int year) {
		return getFirstOrLastGame(true, year);
	}
	
	@Override
	public Game getLastGame() {
		return getFirstOrLastGame(false, null);
	}
	
	@Override
	public Game getLastGameInYear(int year) {
		return getFirstOrLastGame(false, year);
	}
	
	protected Game getFirstOrLastGame(boolean first, Integer year) {
		Criteria criteria = createCriteria(new Game());
		if (year != null) {
			Calendar cal = new GregorianCalendar(year, 0, 1, 0, 0, 0);
			criteria.add(Restrictions.ge("datePlayed", cal.getTime()));
			cal.add(Calendar.YEAR, 1);
			criteria.add(Restrictions.lt("datePlayed", cal.getTime()));
		}
		criteria.setMaxResults(1);
		Order order = first?Order.asc("datePlayed"):Order.desc("datePlayed");
		criteria.addOrder(order);
		return uniqueResult(criteria);
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
			return gamesBetween(null, beginningOf(beforeGameFilter.getBefore()));
		}

		@Override
		public SortedSet<Game> visit(BetweenGameFilter betweenGameFilter) {
			return gamesBetween(beginningOf(betweenGameFilter.getFrom()), endOf(betweenGameFilter.getTo()));
		}

		@Override
		public SortedSet<Game> visit(SinceGameFilter sinceGameFilter) {
			return gamesBetween(beginningOf(sinceGameFilter.getSince()), null);
		}

		protected DateTime beginningOf(Date date) {
			DateTime beginningOfDay = new DateTime(date).withHourOfDay(0).withMillisOfDay(0);
			return beginningOfDay;
		}

		protected DateTime endOf(Date date) {
			DateTime endOfDay = beginningOf(date).plusDays(1).minusMillis(1);
			return endOfDay;
		}

		@Override
		public SortedSet<Game> visit(MonthGameFilter monthGameFilter) {
			DateTime startDate = new DateTime(monthGameFilter.getDate());
			startDate = startDate.withDayOfMonth(1).withMillisOfDay(0);
			DateTime endDate = startDate.plusMonths(1).minusMillis(1);
			return gamesBetween(startDate, endDate);
		}

		@Override
		public SortedSet<Game> visit(WeekGameFilter weekGameFilter) {
			DateTime startDate = new DateTime(weekGameFilter.getDate());
			startDate = startDate.withDayOfWeek(1).withMillisOfDay(0);
			DateTime endDate = startDate.plusWeeks(1).minusMillis(1);
			return gamesBetween(startDate, endDate);
		}

		@Override
		public SortedSet<Game> visit(YearGameFilter yearGameFilter) {
			DateTime startDate = new DateTime(yearGameFilter.getDate());
			startDate = startDate.withDayOfYear(1).withMillisOfDay(0);
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
				criteria.add(Restrictions.le("datePlayed", endDate.toDate()));
			}
			else if (startDate != null && endDate == null) {
				criteria.add(Restrictions.ge("datePlayed", startDate.toDate()));
			}
			else if (startDate != null && endDate != null) {
				criteria.add(Restrictions.between("datePlayed", startDate.toDate(), endDate.toDate()));
	
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
