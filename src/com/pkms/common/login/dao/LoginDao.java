package com.pkms.common.login.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.usermg.bp.model.BpModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("LoginDao")
public class LoginDao extends IbatisAbstractDAO {
	
	public BpModel read_loginCnt(BpModel bpModel){
		return (BpModel) read("LoginDao.read_LoginCnt", bpModel);
	}

	public void update_loginCnt(BpModel bpModel) {
		update("LoginDao.update_loginCnt", bpModel);
	}
	
}
