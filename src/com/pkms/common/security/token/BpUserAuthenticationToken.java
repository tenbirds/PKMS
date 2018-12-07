package com.pkms.common.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class BpUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	public BpUserAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

}
