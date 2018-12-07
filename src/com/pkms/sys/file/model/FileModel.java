package com.pkms.sys.file.model;

import com.pkms.common.model.AbstractModel;

public class FileModel extends AbstractModel {
	
	private static final long serialVersionUID = 1L;
	
	private String group1Condition;
	private String group2Condition;
	private String group3Condition;
	private String systemCondition;
	private String gubunCondition;
	
	// group1
	private String group1_seq;
	private String group1_name;
	
	// group2
	private String group2_seq;
	private String group2_name;
	// group3
	private String group3_seq;
	private String group3_name;
	
	// system
	private String system_seq;
	private String[] system_seqs;
	private String system_name;
	
	// tree	
	private String tree_id;
	private String tree_name;
	
	// attach_file_info
	private String master_file_id;
	private String attach_file_id;
	private String file_extension;
	private String file_size;
	private String file_path;
	private String file_org_name;
	private String file_name;
	private String parent_tree_id;
	private String reg_date;
	private String reg_user;
	
	private boolean no_file;
	
	private String charge_gubun;
	private String charge_gubun_name;
	private String[] charge_gubuns;
	
	private String title;
	private String content;
	
	//메일보내기
	private String user_email;
	private String user_name;
	private String user_sosok;
	
	//대표 담당자
	private String system_user_id;
	private String system_user_name;
	
	//검토완료
	private String confirm_yn;
	private String confirm_user;
	private String confirm_date;
	
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
	public String getGroup3Condition() {
		return group3Condition;
	}
	public void setGroup3Condition(String group3Condition) {
		this.group3Condition = group3Condition;
	}
	public String getSystemCondition() {
		return systemCondition;
	}
	public void setSystemCondition(String systemCondition) {
		this.systemCondition = systemCondition;
	}
	public String getGubunCondition() {
		return gubunCondition;
	}
	public void setGubunCondition(String gubunCondition) {
		this.gubunCondition = gubunCondition;
	}
	public String getGroup1_seq() {
		return group1_seq;
	}
	public void setGroup1_seq(String group1_seq) {
		this.group1_seq = group1_seq;
	}
	public String getGroup1_name() {
		return group1_name;
	}
	public void setGroup1_name(String group1_name) {
		this.group1_name = group1_name;
	}
	public String getGroup2_seq() {
		return group2_seq;
	}
	public void setGroup2_seq(String group2_seq) {
		this.group2_seq = group2_seq;
	}
	public String getGroup2_name() {
		return group2_name;
	}
	public void setGroup2_name(String group2_name) {
		this.group2_name = group2_name;
	}
	public String getGroup3_seq() {
		return group3_seq;
	}
	public void setGroup3_seq(String group3_seq) {
		this.group3_seq = group3_seq;
	}
	public String getGroup3_name() {
		return group3_name;
	}
	public void setGroup3_name(String group3_name) {
		this.group3_name = group3_name;
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
	public String getTree_id() {
		return tree_id;
	}
	public void setTree_id(String tree_id) {
		this.tree_id = tree_id;
	}
	public String getTree_name() {
		return tree_name;
	}
	public void setTree_name(String tree_name) {
		this.tree_name = tree_name;
	}
	public String getMaster_file_id() {
		return master_file_id;
	}
	public void setMaster_file_id(String master_file_id) {
		this.master_file_id = master_file_id;
	}
	public String getAttach_file_id() {
		return attach_file_id;
	}
	public void setAttach_file_id(String attach_file_id) {
		this.attach_file_id = attach_file_id;
	}
	public String getFile_extension() {
		return file_extension;
	}
	public void setFile_extension(String file_extension) {
		this.file_extension = file_extension;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getFile_org_name() {
		return file_org_name;
	}
	public void setFile_org_name(String file_org_name) {
		this.file_org_name = file_org_name;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getParent_tree_id() {
		return parent_tree_id;
	}
	public void setParent_tree_id(String parent_tree_id) {
		this.parent_tree_id = parent_tree_id;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getReg_user() {
		return reg_user;
	}
	public void setReg_user(String reg_user) {
		this.reg_user = reg_user;
	}
	public boolean getNo_file() {
		return no_file;
	}
	public void setNo_file(boolean no_file) {
		this.no_file = no_file;
	}
	public String[] getSystem_seqs() {
		return system_seqs;
	}
	public void setSystem_seqs(String[] system_seqs) {
		this.system_seqs = system_seqs;
	}
	public String getCharge_gubun() {
		return charge_gubun;
	}
	public void setCharge_gubun(String charge_gubun) {
		this.charge_gubun = charge_gubun;
	}
	public String[] getCharge_gubuns() {
		return charge_gubuns;
	}
	public void setCharge_gubuns(String[] charge_gubuns) {
		this.charge_gubuns = charge_gubuns;
	}
	public String getCharge_gubun_name() {
		return charge_gubun_name;
	}
	public void setCharge_gubun_name(String charge_gubun_name) {
		this.charge_gubun_name = charge_gubun_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_sosok() {
		return user_sosok;
	}
	public void setUser_sosok(String user_sosok) {
		this.user_sosok = user_sosok;
	}
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
	public String getConfirm_yn() {
		return confirm_yn;
	}
	public void setConfirm_yn(String confirm_yn) {
		this.confirm_yn = confirm_yn;
	}
	public String getConfirm_user() {
		return confirm_user;
	}
	public void setConfirm_user(String confirm_user) {
		this.confirm_user = confirm_user;
	}
	public String getConfirm_date() {
		return confirm_date;
	}
	public void setConfirm_date(String confirm_date) {
		this.confirm_date = confirm_date;
	}
}
