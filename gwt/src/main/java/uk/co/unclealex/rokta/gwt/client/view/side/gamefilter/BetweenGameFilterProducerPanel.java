package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.pub.filter.BetweenGameFilter;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

public class BetweenGameFilterProducerPanel extends DatePickerGameProducerPanel {

	public BetweenGameFilterProducerPanel(
			RoktaController roktaController, InitialDatesModel model, GameFilterProducerListener... gameFilterProducerListeners) {
		super(
				roktaController, model, gameFilterProducerListeners,
				new DateFormatter() {
					public String prefix() {
						return DatePickerNames.getInstance().from();
					}
					public String formatDate(Date date) {
						return DatePickerNames.getInstance().fromFormat(date);
					}
				},
				new DateFormatter() {
					public String prefix() {
						return DatePickerNames.getInstance().to();
					}
					public String formatDate(Date date) {
						return DatePickerNames.getInstance().toFormat(date);
					}
				});
	}

	@Override
	public GameFilter createGameFilter(Date[] dates) {
		if (dates[0].compareTo(dates[1]) > 0) {
			return null;
		}
		else {
			return new BetweenGameFilter(dates[0], dates[1]);
		}
	}

	public void setFromDate(Date from) {
		getDatePickers()[0].setSelectedDate(from);
	}

	public void setToDate(Date to) {
		getDatePickers()[1].setSelectedDate(to);
	}

}
