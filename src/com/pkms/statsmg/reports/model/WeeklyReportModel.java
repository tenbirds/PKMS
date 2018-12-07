package com.pkms.statsmg.reports.model;

import java.util.TreeMap;

/**
 * 
 * 
 * 주간 보고서를 위한 모델 입니다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 28.
 * 
 */
public class WeeklyReportModel extends QuarterlyReportModel implements Cloneable {

	private static final long serialVersionUID = 1L; 
	private String idc_seq = "";
	private String idc_name = "";
	private String team_name = "";
	private String central_name = "";
	private String work_date = "";
	private String system_user_name = "";
	private String downtime = "";
	private String pkg_seq = "";
	private String pkg_name = "";
	private String pkg_verify_count = "";

	private String base_date = "";
	private String start_date_before = "";
	private String end_date_before = "";
	private String start_date_after = "";
	private String end_date_after = "";
	
	private String equipment_cnt = "";
	private String equipment_group3_sum = "";
	private String equipment_group1_sum = "";
	private String location_code = "";

	TreeMap<String, WeeklyReportModel> subReportMap = new TreeMap<String, WeeklyReportModel>();

	TreeMap<String, String> pkgSeqMap = new TreeMap<String, String>();

	public int getPkg_count() {
		return pkgSeqMap.size();
	}

	public String getIdc_seq() {
		return idc_seq;
	}

	public void setIdc_seq(String idc_seq) {
		this.idc_seq = idc_seq;
	}

	public String getIdc_name() {
		return idc_name;
	}

	public void setIdc_name(String idc_name) {
		this.idc_name = idc_name;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getCentral_name() {
		return central_name;
	}

	public void setCentral_name(String central_name) {
		this.central_name = central_name;
	}

	public String getWork_date() {
		return work_date;
	}

	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}

	public String getSystem_user_name() {
		return system_user_name;
	}

	public void setSystem_user_name(String system_user_name) {
		this.system_user_name = system_user_name;
	}

	public String getDowntime() {
		return downtime;
	}

	public void setDowntime(String downtime) {
		this.downtime = downtime;
	}

	public String getPkg_seq() {
		return pkg_seq;
	}

	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}

	public String getPkg_name() {
		return pkg_name;
	}

	public void setPkg_name(String pkg_name) {
		this.pkg_name = pkg_name;
	}

	public TreeMap<String, WeeklyReportModel> getSubReportMap() {
		return subReportMap;
	}

	public void setSubReportMap(TreeMap<String, WeeklyReportModel> subReportMap) {
		this.subReportMap = subReportMap;
	}

	public TreeMap<String, String> getPkgSeqMap() {
		return pkgSeqMap;
	}

	public void setPkgSeqMap(TreeMap<String, String> pkgSeqMap) {
		this.pkgSeqMap = pkgSeqMap;
	}

	public String getPkg_verify_count() {
		return pkg_verify_count;
	}

	public void setPkg_verify_count(String pkg_verify_count) {
		this.pkg_verify_count = pkg_verify_count;
	}

	private String[] pkg_seq_list = new String[] {};

	public String[] getPkg_seq_list() {
		return pkg_seq_list;
	}

	public String getBase_date() {
		return base_date;
	}

	public void setBase_date(String base_date) {
		this.base_date = base_date;
	}

	public String getStart_date_before() {
		return start_date_before;
	}

	public void setStart_date_before(String start_date_before) {
		this.start_date_before = start_date_before;
	}

	public String getEnd_date_before() {
		return end_date_before;
	}

	public void setEnd_date_before(String end_date_before) {
		this.end_date_before = end_date_before;
	}

	public String getStart_date_after() {
		return start_date_after;
	}

	public void setStart_date_after(String start_date_after) {
		this.start_date_after = start_date_after;
	}

	public String getEnd_date_after() {
		return end_date_after;
	}

	public void setEnd_date_after(String end_date_after) {
		this.end_date_after = end_date_after;
	}

	public void initPkg_seq_list() {
		this.pkg_seq_list = pkgSeqMap.keySet().toArray(new String[0]);
	}

	public WeeklyReportModel clone() throws CloneNotSupportedException {
		return (WeeklyReportModel) super.clone();
	}

	public String getEquipment_cnt() {
		return equipment_cnt;
	}

	public void setEquipment_cnt(String equipment_cnt) {
		this.equipment_cnt = equipment_cnt;
	}

	public String getEquipment_group3_sum() {
		return equipment_group3_sum;
	}

	public void setEquipment_group3_sum(String equipment_group3_sum) {
		this.equipment_group3_sum = equipment_group3_sum;
	}

	public String getEquipment_group1_sum() {
		return equipment_group1_sum;
	}

	public void setEquipment_group1_sum(String equipment_group1_sum) {
		this.equipment_group1_sum = equipment_group1_sum;
	}

	public String getLocation_code() {
		return location_code;
	}

	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}
	
}
