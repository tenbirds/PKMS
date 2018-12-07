package com.pkms.pkgmg.pkg.model;

import com.pkms.common.model.AbstractModel;

/**
 * Pkg 검증요청 보완적용내역<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 26.
 * 
 */
public class PkgDetailModel extends AbstractModel {
	private static final long serialVersionUID = 1L;

	/**
	 * PKG seq
	 */
	private String pkg_seq = "";
	
	/**
	 * PKG Detail seq
	 */
	private String pkg_detail_seq ="";
	
	/**
	 * System seq
	 */
	private String system_seq ="";

	/**
	 * no
	 */
	private String no = "";
	
	/**
	 * NewPnCr 구분
	 */
	private String new_pn_cr_gubun = "";
	
	private String new_pn_cr_gubun_dev = "";
	
	/**
	 * NewPnCr seq
	 */
	private int new_pn_cr_seq = 0;
	
	/**
	 * NewPnCr gubun + seq
	 */
	private String new_pn_cr_no = "0";
	
	/**
	 * 유효성 결과
	 */
	private String validation_result = "";
	
	/**
	 * 유효성 결과 내용
	 */
	private String validation_result_content = "";

	/**
	 * 사용여부
	 */
	private String use_yn = "";

	/**
	 * 등록자
	 */
	private String reg_user = "";
	
	/**
	 * 등록일
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

	/**
	 * 제목
	 */
	private String title = "";
	
	/**
	 * 내용
	 */
	private String content = "";
	
	//-----------------------------------20120831 인수시험 테스트 추가사항-------------------------
	/**
	 * 중요도
	 */
	private String importance = "";
	
	/**
	 * 상용 검증 결과
	 */
	private String commercial_validation = "";
	
	/**
	 * 요구분류
	 */
	private String require_category = "";
	
	/**
	 * 검증분야개수
	 */
	private String verification_sector = "";
	
	/**
	 * 자체검증결과
	 */
	private String self_validation = "";
	//-----------------------------------20120831 인수시험 테스트 추가사항 여기까지-------------------------
	
	public String getPkg_seq() {
		return pkg_seq;
	}
	public String getValidation_result() {
		return validation_result;
	}
	public void setValidation_result(String validation_result) {
		this.validation_result = validation_result;
	}
	public String getValidation_result_content() {
		return validation_result_content;
	}
	public void setValidation_result_content(String validation_result_content) {
		this.validation_result_content = validation_result_content;
	}
	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}
	public String getPkg_detail_seq() {
		return pkg_detail_seq;
	}
	public void setPkg_detail_seq(String pkg_detail_seq) {
		this.pkg_detail_seq = pkg_detail_seq;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getNew_pn_cr_gubun() {
		return new_pn_cr_gubun;
	}
	public void setNew_pn_cr_gubun(String new_pn_cr_gubun) {
		this.new_pn_cr_gubun = new_pn_cr_gubun;
	}
	public String getNew_pn_cr_gubun_dev() {
		return new_pn_cr_gubun_dev;
	}
	public void setNew_pn_cr_gubun_dev(String new_pn_cr_gubun_dev) {
		this.new_pn_cr_gubun_dev = new_pn_cr_gubun_dev;
	}
	public int getNew_pn_cr_seq() {
		return new_pn_cr_seq;
	}
	public void setNew_pn_cr_seq(int new_pn_cr_seq) {
		this.new_pn_cr_seq = new_pn_cr_seq;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSystem_seq() {
		return system_seq;
	}
	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}
	public String getNew_pn_cr_no() {
		return new_pn_cr_no;
	}
	public void setNew_pn_cr_no(String new_pn_cr_no) {
		this.new_pn_cr_no = new_pn_cr_no;
	}
	public String getImportance() {
		return importance;
	}
	public void setImportance(String importance) {
		this.importance = importance;
	}
	public String getCommercial_validation() {
		return commercial_validation;
	}
	public void setCommercial_validation(String commercial_validation) {
		this.commercial_validation = commercial_validation;
	}
	public String getRequire_category() {
		return require_category;
	}
	public void setRequire_category(String require_category) {
		this.require_category = require_category;
	}
	public String getVerification_sector() {
		return verification_sector;
	}
	public void setVerification_sector(String verification_sector) {
		this.verification_sector = verification_sector;
	}
	public String getSelf_validation() {
		return self_validation;
	}
	public void setSelf_validation(String self_validation) {
		this.self_validation = self_validation;
	}
	
}
