package uk.co.unclealex.rokta.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

public class ErrorHandler {

	public static final void log(Throwable t) {
		String message = t.getMessage();
		GWT.log(message, t);
		Window.alert("Error: " + message);
	}
}
