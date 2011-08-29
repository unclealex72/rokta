package uk.co.unclealex.rokta.server.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class RoktaUserDetails implements UserDetails {

	private final String i_username;
	private final String i_password;
	private final Collection<GrantedAuthority> i_authorities;
	
	public RoktaUserDetails(String username, String password) {
		super();
		i_username = username;
		i_password = password;
		GrantedAuthority grantedAuthority = new GrantedAuthority() {
			public String getAuthority() {
				return "ROLE_USER";
			}
		};
		i_authorities = Collections.singleton(grantedAuthority);

	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getUsername() {
		return i_username;
	}

	public String getPassword() {
		return i_password;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return i_authorities;
	}

}
