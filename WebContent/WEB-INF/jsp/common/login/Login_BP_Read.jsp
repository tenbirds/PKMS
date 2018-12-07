<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>PKMS (패키지 관리 시스템)</title>

<script type="text/javaScript">

	var password_id = "j_password";
	var password_elem;

	$(document).ready(function($) {
		password_elem = document.getElementById(password_id);
	});

	function cntMaxMsg(login_cnt) {
		if(login_cnt == "N"){
			alert("계정이 잠금상태 입니다. 비밀번호변경을 해주시기 바랍니다.");
		}else{
			alert("비밀번호 실패를 연속 5회 하셨습니다. 계정 잠금상태로 전환 합니다. 비밀번호를 변경해 주시기 바랍니다.");
		}
	}
	
	function loginFailMsg() {
		alert("아이디/비밀번호를 확인해주세요");
	}
	
	function login() {
		if (username_elem.value == '') {
			alert("아이디를 입력 하세요.");
			username_elem.focus();
			return;
		}

		if (password_elem.value == '') {
			alert('비밀번호를 입력 하세요.');
			password_elem.focus();
			return;
		}
		
		document.getElementById("check_user_type").value = "B";

		execLogin();
	}

	function onKeyDown(elem) {
		if (event.keyCode == 13) {
			if (elem.id == username_id) {
				password_elem.focus();
			} else if (elem.id == password_id) {
				login();
			}
		}
	}

	function addBP() {
		var option = "width=1235px, height=800px, scrollbars=yes, resizable=no, statusbar=no";
		var sWin = window.open("", "addBP", option);

		var form = document.getElementById("mForm");
		form.target = "addBP";
		form.action = "<c:url value='/common/login/BpAdd_Popup_Read.do'/>";
		form.submit();
		sWin.focus();
	}
	
	function notice_popup(){
		window.open("/Notice_Popup.do?type=B","bp_notice_popup","width=440, height=420, scrollbars=no, resizable=no, statusbar=no");
	}
	
	//id pass 찾기
	function password_popup_search(gubun){
		var url = "/usermg/bp/Password_Popup_Search.do?url_gubun="+gubun;
		var popName = "password_popup_search";
		var option = "width=700, height=300, scrollbars=yes, resizable=yes, statusbar=no, toolbar=no, menubar=no, location=no";

		window.open(url, popName, option);
	}
</script>

</head>

<!-- <body onload="javascript:notice_popup();"> -->
<body>
	<form id='mForm' action='/j_spring_security_check.do' method='post' onsubmit="return false;">

		<!--contents 레이아웃 시작-->
		<div id="centercolumn">
			<input type="hidden" id="_spring_security_remember_me" name="_spring_security_remember_me" value="true" />
			<input type="hidden" id="check_user_type" name="check_user_type" />

			<!--로그인 시작-->
			<div class="login_bp">

				<!--아이디,사번,비밀번호 입력 및 저장 시작 -->
				<div class="login_inp_bp">
					<!--입력 -->
					<ul class="login_form_bp">
						<li>
							<img id="login_id_label" src="/images/login_id.png" alt="아이디" />
							<input tabindex="3" id="j_username" name="j_username" type="text" class="inp" style="width: 150px" maxlength="20" style="ime-mode:disabled;" onkeypress="javascript:onKeyDown(this);" value="" />
						</li>
						<li>
							<img src="/images/login_pass.png" alt="비밀번호" />
							<input tabindex="4" id="j_password" name="j_password" type="password" class="inp" style="width: 150px" maxlength="20" onkeypress="javascript:onKeyDown(this);" value="" />
						</li>
						<li style="text-align: right; margin-right: 30px;">
							<a href="javascript:password_popup_search('id')">
								아이디 찾기
							</a>
							/
							<a href="javascript:password_popup_search('pass')">
								비밀번호 변경
							</a>
						</li>
					</ul>

					<!--로그인버튼 -->
					<div class="btn_login_bp">
						<a tabindex="5" href="javascript:login();"><img src="/images/login_btn_login.png" alt="로그인" /></a>
					</div>
				</div>
				<!--아이디,사번,비밀번호 입력 및 저장 끝 -->

				<!--협력업체등록요청 -->
				<div id="add_bp" class="btn_subcontractor"><a href="javascript:addBP();"><img src="/images/login_btn_subcontractor.gif" alt="협력업체등록요청" /></a></div>

			</div>
			<!--로그인 끝-->
		</div>
		<!--contents 레이아웃 끝-->

	</form>


</body>
</html>

