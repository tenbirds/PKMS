package com.pkms.sys.system.model;

import com.pkms.common.model.AbstractModel;

public class SystemUserModel extends AbstractModel {

	public enum SYSTEM_USER_CHARGE_GUBUN {

		VU("VU", "상용 검증담당자"), AU("AU", "상용 승인담당자"), PU("PU", "사업계획 담당자"), BU("BU", "협력업체 담당자"), DV("DV", "개발 검증담당자"), DA("DA", "개발 승인담당자"),
		VO("VO", "용량 검증담당자"), SE("SE", "보안 검증담당자"), CH("CH", "과금 검증담당자"), NO("NO", "비기능 검증담당자"), LV("LV", "현장 담당자"), LA("LA", "현장 승인담당자"),
		VA("VA", "용량 검증 승인"), CA("CA", "과금 검증 승인"), MO("MO", "상황관제 담당자") ;

		private String code;
		private String description;

		SYSTEM_USER_CHARGE_GUBUN(final String code, final String description) {
			this.code = code;
			this.description = description;
		}

		@Override
		public String toString() {
			return this.code;
		}

		public String getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}

	}

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private SYSTEM_USER_CHARGE_GUBUN charge_gubun;
	private String ord = "";
	private String system_seq = "";
	private String user_id = "";
	private USER_TYPE user_gubun;
	private String use_yn = "";
	private String reg_user = "";
	private String reg_date = "";
	private String update_user = "";
	private String update_date = "";
	private String bu_idx = "";

	private String user_name = "";
	private String user_email = "";
	private String user_phone = "";
	private String sosok = "";
	private String tsosok = "";
	
	private String indept = "";
	//bp사를 위한 잠시~0521
	private String bp_name="";
	
	//검증 flow 승인 check 유무
	private String system_user_ok_gubun ="";
	
	public String getSystem_user_ok_gubun() {
		return system_user_ok_gubun;
	}

	public void setSystem_user_ok_gubun(String system_user_ok_gubun) {
		this.system_user_ok_gubun = system_user_ok_gubun;
	}

	public SYSTEM_USER_CHARGE_GUBUN getCharge_gubun() {
		return charge_gubun;
	}

	public void setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN charge_gubun) {
		this.charge_gubun = charge_gubun;
	}

	public void setCharge_gubun(String charge_gubun) {
		this.charge_gubun = SYSTEM_USER_CHARGE_GUBUN.valueOf(charge_gubun);
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public String getSystem_seq() {
		return system_seq;
	}

	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public USER_TYPE getUser_gubun() {
		return user_gubun;
	}

	public void setUser_gubun(USER_TYPE user_gubun) {
		this.user_gubun = user_gubun;
	}

	public void setUser_gubun(String user_gubun) {
		this.user_gubun = USER_TYPE.valueOf(user_gubun);
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

	public String getBu_idx() {
		return bu_idx;
	}

	public void setBu_idx(String bu_idx) {
		this.bu_idx = bu_idx;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getSosok() {
		return sosok;
	}

	public void setSosok(String sosok) {
		this.sosok = sosok;
	}

	public String getTsosok() {
		return tsosok;
	}

	public void setTsosok(String tsosok) {
		this.tsosok = tsosok;
	}

	public String getIndept() {
		return indept;
	}

	public void setIndept(String indept) {
		this.indept = indept;
	}

	public String getBp_name() {
		return bp_name;
	}

	public void setBp_name(String bp_name) {
		this.bp_name = bp_name;
	}
	
	
}
