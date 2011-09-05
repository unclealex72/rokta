package uk.co.unclealex.rokta.client.presenters;

import javax.inject.Inject;

import uk.co.unclealex.rokta.client.presenters.AuthenticationPresenter.Display;
import uk.co.unclealex.rokta.client.security.AuthenticationEvent;
import uk.co.unclealex.rokta.client.security.AuthenticationEventListener;
import uk.co.unclealex.rokta.client.security.AuthenticationManager;
import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.client.util.ClickHandlerAndFailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.ExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.FailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;
import uk.co.unclealex.rokta.shared.service.UserRoktaServiceAsync;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;

public class AuthenticationPresenter implements Presenter<Display>, AuthenticationEventListener {

	public static interface Display extends IsWidget {
		Anchor getSignIn();
		Anchor getSignOut();
		Label getCurrentUser();
	}
	
	private final Display i_display;
	private final AsyncCallbackExecutor i_asyncCallbackExecutor;
	private final AuthenticationManager i_authenticationManager;
	
	@Inject
	public AuthenticationPresenter(Display display, AsyncCallbackExecutor asyncCallbackExecutor, AuthenticationManager authenticationManager) {
		super();
		i_display = display;
		i_asyncCallbackExecutor = asyncCallbackExecutor;
		i_authenticationManager = authenticationManager;
		authenticationManager.addAuthenticationEventListener(this);
	}


	@Override
	public void show(AcceptsOneWidget container) {
		prepareDisplay();
		final Display display = getDisplay();
		AsyncCallbackExecutor asyncCallbackExecutor = getAsyncCallbackExecutor();
		ClickHandler signInClickHandler = 
				new ClickHandlerAndFailureAsPopupExecutableAsyncCallback<Void>(asyncCallbackExecutor, "Signing in") {
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<Void> callback) {
				userRoktaService.forceSignIn(callback);
			}
		};
		display.getSignIn().addClickHandler(signInClickHandler);
		ClickHandler signOutClickHandler =
			new ClickHandlerAndFailureAsPopupExecutableAsyncCallback<Void>(asyncCallbackExecutor, "Signing out") {
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<Void> callback) {
				anonymousRoktaService.logout(callback);
				getAuthenticationManager().unauthenticated();
			}
		};
		display.getSignOut().addClickHandler(signOutClickHandler);
		container.setWidget(display);
	}

	@Override
	public void onAuthenticationChanged(AuthenticationEvent event) {
		prepareDisplay();
	}
	
	protected void prepareDisplay() {
		ExecutableAsyncCallback<String> callback = new FailureAsPopupExecutableAsyncCallback<String>() {
			@Override
			public void onSuccess(String username) {
				prepareDisplay(username);
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService, UserRoktaServiceAsync userRoktaService,
					AsyncCallback<String> callback) {
				anonymousRoktaService.getUserPrincipal(callback);
			}
		};
		getAsyncCallbackExecutor().executeAndWait(callback, "Finding who is currently logged in");
	}


	protected void prepareDisplay(String username) {
		Display display = getDisplay();
		Label currentUser = display.getCurrentUser();
		Anchor signIn = display.getSignIn();
		Anchor signOut = display.getSignOut();
		if (username == null) {
			currentUser.setVisible(false);
			signIn.setVisible(true);
			signOut.setVisible(false);
		}
		else {
			currentUser.setVisible(true);
			currentUser.setText(username);
			signIn.setVisible(false);
			signOut.setVisible(true);			
		}
	}


	public Display getDisplay() {
		return i_display;
	}


	public AsyncCallbackExecutor getAsyncCallbackExecutor() {
		return i_asyncCallbackExecutor;
	}


	public AuthenticationManager getAuthenticationManager() {
		return i_authenticationManager;
	}

}
