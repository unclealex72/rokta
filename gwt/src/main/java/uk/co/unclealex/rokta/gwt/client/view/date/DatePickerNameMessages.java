package uk.co.unclealex.rokta.gwt.client.view.date;

import java.util.Date;

import com.google.gwt.i18n.client.Messages;

public interface DatePickerNameMessages extends Messages {

	@DefaultMessage("Before")
	public String before();
	@DefaultMessage("{0,date,EEE dd MMM yyyy}")
	public String beforeFormat(Date date);
	
	@DefaultMessage("Since")
	public String since();
	@DefaultMessage("{0,date,EEE dd MMM yyyy}")
	public String sinceFormat(Date date);	
	
	@DefaultMessage("From")
	public String from();	
	@DefaultMessage("{0,date,EEE dd MMM yyyy}")
	public String fromFormat(Date date);	

	@DefaultMessage("To")
	public String to();
	@DefaultMessage("{0,date,EEE dd MMM yyyy}")
	public String toFormat(Date date);	

	@DefaultMessage("Week")
	public String week();
	@DefaultMessage("{0,date,w yyyy}")
	public String weekFormat(Date date);	

	@DefaultMessage("Month")
	public String month();
	@DefaultMessage("{0,date,MMM yyyy}")
	public String monthFormat(Date date);

	@DefaultMessage("Date Played")
	public String datePlayed();
	@DefaultMessage("{0,date,EEE dd MMM yyyy HH:mm}")
	public String datePlayedFormat(Date date);
}
