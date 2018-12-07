package com.pkms.board.request.model;

import java.util.List;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

public class RequestModel extends AbstractModel{
	
	public RequestModel() {
		setRequest_classList();
		setSearch_request_stateList();
	}
	
	private static final long serialVersionUID = 1L;
	
	private String request_seq;
	private String request_state;
	private String request_date_start;
	private String request_date_end;
	private String system_seq;
	private String system_name;
	private String ptn_name1;
	private String ptn_name2;
	private String goal;
	private String content;
	private String request_issue;
	private String request_class;
	private List<CodeModel> request_classList;
	private final String[][] requestClassConditions = new String[][] { {"공급사검증", "공급사검증"}, {"SKT Core 망 Solution팀 검증", "SKT Core 망 Solution팀 검증"}, {"SKT 타팀 검증", "SKT 타팀 검증"} };
	private String reg_time_h;
	private String reg_time_m;
	private String system_user_id;
	private String system_user_name;
	private String equip_spec;
	private String equip_date_start;
	private String equip_date_end;
	private String use_yn;
	private String reg_user;
	private String reg_user_name;
	private String update_date;
	private String reg_date;
	private String update_user;
	private String ord;
	private String request_name;
	private String request_phone;
	
	private String stateInfo;
	private String searchKeyword;
	private boolean searchAllManager = false;
	private String request_search_date_start;
	private String request_search_date_end;
	private String search_system_seq;
	private String search_system_name;
	private String search_system_user_id;
	private String search_system_user_name;
	private String search_bp_name;
	private String search_request_state;
	private List<CodeModel> search_request_stateList;
	private final String[][] search_requstStateConditions = new String[][] { {"0", "전체"}, {"1", "요청"}, {"2", "승인"}, {"3", "반려"} };
	
	
	
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getSystem_name() {
		return system_name;
	}
	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}
	public String getSystem_user_name() {
		return system_user_name;
	}
	public void setSystem_user_name(String system_user_name) {
		this.system_user_name = system_user_name;
	}
	public String getSearch_system_user_name() {
		return search_system_user_name;
	}
	public void setSearch_system_user_name(String search_system_user_name) {
		this.search_system_user_name = search_system_user_name;
	}
	public String getSearch_system_name() {
		return search_system_name;
	}
	public void setSearch_system_name(String search_system_name) {
		this.search_system_name = search_system_name;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public boolean isSearchAllManager() {
		return searchAllManager;
	}
	public void setSearchAllManager(boolean searchAllManager) {
		this.searchAllManager = searchAllManager;
	}
	public List<CodeModel> getSearch_request_stateList() {
		return search_request_stateList;
	}
	public void setSearch_request_stateList() {
		this.search_request_stateList = CodeUtil.convertCodeModel(search_requstStateConditions);
	}
	public String getSearch_bp_name() {
		return search_bp_name;
	}
	public void setSearch_bp_name(String search_bp_name) {
		this.search_bp_name = search_bp_name;
	}
	public String getRequest_search_date_start() {
		return request_search_date_start;
	}
	public void setRequest_search_date_start(String request_search_date_start) {
		this.request_search_date_start = request_search_date_start;
	}
	public String getRequest_search_date_end() {
		return request_search_date_end;
	}
	public void setRequest_search_date_end(String request_search_date_end) {
		this.request_search_date_end = request_search_date_end;
	}
	public String getSearch_system_seq() {
		return search_system_seq;
	}
	public void setSearch_system_seq(String search_system_seq) {
		this.search_system_seq = search_system_seq;
	}
	public String getSearch_system_user_id() {
		return search_system_user_id;
	}
	public void setSearch_system_user_id(String search_system_user_id) {
		this.search_system_user_id = search_system_user_id;
	}
	public String getSearch_request_state() {
		return search_request_state;
	}
	public void setSearch_request_state(String search_request_state) {
		this.search_request_state = search_request_state;
	}
	public String getRequest_seq() {
		return request_seq;
	}
	public String getRequest_class() {
		return request_class;
	}
	public void setRequest_class(String request_class) {
		this.request_class = request_class;
	}
	
	public List<CodeModel> getRequest_classList() {
		return request_classList;
	}
	public void setRequest_classList() {
		this.request_classList = CodeUtil.convertCodeModel(requestClassConditions);
	}
	public void setRequest_seq(String request_seq) {
		this.request_seq = request_seq;
	}
	public String getRequest_state() {
		return request_state;
	}
	public void setRequest_state(String request_state) {
		this.request_state = request_state;
	}
	public String getRequest_date_start() {
		return request_date_start;
	}
	public void setRequest_date_start(String request_date_start) {
		this.request_date_start = request_date_start;
	}
	public String getRequest_date_end() {
		return request_date_end;
	}
	public void setRequest_date_end(String request_date_end) {
		this.request_date_end = request_date_end;
	}
	public String getSystem_seq() {
		return system_seq;
	}
	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}
	public String getPtn_name1() {
		return ptn_name1;
	}
	public void setPtn_name1(String ptn_name1) {
		this.ptn_name1 = ptn_name1;
	}
	public String getPtn_name2() {
		return ptn_name2;
	}
	public void setPtn_name2(String ptn_name2) {
		this.ptn_name2 = ptn_name2;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRequest_issue() {
		return request_issue;
	}
	public void setRequest_issue(String request_issue) {
		this.request_issue = request_issue;
	}
	public String getReg_time_h() {
		return reg_time_h;
	}
	public void setReg_time_h(String reg_time_h) {
		this.reg_time_h = reg_time_h;
	}
	public String getReg_time_m() {
		return reg_time_m;
	}
	public void setReg_time_m(String reg_time_m) {
		this.reg_time_m = reg_time_m;
	}
	public String getSystem_user_id() {
		return system_user_id;
	}
	public void setSystem_user_id(String system_user_id) {
		this.system_user_id = system_user_id;
	}
	public String getEquip_spec() {
		return equip_spec;
	}
	public void setEquip_spec(String equip_spec) {
		this.equip_spec = equip_spec;
	}
	public String getEquip_date_start() {
		return equip_date_start;
	}
	public void setEquip_date_start(String equip_date_start) {
		this.equip_date_start = equip_date_start;
	}
	public String getEquip_date_end() {
		return equip_date_end;
	}
	public void setEquip_date_end(String equip_date_end) {
		this.equip_date_end = equip_date_end;
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
	public String getReg_user_name() {
		return reg_user_name;
	}
	public void setReg_user_name(String reg_user_name) {
		this.reg_user_name = reg_user_name;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
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
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getRequest_name() {
		return request_name;
	}
	public void setRequest_name(String request_name) {
		this.request_name = request_name;
	}
	public String getRequest_phone() {
		return request_phone;
	}
	public void setRequest_phone(String request_phone) {
		this.request_phone = request_phone;
	}

	
	
}
