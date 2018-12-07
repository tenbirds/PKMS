package com.pkms.sys.equipment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.sys.equipment.model.EquipmentUserModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * 
 * 시스템 담당자 정보를 위한 DAO<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Repository("EquipmentUserDAO")
public class EquipmentUserDAO extends IbatisAbstractDAO {

	/**
	 * 시스템 담당자 정보를 DB에 생성
	 * 
	 * @param EquipmentUserModel
	 *            - 등록 정보 모델
	 * @return String - 등록 결과
	 * @throws Exception
	 */
	public String create(List<EquipmentUserModel> modelList) throws Exception {
		return (String) create("EquipmentUserDAO.create", modelList);
	}

	/**
	 * 시스템 담당자 정보를 DB에서 삭제
	 * 
	 * @param EquipmentUserModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete(EquipmentUserModel model) throws Exception {
		delete("EquipmentUserDAO.delete", model);
	}
	
	/**
	 * 시스템 담당자 정보를 DB에서 수정 -use_yn
	 * 
	 * @param EquipmentUserModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete_u(EquipmentUserModel model) throws Exception {
		delete("EquipmentUserDAO.delete_u", model);
	}

	/**
	 * 시스템 담당자 정보 정보 목록을 DB에서 조회
	 * 
	 * @param EquipmentUserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readList(EquipmentUserModel model) throws Exception {
		return readList("EquipmentUserDAO.readList", model);
	}

	/**
	 * 담당자에 해당하는 장비 SEQ 목록을 DB에서 조회
	 * 
	 * @param EquipmentUserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readListAppliedToUser(EquipmentUserModel model) throws Exception {
		return readList("EquipmentUserDAO.readListAppliedToUser", model);
	}

	/**
	 * 국사별 담당자
	 * 
	 * @param EquipmentUserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> idc_readList(EquipmentUserModel model) throws Exception {
		return readList("EquipmentUserDAO.idc_readList", model);
	}
}
