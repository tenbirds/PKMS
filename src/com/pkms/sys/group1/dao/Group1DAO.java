package com.pkms.sys.group1.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.sys.common.model.SysModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * 
 * 시스템 대분류 정보를 위한 DAO<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Repository("Group1DAO")
public class Group1DAO extends IbatisAbstractDAO {

	/**
	 * 시스템 대분류 정보를 DB에 생성
	 * 
	 * @param SysModel
	 *            - 등록 정보 모델
	 * @return String - 등록 결과
	 * @throws Exception
	 */
	public String create(SysModel model) throws Exception {
		return (String) create("Group1DAO.create", model);
	}

	/**
	 * 시스템 대분류 정보를 DB에 수정
	 * 
	 * @param SysModel
	 *            - 수정 정보 모델
	 * @exception Exception
	 */
	public void update(SysModel model) throws Exception {
		update("Group1DAO.update", model);
	}

	/**
	 * 시스템 대분류 정보를 DB에서 삭제
	 * 
	 * @param SysModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete(SysModel model) throws Exception {
		delete("Group1DAO.delete", model);
	}

	/**
	 * 시스템 대분류 정보 정보를 DB에서 조회
	 * 
	 * @param SysModel
	 *            - 조회 대상 모델
	 * @return SysModel - 조회 결과 모델
	 * @throws Exception
	 */
	public SysModel read(SysModel model) throws Exception {
		return (SysModel) read("Group1DAO.read", model);
	}

	/**
	 * 시스템 대분류 정보 정보 목록을 DB에서 조회
	 * 
	 * @param SysModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SysModel> readList(SysModel model) throws Exception {
		return (List<SysModel>) readList("Group1DAO.readList", model);
	}

	public String readNextSeq() {
		return (String) getSqlMapClientTemplate().queryForObject("Group1DAO.NextSeq");
	}
	
	@SuppressWarnings("unchecked")
	public List<SysModel> searchList_0(SysModel model) throws Exception {
		return (List<SysModel>) readList("Group1DAO.searchList_0", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<SysModel> searchList_1(SysModel model) throws Exception {
		return (List<SysModel>) readList("Group1DAO.searchList_1", model);
	}
}
