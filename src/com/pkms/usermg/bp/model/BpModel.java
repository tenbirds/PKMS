package com.pkms.usermg.bp.model;

import java.util.ArrayList;
import java.util.List;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.pkms.usermg.user.model.BpUserModel;
import com.wings.model.CodeModel;

/**
 * 
 * 
 * 협력업체 정보를 표현하는 모델<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 16.
 * 
 */
public class BpModel extends AbstractModel {

	public enum BP_APPROVAL_STATE {

		ALL("2", "전체"), REQUEST("0", "승인요청"), COMPLETE("1", "승인완료"), TEMP("3", "임시저장");

		private String code;
		private String description;

		BP_APPROVAL_STATE(final String code, final String description) {
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

	public enum USE_YN {

		YES("Y", "예"), NO("N", "아니오");

		private String code;
		private String description;

		USE_YN(final String code, final String description) {
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

	/**
	 * 협력사 구분
	 */
	private String bp_gubun = "S";

	/**
	 * 협력사명
	 */
	private String bp_name = "";

	/**
	 * 사업자등록번호
	 */
	private String bp_num = "";
	private String cur_bp_idx = "";

	/**
	 * 업체 승인 요청 시 사용되는 사업자등록번호
	 */
	private String bp_num_temp = "";

	/**
	 * 전화번호 첫번째 자리
	 */
	private String bp_tel1 = "";

	/**
	 * 전화번호 두번째 자리
	 */
	private String bp_tel2 = "";

	/**
	 * 전화번호 세번째 자리
	 */
	private String bp_tel3 = "";

	/**
	 * 주소
	 */
	private String bp_addr = "";

	/**
	 * 설립일자
	 */
	private String bp_established_day = "";

	/**
	 * 자본금
	 */
	private String bp_assets = "";

	/**
	 * 회사형태-주식
	 */
	private String bp_forms_ltd = "L";

	/**
	 * 회사형태-법인
	 */
	private String bp_forms_corp = "I";

	/**
	 * 전문분야1
	 */
	private String bp_expertise_areas1 = "";

	/**
	 * 전문분야2
	 */
	private String bp_expertise_areas2 = "";

	/**
	 * 홈페이지
	 */
	private String bp_url = "";

	/**
	 * 승인상태(요청,완료)
	 */
	private String approval_state = "";

	/**
	 * 사용여부
	 */
	private String use_yn = "Y";

	/**
	 * 작성자
	 */
	private String reg_user = "";

	/**
	 * 작성일
	 */
	private String reg_date = "";

	/**
	 * 수정자
	 */
	private String update_user = "";

	/**
	 * 수정일
	 */
	private String update_date = "";

	/**
	 * 업체/업체 담당자 전체 승인해야 할 개수
	 */
	private int approval_count = 0;

	/**
	 * 승인 담당자 아이디
	 */
	private String approval_user_id = "";

	/**
	 * 승인 담당자 이름
	 */
	private String approval_user_name = "";

	 private String system_seq;
	 private String group1_seq;
	 private String group2_seq;
	 private String group3_seq;
	 private String equipment_seq;
	 private String equipment_name;
	
	/**
	 * 대/중/소/시스템
	 */
	private String group_depth = "";
	
	/**
	 * 담당시스템 검색 SKT 부분 추가
	 */
	private String hname = "";
	private String sosok = "";
	private String empno = "";

	/**
	 * 검색조건 관련
	 */
	private String searchCondition;
	private String searchKeyword = "";
	private String companyCondition = "bp";

	private List<CodeModel> searchConditionsList;
	private final String[][] searchConditions = new String[][] { { "0", "소속명" }, { "1", "담당자명" } };
	
	private List<CodeModel> companyConditionsList;
	private final String[][] companyConditions = new String[][] { { "bp", "협력업체" }, { "skt", "SKT" } , { "operator", "운용팀(장비)" }};
	
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
	
	public String getCompanyCondition() {
		return companyCondition;
	}

	public void setCompanyCondition(String companyCondition) {
		this.companyCondition = companyCondition;
	}
	
	public List<CodeModel> getCompanyConditionsList() {
		return companyConditionsList;
	}

	public void setCompanyConditionsList() {
		this.companyConditionsList = CodeUtil.convertCodeModel(companyConditions);
	}

	/**
	 * 업체구분 관련
	 */
	private List<CodeModel> bpGubunList;
	private final String[][] bpGubunConstants = new String[][] { { "S", "공급사" }, { "M", "제조사" } };

	public List<CodeModel> getBpGubunList() {
		return bpGubunList;
	}

	public void setBpGubunList() {
		this.bpGubunList = CodeUtil.convertCodeModel(bpGubunConstants);
	}

	/**
	 * 회사형태 관련
	 */
	private List<CodeModel> bpFormsLtdList;
	private final String[][] bpFormsLtdConstants = new String[][] { { "L", "상장" }, { "U", "비상장" } };

	public List<CodeModel> getBpFormsLtdList() {
		return bpFormsLtdList;
	}

	public void setBpFormsLtdList() {
		this.bpFormsLtdList = CodeUtil.convertCodeModel(bpFormsLtdConstants);
	}

	private List<CodeModel> bpFormsCorpList;
	private final String[][] bpFormsCorpConstants = new String[][] { { "I", "개인" }, { "L", "법인" } };

	public List<CodeModel> getBpFormsCorpList() {
		return bpFormsCorpList;
	}

	public void setBpFormsCorpList() {
		this.bpFormsCorpList = CodeUtil.convertCodeModel(bpFormsCorpConstants);
	}

	/**
	 * 승인상태 관련
	 */
	private String searchFilter = "0";
	private List<CodeModel> approvalStateFilterList = new ArrayList<CodeModel>();

	public String getSearchFilter() {
		return searchFilter;
	}

	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}

	public List<CodeModel> getApprovalStateFilterList() {
		return approvalStateFilterList;
	}

	public void setApprovalStateFilterList() {
		this.approvalStateFilterList.clear();
		for (BP_APPROVAL_STATE code : BP_APPROVAL_STATE.values()) {
			CodeModel codeModel = new CodeModel();
			codeModel.setCode(code.getCode());
			codeModel.setCodeName(code.getDescription());
			this.approvalStateFilterList.add(codeModel);
		}
	}

	/**
	 * 사용여부 관련
	 */
	private List<CodeModel> useYnItems;

	public List<CodeModel> getUseYnItems() {
		return useYnItems;
	}

	public void setUseYnItems(List<CodeModel> useYnItems) {
		this.useYnItems = useYnItems;
	}

	// ========================================================================================================

	/**
	 * Constructor
	 */
	public BpModel() {
		setSearchConditionsList();
		setBpGubunList();
		setApprovalStateFilterList();
		setBpFormsLtdList();
		setBpFormsCorpList();
		setCompanyConditionsList();
	}

	public String getBp_gubun() {
		return bp_gubun;
	}

	public void setBp_gubun(String bp_gubun) {
		this.bp_gubun = bp_gubun;
	}

	public String getBp_name() {
		return bp_name;
	}

	public void setBp_name(String bp_name) {
		this.bp_name = bp_name;
	}

	public String getBp_num() {
		return bp_num;
	}

	public void setBp_num(String bp_num) {
		this.bp_num = bp_num;
	}

	public String getCur_bp_idx() {
		return cur_bp_idx;
	}

	public void setCur_bp_idx(String cur_bp_idx) {
		this.cur_bp_idx = cur_bp_idx;
	}

	public String getBp_num_temp() {
		return bp_num_temp;
	}

	public void setBp_num_temp(String bp_num_temp) {
		this.bp_num_temp = bp_num_temp;
	}

	public String getBp_tel1() {
		return bp_tel1;
	}

	public void setBp_tel1(String bp_tel1) {
		this.bp_tel1 = bp_tel1;
	}

	public String getBp_tel2() {
		return bp_tel2;
	}

	public void setBp_tel2(String bp_tel2) {
		this.bp_tel2 = bp_tel2;
	}

	public String getBp_tel3() {
		return bp_tel3;
	}

	public void setBp_tel3(String bp_tel3) {
		this.bp_tel3 = bp_tel3;
	}

	public String getBp_addr() {
		return bp_addr;
	}

	public void setBp_addr(String bp_addr) {
		this.bp_addr = bp_addr;
	}

	public String getBp_established_day() {
		return bp_established_day;
	}

	public void setBp_established_day(String bp_established_day) {
		this.bp_established_day = bp_established_day;
	}

	public String getBp_assets() {
		return bp_assets;
	}

	public void setBp_assets(String bp_assets) {
		this.bp_assets = bp_assets;
	}

	public String getBp_forms_ltd() {
		return bp_forms_ltd;
	}

	public void setBp_forms_ltd(String bp_forms_ltd) {
		this.bp_forms_ltd = bp_forms_ltd;
	}

	public String getBp_forms_corp() {
		return bp_forms_corp;
	}

	public void setBp_forms_corp(String bp_forms_corp) {
		this.bp_forms_corp = bp_forms_corp;
	}

	public String getBp_expertise_areas1() {
		return bp_expertise_areas1;
	}

	public void setBp_expertise_areas1(String bp_expertise_areas1) {
		this.bp_expertise_areas1 = bp_expertise_areas1;
	}

	public String getBp_expertise_areas2() {
		return bp_expertise_areas2;
	}

	public void setBp_expertise_areas2(String bp_expertise_areas2) {
		this.bp_expertise_areas2 = bp_expertise_areas2;
	}

	public String getBp_url() {
		return bp_url;
	}

	public void setBp_url(String bp_url) {
		this.bp_url = bp_url;
	}

	public String getApproval_state() {
		return approval_state;
	}

	public void setApproval_state(String approval_state) {
		this.approval_state = approval_state;
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

	public int getApproval_count() {
		return approval_count;
	}

	public void setApproval_count(int approval_count) {
		this.approval_count = approval_count;
	}

	public String getApproval_user_id() {
		return approval_user_id;
	}

	public void setApproval_user_id(String approval_user_id) {
		this.approval_user_id = approval_user_id;
	}

	public String getApproval_user_name() {
		return approval_user_name;
	}

	public void setApproval_user_name(String approval_user_name) {
		this.approval_user_name = approval_user_name;
	}

	/**
	 * ================================================================
	 * 협력 업체 담당자 모델 중 화면 컨트롤을 위하여 협력 업체 모델에 통합
	 * ================================================================
	 */

	/**
	 * 아이디
	 */
	private String bp_user_id = "";

	/**
	 * 비번
	 */
	private String bp_user_pw = "";

	/**
	 * 이름
	 */
	private String bp_user_name = "";

	/**
	 * 핸드폰번호 첫번째 자리
	 */
	private String bp_user_phone1 = "";

	/**
	 * 핸드폰번호 두번째 자리
	 */
	private String bp_user_phone2 = "";

	/**
	 * 핸드폰번호 세번째 자리
	 */
	private String bp_user_phone3 = "";

	/**
	 * 이메일
	 */
	private String bp_user_email = "";

	/**
	 * 비번에러수
	 */
	private int passwd_err_cnt = 0;
	
	private int cnt_bp_user_id = 0;
	
	private int login_cnt = 0;

	public String getBp_user_id() {
		return bp_user_id;
	}

	public void setBp_user_id(String bp_user_id) {
		this.bp_user_id = bp_user_id;
	}

	public String getBp_user_pw() {
		return bp_user_pw;
	}

	public void setBp_user_pw(String bp_user_pw) {
		this.bp_user_pw = bp_user_pw;
	}

	public String getBp_user_name() {
		return bp_user_name;
	}

	public void setBp_user_name(String bp_user_name) {
		this.bp_user_name = bp_user_name;
	}

	public String getBp_user_phone1() {
		return bp_user_phone1;
	}

	public void setBp_user_phone1(String bp_user_phone1) {
		this.bp_user_phone1 = bp_user_phone1;
	}

	public String getBp_user_phone2() {
		return bp_user_phone2;
	}

	public void setBp_user_phone2(String bp_user_phone2) {
		this.bp_user_phone2 = bp_user_phone2;
	}

	public String getBp_user_phone3() {
		return bp_user_phone3;
	}

	public void setBp_user_phone3(String bp_user_phone3) {
		this.bp_user_phone3 = bp_user_phone3;
	}

	public String getBp_user_email() {
		return bp_user_email;
	}

	public void setBp_user_email(String bp_user_email) {
		this.bp_user_email = bp_user_email;
	}

	public int getPasswd_err_cnt() {
		return passwd_err_cnt;
	}

	public void setPasswd_err_cnt(int passwd_err_cnt) {
		this.passwd_err_cnt = passwd_err_cnt;
	}

	public String getGroup_depth() {
		return group_depth;
	}

	public void setGroup_depth(String group_depth) {
		this.group_depth = group_depth;
	}
	
	public String getHname() {
		return hname;
	}

	public void setHname(String hname) {
		this.hname = hname;
	}

	public String getSosok() {
		return sosok;
	}

	public void setSosok(String sosok) {
		this.sosok = sosok;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public BpUserModel createBpUserModel() {

		BpUserModel bpUserModel = new BpUserModel(this.bp_user_id, this.bp_num, this.bp_user_pw, this.bp_user_name, this.bp_user_phone1, this.bp_user_phone2, this.bp_user_phone3, this.bp_user_email, this.passwd_err_cnt, this.approval_state, this.use_yn, this.reg_user, this.reg_date, this.update_user, this.update_date, this.group_depth);

		bpUserModel.setSession_user_id(super.getSession_user_id());
		bpUserModel.setSession_user_name(super.getSession_user_name());
		bpUserModel.setRetUrl(super.getRetUrl());
		bpUserModel.setFirstIndex(super.getFirstIndex());
		bpUserModel.setRecordCountPerPage(super.getRecordCountPerPage());
		bpUserModel.setPageIndex(super.getPageIndex());
		bpUserModel.setTotalCount(super.getTotalCount());

		return bpUserModel;
	}

	public void updateBpUserModel(BpUserModel bpUserModel) {
		setBp_user_id(bpUserModel.getBp_user_id());
		setBp_num(bpUserModel.getBp_num());
		setBp_user_pw(bpUserModel.getBp_user_pw());
		setBp_user_name(bpUserModel.getBp_user_name());
		setBp_user_phone1(bpUserModel.getBp_user_phone1());
		setBp_user_phone2(bpUserModel.getBp_user_phone2());
		setBp_user_phone3(bpUserModel.getBp_user_phone3());
		setBp_user_email(bpUserModel.getBp_user_email());
		setPasswd_err_cnt(bpUserModel.getPasswd_err_cnt());
		setApproval_state(bpUserModel.getApproval_state());
		setUse_yn(bpUserModel.getUse_yn());
		setReg_user(bpUserModel.getReg_user());
		setReg_date(bpUserModel.getReg_date());
		setUpdate_user(bpUserModel.getUpdate_user());
		setUpdate_date(bpUserModel.getUpdate_date());
		setGroup_depth(bpUserModel.getGroup_depth());
	}

	public int getCnt_bp_user_id() {
		return cnt_bp_user_id;
	}

	public void setCnt_bp_user_id(int cnt_bp_user_id) {
		this.cnt_bp_user_id = cnt_bp_user_id;
	}

	public String getSystem_seq() {
		return system_seq;
	}

	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
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

	public String getEquipment_seq() {
		return equipment_seq;
	}

	public void setEquipment_seq(String equipment_seq) {
		this.equipment_seq = equipment_seq;
	}

	public String getEquipment_name() {
		return equipment_name;
	}

	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}

	public int getLogin_cnt() {
		return login_cnt;
	}

	public void setLogin_cnt(int login_cnt) {
		this.login_cnt = login_cnt;
	}
	
}
