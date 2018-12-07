package com.pkms.tempmg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.tempmg.model.TempmgModel;
import com.pkms.usermg.user.model.BpUserModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 템플릿 항목별 DAO
 * @author : 009
 * @Date : 2012. 4. 20
 */
@Repository("TempmgColumnDAO")
public class TempmgColumnDAO extends IbatisAbstractDAO {

	/**
	 * 샘플 데이터를 DB에 생성
	 * 
	 * @param UserModel
	 *            - 등록 정보 모델
	 * @return String - 등록 결과
	 * @throws Exception
	 */
	public String create(TempmgModel tempmgModel) throws Exception {
		return (String) create("TempmgColumnDAO.create", tempmgModel);
	}

	public String createByTpl_seq(TempmgModel tempmgModel) throws Exception {
		return (String) create("TempmgColumnDAO.createByTpl_seq", tempmgModel);
	}

	/**
	 * 샘플 데이터를 DB에서 삭제
	 * 
	 * @param UserModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete(TempmgModel model) throws Exception {
		delete("TempmgColumnDAO.delete", model);
	}

	/**
	 * 샘플 데이터 정보를 DB에서 조회
	 * 
	 * @param UserModel
	 *            - 조회 대상 모델
	 * @return TempmgModel - 조회 결과 모델
	 * @throws Exception
	 */
	public TempmgModel read(TempmgModel model) throws Exception {
		return (TempmgModel) read("TempmgColumnDAO.read", model);
	}

	/**
	 * 샘플 데이터 정보 목록을 DB에서 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<TempmgModel> readList(TempmgModel model) throws Exception {
		return (List<TempmgModel>)readList("TempmgColumnDAO.readList", model);
	}

	/**
	 * 샘플 데이터를 DB에 수정
	 * 
	 * @param UserModel
	 *            - 수정 정보 모델
	 * @exception Exception
	 */
	public void update(TempmgModel model) throws Exception {
		update("TempmgColumnDAO.update", model);
	}

	public void delete(BpUserModel model) throws Exception {
		delete("TempmgColumnDAO.delete", model);
	}

}
