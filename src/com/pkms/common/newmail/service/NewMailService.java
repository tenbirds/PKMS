package com.pkms.common.newmail.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.springframework.stereotype.Service;
import org.apache.axis.client.Stub;
import org.apache.log4j.Logger;

import com.pkms.common.mail.model.MailModel;
import com.pkms.common.mail.service.MailServiceIf;
import com.pkms.common.newmail.dao.NewMailDAO;
import com.pkms.common.newmail.model.NewMailModel;
import com.pkms.common.sms.model.SmsModel;
import com.pkms.common.sms.service.SmsServiceIf;
import com.pkms.common.util.PkgSmsUtil;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgUserModel;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.pkgmg.wired.service.WiredStatusServiceIf;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.usermg.user.model.BpUserModel;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.BpUserServiceIf;
import com.pkms.usermg.user.service.SktUserServiceIf;
import com.wings.properties.service.PropertyServiceIf;

import skt.soa.notification.webservice.MailSender;
import skt.soa.notification.webservice.MailSenderServiceLocator;
import skt.soa.notification.webservice.SMSSender;
import skt.soa.notification.webservice.SMSSenderServiceLocator;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : 
 * @Date : 2012. 
 * 
 */
@Service("NewMailService")
public class NewMailService implements NewMailServiceIf {
	
	static Logger logger = Logger.getLogger(NewMailService.class);
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;

	@Resource(name = "NewMailDAO")
	protected NewMailDAO newmaildao;

	@Resource(name = "MailService")
	protected MailServiceIf mailService;		
	
	@Resource(name = "SmsService")
	private SmsServiceIf smsService;
	
	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;
	
	@Resource(name = "BpUserService")
	private BpUserServiceIf bpUserService;
	
	@Resource(name = "WiredStatusService")
	private WiredStatusServiceIf wiredStatusService;
	
//system_seq		- 시스템 시권스 값
//gubun				- 구분값 LIST로 받으면 됨  코드값으로  
//						VU("VU", "상용 검증담당자"), AU("AU", "상용 승인담당자"), PU("PU", "사업계획 담당자"), BU("BU", "협력업체 담당자"), DV("DV", "개발 검증담당자"), DA("DA", "개발 승인담당자"),
//						VO("VO", "용량 검증담당자"), SE("SE", "보안 검증담당자"), CH("CH", "과금 검증담당자"), NO("NO", "비기능 검증담당자"), LV("LV", "현장 담당자"), LA("LA", "현장 승인담당자"),
//						VA("VA", "용량 검증 승인"), CA("CA", "과금 검증 승인"), MO("MO", "상황관제 담당자"))
//nowPkgStatus		- 상태값 상단바에 표시되는 상태  현재는 String "NON" 입력하면 기존 메일처럼
//inputModel		- title 제목 / content 내용


	@Override
	public void maileModule(String system_seq, List<String> gubun, String nowPkgStatus,NewMailModel inputModel) {
		
//1. start pkg_seq 로 system_seq 검색
//		pkg_seq로 입력 받아서 변환할지 앞단에서 변환해서 넣어줄지 ...
		NewMailModel pkg_seqsearch = new NewMailModel();
		String pkg_seq = system_seq; // 
		pkg_seqsearch.setPkg_seq(pkg_seq);
		NewMailModel system_seq2 = new NewMailModel();
		try {
			system_seq2 = newmaildao.systemSeqSearch(pkg_seqsearch);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		system_seq = system_seq2.getSystem_seq();
//1. end
		
//2. start - system_seq/gubun/type(M) 으로  
//		system_user 테이블 검색 후 inf_person_info_rcv 검색하여 유저 상세정보(메일 전화번호... )검색
		NewMailModel searchId = new NewMailModel();
		searchId.setSystem_seq(system_seq);
		searchId.setGubuns(gubun);
		searchId.setUser_type("M");

		List<NewMailModel> userinfo_List = new ArrayList();
		List<NewMailModel> systemUserinfo_List = new ArrayList();
		try {			
			systemUserinfo_List =	 newmaildao.readuserList(searchId);   // 해당 user ID 검색
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if((inputModel.getAddUsers() != null)  && !((inputModel.getAddUsers()).isEmpty())) {
			userinfo_List.addAll(systemUserinfo_List);
			userinfo_List.addAll(inputModel.getAddUsers());
		}else {
			userinfo_List.addAll(systemUserinfo_List);
		}
//2. end
				
//3. start - 보내는 메일 정보 셋팅 		
		String[] To = new String[userinfo_List.size()];
		String[] userinfos = new String[userinfo_List.size()];
		String[] ToSms = new String[userinfo_List.size()];
		int size =0;
		for (NewMailModel userinfo : userinfo_List) {
			To[size] = userinfo.getEmail();
			userinfos[size] = userinfo.getEmail()+"("+userinfo.getHname()+" "+userinfo.getSosok()+")" ;
			ToSms[size] = userinfo.getMovetelno();
			size++;
		}

			MailModel mailModel = new MailModel();
			mailModel.setTos(To);//배열 받는사람
			mailModel.setFrom(inputModel.getSession_user_email()); // 현재 로그인 된 사용자 이메일  from = "pkms@sk.com";
			mailModel.setTosInfo(userinfos);//배열 받는사람 정보 
//			mailModel.setType(type);//?? 어떤값인지...
			
			
			try {
				this.newSendSMS(inputModel.getSmsMsgvalue(),ToSms);  // (MSG,phon_number[])
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			nowPkgStatus="NEW";//무조건 신규메일 양식
			
			//현재 상태에 따른 내용 변화  
			if("NON".equals(nowPkgStatus)) { // 기존 메일 
				if(inputModel.getMailTitle() != null&& inputModel.getMailTitle()!="") {
					mailModel.setMsgSubj(inputModel.getMailTitle());//제목				
				}else {
					mailModel.setMsgSubj("PKMS 에서 전달드립니다.");//제목		
				}
				
				if(inputModel.getMailContent() != null&& inputModel.getMailContent()!="") {
					mailModel.setMsgText(inputModel.getMailContent());//내용
				}else {
					mailModel.setMsgText("");//내용
				}
//		(Object toAddresses, String from, String msgSubj, String contentType, String content, Object toInfos)	
				mailService.create4Multi(mailModel);	//기존메일
				
				
				
				
				
			}else if("NEW".equals(nowPkgStatus)) {
				String title = "";
				if(inputModel.getMailTitle() != null&& inputModel.getMailTitle()!="") {
					title = inputModel.getMailTitle();//제목				
				}else {
					title="PKMS 에서 전달드립니다.";//제목		
				}
				
				String fullcontent =	this.makeContent( inputModel.getMailContent(),  userinfos,  inputModel ,  "NON");
				newtransport( To, inputModel.getSession_user_email(),  title, fullcontent);
//			}else {
				
			}
//3. end	
			
	}  // end maileModule



	
	
	
//필수
//	NewMailModel.getUser_ids()	- List<String>
//	nowPkgStatus 				-	"NON"   // 메일 양식 선택값
	@Override
	public void maileModuleUserId( String nowPkgStatus, NewMailModel inputModel) {
		
		if(!((inputModel.getUser_ids()).isEmpty()) || !((inputModel.getUser_id()).isEmpty())) { //id list null  체크    등록자추가로 User_id 추가
				
//2. start -  inf_person_info_rcv 검색하여 유저 상세정보(메일 전화번호... )검색
			List<NewMailModel> userinfo_List = new ArrayList();
			if(inputModel.getUser_type() == "B") {
				NewMailModel userinfo = new NewMailModel();
				BpUserModel bpUserModel = new BpUserModel();
				bpUserModel.setBp_user_id(inputModel.getUser_id());
				try {
					bpUserModel = bpUserService.read(bpUserModel);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(bpUserModel != null) {
						userinfo.setHname(bpUserModel.getBp_user_name());//한글명
						userinfo.setEmail(bpUserModel.getBp_user_email());
						userinfo.setSosok(bpUserModel.getBp_name());//소속
						userinfo.setMovetelno(bpUserModel.getBp_user_phone1()+"-"+bpUserModel.getBp_user_phone2()+"-"+bpUserModel.getBp_user_phone3());//010-000-000
//						userinfo.setTelno(telno);//부서전화?
						userinfo_List.add(userinfo);
				}
				
			}else {
				try {			
					userinfo_List =	newmaildao.readInfo(inputModel);   // 해당 user ID 검색 getUser_id 값 필수
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
//2. end
			if(!(userinfo_List.isEmpty())) {	
//3. start - 보내는 메일 정보 셋팅 		-- maileModule과 공통사항
				String[] To = new String[userinfo_List.size()];
				String[] userinfos = new String[userinfo_List.size()];
				String[] ToSms = new String[userinfo_List.size()];
							
				int size =0;
				for (NewMailModel userinfo : userinfo_List) {  // 받는사람 정보 셋팅
					To[size] = userinfo.getEmail();
					userinfos[size] = userinfo.getEmail()+"("+userinfo.getHname()+" "+userinfo.getSosok()+")" ;
					ToSms[size] = userinfo.getMovetelno();
					size++;
				}

				
				try { //send SMS 
					this.newSendSMS(inputModel.getSmsMsgvalue(),ToSms);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

					MailModel mailModel = new MailModel();
					mailModel.setTos(To);//배열 받는사람
					mailModel.setFrom(inputModel.getSession_user_email()); // 현재 로그인 된 사용자 이메일  현재는 무조건 from = "pkms@sk.com";
					mailModel.setTosInfo(userinfos);//배열 받는사람 정보 
		//			mailModel.setType(type);//?? 어떤값인지...

					nowPkgStatus="NEW";
					//현재 상태에 따른 내용 변화  
					if(nowPkgStatus=="NON") { // 기존 메일 
						if(inputModel.getMailTitle() != null&& inputModel.getMailTitle()!="") {
							mailModel.setMsgSubj(inputModel.getMailTitle());//제목				
						}else {
							mailModel.setMsgSubj("PKMS 에서 전달드립니다.");//제목		
						}
						if(inputModel.getMailContent() != null&& inputModel.getMailContent()!="") {
							mailModel.setMsgText(inputModel.getMailContent());//내용
						}else {
							mailModel.setMsgText("");//내용
						}
						mailService.create4Multi(mailModel);		
						
					}else if(nowPkgStatus=="NEW") {
						String title = "";
						if(inputModel.getMailTitle() != null&& inputModel.getMailTitle()!="") {
							title = inputModel.getMailTitle();//제목				
						}else {
							title="PKMS 에서 전달드립니다.";//제목		
						}
						String fullcontent =	this.makeContent( inputModel.getMailContent(),  userinfos,  inputModel ,  "NON");
						newtransport( To, inputModel.getSession_user_email(),  title, fullcontent);
		//			}else {
					}
//3. end	
			}//end userinfo_List null check
		}// end inputModel List null check
	}// end maileModuleUserId
	
	
	
	
	@Override
	public List<NewMailModel> pkgUserInfoList( NewMailModel inputModel) throws Exception {
		return newmaildao.pkgUserInfoList(inputModel);
	}
	
	@Override
	public List<String> pkgUserIdList( NewMailModel inputModel) throws Exception {
		return newmaildao.pkgUserIdList(inputModel);
	}
	
	@Override
	public List<String> pkgUserIdList2( NewMailModel inputModel) throws Exception {
		return newmaildao.pkgUserIdList2(inputModel);
	}
		
	@Override
	public List<NewMailModel> eqmentUserList( NewMailModel eqment_seq) throws Exception {
		return newmaildao.eqmentUserList(eqment_seq);
	}
	
	@Override
	public List<PkgEquipmentModel> eqmentList( Pkg21Model pkg21Model) throws Exception {
		return newmaildao.eqmentList(pkg21Model);
	}
	
	

	

//============================================================================================================================
	


private String makeContent(String content, Object toInfos, NewMailModel inputModel , String mailform_type) {//mailform_type은 아직사용안함
	
	if(content.indexOf("null") > -1){
		content = content.replaceAll("null", "");
	}

	String fullcontent="";//내용
	String receiverInfos = makeToUserInfo(toInfos); ;//받는 사람정보
	
	/*String header = "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'><html><head>";
	header += "<link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'> </head><body>";
	header += "<table style='width:100%;'>";
	header += "<tr><td align='center'><table cellspacing='0' cellpadding='0' style='width:643px;font-size:13px;border:4px solid #fab226;'><tr><td align='right' style='height:100px;text-align:right;' background='#f5f5f4'>";
	header += "<table style='width:360px;'><tr><td style='background:#e51a38;text-align:left;font-size:24px;color:#fff;font-weight:300;padding:15px 0 15px 25px;'>SK 텔레콤  PKMS 2.0</td></tr></table></td></tr>";*/
	
	String header = "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'><html><head>";
	header += "<link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'> </head><body>";
	header += "<table style='width:100%;'>";
	header += "<tr><td align='center'><table cellspacing='0' cellpadding='0' style='width:643px;font-size:13px;border:4px solid #fab226;'><tr><td align='right' style='height:100px;text-align:right;' background='#f5f5f4'>";
	header += "<table style='width:360px;'><tr><td style='background:#e51a38;text-align:left;font-size:24px;color:#fff;font-weight:300;padding:15px 0 15px 25px;'>SK 텔레콤  PKMS 2.0</td></tr></table></td></tr>";
	
	/*String 	footer = "<tr><td style='padding:20px 0;text-align:center;'>";
	footer += "<table style='width:220px;margin:0 auto 20px;' cellspacing='4'><tr>";
	footer += "<td style='width:110px;height:80px;text-align:center;background:#4e78fd;padding-top:15px;'><a href='http://pkms.sktelecom.com/' style='font-size:18px;color:#fff;text-decoration:none;'>";
	footer += "<b>PKMS</b><br>바로가기(SK내부직원용)</a></td>";
	footer += "<td style='width:110px;height:80px;text-align:center;background:#4e78fd;padding-top:15px;'><a href='http://pkmss.sktelecom.com/' style='font-size:18px;color:#fff;text-decoration:none;'>";
	footer += "<b>PKMS</b><br>바로가기(BP용)</a></td></tr></table></td></tr></table><br>";
	footer += "<table style='width:643px;background:#f5f5f4;border:1px solid #ddd;'>";
	footer += "<tr><th valign='top' align='left' style='width:90px;font-size:18px;color:#333;font-weight:400;line-height:20px;'><u>&nbsp;&nbsp;&nbsp;수신자</u></th>";
	footer += "<td style='text-align:left;padding:10px 15px;font-size:12px;color:#666;line-height:20px;'>"+receiverInfos+"</td></tr></table>";
	footer += "<table style='width:643px;'><tr><td style='text-align:center;font-size:11px;color:#888;padding:15px 0;'>";
	footer += "Copyright ⓒ 2012,2018 SKTelecom All Rights Reserved</td></tr></table></td>	</tr></table></body></html>";*/
	
	String 	footer = "<tr><td style='padding:20px 0;text-align:center;'>";
	footer += "<table style='width:220px;margin:0 auto 20px;' cellspacing='4'><tr>";
	footer += "<td style='width:110px;height:80px;text-align:center;background:#4e78fd;padding-top:15px;'><a href='http://pkms.sktelecom.com/' style='font-size:18px;color:#fff;text-decoration:none;'>";
	footer += "<b>PKMS</b><br>바로가기(SK내부직원용)</a></td>";
	footer += "<td style='width:110px;height:80px;text-align:center;background:#4e78fd;padding-top:15px;'><a href='http://pkmss.sktelecom.com/' style='font-size:18px;color:#fff;text-decoration:none;'>";
	footer += "<b>PKMS</b><br>바로가기(BP용)</a></td></tr></table></td></tr></table><br>";
	footer += "<table style='width:643px;background:#f5f5f4;border:1px solid #ddd;'>";
	footer += "<tr><th valign='top' align='left' style='width:90px;font-size:18px;color:#333;font-weight:400;line-height:20px;'><u>&nbsp;&nbsp;&nbsp;수신자</u></th>";
	footer += "<td style='text-align:left;padding:10px 15px;font-size:12px;color:#666;line-height:20px;'>"+receiverInfos+"</td></tr></table>";
	footer += "<table style='width:643px;'><tr><td style='text-align:center;font-size:11px;color:#888;padding:15px 0;'>";
	footer += "Copyright ⓒ 2012,2018 SKTelecom All Rights Reserved</td></tr></table></td>	</tr></table></body></html>";
	
	
	
	fullcontent		 = header + content + footer ;

return fullcontent;
}





private static String makeToUserInfo(Object toInfos) {

	String receiverInfos = ""; //받는사람들 정보
	String[] toIfs = null;
	
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
	
	int cnt = 0;
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
	
return receiverInfos;
}


				
	
	
	
	private void newtransport(Object toAddresses, String from, String msgSubj, String content) {
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

			//Output 
			String _return = "";
			//받는사람
			String[] toStrs = null;
			//받는사람들 정보

			
			if(toAddresses instanceof String[]) {
				toStrs = (String[]) toAddresses;
							

				// 중복사용자 제거. 20121030
				ArrayList<String> uniqEmailAl = new ArrayList<String>();
				for (String email : toStrs) { //받을 사람 수만큼
					if (uniqEmailAl.contains(email)) continue; //리스트 안의 중복자 제거 contains
					uniqEmailAl.add(email); //중보 제거된 값 리스트에 생성
				}
				toStrs = (String[]) uniqEmailAl.toArray(new String[uniqEmailAl.size()]); //Array 값 -> String 으로 변환
				uniqEmailAl.clear();
				uniqEmailAl = null;
		
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

						if(cnt == 0){
							receiverEmail += toStrs[i];
						}else{
							receiverEmail += "," + toStrs[i];
						}
						
						cnt++;
					}
					//웹서비스 호출
				}
				cnt = 0;

					logger.debug("=================NEW SEND SUCCESS =================pkg21 title : "+msgSubj+" ===================" + receiverEmail);
//					_return = service.send(senderEmail, receiverEmail, subject, content);//test 임시 주석 org source
//					System.out.println("★★★★★★★★★★★★★★1");
//					System.out.println(senderEmail);
//					System.out.println("★★★★★★★★★★★★★★2");
//					System.out.println(receiverEmail);
//					System.out.println("★★★★★★★★★★★★★★3");
//					System.out.println(subject);
//					System.out.println("★★★★★★★★★★★★★★4");
//					System.out.println(content);
//					System.out.println("★★★★★★★★★★★★★★5");

					
					logger.debug("============================NEW SEND SUCCESS END ====================================" + _return);
			} 
		} catch (ServiceException e) {
			e.printStackTrace();
//		}//_rCeturn
		} 
		/*catch (RemoteException e) {//test 임시 주석 org source
			e.printStackTrace();
		}*/
	}
	
	
	
	
@Override
public String genrerateString(List<PkgEquipmentModel> eqmentList ,Pkg21Model pkg21Model , String stepCheck ,String user_ord) throws Exception {	

/*
 * stepCheck 정의
 * 1  : SVT 계획수립
 * 2  : SVT 결과
 * 3  : DVT 계획수립
 * 4  : DVT 결과
 * 5  : DVT 결과승인완료
 * 6  : CVT 계획수립
 * 7  : CVT 결과
 * 8  : CVT 결과승인완료
 * 9  : 초도적용 계획수립 - 메일 2개 noti/승인요청
 * 10 : 초도적용결과 당일
 * 11 : 초도적용결과 최종
 * 12 : 확대적용계획수립 - 메일 2개 noti/승인요청
 * 13 : 확대적용 결과
 * 14 : 용량검증 승인요청(151)
 * 15 : 용량검증 결과등록(151)
 * 16 : 과금검증 승인요청(161)
 * 17 : 과금검증 결과등록(151)
 * 18 : 보안검증 승인요청(171)
 * 19 : 보안검증 결과등록(151)
 * 3,4,6,7 에서만 user_ord 사용 받는사람의 담당자 순위 ( 현재 승인 하는 담당자순위 +1)
 * 
 */

	
//장비 검색 에 필요
	String htmltable ="";
	List<PkgEquipmentModel> eqmentListSum = new ArrayList<PkgEquipmentModel>();
	if(eqmentList != null && !(eqmentList.isEmpty())){	//초도 3곳 ,확대 2곳      초도 -계획/초도-결과(당일/최종) / 확대-수립/확대-결과
		eqmentListSum = eqmentList;
	
		
			if("12".equals(stepCheck) || "13".equals(stepCheck)){//확대결과
				htmltable += "<table style='width:95%;margin:0 auto;border-top:1px solid #4e78fd;'>";
				htmltable += "<tr style='height:30px;font-size:13px;color:#3c465a;line-height:18px;text-align:center;'><th style='border-bottom:1px solid #ddd;'>국사</th>";
				htmltable += "<th style='border-bottom:1px solid #ddd;'>장비명</th><th style='border-bottom:1px solid #ddd;'>서비스지역</th><th style='border-bottom:1px solid #ddd;'>적용일시</th>";
				if("13".equals(stepCheck)) {
					htmltable += "<th style='border-bottom:1px solid #ddd;'>적용결과</th>";
				}
				htmltable += "</tr>";
				
				String check = "NO";		
				for(PkgEquipmentModel eqment : eqmentListSum) {
					if(pkg21Model.getCheck_seqs_e() != null && (pkg21Model.getCheck_seqs_e().length != 0)){
//						for(String seqs : pkg21Model.getCheck_seqs_e()) {
						for (int i=0; i < pkg21Model.getCheck_seqs_e().length; i++) {
							if (eqment.getEquipment_seq().equals(pkg21Model.getCheck_seqs_e()[i] )){  // 체크된 값중이 있는지 없는지 확인-- 적용일시가 있는지 없는지 확인
								check="OK";
							}
						}
					}
					htmltable += "<tr style='height:25px;font-size:13px;line-height:18px;text-align:center;color:#666;padding:5px;'>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>	"+eqment.getTeam_name()+"		</td>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>"+eqment.getEquipment_name()+"		</td>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>	"+eqment.getService_area()+"		</td>";
					if (check.equals("OK") ){
					String eqmentDate = eqment.getAmpm() +" " ;	
							eqmentDate += eqment.getStart_date() +" "+ eqment.getStart_time1() +" : "+eqment.getStart_time2() + " ~ ";//시작일시
							eqmentDate += eqment.getEnd_date() +" "+ eqment.getEnd_time1() +" : "+eqment.getEnd_time2() + " ";//완료
						htmltable += "<td style='border-bottom:1px solid #ddd;'>"+ eqmentDate   +"	</td>";
					}else {
						htmltable += "<td style='border-bottom:1px solid #ddd;'>	확대일정 수립 필요	</td>";
					}
					
					if("13".equals(stepCheck)) {
						htmltable += "<td style='border-bottom:1px solid #ddd;'>	"+eqment.getWork_result()+"	</td>";
					}
					
					htmltable += "</tr>";
					check = "NO";
				}
				htmltable += "</table>";
				// 확대 결과 end
				
				
			}else {// 적용 결과가 있는 부분은 확대적용 결과 에만 필요
				
				htmltable += "<table style='width:95%;margin:0 auto;border-top:1px solid #4e78fd;'>";
				htmltable += "<tr style='height:30px;font-size:13px;color:#3c465a;line-height:18px;text-align:center;'><th style='border-bottom:1px solid #ddd;'>국사</th>";
				htmltable += "<th style='border-bottom:1px solid #ddd;'>장비명</th><th style='border-bottom:1px solid #ddd;'>서비스지역</th>";
				htmltable += "<th style='border-bottom:1px solid #ddd;'>적용일시</th></tr>";

				
				for(PkgEquipmentModel eqment : eqmentListSum) {						
					String check = "NO";
					if(pkg21Model.getCheck_seqs_e() != null && (pkg21Model.getCheck_seqs_e().length != 0)){
//						for(String seqs : pkg21Model.getCheck_seqs_e()) {
//							if (eqment.getEquipment_seq() == seqs ){  // 체크된 값중이 있는지 없는지 확인-- 적용일시가 있는지 없는지 확인
						for (int i=0; i < pkg21Model.getCheck_seqs_e().length; i++) {
							System.out.println("eq seq : "+ eqment.getEquipment_seq());
							System.out.println("pk21 seq : "+ pkg21Model.getCheck_seqs_e()[i]);		 
							if (eqment.getEquipment_seq().equals(pkg21Model.getCheck_seqs_e()[i] )){  // 체크된 값중이 있는지 없는지 확인-- 적용일시가 있는지 없는지 확인
								check="OK";
							}
						}
					}
					
					htmltable += "<tr style='height:25px;font-size:13px;line-height:18px;text-align:center;color:#666;padding:5px;'>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>	"+eqment.getTeam_name()+"		</td>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>"+eqment.getEquipment_name()+"		</td>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>"+eqment.getService_area()+"		</td>";
					if (check.equals("OK") ){
					String eqmentDate = eqment.getAmpm() +" " ;	
							eqmentDate += eqment.getStart_date() +" "+ eqment.getStart_time1() +" : "+eqment.getStart_time2() + " ~ ";//시작일시
							eqmentDate += eqment.getEnd_date() +" "+ eqment.getEnd_time1() +" : "+eqment.getEnd_time2() + " ";//완료
						htmltable += "<td style='border-bottom:1px solid #ddd;'>"+ eqmentDate   +"	</td>";
					}else {
						htmltable += "<td style='border-bottom:1px solid #ddd;'>	초도일정 수립 필요	</td>";
					}
					htmltable += "</tr>";
				}
				htmltable += "</table>";
				
			}// 장비 list 생성 end
		
	}//장비  검색 종료
	
	
	String content ="";					
	content += "<tr><td align='center' style='font-size:13px;' background='#f5f5f4'>";
	
	if("1".equals(stepCheck)) {// SVT계획수립
		
		String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
		String lastOne = bits[bits.length-1];
//		content += "<div class='top_con'><p>["+pkg21Model.getTitle()+"]의 SVT계획수립 등록이 완료되었습니다</p> </div>";
//		content += "<div class='top_con'><p>["+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"]의 SVT계획수립 등록이 완료되었습니다</p> </div>";
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"]의 SVT계획수립 등록이 완료되었습니다</td></tr></table>";
		
//		content += "<ul>";
		
		String ver = "";
		if("F".equals(pkg21Model.getVer_gubun())) {
			ver = "Full";
		}else if("P".equals(pkg21Model.getVer_gubun())) {
			ver = "Partial";
		}else { // C
			ver = "Patch";
		}
		
		if("Y".equals(pkg21Model.getVol_yn())) {
			ver += "/ 용량검증";
		}
		if("Y".equals(pkg21Model.getCha_yn())) {
			ver += "/ 과금검증"; 
		}
		
		String bypassEffect ="";
		if("Y".equals(pkg21Model.getBypass_traffic())) {//bypass_traffic_comment
			bypassEffect = "있음 : "+pkg21Model.getBypass_traffic_comment();
		}else {
			bypassEffect = "없음";
		}
		
		String chaEffect ="";
		if("Y".equals(pkg21Model.getPe_yn())) {//bypass_traffic_comment
			chaEffect = "있음 : "+pkg21Model.getPe_yn_comment();
		}else {
			chaEffect = "없음";
		}

		
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";   
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";   
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[시스템]</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getSystem_name()+"</td></tr>";                                    
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[제목 ]</th><td style='color:#666;padding-left:20px;'>"+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"</td></tr>";       
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>버전/버전구분</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getVer() +" / "+ ver+"</td></tr>";                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";                                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";                                   
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>서비스 중단시간</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getSer_downtime()+"</td></tr>";                             
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>로밍영향도</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getRoaming_link()+"</td></tr>";                                  
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>우회소통</th><td style='color:#666;padding-left:20px;'>"+bypassEffect+"</td></tr>";                                                   
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>과금영향도</th><td style='color:#666;padding-left:20px;'>"+chaEffect+"</td></tr>";   
		content += "</table><br>";   

		

		
	}else if("2".equals(stepCheck))	{// SVT계획결과
		String Ser_dow="";
		if("Y".equals(pkg21Model.getCol17()) ){
			Ser_dow = pkg21Model.getCol15() +" ~ "+  pkg21Model.getCol16();
		}else {
			Ser_dow = " 없음";
		}
		
	

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 SVT계획결과 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>개발기간</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1() +" ~ "+ pkg21Model.getCol2()+"</td></tr>";                   
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증 DVT</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3() +" ~ "+ pkg21Model.getCol4()+"</td></tr>";                  
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증 CVT</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol5() +" ~ "+  pkg21Model.getCol6()+"</td></tr>";                 
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증 과금검증</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol7()+ " ~ "+ pkg21Model.getCol8()+"</td></tr>";              
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증 용량시험</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol9()+ " ~ "+  pkg21Model.getCol10()+"</td></tr>";            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>적용예정기간</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol11()+ " ~ "+  pkg21Model.getCol12()+"</td></tr>";            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>IT 요소기술 Review 일정</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol13() +" ~ "+  pkg21Model.getCol14()+"</td></tr>"; 
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>Cell 참여 일정</th><td style='color:#666;padding-left:20px;'>"+Ser_dow+"</td></tr>";                                                       
		content += "</table><br>";

		
		
		
		
		
		
		
	}else if("3".equals(stepCheck))	{// DVT 검증계획-담당자에게

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 DVT 계획 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- DVT 검증계획- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>DVT 검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1() +" ~ "+ pkg21Model.getCol2()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th  colspan='2' align='center'>승인 담당자</th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>승인 차수</th><td style='color:#666;padding-left:20px;'>"+user_ord+"</td></tr>";
		content += "</table><br>";


		
	}else if("4".equals(stepCheck))	{// DVT 검증결과-담당자에게

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 DVT 결과 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- DVT 검증계획- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>DVT 검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1() +" ~ "+ pkg21Model.getCol2()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- DVT 검증결과- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>DVT 검증결과</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th  colspan='2' align='center'>승인 담당자</th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>승인 차수</th><td style='color:#666;padding-left:20px;'>"+user_ord+"</td></tr>";
		content += "</table><br>";


	}else if("5".equals(stepCheck))	{//DVT 결과승인완료

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 DVT 결과 승인이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- DVT 검증계획- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>DVT 검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1() +" ~ "+ pkg21Model.getCol2()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- DVT 검증결과- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>DVT 검증결과</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+"</td></tr>";
		content += "</table><br>";

		
	}else if("6".equals(stepCheck))	{// CVT 검증계획 등록-담당자에게


		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 CVT 계획수립 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- CVT 검증계획- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CVT 검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1() +" ~ "+ pkg21Model.getCol2()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3() +"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th  colspan='2' align='center'>승인 담당자</th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>승인 차수</th><td style='color:#666;padding-left:20px;'>"+user_ord+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getAu_comment() +"</td></tr>";
		content += "</table><br>";

		
	}else if("7".equals(stepCheck))	{//CVT 검증결과-승인담당자

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 CVT 결과 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- CVT 검증계획- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CVT 검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1() +" ~ "+ pkg21Model.getCol2()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- CVT 검증결과 </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증결과</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol11()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>과금검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol6()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>용량검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol7()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>상용적용시 유의사항</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol8()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th  colspan='2' align='center'>승인 담당자</th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>승인 차수</th><td style='color:#666;padding-left:20px;'>"+user_ord+"</td></tr>";
		content += "</table><br>";


	}else if("8".equals(stepCheck))	{//CVT 결과승인완료

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 CVT 결과 승인이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- CVT 검증계획- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CVT 검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1() +" ~ "+ pkg21Model.getCol2()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- CVT 검증결과 </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증결과</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol11()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>과금검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol6()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>용량검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol7()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>상용적용시 유의사항</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol8()+"</td></tr>";
		content += "</table><br>";
	
	}else if("9".equals(stepCheck))	{//초도적용 계획수립 - 메일 2개 noti/승인요청
		

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 초도적용계획수립 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 초도적용계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "</table><br>";
		content += " "+htmltable+" ";
		

		
	}else if("10".equals(stepCheck)){//초도적용결과 당일
		
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 초도적용 결과 [당일] 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 초도적용 결과[당일]- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>당일결과</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol2()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>최종결과 등록예정일</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+"</td></tr>";
		content += "</table><br>";


	}else if("11".equals(stepCheck)){//초도적용결과 최종
		
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 초도적용 결과 [최종] 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 초도적용 결과[최종]- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>최종결과</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol4()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol5()+"</td></tr>";
		content += "</table><br>";

		
	}else if("12".equals(stepCheck)){//확대적용계획수립 - 메일 2개 noti/승인요청
		
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 확대적용 계획수립 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 확대적용 계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "</table><br>";
		content += " "+htmltable+" ";
		
	}else if("13".equals(stepCheck)){//확대적용 결과
		
		
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 확대적용결과 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 확대적용 계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "</table><br>";
		content += " "+htmltable+" ";
		
		
		
	}else if("14".equals(stepCheck)){//용량검증 승인요청
	
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 용량검증 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 용량검증 결과- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>용량 검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+" ~ "+pkg21Model.getCol2()+"</td></tr>";	
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>용량검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+"</td></tr>";	
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 확대적용 계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "</table><br>";
	
		
		
		
		content += "<table style='width:95%;margin:0 auto;border-top:1px solid #4e78fd;'><tr style='height:30px;font-size:13px;color:#3c465a;line-height:18px;text-align:center;'>";
		content += "<th style='border-bottom:1px solid #ddd;'>번호</th><th style='border-bottom:1px solid #ddd;'>검 증 항 목</th><th style='border-bottom:1px solid #ddd;'>결과</th></tr>";
		for (Pkg21Model result : pkg21Model.getPkg21ModelList()) {			
			content += "<tr style='height:25px;font-size:13px;line-height:18px;text-align:center;color:#666;padding:5px;'>";
			content += "<td style='border-bottom:1px solid #ddd;'>"+result.getVol_no()+"</td>";
			content += "<td style='border-bottom:1px solid #ddd;'>"+result.getTitle()+"</td>";
			content += "<td style='border-bottom:1px solid #ddd;'>";
			if("1".equals(result.getChk_result())) {
				content += "OK";
			}else if("2".equals(result.getChk_result())) {
				content += "NOK";
			}else if("3".equals(result.getChk_result())) {
				content += "PASS";
			}else {
				content += "";
			}
			content += "</td>";
			content += "</tr>";
		}
		
		
		

		
	}else if("15".equals(stepCheck)){//용량검증
		
		
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 용량검증 결과등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 용량검증 결과- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>용량 검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+" ~ "+pkg21Model.getCol2()+"</td></tr>";	
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>용량검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+"</td></tr>";	
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 확대적용 계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "</table><br>";
		
		
		
		
		content += "<table style='width:95%;margin:0 auto;border-top:1px solid #4e78fd;'><tr style='height:30px;font-size:13px;color:#3c465a;line-height:18px;text-align:center;'>";
		content += "<th style='border-bottom:1px solid #ddd;'>번호</th><th style='border-bottom:1px solid #ddd;'>검 증 항 목</th><th style='border-bottom:1px solid #ddd;'>결과</th></tr>";
		for (Pkg21Model result : pkg21Model.getPkg21ModelList()) {			
			content += "<tr style='height:25px;font-size:13px;line-height:18px;text-align:center;color:#666;padding:5px;'>";
			content += "<td style='border-bottom:1px solid #ddd;'>"+result.getVol_no()+"</td>";
			content += "<td style='border-bottom:1px solid #ddd;'>"+result.getTitle()+"</td>";
			content += "<td style='border-bottom:1px solid #ddd;'>";
			if("1".equals(result.getChk_result())) {
				content += "OK";
			}else if("2".equals(result.getChk_result())) {
				content += "NOK";
			}else if("3".equals(result.getChk_result())) {
				content += "PASS";
			}else {
				content += "";
			}
			content += "</td>";
			content += "</tr>";
		}

		
	}else if("16".equals(stepCheck)){//과금검증 승인요청

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 과금검증 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 과금검증 결과- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>과금 검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+" ~ "+pkg21Model.getCol2()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CVT 과금검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>과금검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol5()+"</td></tr>";
		content += "</table><br>";

		
		
	}else if("17".equals(stepCheck)){//과금검증
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의  과금검증 결과등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 과금검증 결과- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>과금 검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+" ~ "+pkg21Model.getCol2()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CVT 과금검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>과금검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol5()+"</td></tr>";
		content += "</table><br>";
		
		if(pkg21Model.getPkgEquipmentModelList() != null && !(pkg21Model.getPkgEquipmentModelList().isEmpty())) {
			

			content += "<table style='width:95%;margin:0 auto;border-top:1px solid #4e78fd;'><tr style='height:30px;font-size:13px;color:#3c465a;line-height:18px;text-align:center;'>";
			content += "<th style='border-bottom:1px solid #ddd;'>국사</th><th style='border-bottom:1px solid #ddd;'>장비명</th>";
			content += "<th style='border-bottom:1px solid #ddd;'>서비스지역</th><th style='border-bottom:1px solid #ddd;'>적용일시</th>";
			content += "<th style='border-bottom:1px solid #ddd;'>적용결과</th><th style='border-bottom:1px solid #ddd;'>확인</th></tr>";
			
			for (int i = 0; i < pkg21Model.getPkgEquipmentModelList().size(); i++) {
				content += "<tr style='height:25px;font-size:13px;line-height:18px;text-align:center;color:#666;padding:5px;'>";
				content += "<td style='border-bottom:1px solid #ddd;'>"+pkg21Model.getPkgEquipmentModelList().get(i).getTeam_name() +"</td>";
				content += "<td style='border-bottom:1px solid #ddd;'>"+pkg21Model.getPkgEquipmentModelList().get(i).getEquipment_name() +"</td>";
				content += "<td style='border-bottom:1px solid #ddd;'>"+pkg21Model.getPkgEquipmentModelList().get(i).getService_area() +"</td>";
				
				if(pkg21Model.getPkgEquipmentModelList().get(i).getStart_date().isEmpty()) {
					content += "<td style='border-bottom:1px solid #ddd;'>	확대일정 미수립 </td>";
				}else {
					content += "<td style='border-bottom:1px solid #ddd;'>";
					content += pkg21Model.getPkgEquipmentModelList().get(i).getStart_date() +" "+pkg21Model.getPkgEquipmentModelList().get(i).getStart_time1() +" : "+pkg21Model.getPkgEquipmentModelList().get(i).getStart_time2() +" ~  ";
					content += pkg21Model.getPkgEquipmentModelList().get(i).getEnd_date()+" "+pkg21Model.getPkgEquipmentModelList().get(i).getEnd_time1() +" : "+pkg21Model.getPkgEquipmentModelList().get(i).getEnd_time2() +"</td>";
				}
				content += "<td style='border-bottom:1px solid #ddd;'>"+pkg21Model.getPkgEquipmentModelList().get(i).getCharge_result() +"</td>";
				content += "<td style='border-bottom:1px solid #ddd;'>"+pkg21Model.getPkgEquipmentModelList().get(i).getReg_user_name() +"</td>";
				content += "</tr>";
			}			
			content += "</table></div>";
			
		}
		
		
	}else if("18".equals(stepCheck)){//보안검증 승인요청
		
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의  보안검증 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 보안검증 결과- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>보안검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+"</td></tr>";
		content += "</table><br>";
		
		
		
	}else if("19".equals(stepCheck)){//보안검증
		
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의  보안검증 결과등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 보안검증 결과- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>보안검증 Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+"</td></tr>";
		content += "</table><br>";
		
		
		
		
		
	}else {
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>내용이 없습니다.</td></tr></table>";
	}
	

//	content += "</div></td></tr>";
	content += "</td></tr></table></td></tr>";
	

	
	return content; // 내용	
		
}
	
	

public void newSendSMS(String smsMsgValue,String[] phone) throws Exception {	

	String [] userPhone = this.cleanStr(phone);//중복제거

	for(String phone_number : userPhone){
		if(phone_number != null && (!phone_number.isEmpty())) {
		this.sendSms( smsMsgValue, phone_number);//sendSMS
		}
	}
}



public String [] cleanStr(String [] str) throws Exception {
	// 중복사용자 제거
	ArrayList<String> uniqEmailAl = new ArrayList<String>();
	for (String clean_str : str) { //받을 사람 수만큼
		if (uniqEmailAl.contains(clean_str)) continue; //리스트 안의 중복자 제거 contains
		uniqEmailAl.add(clean_str); //중보 제거된 값 리스트에 생성
	}
	str = (String[]) uniqEmailAl.toArray(new String[uniqEmailAl.size()]); //Array 값 -> String 으로 변환
	uniqEmailAl.clear();
	uniqEmailAl = null;
	
	return str;
}



@Override
public String makeSmsMsg(Pkg21Model pkg21Model,String stepCheck) throws Exception {

	String url ="http://pkmss.sktelecom.com/M.do";
	String msg ="[PKMS] ";
	
	if("1".equals(stepCheck)) {// SVT계획수립
		String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
		String lastOne = bits[bits.length-1];
//		msg += lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+" SVT계획수립 등록 완료";
		
		msg += "SVT계획수립 등록 "+url+" ("+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+")";
	}else if("2".equals(stepCheck))	{// SVT계획결과
//		msg += pkg21Model.getTitle()+" SVT계획결과 등록 완료";
		msg += "SVT계획결과 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("3".equals(stepCheck))	{// DVT 검증계획-담당자에게
//		msg += pkg21Model.getTitle()+" DVT 계획 등록 완료";
		msg += "DVT 계획 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("4".equals(stepCheck))	{// DVT 검증결과-담당자에게
//		msg += pkg21Model.getTitle()+" DVT 결과 등록 완료";
		msg += "DVT 결과 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("5".equals(stepCheck))	{//DVT 결과승인완료
//		msg += pkg21Model.getTitle()+" DVT 결과 승인 완료";
		msg += "DVT 결과 승인 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("6".equals(stepCheck))	{// CVT 검증계획 등록-담당자에게
//		msg += pkg21Model.getTitle()+" CVT 계획수립 등록 완료";
		msg += "CVT 계획수립 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("7".equals(stepCheck))	{//CVT 검증결과-승인담당자
//		msg += pkg21Model.getTitle()+" CVT 결과 등록 완료";
		msg += "CVT 결과 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("8".equals(stepCheck))	{//CVT 결과승인완료
//		msg += pkg21Model.getTitle()+" CVT 결과 승인 완료";
		msg += "CVT 결과 승인 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("9".equals(stepCheck))	{//초도적용 계획수립 - 메일 2개 noti/승인요청					 
//		msg += pkg21Model.getTitle()+" 초도적용계획수립 등록 완료";
		msg += "초도적용계획수립 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("10".equals(stepCheck)){//초도적용결과 당일
//		msg += pkg21Model.getTitle()+" 초도적용 결과 [당일] 등록 완료";
		msg += "초도적용 결과 [당일] 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("11".equals(stepCheck)){//초도적용결과 최종
//		msg += pkg21Model.getTitle()+" 초도적용 결과 [최종] 등록 완료";
		msg += "초도적용 결과 [최종] 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("12".equals(stepCheck)){//확대적용계획수립 - 메일 2개 noti/승인요청
//		msg += pkg21Model.getTitle()+" 확대적용 계획수립 등록 완료";
		msg += "확대적용 계획수립 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("13".equals(stepCheck)){//확대적용 결과
//		msg += pkg21Model.getTitle()+" 확대적용결과 등록 완료";
		msg += "확대적용결과 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("14".equals(stepCheck)){//용량검증 승인요청
//		msg += pkg21Model.getTitle()+" 용량검증 등록 완료";
		msg += "용량검증 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("15".equals(stepCheck)){//용량검증
//		msg += pkg21Model.getTitle()+"의 용량검증 결과등록 완료";
		msg += "용량검증 결과등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("16".equals(stepCheck)){//과금검증 승인요청
//		msg += pkg21Model.getTitle()+"의 과금검증 등록 완료";
		msg += "과금검증 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("17".equals(stepCheck)){//과금검증
//		msg += pkg21Model.getTitle()+" 과금검증 결과등록 완료";
		msg += "과금검증 결과등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("18".equals(stepCheck)){//보안검증 승인요청
//		msg += pkg21Model.getTitle()+" 보안검증 등록 완료";
		msg += "보안검증 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("19".equals(stepCheck)){//보안검증
//		msg += pkg21Model.getTitle()+" 보안검증 결과등록 완료";
		msg += "보안검증 결과등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else {
		msg += "내용이 없습니다.";
	}	
	
//	msg += " http://pkmss.sktelecom.com/M.do";
//	msg += "[TTITLE] "+pkg21Model.getSystem_name()+"";
	return msg;
}






























@Override
public String genrerateStringWired(List<PkgEquipmentModel> eqmentList ,Pkg21Model pkg21Model , String stepCheck ,String user_ord) throws Exception {	

/*
 * stepCheck 정의
 * 1  : pkg개발 결과 
 * 2  : 검증계획 승인요청
 * 3  : 검증결과 승인요청
 * 4  : 검증결과 완료
 * 5  : 초도계획 계획수립 - 메일 2개 noti/승인요청
 * 6  : 초도계획 승인요청
 * 7  : 초도적용결과
 * 8  : 확대적용계획수립 - 메일 2개 noti/승인요청
 * 9  : 확대적용계획수립  승인요청
 * 10 : 확대적용 결과
 * 2,3,6,9 에서만 user_ord 사용 받는사람의 담당자 순위 ( 현재 승인 하는 담당자순위 +1)
 * 
 * 
 *  #############100대 부터 반려
 * 101 : 검증계획 단계에서 반려 -> pkg 개발결과 등록    
 * 102 : 검증계획 승인 에서 반려 -> pkg 개발결과 상태      / 등록자에게 전달, 2차승인자일 경우 1차 승인 자 에게도 전달 
 * 103 : 검증결과 승인 에서 반려 -> 검증계획 승인 상태   / 요청자에게 전달 ,  2차승인자일 경우 1차 승인 자 에게도 전달
 * 104 : 초도적용계획 승인 에서 반려 -> 검증결과 승인 상태   / 요청자에게 전달 ,  2차승인자일 경우 1차 승인 자 에게도 전달
 * 105 : 확대적용계획 승인 에서 반려 -> 검증결과 승인 상태   / 요청자에게 전달 ,  2차승인자일 경우 1차 승인 자 에게도 전달
 */

	
//장비 검색 에 필요
	String htmltable ="";
	List<PkgEquipmentModel> eqmentListSum = new ArrayList<PkgEquipmentModel>();
	if(eqmentList != null && !(eqmentList.isEmpty())){	//초도 3곳 ,확대 2곳      초도 -계획/초도-결과(당일/최종) / 확대-수립/확대-결과
		eqmentListSum = eqmentList;
	
		
			if("8".equals(stepCheck) || "9".equals(stepCheck) || "10".equals(stepCheck) || "105".equals(stepCheck)){//확대결과
				htmltable += "<table style='width:95%;margin:0 auto;border-top:1px solid #4e78fd;'>";
				htmltable += "<tr style='height:30px;font-size:13px;color:#3c465a;line-height:18px;text-align:center;'><th style='border-bottom:1px solid #ddd;'>국사</th>";
				htmltable += "<th style='border-bottom:1px solid #ddd;'>장비명</th><th style='border-bottom:1px solid #ddd;'>서비스지역</th><th style='border-bottom:1px solid #ddd;'>적용일시</th>";
				if("10".equals(stepCheck)) {
					htmltable += "<th style='border-bottom:1px solid #ddd;'>적용결과</th>";
				}
				htmltable += "</tr>";
				
				String check = "NO";		
				for(PkgEquipmentModel eqment : eqmentListSum) {
					if(pkg21Model.getCheck_seqs_e() != null && (pkg21Model.getCheck_seqs_e().length != 0)){
//						for(String seqs : pkg21Model.getCheck_seqs_e()) {
						for (int i=0; i < pkg21Model.getCheck_seqs_e().length; i++) {
							if (eqment.getEquipment_seq().equals(pkg21Model.getCheck_seqs_e()[i] )){  // 체크된 값중이 있는지 없는지 확인-- 적용일시가 있는지 없는지 확인
								check="OK";
							}
						}
					}
					htmltable += "<tr style='height:25px;font-size:13px;line-height:18px;text-align:center;color:#666;padding:5px;'>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>	"+eqment.getTeam_name()+"		</td>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>"+eqment.getEquipment_name()+"		</td>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>	"+eqment.getService_area()+"		</td>";
					if (check.equals("OK") ){
					String eqmentDate = eqment.getAmpm() +" " ;	
							eqmentDate += eqment.getStart_date() +" "+ eqment.getStart_time1() +" : "+eqment.getStart_time2() + " ~ ";//시작일시
							eqmentDate += eqment.getEnd_date() +" "+ eqment.getEnd_time1() +" : "+eqment.getEnd_time2() + " ";//완료
						htmltable += "<td style='border-bottom:1px solid #ddd;'>"+ eqmentDate   +"	</td>";
					}else {
						htmltable += "<td style='border-bottom:1px solid #ddd;'>	확대일정 수립 필요	</td>";
					}
					
					if("10".equals(stepCheck)) {
						htmltable += "<td style='border-bottom:1px solid #ddd;'>	"+eqment.getWork_result()+"	</td>";
					}
					
					htmltable += "</tr>";
					check = "NO";
				}
				htmltable += "</table>";
				// 확대 결과 end
				
				
			}else {// 적용 결과가 있는 부분은 확대적용 결과 에만 필요
				
				htmltable += "<table style='width:95%;margin:0 auto;border-top:1px solid #4e78fd;'>";
				htmltable += "<tr style='height:30px;font-size:13px;color:#3c465a;line-height:18px;text-align:center;'><th style='border-bottom:1px solid #ddd;'>국사</th>";
				htmltable += "<th style='border-bottom:1px solid #ddd;'>장비명</th><th style='border-bottom:1px solid #ddd;'>서비스지역</th>";
				htmltable += "<th style='border-bottom:1px solid #ddd;'>적용일시</th></tr>";

				
				for(PkgEquipmentModel eqment : eqmentListSum) {						
					String check = "NO";
					if(pkg21Model.getCheck_seqs_e() != null && (pkg21Model.getCheck_seqs_e().length != 0)){
//						for(String seqs : pkg21Model.getCheck_seqs_e()) {
//							if (eqment.getEquipment_seq() == seqs ){  // 체크된 값중이 있는지 없는지 확인-- 적용일시가 있는지 없는지 확인
						for (int i=0; i < pkg21Model.getCheck_seqs_e().length; i++) {
							System.out.println("eq seq : "+ eqment.getEquipment_seq());
							System.out.println("pk21 seq : "+ pkg21Model.getCheck_seqs_e()[i]);		 
							if (eqment.getEquipment_seq().equals(pkg21Model.getCheck_seqs_e()[i] )){  // 체크된 값중이 있는지 없는지 확인-- 적용일시가 있는지 없는지 확인
								check="OK";
							}
						}
					}
					
					htmltable += "<tr style='height:25px;font-size:13px;line-height:18px;text-align:center;color:#666;padding:5px;'>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>	"+eqment.getTeam_name()+"		</td>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>"+eqment.getEquipment_name()+"		</td>";
					htmltable += "<td style='border-bottom:1px solid #ddd;'>"+eqment.getService_area()+"		</td>";
					if (check.equals("OK") ){
					String eqmentDate = eqment.getAmpm() +" " ;	
							eqmentDate += eqment.getStart_date() +" "+ eqment.getStart_time1() +" : "+eqment.getStart_time2() + " ~ ";//시작일시
							eqmentDate += eqment.getEnd_date() +" "+ eqment.getEnd_time1() +" : "+eqment.getEnd_time2() + " ";//완료
						htmltable += "<td style='border-bottom:1px solid #ddd;'>"+ eqmentDate   +"	</td>";
					}else {
						htmltable += "<td style='border-bottom:1px solid #ddd;'>	초도일정 수립 필요	</td>";
					}
					htmltable += "</tr>";
				}
				htmltable += "</table>";
				
			}// 장비 list 생성 end
		
	}//장비  검색 종료
	
	
	
	
	
	
	//현재 상태 검색 model
	Pkg21Model p21Model = new Pkg21Model ();
	Pkg21Model search = new Pkg21Model ();

	
	String content ="";					
	content += "<tr><td align='center' style='font-size:13px;' background='#f5f5f4'>";
	
	if("1".equals(stepCheck)) {// 1번 멜 pkg 개발 결과 등록
		
		String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
		String lastOne = bits[bits.length-1];
//		content += "<div class='top_con'><p>["+pkg21Model.getTitle()+"]의 SVT계획수립 등록이 완료되었습니다</p> </div>";
//		content += "<div class='top_con'><p>["+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"]의 SVT계획수립 등록이 완료되었습니다</p> </div>";
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"]의 SVT계획수립 등록이 완료되었습니다</td></tr></table>";
		
//		content += "<ul>";
		
		String ver = "";
		if("F".equals(pkg21Model.getVer_gubun())) {
			ver = "Full";
		}else if("P".equals(pkg21Model.getVer_gubun())) {
			ver = "Partial";
		}else { // C
			ver = "Patch";
		}
		
		if("Y".equals(pkg21Model.getVol_yn())) {
			ver += "/ 용량검증";
		}
		if("Y".equals(pkg21Model.getCha_yn())) {
			ver += "/ 과금검증"; 
		}
		
		String bypassEffect ="";
		if("Y".equals(pkg21Model.getBypass_traffic())) {//bypass_traffic_comment
			bypassEffect = "있음 : "+pkg21Model.getBypass_traffic_comment();
		}else {
			bypassEffect = "없음";
		}
		
		String chaEffect ="";
		if("Y".equals(pkg21Model.getPe_yn())) {//bypass_traffic_comment
			chaEffect = "있음 : "+pkg21Model.getPe_yn_comment();
		}else {
			chaEffect = "없음";
		}

		
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";   
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";   
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[시스템]</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getSystem_name()+"</td></tr>";                                    
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[제목 ]</th><td style='color:#666;padding-left:20px;'>"+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"</td></tr>";       
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>버전/버전구분</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getVer() +" / "+ ver+"</td></tr>";                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";                                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체개선</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";                                   
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>서비스 중단시간</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getSer_downtime()+"</td></tr>";                             
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>로밍영향도</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getRoaming_link()+"</td></tr>";                                  
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>우회소통</th><td style='color:#666;padding-left:20px;'>"+bypassEffect+"</td></tr>";                                                   
//		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>과금영향도</th><td style='color:#666;padding-left:20px;'>"+chaEffect+"</td></tr>";   

		
//		301검색
//		search.setPkg_seq(pkg21Model.getPkg_seq());
//		search.setSelect_status("301");
//		p21Model = wiredStatusService.read(pkg21Model);
			
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>개발기간</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+" - "+pkg21Model.getCol2()+"</td></tr>"; 
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증기간</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+" - "+pkg21Model.getCol4()+"</td></tr>"; 
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>적용예정기간</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol5()+" - "+pkg21Model.getCol6()+"</td></tr>"; 
		
		content += "</table><br>";   

		

		
	}else if("2".equals(stepCheck))	{// 검증계획 승인 요청
		
		
		String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
		String lastOne = bits[bits.length-1];
		
		String ver = "";
		if("F".equals(pkg21Model.getVer_gubun())) {
			ver = "Full";
		}else if("P".equals(pkg21Model.getVer_gubun())) {
			ver = "Partial";
		}else { // C
			ver = "Patch";
		}
		
		if("Y".equals(pkg21Model.getVol_yn())) {
			ver += "/ 용량검증";
		}
		if("Y".equals(pkg21Model.getCha_yn())) {
			ver += "/ 과금검증"; 
		}
		
		String bypassEffect ="";
		if("Y".equals(pkg21Model.getBypass_traffic())) {//bypass_traffic_comment
			bypassEffect = "있음 : "+pkg21Model.getBypass_traffic_comment();
		}else {
			bypassEffect = "없음";
		}
		
		String chaEffect ="";
		if("Y".equals(pkg21Model.getPe_yn())) {//bypass_traffic_comment
			chaEffect = "있음 : "+pkg21Model.getPe_yn_comment();
		}else {
			chaEffect = "없음";
		}
		
		
		
		
		
		
		String Ser_dow="";
		if("Y".equals(pkg21Model.getCol17()) ){
			Ser_dow = pkg21Model.getCol15() +" ~ "+  pkg21Model.getCol16();
		}else {
			Ser_dow = " 없음";
		}
		
	

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 검증계획 등록이 승인 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[시스템]</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getSystem_name()+"</td></tr>";                                    
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[제목 ]</th><td style='color:#666;padding-left:20px;'>"+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"</td></tr>";       

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>버전/버전구분</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getVer() +" / "+ ver+"</td></tr>";                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";                                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체개선</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";                                   
		
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 검증계획- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+" ~ "+pkg21Model.getCol2()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'> Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th  colspan='2' align='center'>승인 담당자</th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>승인 차수</th><td style='color:#666;padding-left:20px;'>"+user_ord+"</td></tr>";
		content += "</table><br>";
		
		
		
	}else if("3".equals(stepCheck))	{// 검증결과 승인요청
		
		String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
		String lastOne = bits[bits.length-1];
		
		String ver = "";
		if("F".equals(pkg21Model.getVer_gubun())) {
			ver = "Full";
		}else if("P".equals(pkg21Model.getVer_gubun())) {
			ver = "Partial";
		}else { // C
			ver = "Patch";
		}
		
		if("Y".equals(pkg21Model.getVol_yn())) {
			ver += "/ 용량검증";
		}
		if("Y".equals(pkg21Model.getCha_yn())) {
			ver += "/ 과금검증"; 
		}
		
		String bypassEffect ="";
		if("Y".equals(pkg21Model.getBypass_traffic())) {//bypass_traffic_comment
			bypassEffect = "있음 : "+pkg21Model.getBypass_traffic_comment();
		}else {
			bypassEffect = "없음";
		}
		
		String chaEffect ="";
		if("Y".equals(pkg21Model.getPe_yn())) {//bypass_traffic_comment
			chaEffect = "있음 : "+pkg21Model.getPe_yn_comment();
		}else {
			chaEffect = "없음";
		}
		
		
		String Ser_dow="";
		if("Y".equals(pkg21Model.getCol17()) ){
			Ser_dow = pkg21Model.getCol15() +" ~ "+  pkg21Model.getCol16();
		}else {
			Ser_dow = " 없음";
		}
	

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 검증계획 결과 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[시스템]</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getSystem_name()+"</td></tr>";                                    
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[제목 ]</th><td style='color:#666;padding-left:20px;'>"+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"</td></tr>";       

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>버전/버전구분</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getVer() +" / "+ ver+"</td></tr>";                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";                                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체개선</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";                                   
		
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 검증계획- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+" ~ "+pkg21Model.getCol2()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'> Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+"</td></tr>";
		
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 검증결과- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol4()+" ~ "+pkg21Model.getCol5()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증 결과</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol11()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>상용적용시 유의사항</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol6()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th  colspan='2' align='center'>승인 담당자</th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>승인 차수</th><td style='color:#666;padding-left:20px;'>"+user_ord+"</td></tr>";
		
		content += "</table><br>";

				
		
		
	}else if("4".equals(stepCheck))	{// 검증결결과 완료
		String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
		String lastOne = bits[bits.length-1];
		
		String ver = "";
		if("F".equals(pkg21Model.getVer_gubun())) {
			ver = "Full";
		}else if("P".equals(pkg21Model.getVer_gubun())) {
			ver = "Partial";
		}else { // C
			ver = "Patch";
		}
		
		if("Y".equals(pkg21Model.getVol_yn())) {
			ver += "/ 용량검증";
		}
		if("Y".equals(pkg21Model.getCha_yn())) {
			ver += "/ 과금검증"; 
		}
		
		String bypassEffect ="";
		if("Y".equals(pkg21Model.getBypass_traffic())) {//bypass_traffic_comment
			bypassEffect = "있음 : "+pkg21Model.getBypass_traffic_comment();
		}else {
			bypassEffect = "없음";
		}
		
		String chaEffect ="";
		if("Y".equals(pkg21Model.getPe_yn())) {//bypass_traffic_comment
			chaEffect = "있음 : "+pkg21Model.getPe_yn_comment();
		}else {
			chaEffect = "없음";
		}
		
		
		String Ser_dow="";
		if("Y".equals(pkg21Model.getCol17()) ){
			Ser_dow = pkg21Model.getCol15() +" ~ "+  pkg21Model.getCol16();
		}else {
			Ser_dow = " 없음";
		}
	

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 검증계획 결과 등록이 승인 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[시스템]</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getSystem_name()+"</td></tr>";                                    
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[제목 ]</th><td style='color:#666;padding-left:20px;'>"+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"</td></tr>";       

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>버전/버전구분</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getVer() +" / "+ ver+"</td></tr>";                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";                                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체개선</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";                                   
		
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 검증계획- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+" ~ "+pkg21Model.getCol2()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'> Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+"</td></tr>";
		
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 검증결과- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증 일자</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol4()+" ~ "+pkg21Model.getCol5()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>검증 결과</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol11()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>상용적용시 유의사항</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol6()+"</td></tr>";

		
		content += "</table><br>";
		
		
	}else if("5".equals(stepCheck))	{// 초도 적용 계획 수립
		
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 초도적용계획수립 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 초도적용계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "</table><br>";
		content += " "+htmltable+" ";
		
	}else if("6".equals(stepCheck))	{// 초도 적용 계획 승인
		
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 초도적용계획수립 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 초도적용계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th  colspan='2' align='center'>승인 담당자</th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>승인 차수</th><td style='color:#666;padding-left:20px;'>"+user_ord+"</td></tr>";
		content += "</table><br>";
		content += " "+htmltable+" ";
		
		
		

	}else if("7".equals(stepCheck))	{// 초도 적용 결과
		
		

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 초도적용 결과 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";

		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 초도적용 결과- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>결과</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol1()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>Comment</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol2()+"</td></tr>";
//		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>최종결과 등록예정일</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getCol3()+"</td></tr>";
		content += "</table><br>";
		
		
		
	}else if("8".equals(stepCheck))	{// 확대적용 계획 수립
		

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 확대적용 계획수립 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 확대적용 계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "</table><br>";
		content += " "+htmltable+" ";
		
	}else if("9".equals(stepCheck))	{// 확대적용 계획 승인요청
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 확대적용 계획수립 등록 승인 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 확대적용 계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th  colspan='2' align='center'>승인 담당자</th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>승인 차수</th><td style='color:#666;padding-left:20px;'>"+user_ord+"</td></tr>";
		content += "</table><br>";
		content += " "+htmltable+" ";
		
		
	}else if("10".equals(stepCheck))	{// 확대적용 결과
		
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 확대적용결과 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 확대적용 계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "</table><br>";
		content += " "+htmltable+" ";
		
		
		
		
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
////////////반려 100번대	
	}else if("101".equals(stepCheck))	{// 검증계획 단계에서 반려 -> pkg 개발결과 등록    
		
		
		String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
		String lastOne = bits[bits.length-1];
		
		String ver = "";
		if("F".equals(pkg21Model.getVer_gubun())) {
			ver = "Full";
		}else if("P".equals(pkg21Model.getVer_gubun())) {
			ver = "Partial";
		}else { // C
			ver = "Patch";
		}
		
		if("Y".equals(pkg21Model.getVol_yn())) {
			ver += "/ 용량검증";
		}
		if("Y".equals(pkg21Model.getCha_yn())) {
			ver += "/ 과금검증"; 
		}
		
		String bypassEffect ="";
		if("Y".equals(pkg21Model.getBypass_traffic())) {//bypass_traffic_comment
			bypassEffect = "있음 : "+pkg21Model.getBypass_traffic_comment();
		}else {
			bypassEffect = "없음";
		}
		
		String chaEffect ="";
		if("Y".equals(pkg21Model.getPe_yn())) {//bypass_traffic_comment
			chaEffect = "있음 : "+pkg21Model.getPe_yn_comment();
		}else {
			chaEffect = "없음";
		}
		
		
		String Ser_dow="";
		if("Y".equals(pkg21Model.getCol17()) ){
			Ser_dow = pkg21Model.getCol15() +" ~ "+  pkg21Model.getCol16();
		}else {
			Ser_dow = " 없음";
		}
		
	

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 SVT계획결과 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[시스템]</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getSystem_name()+"</td></tr>";                                    
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[제목 ]</th><td style='color:#666;padding-left:20px;'>"+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"</td></tr>";       

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>버전/버전구분</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getVer() +" / "+ ver+"</td></tr>";                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";                                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체개선</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";                                   
		
		content += "</table><br>";
		
	}else if("102".equals(stepCheck))	{// 검증계획 승인 에서 반려 -> pkg 개발결과 상태      / 등록자에게 전달, 2차승인자일 경우 1차 승인 자 에게도 전달 
		
		String reject_comment = pkg21Model.getCol14();//반려이유
		
		String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
		String lastOne = bits[bits.length-1];
		
		String ver = "";
		if("F".equals(pkg21Model.getVer_gubun())) {
			ver = "Full";
		}else if("P".equals(pkg21Model.getVer_gubun())) {
			ver = "Partial";
		}else { // C
			ver = "Patch";
		}
		
		if("Y".equals(pkg21Model.getVol_yn())) {
			ver += "/ 용량검증";
		}
		if("Y".equals(pkg21Model.getCha_yn())) {
			ver += "/ 과금검증"; 
		}
		
		String bypassEffect ="";
		if("Y".equals(pkg21Model.getBypass_traffic())) {//bypass_traffic_comment
			bypassEffect = "있음 : "+pkg21Model.getBypass_traffic_comment();
		}else {
			bypassEffect = "없음";
		}
		
		String chaEffect ="";
		if("Y".equals(pkg21Model.getPe_yn())) {//bypass_traffic_comment
			chaEffect = "있음 : "+pkg21Model.getPe_yn_comment();
		}else {
			chaEffect = "없음";
		}
		
		
		String Ser_dow="";
		if("Y".equals(pkg21Model.getCol17()) ){
			Ser_dow = pkg21Model.getCol15() +" ~ "+  pkg21Model.getCol16();
		}else {
			Ser_dow = " 없음";
		}
		
	

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 SVT계획결과 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[시스템]</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getSystem_name()+"</td></tr>";                                    
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[제목 ]</th><td style='color:#666;padding-left:20px;'>"+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"</td></tr>";       

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>버전/버전구분</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getVer() +" / "+ ver+"</td></tr>";                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";                                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체개선</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";                                   
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>-------- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>반려이유</th><td style='color:#666;padding-left:20px;'>"+reject_comment+"</td></tr>";                                   
		
		content += "</table><br>";
		
			
}else if("103".equals(stepCheck))	{// 검증결과 승인 에서 반려 -> 검증계획 승인 상태   / 요청자에게 전달 ,  2차승인자일 경우 1차 승인 자 에게도 전달
		
		String reject_comment = pkg21Model.getCol15();//반려이유
		
		String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
		String lastOne = bits[bits.length-1];
		
		String ver = "";
		if("F".equals(pkg21Model.getVer_gubun())) {
			ver = "Full";
		}else if("P".equals(pkg21Model.getVer_gubun())) {
			ver = "Partial";
		}else { // C
			ver = "Patch";
		}
		
		if("Y".equals(pkg21Model.getVol_yn())) {
			ver += "/ 용량검증";
		}
		if("Y".equals(pkg21Model.getCha_yn())) {
			ver += "/ 과금검증"; 
		}
		
		String bypassEffect ="";
		if("Y".equals(pkg21Model.getBypass_traffic())) {//bypass_traffic_comment
			bypassEffect = "있음 : "+pkg21Model.getBypass_traffic_comment();
		}else {
			bypassEffect = "없음";
		}
		
		String chaEffect ="";
		if("Y".equals(pkg21Model.getPe_yn())) {//bypass_traffic_comment
			chaEffect = "있음 : "+pkg21Model.getPe_yn_comment();
		}else {
			chaEffect = "없음";
		}
		
		
		String Ser_dow="";
		if("Y".equals(pkg21Model.getCol17()) ){
			Ser_dow = pkg21Model.getCol15() +" ~ "+  pkg21Model.getCol16();
		}else {
			Ser_dow = " 없음";
		}
		
	

		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 SVT계획결과 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[시스템]</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getSystem_name()+"</td></tr>";                                    
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>[제목 ]</th><td style='color:#666;padding-left:20px;'>"+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+"</td></tr>";       

		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>버전/버전구분</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getVer() +" / "+ ver+"</td></tr>";                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";                                            
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";                                          
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체개선</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";                                   
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>-------- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>반려이유</th><td style='color:#666;padding-left:20px;'>"+reject_comment+"</td></tr>";                                   

		content += "</table><br>";
		
					
		
}else if("104".equals(stepCheck))	{// 초도적용계획 승인 에서 반려 -> 검증결과 승인 상태   / 요청자에게 전달 ,  2차승인자일 경우 1차 승인 자 에게도 전달
	
	String reject_comment = pkg21Model.getCol14();//반려이유
	
	content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 초도적용계획수립 등록이 완료되었습니다</td></tr></table>";
	content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
	content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
	content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
	content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
	content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
	content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
	content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
	
	content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>-------- </th><td style='color:#666;padding-left:20px;'></td></tr>";
	content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>반려이유</th><td style='color:#666;padding-left:20px;'>"+reject_comment+"</td></tr>";                                   

	
	content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 초도적용계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";
	content += "</table><br>";
	content += " "+htmltable+" ";
	
		
	
	}else if("105".equals(stepCheck))	{// 확대적용계획 승인 에서 반려 -> 초도적용결과 승인 상태   / 요청자에게 전달 ,  2차승인자일 경우 1차 승인 자 에게도 전달
		String reject_comment = pkg21Model.getCol14();//반려이유
		
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>["+pkg21Model.getTitle()+"]의 확대적용 계획수립 등록이 완료되었습니다</td></tr></table>";
		content += "<table style='width:90%;background:#fff;padding:20px 0;margin:0 auto;border:1px solid #bbb;'><tr background='#fff'><td align='center'>";
		content += "<table style='width:90%;'><colgroup><col width='40%'><col width='60%'></colgroup>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- PKG  요약- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>신규</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>PN</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_pn()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>CR</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_cr()+"</td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>자체보완</th><td style='color:#666;padding-left:20px;'>"+pkg21Model.getContent_self()+"</td></tr>";
		
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>-------- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th align='right'>반려이유</th><td style='color:#666;padding-left:20px;'>"+reject_comment+"</td></tr>";                                   
				
		content += "<tr style='font-size:13px;color:#3c465a;line-height:15px;'><th colspan='2' align='center'>- 확대적용 계획수립- </th><td style='color:#666;padding-left:20px;'></td></tr>";
		
		content += "</table><br>";
		content += " "+htmltable+" ";
		
	
		
				
		
	}else {
		content += "<table style='width:90%;margin:0 auto;color:#666;padding:20px 10px 30px;'><tr style='font-size:13px;'><td align='center'>내용이 없습니다.</td></tr></table>";
	}
	

//	content += "</div></td></tr>";
	content += "</td></tr></table></td></tr>";
	

	
	return content; // 내용	
		
}
	


@Override
public String makeSmsMsgWired(Pkg21Model pkg21Model,String stepCheck) throws Exception {  //유선 

	String url ="http://pkmss.sktelecom.com/M.do";
	String msg ="[PKMS] ";
	
	if("1".equals(stepCheck)) {// PKG개발결과
		String[] bits = ((String)pkg21Model.getSystem_name()).split("＞");
		String lastOne = bits[bits.length-1];
//		msg += lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+" SVT계획수립 등록 완료";
		
		msg += "유선 PKG개발결과 등록 "+url+" ("+lastOne+"_"+pkg21Model.getVer()+"_"+pkg21Model.getTitle()+")";
	}else if("2".equals(stepCheck))	{// 개발검증 계획수립 
//		msg += pkg21Model.getTitle()+" 승인요청";
		msg += "유선 검증 계획 수립 "+url+" ("+pkg21Model.getTitle()+")";

	}else if("3".equals(stepCheck))	{//계획 결과저장 및 승인요청
//		msg += pkg21Model.getTitle()+" 승인요청";
		msg += "유선 검증 결과 등록  "+url+" ("+pkg21Model.getTitle()+")";
	}else if("4".equals(stepCheck))	{// 검증결과 승인완료
//		msg += pkg21Model.getTitle()+" 승인완료";
		msg += "유선 개발 검증 결과 승인 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("5".equals(stepCheck))	{//DVT 결과승인완료
//		msg += pkg21Model.getTitle()+" DVT 결과 승인 완료";
		msg += "유선 초도적용계획수립 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("6".equals(stepCheck))	{// CVT 검증계획 등록-담당자에게
//		msg += pkg21Model.getTitle()+" CVT 계획수립 등록 완료";
		msg += "유선 초도적용계획 승인 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("7".equals(stepCheck))	{//CVT 검증결과-승인담당자
//		msg += pkg21Model.getTitle()+" CVT 결과 등록 완료";
		msg += "유선 초도적용결과 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("8".equals(stepCheck))	{//CVT 결과승인완료
//		msg += pkg21Model.getTitle()+" CVT 결과 승인 완료";
		msg += "유선 확대적용 계획 수립 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("9".equals(stepCheck))	{//초도적용 계획수립 - 메일 2개 noti/승인요청					 
//		msg += pkg21Model.getTitle()+" 초도적용계획수립 등록 완료";
		msg += "유선 확대적용 계획 승인 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("10".equals(stepCheck)){//초도적용결과 당일
//		msg += pkg21Model.getTitle()+" 초도적용 결과 [당일] 등록 완료";
		msg += "유선 확대적용 결과 등록 "+url+" ("+pkg21Model.getTitle()+")";
	}else if("11".equals(stepCheck)){//초도적용결과 최종
//		msg += pkg21Model.getTitle()+" 초도적용 결과 [최종] 등록 완료";
		msg += "유선 PKG 완료 "+url+" ("+pkg21Model.getTitle()+")";
		
// 반려===================================================================		
	}else if("101".equals(stepCheck)){//초도적용결과 최종
//		msg += pkg21Model.getTitle()+" 초도적용 결과 [최종] 등록 완료";
		msg += "유선 PKG개발결과 반려 "+url+" ("+pkg21Model.getTitle()+")";
		
	}else if("102".equals(stepCheck)){//초도적용결과 최종
//		msg += pkg21Model.getTitle()+" 초도적용 결과 [최종] 등록 완료";
		msg += "유선 검증계획 반려 "+url+" ("+pkg21Model.getTitle()+")";
		
	}else if("103".equals(stepCheck)){//초도적용결과 최종
//		msg += pkg21Model.getTitle()+" 초도적용 결과 [최종] 등록 완료";
		msg += "유선 검증 결과 승인 반려  "+url+" ("+pkg21Model.getTitle()+")";
		
	}else if("104".equals(stepCheck)){//초도적용결과 최종
//		msg += pkg21Model.getTitle()+" 초도적용 결과 [최종] 등록 완료";
		msg += "초도적용 계획 승인 반려 "+url+" ("+pkg21Model.getTitle()+")";
			
	}else if("105".equals(stepCheck)){//초도적용결과 최종
//		msg += pkg21Model.getTitle()+" 초도적용 결과 [최종] 등록 완료";
		msg += "확대적용 계획 승인 반려 "+url+" ("+pkg21Model.getTitle()+")";
		 
	}else {
		msg += "내용이 없습니다.";
	}	
	
//	msg += " http://pkmss.sktelecom.com/M.do";
//	msg += "[TTITLE] "+pkg21Model.getSystem_name()+"";
	return msg;
}





/**
 * SMS 발송
 * @param pkgModel
 * @param pkgUserModel
 * @throws Exception
 */
//@Override
//public void sendSms(PkgModel pkgModel, String phone) throws Exception {
public void sendSms(String smsMsg, String phone) throws Exception {
	String tel = phone;
	String tel1 = "";
	String tel2 = "";
	if(tel != null) {
		tel = tel.replaceAll("-", "");
		tel1 = tel.substring(0, 3);
		tel2 = tel.substring(3, tel.length());

		SmsModel smsModel = new SmsModel();
		smsModel.setLog_no("1");//의미없음
		
//		smsModel.setMsg("[PKMS PKG 검증요청] " + pkgModel.getTitle());
		smsModel.setMsg(smsMsg);
		
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
			
//			String TITLE = "(PKMS PKG 검증요청) [" + pkgModel.getTitle() + "] 확인해 주시기 바랍니다."; //전송메시지
			String TITLE = smsMsg; //전송메시지
			
			
			
			String PHONE = tel1 + tel2; //수신자 전화번호
			
			String URL = ""; //수신 URL		 					
			String START_DT_HMS = ""; //예약발송 시작시간			
			String END_DT_HMS = "";	//예약발송 종료시간
			
			logger.debug("============================new sms_send_input_CONSUMER_ID ============================" + CONSUMER_ID);
			logger.debug("============================new sms_send_input_RPLY_PHON_NUM ==========================" + RPLY_PHON_NUM);
			logger.debug("============================new sms_send_input_TITLE ==================================" + TITLE);
			logger.debug("============================new sms_send_input_PHONE ==================================" + PHONE);
			
			//Output (uuid는 예약전송일 경우에만 들어온다.");
			StringHolder _return = new StringHolder();
			StringHolder uuid = new StringHolder();
			
			//웹서비스 호출. (send오퍼레이션)
//			service.send(CONSUMER_ID, RPLY_PHON_NUM, TITLE, PHONE, URL, START_DT_HMS, END_DT_HMS, _return, uuid); // 실제전송 //test 일단 test 중에만 
			
			logger.debug("============================new sms_send_return =======================================" + _return.value);
			logger.debug("============================new sms_send_return_uuid ==================================" + uuid.value);
		} catch (ServiceException e) {
			e.printStackTrace();
//		}//test 일단 test 중에만 
		} 
		/*catch (RemoteException e) {
			e.printStackTrace();
		}*/
		smsService.create(smsModel);//DB저장
	}
}


	
}//NewMailService  end
