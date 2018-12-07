package com.pkms.pkgmg.schedule.service;

import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.client.Stub;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import skt.soa.notification.webservice.SMSSender;
import skt.soa.notification.webservice.SMSSenderServiceLocator;

import com.pkms.common.ftp.service.FtpServiceIf;
import com.pkms.common.mail.model.MailModel;
import com.pkms.common.mail.service.MailService;
import com.pkms.common.mail.service.MailServiceIf;
import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.common.sms.model.SmsModel;
import com.pkms.common.sms.service.SmsServiceIf;
import com.pkms.common.util.PkgSmsUtil;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgUserModel;
import com.pkms.pkgmg.pkg.service.PkgEquipmentServiceIf;
import com.pkms.pkgmg.schedule.dao.ScheduleDAO;
import com.pkms.pkgmg.schedule.model.ScheduleModel;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.usermg.user.model.SktUserModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ExcelUtil;
import com.wings.util.ObjectUtil;
import com.opencsv.CSVReader;


/**
 * PKG검증/일정 > 일정목록<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 05.
 * 
 */
@Service("ScheduleService")
public class ScheduleService implements ScheduleServiceIf {
	static Logger logger = Logger.getLogger(ScheduleService.class);
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "ScheduleDAO")
	private ScheduleDAO scheduleDAO;

	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;

	@Resource(name = "PkgEquipmentService")
	private PkgEquipmentServiceIf pkgEquipmentService;

	@Resource(name = "MailService")
	private MailServiceIf mailService;
	
	@Resource(name = "SmsService")
	private SmsServiceIf smsService;
	
	@Resource(name = "FtpService")
	private FtpServiceIf ftpService;
	
	private final String SCHEDUL_READLIST_SESSION_KEY = "SCHEDUL_READLIST_SESSION_KEY";

	@Override
	public List<ScheduleModel> readList(ScheduleModel scheduleModel) throws Exception {
		List<ScheduleModel> resultList = scheduleDAO.readList(scheduleModel);
		scheduleModel.setTotalCount(scheduleDAO.readTotalCount(scheduleModel));

		// PKG EQUIPMENT 목록 조회 및 세팅
		for(ScheduleModel schedul : resultList){
			
			PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
			pkgEquipmentModel.setPkg_seq(schedul.getPkg_seq());
			List<PkgEquipmentModel> pkgEquipmentList = pkgEquipmentService.readList(pkgEquipmentModel);
			 
			schedul.setPkgEquipmentModelList(pkgEquipmentList);
			
//			PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
//			pkgEquipmentModel.setPkg_seq(schedul.getPkg_seq());
//			pkgEquipmentModel.setWork_gubun("S");
//			schedul.setPkgEquipmentModelList(scheduleDAO.readListPkgEquipment(pkgEquipmentModel));
//			pkgEquipmentModel.setWork_gubun("E");
//			schedul.getPkgEquipmentModelList().addAll(scheduleDAO.readListPkgEquipment(pkgEquipmentModel));	
		}
		
		return resultList;
	}

	@Override
	public ScheduleModel setSearchCondition(ScheduleModel scheduleModel) throws Exception {
		if (scheduleModel.isSessionCondition()) {
			ScheduleModel sessionModel = (ScheduleModel) sessionService.read(SCHEDUL_READLIST_SESSION_KEY);
			if (sessionModel == null) {
				scheduleModel = new ScheduleModel();
			} else {
				scheduleModel = sessionModel;
			}
		} else {
			sessionService.create(SCHEDUL_READLIST_SESSION_KEY, scheduleModel);
		}
		return scheduleModel;
	}
	
	
	@Override
	public String excelDownload(ScheduleModel scheduleModel) throws Exception {
		scheduleModel.setPaging(false);
		List<ScheduleModel> readList =  this.readList(scheduleModel);	
		 
		List<List<String>> rowList = new ArrayList<List<String>>();
		List<String> colList;
		
		//Excel 헤더
		//String[] headers = new String[]{"status_name ver  group_depth title verify_date_start verify_date_end "};
		String[] headers = new String[]{"status_name","group_depth","ver", "title","verify_date"};
		
		//Excel 헤더
		//String[] headers1 = new String[]{"work_gubun", "equipment_name", "work_date", "start_time1", "start_time2", "end_time1", "end_time2"};
		String[] headers1 = new String[]{"work_gubun", "equipment_name", "work_date"};
		List<Integer> merge_list =  new ArrayList<Integer>();
		
		for(ScheduleModel schedul : readList){  
			
			List<PkgEquipmentModel> pkgEquipmentList =schedul.getPkgEquipmentModelList();
			
			merge_list.add(pkgEquipmentList.size());
			
			if(pkgEquipmentList.size()>0){
				int i=0;
				for(i=0; i<pkgEquipmentList.size(); i++){
					
					colList = new ArrayList<String>();
					
					for(String field : headers) {
						
						if( field.equals("verify_date") ){
							String temp_date = (ObjectUtil.getObjectFieldValue(schedul, "verify_date_start")==null?"":ObjectUtil.getObjectFieldValue(schedul, "verify_date_start").toString())+"~"+(ObjectUtil.getObjectFieldValue(schedul, "verify_date_end")==null?"":ObjectUtil.getObjectFieldValue(schedul, "verify_date_end").toString());
							colList.add(temp_date);
						} else {
							colList.add(ObjectUtil.getObjectFieldValue(schedul, field)==null?"":ObjectUtil.getObjectFieldValue(schedul, field).toString());
						}
					}
					 
					PkgEquipmentModel pkgEquipmentModel = 	pkgEquipmentList.get(i);
					
					
					
					for(String field1 : headers1) {
						
						if(field1.equals("work_gubun")){
							String work_gubun =(ObjectUtil.getObjectFieldValue(pkgEquipmentModel, field1)==null?"":ObjectUtil.getObjectFieldValue(pkgEquipmentModel, field1).toString());
							
							if( work_gubun.equals("S") ){
								colList.add("초도");
							}else if( work_gubun.equals("E") ){
								colList.add("확대");
							}
							continue;	
						}
						
						if(field1.equals("work_date")){
							 
							 
							String work_date =(ObjectUtil.getObjectFieldValue(pkgEquipmentModel, field1)==null?"":ObjectUtil.getObjectFieldValue(pkgEquipmentModel, field1).toString());
							work_date =work_date+" "+(ObjectUtil.getObjectFieldValue(pkgEquipmentModel, "start_time1")==null?"":ObjectUtil.getObjectFieldValue(pkgEquipmentModel, "start_time1").toString()) +" : "+(ObjectUtil.getObjectFieldValue(pkgEquipmentModel, "start_time2")==null?"":ObjectUtil.getObjectFieldValue(pkgEquipmentModel, "start_time2").toString());
							work_date =work_date+ " ~ "+(ObjectUtil.getObjectFieldValue(pkgEquipmentModel, "end_time1")==null?"":ObjectUtil.getObjectFieldValue(pkgEquipmentModel, "end_time1").toString() )+" : "+(ObjectUtil.getObjectFieldValue(pkgEquipmentModel, "end_time2")==null?"":ObjectUtil.getObjectFieldValue(pkgEquipmentModel, "end_time2").toString());
							 
							colList.add(work_date);
							
						}else{
							colList.add(ObjectUtil.getObjectFieldValue(pkgEquipmentModel, field1)==null?"":ObjectUtil.getObjectFieldValue(pkgEquipmentModel, field1).toString());
						}
						
					}
					
					rowList.add(colList);
				}
				
			} else {
				colList = new ArrayList<String>();
				
				for(String field : headers) {
					if( field.equals("verify_date") ){
						String temp_date = (ObjectUtil.getObjectFieldValue(schedul, "verify_date_start")==null?"":ObjectUtil.getObjectFieldValue(schedul, "verify_date_start").toString())+"~"+(ObjectUtil.getObjectFieldValue(schedul, "verify_date_end")==null?"":ObjectUtil.getObjectFieldValue(schedul, "verify_date_end").toString());
						colList.add(temp_date);
					} else {
						colList.add(ObjectUtil.getObjectFieldValue(schedul, field)==null?"":ObjectUtil.getObjectFieldValue(schedul, field).toString());
					}
				}
				rowList.add(colList);
			}
			
			
		}
		
		
		String [] excel_header =new String[]{ "상태","시스템","버젼","제목","검증일자","구분","장비","장비별 적용일자"};
		 
		Map<Integer,String[]> headerMap  = new HashMap<Integer,String[]>();  
		 
		 headerMap.put(0, excel_header); 
		//파일 생성 후 다운로드할 파일명
		String downloadFileName =ExcelUtil.write(scheduleModel.getEXCEL_FILE_NAME(), propertyService.getString("Globals.fileStorePath"), headerMap, rowList ,merge_list,5,"R");
		
		return downloadFileName;
	}

	
	/** ☆☆☆☆★★★★☆☆☆☆★★★★140226 이전 소스 시작 부분☆☆☆☆★★★★☆☆☆☆★★★★*/
	/**미완료된 pkg일정 알림 
	 * 7일에 한번씩 스케줄러 작동
	 * SMS와 메일로 알림 발송
	 * */
	/**pkg검증일정-미완료건 7일마다 메일/sms 발송 추가 1023 , 수정 1104 - ksy 
	 *     0 0 8 * * MON   */
/*	@Override
//	@Scheduled(cron = "0 0 8 * * MON") // 월요일마다 오전8시에 실행
//	@Scheduled(cron = "0 0/2 * * * *") // 테스트
	public void sendPkgMailSms() throws Exception {
		ScheduleModel scheduleModel = new ScheduleModel();
		*//**초도승인완료 후, 확대일정수립 후에 일주일지나도 pkg가 완료되지않은 건에 대한 알림 1106 - ksy *//*
		//incompletePkgSeq 가 있으면
		System.out.println("●●●●●●●●●●●●●●●●●●●●●●●●●PKG 미완료 알람●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●");
		List<ScheduleModel> incompletePkgSeq = scheduleDAO.incompletePkgSeq(scheduleModel); //초도승인완료, 확대일정수립 상태인 pkg 시퀀스 리스트
		String pkgSeq = "";
		if(incompletePkgSeq != null){
			for (int i = 0; i < incompletePkgSeq.size(); i++){
				int j =0 ;
				pkgSeq = incompletePkgSeq.get(i).getPkg_seq();
				List<ScheduleModel> incompletePkgList = scheduleDAO.incompletePkgList(pkgSeq); //해당 pkg 시퀀스를 가지고 해당 정보 출력 리스트
			
				String[] mailTos = new String[incompletePkgList.size()];
				String[] mailTosInfo = new String[incompletePkgList.size()];
				String[] pkgTitles = new String[incompletePkgList.size()];
				//System.out.println(incompletePkgList.size()+"  ====== incompletePkgList.size ★★★★★★★★★★★★★");
			
				for(Object obj : incompletePkgList){
					mailTos[j] = ((ScheduleModel)obj).getEmail();
					mailTosInfo[j] = ((ScheduleModel)obj).getEmail() + "(" + ((ScheduleModel)obj).getHname() + " " + ((ScheduleModel)obj).getSosok() + ")";
					pkgTitles[j] = ((ScheduleModel)obj).getTitle();
					//System.out.println(mailTos[j]+"  ====== 메일주소 "+j+" 번째 ★★★★★★★★★★★★★");
					j++;
				}
			
				scheduleModel.setEmails(mailTos);
				scheduleModel.setTitles(pkgTitles);
				scheduleModel.setEmailsInfo(mailTosInfo);
				scheduleModel.setMail_gubun("1"); //초도승인후,확대일정수립후 미완료 구분
				this.sendMail(scheduleModel);
			}
		}
		*//***//*
		System.out.println("●●●●●●●●●●●●●●●●●●●●●●●●●긴급등록 미전환 알람●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●");
		*//**pkg 긴급등록시 일주일 경과 일반 pkg로 전환 되지 않은 건에 대한 알림 1111 - ksy *//*
		List<ScheduleModel> emergencyPkgSeq = scheduleDAO.emergencyPkgSeq(scheduleModel);
		if(emergencyPkgSeq != null){
			for (int n = 0; n < emergencyPkgSeq.size(); n++){
				int m =0 ;
				pkgSeq = emergencyPkgSeq.get(n).getPkg_seq();
				List<ScheduleModel> incompletePkgList = scheduleDAO.incompletePkgList(pkgSeq); //해당 pkg 시퀀스를 가지고 해당 정보 출력 리스트
			
				String[] mailTos = new String[incompletePkgList.size()];
				String[] mailTosInfo = new String[incompletePkgList.size()];
				String[] pkgTitles = new String[incompletePkgList.size()];
				//System.out.println(incompletePkgList.size()+"  ====== incompletePkgList.size ★★★★★★★★★★★★★");
			
				for(Object obj : incompletePkgList){
					mailTos[m] = ((ScheduleModel)obj).getEmail();
					mailTosInfo[m] = ((ScheduleModel)obj).getEmail() + "(" + ((ScheduleModel)obj).getHname() + " " + ((ScheduleModel)obj).getSosok() + ")";
					pkgTitles[m] = ((ScheduleModel)obj).getTitle();
					//System.out.println(mailTos[m]+"  ====== 메일주소 "+m+" 번째 ★★★★★★★★★★★★★");
					m++;
				}
			
				scheduleModel.setEmails(mailTos); //받는사람
				scheduleModel.setTitles(pkgTitles);//메일 제목
				scheduleModel.setEmailsInfo(mailTosInfo);//받는 사람 정보
				scheduleModel.setMail_gubun("2"); //긴급pkg 일반건으로 미전환 구분
				this.sendMail(scheduleModel);
			}
		}
		*//***//*

		
		*//**단일메일발송*//*
		List<ScheduleModel> incompletePkgList6_1 = scheduleDAO.incompletePkgList6_1(scheduleModel);
		String mailTo = ""; String pkgTitle = ""; String mailToInfo = "";
		for(int k = 0; k < incompletePkgList6_1.size(); k++){
			mailTo = incompletePkgList6_1.get(k).getEmail();
			
			pkgTitle = "<tr style='height:18pt'><td colspan='2' align='left'><font style='color: blue;'>";
			pkgTitle+= "<b> * 미완료된 PKG일정이 있습니다. 담당자께서는 확인하신 후 진행 바랍니다.</b></font></td><td></td></tr>";
			pkgTitle+= "<tr style='height:14pt'><td align='left' valign='top' ><b>[제목]</b></td><td align='left' valign='top' >";
			pkgTitle+= incompletePkgList6_1.get(k).getTitle();
			pkgTitle+= "</td></tr><tr style='height:14pt'><td align='left' colspan='2'><br/>";
			pkgTitle+= "<a href='http://pkms.sktelecom.com/'>▶ PKMS 바로가기(SK내부직원용)</a></td></tr>";
			pkgTitle+= "<tr style='height:14pt'><td align='left' colspan='2'>";
			pkgTitle+= "<a href='http://pkmss.sktelecom.com/'>▶ PKMS 바로가기(BP용)</a><br/><br/></td></tr>";
			
			mailToInfo= incompletePkgList6_1.get(k).getEmail() + "(" + incompletePkgList6_1.get(k).getHname() + " " + incompletePkgList6_1.get(k).getSosok() + ")";
			
			String mailFrom = "";
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj(this.getTitle());
			mailModel.setMsgText(pkgTitle);
			mailModel.setTo(mailTo);
			mailModel.setFrom(mailFrom);
			mailModel.setToInfo(mailToInfo);
			
			System.out.println(mailModel.getTo()+" === ck.jeon@sk.com??");
			System.out.println(mailModel.getMsgText()+" === Tmapgw_AIMS#6_개통관련_헤더추가??");

			//메일발송
			mailService.create(mailModel);
		}*//***//*
		
	
	}
	
	private void sendMail(ScheduleModel scheduleModel) throws Exception {
		String[] pkgTitles = scheduleModel.getTitles();
		ArrayList<String> uniqTitles = new ArrayList<String>();
		for(String uniqTitle : pkgTitles){
			if(uniqTitles.contains(uniqTitle)) continue;
			uniqTitles.add(uniqTitle);
		}
		pkgTitles = (String[]) uniqTitles.toArray(new String[uniqTitles.size()]);
		uniqTitles.clear();
		uniqTitles = null;
		
			String 
			msgContent = "<tr style='height:18pt'><td colspan='2' align='left'><font style='color: blue;'>";
			if(scheduleModel.getMail_gubun().equals("1")){
				msgContent+= "<b> * 미완료된 PKG일정이 있습니다. 담당자께서는 확인하신 후 진행 바랍니다.</b></font></td><td></td></tr>";
			}
			if(scheduleModel.getMail_gubun().equals("2")){
				msgContent+= "<b> * 일반 PKG로 미전환된 긴급 PKG일정이 있습니다. 담당자께서는 확인하신 후 진행 바랍니다.</b></font></td><td></td></tr>";
			}
			msgContent+= "<tr style='height:14pt'><td align='left' valign='top' ><b>[제목]</b></td><td align='left' valign='top' >";
			msgContent+= pkgTitles[0];
			msgContent+= "</td></tr><tr style='height:14pt'><td align='left' colspan='2'><br/>";
			msgContent+= "<a href='http://pkms.sktelecom.com/'>▶ PKMS 바로가기(SK내부직원용)</a></td></tr>";
			msgContent+= "<tr style='height:14pt'><td align='left' colspan='2'>";
			msgContent+= "<a href='http://pkmss.sktelecom.com/'>▶ PKMS 바로가기(BP용)</a><br/><br/></td></tr>";
			//System.out.println(pkgTitles[0]+" ===== pkgTitles[0]");
			//System.out.println(msgContent );
		
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj(this.getTitle(scheduleModel));
			mailModel.setMsgText(msgContent);
			mailModel.setTos(scheduleModel.getEmails());
			mailModel.setFrom("cpg@in-soft.co.kr");
			mailModel.setTosInfo(scheduleModel.getEmailsInfo());

		//메일발송
		mailService.create4Multi(mailModel);
		
	}
	
	public String getTitle(ScheduleModel scheduleModel) throws Exception{
		String title = null;
		if(scheduleModel.getMail_gubun().equals("1")){
			title = "[PKMS] 미완료된 PKG일정 알림";
		}
		if(scheduleModel.getMail_gubun().equals("2")){
			title = "[PKMS] 미전환된 긴급 PKG일정 알림";
		}
		
		return title;
	}
	*/
	/** ☆☆☆☆★★★★☆☆☆☆★★★★140226 이전 소스 끝 부분☆☆☆☆★★★★☆☆☆☆★★★★*/
	
	
	/**미완료된 pkg일정 알림 
	 * 7일에 한번씩 스케줄러 작동
	 * SMS와 메일로 알림 발송
	 * */
	/**pkg검증일정-미완료건 7일마다 메일/sms 발송 추가 1023 , 수정 1104 - ksy 
	 *     0 0 8 * * MON   */
	@Override
//	@Scheduled(cron = "0 0 8 * * MON") // 월요일마다 오전8시에 실행
//	@Scheduled(cron = "0 */1 * * * *") // 테스트
	public void sendPkgMailSms() throws Exception {
		ScheduleModel scheduleModel = new ScheduleModel();
		
		System.out.println("●●●●●●●●●●●●●●●●●●●●●●●●●PKG 미완료 알람●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●●");
		List<ScheduleModel> incompletePkgUser = scheduleDAO.incompletePkgUser(scheduleModel); //초도승인완료, 확대일정수립 상태인 pkg 시퀀스 리스트
		
		String[] mailTos = new String[incompletePkgUser.size()];
		String[] mailTosInfo = new String[incompletePkgUser.size()];
		
		int cnt = 0;
		
		if(incompletePkgUser != null){
			for(ScheduleModel model : incompletePkgUser){
				if(cnt == 0){
					mailTos[cnt] = "jhkim@in-soft.co.kr";
					mailTosInfo[cnt] = "jhkim@in-soft.co.kr" + "(" + "아이엔소프트" + " " + "[PKMS]" + ")";
				}else{
					mailTos[cnt] = model.getEmail();
					mailTosInfo[cnt] = model.getEmail() + "(" + model.getHname() + " " + model.getSosok() + ")";
				}
				cnt++;
			}
		}
		
		scheduleModel.setEmails(mailTos);
		scheduleModel.setEmailsInfo(mailTosInfo);
		this.sendMail(scheduleModel);
	}
	
	private void sendMail(ScheduleModel scheduleModel) throws Exception {
		String title = "[PKMS] 미완료된 PKG검증 알림";
		String msgContent = "<tr style='height:18pt'><td colspan='2' align='left'><font style='color: blue;'>";
		
		msgContent+= "<b> * 미완료된 PKG일정이 있습니다. 담당자께서는 확인하신 후 진행 바랍니다.</b></font></td><td></td></tr>";
		
		
		msgContent+= "<tr style='height:14pt'><td align='left' valign='top' ><b>[제목]</b></td><td align='left' valign='top' >";
		msgContent+= title;
		msgContent+= "</td></tr><tr style='height:14pt'><td align='left' colspan='2'><br/>";
		msgContent+= "<a href='http://pkms.sktelecom.com/'>▶ PKMS 바로가기(SK내부직원용)</a></td></tr>";
		msgContent+= "<tr style='height:14pt'><td align='left' colspan='2'>";
		msgContent+= "<a href='http://pkmss.sktelecom.com/'>▶ PKMS 바로가기(BP용)</a><br/><br/></td></tr>";
		System.out.println(msgContent );
		System.out.println(scheduleModel.getEmails() );
	
		MailModel mailModel = new MailModel();
		mailModel.setMsgSubj(title);
		mailModel.setMsgText(msgContent);
		mailModel.setTos(scheduleModel.getEmails());
		mailModel.setFrom("jhkim@in-soft.co.kr");
		mailModel.setTosInfo(scheduleModel.getEmailsInfo());

		//메일발송
		mailService.create4Multi(mailModel);
		
	}
	
	/**
	 * Excel file 받아오면 읽어서 DB에 넣기 
	 * TEST-BED
	 * 
	 **/
	@Override
//	@Scheduled(cron = "0 0 8 * * MON") // 월요일마다 오전8시에 실행
//	@Scheduled(cron = "0 */1 * * * *") // 테스트
//	@Scheduled(cron = "0 0 4 * * ?") /* 매일 새벽 4시 */
	public void create_testbedEq() throws Exception {
		//Data_TB_Info_YYYYMMDD.csv
		String strTodayDate = "";// 오늘일자
		Calendar calInfo = Calendar.getInstance();
		// calInfo.add(cal.DATE, -1 );
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		strTodayDate = formatter.format(calInfo.getTime());
		
		String connect = "/home/insoft/Data_TB_Info_"+strTodayDate+".csv"; // ftp 접속 경로
		String filename = "Data_TB_Info_"+strTodayDate+".csv"; // 다운로드해서 filename 변환
		
		try {
			//file download
			this.getFtpFile(connect, filename);
			
			CSVReader reader = new CSVReader(new FileReader("D:\\pkms_file\\"+filename), '|');
			String[] nextLine;
			List<ScheduleModel> excellist = new ArrayList<ScheduleModel>();
//			int i = 0; //컬럼명 제거 cnt
//			12개
//			11개
			while ((nextLine = reader.readNext()) != null) {
//				if(i != 0){
					ScheduleModel scheduleModel = new ScheduleModel();
					// nextLine[] is an array of values from the line
					int vertical_cnt = 0; //열 cnt
					for(String read : nextLine){
//						System.out.println(read+"☆☆☆☆☆★★★★★"+nextLine[vertical_cnt]);
						if(vertical_cnt==0){							
							scheduleModel.setAsset_uid(read);
						}else if(vertical_cnt==1){
							scheduleModel.setSerial_no(read);
						}else if(vertical_cnt==2){
							scheduleModel.setAsset_status(read);
						}else if(vertical_cnt==3){
							scheduleModel.setIdc_name(read);
						}else if(vertical_cnt==4){
							scheduleModel.setEq_gubun(read);
						}else if(vertical_cnt==5){
							scheduleModel.setEq_maker(read);
						}else if(vertical_cnt==6){
							scheduleModel.setEq_model(read);
						}else if(vertical_cnt==7){
							scheduleModel.setService_group(read);
						}else if(vertical_cnt==8){
							scheduleModel.setEq_group(read);
						}else if(vertical_cnt==9){
							scheduleModel.setHost_name(read);
						}else if(vertical_cnt==10){
							scheduleModel.setIp_addr1(read);
						}else if(vertical_cnt==11){
							scheduleModel.setIp_addr2(read);
						}
						vertical_cnt++;
					}
					excellist.add(scheduleModel);
//				}
//				i++;
			}
			
			if(excellist != null){
				scheduleDAO.delete_testbed();
				scheduleDAO.create_testbed(excellist);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}


	}
	
	@Override
//	@Scheduled(cron = "0 0 8 * * MON") // 월요일마다 오전8시에 실행
//	@Scheduled(cron = "0 */1 * * * *") // 테스트
//	@Scheduled(cron = "0 0 4 * * ?") /* 매일 새벽 4시 */
	public void create_realEq() throws Exception {
		//Data_Real_Info_YYYYMMDD.csv
		String strTodayDate = "";// 오늘일자
		Calendar calInfo = Calendar.getInstance();
		// calInfo.add(cal.DATE, -1 );
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		strTodayDate = formatter.format(calInfo.getTime());
		
		String connect = "/home/insoft/Data_Real_Info_"+strTodayDate+".csv"; // ftp 접속 경로
		String filename = "Data_Real_Info_"+strTodayDate+".csv"; // 다운로드해서 filename 변환
		
		try {
			//file download
			this.getFtpFile(connect, filename);
			
			CSVReader reader = new CSVReader(new FileReader("D:\\pkms_file\\"+filename), '|');
			String[] nextLine;
			List<ScheduleModel> excellist = new ArrayList<ScheduleModel>();
//			int i = 0; //컬럼명 제거 cnt
//			12개
//			11개
			while ((nextLine = reader.readNext()) != null) {
//				if(i != 0){
					ScheduleModel scheduleModel = new ScheduleModel();
					// nextLine[] is an array of values from the line
					int vertical_cnt = 0; //열 cnt
					for(String read : nextLine){
//						System.out.println(read+"☆☆☆☆☆★★★★★"+nextLine[vertical_cnt]);
						if(vertical_cnt==0){							
							scheduleModel.setIdc_name(read);
						}else if(vertical_cnt==1){
							scheduleModel.setTeam_name(read);
						}else if(vertical_cnt==2){
							scheduleModel.setNetwork_group(read);
						}else if(vertical_cnt==3){
							scheduleModel.setSystem_group(read);
						}else if(vertical_cnt==4){
							scheduleModel.setSystem_name(read);
						}else if(vertical_cnt==5){
							scheduleModel.setManage_name(read);
						}else if(vertical_cnt==6){
							scheduleModel.setReal_host_name(read);
						}else if(vertical_cnt==7){
							scheduleModel.setReal_ip(read);
						}else if(vertical_cnt==8){
							scheduleModel.setSerial_no(read);
						}else if(vertical_cnt==9){
							scheduleModel.setIp_addr1(read);
						}else if(vertical_cnt==10){
							scheduleModel.setIp_addr2(read);
						}
						vertical_cnt++;
					}
					excellist.add(scheduleModel);
//				}
//				i++;
			}
			
			if(excellist != null){
				scheduleDAO.delete_real();
				scheduleDAO.create_real(excellist);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	/**
	 * 
	 * @param connect
	 * @param filename
	 * @throws Exception
	 * 
	 * ftp 접속하여 파일을 다운로드 한다.
	 */
	@Override
	public void getFtpFile(String connect, String filename) throws Exception {
		try {
			ftpService.connect();
			ftpService.login();
			ftpService.list();
			
			/*
			 * get(상대방의 다운로드 위치 및 파일 name, target, 파일을 가져와서 이름을 정한다.)
			 * 다운로드 시 ftp 접속 정보는 ftpService 에 있다.
			 * ftpService.get("/home/usr/pkms_file/" + strTodayDate + ".txt", "","Update_Data.txt");
			 */
			ftpService.get(connect, "",filename);
			ftpService.logout();

		} catch (Exception e) {
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("파일 다운로드 실패 : " + e);
			System.out.println("--------------------------------------------------------------------------------");
			return;
		}

	}
	
	@Override
//	@Scheduled(cron = "0 0 8 * * MON") // 월요일마다 오전8시에 실행
//	@Scheduled(cron = "0 */1 * * * *") // 테스트
//	@Scheduled(cron = "0 0 4 * * ?") /* 매일 새벽 4시 */
	public void create_group_depth() throws Exception {
		
		try {
			CSVReader reader = new CSVReader(new FileReader("D:\\pkms_file\\"+"group_depth.csv"), '|');
			String[] nextLine;
			List<ScheduleModel> excellist = new ArrayList<ScheduleModel>();
//			int i = 0; //컬럼명 제거 cnt
//			12개
//			11개
			while ((nextLine = reader.readNext()) != null) {
//				if(i != 0){
					ScheduleModel scheduleModel = new ScheduleModel();
					int vertical_cnt = 0; //열 cnt
					for(String read : nextLine){
//						System.out.println(read+"☆☆☆☆☆★★★★★"+nextLine[vertical_cnt]);
						if(vertical_cnt==0){							
							scheduleModel.setGroup1_code(read);
						}else if(vertical_cnt==1){
							scheduleModel.setGroup1_name(read);
						}else if(vertical_cnt==2){
							scheduleModel.setGroup2_code(read);
						}else if(vertical_cnt==3){
							scheduleModel.setGroup2_name(read);
						}else if(vertical_cnt==4){
							scheduleModel.setGroup3_code(read);
						}else if(vertical_cnt==5){
							scheduleModel.setGroup3_name(read);
						}else if(vertical_cnt==6){
							scheduleModel.setSystem_code(read);
						}else if(vertical_cnt==7){
							scheduleModel.setSystem_name(read);
						}else if(vertical_cnt==8){
							scheduleModel.setEquipment_code(read);
						}else if(vertical_cnt==9){
							scheduleModel.setEquipment_name(read);
						}else if(vertical_cnt==10){
							scheduleModel.setG1_seq(read);
						}else if(vertical_cnt==11){
							scheduleModel.setG1_name(read);
						}else if(vertical_cnt==12){
							scheduleModel.setG2_seq(read);
						}else if(vertical_cnt==13){
							scheduleModel.setG2_name(read);
						}else if(vertical_cnt==14){
							scheduleModel.setG3_seq(read);
						}else if(vertical_cnt==15){
							scheduleModel.setG3_name(read);
						}else if(vertical_cnt==16){
							scheduleModel.setSys_seq(read);
						}else if(vertical_cnt==17){
							scheduleModel.setSys_name(read);
						}else if(vertical_cnt==18){
							scheduleModel.setEq_seq(read);
						}else if(vertical_cnt==19){
							scheduleModel.setEq_name(read);
						}

						vertical_cnt++;
					}
					excellist.add(scheduleModel);
//				}
//				i++;
			}
			if(excellist != null){
				scheduleDAO.delete_group_depth();
				scheduleDAO.create_group_depth(excellist);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("데이터가 성공적으로 delete 및 insert 되었습니다.");
		}
	}
	
/*	public String getContent() throws Exception{
		String content = null;
		content = " 미완료된 PKG일정이 있습니다. <br/> 담당자 혹은 검증자께서는 미완료된 PKG일정을 확인하신 후 진행 바랍니다.<br/></td></tr>";
		
		return content;
	}
	*/
	
	/*private void sendSms(ScheduleModel scheduleModel) throws Exception {
		String tel = "";
		String[] tels = scheduleModel.getMovetelnos();
		
		for(int i= 0; i < tels.length; i++){
			if(i == tels.length){
				tel+= tels[i];
			}else{
				tel+= tels[i] + ",";
			}
		}

		if(tel != null) {
			tel = tel.replaceAll("-", "");
			System.out.println(tel+"===전화번호82개ㅋㅋㅋ");
			SmsModel smsModel = new SmsModel();
			smsModel.setLog_no("1");//의미없음
			smsModel.setMsg("[PKMS] 미완료된 PKG일정이 있습니다. 확인하신 후 진행 바랍니다.");
			smsModel.setDestcid("");//국번
			smsModel.setDestcallno("");
			smsModel.setPortedflag("0");//의미없음
			smsModel.setTid("65491");//승인인 경우
			
			//soa 연동
			try{
				//Sms서비스 생성
				SMSSenderServiceLocator locator = new SMSSenderServiceLocator();
				
				//개발기 (생략시 WSDL파일에 있는 포트사용)
				locator.setEndpointAddress("SMSSenderSoapPort", propertyService.getString("SOA.Sms.ip"));
				
				SMSSender service = locator.getSMSSenderSoapPort();
				
				//Authentication 설정
				Stub stub = (Stub)service;
				stub._setProperty(Stub.USERNAME_PROPERTY, propertyService.getString("SOA.Username"));
				stub._setProperty(Stub.PASSWORD_PROPERTY, propertyService.getString("SOA.Password"));				
				
				//Input 파라미터
				String CONSUMER_ID = null; //송신시스템 id(시스템 별 ID 부여)
				String RPLY_PHON_NUM = null; //송신자 전화번호
				String TITLE = null; //전송메시지
				String PHONE = null; //수신자 전화번호
				
				String URL = ""; //수신 URL		 					
				String START_DT_HMS = ""; //예약발송 시작시간			
				String END_DT_HMS = "";	//예약발송 종료시간			
				
				//테스트용
//				CONSUMER_ID = "test_consumer";
//				RPLY_PHON_NUM = "01091168781";
//				TITLE = "안녕하세요.PKMS입니다.";
//				PHONE = "01091168781";
			
				CONSUMER_ID = propertyService.getString("SOA.Username");
				RPLY_PHON_NUM = propertyService.getString("SOA.Sms.sendNum");
				TITLE = "[PKMS] 미완료된 PKG일정이 있습니다. 확인하신 후 진행 바랍니다.";
				PHONE = tel;
				
				//Output (uuid는 예약전송일 경우에만 들어온다.");
				StringHolder _return = new StringHolder();
				StringHolder uuid = new StringHolder();
				
				logger.debug("============================ sms_send_input_CONSUMER_ID ============================" + CONSUMER_ID);
				logger.debug("============================ sms_send_input_RPLY_PHON_NUM ==========================" + RPLY_PHON_NUM);
				logger.debug("============================ sms_send_input_TITLE ==================================" + TITLE);
				logger.debug("============================ sms_send_input_PHONE ==================================" + PHONE);
				
//				//웹서비스 호출. (send오퍼레이션)
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
	}*/
	
/*public void oldPkgSchduleCall() throws Exception {
		
		PkgScheduleModel pkgScheduleModel = new PkgScheduleModel();
		
		List<PkgScheduleModel> oldList =pkgScheduleDAO.oldList(pkgScheduleModel);
		
		for(int i=0; i < oldList.size(); i++){
			pkgScheduleModel.setSystem_seq(oldList.get(i).getSystem_seq());
			List<PkgScheduleModel> userList = pkgScheduleDAO.userId(pkgScheduleModel);
			
			String[] emails = new String[userList.size()];
			
			for(int j=0; j < userList.size(); j++){
				pkgScheduleModel.setEmpno(userList.get(j).getUser_id());
				pkgScheduleModel = pkgScheduleDAO.userInfo(pkgScheduleModel);
				
				emails[j] = pkgScheduleModel.getEmail();
				
				System.out.println("---------------------------------------------------");
				System.out.println("사원번호 : " + pkgScheduleModel.getEmpno());
				System.out.println("이메일 : " + pkgScheduleModel.getEmail());
				System.out.println("핸드폰번호" + pkgScheduleModel.getMovetelno());
				System.out.println("---------------------------------------------------");
				sendSms(pkgScheduleModel);
			}
			pkgScheduleModel.setEmails(emails);
			sendMail(pkgScheduleModel);
			emails = null;
		}
	}*/
	
}//end class
