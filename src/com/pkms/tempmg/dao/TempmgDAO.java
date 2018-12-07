package com.pkms.tempmg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.tempmg.model.TempmgModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 템필릇 DAO
 * @author : 009
 * @Date : 2012. 4. 20
 */
@Repository("TempmgDAO")
public class TempmgDAO extends IbatisAbstractDAO {

	public String create(TempmgModel tempmgModel) throws Exception {
		return (String) create("TempmgDAO.create", tempmgModel);
	}
	
	/**
	 * 샘플 데이터를 DB에 수정
	 * 
	 * @param UserModel
	 *            - 수정 정보 모델
	 * @exception Exception
	 */
	public void update(TempmgModel tempmgModel) throws Exception {
		update("TempmgDAO.update", tempmgModel);
	}

	public void updateOther(TempmgModel tempmgModel) throws Exception {
		update("TempmgDAO.updateOther", tempmgModel);
	}

	/**
	 * 샘플 데이터 정보를 DB에서 조회
	 * 
	 * @param UserModel
	 *            - 조회 대상 모델
	 * @return TempmgModel - 조회 결과 모델
	 * @throws Exception
	 */
	public TempmgModel read(TempmgModel tempmgModel) throws Exception {
		return (TempmgModel) read("TempmgDAO.read", tempmgModel);
	}

	/**
	 * 샘플 데이터 정보 목록을 DB에서 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<TempmgModel> readList(TempmgModel tempmgModel) throws Exception {
		return (List<TempmgModel>)readList("TempmgDAO.readList", tempmgModel);
	}

	/**
	 * 샘플 데이터의 총 개수 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return int - 결과 개수
	 */
	public int readTotalCount(TempmgModel tempmgModel) {
		return (Integer) getSqlMapClientTemplate().queryForObject("TempmgDAO.readTotalCount", tempmgModel);
	}

}
