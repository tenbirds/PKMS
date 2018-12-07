package com.pkms.usermg.user.service;

import java.util.List;

import com.pkms.usermg.user.model.SktUserModel;

public interface SktUserPopupServiceIf {

	public SktUserModel read(SktUserModel userModel) throws Exception;

	public List<?> readListAuth(SktUserModel userModel) throws Exception;

	public List<?> readListAuth_boan(SktUserModel userModel) throws Exception;
	
	public List<?> readListManager(SktUserModel userModel) throws Exception;
	
	public SktUserModel readEmpno(SktUserModel userModel) throws Exception;

}
