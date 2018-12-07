package com.pkms.usermg.auth.service;

import java.util.List;

import com.pkms.usermg.auth.model.AuthModel;

/**
 * 권한 관리 서비스 인터페이스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 24.
 * 
 */
public interface AuthServiceIf {

	public AuthModel read(AuthModel authModel) throws Exception;

	public List<?> readList(AuthModel authModel) throws Exception;

	public void update(AuthModel authModel) throws Exception;

}
