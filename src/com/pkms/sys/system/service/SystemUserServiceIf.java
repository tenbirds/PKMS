package com.pkms.sys.system.service;

import java.util.List;

import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.system.model.SystemUserModel;

public interface SystemUserServiceIf {

	public void create(List<SystemUserModel> systemUserModelList) throws Exception;

	public SysModel read(SysModel sysModel) throws Exception;

	public List<?> readListSearchUser(SysModel sysModel) throws Exception;

	public List<?> readListSystemUser(SystemUserModel systemUserModel) throws Exception;

	public List<?> readListAppliedToUser(SystemUserModel systemUserModel) throws Exception;

	public void delete(SystemUserModel systemUserModel) throws Exception;
	
	public void delete_u(SystemUserModel systemUserModel) throws Exception;
}
