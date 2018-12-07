package com.pkms.common.security.provider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.pkms.common.security.token.BpUserAuthenticationToken;

public class BpUserAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	public boolean supports(Class<?> authentication) {
		return BpUserAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
