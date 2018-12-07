package com.pkms.org.model;

import com.pkms.common.model.AbstractModel;
import com.pkms.usermg.auth.model.AuthModel.AUTH_ROLE;

/**
 * SKT 조직<br>
 * 
 * @author : scott
 * @Date : 2012. 3. 29.
 * 
 */
public class OrgModel extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String indept = "";
	private String deptnm = "";
	private String sosok = "";
	private String highpartdept = "";
	private String levelcd = "";
	private String lowyn = "";
	private String treeScript = "";
	private ORG_OPTION option;

	private AUTH_ROLE auth_role;

	private boolean show = false;

	public String getIndept() {
		return indept;
	}

	public void setIndept(String indept) {
		this.indept = indept;
	}

	public String getDeptnm() {
		return deptnm;
	}

	public void setDeptnm(String deptnm) {
		this.deptnm = deptnm;
	}

	public String getSosok() {
		return sosok;
	}

	public void setSosok(String sosok) {
		this.sosok = sosok;
	}

	public String getHighpartdept() {
		return highpartdept;
	}

	public void setHighpartdept(String highpartdept) {
		this.highpartdept = highpartdept;
	}

	public String getLevelcd() {
		return levelcd;
	}

	public void setLevelcd(String levelcd) {
		this.levelcd = levelcd;
	}

	public String getLowyn() {
		return lowyn;
	}

	public void setLowyn(String lowyn) {
		this.lowyn = lowyn;
	}

	public String getTreeScript() {
		return treeScript;
	}

	public void setTreeScript(String treeScript) {
		this.treeScript = treeScript;
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

	public ORG_OPTION getOption() {
		return option;
	}

	public void setOption(ORG_OPTION option) {
		this.option = option;
	}

	public enum ORG_OPTION {
		GROUP_ONLY, AUTH_GROUP_ONLY, MANAGER_GROUP_ONLY, ALL;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

}
