package com.pkms.common.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SktUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	public SktUserAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

}
