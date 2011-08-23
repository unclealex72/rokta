package uk.co.unclealex.rokta.client.presenters;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DatePickingLink implements ClickHandler {
	private final Anchor i_anchor;
	private Date i_earliestDate;
	private Date i_latestDate;
	private Date i_date;
	private final HandlerRegistration i_handlerRegistration;
	private final HasValue<Boolean> i_selection;
	private final DatePickingLink i_datePickingLinkToUpdate;
	
	public DatePickingLink(Anchor anchor, Date earliestDate, Date latestDate, Date initialDate, HasValue<Boolean> selection) {
		this(anchor, earliestDate, latestDate, initialDate, selection, null);
	}
	
	public DatePickingLink(
			Anchor anchor, Date earliestDate, Date latestDate, Date initialDate, 
			HasValue<Boolean> selection, DatePickingLink datePickingLinkToUpdate) {
		super();
		i_anchor = anchor;
		setDateAndDisplay(initialDate);
		i_latestDate = latestDate;
		i_earliestDate = earliestDate;
		i_selection = selection;
		i_datePickingLinkToUpdate = datePickingLinkToUpdate;
		i_handlerRegistration = anchor.addClickHandler(this);
	}
	
	@Override
	public void onClick(ClickEvent event) {
		final PopupPanel panel = new PopupPanel(true, false);
		DatePicker datePicker = new DatePicker();
		datePicker.setValue(getDate());
		panel.setWidget(datePicker);
		panel.showRelativeTo(getAnchor());
		ValueChangeHandler<Date> valueChangeHandler = new ValueChangeHandler<Date>() {
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date date = event.getValue();
				if (
					CalendarUtil.getDaysBetween(date, getEarliestDate()) <= 0 && 
					CalendarUtil.getDaysBetween(date, getLatestDate()) >= 0) {
					setDateAndDisplay(date);
					panel.hide();
					DatePickingLink datePickingLinkToUpdate = getDatePickingLinkToUpdate();
					if (datePickingLinkToUpdate != null) {
						updateOnClose(datePickingLinkToUpdate, date);
					}
					getSelection().setValue(true);
				}
			}
		};
		datePicker.addValueChangeHandler(valueChangeHandler);
	}
	
	protected void updateOnClose(DatePickingLink datePickingLink, Date date) {
		// Do nothing
	}

	public void remove() {
		getHandlerRegistration().removeHandler();
	}
	
	protected void setDateAndDisplay(Date date) {
		setDate(date);
		getAnchor().setText(DateTimeFormat.getFormat("dd/MM/yyyy").format(date));
	}
	
	public Date getDate() {
		return i_date;
	}
	
	public void setDate(Date date) {
		i_date = date;
	}

	public Anchor getAnchor() {
		return i_anchor;
	}

	public DatePickingLink getDatePickingLinkToUpdate() {
		return i_datePickingLinkToUpdate;
	}
	
	public Date getEarliestDate() {
		return i_earliestDate;
	}

	public void setEarliestDate(Date earliestDate) {
		i_earliestDate = earliestDate;
	}

	public Date getLatestDate() {
		return i_latestDate;
	}

	public void setLatestDate(Date latestDate) {
		i_latestDate = latestDate;
	}

	public HandlerRegistration getHandlerRegistration() {
		return i_handlerRegistration;
	}
	
	public HasValue<Boolean> getSelection() {
		return i_selection;
	}
}