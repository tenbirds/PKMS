package com.pkms.pkgmg.pkg.service;

import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * PKG 복사 관련 Service<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
public interface PkgCopyServiceIf {
	
	/**
	 * 복사
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public String create(PkgModel pkgModel) throws Exception;

}
