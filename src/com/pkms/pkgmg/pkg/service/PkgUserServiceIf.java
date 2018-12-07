package com.pkms.pkgmg.pkg.service;

import java.util.List;

import com.pkms.pkgmg.pkg.model.PkgUserModel;

/**
 * PKG 승인자 관련 Service
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
public interface PkgUserServiceIf {
	
	/**
	 * 승인자 설정
	 * 
	 * @param pkgUserModel
	 * @throws Exception
	 */
	public void create(PkgUserModel pkgUserModel) throws Exception;

	/**
	 * 승인자 목록 조회
	 * 
	 * @param pkgUserModel
	 * @return
	 * @throws Exception
	 */
	public List<PkgUserModel> readList(PkgUserModel pkgUserModel) throws Exception;

	/**
	 * 진행 중인 승인자 조회
	 * 
	 * @param pkgUserModel
	 * @return
	 * @throws Exception
	 */
	public PkgUserModel readActive(PkgUserModel pkgUserModel) throws Exception;

	/**
	 * 승인 처리
	 * 
	 * @param pkgUserModel
	 * @return
	 * @throws Exception
	 */
	public int update(PkgUserModel pkgUserModel) throws Exception;
	
	/**
	 * 승인자 삭제<br/>
	 * - 승인자 목록에서 수정 시 all delete-insert 때문에 필요
	 * 
	 * @param pkgUserModel
	 * @throws Exception
	 */
	public void delete(PkgUserModel pkgUserModel) throws Exception;
}
