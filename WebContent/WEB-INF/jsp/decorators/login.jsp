<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=3.0, minimum-scale=0.5, user-scalable=yes" />

	<title><decorator:title default="PKMS (패키지 관리 시스템)" /></title>
	<link rel="stylesheet" type="text/css" href="/css/base.css" />

	<script type="text/javascript" src="/js/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="/js/common.js"></script>

	<script type="text/javaScript">
	
	//	fn_invaildSession(false);//frameset 용
		
		var username_id = "j_username";
		var username_elem;
	
		$(document).ready(function($) {
			
			username_elem = document.getElementById(username_id);
			
			// 쿠기에 저장된 정보 폼에 세팅
			username_elem.value = getCookie(username_id);
	
			// ID 입력 란에 포커스 주기		
			if(username_elem.value == null || username_elem.value == "") {
				username_elem.focus();
			}
			
			// 로그인 실패 URL인 경우 실패 안내 문구 보여주기(새로 고침을 경우 이 문구가 계속 보여지는 문제 논의)
	//		var url = location.href;
	//		if(url.match(new RegExp("common/login/Login_Create_Fail.do"))){
		
			if("5" == "<c:out value='${fail_loginCnt}'/>") {
				var login_cnt = "5";
				cntMaxMsg(login_cnt);
				return false;
			}else if("N" == "<c:out value='${fail_loginCnt}'/>"){
				var login_cnt = "N";
				cntMaxMsg(login_cnt);
				return false;
			}else if("BadCredentialsException" == "<c:out value='${is_login_fail}'/>") {
				loginFailMsg();
				return false;
			}else if("UnknownException" == "<c:out value='${is_login_fail}'/>"){
				alert("알수 없는 오류가 발생하여 로그인을 할 수 없습니다.\n관리자에게 문의 하세요.");
				return false;
	//		}else if("is_invalid_session" == "<c:out value='${is_invalid_session}'/>"){
	//			return false;
			}
		});
	
		function execLogin() {
			// 폼데이터 저장
			saveData(username_id, username_elem.value);
	
			// 로그인 폼 전송
			var form = document.getElementById("mForm");
			form.target = "_self";
// 			form.action = "/j_spring_security_check.do";
			form.submit();
		}
	
		function setCookie(name, value, expires) {
			document.cookie = name + "=" + escape(value) + "; path=/; expires="
					+ expires.toGMTString();
		}
		
		function getCookie(Name) {
			var search = Name + "=";
			if (document.cookie.length > 0) { // 쿠키가 설정되어 있다면
				offset = document.cookie.indexOf(search);
				if (offset != -1) { // 쿠키가 존재하면
					offset += search.length;
					// set index of beginning of value
					end = document.cookie.indexOf(";", offset);
					// 쿠키 값의 마지막 위치 인덱스 번호 설정
					if (end == -1)
						end = document.cookie.length;
					return unescape(document.cookie.substring(offset, end));
				}
			}
			return "";
		}
		
		function saveData(key, value) {
			var expdate = new Date();
			expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 30);
			setCookie(key, value, expdate);
		}
		
		function setData(key, elem) {
			elem.value = getCookie(key);
	
		}
		
	</script>

	<decorator:head/>
</head>

<body onload="<decorator:getProperty property='body.onload' />" onunload="<decorator:getProperty property='body.onunload' />">
	

			<decorator:body />

</body>
</html>
