package uk.co.unclealex.rokta.gwt.client.main;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.ErrorHandler;
import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.RoktaAwareComposite;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.InfiniteInteger;
import uk.co.unclealex.rokta.pub.views.League;
import uk.co.unclealex.rokta.pub.views.LeagueRow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;

public class LeagueWidget extends RoktaAwareComposite implements AsyncCallback<League> {

	private Grid i_grid = new Grid();
	private LeagueWidgetMessages i_messages = GWT.create(LeagueWidgetMessages.class);
	
	protected LeagueWidget(RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
		initWidget(getGrid());
	}

	public void onGameFilterChange(GameFilter newGameFilter) {
		getRoktaAdaptor().getLeague(new Date(), this);
	}

	public void onFailure(Throwable t) {
		ErrorHandler.log(t);
	}
	
	public void onSuccess(League league) {
		LeagueWidgetMessages messages = getMessages();
		Grid grid = getGrid();
		boolean current = league.isCurrent();
		grid.resize(league.getRows().size() + 1,8 + (current?1:0));
		String[] headers = 
			new String[] {
				"", messages.playerTitle(), messages.gamesTitle(), messages.roundsTitle(), messages.lostTitle(), 
				messages.roundsPerWonGameTitle(), messages.roundsPerLostGameTitle(), messages.lossesPerGameTitle()
			};
		int column;
		for (column = 0; column < headers.length; column++) {
			grid.setText(0, column, headers[column]);
		}
		if (current) {
			grid.setText(0, column, messages.gamesTitle());
		}
		int row = 1;
		for (LeagueRow leagueRow : league.getRows()) {
			column = 0;

			grid.setText(row, column++, leagueRow.getDelta().name());
			grid.setText(row, column++, messages.playerFormat(leagueRow.getPersonName()));
			grid.setText(row, column++, messages.gamesFormat(leagueRow.getGamesPlayed()));
			grid.setText(row, column++, messages.roundsFormat(leagueRow.getRoundsPlayed()));
			grid.setText(row, column++, messages.gamesFormat(leagueRow.getGamesLost()));
			String roundsPerWonGame;
			if (leagueRow.getGamesWon() == 0) {
				roundsPerWonGame = "-";
			}
			else {
				roundsPerWonGame = messages.roundsPerWonGameFormat(leagueRow.getRoundsPerWonGames());
			}
			grid.setText(row, column++, roundsPerWonGame);
			String roundsPerLostGame;
			if (leagueRow.getGamesLost() == 0) {
				roundsPerLostGame = "-";
			}
			else {
				roundsPerLostGame = messages.roundsPerLostGameFormat(leagueRow.getRoundsPerLostGames());
			}
			grid.setText(row, column++, roundsPerLostGame);
			grid.setText(row, column++, messages.lossesPerGameFormat(leagueRow.getLossesPerGame()));
			if (current) {
				InfiniteInteger gap = leagueRow.getGap();
				String gapview = (gap==null || gap.isInfinite())?"":messages.gapFormat(gap.getValue());
				grid.setText(row, column++, gapview);
			}
			row++;
		}
	}
	
	public Grid getGrid() {
		return i_grid;
	}

	public LeagueWidgetMessages getMessages() {
		return i_messages;
	}

}
