package uk.co.unclealex.rokta.client.security;


public interface AuthenticationEventListener {
  void onAuthenticationChanged(AuthenticationEvent event);
}
