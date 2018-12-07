package com.pkms.common.security.provider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.pkms.common.security.token.SktUserMobileAuthenticationToken;

public class SktUserMobileAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	public boolean supports(Class<?> authentication) {
		return SktUserMobileAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
