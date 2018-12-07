package com.pkms.statsmg.reports.model;

import java.util.List;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

/**
 * 
 * 
 * 분기별 보고서를 위한 모델 입니다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 28.
 * 
 */
public class QuarterlyReportModel extends AbstractModel {

	public enum REPORT_ITEM {
		PKG, PNCR
	}
	private final String EXCEL_FILE_NAME ="보고서";
	
	private static final long serialVersionUID = 1L;

	private String group1_seq = "";
	private String group2_seq = "";
	private String group3_seq = "";
	private String system_seq = "";
	private String equipment_seq = "";

	private String group_name = "";

	private String group1_name = "";
	private String group2_name = "";
	private String group3_name = "";
	private String system_name = "";
	private String equipment_name = "";

	private String pncrSystemName = "";
	private String pkgSystemName = "";
	private String work_gubun = "";
	private String status = "";
	private String status2 = "";
	private String new_pncr_gubun = "";

	private String start_date = "";
	private String end_date = "";
	
	private int pncrSystemCount = 0;
	private int pkgSystemCount = 0;
	private int pkgEquipmentCount = 0;

	private int equipmentPkgStartCount = 0;
	private int equipmentPkgEndCount = 0;
	private int equipmentPkgRevertCount = 0;

	private int newCount = 0;
	private int pnCount = 0;
	private int crCount = 0;

	private int count = 0;
	
	private String supply = "";
	
	private int count_total = 0;

	private String equipment_seq_b = "";
	private String work_gubun_b = "";
	private String status_b = "";
	private String status2_b = "";
	/*
	 * junhee - 추가사항
	 */
	private String content = ""; // junhee 개선내용요약
	private String sbt = ""; // 서비스 중단시간
	private int verify_day = 0; // 검증소요기간
	private String verify_start = ""; // 검증소요기간 시작날짜값
	private String verify_end = ""; // 검증소요기간 끝 날짜값
	private String rownum = "";
	private String ver;
	
	private int pkg_equipment_count = 0;
	private String ver_gubun;
	private String roaming_link;
	private String pe_yn;
	private int ampm_total;
	private int am_cnt;
	
	private String daytime;
	private String chodo;
	private String hwakdae;
	
	//160503 추가내용
	private String chodo_workdate;
	private String hwakdae_workdate;
	private String pkg_end_ox;
	private String dev_yn;
	private String on_yn;
	private String non_yn;
	private String vol_yn;
	private String cha_yn;
	private String sec_yn;
	private String on_comment;
	private String non_comment;
	private String vol_comment;
	private String cha_comment;
	private String sec_comment;
	private String col4;
	private String col4_file;
	private String col6;
	private String col6_file;
	private String col8;
	private String col8_file;
	private String col29;
	private String col29_file;
	private String col10;
	private String col10_file;
	private String col30;
	private String col30_file;
	private String col31;
	private String col31_file;
	private String col32;
	private String col32_file;
	private String col12;
	private String col12_file;
	private String col41;
	private String col41_file;
	private String col14;
	private String col14_file;
	private String col16;
	private String col16_file;
	private String col18;
	private String col18_file;
	private String col20;
	private String col20_file;
	private String col22;
	private String col22_file;
	private String col24;
	private String col24_file;
	private String col26;
	private String col26_file;
	private String col39;
	private String col39_file;
	
	
	public QuarterlyReportModel() {
		this.kindConditionsList = CodeUtil.convertCodeModel(kindConditions);
	}

	public String getGroup1_seq() {
		return group1_seq;
	}

	public void setGroup1_seq(String group1_seq) {
		this.group1_seq = group1_seq;
	}

	public String getGroup2_seq() {
		return group2_seq;
	}

	public void setGroup2_seq(String group2_seq) {
		this.group2_seq = group2_seq;
	}

	public String getGroup3_seq() {
		return group3_seq;
	}

	public void setGroup3_seq(String group3_seq) {
		this.group3_seq = group3_seq;
	}

	public String getSystem_seq() {
		return system_seq;
	}

	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}

	public String getEquipment_seq() {
		return equipment_seq;
	}

	public void setEquipment_seq(String equipment_seq) {
		this.equipment_seq = equipment_seq;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getGroup1_name() {
		return group1_name;
	}

	public void setGroup1_name(String group1_name) {
		this.group1_name = group1_name;
	}

	public String getGroup2_name() {
		return group2_name;
	}

	public void setGroup2_name(String group2_name) {
		this.group2_name = group2_name;
	}

	public String getGroup3_name() {
		return group3_name;
	}

	public void setGroup3_name(String group3_name) {
		this.group3_name = group3_name;
	}

	public String getSystem_name() {
		return system_name;
	}

	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}

	public String getEquipment_name() {
		return equipment_name;
	}

	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}

	public String getWork_gubun() {
		return work_gubun;
	}

	public void setWork_gubun(String work_gubun) {
		this.work_gubun = work_gubun;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getNew_pncr_gubun() {
		return new_pncr_gubun;
	}

	public void setNew_pncr_gubun(String new_pncr_gubun) {
		this.new_pncr_gubun = new_pncr_gubun;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public int getPncrSystemCount() {
		return pncrSystemCount;
	}
	
	public void setPncrSystemCount(int pncrSystemCount) {
		this.pncrSystemCount = pncrSystemCount;
	}

	public int getPkgSystemCount() {
		return pkgSystemCount;
	}

	public void setPkgSystemCount(int pkgSystemCount) {
		this.pkgSystemCount = pkgSystemCount;
	}

	public int getPkgEquipmentCount() {
		return pkgEquipmentCount;
	}

	public void setPkgEquipmentCount(int pkgEquipmentCount) {
		this.pkgEquipmentCount = pkgEquipmentCount;
	}

	public String[][] getUserConditions() {
		return userConditions;
	}

	public void setUserConditions(String[][] userConditions) {
		this.userConditions = userConditions;
	}

	public void setUserConditionsList(List<CodeModel> userConditionsList) {
		this.userConditionsList = userConditionsList;
	}

	public int getEquipmentPkgStartCount() {
		return equipmentPkgStartCount;
	}

	public void setEquipmentPkgStartCount(int equipmentPkgStartCount) {
		this.equipmentPkgStartCount = equipmentPkgStartCount;
	}

	public int getEquipmentPkgEndCount() {
		return equipmentPkgEndCount;
	}

	public void setEquipmentPkgEndCount(int equipmentPkgEndCount) {
		this.equipmentPkgEndCount = equipmentPkgEndCount;
	}

	public int getEquipmentPkgRevertCount() {
		return equipmentPkgRevertCount;
	}

	public void setEquipmentPkgRevertCount(int equipmentPkgRevertCount) {
		this.equipmentPkgRevertCount = equipmentPkgRevertCount;
	}
	
	public int getNewCount() {
		return newCount;
	}

	public void setNewCount(int newCount) {
		this.newCount = newCount;
	}

	public int getPnCount() {
		return pnCount;
	}

	public void setPnCount(int pnCount) {
		this.pnCount = pnCount;
	}

	public int getCrCount() {
		return crCount;
	}

	public void setCrCount(int crCount) {
		this.crCount = crCount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPncrSystemName() {
		return pncrSystemName;
	}

	public void setPncrSystemName(String pncrSystemName) {
		this.pncrSystemName = pncrSystemName;
	}

	public String getPkgSystemName() {
		return pkgSystemName;
	}

	public void setPkgSystemName(String pkgSystemName) {
		this.pkgSystemName = pkgSystemName;
	}

	// 조회 조건 세션 사용 여부
	private boolean sessionCondition = true;

	public boolean isSessionCondition() {
		return sessionCondition;
	}

	public void setSessionCondition(boolean sessionCondition) {
		this.sessionCondition = sessionCondition;
	}

	// 검색 항목
	private REPORT_ITEM item = REPORT_ITEM.PKG;

	public REPORT_ITEM getItem() {
		return item;
	}

	public void setItem(REPORT_ITEM item) {
		this.item = item;
	}

	// 검색조건 - 담당별
	private String userCondition = "0";
	private List<CodeModel> userConditionsList;
	private String[][] userConditions;
	
	public String getUserCondition() {
		return userCondition;
	}

	public void setUserCondition(String userCondition) {
		this.userCondition = userCondition;
	}

	public List<CodeModel> getUserConditionsList() {
		userConditions = new String[][] { { "0", getSession_user_name() }, { "1", "전체" } };
		this.userConditionsList = CodeUtil.convertCodeModel(userConditions);
		return userConditionsList;
	}
	
	// 검색조건 - 분류
	private String kindCondition = "0";
	private List<CodeModel> kindConditionsList;
	private String[][] kindConditions = new String[][] { { "0", "대" }, { "1", "중" }, { "2", "소" } };

	public String getKindCondition() {
		return kindCondition;
	}

	public void setKindCondition(String kindCondition) {
		this.kindCondition = kindCondition;
	}

	public List<CodeModel> getKindConditionsList() {
		return kindConditionsList;
	}

	public String getEXCEL_FILE_NAME() {
		return EXCEL_FILE_NAME;
	}
	
	//chart 선택
	private String chartCondition = "0";
	private List<CodeModel> chartConditionsList;
	private String[][] chartConditions;

	public String getChartCondition() {
		return chartCondition;
	}

	public void setChartCondition(String chartCondition) {
		this.chartCondition = chartCondition;
	}
	 
	public List<CodeModel> getChartConditionsList() {
		chartConditions = new String[][] { {"0", "건수"} ,{"1", "검증분야 개수"},{"2", "초도"},{"3", "확대"},{"4", "원복"},{"5", "NEW"},{"6", "PN"},{"7", "CR"} };
		this.chartConditionsList = CodeUtil.convertCodeModel(chartConditions);
		return chartConditionsList;
	}
	
	//구분
	private String chartGunun4X = "";
	private String chartGunun4Y = "";

	public String getChartGunun4X() {
		return chartGunun4X;
	}

	public void setChartGunun4X(String chartGunun4X) {
		this.chartGunun4X = chartGunun4X;
	}

	public String getChartGunun4Y() {
		return chartGunun4Y;
	}

	public void setChartGunun4Y(String chartGunun4Y) {
		this.chartGunun4Y = chartGunun4Y;
	}

	public String getSupply() {
		return supply;
	}

	public void setSupply(String supply) {
		this.supply = supply;
	}

	/*
	 * junhee - 추가사항
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSbt() {
		return sbt;
	}

	public void setSbt(String sbt) {
		this.sbt = sbt;
	}

	
	public int getVerify_day() {
		return verify_day;
	}

	public void setVerify_day(int verify_day) {
		this.verify_day = verify_day;
	}

	public String getVerify_start() {
		return verify_start;
	}

	public void setVerify_start(String verify_start) {
		this.verify_start = verify_start;
	}

	public String getVerify_end() {
		return verify_end;
	}

	public void setVerify_end(String verify_end) {
		this.verify_end = verify_end;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public int getCount_total() {
		return count_total;
	}

	public void setCount_total(int count_total) {
		this.count_total = count_total;
	}

	public String getEquipment_seq_b() {
		return equipment_seq_b;
	}

	public void setEquipment_seq_b(String equipment_seq_b) {
		this.equipment_seq_b = equipment_seq_b;
	}

	public String getWork_gubun_b() {
		return work_gubun_b;
	}

	public void setWork_gubun_b(String work_gubun_b) {
		this.work_gubun_b = work_gubun_b;
	}

	public String getStatus_b() {
		return status_b;
	}

	public void setStatus_b(String status_b) {
		this.status_b = status_b;
	}

	public String getStatus2_b() {
		return status2_b;
	}

	public void setStatus2_b(String status2_b) {
		this.status2_b = status2_b;
	}

	public int getPkg_equipment_count() {
		return pkg_equipment_count;
	}

	public void setPkg_equipment_count(int pkg_equipment_count) {
		this.pkg_equipment_count = pkg_equipment_count;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getVer_gubun() {
		return ver_gubun;
	}

	public void setVer_gubun(String ver_gubun) {
		this.ver_gubun = ver_gubun;
	}

	public String getRoaming_link() {
		return roaming_link;
	}

	public void setRoaming_link(String roaming_link) {
		this.roaming_link = roaming_link;
	}

	public String getPe_yn() {
		return pe_yn;
	}

	public void setPe_yn(String pe_yn) {
		this.pe_yn = pe_yn;
	}

	public int getAmpm_total() {
		return ampm_total;
	}

	public void setAmpm_total(int ampm_total) {
		this.ampm_total = ampm_total;
	}

	public int getAm_cnt() {
		return am_cnt;
	}

	public void setAm_cnt(int am_cnt) {
		this.am_cnt = am_cnt;
	}

	public String getDaytime() {
		return daytime;
	}

	public void setDaytime(String daytime) {
		this.daytime = daytime;
	}

	public String getChodo() {
		return chodo;
	}

	public void setChodo(String chodo) {
		this.chodo = chodo;
	}

	public String getHwakdae() {
		return hwakdae;
	}

	public void setHwakdae(String hwakdae) {
		this.hwakdae = hwakdae;
	}

	public String getChodo_workdate() {
		return chodo_workdate;
	}

	public void setChodo_workdate(String chodo_workdate) {
		this.chodo_workdate = chodo_workdate;
	}

	public String getHwakdae_workdate() {
		return hwakdae_workdate;
	}

	public void setHwakdae_workdate(String hwakdae_workdate) {
		this.hwakdae_workdate = hwakdae_workdate;
	}

	public String getPkg_end_ox() {
		return pkg_end_ox;
	}

	public void setPkg_end_ox(String pkg_end_ox) {
		this.pkg_end_ox = pkg_end_ox;
	}

	public String getDev_yn() {
		return dev_yn;
	}

	public void setDev_yn(String dev_yn) {
		this.dev_yn = dev_yn;
	}

	public String getOn_yn() {
		return on_yn;
	}

	public void setOn_yn(String on_yn) {
		this.on_yn = on_yn;
	}

	public String getNon_yn() {
		return non_yn;
	}

	public void setNon_yn(String non_yn) {
		this.non_yn = non_yn;
	}

	public String getVol_yn() {
		return vol_yn;
	}

	public void setVol_yn(String vol_yn) {
		this.vol_yn = vol_yn;
	}

	public String getCha_yn() {
		return cha_yn;
	}

	public void setCha_yn(String cha_yn) {
		this.cha_yn = cha_yn;
	}

	public String getSec_yn() {
		return sec_yn;
	}

	public void setSec_yn(String sec_yn) {
		this.sec_yn = sec_yn;
	}

	public String getOn_comment() {
		return on_comment;
	}

	public void setOn_comment(String on_comment) {
		this.on_comment = on_comment;
	}

	public String getNon_comment() {
		return non_comment;
	}

	public void setNon_comment(String non_comment) {
		this.non_comment = non_comment;
	}

	public String getVol_comment() {
		return vol_comment;
	}

	public void setVol_comment(String vol_comment) {
		this.vol_comment = vol_comment;
	}

	public String getCha_comment() {
		return cha_comment;
	}

	public void setCha_comment(String cha_comment) {
		this.cha_comment = cha_comment;
	}

	public String getSec_comment() {
		return sec_comment;
	}

	public void setSec_comment(String sec_comment) {
		this.sec_comment = sec_comment;
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	public String getCol4_file() {
		return col4_file;
	}

	public void setCol4_file(String col4_file) {
		this.col4_file = col4_file;
	}

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getCol6_file() {
		return col6_file;
	}

	public void setCol6_file(String col6_file) {
		this.col6_file = col6_file;
	}

	public String getCol8() {
		return col8;
	}

	public void setCol8(String col8) {
		this.col8 = col8;
	}

	public String getCol8_file() {
		return col8_file;
	}

	public void setCol8_file(String col8_file) {
		this.col8_file = col8_file;
	}

	public String getCol29() {
		return col29;
	}

	public void setCol29(String col29) {
		this.col29 = col29;
	}

	public String getCol29_file() {
		return col29_file;
	}

	public void setCol29_file(String col29_file) {
		this.col29_file = col29_file;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public String getCol10_file() {
		return col10_file;
	}

	public void setCol10_file(String col10_file) {
		this.col10_file = col10_file;
	}

	public String getCol30() {
		return col30;
	}

	public void setCol30(String col30) {
		this.col30 = col30;
	}

	public String getCol30_file() {
		return col30_file;
	}

	public void setCol30_file(String col30_file) {
		this.col30_file = col30_file;
	}

	public String getCol31() {
		return col31;
	}

	public void setCol31(String col31) {
		this.col31 = col31;
	}

	public String getCol31_file() {
		return col31_file;
	}

	public void setCol31_file(String col31_file) {
		this.col31_file = col31_file;
	}

	public String getCol32() {
		return col32;
	}

	public void setCol32(String col32) {
		this.col32 = col32;
	}

	public String getCol32_file() {
		return col32_file;
	}

	public void setCol32_file(String col32_file) {
		this.col32_file = col32_file;
	}

	public String getCol12() {
		return col12;
	}

	public void setCol12(String col12) {
		this.col12 = col12;
	}

	public String getCol12_file() {
		return col12_file;
	}

	public void setCol12_file(String col12_file) {
		this.col12_file = col12_file;
	}

	public String getCol41() {
		return col41;
	}

	public void setCol41(String col41) {
		this.col41 = col41;
	}

	public String getCol41_file() {
		return col41_file;
	}

	public void setCol41_file(String col41_file) {
		this.col41_file = col41_file;
	}

	public String getCol14() {
		return col14;
	}

	public void setCol14(String col14) {
		this.col14 = col14;
	}

	public String getCol14_file() {
		return col14_file;
	}

	public void setCol14_file(String col14_file) {
		this.col14_file = col14_file;
	}

	public String getCol16() {
		return col16;
	}

	public void setCol16(String col16) {
		this.col16 = col16;
	}

	public String getCol16_file() {
		return col16_file;
	}

	public void setCol16_file(String col16_file) {
		this.col16_file = col16_file;
	}

	public String getCol18() {
		return col18;
	}

	public void setCol18(String col18) {
		this.col18 = col18;
	}

	public String getCol18_file() {
		return col18_file;
	}

	public void setCol18_file(String col18_file) {
		this.col18_file = col18_file;
	}

	public String getCol20() {
		return col20;
	}

	public void setCol20(String col20) {
		this.col20 = col20;
	}

	public String getCol20_file() {
		return col20_file;
	}

	public void setCol20_file(String col20_file) {
		this.col20_file = col20_file;
	}

	public String getCol22() {
		return col22;
	}

	public void setCol22(String col22) {
		this.col22 = col22;
	}

	public String getCol22_file() {
		return col22_file;
	}

	public void setCol22_file(String col22_file) {
		this.col22_file = col22_file;
	}

	public String getCol24() {
		return col24;
	}

	public void setCol24(String col24) {
		this.col24 = col24;
	}

	public String getCol24_file() {
		return col24_file;
	}

	public void setCol24_file(String col24_file) {
		this.col24_file = col24_file;
	}

	public String getCol26() {
		return col26;
	}

	public void setCol26(String col26) {
		this.col26 = col26;
	}

	public String getCol26_file() {
		return col26_file;
	}

	public void setCol26_file(String col26_file) {
		this.col26_file = col26_file;
	}

	public String getCol39() {
		return col39;
	}

	public void setCol39(String col39) {
		this.col39 = col39;
	}

	public String getCol39_file() {
		return col39_file;
	}

	public void setCol39_file(String col39_file) {
		this.col39_file = col39_file;
	}

	public String[][] getChartConditions() {
		return chartConditions;
	}

	public void setChartConditions(String[][] chartConditions) {
		this.chartConditions = chartConditions;
	}

	public void setChartConditionsList(List<CodeModel> chartConditionsList) {
		this.chartConditionsList = chartConditionsList;
	}
	
	
}
