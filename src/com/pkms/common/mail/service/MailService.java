package com.pkms.common.mail.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.xml.rpc.ServiceException;


import org.apache.axis.client.Stub;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import skt.soa.notification.webservice.MailSender;
import skt.soa.notification.webservice.MailSenderServiceLocator;

import com.pkms.common.mail.model.MailModel;
import com.wings.properties.service.PropertyServiceIf;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : 
 * @Date : 2012. 
 * 
 */
@Service("MailService")
public class MailService implements MailServiceIf {
	
	static Logger logger = Logger.getLogger(MailService.class);
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;

	private final String PKMS = "PKMS(패키지 관리 시스템)";

	@Override
	public void create(MailModel mailModel) {
		//배포시 삭제
//		mailModel.setTo("subaek@in-soft.co.kr");
		
		transport(mailModel.getTo(), mailModel.getFrom(), mailModel.getMsgSubj(), mailModel.getType(), mailModel.getMsgText(), mailModel.getToInfo());	
	}

	@Override
	public void create4Multi(MailModel mailModel) {
		//배포시 삭제
//		mailModel.setTos(new String[] {"tbimsmail1@sktelecom.com", "tbimsmail1@sktelecom.com"});
//		mailModel.setFrom("tbimsmail1@sktelecom.com");
		
//		mailModel.setTos(new String[] {"subaek@in-soft.co.kr", "jhkim@in-soft.co.kr"});
//		mailModel.setFrom("subaek@in-soft.co.kr");
		
		transport(mailModel.getTos(), mailModel.getFrom(), mailModel.getMsgSubj(), mailModel.getType(), mailModel.getMsgText(), mailModel.getTosInfo());
	}

	private void transport(Object toAddresses, String from, String msgSubj, String contentType, String content, Object toInfos) {
		try{
			//20160420 보내는 사람은 무저건 cpg@in-soft.co.kr
			from = "pkms@sk.com";
			//soa 연동
			MailSenderServiceLocator locator = new MailSenderServiceLocator();
			
			locator.setEndpointAddress("MailSenderSoapPort", propertyService.getString("SOA.Mail.ip"));
		
			MailSender service = locator.getMailSenderSoapPort();
			
			Stub stub = (Stub)service;
			stub._setProperty(Stub.USERNAME_PROPERTY, propertyService.getString("SOA.Username"));
			stub._setProperty(Stub.PASSWORD_PROPERTY, propertyService.getString("SOA.Password"));
			
			//Input
			String senderEmail = null; //보내는사람	
			String receiverEmail = ""; //받는사람
			String subject = null; //제목	
			String mailContent = null; //내용
			String receiverInfos = ""; //받는사람들 정보
			String receiverInfo = ""; //받는사람 정보
			//Output 
			String _return = "";
			//받는사람
			String[] toStrs = null;
			//받는사람들 정보
			String[] toIfs = null;
			
			if(toAddresses instanceof String[]) {
				toStrs = (String[]) toAddresses;
				
//System.out.print("[BEFORE]"); for (String email : toStrs) System.out.print(email+","); System.out.println("");				

				// 중복사용자 제거. 20121030
				ArrayList<String> uniqEmailAl = new ArrayList<String>();
				for (String email : toStrs) { //받을 사람 수만큼
					if (uniqEmailAl.contains(email)) continue; //리스트 안의 중복자 제거 contains
					uniqEmailAl.add(email); //중보 제거된 값 리스트에 생성
				}
				toStrs = (String[]) uniqEmailAl.toArray(new String[uniqEmailAl.size()]); //Array 값 -> String 으로 변환
				uniqEmailAl.clear();
				uniqEmailAl = null;

//System.out.print("[AFTER]"); for (String email : toStrs) System.out.print(email+","); System.out.println("");				
				int cnt = 0;
				for (int i = 0; i < toStrs.length; i++) {
					if(toStrs[i] != null) {
						//받는사람
//						receiverEmail = toStrs[i];
						//보내는사람 
						senderEmail = from;
						//제목
						subject = msgSubj;
						//내용
						mailContent = content;
						if(cnt == 0){
							receiverEmail += toStrs[i];
						}else{
							receiverEmail += "," + toStrs[i];
						}
						
						if(mailContent.indexOf("null") > -1){
							mailContent = mailContent.replaceAll("null", "");
						}
						
						/*logger.debug("============================ mail_send_input_receiverEmail ================================" + receiverEmail);
						logger.debug("============================ mail_send_input_senderEmail ==================================" + senderEmail);
						logger.debug("============================ mail_send_input_subject ======================================" + subject);
						logger.debug("============================ mail_send_input_mailContent ==================================" + mailContent);
						
						
						logger.debug("============================ mail_send_return" + i + " ====================================" + _return);*/
						
						cnt++;
					}
					//웹서비스 호출
				}
				cnt = 0;
				
				/**
				 받는사람들 정보출력 위한 중복제거와 콤마구분 추가 1017 - ksy */
				toIfs = (String[]) toInfos;
				ArrayList<String> uniqToinfos = new ArrayList<String>();
				for(String uniqInfo : toIfs){
					if(uniqToinfos.contains(uniqInfo)) continue;
					uniqToinfos.add(uniqInfo);
				}
				toIfs = (String[]) uniqToinfos.toArray(new String[uniqToinfos.size()]);
				uniqToinfos.clear();
				uniqToinfos = null;
				
				for(int t =0; t < toIfs.length; t++){
					if(toIfs[t] != null){
						if(cnt == 0){
							receiverInfos += toIfs[t];
						}else{
							receiverInfos += ", " + toIfs[t];
						}
						cnt++;
					}
				}
				cnt=0;
				//System.out.println(receiverInfos + "  ======= 받는사람들 정보");
				/***/
				
					 String contentTemp	 = "<table cellspacing=5 cellpadding=0 style='width:550pt; border:5px; border-color:#E7E7E7; border-style:solid;'><tr>";
							contentTemp += "<td valign=top style='background:white;'><table cellspacing=0 cellpadding=0 style='width:100%;'>";
							contentTemp += "<tr style='height:26.25pt'><td style='background:black; height:28.25pt'><p style='line-height:15.0pt'>";
							contentTemp += "<span style='color: #E1193E; font-weight: bold; font-size: 30px; font-family:맑은 고딕;'>&nbsp;SK 텔레콤&nbsp;</span>";
							contentTemp += "<span style='color: #F5872E; font-weight: bold; font-size: 30px; font-family:맑은 고딕;'>PKMS</span>";
							contentTemp += "<span style='font-size: 17px; color: orange; font-family:맑은 고딕;'>2.0</span></p></td></tr><tr><td><div align=center>";
							contentTemp += "<table border=0 cellspacing=0 cellpadding=0 style='width:85.0%;'><tr style='height:12pt'><td style='width: 20%; '></td><td></td></tr>";
							
					String 	contentTemp2  = "<tr style='height:20pt'><td colspan='2'><hr size='0' color='gray' ></td><td></td></tr>";
							contentTemp2 += "<tr style='height:14pt'><td align='left' valign='top'><b>[수신자]</b></td>";		
							contentTemp2 += "<td align='left' valign='top' >" + receiverInfos + "<br/><br/></td></tr>";
							contentTemp2 += "</table></div></td></tr></table></td></tr></table>";
							
							mailContent = contentTemp + mailContent + contentTemp2; 
							//System.out.println("★★★★★★★★★★★★★★3");
							//System.out.println(mailContent);
							//System.out.println("★★★★★★★★★★★★★★3");
					logger.debug("============================ SEND SUCCESS ====================================" + receiverEmail);
					_return = service.send(senderEmail, receiverEmail, subject, mailContent);
					logger.debug("============================ SEND SUCCESS END ====================================" + _return);
			} else {
				//받는사람
				receiverEmail = toAddresses.toString();
				//보내는사람 
				senderEmail = from;
				//제목
				subject = msgSubj;
				//내용
				mailContent = content;
				//받는사람 정보
				receiverInfo = toInfos.toString();
				
				if(mailContent.indexOf("null") > -1){
					mailContent = mailContent.replaceAll("null", "");
				}
				
				logger.debug("============================ mail_send_input_receiverEmail2 ================================" + receiverEmail);
				logger.debug("============================ mail_send_input_senderEmail2 ==================================" + senderEmail);
				logger.debug("============================ mail_send_input_subject2 ======================================" + subject);
				logger.debug("============================ mail_send_input_mailContent2 ==================================" + mailContent);
				
				
				
		String  contentTemp	 = "<table cellspacing=5 cellpadding=0 style='width:550pt; border:5px; border-color:#E7E7E7; border-style:solid;'><tr>";
				contentTemp += "<td valign=top style='background:white;'><table cellspacing=0 cellpadding=0 style='width:100%;'>";
				contentTemp += "<tr style='height:26.25pt'><td style='background:black; height:28.25pt'><p style='line-height:15.0pt'>";
				contentTemp += "<span style='color: #E1193E; font-weight: bold; font-size: 30px; font-family:맑은 고딕;'>&nbsp;SK 텔레콤&nbsp;</span>";
				contentTemp += "<span style='color: #F5872E; font-weight: bold; font-size: 30px; font-family:맑은 고딕;'>PKMS</span>";
				contentTemp += "<span style='font-size: 17px; color: orange; font-family:맑은 고딕;'>2.0</span></p></td></tr><tr><td><div align=center>";
				contentTemp += "<table border=0 cellspacing=0 cellpadding=0 style='width:85.0%;'><tr style='height:12pt'><td style='width: 20%; '></td><td></td></tr>";
		
		String 	contentTemp2  = "<tr style='height:20pt'><td colspan='2'><hr size='0' color='gray' ></td><td></td></tr>";
				contentTemp2 += "<tr style='height:14pt'><td align='left' valign='top'><b>[수신자]</b></td>";		
				contentTemp2 += "<td align='left' valign='top' >" + receiverInfo + "<br/><br/></td></tr>";
				contentTemp2 += "</table></div></td></tr></table></td></tr></table>";	
				
			    mailContent = contentTemp+mailContent+contentTemp2;
				//웹서비스 호출
				_return = service.send(senderEmail, receiverEmail, subject, mailContent);
				
				logger.debug("============================ mail_send_return2 ====================================" + _return);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
//		try {			
//			//Init
//			Properties props = new Properties();
//			props.put("mail.transport.protocol", "smtp");
//			props.put("mail.smtp.host", propertyService.getString("Mail.ip"));
//			props.put("mail.smtp.port", propertyService.getString("Mail.port"));
//			
////			MailAuthenticator mailAuthenticator = new MailAuthenticator(propertyService.getString("Mail.id"), propertyService.getString("Mail.pw"));
//			
////			Session msgSession = Session.getDefaultInstance(props, mailAuthenticator); 
//			Session msgSession = Session.getDefaultInstance(props, null); 
//			Message msg = new MimeMessage(msgSession);
//			
//			//FROM
//			InternetAddress fromIa = new InternetAddress();
//			fromIa.setPersonal(PKMS, "UTF-8");
//			fromIa.setAddress(from);
//			msg.setFrom(fromIa);
//			
//			//TO
//			if(toAddresses == null)  {
//				throw new Exception("error.sys.보내는 사람 주소가 잘못되었습니다.");
//			}
//			
//			Address[] toAddressesIas = null;
//			Address toAddressesIa = null;
//			String[] toStrs = null;
//			
//			if(toAddresses instanceof String[]) {
//				toStrs = (String[]) toAddresses;
//				toAddressesIas = new InternetAddress[toStrs.length];
//				for (int i = 0; i < toStrs.length; i++) {
//					if(toStrs[i] != null) {
//						toAddressesIas[i] = new InternetAddress(toStrs[i]);
//					}
//				}
//				msg.setRecipients(Message.RecipientType.TO, toAddressesIas);
//			} else if(toAddresses instanceof String) {
//				System.out.println("============================1=============================" + toAddresses.toString());
//				toAddressesIa = new InternetAddress((String) toAddresses, "UTF-8");
//				msg.setRecipient(Message.RecipientType.TO, toAddressesIa);
//			}
//			
//			//Subject
//			msgSubj = MimeUtility.encodeText(msgSubj, "UTF-8", "B");
//			msg.setSubject(msgSubj);
//			
//			//Content
//			if(contentType == null || "".equals(contentType)) {
//				contentType = "text";
//			}
//			contentType = (contentType.equals("text")) ? "text/plain;charset=utf-8" : "text/html;charset=utf-8";
//			
//			if(!"text".equals(contentType)) {
//				content = content.replace("\n", "<br/>");
////				content = content + "<br/>" + propertyService.getString("Mail.pkms.url");
//			} else {
//				content = content.replace("<br/>", "\n");
//			}
//			msg.setContent(content, contentType);
//			
//			msg.setSentDate(new Date());
//			
//			//Transport
//			Transport.send(msg);
//		} catch (Exception e) {
////			e.printStackTrace();
//			logger.error(e.getMessage());
//		}

		
		
		
	}
}
