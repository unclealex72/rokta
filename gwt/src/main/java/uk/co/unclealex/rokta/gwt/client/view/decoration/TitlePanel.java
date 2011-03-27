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
package uk.co.unclealex.rokta.gwt.client.view.decoration;

import uk.co.unclealex.rokta.gwt.client.controller.RoktaController;
import uk.co.unclealex.rokta.gwt.client.model.TitleModel;
import uk.co.unclealex.rokta.gwt.client.view.LoadingAwareComposite;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The top panel, which contains the 'welcome' message and various links.
 */
public class TitlePanel extends LoadingAwareComposite<String, HorizontalPanel> implements ClickListener {

  /**
   * An image bundle for this widgets images.
   */
  public interface Images extends ImageBundle {
    AbstractImagePrototype logo();
  }

  private HTML i_signOutLink = new HTML("<a href='javascript:;'>Sign Out</a>");
  private HTML i_aboutLink = new HTML("<a href='javascript:;'>About</a>");

  private HorizontalPanel i_linksPanel;
  private Images i_images;
  
  public TitlePanel(RoktaController roktaController, TitleModel titleModel, Images images) {
  	super(roktaController, titleModel);
  	setImages(images);
  }

  @Override
  protected HorizontalPanel create() {
    HorizontalPanel outer = new HorizontalPanel();
    VerticalPanel inner = new VerticalPanel();

    outer.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
    inner.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);

    HorizontalPanel links = new HorizontalPanel();
    links.setSpacing(4);
    links.add(getSignOutLink());
    links.add(getAboutLink());
    setLinksPanel(links);
    
    final Image logo = getImages().logo().createImage();
    outer.add(logo);
    outer.setCellHorizontalAlignment(logo, HorizontalPanel.ALIGN_LEFT);

    outer.add(inner);
    inner.add(new HTML("<b>Welcome back, foo@example.com</b>"));
    inner.add(links);

    getSignOutLink().addClickListener(this);
    getAboutLink().addClickListener(this);
    return outer;
  }
  
  @Override
  protected void postCreate(HorizontalPanel horizontalPanel) {
    setStyleName("mail-TopPanel");
    getLinksPanel().setStyleName("mail-TopPanelLinks");
  }
  
  public void onValueChanged(String title) {
  	// TODO Auto-generated method stub  	
  }
  
  public void onClick(Widget source) {
  	// TODO Auto-generated method stub
  	
  }

	public HorizontalPanel getLinksPanel() {
		return i_linksPanel;
	}

	protected void setLinksPanel(HorizontalPanel linksPanel) {
		i_linksPanel = linksPanel;
	}

	public Images getImages() {
		return i_images;
	}

	protected void setImages(Images images) {
		i_images = images;
	}

	public HTML getSignOutLink() {
		return i_signOutLink;
	}

	public HTML getAboutLink() {
		return i_aboutLink;
	}
}
