package com.pkms.pkgmg.pkg.service;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgStatusModel;

/**
 * PKG 상태별 관련 Service<br/>
 * 
 * @author : 009
 * @Date : 2012. 5. 08.
 * 
 */
public interface PkgStatusServiceIf {

	/**
	 * 상태별 등록
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void create(PkgModel pkgModel) throws Exception;

	/**
	 * 상태별 조회
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel read(PkgModel pkgModel) throws Exception;
	
	public void createStatus4Verify(PkgStatusModel pkgStatusModel) throws Exception;
	public void updateStatus4Verify(PkgStatusModel pkgStatusModel) throws Exception;

}
