package com.pkms.sys.equipment.service;

import java.util.List;

import com.pkms.sys.equipment.model.EquipmentUserModel;

public interface EquipmentUserServiceIf {

	public void create(List<EquipmentUserModel> EquipmentUserModelList) throws Exception;

	public List<?> readList(EquipmentUserModel EquipmentUserModel) throws Exception;

	public List<?> readListAppliedToUser(EquipmentUserModel EquipmentUserModel) throws Exception;

	public void delete(EquipmentUserModel EquipmentUserModel) throws Exception;
	
	public void delete_u(EquipmentUserModel EquipmentUserModel) throws Exception;
	
	public List<?> idc_readList(EquipmentUserModel EquipmentUserModel) throws Exception;

}
