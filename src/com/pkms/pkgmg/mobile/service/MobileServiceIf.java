package com.pkms.pkgmg.mobile.service;

import java.util.List;

import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * 모바일용 서비스
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
public interface MobileServiceIf {
	
	/**
	 * 상세 조회
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel read(PkgModel pkgModel) throws Exception;

	/**
	 * 목록 조회
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public List<PkgModel> readList(PkgModel pkgModel) throws Exception;
	
	/**
	 * 보완적용내역 목록
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public List<PkgModel> readList_bp_step3(PkgModel pkgModel) throws Exception;
	
	/**
	 * 검증완료 목록
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel read_skt_step3(PkgModel pkgModel) throws Exception;
	
	/**
	 * 검증진도율
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel read_progress(PkgModel pkgModel) throws Exception;
}
