package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.ProfilePresenter.Display;
import uk.co.unclealex.rokta.shared.model.Colour;

import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class Profile extends Composite implements Display, IsWide {

  @UiTemplate("Profile.ui.xml")
	public interface Binder extends UiBinder<Widget, Profile> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	public interface Style extends CssResource {
		String dark();
		String light();
	}
	
	@UiField SimpleLayoutPanel handCountPanel;
	@UiField SimpleLayoutPanel openingHandCountPanel;
	@UiField HasText colourTitle;
	@UiField Label colour;
	@UiField Style style;
	
	@Inject
	public Profile() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void setColour(Colour colour) {
		Label colourWidget = getColour();
		colourWidget.setText(Joiner.on(' ').join(colour.getDescriptiveWords()));
		Style style = getStyle();
		colourWidget.addStyleName(colour.isDark()?style.dark():style.light());
		colourWidget.getElement().getStyle().setBackgroundColor(colour.getHtmlName());
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

	public Label getColour() {
		return colour;
	}

	public Style getStyle() {
		return style;
	}
}
