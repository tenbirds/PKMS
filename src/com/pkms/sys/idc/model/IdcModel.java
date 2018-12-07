package com.pkms.sys.idc.model;

import java.util.List;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

public class IdcModel extends AbstractModel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 국사SEQ
	 */
	private String idc_seq = "";

	/**
	 * 팀SEQ
	 */
	private String team_seq = "";

	/**
	 * 본부SEQ
	 */
	private String central_seq = "";

	/**
	 * 지역CODE
	 */
	private String location_code = "";

	/**
	 * 국사명
	 */
	private String idc_name = "";

	/**
	 * 팀명
	 */
	private String team_name = "";

	/**
	 * 본부명
	 */
	private String central_name = "";

	/**
	 * 사용여부
	 */
	private String use_yn = "";

	/**
	 * 작성자
	 */
	private String reg_user = "";

	/**
	 * 작성일
	 */
	private String reg_date = "";

	/**
	 * 수정자
	 */
	private String update_user = "";

	/**
	 * 수정일
	 */
	private String update_date = "";
	
	private String system_seq;

	public IdcModel() {
		// setSearchConditionsList();
	}

	public String getIdc_seq() {
		return idc_seq;
	}

	public void setIdc_seq(String idc_seq) {
		this.idc_seq = idc_seq;
	}

	public String getTeam_seq() {
		return team_seq;
	}

	public void setTeam_seq(String team_seq) {
		this.team_seq = team_seq;
	}

	public String getCentral_seq() {
		return central_seq;
	}

	public void setCentral_seq(String central_seq) {
		this.central_seq = central_seq;
	}

	public String getLocation_code() {
		return location_code;
	}

	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}

	public String getIdc_name() {
		return idc_name;
	}

	public void setIdc_name(String idc_name) {
		this.idc_name = idc_name;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getCentral_name() {
		return central_name;
	}

	public void setCentral_name(String central_name) {
		this.central_name = central_name;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
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

	/**
	 * 검색조건 관련
	 */
	private String searchCondition;
	private String searchKeyword = "";

	private List<CodeModel> searchConditionsList;
	private final String[][] searchConditions = new String[][] { { "0", "국사명" }, { "1", "지역코드" } };

	public String getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	public List<CodeModel> getSearchConditionsList() {
		return searchConditionsList;
	}

	public void setSearchConditionsList() {
		this.searchConditionsList = CodeUtil.convertCodeModel(searchConditions);
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getSystem_seq() {
		return system_seq;
	}

	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}

}
