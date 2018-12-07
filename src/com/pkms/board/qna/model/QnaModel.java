package com.pkms.board.qna.model;

import java.util.List;

import com.pkms.common.attachfile.model.AttachFileMasterKey;
import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

public class QnaModel extends AbstractModel{
	
	public QnaModel() {
		setSearchConditionsList();
	}
	
	private static final long serialVersionUID = 1L;
	
	private String qna_seq;
	private String title;
	private String content;
	private String reg_name;
	private String use_yn;
	private String reg_user;
	private String update_name;
	private String reg_date;
	private String update_user;
	private String update_date;
	private int cnt;
	private String qna_comment_seq;
	@AttachFileMasterKey
	private String master_file_id;
	/** 검색조건*/
	private String searchCondition;
	private String searchKeyword = "";
	private List<CodeModel> searchConditionsList;
	private final String[][] searchConditions = new String[][] {{"0", "제목"}, {"1", "내용"}, {"2", "이름"}};
	
	private String deleteList;
	
	public String getUpdate_name() {
		return update_name;
	}
	public void setUpdate_name(String update_name) {
		this.update_name = update_name;
	}
	public List<CodeModel> getSearchConditionsList() {
		return searchConditionsList;
	}
	public void setSearchConditionsList() {
		this.searchConditionsList = CodeUtil.convertCodeModel(searchConditions);
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
	public String getQna_comment_seq() {
		return qna_comment_seq;
	}
	public void setQna_comment_seq(String qna_comment_seq) {
		this.qna_comment_seq = qna_comment_seq;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getReg_name() {
		return reg_name;
	}
	public void setReg_name(String reg_name) {
		this.reg_name = reg_name;
	}
	public String getQna_seq() {
		return qna_seq;
	}
	public void setQna_seq(String qna_seq) {
		this.qna_seq = qna_seq;
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
	public String getDeleteList() {
		return deleteList;
	}
	public void setDeleteList(String deleteList) {
		this.deleteList = deleteList;
	}
}
