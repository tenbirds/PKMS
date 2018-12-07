package com.pkms.newpncrmg.model;

import java.util.List;

import com.pkms.common.attachfile.model.AttachFileMasterKey;
import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

public class NewpncrModel extends AbstractModel {
	
	public NewpncrModel() {
		setSearchConditionsList();
		setNew_pncr_gubunList();
		setPriorityList();
		setProblem_gubunList();
		setSearchRoleConditionList();
		setSearchNew_pncr_gubunList();
		setSearch_stateList();
	}
	
	private static final long serialVersionUID = 1L;
	
	
	private String init = "false";
	private String new_pn_cr_seq;
	private String new_pn_cr_no;
	private String title;
	private String new_pncr_gubun;
	private List<CodeModel> new_pncr_gubunList;
	private final String[][] new_pncr_gubuns = new String[][] {{"신규", "신규"}, {"보완", "보완"}, {"개선", "개선"}};
	private String priority;
	private String priority_name;
	private List<CodeModel> priorityList;
	private final String[][] prioritys = new String[][] { {"0", "Critical"},{"1", "Major"},{"2", "Minor"},{"3", "Normal"} }; 
	private String system_seq;
	private String system_seq2;
	private String system_name;
	private String system_name2;
	private String group1_name;
	private String group2_name;
	private String group3_name;
	private List<String> systemKey;
	private String problem_gubun;
	private String problem_gubun_name;
	private List<CodeModel> problem_gubunList;
	private final String[][] problem_gubuns = new String[][] { {"0", "호처리"},{"1", "프로세스"},{"2", "상태관리불량"},{"3", "시험진단관리"},{"4", "과금"},{"5", "통계(OMC)"},{"6", "H/W(보드 문제등)"},{"7", "기타"} };
	private String problem;
	private String requirement;
	private String manager_comment;
	private String bp_comment;
	private String refine_comment;
	private String reject_comment;
	private String reject_flag;
	private String state;
	private String stateInfo;
	private String use_yn;
	private String reg_user;
	private String reg_name;
	private String reg_date;
	private String update_user;
	private String update_date;
	private boolean userChargeGubun;
	
	@AttachFileMasterKey
	private String master_file_id;
	private AttachFileModel file1;
	private AttachFileModel file2;
	private AttachFileModel file3;
	private AttachFileModel file4;
	private AttachFileModel file5;
	private AttachFileModel file6;
	private AttachFileModel file7;
	private AttachFileModel file8;
	private AttachFileModel file9;
	
	/** 검색 조건 */
	private String search_reg_date_start;
	private String search_reg_date_end;
	private String search_system_seq;
	private String search_system_name;
	private String search_reg_user;
	private String search_reg_name;
	private String searchCondition;
	private String searchKeyword = "";
	private List<CodeModel> searchConditionsList;
	private final String[][] searchConditions = new String[][] {{"0", "제목"}, {"1", "문제점"}, {"2", "요구사항"}, {"3", "등록팀"}};
	private String searchRoleCondition = "";
	private List<CodeModel> searchRoleConditionList;
	private final String[][] searchRoleConditions = new String[][] {{"0", "본인 등록"}, {"1", "담당 시스템"}, {"2", "전체"}};
	private String searchNew_pncr_gubun;
	private List<CodeModel> searchNew_pncr_gubunList;
	private final String[][] searchNew_pncr_gubuns = new String[][] {{null, "전체"},{"신규", "신규"}, {"보완", "보완"}, {"개선", "개선"}};
	private String search_state;
	private List<CodeModel> search_stateList;
	private final String[][] search_states = new String[][] {{null, "전체"},{"0", "검토요청"},{"1", "협력사검토요청"}, {"2", "검토완료"}, {"3", "반영"}, {"4", "보류"}, {"5", "반려"} };
	private boolean isMain = false;
	//대분류 중분류 소분류 구분 검색
	private String system_search_gubun = "";
	//부서 선택 검색
	private String search_indept = "";
	/**
	 * 엑셀 파일 명
	 */
	public final String EXCEL_FILE_NAME = "신규_보완_개선 현황";
	
	/**
	 * 팀(SOSOK[session_group_team_name]) 검색
	 */
	private String sosok;
	
	/**
	 * 답글 추가 0905 - ksy
	 */
	private String new_pn_cr_re_seq ;
	private String manager_repl ;
	private String bp_repl ;
	private String reg_user_re ;
	private String reg_date_re ;
	private String update_date_re ;
	private String reg_name_re;
	//매니저,BP의견 수정을위한 구분값
	private String replstatusB;
	private String replstatusM;
	
	private String relation_system_seq[];
	private String registerFlag = "0";
	
	private String deleteList;
	
	public String getRegisterFlag() {
		return registerFlag;
	}
	public void setRegisterFlag(String registerFlag) {
		this.registerFlag = registerFlag;
	}
	public String[] getRelation_system_seq() {
		return relation_system_seq;
	}
	public void setRelation_system_seq(String[] relation_system_seq) {
		this.relation_system_seq = relation_system_seq;
	}
	public String getReject_flag() {
		return reject_flag;
	}
	public void setReject_flag(String reject_flag) {
		this.reject_flag = reject_flag;
	}
	public boolean getUserChargeGubun() {
		return userChargeGubun;
	}
	public void setUserChargeGubun(boolean userChargeGubun) {
		this.userChargeGubun = userChargeGubun;
	}
	public String getNew_pn_cr_no() {
		return new_pn_cr_no;
	}
	public void setNew_pn_cr_no(String new_pn_cr_no) {
		this.new_pn_cr_no = new_pn_cr_no;
	}
	public boolean isMain() {
		return isMain;
	}
	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	public String getManager_comment() {
		return manager_comment;
	}
	public void setManager_comment(String manager_comment) {
		this.manager_comment = manager_comment;
	}
	public String getRefine_comment() {
		return refine_comment;
	}
	public void setRefine_comment(String refine_comment) {
		this.refine_comment = refine_comment;
	}
	public String getReject_comment() {
		return reject_comment;
	}
	public void setReject_comment(String reject_comment) {
		this.reject_comment = reject_comment;
	}
	public AttachFileModel getFile6() {
		return file6;
	}
	public void setFile6(AttachFileModel file6) {
		this.file6 = file6;
	}
	public AttachFileModel getFile7() {
		return file7;
	}
	public void setFile7(AttachFileModel file7) {
		this.file7 = file7;
	}
	public AttachFileModel getFile8() {
		return file8;
	}
	public void setFile8(AttachFileModel file8) {
		this.file8 = file8;
	}
	public AttachFileModel getFile9() {
		return file9;
	}
	public void setFile9(AttachFileModel file9) {
		this.file9 = file9;
	}
	public String getReg_name() {
		return reg_name;
	}
	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
	}
	public String getProblem_gubun_name() {
		return problem_gubun_name;
	}
	public void setProblem_gubun_name(String problem_gubun_name) {
		this.problem_gubun_name = problem_gubun_name;
	}
	public String getPriority_name() {
		return priority_name;
	}
	public void setPriority_name(String priority_name) {
		this.priority_name = priority_name;
	}
	public String getSearch_state() {
		return search_state;
	}
	public void setSearch_state(String search_state) {
		this.search_state = search_state;
	}
	public List<CodeModel> getSearch_stateList() {
		return search_stateList;
	}
	public void setSearch_stateList() {
		this.search_stateList = CodeUtil.convertCodeModel(search_states);
	}
	public String getSearchNew_pncr_gubun() {
		return searchNew_pncr_gubun;
	}
	public void setSearchNew_pncr_gubun(String searchNew_pncr_gubun) {
		this.searchNew_pncr_gubun = searchNew_pncr_gubun;
	}
	public List<CodeModel> getSearchNew_pncr_gubunList() {
		return searchNew_pncr_gubunList;
	}
	public void setSearchNew_pncr_gubunList() {
		this.searchNew_pncr_gubunList = CodeUtil.convertCodeModel(searchNew_pncr_gubuns);
	}
	public String getInit() {
		return init;
	}
	public void setInit(String init) {
		this.init = init;
	}
	public List<String> getSystemKey() {
		return systemKey;
	}
	public void setSystemKey(List<String> systemKey) {
		this.systemKey = systemKey;
	}
	public String getBp_comment() {
		return bp_comment;
	}
	public void setBp_comment(String bp_comment) {
		this.bp_comment = bp_comment;
	}
	public String getSearch_reg_name() {
		return search_reg_name;
	}
	public void setSearch_reg_name(String search_reg_name) {
		this.search_reg_name = search_reg_name;
	}
	public String getSearch_system_name() {
		return search_system_name;
	}
	public void setSearch_system_name(String search_system_name) {
		this.search_system_name = search_system_name;
	}
	public String getSearchRoleCondition() {
		return searchRoleCondition;
	}
	public void setSearchRoleCondition(String searchRoleCondition) {
		this.searchRoleCondition = searchRoleCondition;
	}
	public List<CodeModel> getSearchRoleConditionList() {
		return searchRoleConditionList;
	}
	public void setSearchRoleConditionList() {
		this.searchRoleConditionList = CodeUtil.convertCodeModel(searchRoleConditions);
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public String getSystem_name() {
		return system_name;
	}
	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}
	public List<CodeModel> getPriorityList() {
		return priorityList;
	}
	public void setPriorityList() { 
		this.priorityList = CodeUtil.convertCodeModel(prioritys);
	}
	public List<CodeModel> getProblem_gubunList() {
		return problem_gubunList;
	}
	public void setProblem_gubunList() {
		this.problem_gubunList = CodeUtil.convertCodeModel(problem_gubuns);
	}
	public List<CodeModel> getNew_pncr_gubunList() {
		return new_pncr_gubunList;
	}
	public void setNew_pncr_gubunList() {
		this.new_pncr_gubunList = CodeUtil.convertCodeModel(new_pncr_gubuns);
	}
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
	public String getSearch_reg_user() {
		return search_reg_user;
	}
	public void setSearch_reg_user(String search_reg_user) {
		this.search_reg_user = search_reg_user;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
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
	public String getNew_pn_cr_seq() {
		return new_pn_cr_seq;
	}
	public void setNew_pn_cr_seq(String new_pn_cr_seq) {
		this.new_pn_cr_seq = new_pn_cr_seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNew_pncr_gubun() {
		return new_pncr_gubun;
	}
	public void setNew_pncr_gubun(String new_pncr_gubun) {
		this.new_pncr_gubun = new_pncr_gubun;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getSystem_seq() {
		return system_seq;
	}
	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}
	public String getProblem_gubun() {
		return problem_gubun;
	}
	public void setProblem_gubun(String problem_gubun) {
		this.problem_gubun = problem_gubun;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getMaster_file_id() {
		return master_file_id;
	}
	public void setMaster_file_id(String master_file_id) {
		this.master_file_id = master_file_id;
	}
	public AttachFileModel getFile1() {
		return file1;
	}
	public void setFile1(AttachFileModel file1) {
		this.file1 = file1;
	}
	public AttachFileModel getFile2() {
		return file2;
	}
	public void setFile2(AttachFileModel file2) {
		this.file2 = file2;
	}
	public AttachFileModel getFile3() {
		return file3;
	}
	public void setFile3(AttachFileModel file3) {
		this.file3 = file3;
	}
	public AttachFileModel getFile4() {
		return file4;
	}
	public void setFile4(AttachFileModel file4) {
		this.file4 = file4;
	}
	public AttachFileModel getFile5() {
		return file5;
	}
	public void setFile5(AttachFileModel file5) {
		this.file5 = file5;
	}
	public String getSystem_search_gubun() {
		return system_search_gubun;
	}
	public void setSystem_search_gubun(String system_search_gubun) {
		this.system_search_gubun = system_search_gubun;
	}
	public String getSearch_indept() {
		return search_indept;
	}
	public void setSearch_indept(String search_indept) {
		this.search_indept = search_indept;
	}
	
	
	/**
	 * 답글 추가 0905 - ksy
	 */
	public String getNew_pn_cr_re_seq() {
		return new_pn_cr_re_seq;
	}
	public void setNew_pn_cr_re_seq(String new_pn_cr_re_seq) {
		this.new_pn_cr_re_seq = new_pn_cr_re_seq;
	}
	public String getManager_repl() {
		return manager_repl;
	}
	public void setManager_repl(String manager_repl) {
		this.manager_repl = manager_repl;
	}
	public String getBp_repl() {
		return bp_repl;
	}
	public void setBp_repl(String bp_repl) {
		this.bp_repl = bp_repl;
	}
	public String getReg_user_re() {
		return reg_user_re;
	}
	public void setReg_user_re(String reg_user_re) {
		this.reg_user_re = reg_user_re;
	}
	public String getReg_name_re() {
		return reg_name_re;
	}
	public void setReg_name_re(String reg_name_re) {
		this.reg_name_re = reg_name_re;
	}
	public String getReg_date_re() {
		return reg_date_re;
	}
	public void setReg_date_re(String reg_date_re) {
		this.reg_date_re = reg_date_re;
	}
	public String getUpdate_date_re() {
		return update_date_re;
	}
	public void setUpdate_date_re(String update_date_re) {
		this.update_date_re = update_date_re;
	}
	public String getReplstatusB() {
		return replstatusB;
	}
	public void setReplstatusB(String replstatusB) {
		this.replstatusB = replstatusB;
	}
	public String getReplstatusM() {
		return replstatusM;
	}
	public void setReplstatusM(String replstatusM) {
		this.replstatusM = replstatusM;
	}
	public String getSosok() {
		return sosok;
	}
	public void setSosok(String sosok) {
		this.sosok = sosok;
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
	public String getSystem_seq2() {
		return system_seq2;
	}
	public void setSystem_seq2(String system_seq2) {
		this.system_seq2 = system_seq2;
	}
	public String getSystem_name2() {
		return system_name2;
	}
	public void setSystem_name2(String system_name2) {
		this.system_name2 = system_name2;
	}
	public String getDeleteList() {
		return deleteList;
	}
	public void setDeleteList(String deleteList) {
		this.deleteList = deleteList;
	}
}
