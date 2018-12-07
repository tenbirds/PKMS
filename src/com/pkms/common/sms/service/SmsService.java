package com.pkms.common.sms.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pkms.common.sms.dao.SmsDao;
import com.pkms.common.sms.model.SmsModel;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : 
 * @Date : 2012. 
 * 
 */
@Service("SmsService")
public class SmsService implements SmsServiceIf {
	
	static Logger logger = Logger.getLogger(SmsService.class);
	
	@Resource(name = "SmsDao")
	private SmsDao smsDao;

	@Override
	public void create(SmsModel smsModel) {
		
		try {
			smsDao.create(smsModel);
//			System.out.println("SMS 발송 완료");
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public void update(SmsModel smsModel) {
		try {
			smsDao.update(smsModel);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public SmsModel read(SmsModel smsModel) throws Exception {
		return null;
	}

	@Override
	public List<SmsModel> readList(SmsModel smsModel) throws Exception {
		return smsDao.readList(smsModel);
	}

	
}
