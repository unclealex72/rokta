package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.ProfilePresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class Profile extends Composite implements Display, IsWide {

  @UiTemplate("Profile.ui.xml")
	public interface Binder extends UiBinder<Widget, Profile> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField SimpleLayoutPanel handCountPanel;
	@UiField SimpleLayoutPanel openingHandCountPanel;
	@UiField HasText colourTitle;
	
	@Inject
	public Profile() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void setColour(String htmlColour) {
		// TODO Auto-generated method stub
		
	}

	public SimpleLayoutPanel getHandCountPanel() {
		return handCountPanel;
	}

	public SimpleLayoutPanel getOpeningHandCountPanel() {
		return openingHandCountPanel;
	}

	public HasText getColourTitle() {
		return colourTitle;
	}
}
