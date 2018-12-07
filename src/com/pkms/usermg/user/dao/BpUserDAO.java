package com.pkms.usermg.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.usermg.user.model.BpUserModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * 
 * BP 사용자를 위한 DAO<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 12.
 * 
 */
@Repository("BpUserDAO")
public class BpUserDAO extends IbatisAbstractDAO {

	public String create(BpUserModel model) throws Exception {
		return (String) create("BpUserDAO.create", model);
	}

	public void update(BpUserModel model) throws Exception {
		update("BpUserDAO.update", model);
	}

	public void updateYN(BpUserModel model) throws Exception {
		delete("BpUserDAO.updateYN", model);
	}

	public BpUserModel read(BpUserModel model) throws Exception {
		return (BpUserModel) read("BpUserDAO.read", model);
	}

	public int readCount(BpUserModel model) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("BpUserDAO.readCount", model);
	}

	@SuppressWarnings("unchecked")
	public List<BpUserModel> readList(BpUserModel model) throws Exception {
		return (List<BpUserModel>) readList("BpUserDAO.readList", model);
	}

	@SuppressWarnings("unchecked")
	public List<BpUserModel> bp_sys_readList(BpUserModel model) throws Exception {
		
		return (List<BpUserModel>) readList("BpUserDAO.bp_sys_readList", model);
	}

	// 협력업체 담당자 삭제시 해당 시스템의 담당자 목록 동시 삭제 - ksy (0826)
	public void deleteDamdang(BpUserModel model) throws Exception {
		delete("BpUserDAO.deleteDamdang", model);
	}
}
