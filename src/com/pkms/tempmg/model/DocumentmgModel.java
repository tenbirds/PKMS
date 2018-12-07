package com.pkms.tempmg.model;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

public class DocumentmgModel extends AbstractModel{
	
//	public PkgCheckListManagerModel() {
//		setSearchConditionsList();
//	}
	
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
		
	
	private String doc_seq;
	private String doc_name;
	private String doc_type;
	private String doc_type_name;
	private String doc_version;
	private String file_extension;
//	private String file_size;
	private String file_path;
	private String file_org_name;
	private String file_name;
	private String reg_user_id;
	private String reg_user_name;  
	private String reg_date;
	private String update_user_id;
	private String update_user_name;
	private String update_date;
	
	private String prefix;
	private String[] chk_doc_seq;
	
	
	/** code */	
	private String gubun;
	private String common_code;
	private String name;
	private String col1;
	private String col2;
	private String col3;
	private String col4;
	private int ord;
	private String remark;
	private String use_yn;
	
	public final String EXCEL_FILE_NAME = "검증항목 현황";
	


	private List<CommonsMultipartFile> files;
	public long file_size = 0L;
	
	
	
	
	/** 검색조건*/
//	private String searchCondition;
//	private String searchKeyword = "";
//	private List<CodeModel> searchConditionsList;
//	private final String[][] searchConditions = new String[][] {{"0", "제목"}, {"1", "내용"}, {"2", "이름"}};
//	
////	private String deleteList;	
//	
//	public List<CodeModel> getSearchConditionsList() {
//		return searchConditionsList;
//	}
//	public void setSearchConditionsList() {
//		this.searchConditionsList = CodeUtil.convertCodeModel(searchConditions);
//	}
//	public String getSearchCondition() {
//		return searchCondition;
//	}
//	public void setSearchCondition(String searchCondition) {
//		this.searchCondition = searchCondition;
//	}
	
	
	
	/** code  start */		
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getCommon_code() {
		return common_code;
	}
	public void setCommon_code(String common_code) {
		this.common_code = common_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		this.col4 = col4;
	}
	public int getOrd() {
		return ord;
	}
	public void setOrd(int ord) {
		this.ord = ord;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}	
	/** code  end */	
	
	
	

	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getReg_user_name() {
		return reg_user_name;
	}
	public void setReg_user_name(String reg_user_name) {
		this.reg_user_name = reg_user_name;
	}
	public String getUpdate_user_name() {
		return update_user_name;
	}
	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}
	public String getDoc_seq() {
		return doc_seq;
	}
	public void setDoc_seq(String doc_seq) {
		this.doc_seq = doc_seq;
	}
	public String getDoc_name() {
		return doc_name;
	}
	public void setDoc_name(String doc_name) {
		this.doc_name = doc_name;
	}
	public String getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}
	public String getDoc_type_name() {
		return doc_type_name;
	}
	public void setDoc_type_name(String doc_type_name) {
		this.doc_type_name = doc_type_name;
	}
	public String getDoc_version() {
		return doc_version;
	}
	public void setDoc_version(String doc_version) {
		this.doc_version = doc_version;
	}
	public String getFile_extension() {
		return file_extension;
	}
	public void setFile_extension(String file_extension) {
		this.file_extension = file_extension;
	}
//	public String getFile_size() {
//		return file_size;
//	}
//	public void setFile_size(String file_size) {
//		this.file_size = file_size;
//	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getFile_org_name() {
		return file_org_name;
	}
	public void setFile_org_name(String file_org_name) {
		this.file_org_name = file_org_name;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getReg_user_id() {
		return reg_user_id;
	}
	public void setReg_user_id(String reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public String getUpdate_user_id() {
		return update_user_id;
	}
	public void setUpdate_user_id(String update_user_id) {
		this.update_user_id = update_user_id;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String[] getChk_doc_seq() {
		return chk_doc_seq;
	}
	public void setChk_doc_seq(String[] chk_doc_seq) {
		this.chk_doc_seq = chk_doc_seq;
	}
	public List<CommonsMultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<CommonsMultipartFile> files) {
		this.files = files;
	}
	public long getFile_size() {
		return file_size;
	}
	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}
	public String getEXCEL_FILE_NAME() {
		return EXCEL_FILE_NAME;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
