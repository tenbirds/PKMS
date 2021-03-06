<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="decorator" content="mobile" />
	<meta name="viewport" content="width=480, initial-scale=-1, user-scalable=yes,target-densitydpi=device-dpi" />
<title>PKMS (패키지 관리 시스템)</title>

<script type="text/javaScript">
	var username_id = "j_username";
	var username_elem;
	
	var password_id = "j_password";
	var password_elem;
	
	$(document).ready(function($) {
		
		username_elem = document.getElementById(username_id);
		password_elem = document.getElementById(password_id);
		
		// 쿠기에 저장된 정보 폼에 세팅
		username_elem.value = getCookie(username_id);
	
		// ID 입력 란에 포커스 주기		
		if(username_elem.value == null || username_elem.value == "") {
			username_elem.focus();
		}
		
		// 로그인 실패 URL인 경우 실패 안내 문구 보여주기(새로 고침을 경우 이 문구가 계속 보여지는 문제 논의)
	//		var url = location.href;
	//		if(url.match(new RegExp("common/login/Login_Create_Fail.do"))){
		if("BadCredentialsException" == "<c:out value='${is_login_fail}'/>") {
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
		form.action = "/j_spring_security_check.do";
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


	function loginFailMsg() {
		alert("사번/비밀번호를 확인해주세요");
	}
	
	function login() {
		if (username_elem.value == '') {
			alert("사번을 입력 하세요.");
			username_elem.focus();
			return;
		}
		
		if (password_elem.value == '') {
			alert("비밀번호를 입력 하세요.");
			password_elem.focus();
			return;
		}
	
		document.getElementById("check_user_type").value = "MM";
		
		execLogin();
	}
	
	function onKeyDown(elem) {
		if (event.keyCode == 13) {
			login();
		}
	}
	
</script>


</head>

<body>
	<form id='mForm' action='/j_spring_security_check.do' method='post' onsubmit="return false;">
	
		<input type="hidden" id="_spring_security_remember_me" name="_spring_security_remember_me" value="true" />
		<input type="hidden" id="check_user_type" name="check_user_type" />
	
		<!--모바일 로그인 시작  -->
		
		<div class="mob_bg">
			<h1><b>PKMS</b> <p>Package Management system</p></h1>
			
			<div class="m_login_inp">
				<!--사번입력 -->
				<dl>
					<dt>사번</dt>
					<dd><input type="text" onkeypress="javascript:onKeyDown(this);" class="new_inp" id="j_username" name="j_username" value="" maxlength="20" style="ime-mode:disabled;" size="16" /></dd>
					<dt>비밀번호</dt>
					<dd><input type="password" onkeypress="javascript:onKeyDown(this);" class="new_inp" id="j_password" name="j_password" value="" maxlength="20" style="ime-mode:disabled;" size="16" /></dd>
				</dl>
				<!--로그인버튼 -->
				<div class="m_btn_login"><a href="javascript:login();">Log - in</a></div>
			</div>
		</div>
		<!--모바일 로그인 끝  -->
	
	</form>
</body>
</html>

