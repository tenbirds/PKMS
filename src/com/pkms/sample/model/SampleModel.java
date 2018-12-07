package com.pkms.sample.model;

import java.util.List;

import com.pkms.common.attachfile.model.AttachFileMasterKey;
import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.model.AbstractModel;
import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

/**
 * 
 * 
 * 테스트를 위한 모델 입니다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 3. 12.
 * 
 */
public class SampleModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String id;
	private String user_name = "";
	private String reg_user = "";
	private String reg_date = "";
	private String update_user = "";
	private String update_date = "";

	private String description;

	private String use_yn = "Y";
	private List<CodeModel> useYnItems;

	private boolean recv_yn = false;

	private String passwd;

	@AttachFileMasterKey
	private String file_id;

	private AttachFileModel file1;
	private AttachFileModel file2;

	private String sex = "M";
	private List<CodeModel> sexList;
	private final String[][] sexConstants = new String[][] { { "M", "남자" }, { "W", "여자" } };

	public SampleModel() {
		// 코드 세팅
		setSearchConditionsList();
		setSexList();
	}

	/**
	 * 검색 관련 샘플
	 */
	/** 검색조건 */
	private String searchCondition;
	/** 검색Keyword */
	private String searchKeyword = "";

	private List<CodeModel> searchConditionsList;
	private final String[][] searchConditions = new String[][] { { "0", "아이디" }, { "1", "이름" } };

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

	/**
	 * 검색 관련 샘플
	 */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public boolean isRecv_yn() {
		return this.recv_yn;
	}

	public void setRecv_yn(boolean recv_yn) {
		this.recv_yn = recv_yn;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
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

	public List<CodeModel> getUseYnItems() {
		return useYnItems;
	}

	public void setUseYnItems(List<CodeModel> useYnItems) {
		this.useYnItems = useYnItems;
	}

	public List<CodeModel> getSexList() {
		return sexList;
	}

	public void setSexList() {
		this.sexList = CodeUtil.convertCodeModel(sexConstants);
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

}
