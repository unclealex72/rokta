package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.SinceGameFilter;

public class SinceGameFilterProducerPanel extends DatePickerGameProducerPanel {

	public SinceGameFilterProducerPanel(
			RoktaAdaptor roktaAdaptor, Date initialDate, Date earliestAllowedDate, Date latestAllowedDate,
			GameFilterProducerListener... gameFilterProducerListeners) {
		super(
				roktaAdaptor, initialDate, earliestAllowedDate, latestAllowedDate,
				gameFilterProducerListeners,
				new DateFormatter() {
					public String prefix() {
						return DatePickerNames.getInstance().since();
					}
					public String formatDate(Date date) {
						return DatePickerNames.getInstance().sinceFormat(date);
					}
				});
	}

	@Override
	public GameFilter createGameFilter(Date[] dates) {
		return new SinceGameFilter(dates[0]);
	}

	public void setSinceDate(Date since) {
		getDatePickers()[0].setSelectedDate(since);
	}

}
