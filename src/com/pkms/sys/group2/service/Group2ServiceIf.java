package com.pkms.sys.group2.service;

import java.util.List;

import com.pkms.sys.common.model.SysModel;

public interface Group2ServiceIf {

	public SysModel create(SysModel sysModel) throws Exception;

	public SysModel read(SysModel sysModel) throws Exception;

	public List<SysModel> readList(SysModel sysModel) throws Exception;
	
	public List<SysModel> readList4User(SysModel sysModel) throws Exception;

	public void update(SysModel sysModel) throws Exception;

	public void delete(SysModel sysModel) throws Exception;

}
