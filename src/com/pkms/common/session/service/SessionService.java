package com.pkms.common.session.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.common.login.service.LoginServiceIf;
import com.pkms.common.model.AbstractModel.USER_TYPE;
import com.pkms.common.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.pkms.common.session.dao.SessionDAO;
import com.pkms.usermg.bp.model.BpModel;
import com.pkms.usermg.bp.service.BpServiceIf;
import com.pkms.usermg.user.model.BpUserModel;
import com.pkms.usermg.user.model.CommonUserModel;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.BpUserServiceIf;
import com.pkms.usermg.user.service.SktUserServiceIf;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : skywarker
 * @Date : 2012. 3. 14.
 * 
 */
@Service("SessionService")
public class SessionService implements SessionServiceIf {

	@Resource(name = "sessionContextFactory")
	ObjectFactory<SessionDAO> sessionContextFactory;

	@Resource(name = "BpUserService")
	private BpUserServiceIf bpUserService;

	@Resource(name = "BpService")
	private BpServiceIf bpService;

	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;
	
	@Resource(name = "LoginService")
	private LoginServiceIf loginService;
	
	@Override
	public void create(HttpServletRequest request) throws Exception {

		String userId = null;
		String userType = null;
		HttpSession session = request.getSession();

		if (session != null) {
			userId = (String) session.getAttribute(CustomUsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERID);
			userType = (String) session.getAttribute(CustomUsernamePasswordAuthenticationFilter.CHECK_USER_TYPE_PARAM);
		}
		
		if(userId == null){
			userId = (String) request.getAttribute("j_username");
		}
		
		if(userType == null){
			userType = (String) request.getAttribute("check_user_type");
		}

		if (!StringUtils.hasLength(userId) || !StringUtils.hasLength(userType) || !(userType.equals(USER_TYPE.B.getCode()) || userType.equals(USER_TYPE.M.getCode()) || userType.equals(USER_TYPE.MM.getCode()))) {
			throw new Exception("User information is incorrect.");
		}

		CommonUserModel commonUserModel = new CommonUserModel();

		if (userType.equals(USER_TYPE.B.getCode())) {

			// BP User 조회
			BpUserModel bpUserModel = new BpUserModel();
			bpUserModel.setBp_user_id(userId);
			bpUserModel = bpUserService.read(bpUserModel);

			BpModel bpModel = new BpModel();
			bpModel.setBp_num(bpUserModel.getBp_num());
			bpModel = bpService.read(bpModel);

			// 공통 사용자 모델 세팅
			commonUserModel.setCu_id(bpUserModel.getBp_user_id());
			commonUserModel.setCu_name(bpUserModel.getBp_user_name());
			commonUserModel.setCu_email(bpUserModel.getBp_user_email());
			commonUserModel.setCu_mobile_phone(bpUserModel.getBp_user_phone1() + "-" + bpUserModel.getBp_user_phone2() + "-" + bpUserModel.getBp_user_phone3());
			commonUserModel.setCu_type(USER_TYPE.B);
			commonUserModel.setGroup_id(bpModel.getBp_num());
			commonUserModel.setGroup_name(bpModel.getBp_name());
			
			//로그인 성공 시 로그인 카운트 횟수 초기
			bpModel.setBp_user_id(userId);
			bpModel.setLogin_cnt(0);
			
			loginService.update_loginCnt(bpModel);
			
			

		} else if (userType.equals(USER_TYPE.M.getCode()) || userType.equals(USER_TYPE.MM.getCode())) {

			// SK User 조회
			SktUserModel sktUserModel = new SktUserModel();
			sktUserModel.setEmpno(userId);
			sktUserModel = sktUserService.read(sktUserModel);

			// 공통 사용자 모델 세팅
			commonUserModel.setCu_id(sktUserModel.getEmpno());
			commonUserModel.setCu_name(sktUserModel.getHname());
			commonUserModel.setCu_email(sktUserModel.getEmail());
			commonUserModel.setCu_mobile_phone(sktUserModel.getMovetelno());
			commonUserModel.setCu_type(USER_TYPE.M);
			commonUserModel.setGroup_id(sktUserModel.getIndept());
			commonUserModel.setGroup_name(sktUserModel.getSosok());

		}

		SessionDAO sessionDAO = sessionContextFactory.getObject();
		sessionDAO.create(SESSION_KEY_COMMON_USER, commonUserModel);
	}

	@Override
	public void create(String key, Object object) throws Exception {
		SessionDAO sessionDAO = sessionContextFactory.getObject();
		sessionDAO.create(key, object);
	}

	@Override
	public Object read(String key) throws Exception {
		SessionDAO sessionDAO = sessionContextFactory.getObject();
		return sessionDAO.read(key);
	}

	@Override
	public List<String> readAuth() throws Exception {
		SecurityContext sc = SecurityContextHolder.getContext();
		List<String> list = new ArrayList<String>();
		if (sc != null) {
			Authentication au = sc.getAuthentication();
			if (au != null) {
				Collection<?> aues = au.getAuthorities();
				Iterator<?> it = aues.iterator();
				while (it.hasNext()) {
					SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
					list.add(sga.getAuthority());
				}
			}
		}
		return list;
	}

}
