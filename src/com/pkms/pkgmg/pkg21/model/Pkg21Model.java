package com.pkms.pkgmg.pkg21.model;

import java.util.ArrayList;
import java.util.List;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgUserModel;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.pkgmg.pkg21.model.Pkg21FileModel;
import com.wings.model.CodeModel;

public class Pkg21Model extends AbstractModel {
	private static final long serialVersionUID = 1L;
	
	public Pkg21Model(){
		setStatusConditionsList();
		setDev_yn_list();
		setWork_level_list();
		setImportant_list();
		setApprove_list();
		setVer_gubun_list();
		setVer_gubun_access_list();
		setRoaming_link_list();
		setBypass_traffic_list();
		setPe_yn_list();
		setVerify_result_3List();
		setS_result_3List();
		setChk_result_list();
		setOk_nok_list();
	}
	
	private String pkg_seq = "";
	private String system_seq = "";
	private String system_name = "";
	private String system_name_real = "";
	private String read_gubun = "";
	private String status = "";
	private String status_dev = "";
	private String select_status = "";
	private String save_status = "";
	private String pkg_user_status = "";
	private String date_start = "";
	private String date_end = "";
	private String status_name = "";
	private String title = "";
	private String ver = "";
	private String system_user_name = "";
	private String dev_system_user_name = "";
	private String group_depth = "";
	private String reg_date = "";
	private String reg_user_name = "";
	private String update_date = "";
	private String update_user_name = "";
	private String update_gubun = "";
	
	private String reg_date_plan = "";
	private String reg_plan_user = "";
	private String reg_date_result = "";
	private String reg_result_user = "";
	
	private String ord = "";
	private String au_comment = "";
	
	private String verify_date_start = "";
	private String verify_date_end = "";
	
	private String impact_systems = "";
	
	private String chk_seq = "";
	private String vol_no = "";
	private String chk_result ="";
	
	private String reg_user ="";
	private String reg_user_gubun ="";

	private String work_gubun = "";
	private String[] work_result = null;
	private String[] charge_result = null;
	private String[] chk_seqs = null;
	private String[] check_seqs = null;
	private String[] check_seqs_e = null;
	private String[] e_check_seqs_e = null;
	private String[] ampms = null;
	private String[] start_dates = null;
	private String[] end_dates = null;
	private String[] start_times1 = null;
	private String[] start_times2 = null;
	private String[] end_times1 = null;
	private String[] end_times2 = null;
	
	private String start_date = "";
	private String end_date = "";
	private String start_time1 = "";
	private String start_time2 = "";
	private String end_time1 = "";
	private String end_time2 = "";
	private String ampm = "";
	
	private String patch_title = "";
	private String[] result_comment = null;
	
	// 검색조건 - 상태별
	private String statusCondition = "";
	private List<CodeModel> statusConditionsList;
	private final String[][] statusConditions = 
			new String[][] { { "", "전 체" }, { "101", "SVT계획수립" }, { "102", "SVT결과" }
			, { "111", "DVT계획 승인요청" }, { "112", "DVT계획 승인완료" }, { "113", "DVT결과 승인요청" }, { "114", "DVT결과 승인완료" }
			, { "121", "CVT계획 승인요청" }, { "122", "CVT계획 승인완료" }, { "123", "CVT결과 승인요청" }, { "124", "CVT결과 승인완료" }
			, { "131", "초도적용 계획수립" }, { "132", "초도적용 계획승인" }, { "133", "초도적용 당일결과" }, { "134", "초도적용 최종결과" }
			, { "141", "확대적용 계획수립" }, { "142", "확대적용 계획승인" }, { "143", "확대적용 결과" }
			, { "199", "PKG완료" }};
	
	// 검색조건 - 담당별
	private String userCondition = "0";
	private List<CodeModel> userConditionsList;
	private String[][] userConditions;
	
	// 검색조건 - 분류별
	private String group1Condition = "";
	private String group2Condition = "";
	
	private String searchKeyword = "";
	
	/**
	 *  개발 검증 요청 리스트
	 */
	private String dev_yn = "Y";
	private List<CodeModel> dev_yn_list;
	private final String[][] dev_yn_list_data = new String[][] { { "Y", "DVT/CVT 검증요청" }, { "N", "CVT 검증요청(DVT 검증생략)" }, { "D", "DVT/CVT 동시검증요청" }};
	
	/**
	 *  작업난이도
	 */
	private String work_level = "";
	private List<CodeModel> work_level_list;
	private final String[][] work_level_list_data = new String[][] { { "", "선택" }, { "상", "상" }, { "중", "중" }, { "하", "하" }};
	
	/**
	 *  작업난이도
	 */
	private String important = "";
	private List<CodeModel> important_list;
	private final String[][] important_list_data = new String[][] { { "", "선택" }, { "상", "상" }, { "중", "중" }, { "하", "하" }};
	
	/**
	 *  승인 / 반려
	 */
	private List<CodeModel> approve_list;
	private final String[][] approve_list_data = new String[][] { { "양호", "양호" }, { "불량", "불량" }};
	
	/**
	 * 버전구분
	 */
	private String ver_gubun = "F";
	private List<CodeModel> ver_gubun_list;
	private final String[][] ver_gubun_list_data = new String[][] { { "F", "Full" }, { "P", "Partial" }, { "C", "Patch" } };
	
	private List<CodeModel> ver_gubun_access_list;
	private final String[][] ver_gubun_access_list_data = new String[][] { { "F", "Full" }, { "C", "Patch" } };
	
	private String[] chk_results=null;
	private List<CodeModel> chk_result_list;
	private final String[][] chk_result_list_data = new String[][] { { "1", "OK" }, { "2", "NOK" }, { "3", "PASS" } };
	
	private List<CodeModel> ok_nok_list;
	private final String[][] ok_nok_list_data = new String[][] { { "1", "OK" }, { "2", "NOK" }};
	
	private String patch_yn = "N";
	
	private String pe_type = "";
	private String cha_yn = "N";
	private String vol_yn = "N";
	private String sec_yn = "N";
	
	private String content = "";
	private String content_pn = "";
	private String content_cr = "";
	private String content_self = "";
	private String content_invest = "";
	private String ttm = "";
	
	private String ser_downtime = "";

	
	private String master_file_id ="";
	private String del_file_id="";
	private String copy_file_count="";
	private String pkg_user_all_yn = "";
	
	/**
	 * 로밍연결
	 */
	private String roaming_link = "N";
	private List<CodeModel> roaming_link_list;
	private final String[][] roaming_link_list_data = new String[][] { { "N", "없음" }, { "상", "상" }, { "중", "중" }, { "하", "하" } };
	
	/**
	 * 우회소통
	 */
	private String bypass_traffic = "Y";
	private List<CodeModel> bypass_traffic_list;
	private final String[][] bypass_traffic_list_data = new String[][] { { "Y", "있음" }, { "N", "없음" } };
	private String bypass_traffic_comment = "";
	/**
	 * 과금영향도
	 */
	private String pe_yn = "Y";
	private List<CodeModel> pe_yn_list;
	private final String[][] pe_yn_list_data = new String[][] { { "Y", "있음" } , { "N", "없음" } };
	private String pe_yn_comment = "";
	
	private String col1 = "";
	private String col2 = "";
	private String col3 = "";
	private String col4 = "";
	private String col5 = "";
	private String col6 = "";
	private String col7 = "";
	private String col8 = "";
	private String col9 = "";
	private String col10 = "";
	private String col11 = "";
	private String col12 = "";
	private String col13 = "";
	private String col14 = "";
	private String col15 = "";
	private String col16 = "";
	private String col17 = "";
	private String col18 = "";
	private String col19 = "";
	private String col20 = "";
	private String col21 = "";
	private String col22 = "";
	private String col23 = "";
	private String col24 = "";
	private String col25 = "";
	private String col26 = "";
	private String col27 = "";
	private String col28 = "";
	private String col29 = "";
	private String col30 = "";
	private String col31 = "";
	private String col32 = "";
	private String col33 = "";
	private String col34 = "";
	private String col35 = "";
	private String col36 = "";
	private String col37 = "";
	private String col38 = "";
	private String col39 = "";
	private String col40 = "";
	private String col41 = "";
	private String col42 = "";
	private String col43 = "";
	
	
	/**
	 * 검증 결과
	 */
	private final String[][] verify_result_3 = new String[][] { {"", "선택"}, {"양호", "양호"}, {"불량", "불량"} };
	private List<CodeModel> verify_result_3List;
	
	/**
	 * 검증 결과
	 */
	private final String[][] s_result_3 = new String[][] { {"", "선택"}, {"양호", "양호"}, {"영없", "서비스영향없음"}, {"원복", "서비스영향있음(원복)"} };
	private List<CodeModel> s_result_3List;
	
	private List<SystemUserModel> systemUserModelList = null;
	private List<PkgUserModel> pkgUserModelList = null;
	private List<PkgUserModel> pkgUserModelList2 = null;
	
	private String user_active_status ="";
	private String user_active_status2 ="";
	
	private List<PkgEquipmentModel> pkgEquipmentModelList = null;
	private List<PkgEquipmentModel> pkgEquipmentModelList4E = null;
	
	private List<Pkg21FileModel> pkg21FileModelList=null;
	private List<Pkg21Model> pkg21ModelList=null;
	
	private int eq_cnt_left = 0;
	
	private String status_chk="";
	private String charge_gubun="";
	
	private String chk_type="";
	
	private List<Pkg21Model> readCellUserList = null;
	
	public String getPkg_seq() {
		return pkg_seq;
	}
	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}
	public String getRead_gubun() {
		return read_gubun;
	}
	public void setRead_gubun(String read_gubun) {
		this.read_gubun = read_gubun;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSelect_status() {
		return select_status;
	}
	public void setSelect_status(String select_status) {
		this.select_status = select_status;
	}
	public String getDate_start() {
		return date_start;
	}
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public String getDate_end() {
		return date_end;
	}
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
	
	public String getStatusCondition() {
		return statusCondition;
	}

	public void setStatusCondition(String statusCondition) {
		this.statusCondition = statusCondition;
	}

	public List<CodeModel> getStatusConditionsList() {
		return statusConditionsList;
	}

	public void setStatusConditionsList() {
		this.statusConditionsList = CodeUtil.convertCodeModel(statusConditions);
	}
	
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
	
	public String getGroup1Condition() {
		return group1Condition;
	}
	public void setGroup1Condition(String group1Condition) {
		this.group1Condition = group1Condition;
	}
	public String getGroup2Condition() {
		return group2Condition;
	}
	public void setGroup2Condition(String group2Condition) {
		this.group2Condition = group2Condition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSystem_user_name() {
		return system_user_name;
	}
	public void setSystem_user_name(String system_user_name) {
		this.system_user_name = system_user_name;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getGroup_depth() {
		return group_depth;
	}
	public void setGroup_depth(String group_depth) {
		this.group_depth = group_depth;
	}
	public String getReg_user_name() {
		return reg_user_name;
	}
	public void setReg_user_name(String reg_user_name) {
		this.reg_user_name = reg_user_name;
	}
	public String getPkg_user_status() {
		return pkg_user_status;
	}
	public void setPkg_user_status(String pkg_user_status) {
		this.pkg_user_status = pkg_user_status;
	}
	public String getSystem_seq() {
		return system_seq;
	}
	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}
	public String getSystem_name() {
		return system_name;
	}
	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}
	public String getDev_yn() {
		return dev_yn;
	}
	public void setDev_yn(String dev_yn) {
		this.dev_yn = dev_yn;
	}
	public List<CodeModel> getDev_yn_list() {
		return dev_yn_list;
	}
	public void setDev_yn_list() {
		this.dev_yn_list = CodeUtil.convertCodeModel(dev_yn_list_data);
	}
	public String getWork_level() {
		return work_level;
	}
	public void setWork_level(String work_level) {
		this.work_level = work_level;
	}
	public List<CodeModel> getWork_level_list() {
		return work_level_list;
	}
	public void setWork_level_list() {
		this.work_level_list = CodeUtil.convertCodeModel(work_level_list_data);
	}
	
	public String getImportant() {
		return important;
	}
	public void setImportant(String important) {
		this.important = important;
	}
	public List<CodeModel> getImportant_list() {
		return important_list;
	}
	public void setImportant_list() {
		this.important_list = CodeUtil.convertCodeModel(important_list_data);
	}
	public List<CodeModel> getApprove_list() {
		return approve_list;
	}
	public void setApprove_list() {
		this.approve_list = CodeUtil.convertCodeModel(approve_list_data);
	}
	
	public String getVer_gubun() {
		return ver_gubun;
	}
	public void setVer_gubun(String ver_gubun) {
		this.ver_gubun = ver_gubun;
	}
	public List<CodeModel> getVer_gubun_list() {
		return ver_gubun_list;
	}
	public void setVer_gubun_list() {
		this.ver_gubun_list = CodeUtil.convertCodeModel(ver_gubun_list_data);
	}
	public List<CodeModel> getVer_gubun_access_list() {
		return ver_gubun_access_list;
	}
	public void setVer_gubun_access_list() {
		this.ver_gubun_access_list = CodeUtil.convertCodeModel(ver_gubun_access_list_data);
	}
	public List<CodeModel> getChk_result_list() {
		return chk_result_list;
	}
	public void setChk_result_list() {
		this.chk_result_list = CodeUtil.convertCodeModel(chk_result_list_data);
	}
	public List<CodeModel> getOk_nok_list() {
		return ok_nok_list;
	}
	public void setOk_nok_list() {
		this.ok_nok_list = CodeUtil.convertCodeModel(ok_nok_list_data);
	}
	public String getPatch_yn() {
		return patch_yn;
	}
	public void setPatch_yn(String patch_yn) {
		this.patch_yn = patch_yn;
	}
	public String getPe_type() {
		return pe_type;
	}
	public void setPe_type(String pe_type) {
		this.pe_type = pe_type;
	}
	public String getCha_yn() {
		return cha_yn;
	}
	public void setCha_yn(String cha_yn) {
		this.cha_yn = cha_yn;
	}
	public String getVol_yn() {
		return vol_yn;
	}
	public void setVol_yn(String vol_yn) {
		this.vol_yn = vol_yn;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getUpdate_user_name() {
		return update_user_name;
	}
	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent_pn() {
		return content_pn;
	}
	public void setContent_pn(String content_pn) {
		this.content_pn = content_pn;
	}
	public String getContent_cr() {
		return content_cr;
	}
	public void setContent_cr(String content_cr) {
		this.content_cr = content_cr;
	}
	public String getContent_self() {
		return content_self;
	}
	public void setContent_self(String content_self) {
		this.content_self = content_self;
	}
	public String getTtm() {
		return ttm;
	}
	public void setTtm(String ttm) {
		this.ttm = ttm;
	}
	public String getSer_downtime() {
		return ser_downtime;
	}
	public void setSer_downtime(String ser_downtime) {
		this.ser_downtime = ser_downtime;
	}
	public String getRoaming_link() {
		return roaming_link;
	}
	public void setRoaming_link(String roaming_link) {
		this.roaming_link = roaming_link;
	}
	public String getBypass_traffic() {
		return bypass_traffic;
	}
	public void setBypass_traffic(String bypass_traffic) {
		this.bypass_traffic = bypass_traffic;
	}
	public String getBypass_traffic_comment() {
		return bypass_traffic_comment;
	}
	public void setBypass_traffic_comment(String bypass_traffic_comment) {
		this.bypass_traffic_comment = bypass_traffic_comment;
	}
	public String getPe_yn() {
		return pe_yn;
	}
	public void setPe_yn(String pe_yn) {
		this.pe_yn = pe_yn;
	}
	public String getPe_yn_comment() {
		return pe_yn_comment;
	}
	public void setPe_yn_comment(String pe_yn_comment) {
		this.pe_yn_comment = pe_yn_comment;
	}
	public List<CodeModel> getPe_yn_list() {
		return pe_yn_list;
	}
	public void setPe_yn_list() {
		this.pe_yn_list = CodeUtil.convertCodeModel(pe_yn_list_data);
	}
	public List<CodeModel> getRoaming_link_list() {
		return roaming_link_list;
	}
	public void setRoaming_link_list() {
		this.roaming_link_list = CodeUtil.convertCodeModel(roaming_link_list_data);
	}
	public List<CodeModel> getBypass_traffic_list() {
		return bypass_traffic_list;
	}
	public void setBypass_traffic_list() {
		this.bypass_traffic_list = CodeUtil.convertCodeModel(bypass_traffic_list_data);
	}
	public String getStatus_dev() {
		return status_dev;
	}
	public void setStatus_dev(String status_dev) {
		this.status_dev = status_dev;
	}
	public String getCol1() {
		return col1;
	}
	public void setCol1(String col1) {
		this.col1 = col1;
	}
	public String getCol2() {
		return col2;
	}
	public void setCol2(String col2) {
		this.col2 = col2;
	}
	public String getCol3() {
		return col3;
	}
	public void setCol3(String col3) {
		this.col3 = col3;
	}
	public String getCol4() {
		return col4;
	}
	public void setCol4(String col4) {
		this.col4 = col4;
	}
	public String getCol5() {
		return col5;
	}
	public void setCol5(String col5) {
		this.col5 = col5;
	}
	public String getCol6() {
		return col6;
	}
	public void setCol6(String col6) {
		this.col6 = col6;
	}
	public String getCol7() {
		return col7;
	}
	public void setCol7(String col7) {
		this.col7 = col7;
	}
	public String getCol8() {
		return col8;
	}
	public void setCol8(String col8) {
		this.col8 = col8;
	}
	public String getCol9() {
		return col9;
	}
	public void setCol9(String col9) {
		this.col9 = col9;
	}
	public String getCol10() {
		return col10;
	}
	public void setCol10(String col10) {
		this.col10 = col10;
	}
	public String getCol11() {
		return col11;
	}
	public void setCol11(String col11) {
		this.col11 = col11;
	}
	public String getCol12() {
		return col12;
	}
	public void setCol12(String col12) {
		this.col12 = col12;
	}
	public String getCol13() {
		return col13;
	}
	public void setCol13(String col13) {
		this.col13 = col13;
	}
	public String getCol14() {
		return col14;
	}
	public void setCol14(String col14) {
		this.col14 = col14;
	}
	public String getCol15() {
		return col15;
	}
	public void setCol15(String col15) {
		this.col15 = col15;
	}
	public String getCol16() {
		return col16;
	}
	public void setCol16(String col16) {
		this.col16 = col16;
	}
	public String getCol17() {
		return col17;
	}
	public void setCol17(String col17) {
		this.col17 = col17;
	}
	public String getCol18() {
		return col18;
	}
	public void setCol18(String col18) {
		this.col18 = col18;
	}
	public String getCol19() {
		return col19;
	}
	public void setCol19(String col19) {
		this.col19 = col19;
	}
	public String getCol20() {
		return col20;
	}
	public void setCol20(String col20) {
		this.col20 = col20;
	}
	public String getCol21() {
		return col21;
	}
	public void setCol21(String col21) {
		this.col21 = col21;
	}
	public String getCol22() {
		return col22;
	}
	public void setCol22(String col22) {
		this.col22 = col22;
	}
	public String getCol23() {
		return col23;
	}
	public void setCol23(String col23) {
		this.col23 = col23;
	}
	public String getCol24() {
		return col24;
	}
	public void setCol24(String col24) {
		this.col24 = col24;
	}
	public String getCol25() {
		return col25;
	}
	public void setCol25(String col25) {
		this.col25 = col25;
	}
	public String getCol26() {
		return col26;
	}
	public void setCol26(String col26) {
		this.col26 = col26;
	}
	public String getCol27() {
		return col27;
	}
	public void setCol27(String col27) {
		this.col27 = col27;
	}
	public String getCol28() {
		return col28;
	}
	public void setCol28(String col28) {
		this.col28 = col28;
	}
	public String getCol29() {
		return col29;
	}
	public void setCol29(String col29) {
		this.col29 = col29;
	}
	public String getCol30() {
		return col30;
	}
	public void setCol30(String col30) {
		this.col30 = col30;
	}
	public String getCol31() {
		return col31;
	}
	public void setCol31(String col31) {
		this.col31 = col31;
	}
	public String getCol32() {
		return col32;
	}
	public void setCol32(String col32) {
		this.col32 = col32;
	}
	public String getCol33() {
		return col33;
	}
	public void setCol33(String col33) {
		this.col33 = col33;
	}
	public String getCol34() {
		return col34;
	}
	public void setCol34(String col34) {
		this.col34 = col34;
	}
	public String getCol35() {
		return col35;
	}
	public void setCol35(String col35) {
		this.col35 = col35;
	}
	public String getCol36() {
		return col36;
	}
	public void setCol36(String col36) {
		this.col36 = col36;
	}
	public String getCol37() {
		return col37;
	}
	public void setCol37(String col37) {
		this.col37 = col37;
	}
	public String getCol38() {
		return col38;
	}
	public void setCol38(String col38) {
		this.col38 = col38;
	}
	public String getCol39() {
		return col39;
	}
	public void setCol39(String col39) {
		this.col39 = col39;
	}
	public String getCol40() {
		return col40;
	}
	public void setCol40(String col40) {
		this.col40 = col40;
	}
	public String getCol41() {
		return col41;
	}
	public void setCol41(String col41) {
		this.col41 = col41;
	}
	public String getCol42() {
		return col42;
	}
	public void setCol42(String col42) {
		this.col42 = col42;
	}
	public String getCol43() {
		return col43;
	}
	public void setCol43(String col43) {
		this.col43 = col43;
	}
	public String getVerify_date_start() {
		return verify_date_start;
	}
	public void setVerify_date_start(String verify_date_start) {
		this.verify_date_start = verify_date_start;
	}
	public String getVerify_date_end() {
		return verify_date_end;
	}
	public void setVerify_date_end(String verify_date_end) {
		this.verify_date_end = verify_date_end;
	}
	public List<CodeModel> getVerify_result_3List() {
		return verify_result_3List;
	}
	public void setVerify_result_3List() {
		this.verify_result_3List = CodeUtil.convertCodeModel(verify_result_3);
	}
	public List<CodeModel> getS_result_3List() {
		return s_result_3List;
	}
	public void setS_result_3List() {
		this.s_result_3List = CodeUtil.convertCodeModel(s_result_3);
	}
	public List<SystemUserModel> getSystemUserModelList() {
		if (systemUserModelList == null) {
			systemUserModelList = new ArrayList<SystemUserModel>();
		}
		return systemUserModelList;
	}
	public void setSystemUserModelList(List<SystemUserModel> systemUserModelList) {
		this.systemUserModelList = systemUserModelList;
	}
	public List<PkgUserModel> getPkgUserModelList() {
		if (pkgUserModelList == null) {
			pkgUserModelList = new ArrayList<PkgUserModel>();
		}
		return pkgUserModelList;
	}

	public void setPkgUserModelList(List<PkgUserModel> pkgUserModelList) {
		this.pkgUserModelList = pkgUserModelList;
	}
	public List<PkgUserModel> getPkgUserModelList2() {
		if (pkgUserModelList2 == null) {
			pkgUserModelList2 = new ArrayList<PkgUserModel>();
		}
		return pkgUserModelList2;
	}

	public void setPkgUserModelList2(List<PkgUserModel> pkgUserModelList2) {
		this.pkgUserModelList2 = pkgUserModelList2;
	}
	public String getUser_active_status() {
		return user_active_status;
	}
	public void setUser_active_status(String user_active_status) {
		this.user_active_status = user_active_status;
	}
	public String getUser_active_status2() {
		return user_active_status2;
	}
	public void setUser_active_status2(String user_active_status2) {
		this.user_active_status2 = user_active_status2;
	}
	public String getSave_status() {
		return save_status;
	}
	public void setSave_status(String save_status) {
		this.save_status = save_status;
	}
	public String[] getCheck_seqs() {
		return check_seqs;
	}
	public void setCheck_seqs(String[] check_seqs) {
		this.check_seqs = check_seqs;
	}
	public String getReg_date_plan() {
		return reg_date_plan;
	}
	public void setReg_date_plan(String reg_date_plan) {
		this.reg_date_plan = reg_date_plan;
	}
	public String getReg_plan_user() {
		return reg_plan_user;
	}
	public void setReg_plan_user(String reg_plan_user) {
		this.reg_plan_user = reg_plan_user;
	}
	public String getReg_date_result() {
		return reg_date_result;
	}
	public void setReg_date_result(String reg_date_result) {
		this.reg_date_result = reg_date_result;
	}
	public String getReg_result_user() {
		return reg_result_user;
	}
	public void setReg_result_user(String reg_result_user) {
		this.reg_result_user = reg_result_user;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getAu_comment() {
		return au_comment;
	}
	public void setAu_comment(String au_comment) {
		this.au_comment = au_comment;
	}
	public String getSec_yn() {
		return sec_yn;
	}
	public void setSec_yn(String sec_yn) {
		this.sec_yn = sec_yn;
	}
	public String getImpact_systems() {
		return impact_systems;
	}
	public void setImpact_systems(String impact_systems) {
		this.impact_systems = impact_systems;
	}
	public String getWork_gubun() {
		return work_gubun;
	}
	public void setWork_gubun(String work_gubun) {
		this.work_gubun = work_gubun;
	}
	
	public List<PkgEquipmentModel> getPkgEquipmentModelList() {
		if (pkgEquipmentModelList == null) {
			pkgEquipmentModelList = new ArrayList<PkgEquipmentModel>();
		}
		return pkgEquipmentModelList;
	}

	public void setPkgEquipmentModelList(List<PkgEquipmentModel> pkgEquipmentModelList) {
		this.pkgEquipmentModelList = pkgEquipmentModelList;
	}
	public String[] getStart_dates() {
		return start_dates;
	}
	public void setStart_dates(String[] start_dates) {
		this.start_dates = start_dates;
	}
	public String[] getEnd_dates() {
		return end_dates;
	}
	public void setEnd_dates(String[] end_dates) {
		this.end_dates = end_dates;
	}
	public String[] getStart_times1() {
		return start_times1;
	}
	public void setStart_times1(String[] start_times1) {
		this.start_times1 = start_times1;
	}
	public String[] getStart_times2() {
		return start_times2;
	}
	public void setStart_times2(String[] start_times2) {
		this.start_times2 = start_times2;
	}
	public String[] getEnd_times1() {
		return end_times1;
	}
	public void setEnd_times1(String[] end_times1) {
		this.end_times1 = end_times1;
	}
	public String[] getEnd_times2() {
		return end_times2;
	}
	public void setEnd_times2(String[] end_times2) {
		this.end_times2 = end_times2;
	}
	public String[] getCheck_seqs_e() {
		return check_seqs_e;
	}
	public void setCheck_seqs_e(String[] check_seqs_e) {
		this.check_seqs_e = check_seqs_e;
	}
	public String[] getChk_results() {
		return chk_results;
	}
	public void setChk_results(String[] chk_results) {
		this.chk_results = chk_results;
	}
	public String[] getAmpms() {
		return ampms;
	}
	public void setAmpms(String[] ampms) {
		this.ampms = ampms;
	}
	public String[] getWork_result() {
		return work_result;
	}
	public void setWork_result(String[] work_result) {
		this.work_result = work_result;
	}
	public String[] getCharge_result() {
		return charge_result;
	}
	public void setCharge_result(String[] charge_result) {
		this.charge_result = charge_result;
	}
	public List<PkgEquipmentModel> getPkgEquipmentModelList4E() {
		if(pkgEquipmentModelList4E == null){
			pkgEquipmentModelList4E = new ArrayList<PkgEquipmentModel>();
		}
		return pkgEquipmentModelList4E;
	}

	public void setPkgEquipmentModelList4E(List<PkgEquipmentModel> pkgEquipmentModelList4E) {
		this.pkgEquipmentModelList4E = pkgEquipmentModelList4E;
	}
	
	public List<Pkg21FileModel> getPkg21FileModelList() {
		if(pkg21FileModelList == null){
			pkg21FileModelList = new ArrayList<Pkg21FileModel>();
		}
		return pkg21FileModelList;
	}

	public void setPkg21FileModelList(List<Pkg21FileModel> pkg21FileModelList) {
		this.pkg21FileModelList = pkg21FileModelList;
	}
	
	public List<Pkg21Model> getPkg21ModelList() {
		if(pkg21ModelList == null){
			pkg21ModelList = new ArrayList<Pkg21Model>();
		}
		return pkg21ModelList;
	}

	public void setPkg21ModelList(List<Pkg21Model> pkg21ModelList) {
		this.pkg21ModelList = pkg21ModelList;
	}
	
	public String getMaster_file_id() {
		return master_file_id;
	}
	public void setMaster_file_id(String master_file_id) {
		this.master_file_id = master_file_id;
	}
	public String getUpdate_gubun() {
		return update_gubun;
	}
	public void setUpdate_gubun(String update_gubun) {
		this.update_gubun = update_gubun;
	}
	public String getChk_seq() {
		return chk_seq;
	}
	public void setChk_seq(String chk_seq) {
		this.chk_seq = chk_seq;
	}
	public String[] getChk_seqs() {
		return chk_seqs;
	}
	public void setChk_seqs(String[] chk_seqs) {
		this.chk_seqs = chk_seqs;
	}
	public String getVol_no() {
		return vol_no;
	}
	public void setVol_no(String vol_no) {
		this.vol_no = vol_no;
	}
	public String getChk_result() {
		return chk_result;
	}
	public void setChk_result(String chk_result) {
		this.chk_result = chk_result;
	}
	public String getDev_system_user_name() {
		return dev_system_user_name;
	}
	public void setDev_system_user_name(String dev_system_user_name) {
		this.dev_system_user_name = dev_system_user_name;
	}
	public int getEq_cnt_left() {
		return eq_cnt_left;
	}
	public void setEq_cnt_left(int eq_cnt_left) {
		this.eq_cnt_left = eq_cnt_left;
	}
	public String getContent_invest() {
		return content_invest;
	}
	public void setContent_invest(String content_invest) {
		this.content_invest = content_invest;
	}
	public String getPkg_user_all_yn() {
		return pkg_user_all_yn;
	}
	public void setPkg_user_all_yn(String pkg_user_all_yn) {
		this.pkg_user_all_yn = pkg_user_all_yn;
	}
	public String getStatus_chk() {
		return status_chk;
	}
	public void setStatus_chk(String status_chk) {
		this.status_chk = status_chk;
	}
	public String getCharge_gubun() {
		return charge_gubun;
	}
	public void setCharge_gubun(String charge_gubun) {
		this.charge_gubun = charge_gubun;
	}
	public String getChk_type() {
		return chk_type;
	}
	public void setChk_type(String chk_type) {
		this.chk_type = chk_type;
	}
	
	public String getReg_user() {
		return reg_user;
	}
	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}
	public String getReg_user_gubun() {
		return reg_user_gubun;
	}
	public void setReg_user_gubun(String reg_user_gubun) {
		this.reg_user_gubun = reg_user_gubun;
	}
	public String[] getE_check_seqs_e() {
		return e_check_seqs_e;
	}
	public void setE_check_seqs_e(String[] e_check_seqs_e) {
		this.e_check_seqs_e = e_check_seqs_e;
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
	public String getStart_time1() {
		return start_time1;
	}
	public void setStart_time1(String start_time1) {
		this.start_time1 = start_time1;
	}
	public String getStart_time2() {
		return start_time2;
	}
	public void setStart_time2(String start_time2) {
		this.start_time2 = start_time2;
	}
	public String getEnd_time1() {
		return end_time1;
	}
	public void setEnd_time1(String end_time1) {
		this.end_time1 = end_time1;
	}
	public String getEnd_time2() {
		return end_time2;
	}
	public void setEnd_time2(String end_time2) {
		this.end_time2 = end_time2;
	}
	public String getAmpm() {
		return ampm;
	}
	public void setAmpm(String ampm) {
		this.ampm = ampm;
	}
	public String getDel_file_id() {
		return del_file_id;
	}
	public void setDel_file_id(String del_file_id) {
		this.del_file_id = del_file_id;
	}
	public String getCopy_file_count() {
		return copy_file_count;
	}
	public void setCopy_file_count(String copy_file_count) {
		this.copy_file_count = copy_file_count;
	}
	public String getPatch_title() {
		return patch_title;
	}
	public void setPatch_title(String patch_title) {
		this.patch_title = patch_title;
	}
	public String[] getResult_comment() {
		return result_comment;
	}
	public void setResult_comment(String[] result_comment) {
		this.result_comment = result_comment;
	}
	public String getSystem_name_real() {
		return system_name_real;
	}
	public void setSystem_name_real(String system_name_real) {
		this.system_name_real = system_name_real;
	}
	
	/**
	 * 20181115 eryoon 추가
	 */
	private String cell_user_id;
	private String select_user;
	private String dev_system_user_id;
	private String cell_user_nm;
	private String cell_indept;
	private String cell_sosok;
	private String cell_movetelno;
	private String cell_email;
	private String system_user_id;
	private String bp_system_user_id;
	private int eq_cnt;
	

	public String getSelect_user() {
		return select_user;
	}
	public void setSelect_user(String select_user) {
		this.select_user = select_user;
	}
	public String getCell_user_id() {
		return cell_user_id;
	}
	public void setCell_user_id(String cell_user_id) {
		this.cell_user_id = cell_user_id;
	}
	public String getDev_system_user_id() {
		return dev_system_user_id;
	}
	public void setDev_system_user_id(String dev_system_user_id) {
		this.dev_system_user_id = dev_system_user_id;
	}
	public List<Pkg21Model> getReadCellUserList() {
		if(readCellUserList == null){
			readCellUserList = new ArrayList<Pkg21Model>();
		}
		return readCellUserList;
	}
	public void setReadCellUserList(List<Pkg21Model> readCellUserList) {
		this.readCellUserList = readCellUserList;
	}
	public String getCell_user_nm() {
		return cell_user_nm;
	}
	public void setCell_user_nm(String cell_user_nm) {
		this.cell_user_nm = cell_user_nm;
	}
	public String getCell_indept() {
		return cell_indept;
	}
	public void setCell_indept(String cell_indept) {
		this.cell_indept = cell_indept;
	}
	public String getCell_sosok() {
		return cell_sosok;
	}
	public void setCell_sosok(String cell_sosok) {
		this.cell_sosok = cell_sosok;
	}
	public String getCell_movetelno() {
		return cell_movetelno;
	}
	public void setCell_movetelno(String cell_movetelno) {
		this.cell_movetelno = cell_movetelno;
	}
	public String getCell_email() {
		return cell_email;
	}
	public void setCell_email(String cell_email) {
		this.cell_email = cell_email;
	}
	public String getSystem_user_id() {
		return system_user_id;
	}
	public void setSystem_user_id(String system_user_id) {
		this.system_user_id = system_user_id;
	}
	public String getBp_system_user_id() {
		return bp_system_user_id;
	}
	public void setBp_system_user_id(String bp_system_user_id) {
		this.bp_system_user_id = bp_system_user_id;
	}
	public int getEq_cnt() {
		return eq_cnt;
	}
	public void setEq_cnt(int eq_cnt) {
		this.eq_cnt = eq_cnt;
	}

	
}
