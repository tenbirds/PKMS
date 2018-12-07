package com.pkms.verify_tem_mg.model;

import java.util.List;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

public class PkgCheckListManagerModel extends AbstractModel{
	
//	public PkgCheckListManagerModel() {
//		setSearchConditionsList();
//	}
	
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String chk_seq;
	private String status;     
	private String title;  
	private String chk_content;
	private String reg_user;    
	private String reg_user_name; 
	private String reg_date;   
	private String chk_type;
	private String update_user;
	private String update_user_name;
	private String update_date;
	private String answer_type;
	private String chk_type_name;
	private String stdate;
	private String eddate;
	private String position;
	
	
	
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
	
	
	

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReg_user() {
		return reg_user;
	}
	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getChk_seq() {
		return chk_seq;
	}
	public void setChk_seq(String chk_seq) {
		this.chk_seq = chk_seq;
	}
	public String getChk_type() {
		return chk_type;
	}
	public void setChk_type(String chk_type) {
		this.chk_type = chk_type;
	}
	public String getChk_content() {
		return chk_content;
	}
	public void setChk_content(String chk_content) {
		this.chk_content = chk_content;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getAnswer_type() {
		return answer_type;
	}
	public void setAnswer_type(String answer_type) {
		this.answer_type = answer_type;
	}
	public String getChk_type_name() {
		return chk_type_name;
	}
	public void setChk_type_name(String chk_type_name) {
		this.chk_type_name = chk_type_name;
	}
	public String getStdate() {
		return stdate;
	}
	public void setStdate(String stdate) {
		this.stdate = stdate;
	}
	public String getEddate() {
		return eddate;
	}
	public void setEddate(String eddate) {
		this.eddate = eddate;
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
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
