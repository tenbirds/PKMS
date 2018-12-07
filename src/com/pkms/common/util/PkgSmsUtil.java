package com.pkms.common.util;

public class PkgSmsUtil {

	public static String getMessage(String selectedStatus, String title) {
		String msg = null;
		if("1".equals(selectedStatus)) {
		} else if("23".equals(selectedStatus)) {
			msg = "(비번은 주민번호 앞 6자리)승인 요청 : http://pkmss.sktelecom.com/M.do";
		} else if("24".equals(selectedStatus)) {
//			msg = "(비번은 주민번호 앞 6자리)승인 요청 : http://pkmss.sktelecom.com/Pkms_Mobile_Read.do?sm_user="+empno+"&pkg_seq="+pkg_seq+"&selected_status=24&password="+regno;
			msg = "(비번은 주민번호 앞 6자리)승인 요청 : http://pkmss.sktelecom.com/M.do";
		} else if("26".equals(selectedStatus)) {
			msg = "(비번은 주민번호 앞 6자리)승인 요청 : http://pkmss.sktelecom.com/M.do";
		} else if("2".equals(selectedStatus)) {
			msg = "(비번은 주민번호 앞 6자리)승인 요청 : http://pkmss.sktelecom.com/M.do";
		} else if("3".equals(selectedStatus)) {
		} else if("4".equals(selectedStatus)) {
		} else if("5".equals(selectedStatus)) {
			msg = "(비번은 주민번호 앞 6자리)승인 요청 : http://pkmss.sktelecom.com/M.do";
//			msg = "승인 요청 : http://203.236.7.17/M.do";
		} else if("6".equals(selectedStatus)) {
			msg = "(비번은 주민번호 앞 6자리)승인 요청 : http://pkmss.sktelecom.com/M.do";
//			msg = "승인 요청 : http://203.236.7.17/M.do";
		} else if("7".equals(selectedStatus)) {
		} else if("8".equals(selectedStatus)) {
		} else if("9".equals(selectedStatus)) {
		} else if("99".equals(selectedStatus)) {
		}
//		return _getMessage(msg + title);
		return _getMessage(msg);
	}
	
	private static String _getMessage(String msg) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("[PKMS] " + msg);
		
		return sb.toString();
	}
}
