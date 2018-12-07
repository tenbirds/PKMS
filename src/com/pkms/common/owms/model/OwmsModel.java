package com.pkms.common.owms.model;

import com.pkms.common.model.AbstractModel;

public class OwmsModel extends AbstractModel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 요청패키지ID: pkg_seq(varchar 11)
	 */
	private String reqno;
	
	/**
	 * 요청자 사번: 검증 접수자 사번(char 7)
	 */
	private String applicant_id;
	
	/**
	 * 검증 제목 (varchar 100) 
	 */
	private String reqtlt;
	
	/**
	 * 과금 변경 내역 (varchar 1000)
	 */
	private String change_sumary; 
	
	/**
	 * 확인/지원 요청 사항 (varchar 1000) 
	 */
	private String applicant_sumary;
	
	/**
	 * 테스트 장비 정보 (varchar 1000)
	 * 구분자( : )
	 */
	private String equipment_sumary; 
	
	/**
	 * 테스트 단말 정보 (varchar 100)
	 * 구분자( | )
	 */
	private String test_mobile_num;
	
	/**
	 * 긴급 요청 (일반: 0, 긴급: 1)
	 */
	private String emergencyflg;
	
	/**
	 * 테스트 요청 일자: (varchar 14)
	 * 구분자 ( . )
	 */
	private String test_dt;
	
	/**
	 * OWM_PKMS_LINK: seq (PK varchar 10)
	 */
	private String seq;
	
	/**
	 * OWM_PKMS_LINK: reqno(pkg_seq)
	 */
	private String seq_no;
	
	/**
	 * OWM_PKMS_LINK: owms 요청서 번호
	 */
	private String req_doc_id;
	
	/**
	 * OWM_PKMS_LINK: 진행상태코드
	 */
	private String proc_st_cd;
	
	/**
	 * OWM_PKMS_LINK: 사용자 의견
	 */
	private String ctt;
	
	/**
	 * OWM_PKMS_LINK: 변경자사번
	 */
	private String audit_emp_num;
	
	/**
	 * OWM_PKMS_LINK: 변경일시
	 */
	private String chg_dtm;
	
	/**
	 * 과금상태
	 */
	private String pe_status;
	private String pe_status_name;
	
	/**
	 * SELECT 값 
	 */
	private String ctt_0;
	private String ctt_10;
	private String ctt_20;
	private String ctt_25;
	private String ctt_30;
	private String ctt_35;
	private String ctt_40;
	private String ctt_50;
	private String ctt_60;
	private String ctt_65;
	private String ctt_90;
	private String audit_emp_num_0;
	private String audit_emp_num_10;
	private String audit_emp_num_20;
	private String audit_emp_num_25;
	private String audit_emp_num_30;
	private String audit_emp_num_35;
	private String audit_emp_num_40;
	private String audit_emp_num_50;
	private String audit_emp_num_60;
	private String audit_emp_num_65;
	private String audit_emp_num_90;
	private String chg_dtm_0;
	private String chg_dtm_10;
	private String chg_dtm_20;
	private String chg_dtm_25;
	private String chg_dtm_30;
	private String chg_dtm_35;
	private String chg_dtm_40;
	private String chg_dtm_50;
	private String chg_dtm_60;
	private String chg_dtm_65;
	private String chg_dtm_90;
	
	
	
	public String getCtt_0() {
		return ctt_0;
	}

	public void setCtt_0(String ctt_0) {
		this.ctt_0 = ctt_0;
	}

	public String getCtt_10() {
		return ctt_10;
	}

	public void setCtt_10(String ctt_10) {
		this.ctt_10 = ctt_10;
	}

	public String getCtt_20() {
		return ctt_20;
	}

	public void setCtt_20(String ctt_20) {
		this.ctt_20 = ctt_20;
	}

	public String getCtt_25() {
		return ctt_25;
	}

	public void setCtt_25(String ctt_25) {
		this.ctt_25 = ctt_25;
	}

	public String getCtt_30() {
		return ctt_30;
	}

	public void setCtt_30(String ctt_30) {
		this.ctt_30 = ctt_30;
	}

	public String getCtt_35() {
		return ctt_35;
	}

	public void setCtt_35(String ctt_35) {
		this.ctt_35 = ctt_35;
	}

	public String getCtt_40() {
		return ctt_40;
	}

	public void setCtt_40(String ctt_40) {
		this.ctt_40 = ctt_40;
	}

	public String getCtt_50() {
		return ctt_50;
	}

	public void setCtt_50(String ctt_50) {
		this.ctt_50 = ctt_50;
	}

	public String getCtt_60() {
		return ctt_60;
	}

	public void setCtt_60(String ctt_60) {
		this.ctt_60 = ctt_60;
	}

	public String getCtt_65() {
		return ctt_65;
	}

	public void setCtt_65(String ctt_65) {
		this.ctt_65 = ctt_65;
	}

	public String getCtt_90() {
		return ctt_90;
	}

	public void setCtt_90(String ctt_90) {
		this.ctt_90 = ctt_90;
	}

	public String getAudit_emp_num_0() {
		return audit_emp_num_0;
	}

	public void setAudit_emp_num_0(String audit_emp_num_0) {
		this.audit_emp_num_0 = audit_emp_num_0;
	}

	public String getAudit_emp_num_10() {
		return audit_emp_num_10;
	}

	public void setAudit_emp_num_10(String audit_emp_num_10) {
		this.audit_emp_num_10 = audit_emp_num_10;
	}

	public String getAudit_emp_num_20() {
		return audit_emp_num_20;
	}

	public void setAudit_emp_num_20(String audit_emp_num_20) {
		this.audit_emp_num_20 = audit_emp_num_20;
	}

	public String getAudit_emp_num_25() {
		return audit_emp_num_25;
	}

	public void setAudit_emp_num_25(String audit_emp_num_25) {
		this.audit_emp_num_25 = audit_emp_num_25;
	}

	public String getAudit_emp_num_30() {
		return audit_emp_num_30;
	}

	public void setAudit_emp_num_30(String audit_emp_num_30) {
		this.audit_emp_num_30 = audit_emp_num_30;
	}

	public String getAudit_emp_num_35() {
		return audit_emp_num_35;
	}

	public void setAudit_emp_num_35(String audit_emp_num_35) {
		this.audit_emp_num_35 = audit_emp_num_35;
	}

	public String getAudit_emp_num_40() {
		return audit_emp_num_40;
	}

	public void setAudit_emp_num_40(String audit_emp_num_40) {
		this.audit_emp_num_40 = audit_emp_num_40;
	}

	public String getAudit_emp_num_50() {
		return audit_emp_num_50;
	}

	public void setAudit_emp_num_50(String audit_emp_num_50) {
		this.audit_emp_num_50 = audit_emp_num_50;
	}

	public String getAudit_emp_num_60() {
		return audit_emp_num_60;
	}

	public void setAudit_emp_num_60(String audit_emp_num_60) {
		this.audit_emp_num_60 = audit_emp_num_60;
	}

	public String getAudit_emp_num_65() {
		return audit_emp_num_65;
	}

	public void setAudit_emp_num_65(String audit_emp_num_65) {
		this.audit_emp_num_65 = audit_emp_num_65;
	}

	public String getAudit_emp_num_90() {
		return audit_emp_num_90;
	}

	public void setAudit_emp_num_90(String audit_emp_num_90) {
		this.audit_emp_num_90 = audit_emp_num_90;
	}

	public String getChg_dtm_0() {
		return chg_dtm_0;
	}

	public void setChg_dtm_0(String chg_dtm_0) {
		this.chg_dtm_0 = chg_dtm_0;
	}

	public String getChg_dtm_10() {
		return chg_dtm_10;
	}

	public void setChg_dtm_10(String chg_dtm_10) {
		this.chg_dtm_10 = chg_dtm_10;
	}

	public String getChg_dtm_20() {
		return chg_dtm_20;
	}

	public void setChg_dtm_20(String chg_dtm_20) {
		this.chg_dtm_20 = chg_dtm_20;
	}

	public String getChg_dtm_25() {
		return chg_dtm_25;
	}

	public void setChg_dtm_25(String chg_dtm_25) {
		this.chg_dtm_25 = chg_dtm_25;
	}

	public String getChg_dtm_30() {
		return chg_dtm_30;
	}

	public void setChg_dtm_30(String chg_dtm_30) {
		this.chg_dtm_30 = chg_dtm_30;
	}

	public String getChg_dtm_35() {
		return chg_dtm_35;
	}

	public void setChg_dtm_35(String chg_dtm_35) {
		this.chg_dtm_35 = chg_dtm_35;
	}

	public String getChg_dtm_40() {
		return chg_dtm_40;
	}

	public void setChg_dtm_40(String chg_dtm_40) {
		this.chg_dtm_40 = chg_dtm_40;
	}

	public String getChg_dtm_50() {
		return chg_dtm_50;
	}

	public void setChg_dtm_50(String chg_dtm_50) {
		this.chg_dtm_50 = chg_dtm_50;
	}

	public String getChg_dtm_60() {
		return chg_dtm_60;
	}

	public void setChg_dtm_60(String chg_dtm_60) {
		this.chg_dtm_60 = chg_dtm_60;
	}

	public String getChg_dtm_65() {
		return chg_dtm_65;
	}

	public void setChg_dtm_65(String chg_dtm_65) {
		this.chg_dtm_65 = chg_dtm_65;
	}

	public String getChg_dtm_90() {
		return chg_dtm_90;
	}

	public void setChg_dtm_90(String chg_dtm_90) {
		this.chg_dtm_90 = chg_dtm_90;
	}

	public String getPe_status_name() {
		return pe_status_name;
	}

	public void setPe_status_name(String pe_status_name) {
		this.pe_status_name = pe_status_name;
	}

	public String getPe_status() {
		return pe_status;
	}

	public void setPe_status(String pe_status) {
		this.pe_status = pe_status;
	}

	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}
	public String getReq_doc_id() {
		return req_doc_id;
	}
	public void setReq_doc_id(String req_doc_id) {
		this.req_doc_id = req_doc_id;
	}
	public String getProc_st_cd() {
		return proc_st_cd;
	}
	public void setProc_st_cd(String proc_st_cd) {
		this.proc_st_cd = proc_st_cd;
	}
	public String getCtt() {
		return ctt;
	}
	public void setCtt(String ctt) {
		this.ctt = ctt;
	}
	public String getAudit_emp_num() {
		return audit_emp_num;
	}
	public void setAudit_emp_num(String audit_emp_num) {
		this.audit_emp_num = audit_emp_num;
	}
	public String getChg_dtm() {
		return chg_dtm;
	}
	public void setChg_dtm(String chg_dtm) {
		this.chg_dtm = chg_dtm;
	}
	public String getReqno() {
		return reqno;
	}
	public void setReqno(String reqno) {
		this.reqno = reqno;
	}
	public String getApplicant_id() {
		return applicant_id;
	}
	public void setApplicant_id(String applicant_id) {
		this.applicant_id = applicant_id;
	}
	public String getReqtlt() {
		return reqtlt;
	}
	public void setReqtlt(String reqtlt) {
		this.reqtlt = reqtlt;
	}
	public String getChange_sumary() {
		return change_sumary;
	}
	public void setChange_sumary(String change_sumary) {
		this.change_sumary = change_sumary;
	}
	public String getApplicant_sumary() {
		return applicant_sumary;
	}
	public void setApplicant_sumary(String applicant_sumary) {
		this.applicant_sumary = applicant_sumary;
	}
	public String getEquipment_sumary() {
		return equipment_sumary;
	}
	public void setEquipment_sumary(String equipment_sumary) {
		this.equipment_sumary = equipment_sumary;
	}
	public String getTest_mobile_num() {
		return test_mobile_num;
	}
	public void setTest_mobile_num(String test_mobile_num) {
		this.test_mobile_num = test_mobile_num;
	}
	public String getEmergencyflg() {
		return emergencyflg;
	}
	public void setEmergencyflg(String emergencyflg) {
		this.emergencyflg = emergencyflg;
	}
	public String getTest_dt() {
		return test_dt;
	}
	public void setTest_dt(String test_dt) {
		this.test_dt = test_dt;
	}
	
	

}
