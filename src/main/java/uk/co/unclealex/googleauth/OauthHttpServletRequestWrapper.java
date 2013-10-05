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

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * An {@link HttpServletRequestWrapper} that adds Google authentication information to a {@link HttpServletRequest}.
 * 
 * @author alex
 */
public class OauthHttpServletRequestWrapper extends HttpServletRequestWrapper {

  /**
   * The {@link OauthPrincipal} who is logged in or <code>null</code> if no user is logged in.
   */
  private final OauthPrincipal oauthPrincipal;
  
  /**
   * The {@link UserManager} used to get role information.
   */
  private final UserManager userManager;

  /**
   * Instantiates a new google oauth http servlet request wrapper.
   * 
   * @param httpServletRequest
   *          the http servlet request
   * @param oauthPrincipal
   *          the oauth principal
   * @param userManager
   *          the user manager
   */
  public OauthHttpServletRequestWrapper(
      HttpServletRequest httpServletRequest,
      OauthPrincipal oauthPrincipal,
      UserManager userManager) {
    super(httpServletRequest);
    this.oauthPrincipal = oauthPrincipal;
    this.userManager = userManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Principal getUserPrincipal() {
    return getOauthPrincipal();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isUserInRole(String roleName) {
    OauthPrincipal oauthPrincipal = getOauthPrincipal();
    if (oauthPrincipal == null) {
      return false;
    }
    return getUserManager().isUserInRole(oauthPrincipal.getName(), roleName);
  }


  /**
   * Gets the {@link OauthPrincipal} who is logged in or <code>null</code> if no
   * user is logged in.
   * 
   * @return the {@link OauthPrincipal} who is logged in or <code>null</code> if
   *         no user is logged in
   */
  public OauthPrincipal getOauthPrincipal() {
    return oauthPrincipal;
  }


  /**
   * Gets the {@link UserManager} used to get role information.
   * 
   * @return the {@link UserManager} used to get role information
   */
  public UserManager getUserManager() {
    return userManager;
  }
}
