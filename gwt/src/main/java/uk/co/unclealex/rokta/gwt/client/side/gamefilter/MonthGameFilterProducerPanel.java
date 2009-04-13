package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.MonthGameFilter;

public class MonthGameFilterProducerPanel extends DatePickerGameProducerPanel {

	public MonthGameFilterProducerPanel(RoktaAdaptor roktaAdaptor, Date initialDate, Date earliestAllowedDate,
			Date latestAllowedDate, GameFilterProducerListener... gameFilterProducerListeners) {
		super(
				roktaAdaptor, initialDate, earliestAllowedDate, latestAllowedDate,
				gameFilterProducerListeners,
				new DateFormatter() {
					public String prefix() {
						return DatePickerNames.getInstance().month();
					}
					public String formatDate(Date date) {
						return DatePickerNames.getInstance().monthFormat(date);
					}
				});
	}

	@Override
	public GameFilter createGameFilter(Date[] dates) {
		return new MonthGameFilter(dates[0]);
	}

	public void updateYearAndMonth(Date date) {
		getDatePickers()[0].setSelectedDate(date);
	}
}
