package com.pkms.pkgmg.pkg.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pkms.common.attachfile.model.AttachFileMasterKey;
import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.pkms.newpncrmg.model.NewpncrModel;
import com.pkms.pkgmg.diary.model.DiaryModel.DIARY_ITEM;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.model.EquipmentUserModel;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.tempmg.model.TempmgModel;
import com.wings.model.CodeModel;

/**
 * Pkg 검증요청<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 03.
 * 
 */
public class PkgModel extends AbstractModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 엑셀 파일 명
	 */
	public final String EXCEL_FILE_NAME = "PKG 현황";
	
	/**
	 * PKG에서 사용하는 권한
	 */
	private String granted;

	/**
	 * PKG seq
	 */
	private String pkg_seq = "";
	
	/**
	 * 제목
	 */
	private String title = "";

	/**
	 * 버전
	 */
	private String ver = "";
	
	/**
	 * 버전구분
	 */
	private String ver_gubun = "F";
	
	/**
	 * 버전구분 List
	 */
	private List<CodeModel> ver_gubun_list;
	
	/**
	 * 버전구분 목록
	 */
	private final String[][] ver_gubun_list_data = new String[][] { { "F", "Full" }, { "P", "Partial" } };

	/**
	 * 서비스영향도
	 */
	private String ser_yn = "N";
	
	/**
	 * 서비스영향도 List
	 */
	private List<CodeModel> ser_yn_list;
	
	/**
	 * 서비스영향도 목록
	 */
	private final String[][] ser_yn_list_data = new String[][] { { "N", "영향도없음" }, { "Y", "영향도있음" } };
	
	/**
	 * 서비스영향도 내용
	 */
	private String ser_content = "";
	
	/**
	 * 서비스중단시간
	 */
	private String ser_downtime = "";

	/**
	 * 내용
	 */
	private String content = "";

	/**
	 * 반려 여부
	 */
	private boolean turn_down = false;
	
	/**
	 * 상태
	 */
	private String status = "0";
	
	/**
	 * 상태
	 */
	private String pkg_user_status = "0";
	
	/**
	 * 상태(개발/검증 동시 적용)
	 */
	private String status_dev = "0";
	
	/**
	 * 상태별에서 가장 마지막 상태
	 */
	private String max_status = "0";
	
	/**
	 * 상태명
	 */
	private String status_name = "";
	
	/**
	 * system seq
	 */
	private String system_seq = "";
	
	/**
	 * 시스템 변경 시 이전 system seq
	 */
	private String old_system_seq = "";
	
	/**
	 * 시스템 명
	 */
	private String system_name = "";
	
	/**
	 * 시스템 명
	 */
	private String system_name_org = "";
	
	private String sales_user_info = "";
	
	public String getSystem_name_org() {
		return system_name_org;
	}

	public void setSystem_name_org(String system_name_org) {
		this.system_name_org = system_name_org;
	}

	/**
	 * 템플릿 seq
	 */
	private String tpl_seq = "";
	
	/**
	 * 현재 사용 가능 템플릿 버전
	 */
	private String useY_tpl_ver = "";
	
	/**
	 * 템플릿 버전
	 */
	private String tpl_ver = "";

	/**
	 * 사용여부
	 */
	private String use_yn = "";

	/**
	 * 등록자
	 */
	private String reg_user = "";
	
	/**
	 * 등록일
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
	 * 등록자 명
	 */
	private String reg_user_name = "";
	
	/**
	 * 등록자 구분
	 */
	private String reg_user_gubun = "B";
	
	/**
	 * 수정자 명
	 */
	private String update_user_name = "";
	
	/**
	 * 수정자 구분
	 */
	private String update_user_gubun = "B";

	/**
	 * 대/중/소/시스템
	 */
	private String group_depth = "";

	/**
	 * 과금영향도
	 */
	private String pe_yn = "Y";
	
	/**
	 * 과금영향도 list
	 */
	private List<CodeModel> pe_yn_list;
	
	/**
	 * 과금영향도 목록
	 */
	private final String[][] pe_yn_list_data = new String[][] { { "N", "영향도없음" }, { "Y", "영향도있음" } };
	
	/**
	 * 로밍연결
	 */
	private String roaming_link = "N";
	private List<CodeModel> roaming_link_list;
	private final String[][] roaming_link_list_data = new String[][] { { "N", "로밍연동-없음" }, { "상", "로밍연동-상" }, { "중", "로밍연동-중" }, { "하", "로밍연동-하" } };
	
	/**
	 * 우회소통
	 */
	private String bypass_traffic = "N";
	private List<CodeModel> bypass_traffic_list;
	private final String[][] bypass_traffic_list_data = new String[][] { { "N", "없음" }, { "Y", "우회소통" }};
	
	/**
	 * 개발 프로세스
	 */
	private String dev_yn = "Y";
	
	private String dev_yn_bak = "";
	
	private String start_date_04;
	private String start_date_07;
	private String start_date_08;
	
	private String end_date_04;
	private String end_date_07;
	private String end_date_08;
	
	private String comment_04;
	private String comment_07;
	private String comment_08;
	
	private String pkg_road_map_seq_04;
	private String pkg_road_map_seq_07;
	private String pkg_road_map_seq_08;
	
	private String road_map_seq_04;
	private String road_map_seq_07;
	private String road_map_seq_08;
	
	/**
	 *  개발 검증 요청 리스트
	 */
	private List<CodeModel> dev_yn_list;
	private final String[][] dev_yn_list_data = new String[][] { { "Y", "개발/상용 검증요청" }, { "N", "상용 검증요청 (개발검증 생략)" }, { "D", "개발/상용 동시검증요청" }};
	
	/**
	 * 변경내역
	 */
	private String pe_edit_title = "";
	
	/**
	 * 확인/지원 요청확인
	 */
	private String pe_content = "";
	
	/**
	 * 테스트일자
	 */
	private String pe_test_date = "";
	
	/**
	 * 테스트 장비 ip
	 */
	private String pe_equip_ip = "";
	
	/**
	 * 테스트 장비 port
	 */
	private String pe_equip_port = "";
	
	/**
	 * 테스트 단말번호 1
	 */
	private String pe_no_1 = "";

	/**
	 * 테스트 단말번호 2
	 */
	private String pe_no_2 = "";

	/**
	 * 테스트 단말번호 3
	 */
	private String pe_no_3 = "";
	
	/**
	 * 테스트 단말번호 4
	 */
	private String pe_no_4 = "";

	/**
	 * 테스트 단말번호 5
	 */
	private String pe_no_5 = "";

	/**
	 * 긴급요청
	 */
	private String pe_gubun = "";

	/**
	 * 과금 상태
	 */
	private String pe_status = "";
	
	/**
	 * 긴급요청 list
	 */
	private List<CodeModel> pe_gubun_list;
	
	/**
	 * 긴급요청 목록
	 */
	private final String[][] pe_gubun_list_data = new String[][] { { "0", "일반" }, { "1", "긴급" } };
	
	private String content_ord = "";
	
	/**
	 * 프린트 내용
	 */
	private String eq_name = "";
	private String eq_cnt = "";
	private String idc_name = "";
	private String work_gubun = "";
	
	private String max_date = "";
	private String min_date = "";
	
	private String pdv_content = "";
	private String pd_new_pn_cr_gubun = "";
	private String pdv_cnt = "";
	
	/**
	 * 추간된 프린트 내용 메일 보내기
	 */
	private String s_target_system = "";
	private String e_target_system = "";
	
	private String s_work_day = "";
	private String e_work_day = "";
	
	/**
	 * 첨부파일
	 */
	@AttachFileMasterKey
	private String master_file_id = "";
	
	/**
	 * 첨부파일1~8
	 */
	private AttachFileModel file1;
	private AttachFileModel file2;
	private AttachFileModel file3;
	private AttachFileModel file4;
	private AttachFileModel file5;
	private AttachFileModel file6;
	private AttachFileModel file7;
	private AttachFileModel file8;
	private AttachFileModel file37;
	private AttachFileModel file38;
	private AttachFileModel file39;

	/**
	 * 검증결과9~16
	 */
	private AttachFileModel file9;
	private AttachFileModel file10;
	private AttachFileModel file11;
	private AttachFileModel file12;
	private AttachFileModel file13;
	private AttachFileModel file14;
	private AttachFileModel file15;
	private AttachFileModel file16;

	/**
	 * 초도결과17~21
	 */
	private AttachFileModel file17;
	private AttachFileModel file18;
	private AttachFileModel file19;
	private AttachFileModel file20;
	private AttachFileModel file21;

	/**
	 * 확대결과22~~26
	 */
	private AttachFileModel file22;
	private AttachFileModel file23;
	private AttachFileModel file24;
	private AttachFileModel file25;
	private AttachFileModel file26;
	
	/**
	 * step2 첨부파일 추가첨부 
	 */
	private AttachFileModel file27;
	private AttachFileModel file28;
	private AttachFileModel file29;
	private AttachFileModel file30;
	private AttachFileModel file31;
	private AttachFileModel file32;
	private AttachFileModel file33;
	private AttachFileModel file34;
	private AttachFileModel file35;
	private AttachFileModel file36;
	
	/**
	 * step2 개발검증 추가 첨부파일 28개
	 */
	private AttachFileModel file40;
	private AttachFileModel file41;
	private AttachFileModel file42;
	private AttachFileModel file43;
	private AttachFileModel file44;
	
	private AttachFileModel file45;
	private AttachFileModel file46;
	private AttachFileModel file47;
	private AttachFileModel file48;
	private AttachFileModel file49;
	
	private AttachFileModel file50;
	private AttachFileModel file51;
	private AttachFileModel file52;
	private AttachFileModel file53;
	private AttachFileModel file54;
	
	private AttachFileModel file55;
	private AttachFileModel file56;
	private AttachFileModel file57;
	private AttachFileModel file58;
	private AttachFileModel file59;
	
	private AttachFileModel file60;
	private AttachFileModel file61;
	private AttachFileModel file62;
	private AttachFileModel file63;
	private AttachFileModel file64;
	
	private AttachFileModel file65;
	private AttachFileModel file66;
	private AttachFileModel file67;
	
	private AttachFileModel file68;
	
	private AttachFileModel file69;
	private AttachFileModel file70;
	private AttachFileModel file71;
	private AttachFileModel file72;
	private AttachFileModel file73;
	
	private String deleteList;
	
	public AttachFileModel getFile70() {
		return file70;
	}

	public void setFile70(AttachFileModel file70) {
		this.file70 = file70;
	}

	public AttachFileModel getFile71() {
		return file71;
	}

	public void setFile71(AttachFileModel file71) {
		this.file71 = file71;
	}

	public AttachFileModel getFile72() {
		return file72;
	}

	public void setFile72(AttachFileModel file72) {
		this.file72 = file72;
	}

	public AttachFileModel getFile73() {
		return file73;
	}

	public void setFile73(AttachFileModel file73) {
		this.file73 = file73;
	}

	public AttachFileModel getFile69() {
		return file69;
	}

	public void setFile69(AttachFileModel file69) {
		this.file69 = file69;
	}

	public AttachFileModel getFile68() {
		return file68;
	}

	public void setFile68(AttachFileModel file68) {
		this.file68 = file68;
	}

	/**
	 * PKG detail seq
	 */
	private String pkg_detail_seq = "";
	
	/**
	 * PKG detail variable ord
	 */
	private String ord = "";
	
	/**
	 * PKG detail count
	 */
	private String pkg_detail_count = "0";
	
	/**
	 * 유효성 성공 여부
	 */
	private String pkg_detail_validation_success_yn = "Y";
	
	/**
	 * 템플릿 모델 list
	 */
	private List<TempmgModel> tempmgModelList = new ArrayList<TempmgModel>();
	
	/**
	 * 템플릿 모델 list size
	 */
	private int tempmgModelListSize = 0;
	
	/**
	 * PKG detail 모델 list
	 */
	private List<PkgDetailModel> pkgDetailModelList = new ArrayList<PkgDetailModel>();
	
	/**
	 * PKG detail variable 모델 list의 list
	 */
	private List<List<PkgDetailVariableModel>> pkgDetailVariableModelList = new ArrayList<List<PkgDetailVariableModel>>();
	
	/**
	 * PKG detail variable 모델 list
	 */
	private List<PkgDetailVariableModel> pkgDetailVariableModel = new ArrayList<PkgDetailVariableModel>();

	/**
	 * 엑셀 업로드 파일
	 */
	private AttachFileModel file_excel_upload;
	
	/**
	 * PKG detail variable content
	 */
	private String[] detail_variable_content = null;
	
	private String[] detail_variable_content2 = null;
	
	/**
	 * NewPnCr seq
	 */
	private String new_pn_cr_seq = "";
	
	/**
	 * NewPnCr
	 */
	private String newPnCr = "";
	
	/**
	 * NewPnCr title
	 */
	private String newPnCr_title = "";
	
	/**
	 * NewPnCr 모델 list
	 */
	private List<NewpncrModel> newpncrModelList = null;
	
	/**
	 * NewPnCr 구분 list
	 */
	private List<CodeModel> new_pncr_gubunList;

	/**
	 * NewPnCr 목록
	 */
	private final String[][] new_pncr_gubuns = new String[][] { { "신규", "신규" }, { "보완", "보완" }, { "개선", "개선" } };

	private String[] work_result = null;
	
	private String[] charge_result = null;
	
	public String getNew_pn_cr_seq() {
		return new_pn_cr_seq;
	}

	public void setNew_pn_cr_seq(String new_pn_cr_seq) {
		this.new_pn_cr_seq = new_pn_cr_seq;
	}

	public String getNewPnCr_title() {
		return newPnCr_title;
	}

	public void setNewPnCr_title(String newPnCr_title) {
		this.newPnCr_title = newPnCr_title;
	}

	public List<CodeModel> getNew_pncr_gubunList() {
		return new_pncr_gubunList;
	}

	public void setNew_pncr_gubunList() {
		this.new_pncr_gubunList = CodeUtil.convertCodeModel(new_pncr_gubuns);
	}

	public String getNewPnCr() {
		return newPnCr;
	}

	public void setNewPnCr(String newPnCr) {
		this.newPnCr = newPnCr;
	}

	public List<NewpncrModel> getNewpncrModelList() {
		if (newpncrModelList == null) {
			newpncrModelList = new ArrayList<NewpncrModel>();
		}
		return newpncrModelList;
	}

	public void setNewpncrModelList(List<NewpncrModel> newpncrModelList) {
		this.newpncrModelList = newpncrModelList;
	}
	
	/**
	 * 초도승인시 최종완료 구분값
	 */
	private String apply_end = "";
	
	/**
	 * 확대일정 수립시 부분 저장 구분값
	 */
	private String part_save = "";
	
	/**
	 * 검증일
	 */
	private String verify_date_start;
	private String verify_date_end;

	private List<PkgEquipmentModel> pkgEquipmentModelList = null;
	
	private List<PkgEquipmentModel> pkgEquipmentModelList4E = null;	//2012.09.04 추가 확대일정수립에서 초도적용장비를 보여주기 위한 리스트 선언

	/**
	 * Status
	 */ 
	private PkgStatusModel pkgStatusModel = new PkgStatusModel();
	private List<PkgStatusModel> pkgStatusModelList = null;
	private String selected_status = "";
	private String status_crud_callback = "";
	private String status_operation = "";
	private String user_active_status = "";
	private String user_active_user_id = "";
	private String au_comment = "";

	// form 제어
	private String pkg_tab_num = "";

	// 검색조건 - 기간별
	private String dateCondition = "0";
	private String date_start = "";
	private String date_end = "";
	private List<CodeModel> dateConditionsList;
	private final String[][] dateConditions = new String[][] { { "0", "요청일자" }, { "1", "적용일자" } };

	// 검색조건 - 상태별
	private String statusCondition = "";
	private List<CodeModel> statusConditionsList;
	private final String[][] statusConditions = new String[][] { { "", "전 체" }, { "0", "임시작성" }, { "1", "요청중" }, { "2", "상용접수계획" }, { "26", "검증계획승인" }, { "3", "상용검증완료" }, { "4", "초도일정수립" }, { "5", "초도승인요청" }, { "6", "초도승인완료" }, { "7", "초도적용완료" }, { "8", "확대일정수립" }, { "9", "확대결과등록" }, { "99", "반려" } };
	
	//개발 검증 상태표시
	private List<CodeModel> statusConditionsList_dev;
	private final String[][] statusConditions_dev = new String[][] { { "", "전 체" }, { "0", "임시작성" }, { "1", "요청중" }, { "21", "개발검증접수" }, { "22", "개발검증완료" }, { "23", "개발완료보고" }, { "24", "개발완료승인" }, { "2", "상용접수계획" }, { "26", "검증계획승인" }, { "3", "상용검증완료" }, { "4", "초도일정수립" }, { "5", "초도승인요청" }, { "6", "초도승인완료" }, { "7", "초도적용완료" }, { "8", "확대일정수립" }, { "9", "확대결과등록" }, { "99", "반려" } };
	
	// 검색조건 - 조건별
	private String searchCondition = "";
	private String searchKeyword = "";
	private List<CodeModel> searchConditionsList;
	private final String[][] searchConditions = new String[][] { { "0", "제목" }, { "1", "version" } };

	// 검색조건 - 담당별
	private String userCondition = "0";
	private List<CodeModel> userConditionsList;
	private String[][] userConditions;

	// 검색조건 - 대분류별
	private String group1Condition = "";
	private String group2Condition = "";

	// 월별일정
	private String diaryScript = "";

	// Template
	private int totalCountTemplate = 0;
	private String template_file_name = "";

	// 담당자
	private List<PkgUserModel> pkgUserModelList = null;
	private List<SystemUserModel> systemUserModelList = null;
	private List<SystemUserModel> systemUserModelList_25 = null;
	private Map<SysModel, List<EquipmentUserModel>> equipmentUserModelMap = null;

	private String downtime = "";
	private String impact_systems = "";

	private String[] check_seqs = null;
	private String[] work_dates = null;
	private String[] start_times1 = null;
	private String[] start_times2 = null;
	private String[] end_times1 = null;
	private String[] end_times2 = null;
	//121120 jun
	private String[] non_check_seqs = null;
	private String[] all_check_seqs = null;
	
	private String[] ampms = null;
	
//	상용검증결과
	private String ok;
	private String nok;
	private String cok;
	
//	상용검증결과	
	private String ok_dev;
	private String nok_dev;
	private String cok_dev;
	private String bypass_dev;
	
	//보완적용내역 신규/보완/개선 구분값 수정 추가 1002 - ksy
	private String new_pn_cr_gubun;
	
	private String urgency_yn;
	
	private String pe_yn_comment;
	
	private String roaming_link_comment;
	
	private String rm_issue_comment;
	
	private String detailvariable_check = "Y";
	
	//작업 건수 관리
	private String team_name;
	private String team_code;
	private String team_count;
	private int work_limit = 0;
	
	//시스템 수정 링크로 인한 추가
	private String group1_seq;
	private String group2_seq;
	private String group3_seq;
	
	private String branch_type="";
	private String charge_gubun="";
	
	public String getBranch_type() {
		return branch_type;
	}

	public void setBranch_type(String branch_type) {
		this.branch_type = branch_type;
	}

	public String getCok() {
		return cok;
	}

	public void setCok(String cok) {
		this.cok = cok;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getNok() {
		return nok;
	}

	public void setNok(String nok) {
		this.nok = nok;
	}
	
	public String getOk_dev() {
		return ok_dev;
	}

	public void setOk_dev(String ok_dev) {
		this.ok_dev = ok_dev;
	}

	public String getNok_dev() {
		return nok_dev;
	}

	public void setNok_dev(String nok_dev) {
		this.nok_dev = nok_dev;
	}

	public String getCok_dev() {
		return cok_dev;
	}

	public void setCok_dev(String cok_dev) {
		this.cok_dev = cok_dev;
	}
	
	public String getBypass_dev() {
		return bypass_dev;
	}

	public void setBypass_dev(String bypass_dev) {
		this.bypass_dev = bypass_dev;
	}
	
	private String startD;
	private String endD;
	private String work_date;
	public String getStartD() {
		return startD;
	}

	public void setStartD(String startD) {
		this.startD = startD;
	}

	public String getEndD() {
		return endD;
	}

	public void setEndD(String endD) {
		this.endD = endD;
	}

	public String getWork_date() {
		return work_date;
	}

	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}

/** 추가 **/
	String detail_variable_add;
	
	
	public String getDetail_variable_add() {
		return detail_variable_add;
	}

	public void setDetail_variable_add(String detail_variable_add) {
		this.detail_variable_add = detail_variable_add;
	}

	/** 조회 조건 세션 사용 여부 */
	private boolean sessionCondition = true;

	public PkgModel() {
		setVer_gubun_list();
		setSer_yn_list();
		setPe_yn_list();
		setPe_gubun_list();
		setNew_pncr_gubunList();
		setDateConditionsList();
		setStatusConditionsList();
		setStatusConditionsList_dev();
		setSearchConditionsList();
		setDiaryItemList();
		setRoaming_link_list();
		setBypass_traffic_list();
		setDev_yn_list();
		// setUserConditionsList();
	}

	/** 조회 조건 */
	private DIARY_ITEM diaryItem = DIARY_ITEM.APPLY;
	// 월별일정 검색조건 - 담당별
	private String userDiaryCondition = "0";

	public DIARY_ITEM getDiaryItem() {
		return diaryItem;
	}

	public void setDiaryItem(DIARY_ITEM diaryItem) {
		this.diaryItem = diaryItem;
	}

	public void setDiaryItem(String diaryItem) {
		this.diaryItem = DIARY_ITEM.valueOf(diaryItem);
	}

	public String getUserDiaryCondition() {
		return userDiaryCondition;
	}

	public void setUserDiaryCondition(String userDiaryCondition) {
		this.userDiaryCondition = userDiaryCondition;
	}

	private List<CodeModel> userDiaryConditionList;

	public List<CodeModel> getUserDiaryConditionList() {
		String session_user_name = getSession_user_name();
		if (session_user_name != null) {
			if (session_user_name.length() > 10) {
				session_user_name = session_user_name.substring(0, 10) + "...";
			}
		}
		if (getSession_user_type().equals(USER_TYPE.M) || getSession_user_type().equals(USER_TYPE.MM)) {
			this.userDiaryConditionList = CodeUtil.convertCodeModel(new String[][] { { "0", session_user_name }, { "1", "전체" }, {"2", "소속" } });
		}else{
			this.userDiaryConditionList = CodeUtil.convertCodeModel(new String[][] { { "0", session_user_name }, { "1", "전체" } });
		}
		return this.userDiaryConditionList;
	}

	private String[][] diaryItems = new String[][] { { DIARY_ITEM.APPLY.getCode(), DIARY_ITEM.APPLY.getDescription() }, { DIARY_ITEM.VERIFY.getCode(), DIARY_ITEM.VERIFY.getDescription() } };

	private List<CodeModel> diaryItemList;

	private void setDiaryItemList() {
		this.diaryItemList = CodeUtil.convertCodeModel(diaryItems);
	}

	public List<CodeModel> getDiaryItemList() {
		return this.diaryItemList;
	}

	public String[] getCheck_seqs() {
		return check_seqs;
	}

	public void setCheck_seqs(String[] check_seqs) {
		this.check_seqs = check_seqs;
	}

	public String[] getTemplateWidths() {
		return TempmgModel.templateWidths;
	}

	public String getDowntime() {
		return downtime;
	}

	public void setDowntime(String downtime) {
		this.downtime = downtime;
	}

	public String getTpl_ver() {
		return tpl_ver;
	}

	public void setTpl_ver(String tpl_ver) {
		this.tpl_ver = tpl_ver;
	}

	public List<CodeModel> getPe_gubun_list() {
		return pe_gubun_list;
	}

	public void setPe_gubun_list() {
		this.pe_gubun_list = CodeUtil.convertCodeModel(pe_gubun_list_data);
	}

	public String getImpact_systems() {
		return impact_systems;
	}

	public void setImpact_systems(String impact_systems) {
		this.impact_systems = impact_systems;
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

	public List<SystemUserModel> getSystemUserModelList() {
		if (systemUserModelList == null) {
			systemUserModelList = new ArrayList<SystemUserModel>();
		}
		return systemUserModelList;
	}

	public void setSystemUserModelList(List<SystemUserModel> systemUserModelList) {
		this.systemUserModelList = systemUserModelList;
	}

	public List<SystemUserModel> getSystemUserModelList_25() {
		return systemUserModelList_25;
	}

	public void setSystemUserModelList_25(
			List<SystemUserModel> systemUserModelList_25) {
		this.systemUserModelList_25 = systemUserModelList_25;
	}

	public Map<SysModel, List<EquipmentUserModel>> getEquipmentUserModelMap() {
		if (equipmentUserModelMap == null) {
			equipmentUserModelMap = new HashMap<SysModel, List<EquipmentUserModel>>();
		}
		return equipmentUserModelMap;
	}

	public void setEquipmentUserModelMap(Map<SysModel, List<EquipmentUserModel>> equipmentUserModelMap) {
		this.equipmentUserModelMap = equipmentUserModelMap;
	}

	public String getStatus_crud_callback() {
		return status_crud_callback;
	}

	public void setStatus_crud_callback(String status_crud_callback) {
		this.status_crud_callback = status_crud_callback;
	}

	public String getSelected_status() {
		return selected_status;
	}

	public void setSelected_status(String selected_status) {
		this.selected_status = selected_status;
	}

	public PkgStatusModel getPkgStatusModel() {
		return pkgStatusModel;
	}

	public void setPkgStatusModel(PkgStatusModel pkgStatusModel) {
		if (pkgStatusModel == null) {
			pkgStatusModel = new PkgStatusModel();
		}
		this.pkgStatusModel = pkgStatusModel;
	}
	
	public int getTempmgModelListSize() {
		if ("".equals(tempmgModelListSize)) {
			tempmgModelListSize = 0;
		}
		return tempmgModelListSize;
	}

	public void setTempmgModelListSize(int tempmgModelListSize) {
		this.tempmgModelListSize = tempmgModelListSize;
	}

	public String getPkg_detail_seq() {
		return pkg_detail_seq;
	}

	public void setPkg_detail_seq(String pkg_detail_seq) {
		this.pkg_detail_seq = pkg_detail_seq;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public String[] getDetail_variable_content() {
		return detail_variable_content;
	}

	public void setDetail_variable_content(String[] detail_variable_content) {
		this.detail_variable_content = detail_variable_content;
	}
	public String[] getDetail_variable_content2() {
		return detail_variable_content2;
	}
	
	public void setDetail_variable_content2(String[] detail_variable_content2) {
		this.detail_variable_content2 = detail_variable_content2;
	}

	public List<TempmgModel> getTempmgModelList() {
		return tempmgModelList;
	}

	public void setTempmgModelList(List<TempmgModel> tempmgModelList) {
		this.tempmgModelList = tempmgModelList;
	}

	public String getPkg_detail_count() {
		return pkg_detail_count;
	}

	public void setPkg_detail_count(String pkg_detail_count) {
		this.pkg_detail_count = pkg_detail_count;
	}

	public String getPkg_detail_validation_success_yn() {
		return pkg_detail_validation_success_yn;
	}

	public void setPkg_detail_validation_success_yn(String pkg_detail_validation_success_yn) {
		this.pkg_detail_validation_success_yn = pkg_detail_validation_success_yn;
	}

	public AttachFileModel getFile_excel_upload() {
		return file_excel_upload;
	}

	public void setFile_excel_upload(AttachFileModel file_excel_upload) {
		this.file_excel_upload = file_excel_upload;
	}

	public List<PkgDetailModel> getPkgDetailModelList() {
		return pkgDetailModelList;
	}

	public void setPkgDetailModelList(List<PkgDetailModel> pkgDetailModelList) {
		this.pkgDetailModelList = pkgDetailModelList;
	}

	public List<List<PkgDetailVariableModel>> getPkgDetailVariableModelList() {
		return pkgDetailVariableModelList;
	}

	public void setPkgDetailVariableModelList(List<List<PkgDetailVariableModel>> pkgDetailVariableModelList) {
		this.pkgDetailVariableModelList = pkgDetailVariableModelList;
	}

	public List<PkgDetailVariableModel> getPkgDetailVariableModel() {
		return pkgDetailVariableModel;
	}

	public void setPkgDetailVariableModel(List<PkgDetailVariableModel> pkgDetailVariableModel) {
		this.pkgDetailVariableModel = pkgDetailVariableModel;
	}

	public String getTemplate_file_name() {
		return template_file_name;
	}

	public void setTemplate_file_name(String template_file_name) {
		this.template_file_name = template_file_name;
	}

	public int getTotalCountTemplate() {
		return totalCountTemplate;
	}

	public void setTotalCountTemplate(int totalCountTemplate) {
		this.totalCountTemplate = totalCountTemplate;
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

	public String getMaster_file_id() {
		return master_file_id;
	}

	public void setMaster_file_id(String master_file_id) {
		this.master_file_id = master_file_id;
	}

	public String getDiaryScript() {
		return diaryScript;
	}

	public void setDiaryScript(String diaryScript) {
		this.diaryScript = diaryScript;
	}

	public String getGroup_depth() {
		return group_depth;
	}

	public void setGroup_depth(String group_depth) {
		this.group_depth = group_depth;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
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

	// public void setUserConditionsList() {
	// this.userConditionsList =
	// CodeUtil.convertCodeModel(userConditions);
	// }

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
	
	public List<CodeModel> getStatusConditionsList_dev() {
		return statusConditionsList_dev;
	}

	public void setStatusConditionsList_dev() {
		this.statusConditionsList_dev = CodeUtil.convertCodeModel(statusConditions_dev);
	}

	public String getDateCondition() {
		return dateCondition;
	}

	public void setDateCondition(String dateCondition) {
		this.dateCondition = dateCondition;
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

	public List<CodeModel> getDateConditionsList() {
		return dateConditionsList;
	}

	public void setDateConditionsList() {
		this.dateConditionsList = CodeUtil.convertCodeModel(dateConditions);
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
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

	public String getReg_user_gubun() {
		return reg_user_gubun;
	}

	public void setReg_user_gubun(String reg_user_gubun) {
		this.reg_user_gubun = reg_user_gubun;
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

	public String getUpdate_user_gubun() {
		return update_user_gubun;
	}

	public void setUpdate_user_gubun(String update_user_gubun) {
		this.update_user_gubun = update_user_gubun;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getPkg_tab_num() {
		return pkg_tab_num;
	}

	public void setPkg_tab_num(String pkg_tab_num) {
		this.pkg_tab_num = pkg_tab_num;
	}

	public String getPe_edit_title() {
		return pe_edit_title;
	}

	public void setPe_edit_title(String pe_edit_title) {
		this.pe_edit_title = pe_edit_title;
	}

	public String getPe_content() {
		return pe_content;
	}

	public void setPe_content(String pe_content) {
		this.pe_content = pe_content;
	}

	public String getPe_test_date() {
		return pe_test_date;
	}

	public void setPe_test_date(String pe_test_date) {
		this.pe_test_date = pe_test_date;
	}

	public String getPe_equip_ip() {
		return pe_equip_ip;
	}

	public void setPe_equip_ip(String pe_equip_ip) {
		this.pe_equip_ip = pe_equip_ip;
	}

	public String getPe_equip_port() {
		return pe_equip_port;
	}

	public void setPe_equip_port(String pe_equip_port) {
		this.pe_equip_port = pe_equip_port;
	}

	public String getPe_no_1() {
		return pe_no_1;
	}

	public void setPe_no_1(String pe_no_1) {
		this.pe_no_1 = pe_no_1;
	}

	public String getPe_no_2() {
		return pe_no_2;
	}

	public void setPe_no_2(String pe_no_2) {
		this.pe_no_2 = pe_no_2;
	}

	public String getPe_no_3() {
		return pe_no_3;
	}

	public void setPe_no_3(String pe_no_3) {
		this.pe_no_3 = pe_no_3;
	}

	public String getPe_no_4() {
		return pe_no_4;
	}

	public void setPe_no_4(String pe_no_4) {
		this.pe_no_4 = pe_no_4;
	}

	public String getPe_no_5() {
		return pe_no_5;
	}

	public void setPe_no_5(String pe_no_5) {
		this.pe_no_5 = pe_no_5;
	}

	public String getPe_gubun() {
		return pe_gubun;
	}

	public void setPe_gubun(String pe_gubun) {
		this.pe_gubun = pe_gubun;
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

	public List<CodeModel> getSer_yn_list() {
		return ser_yn_list;
	}

	public void setSer_yn_list() {
		this.ser_yn_list = CodeUtil.convertCodeModel(ser_yn_list_data);
	}

	public List<CodeModel> getVer_gubun_list() {
		return ver_gubun_list;
	}

	public void setVer_gubun_list() {
		this.ver_gubun_list = CodeUtil.convertCodeModel(ver_gubun_list_data);
	}

	public String getSystem_name() {
		return system_name;
	}

	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}

	public String getPkg_seq() {
		return pkg_seq;
	}

	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getSer_yn() {
		return ser_yn;
	}

	public void setSer_yn(String ser_yn) {
		this.ser_yn = ser_yn;
	}

	public String getSer_content() {
		return ser_content;
	}

	public void setSer_content(String ser_content) {
		this.ser_content = ser_content;
	}

	public String getSer_downtime() {
		return ser_downtime;
	}

	public void setSer_downtime(String ser_downtime) {
		this.ser_downtime = ser_downtime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPe_yn() {
		return pe_yn;
	}

	public void setPe_yn(String pe_yn) {
		this.pe_yn = pe_yn;
	}
	
	public String getRoaming_link() {
		return roaming_link;
	}

	public void setRoaming_link(String roaming_link) {
		this.roaming_link = roaming_link;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOld_system_seq() {
		return old_system_seq;
	}

	public void setOld_system_seq(String old_system_seq) {
		this.old_system_seq = old_system_seq;
	}

	public String getSystem_seq() {
		return system_seq;
	}

	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}

	public String getTpl_seq() {
		return tpl_seq;
	}

	public void setTpl_seq(String tpl_seq) {
		this.tpl_seq = tpl_seq;
	}

	public String getStatus_operation() {
		return status_operation;
	}

	public void setStatus_operation(String status_operation) {
		this.status_operation = status_operation;
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
	
	
	public List<PkgEquipmentModel> getPkgEquipmentModelList4E() {
		if(pkgEquipmentModelList4E == null){
			pkgEquipmentModelList4E = new ArrayList<PkgEquipmentModel>();
		}
		return pkgEquipmentModelList4E;
	}

	public void setPkgEquipmentModelList4E(List<PkgEquipmentModel> pkgEquipmentModelList4E) {
		this.pkgEquipmentModelList4E = pkgEquipmentModelList4E;
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

	public String getUser_active_status() {
		return user_active_status;
	}

	public void setUser_active_status(String user_active_status) {
		this.user_active_status = user_active_status;
	}

	public String getUser_active_user_id() {
		return user_active_user_id;
	}

	public void setUser_active_user_id(String user_active_user_id) {
		this.user_active_user_id = user_active_user_id;
	}

	public String getAu_comment() {
		return au_comment;
	}

	public void setAu_comment(String au_comment) {
		this.au_comment = au_comment;
	}

	public boolean isTurn_down() {
		return turn_down;
	}

	public void setTurn_down(boolean turn_down) {
		this.turn_down = turn_down;
	}

	public String getMax_status() {
		return max_status;
	}

	public void setMax_status(String max_status) {
		this.max_status = max_status;
	}

	public String getUseY_tpl_ver() {
		return useY_tpl_ver;
	}

	public void setUseY_tpl_ver(String useY_tpl_ver) {
		this.useY_tpl_ver = useY_tpl_ver;
	}

	public String getReg_user_name() {
		return reg_user_name;
	}

	public void setReg_user_name(String reg_user_name) {
		this.reg_user_name = reg_user_name;
	}

	public String getPe_status() {
		return pe_status;
	}

	public void setPe_status(String pe_status) {
		this.pe_status = pe_status;
	}

	public boolean isSessionCondition() {
		return sessionCondition;
	}

	public void setSessionCondition(boolean sessionCondition) {
		this.sessionCondition = sessionCondition;
	}

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	public String[] getWork_dates() {
		return work_dates;
	}

	public void setWork_dates(String[] work_dates) {
		this.work_dates = work_dates;
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

	public List<PkgStatusModel> getPkgStatusModelList() {
		return pkgStatusModelList;
	}

	public void setPkgStatusModelList(List<PkgStatusModel> pkgStatusModelList) {
		this.pkgStatusModelList = pkgStatusModelList;
	}

	public String getGranted() {
		return granted;
	}

	public void setGranted(String granted) {
		this.granted = granted;
	}

	public AttachFileModel getFile9() {
		return file9;
	}

	public void setFile9(AttachFileModel file9) {
		this.file9 = file9;
	}

	public AttachFileModel getFile10() {
		return file10;
	}

	public void setFile10(AttachFileModel file10) {
		this.file10 = file10;
	}

	public AttachFileModel getFile11() {
		return file11;
	}

	public void setFile11(AttachFileModel file11) {
		this.file11 = file11;
	}

	public AttachFileModel getFile12() {
		return file12;
	}

	public void setFile12(AttachFileModel file12) {
		this.file12 = file12;
	}

	public AttachFileModel getFile13() {
		return file13;
	}

	public void setFile13(AttachFileModel file13) {
		this.file13 = file13;
	}

	public AttachFileModel getFile14() {
		return file14;
	}

	public void setFile14(AttachFileModel file14) {
		this.file14 = file14;
	}

	public AttachFileModel getFile15() {
		return file15;
	}

	public void setFile15(AttachFileModel file15) {
		this.file15 = file15;
	}

	public AttachFileModel getFile16() {
		return file16;
	}

	public void setFile16(AttachFileModel file16) {
		this.file16 = file16;
	}

	public AttachFileModel getFile17() {
		return file17;
	}

	public void setFile17(AttachFileModel file17) {
		this.file17 = file17;
	}

	public AttachFileModel getFile18() {
		return file18;
	}

	public void setFile18(AttachFileModel file18) {
		this.file18 = file18;
	}

	public AttachFileModel getFile19() {
		return file19;
	}

	public void setFile19(AttachFileModel file19) {
		this.file19 = file19;
	}

	public AttachFileModel getFile20() {
		return file20;
	}

	public void setFile20(AttachFileModel file20) {
		this.file20 = file20;
	}

	public AttachFileModel getFile21() {
		return file21;
	}

	public void setFile21(AttachFileModel file21) {
		this.file21 = file21;
	}

	public AttachFileModel getFile22() {
		return file22;
	}

	public void setFile22(AttachFileModel file22) {
		this.file22 = file22;
	}

	public AttachFileModel getFile23() {
		return file23;
	}

	public void setFile23(AttachFileModel file23) {
		this.file23 = file23;
	}

	public AttachFileModel getFile24() {
		return file24;
	}

	public void setFile24(AttachFileModel file24) {
		this.file24 = file24;
	}

	public AttachFileModel getFile25() {
		return file25;
	}

	public void setFile25(AttachFileModel file25) {
		this.file25 = file25;
	}

	public AttachFileModel getFile26() {
		return file26;
	}
	
	public AttachFileModel getFile37() {
		return file37;
	}

	public void setFile37(AttachFileModel file37) {
		this.file37 = file37;
	}

	public AttachFileModel getFile38() {
		return file38;
	}

	public void setFile38(AttachFileModel file38) {
		this.file38 = file38;
	}

	public AttachFileModel getFile39() {
		return file39;
	}

	public void setFile39(AttachFileModel file39) {
		this.file39 = file39;
	}

	public void setFile26(AttachFileModel file26) {
		this.file26 = file26;
	}

	public AttachFileModel getFile27() {
		return file27;
	}

	public void setFile27(AttachFileModel file27) {
		this.file27 = file27;
	}

	public AttachFileModel getFile28() {
		return file28;
	}

	public void setFile28(AttachFileModel file28) {
		this.file28 = file28;
	}

	public AttachFileModel getFile29() {
		return file29;
	}

	public void setFile29(AttachFileModel file29) {
		this.file29 = file29;
	}

	public AttachFileModel getFile30() {
		return file30;
	}

	public void setFile30(AttachFileModel file30) {
		this.file30 = file30;
	}

	public AttachFileModel getFile31() {
		return file31;
	}

	public void setFile31(AttachFileModel file31) {
		this.file31 = file31;
	}

	public AttachFileModel getFile32() {
		return file32;
	}

	public void setFile32(AttachFileModel file32) {
		this.file32 = file32;
	}

	public AttachFileModel getFile33() {
		return file33;
	}

	public void setFile33(AttachFileModel file33) {
		this.file33 = file33;
	}

	public AttachFileModel getFile34() {
		return file34;
	}

	public void setFile34(AttachFileModel file34) {
		this.file34 = file34;
	}

	public AttachFileModel getFile35() {
		return file35;
	}

	public void setFile35(AttachFileModel file35) {
		this.file35 = file35;
	}

	public AttachFileModel getFile36() {
		return file36;
	}

	public void setFile36(AttachFileModel file36) {
		this.file36 = file36;
	}
	
	public AttachFileModel getFile40() {
		return file40;
	}

	public void setFile40(AttachFileModel file40) {
		this.file40 = file40;
	}

	public AttachFileModel getFile41() {
		return file41;
	}

	public void setFile41(AttachFileModel file41) {
		this.file41 = file41;
	}

	public AttachFileModel getFile42() {
		return file42;
	}

	public void setFile42(AttachFileModel file42) {
		this.file42 = file42;
	}

	public AttachFileModel getFile43() {
		return file43;
	}

	public void setFile43(AttachFileModel file43) {
		this.file43 = file43;
	}

	public AttachFileModel getFile44() {
		return file44;
	}

	public void setFile44(AttachFileModel file44) {
		this.file44 = file44;
	}

	public AttachFileModel getFile45() {
		return file45;
	}

	public void setFile45(AttachFileModel file45) {
		this.file45 = file45;
	}

	public AttachFileModel getFile46() {
		return file46;
	}

	public void setFile46(AttachFileModel file46) {
		this.file46 = file46;
	}

	public AttachFileModel getFile47() {
		return file47;
	}

	public void setFile47(AttachFileModel file47) {
		this.file47 = file47;
	}

	public AttachFileModel getFile48() {
		return file48;
	}

	public void setFile48(AttachFileModel file48) {
		this.file48 = file48;
	}

	public AttachFileModel getFile49() {
		return file49;
	}

	public void setFile49(AttachFileModel file49) {
		this.file49 = file49;
	}

	public AttachFileModel getFile50() {
		return file50;
	}

	public void setFile50(AttachFileModel file50) {
		this.file50 = file50;
	}

	public AttachFileModel getFile51() {
		return file51;
	}

	public void setFile51(AttachFileModel file51) {
		this.file51 = file51;
	}

	public AttachFileModel getFile52() {
		return file52;
	}

	public void setFile52(AttachFileModel file52) {
		this.file52 = file52;
	}

	public AttachFileModel getFile53() {
		return file53;
	}

	public void setFile53(AttachFileModel file53) {
		this.file53 = file53;
	}

	public AttachFileModel getFile54() {
		return file54;
	}

	public void setFile54(AttachFileModel file54) {
		this.file54 = file54;
	}

	public AttachFileModel getFile55() {
		return file55;
	}

	public void setFile55(AttachFileModel file55) {
		this.file55 = file55;
	}

	public AttachFileModel getFile56() {
		return file56;
	}

	public void setFile56(AttachFileModel file56) {
		this.file56 = file56;
	}

	public AttachFileModel getFile57() {
		return file57;
	}

	public void setFile57(AttachFileModel file57) {
		this.file57 = file57;
	}

	public AttachFileModel getFile58() {
		return file58;
	}

	public void setFile58(AttachFileModel file58) {
		this.file58 = file58;
	}

	public AttachFileModel getFile59() {
		return file59;
	}

	public void setFile59(AttachFileModel file59) {
		this.file59 = file59;
	}

	public AttachFileModel getFile60() {
		return file60;
	}

	public void setFile60(AttachFileModel file60) {
		this.file60 = file60;
	}

	public AttachFileModel getFile61() {
		return file61;
	}

	public void setFile61(AttachFileModel file61) {
		this.file61 = file61;
	}

	public AttachFileModel getFile62() {
		return file62;
	}

	public void setFile62(AttachFileModel file62) {
		this.file62 = file62;
	}

	public AttachFileModel getFile63() {
		return file63;
	}

	public void setFile63(AttachFileModel file63) {
		this.file63 = file63;
	}

	public AttachFileModel getFile64() {
		return file64;
	}

	public void setFile64(AttachFileModel file64) {
		this.file64 = file64;
	}

	public AttachFileModel getFile65() {
		return file65;
	}

	public void setFile65(AttachFileModel file65) {
		this.file65 = file65;
	}

	public AttachFileModel getFile66() {
		return file66;
	}

	public void setFile66(AttachFileModel file66) {
		this.file66 = file66;
	}

	public AttachFileModel getFile67() {
		return file67;
	}

	public void setFile67(AttachFileModel file67) {
		this.file67 = file67;
	}

	//모바일 관련
	private String bp_step3_no = "";
	private String bp_step3_new_pn_cr_gubun = "";
	private String bp_step3_content_ord_0 = ""; //중요도
	private String bp_step3_content_ord_1 = ""; //제목
	private String bp_step3_content_ord_2 = ""; //상용검증결과
	private String bp_step3_content_ord_18 = ""; //검증내역개수
	private String bp_step3_content_ord_19 = ""; //개선내역개수
	
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
	
	public String getCol42() {
		return col42;
	}

	public void setCol42(String col42) {
		this.col42 = col42;
	}

	public String getCol41() {
		return col41;
	}

	public void setCol41(String col41) {
		this.col41 = col41;
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

	private String status_chk = "";

	public String getBp_step3_no() {
		return bp_step3_no;
	}

	public void setBp_step3_no(String bp_step3_no) {
		this.bp_step3_no = bp_step3_no;
	}

	public String getBp_step3_new_pn_cr_gubun() {
		return bp_step3_new_pn_cr_gubun;
	}

	public void setBp_step3_new_pn_cr_gubun(String bp_step3_new_pn_cr_gubun) {
		this.bp_step3_new_pn_cr_gubun = bp_step3_new_pn_cr_gubun;
	}

	public String getBp_step3_content_ord_0() {
		return bp_step3_content_ord_0;
	}

	public void setBp_step3_content_ord_0(String bp_step3_content_ord_0) {
		this.bp_step3_content_ord_0 = bp_step3_content_ord_0;
	}

	public String getBp_step3_content_ord_1() {
		return bp_step3_content_ord_1;
	}

	public void setBp_step3_content_ord_1(String bp_step3_content_ord_1) {
		this.bp_step3_content_ord_1 = bp_step3_content_ord_1;
	}

	public String getBp_step3_content_ord_2() {
		return bp_step3_content_ord_2;
	}

	public void setBp_step3_content_ord_2(String bp_step3_content_ord_2) {
		this.bp_step3_content_ord_2 = bp_step3_content_ord_2;
	}

	public String getBp_step3_content_ord_18() {
		return bp_step3_content_ord_18;
	}

	public void setBp_step3_content_ord_18(String bp_step3_content_ord_18) {
		this.bp_step3_content_ord_18 = bp_step3_content_ord_18;
	}

	public String getBp_step3_content_ord_19() {
		return bp_step3_content_ord_19;
	}

	public void setBp_step3_content_ord_19(String bp_step3_content_ord_19) {
		this.bp_step3_content_ord_19 = bp_step3_content_ord_19;
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

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getCol7() {
		return col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7;
	}

	public String getCol9() {
		return col9;
	}

	public void setCol9(String col9) {
		this.col9 = col9;
	}

	public String getCol11() {
		return col11;
	}

	public void setCol11(String col11) {
		this.col11 = col11;
	}

	public String getCol13() {
		return col13;
	}

	public void setCol13(String col13) {
		this.col13 = col13;
	}

	public String getCol15() {
		return col15;
	}

	public void setCol15(String col15) {
		this.col15 = col15;
	}

	public String getCol17() {
		return col17;
	}

	public void setCol17(String col17) {
		this.col17 = col17;
	}
	
	
	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getCol8() {
		return col8;
	}

	public void setCol8(String col8) {
		this.col8 = col8;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public String getCol12() {
		return col12;
	}

	public void setCol12(String col12) {
		this.col12 = col12;
	}

	public String getCol14() {
		return col14;
	}

	public void setCol14(String col14) {
		this.col14 = col14;
	}

	public String getCol16() {
		return col16;
	}

	public void setCol16(String col16) {
		this.col16 = col16;
	}

	public String getCol18() {
		return col18;
	}

	public void setCol18(String col18) {
		this.col18 = col18;
	}

	public String getCol20() {
		return col20;
	}

	public void setCol20(String col20) {
		this.col20 = col20;
	}

	public String getCol22() {
		return col22;
	}

	public void setCol22(String col22) {
		this.col22 = col22;
	}

	public String getCol24() {
		return col24;
	}

	public void setCol24(String col24) {
		this.col24 = col24;
	}

	public String getCol26() {
		return col26;
	}

	public void setCol26(String col26) {
		this.col26 = col26;
	}
	
	public String getCol19() {
		return col19;
	}

	public void setCol19(String col19) {
		this.col19 = col19;
	}

	public String getCol21() {
		return col21;
	}

	public void setCol21(String col21) {
		this.col21 = col21;
	}

	public String getCol23() {
		return col23;
	}

	public void setCol23(String col23) {
		this.col23 = col23;
	}

	public String getCol25() {
		return col25;
	}

	public void setCol25(String col25) {
		this.col25 = col25;
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

	public String getStatus_chk() {
		return status_chk;
	}

	public void setStatus_chk(String status_chk) {
		this.status_chk = status_chk;
	}

	public String getContent_ord() {
		return content_ord;
	}

	public void setContent_ord(String content_ord) {
		this.content_ord = content_ord;
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

	/**
	 * 검증진도율
	 */
	private String new_col1 = "";
	private String new_col2 = "";
	private String new_col3 = "";
	private String new_col4 = "";
	private String pn_col1 = "";
	private String pn_col2 = "";
	private String pn_col3 = "";
	private String pn_col4 = "";
	private String cr_col1 = "";
	private String cr_col2 = "";
	private String cr_col3 = "";
	private String cr_col4 = "";
	private String total_progress = ""; //진도율
	private String total_verify = ""; //검증내역개수
	private String total_improve = ""; //개선내역개수

	private String content_0 = "";
	private String content_1 = "";
	private String content_2 = "";
	private String content_3 = "";
	private String content_4 = "";
	private String content_5 = "";
	private String content_6 = "";
	private String content_7 = "";
	private String content_8 = "";
	private String content_9 = "";
	private String content_10 = "";
	private String content_11 = "";
	private String content_12 = "";
	private String content_13 = "";
	private String content_14 = "";
	private String content_15 = "";
	private String content_16 = "";
	private String content_17 = "";
	private String content_18 = "";
	private String content_19 = "";
	
	
	
	public String getContent_0() {
		return content_0;
	}

	public void setContent_0(String content_0) {
		this.content_0 = content_0;
	}

	public String getContent_1() {
		return content_1;
	}

	public void setContent_1(String content_1) {
		this.content_1 = content_1;
	}

	public String getContent_2() {
		return content_2;
	}

	public void setContent_2(String content_2) {
		this.content_2 = content_2;
	}

	public String getContent_3() {
		return content_3;
	}

	public void setContent_3(String content_3) {
		this.content_3 = content_3;
	}

	public String getContent_4() {
		return content_4;
	}

	public void setContent_4(String content_4) {
		this.content_4 = content_4;
	}

	public String getContent_5() {
		return content_5;
	}

	public void setContent_5(String content_5) {
		this.content_5 = content_5;
	}

	public String getContent_6() {
		return content_6;
	}

	public void setContent_6(String content_6) {
		this.content_6 = content_6;
	}

	public String getContent_7() {
		return content_7;
	}

	public void setContent_7(String content_7) {
		this.content_7 = content_7;
	}

	public String getContent_8() {
		return content_8;
	}

	public void setContent_8(String content_8) {
		this.content_8 = content_8;
	}

	public String getContent_9() {
		return content_9;
	}

	public void setContent_9(String content_9) {
		this.content_9 = content_9;
	}

	public String getContent_10() {
		return content_10;
	}

	public void setContent_10(String content_10) {
		this.content_10 = content_10;
	}

	public String getContent_11() {
		return content_11;
	}

	public void setContent_11(String content_11) {
		this.content_11 = content_11;
	}

	public String getContent_12() {
		return content_12;
	}

	public void setContent_12(String content_12) {
		this.content_12 = content_12;
	}

	public String getContent_13() {
		return content_13;
	}

	public void setContent_13(String content_13) {
		this.content_13 = content_13;
	}

	public String getContent_14() {
		return content_14;
	}

	public void setContent_14(String content_14) {
		this.content_14 = content_14;
	}

	public String getContent_15() {
		return content_15;
	}

	public void setContent_15(String content_15) {
		this.content_15 = content_15;
	}

	public String getContent_16() {
		return content_16;
	}

	public void setContent_16(String content_16) {
		this.content_16 = content_16;
	}

	public String getContent_17() {
		return content_17;
	}

	public void setContent_17(String content_17) {
		this.content_17 = content_17;
	}

	public String getContent_18() {
		return content_18;
	}

	public void setContent_18(String content_18) {
		this.content_18 = content_18;
	}

	public String getContent_19() {
		return content_19;
	}

	public void setContent_19(String content_19) {
		this.content_19 = content_19;
	}

	public String getNew_col1() {
		return new_col1;
	}

	public void setNew_col1(String new_col1) {
		this.new_col1 = new_col1;
	}

	public String getNew_col2() {
		return new_col2;
	}

	public void setNew_col2(String new_col2) {
		this.new_col2 = new_col2;
	}

	public String getNew_col3() {
		return new_col3;
	}

	public void setNew_col3(String new_col3) {
		this.new_col3 = new_col3;
	}

	public String getNew_col4() {
		return new_col4;
	}

	public void setNew_col4(String new_col4) {
		this.new_col4 = new_col4;
	}

	public String getPn_col1() {
		return pn_col1;
	}

	public void setPn_col1(String pn_col1) {
		this.pn_col1 = pn_col1;
	}

	public String getPn_col2() {
		return pn_col2;
	}

	public void setPn_col2(String pn_col2) {
		this.pn_col2 = pn_col2;
	}

	public String getPn_col3() {
		return pn_col3;
	}

	public void setPn_col3(String pn_col3) {
		this.pn_col3 = pn_col3;
	}

	public String getPn_col4() {
		return pn_col4;
	}

	public void setPn_col4(String pn_col4) {
		this.pn_col4 = pn_col4;
	}

	public String getCr_col1() {
		return cr_col1;
	}

	public void setCr_col1(String cr_col1) {
		this.cr_col1 = cr_col1;
	}

	public String getCr_col2() {
		return cr_col2;
	}

	public void setCr_col2(String cr_col2) {
		this.cr_col2 = cr_col2;
	}

	public String getCr_col3() {
		return cr_col3;
	}

	public void setCr_col3(String cr_col3) {
		this.cr_col3 = cr_col3;
	}

	public String getCr_col4() {
		return cr_col4;
	}

	public void setCr_col4(String cr_col4) {
		this.cr_col4 = cr_col4;
	}
	
	public String getTotal_progress() {
		return total_progress;
	}

	public void setTotal_progress(String total_progress) {
		this.total_progress = total_progress;
	}

	public String getTotal_verify() {
		return total_verify;
	}

	public void setTotal_verify(String total_verify) {
		this.total_verify = total_verify;
	}

	public String getTotal_improve() {
		return total_improve;
	}

	public void setTotal_improve(String total_improve) {
		this.total_improve = total_improve;
	}

	public String getApply_end() {
		return apply_end;
	}

	public void setApply_end(String apply_end) {
		this.apply_end = apply_end;
	}

	public String getPart_save() {
		return part_save;
	}

	public void setPart_save(String part_save) {
		this.part_save = part_save;
	}

	public String[] getNon_check_seqs() {
		return non_check_seqs;
	}

	public void setNon_check_seqs(String[] non_check_seqs) {
		this.non_check_seqs = non_check_seqs;
	}

	public String[] getAll_check_seqs() {
		return all_check_seqs;
	}

	public void setAll_check_seqs(String[] all_check_seqs) {
		this.all_check_seqs = all_check_seqs;
	}
	
	/**
	 * 대표담당자 ID / NAME
	 */
	private String system_user_id = "";
	private String system_user_name = "";
	private String dev_system_user_name = "";

	public String getSystem_user_id() {
		return system_user_id;
	}

	public void setSystem_user_id(String system_user_id) {
		this.system_user_id = system_user_id;
	}

	public String getSystem_user_name() {
		return system_user_name;
	}

	public void setSystem_user_name(String system_user_name) {
		this.system_user_name = system_user_name;
	}
	
	public String getDev_system_user_name() {
		return dev_system_user_name;
	}

	public void setDev_system_user_name(String dev_system_user_name) {
		this.dev_system_user_name = dev_system_user_name;
	}

	public String getNew_pn_cr_gubun() {
		return new_pn_cr_gubun;
	}

	public void setNew_pn_cr_gubun(String new_pn_cr_gubun) {
		this.new_pn_cr_gubun = new_pn_cr_gubun;
	}
	
	public String getUrgency_yn() {
		return urgency_yn;
	}

	public void setUrgency_yn(String urgency_yn) {
		this.urgency_yn = urgency_yn;
	}

	public String getEq_name() {
		return eq_name;
	}

	public void setEq_name(String eq_name) {
		this.eq_name = eq_name;
	}

	public String getEq_cnt() {
		return eq_cnt;
	}

	public void setEq_cnt(String eq_cnt) {
		this.eq_cnt = eq_cnt;
	}

	public String getIdc_name() {
		return idc_name;
	}

	public void setIdc_name(String idc_name) {
		this.idc_name = idc_name;
	}

	public String getPdv_content() {
		return pdv_content;
	}

	public void setPdv_content(String pdv_content) {
		this.pdv_content = pdv_content;
	}

	public String getPd_new_pn_cr_gubun() {
		return pd_new_pn_cr_gubun;
	}

	public void setPd_new_pn_cr_gubun(String pd_new_pn_cr_gubun) {
		this.pd_new_pn_cr_gubun = pd_new_pn_cr_gubun;
	}

	public String getPdv_cnt() {
		return pdv_cnt;
	}

	public void setPdv_cnt(String pdv_cnt) {
		this.pdv_cnt = pdv_cnt;
	}

	public String getPe_yn_comment() {
		return pe_yn_comment;
	}

	public void setPe_yn_comment(String pe_yn_comment) {
		this.pe_yn_comment = pe_yn_comment;
	}

	public String getRoaming_link_comment() {
		return roaming_link_comment;
	}

	public void setRoaming_link_comment(String roaming_link_comment) {
		this.roaming_link_comment = roaming_link_comment;
	}

	public String getRm_issue_comment() {
		return rm_issue_comment;
	}

	public void setRm_issue_comment(String rm_issue_comment) {
		this.rm_issue_comment = rm_issue_comment;
	}

	public String getWork_gubun() {
		return work_gubun;
	}

	public void setWork_gubun(String work_gubun) {
		this.work_gubun = work_gubun;
	}

	public String getMax_date() {
		return max_date;
	}

	public void setMax_date(String max_date) {
		this.max_date = max_date;
	}

	public String getMin_date() {
		return min_date;
	}

	public void setMin_date(String min_date) {
		this.min_date = min_date;
	}

	public String getS_target_system() {
		return s_target_system;
	}

	public void setS_target_system(String s_target_system) {
		this.s_target_system = s_target_system;
	}

	public String getE_target_system() {
		return e_target_system;
	}

	public void setE_target_system(String e_target_system) {
		this.e_target_system = e_target_system;
	}

	public String getS_work_day() {
		return s_work_day;
	}

	public void setS_work_day(String s_work_day) {
		this.s_work_day = s_work_day;
	}

	public String getE_work_day() {
		return e_work_day;
	}

	public void setE_work_day(String e_work_day) {
		this.e_work_day = e_work_day;
	}

	public String[] getAmpms() {
		return ampms;
	}

	public void setAmpms(String[] ampms) {
		this.ampms = ampms;
	}

	public String getDetailvariable_check() {
		return detailvariable_check;
	}

	public void setDetailvariable_check(String detailvariable_check) {
		this.detailvariable_check = detailvariable_check;
	}

	public String getBypass_traffic() {
		return bypass_traffic;
	}

	public void setBypass_traffic(String bypass_traffic) {
		this.bypass_traffic = bypass_traffic;
	}
	
	public List<CodeModel> getBypass_traffic_list() {
		return bypass_traffic_list;
	}

	public void setBypass_traffic_list() {
		this.bypass_traffic_list = CodeUtil.convertCodeModel(bypass_traffic_list_data);
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
	
	public String getStart_date_04() {
		return start_date_04;
	}

	public void setStart_date_04(String start_date_04) {
		this.start_date_04 = start_date_04;
	}

	public String getStart_date_07() {
		return start_date_07;
	}

	public void setStart_date_07(String start_date_07) {
		this.start_date_07 = start_date_07;
	}

	public String getStart_date_08() {
		return start_date_08;
	}

	public void setStart_date_08(String start_date_08) {
		this.start_date_08 = start_date_08;
	}

	public String getEnd_date_04() {
		return end_date_04;
	}

	public void setEnd_date_04(String end_date_04) {
		this.end_date_04 = end_date_04;
	}

	public String getEnd_date_07() {
		return end_date_07;
	}

	public void setEnd_date_07(String end_date_07) {
		this.end_date_07 = end_date_07;
	}

	public String getEnd_date_08() {
		return end_date_08;
	}

	public void setEnd_date_08(String end_date_08) {
		this.end_date_08 = end_date_08;
	}

	public String getComment_04() {
		return comment_04;
	}

	public void setComment_04(String comment_04) {
		this.comment_04 = comment_04;
	}

	public String getComment_07() {
		return comment_07;
	}

	public void setComment_07(String comment_07) {
		this.comment_07 = comment_07;
	}

	public String getComment_08() {
		return comment_08;
	}

	public void setComment_08(String comment_08) {
		this.comment_08 = comment_08;
	}
	
	public String getPkg_road_map_seq_04() {
		return pkg_road_map_seq_04;
	}

	public void setPkg_road_map_seq_04(String pkg_road_map_seq_04) {
		this.pkg_road_map_seq_04 = pkg_road_map_seq_04;
	}

	public String getPkg_road_map_seq_07() {
		return pkg_road_map_seq_07;
	}

	public void setPkg_road_map_seq_07(String pkg_road_map_seq_07) {
		this.pkg_road_map_seq_07 = pkg_road_map_seq_07;
	}

	public String getPkg_road_map_seq_08() {
		return pkg_road_map_seq_08;
	}

	public void setPkg_road_map_seq_08(String pkg_road_map_seq_08) {
		this.pkg_road_map_seq_08 = pkg_road_map_seq_08;
	}
	
	public String getRoad_map_seq_04() {
		return road_map_seq_04;
	}

	public void setRoad_map_seq_04(String road_map_seq_04) {
		this.road_map_seq_04 = road_map_seq_04;
	}

	public String getRoad_map_seq_07() {
		return road_map_seq_07;
	}

	public void setRoad_map_seq_07(String road_map_seq_07) {
		this.road_map_seq_07 = road_map_seq_07;
	}

	public String getRoad_map_seq_08() {
		return road_map_seq_08;
	}

	public void setRoad_map_seq_08(String road_map_seq_08) {
		this.road_map_seq_08 = road_map_seq_08;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public String getTeam_count() {
		return team_count;
	}

	public void setTeam_count(String team_count) {
		this.team_count = team_count;
	}

	public int getWork_limit() {
		return work_limit;
	}

	public void setWork_limit(int work_limit) {
		this.work_limit = work_limit;
	}
	
	//승인 요청 comment
	private String dev_comment="";

	public String getDev_comment() {
		return dev_comment;
	}

	public void setDev_comment(String dev_comment) {
		this.dev_comment = dev_comment;
	}
	
	private List<PkgModel> pkgContetlList = new ArrayList<PkgModel>();

	public List<PkgModel> getPkgContetlList() {
		return pkgContetlList;
	}

	public void setPkgContetlList(List<PkgModel> pkgContetlList) {
		this.pkgContetlList = pkgContetlList;
	}
	
	private int count_hello=0;

	public int getCount_hello() {
		return count_hello;
	}

	public void setCount_hello(int count_hello) {
		this.count_hello = count_hello;
	}

	public String getStatus_dev() {
		return status_dev;
	}

	public void setStatus_dev(String status_dev) {
		this.status_dev = status_dev;
	}
	
	private String vol_yn = "N";
	private String sec_yn = "N";
	private String cha_yn = "N";
	private String non_yn = "N";
	private String on_yn = "N";
	private String user_ok_yn = "N";
	
	public String getVol_yn() {
		return vol_yn;
	}

	public void setVol_yn(String vol_yn) {
		this.vol_yn = vol_yn;
	}

	public String getSec_yn() {
		return sec_yn;
	}

	public void setSec_yn(String sec_yn) {
		this.sec_yn = sec_yn;
	}

	public String getCha_yn() {
		return cha_yn;
	}

	public void setCha_yn(String cha_yn) {
		this.cha_yn = cha_yn;
	}

	public String getNon_yn() {
		return non_yn;
	}

	public void setNon_yn(String non_yn) {
		this.non_yn = non_yn;
	}
	
	public String getOn_yn() {
		return on_yn;
	}

	public void setOn_yn(String on_yn) {
		this.on_yn = on_yn;
	}

	public String getUser_ok_yn() {
		return user_ok_yn;
	}

	public void setUser_ok_yn(String user_ok_yn) {
		this.user_ok_yn = user_ok_yn;
	}

	public String getDev_yn_bak() {
		return dev_yn_bak;
	}

	public void setDev_yn_bak(String dev_yn_bak) {
		this.dev_yn_bak = dev_yn_bak;
	}

	
	/** 패키지검증의 용량검증|보안검증|과금검증|비기능검증  */
	private String verify_type;
	private String result_quest_input;
	private String result_item_input;
	private String result_quest_radio;
	private String result_item_radio;
	private String verify_tem_info;
	private String verify_content;
	private String end;
	private String verify_seq;
	private String verify_ver;
	
	private String col43 = "";
	private String update_gubun = "N";
	
	private String eq_history_yn;
	
	private AttachFileModel file74;
	private AttachFileModel file75;
	private AttachFileModel file76;
	private AttachFileModel file77;
	
	private AttachFileModel file78;
	private AttachFileModel file79;
	private AttachFileModel file80;
	private AttachFileModel file81;
	
	private AttachFileModel file82;
	private AttachFileModel file83;
	private AttachFileModel file84;
	private AttachFileModel file85;
	
	private AttachFileModel file86;
	private AttachFileModel file87;
	private AttachFileModel file88;
	private AttachFileModel file89;
	
	private AttachFileModel file90;
	private AttachFileModel file91;
	private AttachFileModel file92;
	private AttachFileModel file93;
	private AttachFileModel file94;
	
	
	private String urgency_verifi = "";
	
	public String getUrgency_verifi() {
		return urgency_verifi;
	}

	public void setUrgency_verifi(String urgency_verifi) {
		this.urgency_verifi = urgency_verifi;
	}

	public String getVerify_type() {
		return verify_type;
	}

	public void setVerify_type(String verify_type) {
		this.verify_type = verify_type;
	}

	public AttachFileModel getFile74() {
		return file74;
	}

	public void setFile74(AttachFileModel file74) {
		this.file74 = file74;
	}

	public AttachFileModel getFile75() {
		return file75;
	}

	public void setFile75(AttachFileModel file75) {
		this.file75 = file75;
	}

	public AttachFileModel getFile76() {
		return file76;
	}

	public void setFile76(AttachFileModel file76) {
		this.file76 = file76;
	}

	public AttachFileModel getFile77() {
		return file77;
	}

	public void setFile77(AttachFileModel file77) {
		this.file77 = file77;
	}

	public AttachFileModel getFile78() {
		return file78;
	}

	public void setFile78(AttachFileModel file78) {
		this.file78 = file78;
	}

	public AttachFileModel getFile79() {
		return file79;
	}

	public void setFile79(AttachFileModel file79) {
		this.file79 = file79;
	}

	public AttachFileModel getFile80() {
		return file80;
	}

	public void setFile80(AttachFileModel file80) {
		this.file80 = file80;
	}

	public AttachFileModel getFile81() {
		return file81;
	}

	public void setFile81(AttachFileModel file81) {
		this.file81 = file81;
	}

	public AttachFileModel getFile82() {
		return file82;
	}

	public void setFile82(AttachFileModel file82) {
		this.file82 = file82;
	}

	public AttachFileModel getFile83() {
		return file83;
	}

	public void setFile83(AttachFileModel file83) {
		this.file83 = file83;
	}

	public AttachFileModel getFile84() {
		return file84;
	}

	public void setFile84(AttachFileModel file84) {
		this.file84 = file84;
	}

	public String getResult_quest_input() {
		return result_quest_input;
	}

	public void setResult_quest_input(String result_quest_input) {
		this.result_quest_input = result_quest_input;
	}

	public String getResult_item_input() {
		return result_item_input;
	}

	public void setResult_item_input(String result_item_input) {
		this.result_item_input = result_item_input;
	}

	public String getResult_quest_radio() {
		return result_quest_radio;
	}

	public void setResult_quest_radio(String result_quest_radio) {
		this.result_quest_radio = result_quest_radio;
	}

	public String getResult_item_radio() {
		return result_item_radio;
	}

	public void setResult_item_radio(String result_item_radio) {
		this.result_item_radio = result_item_radio;
	}

	public String getVerify_tem_info() {
		return verify_tem_info;
	}

	public void setVerify_tem_info(String verify_tem_info) {
		this.verify_tem_info = verify_tem_info;
	}

	public String getVerify_content() {
		return verify_content;
	}

	public void setVerify_content(String verify_content) {
		this.verify_content = verify_content;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getCol43() {
		return col43;
	}

	public void setCol43(String col43) {
		this.col43 = col43;
	}

	public String getVerify_seq() {
		return verify_seq;
	}

	public void setVerify_seq(String verify_seq) {
		this.verify_seq = verify_seq;
	}

	public String getVerify_ver() {
		return verify_ver;
	}

	public void setVerify_ver(String verify_ver) {
		this.verify_ver = verify_ver;
	}

	public AttachFileModel getFile85() {
		return file85;
	}

	public void setFile85(AttachFileModel file85) {
		this.file85 = file85;
	}

	public AttachFileModel getFile86() {
		return file86;
	}

	public void setFile86(AttachFileModel file86) {
		this.file86 = file86;
	}

	public AttachFileModel getFile87() {
		return file87;
	}

	public void setFile87(AttachFileModel file87) {
		this.file87 = file87;
	}

	public AttachFileModel getFile88() {
		return file88;
	}

	public void setFile88(AttachFileModel file88) {
		this.file88 = file88;
	}

	public AttachFileModel getFile89() {
		return file89;
	}

	public void setFile89(AttachFileModel file89) {
		this.file89 = file89;
	}
	
	public AttachFileModel getFile90() {
		return file90;
	}

	public void setFile90(AttachFileModel file90) {
		this.file90 = file90;
	}

	public AttachFileModel getFile91() {
		return file91;
	}

	public void setFile91(AttachFileModel file91) {
		this.file91 = file91;
	}

	public AttachFileModel getFile92() {
		return file92;
	}

	public void setFile92(AttachFileModel file92) {
		this.file92 = file92;
	}

	public AttachFileModel getFile93() {
		return file93;
	}

	public void setFile93(AttachFileModel file93) {
		this.file93 = file93;
	}

	public AttachFileModel getFile94() {
		return file94;
	}

	public void setFile94(AttachFileModel file94) {
		this.file94 = file94;
	}

	private String selectAjx_YN = "N";

	public String getSelectAjx_YN() {
		return selectAjx_YN;
	}

	public void setSelectAjx_YN(String selectAjx_YN) {
		this.selectAjx_YN = selectAjx_YN;
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

	public String getUpdate_gubun() {
		return update_gubun;
	}

	public void setUpdate_gubun(String update_gubun) {
		this.update_gubun = update_gubun;
	}

	public String getEq_history_yn() {
		return eq_history_yn;
	}

	public void setEq_history_yn(String eq_history_yn) {
		this.eq_history_yn = eq_history_yn;
	}

	public String getSales_user_info() {
		return sales_user_info;
	}

	public void setSales_user_info(String sales_user_info) {
		this.sales_user_info = sales_user_info;
	}

	public String getPkg_user_status() {
		return pkg_user_status;
	}

	public void setPkg_user_status(String pkg_user_status) {
		this.pkg_user_status = pkg_user_status;
	}

	public String getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(String deleteList) {
		this.deleteList = deleteList;
	}

	public String getCharge_gubun() {
		return charge_gubun;
	}

	public void setCharge_gubun(String charge_gubun) {
		this.charge_gubun = charge_gubun;
	}
	
}
