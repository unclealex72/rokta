package uk.co.unclealex.rokta.client.presenters;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import javax.inject.Inject;
import javax.inject.Provider;

import uk.co.unclealex.rokta.client.cache.InformationCallback;
import uk.co.unclealex.rokta.client.cache.InformationService;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.places.GameFilterAwarePlace;
import uk.co.unclealex.rokta.client.presenters.NewsPresenter.Display;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.DatedGame;
import uk.co.unclealex.rokta.shared.model.League;
import uk.co.unclealex.rokta.shared.model.LeagueRow;
import uk.co.unclealex.rokta.shared.model.News;
import uk.co.unclealex.rokta.shared.model.Streak;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

public class NewsPresenter implements 
	Presenter<Display>, InformationCallback<News>, Function<CurrentInformation, News>, PlaceChangeEvent.Handler {

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
		eventBus.addHandler(PlaceChangeEvent.TYPE, this);
	}

	@Override
	public News apply(CurrentInformation currentInformation) {
		return currentInformation.getNews();
	}
	
	@Override
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
		Comparator<Entry<String, Integer>> comparator = new Comparator<Entry<String,Integer>>() {
			@Override
			public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
				int cmp = e2.getValue() - e1.getValue();
				return cmp == 0?e1.getKey().compareTo(e2.getKey()):cmp;
			}
		};
		List<Entry<String, Integer>> lossCounts = Lists.newArrayList(lossesByName.entrySet());
		Collections.sort(lossCounts, comparator);
		InformationDisplayer<Entry<String, Integer>> lossCountDisplayer = new InformationDisplayer<Map.Entry<String,Integer>>() {
			@Override
			public String asText(TitleMessages titleMessages, Entry<String, Integer> losses) {
				return titleMessages.lossCount(losses.getKey(), losses.getValue());
			}
			@Override
			public String onEmpty(TitleMessages titleMessages) {
				return null;
			}
		};
		processInformation().on(getDisplay().getTodaysGamesPanel()).
			with(Collections.singleton(todaysGames.size()), gameCountDisplayer).
			with(lossCounts, lossCountDisplayer);
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
		StreaksDisplayer winningStreaksDisplayer = new StreaksDisplayer() {
			@Override
			public String onEmpty(TitleMessages titleMessages) {
				return titleMessages.noWinningStreaks();
			}
			@Override
			protected String asText(TitleMessages titleMessages, String personName, int length) {
				return titleMessages.currentWinningStreak(personName, length);
			}
		};
		StreaksDisplayer losingStreaksDisplayer = new StreaksDisplayer() {
			@Override
			public String onEmpty(TitleMessages titleMessages) {
				return titleMessages.noLosingStreaks();
			}
			@Override
			protected String asText(TitleMessages titleMessages, String personName, int length) {
				return titleMessages.currentLosingStreak(personName, length);
			}
		};
		processInformation().on(display.getCurrentStreaksPanel()).
			with(news.getCurrentWinningStreaks(), winningStreaksDisplayer).
			with(news.getCurrentLosingStreaks(), losingStreaksDisplayer);
	}

	abstract class StreaksDisplayer implements InformationDisplayer<Streak> {
		@Override
		public String asText(TitleMessages titleMessages, Streak streak) {
			return asText(titleMessages, streak.getPersonName(), streak.getLength());
		}

		protected abstract String asText(TitleMessages titleMessages, String personName, int length);
		
	}
	public interface InformationDisplayer<I> {
		public String asText(TitleMessages titleMessages, I information);
		public String onEmpty(TitleMessages titleMessages);
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
	public void onPlaceChange(PlaceChangeEvent event) {
		Place newPlace = event.getNewPlace();
		if (newPlace instanceof GameFilterAwarePlace) {
			GameFilterAwarePlace place = (GameFilterAwarePlace) newPlace;
			getInformationService().execute(place.getGameFilter(), this, this);
		}
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
