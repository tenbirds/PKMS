package com.pkms.pkgmg.pkg.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pkms.pkgmg.pkg.dao.PkgStatusVerifyDao;
import com.pkms.pkgmg.pkg.dao.PkgSnetDao;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.verify_tem_mg.model.VerifyTemModel;
import com.wings.properties.service.PropertyServiceIf;

@Service("PkgStatusVerifyService")
public class PkgStatusVerifyService implements PkgStatusVerifyServiceIf{
	static Logger logger = Logger.getLogger(PkgStatusService.class);
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;

	@Resource(name = "PkgStatusVerifyDao")
	private PkgStatusVerifyDao pkgStatusVerifyDao;
	
	@Resource(name = "PkgSnetDao")
	private PkgSnetDao pkgSnetDao;
	
	public List<VerifyTemModel> getList_verifyTem(VerifyTemModel verifyTemModel) throws Exception{
		List<VerifyTemModel> readList = pkgStatusVerifyDao.getList_verifyTem(verifyTemModel);
		return readList;
	}
	
	public List<VerifyTemModel> getList_verifyQuest(VerifyTemModel verifyTemModel) throws Exception{
		List<VerifyTemModel> readList = pkgStatusVerifyDao.getList_verifyQuest(verifyTemModel);
		return readList;
	}
	
	public List<VerifyTemModel> getList_verifyItem(VerifyTemModel verifyTemModel) throws Exception{
		List<VerifyTemModel> readList = pkgStatusVerifyDao.getList_verifyItem(verifyTemModel);
		return readList;
	}
	
	public void create_verifyResult(VerifyTemModel verifyTemModel) throws Exception{
		pkgStatusVerifyDao.create_verifyResult(verifyTemModel);
	}
	
	public void to_pkgmaster_verifyinfo(VerifyTemModel verifyTemModel) throws Exception{
		pkgStatusVerifyDao.to_pkgmaster_verifyinfo(verifyTemModel);
	}
	
	public VerifyTemModel getVerifyYN_frompkg(String pkgseq) throws Exception{
		VerifyTemModel verifyinfo = pkgStatusVerifyDao.getVerifyYN_frompkg(pkgseq);
		return verifyinfo;
	}
	
	public List<VerifyTemModel> readVerify_result(VerifyTemModel verifyTemModel) throws Exception{
		List<VerifyTemModel> readList = pkgStatusVerifyDao.readVerify_result(verifyTemModel);
		return readList;
	}
	
	public void delVerify_result(VerifyTemModel verifyTemModel) throws Exception{
		pkgStatusVerifyDao.delVerify_result(verifyTemModel);
	}
	
	public PkgModel readPKGVerify_info(PkgModel pkgModel) throws Exception{
		PkgModel pkgModelInfo =  pkgStatusVerifyDao.readPKGVerify_info(pkgModel);
		return pkgModelInfo;
	}
	
	public void createPKGVerify_Info(PkgModel pkgModel) throws Exception{
		pkgStatusVerifyDao.createPKGVerify_Info(pkgModel);
	}
	
	public void updatePKGVerify_Info(PkgModel pkgModel) throws Exception{
		pkgStatusVerifyDao.updatePKGVerify_Info(pkgModel);
	}
	
	public int getPKG_verify_gubun(PkgModel pkgModel) throws Exception{
		int pkg_verify_gubun = pkgStatusVerifyDao.getPKG_verify_gubun(pkgModel);
		return pkg_verify_gubun;
	}
	
	public int getAudit_Rate(VerifyTemModel verifyTemModel) throws Exception{
		return pkgSnetDao.getAudit_Rate(verifyTemModel);
	}

}
