package com.pkms.pkgmg.pkg.service;

import java.util.List;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.verify_tem_mg.model.VerifyTemModel;

public interface PkgStatusVerifyServiceIf {
	
	public List<VerifyTemModel> getList_verifyTem(VerifyTemModel verifyTemModel) throws Exception;
	public List<VerifyTemModel> getList_verifyQuest(VerifyTemModel verifyTemModel) throws Exception;
	public List<VerifyTemModel> getList_verifyItem(VerifyTemModel verifyTemModel) throws Exception;
	public void create_verifyResult(VerifyTemModel verifyTemModel) throws Exception;
	public void to_pkgmaster_verifyinfo(VerifyTemModel verifyTemModel) throws Exception;
	public VerifyTemModel getVerifyYN_frompkg(String pkgseq) throws Exception;
	public List<VerifyTemModel> readVerify_result(VerifyTemModel verifyTemModel) throws Exception;
	public void delVerify_result(VerifyTemModel verifyTemModel) throws Exception;
	public PkgModel readPKGVerify_info(PkgModel pkgModel) throws Exception;
	public void createPKGVerify_Info(PkgModel pkgModel) throws Exception;
	public void updatePKGVerify_Info(PkgModel pkgModel) throws Exception;
	public int getPKG_verify_gubun(PkgModel pkgModel) throws Exception;
	public int getAudit_Rate(VerifyTemModel verifyTemModel) throws Exception;
}
