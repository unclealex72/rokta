package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.Date;

import com.google.code.p.gwtchismes.client.GWTCButton;
import com.google.code.p.gwtchismes.client.GWTCDatePicker;
import com.google.code.p.gwtchismes.client.GWTCSimpleDatePicker;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

@SuppressWarnings("deprecation")
public class DatePicker extends Composite {

	private Label i_label;
	private DateFormatter i_dateFormatter;
	private GWTCDatePicker i_gwtcDatePicker;
	
	public DatePicker(int options, DateFormatter dateFormatter) {
    final Label label = new Label();
		final GWTCDatePicker datePicker = new GWTCDatePicker(options);
		i_dateFormatter = dateFormatter;
		i_label = label;
		i_gwtcDatePicker = datePicker;
    final GWTCButton button = new GWTCButton();
    button.setImageSrc("images/gwtc-calendar.gif");
    button.addClickListener(new ClickListener() {
        public void onClick(Widget sender) {
          datePicker.show(button);
        }
    });
    ChangeListener listener = new ChangeListener() {
      public void onChange(Widget sender) {
      	changeLabel(((GWTCSimpleDatePicker) sender).getSelectedDate());
        datePicker.hide();
      }
    };
		datePicker.addChangeListener(listener);
    HorizontalPanel panel = new HorizontalPanel();
    panel.setWidth("100%");
    panel.add(label);
    panel.add(button);
    panel.setCellVerticalAlignment(label, HorizontalPanel.ALIGN_MIDDLE);
    panel.setCellVerticalAlignment(button, HorizontalPanel.ALIGN_MIDDLE);
    panel.setCellHorizontalAlignment(label, HorizontalPanel.ALIGN_LEFT);
    panel.setCellHorizontalAlignment(button, HorizontalPanel.ALIGN_RIGHT);
    initWidget(panel);
	}

	protected void changeLabel(Date date) {
		DateFormatter dateFormatter = getDateFormatter();
		getLabel().setText(dateFormatter.prefix() + " " + dateFormatter.formatDate(date));
	}


	public void setMinimalDate(Date minimalDate) {
		getGwtcDatePicker().setMinimalDate(minimalDate);
	}

	public void setMaximalDate(Date maximalDate) {
		getGwtcDatePicker().setMaximalDate(maximalDate);
	}

	public void setSelectedDate(Date selectedDate) {
		getGwtcDatePicker().setSelectedDate(selectedDate);
		changeLabel(selectedDate);
	}

	public Date getSelectedDate() {
		return getGwtcDatePicker().getSelectedDate();
	}

	public void addChangeListener(ChangeListener listener) {
		getGwtcDatePicker().addChangeListener(listener);
	}

	protected Label getLabel() {
		return i_label;
	}

	protected DateFormatter getDateFormatter() {
		return i_dateFormatter;
	}

	protected GWTCDatePicker getGwtcDatePicker() {
		return i_gwtcDatePicker;
	}
}
