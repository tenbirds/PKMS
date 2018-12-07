package com.pkms.usermg.user.model;

import java.util.List;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.pkms.org.model.OrgModel.ORG_OPTION;
import com.wings.model.CodeModel;

public class SktUserModel extends AbstractModel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private String empno = "";
	private String hname = "";
	private String ename = "";
	private String indept = "";
	private String booser = "";
	private String sosok = "";
	private String tsosok = "";
	private String jbgrp = "";
	private String jbrank = "";
	private String jbcharge = "";
	private String holdoffidivi = "";
	private String place = "";
	private String joincomdd = "";
	private String prodate = "";
	private String retiredd = "";
	private String job = "";
	private String jobnm = "";
	private String startdate = "";
	private String closedate = "";
	private String morepoyn = "";
	private String intelno = "";
	private String movetelno = "";
	private String telno = "";
	private String fax = "";
	private String email = "";
	private String email2 = "";
	private String idstartdate = "";
	private String photourl = "";
	private String t_flag = "";
	private String senddt = "";
	private String regno = "";

	private boolean authUser = false;

	private String treeScript = "";
	
	public SktUserModel() {
		setSearchConditionsList();
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getHname() {
		return hname;
	}

	public void setHname(String hname) {
		this.hname = hname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getIndept() {
		return indept;
	}

	public void setIndept(String indept) {
		this.indept = indept;
	}

	public String getBooser() {
		return booser;
	}

	public void setBooser(String booser) {
		this.booser = booser;
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

	public String getJbgrp() {
		return jbgrp;
	}

	public void setJbgrp(String jbgrp) {
		this.jbgrp = jbgrp;
	}

	public String getJbrank() {
		return jbrank;
	}

	public void setJbrank(String jbrank) {
		this.jbrank = jbrank;
	}

	public String getJbcharge() {
		return jbcharge;
	}

	public void setJbcharge(String jbcharge) {
		this.jbcharge = jbcharge;
	}

	public String getHoldoffidivi() {
		return holdoffidivi;
	}

	public void setHoldoffidivi(String holdoffidivi) {
		this.holdoffidivi = holdoffidivi;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getJoincomdd() {
		return joincomdd;
	}

	public void setJoincomdd(String joincomdd) {
		this.joincomdd = joincomdd;
	}

	public String getProdate() {
		return prodate;
	}

	public void setProdate(String prodate) {
		this.prodate = prodate;
	}

	public String getRetiredd() {
		return retiredd;
	}

	public void setRetiredd(String retiredd) {
		this.retiredd = retiredd;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getJobnm() {
		return jobnm;
	}

	public void setJobnm(String jobnm) {
		this.jobnm = jobnm;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getClosedate() {
		return closedate;
	}

	public void setClosedate(String closedate) {
		this.closedate = closedate;
	}

	public String getMorepoyn() {
		return morepoyn;
	}

	public void setMorepoyn(String morepoyn) {
		this.morepoyn = morepoyn;
	}

	public String getIntelno() {
		return intelno;
	}

	public void setIntelno(String intelno) {
		this.intelno = intelno;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getIdstartdate() {
		return idstartdate;
	}

	public void setIdstartdate(String idstartdate) {
		this.idstartdate = idstartdate;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public String getT_flag() {
		return t_flag;
	}

	public void setT_flag(String t_flag) {
		this.t_flag = t_flag;
	}

	public String getSenddt() {
		return senddt;
	}

	public void setSenddt(String senddt) {
		this.senddt = senddt;
	}

	public boolean isAuthUser() {
		return authUser;
	}

	public void setAuthUser(boolean authUser) {
		this.authUser = authUser;
	}

	public String getTreeScript() {
		return treeScript;
	}

	public void setTreeScript(String treeScript) {
		this.treeScript = treeScript;
	}

	/** 검색조건 */
	private String searchCondition;
	/** 검색Keyword */
	private String searchKeyword = "";

	private List<CodeModel> searchConditionsList;
	private final String[][] searchConditions = new String[][] { { "0", "이름" }, { "1", "부서" } };

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

	// 담당자 필터링 옵션
	private ORG_OPTION option;
	
	public ORG_OPTION getOption() {
		return option;
	}

	public void setOption(ORG_OPTION option) {
		this.option = option;
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}
}
