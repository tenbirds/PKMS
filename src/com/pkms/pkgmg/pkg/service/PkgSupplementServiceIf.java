package com.pkms.pkgmg.pkg.service;

import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * 보완적용내용 등록/수정 관련 service<br/>
 *  - 엑셀업로드 등
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
public interface PkgSupplementServiceIf {

	/**
	 * 엑셀 검사 통과 후 저장
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void create(PkgModel pkgModel) throws Exception;

	/**
	 * 엑셀 유효성 검사 및 업로드
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void excelUpload(PkgModel pkgModel) throws Exception;

	/**
	 * 템플릿 다운로드
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public String templateDownload(PkgModel pkgModel) throws Exception;

	/**
	 * 등록된 데이터 다운로드
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public String excelDownload(PkgModel pkgModel) throws Exception;

}
