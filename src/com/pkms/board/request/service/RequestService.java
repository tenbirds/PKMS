package com.pkms.board.request.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.board.request.dao.RequestDao;
import com.pkms.board.request.model.RequestModel;
import com.pkms.common.mail.model.MailModel;
import com.pkms.common.mail.service.MailServiceIf;
import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.usermg.auth.model.AuthModel.AUTH_ROLE;
import com.pkms.usermg.user.model.BpUserModel;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.BpUserServiceIf;
import com.pkms.usermg.user.service.SktUserServiceIf;

@Service("RequestService")
public class RequestService implements RequestServiceIf {
	
	@Resource(name = "RequestDao")
	private RequestDao requestDao;
	
	@Resource(name = "MailService")
	private MailServiceIf maService;
	
	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;
	
	@Resource(name = "BpUserService")
	private BpUserServiceIf bpUserService;
	
	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	@Override
	public void create(RequestModel requestModel) throws Exception {
		
		requestModel.setRequest_seq(this.requestDao.create(requestModel));
		
		requestModel = this.read(requestModel);
		
		MailModel mailModel = new MailModel();
		SktUserModel sktUserModel = new SktUserModel();
		sktUserModel.setEmpno(requestModel.getSystem_user_id());
		sktUserModel = sktUserService.read(sktUserModel);
		
		
		mailModel.setMsgSubj("검증 센터 이용 신청");
		mailModel.setMsgText(this.getContent4Status(requestModel, "1"));
		mailModel.setTo(sktUserModel.getEmail());
		mailModel.setToInfo(sktUserModel.getEmail());
		
		maService.create(mailModel);
	}

	@Override
	public RequestModel read(RequestModel requestModel) throws Exception {
		requestModel = requestDao.read(requestModel);
		return requestModel;
	}

	@Override
	public List<?> readList(RequestModel requestModel) throws Exception {
		
		/*
		 * DAO에서 정보 목록 조회
		 */
		List<?> resultList = requestDao.readList(requestModel);
		
		/*
		 * 목록 전체 개수 조회
		 */
		int totalCount = requestDao.readTotalCount(requestModel);
		requestModel.setTotalCount(totalCount);
		return resultList;
	}

	@Override
	public void update(RequestModel requestModel) throws Exception {
		
		// key = 요청상태 1 : 요청, 2 : 승인, 3 : 반려
		
		if("2".equals(requestModel.getStateInfo())){
			
			this.requestDao.update_Approve(requestModel);	
			
			requestModel = this.read(requestModel);
			
			List<String> authList = sessionService.readAuth();		
	
			//Spring Security 권한 체크 
			for(String auth : authList){				
				// BP 권한인 경우: 무조건 BP
				if("ROLE_BP".equals(auth)) {
					BpUserModel bpUserModel = new BpUserModel();
					bpUserModel.setBp_user_id(requestModel.getReg_user());
					bpUserModel = bpUserService.read(bpUserModel);
					
					MailModel mailModel = new MailModel();
					mailModel.setMsgSubj("검증 센터 이용 신청 결과");
					mailModel.setMsgText(this.getContent4Status(requestModel, "2"));
					mailModel.setTo(bpUserModel.getBp_user_email());
					mailModel.setToInfo(bpUserModel.getBp_user_email());
					
					maService.create(mailModel);
				}
			}
		}else if("3".equals(requestModel.getStateInfo())){
			
			this.requestDao.update_Reject(requestModel);
			
			requestModel = this.read(requestModel);
			
			List<String> authList = sessionService.readAuth();
			
			//Spring Security 권한 체크 
			for(String auth : authList){				
				// BP 권한인 경우: 무조건 BP
				if("ROLE_BP".equals(auth)) {
					BpUserModel bpUserModel = new BpUserModel();
					bpUserModel.setBp_user_id(requestModel.getReg_user());
					bpUserModel = bpUserService.read(bpUserModel);
					
					MailModel mailModel = new MailModel();
					
					mailModel.setMsgSubj("검증 센터 이용 신청 결과");
					mailModel.setMsgText(this.getContent4Status(requestModel, "3"));
					mailModel.setTo(bpUserModel.getBp_user_email());
					mailModel.setToInfo(bpUserModel.getBp_user_email());
					
					maService.create(mailModel);
				}
			}
		}
		else{
			this.requestDao.update(requestModel);
		}
	}

	@Override
	public void delete(RequestModel requestModel) throws Exception {
		this.requestDao.delete(requestModel);
	}
	
	public String getContent4Status(RequestModel requestModel, String selectedStatus) {
		String appendStr = null;
		
		if("1".equals(selectedStatus)){
			appendStr = getContent4Status1(requestModel);
		}else if("2".equals(selectedStatus)){
			appendStr = getContent4Status2(requestModel);
		}else if("3".equals(selectedStatus)){
			appendStr = getContent4Status3(requestModel);
		}
		
		return getBasicContent(requestModel)+appendStr;
	}

	private String getBasicContent(RequestModel requestModel){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" - 제목: " + requestModel.getGoal() + "<br />");
		sb.append(" - 시스템: " + requestModel.getSystem_name() + "<br />");
		sb.append("<br />");
		
		return sb.toString();
	}

	private String getContent4Status1(RequestModel requestModel) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" - 검증 요청 회사 : " + requestModel.getPtn_name1() + "<br />");
		sb.append(" - 검증 요청 이용기간 : " + requestModel.getRequest_date_start() + "~"+ 
					requestModel.getRequest_date_end() + "<br />");
		
		return sb.toString();
	}
	
	private String getContent4Status2(RequestModel requestModel) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" - 승인자 : " + requestModel.getSystem_user_name() + "<br />");
		sb.append(" - 검증 센터 이용 신청이 승인되었습니다."+ "<br />");
		
		return sb.toString();
	}
	
	private String getContent4Status3(RequestModel requestModel) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" - 승인자 : " + requestModel.getSystem_user_name() + "<br />");
		sb.append(" - 검증 센터 이용 신청이 반려되었습니다."+ "<br />");
		
		return sb.toString();
	}

}
