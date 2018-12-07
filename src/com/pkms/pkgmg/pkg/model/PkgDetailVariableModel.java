package com.pkms.pkgmg.pkg.model;

import com.pkms.common.model.AbstractModel;

/**
 * Pkg 검증요청 보완적용내역<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 26.
 * 
 */
public class PkgDetailVariableModel extends AbstractModel {
	private static final long serialVersionUID = 1L;

	/**
	 * PKG Detail seq
	 */
	private String pkg_detail_seq ="";

	/**
	 * 순번
	 */
	private String ord = "";
	
	/**
	 * 내용
	 */
	private String content = "";

	/**
	 * 사용여부
	 */
	private String use_yn = "";

	/**
	 * 등록자
	 */
	private String reg_user = "";
	
	/**
	 * 등록일
	 */
	private String reg_date = "";
	
	/**
	 * 수정자
	 */
	private String update_user = "";
	
	/**
	 * 수정일
	 */
	private String update_date = "";
	private String new_pn_cr_gubun;
	private int detailMaxNO;
	private String pkg_seq;
	public String getNew_pn_cr_gubun() {
		return new_pn_cr_gubun;
	}
	public void setNew_pn_cr_gubun(String new_pn_cr_gubun) {
		this.new_pn_cr_gubun = new_pn_cr_gubun;
	}
	public int getDetailMaxNO() {
		return detailMaxNO;
	}
	public void setDetailMaxNO(int detailMaxNO) {
		this.detailMaxNO = detailMaxNO;
	}
	
	public String getPkg_seq() {
		return pkg_seq;
	}
	public void setPkg_seq(String pkg_seq) {
		this.pkg_seq = pkg_seq;
	}
	private int detailMaxSeq;
	
	public int getDetailMaxSeq() {
		return detailMaxSeq;
	}
	public void setDetailMaxSeq(int detailMaxSeq) {
		this.detailMaxSeq = detailMaxSeq;
	}
	public String getPkg_detail_seq() {
		return pkg_detail_seq;
	}
	public void setPkg_detail_seq(String pkg_detail_seq) {
		this.pkg_detail_seq = pkg_detail_seq;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
}
