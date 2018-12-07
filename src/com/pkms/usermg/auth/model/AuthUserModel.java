package com.pkms.usermg.auth.model;

import java.util.List;

import com.pkms.common.util.CodeUtil;
import com.wings.model.CodeModel;

/**
 * 
 * 
 * 권한 관리를 위한 모델 입니다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 24.
 * 
 */
public class AuthUserModel extends AuthModel {

	private static final long serialVersionUID = 1L;

	private String auth_user_id = "";
	private String auth_user_name = "";
	private String auth_user_group_id = "";
	private String auth_user_group_name = "";
	private boolean auth_role_admin = false;
	private boolean auth_role_manager = false;
	private boolean auth_role_operator = false;

	private String[] auth_role_admins = new String[] {};
	private String[] auth_role_managers = new String[] {};
	private String[] auth_role_operators = new String[] {};
	private String[] auth_update_users = new String[] {};

	public AuthUserModel() {
		// 코드 세팅
		setSearchConditionsList();
	}

	public String getAuth_user_id() {
		return auth_user_id;
	}

	public void setAuth_user_id(String auth_user_id) {
		this.auth_user_id = auth_user_id;
	}

	public String getAuth_user_name() {
		return auth_user_name;
	}

	public void setAuth_user_name(String auth_user_name) {
		this.auth_user_name = auth_user_name;
	}

	public String getAuth_user_group_id() {
		return auth_user_group_id;
	}

	public void setAuth_user_group_id(String auth_user_group_id) {
		this.auth_user_group_id = auth_user_group_id;
	}

	public String getAuth_user_group_name() {
		return auth_user_group_name;
	}

	public void setAuth_user_group_name(String auth_user_group_name) {
		this.auth_user_group_name = auth_user_group_name;
	}

	public boolean isAuth_role_admin() {
		return auth_role_admin;
	}

	public void setAuth_role_admin(boolean auth_role_admin) {
		this.auth_role_admin = auth_role_admin;
	}

	public boolean isAuth_role_manager() {
		return auth_role_manager;
	}

	public void setAuth_role_manager(boolean auth_role_manager) {
		this.auth_role_manager = auth_role_manager;
	}

	public boolean isAuth_role_operator() {
		return auth_role_operator;
	}

	public void setAuth_role_operator(boolean auth_role_operator) {
		this.auth_role_operator = auth_role_operator;
	}

	public String[] getAuth_role_admins() {
		return auth_role_admins;
	}

	public void setAuth_role_admins(String[] auth_role_admins) {
		this.auth_role_admins = auth_role_admins;
	}

	public String[] getAuth_role_managers() {
		return auth_role_managers;
	}

	public void setAuth_role_managers(String[] auth_role_managers) {
		this.auth_role_managers = auth_role_managers;
	}

	public String[] getAuth_role_operators() {
		return auth_role_operators;
	}

	public void setAuth_role_operators(String[] auth_role_operators) {
		this.auth_role_operators = auth_role_operators;
	}

	public String[] getAuth_update_users() {
		return auth_update_users;
	}

	public void setAuth_update_users(String[] auth_update_users) {
		this.auth_update_users = auth_update_users;
	}

	/** 검색조건 */
	private String searchCondition;
	/** 검색Keyword */
	private String searchKeyword = "";

	private List<CodeModel> searchConditionsList;
	private final String[][] searchConditions = new String[][] { { "0", "이름" }, { "1", "부서" } };

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

}
