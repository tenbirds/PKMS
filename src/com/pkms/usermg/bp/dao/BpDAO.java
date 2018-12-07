package com.pkms.usermg.bp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.usermg.bp.model.BpModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * 
 * 협력업체 정보를 처리하는 DAO <br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 16.
 * 
 */
@Repository("BpDAO")
public class BpDAO extends IbatisAbstractDAO {

	/**
	 * 협력업체 정보를 DB에 생성
	 * 
	 * @param UserModel
	 *            - 등록 정보 모델
	 * @return String - 등록 결과
	 * @throws Exception
	 */
	public String create(BpModel model) throws Exception {
		return (String) create("BpDAO.create", model);
	}

	/**
	 * 협력업체 정보를 DB에 수정
	 * 
	 * @param UserModel
	 *            - 수정 정보 모델
	 * @exception Exception
	 */
	public void update(BpModel model) throws Exception {
		update("BpDAO.update", model);
	}

	/**
	 * 협력업체 정보를 DB에서 삭제
	 * 
	 * @param UserModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete(BpModel model) throws Exception {
		delete("BpDAO.delete", model);
	}

	/**
	 * 협력업체 정보 정보를 DB에서 조회
	 * 
	 * @param UserModel
	 *            - 조회 대상 모델
	 * @return BpModel - 조회 결과 모델
	 * @throws Exception
	 */
	public BpModel read(BpModel model) throws Exception {
		return (BpModel) read("BpDAO.read", model);
	}

	/**
	 * 해당 사업자번호의 업체정보 개수 조회
	 * 
	 * @param UserModel
	 *            - 조회 대상 모델
	 * @return BpModel - 조회 결과 모델
	 * @throws Exception
	 */
	public int readCount(BpModel model) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("BpDAO.readCount", model);
	}

	/**
	 * 협력업체 정보 목록을 DB에서 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readList(BpModel model) throws Exception {
		return readList("BpDAO.readList", model);
	}

	/**
	 * 사용중인 협력업체 전체 목록을 DB에서 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readListAll(BpModel model) throws Exception {
		return readList("BpDAO.readListAll", model);
	}

	/**
	 * 협력업체 정보의 총 개수 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return int - 결과 개수
	 */
	public int readTotalCount(BpModel model) {
		return (Integer) getSqlMapClientTemplate().queryForObject("BpDAO.readTotalCount", model);
	}
	
	/**
	 * 협력업체 System 등록정보 목록을 DB에서 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<BpModel> readListSystem(BpModel model) throws Exception {
		return (List<BpModel>) readList("BpDAO.readListSystem", model);
	}
	
	/**
	 * SKT System 등록정보 목록을 DB에서 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<BpModel> readListSystem_skt(BpModel model) throws Exception {
		return (List<BpModel>) readList("BpDAO.readListSystem_skt", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<BpModel> readListSystem_skt_sosok(BpModel model) throws Exception {
		return (List<BpModel>) readList("BpDAO.readListSystem_skt_sosok", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<BpModel> readListSystem_operator(BpModel model) throws Exception {
		return (List<BpModel>) readList("BpDAO.readListSystem_operator", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<BpModel> readListSystem_operator_sosok(BpModel model) throws Exception {
		return (List<BpModel>) readList("BpDAO.readListSystem_operator_sosok", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<BpModel> readList_equipment(BpModel model) throws Exception {
		return (List<BpModel>) readList("BpDAO.readList_equipment", model);
	}
	
	/**
	 * 협력업체 System 등록정보의 총 개수 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return int - 결과 개수
	 */
	public int readListSystemCount(BpModel model) {
		return (Integer) getSqlMapClientTemplate().queryForObject("BpDAO.readListSystemCount", model);
	}
	
	/**
	 * 협력업체 System 등록정보의 총 개수 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return int - 결과 개수
	 */
	public int readListSystemCount_skt(BpModel model) {
		return (Integer) getSqlMapClientTemplate().queryForObject("BpDAO.readListSystemCount_skt", model);
	}
	
	public int readListSystemCount_skt_sosok(BpModel model) {
		return (Integer) getSqlMapClientTemplate().queryForObject("BpDAO.readListSystemCount_skt_sosok", model);
	}
	
	public int readListSystemCount_operator(BpModel model) {
		return (Integer) getSqlMapClientTemplate().queryForObject("BpDAO.readListSystemCount_operator", model);
	}
	
	public int readListSystemCount_operator_sosok(BpModel model) {
		return (Integer) getSqlMapClientTemplate().queryForObject("BpDAO.readListSystemCount_operator_sosok", model);
	}
	
	/**
	 * ID 찾기
	 * @param BpModel
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<BpModel> findIdList(BpModel model) throws Exception {
		return (List<BpModel>) readList("BpDAO.findIdList", model);
	}
	
	/**
	 * 비밀번호 변경
	 * 
	 * @param BpModel
	 *            - 수정 정보 모델
	 * @exception Exception
	 */
	public void pass_update(BpModel model) throws Exception {
		update("BpDAO.pass_update", model);
	}
}
