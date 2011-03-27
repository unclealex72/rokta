package uk.co.unclealex.rokta.gwt.client.view.factory;

import java.util.Collection;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.RoktaModel;
import uk.co.unclealex.rokta.gwt.client.view.side.DetailPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.NavigationPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.ProfilesPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.StatisticsPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.GameFilterWidget;

import com.google.gwt.user.client.ui.Widget;

public class SidePanelFactory extends ViewFactory {

	private GameFilterFactory i_gameFilterFactory;
	
	public SidePanelFactory(RoktaController roktaController, RoktaModel roktaModel) {
		super(roktaController, roktaModel);
		i_gameFilterFactory = new GameFilterFactory(roktaController, roktaModel);
	}

	public Widget createNavigationPanel() {
		NavigationPanel navigationPanel = new NavigationPanel(getRoktaController());
		navigationPanel.initialise();
		return navigationPanel;
	}

	public Widget createDetailPanel(Collection<String> playerNames) {
		RoktaController roktaController = getRoktaController();
		RoktaModel roktaModel = getRoktaModel();
		StatisticsPanel statisticsPanel = new StatisticsPanel(roktaController);
		statisticsPanel.initialise();
		ProfilesPanel profilesPanel = new ProfilesPanel(roktaController, playerNames);
		profilesPanel.initialise();
		GameFilterWidget gameFilterWidget = getGameFilterFactory().createGameFilterWidget();
		DetailPanel detailPanel = new DetailPanel(
			roktaController, roktaModel.getDetailPageModel(), gameFilterWidget, profilesPanel, statisticsPanel);
		detailPanel.initialise();
		return detailPanel;
	}

	public GameFilterFactory getGameFilterFactory() {
		return i_gameFilterFactory;
	}

}
