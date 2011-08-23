package uk.co.unclealex.rokta.client.views;

import uk.co.unclealex.rokta.client.presenters.StreaksPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.Widget;

public class Streaks extends Composite implements Display {

  @UiTemplate("Streaks.ui.xml")
	public interface Binder extends UiBinder<Widget, Streaks> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@UiField HasOneWidget allStreaksPanel;
	@UiField HasOneWidget currentStreaksPanel;
	
	public Streaks() {
		initWidget(binder.createAndBindUi(this));
	}

	public HasOneWidget getAllStreaksPanel() {
		return allStreaksPanel;
	}

	public HasOneWidget getCurrentStreaksPanel() {
		return currentStreaksPanel;
	}


}
