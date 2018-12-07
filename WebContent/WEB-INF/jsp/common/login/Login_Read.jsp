<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>PKMS (패키지 관리 시스템)</title>

<script type="text/javaScript">

	var password_id = "j_password";
	var usertype_id = "check_user_type_radio";
	var loginlabel_id = "login_id_label";
	var password_elem;

	$(document).ready(function($) {
		password_elem = document.getElementById(password_id);
		
		var check_user_type = getCookie(usertype_id);
		var userTypeList = document.getElementsByName(usertype_id);
		for ( var i = 0; i < userTypeList.length; i++) {
			if (userTypeList[i].value == check_user_type) {
				userTypeList[i].checked = "true";
	
				fn_selectUserType(userTypeList[i].value);
			}
		}
	});

	function loginFailMsg() {
		alert("아이디/사번 또는 비밀번호를 확인해주세요");
	}

	function login() {

		if (username_elem.value == '') {
			alert(document.getElementById(loginlabel_id).alt + "을(를) 입력 하세요.");
			username_elem.focus();
			return;
		}

		var tempUserType = null;
		var userTypeList = document.getElementsByName(usertype_id);
		for ( var i = 0; i < userTypeList.length; i++) {
			if (userTypeList[i].checked) {
				tempUserType = userTypeList[i];
			}
		}
		
		if(tempUserType.value == 'B') {
			if (password_elem.value == '') {
				alert('비밀 번호를 입력 하세요.');
				password_elem.focus();
				return;
			}
		}

		document.getElementById("check_user_type").value = tempUserType.value;
		
		execLogin();
	}

	function fn_selectUserType(userType) {

		var label = document.getElementById(loginlabel_id);
		var pwli = document.getElementById("login_pw_li");
		var add_bp = document.getElementById("add_bp");
		var find_pw = document.getElementById("find_pw");
		if (userType == 'B') {
			pwli.style.display = "block";
			add_bp.style.display = "block";
			find_pw.style.display = "block";
			label.src = "/images/login_id.png";
			label.alt = "아이디";
		} else if (userType == 'M') {
			pwli.style.display = "none";
			add_bp.style.display = "none";
			find_pw.style.display = "none";
			label.src = "/images/login_num.png";
			label.alt = "사번";
		}
	}

	function onKeyDown(elem) {
		if (event.keyCode == 13) {
			
			var selectedUserType = $("input[name=check_user_type_radio]:checked").val();
			
			if(selectedUserType=="B"){ //BP
				if (elem.id == username_id) {
					password_elem.focus();
				} else if (elem.id == password_id) {
					login();
				}
			}else if(selectedUserType=="M"){ //SKT
				login();
			}
			
		}
	}

	function addBP() {
		var option = "width=1000px, height=700px, scrollbars=yes, resizable=no, statusbar=no";
		var sWin = window.open("", "addBP", option);

		var form = document.getElementById("mForm");
		form.target = "addBP";
		form.action = "<c:url value='/common/login/BpAdd_Popup_Read.do'/>";
		form.submit();
		sWin.focus();
	}
	
	function notice_popup(){
		window.open("/Notice_Popup.do","bp_notice_popup","width=500, height=500, scrollbars=no, resizable=no, statusbar=no");
	}
	
	//id pass 찾기
	function password_popup_search(gubun){
		var url = "/usermg/bp/Password_Popup_Search.do?url_gubun="+gubun;
		var popName = "password_popup_search";
		var option = "width=801, height=400, scrollbars=yes, resizable=yes, statusbar=no, toolbar=no, menubar=no, location=no";

		window.open(url, popName, option);
	}
</script>

</head>

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
				<div class="login_inp fl_left">
					<!--업체선택 -->
					<ul class="com_select fl_wrap">
						<li><input tabindex="1" id="radio1" name="check_user_type_radio" type="radio" onclick="javascript:fn_selectUserType(this.value);"  value="B" checked="checked" /><label for="radio1"> 협력업체</label></li>
						<li><input tabindex="2" id="radio2" name="check_user_type_radio" type="radio" onclick="javascript:fn_selectUserType(this.value);"  value="M" /><label for="radio2"> SKT</label></li>
					</ul>
					
					<!--입력 -->
					<ul class="login_form">
						<li><img id="login_id_label" src="/images/login_id.png" alt="아이디" />
						<input tabindex="3" id="j_username" name="j_username" type="text" style="Width:97%;" maxlength="20" style="ime-mode:disabled;" onkeypress="javascript:onKeyDown(this);" value="" />
						</li>
						<li id="login_pw_li">
							<img src="/images/login_pass.png" alt="비밀번호" />
							<input tabindex="4" id="j_password" name="j_password" type="password" style="Width:97%;" maxlength="20" onkeypress="javascript:onKeyDown(this);" value="" />
						</li>
						<li id="find_pw" class="find_idpw">
							<a href="javascript:password_popup_search('id')">아이디 찾기</a> / <a href="javascript:password_popup_search('pass')">비밀번호 변경</a>
						</li>
					</ul>

					<!--로그인버튼 -->
					<div class="btn_login">
						<a tabindex="5" href="javascript:login();">Log - in</a>
					</div>
					
				</div>
				<!--아이디,사번,비밀번호 입력 및 저장 끝 -->
				
				<div class="callcenter fl_left">
					<dl>
						<dt><b>PKMS</b> <p>Package Management system</p></dt>
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

