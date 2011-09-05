package uk.co.unclealex.rokta.client.presenters;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.places.AdminPlace;
import uk.co.unclealex.rokta.client.places.GameFilterAwarePlace;
import uk.co.unclealex.rokta.client.places.GamePlace;
import uk.co.unclealex.rokta.client.places.GraphPlace;
import uk.co.unclealex.rokta.client.places.HeadToHeadsPlace;
import uk.co.unclealex.rokta.client.places.LeaguePlace;
import uk.co.unclealex.rokta.client.places.LosingStreaksPlace;
import uk.co.unclealex.rokta.client.places.ProfilePlace;
import uk.co.unclealex.rokta.client.places.RoktaPlace;
import uk.co.unclealex.rokta.client.places.RoktaPlaceVisitor;
import uk.co.unclealex.rokta.client.places.WinningStreaksPlace;
import uk.co.unclealex.rokta.client.presenters.TitlePresenter.Display;
import uk.co.unclealex.rokta.client.util.WaitingController;
import uk.co.unclealex.rokta.client.util.WaitingListener;
import uk.co.unclealex.rokta.shared.model.Game;
import uk.co.unclealex.rokta.shared.model.Game.Round;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;

public class TitlePresenter implements Presenter<Display>, WaitingListener, PlaceChangeEvent.Handler {

	public static interface Display extends IsWidget {
	
		HasText getMainTitle();
		Image getWaitingImage();
		void showWaitingImage();
		void hideWaitingImage();
	}
	
	public interface WaitingImages extends ClientBundle {
		
		@Source("images/waiting/pacman.gif")
		ImageResource pacman();
		@Source("images/waiting/flowery.gif")
		ImageResource flowery();
		@Source("images/waiting/snake.gif")
		ImageResource snake();
		@Source("images/waiting/bar.gif")
		ImageResource bar();
		@Source("images/waiting/led-bar.gif")
		ImageResource ledBar();
	}
	
	private final Display i_display;
	private final TitleMessages i_titleMessages;
	private final ImageResource[] i_waitingImages;
	private final Map<Integer, String> i_waitingMessagesByHandler = Maps.newLinkedHashMap();
	
	@Inject
	public TitlePresenter(
			Display display, TitleMessages titleMessages, WaitingController waitingController, WaitingImages waitingImages, EventBus eventBus) {
		super();
		i_display = display;
		i_titleMessages = titleMessages;
		i_waitingImages = new ImageResource[] {
			waitingImages.pacman(),
			waitingImages.flowery(), 
			waitingImages.snake(),
			waitingImages.bar(),
			waitingImages.ledBar() };
		waitingController.addWaitingListener(this);
		eventBus.addHandler(PlaceChangeEvent.TYPE, this);
	}

	@Override
	public void show(AcceptsOneWidget container) {
		Display display = getDisplay();
		container.setWidget(display);
	}

	protected String makeTitle(RoktaPlace roktaPlace) {
		final TitleMessages titleMessages = getTitleMessages();
		RoktaPlaceVisitor<String> visitor = new RoktaPlaceVisitor<String>() {
			public String visit(RoktaPlace roktaPlace) {
				return null;
			}
			public String visit(AdminPlace adminPlace) {
				return makeGameFilterTitle(titleMessages.admin(), adminPlace);
			}
			public String visit(GamePlace gamePlace) {
				return makeGameTitle(gamePlace.getGame());
			}
			public String visit(ProfilePlace profilePlace) {
				return makeGameFilterTitle(titleMessages.profile(profilePlace.getUsername()), profilePlace);
			}
			public String visit(LeaguePlace leaguePlace) {
				return makeGameFilterTitle(titleMessages.league(), leaguePlace);
			}
			public String visit(GraphPlace graphPlace) {
				return makeGameFilterTitle(titleMessages.graph(), graphPlace);
			}
			public String visit(WinningStreaksPlace winningStreaksPlace) {
				return makeGameFilterTitle(titleMessages.winningStreaks(), winningStreaksPlace);
			}
			public String visit(LosingStreaksPlace losingStreaksPlace) {
				return makeGameFilterTitle(titleMessages.losingStreaks(), losingStreaksPlace);
			}
			public String visit(HeadToHeadsPlace headToHeadsPlace) {
				return makeGameFilterTitle(titleMessages.headToHeads(), headToHeadsPlace);
			}
		};
		return roktaPlace.accept(visitor);
	}
	
	protected String makeGameTitle(Game game) {
		TitleMessages titleMessages = getTitleMessages();
		String title;
		if (!game.isStarted()) {
			title = titleMessages.newGame();
		}
		else if (game.isLost()) {
			String loser = game.getLoser();
			String[] possibleTitles = new String[] { 
				titleMessages.gameLostOne(loser), 
				titleMessages.gameLostTwo(loser), 
				titleMessages.gameLostThree(loser), 
				titleMessages.gameLostFour(loser) };
			int rnd = (int) (Math.random() * possibleTitles.length);
			title = possibleTitles[rnd];
		}
		else {
			List<Round> rounds = game.getRounds();
			int round = rounds==null?1:rounds.size() + 1;
			title = titleMessages.gameRound(round);
		}
		return titleMessages.title(title);
	}

	protected String makeGameFilterTitle(String placeTitle, GameFilterAwarePlace place) {
		final TitleMessages titleMessages = getTitleMessages();
		GameFilterVisitor<String> gameFilterVisitor = new GameFilterVisitor<String>() {
			public String visit(BeforeGameFilter beforeGameFilter) {
				return titleMessages.before(beforeGameFilter.getDate());
			}
			public String visit(BetweenGameFilter betweenGameFilter) {
				return titleMessages.between(betweenGameFilter.getFrom(), betweenGameFilter.getTo());
			}
			public String visit(SinceGameFilter sinceGameFilter) {
				return titleMessages.since(sinceGameFilter.getDate());
			}
			public String visit(MonthGameFilter monthGameFilter) {
				return titleMessages.month(monthGameFilter.getDate());
			}
			public String visit(WeekGameFilter weekGameFilter) {
				return titleMessages.week(weekGameFilter.getDate());
			}
			public String visit(YearGameFilter yearGameFilter) {
				return titleMessages.year(yearGameFilter.getDate());
			}
			public String visit(AllGameFilter allGameFilter) {
				return titleMessages.all();
			}
			public String visit(GameFilter gameFilter) {
				return null;
			}
		};
		ModifierVistor<String> modifierVisitor = new ModifierVistor<String>() {
			public String visit(Modifier modifier) {
				return null;
			}
			public String visit(FirstGameOfTheYearModifier firstGameOfTheYearModifier) {
				return titleMessages.firstGameOfTheYear();
			}
			public String visit(FirstGameOfTheMonthModifier firstGameOfTheMonthModifier) {
				return titleMessages.firstGameOfTheMonth();
			}
			public String visit(FirstGameOfTheWeekModifier firstGameOfTheWeekModifier) {
				return titleMessages.firstGameOfTheWeek();
			}
			public String visit(FirstGameOfTheDayModifier firstGameOfTheDayModifier) {
				return titleMessages.firstGameOfTheDay();
			}
			public String visit(LastGameOfTheYearModifier lastGameOfTheYearModifier) {
				return titleMessages.lastGameOfTheYear();
			}
			public String visit(LastGameOfTheMonthModifier lastGameOfTheMonthModifier) {
				return titleMessages.lastGameOfTheMonth();
			}
			public String visit(LastGameOfTheWeekModifier lastGameOfTheWeekModifier) {
				return titleMessages.lastGameOfTheWeek();
			}
			public String visit(LastGameOfTheDayModifier lastGameOfTheDayModifier) {
				return titleMessages.lastGameOfTheDay();
			}
			public String visit(NoOpModifier noOpModifier) {
				return titleMessages.noop();
			}
		};
		GameFilter gameFilter = place.getGameFilter();
		String unmodifiedGameFilter = gameFilter.accept(gameFilterVisitor);
		String modifier = gameFilter.getModifier().accept(modifierVisitor);
		return titleMessages.gameFilterTitle(placeTitle, titleMessages.gameFilter(modifier, unmodifiedGameFilter));
	}
	
	@Override
	public void onPlaceChange(PlaceChangeEvent event) {
		Place newPlace = event.getNewPlace();
		if (newPlace instanceof RoktaPlace) {
			RoktaPlace place = (RoktaPlace) newPlace;
			String title = makeTitle(place);
			getDisplay().getMainTitle().setText(title);
			Window.setTitle(title);
		}
	}

	@Override
	public void startWaiting(String message, int waitingHandler) {
		Display display = getDisplay();
		Map<Integer, String> waitingMessagesByHandler = getWaitingMessagesByHandler();
		if (waitingMessagesByHandler.isEmpty()) {
			ImageResource[] waitingImages = getWaitingImages();
			int rnd = (int) (Math.random() * waitingImages.length);
			display.getWaitingImage().setResource(waitingImages[rnd]);
			display.showWaitingImage();
		}
		waitingMessagesByHandler.put(waitingHandler, message);
		display.getWaitingImage().setTitle(Joiner.on('\n').join(waitingMessagesByHandler.values()));
	}
	
	@Override
	public void stopWaiting(int waitingHandler) {
		Map<Integer, String> waitingMessagesByHandler = getWaitingMessagesByHandler();
		waitingMessagesByHandler.remove(waitingHandler);
		if (waitingMessagesByHandler.isEmpty()) {
			getDisplay().hideWaitingImage();
		}
	}
	
	public Display getDisplay() {
		return i_display;
	}


	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}

	public ImageResource[] getWaitingImages() {
		return i_waitingImages;
	}

	public Map<Integer, String> getWaitingMessagesByHandler() {
		return i_waitingMessagesByHandler;
	}

}
