package uk.co.unclealex.rokta.server.dao;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import uk.co.unclealex.rokta.client.filter.AllGameFilter;
import uk.co.unclealex.rokta.client.filter.BeforeGameFilter;
import uk.co.unclealex.rokta.client.filter.BetweenGameFilter;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheDayModifier;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheMonthModifier;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheWeekModifier;
import uk.co.unclealex.rokta.client.filter.FirstGameOfTheYearModifier;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.filter.GameFilterVisitor;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheDayModifier;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheMonthModifier;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheWeekModifier;
import uk.co.unclealex.rokta.client.filter.LastGameOfTheYearModifier;
import uk.co.unclealex.rokta.client.filter.Modifier;
import uk.co.unclealex.rokta.client.filter.ModifierVistor;
import uk.co.unclealex.rokta.client.filter.MonthGameFilter;
import uk.co.unclealex.rokta.client.filter.NoOpModifier;
import uk.co.unclealex.rokta.client.filter.SinceGameFilter;
import uk.co.unclealex.rokta.client.filter.WeekGameFilter;
import uk.co.unclealex.rokta.client.filter.YearGameFilter;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

public class GameFilterSupportImpl extends HibernateDaoSupport implements GameFilterSupport {

	@Override
	public Query createQuery(GameFilter gameFilter, String selectClause, QueryParameters queryParameters) {
		return createQuery(gameFilter, selectClause, queryParameters, "");
	}
	
	@Override
	public Query createQuery(GameFilter gameFilter, String selectClause, QueryParameters queryParameters, char gameAlias) {
		return createQuery(gameFilter, selectClause, queryParameters, gameAlias, "");
	}
	
	@Override
	public Query createQuery(GameFilter gameFilter, String selectClause, QueryParameters queryParameters, String postWhereClause) {
		return createQuery(gameFilter, selectClause, queryParameters, 'g', postWhereClause);
	}

	@Override
	public Query createQuery(GameFilter gameFilter, String selectClause, QueryParameters queryParameters, char gameAlias, String postWhereClause) {
		QueryParameters gameFilterParameters = makeQueryParameters(gameFilter, gameAlias);
		StringBuilder builder = new StringBuilder(selectClause);
		QueryParameters allQueryParameters = new JoinQueryParameters(queryParameters, gameFilterParameters);
		Iterable<String> allParameters = allQueryParameters.getParameters();
		if (!Iterables.isEmpty(allParameters)) {
			builder.append(" where ");
			builder.append(Joiner.on(" and ").join(allParameters));
		}
		builder.append(' ').append(postWhereClause);
		Query query = getSession().createQuery(builder.toString());
		allQueryParameters.addRestrictions(query);
		return query;
	}

	protected class Grouper {
		private final boolean i_max;
		private final List<String> i_groupColumns;
		
		public Grouper(boolean max, String... groupColumns) {
			super();
			i_max = max;
			i_groupColumns = Arrays.asList(groupColumns);
		}
		
		public boolean isMax() {
			return i_max;
		}
		
		public List<String> getGroupColumns() {
			return i_groupColumns;
		};
	}
	
	public Grouper makeGrouper(Modifier modifier) {
		ModifierVistor<Grouper> visitor = new ModifierVistor<Grouper>() {

			@Override
			public Grouper visit(FirstGameOfTheYearModifier firstGameOfTheYearModifier) {
				return year(false);
			}

			@Override
			public Grouper visit(FirstGameOfTheMonthModifier firstGameOfTheMonthModifier) {
				return month(false);
			}

			@Override
			public Grouper visit(FirstGameOfTheWeekModifier firstGameOfTheWeekModifier) {
				return week(false);
			}

			@Override
			public Grouper visit(FirstGameOfTheDayModifier firstGameOfTheDayModifier) {
				return day(false);
			}

			@Override
			public Grouper visit(LastGameOfTheYearModifier lastGameOfTheYearModifier) {
				return year(true);
			}

			@Override
			public Grouper visit(LastGameOfTheMonthModifier lastGameOfTheMonthModifier) {
				return month(true);
			}

			@Override
			public Grouper visit(LastGameOfTheWeekModifier lastGameOfTheWeekModifier) {
				return week(true);
			}

			@Override
			public Grouper visit(LastGameOfTheDayModifier lastGameOfTheDayModifier) {
				return day(true);
			}

			@Override
			public Grouper visit(NoOpModifier noOpModifier) {
				return null;
			}

			@Override
			public Grouper visit(Modifier modifier) {
				throw new IllegalArgumentException(modifier.getClass() + " is not a valid modifier.");
			}
		};
		return modifier.accept(visitor);
	}
	
	public Grouper year(boolean max) {
		return new Grouper(max, "yearPlayed");
	}
	
	public Grouper month(boolean max) {
		return new Grouper(max, "yearPlayed", "monthPlayed");
	}

	public Grouper week(boolean max) {
		return new Grouper(max, "yearPlayed", "weekPlayed");
	}

	public Grouper day(boolean max) {
		return new Grouper(max, "yearPlayed", "monthPlayed", "dayPlayed");
	}

	protected QueryParameters makeQueryParameters(GameFilter gameFilter, final char gameAlias) {
		GameFilterVisitor<QueryParameters> visitor = new GameFilterVisitor<QueryParameters>() {

			@Override
			public SimpleQueryParameters visit(final BeforeGameFilter beforeGameFilter) {
				return new SimpleQueryParameters(gameAlias + ".datePlayed < :before") {
					@Override
					public void addRestrictions(Query query) {
						query.setDate("before", midnight(beforeGameFilter.getDate()));
					}
				};
			}

			@Override
			public QueryParameters visit(final BetweenGameFilter betweenGameFilter) {
				return new SimpleQueryParameters(gameAlias + ".datePlayed >= :from", gameAlias + ".datePlayed < :to") {
					@Override
					public void addRestrictions(Query query) {
						query.setDate("from", midnight(betweenGameFilter.getFrom()));
						query.setDate("to", midnight(betweenGameFilter.getTo()));
					}
				};
			}

			@Override
			public QueryParameters visit(final SinceGameFilter sinceGameFilter) {
				return new SimpleQueryParameters(gameAlias + ".datePlayed >= :since") {
					@Override
					public void addRestrictions(Query query) {
						query.setDate("since", midnight(sinceGameFilter.getDate()));
					}
				};
			}

			@Override
			public QueryParameters visit(final MonthGameFilter monthGameFilter) {
				return new SimpleQueryParameters(gameAlias + ".monthPlayed = :month", gameAlias + ".yearPlayed = :year") {
					@Override
					public void addRestrictions(Query query) {
						Calendar cal = asCalendar(monthGameFilter.getDate());
						query.setInteger("month", cal.get(Calendar.MONTH));
						query.setInteger("year", cal.get(Calendar.YEAR));
					}
				};
			}

			@Override
			public QueryParameters visit(final WeekGameFilter weekGameFilter) {
				return new SimpleQueryParameters(gameAlias + ".weekPlayed = :week", gameAlias + ".yearPlayed = :year") {
					@Override
					public void addRestrictions(Query query) {
						Calendar cal = asCalendar(weekGameFilter.getDate());
						query.setInteger("week", cal.get(Calendar.WEEK_OF_YEAR));
						query.setInteger("year", cal.get(Calendar.YEAR));
					}
				};
			}

			@Override
			public QueryParameters visit(final YearGameFilter yearGameFilter) {
				return new SimpleQueryParameters(gameAlias + ".yearPlayed = :year") {
					@Override
					public void addRestrictions(Query query) {
						Calendar cal = asCalendar(yearGameFilter.getDate());
						query.setInteger("year", cal.get(Calendar.YEAR));
					}
				};
			}

			@Override
			public QueryParameters visit(AllGameFilter allGameFilter) {
				return new EmptyQueryParameters();
			}

			@Override
			public QueryParameters visit(GameFilter gameFilter) {
				throw new IllegalArgumentException(gameFilter.getClass() + " is not a valid game filter.");
			}
		};
		QueryParameters queryParameters = gameFilter.accept(visitor);
		Grouper grouper = makeGrouper(gameFilter.getModifier());
		if (grouper != null) {
			String hql = 
				String.format("%s.datePlayed in (select %s(datePlayed) from Game group by %s)",
					grouper.isMax()?"max":"min",
					Joiner.on(", ").join(grouper.getGroupColumns()));
			QueryParameters groupingQueryParameters = new SimpleQueryParameters(hql) {
				@Override
				public void addRestrictions(Query query) {
					// Do nothing
				}
			};
			queryParameters = new JoinQueryParameters(queryParameters, groupingQueryParameters);
		}
		return queryParameters;
	}

	protected Date midnight(Date date) {
		Calendar cal = asCalendar(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	protected Calendar asCalendar(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal;
	}

}
