package com.pkms.common.security.provider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.pkms.common.security.token.SktUserAuthenticationToken;

public class SktUserAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	public boolean supports(Class<?> authentication) {
		return SktUserAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
