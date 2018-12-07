package com.pkms.common.sms.model;

import com.pkms.common.model.AbstractModel;

public class SmsModel extends AbstractModel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	private String sms_no;
	private String log_no;
	private String srccallno;
	private String destcid;
	private String destcallno;
	private String portedflag;
	private String tid;
	private String cb;
	private String msg;
	private String status;
	
	public String getSms_no() {
		return sms_no;
	}
	public void setSms_no(String sms_no) {
		this.sms_no = sms_no;
	}
	public String getLog_no() {
		return log_no;
	}
	public void setLog_no(String log_no) {
		this.log_no = log_no;
	}
	public String getSrccallno() {
		return srccallno;
	}
	public void setSrccallno(String srccallno) {
		this.srccallno = srccallno;
	}
	public String getDestcid() {
		return destcid;
	}
	public void setDestcid(String destcid) {
		this.destcid = destcid;
	}
	public String getDestcallno() {
		return destcallno;
	}
	public void setDestcallno(String destcallno) {
		this.destcallno = destcallno;
	}
	public String getPortedflag() {
		return portedflag;
	}
	public void setPortedflag(String portedflag) {
		this.portedflag = portedflag;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getCb() {
		return cb;
	}
	public void setCb(String cb) {
		this.cb = cb;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
