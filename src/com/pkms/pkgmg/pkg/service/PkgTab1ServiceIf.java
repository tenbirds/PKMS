package com.pkms.pkgmg.pkg.service;

import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * 기본정보 관련 Service
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
public interface PkgTab1ServiceIf {

	/**
	 * Pkg 기본정보 등록
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void create(PkgModel pkgModel) throws Exception;

	/**
	 * Pkg 기본정보 수정
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void update(PkgModel pkgModel) throws Exception;
}
