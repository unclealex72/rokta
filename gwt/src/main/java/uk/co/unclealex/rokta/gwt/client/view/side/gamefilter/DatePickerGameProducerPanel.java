package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.InitialDatesModel;
import uk.co.unclealex.rokta.pub.filter.GameFilter;
import uk.co.unclealex.rokta.pub.views.InitialDatesView;

import com.google.code.p.gwtchismes.client.GWTCDatePicker;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public abstract class DatePickerGameProducerPanel extends GameFilterProducerComposite implements ChangeListener {

	private DateFormatter[] i_dateFormatters;
	private	DatePicker[] i_datePickers;
	private boolean i_dateSelected;
	
	public DatePickerGameProducerPanel(
			RoktaController roktaController, InitialDatesModel model,
			GameFilterProducerListener[] gameFilterProducerListeners, DateFormatter... dateFormatters) {
		super(roktaController, model, gameFilterProducerListeners);
		i_dateFormatters = dateFormatters;
		int length = dateFormatters.length;
		i_datePickers = new DatePicker[length];
		Panel panel = new VerticalPanel();
    panel.setWidth("100%");
		for (int idx = 0; idx < length; idx++) {
			DatePicker datePicker = createDatePicker(panel, dateFormatters[idx]);
			i_datePickers[idx] = datePicker;
			panel.add(datePicker);
		}
		initWidget(panel);
	}

	protected DatePicker createDatePicker(Panel panel, final DateFormatter dateFormatter) {
		int options = GWTCDatePicker.CONFIG_DIALOG  
      | GWTCDatePicker.CONFIG_NO_HELP_BUTTON | GWTCDatePicker.CONFIG_BACKGROUND
      | GWTCDatePicker.CONFIG_LAYOUT_1;
		DatePicker datePicker = new DatePicker(options, dateFormatter);
		datePicker.addChangeListener(this);
		return datePicker;
  }

	public void onValueChanged(InitialDatesView value) {
		Date earliestDate = value.getEarliestDate();
		Date latestDate = value.getLatestDate();
		Date initialDate = value.getInitialDate();
		for (DatePicker datePicker : getDatePickers()) {
			datePicker.setMinimalDate(earliestDate);
			datePicker.setMaximalDate(latestDate);
			if (!isDateSelected()) {
				datePicker.setSelectedDate(initialDate);
			}
		}
		setDateSelected(true);
	}
	
	public void onChange(Widget sender) {
		setGameFilter(createGameFilter());
	}
	
	@Override
	protected GameFilter createGameFilter() {
		DatePicker[] datePickers = getDatePickers();
		int length = datePickers.length;
		Date[] dates = new Date[length];
		for (int idx = 0; idx < length; idx++) {
			Date selectedDate = datePickers[idx].getSelectedDate();
			if (selectedDate == null) {
				return null;
			}
			else {
				dates[idx] = selectedDate;
			}
		}
		return createGameFilter(dates);
	}
	
	public abstract GameFilter createGameFilter(Date[] dates);
	
	protected DatePicker[] getDatePickers() {
		return i_datePickers;
	}

	public DateFormatter[] getDateFormatters() {
		return i_dateFormatters;
	}

	protected boolean isDateSelected() {
		return i_dateSelected;
	}

	protected void setDateSelected(boolean dateSelected) {
		i_dateSelected = dateSelected;
	}
}
