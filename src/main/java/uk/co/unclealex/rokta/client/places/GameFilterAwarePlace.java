package uk.co.unclealex.rokta.client.places;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import uk.co.unclealex.rokta.client.filter.MonthGameFilter;
import uk.co.unclealex.rokta.client.filter.NoOpModifier;
import uk.co.unclealex.rokta.client.filter.SinceGameFilter;
import uk.co.unclealex.rokta.client.filter.WeekGameFilter;
import uk.co.unclealex.rokta.client.filter.YearGameFilter;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.place.shared.PlaceTokenizer;

public abstract class GameFilterAwarePlace extends RoktaPlace {

	private final GameFilter i_gameFilter;
	
	public GameFilterAwarePlace(GameFilter gameFilter) {
		super();
		i_gameFilter = gameFilter;
	}

	public abstract boolean equals(Object other);
	
	public boolean isEqual(GameFilterAwarePlace other) {
		return getGameFilter().equals(other.getGameFilter());
	}
	
	public abstract GameFilterAwarePlace withGameFilter(GameFilter gameFilter);
	
	protected abstract static class Tokenizer<P extends GameFilterAwarePlace> implements PlaceTokenizer<P> {
		
		private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("yyyyMMdd");
		private static final String ALL = "al";
		private static final String YEAR = "ye";
		private static final String WEEK = "we";
		private static final String MONTH = "mo";
		private static final String SINCE = "si";
		private static final String BETWEEN = "bt";
		private static final String BEFORE = "bf";
		
		private static final Map<String, GameFilterFactory> FACTORIES = 
			Maps.uniqueIndex(Arrays.asList(new GameFilterFactory[] {
					new SimpleGameFilterFactory(ALL) { 
						public GameFilter createGameFilter(Modifier modifier) { return new AllGameFilter(modifier); }},
					new SingleDateGameFilterFactory(YEAR) {
							public GameFilter createGameFilter(Modifier modifier, Date date) { return new YearGameFilter(modifier, date); }},
					new SingleDateGameFilterFactory(WEEK) {
						public GameFilter createGameFilter(Modifier modifier, Date date) { return new WeekGameFilter(modifier, date); }},
					new SingleDateGameFilterFactory(MONTH) {
						public GameFilter createGameFilter(Modifier modifier, Date date) { return new MonthGameFilter(modifier, date); }},
					new SingleDateGameFilterFactory(SINCE) {
						public GameFilter createGameFilter(Modifier modifier, Date date) { return new SinceGameFilter(modifier, date); }},
					new SingleDateGameFilterFactory(BEFORE) {
						public GameFilter createGameFilter(Modifier modifier, Date date) { return new BeforeGameFilter(modifier, date); }},
					new DoubleDateGameFilterFactory(BETWEEN) {
						public GameFilter createGameFilter(Modifier modifier, Date from, Date to) { return new BetweenGameFilter(modifier, from, to); }}
				}),
				new Function<GameFilterFactory, String>() {
					public String apply(GameFilterFactory gameFilterFactory) {
						return gameFilterFactory.getPrefix();
				}
			});
		
		static abstract class GameFilterFactory {
			private final String i_prefix;
			
			public GameFilterFactory(String prefix) {
				super();
				i_prefix = prefix;
			}

			abstract GameFilter createGameFilter(Modifier modifier, Date[] dates);
			
			public String getPrefix() {
				return i_prefix;
			}
		}
		
		static abstract class SimpleGameFilterFactory extends GameFilterFactory {
			public SimpleGameFilterFactory(String prefix) {
				super(prefix);
			}

			public GameFilter createGameFilter(Modifier modifier, Date[] dates) {
				return createGameFilter(modifier);
			}
			
			abstract GameFilter createGameFilter(Modifier modifier);
		}

		static abstract class SingleDateGameFilterFactory extends GameFilterFactory {
			public SingleDateGameFilterFactory(String prefix) {
				super(prefix);
			}

			public GameFilter createGameFilter(Modifier modifier, Date[] dates) {
				return createGameFilter(modifier, dates[0]);
			}
			
			abstract GameFilter createGameFilter(Modifier modifier, Date date);
		}
		
		static abstract class DoubleDateGameFilterFactory extends GameFilterFactory {
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
		
		@Override
		public P getPlace(String token) {
			List<String> tokenParts = Lists.newArrayList(Splitter.on('@').split(token));
			int lastIndex = tokenParts.size() - 1;
			List<String> tokens = tokenParts.subList(0, lastIndex);
			String gameFilterToken = tokenParts.get(lastIndex);
			return getPlace(tokens, parse(gameFilterToken));
		}
		
		protected abstract P getPlace(List<String> tokens, GameFilter gameFilter);

		@Override
		public String getToken(P place) {
			return 
				Joiner.on('@').join(
					Iterables.concat(formatTokens(place), Collections.singleton(format(place.getGameFilter()))));
		}

		protected Iterable<String> formatTokens(P place) {
			return Collections.emptySet();
		}
		
		protected GameFilter parse(String token) {
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

		protected String format(GameFilter gameFilter) {
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

	public GameFilter getGameFilter() {
		return i_gameFilter;
	}
}


