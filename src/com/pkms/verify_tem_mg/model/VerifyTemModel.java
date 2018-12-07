package com.pkms.verify_tem_mg.model;

import com.pkms.common.model.AbstractModel;

public class VerifyTemModel extends AbstractModel{
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String item_seq;      
	private String quest_seq;     
	private String item_content;  
	private int item_num; 
	private String verify_seq;    
	private String verify_type;   
	private String verify_title;  
	private String reg_date;      
	private String reg_user;  
	private String reg_name;
	private String update_date;   
	private String update_user;   
	private String use_yn;
	private String quest_title;  
	private String quest_type;   
	private String result_seq;  
	private String result_memo; 
	private String pkg_seq;
	private int verify_ver = 1;
	private int new_ver;
	private String quest_seq_space;
	private int quest_cnt;
	private String verify_title_ori;
	private String verify_content;
	private String result_quest_input;
	private String result_item_input;
	private String result_quest_radio;
	private String result_item_radio;
	private String end;
	private String vol_yn;
	private String sec_yn;
	private String cha_yn;
	private String non_yn;
	private String system_name;
	private String system_seq;
	private String searchCondition = "";
	private String searchKeyword;
	private String verify_start_date = "";
	private String verify_end_date = "";
	private String seq_num;
	private String pkg_nm;
	private String user_id;
	private String user_nm;
	private String audit_rate;
	
	public String getItem_seq() {
		return item_seq;
	}
	public void setItem_seq(String item_seq) {
		this.item_seq = item_seq;
	}
	public String getQuest_seq() {
		return quest_seq;
	}
	public void setQuest_seq(String quest_seq) {
		this.quest_seq = quest_seq;
	}
	public String getItem_content() {
		return item_content;
	}
	public void setItem_content(String item_content) {
		this.item_content = item_content;
	}
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public String getVerify_seq() {
		return verify_seq;
	}
	public void setVerify_seq(String verify_seq) {
		this.verify_seq = verify_seq;
	}
	public String getVerify_type() {
		return verify_type;
	}
	public void setVerify_type(String verify_type) {
		this.verify_type = verify_type;
	}
	public String getVerify_title() {
		return verify_title;
	}
	public void setVerify_title(String verify_title) {
		this.verify_title = verify_title;
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
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getQuest_title() {
		return quest_title;
	}
	public void setQuest_title(String quest_title) {
		this.quest_title = quest_title;
	}
	public String getQuest_type() {
		return quest_type;
	}
	public void setQuest_type(String quest_type) {
		this.quest_type = quest_type;
	}
	public String getResult_seq() {
		return result_seq;
	}
	public void setResult_seq(String result_seq) {
		this.result_seq = result_seq;
	}
	public String getResult_memo() {
		return result_memo;
	}
	public void setResult_memo(String result_memo) {
		this.result_memo = result_memo;
	}
	public String getPkg_seq() {
		return pkg_seq;
	}
	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}
	public String getSearchCondition() {
		return searchCondition;
	}
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public int getVerify_ver() {
		return verify_ver;
	}
	public void setVerify_ver(int verify_ver) {
		this.verify_ver = verify_ver;
	}
	public String getQuest_seq_space() {
		return quest_seq_space;
	}
	public void setQuest_seq_space(String quest_seq_space) {
		this.quest_seq_space = quest_seq_space;
	}
	public int getQuest_cnt() {
		return quest_cnt;
	}
	public void setQuest_cnt(int quest_cnt) {
		this.quest_cnt = quest_cnt;
	}
	public String getReg_name() {
		return reg_name;
	}
	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
	}
	public String getVerify_title_ori() {
		return verify_title_ori;
	}
	public void setVerify_title_ori(String verify_title_ori) {
		this.verify_title_ori = verify_title_ori;
	}
	public int getNew_ver() {
		return new_ver;
	}
	public void setNew_ver(int new_ver) {
		this.new_ver = new_ver;
	}
	public String getVerify_content() {
		return verify_content;
	}
	public void setVerify_content(String verify_content) {
		this.verify_content = verify_content;
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
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
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
	public String getSystem_name() {
		return system_name;
	}
	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}
	public String getSystem_seq() {
		return system_seq;
	}
	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}
	public String getVerify_start_date() {
		return verify_start_date;
	}
	public void setVerify_start_date(String verify_start_date) {
		this.verify_start_date = verify_start_date;
	}
	public String getVerify_end_date() {
		return verify_end_date;
	}
	public void setVerify_end_date(String verify_end_date) {
		this.verify_end_date = verify_end_date;
	}
	public String getPkg_nm() {
		return pkg_nm;
	}
	public void setPkg_nm(String pkg_nm) {
		this.pkg_nm = pkg_nm;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getSeq_num() {
		return seq_num;
	}
	public void setSeq_num(String seq_num) {
		this.seq_num = seq_num;
	}
	public String getAudit_rate() {
		return audit_rate;
	}
	public void setAudit_rate(String audit_rate) {
		this.audit_rate = audit_rate;
	} 
	
}
