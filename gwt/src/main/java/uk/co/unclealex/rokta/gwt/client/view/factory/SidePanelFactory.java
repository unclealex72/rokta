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

	public SidePanelFactory(RoktaController roktaController, RoktaModel roktaModel) {
		super(roktaController, roktaModel);
	}

	public Widget createNavigationPanel() {
		return new NavigationPanel(getRoktaController());
	}

	public Widget createDetailPanel(Collection<String> playerNames) {
		RoktaController roktaController = getRoktaController();
		RoktaModel roktaModel = getRoktaModel();
		StatisticsPanel statisticsPanel = new StatisticsPanel(roktaController);
		ProfilesPanel profilesPanel = new ProfilesPanel(roktaController, playerNames);
		GameFilterWidget gameFilterWidget = new GameFilterWidget(roktaController, roktaModel.getInitialDatesModel());
		return new DetailPanel(
			roktaController, roktaModel.getDetailPageModel(), gameFilterWidget, profilesPanel, statisticsPanel);
	}

}
