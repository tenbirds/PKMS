package com.pkms.pkgmg.pkg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.pkgmg.pkg.dao.PkgDetailDAO;
import com.pkms.pkgmg.pkg.model.PkgDetailModel;
import com.pkms.pkgmg.pkg.model.PkgDetailVariableModel;
import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * 보완적용내역 관련 Service
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Service("PkgDetailService")
public class PkgDetailService implements PkgDetailServiceIf {

	@Resource(name = "PkgDetailDAO")
	private PkgDetailDAO pkgDetailDAO;

	@Override
	public String create(PkgDetailModel model) throws Exception {
		return pkgDetailDAO.create(model);
	}
	
	@Override
	public List<PkgDetailModel> readList(PkgModel model) throws Exception {
		return pkgDetailDAO.readList(model);
	}

	@Override
	public List<PkgDetailVariableModel> variable_readList(PkgDetailModel model) throws Exception {
		return pkgDetailDAO.read(model);
	}
	
	@Override
	public void delete(PkgModel model) throws Exception {
		pkgDetailDAO.delete(model);
	}
	
	
	//보완적용내역 신규/보완/개선 구분값 수정 추가 1002 - ksy
	@Override
	public void updateNPC_gubun(PkgDetailModel model) throws Exception {
		pkgDetailDAO.updateNPC_gubun(model);
	}

}
