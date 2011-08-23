package uk.co.unclealex.rokta.client.presenters;

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
import uk.co.unclealex.rokta.client.util.TitleManager;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;

public class TitlePresenter implements Presenter, TitleManager {

	public static interface Display extends IsWidget {
	
		HasText getMainTitle();
	}
	
	private final Display i_display;
	private final TitleMessages i_titleMessages;
	
	@Inject
	public TitlePresenter(Display display, TitleMessages titleMessages) {
		super();
		i_display = display;
		i_titleMessages = titleMessages;
	}

	@Override
	public void show(AcceptsOneWidget container) {
		container.setWidget(getDisplay());
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
				return getTitleMessages().title(titleMessages.game());
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
	public void updateTitle(RoktaPlace place) {
		String title = makeTitle(place);
		getDisplay().getMainTitle().setText(title);
		Window.setTitle(title);
	}
	
	public Display getDisplay() {
		return i_display;
	}


	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}

}
