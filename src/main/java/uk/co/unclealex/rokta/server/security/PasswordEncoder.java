package uk.co.unclealex.rokta.server.security;

public interface PasswordEncoder {

	public String encode(String rawPassword);
}
