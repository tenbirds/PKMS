package com.pkms.usermg.user.model;

import java.io.Serializable;

import com.pkms.common.model.AbstractModel.USER_TYPE;

public class CommonUserModel implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 아이디
	 */
	private String cu_id = "";

	/**
	 * 사용자 이름
	 */
	private String cu_name = "";

	/**
	 * 사용자 타입
	 */
	private USER_TYPE cu_type;

	/**
	 * 사용자 이메일
	 */
	private String cu_email = "";

	/**
	 * 사용자 휴대전화
	 */
	private String cu_mobile_phone = "";

	/**
	 * 사용자 회사 또는 부서 아이디
	 */
	private String group_id = "";

	/**
	 * 사용자 회사 또는 부서 이름
	 */
	private String group_name = "";

	public String getCu_id() {
		return cu_id;
	}

	public void setCu_id(String cu_id) {
		this.cu_id = cu_id;
	}

	public String getCu_name() {
		return cu_name;
	}

	public void setCu_name(String cu_name) {
		this.cu_name = cu_name;
	}

	public USER_TYPE getCu_type() {
		return cu_type;
	}

	public void setCu_type(USER_TYPE cu_type) {
		this.cu_type = cu_type;
	}

	public String getCu_email() {
		return cu_email;
	}

	public void setCu_email(String cu_email) {
		this.cu_email = cu_email;
	}

	public String getCu_mobile_phone() {
		return cu_mobile_phone;
	}

	public void setCu_mobile_phone(String cu_mobile_phone) {
		this.cu_mobile_phone = cu_mobile_phone;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

}
