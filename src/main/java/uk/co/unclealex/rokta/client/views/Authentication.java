package uk.co.unclealex.rokta.client.views;

import uk.co.unclealex.rokta.client.presenters.AuthenticationPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Authentication extends Composite implements Display {

	@UiField Anchor signIn;
	@UiField Anchor signOut;
	@UiField Label currentUser;
	
  @UiTemplate("Authentication.ui.xml")
	public interface Binder extends UiBinder<Widget, Authentication> {
    // No extra methods
	}
	
	private static final Binder binder = GWT.create(Binder.class);

	public Authentication() {
		super();
		initWidget(binder.createAndBindUi(this));
	}

	@UiFactory
	public Anchor createAnchor() {
		return new Anchor(true);
	}
	
	public Anchor getSignIn() {
		return signIn;
	}

	public Anchor getSignOut() {
		return signOut;
	}

	public Label getCurrentUser() {
		return currentUser;
	}

}
