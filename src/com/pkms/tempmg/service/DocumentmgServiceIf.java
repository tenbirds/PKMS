package com.pkms.tempmg.service;

import java.util.List;

import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.tempmg.model.DocumentmgModel;

public interface DocumentmgServiceIf {


	public List<?> readList(DocumentmgModel documentmgmodel) throws Exception;
	public List<?> codeList(DocumentmgModel documentmgmodel) throws Exception;
	
	public DocumentmgModel readone(DocumentmgModel documentmgmodel) throws Exception;
	public DocumentmgModel insert(DocumentmgModel documentmgmodel) throws Exception;
	public void delete(DocumentmgModel documentmgmodel) throws Exception;
	public void update(DocumentmgModel documentmgmodel) throws Exception;
	
	
	/**
	 * 목록 엑셀다운로드
	 * @param newpncrModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String excelDownload(DocumentmgModel documentmgmodel) throws Exception;
	
	
//	public String doc_file_add(DocumentmgModel documentmgmodel  ,String prefix) throws Exception;
	public String doc_file_add(SystemFileModel systemFileModel  ,String prefix) throws Exception;
	
	
	public String doc_file_del(DocumentmgModel documentmgmodel ,String prefix) throws Exception;
	
	public void deleteList(DocumentmgModel documentmgmodel) throws Exception;
	
	public int docfileIdx(DocumentmgModel documentmgmodel) throws Exception;
}
