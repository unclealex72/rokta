package uk.co.unclealex.rokta.client.presenters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Provider;

import uk.co.unclealex.rokta.client.cache.CurrentInformationChangeEvent;
import uk.co.unclealex.rokta.client.cache.InformationService;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.presenters.NewsPresenter.Display;
import uk.co.unclealex.rokta.shared.model.DatedGame;
import uk.co.unclealex.rokta.shared.model.League;
import uk.co.unclealex.rokta.shared.model.LeagueRow;
import uk.co.unclealex.rokta.shared.model.News;
import uk.co.unclealex.rokta.shared.model.Streak;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

public class NewsPresenter implements Presenter<Display>, CurrentInformationChangeEvent.Handler {

	public static interface Display extends IsWidget {
		IsWidget createLabel(String text);
		HasWidgets getCurrentStreaksPanel();
		HasWidgets getLeaguePanel();
		HasWidgets getGamePanel();
		HasWidgets getTodaysGamesPanel();
	}
	
	private final Display i_display;
	private final InformationService i_informationService;
	private final TitleMessages i_titleMessages;
	
	@Inject
	public NewsPresenter(Display display, InformationService informationService, TitleMessages titleMessages, EventBus eventBus) {
		i_display = display;
		i_informationService = informationService;
		i_titleMessages = titleMessages;
		eventBus.addHandler(CurrentInformationChangeEvent.TYPE, this);
	}

	public void execute(News news) {
		Display display = getDisplay();
		TitleMessages titleMessages = getTitleMessages();
		processTodaysGames(news, display, titleMessages);
		processStreaks(news, display);
		processLeague(news, display);
		processLastGame(news, display, titleMessages);
	}

	protected void processTodaysGames(News news, Display display, final TitleMessages titleMessages) {
		InformationDisplayer<Integer> gameCountDisplayer = new InformationDisplayer<Integer>() {
			@Override
			public String asText(TitleMessages titleMessages, Integer gameCount) {
				return titleMessages.gamesPlayedToday(gameCount);
			}
			@Override
			public String onEmpty(TitleMessages titleMessages) {
				return titleMessages.gamesPlayedToday(0);
			}
		};
		SortedSet<DatedGame> todaysGames = news.getTodaysGames();
		Map<String, Integer> lossesByName = Maps.newHashMap();
		for (DatedGame game : todaysGames) {
			String loser = game.getLoser();
			Integer losses = lossesByName.get(loser);
			if (losses == null) {
				losses = 0;
			}
			lossesByName.put(loser, losses + 1);
		}
		PersonCountDisplayer lossCountDisplayer = new PersonCountDisplayer() {
			@Override
			protected String asText(TitleMessages titleMessages, List<String> personNames, String personName, int length) {
				return titleMessages.lossCount(personNames, personName, titleMessages.gameCount(length));
			}
			@Override
			public String onEmpty(TitleMessages titleMessages) {
				return null;
			}
		};
		processInformation().on(getDisplay().getTodaysGamesPanel()).
			with(Collections.singleton(todaysGames.size()), gameCountDisplayer).
			with(switchPersonCount(lossesByName), lossCountDisplayer);
	}

	protected void processLastGame(News news, Display display, final TitleMessages titleMessages) {
		InformationDisplayer<String> gameDisplayer = new SimpleInformationDisplayer() {
			@Override
			public String onEmpty(TitleMessages titleMessages) {
				return titleMessages.noGamesPlayed();
			}
		};
		Provider<Iterable<String>> gameInformationProvider = new NullToEmptyProvider<DatedGame, String>(news.getLastGame()) {
			protected Iterable<String> items(DatedGame game) {
				return Arrays.asList(new String[] { 
					titleMessages.lastGameDatePlayed(game.getDatePlayed()),
					titleMessages.lastGameLoser(game.getLoser())
				});
			}
		};
		processInformation().on(display.getGamePanel()).with(gameInformationProvider, gameDisplayer);
	}

	protected void processLeague(News news, Display display) {
		InformationDisplayer<LeagueRow> leagueRowDisplayer = new InformationDisplayer<LeagueRow>() {
			int rank = 1;
			@Override
			public String asText(TitleMessages titleMessages, LeagueRow information) {
				return titleMessages.leagueRowSummary(
						rank++, information.getPersonName(), information.getLossesPerGame() * 100.0);
			}
			@Override
			public String onEmpty(TitleMessages titleMessages) {
				return titleMessages.noLeague();
			}
		};
		Provider<Iterable<LeagueRow>> leagueRowProvider = new NullToEmptyProvider<League, LeagueRow>(news.getLastLeague()) {
			@Override
			protected Iterable<LeagueRow> items(League league) {
				return league.getRows();
			}
		};
		processInformation().on(display.getLeaguePanel()).with(leagueRowProvider, leagueRowDisplayer);
	}

	protected void processStreaks(News news, Display display) {
		PersonCountDisplayer winningStreaksDisplayer = new PersonCountDisplayer() {
			@Override
			public String onEmpty(TitleMessages titleMessages) {
				return titleMessages.noWinningStreaks();
			}
			@Override
			protected String asText(TitleMessages titleMessages, List<String> personNames, String personName, int length) {
				return titleMessages.currentWinningStreak(personNames, personName, length);
			}
		};
		PersonCountDisplayer losingStreaksDisplayer = new PersonCountDisplayer() {
			@Override
			public String onEmpty(TitleMessages titleMessages) {
				return titleMessages.noLosingStreaks();
			}
			@Override
			protected String asText(TitleMessages titleMessages, List<String> personNames, String personName, int length) {
				return titleMessages.currentLosingStreak(personNames, personName, length);
			}
		};
		processInformation().on(display.getCurrentStreaksPanel()).
			with(switchPersonCount(news.getCurrentWinningStreaks()), winningStreaksDisplayer).
			with(switchPersonCount(news.getCurrentLosingStreaks()), losingStreaksDisplayer);
	}

	protected Set<Entry<Integer, Collection<String>>> switchPersonCount(SortedSet<Streak> streaks) {
		Map<String, Integer> streakLengthsByPersonName = Maps.newHashMap();
		for (Streak streak : streaks) {
			streakLengthsByPersonName.put(streak.getPersonName(), streak.getLength());
		}
		return switchPersonCount(streakLengthsByPersonName);
	}
	
	protected Set<Entry<Integer, Collection<String>>> switchPersonCount(Map<String, Integer> countsByPerson) {
		SetMultimap<String, Integer> countsByPersonMultimap = Multimaps.forMap(countsByPerson);
		Supplier<? extends Collection<String>> factory = new Supplier<Collection<String>>() {
			@Override
			public Collection<String> get() {
				return Lists.newArrayList();
			}
		};
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		};
		SortedMap<Integer, Collection<String>> map = new TreeMap<Integer, Collection<String>>(comparator);
		Multimap<Integer, String> dest = Multimaps.newMultimap(map, factory);
		Multimaps.invertFrom(countsByPersonMultimap, dest);
		return map.entrySet();
	}
	
	public interface InformationDisplayer<I> {
		public String asText(TitleMessages titleMessages, I information);
		public String onEmpty(TitleMessages titleMessages);
	}

	abstract class PersonCountDisplayer implements InformationDisplayer<Entry<Integer, Collection<String>>> {
		@Override
		public String asText(TitleMessages titleMessages, Entry<Integer, Collection<String>> information) {
			SortedSet<String> names = Sets.newTreeSet(information.getValue());
			String lastName = names.last();
			List<String> allBarLast = Lists.newArrayList(names.headSet(lastName));
			return asText(getTitleMessages(), allBarLast, lastName, information.getKey());
		}

		protected abstract String asText(TitleMessages titleMessages, List<String> personNames, String personName, int length);
	}
	
	abstract class SimpleInformationDisplayer implements InformationDisplayer<String> {
		@Override
		public String asText(TitleMessages titleMessages, String information) {
			return information;
		}
	}
	
	abstract class NullToEmptyProvider<I, P> implements Provider<Iterable<P>> {
		final I information;
		
		public NullToEmptyProvider(I information) {
			super();
			this.information = information;
		}

		@Override
		public Iterable<P> get() {
			Iterable<P> iterable;
			if (information == null) {
				iterable = Collections.emptySet();
			}
			else {
				iterable = items(information);
				if (iterable == null) {
					iterable = Collections.emptySet();
				}
			}
			return iterable;
		}
		
		protected abstract Iterable<P> items(I information);
		
	}
	
	protected class InformationProcessor {
		HasWidgets panel;
		
		public InformationProcessor on(HasWidgets panel) {
			this.panel = panel;
			panel.clear();
			return this;
		}
		
		public <I> InformationProcessor with(Provider<Iterable<I>> itemsProvider, final InformationDisplayer<I> displayer) {
			return with(itemsProvider.get(), displayer);
		}
		
		public <I> InformationProcessor with(Iterable<I> items, final InformationDisplayer<I> displayer) {
			final TitleMessages titleMessages = getTitleMessages();
			Iterable<String> messages;
			if (Iterables.isEmpty(items)) {
				String emptyMessage = displayer.onEmpty(titleMessages);
				if (emptyMessage == null) {
					messages = Collections.emptySet();
				}
				else {
					messages = Collections.singleton(emptyMessage);
				}
			}
			else {
				Function<I, String> function = new Function<I, String>() {
					public String apply(I information) {
						return displayer.asText(titleMessages, information);
					}
				};
				messages = Iterables.transform(items, function);
			}
			Display display = getDisplay();
			for (String item : messages) {
				panel.add(display.createLabel(item).asWidget());
			}
			return this;
		}
	}
	
	public InformationProcessor processInformation() {
		return new InformationProcessor();
	}
	
	@Override
	public void onCurrentInformationChange(CurrentInformationChangeEvent event) {
		execute(event.getNewCurrentInformation().getNews());
	}
	
	@Override
	public void show(AcceptsOneWidget container) {
		container.setWidget(getDisplay());
	}
	
	public Display getDisplay() {
		return i_display;
	}

	public InformationService getInformationService() {
		return i_informationService;
	}

	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}
}
