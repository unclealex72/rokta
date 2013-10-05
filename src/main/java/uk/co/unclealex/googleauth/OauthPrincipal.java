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

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.oauth2.model.Userinfo;

/**
 * An implementation of {@link Principal} that exposes both a Google {@link Userinfo} and a Google {@link Credential}.
 * @author alex
 *
 */
public class OauthPrincipal implements Principal {

  /**
   * A Google {@link Credential} that can be used to call Google APIs.
   */
  private final Credential credential;
  
  /**
   * The {@link Userinfo} for the currently logged in user.
   */
  private final Userinfo userinfo;

  /**
   * @param credential
   * @param userinfo
   */
  public OauthPrincipal(Credential credential, Userinfo userinfo) {
    super();
    this.credential = credential;
    this.userinfo = userinfo;
  }

  /**
   * Get the Gmail address of the currently logged in user.
   * @return The Gmail address of the currently logged in user. 
   */
  @Override
  public String getName() {
    return getUserinfo().getEmail();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    return (obj instanceof OauthPrincipal) && getName().equals(((OauthPrincipal) obj).getUserinfo());
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return getUserinfo().getEmail().hashCode();
  }
  
  public Credential getCredential() {
    return credential;
  }

  public Userinfo getUserinfo() {
    return userinfo;
  }

}
