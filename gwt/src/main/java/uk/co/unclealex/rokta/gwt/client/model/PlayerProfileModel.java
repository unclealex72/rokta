package uk.co.unclealex.rokta.gwt.client.model;

public class PlayerProfileModel extends CompositeModel {

	private HandDistributionChartModel i_allHandDistributionChartModel;
	private HandDistributionChartModel i_openingHandDistributionChartModel;
	
	public PlayerProfileModel() {
		i_allHandDistributionChartModel = new HandDistributionChartModel();
		i_openingHandDistributionChartModel = new HandDistributionChartModel();
		add(i_allHandDistributionChartModel);
		add(i_openingHandDistributionChartModel);
	}
	
	public HandDistributionChartModel getAllHandDistributionChartModel() {
		return i_allHandDistributionChartModel;
	}
	
	public HandDistributionChartModel getOpeningHandDistributionChartModel() {
		return i_openingHandDistributionChartModel;
	}
	
}
