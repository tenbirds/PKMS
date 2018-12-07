package com.pkms.common.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.Assert;

import com.pkms.common.model.AbstractModel.USER_TYPE;
import com.pkms.common.security.token.BpUserAuthenticationToken;
import com.pkms.common.security.token.SktUserAuthenticationToken;
import com.pkms.common.security.token.SktUserMobileAuthenticationToken;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 3.
 * 
 */
public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private String usernameParameter = "j_username";
	private String passwordParameter = "j_password";

	public static final String CHECK_USER_TYPE_PARAM = "check_user_type";
	public static final String SPRING_SECURITY_LAST_USERID = "SPRING_SECURITY_LAST_USERNAME";

//	public static final String BP_USER_PARAM_VALUE = "B";
//	public static final String SKT_USER_PARAM_VALUE = "M";

	private boolean postOnly = true;

	// ~ Constructors
	// ===================================================================================================

	public CustomUsernamePasswordAuthenticationFilter() {
		super("/j_spring_security_check");
	}

	// ~ Methods
	// ========================================================================================================

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = null;

		String checkUserType = request.getParameter(CHECK_USER_TYPE_PARAM);
		String loginType = request.getParameter("loginYype");
		
		if(null == loginType ){
			loginType = "Y";
		}
		

		if (checkUserType == null) {
			throw new AuthenticationServiceException("user type error: check_user_type is null!");
		}
		
		if (checkUserType.equals(USER_TYPE.B.getCode())) {
			authRequest = new BpUserAuthenticationToken(username, password);

		} else if (checkUserType.equals(USER_TYPE.M.getCode())) {
			authRequest = new SktUserAuthenticationToken(username, username);
		} else if (checkUserType.equals(USER_TYPE.MM.getCode())) {
			authRequest = new SktUserMobileAuthenticationToken(username, password);
		} else {
			throw new AuthenticationServiceException("user type not supported: " + checkUserType);
		}

		// Place the last username attempted into HttpSession for views
		HttpSession session = request.getSession(false);

 		if (session != null || getAllowSessionCreation()) {
			//request.getSession().setAttribute(SPRING_SECURITY_LAST_USERID, TextEscapeUtils.escapeEntities(username));
 			request.getSession().setAttribute(SPRING_SECURITY_LAST_USERID, username); // 2012-09-04 수정 특수문자 치환하는 작업을 제거
			request.getSession().setAttribute(CHECK_USER_TYPE_PARAM, checkUserType);
			request.getSession().setAttribute("loginType", loginType);
			
			if(loginType.equals("N")){
				request.getSession().setAttribute("pkg_seq", request.getParameter("pkg_seq"));
			}
			
		}

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);

	}

	/**
	 * Enables subclasses to override the composition of the password,
	 * such as by including additional values and a separator.
	 * <p>
	 * This might be used for example if a postcode/zipcode was required
	 * in addition to the password. A delimiter such as a pipe (|)
	 * should be used to separate the password and extended value(s).
	 * The <code>AuthenticationDao</code> will need to generate the
	 * expected password in a corresponding manner.
	 * </p>
	 * 
	 * @param request
	 *            so that request attributes can be retrieved
	 * 
	 * @return the password that will be presented in the
	 *         <code>Authentication</code> request token to the
	 *         <code>AuthenticationManager</code>
	 */
	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	/**
	 * Enables subclasses to override the composition of the username,
	 * such as by including additional values and a separator.
	 * 
	 * @param request
	 *            so that request attributes can be retrieved
	 * 
	 * @return the username that will be presented in the
	 *         <code>Authentication</code> request token to the
	 *         <code>AuthenticationManager</code>
	 */
	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	/**
	 * Provided so that subclasses may configure what is put into the
	 * authentication request's details property.
	 * 
	 * @param request
	 *            that an authentication request is being created for
	 * @param authRequest
	 *            the authentication request object that should have its
	 *            details set
	 */
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Sets the parameter name which will be used to obtain the username
	 * from the login request.
	 * 
	 * @param usernameParameter
	 *            the parameter name. Defaults to "j_username".
	 */
	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	/**
	 * Sets the parameter name which will be used to obtain the password
	 * from the login request..
	 * 
	 * @param passwordParameter
	 *            the parameter name. Defaults to "j_password".
	 */
	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this
	 * filter. If set to true, and an authentication request is received
	 * which is not a POST request, an exception will be raised
	 * immediately and authentication will not be attempted. The
	 * <tt>unsuccessfulAuthentication()</tt> method will be called as if
	 * handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return usernameParameter;
	}

	public final String getPasswordParameter() {
		return passwordParameter;
	}

}
