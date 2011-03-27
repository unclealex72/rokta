package uk.co.unclealex.rokta.gwt.client.controller;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerCollection;
import com.google.gwt.user.client.ui.SourcesMouseEvents;
import com.google.gwt.user.client.ui.Widget;

public class SourcesMouseEventsSupport implements SourcesMouseEvents {

  private MouseListenerCollection i_mouseListeners;
  private Widget i_widget;
  
  public SourcesMouseEventsSupport(Widget widget) {
		super();
		i_widget = widget;
	}

  public boolean onBrowserEvent(Event event) {
    if ((DOM.eventGetType(event) & Event.MOUSEEVENTS) != 0) {
      MouseListenerCollection mouseListeners = getMouseListeners();
			if (mouseListeners != null) {
        mouseListeners.fireMouseEvent(getWidget(), event);
      }
			return true;
    }
    return false;
  }

  public void addMouseListener(MouseListener listener) {
    MouseListenerCollection mouseListeners = getMouseListeners();
		if (mouseListeners == null) {
      setMouseListeners(new MouseListenerCollection());
      getWidget().sinkEvents(Event.MOUSEEVENTS);
    }
    getMouseListeners().add(listener);
  }

  public void removeMouseListener(MouseListener listener) {
    MouseListenerCollection mouseListeners = getMouseListeners();
		if (mouseListeners != null) {
      mouseListeners.remove(listener);
    }
  }
  
  protected MouseListenerCollection getMouseListeners() {
		return i_mouseListeners;
	}
  
  public void setMouseListeners(MouseListenerCollection mouseListeners) {
		i_mouseListeners = mouseListeners;
	}

	protected Widget getWidget() {
		return i_widget;
	}
}
