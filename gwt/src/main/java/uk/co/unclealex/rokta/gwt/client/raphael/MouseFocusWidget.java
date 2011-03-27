package uk.co.unclealex.rokta.gwt.client.raphael;

import uk.co.unclealex.rokta.gwt.client.controller.SourcesMouseEventsSupport;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.SourcesMouseEvents;

public class MouseFocusWidget extends FocusWidget implements SourcesMouseEvents {

	private SourcesMouseEventsSupport i_sourcesMouseEventsSupport = new SourcesMouseEventsSupport(this);

	public void addMouseListener(MouseListener listener) {
		getSourcesMouseEventsSupport().addMouseListener(listener);
	}

	public void removeMouseListener(MouseListener listener) {
		getSourcesMouseEventsSupport().removeMouseListener(listener);
	}

	@Override
	public void onBrowserEvent(Event event) {
		if (!getSourcesMouseEventsSupport().onBrowserEvent(event)) {
			super.onBrowserEvent(event);
		}
	}
	
	public SourcesMouseEventsSupport getSourcesMouseEventsSupport() {
		return i_sourcesMouseEventsSupport;
	}

}
