package com.pkms.board.notice.model;

import java.util.List;

import com.pkms.common.attachfile.model.AttachFileMasterKey;
import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

public class NoticeModel extends AbstractModel {
	
	public NoticeModel() {
		setSearchConditionsList();
	}
	
	private static final long serialVersionUID = 1L;
	
	private String reg_name;
	private String use_yn;
	private String update_user;
	private String update_name;
	private String update_date;
	private String title;
	private String reg_user;
	private String reg_date;
	private String notice_seq;
	private boolean main_pop_yn = false;
	private String main_date_start;
	private String main_date_end;
	private String content;
	/** 검색조건*/
	private String searchCondition;
	private String searchKeyword = "";
	private List<CodeModel> searchConditionsList;
	private final String[][] searchConditions = new String[][] {{"0", "제목"}, {"1", "내용"}, {"2", "이름"}};
	
	@AttachFileMasterKey
	private String master_file_id;
	private AttachFileModel file1;
	private AttachFileModel file2;
	private AttachFileModel file3;
	private AttachFileModel file4;
	private AttachFileModel file5;
	
	private String deleteList;
	
	public String getUpdate_name() {
		return update_name;
	}

	public void setUpdate_name(String update_name) {
		this.update_name = update_name;
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
	public String getReg_name() {
		return reg_name;
	}
	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
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
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getNotice_seq() {
		return notice_seq;
	}
	public void setNotice_seq(String notice_seq) {
		this.notice_seq = notice_seq;
	}
	public String getMaster_file_id() {
		return master_file_id;
	}
	public void setMaster_file_id(String master_file_id) {
		this.master_file_id = master_file_id;
	}
	
	public boolean isMain_pop_yn() {
		return main_pop_yn;
	}

	public void setMain_pop_yn(boolean main_pop_yn) {
		this.main_pop_yn = main_pop_yn;
	}

	public String getMain_date_start() {
		return main_date_start;
	}
	public void setMain_date_start(String main_date_start) {
		this.main_date_start = main_date_start;
	}
	public String getMain_date_end() {
		return main_date_end;
	}
	public void setMain_date_end(String main_date_end) {
		this.main_date_end = main_date_end;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(String deleteList) {
		this.deleteList = deleteList;
	}
	
}
