package com.pkms.sys.equipment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.sys.common.model.SysModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * 
 * 시스템 장비 정보를 위한 DAO<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Repository("EquipmentDAO")
public class EquipmentDAO extends IbatisAbstractDAO {

	/**
	 * 시스템 장비 정보를 DB에 생성
	 * 
	 * @param SysModel
	 *            - 등록 정보 모델
	 * @return String - 등록 결과
	 * @throws Exception
	 */
	public String create(SysModel model) throws Exception {
		return (String) create("EquipmentDAO.create", model);
	}

	/**
	 * 시스템 장비 정보를 DB에 수정
	 * 
	 * @param SysModel
	 *            - 수정 정보 모델
	 * @exception Exception
	 */
	public void update(SysModel model) throws Exception {
		update("EquipmentDAO.update", model);
	}

	/**
	 * 시스템 장비 정보를 DB에서flag 값 변경 - data 유지
	 * 
	 * @param SysModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete(SysModel model) throws Exception {
		delete("EquipmentDAO.delete", model);
	}
	
	/**
	 * 시스템 장비 정보를 DB에서 삭제 - 완전 삭제  (복구 불가)
	 * 
	 * @param SysModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete_real(SysModel model) throws Exception {
		delete("EquipmentDAO.delete_real", model);
	}

	/**
	 * 시스템 장비 정보 정보를 DB에서 조회
	 * 
	 * @param SysModel
	 *            - 조회 대상 모델
	 * @return SysModel - 조회 결과 모델
	 * @throws Exception
	 */
	public SysModel read(SysModel model) throws Exception {
		return (SysModel) read("EquipmentDAO.read", model);
	}

	/**
	 * 시스템 장비 정보 정보 목록을 DB에서 조회
	 * 
	 * @param SysModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readList(SysModel model) throws Exception {
		return readList("EquipmentDAO.readList", model);
	}

	public String readNextSeq() {
		return (String) getSqlMapClientTemplate().queryForObject("EquipmentDAO.NextSeq");
	}
	
	public List<?> idc_readList(SysModel model) throws Exception {
		return readList("EquipmentDAO.idc_readList", model);
	}
	
	public List<?> eq_readList(SysModel model) throws Exception {
		return readList("EquipmentDAO.eq_readList", model);
	}
}
