package com.pkms.sys.equipment.model;

import com.pkms.common.model.AbstractModel;

public class EquipmentUserModel extends AbstractModel {

	public static final String EQUIPMENT_USER_GUBUN_CODE = "OU";

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private String ord = "";
	private String equipment_seq = "";
	private String user_id = "";
	private USER_TYPE user_gubun;
	private String use_yn = "";
	private String reg_user = "";
	private String reg_date = "";
	private String update_user = "";
	private String update_date = "";

	private String user_name = "";
	private String user_email = "";
	private String user_phone = "";
	private String sosok = "";
	private String tsosok = "";
	private String indept = "";
	private String idc_seq = "";
	private String system_seq = "";

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public String getEquipment_seq() {
		return equipment_seq;
	}

	public void setEquipment_seq(String equipment_seq) {
		this.equipment_seq = equipment_seq;
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

	public String getIdc_seq() {
		return idc_seq;
	}

	public void setIdc_seq(String idc_seq) {
		this.idc_seq = idc_seq;
	}

	public String getSystem_seq() {
		return system_seq;
	}

	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}
	
	
}
