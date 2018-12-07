package com.pkms.common.mail.model;

import com.pkms.common.model.AbstractModel;

public class MailModel extends AbstractModel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 받는 사람
	 */
	private String to;

	private String[] tos;
	
	private String[] tosInfo; //메일,이름,소속
	
	private String toInfo; ////메일,이름,소속 (단일)

	

	/**
	 * 보내는 사람
	 */
	private String from;
	
	
	/**
	 * 참조
	 */
	private String cc;
	
	/**
	 * 숨은참조
	 */
	private String bcc;
	
	/**
	 * 메일 제목
	 */
	private String msgSubj;
	
	/**
	 * 메일 내용
	 */
	private String msgText;
	
	/**
	 * 메시지 타입 text | html | multipart
	 */
	private String type;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	

	public String getMsgSubj() {
		return msgSubj;
	}

	public void setMsgSubj(String msgSubj) {
		this.msgSubj = msgSubj;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getTos() {
		return tos;
	}

	public void setTos(String[] tos) {
		this.tos = tos;
	}

	public String[] getTosInfo() {
		return tosInfo;
	}

	public void setTosInfo(String[] tosInfo) {
		this.tosInfo = tosInfo;
	}
	
	public String getToInfo() {
		return toInfo;
	}

	public void setToInfo(String toInfo) {
		this.toInfo = toInfo;
	}
}
