package uk.co.unclealex.rokta.server.security;

import uk.co.unclealex.googleauth.GoogleOauthFilter;
import uk.co.unclealex.googleauth.UserManager;

import com.google.api.client.http.javanet.NetHttpTransport;

public class RoktaSecurityFilter extends GoogleOauthFilter {

  public RoktaSecurityFilter(UserManager userManager) {
    super(new NetHttpTransport(), userManager, "oauth.html", "rokta-secrets.json");
  }

}
