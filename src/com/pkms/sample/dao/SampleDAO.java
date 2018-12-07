package com.pkms.sample.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.sample.model.SampleModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * @author skywarker
 * 
 */
@Repository("SampleDAO")
public class SampleDAO extends IbatisAbstractDAO {

	/**
	 * 샘플 데이터를 DB에 생성
	 * 
	 * @param UserModel
	 *            - 등록 정보 모델
	 * @return String - 등록 결과
	 * @throws Exception
	 */
	public String create(SampleModel model) throws Exception {
		return (String) create("SampleDAO.create", model);
	}

	/**
	 * 샘플 데이터를 DB에 수정
	 * 
	 * @param UserModel
	 *            - 수정 정보 모델
	 * @exception Exception
	 */
	public void update(SampleModel model) throws Exception {
		update("SampleDAO.update", model);
	}

	/**
	 * 샘플 데이터를 DB에서 삭제
	 * 
	 * @param UserModel
	 *            - 삭제 정보 모델
	 * @throws Exception
	 */
	public void delete(SampleModel model) throws Exception {
		delete("SampleDAO.delete", model);
	}

	/**
	 * 샘플 데이터 정보를 DB에서 조회
	 * 
	 * @param UserModel
	 *            - 조회 대상 모델
	 * @return SampleModel - 조회 결과 모델
	 * @throws Exception
	 */
	public SampleModel read(SampleModel model) throws Exception {
		return (SampleModel) read("SampleDAO.read", model);
	}

	/**
	 * 샘플 데이터 정보 목록을 DB에서 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readList(SampleModel model) throws Exception {
		return readList("SampleDAO.readList", model);
	}

	/**
	 * 샘플 데이터의 총 개수 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return int - 결과 개수
	 */
	public int readTotalCount(SampleModel model) {
		return (Integer) getSqlMapClientTemplate().queryForObject("SampleDAO.readTotalCount", model);
	}

}
