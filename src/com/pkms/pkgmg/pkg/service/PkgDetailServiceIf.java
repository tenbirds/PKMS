package com.pkms.pkgmg.pkg.service;

import java.util.List;

import com.pkms.pkgmg.pkg.model.PkgDetailModel;
import com.pkms.pkgmg.pkg.model.PkgDetailVariableModel;
import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * 보완적용내역 관련 Service
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
public interface PkgDetailServiceIf {

	/**
	 * Pkg detail 등록<br/>
	 *  - 엑셀 업로드 시 호출
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String create(PkgDetailModel model) throws Exception;

	/**
	 * Pkg detail 목록 조회<br/>
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<PkgDetailModel> readList(PkgModel model) throws Exception;

	/**
	 * Pkg detail 상세 조회<br/>
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<PkgDetailVariableModel> variable_readList(PkgDetailModel model) throws Exception;
	
	/**
	 * detail_delete 삭제
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void delete(PkgModel model) throws Exception;
	
	//보완적용내역 신규/보완/개선 구분값 수정 추가 1002 - ksy
	public void updateNPC_gubun(PkgDetailModel model) throws Exception;
}
