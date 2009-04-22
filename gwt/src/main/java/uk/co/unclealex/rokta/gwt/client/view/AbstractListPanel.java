package uk.co.unclealex.rokta.gwt.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractListPanel extends ComplexPanel {
	
  public AbstractListPanel() {
  	setElement(DOM.createElement(getListElementName()));
  }

  protected abstract String getListElementName();

	public void add(Widget w) {
  	super.add(w, getElement());
  }

  public void insert(Widget w, int beforeIndex) {
  	super.insert(w, getElement(), beforeIndex, true);
  }
}
