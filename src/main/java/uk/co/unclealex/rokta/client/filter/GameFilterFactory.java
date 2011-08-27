package uk.co.unclealex.rokta.client.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.CalendarUtil;

public final class GameFilterFactory {

	private static final DateTimeFormat DAY_ONLY_FORMAT = DateTimeFormat.getFormat("EEE");
	private static final List<String> DAYS = Lists.newArrayList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");

	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("yyyyMMdd");
	private static final String ALL = "al";
	private static final String YEAR = "ye";
	private static final String WEEK = "we";
	private static final String MONTH = "mo";
	private static final String SINCE = "si";
	private static final String BETWEEN = "bt";
	private static final String BEFORE = "bf";
	
	private static final Map<String, InternalGameFilterFactory> FACTORIES = 
		Maps.uniqueIndex(Arrays.asList(new InternalGameFilterFactory[] {
				new SimpleGameFilterFactory(ALL) { 
					public GameFilter createGameFilter(Modifier modifier) { return createAllGameFilter(modifier); }},
				new SingleDateGameFilterFactory(YEAR) {
						public GameFilter createGameFilter(Modifier modifier, Date date) { return createYearGameFilter(modifier, date); }},
				new SingleDateGameFilterFactory(WEEK) {
					public GameFilter createGameFilter(Modifier modifier, Date date) { return createWeekGameFilter(modifier, date); }},
				new SingleDateGameFilterFactory(MONTH) {
					public GameFilter createGameFilter(Modifier modifier, Date date) { return createMonthGameFilter(modifier, date); }},
				new SingleDateGameFilterFactory(SINCE) {
					public GameFilter createGameFilter(Modifier modifier, Date date) { return createSinceGameFilter(modifier, date); }},
				new SingleDateGameFilterFactory(BEFORE) {
					public GameFilter createGameFilter(Modifier modifier, Date date) { return createBeforeGameFilter(modifier, date); }},
				new DoubleDateGameFilterFactory(BETWEEN) {
					public GameFilter createGameFilter(Modifier modifier, Date from, Date to) { return createBetweenGameFilter(modifier, from, to); }}
			}),
			new Function<InternalGameFilterFactory, String>() {
				public String apply(InternalGameFilterFactory gameFilterFactory) {
					return gameFilterFactory.getPrefix();
			}
		});
	
	public static final GameFilter createBeforeGameFilter(Modifier modifier, Date before) {
		return new BeforeGameFilter(modifier, toDay(before));
	}
	
	public static final GameFilter createBetweenGameFilter(Modifier modifier, Date from, Date to) {
		return new BetweenGameFilter(modifier, toDay(from), toDay(to));
		
	}
	
	public static final GameFilter createSinceGameFilter(Modifier modifier, Date since) {
		return new SinceGameFilter(modifier, toDay(since));
	}
	
	public static final GameFilter createMonthGameFilter(Modifier modifier, Date date) {
		return new MonthGameFilter(modifier, toMonth(date));
	}
	
	public static final GameFilter createWeekGameFilter(Modifier modifier, Date date) {
		return new WeekGameFilter(modifier, toWeek(date));
	}
	
	public static final GameFilter createYearGameFilter(Modifier modifier, Date date) {
		return new YearGameFilter(modifier, toYear(date));
	}
	
	public static final GameFilter createDefaultGameFilter() {
		return createYearGameFilter(new NoOpModifier(), new Date());
	}
	
	public static final GameFilter createAllGameFilter(Modifier modifier) {
		return new AllGameFilter(modifier);
	}

	@SuppressWarnings("deprecation")
	protected static final Date toDay(Date original) {
		Date date = CalendarUtil.copyDate(original);
	  long msec = date.getTime();
	  msec = (msec / 1000) * 1000;
	  date.setTime(msec);
	
	  // Daylight savings time occurs at midnight in some time zones, so we reset
	  // the time to noon instead.
	  date.setHours(12);
	  date.setMinutes(0);
	  date.setSeconds(0);
	  return date;
	}		

	@SuppressWarnings("deprecation")
	protected static final Date toYear(Date original) {
		Date date = toDay(original);
		date.setMonth(0);
		date.setDate(1);
		return date;
	}

	@SuppressWarnings("deprecation")
	protected static final Date toMonth(Date original) {
		Date date = toDay(original);
		date.setDate(1);
		return date;
	}

	protected static final Date toWeek(Date original) {
		Date date = toDay(original);
		String day = DAY_ONLY_FORMAT.format(date);
		int modifier = 1 - DAYS.indexOf(day);
		CalendarUtil.addDaysToDate(date, modifier);
		return date;
	}
	
	static abstract class InternalGameFilterFactory {
		private final String i_prefix;
		
		public InternalGameFilterFactory(String prefix) {
			super();
			i_prefix = prefix;
		}

		abstract GameFilter createGameFilter(Modifier modifier, Date[] dates);
		
		public String getPrefix() {
			return i_prefix;
		}
	}
	
	static abstract class SimpleGameFilterFactory extends InternalGameFilterFactory {
		public SimpleGameFilterFactory(String prefix) {
			super(prefix);
		}

		public GameFilter createGameFilter(Modifier modifier, Date[] dates) {
			return createGameFilter(modifier);
		}
		
		abstract GameFilter createGameFilter(Modifier modifier);
	}

	static abstract class SingleDateGameFilterFactory extends InternalGameFilterFactory {
		public SingleDateGameFilterFactory(String prefix) {
			super(prefix);
		}

		public GameFilter createGameFilter(Modifier modifier, Date[] dates) {
			return createGameFilter(modifier, dates[0]);
		}
		
		abstract GameFilter createGameFilter(Modifier modifier, Date date);
	}
	
	static abstract class DoubleDateGameFilterFactory extends InternalGameFilterFactory {
		public DoubleDateGameFilterFactory(String prefix) {
			super(prefix);
		}

		public GameFilter createGameFilter(Modifier modifier, Date[] dates) {
			return createGameFilter(modifier, dates[0], dates[1]);
		}
		
		abstract GameFilter createGameFilter(Modifier modifier, Date firstDate, Date secondDate);
	}

	static enum ModifierPrefix {
		LAST_YEAR("ly", new LastGameOfTheYearModifier()),
		LAST_MONTH("lm", new LastGameOfTheMonthModifier()),
		LAST_WEEK("lw", new LastGameOfTheWeekModifier()),
		LAST_DAY("ld", new LastGameOfTheDayModifier()),
		FIRST_YEAR("fy", new FirstGameOfTheYearModifier()),
		FIRST_MONTH("fm", new FirstGameOfTheMonthModifier()),
		FIRST_WEEK("fw", new FirstGameOfTheWeekModifier()),
		FIRST_DAY("fd", new FirstGameOfTheDayModifier()),
		NO_OP("no", new NoOpModifier());
		
		private String i_prefix;
		private Modifier i_modifier;
		
		private ModifierPrefix(String prefix, Modifier modifier) {
			i_prefix = prefix;
			i_modifier = modifier;
		}
		
		public String getPrefix() {
			return i_prefix;
		}
		
		public Modifier getModifier() {
			return i_modifier;
		}
	}
	
	public static final GameFilter createGameFilter(String token) {
		final String modifierPrefixToken = token.substring(0, 2);
		Predicate<ModifierPrefix> predicate = new Predicate<ModifierPrefix>() {
			public boolean apply(ModifierPrefix modifierPrefix) {
				return modifierPrefix.getPrefix().equals(modifierPrefixToken);
			}
		};
		Modifier modifier = Iterables.find(Arrays.asList(ModifierPrefix.values()), predicate).getModifier();
		String gameFilterToken = token.substring(2);
		List<String> parts = Lists.newLinkedList(Splitter.on(':').split(gameFilterToken));
		String prefix = parts.remove(0);
		Function<String, Date> function = new Function<String, Date>() {
			public Date apply(String fmt) {
					return DATE_FORMAT.parse(fmt);
			}
		};
		Date[] dates = new Date[parts.size()];
		int idx = 0;
		for (Date date : Iterables.transform(parts, function)) {
			dates[idx++] = date;
		}
		return FACTORIES.get(prefix).createGameFilter(modifier, dates);
	}

	public static final String asToken(GameFilter gameFilter) {
		final Modifier modifier = gameFilter.getModifier();
		final StringBuilder builder = new StringBuilder();
		Predicate<ModifierPrefix> predicate = new Predicate<ModifierPrefix>() {
			@Override
			public boolean apply(ModifierPrefix modifierPrefix) {
				return modifier.equals(modifierPrefix.getModifier());
			}
		};
		builder.append(Iterables.find(Arrays.asList(ModifierPrefix.values()), predicate).getPrefix());
		GameFilterVisitor<Void> visitor = new GameFilterVisitor<Void>() {
			protected Void prefix(String prefix, Date... dates) {
				Function<Date, String> function = new Function<Date, String>() {
					@Override
					public String apply(Date date) {
						return DATE_FORMAT.format(date);
					}
				};
				builder.append(Joiner.on(':').join(
					Iterables.concat(Collections.singleton(prefix), Iterables.transform(Arrays.asList(dates), function))));
				return null;
			}
			
			public Void visit(BeforeGameFilter beforeGameFilter) {
				return prefix(BEFORE, beforeGameFilter.getDate());
			}
			
			public Void visit(BetweenGameFilter betweenGameFilter) {
				return prefix(BETWEEN, betweenGameFilter.getFrom(), betweenGameFilter.getTo());
			}

			@Override
			public Void visit(SinceGameFilter sinceGameFilter) {
				return prefix(SINCE, sinceGameFilter.getDate());
			}

			@Override
			public Void visit(MonthGameFilter monthGameFilter) {
				return prefix(MONTH, monthGameFilter.getDate());
			}

			@Override
			public Void visit(WeekGameFilter weekGameFilter) {
				return prefix(WEEK, weekGameFilter.getDate());
			}

			@Override
			public Void visit(YearGameFilter yearGameFilter) {
				return prefix(YEAR, yearGameFilter.getDate());
			}

			@Override
			public Void visit(AllGameFilter allGameFilter) {
				return prefix(ALL);
			}

			@Override
			public Void visit(GameFilter gameFilter) {
				// Cannot get here.
				return null;
			}
		}; 
		gameFilter.accept(visitor);
		return builder.toString();
	}
}
