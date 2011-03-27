package uk.co.unclealex.rokta.gwt.client.view.decoration;

import com.google.gwt.i18n.client.Messages;

public interface TitleMessages extends Messages {

	@DefaultMessage("{0} {1}")
	public String appendGameFilterDescription(String prefix, String gameFilterDescription);
}
