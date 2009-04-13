/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.co.unclealex.rokta.gwt.client.decoration;

import uk.co.unclealex.rokta.gwt.client.GameFilterListener;
import uk.co.unclealex.rokta.gwt.client.RoktaAdaptor;
import uk.co.unclealex.rokta.gwt.client.RoktaAwareComposite;
import uk.co.unclealex.rokta.pub.filter.GameFilter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The top panel, which contains the 'welcome' message and various links.
 */
public class TitlePanel extends RoktaAwareComposite implements ClickHandler, GameFilterListener {

  /**
   * An image bundle for this widgets images.
   */
  public interface Images extends ImageBundle {
    AbstractImagePrototype logo();
  }

  private RoktaAdaptor i_roktaAdaptor;
  private HTML signOutLink = new HTML("<a href='javascript:;'>Sign Out</a>");
  private HTML aboutLink = new HTML("<a href='javascript:;'>About</a>");

  public TitlePanel(String id, RoktaAdaptor roktaAdaptor, Images images) {
  	super(roktaAdaptor);
  	roktaAdaptor.addGameFilterListener(this);
  	
    HorizontalPanel outer = new HorizontalPanel();
    VerticalPanel inner = new VerticalPanel();

    outer.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
    inner.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);

    HorizontalPanel links = new HorizontalPanel();
    links.setSpacing(4);
    links.add(signOutLink);
    links.add(aboutLink);

    final Image logo = images.logo().createImage();
    outer.add(logo);
    outer.setCellHorizontalAlignment(logo, HorizontalPanel.ALIGN_LEFT);

    outer.add(inner);
    inner.add(new HTML("<b>Welcome back, foo@example.com</b>"));
    inner.add(links);

    signOutLink.addClickHandler(this);
    aboutLink.addClickHandler(this);

    initWidget(id, outer);
    setStyleName("mail-TopPanel");
    links.setStyleName("mail-TopPanelLinks");
  }

  public void onGameFilterChange(GameFilter newGameFilter) {
  	// TODO Auto-generated method stub
  	
  }

  public void onClick(ClickEvent event) {
  	// TODO Auto-generated method stub
  	
  }

	public RoktaAdaptor getRoktaAdaptor() {
		return i_roktaAdaptor;
	}
}
