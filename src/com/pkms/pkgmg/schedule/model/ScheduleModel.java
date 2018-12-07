package com.pkms.pkgmg.schedule.model;

import java.util.List;

import com.pkms.common.util.CodeUtil;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.wings.model.CodeModel;

/**
 * PKG검증/일정 > 일정목록<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 05.
 * 
 */
public class ScheduleModel extends PkgModel {
	
	private static final long serialVersionUID = 1L;
	
	private final String EXCEL_FILE_NAME ="PKG 작업일정";
 

	public ScheduleModel(){
		super();
		setWorkDateConditionsList();
	}
	
	private List<CodeModel> workDateConditionsList;
	private final String[][] dateWorkConditions = new String[][] { { "0", "검증일자" }, { "1", "적용일자" } };

	public List<CodeModel> getWorkDateConditionsList() {
		return workDateConditionsList;
	}

	public void setWorkDateConditionsList() {
		this.workDateConditionsList = CodeUtil.convertCodeModel(dateWorkConditions);
	}

	public String getEXCEL_FILE_NAME() {
		return EXCEL_FILE_NAME;
	}

	
	/**pkg검증일정-미완료건 7일마다 메일/sms 발송 추가  */
    private String pkg_seq;
    private String system_seq;
    private String reg_date;
    private String title;
    private String status;
    private String charge_gubun;
    private String user_id;
    
    private String email;
    private String movetelno;
    private String empno;

    private String[] emails;
    private String[] movetelnos;
    private String[] titles;
    private String[] emailsInfo;
    
    private String use_yn;
    private String apply_date;
    private String hname;
    private String sosok;
 
    private String mail_gubun; // 1 = 초도승인후,확대일정수립후 미완료 / 2 = 긴급pkg 일반으로 미전환

    
	//---------------------test bed----------------
    private String asset_uid;
    private String serial_no; // 공통
    private String asset_status;
    private String idc_name; // 공통
    private String eq_gubun;
    private String eq_maker;
    private String eq_model;
    private String service_group;
    private String eq_group;
    private String host_name;
    private String ip_addr1; //공통
    private String ip_addr2; //공통
    
    //---------------------real(data info)--------------------
    private String team_name;
    private String network_group;
    private String system_group;
    private String system_name;
    private String manage_name;
    private String real_host_name;
    private String real_ip;
    
    //---------------------group_depth--------------------
    
    private String group1_code;
    private String group1_name;
    private String group2_code;
    private String group2_name;
    private String group3_code;
    private String group3_name;
    private String system_code;
    private String equipment_code;
    private String equipment_name;
    private String g1_seq;
    private String g1_name;
    private String g2_seq;
    private String g2_name;
    private String g3_seq;
    private String g3_name;
    private String sys_seq;
    private String sys_name;
    private String eq_seq;
    private String eq_name;
    
	public String getGroup1_code() {
		return group1_code;
	}

	public void setGroup1_code(String group1_code) {
		this.group1_code = group1_code;
	}

	public String getGroup1_name() {
		return group1_name;
	}

	public void setGroup1_name(String group1_name) {
		this.group1_name = group1_name;
	}

	public String getGroup2_code() {
		return group2_code;
	}

	public void setGroup2_code(String group2_code) {
		this.group2_code = group2_code;
	}

	public String getGroup2_name() {
		return group2_name;
	}

	public void setGroup2_name(String group2_name) {
		this.group2_name = group2_name;
	}

	public String getGroup3_code() {
		return group3_code;
	}

	public void setGroup3_code(String group3_code) {
		this.group3_code = group3_code;
	}

	public String getGroup3_name() {
		return group3_name;
	}

	public void setGroup3_name(String group3_name) {
		this.group3_name = group3_name;
	}

	public String getSystem_code() {
		return system_code;
	}

	public void setSystem_code(String system_code) {
		this.system_code = system_code;
	}

	public String getEquipment_code() {
		return equipment_code;
	}

	public void setEquipment_code(String equipment_code) {
		this.equipment_code = equipment_code;
	}

	public String getEquipment_name() {
		return equipment_name;
	}

	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}

	public String getG1_seq() {
		return g1_seq;
	}

	public void setG1_seq(String g1_seq) {
		this.g1_seq = g1_seq;
	}

	public String getG1_name() {
		return g1_name;
	}

	public void setG1_name(String g1_name) {
		this.g1_name = g1_name;
	}

	public String getG2_seq() {
		return g2_seq;
	}

	public void setG2_seq(String g2_seq) {
		this.g2_seq = g2_seq;
	}

	public String getG2_name() {
		return g2_name;
	}

	public void setG2_name(String g2_name) {
		this.g2_name = g2_name;
	}

	public String getG3_seq() {
		return g3_seq;
	}

	public void setG3_seq(String g3_seq) {
		this.g3_seq = g3_seq;
	}

	public String getG3_name() {
		return g3_name;
	}

	public void setG3_name(String g3_name) {
		this.g3_name = g3_name;
	}

	public String getSys_seq() {
		return sys_seq;
	}

	public void setSys_seq(String sys_seq) {
		this.sys_seq = sys_seq;
	}

	public String getSys_name() {
		return sys_name;
	}

	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}

	public String getEq_seq() {
		return eq_seq;
	}

	public void setEq_seq(String eq_seq) {
		this.eq_seq = eq_seq;
	}

	public String getEq_name() {
		return eq_name;
	}

	public void setEq_name(String eq_name) {
		this.eq_name = eq_name;
	}

	public String getPkg_seq() {
		return pkg_seq;
	}

	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}

	public String getSystem_seq() {
		return system_seq;
	}

	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCharge_gubun() {
		return charge_gubun;
	}

	public void setCharge_gubun(String charge_gubun) {
		this.charge_gubun = charge_gubun;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMovetelno() {
		return movetelno;
	}

	public void setMovetelno(String movetelno) {
		this.movetelno = movetelno;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String[] getEmails() {
		return emails;
	}

	public void setEmails(String[] emails) {
		this.emails = emails;
	}

	public String[] getMovetelnos() {
		return movetelnos;
	}

	public void setMovetelnos(String[] movetelnos) {
		this.movetelnos = movetelnos;
	}
 
	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public String getApply_date() {
		return apply_date;
	}

	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
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
	
	public String[] getEmailsInfo() {
		return emailsInfo;
	}

	public void setEmailsInfo(String[] emailsInfo) {
		this.emailsInfo = emailsInfo;
	}
	
	public String getMail_gubun() {
		return mail_gubun;
	}

	public void setMail_gubun(String mail_gubun) {
		this.mail_gubun = mail_gubun;
	}

	public String getAsset_uid() {
		return asset_uid;
	}

	public void setAsset_uid(String asset_uid) {
		this.asset_uid = asset_uid;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getAsset_status() {
		return asset_status;
	}

	public void setAsset_status(String asset_status) {
		this.asset_status = asset_status;
	}

	public String getIdc_name() {
		return idc_name;
	}

	public void setIdc_name(String idc_name) {
		this.idc_name = idc_name;
	}

	public String getEq_gubun() {
		return eq_gubun;
	}

	public void setEq_gubun(String eq_gubun) {
		this.eq_gubun = eq_gubun;
	}

	public String getEq_maker() {
		return eq_maker;
	}

	public void setEq_maker(String eq_maker) {
		this.eq_maker = eq_maker;
	}

	public String getEq_model() {
		return eq_model;
	}

	public void setEq_model(String eq_model) {
		this.eq_model = eq_model;
	}

	public String getService_group() {
		return service_group;
	}

	public void setService_group(String service_group) {
		this.service_group = service_group;
	}

	public String getEq_group() {
		return eq_group;
	}

	public void setEq_group(String eq_group) {
		this.eq_group = eq_group;
	}

	public String getHost_name() {
		return host_name;
	}

	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}

	public String getIp_addr1() {
		return ip_addr1;
	}

	public void setIp_addr1(String ip_addr1) {
		this.ip_addr1 = ip_addr1;
	}

	public String getIp_addr2() {
		return ip_addr2;
	}

	public void setIp_addr2(String ip_addr2) {
		this.ip_addr2 = ip_addr2;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getNetwork_group() {
		return network_group;
	}

	public void setNetwork_group(String network_group) {
		this.network_group = network_group;
	}

	public String getSystem_group() {
		return system_group;
	}

	public void setSystem_group(String system_group) {
		this.system_group = system_group;
	}

	public String getSystem_name() {
		return system_name;
	}

	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}

	public String getManage_name() {
		return manage_name;
	}

	public void setManage_name(String manage_name) {
		this.manage_name = manage_name;
	}

	public String getReal_host_name() {
		return real_host_name;
	}

	public void setReal_host_name(String real_host_name) {
		this.real_host_name = real_host_name;
	}

	public String getReal_ip() {
		return real_ip;
	}

	public void setReal_ip(String real_ip) {
		this.real_ip = real_ip;
	}
	
}
