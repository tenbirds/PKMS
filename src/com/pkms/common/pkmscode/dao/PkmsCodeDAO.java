package com.pkms.common.pkmscode.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.common.pkmscode.model.PkmsCodeModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * @author : skywarker
 * @Date : 2012. 4. 13.
 * 
 */
@Repository("PkmsCodeDAO")
public class PkmsCodeDAO extends IbatisAbstractDAO {

	/**
	 * Common code 정보 목록을 DB에서 조회
	 * 
	 * @param PkmsCodeModel
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	public List<?> readList(PkmsCodeModel model) throws Exception {
		return readList("PkmsCodeDAO.readList", model);
	}

}
