package com.pkms.common.workSystem.model;

import com.pkms.common.model.AbstractModel;

public class WorkSystemModel extends AbstractModel {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * as-is table PSAPPLYBAS seq값
	 */
	private String no;
	
	/**
	 * 요청패키지ID: pkg_seq
	 */
	private String work_seq; 
	
	/**
	 * 최종 승인자 사번
	 */
	private String master_id;
	
	/**
	 * 최종 승인자 이름
	 */
	private String master_name;
	
	/**
	 * 최종 승인자 팀코드 INF_PERSON_INFO_RCV.INDEPT
	 */
	private String master_team_code;
	
	/**
	 * 최종 승인자 팀이름 INF_PERSON_INFO_RCV.BOOSER
	 */
	private String master_team_name;
	
	/**
	 * 최종 승인자 연락 번호 INF_PERSON_INFO_RCV.MOVETELNO
	 */
	private String master_movetel;
	
	/**
	 * 상태 하드코딩 "확인" 으로 하드코딩
	 */
	private String state;
	
	/**
	 * 작성일자 sysdate 형식 (승인 완료일)
	 */
	private String c_date;

	/**
	 * 작업 시작 시간
	 */
	private String work_realdate_s; 
	
	/**
	 * 작업 종료 시간
	 */
	private String work_realdate_e;
	
	/**
	 * 작업결과
	 */
	private String work_result;
	
	/**
	 * 작업결과 입력자 id(사번)
	 */
	private String work_result_id;
	
	/**
	 * 작업결과 입력자 이름
	 */
	private String work_result_name;
	
	/**
	 * 작업결과 입력 내용
	 */
	private String result_workservice_text;
	
	/**
	 * 작업결과 입력 기타 사항
	 */
	private String result_etctext; 
	
	/**
	 * as-is table PSAPPLYBAS seq값
	 */
	private int seq = 0;
	
	/**
	 * 검증요청 등록자 : BP 사용자 이름
	 */
	private String work_name;
	
	/**
	 * 계열 id값 : core access data platform 전송
	 */
	private String job_bunya;
	
	private String job_gubun1; // 0418 변수 추가
	
	private int gubun_idx;
	
	/**
	 * 시스템 이름 "PKMS" 하드 코딩
	 */
	private String system_name;
	
	/**
	 * 작업 시작 시간
	 */
	private String work_plandate_s;
	
	/**
	 * 작업 종료 시간
	 */
	private String work_plandate_e;
	
	/**
	 * pkg검증 요청시 입력하는 작업 내역 및 사유
	 */
	private String work_content;
	
	/**
	 * 최종 승인자명
	 */
	private String confirm_name;
	
	/**
	 * pkg 풀네임 
	 */
	private String package_version;

	/**
	 * 작업자 : BP 유저 id
	 */
	private String job_man_id;
	
	/**
	 * 작업자 : BP 유저 이름
	 */
	private String job_man;
	
	/**
	 * 작업자 근무처 : BP 유저 근무처
	 */
	private String job_man_post;
	
	/**
	 * pkg 검증 요청시 입력하는 요청 제목
	 */
	private String title;
	
	/**
	 * "" 하드 코딩
	 */
	private String pkms_check;
	
	/**
	 * pkg 검증 요청시 입력하는 법인 사업자 영향도 라디오 박스 영향있음 Y 없음 N
	 */
	private String b2b_use;
	
	/**
	 * pkg 검증 요청시 입력하는 과음 영향도 라디오 박스 영향있음 Y 없음 N
	 */
	private String pay_use;
	
	private String eq_work_seq;
	
	private int service_effecttime; 

	private String service_effect_area;
	
	private String reg_user;
	
	private String system_seq;
	private String equipment_seq;
	private String pkg_seq;
	private String work_date;
	private String pkms_update_chk;
	private String ampm;
	
	private String work_gubun;
	private String team_code;
	
	private String start_time1;
	private String start_time2;
	private String end_time1;
	private String end_time2;
	
	private String[] equipment_seqs;
	
	//141007 작업관리 시스템 -> IWCS 리뉴얼 작업
	private String impo;
	private String ins_name;
	private String ins_id;
	private String ins_sosok;
	private String ins_sosok_code;
	private String ins_date;
	private String gojang_step;
	private String work_effect;
	private String job_gubun2;
	private String work_summary;
	private String work_rank;
	private String work_sosok;
	private String work_phone;
	private String work_result_date_s;
	private String org_gubun;
	private String target_system;
	private String full_system_name;
	private String background_target;
	private String master_date_s;
	private String work_confirm_id;
	private String work_confirm_name;
	private String work_confirm_date_s;
	
	private int pkms_seq = 0;
	private String file_name;
	private String file_org_name;
	private String file_path;
	private String master_file_id;
	
	private String start_date;
	private String end_date;
	
	private String s_status="";
	
	private String tango_id="";
	private String equipment_name="";
	
	private String ord = "";
	
	public String getJob_gubun1() {
		return job_gubun1;
	}

	public void setJob_gubun1(String job_gubun1) {
		this.job_gubun1 = job_gubun1;
	}

	public int getService_effecttime() {
		return service_effecttime;
	}

	public void setService_effecttime(int service_effecttime) {
		this.service_effecttime = service_effecttime;
	}

	public String getService_effect_area() {
		return service_effect_area;
	}

	public void setService_effect_area(String service_effect_area) {
		this.service_effect_area = service_effect_area;
	}

	public String getEq_work_seq() {
		return eq_work_seq;
	}

	public void setEq_work_seq(String eq_work_seq) {
		this.eq_work_seq = eq_work_seq;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getWork_seq() {
		return work_seq;
	}

	public void setWork_seq(String work_seq) {
		this.work_seq = work_seq;
	}

	public String getMaster_id() {
		return master_id;
	}

	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}

	public String getMaster_name() {
		return master_name;
	}

	public void setMaster_name(String master_name) {
		this.master_name = master_name;
	}

	public String getMaster_team_code() {
		return master_team_code;
	}

	public void setMaster_team_code(String master_team_code) {
		this.master_team_code = master_team_code;
	}

	public String getMaster_team_name() {
		return master_team_name;
	}

	public void setMaster_team_name(String master_team_name) {
		this.master_team_name = master_team_name;
	}

	public String getMaster_movetel() {
		return master_movetel;
	}

	public void setMaster_movetel(String master_movetel) {
		this.master_movetel = master_movetel;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getC_date() {
		return c_date;
	}

	public void setC_date(String c_date) {
		this.c_date = c_date;
	}

	public String getWork_realdate_s() {
		return work_realdate_s;
	}

	public void setWork_realdate_s(String work_realdate_s) {
		this.work_realdate_s = work_realdate_s;
	}

	public String getWork_realdate_e() {
		return work_realdate_e;
	}

	public void setWork_realdate_e(String work_realdate_e) {
		this.work_realdate_e = work_realdate_e;
	}

	public String getWork_result() {
		return work_result;
	}

	public void setWork_result(String work_result) {
		this.work_result = work_result;
	}

	public String getWork_result_id() {
		return work_result_id;
	}

	public void setWork_result_id(String work_result_id) {
		this.work_result_id = work_result_id;
	}

	public String getWork_result_name() {
		return work_result_name;
	}

	public void setWork_result_name(String work_result_name) {
		this.work_result_name = work_result_name;
	}

	public String getResult_workservice_text() {
		return result_workservice_text;
	}

	public void setResult_workservice_text(String result_workservice_text) {
		this.result_workservice_text = result_workservice_text;
	}

	public String getResult_etctext() {
		return result_etctext;
	}

	public void setResult_etctext(String result_etctext) {
		this.result_etctext = result_etctext;
	}
	
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getWork_name() {
		return work_name;
	}

	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}

	public String getJob_bunya() {
		return job_bunya;
	}

	public void setJob_bunya(String job_bunya) {
		this.job_bunya = job_bunya;
	}

	public String getSystem_name() {
		return system_name;
	}

	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}

	public String getWork_plandate_s() {
		return work_plandate_s;
	}

	public void setWork_plandate_s(String work_plandate_s) {
		this.work_plandate_s = work_plandate_s;
	}

	public String getWork_plandate_e() {
		return work_plandate_e;
	}

	public void setWork_plandate_e(String work_plandate_e) {
		this.work_plandate_e = work_plandate_e;
	}

	public String getWork_content() {
		return work_content;
	}

	public void setWork_content(String work_content) {
		this.work_content = work_content;
	}

	public String getConfirm_name() {
		return confirm_name;
	}

	public void setConfirm_name(String confirm_name) {
		this.confirm_name = confirm_name;
	}

	public String getPackage_version() {
		return package_version;
	}

	public void setPackage_version(String package_version) {
		this.package_version = package_version;
	}
	
	public String getJob_man_id() {
		return job_man_id;
	}

	public void setJob_man_id(String job_man_id) {
		this.job_man_id = job_man_id;
	}

	public String getJob_man() {
		return job_man;
	}

	public void setJob_man(String job_man) {
		this.job_man = job_man;
	}

	public String getJob_man_post() {
		return job_man_post;
	}

	public void setJob_man_post(String job_man_post) {
		this.job_man_post = job_man_post;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPkms_check() {
		return pkms_check;
	}

	public void setPkms_check(String pkms_check) {
		this.pkms_check = pkms_check;
	}

	public String getB2b_use() {
		return b2b_use;
	}

	public void setB2b_use(String b2b_use) {
		this.b2b_use = b2b_use;
	}

	public String getPay_use() {
		return pay_use;
	}

	public void setPay_use(String pay_use) {
		this.pay_use = pay_use;
	}

	public int getGubun_idx() {
		return gubun_idx;
	}

	public void setGubun_idx(int gubun_idx) {
		this.gubun_idx = gubun_idx;
	}

	public String getReg_user() {
		return reg_user;
	}

	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
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

	public String getPkg_seq() {
		return pkg_seq;
	}

	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}

	public String getWork_date() {
		return work_date;
	}

	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}

	public String getPkms_update_chk() {
		return pkms_update_chk;
	}

	public void setPkms_update_chk(String pkms_update_chk) {
		this.pkms_update_chk = pkms_update_chk;
	}

	public String getWork_gubun() {
		return work_gubun;
	}

	public void setWork_gubun(String work_gubun) {
		this.work_gubun = work_gubun;
	}

	public String getAmpm() {
		return ampm;
	}

	public void setAmpm(String ampm) {
		this.ampm = ampm;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
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

	public String[] getEquipment_seqs() {
		return equipment_seqs;
	}

	public void setEquipment_seqs(String[] equipment_seqs) {
		this.equipment_seqs = equipment_seqs;
	}

	public String getImpo() {
		return impo;
	}

	public void setImpo(String impo) {
		this.impo = impo;
	}

	public String getIns_name() {
		return ins_name;
	}

	public void setIns_name(String ins_name) {
		this.ins_name = ins_name;
	}

	public String getIns_id() {
		return ins_id;
	}

	public void setIns_id(String ins_id) {
		this.ins_id = ins_id;
	}

	public String getIns_sosok() {
		return ins_sosok;
	}

	public void setIns_sosok(String ins_sosok) {
		this.ins_sosok = ins_sosok;
	}

	public String getIns_sosok_code() {
		return ins_sosok_code;
	}

	public void setIns_sosok_code(String ins_sosok_code) {
		this.ins_sosok_code = ins_sosok_code;
	}

	public String getIns_date() {
		return ins_date;
	}

	public void setIns_date(String ins_date) {
		this.ins_date = ins_date;
	}

	public String getGojang_step() {
		return gojang_step;
	}

	public void setGojang_step(String gojang_step) {
		this.gojang_step = gojang_step;
	}

	public String getWork_effect() {
		return work_effect;
	}

	public void setWork_effect(String work_effect) {
		this.work_effect = work_effect;
	}

	public String getJob_gubun2() {
		return job_gubun2;
	}

	public void setJob_gubun2(String job_gubun2) {
		this.job_gubun2 = job_gubun2;
	}

	public String getWork_summary() {
		return work_summary;
	}

	public void setWork_summary(String work_summary) {
		this.work_summary = work_summary;
	}

	public String getWork_rank() {
		return work_rank;
	}

	public void setWork_rank(String work_rank) {
		this.work_rank = work_rank;
	}

	public String getWork_sosok() {
		return work_sosok;
	}

	public void setWork_sosok(String work_sosok) {
		this.work_sosok = work_sosok;
	}

	public String getWork_phone() {
		return work_phone;
	}

	public void setWork_phone(String work_phone) {
		this.work_phone = work_phone;
	}

	public String getOrg_gubun() {
		return org_gubun;
	}

	public void setOrg_gubun(String org_gubun) {
		this.org_gubun = org_gubun;
	}

	public String getWork_result_date_s() {
		return work_result_date_s;
	}

	public void setWork_result_date_s(String work_result_date_s) {
		this.work_result_date_s = work_result_date_s;
	}

	public String getTarget_system() {
		return target_system;
	}

	public void setTarget_system(String target_system) {
		this.target_system = target_system;
	}

	public String getFull_system_name() {
		return full_system_name;
	}

	public void setFull_system_name(String full_system_name) {
		this.full_system_name = full_system_name;
	}

	public String getBackground_target() {
		return background_target;
	}

	public void setBackground_target(String background_target) {
		this.background_target = background_target;
	}

	public String getMaster_date_s() {
		return master_date_s;
	}

	public void setMaster_date_s(String master_date_s) {
		this.master_date_s = master_date_s;
	}

	public String getWork_confirm_name() {
		return work_confirm_name;
	}

	public void setWork_confirm_name(String work_confirm_name) {
		this.work_confirm_name = work_confirm_name;
	}

	public String getWork_confirm_date_s() {
		return work_confirm_date_s;
	}

	public void setWork_confirm_date_s(String work_confirm_date_s) {
		this.work_confirm_date_s = work_confirm_date_s;
	}

	public int getPkms_seq() {
		return pkms_seq;
	}

	public void setPkms_seq(int pkms_seq) {
		this.pkms_seq = pkms_seq;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_org_name() {
		return file_org_name;
	}

	public void setFile_org_name(String file_org_name) {
		this.file_org_name = file_org_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getMaster_file_id() {
		return master_file_id;
	}

	public void setMaster_file_id(String master_file_id) {
		this.master_file_id = master_file_id;
	}

	public String getWork_confirm_id() {
		return work_confirm_id;
	}

	public void setWork_confirm_id(String work_confirm_id) {
		this.work_confirm_id = work_confirm_id;
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

	public String getS_status() {
		return s_status;
	}

	public void setS_status(String s_status) {
		this.s_status = s_status;
	}

	public String getTango_id() {
		return tango_id;
	}

	public void setTango_id(String tango_id) {
		this.tango_id = tango_id;
	}

	public String getEquipment_name() {
		return equipment_name;
	}

	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}
	
}
