package com.pkms.common.menu.model;

/**
 * 
 * 
 * 메뉴 권한<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 15.
 * 
 */
public class MenuAuthRoleModel {

	/**
	 * 메뉴 SEQ
	 */
	private int menu_seq;

	/**
	 * 권한
	 */
	private String auth_role;

	public int getMenu_seq() {
		return menu_seq;
	}

	public void setMenu_seq(int menu_seq) {
		this.menu_seq = menu_seq;
	}

	public String getAuth_role() {
		return auth_role;
	}

	public void setAuth_role(String auth_role) {
		this.auth_role = auth_role;
	}

}