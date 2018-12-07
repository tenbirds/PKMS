<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PKMS (패키지 관리 시스템)</title>

<script type="text/javaScript">
	String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/gi, "");
		};
	
	function fn_id_find() {
		var name = $("#bp_user_name").val().trim();
		var p1 = $("#bp_user_phone1").val().trim();
		var p2 = $("#bp_user_phone2").val().trim();
		var p3 = $("#bp_user_phone3").val().trim();
		var email = $("#bp_user_email").val().trim();

		if(name == "" || p1 == "" || p2 == "" || p3 == "" || email == "" ){
			alert("모든 항목에 값을 넣어 주세요");
			return;
		}
		
		doSubmit("BpModel", "/usermg/bp/Id_Find_Ajax_Read.do",
		"fn_callback_find_user");		
	}
	
	function fn_callback_find_user(data) {
		$("#id_list").html(data);
	}
	
	function fn_update() {
		var id = $("#bp_user_id").val().trim();
		var name = $("#bp_user_name").val().trim();
		var pw = $("#bp_user_pw").val().trim();
		var pw_ok = $("#bp_user_pw_ok").val().trim();
		var p1 = $("#bp_user_phone1").val().trim();
		var p2 = $("#bp_user_phone2").val().trim();
		var p3 = $("#bp_user_phone3").val().trim();
		var email = $("#bp_user_email").val().trim();

		if(id == "" || name == "" || pw == "" || pw_ok == "" || p1 == "" || p2 == "" || p3 == "" || email == "" ){
			alert("모든 항목에 값을 넣어 주세요");
			return;
		}
		if($("#bp_user_pw").val() != $("#bp_user_pw_ok").val()){
			alert("비밀번호를 확인해 주세요.");
			return;
		}
		
		//--------------------------------비밀번호 TQAMS 동일 적용------------------------------
		/**
		* 비밀번호 필수 항목 체크
		**/
		var id = $("#bp_user_id").val();
		var pass = $("#bp_user_pw").val()
		
		if(id.toLowerCase() == pass.toLowerCase()) {
			alert("ID와 PASSWORD는 같을 수 없습니다.");
			bp_user_passwd1.focus();
			return;
		}

		var temp = "";
		var intCnt =0;
		
		for(var i = 0; i< pass.length; i++)
		{
			temp = pass.charAt(i);
			if(temp== pass.charAt(i+1) && temp == pass.charAt(i+2) && temp == pass.charAt(i+3))
			{
				intCnt = intCnt + 1;
			}
		}
		
		if(intCnt > 0 )
		{
			alert("비밀번호는 동일문자 연속 4회 이상 올수 없습니다.");
			return;
	    }
		
		if(pass.length < 9)
		{
			alert("비밀번호는 대/소문자, 숫자, 특수문자를 혼용하여 9자 이상으로 설정 하셔야 합니다.");
			return;
		}

		if(!pass.match(/^.*(?=.*\d)(?=.*[a-zA-Z])(?=.*[~,!,@,#,$,*,(,),=,+,_,.,|]).*$/))
		{
			alert("비밀번호는 대/소문자, 숫자, 특수문자를 혼용하여 9자 이상으로 설정 하셔야 합니다.");
			return;
		}
		if(pass.match(/(0123)|(1234)|(2345)|(3456)|(4567)|(5678)|(6789)|(7890)/) || pass.match(/(3210)|(4321)|(5432)|(6543)|(7654)|(8765)|(9870)|(0987)/))
		{
			alert("비밀번호는 연속적인 숫자가 올 수 없습니다.");
			return;
		}
		if(pass.search(id)>-1)
		{
			alert("ID가 포함된 비밀번호는 사용하실 수 없습니다.");
			return;
		}
		
		//--------------------------------end------------------------------
		
		doSubmit("BpModel", "/usermg/bp/Pass_Ajax_Update.do",
		"fn_callback_update");	
	}
	
	function fn_callback_update(data) {
		alert("비밀번호가 변경 되었습니다.");
		window.close();
	}
</script>
</head>
<body>
	<form:form commandName="BpModel" method="post" enctype="multipart/form-data">
		<c:if test="${url_gubun == 'id' }">
			<!--조회버튼 -->
			<input type="hidden" name="retUrl" id="retUrl" value="${retUrl }"/>
			<div class="popup_contents">
			<!--기본정보, 상세정보 DIV 시작(스크롤지정)-->
			
				<div class="fl_wrap">
					<h1 class="tit ml10">아이디 찾기</h1>
					
				</div>
			<!-- <div class="btn_location">
				<a href="javascript:fn_id_find();"><img src="/images/btn_search.gif" alt="조회"/></a>&nbsp;
			</div> -->
			
				<div class="popup_search fl_wrap">
					<span class="con_top_btn mr10"><a href="javascript:fn_id_find();">조회</a></span>
					
					<table class="tbl_type12" style="width:95%;margin:0 auto;">
						<thead>
							<tr>
								<th>
									이름 <span class='necessariness'>*</span>
								</th>
								<th>
									이동전화 <span class='necessariness'>*</span>
								</th>
								<th>
									이메일 <span class='necessariness'>*</span>
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<input type="text" name="bp_user_name" id="bp_user_name" style="width: 100px;"/>
								</td>
								<td>
									<input type="text" name="bp_user_phone1" id="bp_user_phone1" style="width: 60px;" maxlength="4"/> -
									<input type="text" name="bp_user_phone2" id="bp_user_phone2" style="width: 60px;" maxlength="4"/> -
									<input type="text" name="bp_user_phone3" id="bp_user_phone3" style="width: 60px;" maxlength="4"/>
								</td>
								<td>
									<input type="text" name="bp_user_email" id="bp_user_email" />
								</td>
							</tr>
						</tbody>
					</table>
					<br/>
					<div id="id_list">
						<fieldset class="detail_fieldset mt30" style="width:95%;margin:0 auto;">
							<legend>찾기 결과</legend>
							<table class="tbl_type22" style="width:95%;margin:0 auto;">
								<tr>
									<th>
										아이디
									</th>
									<td>
										조회된 아이디가 없습니다.
									</td>
								</tr>
							</table>
						</fieldset>
					</div>
				</div>
			</div>
		</c:if>
		<c:if test="${url_gubun == 'pass' }">
			</br>
			<div class="popup_contents">
			<!--기본정보, 상세정보 DIV 시작(스크롤지정)-->
			
				<div class="fl_wrap">
					<h1 class="tit ml10">비밀번호 변경</h1>
				</div>
				<div class="popup_search fl_wrap">
					<span class="con_top_btn mr10"><a href="javascript:fn_update();">수정</a></span>
					
					<!-- <div class="btn_location">
						<a href="javascript:fn_update();"><img src="/images/btn_modify.gif" alt="수정" /></a>&nbsp;
					</div> -->
					<table class="tbl_type11" style="width:95%;margin:0 auto;">
						<tr>
							<th>
								아이디 <span class='necessariness'>*</span>
							</th>
							<td>
								<input type="text" name="bp_user_id" id="bp_user_id" class="new_inp" />
							</td>
							<th>
								이름 <span class='necessariness'>*</span>
							</th>
							<td>
								<input type="text" name="bp_user_name" id="bp_user_name" class="new_inp" />
							</td>
						</tr>
						<tr>
							<th>
								비밀번호 <span class='necessariness'>*</span>
							</th>
							<td>
								<input type="password" name="bp_user_pw" id="bp_user_pw" style="width: 150px;" class="new_inp" />
							</td>
							<th style="width: 120px;">
								비밀번호 확인 <span class='necessariness'>*</span>
							</th>
							<td>
								<input type="password" name="bp_user_pw_ok" id="bp_user_pw_ok" style="width: 150px;" class="new_inp" />
							</td>
						</tr>
						<tr>
							<th>
								이동전화 <span class='necessariness'>*</span>
							</th>
							<td colspan="3">
								<input type="text" name="bp_user_phone1" id="bp_user_phone1" style="width: 100px;" class="new_inp" maxlength="4"/> -
								<input type="text" name="bp_user_phone2" id="bp_user_phone2" style="width: 100px;" class="new_inp" maxlength="4"/> -
								<input type="text" name="bp_user_phone3" id="bp_user_phone3" style="width: 100px;" class="new_inp" maxlength="4"/>
							</td>
						</tr>
						<tr>
							<th>
								이메일 <span class='necessariness'>*</span>
							</th>
							<td colspan="3">
								<input type="text" name="bp_user_email" id="bp_user_email" style="width: 336px;" class="new_inp"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</c:if>
	</form:form>
</body>
</html>
