/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.co.unclealex.rokta.client.cache;

import uk.co.unclealex.rokta.shared.model.CurrentInformation;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event thrown when the user has reached a new location in the app.
 */
public class CurrentInformationChangeEvent extends GwtEvent<CurrentInformationChangeEvent.Handler> {

  /**
   * Implemented by handlers of PlaceChangeEvent.
   */
  public interface Handler extends EventHandler {
    /**
     * Called when a {@link CurrentInformationChangeEvent} is fired.
     *
     * @param event the {@link CurrentInformationChangeEvent}
     */
    void onCurrentInformationChange(CurrentInformationChangeEvent event);
  }

  /**
   * A singleton instance of Type&lt;Handler&gt;.
   */
  public static final Type<Handler> TYPE = new Type<Handler>();

  private final CurrentInformation i_newCurrentInformation;

  public CurrentInformationChangeEvent(CurrentInformation newCurrentInformation) {
		super();
		i_newCurrentInformation = newCurrentInformation;
	}

	@Override
  public Type<Handler> getAssociatedType() {
    return TYPE;
  }

	public CurrentInformation getNewCurrentInformation() {
		return i_newCurrentInformation;
	}
	
  @Override
  protected void dispatch(Handler handler) {
    handler.onCurrentInformationChange(this);
  }
}
