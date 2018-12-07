package com.pkms.common.intercept;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.usermg.user.model.CommonUserModel;
import com.wings.util.ObjectUtil;

public class Intercepter {

	private final String SESSION_USER_ID = "session_user_id";
	private final String SESSION_USER_NAME = "session_user_name";
	private final String SESSION_USER_TYPE = "session_user_type";
	private final String SESSION_USER_MOBILE_PHONE = "session_user_mobile_phone";
	private final String SESSION_USER_GROUP_ID = "session_user_group_id";
	private final String SESSION_USER_GROUP_NAME = "session_user_group_name";
	private final String SESSION_USER_EMAIL = "session_user_email";

	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;

	private Logger log = Logger.getLogger(this.getClass());

	public void pointCutBefore(JoinPoint joinPoint) {

		try {
			Object[] args = joinPoint.getArgs();
			for (Object arg : args) {
				CommonUserModel commonUserModel = (CommonUserModel) sessionService.read(SessionServiceIf.SESSION_KEY_COMMON_USER);
				ObjectUtil.setObjectFieldValue(arg, SESSION_USER_ID, commonUserModel.getCu_id());
				ObjectUtil.setObjectFieldValue(arg, SESSION_USER_NAME, commonUserModel.getCu_name());
				ObjectUtil.setObjectFieldValue(arg, SESSION_USER_TYPE, commonUserModel.getCu_type());
				ObjectUtil.setObjectFieldValue(arg, SESSION_USER_MOBILE_PHONE, commonUserModel.getCu_mobile_phone());
				ObjectUtil.setObjectFieldValue(arg, SESSION_USER_GROUP_ID, commonUserModel.getGroup_id());
				ObjectUtil.setObjectFieldValue(arg, SESSION_USER_GROUP_NAME, commonUserModel.getGroup_name());
				ObjectUtil.setObjectFieldValue(arg, SESSION_USER_EMAIL, commonUserModel.getCu_email());
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

	}

	public void pointCutAfterThrowing(JoinPoint joinPoint, Throwable error) {
		log.info("logAfterThrowing() is running!");
		log.info("hijacked : " + joinPoint.getSignature().getName());
		log.info("Exception : " + error);
		log.info("******");
	}

}