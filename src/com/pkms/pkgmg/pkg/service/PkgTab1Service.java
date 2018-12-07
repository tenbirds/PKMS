package com.pkms.pkgmg.pkg.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.pkgmg.pkg.dao.PkgStatusDAO;
import com.pkms.pkgmg.pkg.dao.PkgTab1DAO;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgStatusModel;

/**
 * 기본정보 관련 Service
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Service("PkgTab1Service")
public class PkgTab1Service implements PkgTab1ServiceIf {

	@Resource(name = "PkgTab1DAO")
	private PkgTab1DAO PkgTab1DAO;
	
	@Resource(name = "PkgStatusDAO")
	private PkgStatusDAO PkgStatusDAO;

	@Override
	public void create(PkgModel pkgModel) throws Exception {
		//복사
		String copy_gupun =  "";
		if(pkgModel.getPkg_seq().equals("copy")){
			copy_gupun="copy";
		}
		//등록
		pkgModel.setPkg_seq(PkgTab1DAO.create(pkgModel));
		
		if(copy_gupun.equals("copy")){
			PkgStatusModel pkgStatusModel = new PkgStatusModel();
			pkgStatusModel.setSession_user_id(pkgModel.getSession_user_id());
			pkgStatusModel.setPkg_seq(pkgModel.getPkg_seq());
			pkgStatusModel.setStatus("3");
			PkgStatusDAO.create(pkgStatusModel);
			pkgStatusModel.setStatus("22");
			PkgStatusDAO.create(pkgStatusModel);
			
		}
	}

	@Override
	public void update(PkgModel pkgModel) throws Exception {
		PkgTab1DAO.update(pkgModel);
	}
	
}
