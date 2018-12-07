package com.pkms.common.scheduler.job.sms;

import java.util.List;

import javax.annotation.Resource;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.client.Stub;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import skt.soa.notification.webservice.SMSSender;
import skt.soa.notification.webservice.SMSSenderServiceLocator;

import com.pkms.common.mail.service.MailService;
import com.pkms.common.scheduler.job.AbstractJob;
import com.pkms.common.scheduler.job.sms.GIP.GIPSender;
import com.pkms.common.sms.model.SmsModel;
import com.pkms.common.sms.service.SmsServiceIf;
import com.wings.properties.service.PropertyServiceIf;

public class SmsJob extends AbstractJob {
	
	static Logger logger = Logger.getLogger(SmsJob.class);
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;

	@Override
	protected void executeJob(JobExecutionContext arg0) throws JobExecutionException {
		try {
			send();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void send() throws Exception {
		
		if(!SmsConstant.isRun()) {
			SmsConstant.setRun(true);

			try {
				SmsServiceIf smsService = (SmsServiceIf) super.getBean("SmsService");
				
				SmsModel smsModel = new SmsModel();
				List<SmsModel> list = smsService.readList(smsModel);
				
				SendThread sendThread = new SendThread(smsService, list);
				sendThread.run();
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				SmsConstant.setRun(false);
			}
		}
	}

	private class SendThread implements Runnable {
		GIPSender gIPSender = null;
		SmsServiceIf smsService = null;
		List<SmsModel> list = null;
		
		private SendThread(SmsServiceIf smsService, List<SmsModel> list) {
			this.smsService = smsService;
			this.list = list;
		}

		@Override
		public void run() {
			
			try {
				gIPSender = new GIPSender();
				
				for(SmsModel rSmsModel : list) {
					gIPSender.sendSMS(gIPSender.CID, 0, rSmsModel.getDestcid(), Integer.parseInt(rSmsModel.getDestcallno()), rSmsModel.getMsg(), rSmsModel.getTid());
					smsService.update(rSmsModel);
					Thread.sleep(20000L);
				}
				close();
			} catch(Exception e) {
				try {
					close();
					Thread.sleep(60000L);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		
//		@Override
//		public void run() {
//			
//			try {
//				//soa
//				SMSSenderServiceLocator locator = new SMSSenderServiceLocator();
//				
//				locator.setEndpointAddress("SMSSenderSoapPort", propertyService.getString("SOA.Sms.ip"));
//				
//				SMSSender service = locator.getSMSSenderSoapPort();
//				
//				Stub stub = (Stub)service;
//				stub._setProperty(Stub.USERNAME_PROPERTY, propertyService.getString("SOA.Username"));
//				stub._setProperty(Stub.PASSWORD_PROPERTY, propertyService.getString("SOA.Password"));
//				
//				//Input 파라미터
//				String CONSUMER_ID = null; //송신시스템 id(시스템 별 ID 부여)
//				String RPLY_PHON_NUM = null; //송신자 전화번호
//				String TITLE = null; //전송메시지
//				String PHONE = null; //수신자 전화번호
//				
//				String URL = ""; //수신 URL					
//				String START_DT_HMS = ""; //예약발송 시작시간	
//				String END_DT_HMS = "";	//예약발송 종료시간			
//				
//				//output(uuid는 예약전송일 경우에만 들어온다.);
//				StringHolder _return = new StringHolder(); 
//				StringHolder uuid = new StringHolder();
//				
//				for(SmsModel rSmsModel : list) {
//					System.out.println("=============================sms==================================");
//					
//					CONSUMER_ID = "test_consumer";
//					RPLY_PHON_NUM = "01091168781";
//					TITLE = rSmsModel.getMsg();
////					PHONE = rSmsModel.getDestcid() + rSmsModel.getDestcallno();
//					PHONE = "01091168781";
//					
//					logger.debug("=============================CONSUMER_ID==================================" + CONSUMER_ID);
//					logger.debug("=============================RPLY_PHON_NUM==================================" + RPLY_PHON_NUM);
//					logger.debug("=============================TITLE==================================" + TITLE);
//					logger.debug("=============================PHONE==================================" + PHONE);
//					
//					//웹서비스 호출. (send오퍼레이션)
//					service.send(CONSUMER_ID, RPLY_PHON_NUM, TITLE, PHONE, URL, START_DT_HMS, END_DT_HMS, _return, uuid);
//					
//					System.out.println("===============================_return==============================="+_return.value);
//					System.out.println("===============================uuid==============================="+uuid.value);
//					
//					smsService.update(rSmsModel);
//					Thread.sleep(20000L);
//				}
//				close();
//			} catch(Exception e) {
//				try {
//					close();
//					Thread.sleep(60000L);
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
		
		private void close() {
			if(gIPSender != null) {
				gIPSender.close();
			}
		}
		
	}
}
