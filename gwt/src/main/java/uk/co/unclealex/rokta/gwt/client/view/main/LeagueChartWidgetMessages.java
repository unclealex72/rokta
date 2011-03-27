package uk.co.unclealex.rokta.gwt.client.view.main;

import com.google.gwt.i18n.client.Messages;

public interface LeagueChartWidgetMessages extends Messages {

	@DefaultMessage("{0,number,0.00}%")	
	public String datum(double datum);
	
}
