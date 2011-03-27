package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.gwt.client.view.date.DateFormatter;
import uk.co.unclealex.rokta.gwt.client.view.date.DatePickerNames;
import uk.co.unclealex.rokta.pub.filter.BeforeGameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

public class BeforeGameFilterProducerPanel extends DatePickerGameProducerPanel {

	public BeforeGameFilterProducerPanel(
			RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener... gameFilterProducerListeners) {
		super(
				roktaController, model, gameFilterProducerListeners,
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
