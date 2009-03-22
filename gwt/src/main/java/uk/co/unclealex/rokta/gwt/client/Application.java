package uk.co.unclealex.rokta.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() { 
     RootPanel.get().add(new Label ("GWT-Maven Archetype Project - uk.co.unclealex.rokta.gwt"));
  }
}
