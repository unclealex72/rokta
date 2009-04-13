package uk.co.unclealex.rokta.gwt.client.side.gamefilter;

import java.util.Date;

import com.google.gwt.i18n.client.Messages;

public interface MonthNameMessages extends Messages {

	@DefaultMessage("{0,date,MMMM}")
	public String month(Date date);
}
