package com.pkms.pkgmg.pkg.service;

import com.pkms.pkgmg.pkg.model.PkgDetailVariableModel;
import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * PKG 보완적용내역 관련 Service<br/>
 * - 가변필드 포함
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
public interface PkgDetailVariableServiceIf {
	
	/**
	 * 보완적용내역 등록
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void create(PkgDetailVariableModel model) throws Exception;
	
	/**
	 * 보완적용내역 목록조회
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel read(PkgModel pkgModel) throws Exception;

	/**
	 * 보완적용내역 수정
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void update(PkgModel pkgModel) throws Exception;
	
	/**
	 * 보완적용내역 삭제
	 * @param pkgModel
	 * @throws Exception
	 */
	public void delete(PkgModel pkgModel) throws Exception;

	public void okNokUpdate(PkgModel pkgModel) throws Exception;

	public void insert(PkgModel pkgModel) throws Exception;
	
}
