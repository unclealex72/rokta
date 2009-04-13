package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import java.util.Date;

import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

import com.google.code.p.gwtchismes.client.GWTCButton;
import com.google.code.p.gwtchismes.client.GWTCDatePicker;
import com.google.code.p.gwtchismes.client.GWTCSimpleDatePicker;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public abstract class DatePickerGameProducerPanel extends GameFilterProducerComposite implements ChangeListener {

	private DateFormatter[] i_dateFormatters;
	private	GWTCDatePicker[] i_datePickers;
	
	public DatePickerGameProducerPanel(
			RoktaAdaptor roktaAdaptor, Date initialDate, 
			Date earliestAllowedDate, Date latestAllowedDate,
			GameFilterProducerListener[] gameFilterProducerListeners, DateFormatter... dateFormatters) {
		super(roktaAdaptor, gameFilterProducerListeners);
		i_dateFormatters = dateFormatters;
		int length = dateFormatters.length;
		i_datePickers = new GWTCDatePicker[length];
		Panel panel = new VerticalPanel();
    panel.setWidth("100%");
		for (int idx = 0; idx < length; idx++) {
			GWTCDatePicker datePicker = createDatePicker(panel, dateFormatters[idx], initialDate, earliestAllowedDate, latestAllowedDate);
			i_datePickers[idx] = datePicker;
		}
		initWidget(panel);
	}

	protected GWTCDatePicker createDatePicker(
			Panel panel, final DateFormatter dateFormatter, Date initialDate, Date earliestAllowedDate, Date latestAllowedDate) {
		int options = GWTCDatePicker.CONFIG_DIALOG  
      | GWTCDatePicker.CONFIG_NO_HELP_BUTTON | GWTCDatePicker.CONFIG_BACKGROUND
      | GWTCDatePicker.CONFIG_LAYOUT_1;
		final GWTCDatePicker datePicker = new GWTCDatePicker(options);
		datePicker.setMinimalDate(earliestAllowedDate);
		datePicker.setMaximalDate(latestAllowedDate);
    final GWTCButton button = new GWTCButton();
    final Label label = new Label();
    button.setImageSrc("images/gwtc-calendar.gif");
    button.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          datePicker.show(button);
        }
    });
    ChangeListener listener = new ChangeListener() {
      public void onChange(Widget sender) {
      	changeLabel(label, dateFormatter, ((GWTCSimpleDatePicker) sender).getSelectedDate());
        datePicker.hide();
      }
    };
		datePicker.addChangeListener(listener);
    datePicker.addChangeListener(this);
		datePicker.setSelectedDate(initialDate);
		changeLabel(label, dateFormatter, initialDate);
    HorizontalPanel subPanel = new HorizontalPanel();
    subPanel.setWidth("100%");
    subPanel.add(label);
    subPanel.add(button);
    subPanel.setCellVerticalAlignment(label, HorizontalPanel.ALIGN_MIDDLE);
    subPanel.setCellVerticalAlignment(button, HorizontalPanel.ALIGN_MIDDLE);
    subPanel.setCellHorizontalAlignment(label, HorizontalPanel.ALIGN_LEFT);
    subPanel.setCellHorizontalAlignment(button, HorizontalPanel.ALIGN_RIGHT);
		panel.add(subPanel);
		return datePicker;
  }

	protected void changeLabel(Label label, DateFormatter dateFormatter, Date date) {
		label.setText(dateFormatter.prefix() + " " + dateFormatter.formatDate(date));
	}

	public void onChange(Widget sender) {
		setGameFilter(createGameFilter());
	}
	
	@Override
	protected GameFilter createGameFilter() {
		GWTCDatePicker[] datePickers = getDatePickers();
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
	
	protected GWTCDatePicker[] getDatePickers() {
		return i_datePickers;
	}

	public DateFormatter[] getDateFormatters() {
		return i_dateFormatters;
	}
}
