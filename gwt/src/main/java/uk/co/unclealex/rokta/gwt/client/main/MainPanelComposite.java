package uk.co.unclealex.rokta.gwt.client.main;

import com.google.gwt.core.client.GWT;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.RoktaAwareComposite;
import uk.co.unclealex.rokta.gwt.client.side.NavigationMessages;
import uk.co.unclealex.rokta.gwt.client.side.StatisticsMessages;

public abstract class MainPanelComposite extends RoktaAwareComposite {

	private NavigationMessages i_navigationMessages = GWT.create(NavigationMessages.class);
	private StatisticsMessages i_statisticsMessages = GWT.create(StatisticsMessages.class);
	
	public MainPanelComposite(RoktaAdaptor roktaAdaptor) {
		super(roktaAdaptor);
	}

	public String createTitle() {
		return createTitle(getNavigationMessages(), getStatisticsMessages());
	}
	protected abstract String createTitle(NavigationMessages navigationMessages, StatisticsMessages statisticsMessages);

	public NavigationMessages getNavigationMessages() {
		return i_navigationMessages;
	}
	
	public StatisticsMessages getStatisticsMessages() {
		return i_statisticsMessages;
	}
}
