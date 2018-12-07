package com.pkms.usermg.auth.service;

import java.util.List;

import com.pkms.usermg.auth.model.AuthUserModel;

/**
 * 권한 관리 서비스 인터페이스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 24.
 * 
 */
public interface AuthUserServiceIf {

	public void create(AuthUserModel authUserModel) throws Exception;

	public AuthUserModel read(AuthUserModel authUserModel) throws Exception;

	public List<?> readList(AuthUserModel authUserModel) throws Exception;

	public void update(AuthUserModel authUserModel) throws Exception;

}
