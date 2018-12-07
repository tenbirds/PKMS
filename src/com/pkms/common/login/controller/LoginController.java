package com.pkms.common.login.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.security.filter.CustomUsernamePasswordAuthenticationFilter;
import com.pkms.common.login.service.LoginServiceIf;
import com.pkms.common.model.AbstractModel.USER_TYPE;
import com.pkms.common.security.provider.SktUserAuthenticationProvider;
import com.pkms.common.security.token.SktUserAuthenticationToken;
import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.usermg.user.model.CommonUserModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ObjectUtil;
import com.wings.util.UserAgentUtil;

import com.pkms.usermg.bp.model.BpModel;

/**
 * 
 * 
 * 로그인/로그아웃을 처리하는 Controller.<br>
 * 기본 기능은 spring security에서 제공하는 서블릿을 이용하고 기능 수행 전후에 처리 해야 하는 것들 만 구현
 * 한다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 3. 16.
 * 
 */
@Controller
public class LoginController {
	static Logger logger = Logger.getLogger(LoginController.class);

	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;

	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "LoginService")
	private LoginServiceIf loginService;
	
	private String loginType = "Y";
	private String pkg_seq = "";
	private String selected_status = "";
	
	private static int bad_cnt = 0;

	@RequestMapping(value = "/Login_Create_After.do")
	public String createAfter(HttpServletRequest request, Model model) throws Exception {
		/*
		 * 로그인 성공 이후에 작업해야 할 사항 구현
		 */
		
		HttpSession session = request.getSession();
		String retUrl = null;

		loginType = (String) session.getAttribute("loginType");
		
		if(loginType == null){
			loginType = (String) request.getAttribute("loginType");
		}
		
		if(loginType.equals("N")){
			retUrl = "/pkgmg/pkg/Pkg_Popup_Read";
			pkg_seq = (String) session.getAttribute("pkg_seq");
		}

		sessionService.create(request);
		
		if(loginType.equals("N")){
			return "redirect:/pkgmg/pkg/Pkg_Popup_Read.do?retUrl="+retUrl+"&pkg_seq="+pkg_seq;
		}else if(loginType.equals("M")){
			return "redirect:/pkgmg/mobile/Mobile_Read.do?selected_status="+selected_status+"&pkg_seq="+pkg_seq;
		}else{
			return "redirect:/Main.do";
		}

	}

	@RequestMapping(value = "/Login_Create_Fail.do")
	public String createFail(HttpServletRequest request, Model model) throws Exception {
		/*
		 * 로그인 실패 이후에 작업해야 할 사항 구현
		 */
		try {
			HttpSession session = request.getSession();
			Exception ex = null;

			if(session != null) {
				ex = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
			}
			
			if (ex != null && ex instanceof BadCredentialsException) {
				model.addAttribute("is_login_fail", "BadCredentialsException");
				bad_cnt += 1;
				
				//비밀번호 틀렸을 시 해당 아이디 임계치 cnt + 1
				String userId = null;
				String userType = null;
				
				if (session != null) {
					userId = (String) session.getAttribute(CustomUsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERID);
					userType = (String) session.getAttribute(CustomUsernamePasswordAuthenticationFilter.CHECK_USER_TYPE_PARAM);
				}
				
				if(userId == null){
					userId = (String) request.getAttribute("j_username");
				}
				
				if (userType.equals(USER_TYPE.B.getCode())) {
					BpModel bpModel = new BpModel();
					
					bpModel.setBp_user_id(userId);
					
					bpModel = loginService.read_loginCnt(bpModel);
					int l_cnt = bpModel.getLogin_cnt();
					if(l_cnt < 5){
						l_cnt++;
						bpModel.setLogin_cnt(l_cnt);
						bpModel.setUse_yn("N");
						loginService.update_loginCnt(bpModel);
						if(l_cnt == 5){
							model.addAttribute("fail_loginCnt", "5");
						}
					}else if(l_cnt == 5){
						model.addAttribute("fail_loginCnt", "N");
					}
				}
			} else {
				model.addAttribute("is_login_fail", "UnknownException");
			}
		} catch (Exception ex) {
		}

		return _read(request);
	}

	@RequestMapping(value = "/Login_Read.do")
	public String read(HttpServletRequest request, Model model) throws Exception {
		return _read(request);
	}

	@Autowired SktUserAuthenticationProvider sktUserAuthenticationProvider;
	
	
	private String _read(HttpServletRequest request) throws Exception {
		
		if (UserAgentUtil.isMobile(request)) {
			return "/common/login/Login_Mobile_Read";
		} else {
			String serverName = propertyService.getString("ServerName");
			
			if("MOBILE".equals(serverName)){
				return "/common/login/Login_Mobile_Read";
			}
			
			if("ALL".equals(serverName)) {
				return "/common/login/Login_Read";
			} else if("SKT".equals(serverName)) {
				String userId = request.getHeader("SM_USER");
//				userId = "1107749";
				if(null!=userId){
					if(bad_cnt > 1){
						bad_cnt = 0;
						return "/common/login/Login_SKT_Read";
					}else{
						/*
						UsernamePasswordAuthenticationToken authRequest = new SktUserAuthenticationToken(userId, userId); 
						
					    // Authenticate the user 
					    Authentication authentication = sktUserAuthenticationProvider.authenticate(authRequest); 
					    SecurityContext securityContext = SecurityContextHolder.getContext(); 
					    securityContext.setAuthentication(authentication); 
					 
					    // Create a new session and add the security context. 
					    HttpSession session = request.getSession(true); 
					    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
					    
					    request.setAttribute("check_user_type", "M");
					    request.setAttribute("j_username", userId);
					    request.setAttribute("loginType", "Y");
					    
					    return "forward:/Login_Create_After.do";
					    */
					    request.setAttribute("check_user_type", "M");
					    request.setAttribute("j_username", userId);
					    request.setAttribute("loginType", "Y");
					    return "/common/login/Login_SSO_Read";
					}
//					return "redirect:/Sso_Read.do";
				}else{
					return "/common/login/Login_SKT_Read";
				}
				
			} else {
				return "/common/login/Login_BP_Read";
			}
		}
	}
	
	@RequestMapping(value="/Sso_Read.do")
	public void SsoRead(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		loginService.SsoLogin(request, response, model, loginType);
		
	}
	
	@RequestMapping(value = "/Pkms_Work_Read.do")
	public void Work_Read(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		
		loginType = "N";
		
		loginService.SsoLogin(request, response, model, loginType);
		
	}
	
	@RequestMapping(value = "/Pkms_Mobile_Read.do")
	public void Mobile_Read(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		loginType = "M";
		pkg_seq = request.getParameter("pkg_seq");
		selected_status = request.getParameter("selected_status");
		loginService.SsoLogin(request, response, model, loginType);
	}

	@RequestMapping(value = "/Login_Delete.do")
	public String delete(HttpServletRequest request, Model model) throws Exception {
		request.removeAttribute("check_user_type");
		request.removeAttribute("j_username");
		request.removeAttribute("loginType");

		/*
		 * 로그아웃 이전에 작업해야 할 사항 구현
		 * 
		 */
		return "redirect:/j_spring_security_logout.do";
	}

	@RequestMapping(value = "/Login_Delete_After.do")
	public String deleteAfter(HttpServletRequest request, Model model) throws Exception {
		/*
		 * 로그아웃 이후에 작업해야 할 사항 구현
		 */
		//redirect로 하면 세션타임아웃 처리 되기 때문에
		return "forward:/Login_Read.do";
	}

	@RequestMapping(value = "/InvalidSession_Read.do")
	public String invalidSession(Model model) throws Exception {
		return "/common/login/InvalidSession_Read";
	}

	@RequestMapping(value = "/Access_Denied.do")
	public String accessDenied(Model model) throws Exception {
		/*
		 * 접근 권한이 없는 페이지가 호출 된 경우 작업 해야 할 사항 구현
		 */
		return "redirect:/Main.do";
	}
	
	@RequestMapping(value = "/Notice_Popup.do")
	public String noticePopup() throws Exception {
		return "/common/Notice_Popup";
	}
	
}
