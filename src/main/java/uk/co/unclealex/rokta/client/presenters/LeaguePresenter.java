package uk.co.unclealex.rokta.client.presenters;

import java.util.List;
import java.util.SortedSet;

import uk.co.unclealex.rokta.client.cache.InformationService;
import uk.co.unclealex.rokta.client.factories.TitleFactory;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.model.Table;
import uk.co.unclealex.rokta.client.presenters.LeaguePresenter.Display;
import uk.co.unclealex.rokta.client.views.TableDisplay;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.League;
import uk.co.unclealex.rokta.shared.model.LeagueRow;
import uk.co.unclealex.rokta.shared.model.Leagues;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class LeaguePresenter extends InformationActivity<Display, Leagues> {

	public static interface Display extends TableDisplay {
		String EXEMPT = "exempt";
		String NOT_PLAYING = "notplaying";
		
		HasText getExemptPlayer();
	}

	private final Display i_display;
	private final TitleMessages i_titleMessages;
	
	@Inject
	public LeaguePresenter(
			@Assisted GameFilter gameFilter,
			InformationService informationService, Display display,
			TitleMessages titleMessages) {
		super(gameFilter, informationService);
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
		TableAndExemptPlayer tableAndExemptPlayer = createLeaguesTable(leagues);
		display.draw(tableAndExemptPlayer.getTable(), titleFactory, null);
		String exemptPlayer = tableAndExemptPlayer.getExemptPlayer();
		String exemptMessage =  exemptPlayer==null?titleMessages.nooneExempt():titleMessages.exempt(exemptPlayer);
		display.getExemptPlayer().setText(exemptMessage);
		panel.setWidget(display);
	}

	protected TableAndExemptPlayer createLeaguesTable(Leagues leagues) {
		String exemptPlayer = null;
		final Table table = new Table();
		table.addRow(Display.HEADER, null, "Player", "Games", "Rounds", "Lost", "R/WG", "R/LG", "L/G", "Gap");
		SortedSet<League> allLeagues = leagues.getLeagues();
		if (!allLeagues.isEmpty()) {
			final League league = allLeagues.first();
			List<LeagueRow> leagueRows = league.getRows();
			for (LeagueRow leagueRow : leagueRows) {
				String typeMarker;
				String personName = leagueRow.getPersonName();
				if (!league.isCurrent()) {
					typeMarker = null;
				}
				else if (leagueRow.isExempt()) {
					typeMarker = Display.EXEMPT;
					exemptPlayer = personName;
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
					personName, 
					leagueRow.getGamesPlayed(), 
					leagueRow.getRoundsPlayed(), 
					leagueRow.getGamesLost(), 
					leagueRow.getRoundsPerWonGames(), 
					leagueRow.getRoundsPerLostGames(),
					leagueRow.getLossesPerGame(),
					leagueRow.getGap());
			}
		}
		final String finalExemptPlayer = exemptPlayer;
		return new TableAndExemptPlayer() {
			public Table getTable() { return table; }
			public String getExemptPlayer() { return finalExemptPlayer; }
		};
	}

	protected interface TableAndExemptPlayer {
		Table getTable();
		String getExemptPlayer();
	}
	
	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}

	public Display getDisplay() {
		return i_display;
	}
}
