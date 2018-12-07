package com.pkms.usermg.user.service;

import java.util.List;

import com.pkms.usermg.user.model.SktUserModel;

public interface SktUserServiceIf {

	public SktUserModel read(SktUserModel userModel) throws Exception;

	public List<SktUserModel> readList(SktUserModel userModel) throws Exception;

	public List<SktUserModel> readListAuth(SktUserModel userModel) throws Exception;

	public List<SktUserModel> readListAuth_boan(SktUserModel userModel) throws Exception;
	
	public List<SktUserModel> readListManager(SktUserModel userModel) throws Exception;

}
