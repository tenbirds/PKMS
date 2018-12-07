package com.pkms.sys.equipment.service;

import java.util.List;

import com.pkms.sys.common.model.SysModel;

public interface EquipmentServiceIf {

	public SysModel create(SysModel sysModel) throws Exception;

	public SysModel read(SysModel sysModel) throws Exception;

	public SysModel readOnly(SysModel sysModel) throws Exception;

	public List<?> readList(SysModel sysModel) throws Exception;

	public void update(SysModel sysModel) throws Exception;

	public void delete(SysModel sysModel) throws Exception;
	
	public void delete_real(SysModel sysModel) throws Exception;
	
	public void createEquipmentUser(SysModel sysModel) throws Exception;

	public List<?> idc_readList(SysModel sysModel) throws Exception;
	
}
