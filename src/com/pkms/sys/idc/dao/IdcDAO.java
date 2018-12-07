package com.pkms.sys.idc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.sys.idc.model.IdcModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * @author : skywarker
 * @Date : 2012. 5. 11.
 * 
 */
@Repository("IdcDAO")
public class IdcDAO extends IbatisAbstractDAO {

	/**
	 * 국사 정보를 DB에서 조회
	 * 
	 * @param IdcModel
	 *            - 조회 대상 모델
	 * @return IdcModel - 조회 결과 모델
	 * @throws Exception
	 */
	public IdcModel read(IdcModel model) throws Exception {
		return (IdcModel) read("IdcDAO.read", model);
	}

	/**
	 * 국사 정보 목록을 DB에서 조회
	 * 
	 * @param IdcModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readList(IdcModel model) throws Exception {
		return readList("IdcDAO.readList", model);
	}
	
	public List<?> eq_readList(IdcModel model) throws Exception {
		return readList("IdcDAO.eq_readList", model);
	}

}
