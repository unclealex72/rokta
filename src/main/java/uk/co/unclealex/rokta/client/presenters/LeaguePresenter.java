package uk.co.unclealex.rokta.client.presenters;

import java.util.List;
import java.util.SortedSet;

import uk.co.unclealex.rokta.client.cache.InformationCache;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.visualisation.Visualisation;
import uk.co.unclealex.rokta.client.visualisation.VisualisationDisplay;
import uk.co.unclealex.rokta.client.visualisation.VisualisationProvider;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.InfiniteInteger;
import uk.co.unclealex.rokta.shared.model.League;
import uk.co.unclealex.rokta.shared.model.LeagueRow;
import uk.co.unclealex.rokta.shared.model.Leagues;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class LeaguePresenter extends InformationPresenter<Leagues> {

	public static interface Display extends VisualisationDisplay {
		HasText getExemptPlayer();
	}

	private final Display i_display;
	private final Visualisation i_visualisation;
	private final TitleMessages i_titleMessages;
	
	@Inject
	public LeaguePresenter(
			@Assisted GameFilter gameFilter, Visualisation visualisation, 
			InformationCache informationCache, Display display,
			TitleMessages titleMessages) {
		super(gameFilter, informationCache);
		i_visualisation = visualisation;
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
		VisualisationProvider visualisationProvider = new VisualisationProvider() {
			@Override
			public DataTable createDataTable() {
				return createLeaguesDataTable(leagues);
			}
		};
		getVisualisation().draw(display, visualisationProvider);
	}

	protected DataTable createLeaguesDataTable(Leagues leagues) {
		DataTable dataTable = DataTable.create();
		//Player	Games	Rounds	Lost	R/WG	R/LG	L/G	Gap
		dataTable.addColumn(ColumnType.NUMBER, "", "leagueDelta");
		dataTable.addColumn(ColumnType.STRING, "Player", "leaguePlayer");
		dataTable.addColumn(ColumnType.NUMBER, "Games", "leagueGames");
		dataTable.addColumn(ColumnType.NUMBER, "Rounds", "leagueRounds");
		dataTable.addColumn(ColumnType.NUMBER, "Lost", "leagueLost");
		dataTable.addColumn(ColumnType.NUMBER, "R/WG", "leagueRwg");
		dataTable.addColumn(ColumnType.NUMBER, "R/LG", "leagueRlg");
		dataTable.addColumn(ColumnType.NUMBER, "L/G", "leagueLg");
		dataTable.addColumn(ColumnType.NUMBER, "Gap", "leagueGap");
		SortedSet<League> allLeagues = leagues.getLeagues();
		if (!allLeagues.isEmpty()) {
			League league = allLeagues.first();
			List<LeagueRow> rows = league.getRows();
			for (LeagueRow row : rows) {
				int rowIdx = dataTable.addRow();
				int colIdx = 0;
				dataTable.setValue(rowIdx, colIdx++, row.getDelta());
				dataTable.setValue(rowIdx, colIdx++, row.getPersonName());
				dataTable.setValue(rowIdx, colIdx++, row.getGamesPlayed());
				dataTable.setValue(rowIdx, colIdx++, row.getRoundsPlayed());
				dataTable.setValue(rowIdx, colIdx++, row.getGamesLost());
				double roundsPerWonGames = row.getRoundsPerWonGames();
				if (Double.compare(roundsPerWonGames, Double.NaN) != 0) {
					dataTable.setValue(rowIdx, colIdx++, roundsPerWonGames);
				}
				else {
					colIdx++;
				}
				double roundsPerLostGames = row.getRoundsPerLostGames();
				if (Double.compare(roundsPerLostGames, Double.NaN) != 0) {
					dataTable.setValue(rowIdx, colIdx++, roundsPerLostGames);
				}
				else {
					colIdx++;
				}
				dataTable.setValue(rowIdx, colIdx++, row.getLossesPerGame() * 100.0);
				InfiniteInteger gap = row.getGap();
				if (gap != null && !gap.isInfinite()) {
					dataTable.setValue(rowIdx, colIdx++, gap.getValue());
				}
				else {
					colIdx++;
				}
				rowIdx++;
			}
		}
		return dataTable;
	}

	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}

	public Display getDisplay() {
		return i_display;
	}

	public Visualisation getVisualisation() {
		return i_visualisation;
	}
}
