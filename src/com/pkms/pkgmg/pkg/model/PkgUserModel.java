package com.pkms.pkgmg.pkg.model;

import com.pkms.common.model.AbstractModel;

/**
 * 
 */
public class PkgUserModel extends AbstractModel {
	private static final long serialVersionUID = 1L;

	/**
	 * PKG seq
	 */
	private String pkg_seq = "";
	
	/**
	 * 담당 구분
	 */
	private String charge_gubun = "";
	
	/**
	 * 승인 차수
	 */
	private String ord = "";
	
	/**
	 * 승인 상태
	 */
	private String status = "";
	
	/**
	 * comment
	 */
	private String au_comment = "";
	
	/**
	 * 사용자 ID
	 */
	private String user_id = "";
	
	/**
	 * 사용자 이름
	 */
	private String user_name = "";
	
	/**
	 * 사용자 전화번호
	 */
	private String user_phone = "";
	
	/**
	 * 사용자 메일
	 */
	private String user_email = "";
	
	/**
	 * 사용자 부서
	 */
	private String sosok = "";
	
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
	 * 마지막 여부
	 */
	private String fin = "";

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public String getAu_comment() {
		return au_comment;
	}

	public void setAu_comment(String au_comment) {
		this.au_comment = au_comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getCharge_gubun() {
		return charge_gubun;
	}

	public void setCharge_gubun(String charge_gubun) {
		this.charge_gubun = charge_gubun;
	}

	public String getPkg_seq() {
		return pkg_seq;
	}

	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public String getSosok() {
		return sosok;
	}

	public void setSosok(String sosok) {
		this.sosok = sosok;
	}

}
