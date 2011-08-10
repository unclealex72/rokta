package uk.co.unclealex.rokta.client.security;


public class AuthenticationEvent {
  
  private final String i_username;
  
  public AuthenticationEvent(String username) {
		super();
		i_username = username;
	}

	public String getUsername() {
		return i_username;
	}  
}
