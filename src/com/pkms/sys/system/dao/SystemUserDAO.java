package com.pkms.sys.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.sys.system.model.SystemUserModel;
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
@Repository("SystemUserDAO")
public class SystemUserDAO extends IbatisAbstractDAO {

	/**
	 * 시스템 담당자 정보를 DB에 생성
	 * 
	 * @param SystemUserModel
	 *            - 등록 정보 모델
	 * @return String - 등록 결과
	 * @throws Exception
	 */
	public String create(List<SystemUserModel> modelList) throws Exception {
		return (String) create("SystemUserDAO.create", modelList);
	}

	/**
	 * 시스템 담당자 정보를 DB에서 삭제
	 * 
	 * @param SystemUserModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete(SystemUserModel model) throws Exception {
		delete("SystemUserDAO.delete", model);
	}
	
	/**
	 * 시스템 담당자 정보를 DB에서 update - use_yn 변경
	 * 
	 * @param SystemUserModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete_u(SystemUserModel model) throws Exception {
		delete("SystemUserDAO.delete_u", model);
	}

	/**
	 * 시스템 담당자 정보 정보 목록을 DB에서 조회
	 * 
	 * @param SystemUserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readList(SystemUserModel model) throws Exception {
		return readList("SystemUserDAO.readList", model);
	}

	/**
	 * 담당자에 해당하는 시스템 SEQ 목록을 DB에서 조회
	 * 
	 * @param SystemUserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readListAppliedToUser(SystemUserModel model) throws Exception {
		return readList("SystemUserDAO.readListAppliedToUser", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<SystemUserModel> dev_readUser(SystemUserModel model) throws Exception {
		return (List<SystemUserModel>) readList("SystemUserDAO.dev_readUser", model);
	}

}
