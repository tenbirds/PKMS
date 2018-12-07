package com.pkms.usermg.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.common.mail.model.MailModel;
import com.pkms.common.mail.service.MailServiceIf;
import com.pkms.common.model.AbstractModel.USER_TYPE;
import com.pkms.usermg.auth.model.AuthModel.AUTH_ROLE;
import com.pkms.usermg.auth.model.AuthUserModel;
import com.pkms.usermg.auth.service.AuthUserServiceIf;
import com.pkms.usermg.bp.model.BpModel;
import com.pkms.usermg.bp.model.BpModel.BP_APPROVAL_STATE;
import com.pkms.usermg.bp.service.BpServiceIf;
import com.pkms.usermg.user.dao.BpUserDAO;
import com.pkms.usermg.user.model.BpUserModel;
import com.pkms.usermg.user.model.SktUserModel;

/**
 * 
 * 
 * BP 사용자를 위한 서비스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 12.
 * 
 */
@Service("BpUserService")
public class BpUserService implements BpUserServiceIf {

	@Resource(name = "AuthUserService")
	private AuthUserServiceIf authUserService;

	@Resource(name = "BpService")
	private BpServiceIf bpService;

	@Resource(name = "MailService")
	private MailServiceIf maService;

	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;

	@Resource(name = "BpUserDAO")
	private BpUserDAO bpUserDAO;

	@Override
	public void create(BpUserModel userModel) throws Exception {

		/*
		 * 생성 시 권한에 따른 approval_state의 값에 대한 정의는 요청되는 JSP에서 지정 되어 넘어 옴.
		 */

		// SHA PW 인코딩
		if (StringUtils.hasLength(userModel.getBp_user_pw())) {
			ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
			userModel.setBp_user_pw(shaPasswordEncoder.encodePassword(userModel.getBp_user_pw(), null));
		}

		bpUserDAO.create(userModel);

		// 권한 생성
		AuthUserModel authUserModel = new AuthUserModel();
		authUserModel.setUser_id(userModel.getBp_user_id());
		authUserModel.setAuth_role(AUTH_ROLE.ROLE_BP);
		authUserModel.setUser_gubun(USER_TYPE.B.getCode());
		authUserService.create(authUserModel);

		// 승인 요청시 승인 담당자에게 이메일 전송
		if (BP_APPROVAL_STATE.REQUEST.getCode().equals(userModel.getApproval_state())) {
			sendMailContet(userModel);
		}

	}

	@Override
	public BpUserModel read(BpUserModel userModel) throws Exception {
		return bpUserDAO.read(userModel);
	}

	@Override
	public int readCount(BpUserModel userModel) throws Exception {
		return bpUserDAO.readCount(userModel);
	}

	@Override
	public List<BpUserModel> readList(BpUserModel userModel) throws Exception {
		return bpUserDAO.readList(userModel);
	}

	@Override
	public void update(BpUserModel userModel) throws Exception {

		// SHA PW 인코딩
		if (StringUtils.hasLength(userModel.getBp_user_pw())) {
			ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
			userModel.setBp_user_pw(shaPasswordEncoder.encodePassword(userModel.getBp_user_pw(), null));
		}

		bpUserDAO.update(userModel);
	}

	@Override
	public void updateYN(BpUserModel userModel) throws Exception {

		bpUserDAO.updateYN(userModel);
	}

	private void sendMailContet(BpUserModel bpUserModel) throws Exception {

		BpModel bpModel = new BpModel();
		bpModel.setBp_num(bpUserModel.getBp_num());
		bpModel = bpService.read(bpModel);

		MailModel mailModel = new MailModel();
		SktUserModel sktUserModel = new SktUserModel();
		sktUserModel.setEmpno(bpModel.getApproval_user_id());
		sktUserModel = sktUserService.read(sktUserModel);
		mailModel.setTo(sktUserModel.getEmail());
		mailModel.setToInfo(sktUserModel.getEmail());
		
		mailModel.setMsgSubj("* 협력업체 담당자 승인 요청");
		StringBuffer sb = new StringBuffer();
		sb.append("    업체명: " + bpModel.getBp_name()).append("\n");
		sb.append("    사업자등록번호: " + bpModel.getBp_num()).append("\n");
		sb.append("    전화번호: " + bpModel.getBp_tel1() + "-" + bpModel.getBp_tel2() + "-" + bpModel.getBp_tel3()).append("\n");
		sb.append("    담당자: ").append("\n");
		sb.append("      [이름: " + bpUserModel.getBp_user_name() + ", 아이디: " + bpUserModel.getBp_user_id() + ", 전화번호: " + bpUserModel.getBp_user_phone1() + "-" + bpUserModel.getBp_user_phone2() + "-" + bpUserModel.getBp_user_phone3() + ", 이메일: " + bpUserModel.getBp_user_email()).append("\n");

		mailModel.setMsgText(sb.toString());

		maService.create(mailModel);

	}

	@Override
	public List<BpUserModel> bp_sys_readList(BpUserModel userModel) throws Exception {
		return bpUserDAO.bp_sys_readList(userModel);
	}
	
	// 협력업체 담당자 삭제시 해당 시스템의 담당자 목록 동시 삭제 - ksy (0826) 
	@Override
	public void deleteDamdang(BpUserModel userModel) throws Exception {
		bpUserDAO.deleteDamdang(userModel);
	}
	
}
