package com.pkms.usermg.auth.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.usermg.auth.model.AuthModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 권한 관리 DAO<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 24.
 * 
 */
@Repository("AuthDAO")
public class AuthDAO extends IbatisAbstractDAO {

	/**
	 * 권한 데이터를 DB에 생성
	 * 
	 * @param List<AuthModel>
	 *            - 등록 정보 모델
	 * @return String - 등록 결과
	 * @throws Exception
	 */
	public String create(List<AuthModel> modelList) throws Exception {
		return (String) create("AuthDAO.create", modelList);
	}

	/**
	 * 권한 데이터를 DB에서 삭제
	 * 
	 * @param AuthModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete(AuthModel model) throws Exception {
		delete("AuthDAO.delete", model);
	}

	/**
	 * 권한 데이터 정보 목록을 DB에서 조회
	 * 
	 * @param AuthModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readList(AuthModel model) throws Exception {
		return readList("AuthDAO.readList", model);
	}

}
