package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.TitlePresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class Title extends Composite implements Display {

  @UiTemplate("Title.ui.xml")
	public interface Binder extends UiBinder<Widget, Title> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField HasText mainTitle;
	
	@Inject
	public Title() {
		initWidget(binder.createAndBindUi(this));
	}

	public HasText getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(HasText mainTitle) {
		this.mainTitle = mainTitle;
	}


}
