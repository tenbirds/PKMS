package com.pkms.sys.common.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pkms.common.attachfile.model.AttachFileMasterKey;
import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.sys.equipment.model.EquipmentHistoryModel;
import com.pkms.sys.equipment.model.EquipmentUserModel;
import com.pkms.sys.idc.model.IdcModel;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;
import com.wings.model.CodeModel;

public class SysModel extends AbstractModel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private String group1_seq = "";
	private String group2_seq = "";
	private String group3_seq = "";
	private String system_seq = "";
	private String equipment_seq = "";

	// 공통
	private String name = "";
	private String use_yn = "";
	private String reg_user = "";
	private String reg_date = "";
	private String update_user = "";
	private String update_date = "";

	// 시스템
	@AttachFileMasterKey
	private String master_file_id = "";
	private AttachFileModel attachFile1;
	private AttachFileModel attachFile2;
	private AttachFileModel attachFile3;
	private AttachFileModel attachFile4;
	private AttachFileModel attachFile5;
	private AttachFileModel attachFile6;//추가첨부1
	private AttachFileModel attachFile7;//추가첨부2
	private AttachFileModel attachFile8;//추가첨부3
	private AttachFileModel attachFile9;//추가첨부4
	private AttachFileModel attachFile10;//추가첨부5
	private AttachFileModel attachFile11;//추가첨부6
	private AttachFileModel attachFile12;//추가첨부7
	private AttachFileModel attachFile13;//추가첨부8
	private AttachFileModel attachFile14;//추가첨부9
	private AttachFileModel attachFile15;//추가첨부10
	
	//20160309 시스템 화면 바뀜--------------------
	private AttachFileModel attachFile16;
	private AttachFileModel attachFile17;
	private AttachFileModel attachFile18;
	private AttachFileModel attachFile19;
	private AttachFileModel attachFile20;
	private AttachFileModel attachFile21;
	private AttachFileModel attachFile22;
	private AttachFileModel attachFile23;
	private AttachFileModel attachFile24;
	private AttachFileModel attachFile25;
	private AttachFileModel attachFile26;
	private AttachFileModel attachFile27;
	private AttachFileModel attachFile28;
	private AttachFileModel attachFile29;
	private AttachFileModel attachFile30;
	private AttachFileModel attachFile31;
	private String deleteList;
	private String edu_data = "";
	private String standard = "";
	private String per_capa = "";
	private String rm_plan = "";
	//20160309-------------------------------------

	private String bp_num   = "";
	private String bp_name  = "";
	private String sel_bp_num   = "";

	private String bp_num1  = "";
	private String bp_name1 = "";
	private String bp_num2  = "";
	private String bp_name2 = "";
	private String bp_num3  = "";
	private String bp_name3 = "";
	private String bp_num4  = "";
	private String bp_name4 = "";

	private String downtime = "";
	private String impact_systems = "";
	private String system_user_id = "";
	private List<PkgModel> pkgHistoryList = new ArrayList<PkgModel>();

	// 장비
	private String idc_seq = "";
	private String idc_name = "";
	private String team_name = "";
	private String central_name = "";
	private ArrayList<IdcModel> idcList = new ArrayList<IdcModel>();
	private String warehousing_day = "";
	private String service_area = "";
	private String team_code = "";
	private List<EquipmentHistoryModel> pkgEquipmentHistoryList = new ArrayList<EquipmentHistoryModel>();

	// 시스템/장비 공통
	private String treeScript = "";
	private String[] system_user_ids = new String[] {};
	private String[] system_user_names = new String[] {};
	private String sysUserVerifyId = "";
	private String sysUserApprovalId = "";
	private String sysUserDevId = "";
	private String sysUserBizId = "";
	private String sysUserBpId = "";
	private String sysUserOperId = "";
	
	private String[] sel_sysUserVerifyId = new String[] {};
	private String[] sel_sysUserApprovalId = new String[] {};
	private String[] sel_sysUserDevId = new String[] {};
	private String[] sel_sysUserBizId = new String[] {};
	private String[] sel_sysUserBpId = new String[] {};
	private String[] sel_sysUserBpId1 = new String[] {};
	private String[] sel_sysUserBpId2 = new String[] {};
	private String[] sel_sysUserBpId3 = new String[] {};
	private String[] sel_sysUserBpId4 = new String[] {};
	private String[] sel_sysUserOperId = new String[] {};
	private String[] sel_sysUserVolId = new String[] {};
	private String[] sel_sysUserSecId = new String[] {};
	private String[] sel_sysUserChaId = new String[] {};
	private String[] sel_sysUserNonId = new String[] {};
	
	private String[] sel_sysUserVolApprovalId = new String[] {};
	private String[] sel_sysUserChaApprovalId = new String[] {};

	private String[] sel_sysUserVolApprovalNames = new String[] {};
	private String[] sel_sysUserChaApprovalNames = new String[] {};
	
	
	private String[] sel_sysUserVerifyNames = new String[] {};
	private String[] sel_sysUserApprovalNames = new String[] {};
	private String[] sel_sysUserDevNames = new String[] {};
	private String[] sel_sysUserBizNames = new String[] {};
	private String[] sel_sysUserBpNames = new String[] {};
	private String[] sel_sysUserBpNames1 = new String[] {};
	private String[] sel_sysUserBpNames2 = new String[] {};
	private String[] sel_sysUserBpNames3 = new String[] {};
	private String[] sel_sysUserBpNames4 = new String[] {};
	private String[] sel_sysUserOperNames = new String[] {};
	private String[] sel_sysUserVolNames = new String[] {};
	private String[] sel_sysUserSecNames = new String[] {};
	private String[] sel_sysUserChaNames = new String[] {};
	private String[] sel_sysUserNonNames = new String[] {};

	private List<SystemUserModel> systemUserList = new ArrayList<SystemUserModel>();
	private HashMap<SysModel, List<EquipmentUserModel>> equipmentUserMap = new HashMap<SysModel, List<EquipmentUserModel>>();

	private SYSTEM_USER_CHARGE_GUBUN charge_gubun;

	private String check_auth = "N";
	private String[] system_user_charge_gubun = new String[] {};
	private String equipment_user_auth = "N";
	
	private String supply = ""; //공급사
	private String full_name = ""; //Full Name
	private String oneLine_explain = ""; //한줄설명
	private String job_history = ""; //작업내역 -> 시설현황 상세설명 으로 변경
	private String thisYear_job_plan = ""; //당해작업계획 -> 2012 증감설 계획 으로 변경
	private String nextYear_pkg_plan = ""; //2013 PKG 계획
	private String note = ""; //비고
	private String pe_type = "";
	
	private String dev_system_user_id = "";
	private String devsysUserVerifyId = "";
	private String devsysUserApprovalId = "";
	private String[] sel_devsysUserVerifyId = new String[] {};
	private String[] sel_devsysUserApprovalId = new String[] {};
	private String[] sel_devsysUserVerifyNames = new String[] {};
	private String[] sel_devsysUserApprovalNames = new String[] {};
	
	private String locsysUserVerifyId = "";
	private String locsysUserApprovalId = "";
	private String[] sel_locsysUserVerifyId = new String[] {};
	private String[] sel_locsysUserApprovalId = new String[] {};
	private String[] sel_locsysUserVerifyNames = new String[] {};
	private String[] sel_locsysUserApprovalNames = new String[] {};
	
	private String[] sel_sysUserMoId = new String[] {};
	private String[] sel_sysUserMoNames = new String[] {};
	
	private String system_popup_gubun = "";//system_popup_detail
	
	private String bp_system_user_id="";
	private String sales_user_info="";
	
	private String eq_history_yn;
	
	private String eq_names;
	
	// 조회 조건 세션 사용 여부
	private boolean sessionCondition = true;
	
	// 과금장비 여부
	private String cha_yn;
	
	private String sel_id;
	private String[] sel_ids = new String[] {};
	private String[] sel_names = new String[] {};
	private String select_system_user_id;
	public boolean isSessionCondition() {
		return sessionCondition;
	}

	public void setSessionCondition(boolean sessionCondition) {
		this.sessionCondition = sessionCondition;
	}
	
	public SysModel() {
		setSearchConditionsList();
		setSearchGubunsList();
		setCompanyConditionsList();
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

	public String getBp_num() {
		return bp_num;
	}

	public void setBp_num(String bp_num) {
		this.bp_num = bp_num;
	}

	public String getBp_name() {
		return bp_name;
	}

	public void setBp_name(String bp_name) {
		this.bp_name = bp_name;
	}

	public String getSel_bp_num() {
		return sel_bp_num;
	}

	public void setSel_bp_num(String sel_bp_num) {
		this.sel_bp_num = sel_bp_num;
	}
	
	public String getBp_num1() {
		return bp_num1;
	}

	public void setBp_num1(String bp_num1) {
		this.bp_num1 = bp_num1;
	}

	public String getBp_name1() {
		return bp_name1;
	}

	public void setBp_name1(String bp_name1) {
		this.bp_name1 = bp_name1;
	}
	
	public String getBp_num2() {
		return bp_num2;
	}

	public void setBp_num2(String bp_num2) {
		this.bp_num2 = bp_num2;
	}

	public String getBp_name2() {
		return bp_name2;
	}

	public void setBp_name2(String bp_name2) {
		this.bp_name2 = bp_name2;
	}

	public String getBp_num3() {
		return bp_num3;
	}

	public void setBp_num3(String bp_num3) {
		this.bp_num3 = bp_num3;
	}

	public String getBp_name3() {
		return bp_name3;
	}

	public void setBp_name3(String bp_name3) {
		this.bp_name3 = bp_name3;
	}

	public String getBp_num4() {
		return bp_num4;
	}

	public void setBp_num4(String bp_num4) {
		this.bp_num4 = bp_num4;
	}

	public String getBp_name4() {
		return bp_name4;
	}

	public void setBp_name4(String bp_name4) {
		this.bp_name4 = bp_name4;
	}

	public String getMaster_file_id() {
		return master_file_id;
	}

	public void setMaster_file_id(String master_file_id) {
		this.master_file_id = master_file_id;
	}

	public AttachFileModel getAttachFile1() {
		return attachFile1;
	}

	public void setAttachFile1(AttachFileModel attachFile1) {
		this.attachFile1 = attachFile1;
	}

	public AttachFileModel getAttachFile2() {
		return attachFile2;
	}

	public void setAttachFile2(AttachFileModel attachFile2) {
		this.attachFile2 = attachFile2;
	}

	public AttachFileModel getAttachFile3() {
		return attachFile3;
	}

	public void setAttachFile3(AttachFileModel attachFile3) {
		this.attachFile3 = attachFile3;
	}

	public AttachFileModel getAttachFile4() {
		return attachFile4;
	}

	public void setAttachFile4(AttachFileModel attachFile4) {
		this.attachFile4 = attachFile4;
	}

	public AttachFileModel getAttachFile5() {
		return attachFile5;
	}

	public void setAttachFile5(AttachFileModel attachFile5) {
		this.attachFile5 = attachFile5;
	}
	
	public AttachFileModel getAttachFile6() {
		return attachFile6;
	}

	public void setAttachFile6(AttachFileModel attachFile6) {
		this.attachFile6 = attachFile6;
	}
	
	public AttachFileModel getAttachFile7() {
		return attachFile7;
	}

	public void setAttachFile7(AttachFileModel attachFile7) {
		this.attachFile7 = attachFile7;
	}

	public AttachFileModel getAttachFile8() {
		return attachFile8;
	}

	public void setAttachFile8(AttachFileModel attachFile8) {
		this.attachFile8 = attachFile8;
	}

	public AttachFileModel getAttachFile9() {
		return attachFile9;
	}

	public void setAttachFile9(AttachFileModel attachFile9) {
		this.attachFile9 = attachFile9;
	}

	public AttachFileModel getAttachFile10() {
		return attachFile10;
	}

	public void setAttachFile10(AttachFileModel attachFile10) {
		this.attachFile10 = attachFile10;
	}

	public AttachFileModel getAttachFile11() {
		return attachFile11;
	}

	public void setAttachFile11(AttachFileModel attachFile11) {
		this.attachFile11 = attachFile11;
	}

	public AttachFileModel getAttachFile12() {
		return attachFile12;
	}

	public void setAttachFile12(AttachFileModel attachFile12) {
		this.attachFile12 = attachFile12;
	}

	public AttachFileModel getAttachFile13() {
		return attachFile13;
	}

	public void setAttachFile13(AttachFileModel attachFile13) {
		this.attachFile13 = attachFile13;
	}

	public AttachFileModel getAttachFile14() {
		return attachFile14;
	}

	public void setAttachFile14(AttachFileModel attachFile14) {
		this.attachFile14 = attachFile14;
	}

	public AttachFileModel getAttachFile15() {
		return attachFile15;
	}

	public void setAttachFile15(AttachFileModel attachFile15) {
		this.attachFile15 = attachFile15;
	}

	public AttachFileModel getAttachFile16() {
		return attachFile16;
	}

	public void setAttachFile16(AttachFileModel attachFile16) {
		this.attachFile16 = attachFile16;
	}

	public AttachFileModel getAttachFile17() {
		return attachFile17;
	}

	public void setAttachFile17(AttachFileModel attachFile17) {
		this.attachFile17 = attachFile17;
	}

	public AttachFileModel getAttachFile18() {
		return attachFile18;
	}

	public void setAttachFile18(AttachFileModel attachFile18) {
		this.attachFile18 = attachFile18;
	}

	public AttachFileModel getAttachFile19() {
		return attachFile19;
	}

	public void setAttachFile19(AttachFileModel attachFile19) {
		this.attachFile19 = attachFile19;
	}

	public AttachFileModel getAttachFile20() {
		return attachFile20;
	}

	public void setAttachFile20(AttachFileModel attachFile20) {
		this.attachFile20 = attachFile20;
	}

	public AttachFileModel getAttachFile21() {
		return attachFile21;
	}

	public void setAttachFile21(AttachFileModel attachFile21) {
		this.attachFile21 = attachFile21;
	}

	public AttachFileModel getAttachFile22() {
		return attachFile22;
	}

	public void setAttachFile22(AttachFileModel attachFile22) {
		this.attachFile22 = attachFile22;
	}

	public AttachFileModel getAttachFile23() {
		return attachFile23;
	}

	public void setAttachFile23(AttachFileModel attachFile23) {
		this.attachFile23 = attachFile23;
	}

	public AttachFileModel getAttachFile24() {
		return attachFile24;
	}

	public void setAttachFile24(AttachFileModel attachFile24) {
		this.attachFile24 = attachFile24;
	}

	public AttachFileModel getAttachFile25() {
		return attachFile25;
	}

	public void setAttachFile25(AttachFileModel attachFile25) {
		this.attachFile25 = attachFile25;
	}

	public AttachFileModel getAttachFile26() {
		return attachFile26;
	}

	public void setAttachFile26(AttachFileModel attachFile26) {
		this.attachFile26 = attachFile26;
	}

	public AttachFileModel getAttachFile27() {
		return attachFile27;
	}

	public void setAttachFile27(AttachFileModel attachFile27) {
		this.attachFile27 = attachFile27;
	}

	public AttachFileModel getAttachFile28() {
		return attachFile28;
	}

	public void setAttachFile28(AttachFileModel attachFile28) {
		this.attachFile28 = attachFile28;
	}

	public AttachFileModel getAttachFile29() {
		return attachFile29;
	}

	public void setAttachFile29(AttachFileModel attachFile29) {
		this.attachFile29 = attachFile29;
	}

	public AttachFileModel getAttachFile30() {
		return attachFile30;
	}

	public void setAttachFile30(AttachFileModel attachFile30) {
		this.attachFile30 = attachFile30;
	}

	public AttachFileModel getAttachFile31() {
		return attachFile31;
	}

	public void setAttachFile31(AttachFileModel attachFile31) {
		this.attachFile31 = attachFile31;
	}

	public String getIdc_seq() {
		return idc_seq;
	}

	public void setIdc_seq(String idc_seq) {
		this.idc_seq = idc_seq;
	}

	public String getIdc_name() {
		return idc_name;
	}

	public void setIdc_name(String idc_name) {
		this.idc_name = idc_name;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getCentral_name() {
		return central_name;
	}

	public void setCentral_name(String central_name) {
		this.central_name = central_name;
	}

	public ArrayList<IdcModel> getIdcList() {
		return idcList;
	}

	public void setIdcList(ArrayList<IdcModel> idcList) {
		this.idcList = idcList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTreeScript() {
		return treeScript;
	}

	public void setTreeScript(String treeScript) {
		this.treeScript = treeScript;
	}

	public String[] getSystem_user_ids() {
		return system_user_ids;
	}

	public void setSystem_user_ids(String[] system_user_ids) {
		this.system_user_ids = system_user_ids;
	}

	public String[] getSystem_user_names() {
		return system_user_names;
	}

	public void setSystem_user_names(String[] system_user_names) {
		this.system_user_names = system_user_names;
	}

	public String getSysUserVerifyId() {
		return sysUserVerifyId;
	}

	public void setSysUserVerifyId(String sysUserVerifyId) {
		this.sysUserVerifyId = sysUserVerifyId;
	}

	public String getSysUserApprovalId() {
		return sysUserApprovalId;
	}

	public void setSysUserApprovalId(String sysUserApprovalId) {
		this.sysUserApprovalId = sysUserApprovalId;
	}

	public String getSysUserDevId() {
		return sysUserDevId;
	}

	public void setSysUserDevId(String sysUserDevId) {
		this.sysUserDevId = sysUserDevId;
	}

	public String getSysUserBizId() {
		return sysUserBizId;
	}

	public void setSysUserBizId(String sysUserBizId) {
		this.sysUserBizId = sysUserBizId;
	}

	public String getSysUserBpId() {
		return sysUserBpId;
	}

	public void setSysUserBpId(String sysUserBpId) {
		this.sysUserBpId = sysUserBpId;
	}

	public String[] getSel_sysUserVerifyId() {
		return sel_sysUserVerifyId;
	}

	public void setSel_sysUserVerifyId(String[] sel_sysUserVerifyId) {
		this.sel_sysUserVerifyId = sel_sysUserVerifyId;
	}

	public String getSysUserOperId() {
		return sysUserOperId;
	}

	public void setSysUserOperId(String sysUserOperId) {
		this.sysUserOperId = sysUserOperId;
	}

	public void setSel_sysUserOperNames(String[] sel_sysUserOperNames) {
		this.sel_sysUserOperNames = sel_sysUserOperNames;
	}

	public String[] getSel_sysUserApprovalId() {
		return sel_sysUserApprovalId;
	}

	public void setSel_sysUserApprovalId(String[] sel_sysUserApprovalId) {
		this.sel_sysUserApprovalId = sel_sysUserApprovalId;
	}

	public String[] getSel_sysUserDevId() {
		return sel_sysUserDevId;
	}

	public void setSel_sysUserDevId(String[] sel_sysUserDevId) {
		this.sel_sysUserDevId = sel_sysUserDevId;
	}

	public String[] getSel_sysUserBizId() {
		return sel_sysUserBizId;
	}

	public void setSel_sysUserBizId(String[] sel_sysUserBizId) {
		this.sel_sysUserBizId = sel_sysUserBizId;
	}

	public String[] getSel_sysUserBpId() {
		return sel_sysUserBpId;
	}

	public void setSel_sysUserBpId(String[] sel_sysUserBpId) {
		this.sel_sysUserBpId = sel_sysUserBpId;
	}

	public String[] getSel_sysUserBpId1() {
		return sel_sysUserBpId1;
	}

	public void setSel_sysUserBpId1(String[] sel_sysUserBpId1) {
		this.sel_sysUserBpId1 = sel_sysUserBpId1;
	}

	public String[] getSel_sysUserBpId2() {
		return sel_sysUserBpId2;
	}

	public void setSel_sysUserBpId2(String[] sel_sysUserBpId2) {
		this.sel_sysUserBpId2 = sel_sysUserBpId2;
	}

	public String[] getSel_sysUserBpId3() {
		return sel_sysUserBpId3;
	}

	public void setSel_sysUserBpId3(String[] sel_sysUserBpId3) {
		this.sel_sysUserBpId3 = sel_sysUserBpId3;
	}

	public String[] getSel_sysUserBpId4() {
		return sel_sysUserBpId4;
	}

	public void setSel_sysUserBpId4(String[] sel_sysUserBpId4) {
		this.sel_sysUserBpId4 = sel_sysUserBpId4;
	}

	public String[] getSel_sysUserOperId() {
		return sel_sysUserOperId;
	}

	public void setSel_sysUserOperId(String[] sel_sysUserOperId) {
		this.sel_sysUserOperId = sel_sysUserOperId;
	}

	public void setSel_sysUserVerifyNames(String[] sel_sysUserVerifyNames) {
		this.sel_sysUserVerifyNames = sel_sysUserVerifyNames;
	}

	public String[] getSel_sysUserApprovalNames() {
		return sel_sysUserApprovalNames;
	}

	public void setSel_sysUserApprovalNames(String[] sel_sysUserApprovalNames) {
		this.sel_sysUserApprovalNames = sel_sysUserApprovalNames;
	}

	public String[] getSel_sysUserDevNames() {
		return sel_sysUserDevNames;
	}

	public void setSel_sysUserDevNames(String[] sel_sysUserDevNames) {
		this.sel_sysUserDevNames = sel_sysUserDevNames;
	}

	public String[] getSel_sysUserBizNames() {
		return sel_sysUserBizNames;
	}

	public void setSel_sysUserBizNames(String[] sel_sysUserBizNames) {
		this.sel_sysUserBizNames = sel_sysUserBizNames;
	}

	public String[] getSel_sysUserBpNames() {
		return sel_sysUserBpNames;
	}

	public void setSel_sysUserBpNames(String[] sel_sysUserBpNames) {
		this.sel_sysUserBpNames = sel_sysUserBpNames;
	}

	public String[] getSel_sysUserBpNames1() {
		return sel_sysUserBpNames1;
	}

	public void setSel_sysUserBpNames1(String[] sel_sysUserBpNames1) {
		this.sel_sysUserBpNames1 = sel_sysUserBpNames1;
	}

	public String[] getSel_sysUserBpNames2() {
		return sel_sysUserBpNames2;
	}

	public void setSel_sysUserBpNames2(String[] sel_sysUserBpNames2) {
		this.sel_sysUserBpNames2 = sel_sysUserBpNames2;
	}

	public String[] getSel_sysUserBpNames3() {
		return sel_sysUserBpNames3;
	}

	public void setSel_sysUserBpNames3(String[] sel_sysUserBpNames3) {
		this.sel_sysUserBpNames3 = sel_sysUserBpNames3;
	}

	public String[] getSel_sysUserBpNames4() {
		return sel_sysUserBpNames4;
	}

	public void setSel_sysUserBpNames4(String[] sel_sysUserBpNames4) {
		this.sel_sysUserBpNames4 = sel_sysUserBpNames4;
	}

	public String[] getSel_sysUserOperNames() {
		return sel_sysUserOperNames;
	}

	public String[] getSel_sysUserVerifyNames() {
		return sel_sysUserVerifyNames;
	}

	public List<SystemUserModel> getSystemUserList() {
		return systemUserList;
	}

	public void setSystemUserList(List<SystemUserModel> systemUserList) {
		this.systemUserList = systemUserList;
	}

	public HashMap<SysModel, List<EquipmentUserModel>> getEquipmentUserMap() {
		return equipmentUserMap;
	}

	public void setEquipmentUserMap(HashMap<SysModel, List<EquipmentUserModel>> equipmentUserMap) {
		this.equipmentUserMap = equipmentUserMap;
	}

	public String getDowntime() {
		return downtime;
	}

	public void setDowntime(String downtime) {
		this.downtime = downtime;
	}

	public String getImpact_systems() {
		return impact_systems;
	}

	public void setImpact_systems(String impact_systems) {
		this.impact_systems = impact_systems;
	}

	public String getSystem_user_id() {
		return system_user_id;
	}

	public void setSystem_user_id(String system_user_id) {
		this.system_user_id = system_user_id;
	}

	public String getWarehousing_day() {
		return warehousing_day;
	}

	public void setWarehousing_day(String warehousing_day) {
		this.warehousing_day = warehousing_day;
	}

	public String getService_area() {
		return service_area;
	}

	public void setService_area(String service_area) {
		this.service_area = service_area;
	}

	public List<PkgModel> getPkgHistoryList() {
		return pkgHistoryList;
	}

	public void setPkgHistoryList(List<PkgModel> pkgHistoryList) {
		this.pkgHistoryList = pkgHistoryList;
	}

	public List<EquipmentHistoryModel> getPkgEquipmentHistoryList() {
		return pkgEquipmentHistoryList;
	}

	public void setPkgEquipmentHistoryList(List<EquipmentHistoryModel> pkgEquipmentHistoryList) {
		this.pkgEquipmentHistoryList = pkgEquipmentHistoryList;
	}

	public String getCheck_auth() {
		return check_auth;
	}

	public void setCheck_auth(String check_auth) {
		this.check_auth = check_auth;
	}

	public String[] getSystem_user_charge_gubun() {
		return system_user_charge_gubun;
	}

	public void setSystem_user_charge_gubun(String[] system_user_charge_gubun) {
		this.system_user_charge_gubun = system_user_charge_gubun;
	}

	public String getEquipment_user_auth() {
		return equipment_user_auth;
	}

	public void setEquipment_user_auth(String equipment_user_auth) {
		this.equipment_user_auth = equipment_user_auth;
	}

	/** 검색조건 */
	private String searchCondition;
	/** 검색Keyword */
	private String searchKeyword = "";

	private String group_id = "";

	private List<CodeModel> searchConditionsList;
	private final String[][] searchConditions = new String[][] { { "0", "이름" }, { "1", "부서" } };
	
	private String searchGubun;
	private String searchInput = "";
	
	private List<CodeModel> searchGubunsList;
	private final String[][] searchGubuns = new String[][] { { "0", "명칭 [시스템/장비]" }, { "1", "담당자명" }};
	
	private String companyCondition = "sys";
	
	private List<CodeModel> companyConditionsList;
	private final String[][] companyConditions = new String[][] { { "sys", "시스템" }, {"eq", "장비(운용)"}};
	
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
	
	private String system_user_name ="";
	private String user_sosok_name ="";
	private String group_full_name = "";
	
	public String getGroup_full_name() {
		return group_full_name;
	}

	public void setGroup_full_name(String group_full_name) {
		this.group_full_name = group_full_name;
	}

	public String getSystem_user_name() {
		return system_user_name;
	}

	public void setSystem_user_name(String system_user_name) {
		this.system_user_name = system_user_name;
	}

	public String getUser_sosok_name() {
		return user_sosok_name;
	}

	public void setUser_sosok_name(String user_sosok_name) {
		this.user_sosok_name = user_sosok_name;
	}

	public String getSearchGubun() {
		return searchGubun;
	}

	public void setSearchGubun(String searchGubun) {
		this.searchGubun = searchGubun;
	}

	public String getSearchInput() {
		return searchInput;
	}

	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}

	public List<CodeModel> getSearchGubunsList() {
		return searchGubunsList;
	}

	public void setSearchGubunsList() {
		this.searchGubunsList = CodeUtil.convertCodeModel(searchGubuns);
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

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public SYSTEM_USER_CHARGE_GUBUN getCharge_gubun() {
		return charge_gubun;
	}

	public void setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN charge_gubun) {
		this.charge_gubun = charge_gubun;
	}

	public void setCharge_gubun(String charge_gubun) {
		this.charge_gubun = SYSTEM_USER_CHARGE_GUBUN.valueOf(charge_gubun);
	}

	// 검색조건 - 담당별
	private String userCondition = "0";
	
	// 검색조건 -  전체
	private String pkgVerCondition = "1";
	
	public String getPkgVerCondition() {
		return pkgVerCondition;
	}

	public void setPkgVerCondition(String pkgVerDiaryCondition) {
		this.pkgVerCondition = pkgVerDiaryCondition;
	}

	public String getUserCondition() {
		return userCondition;
	}

	public void setUserCondition(String userDiaryCondition) {
		this.userCondition = userDiaryCondition;
	}

	private List<CodeModel> userConditionList;

	public List<CodeModel> getUserConditionList() {
		this.userConditionList = CodeUtil.convertCodeModel(new String[][] { { "0", getSession_user_name() }, { "1", "전체" } });
		return this.userConditionList;
	}

	private List<CodeModel> pkgVerConditionList;
	
	public List<CodeModel> getPkgVerConditionList() {
		this.pkgVerConditionList = CodeUtil.convertCodeModel(new String[][] {{ "1", "전체" }});
		return this.pkgVerConditionList;
	}
	
	public String getSupply() {
		return supply;
	}

	public void setSupply(String supply) {
		this.supply = supply;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getOneLine_explain() {
		return oneLine_explain;
	}

	public void setOneLine_explain(String oneLine_explain) {
		this.oneLine_explain = oneLine_explain;
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

	public String getPe_type() {
		return pe_type;
	}

	public void setPe_type(String pe_type) {
		this.pe_type = pe_type;
	}

	public String getSystem_popup_gubun() {
		return system_popup_gubun;
	}

	public void setSystem_popup_gubun(String system_popup_gubun) {
		this.system_popup_gubun = system_popup_gubun;
	}

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public String getDev_system_user_id() {
		return dev_system_user_id;
	}

	public void setDev_system_user_id(String dev_system_user_id) {
		this.dev_system_user_id = dev_system_user_id;
	}

	public String getDevsysUserVerifyId() {
		return devsysUserVerifyId;
	}

	public void setDevsysUserVerifyId(String devsysUserVerifyId) {
		this.devsysUserVerifyId = devsysUserVerifyId;
	}

	public String getDevsysUserApprovalId() {
		return devsysUserApprovalId;
	}

	public void setDevsysUserApprovalId(String devsysUserApprovalId) {
		this.devsysUserApprovalId = devsysUserApprovalId;
	}

	public String[] getSel_devsysUserVerifyId() {
		return sel_devsysUserVerifyId;
	}

	public void setSel_devsysUserVerifyId(String[] sel_devsysUserVerifyId) {
		this.sel_devsysUserVerifyId = sel_devsysUserVerifyId;
	}

	public String[] getSel_devsysUserApprovalId() {
		return sel_devsysUserApprovalId;
	}

	public void setSel_devsysUserApprovalId(String[] sel_devsysUserApprovalId) {
		this.sel_devsysUserApprovalId = sel_devsysUserApprovalId;
	}

	public String[] getSel_devsysUserVerifyNames() {
		return sel_devsysUserVerifyNames;
	}

	public void setSel_devsysUserVerifyNames(String[] sel_devsysUserVerifyNames) {
		this.sel_devsysUserVerifyNames = sel_devsysUserVerifyNames;
	}

	public String[] getSel_devsysUserApprovalNames() {
		return sel_devsysUserApprovalNames;
	}

	public void setSel_devsysUserApprovalNames(String[] sel_devsysUserApprovalNames) {
		this.sel_devsysUserApprovalNames = sel_devsysUserApprovalNames;
	}

	public String getLocsysUserVerifyId() {
		return locsysUserVerifyId;
	}

	public void setLocsysUserVerifyId(String locsysUserVerifyId) {
		this.locsysUserVerifyId = locsysUserVerifyId;
	}

	public String getLocsysUserApprovalId() {
		return locsysUserApprovalId;
	}

	public void setLocsysUserApprovalId(String locsysUserApprovalId) {
		this.locsysUserApprovalId = locsysUserApprovalId;
	}

	public String[] getSel_locsysUserVerifyId() {
		return sel_locsysUserVerifyId;
	}

	public void setSel_locsysUserVerifyId(String[] sel_locsysUserVerifyId) {
		this.sel_locsysUserVerifyId = sel_locsysUserVerifyId;
	}

	public String[] getSel_locsysUserApprovalId() {
		return sel_locsysUserApprovalId;
	}

	public void setSel_locsysUserApprovalId(String[] sel_locsysUserApprovalId) {
		this.sel_locsysUserApprovalId = sel_locsysUserApprovalId;
	}

	public String[] getSel_locsysUserVerifyNames() {
		return sel_locsysUserVerifyNames;
	}

	public void setSel_locsysUserVerifyNames(String[] sel_locsysUserVerifyNames) {
		this.sel_locsysUserVerifyNames = sel_locsysUserVerifyNames;
	}

	public String[] getSel_locsysUserApprovalNames() {
		return sel_locsysUserApprovalNames;
	}

	public void setSel_locsysUserApprovalNames(String[] sel_locsysUserApprovalNames) {
		this.sel_locsysUserApprovalNames = sel_locsysUserApprovalNames;
	}

	public String[] getSel_sysUserMoId() {
		return sel_sysUserMoId;
	}

	public void setSel_sysUserMoId(String[] sel_sysUserMoId) {
		this.sel_sysUserMoId = sel_sysUserMoId;
	}

	public String[] getSel_sysUserMoNames() {
		return sel_sysUserMoNames;
	}

	public void setSel_sysUserMoNames(String[] sel_sysUserMoNames) {
		this.sel_sysUserMoNames = sel_sysUserMoNames;
	}

	public String[] getSel_sysUserVolId() {
		return sel_sysUserVolId;
	}

	public void setSel_sysUserVolId(String[] sel_sysUserVolId) {
		this.sel_sysUserVolId = sel_sysUserVolId;
	}

	public String[] getSel_sysUserSecId() {
		return sel_sysUserSecId;
	}

	public void setSel_sysUserSecId(String[] sel_sysUserSecId) {
		this.sel_sysUserSecId = sel_sysUserSecId;
	}

	public String[] getSel_sysUserChaId() {
		return sel_sysUserChaId;
	}

	public void setSel_sysUserChaId(String[] sel_sysUserChaId) {
		this.sel_sysUserChaId = sel_sysUserChaId;
	}

	public String[] getSel_sysUserNonId() {
		return sel_sysUserNonId;
	}

	public void setSel_sysUserNonId(String[] sel_sysUserNonId) {
		this.sel_sysUserNonId = sel_sysUserNonId;
	}

	public String[] getSel_sysUserVolNames() {
		return sel_sysUserVolNames;
	}

	public void setSel_sysUserVolNames(String[] sel_sysUserVolNames) {
		this.sel_sysUserVolNames = sel_sysUserVolNames;
	}

	public String[] getSel_sysUserSecNames() {
		return sel_sysUserSecNames;
	}

	public void setSel_sysUserSecNames(String[] sel_sysUserSecNames) {
		this.sel_sysUserSecNames = sel_sysUserSecNames;
	}

	public String[] getSel_sysUserChaNames() {
		return sel_sysUserChaNames;
	}

	public void setSel_sysUserChaNames(String[] sel_sysUserChaNames) {
		this.sel_sysUserChaNames = sel_sysUserChaNames;
	}

	public String[] getSel_sysUserNonNames() {
		return sel_sysUserNonNames;
	}

	public void setSel_sysUserNonNames(String[] sel_sysUserNonNames) {
		this.sel_sysUserNonNames = sel_sysUserNonNames;
	}

	public String getEdu_data() {
		return edu_data;
	}

	public void setEdu_data(String edu_data) {
		this.edu_data = edu_data;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getPer_capa() {
		return per_capa;
	}

	public void setPer_capa(String per_capa) {
		this.per_capa = per_capa;
	}

	public String getRm_plan() {
		return rm_plan;
	}

	public void setRm_plan(String rm_plan) {
		this.rm_plan = rm_plan;
	}

	public String getBp_system_user_id() {
		return bp_system_user_id;
	}

	public void setBp_system_user_id(String bp_system_user_id) {
		this.bp_system_user_id = bp_system_user_id;
	}

	public String getSales_user_info() {
		return sales_user_info;
	}

	public void setSales_user_info(String sales_user_info) {
		this.sales_user_info = sales_user_info;
	}

	public String getEq_history_yn() {
		return eq_history_yn;
	}

	public void setEq_history_yn(String eq_history_yn) {
		this.eq_history_yn = eq_history_yn;
	}

	public String getEq_names() {
		return eq_names;
	}

	public void setEq_names(String eq_names) {
		this.eq_names = eq_names;
	}

	public String getCha_yn() {
		return cha_yn;
	}

	public void setCha_yn(String cha_yn) {
		this.cha_yn = cha_yn;
	}

	public String getSel_id() {
		return sel_id;
	}

	public void setSel_id(String sel_id) {
		this.sel_id = sel_id;
	}

	public String[] getSel_ids() {
		return sel_ids;
	}

	public void setSel_ids(String[] sel_ids) {
		this.sel_ids = sel_ids;
	}

	public String[] getSel_names() {
		return sel_names;
	}

	public void setSel_names(String[] sel_names) {
		this.sel_names = sel_names;
	}

	public String getSelect_system_user_id() {
		return select_system_user_id;
	}

	public void setSelect_system_user_id(String select_system_user_id) {
		this.select_system_user_id = select_system_user_id;
	}

	public String getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(String deleteList) {
		this.deleteList = deleteList;
	}

	public String[] getSel_sysUserVolApprovalId() {
		return sel_sysUserVolApprovalId;
	}

	public void setSel_sysUserVolApprovalId(String[] sel_sysUserVolApprovalId) {
		this.sel_sysUserVolApprovalId = sel_sysUserVolApprovalId;
	}

	public String[] getSel_sysUserChaApprovalId() {
		return sel_sysUserChaApprovalId;
	}

	public void setSel_sysUserChaApprovalId(String[] sel_sysUserChaApprovalId) {
		this.sel_sysUserChaApprovalId = sel_sysUserChaApprovalId;
	}

	public String[] getSel_sysUserVolApprovalNames() {
		return sel_sysUserVolApprovalNames;
	}

	public void setSel_sysUserVolApprovalNames(String[] sel_sysUserVolApprovalNames) {
		this.sel_sysUserVolApprovalNames = sel_sysUserVolApprovalNames;
	}

	public String[] getSel_sysUserChaApprovalNames() {
		return sel_sysUserChaApprovalNames;
	}

	public void setSel_sysUserChaApprovalNames(String[] sel_sysUserChaApprovalNames) {
		this.sel_sysUserChaApprovalNames = sel_sysUserChaApprovalNames;
	}
	
}
