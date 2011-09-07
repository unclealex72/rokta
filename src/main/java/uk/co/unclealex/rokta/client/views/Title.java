package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.TitlePresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class Title extends Composite implements Display {

	public interface Style extends CssResource {
		String hiding();
	}
	
  @UiTemplate("Title.ui.xml")
	public interface Binder extends UiBinder<Widget, Title> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField HasText mainTitle;
	@UiField Image waitingImage;
	@UiField Style style;
	
	@Inject
	public Title() {
		initWidget(binder.createAndBindUi(this));
	}

	@Override
	public void hideWaitingImage() {
		//getWaitingImage().addStyleName(getStyle().hidingStyle());
	}
	
	@Override
	public void showWaitingImage() {
		getWaitingImage().removeStyleName(getStyle().hiding());
	}
	public HasText getMainTitle() {
		return mainTitle;
	}

	public Image getWaitingImage() {
		return waitingImage;
	}

	public Style getStyle() {
		return style;
	}


}
