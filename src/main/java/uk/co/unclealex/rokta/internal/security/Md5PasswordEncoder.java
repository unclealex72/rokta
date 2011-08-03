package uk.co.unclealex.rokta.internal.security;

import org.apache.commons.codec.digest.DigestUtils;


public class Md5PasswordEncoder implements PasswordEncoder {

	public String encode(String rawPassword) {
		return DigestUtils.md5Hex(rawPassword).toLowerCase();
	}
}
