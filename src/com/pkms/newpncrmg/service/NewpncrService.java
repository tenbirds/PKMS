package com.pkms.newpncrmg.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.mail.model.MailModel;
import com.pkms.common.mail.service.MailServiceIf;
import com.pkms.common.model.AbstractModel.USER_TYPE;
import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.newpncrmg.dao.NewpncrDao;
import com.pkms.newpncrmg.model.NewpncrModel;
import com.pkms.sys.common.model.SysRoadMapModel;
import com.pkms.sys.common.model.SysRoadMapSubModel;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;
import com.pkms.sys.system.service.SystemServiceIf;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ExcelUtil;

@Service("NewpncrService")
public class NewpncrService implements NewpncrServiceIf {
	
	static Logger logger = Logger.getLogger(NewpncrService.class);
	
	@Resource(name = "NewpncrDao")
	private NewpncrDao newpncrDao;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;
	
	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	@Resource(name = "SystemService")
	private SystemServiceIf SystemService;
	
	@Resource(name = "MailService")
	private MailServiceIf mailService;
	
	@Resource(name = "SystemService")
	private SystemServiceIf systemService;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Override
	public void create(NewpncrModel newpncrModel) throws Exception {
		
		/**
		 * 첨부 파일 처리
		 */
		
		fileManageService.create(newpncrModel, "NEWPNCR_");
		
		//state(상태)값 셋팅
		newpncrModel.setState("0");
		
		String mailFrom = null;
		mailFrom = newpncrModel.getSession_user_email();
		
		newpncrModel.setNew_pn_cr_seq(this.newpncrDao.create(newpncrModel));
		
		newpncrModel = this.read(newpncrModel);
		
		
		SysModel sysModel = new SysModel();
		sysModel.setSystem_seq(newpncrModel.getSystem_seq());
		sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.VU);
		sysModel = systemService.readUsersAppliedToSystem(sysModel);
		
		MailModel mailModel = new MailModel();
		mailModel.setMsgSubj(this.getTitle4Status(newpncrModel));
		mailModel.setMsgText(this.getContent4Status(newpncrModel));
		mailModel.setTos(this.gettoAddress4Status(sysModel.getSystemUserList()));
		mailModel.setFrom(mailFrom);
		
		/** 받는사람 정보에 email,이름,소속 같이 출력 추가 1016 - ksy */
		List<SystemUserModel> systemUserList = sysModel.getSystemUserList();
		String[] toInfos = new String[systemUserList.size()];
		int i = 0;
			for(Object obj : systemUserList){
				toInfos[i] = ((SystemUserModel)obj).getUser_email() + "("+((SystemUserModel)obj).getUser_name()+" "+((SystemUserModel)obj).getSosok()+")";
				i++;
			}
		mailModel.setTosInfo(toInfos);
		/***/
		
		mailService.create4Multi(mailModel);
	}

	@Override
	public NewpncrModel read(NewpncrModel newpncrModel) throws Exception {

		String retUrl = newpncrModel.getRetUrl();
		String sessionId = newpncrModel.getSession_user_id();
		
		boolean isOperator = false;
		List<String> authList = sessionService.readAuth();
		//매니져
		if(USER_TYPE.M.equals(newpncrModel.getSession_user_type())){

			for(String key : authList){
				if(key.equals("ROLE_MANAGER")){
					isOperator = true;
					break;
				}
			}
		}
		newpncrModel = newpncrDao.read(newpncrModel);
		NewpncrModel model = newpncrDao.bpComment_read(newpncrModel);
		if(retUrl.indexOf("View") > -1) {
			if(newpncrModel.getProblem() != null){
				newpncrModel.setProblem(newpncrModel.getProblem().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
			if(newpncrModel.getRequirement() != null){
				newpncrModel.setRequirement(newpncrModel.getRequirement().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
		}
		
		if("1".equals(newpncrModel.getState()) || "2".equals(newpncrModel.getState()) || "3".equals(newpncrModel.getState()) || "4".equals(newpncrModel.getState()) || "5".equals(newpncrModel.getState()) || "9".equals(newpncrModel.getState())){
			
			if(null != model.getManager_comment()){
				newpncrModel.setManager_comment(model.getManager_comment().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
			if(null != model.getBp_comment()){
				newpncrModel.setBp_comment(model.getBp_comment().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
			if(null != model.getReject_comment()){
				newpncrModel.setReject_comment(model.getReject_comment().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
			if(null != model.getRefine_comment()){
				newpncrModel.setRefine_comment(model.getRefine_comment().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
	/*		if(null != model.getManager_repl()){
				newpncrModel.setManager_repl(model.getManager_repl().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
			if(null != model.getBp_repl()){
				newpncrModel.setBp_repl(model.getBp_repl().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}*/
			
		}
		
		boolean isUserCharheGubun = false;
		
		if(isOperator){
			
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(newpncrModel.getSystem_seq());
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			List<SystemUserModel> systemUserModelList =  sysModel.getSystemUserList();
			
			for(SystemUserModel systemUserModel : systemUserModelList){
				
				if(SYSTEM_USER_CHARGE_GUBUN.AU.equals(systemUserModel.getCharge_gubun())) {
					if(sessionId.equals(systemUserModel.getUser_id())){
						isUserCharheGubun = true;
					}
				}
				
				if(SYSTEM_USER_CHARGE_GUBUN.VU.equals(systemUserModel.getCharge_gubun())) {
					if(sessionId.equals(systemUserModel.getUser_id())){
						isUserCharheGubun = true;
					}
				}
			}
		}
		
		newpncrModel.setUserChargeGubun(isUserCharheGubun);
		
		//첨부 파일 정보 세팅
		fileManageService.read(newpncrModel);
		return newpncrModel;
	}

	@Override
	public List<?> readList(NewpncrModel newpncrModel) throws Exception {
		
		List<?> resultList = null;
		int totalCount = 0;
		
		/*
		 * 세션 정보에서 로그인한 사용자의 관리 권한을 가져옴
		 */
		List<String> authList = sessionService.readAuth();
		
		boolean isOperator = true;
		
		//매니져
		if(USER_TYPE.M.equals(newpncrModel.getSession_user_type())){

			for(String key : authList){
				if(key.equals("ROLE_ADMIN") || key.equals("ROLE_MANAGER")){
					isOperator = false;
					break;
				}
			}
			
			if(isOperator){
				if("".equals(newpncrModel.getSearchRoleCondition())){
					newpncrModel.setSearchRoleCondition("0");
				}
				if("1".equals(newpncrModel.getSearchRoleCondition())){
					
					SystemUserModel systemUserModel = new SystemUserModel();
					systemUserModel.setUser_id(newpncrModel.getSession_user_id());
					systemUserModel.setUser_gubun(newpncrModel.getSession_user_type());
					
					List<?> list =  SystemService.readListAppliedToUser(systemUserModel);
					List<String> systemList = new ArrayList<String>();
					
					
					for(Object object : list){
						SysModel model = (SysModel) object;
						String systemkey = model.getSystem_seq();
						systemList.add(systemkey);
					}
					
					newpncrModel.setSystemKey(systemList);
				}
			}else{
				if("".equals(newpncrModel.getSearchRoleCondition())){
					newpncrModel.setSearchRoleCondition("1");
				}
				
				if("1".equals(newpncrModel.getSearchRoleCondition())){
					
					SystemUserModel systemUserModel = new SystemUserModel();
					systemUserModel.setUser_id(newpncrModel.getSession_user_id());
					systemUserModel.setUser_gubun(newpncrModel.getSession_user_type());
					
					List<?> list =  SystemService.readListAppliedToUser(systemUserModel);
					List<String> systemList = new ArrayList<String>();
					
					
					for(Object object : list){
						SysModel model = (SysModel) object;
						String systemkey = model.getSystem_seq();
						systemList.add(systemkey);
					}
					
					newpncrModel.setSystemKey(systemList);
				}
			}
		}
		
		if("5".equals(newpncrModel.getSearch_state())){ //반려인 목록 검색
			newpncrModel.setSearch_state("");
			newpncrModel.setReject_flag("Y");
		}
		if("1".equals(newpncrModel.getSearch_state())){ //반려가 아닌경우 반려 목록 출력 제거- 반려인 경우는 1번과 3번의 경우에서만 일어남
			newpncrModel.setReject_flag("N");
		}
		if("3".equals(newpncrModel.getSearch_state())){ //반려가 아닌경우 반려 목록 출력 제거- 반려인 경우는 1번과 3번의 경우에서만 일어남
			newpncrModel.setReject_flag("N");
		}
		resultList = newpncrDao.readList(newpncrModel);
		totalCount = newpncrDao.readTotalCount(newpncrModel);
		
		newpncrModel.setTotalCount(totalCount);
		return resultList;
	}

	@Override
	public void update(NewpncrModel newpncrModel) throws Exception {
		
		//첨부파일 정보 수정 
//		fileManageService.update(newpncrModel, "NEWPNCR_");
		
		if("update".equals(newpncrModel.getStateInfo())){
			//첨부파일 정보 수정 
			fileManageService.update(newpncrModel, "NEWPNCR_");
			
			this.newpncrDao.update(newpncrModel);
		
		//협력사 검토요청
		}else if("approveRequest".equals(newpncrModel.getStateInfo())){
			//첨부파일 정보 수정 
			fileManageService.update(newpncrModel, "NEWPNCR_");
			
			newpncrModel.setState("1");
			
			String mailFrom = null;
			mailFrom = newpncrModel.getSession_user_email();
			
			this.newpncrDao.stateInfo_update(newpncrModel);
			
			newpncrModel = this.read(newpncrModel);
			
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(newpncrModel.getSystem_seq());
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.BU);
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj(this.getTitle4Status(newpncrModel));
			mailModel.setMsgText(this.getContent4Status(newpncrModel));
			mailModel.setTos(this.gettoAddress4Status(sysModel.getSystemUserList()));
			mailModel.setFrom(mailFrom);
			
			/** 받는사람 정보에 email,이름,소속 같이 출력 추가 1016 - ksy */
			List<SystemUserModel> systemUserList = sysModel.getSystemUserList();
			String[] toInfos = new String[systemUserList.size()];
			int i = 0;
				for(Object obj : systemUserList){
					toInfos[i] = ((SystemUserModel)obj).getUser_email() + "("+((SystemUserModel)obj).getUser_name()+" "+((SystemUserModel)obj).getSosok()+")";
					i++;
				}
			mailModel.setTosInfo(toInfos);
			/***/
			
			mailService.create4Multi(mailModel);
			
		//검토완료
		}else if("confirm".equals(newpncrModel.getStateInfo())){
			//첨부파일 정보 수정 
			fileManageService.update(newpncrModel, "NEWPNCR_");
			
			newpncrModel.setState("2");
			String mailFrom = null;
			mailFrom = newpncrModel.getSession_user_email();
			
			this.newpncrDao.stateInfo_update(newpncrModel);
			
			newpncrModel = this.read(newpncrModel);
			
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(newpncrModel.getSystem_seq());
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.VU);
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			
			
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj(this.getTitle4Status(newpncrModel));
			mailModel.setMsgText(this.getContent4Status(newpncrModel));
			mailModel.setTos(this.gettoAddress4Status(sysModel.getSystemUserList()));
			mailModel.setFrom(mailFrom);
			
			/** 받는사람 정보에 email,이름,소속 같이 출력 추가 1016 - ksy */
			List<SystemUserModel> systemUserList = sysModel.getSystemUserList();
			String[] toInfos = new String[systemUserList.size()];
			int i = 0;
				for(Object obj : systemUserList){
					toInfos[i] = ((SystemUserModel)obj).getUser_email() + "("+((SystemUserModel)obj).getUser_name()+" "+((SystemUserModel)obj).getSosok()+")";
					i++;
				}
			mailModel.setTosInfo(toInfos);
			/***/
			
			mailService.create4Multi(mailModel);
			
		//정제
		}else if("refine".equals(newpncrModel.getStateInfo())){
			//첨부파일 정보 수정 
			fileManageService.update(newpncrModel, "NEWPNCR_");
			
			newpncrModel.setState("3");
			
			String mailFrom = null;
			mailFrom = newpncrModel.getSession_user_email();
			
			this.newpncrDao.stateInfo_update(newpncrModel);
			
			newpncrModel = this.read(newpncrModel);
			
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(newpncrModel.getSystem_seq());
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.VU);
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj(this.getTitle4Status(newpncrModel));
			mailModel.setMsgText(this.getContent4Status(newpncrModel));
			mailModel.setTos(this.gettoAddress4Status(sysModel.getSystemUserList()));
			mailModel.setFrom(mailFrom);
			
			/** 받는사람 정보에 email,이름,소속 같이 출력 추가 1016 - ksy */
			List<SystemUserModel> systemUserList = sysModel.getSystemUserList();
			String[] toInfos = new String[systemUserList.size()];
			int i = 0;
				for(Object obj : systemUserList){
					toInfos[i] = ((SystemUserModel)obj).getUser_email() + "("+((SystemUserModel)obj).getUser_name()+" "+((SystemUserModel)obj).getSosok()+")";
					i++;
				}
			mailModel.setTosInfo(toInfos);
			/***/
			
			mailService.create4Multi(mailModel);
			
		//협력사 검토 요청전 반려
		}else if("reject_1".equals(newpncrModel.getStateInfo())){
			//첨부파일 정보 수정 
			fileManageService.update(newpncrModel, "NEWPNCR_");
			
			newpncrModel.setState("1");
			newpncrModel.setReject_flag("Y");
			
			String mailFrom = null;
			mailFrom = newpncrModel.getSession_user_email();
			
			this.newpncrDao.stateInfo_update(newpncrModel);
			
			newpncrModel = this.read(newpncrModel);
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(newpncrModel.getSystem_seq());
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj(this.getTitle4Status(newpncrModel));
			mailModel.setMsgText(this.getContent4Status(newpncrModel));
			mailModel.setTos(this.gettoAddress4Status(sysModel.getSystemUserList()));
			mailModel.setFrom(mailFrom);
			
			/** 받는사람 정보에 email,이름,소속 같이 출력 추가 1016 - ksy */
			List<SystemUserModel> systemUserList = sysModel.getSystemUserList();
			String[] toInfos = new String[systemUserList.size()];
			int i = 0;
				for(Object obj : systemUserList){
					toInfos[i] = ((SystemUserModel)obj).getUser_email() + "("+((SystemUserModel)obj).getUser_name()+" "+((SystemUserModel)obj).getSosok()+")";
					i++;
				}
			mailModel.setTosInfo(toInfos);
			/***/
			
			mailService.create4Multi(mailModel);
			
		//협력사 검토 요청후 반려(검토완료 전)	0904 - ksy
		}else if("reject_9".equals(newpncrModel.getStateInfo())){
			//첨부파일 정보 수정 
			fileManageService.update(newpncrModel, "NEWPNCR_");
			
			newpncrModel.setState("9");
			newpncrModel.setReject_flag("Y");
			
			String mailFrom = null;
			mailFrom = newpncrModel.getSession_user_email();
			
			this.newpncrDao.stateInfo_update(newpncrModel);
			
			newpncrModel = this.read(newpncrModel);
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(newpncrModel.getSystem_seq());
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj(this.getTitle4Status(newpncrModel));
			mailModel.setMsgText(this.getContent4Status(newpncrModel));
			mailModel.setTos(this.gettoAddress4Status(sysModel.getSystemUserList()));
			mailModel.setFrom(mailFrom);
			
			/** 받는사람 정보에 email,이름,소속 같이 출력 추가 1016 - ksy */
			List<SystemUserModel> systemUserList = sysModel.getSystemUserList();
			String[] toInfos = new String[systemUserList.size()];
			int i = 0;
				for(Object obj : systemUserList){
					toInfos[i] = ((SystemUserModel)obj).getUser_email() + "("+((SystemUserModel)obj).getUser_name()+" "+((SystemUserModel)obj).getSosok()+")";
					i++;
				}
			mailModel.setTosInfo(toInfos);
			/***/
			
			mailService.create4Multi(mailModel);
			
		//정제 단계에서의 반려
		}else if("reject_2".equals(newpncrModel.getStateInfo())){
			//첨부파일 정보 수정 
			fileManageService.update(newpncrModel, "NEWPNCR_");
			
			newpncrModel.setState("3");
			newpncrModel.setReject_flag("Y");
			
			String mailFrom = null;
			mailFrom = newpncrModel.getSession_user_email();
			
			this.newpncrDao.stateInfo_update(newpncrModel);
			
			newpncrModel = this.read(newpncrModel);
			
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(newpncrModel.getSystem_seq());
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.VU);
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj(this.getTitle4Status(newpncrModel));
			mailModel.setMsgText(this.getContent4Status(newpncrModel));
			mailModel.setTos(this.gettoAddress4Status(sysModel.getSystemUserList()));
			mailModel.setFrom(mailFrom);
			
			/** 받는사람 정보에 email,이름,소속 같이 출력 추가 1016 - ksy */
			List<SystemUserModel> systemUserList = sysModel.getSystemUserList();
			String[] toInfos = new String[systemUserList.size()];
			int i = 0;
				for(Object obj : systemUserList){
					toInfos[i] = ((SystemUserModel)obj).getUser_email() + "("+((SystemUserModel)obj).getUser_name()+" "+((SystemUserModel)obj).getSosok()+")";
					i++;
				}
			mailModel.setTosInfo(toInfos);
			/***/
			
			mailService.create4Multi(mailModel);
			
		//보류
		}else if("hold".equals(newpncrModel.getStateInfo())){
			newpncrModel.setState("4");
			this.newpncrDao.stateInfo_update(newpncrModel);
		}
		
		//협력사가 검토완료 하기전 첫번째 의견을 먼저 저장해야함. 0909 - ksy
		else if("saveRepl".equals(newpncrModel.getStateInfo())){
			//첨부파일 정보 수정 
			fileManageService.update(newpncrModel, "NEWPNCR_");
			newpncrModel.setState("1");
			
			this.newpncrDao.stateInfo_update(newpncrModel);
			newpncrModel = this.read(newpncrModel);
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(newpncrModel.getSystem_seq());
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.VU);
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
		
		}
	}

	@Override
	public void delete(NewpncrModel newpncrModel) throws Exception {
		// 첨부 파일 삭제
		fileManageService.delete(newpncrModel);
		this.newpncrDao.delete(newpncrModel);
	}
	
	public String getTitle4Status(NewpncrModel newpncrModel) {
		
		String tlt = null;
		if("0".equals(newpncrModel.getState())){
			tlt = " 신규/PN/CR 검토요청 ";
		}else if("1".equals(newpncrModel.getState())){
			if("N".equals(newpncrModel.getReject_flag())){
				tlt = " 신규/PN/CR 협력사 검토요청 ";	
			}else{
				tlt = " 신규/PN/CR 반려 ";
			}
		}else if("2".equals(newpncrModel.getState())){			
				tlt = " 신규/PN/CR 협력사 검토완료 ";	
		}else if("3".equals(newpncrModel.getState())){			
			if("N".equals(newpncrModel.getReject_flag())){
				tlt = " 신규/PN/CR 정제 완료 ";
			}else{
				tlt = " 신규/PN/CR 반려 ";
			}
		}else if("4".equals(newpncrModel.getState())){
			tlt = " 신규/PN/CR 보류 ";
		}else if("9".equals(newpncrModel.getState()) && "Y".equals(newpncrModel.getReject_flag())){
			tlt = " 신규/PN/CR 반려 ";
		}
		
		return tlt;
	}

	public String getContent4Status(NewpncrModel newpncrModel) {
		String appendStr = null;
		
		if("0".equals(newpncrModel.getState())){
			appendStr = getContent4Status0(newpncrModel);
		}else if("1".equals(newpncrModel.getState())){
			appendStr = getContent4Status1(newpncrModel);
		}else if("2".equals(newpncrModel.getState()) || "9".equals(newpncrModel.getState())){
			appendStr = getContent4Status2(newpncrModel);
		}else if("3".equals(newpncrModel.getState())){
			appendStr = getContent4Status3(newpncrModel);
		}else if("4".equals(newpncrModel.getState())){
			appendStr = getContent4Status4(newpncrModel);
		}
		
		return getBasicContent(newpncrModel) + appendStr;
	}

	private String getBasicContent(NewpncrModel newpncrModel) {
		StringBuffer sb = new StringBuffer();
		sb.append(" <tr style='height:14pt'><td align='left' valign='top' ><b>[제목]</b></td><td align='left' valign='top' > ");
		sb.append( newpncrModel.getTitle() + "</td></tr>");
		sb.append(" <tr style='height:14pt'><td align='left' valign='top' ><b>[시스템]</b></td><td align='left' valign='top'>");
		sb.append( newpncrModel.getSystem_name() + "</td></tr>");
		sb.append(" <tr style='height:14pt'><td align='left' valign='top'><b>[등록자]</b></td><td align='left' valign='top'>");
		sb.append( newpncrModel.getReg_name() + "</td></tr>");
		sb.append(" <tr style='height:14pt'><td align='left' valign='top' ><b>[등록일]</b></td><td align='left' valign='top'>");		
		sb.append( newpncrModel.getReg_date() + "</td></tr>");	
		sb.append("<tr style='height:14pt'><td align='left' colspan='2'><br/>");
		sb.append("<a href='http://pkms.sktelecom.com/'>▶ PKMS 바로가기(SK내부직원용)</a></td></tr>");
		sb.append("<tr style='height:14pt'><td align='left' colspan='2'>");
		sb.append("<a href='http://pkmss.sktelecom.com/'>▶ PKMS 바로가기(BP용)</a><br/><br/></td></tr>");
		sb.append("<tr style='height:14pt'><td colspan='2' align='left'>");
		return sb.toString();
	}

	private String getContent4Status0(NewpncrModel newpncrModel) {
		StringBuffer sb = new StringBuffer();
		if(newpncrModel.getProblem()!=null){
			newpncrModel.setProblem(newpncrModel.getProblem().replaceAll("\n", "<br/>&nbsp;"));
		}
		if(newpncrModel.getRequirement()!=null){
			newpncrModel.setRequirement(newpncrModel.getRequirement().replaceAll("\n", "<br/>&nbsp;"));
		}
		
		sb.append(" <b>[유형] :</b> " + newpncrModel.getNew_pncr_gubun() + "<br />");
		sb.append(" <b>[우선 순위] :</b> " + newpncrModel.getPriority_name() + "<br />");
		sb.append(" <b>[문제 구분] :</b> " + newpncrModel.getProblem_gubun_name() + "<br />");
		sb.append(" <b>[문제점] :</b> <br/>&nbsp;" + newpncrModel.getProblem() + "<br />");
		sb.append(" <b>[요구 사항] :</b> <br/>&nbsp;" + newpncrModel.getRequirement() + "<br/></td></tr>");
		
		return sb.toString();
	}
	
	private String getContent4Status1(NewpncrModel newpncrModel) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" <b>[유형] :</b> " + newpncrModel.getNew_pncr_gubun() + "<br />");
		sb.append(" <b>[우선 순위] :</b> " + newpncrModel.getPriority_name() + "<br />");
		sb.append(" <b>[문제 구분] :</b> " + newpncrModel.getProblem_gubun_name() + "<br />");
		
		if("N".equals(newpncrModel.getReject_flag())){
			sb.append(" <b>[검토 요청 의견] :</b> <br/>&nbsp;" + newpncrModel.getManager_comment() + "<br/></td></tr>");	
		}else{
			sb.append(" <b>[반려 사유] :</b> <br/>&nbsp;" + newpncrModel.getReject_comment() + "<br/></td></tr>");
		}
		
		
		return sb.toString();
	}
	
	private String getContent4Status2(NewpncrModel newpncrModel) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" <b>[유형] :</b> " + newpncrModel.getNew_pncr_gubun() + "<br />");
		sb.append(" <b>[우선 순위] :</b> " + newpncrModel.getPriority_name() + "<br />");
		sb.append(" <b>[문제 구분] :</b> " + newpncrModel.getProblem_gubun_name() + "<br />");
		sb.append(" <b>[검토 요청 의견] :</b> <br/>&nbsp;" + newpncrModel.getManager_comment() + "<br />");
		sb.append(" <b>[협력사 검토 요청 의견] :</b> <br/>&nbsp;" + newpncrModel.getBp_comment() + (("Y".equals(newpncrModel.getReject_flag())) ? "<br />" : "<br/></td></tr>"));
		if("Y".equals(newpncrModel.getReject_flag())){
			sb.append(" <b>[반려 사유] :</b> <br/>&nbsp;" + newpncrModel.getReject_comment() + "<br/></td></tr>");
		}
		
		return sb.toString();
	}

	private String getContent4Status3(NewpncrModel newpncrModel) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" <b>[유형] :</b> " + newpncrModel.getNew_pncr_gubun() + "<br />");
		sb.append(" <b>[우선 순위] :</b> " + newpncrModel.getPriority_name() + "<br />");
		sb.append(" <b>[문제 구분] :</b> " + newpncrModel.getProblem_gubun_name() + "<br />");
		sb.append(" <b>[검토 요청 의견] :</b> <br/>&nbsp;" + newpncrModel.getManager_comment() + "<br />");
		sb.append(" <b>[협력사 검토 요청 의견] :</b> <br/>&nbsp;" + newpncrModel.getBp_comment() + "<br />");
		if("N".equals(newpncrModel.getReject_flag())){
			sb.append(" <b>[검토 요청 의견] :</b> <br/>&nbsp;" + newpncrModel.getManager_comment() + "<br />");	
		}else{
			sb.append(" <b>[반려 사유] :</b> <br/>&nbsp;" + newpncrModel.getReject_comment() + "<br />");
		}
		sb.append(" <b>[정제 의견] :</b> <br/>&nbsp;" + newpncrModel.getRefine_comment() + "<br/></td></tr>");
		
		return sb.toString();
	}
	
	//있어도 되는지 유무 확인
	private String getContent4Status4(NewpncrModel newpncrModel) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" <b>[유형] :</b> " + newpncrModel.getNew_pncr_gubun() + "<br />");
		sb.append(" <b>[우선 순위] :</b> " + newpncrModel.getPriority_name() + "<br />");
		sb.append(" <b>[문제 구분] :</b> " + newpncrModel.getProblem_gubun_name() + "<br />");
		sb.append(" <b>[검토 요청 의견] :</b> <br/>&nbsp;" + newpncrModel.getManager_comment() + "<br />");
		sb.append(" <b>[협력사 검토 요청 의견] :</b> <br/>&nbsp;" + newpncrModel.getBp_comment() + "<br />");
		sb.append(" <b>[보류 사유] :</b> <br/>&nbsp;" + newpncrModel.getReject_comment() + "<br/></td></tr>");
		
		return sb.toString();
	}
	
	public String[] gettoAddress4Status(List<SystemUserModel> systemUserList) {
		
		String[] rets = new String[systemUserList.size()];
		int i = 0;
			for(Object obj : systemUserList){
				rets[i] = ((SystemUserModel)obj).getUser_email();
				i++;
			}
		return rets;
	}
	
	@Override
	public String excelDownload(NewpncrModel newpncrModel) throws Exception {
		//데이터
		newpncrModel.setPaging(false);
		List<?> readList =  this.readList(newpncrModel);		
		
		//Excel 헤더
		String[] headers = new String[]{"NEW_PN_CR_NO", "STATE", "NEW_PNCR_GUBUN", "GROUP1_NAME", "GROUP2_NAME", "GROUP3_NAME","SYSTEM_NAME", "REG_NAME", "SOSOK", "REG_DATE", "TITLE", "PRIORITY_NAME", "PROBLEM_GUBUN_NAME", "PROBLEM", "REQUIREMENT", "MANAGER_COMMENT","BP_COMMENT", "REFINE_COMMENT","REJECT_COMMENT"};
		
		//Excel 데이터 추출
		@SuppressWarnings("unchecked")
		List<List<String>> excelDataList = ExcelUtil.extractExcelData((List<Object>) readList, headers);

		//파일 생성 후 다운로드할 파일명
		String downloadFileName = ExcelUtil.write(newpncrModel.EXCEL_FILE_NAME, propertyService.getString("Globals.fileStorePath"), new String[]{ "번호","상태","유형","대분류","중분류","소분류","시스템","등록자", "등록팀", "등록일", "제목", "우선순위", "문제구분", "문제점", "요구사항", "성능개선 담당의견","협력업체 검토의견", "정제 의견","반려 사유"}, excelDataList);
		
		return downloadFileName;
	}

	
	/**
	 * 답글 추가 0909 - ksy
	 */
	
	@Override
	public void createRepl(NewpncrModel newpncrModel) throws Exception {
		//fileManageService.create(newpncrModel, "NEWPNCR_REPL_");
		newpncrDao.createRepl(newpncrModel);
	}
	
	@Override
	public List<NewpncrModel> readListRepl(NewpncrModel newpncrModel) throws Exception {
		List<NewpncrModel> readListRepl = newpncrDao.readListRepl(newpncrModel);
		
	/*	for(int i=0; i<readListRepl.size(); i++){
			if(null != newpncrModel.getManager_repl()){
				readListRepl.get(i).setManager_repl(newpncrModel.getManager_repl().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
			if(null != newpncrModel.getBp_repl()){
				readListRepl.get(i).setBp_repl(newpncrModel.getBp_repl().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
		}*/
		
		//첨부 파일 정보 세팅
		/*System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★");
		System.out.println(newpncrModel.getFile10());
		System.out.println(newpncrModel.getFile11());
		System.out.println(newpncrModel.getMaster_file_id());
		System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★");

		fileManageService.read(newpncrModel);*/
		return readListRepl; 
	}
	@Override
	public void updateRepl(NewpncrModel newpncrModel) throws Exception {
		newpncrDao.updateRepl(newpncrModel);
	}

	@Override
	public List<SysRoadMapModel> readListChart(NewpncrModel newpncrModel) {
		List<SysRoadMapModel> readListChart =	newpncrDao.readListChart(newpncrModel);
		
		List<SysRoadMapModel> list = new ArrayList<SysRoadMapModel>();
		if(readListChart.size() != 0)
		list.add(readListChart.get(0));
		
		for (int i = 1; i < readListChart.size(); i++) {
			if(readListChart.get(i).getCode().equals(readListChart.get(i-1).getCode())){
				SysRoadMapSubModel model = new SysRoadMapSubModel();
								   model.setCode(readListChart.get(i).getCode());
								   model.setRoad_map_seq(readListChart.get(i).getRoad_map_seq());
								   model.setSystem_seq(readListChart.get(i).getSystem_seq());
								   model.setNew_pn_cr_seq(readListChart.get(i).getNew_pn_cr_seq());
								   model.setContent(readListChart.get(i).getContent());
								   model.setStart_date(readListChart.get(i).getStart_date());
								   model.setEnd_date(readListChart.get(i).getEnd_date());
								   model.setReg_user(readListChart.get(i).getReg_user());
								   model.setReg_date(readListChart.get(i).getReg_date());
								   model.setUpdate_date(readListChart.get(i).getUpdate_date());
								   model.setUpdate_user(readListChart.get(i).getUpdate_user());
								   model.setMapping(readListChart.get(i).getMapping());
								   model.setMap_code_seq(readListChart.get(i).getMap_code_seq());
								   model.setCode_desc(readListChart.get(i).getCode_desc());
								   model.setTooltip(readListChart.get(i).getTooltip());
								   model.setName(readListChart.get(i).getName());
								   model.setHname(readListChart.get(i).getHname());
								   model.setMapping_list(readListChart.get(i).getMapping_list());
								   list.get(list.size()-1).getList().add(model);
			}else{
				list.add(readListChart.get(i));
			}
		}
		
		return list;
	}

	@Override
	public List<List<SysRoadMapModel>> readListSubChart(NewpncrModel newpncrModel) {
		List<List<SysRoadMapModel>> list = new ArrayList();
		
		//newpncr 등록시 시스템 추가될 경우
		if(newpncrModel.getSystem_seq2() != null && !"".equals(newpncrModel.getSystem_seq2())){
			newpncrModel.setSystem_seq(newpncrModel.getSystem_seq2());
			List<SysRoadMapModel> readListChart =	newpncrDao.readListChart(newpncrModel);
			
			List<SysRoadMapModel> list2 = new ArrayList<SysRoadMapModel>();
			if(readListChart.size() != 0)
			list2.add(readListChart.get(0));
			for (int j = 1; j < readListChart.size(); j++) {
				if(readListChart.get(j).getCode().equals(readListChart.get(j-1).getCode())){
					SysRoadMapSubModel model = new SysRoadMapSubModel();
									   model.setCode(readListChart.get(j).getCode());
									   model.setRoad_map_seq(readListChart.get(j).getRoad_map_seq());
									   model.setSystem_seq(readListChart.get(j).getSystem_seq());
									   model.setNew_pn_cr_seq(readListChart.get(j).getNew_pn_cr_seq());
									   model.setContent(readListChart.get(j).getContent());
									   model.setStart_date(readListChart.get(j).getStart_date());
									   model.setEnd_date(readListChart.get(j).getEnd_date());
									   model.setReg_user(readListChart.get(j).getReg_user());
									   model.setReg_date(readListChart.get(j).getReg_date());
									   model.setUpdate_date(readListChart.get(j).getUpdate_date());
									   model.setUpdate_user(readListChart.get(j).getUpdate_user());
									   model.setMapping(readListChart.get(j).getMapping());
									   model.setMap_code_seq(readListChart.get(j).getMap_code_seq());
									   model.setCode_desc(readListChart.get(j).getCode_desc());
									   model.setTooltip(readListChart.get(j).getTooltip());
									   model.setName(readListChart.get(j).getName());
									   model.setHname(readListChart.get(j).getHname());
									   model.setMapping_list(readListChart.get(j).getMapping_list());
									   list2.get(list2.size()-1).getList().add(model);
				}else{
					list2.add(readListChart.get(j));
				}
			}
			list.add(list2);
		}
		
		for (int i = 0; i < newpncrModel.getRelation_system_seq().length; i++) {
			newpncrModel.setSystem_seq(newpncrModel.getRelation_system_seq()[i]);
			List<SysRoadMapModel> readListChart =	newpncrDao.readListChart(newpncrModel);
			
			List<SysRoadMapModel> list2 = new ArrayList<SysRoadMapModel>();
			if(readListChart.size() != 0)
			list2.add(readListChart.get(0));
			for (int j = 1; j < readListChart.size(); j++) {
				if(readListChart.get(j).getCode().equals(readListChart.get(j-1).getCode())){
					SysRoadMapSubModel model = new SysRoadMapSubModel();
									   model.setCode(readListChart.get(j).getCode());
									   model.setRoad_map_seq(readListChart.get(j).getRoad_map_seq());
									   model.setSystem_seq(readListChart.get(j).getSystem_seq());
									   model.setNew_pn_cr_seq(readListChart.get(j).getNew_pn_cr_seq());
									   model.setContent(readListChart.get(j).getContent());
									   model.setStart_date(readListChart.get(j).getStart_date());
									   model.setEnd_date(readListChart.get(j).getEnd_date());
									   model.setReg_user(readListChart.get(j).getReg_user());
									   model.setReg_date(readListChart.get(j).getReg_date());
									   model.setUpdate_date(readListChart.get(j).getUpdate_date());
									   model.setUpdate_user(readListChart.get(j).getUpdate_user());
									   model.setMapping(readListChart.get(j).getMapping());
									   model.setMap_code_seq(readListChart.get(j).getMap_code_seq());
									   model.setCode_desc(readListChart.get(j).getCode_desc());
									   model.setTooltip(readListChart.get(j).getTooltip());
									   model.setName(readListChart.get(j).getName());
									   model.setHname(readListChart.get(j).getHname());
									   model.setMapping_list(readListChart.get(j).getMapping_list());
									   list2.get(list2.size()-1).getList().add(model);
				}else{
					list2.add(readListChart.get(j));
				}
			}
			list.add(list2);
		}
		
		
		return list;
	}

	@Override
	public SysRoadMapModel readChart(SysRoadMapModel sysRoadMapModel) {
		return newpncrDao.readChart(sysRoadMapModel);
	}

	@Override
	public void createRoadMapMapping(NewpncrModel newpncrModel,	SysRoadMapModel sysRoadMapModel) {

		for (int i = 0; i < sysRoadMapModel.getRoad_map_seqs().length; i++) { //road_map에 항목들 INSERT
			SysRoadMapModel model = new SysRoadMapModel();
			model.setNew_pn_cr_seq(newpncrModel.getNew_pn_cr_seq());
			model.setTitle(newpncrModel.getTitle());
			model.setRoad_map_seq(sysRoadMapModel.getRoad_map_seqs()[i]);
			newpncrDao.createRoadMapMapping(model);
		}
		
		if(null != sysRoadMapModel.getCheck_seq()){
			for (int i = 0; i < sysRoadMapModel.getCheck_seq().length; i++) { //추가된 항목들 중에서 Mapping된 항목 상태값 Y로 변경
				SysRoadMapModel model = new SysRoadMapModel();
								model.setNew_pn_cr_seq(newpncrModel.getNew_pn_cr_seq());
								model.setTitle(newpncrModel.getTitle());
								model.setRoad_map_seq(sysRoadMapModel.getCheck_seq()[i]);
				newpncrDao.updateRoadMapMapping(model);
			}
		}
		
	}

	@Override
	public List<List<SysRoadMapModel>> readListChartDetail(NewpncrModel newpncrModel) {
		List<String> list = newpncrDao.chartMappingSystemSeqList(newpncrModel);
//		for (int i = 0; i < list.size(); i++) {
//			list.get(i).getSystem_seq();
//		}
//		
		newpncrModel.setRelation_system_seq(list.toArray(new String[list.size()]));
		List<List<SysRoadMapModel>> list2 = readListSubChart(newpncrModel);
		
		return list2;
	}
	
	@Override
	public List<String> systemSeqs(NewpncrModel newpncrModel){
		return newpncrDao.chartMappingSystemSeqList(newpncrModel);
	}
	
	@Override
	public List<SysRoadMapModel> systemList(SysRoadMapModel sysRoadMapModel){
		return newpncrDao.systemList(sysRoadMapModel);
	}

	@Override
	public void deleteRoadMapMapping(NewpncrModel newpncrModel) {
		newpncrDao.deleteRoadMapMapping(newpncrModel);
		
	}
}
