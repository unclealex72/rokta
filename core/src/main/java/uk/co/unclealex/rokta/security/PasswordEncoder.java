package uk.co.unclealex.rokta.security;

public interface PasswordEncoder {

	public String encode(String rawPassword);
}
