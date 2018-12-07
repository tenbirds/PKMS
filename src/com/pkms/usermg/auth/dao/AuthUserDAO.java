package com.pkms.usermg.auth.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.usermg.auth.model.AuthUserModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 권한 관리 DAO<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 24.
 * 
 */
@Repository("AuthUserDAO")
public class AuthUserDAO extends IbatisAbstractDAO {

	/**
	 * 권한 데이터를 DB에 생성
	 * 
	 * @param List
	 *            <AuthUserModel> - 등록 정보 모델
	 * @return String - 등록 결과
	 * @throws Exception
	 */
	public String create(List<AuthUserModel> modelList) throws Exception {
		return (String) create("AuthUserDAO.create", modelList);
	}

	/**
	 * 권한 데이터를 DB에서 삭제
	 * 
	 * @param AuthUserModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete(AuthUserModel model) throws Exception {
		delete("AuthUserDAO.delete", model);
	}

	/**
	 * 권한 데이터 정보를 DB에서 조회
	 * 
	 * @param AuthUserModel
	 *            - 조회 조건을 담은 모델
	 * @return AuthUserModel - 결과 모델을 담은 객체
	 * @throws Exception
	 */
	public AuthUserModel read(AuthUserModel model) throws Exception {
		return (AuthUserModel) read("AuthUserDAO.read", model);
	}

}
