package com.pkms.pkgmg.pkg.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * PKG 복사 관련 Service<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Service("PkgCopyService")
public class PkgCopyService implements PkgCopyServiceIf {

	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;

	@Resource(name = "PkgTab1Service")
	private PkgTab1ServiceIf pkgTab1Service;

	@Resource(name = "PkgTab2Service")
	private PkgTab2ServiceIf pkgTab2Service;

	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;

	@Override
	public String create(PkgModel pkgModel) throws Exception {
		//기본정보 복사
		pkgModel = pkgService.read4Master(pkgModel);
		pkgModel.setPkg_seq("copy");
		pkgModel.setStatus("0");//임시저장으로
		pkgModel.setTitle("사본_"+pkgModel.getTitle());
		
		pkgTab1Service.create(pkgModel);

		//파일복사
		fileManageService.copy(pkgModel);
		pkgTab2Service.update(pkgModel);
		
		//생성된 pkg_seq 리턴
		return pkgModel.getPkg_seq();
	}


}
