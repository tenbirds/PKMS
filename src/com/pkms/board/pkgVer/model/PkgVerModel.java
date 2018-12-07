package com.pkms.board.pkgVer.model;

import java.util.Collection;
import java.util.TreeMap;

import com.pkms.common.model.AbstractModel;

public class PkgVerModel extends AbstractModel{
	
	private static final long serialVersionUID = 1L;
	
	private String ver_seq;
	private String system_seq;
	private String group_name;
	private String system_name;
	private String pkg_ver;
	private String pkg_apply_day;
	private String sys_skt_user;
	private String sys_bp_user;
	private String ver_note;
	private String user_yn;
	private String reg_user;
	private String reg_name;
	private String reg_date;
	private String update_user;
	private String update_name;
	private String update_date;
	private String skt_phone;
	private String bp_name;
	private String bp_phone;
	
	private String pageGubun;
	
	
	/** 검색조건 */
	private String search_reg_date_start;
	private String search_reg_date_end;
	
	// 검색 조건 - 시스템
	private String search_system_seq;
	private String search_system_name;
	
	private String pkgVer_seq;
	
	private String content;
	
	// kindmap 설정 (같은값의 중분류를 묶기 위한)
	TreeMap<String, PkgVerModel> kindMap = new TreeMap<String, PkgVerModel>();
		
	
	public String getVer_seq() {
		return ver_seq;
	}
	public void setVer_seq(String ver_seq) {
		this.ver_seq = ver_seq;
	}
	public String getSystem_seq() {
		return system_seq;
	}
	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getSystem_name() {
		return system_name;
	}
	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}
	public String getPkg_ver() {
		return pkg_ver;
	}
	public void setPkg_ver(String pkg_ver) {
		this.pkg_ver = pkg_ver;
	}
	public String getPkg_apply_day() {
		return pkg_apply_day;
	}
	public void setPkg_apply_day(String pkg_apply_day) {
		this.pkg_apply_day = pkg_apply_day;
	}
	public String getSys_skt_user() {
		return sys_skt_user;
	}
	public void setSys_skt_user(String sys_skt_user) {
		this.sys_skt_user = sys_skt_user;
	}
	public String getSys_bp_user() {
		return sys_bp_user;
	}
	public void setSys_bp_user(String sys_bp_user) {
		this.sys_bp_user = sys_bp_user;
	}
	public String getVer_note() {
		return ver_note;
	}
	public void setVer_note(String ver_note) {
		this.ver_note = ver_note;
	}
	public String getUser_yn() {
		return user_yn;
	}
	public void setUser_yn(String user_yn) {
		this.user_yn = user_yn;
	}
	public String getReg_user() {
		return reg_user;
	}
	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}
	public String getReg_name() {
		return reg_name;
	}
	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
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
	public String getUpdate_name() {
		return update_name;
	}
	public void setUpdate_name(String update_name) {
		this.update_name = update_name;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
		
	public String getPkgVer_seq() {
		return pkgVer_seq;
	}
	public void setPkgVer_seq(String pkgVer_seq) {
		this.pkgVer_seq = pkgVer_seq;
	}
	public String getSearch_reg_date_start() {
		return search_reg_date_start;
	}
	public void setSearch_reg_date_start(String search_reg_date_start) {
		this.search_reg_date_start = search_reg_date_start;
	}
	public String getSearch_reg_date_end() {
		return search_reg_date_end;
	}
	public void setSearch_reg_date_end(String search_reg_date_end) {
		this.search_reg_date_end = search_reg_date_end;
	}
	public String getSearch_system_seq() {
		return search_system_seq;
	}
	public void setSearch_system_seq(String search_system_seq) {
		this.search_system_seq = search_system_seq;
	}
	public String getSearch_system_name() {
		return search_system_name;
	}
	public void setSearch_system_name(String search_system_name) {
		this.search_system_name = search_system_name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getSkt_phone() {
		return skt_phone;
	}
	public void setSkt_phone(String skt_phone) {
		this.skt_phone = skt_phone;
	}
	public String getBp_name() {
		return bp_name;
	}
	public void setBp_name(String bp_name) {
		this.bp_name = bp_name;
	}
	public String getBp_phone() {
		return bp_phone;
	}
	public void setBp_phone(String bp_phone) {
		this.bp_phone = bp_phone;
	}

	public TreeMap<String, PkgVerModel> getKindMap() {
		return kindMap;
	}
	public void setKindMap(TreeMap<String, PkgVerModel> kindMap) {
		this.kindMap = kindMap;
	}
	public Collection<PkgVerModel> getKindSet(){
		return kindMap.values();
	}
	
	public String getPageGubun() {
		return pageGubun;
	}
	public void setPageGubun(String pageGubun) {
		this.pageGubun = pageGubun;
	}

}
