package com.pkms.common.mail.service;

import com.pkms.common.mail.model.MailModel;

public interface MailServiceIf {

	public void create(MailModel mailModel) ;
	
	public void create4Multi(MailModel mailModel);
}
