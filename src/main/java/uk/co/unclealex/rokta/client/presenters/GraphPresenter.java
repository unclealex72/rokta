package uk.co.unclealex.rokta.client.presenters;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import uk.co.unclealex.rokta.client.cache.InformationCache;
import uk.co.unclealex.rokta.client.filter.GameFilter;
import uk.co.unclealex.rokta.client.messages.TitleMessages;
import uk.co.unclealex.rokta.client.visualisation.Visualisation;
import uk.co.unclealex.rokta.client.visualisation.VisualisationDisplay;
import uk.co.unclealex.rokta.client.visualisation.VisualisationProvider;
import uk.co.unclealex.rokta.shared.model.CurrentInformation;
import uk.co.unclealex.rokta.shared.model.League;
import uk.co.unclealex.rokta.shared.model.LeagueRow;
import uk.co.unclealex.rokta.shared.model.Leagues;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class GraphPresenter extends InformationPresenter<Leagues> {

	public static interface Display extends VisualisationDisplay {
		void setColours(Iterable<String> colours);
	}

	private final TitleMessages i_titleMessages;
	private final Visualisation i_visualisation;
	private final Display i_display;

	@Inject
	public GraphPresenter(@Assisted GameFilter gameFilter, InformationCache informationCache, TitleMessages titleMessages,
			Visualisation visualisation, Display display) {
		super(gameFilter, informationCache);
		i_titleMessages = titleMessages;
		i_visualisation = visualisation;
		i_display = display;
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
				return createGraphDataTable(leagues);
			}
		};
		display.setColours(leagues.getHtmlColoursByName().values());
		getVisualisation().draw(display, visualisationProvider);
	}
	
	protected DataTable createGraphDataTable(Leagues leagues) {
		DataTable dataTable = DataTable.create();
		Set<String> playerNames = leagues.getHtmlColoursByName().keySet();
		Map<String, Integer> columnsByPlayer = new HashMap<String, Integer>();
		dataTable.addColumn(ColumnType.DATETIME);
		int column = 1;
		for (Iterator<String> iter = playerNames.iterator(); iter.hasNext(); ) {
			String playerName = iter.next();
			columnsByPlayer.put(playerName, column++);
			dataTable.addColumn(ColumnType.NUMBER, playerName);
		}
		SortedSet<League> allLeagues = leagues.getLeagues();
		for (League league : allLeagues) {
			int row = dataTable.addRow();
			dataTable.setValue(row, 0, league.getLastGameDate());
			for (LeagueRow leagueRow : league.getRows()) {
				String personName = leagueRow.getPersonName();
				dataTable.setValue(row, columnsByPlayer.get(personName), leagueRow.getLossesPerGame() * 100.0);
			}
		}
		return dataTable;
	}
	
	public TitleMessages getTitleMessages() {
		return i_titleMessages;
	}

	public Visualisation getVisualisation() {
		return i_visualisation;
	}

	public Display getDisplay() {
		return i_display;
	}
}
