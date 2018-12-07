package com.pkms.common.sms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.common.sms.model.SmsModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("SmsDao")
public class SmsDao extends IbatisAbstractDAO{

	public void create(SmsModel smsModel) {
		create("SmsDao.create", smsModel);
	}

	@SuppressWarnings("unchecked")
	public List<SmsModel> readList(SmsModel smsModel) {
		return (List<SmsModel>) readList("SmsDao.readList", smsModel);
	}

	public void update(SmsModel smsModel) {
		update("SmsDao.update", smsModel);
	}
	
	
	
}
