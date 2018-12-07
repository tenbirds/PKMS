package com.pkms.pkgmg.mobile.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.pkgmg.mobile.dao.MobileDAO;
import com.pkms.pkgmg.pkg.dao.PkgDAO;
import com.pkms.pkgmg.pkg.model.PkgDetailModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgStatusModel;

/**
 * 모바일용 서비스
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Service("MobileService")
public class MobileService implements MobileServiceIf {

	@Resource(name = "MobileDAO")
	private MobileDAO mobileDAO;
	
	@Resource(name = "PkgDAO")
	private PkgDAO pkgDAO;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;

	@Override
	public PkgModel read(PkgModel pkgModel) throws Exception {	
		pkgModel = mobileDAO.read(pkgModel);
		
		if (pkgModel.getMaster_file_id() != null && !"".equals(pkgModel.getMaster_file_id())) {
			// 첨부 파일 정보 세팅
			fileManageService.read(pkgModel);
		}
		
//		return mobileDAO.read(pkgModel);
		return pkgModel;
	}

	@Override
	public List<PkgModel> readList(PkgModel pkgModel) throws Exception {
		return mobileDAO.readList(pkgModel);
	}
	
	@Override
	public List<PkgModel> readList_bp_step3(PkgModel pkgModel) throws Exception {
		return mobileDAO.readList_bp_step3(pkgModel);
	}
	
	@Override
	public PkgModel read_skt_step3(PkgModel pkgModel) throws Exception {
		return mobileDAO.read_skt_step3(pkgModel);
	}

	@Override
	public PkgModel read_progress(PkgModel pkgModel) throws Exception {
		return mobileDAO.read_progress(pkgModel);
	}
}
