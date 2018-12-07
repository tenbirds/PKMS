package com.pkms.statsmg.stats.model;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import com.pkms.common.util.CodeUtil;
import com.pkms.statsmg.reports.model.WeeklyReportModel;
import com.wings.model.CodeModel;

/**
 * 
 * 
 * PKG 통계를 위한 모델 입니다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 28.
 * 
 */
public class StatsModel extends WeeklyReportModel {

	private static final long serialVersionUID = 1L;
	private final String EXCEL_FILE_NAME ="PKG 통계";

	private String system_user_id = "";
	
	private String job_history = ""; //시설현황 상세설명 으로 변경
	private String thisYear_job_plan = ""; //2012 증감설 계획 으로 변경
	private String nextYear_pkg_plan = ""; //2013 PKG 계획
	private String note = ""; //비고

	private String work_date = ""; //적용일자
	
	//idc_cnt
	private String boramae_cnt ="0";
	private String sungsu_cnt ="0";
	private String bundang_cnt ="0";
	private String suyu_cnt ="0";
	private String inchun_cnt ="0";
	private String senterm_cnt ="0";
	private String buam_cnt ="0";
	private String taepyong_cnt ="0";
	private String bonri_cnt ="0";
	private String seobu_cnt ="0";
	private String dunsan_cnt ="0";
	private String busa_cnt ="0";
	private String pkms_cnt ="0";
	
	private String full_name = "";
	private String oneline_explain = "";
	private String impact_systems = "";
	private String dv_user = "";
	private String da_user = "";
	private String vu_user = "";
	private String au_user = "";
	private String pu_user = "";
	private String vo_user = "";
	private String se_user = "";
	private String ch_user = "";
	private String no_user = "";
	private String eq_user = "";
	private String bpu0 = "";
	private String bpu1 = "";
	private String file1 = "";
	private String file2 = "";
	private String file3 = "";
	private String file4 = "";
	private String file5 = "";
	private String file6 = "";
	private String file7 = "";
	private String file8 = "";
	private String dev_system_user_name = "";
	private String road_map_ox = "";
	private String non_ox = "";
	private String vol_ox = "";
	private String cha_ox = "";
	private String non_title = "";
	private String vol_title = "";
	private String cha_title = "";
	private String bp_user_name ="";
	private String sales_user_info ="";
	
	TreeMap<String, StatsModel> subStatsMap = new TreeMap<String, StatsModel>();

	TreeMap<String, StatsModel> kindStatsMap = new TreeMap<String, StatsModel>();

	public String getSystem_user_id() {
		return system_user_id;
	}

	public void setSystem_user_id(String system_user_id) {
		this.system_user_id = system_user_id;
	}

	public TreeMap<String, StatsModel> getSubStatsMap() {
		return subStatsMap;
	}

	public void setSubStatsMap(TreeMap<String, StatsModel> subStatsMap) {
		this.subStatsMap = subStatsMap;
	}

	public TreeMap<String, StatsModel> getKindStatsMap() {
		return kindStatsMap;
	}

	public void setKindStatsMap(TreeMap<String, StatsModel> kindStatsMap) {
		this.kindStatsMap = kindStatsMap;
	}

	public Collection<StatsModel> getKindStatsSet() {
		return kindStatsMap.values();
	}

	
	// 검색조건 - 종류
	private String dateCondition = "MM";

	public String getDateCondition() {
		return dateCondition;
	}

	public void setDateCondition(String dateCondition) {
		this.dateCondition = dateCondition;
	}

	private String[][] dateConditions = new String[][] { { "DD", "일별" }, { "WW", "주별" }, { "MM", "월별" } };

	public List<CodeModel> getDateConditionsList() {
		return CodeUtil.convertCodeModel(dateConditions);
	}
	
	// 검색조건 - 기간 분류
	private String termCondition = "APPLY";

	public String getTermCondition() {
		return termCondition;
	}

	public void setTermCondition(String termCondition) {
		this.termCondition = termCondition;
	}

	private String[][] termConditions = new String[][] { { "APPLY", "적용" }, { "VERIFY", "검증" } };

	public List<CodeModel> getTermConditionsList() {
		return CodeUtil.convertCodeModel(termConditions);
	}

	// 검색조건 - 분류별
	private boolean kind_group1 = false;
	private boolean kind_group2 = false;
	private boolean kind_group3 = false;
	private boolean kind_system = true;
	private boolean kind_user = true;
	private boolean kind_idc = true;
	private boolean kind_equipment = true;
	private boolean kind_title = true; //pkg 제목
	private boolean kind_content = false; //개선내역 요약
	private boolean kind_sbt = false; //서비스 중단시간
	private boolean kind_verify_day = false; //검증 소요일
	private boolean kind_daytime = false; //주간야간

	public int getKind_count() {
		int count = 0;
		boolean[] checkList = new boolean[] { kind_group1, kind_group2, kind_group3, kind_system, kind_user, kind_idc, kind_equipment, kind_title, kind_content, kind_sbt, kind_verify_day, kind_daytime};
		for (boolean check : checkList) {
			if (check) {
				count++;
			}
		}
		return count;
	}

	public boolean isKind_group1() {
		return kind_group1;
	}

	public void setKind_group1(boolean kind_group1) {
		this.kind_group1 = kind_group1;
	}

	public boolean isKind_group2() {
		return kind_group2;
	}

	public void setKind_group2(boolean kind_group2) {
		this.kind_group2 = kind_group2;
	}

	public boolean isKind_group3() {
		return kind_group3;
	}

	public void setKind_group3(boolean kind_group3) {
		this.kind_group3 = kind_group3;
	}

	public boolean isKind_system() {
		return kind_system;
	}

	public void setKind_system(boolean kind_system) {
		this.kind_system = kind_system;
	}

	public boolean isKind_user() {
		return kind_user;
	}

	public void setKind_user(boolean kind_user) {
		this.kind_user = kind_user;
	}

	public boolean isKind_idc() {
		return kind_idc;
	}

	public void setKind_idc(boolean kind_idc) {
		this.kind_idc = kind_idc;
	}

	public boolean isKind_equipment() {
		return kind_equipment;
	}

	public void setKind_equipment(boolean kind_equipment) {
		this.kind_equipment = kind_equipment;
	}
	
	public boolean isKind_title() {
		return kind_title;
	}

	public void setKind_title(boolean kind_title) {
		this.kind_title = kind_title;
	}
	
	public boolean isKind_content() {
		return kind_content;
	}

	public void setKind_content(boolean kind_content) {
		this.kind_content = kind_content;
	}

	public boolean isKind_sbt() {
		return kind_sbt;
	}

	public void setKind_sbt(boolean kind_sbt) {
		this.kind_sbt = kind_sbt;
	}

	public boolean isKind_verify_day() {
		return kind_verify_day;
	}

	public void setKind_verify_day(boolean kind_verify_day) {
		this.kind_verify_day = kind_verify_day;
	}

	public boolean isKind_daytime() {
		return kind_daytime;
	}

	public void setKind_daytime(boolean kind_daytime) {
		this.kind_daytime = kind_daytime;
	}


	// 검색 조건 - 시스템
	private String search_system_seq;
	private String search_system_name;

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

	public StatsModel clone() throws CloneNotSupportedException {
		return (StatsModel) super.clone();
	}

	public String getEXCEL_FILE_NAME() {
		return EXCEL_FILE_NAME;
	}

	public String getJob_history() {
		return job_history;
	}

	public void setJob_history(String job_history) {
		this.job_history = job_history;
	}

	public String getThisYear_job_plan() {
		return thisYear_job_plan;
	}

	public void setThisYear_job_plan(String thisYear_job_plan) {
		this.thisYear_job_plan = thisYear_job_plan;
	}

	public String getNextYear_pkg_plan() {
		return nextYear_pkg_plan;
	}

	public void setNextYear_pkg_plan(String nextYear_pkg_plan) {
		this.nextYear_pkg_plan = nextYear_pkg_plan;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getWork_date() {
		return work_date;
	}

	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}

	public String getBoramae_cnt() {
		return boramae_cnt;
	}

	public void setBoramae_cnt(String boramae_cnt) {
		this.boramae_cnt = boramae_cnt;
	}

	public String getSungsu_cnt() {
		return sungsu_cnt;
	}

	public void setSungsu_cnt(String sungsu_cnt) {
		this.sungsu_cnt = sungsu_cnt;
	}

	public String getBundang_cnt() {
		return bundang_cnt;
	}

	public void setBundang_cnt(String bundang_cnt) {
		this.bundang_cnt = bundang_cnt;
	}

	public String getSuyu_cnt() {
		return suyu_cnt;
	}

	public void setSuyu_cnt(String suyu_cnt) {
		this.suyu_cnt = suyu_cnt;
	}

	public String getInchun_cnt() {
		return inchun_cnt;
	}

	public void setInchun_cnt(String inchun_cnt) {
		this.inchun_cnt = inchun_cnt;
	}

	public String getSenterm_cnt() {
		return senterm_cnt;
	}

	public void setSenterm_cnt(String senterm_cnt) {
		this.senterm_cnt = senterm_cnt;
	}

	public String getBuam_cnt() {
		return buam_cnt;
	}

	public void setBuam_cnt(String buam_cnt) {
		this.buam_cnt = buam_cnt;
	}

	public String getTaepyong_cnt() {
		return taepyong_cnt;
	}

	public void setTaepyong_cnt(String taepyong_cnt) {
		this.taepyong_cnt = taepyong_cnt;
	}

	public String getBonri_cnt() {
		return bonri_cnt;
	}

	public void setBonri_cnt(String bonri_cnt) {
		this.bonri_cnt = bonri_cnt;
	}

	public String getSeobu_cnt() {
		return seobu_cnt;
	}

	public void setSeobu_cnt(String seobu_cnt) {
		this.seobu_cnt = seobu_cnt;
	}

	public String getDunsan_cnt() {
		return dunsan_cnt;
	}

	public void setDunsan_cnt(String dunsan_cnt) {
		this.dunsan_cnt = dunsan_cnt;
	}

	public String getBusa_cnt() {
		return busa_cnt;
	}

	public void setBusa_cnt(String busa_cnt) {
		this.busa_cnt = busa_cnt;
	}

	public String getPkms_cnt() {
		return pkms_cnt;
	}

	public void setPkms_cnt(String pkms_cnt) {
		this.pkms_cnt = pkms_cnt;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getOneline_explain() {
		return oneline_explain;
	}

	public void setOneline_explain(String oneline_explain) {
		this.oneline_explain = oneline_explain;
	}

	public String getImpact_systems() {
		return impact_systems;
	}

	public void setImpact_systems(String impact_systems) {
		this.impact_systems = impact_systems;
	}

	public String getDv_user() {
		return dv_user;
	}

	public void setDv_user(String dv_user) {
		this.dv_user = dv_user;
	}

	public String getDa_user() {
		return da_user;
	}

	public void setDa_user(String da_user) {
		this.da_user = da_user;
	}

	public String getVu_user() {
		return vu_user;
	}

	public void setVu_user(String vu_user) {
		this.vu_user = vu_user;
	}

	public String getAu_user() {
		return au_user;
	}

	public void setAu_user(String au_user) {
		this.au_user = au_user;
	}

	public String getPu_user() {
		return pu_user;
	}

	public void setPu_user(String pu_user) {
		this.pu_user = pu_user;
	}

	public String getVo_user() {
		return vo_user;
	}

	public void setVo_user(String vo_user) {
		this.vo_user = vo_user;
	}

	public String getSe_user() {
		return se_user;
	}

	public void setSe_user(String se_user) {
		this.se_user = se_user;
	}

	public String getCh_user() {
		return ch_user;
	}

	public void setCh_user(String ch_user) {
		this.ch_user = ch_user;
	}

	public String getNo_user() {
		return no_user;
	}

	public void setNo_user(String no_user) {
		this.no_user = no_user;
	}

	public String getEq_user() {
		return eq_user;
	}

	public void setEq_user(String eq_user) {
		this.eq_user = eq_user;
	}

	public String getBpu0() {
		return bpu0;
	}

	public void setBpu0(String bpu0) {
		this.bpu0 = bpu0;
	}

	public String getBpu1() {
		return bpu1;
	}

	public void setBpu1(String bpu1) {
		this.bpu1 = bpu1;
	}

	public String getFile1() {
		return file1;
	}

	public void setFile1(String file1) {
		this.file1 = file1;
	}

	public String getFile2() {
		return file2;
	}

	public void setFile2(String file2) {
		this.file2 = file2;
	}

	public String getFile3() {
		return file3;
	}

	public void setFile3(String file3) {
		this.file3 = file3;
	}

	public String getFile4() {
		return file4;
	}

	public void setFile4(String file4) {
		this.file4 = file4;
	}

	public String getFile5() {
		return file5;
	}

	public void setFile5(String file5) {
		this.file5 = file5;
	}

	public String getFile6() {
		return file6;
	}

	public void setFile6(String file6) {
		this.file6 = file6;
	}

	public String getFile7() {
		return file7;
	}

	public void setFile7(String file7) {
		this.file7 = file7;
	}

	public String getFile8() {
		return file8;
	}

	public void setFile8(String file8) {
		this.file8 = file8;
	}

	public String getDev_system_user_name() {
		return dev_system_user_name;
	}

	public void setDev_system_user_name(String dev_system_user_name) {
		this.dev_system_user_name = dev_system_user_name;
	}

	public String getRoad_map_ox() {
		return road_map_ox;
	}

	public void setRoad_map_ox(String road_map_ox) {
		this.road_map_ox = road_map_ox;
	}

	public String getNon_ox() {
		return non_ox;
	}

	public void setNon_ox(String non_ox) {
		this.non_ox = non_ox;
	}

	public String getVol_ox() {
		return vol_ox;
	}

	public void setVol_ox(String vol_ox) {
		this.vol_ox = vol_ox;
	}

	public String getCha_ox() {
		return cha_ox;
	}

	public void setCha_ox(String cha_ox) {
		this.cha_ox = cha_ox;
	}

	public String getNon_title() {
		return non_title;
	}

	public void setNon_title(String non_title) {
		this.non_title = non_title;
	}

	public String getVol_title() {
		return vol_title;
	}

	public void setVol_title(String vol_title) {
		this.vol_title = vol_title;
	}

	public String getCha_title() {
		return cha_title;
	}

	public void setCha_title(String cha_title) {
		this.cha_title = cha_title;
	}

	public String getSales_user_info() {
		return sales_user_info;
	}

	public void setSales_user_info(String sales_user_info) {
		this.sales_user_info = sales_user_info;
	}

	public String getBp_user_name() {
		return bp_user_name;
	}

	public void setBp_user_name(String bp_user_name) {
		this.bp_user_name = bp_user_name;
	}
	
}
