/**
 * 
 */
package uk.co.unclealex.rokta.client.presenters;

import uk.co.unclealex.rokta.client.presenters.LoginPresenter.Display;
import uk.co.unclealex.rokta.client.security.AuthenticationManager;
import uk.co.unclealex.rokta.client.util.AsyncCallbackExecutor;
import uk.co.unclealex.rokta.client.util.ClickHelper;
import uk.co.unclealex.rokta.client.util.ExecutableAsyncCallback;
import uk.co.unclealex.rokta.client.util.FailureAsPopupExecutableAsyncCallback;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;
import uk.co.unclealex.rokta.shared.service.UserRoktaServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Copyright 2011 Alex Jones
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 *
 * @author unclealex72
 *
 */
public class LoginPresenter extends AbstractPopupPresenter<PopupPanel, Display> {

	public static interface Display extends AbstractPopupPresenter.Display<PopupPanel> {
		public HasText getPassword();
		public ListBox getUsername();
		public Button getLogin();
		public Button getCancel();
		public Label getFailureLabel();
	}

	private final Display i_display;
	private final AnonymousRoktaServiceAsync i_anonymousRoktaService;
	private final AuthenticationManager i_authenticationManager;
	private final AsyncCallbackExecutor i_asyncCallbackExecutor;
	private final Runnable i_originalAction;
	private final Runnable i_cancelAction;
	private final ClickHelper i_clickHelper;
	
	public LoginPresenter(
			Display display, AnonymousRoktaServiceAsync anonymousRoktaService, AsyncCallbackExecutor asyncCallbackExecutor,
			AuthenticationManager authenticationManager, ClickHelper clickHelper, Runnable originalAction, Runnable cancelAction) {
		super();
		i_display = display;
		i_anonymousRoktaService = anonymousRoktaService;
		i_authenticationManager = authenticationManager;
		i_originalAction = originalAction;
		i_clickHelper = clickHelper;
		i_asyncCallbackExecutor = asyncCallbackExecutor;
		i_cancelAction = cancelAction;
	}

	@Override
	protected void prepare(final Display display) {
		ExecutableAsyncCallback<String[]> callback = new FailureAsPopupExecutableAsyncCallback<String[]>() {
			@Override
			public void onSuccess(String[] usernames) {
				prepare(display, usernames);
			}
			@Override
			public void execute(AnonymousRoktaServiceAsync anonymousRoktaService,
					UserRoktaServiceAsync userRoktaService, AsyncCallback<String[]> callback) {
				anonymousRoktaService.getAllUsersNames(callback);
			}
		};
		getAsyncCallbackExecutor().executeAndWait(callback, "Getting a list of all usernames");
	}
	
	protected void prepare(final Display display, String[] usernames) {
		final ListBox usernameListBox = display.getUsername();
		for (String username : usernames) {
			usernameListBox.addItem(username);
		}
		ClickHandler loginHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String username = usernameListBox.getValue(usernameListBox.getSelectedIndex());
				authenticate(username, display.getPassword().getText());
			}
		};
		display.getLogin().addClickHandler(loginHandler);
		ClickHandler cancelHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
				Runnable cancelAction = getCancelAction();
				if (cancelAction != null) {
					cancelAction.run();
				}
			}
		};
		display.getCancel().addClickHandler(cancelHandler);
		display.getFailureLabel().setVisible(false);
		getClickHelper().clickOnReturnKeyPressed(display.getPassword(), display.getLogin());
	}
	
	protected void authenticate(final String username, String password) {
    final Display display = getDisplay();
		final Button login = display.getLogin();
		login.setEnabled(false);
		AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean authenticated) {
				login.setEnabled(true);
				if (authenticated) {
					hide();
					getAuthenticationManager().authenticated(username);
					getOriginalAction().run();
				}
				else {
          display.getFailureLabel().setVisible(true);
					getAuthenticationManager().unauthenticated();
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				login.setEnabled(true);
				Window.alert("There was an error whilst logging in. Please try again.");
			}
		};
		getAnonymousRoktaService().authenticate(username, password, callback );
	}

	
	public Display getDisplay() {
		return i_display;
	}


	public AnonymousRoktaServiceAsync getAnonymousRoktaService() {
		return i_anonymousRoktaService;
	}

	public AuthenticationManager getAuthenticationManager() {
		return i_authenticationManager;
	}

	public Runnable getOriginalAction() {
		return i_originalAction;
	}


  public ClickHelper getClickHelper() {
    return i_clickHelper;
  }

	public AsyncCallbackExecutor getAsyncCallbackExecutor() {
		return i_asyncCallbackExecutor;
	}

	public Runnable getCancelAction() {
		return i_cancelAction;
	}
}
