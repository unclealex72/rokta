package uk.co.unclealex.rokta.gwt.client.view.side.gamefilter;

import java.util.Date;

import com.google.gwt.core.client.GWT;

public class DatePickerNames implements DatePickerNameMessages {

	private static final DatePickerNames INSTANCE = new DatePickerNames();
	
	public static final DatePickerNames getInstance() {
		return INSTANCE;
	}
	
	private DatePickerNameMessages i_datePickerNameMessages = GWT.create(DatePickerNameMessages.class);
	
	protected DatePickerNames() {
		// Default no args constructor
	}	
	
	public String before() {
		return getDatePickerNameMessages().before();
	}

	public String beforeFormat(Date date) {
		return getDatePickerNameMessages().beforeFormat(date);
	}	
	
	public String from() {
		return getDatePickerNameMessages().from();
	}

	public String fromFormat(Date date) {
		return getDatePickerNameMessages().fromFormat(date);
	}	
	
	public String toFormat(Date date) {
		return getDatePickerNameMessages().toFormat(date);
	}	
	
	public String since() {
		return getDatePickerNameMessages().since();
	}

	public String sinceFormat(Date date) {
		return getDatePickerNameMessages().sinceFormat(date);
	}	
	
	public String to() {
		return getDatePickerNameMessages().to();
	}

	public String week() {
		return getDatePickerNameMessages().week();
	}

	public String weekFormat(Date date) {
		return getDatePickerNameMessages().weekFormat(date);
	}	
	
	public String month() {
		return getDatePickerNameMessages().month();
	}

	public String monthFormat(Date date) {
		return getDatePickerNameMessages().monthFormat(date);
	}	
	
	protected DatePickerNameMessages getDatePickerNameMessages() {
		return i_datePickerNameMessages;
	}


}
