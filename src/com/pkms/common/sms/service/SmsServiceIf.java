package com.pkms.common.sms.service;

import java.util.List;

import com.pkms.common.sms.model.SmsModel;

public interface SmsServiceIf {

	public void create(SmsModel smsModel);
	
	public void update(SmsModel smsModel);
	
	public SmsModel read(SmsModel smsModel) throws Exception;
	
	public List<SmsModel> readList(SmsModel smsModel) throws Exception;

}
