<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>PKMS (패키지 관리 시스템)</title>

<script type="text/javaScript">

	function loginFailMsg() {
		alert("사번을 확인해주세요");
	}
	
	function login() {
		if (username_elem.value == '') {
			alert("사번을 입력 하세요.");
			username_elem.focus();
			return;
		}

		document.getElementById("check_user_type").value = "M";
		
		execLogin();
	}

	function onKeyDown(elem) {
		if (event.keyCode == 13) {
			login();
		}
	}
	
	function notice_popup(){
		window.open("/Notice_Popup.do?type=S","bp_notice_popup","width=440, height=380, scrollbars=no, resizable=no, statusbar=no");
	}
	
</script>

</head>

<!-- <body onload="javascript:notice_popup();"> -->
<body>
	<form id='mForm' action='/j_spring_security_check.do' method='post' onsubmit="return false;">
	<div class="wraper_gray">	
		<!--contents 레이아웃 시작-->
		<div id="" class="login_wrap">
			<input type="hidden" id="_spring_security_remember_me" name="_spring_security_remember_me" value="true" />
			<input type="hidden" id="check_user_type" name="check_user_type" />

			<!--로그인 시작-->
			<div class="login">

				<!--아이디,사번,비밀번호 입력 및 저장 시작 -->
				<div class="login_inp_skt fl_left">
					<dl>
						<dt><b>PKMS</b> <p>Package Management system</p></dt>
					</dl>
					<!--입력 -->
					<ul class="login_form_skt">
						<li>
							<img id="login_id_label" src="/images/login_num.png" alt="사번" />
							<input tabindex="3" id="j_username" name="j_username" type="text" style="Width:97%;" maxlength="20" style="ime-mode:disabled;" onkeypress="javascript:onKeyDown(this);" value="" />
						</li>
						<li></li>
					</ul>

					<!--로그인버튼 -->
					<div class="btn_login">
						<a tabindex="5" href="javascript:login();">Log - in</a>
					</div>
				</div>
				<!--아이디,사번,비밀번호 입력 및 저장 끝 -->
					
				<div class="callcenter fl_left">
					<dl>
						<dt>&nbsp;</dt>
						<dd><b>- 문 의 -</b></dd>
						<dd>유형만 ( nabiiyhm@sk.com )</dd>
						<dd>윤강근 ( kkyoon@sk.com )</dd>
						<dd>김준희대리 ( jhkim@in-soft.co.kr )</dd>
						<dd><span id="add_bp" class="btn_subcontractor" ><a href="javascript:addBP();">협력업체등록요청</a></span></dd>
					</dl>
					<!--협력업체등록요청 -->
					<p class="txt_area">IE11 또는 Chrome에서 최적화 되었습니다.</p>
				</div>
			</div>
			<!--로그인 끝-->
			
		</div>
		<!--contents 레이아웃 끝-->
	</div>
	</form>
</body>
</html>

