package com.pkms.pkgmg.pkg.dao;

import org.springframework.stereotype.Repository;

import com.pkms.verify_tem_mg.model.VerifyTemModel;
import com.wings.dao.IbatisSnetAbstractDAO;

@Repository("PkgSnetDao")
public class PkgSnetDao extends IbatisSnetAbstractDAO {
	
	public void pkgStatusSecurity_create(VerifyTemModel verifyTemModel) throws Exception {
		create("PkgSnetDAO.pkgStatusSecurity_create", verifyTemModel);
	}

	public int getAudit_Rate(VerifyTemModel verifyTemModel) throws Exception {
		return (Integer) read("PkgSnetDAO.getAudit_Rate", verifyTemModel);
	}
}
