package com.pkms.usermg.user.model;

import com.pkms.common.model.AbstractModel;

public class BpUserModel extends AbstractModel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 아이디
	 */
	private String bp_user_id = "";

	/**
	 * 업체 사업자등록번호
	 */
	private String bp_num = "";
	/**
	 * 업체 이름
	 */
	private String bp_name = "";

	
	/**
	 * 비번
	 */
	private String bp_user_pw = "";

	/**
	 * 이름
	 */
	private String bp_user_name = "";

	/**
	 * 핸드폰번호 첫번째 자리
	 */
	private String bp_user_phone1 = "";

	/**
	 * 핸드폰번호 두번째 자리
	 */
	private String bp_user_phone2 = "";

	/**
	 * 핸드폰번호 세번째 자리
	 */
	private String bp_user_phone3 = "";

	/**
	 * 이메일
	 */
	private String bp_user_email = "";

	/**
	 * 비번에러수
	 */
	private int passwd_err_cnt = 0;

	/**
	 * '승인상태(요청,완료)
	 */
	private String approval_state = "";

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
	
	/**
	 * 대/중/소/시스템
	 */
	private String group_depth = "";
	
	public BpUserModel() {

	}

	public BpUserModel(String bp_user_id, String bp_num, String bp_user_pw, String bp_user_name, String bp_user_phone1, String bp_user_phone2, String bp_user_phone3, String bp_user_email, int passwd_err_cnt, String approval_state, String use_yn, String reg_user, String reg_date, String update_user, String update_date, String group_depth) {
		super();
		this.bp_user_id = bp_user_id;
		this.bp_num = bp_num;
		this.bp_user_pw = bp_user_pw;
		this.bp_user_name = bp_user_name;
		this.bp_user_phone1 = bp_user_phone1;
		this.bp_user_phone2 = bp_user_phone2;
		this.bp_user_phone3 = bp_user_phone3;
		this.bp_user_email = bp_user_email;
		this.passwd_err_cnt = passwd_err_cnt;
		this.approval_state = approval_state;
		this.use_yn = use_yn;
		this.reg_user = reg_user;
		this.reg_date = reg_date;
		this.update_user = update_user;
		this.update_date = update_date;
		this.group_depth = group_depth;
	}

	public String getBp_user_id() {
		return bp_user_id;
	}

	public void setBp_user_id(String bp_user_id) {
		this.bp_user_id = bp_user_id;
	}

	public String getBp_num() {
		return bp_num;
	}

	public void setBp_num(String bp_num) {
		this.bp_num = bp_num;
	}

	public String getBp_user_pw() {
		return bp_user_pw;
	}

	public void setBp_user_pw(String bp_user_pw) {
		this.bp_user_pw = bp_user_pw;
	}

	public String getBp_user_name() {
		return bp_user_name;
	}

	public void setBp_user_name(String bp_user_name) {
		this.bp_user_name = bp_user_name;
	}

	public String getBp_user_phone1() {
		return bp_user_phone1;
	}

	public void setBp_user_phone1(String bp_user_phone1) {
		this.bp_user_phone1 = bp_user_phone1;
	}

	public String getBp_user_phone2() {
		return bp_user_phone2;
	}

	public void setBp_user_phone2(String bp_user_phone2) {
		this.bp_user_phone2 = bp_user_phone2;
	}

	public String getBp_user_phone3() {
		return bp_user_phone3;
	}

	public void setBp_user_phone3(String bp_user_phone3) {
		this.bp_user_phone3 = bp_user_phone3;
	}

	public String getBp_user_email() {
		return bp_user_email;
	}

	public void setBp_user_email(String bp_user_email) {
		this.bp_user_email = bp_user_email;
	}

	public int getPasswd_err_cnt() {
		return passwd_err_cnt;
	}

	public void setPasswd_err_cnt(int passwd_err_cnt) {
		this.passwd_err_cnt = passwd_err_cnt;
	}

	public String getApproval_state() {
		return approval_state;
	}

	public void setApproval_state(String approval_state) {
		this.approval_state = approval_state;
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
	public String getBp_name() {
		return bp_name;
	}

	public void setBp_name(String bp_name) {
		this.bp_name = bp_name;
	}

	public String getGroup_depth() {
		return group_depth;
	}

	public void setGroup_depth(String group_depth) {
		this.group_depth = group_depth;
	}

}
