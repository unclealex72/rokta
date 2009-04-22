package uk.co.unclealex.rokta.gwt.client.view.side;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class HeaderElement extends Widget implements HasText {

  public HeaderElement(int level) {
  	this(null, level);
  }

  public HeaderElement(String text, int level) {
  	setElement(DOM.createElement("h" + level));
  	setText(text);
  }

  public String getText() {
    return DOM.getInnerText(getElement());
  }

  public void setText(String text) {
  	DOM.setInnerText(getElement(), (text == null) ? "" : text);
  }

}
