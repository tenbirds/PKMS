package com.pkms.board.report.service;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import java.rmi.RemoteException;

import skt.soa.notification.webservice.SMSSender;
import skt.soa.notification.webservice.SMSSenderServiceLocator;

import org.apache.axis.client.Stub;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pkms.board.report.dao.SolutionReportDao;
import com.pkms.board.report.model.SolutionReportModel;
import com.pkms.board.report.model.SolutionReportUserModel;
import com.pkms.pkgmg.pkg.service.PkgStatusService;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;
import com.pkms.common.mail.model.MailModel;
import com.pkms.common.sms.model.SmsModel;
import com.wings.properties.service.PropertyServiceIf;
import com.pkms.common.sms.service.SmsServiceIf;
import com.pkms.common.mail.service.MailServiceIf;
import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.sys.system.service.SystemServiceIf;

import java.util.ArrayList;
import java.util.List;

@Service("SolutionReportService")
public class SolutionReportService implements SolutionReportServiceIf{
	static Logger logger = Logger.getLogger(PkgStatusService.class);
	
	@Resource(name = "SolutionReportDao")
	private SolutionReportDao solutionReportDao;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;
	
	@Resource(name = "SystemService")
	private SystemServiceIf systemService;
	
	@Resource(name = "MailService")
	private MailServiceIf mailService;
	
	@Resource(name = "SmsService")
	private SmsServiceIf smsService;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;

	public List<?> readList(SolutionReportModel SRModel) throws Exception{
		List<?> resultList = solutionReportDao.readList(SRModel);
		SRModel.setTotalCount(solutionReportDao.readTotalCount(SRModel));
		return resultList;
	}
	
	public SolutionReportModel read(SolutionReportModel SRModel) throws Exception {
		SRModel = solutionReportDao.read(SRModel);

		//첨부파일 정보 세팅
		fileManageService.read(SRModel);
		return SRModel;
	}
	
	public List<?> commentList(SolutionReportModel SRModel) throws Exception{
		return solutionReportDao.commentList(SRModel);
	}
	
	public List<SolutionReportUserModel> srUserList(SolutionReportUserModel SRUserModel) throws Exception {
		return (List<SolutionReportUserModel>) solutionReportDao.srUserList(SRUserModel);
	}
	
	public List<SolutionReportUserModel> srSosokList(SolutionReportUserModel SRUserModel) throws Exception {
		return (List<SolutionReportUserModel>) solutionReportDao.srSosokList(SRUserModel);
	}
	
	public List<SolutionReportUserModel> readSosokList(SolutionReportUserModel SRUserModel) throws Exception {
		return (List<SolutionReportUserModel>) solutionReportDao.readSosokList(SRUserModel);
	}
	
	public void create(SolutionReportModel SRModel) throws Exception {
		/**
		 * 첨부 파일 처리
		 */
		fileManageService.create(SRModel,"SR_");
		
		SRModel.setSolution_report_seq(solutionReportDao.readSeq());  
		solutionReportDao.create(SRModel);
		
		MailModel mailModel = new MailModel();
		mailModel.setMsgSubj("[SOLUTION REPORT] "+SRModel.getTitle());
		mailModel.setMsgText(this.getContent(SRModel));
		mailModel.setFrom(SRModel.getSession_user_email());
		/*
		 * 승인자 세팅
		 */
		SysModel sysModel = new SysModel();
		SysModel sysUserModel = new SysModel();

		sysModel.setSystem_seq(SRModel.getSystem_seq());
		sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
		sysModel = systemService.readUsersAppliedToSystem(sysModel);

		String[] checkSeqs = SRModel.getCheck_seqs();
		SolutionReportUserModel SRUserModel = new SolutionReportUserModel();
		SRUserModel.setSolution_report_seq(SRModel.getSolution_report_seq());
		SRUserModel.setReg_user(SRModel.getSession_user_id());
		
		List<SolutionReportUserModel> sosokMail_cnt = new ArrayList<SolutionReportUserModel>();
		int indepts_total_cnt = 0;
		int indept_user_cnt=0;
		
		for(String indept : SRModel.getCheck_indepts()){
			SRUserModel.setSystem_seq(SRModel.getSystem_seq());
			SRUserModel.setIndept(indept);
			sosokMail_cnt = solutionReportDao.srSosokMailList(SRUserModel);
			if(indept_user_cnt == 0){
				indepts_total_cnt = sosokMail_cnt.size();
			}else{
				indepts_total_cnt = indepts_total_cnt + sosokMail_cnt.size();
			}
			indept_user_cnt++;
		}

		String[] sms_users = new String[indepts_total_cnt + SRModel.getCheck_seqs().length];
		int sms_cnt = 0;
		
		for(SystemUserModel srUserMdl : sysModel.getSystemUserList()){
			int cnt = 0;
			for(String user_id : checkSeqs){
				if(user_id.equals(srUserMdl.getUser_id())){
					cnt++;
					sysUserModel.getSystemUserList().add(srUserMdl);
					
					sms_users [sms_cnt] = srUserMdl.getUser_id();
					sms_cnt++;
				}
			}
			SRUserModel.setUser_id(srUserMdl.getUser_id());
			if(cnt > 0){
				SRUserModel.setUse_yn("Y");
			}else{
				SRUserModel.setUse_yn("N");
			}
			SRUserModel.setStatus_yn("N");
			//승인자 저장
			solutionReportDao.userCreate(SRUserModel);
		}
		
		
		SRModel.setSolutionReportSosokList(solutionReportDao.srSosokList(SRUserModel));
		
		SRUserModel.setSystem_seq(SRModel.getSystem_seq());
		SRUserModel.setSolution_report_seq(SRModel.getSolution_report_seq());
		SRUserModel.setReg_user(SRModel.getSession_user_id());
		
		String[] checkIndepts = SRModel.getCheck_indepts();
		String[] indepts = new String[checkIndepts.length];
		int mail_cnt = 0;
		for(SolutionReportUserModel srSosokModel : SRModel.getSolutionReportSosokList()){
			int cnt = 0;
			for(String user_id : checkIndepts){
				if(user_id.equals(srSosokModel.getIndept())){
					cnt++;
				}
			}
			SRUserModel.setIndept(srSosokModel.getIndept());
			SRUserModel.setSosok(srSosokModel.getSosok());
			if(cnt > 0){
				SRUserModel.setUse_yn("Y");
				indepts[mail_cnt] = srSosokModel.getIndept();
				mail_cnt++;
			}else{
				SRUserModel.setUse_yn("N");
			}
			SRUserModel.setConfirm_yn("N");
			//승인자 저장
			solutionReportDao.sosokCreate(SRUserModel);
		}
		
		List<SolutionReportUserModel> sosokMailList = new ArrayList<SolutionReportUserModel>();
		List<SolutionReportUserModel> sosokMail = new ArrayList<SolutionReportUserModel>();
		
		for(String indept : indepts){
			SRUserModel.setIndept(indept);
			sosokMail = solutionReportDao.srSosokMailList(SRUserModel);
			for(SolutionReportUserModel mailinfo : sosokMail){
				sosokMailList.add(mailinfo);
				
				sms_users [sms_cnt] = mailinfo.getUser_id();
				sms_cnt++;
			}
		}
		
		mailModel.setTos(this.gettoAddress(sysUserModel.getSystemUserList(), sosokMailList));
		mailModel.setTosInfo(this.gettoInfos(sysUserModel.getSystemUserList(), sosokMailList));
		
		String [] clean_users = this.cleanId(sms_users);

		mailService.create4Multi(mailModel);
		
		for(String user_id : clean_users){			
			this.sendSms(SRModel, user_id);
		}
	}
	
	public void update(SolutionReportModel SRModel) throws Exception {
		/**
		 * 첨부 파일 처리
		 */
		fileManageService.update(SRModel,"SR_");

		solutionReportDao.update(SRModel);
		
		/*
		 * 승인정보 update
		 */
		String[] checkSeqs = SRModel.getCheck_seqs();
		SolutionReportUserModel SRUserModel = new SolutionReportUserModel();
		SRUserModel.setSolution_report_seq(SRModel.getSolution_report_seq());

		SRUserModel.setReg_user(SRModel.getSession_user_id());
		
		//부서 체크 수
		String[] checkIndepts = SRModel.getCheck_indepts();
		if(SRModel.getSystem_seq().equals(SRModel.getSystem_seq_bak())){			
			SRModel.setSolutionReportUserList(this.srUserList(SRUserModel));
			
			for(SolutionReportUserModel srUserMdl : SRModel.getSolutionReportUserList()){
				int cnt=0;
				if(checkSeqs != null){
					for(String user_id : checkSeqs){
						if(user_id.equals(srUserMdl.getUser_id())){
							cnt++;
						}
					}					
				}
				SRUserModel.setUser_id(srUserMdl.getUser_id());
				if(cnt > 0){
					SRUserModel.setUse_yn("Y");
				}else{
					SRUserModel.setUse_yn("N");
				}
				solutionReportDao.userUpdate(SRUserModel);
			}
			
			SRModel.setSolutionReportSosokList(solutionReportDao.readSosokList(SRUserModel));
			for(SolutionReportUserModel srSosokMdl : SRModel.getSolutionReportSosokList()){
				int cnt=0;
				if(checkIndepts != null){
					for(String indept : checkIndepts){
						if(indept.equals(srSosokMdl.getIndept())){
							cnt++;
						}
					}
					SRUserModel.setIndept(srSosokMdl.getIndept());
					if(cnt > 0){
						SRUserModel.setUse_yn("Y");
					}else{
						SRUserModel.setUse_yn("N");
					}
					solutionReportDao.sosokUpdate(SRUserModel);
				}
			}
			
		}else{
			solutionReportDao.userDelete(SRUserModel);
			
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(SRModel.getSystem_seq());
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			
			for(SystemUserModel srUserMdl : sysModel.getSystemUserList()){
				int cnt = 0;
				if(checkSeqs != null){
					for(String user_id : checkSeqs){
						if(user_id.equals(srUserMdl.getUser_id())){
							cnt++;
						}
					}
				}
				SRUserModel.setUser_id(srUserMdl.getUser_id());
				if(cnt > 0){
					SRUserModel.setUse_yn("Y");
				}else{
					SRUserModel.setUse_yn("N");
				}
				SRUserModel.setStatus_yn("N");
				solutionReportDao.userCreate(SRUserModel);
			}
			
			//소속 삭제 및 생성
			solutionReportDao.sosokDelete(SRUserModel);
			
			SRUserModel.setSystem_seq(SRModel.getSystem_seq());
			SRModel.setSolutionReportSosokList(solutionReportDao.srSosokList(SRUserModel));
			
			SRUserModel.setSolution_report_seq(SRModel.getSolution_report_seq());
			SRUserModel.setReg_user(SRModel.getSession_user_id());
			for(SolutionReportUserModel srSosokModel : SRModel.getSolutionReportSosokList()){
				int cnt = 0;
				for(String user_id : checkIndepts){
					if(user_id.equals(srSosokModel.getIndept())){
						cnt++;
					}
				}
				SRUserModel.setIndept(srSosokModel.getIndept());
				if(cnt > 0){
					SRUserModel.setUse_yn("Y");
				}else{
					SRUserModel.setUse_yn("N");
				}
				SRUserModel.setConfirm_yn("N");
				//승인자 저장
				solutionReportDao.sosokCreate(SRUserModel);
			}
		}
	}
	
	public void delete(SolutionReportModel SRModel) throws Exception {
		solutionReportDao.delete(SRModel);
	}
	
	public void complete(SolutionReportModel SRModel) throws Exception {
		solutionReportDao.complete(SRModel);
	}
	/*
	 * Comment create, delete
	 */
	public void commentCreate(SolutionReportModel SRModel) throws Exception {

		if(SRModel.getCheck_mails() != null && !"".equals(SRModel.getCheck_mails().trim())){
			String check_mails[] = SRModel.getCheck_mails().split(",");
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj("SOLUTION REPORT 답글");
			mailModel.setMsgText(this.getContentComment(SRModel));
			mailModel.setFrom(SRModel.getSession_user_email());
			mailModel.setTos(check_mails);
			mailModel.setTosInfo(check_mails);
			
			mailService.create4Multi(mailModel);
		}
		
		solutionReportDao.commentCreate(SRModel);
	}
	//MAIL 내용 세팅
		public String getContentComment(SolutionReportModel SRModel){
			StringBuffer sb = new StringBuffer();
			sb.append(" <tr style='height:14pt'><td align='left' valign='top' ><b>[제목]</b></td><td align='left' valign='top' > ");
			sb.append(SRModel.getTitle()+"</td></tr>");
			sb.append(" <tr style='height:14pt'><td align='left' valign='top' ><b>[시스템]</b></td><td align='left' valign='top'>");
			sb.append(SRModel.getSystem_name()+"</td></tr>");
			sb.append(" <tr style='height:14pt'><td align='left' valign='top'><b>[등록자]</b></td><td align='left' valign='top'>");
			sb.append(SRModel.getSession_user_name()+"</td></tr>");
			sb.append("<tr style='height:14pt'><td align='left' colspan='2'><br/>");
			sb.append("<a href='http://pkms.sktelecom.com/'>▶ PKMS 바로가기(SK내부직원용)</a></td></tr>");
			sb.append("<tr style='height:14pt'><td align='left' colspan='2'>");
			sb.append("<a href='http://pkmss.sktelecom.com/'>▶ PKMS 바로가기(BP용)</a><br/><br/></td></tr>");
			sb.append("<tr style='height:14pt'><td colspan='2' align='left'>");
			sb.append("<b>[답글 내용] :</b> <br/>&nbsp;" +SRModel.getContent().replace("\n", "<br>").replace(" ", "&nbsp;")+"<br/></td></tr>");
			
			return sb.toString();
		}
	
	public void commentDelete(SolutionReportModel SRModel) throws Exception {
		solutionReportDao.commentDelete(SRModel);
	}
	
	public void userYes(SolutionReportModel SRModel) throws Exception {
		solutionReportDao.userYes(SRModel);
	}
	
	public void sosokYes(SolutionReportModel SRModel) throws Exception {
		solutionReportDao.sosokYes(SRModel);
	}
	
	//MAIL 내용 세팅
	public String getContent(SolutionReportModel SRModel){
		StringBuffer sb = new StringBuffer();
		sb.append(" <tr style='height:14pt'><td align='left' valign='top' ><b>[제목]</b></td><td align='left' valign='top' > ");
		sb.append(SRModel.getTitle()+"</td></tr>");
		sb.append(" <tr style='height:14pt'><td align='left' valign='top' ><b>[시스템]</b></td><td align='left' valign='top'>");
		sb.append(SRModel.getSystem_name()+"</td></tr>");
		sb.append(" <tr style='height:14pt'><td align='left' valign='top'><b>[등록자]</b></td><td align='left' valign='top'>");
		sb.append(SRModel.getSession_user_name()+"</td></tr>");
		sb.append("<tr style='height:14pt'><td align='left' colspan='2'><br/>");
		sb.append("<a href='http://pkms.sktelecom.com/'>▶ PKMS 바로가기(SK내부직원용)</a></td></tr>");
		sb.append("<tr style='height:14pt'><td align='left' colspan='2'>");
		sb.append("<a href='http://pkmss.sktelecom.com/'>▶ PKMS 바로가기(BP용)</a><br/><br/></td></tr>");
		sb.append("<tr style='height:14pt'><td colspan='2' align='left'>");
		sb.append("<b>[내용분류] :</b>" +SRModel.getContent_gubun()+"<br/>");
		sb.append("<b>[공지사유] :</b> <br/>&nbsp;" +SRModel.getNoti_why()+"<br/>");
		sb.append("<b>[내용] :</b> <br/>&nbsp;" +SRModel.getContent().replace("\n", "<br>").replace(" ", "&nbsp;")+"<br/>"
				+ "<b>**본 메일을 수신하신 분은 PKMS에 접속하여 '확인'혹은 '승인'을 클릭하여 주시기 바랍니다.</b>" + "<br/>" + "</td></tr>");
		
		return sb.toString();
	}
	
	public String[] gettoAddress(List<SystemUserModel> systemUserList, List<SolutionReportUserModel> sosokMailList) {
		String[] rets = new String[systemUserList.size() + sosokMailList.size()];
		int i=0;
		for(Object obj : systemUserList){
			rets[i] = ((SystemUserModel)obj).getUser_email();
			i++;
		}
		for(Object obj : sosokMailList){
			rets[i] =((SolutionReportUserModel)obj).getUser_email();
			i++;
		}
		
		return rets;
	}
	
	public String[] gettoInfos(List<SystemUserModel> systemUserList, List<SolutionReportUserModel> sosokMailList) throws Exception {
		String[] rets = new String[systemUserList.size() + sosokMailList.size() + 2];
		int i=0;
		rets[0] = "<table><tr><td align = 'center'>이름</td><td align = 'center'>담당</td><td align = 'center'>소속</td><td align = 'center'>이메일</td></tr>";
		i++;
		//승인자
		for(Object obj : systemUserList){
//			rets[i] = ((SystemUserModel)obj).getUser_email() + "("+((SystemUserModel)obj).getUser_name()+" "+((SystemUserModel)obj).getSosok()+")";
			rets[i] ="<tr><td>" + ((SystemUserModel)obj).getUser_name() +"</td><td>"+"승인"+"</td><td>"
					 +((SystemUserModel)obj).getSosok()+"</td><td>"+((SystemUserModel)obj).getUser_email() +"</td></tr>";
			i++;
		}
		for(Object obj : sosokMailList){
//			rets[i] =((SolutionReportUserModel)obj).getUser_email() + "("+((SolutionReportUserModel)obj).getUser_name()+" "+((SolutionReportUserModel)obj).getSosok()+")";
			rets[i] ="<tr><td>" + ((SolutionReportUserModel)obj).getUser_name() +"</td><td>"+((SolutionReportUserModel)obj).getCharge_gubun_name()+"</td><td>"
					 +((SolutionReportUserModel)obj).getSosok()+"</td><td>"+((SolutionReportUserModel)obj).getUser_email() +"</td></tr>";
			i++;
		}
		rets[i] = "</table>";
		return rets;
	}
	
	public String readVuYn(SolutionReportModel SRModel) throws Exception {
		return solutionReportDao.readVuYn(SRModel);
	}
	
	public List<SolutionReportUserModel> readMailList(SolutionReportUserModel SRUserModel) throws Exception {
		return (List<SolutionReportUserModel>) solutionReportDao.srSosokMailList(SRUserModel);
	}
	
	public void sendSms(SolutionReportModel SRModel, String user_id) throws Exception {
		SRModel.setUser_id(user_id);
		String tel = solutionReportDao.readTel(SRModel);
		String tel1 = "";
		String tel2 = "";
		
		if(tel != null) {
			tel = tel.replaceAll("-", "");
			tel1 = tel.substring(0, 3);
			tel2 = tel.substring(3, tel.length());
			
			SmsModel smsModel = new SmsModel();
			smsModel.setLog_no("1");//의미없음
			smsModel.setMsg("[PKMS] SOLUTION REPORT " + SRModel.getTitle());
			smsModel.setDestcid(tel1);//국번
			smsModel.setDestcallno(tel2);
			smsModel.setPortedflag("0");//의미없음
			smsModel.setTid("65491");//승인인 경우
			try{
				SMSSenderServiceLocator locator = new SMSSenderServiceLocator();
				locator.setEndpointAddress("SMSSenderSoapPort", propertyService.getString("SOA.Sms.ip"));
				SMSSender service = locator.getSMSSenderSoapPort();
				Stub stub = (Stub)service;
				stub._setProperty(Stub.USERNAME_PROPERTY, propertyService.getString("SOA.Username"));
				stub._setProperty(Stub.PASSWORD_PROPERTY, propertyService.getString("SOA.Password"));
				
				//Input 파라미터
				String CONSUMER_ID = propertyService.getString("SOA.Username"); //송신시스템 id(시스템 별 ID 부여)
				String RPLY_PHON_NUM = propertyService.getString("SOA.Sms.sendNum"); //송신자 전화번호
				String TITLE = "(PKMS) SOLUTION REPORT [" + SRModel.getTitle() + "] 확인해 주시기 바랍니다."; //전송메시지
				String PHONE = tel1 + tel2; //수신자 전화번호
				
				String URL = ""; //수신 URL		 					
				String START_DT_HMS = ""; //예약발송 시작시간			
				String END_DT_HMS = "";	//예약발송 종료시간
				
				logger.debug("============================ sms_send_input_CONSUMER_ID ============================" + CONSUMER_ID);
				logger.debug("============================ sms_send_input_RPLY_PHON_NUM ==========================" + RPLY_PHON_NUM);
				logger.debug("============================ sms_send_input_TITLE ==================================" + TITLE);
				logger.debug("============================ sms_send_input_PHONE ==================================" + PHONE);
				
				//Output (uuid는 예약전송일 경우에만 들어온다.");
				StringHolder _return = new StringHolder();
				StringHolder uuid = new StringHolder();
				
				//웹서비스 호출. (send오퍼레이션)
				service.send(CONSUMER_ID, RPLY_PHON_NUM, TITLE, PHONE, URL, START_DT_HMS, END_DT_HMS, _return, uuid);
				
				logger.debug("============================ sms_send_return =======================================" + _return.value);
				logger.debug("============================ sms_send_return_uuid ==================================" + uuid.value);
			} catch (ServiceException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			smsService.create(smsModel);
		}	
	}
	
	public String [] cleanId(String [] user_id) throws Exception {
		// 중복사용자 제거
		ArrayList<String> uniqEmailAl = new ArrayList<String>();
		for (String id : user_id) { //받을 사람 수만큼
			if (uniqEmailAl.contains(id)) continue; //리스트 안의 중복자 제거 contains
			uniqEmailAl.add(id); //중보 제거된 값 리스트에 생성
		}
		user_id = (String[]) uniqEmailAl.toArray(new String[uniqEmailAl.size()]); //Array 값 -> String 으로 변환
		uniqEmailAl.clear();
		uniqEmailAl = null;
		
		return user_id;
	}
	
}
