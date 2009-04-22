package uk.co.unclealex.rokta.gwt.client.view.decoration;

import java.util.Date;

import com.google.gwt.i18n.client.Messages;

public interface CopyrightMessages extends Messages {

	@DefaultMessage("\u00a9 2006 - {0,date,yyyy} Alex Jones")
	public String getCopyright(Date date);
}
