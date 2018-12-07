package com.pkms.usermg.auth.model;

import com.pkms.common.model.AbstractModel;
import com.pkms.org.model.OrgModel.ORG_OPTION;

/**
 * 
 * 
 * 권한 관리를 위한 모델 입니다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 24.
 * 
 */
public class AuthModel extends AbstractModel {

	public enum AUTH_ROLE {

		ROLE_ADMIN("ROLE_ADMIN", "관리자"), ROLE_MANAGER("ROLE_MANAGER", "검증자"), ROLE_OPERATOR("ROLE_OPERATOR", "운용자"), ROLE_BP("ROLE_BP", "협력업체");

		private String code;
		private String description;

		AUTH_ROLE(final String code, final String description) {
			this.code = code;
			this.description = description;
		}

		@Override
		public String toString() {
			return this.code;
		}

		public String getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * 사용자 아이디
	 */
	private String user_id = "";

	/**
	 * 사용자 구분
	 */
	private String user_gubun = "";

	/**
	 * 권한그룹코드
	 */
	private AUTH_ROLE auth_role;

	private String treeScript = "";

	private ORG_OPTION option;

	private String selectedKeys = "";

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_gubun() {
		return user_gubun;
	}

	public void setUser_gubun(String user_gubun) {
		this.user_gubun = user_gubun;
	}

	public AUTH_ROLE getAuth_role() {
		return auth_role;
	}

	public void setAuth_role(AUTH_ROLE auth_role) {
		this.auth_role = auth_role;
	}

	public void setAuth_role(String auth_role) {
		this.auth_role = AUTH_ROLE.valueOf(auth_role);
	}

	public String getTreeScript() {
		return treeScript;
	}

	public void setTreeScript(String treeScript) {
		this.treeScript = treeScript;
	}

	public ORG_OPTION getOption() {
		return option;
	}

	public void setOption(ORG_OPTION option) {
		this.option = option;
	}

	public String getSelectedKeys() {
		return selectedKeys;
	}

	public void setSelectedKeys(String selectedKeys) {
		this.selectedKeys = selectedKeys;
	}

}
