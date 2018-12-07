package com.pkms.main.model;

import com.pkms.pkgmg.pkg.model.PkgModel;

/**
 * 
 * 
 * 메인화면을 위한 모델 입니다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 22.
 * 
 */
public class MainModel extends PkgModel {

	private static final long serialVersionUID = 1L;

	private String pkg_seq;
	private String new_pn_cr_seq = "";
	private String notice_seq = "";

	public MainModel() {
		super();
	}

	public String getPkg_seq() {
		return pkg_seq;
	}

	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}

	public String getNew_pn_cr_seq() {
		return new_pn_cr_seq;
	}

	public void setNew_pn_cr_seq(String new_pn_cr_seq) {
		this.new_pn_cr_seq = new_pn_cr_seq;
	}

	public String getNotice_seq() {
		return notice_seq;
	}

	public void setNotice_seq(String notice_seq) {
		this.notice_seq = notice_seq;
	}

}
