package com.pkms.pkgmg.pkg.service;

import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * PKG NewPnCr 관련 service<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
public interface PkgDetailNewPnCrServiceIf {
	
	/**
	 * NewPnCr 목록 조회
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel readList(PkgModel pkgModel) throws Exception;
	
	/**
	 * NewPnCr 매핑
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void update(PkgModel pkgModel) throws Exception;
}
