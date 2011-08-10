/**
 * 
 */
package uk.co.unclealex.rokta.client.presenters;

import uk.co.unclealex.rokta.client.presenters.LoginPresenter.Display;
import uk.co.unclealex.rokta.client.security.AuthenticationManager;
import uk.co.unclealex.rokta.client.util.ClickHelper;
import uk.co.unclealex.rokta.shared.service.AnonymousRoktaServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
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
		public HasText getUsername();
		public Button getLogin();
		public Button getCancel();
		public Label getFailureLabel();
	}

	private final Display i_display;
	private final AnonymousRoktaServiceAsync i_anonymousRoktaService;
	private final AuthenticationManager i_authenticationManager;
	private final Runnable i_originalAction;
	private final ClickHelper i_clickHelper;
	
	public LoginPresenter(
			Display display, AnonymousRoktaServiceAsync anonymousRoktaService, 
			AuthenticationManager authenticationManager, ClickHelper clickHelper, Runnable originalAction) {
		super();
		i_display = display;
		i_anonymousRoktaService = anonymousRoktaService;
		i_authenticationManager = authenticationManager;
		i_originalAction = originalAction;
		i_clickHelper = clickHelper;
	}

	
	@Override
	public void prepare(final Display display) {
		ClickHandler loginHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				authenticate(display.getUsername().getText(), display.getPassword().getText());
			}
		};
		display.getLogin().addClickHandler(loginHandler);
		ClickHandler cancelHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
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
}
