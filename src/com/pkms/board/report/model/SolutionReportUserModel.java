package com.pkms.board.report.model;

import com.pkms.common.model.AbstractModel;

public class SolutionReportUserModel extends AbstractModel{
	private static final long serialVersionUID = 1L;
	
	private String solution_report_seq;
	
	private String reg_user;
	private String reg_date;
	
	private String update_user;
	private String update_name;
	private String update_date;
	
	private String user_id;
	private String user_name;
	private String user_phone;
	private String user_email;
	private String sosok;
	
	private String use_yn;
	private String status_yn;
	private String au_comment;
	
	private String indept;
	private String confirm_yn;
	private String system_seq;
	private String sosok_comment;
	private String charge_gubun_name;

	public String getSolution_report_seq() {
		return solution_report_seq;
	}

	public void setSolution_report_seq(String solution_report_seq) {
		this.solution_report_seq = solution_report_seq;
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

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public String getSosok() {
		return sosok;
	}

	public void setSosok(String sosok) {
		this.sosok = sosok;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public String getAu_comment() {
		return au_comment;
	}

	public void setAu_comment(String au_comment) {
		this.au_comment = au_comment;
	}

	public String getStatus_yn() {
		return status_yn;
	}

	public void setStatus_yn(String status_yn) {
		this.status_yn = status_yn;
	}

	public String getUpdate_name() {
		return update_name;
	}

	public void setUpdate_name(String update_name) {
		this.update_name = update_name;
	}

	public String getIndept() {
		return indept;
	}

	public void setIndept(String indept) {
		this.indept = indept;
	}

	public String getConfirm_yn() {
		return confirm_yn;
	}

	public void setConfirm_yn(String confirm_yn) {
		this.confirm_yn = confirm_yn;
	}

	public String getSystem_seq() {
		return system_seq;
	}

	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}

	public String getSosok_comment() {
		return sosok_comment;
	}

	public void setSosok_comment(String sosok_comment) {
		this.sosok_comment = sosok_comment;
	}

	public String getCharge_gubun_name() {
		return charge_gubun_name;
	}

	public void setCharge_gubun_name(String charge_gubun_name) {
		this.charge_gubun_name = charge_gubun_name;
	}
}
