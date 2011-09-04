package uk.co.unclealex.rokta.client.presenters;

import java.util.List;
import java.util.SortedSet;

import uk.co.unclealex.rokta.client.cache.InformationCache;
import uk.co.unclealex.rokta.client.factories.TitleFactory;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.model.Table;
import uk.co.unclealex.rokta.client.views.TableDisplay;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.League;
import uk.co.unclealex.rokta.shared.model.LeagueRow;
import uk.co.unclealex.rokta.shared.model.Leagues;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class LeaguePresenter extends InformationPresenter<Leagues> {

	public static interface Display extends TableDisplay {
		String EXEMPT = "exempt";
		String HEADER = "header";
		String NOT_PLAYING = "notplaying";
		
		HasText getExemptPlayer();
	}

	private final Display i_display;
	private final TitleMessages i_titleMessages;
	
	@Inject
	public LeaguePresenter(
			@Assisted GameFilter gameFilter,
			InformationCache informationCache, Display display,
			TitleMessages titleMessages) {
		super(gameFilter, informationCache);
		i_display = display;
		i_titleMessages = titleMessages;
	}

	@Override
	protected Leagues asSpecificInformation(CurrentInformation currentInformation) {
		return currentInformation.getLeagues();
	}
	
	@Override
	protected void show(GameFilter gameFilter, AcceptsOneWidget panel, final Leagues leagues) {
		Display display = getDisplay();
		panel.setWidget(display);
		final TitleMessages titleMessages = getTitleMessages();
		TitleFactory titleFactory = new TitleFactory() {
			public String createTitle(int row, int column) {
				if (row != 0) {
					LeagueRow leagueRow = leagues.getLeagues().first().getRows().get(row - 1);
					double oneMoreGamePlayed = (double) (leagueRow.getGamesPlayed() + 1) / 100.0;
					double winNext = (double) leagueRow.getGamesLost() / oneMoreGamePlayed;
					double loseNext = (double) (leagueRow.getGamesLost() + 1) / oneMoreGamePlayed;
					return titleMessages.nextGame(winNext, loseNext);
				}
				else { 
					return null;
				}
			}
		};
		display.draw(createLeaguesTable(leagues), titleFactory, null);
	}

	protected Table createLeaguesTable(Leagues leagues) {
		Table table = new Table();
		table.addRow(Display.HEADER, null, "Player", "Games", "Rounds", "Lost", "R/WG", "R/LG", "L/G", "Gap");
		SortedSet<League> allLeagues = leagues.getLeagues();
		if (!allLeagues.isEmpty()) {
			final League league = allLeagues.first();
			List<LeagueRow> leagueRows = league.getRows();
			for (LeagueRow leagueRow : leagueRows) {
				String typeMarker;
				if (!league.isCurrent()) {
					typeMarker = null;
				}
				else if (leagueRow.isExempt()) {
					typeMarker = Display.EXEMPT;
				}
				else if (!leagueRow.isPlayingToday()) {
					typeMarker = Display.NOT_PLAYING;
				}
				else {
					typeMarker = null;
				}
				table.addRow(
					typeMarker,
					leagueRow.getDelta(), 
					leagueRow.getPersonName(), 
					leagueRow.getGamesPlayed(), 
					leagueRow.getRoundsPlayed(), 
					leagueRow.getGamesLost(), 
					leagueRow.getRoundsPerWonGames(), 
					leagueRow.getRoundsPerLostGames(),
					leagueRow.getLossesPerGame(),
					leagueRow.getGap());
			}
		}
		return table;
	}

	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}

	public Display getDisplay() {
		return i_display;
	}
}
