package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.MonthGameFilter;

public class MonthGameFilterProducerPanel extends DatePickerGameProducerPanel {

	public MonthGameFilterProducerPanel(
			RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener... gameFilterProducerListeners) {
		super(
				roktaController, model, gameFilterProducerListeners,
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
