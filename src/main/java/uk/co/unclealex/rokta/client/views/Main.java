package uk.co.unclealex.rokta.client.views;

import javax.inject.Inject;

import uk.co.unclealex.hammers.calendar.client.views.Admin;
import uk.co.unclealex.hammers.calendar.client.views.Admin.Binder;
import uk.co.unclealex.rokta.client.presenters.MainPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class Main extends Composite implements Display {

  @UiTemplate("Main.ui.xml")
	public interface Binder extends UiBinder<Widget, Main> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	@Inject
	public Main() {
		initWidget(binder.createAndBindUi(this));
	}

}
