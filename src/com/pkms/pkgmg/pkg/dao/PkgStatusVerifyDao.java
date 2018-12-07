package com.pkms.pkgmg.pkg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.verify_tem_mg.model.VerifyTemModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("PkgStatusVerifyDao")
public class PkgStatusVerifyDao extends IbatisAbstractDAO {
	
	@SuppressWarnings("unchecked")
	public List<VerifyTemModel> getList_verifyTem(VerifyTemModel verifyTemModel) throws Exception {
		return (List<VerifyTemModel>) readList("PkgStatusVerifyDAO.getList_verifyTem", verifyTemModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<VerifyTemModel> getList_verifyQuest(VerifyTemModel verifyTemModel) throws Exception {
		return (List<VerifyTemModel>) readList("PkgStatusVerifyDAO.getList_verifyQuest", verifyTemModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<VerifyTemModel> getList_verifyItem(VerifyTemModel verifyTemModel) throws Exception {
		return (List<VerifyTemModel>) readList("PkgStatusVerifyDAO.getList_verifyItem", verifyTemModel);
	}
	
	public void create_verifyResult(VerifyTemModel verifyTemModel) throws Exception {
		create("PkgStatusVerifyDAO.create_verifyResult", verifyTemModel);
	}
	
	public void to_pkgmaster_verifyinfo(VerifyTemModel verifyTemModel) throws Exception {
		update("PkgStatusVerifyDAO.to_pkgmaster_verifyinfo", verifyTemModel);
	}
	
	public VerifyTemModel getVerifyYN_frompkg(String pkgseq) throws Exception {
		return (VerifyTemModel) read("PkgStatusVerifyDAO.getVerifyYN_frompkg", pkgseq);
	}
	
	@SuppressWarnings("unchecked")
	public List<VerifyTemModel> readVerify_result(VerifyTemModel verifyTemModel) throws Exception {
		return (List<VerifyTemModel>) readList("PkgStatusVerifyDAO.readVerify_result", verifyTemModel);
	}
	
	public void delVerify_result(VerifyTemModel verifyTemModel) throws Exception {
		delete("PkgStatusVerifyDAO.delVerify_result", verifyTemModel);
	}
	
	public PkgModel readPKGVerify_info(PkgModel pkgModel) throws Exception {
		return (PkgModel) read("PkgStatusVerifyDAO.readPKGVerify_info", pkgModel);
	}
	
	public void createPKGVerify_Info(PkgModel pkgModel) throws Exception {
		create("PkgStatusVerifyDAO.createPKGVerify_Info", pkgModel);
	}
	
	public void updatePKGVerify_Info(PkgModel pkgModel) throws Exception {
		create("PkgStatusVerifyDAO.updatePKGVerify_Info", pkgModel);
	}
	
	public int getPKG_verify_gubun(PkgModel pkgModel) throws Exception {
		@SuppressWarnings("unchecked")
		int pkg_verify_gubun = (Integer) read("PkgStatusVerifyDAO.getPKG_verify_gubun", pkgModel);
		return pkg_verify_gubun;
	}
}
