package com.pkms.sys.equipment.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.sys.equipment.dao.EquipmentUserDAO;
import com.pkms.sys.equipment.model.EquipmentUserModel;

/**
 * 
 * 
 * 장비 담당자를 관리하는 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 14.
 * 
 */
@Service("EquipmentUserService")
public class EquipmentUserService implements EquipmentUserServiceIf {

	@Resource(name = "EquipmentUserDAO")
	private EquipmentUserDAO equipmentUserDAO;

	@Override
	public void create(List<EquipmentUserModel> equipmentUserModelList) throws Exception {
		equipmentUserDAO.create(equipmentUserModelList);
	}

	@Override
	public List<?> readList(EquipmentUserModel equipmentUserModel) throws Exception {
		return equipmentUserDAO.readList(equipmentUserModel);
	}

	@Override
	public List<?> readListAppliedToUser(EquipmentUserModel EquipmentUserModel) throws Exception {
		return equipmentUserDAO.readListAppliedToUser(EquipmentUserModel);
	}

	@Override
	public void delete(EquipmentUserModel equipmentUserModel) throws Exception {
		equipmentUserDAO.delete(equipmentUserModel);
	}
	
	@Override
	public void delete_u(EquipmentUserModel equipmentUserModel) throws Exception {
		equipmentUserDAO.delete_u(equipmentUserModel);
	}
	
	@Override
	public List<?> idc_readList(EquipmentUserModel equipmentUserModel) throws Exception {
		return equipmentUserDAO.idc_readList(equipmentUserModel);
	}

}
