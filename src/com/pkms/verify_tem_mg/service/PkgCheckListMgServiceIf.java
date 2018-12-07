package com.pkms.verify_tem_mg.service;

import java.util.List;

import com.pkms.verify_tem_mg.model.PkgCheckListManagerModel;

public interface PkgCheckListMgServiceIf {


	public List<?> readList(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception;
	public List<?> codeList(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception;
	
	public PkgCheckListManagerModel readone(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception;
	public PkgCheckListManagerModel insert(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception;
	public void delete(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception;
	public void update(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception;
	
	
	/**
	 * 목록 엑셀다운로드
	 * @param newpncrModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String excelDownload(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception;
}
