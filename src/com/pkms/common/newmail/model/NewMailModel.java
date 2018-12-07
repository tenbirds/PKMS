package com.pkms.common.newmail.model;

import java.util.ArrayList;
import java.util.List;

import com.pkms.common.model.AbstractModel;

public class NewMailModel extends AbstractModel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	

	private String system_seq;
	private String pkg_seq;
	
	private String user_id;
	private List<String> user_ids;
	private String empno;
	
	private String name;
	private String hname; // inf_person_info_rcv 에서 메니저 이름
	private String user_name;
	
	private String email;
	private String user_email;

	private String movetelno;
	private String user_phone;
	private String telno;

	private String sosok;
	private String nowPkgStatus;//내용분기를 위해서 현재 상태(상단 svt계획.. 결과... 판별)

	
	private List<String> gubuns;
	
	private String user_type;
	private String mailTitle;
	private String mailContent;
	
//	private String[] tos;
//	private String[] tosInfo; //메일,이름,소속
//	private String toInfo; ////메일,이름,소속 (단일)
	
	private String charge_gubun;
	private String ord;
	private String status;
	private String au_comment;
	private String use_yn;
	private String reg_user;
	private String update_user;
	private String reg_date;
	private String update_date;
	
	
	private List<NewMailModel> addUsers;
	
	private String[] eqment_seqs;
	
	
	
	
	private String smsMsgvalue;
	
	
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSosok() {
		return sosok;
	}

	public void setSosok(String sosok) {
		this.sosok = sosok;
	}

	public String getMovetelno() {
		return movetelno;
	}

	public void setMovetelno(String movetelno) {
		this.movetelno = movetelno;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getSystem_seq() {
		return system_seq;
	}

	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}

	public String getNowPkgStatus() {
		return nowPkgStatus;
	}

	public void setNowPkgStatus(String nowPkgStatus) {
		this.nowPkgStatus = nowPkgStatus;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public List<String> getGubuns() {
		return gubuns;
	}

	public void setGubuns(List<String> gubuns) {
		this.gubuns = gubuns;
	}

	public String getPkg_seq() {
		return pkg_seq;
	}

	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public List<String> getUser_ids() {
		return user_ids;
	}

	public void setUser_ids(List<String> user_ids) {
		this.user_ids = user_ids;
	}

	public String getHname() {
		return hname;
	}

	public void setHname(String hname) {
		this.hname = hname;
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

	public String getCharge_gubun() {
		return charge_gubun;
	}

	public void setCharge_gubun(String charge_gubun) {
		this.charge_gubun = charge_gubun;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAu_comment() {
		return au_comment;
	}

	public void setAu_comment(String au_comment) {
		this.au_comment = au_comment;
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

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

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

	public List<NewMailModel> getAddUsers() {
		return addUsers;
	}

	public void setAddUsers(List<NewMailModel> addUsers) {
		this.addUsers = addUsers;
	}

	public String[] getEqment_seqs() {
		return eqment_seqs;
	}

	public void setEqment_seqs(String[] eqment_seqs) {
		this.eqment_seqs = eqment_seqs;
	}

	public String getSmsMsgvalue() {
		return smsMsgvalue;
	}

	public void setSmsMsgvalue(String smsMsgvalue) {
		this.smsMsgvalue = smsMsgvalue;
	}




	

}