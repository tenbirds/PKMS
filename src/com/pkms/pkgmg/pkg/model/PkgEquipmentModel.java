package com.pkms.pkgmg.pkg.model;

import java.util.List;

import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

/**
 * Pkg 검증요청<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 03.
 * 
 */
public class PkgEquipmentModel extends AbstractModel implements Cloneable {
	private static final long serialVersionUID = 1L;
	/**
	 * PKG seq
	 */
	private String pkg_seq;
	
	/**
	 * 초도/확대 구분
	 */
	private String work_gubun;
	
	/**
	 * 장비 seq
	 */
	private String equipment_seq;
	
	/**
	 * 장비 name
	 */
	private String equipment_name;
	
	/**
	 * 장비시스템 연동 key
	 */
	private String eq_work_seq;
	
	/**
	 * 국사(팀) 명
	 */
	private String team_name;
	
	/**
	 * 국사(팀) 명2
	 */
	private String idc_name;

	/**
	 * 국사(팀) CODE
	 */
	private String team_code;
	
	/**
	 * 서비스 영향 지역
	 */
	private String service_area;
	
	/**
	 * 일정
	 */
	private String work_date;
	private String start_date;
	private String end_date;
	
	/**
	 * 시작 시간1
	 */
	private String start_time1 = "";
	
	/**
	 * 시작 시간2
	 */
	private String start_time2 = "";
	
	/**
	 * 종료 시간1
	 */
	private String end_time1 = "";
	
	/**
	 * 종료 시간2
	 */
	private String end_time2 = "";
	
	/**
	 * 사용여부
	 */
	private String use_yn = "";

	/**
	 * 등록자
	 */
	private String reg_user = "";
	
	/**
	 * 등록자 이름
	 */
	private String reg_user_name = "";

	private String reg_user_sosok = "";
	
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
	 * 적용결과
	 */
	private String work_result = "";
	
	/**
	 * 과금결과
	 */
	private String charge_result = "";
	
	/**
	 * 서비스영향도
	 */
	private String ampm = "";
	
	private String eq_history_yn;
	
	/**
	 * PKG ACCESS mdl
	 */
	private String system_nm = "";
	private String equip_seq = "";
	private String cuid = "";
	private String eqp_id = "";
	private String sisul_name = "";
	private String ord = "";
	
	private String patch_title = "";
	private String result_comment = "";
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	
	public String getIdc_name() {
		return idc_name;
	}

	public void setIdc_name(String idc_name) {
		this.idc_name = idc_name;
	}

	public String getService_area() {
		return service_area;
	}
	public void setService_area(String service_area) {
		this.service_area = service_area;
	}
	public String getEquipment_name() {
		return equipment_name;
	}
	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}
	public String getPkg_seq() {
		return pkg_seq;
	}
	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}
	public String getWork_gubun() {
		return work_gubun;
	}
	public void setWork_gubun(String work_gubun) {
		this.work_gubun = work_gubun;
	}
	public String getEquipment_seq() {
		return equipment_seq;
	}
	public void setEquipment_seq(String equipment_seq) {
		this.equipment_seq = equipment_seq;
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

	public String getEq_work_seq() {
		return eq_work_seq;
	}

	public void setEq_work_seq(String eq_work_seq) {
		this.eq_work_seq = eq_work_seq;
	}

	public String getWork_date() {
		return work_date;
	}

	public void setWork_date(String work_date) {
		this.work_date = work_date;
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
	
	public String getWork_result() {
		return work_result;
	}

	public void setWork_result(String work_result) {
		this.work_result = work_result;
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

	public String getReg_user_name() {
		return reg_user_name;
	}

	public void setReg_user_name(String reg_user_name) {
		this.reg_user_name = reg_user_name;
	}

	public String getEq_history_yn() {
		return eq_history_yn;
	}

	public void setEq_history_yn(String eq_history_yn) {
		this.eq_history_yn = eq_history_yn;
	}

	public String getCharge_result() {
		return charge_result;
	}

	public void setCharge_result(String charge_result) {
		this.charge_result = charge_result;
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

	public String getReg_user_sosok() {
		return reg_user_sosok;
	}

	public void setReg_user_sosok(String reg_user_sosok) {
		this.reg_user_sosok = reg_user_sosok;
	}

	public String getSystem_nm() {
		return system_nm;
	}

	public void setSystem_nm(String system_nm) {
		this.system_nm = system_nm;
	}

	public String getEquip_seq() {
		return equip_seq;
	}

	public void setEquip_seq(String equip_seq) {
		this.equip_seq = equip_seq;
	}

	public String getCuid() {
		return cuid;
	}

	public void setCuid(String cuid) {
		this.cuid = cuid;
	}

	public String getEqp_id() {
		return eqp_id;
	}

	public void setEqp_id(String eqp_id) {
		this.eqp_id = eqp_id;
	}

	public String getSisul_name() {
		return sisul_name;
	}

	public void setSisul_name(String sisul_name) {
		this.sisul_name = sisul_name;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public String getPatch_title() {
		return patch_title;
	}

	public void setPatch_title(String patch_title) {
		this.patch_title = patch_title;
	}

	public String getResult_comment() {
		return result_comment;
	}

	public void setResult_comment(String result_comment) {
		this.result_comment = result_comment;
	}
	
}
