package com.pkms.board.report.model;

import com.pkms.common.attachfile.model.AttachFileMasterKey;
import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.pkms.sys.system.model.SystemUserModel;
import com.wings.model.CodeModel;

import java.util.ArrayList;
import java.util.List;

public class SolutionReportModel extends AbstractModel{
	private static final long serialVersionUID = 1L;
	
	public SolutionReportModel() {
		setContent_gubunList();
		setSearch_content_gubunList();
	}
	
	private String solution_report_seq;
	private String title;
	private String content;
	
	@AttachFileMasterKey
	private String master_file_id;
	private AttachFileModel attachFile1;
	private AttachFileModel attachFile2;
	private AttachFileModel attachFile3;
	private AttachFileModel attachFile4;
	private AttachFileModel attachFile5;
	private AttachFileModel attachFile6;
	private AttachFileModel attachFile7;
	private AttachFileModel attachFile8;
	
	private String use_yn;
	private String reg_user;
	private String reg_name;
	private String reg_date;
	private String reg_sosok;
	private String update_user;
	private String update_name;
	private String update_date;
	
	private String system_seq;
	private String system_seq_bak;
	private String group1_seq;
	private String group2_seq;
	private String group3_seq;
	
	private String system_name;
	
	private String content_gubun;
	private List<CodeModel> content_gubunList;
	private final String [][] content_gubunConditions = new String[][] {{"이슈사항","이슈사항 : 운용팀 to Sol, Sol to 운용팀"},{"권고사항","권고사항 : Sol to 운용팀"},{"문의사항","문의사항 : 운용팀 to Sol"},{"기타","기타 : 운용팀 to Sol, Sol to 운용팀"}};

	private String result_yn = "N";
	private String noti_why;
	
	private String[] check_seqs = null;
	private String[] check_indepts = null;
	private String check_mails = "";
	
	private String solution_report_comment_seq;
	private String comment_cnt;
	
	//담당자
	private List<SolutionReportUserModel> solutionReportUserList = null;
	private List<SystemUserModel> systemUserModelList = null;
	private List<SolutionReportUserModel> solutionReportSosokList = null;
	private List<SolutionReportUserModel> solutionReportMailList = null;
	private String user_id;
	private int user_cnt=0;
	private String au_comment;
	
	//부서
	private int sosok_cnt=0;
	private String sosok;
	private String indept;
	private String sosok_comment;
	
	//검색조건
	private String search_system_seq;
	private String search_system_name;
	private String search_date_start;
	private String search_date_end;
	private String search_reg_name;
	private String search_reg_sosok;
	private String search_title;
	private String search_content_gubun;
	private List<CodeModel> search_content_gubunList;
	private final String [][] search_content_gubunConditions = new String[][] {{"이슈사항","이슈사항"},{"권고사항","권고사항"},{"문의사항","문의사항"},{"기타","기타"}};
	
	private String vu_yn;
	private String deleteList;
	
	public String getSolution_report_seq() {
		return solution_report_seq;
	}
	public void setSolution_report_seq(String solution_report_seq) {
		this.solution_report_seq = solution_report_seq;
	}
	public String getSolution_report_comment_seq() {
		return solution_report_comment_seq;
	}
	public void setSolution_report_comment_seq(String solution_report_comment_seq) {
		this.solution_report_comment_seq = solution_report_comment_seq;
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
	public String getMaster_file_id() {
		return master_file_id;
	}
	public void setMaster_file_id(String master_file_id) {
		this.master_file_id = master_file_id;
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
	public String getReg_name() {
		return reg_name;
	}
	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getReg_sosok() {
		return reg_sosok;
	}
	public void setReg_sosok(String reg_sosok) {
		this.reg_sosok = reg_sosok;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getUpdate_name() {
		return update_name;
	}
	public void setUpdate_name(String update_name) {
		this.update_name = update_name;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getSystem_seq() {
		return system_seq;
	}
	public void setSystem_seq(String system_seq) {
		this.system_seq = system_seq;
	}
	public String getSystem_seq_bak() {
		return system_seq_bak;
	}
	public void setSystem_seq_bak(String system_seq_bak) {
		this.system_seq_bak = system_seq_bak;
	}
	public String getSystem_name() {
		return system_name;
	}
	public void setSystem_name(String system_name) {
		this.system_name = system_name;
	}
	public String getContent_gubun() {
		return content_gubun;
	}
	public void setContent_gubun(String content_gubun) {
		this.content_gubun = content_gubun;
	}
	public List<CodeModel> getContent_gubunList(){
		return content_gubunList;
	}
	public void setContent_gubunList(){
		this.content_gubunList = CodeUtil.convertCodeModel(content_gubunConditions);
	}
	public String getSearch_content_gubun() {
		return search_content_gubun;
	}
	public void setSearch_content_gubun(String search_content_gubun) {
		this.search_content_gubun = search_content_gubun;
	}
	public List<CodeModel> getSearch_content_gubunList(){
		return search_content_gubunList;
	}
	public void setSearch_content_gubunList(){
		this.search_content_gubunList = CodeUtil.convertCodeModel(search_content_gubunConditions);
	}
	public String getResult_yn() {
		return result_yn;
	}
	public void setResult_yn(String result_yn) {
		this.result_yn = result_yn;
	}
	public String getNoti_why() {
		return noti_why;
	}
	public void setNoti_why(String noti_why) {
		this.noti_why = noti_why;
	}
	public List<SolutionReportUserModel> getSolutionReportUserList() {
		if (solutionReportUserList == null) {
			solutionReportUserList = new ArrayList<SolutionReportUserModel>();
		}
		return solutionReportUserList;
	}

	public void setSolutionReportUserList(List<SolutionReportUserModel> solutionReportUserList) {
		this.solutionReportUserList = solutionReportUserList;
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
	
	public List<SolutionReportUserModel> getSolutionReportSosokList() {
		if (solutionReportSosokList == null) {
			solutionReportSosokList = new ArrayList<SolutionReportUserModel>();
		}
		return solutionReportSosokList;
	}
	public void setSolutionReportSosokList(List<SolutionReportUserModel> solutionReportSosokList) {
		this.solutionReportSosokList = solutionReportSosokList;
	}
	
	public List<SolutionReportUserModel> getSolutionReportMailList() {
		if (solutionReportMailList == null) {
			solutionReportMailList = new ArrayList<SolutionReportUserModel>();
		}
		return solutionReportMailList;
	}
	public void setSolutionReportMailList(List<SolutionReportUserModel> solutionReportMailList) {
		this.solutionReportMailList = solutionReportMailList;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String[] getCheck_seqs() {
		return check_seqs;
	}
	public void setCheck_seqs(String[] check_seqs) {
		this.check_seqs = check_seqs;
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
	public String getSearch_system_seq() {
		return search_system_seq;
	}
	public void setSearch_system_seq(String search_system_seq) {
		this.search_system_seq = search_system_seq;
	}
	public String getSearch_system_name() {
		return search_system_name;
	}
	public void setSearch_system_name(String search_system_name) {
		this.search_system_name = search_system_name;
	}
	public String getSearch_date_start() {
		return search_date_start;
	}
	public void setSearch_date_start(String search_date_start) {
		this.search_date_start = search_date_start;
	}
	public String getSearch_date_end() {
		return search_date_end;
	}
	public void setSearch_date_end(String search_date_end) {
		this.search_date_end = search_date_end;
	}
	public String getSearch_reg_name() {
		return search_reg_name;
	}
	public void setSearch_reg_name(String search_reg_name) {
		this.search_reg_name = search_reg_name;
	}
	public String getSearch_reg_sosok() {
		return search_reg_sosok;
	}
	public void setSearch_reg_sosok(String search_reg_sosok) {
		this.search_reg_sosok = search_reg_sosok;
	}
	public String getComment_cnt() {
		return comment_cnt;
	}
	public void setComment_cnt(String comment_cnt) {
		this.comment_cnt = comment_cnt;
	}
	public int getUser_cnt() {
		return user_cnt;
	}
	public void setUser_cnt(int user_cnt) {
		this.user_cnt = user_cnt;
	}
	public String getAu_comment() {
		return au_comment;
	}
	public void setAu_comment(String au_comment) {
		this.au_comment = au_comment;
	}
	public int getSosok_cnt() {
		return sosok_cnt;
	}
	public void setSosok_cnt(int sosok_cnt) {
		this.sosok_cnt = sosok_cnt;
	}
	public String getSosok() {
		return sosok;
	}
	public void setSosok(String sosok) {
		this.sosok = sosok;
	}
	public String getIndept() {
		return indept;
	}
	public void setIndept(String indept) {
		this.indept = indept;
	}
	public String[] getCheck_indepts() {
		return check_indepts;
	}
	public void setCheck_indepts(String[] check_indepts) {
		this.check_indepts = check_indepts;
	}
	public String getVu_yn() {
		return vu_yn;
	}
	public void setVu_yn(String vu_yn) {
		this.vu_yn = vu_yn;
	}
	public String getCheck_mails() {
		return check_mails;
	}
	public void setCheck_mails(String check_mails) {
		this.check_mails = check_mails;
	}
	public String getSearch_title() {
		return search_title;
	}
	public void setSearch_title(String search_title) {
		this.search_title = search_title;
	}
	public String getSosok_comment() {
		return sosok_comment;
	}
	public void setSosok_comment(String sosok_comment) {
		this.sosok_comment = sosok_comment;
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
	public String getDeleteList() {
		return deleteList;
	}
	public void setDeleteList(String deleteList) {
		this.deleteList = deleteList;
	}
	
}
