/**
 * Copyright 2012 Alex Jones
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
 * @author alex
 *
 */

package uk.co.unclealex.googleauth;

import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.oauth2.model.Userinfo;

/**
 * An interface for classes that provide local user information to the
 * {@link GoogleOauthFilter}.
 * 
 * @author alex
 * 
 */
public interface UserManager {

  /**
   * Get the list of Gmail addresses assoicated with the users who are allowed
   * to log in.
   * 
   * @return The list of Gmail addresses assoicated with the users who are
   *         allowed to log in or <code>null</code> if anyone can log in.
   */
  public List<String> getValidGmailAddresses();
  
  /**
   * Indicate whether a user with the given Gmail address is an the given role.
   * @param gmailAddress The Gmail address of the user.
   * @param roleName The role to check for.
   * @return True if the user is in the role or false otherwise.
   */
  public boolean isUserInRole(String gmailAddress, String roleName);
  
  /**
   * Create a new user due to a previously unknown user logging in.
   * @param gmailAddress The Gmail address of the new user.
   * @param userinfo The Google {@link Userinfo} for the new user.
   * @param credential A Google {@link Credential} for the new user.
   */
  public void createNewUserIfRequired(String gmailAddress, Userinfo userinfo, Credential credential);
}
