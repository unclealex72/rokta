package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.WeekGameFilter;

public class WeekGameFilterProducerPanel extends DatePickerGameProducerPanel {

	public WeekGameFilterProducerPanel(
			RoktaAdaptor roktaAdaptor, Date initialDate, Date earliestAllowedDate, Date latestAllowedDate, 
			GameFilterProducerListener... gameFilterProducerListeners) {
		super(
				roktaAdaptor, initialDate, earliestAllowedDate, latestAllowedDate,
				gameFilterProducerListeners,
				new DateFormatter() {
					public String prefix() {
						return DatePickerNames.getInstance().week();
					}
					public String formatDate(Date date) {
						return DatePickerNames.getInstance().weekFormat(date);
					}
				});
	}

	@Override
	public GameFilter createGameFilter(Date[] dates) {
		return new WeekGameFilter(dates[0]);
	}

	public void updateYearAndWeek(Date date) {
		getDatePickers()[0].setSelectedDate(date);
	}

}
