package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.gwt.client.view.date.DateFormatter;
import uk.co.unclealex.rokta.gwt.client.view.date.DatePickerNames;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.filter.SinceGameFilter;

public class SinceGameFilterProducerPanel extends DatePickerGameProducerPanel {

	public SinceGameFilterProducerPanel(
			RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener... gameFilterProducerListeners) {
		super(
				roktaController, model, gameFilterProducerListeners,
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
