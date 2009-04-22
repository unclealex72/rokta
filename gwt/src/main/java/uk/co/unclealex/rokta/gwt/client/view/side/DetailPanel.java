package uk.co.unclealex.rokta.gwt.client.view.side;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.DetailPageEnum;
import uk.co.unclealex.rokta.gwt.client.model.DetailPageModel;
import uk.co.unclealex.rokta.gwt.client.util.MapMaker;
import uk.co.unclealex.rokta.gwt.client.view.main.ModelAwareDeckPanel;
import uk.co.unclealex.rokta.gwt.client.view.side.gamefilter.GameFilterWidget;

import com.google.gwt.user.client.ui.Widget;

public class DetailPanel extends ModelAwareDeckPanel<DetailPageEnum> {

	public DetailPanel(
			RoktaController roktaController, DetailPageModel detailPageModel,
			GameFilterWidget gameFilterWidget, ProfilesPanel profilesPanel, StatisticsPanel statisticsPanel) {
		super(
			roktaController, detailPageModel, "detail", 
			new MapMaker<DetailPageEnum, Widget>()
				.add(DetailPageEnum.FILTERS, gameFilterWidget)
				.add(DetailPageEnum.PROFILES, profilesPanel)
				.add(DetailPageEnum.STATISTICS, statisticsPanel)
			);
	}
}
