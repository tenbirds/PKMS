package com.pkms.org.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.org.model.OrgModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * SKT 조직<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 25.
 * 
 */
@Repository("OrgDAO")
public class OrgDAO extends IbatisAbstractDAO {

	/**
	 * SK 조직도 정보 목록을 DB에서 조회
	 * 
	 * @param OrgModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<OrgModel> readList(OrgModel model) throws Exception {
		return (List<OrgModel>) readList("OrgDAO.readList", model);
	}

}
