package com.pkms.pkgmg.pkg.service;

import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * 첨부파일 관련 Service
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
public interface PkgTab2ServiceIf {

	/**
	 * 첨부파일 조회
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel read(PkgModel pkgModel) throws Exception;
	
	/**
	 * 첨부파일 수정
	 * 
	 * @param pkgModel
	 * @throws Exception
	 */
	public void update(PkgModel pkgModel) throws Exception;
	
	/**
	 * pkg 로드맵 생성 & 수정
	 * @return 
	 */
	public PkgModel roadMapUpdate(PkgModel pkgModel) throws Exception;

}
