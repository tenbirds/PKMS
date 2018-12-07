package com.pkms.pkgmg.pkg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.pkgmg.pkg.dao.PkgDetailSearchDAO;
import com.pkms.pkgmg.pkg.model.PkgDetailModel;
import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * PKG 보완적용내역 검색 관련 service<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Service("PkgDetailSearchService")
public class PkgDetailSearchService implements PkgDetailSearchServiceIf {

	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;
	
	@Resource(name = "PkgDetailVariableService")
	private PkgDetailVariableServiceIf pkgDetailVariableService;

	@Resource(name = "PkgDetailSearchDAO")
	private PkgDetailSearchDAO pkgDetailSearchDAO;

	@Override
	public List<PkgDetailModel> readList(PkgModel pkgModel) throws Exception {
		pkgModel.setTotalCount(pkgDetailSearchDAO.readTotalCount(pkgModel));

		return pkgDetailSearchDAO.readList(pkgModel);
	}

	@Override
	public PkgModel read(PkgModel pkgModel) throws Exception {
		PkgModel pkgModelData = pkgService.read4Master(pkgModel);
		
		pkgModelData.setPkg_detail_seq(pkgModel.getPkg_detail_seq());
		pkgModelData = pkgDetailVariableService.read(pkgModelData);
		return pkgModelData;
	}
}
