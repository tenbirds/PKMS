package com.pkms.sys.group1.service;

import java.util.List;

import com.pkms.sys.common.model.SysModel;

public interface Group1ServiceIf {

	public SysModel create(SysModel sysModel) throws Exception;

	public SysModel read(SysModel sysModel) throws Exception;

	public List<SysModel> readList(SysModel sysModel) throws Exception;

	public void update(SysModel sysModel) throws Exception;

	public void delete(SysModel sysModel) throws Exception;
	
	public List<SysModel> searchList(SysModel sysModel) throws Exception;
}
