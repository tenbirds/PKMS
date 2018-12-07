package com.pkms.pkgmg.pkg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.newpncrmg.model.NewpncrModel;
import com.pkms.newpncrmg.service.NewpncrServiceIf;
import com.pkms.pkgmg.pkg.dao.PkgDetailNewPnCrDAO;
import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * PKG NewPnCr 관련 service<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Service("PkgDetailNewPnCrService")
public class PkgDetailNewPnCrService implements PkgDetailNewPnCrServiceIf {

	@Resource(name = "NewpncrService")
	private NewpncrServiceIf newpncrService;

	@Resource(name = "PkgDetailNewPnCrDAO")
	private PkgDetailNewPnCrDAO pkgDetailNewPnCrDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	public PkgModel readList(PkgModel pkgModel) throws Exception {
		NewpncrModel newpncrModel = new NewpncrModel();
		newpncrModel.setSearchNew_pncr_gubun(pkgModel.getNewPnCr());
		newpncrModel.setSearch_system_seq(pkgModel.getSystem_seq());
		newpncrModel.setSearch_state("3");//정제완료인 것만
		newpncrModel.setSearchCondition("0");
		newpncrModel.setSearchRoleCondition("2");
		newpncrModel.setSystem_search_gubun("4");// 시스템seq를 쿼리에 set하기 위한 구분 값 고정.
		newpncrModel.setPaging(false);
		newpncrModel.setSearchKeyword(pkgModel.getNewPnCr_title());
		pkgModel.setNewpncrModelList((List<NewpncrModel>) newpncrService.readList(newpncrModel));
		return pkgModel;
	}

	@Override
	public void update(PkgModel pkgModel) throws Exception {
		pkgDetailNewPnCrDAO.update(pkgModel);
	}
	

}
