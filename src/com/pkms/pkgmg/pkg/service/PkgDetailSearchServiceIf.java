package com.pkms.pkgmg.pkg.service;

import java.util.List;

import com.pkms.pkgmg.pkg.model.PkgDetailModel;
import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * PKG 보완적용내역 검색 관련 service<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
public interface PkgDetailSearchServiceIf {
	
	/**
	 * 보완적용내역 목록 조회
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public List<PkgDetailModel> readList(PkgModel pkgModel) throws Exception;
	
	/**
	 * 보완적용내역 상세 조회
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel read(PkgModel pkgModel) throws Exception;
}
