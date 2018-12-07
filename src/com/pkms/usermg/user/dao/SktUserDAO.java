package com.pkms.usermg.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.usermg.user.model.SktUserModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 12.
 * 
 */
@Repository("SktUserDAO")
public class SktUserDAO extends IbatisAbstractDAO {

	public SktUserModel read(SktUserModel model) throws Exception {
		return (SktUserModel) read("SktUserDAO.read", model);
	}

	@SuppressWarnings("unchecked")
	public List<SktUserModel> readList(SktUserModel model) throws Exception {
		return (List<SktUserModel>) readList("SktUserDAO.readList", model);
	}

	@SuppressWarnings("unchecked")
	public List<SktUserModel> readListAuth(SktUserModel model) throws Exception {
		return (List<SktUserModel>) readList("SktUserDAO.readListAuth", model);
	}

	@SuppressWarnings("unchecked")
	public List<SktUserModel> readListAuth_boan(SktUserModel model) throws Exception {
		return (List<SktUserModel>) readList("SktUserDAO.readListAuth_boan", model);
	}

	@SuppressWarnings("unchecked")
	public List<SktUserModel> readListManager(SktUserModel model) throws Exception {
		return (List<SktUserModel>) readList("SktUserDAO.readListManager", model);
	}

	public SktUserModel readEmpno(SktUserModel model) throws Exception {
		return (SktUserModel) read("SktUserDAO.readEmpno", model);
	}
}
