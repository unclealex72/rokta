package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.gwt.client.view.date.DateFormatter;
import uk.co.unclealex.rokta.gwt.client.view.date.DatePickerNames;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.WeekGameFilter;

public class WeekGameFilterProducerPanel extends DatePickerGameProducerPanel {

	public WeekGameFilterProducerPanel(
			RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener... gameFilterProducerListeners) {
		super(
				roktaController, model, gameFilterProducerListeners,
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
