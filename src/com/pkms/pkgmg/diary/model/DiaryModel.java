package com.pkms.pkgmg.diary.model;

import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * PKG검증/일정 > 월별일정<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 05.
 * 
 */
public class DiaryModel extends PkgModel {

	public enum DIARY_ITEM {

		APPLY("APPLY", "적용"), VERIFY("VERIFY", "검증");

		private String code;
		private String description;

		DIARY_ITEM(final String code, final String description) {
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

	private String work_gubun = "";
	private String team_name = "";
	private String start_time = "";
	private String end_time = "";
	private String work_date = "";
	private String final_date = "";
	private String group1_seq = "";
	
	/**
	 * 엑셀 다운로드 시 검색 조건(날짜)
	 */
	private String excel_date;
	private String pkg_title;
	
	public String getWork_gubun() {
		return work_gubun;
	}

	public void setWork_gubun(String work_gubun) {
		this.work_gubun = work_gubun;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getWork_date() {
		return work_date;
	}

	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}

	public String getFinal_date() {
		return final_date;
	}

	public void setFinal_date(String final_date) {
		this.final_date = final_date;
	}

	public String getGroup1_seq() {
		return group1_seq;
	}

	public void setGroup1_seq(String group1_seq) {
		this.group1_seq = group1_seq;
	}

	public String getExcel_date() {
		return excel_date;
	}

	public void setExcel_date(String excel_date) {
		this.excel_date = excel_date;
	}

	public String getPkg_title() {
		return pkg_title;
	}

	public void setPkg_title(String pkg_title) {
		this.pkg_title = pkg_title;
	}
	
}
