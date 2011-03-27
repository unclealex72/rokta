package uk.co.unclealex.rokta.gwt.client.view.date;

import java.util.Date;

public interface DateFormatter {

	public String prefix();
	public String formatDate(Date date);
}
