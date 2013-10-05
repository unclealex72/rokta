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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.MemoryCredentialStore;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.oauth2.model.Userinfo;

/**
 * @author alex
 * 
 */
public class GoogleOauthFilter implements Filter {

  private final static Logger log = LoggerFactory.getLogger(GoogleOauthFilter.class);

  private static final String PROPERTY_PREFIX = "_" + GoogleOauthFilter.class.getName();
  static final String LOCK = PROPERTY_PREFIX + ".lock";
  private static final String FLOW = PROPERTY_PREFIX + ".flow";
  private static final String USERINFO = PROPERTY_PREFIX + ".userinfo";

  private final JsonFactory jsonFactory = new JacksonFactory();
  private final HttpTransport httpTransport;

  private final UserManager userManager;
  private final String oauthCallbackUrl;

  private final List<String> extraScopes;
  private final String clientSecretsResourceName;

  /**
   * @param userManager
   * @param oauthCallbackUrl
   * @param clientSecretsResourceName
   * @param extraScopes
   */
  public GoogleOauthFilter(
      HttpTransport httpTransport,
      UserManager userManager,
      String oauthCallbackUrl,
      String clientSecretsResourceName,
      String... extraScopes) {
    super();
    this.httpTransport = httpTransport;
    this.userManager = userManager;
    this.oauthCallbackUrl = oauthCallbackUrl;
    this.extraScopes = Arrays.asList(extraScopes);
    this.clientSecretsResourceName = clientSecretsResourceName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException,
      ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
    if (req.getRequestURI().equals(req.getContextPath() + "/" + getOauthCallbackUrl())) {
      doOauthRequest(req, resp, chain);
    }
    else {
      doRequest(req, resp, chain);
    }
  }

  public void doRequest(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
      throws IOException,
      ServletException {
    HttpSession session = req.getSession();
    Lock lock = (Lock) session.getAttribute(LOCK);
    lock.lock();
    try {
      AuthorizationCodeFlow flow = loadFlow(session);
      String userId = getUserId(req);
      Credential credential = flow.loadCredential(userId);
      // if credential found with an access token, invoke the user code
      if (credential != null && credential.getAccessToken() != null) {
        try {
          Userinfo userinfo = loadUserInfo(session, credential);
          OauthPrincipal oauthPrincipal = new OauthPrincipal(credential, userinfo);
          UserManager userManager = getUserManager();
          List<String> validGmailAddresses = userManager.getValidGmailAddresses();
          String userEmail = userinfo.getEmail();
          if (validGmailAddresses == null || validGmailAddresses.contains(userEmail)) {
            userManager.createNewUserIfRequired(userEmail, userinfo, credential);
            HttpServletRequest request = new OauthHttpServletRequestWrapper(req, oauthPrincipal, userManager);
            chain.doFilter(request, resp);
            return;
          }
          else {
            onError(
                HttpServletResponse.SC_FORBIDDEN,
                "User " + userEmail + " is not allowed to access this resource.",
                req,
                resp);
            return;
          }
        }
        catch (HttpResponseException e) {
          // if access token is null, assume it is because auth failed and we
          // need to re-authorize
          // but if access token is not null, it is some other problem
          if (credential.getAccessToken() != null && e.getStatusCode() != 401) {
            throw e;
          }
        }
      }
      // redirect to the authorization flow
      AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
      authorizationUrl.setRedirectUri(getRedirectUri(req));
      authorizationUrl.setState(fullUrl(req));
      onAuthorization(req, resp, authorizationUrl);
    }
    finally {
      lock.unlock();
    }
  }

  /**
   * @param session
   * @return
   * @throws IOException
   */
  protected Userinfo loadUserInfo(HttpSession session, Credential credential) throws IOException {
    Userinfo userinfo = (Userinfo) session.getAttribute(USERINFO);
    if (userinfo == null) {
      userinfo = queryForUserinfo(credential);
      session.setAttribute(USERINFO, userinfo);
    }
    return userinfo;
  }

  protected Userinfo queryForUserinfo(Credential credential) throws IOException {
    Userinfo userinfo;
    Oauth2 oauth2 = new Oauth2(getHttpTransport(), getJsonFactory(), credential);
    userinfo = oauth2.userinfo().get().execute();
    return userinfo;
  }

  public void doOauthRequest(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
      throws ServletException,
      IOException {
    String fullUrl = fullUrl(req);
    AuthorizationCodeResponseUrl responseUrl = new AuthorizationCodeResponseUrl(fullUrl);
    String code = responseUrl.getCode();
    if (responseUrl.getError() != null) {
      onError(HttpServletResponse.SC_UNAUTHORIZED, responseUrl.getErrorDescription(), req, resp);
    }
    else if (code == null) {
      onError(HttpServletResponse.SC_BAD_GATEWAY, "Missing authorization code", req, resp);
    }
    else {
      String redirectUri = getRedirectUri(req);
      HttpSession session = req.getSession();
      Lock lock = (Lock) session.getAttribute(LOCK);
      lock.lock();
      try {
        AuthorizationCodeFlow flow = loadFlow(session);
        TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
        String userId = getUserId(req);
        flow.createAndStoreCredential(response, userId);
        resp.sendRedirect(responseUrl.getState());
      }
      finally {
        lock.unlock();
      }
    }
  }

  protected String fullUrl(HttpServletRequest req) {
    StringBuffer buf = req.getRequestURL();
    if (req.getQueryString() != null) {
      buf.append('?').append(req.getQueryString());
    }
    String fullUrl = buf.toString();
    return fullUrl;
  }

  /**
   * Loads the authorization code flow to be used across all HTTP servlet
   * requests (only called during the first HTTP servlet request).
   */
  protected AuthorizationCodeFlow loadFlow(HttpSession session) throws ServletException, IOException {
    AuthorizationCodeFlow flow = (AuthorizationCodeFlow) session.getAttribute(FLOW);
    if (flow == null) {
      GoogleClientSecrets clientSecrets =
          GoogleClientSecrets.load(
              getJsonFactory(),
              getClass().getClassLoader().getResourceAsStream(getClientSecretsResourceName()));
      Set<String> scopes =
          new HashSet<String>(Arrays.asList(Oauth2Scopes.USERINFO_EMAIL, Oauth2Scopes.USERINFO_PROFILE));
      List<String> extraScopes = getExtraScopes();
      if (extraScopes != null) {
        scopes.addAll(extraScopes);
      }
      flow =
          createAuthorizationCodeFlowBuilder(clientSecrets, scopes)
              .setCredentialStore(new MemoryCredentialStore())
              .build();
      session.setAttribute(FLOW, flow);
    }
    return flow;
  }

  protected Builder createAuthorizationCodeFlowBuilder(GoogleClientSecrets clientSecrets, Iterable<String> scopes) {
    return new GoogleAuthorizationCodeFlow.Builder(getHttpTransport(), getJsonFactory(), clientSecrets, scopes);
  }

  /** Returns the redirect URI for the given HTTP servlet request. */
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setPathParts(Arrays.asList("", req.getContextPath().substring(1), getOauthCallbackUrl()));
    return url.build();
  }

  /** Returns the user ID for the given HTTP servlet request. */
  protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
    return req.getSession().getId();
  }

  /**
   * Handles user authorization by redirecting to the OAuth 2.0 authorization
   * server.
   * 
   * <p>
   * Default implementation is to call
   * {@code resp.sendRedirect(authorizationUrl.build())}. Subclasses may
   * override to provide optional parameters such as the recommended state
   * parameter. Sample implementation:
   * </p>
   * 
   * <pre>
   * &#064;Override
   * protected void onAuthorization(
   *     HttpServletRequest req,
   *     HttpServletResponse resp,
   *     AuthorizationCodeRequestUrl authorizationUrl) throws ServletException, IOException {
   *   authorizationUrl.setState(&quot;xyz&quot;);
   *   super.onAuthorization(req, resp, authorizationUrl);
   * }
   * </pre>
   * 
   * @param authorizationUrl
   *          authorization code request URL
   * @param req
   *          HTTP servlet request
   * @throws ServletException
   *           servlet exception
   * @since 1.11
   */
  protected void onAuthorization(
      HttpServletRequest req,
      HttpServletResponse resp,
      AuthorizationCodeRequestUrl authorizationUrl) throws ServletException, IOException {
    resp.sendRedirect(authorizationUrl.build());
  }

  /**
   * Handles an error to the authorization, such as when an end user denies
   * authorization.
   * 
   * <p>
   * Default implementation is to do nothing, but subclasses should override and
   * implement. Sample implementation:
   * </p>
   * 
   * <pre>
   * resp.sendRedirect(&quot;/denied&quot;);
   * </pre>
   * 
   * @param req
   *          HTTP servlet request
   * @param resp
   *          HTTP servlet response
   * @throws ServletException
   *           HTTP servlet exception
   * @throws IOException
   *           some I/O exception
   */
  protected void onError(int errorCode, String message, HttpServletRequest req, HttpServletResponse resp)
      throws ServletException,
      IOException {
    log.error("Authentication error " + errorCode + ": " + message);
    resp.sendError(errorCode, message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() {
    // Do nothing
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // Do nothing

  }

  public JsonFactory getJsonFactory() {
    return jsonFactory;
  }

  public HttpTransport getHttpTransport() {
    return httpTransport;
  }

  public UserManager getUserManager() {
    return userManager;
  }

  public String getOauthCallbackUrl() {
    return oauthCallbackUrl;
  }

  public List<String> getExtraScopes() {
    return extraScopes;
  }

  public String getClientSecretsResourceName() {
    return clientSecretsResourceName;
  }

}
