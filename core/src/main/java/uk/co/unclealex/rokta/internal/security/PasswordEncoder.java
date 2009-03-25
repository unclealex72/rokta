package uk.co.unclealex.rokta.internal.security;

public interface PasswordEncoder {

	public String encode(String rawPassword);
}
