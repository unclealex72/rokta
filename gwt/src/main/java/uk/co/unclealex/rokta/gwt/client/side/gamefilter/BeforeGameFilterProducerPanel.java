package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.filter.BeforeGameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

public class BeforeGameFilterProducerPanel extends DatePickerGameProducerPanel {

	public BeforeGameFilterProducerPanel(
			RoktaAdaptor roktaAdaptor, Date initialDate, Date earliestAllowedDate, Date latestAllowedDate, 
			GameFilterProducerListener... gameFilterProducerListeners) {
		super(
				roktaAdaptor, initialDate, earliestAllowedDate, latestAllowedDate,
				gameFilterProducerListeners,
				new DateFormatter() {
					public String prefix() {
						return DatePickerNames.getInstance().before();
					}
					public String formatDate(Date date) {
						return DatePickerNames.getInstance().beforeFormat(date);
					}
				});
	}

	@Override
	public GameFilter createGameFilter(Date[] dates) {
		return new BeforeGameFilter(dates[0]);
	}

	public void setBeforeDate(Date before) {
		getDatePickers()[0].setSelectedDate(before);
	}
}
