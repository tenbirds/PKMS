<%--
/**
 * 협력업체 상세와 담당자 목록 및 상세 정보를 관리하는 페이지
 * 
 * @author : skywarker
 * @Date : 2012. 4. 19.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty BpModel.bp_num ? '등록' : '수정'}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PKMS (패키지 관리 시스템)</title>

<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>


<style type="text/css">
.inp_bp_w1 {
	width: 435px;
}

.inp_bp_w2 {
	width: 150px;
}

.inp_bp_w3 {
	width: 100px;
}

.inp_bp_w4 {
	width: 42px;
}

.td_bp_w1 {
	width: 130px;
}

.td_bp_w2 {
	width: 170px;
}
</style>
<script type="text/javaScript" defer="defer">
	/* BP 등록 관련 */
	var mode = "<c:out value="${registerFlag}" />";
	var isBpNumValid = false;
	var isBpNumDuplicate = false;
	var bpNumValidMsg = "";
	var tempBpNum = "";

	/* BP 사용자 등록 관련 */
	var isBpUserIdValid = false;
	var isBpUserIdDuplicate = false;
	var bpUserIdValidMsg = "";
	var tempBpUserId = "";

	$(document).ready(
			function($) {
				if (mode == '수정') {
					fn_user_readList();
				}
				doCalendar("bp_established_day");
				init_sktuser_bp_popup();
// 				doTable("scrollTable", "tbl_type", "1", "0", [ "180", "180","180", "180", "280", "165" ]);
			});

	
	/* 등록,수정 값 유효성 체크 */
	function validationBpForm(create) {

		var bp_name = document.getElementById("bp_name");
		var bp_addr = document.getElementById("bp_addr");
		var bp_tel1 = document.getElementById("bp_tel1");
		var bp_tel2 = document.getElementById("bp_tel2");
		var bp_tel3 = document.getElementById("bp_tel3");
		var approval_user_name = document.getElementById("approval_user_name");

		//필수 입력 체크
		if (!isNullAndTrim(bp_name, "업체명은 필수 입력사항 입니다."))
			return false;
		if (!isNullAndTrim(bp_tel1, "전화번호는 필수 입력사항 입니다."))
			return false;
		if (!isNullAndTrim(bp_tel2, "전화번호는 필수 입력사항 입니다."))
			return false;
		if (!isNullAndTrim(bp_tel3, "전화번호는 필수 입력사항 입니다."))
			return false;
		if (!isNumber(bp_tel1, "전화번호는 숫자 이여야 합니다."))
			return false;
		if (!isNumber(bp_tel2, "전화번호는 숫자 이여야 합니다."))
			return false;
		if (!isNumber(bp_tel3, "전화번호는 숫자 이여야 합니다."))
			return false;
		if (!isNullAndTrim(approval_user_name, "승인 담당자는 필수 입력사항 입니다."))
			return false;

		//옵션 입력 체크
		var bp_assets = document.getElementById("bp_assets");
		if (bp_assets.value != "") {
			if (!isNumber(bp_assets, "자본금은 숫자 이여야 합니다."))
				return false;
		}

		return true;
	}

	/* 등록 */
	function fn_create() {

		if (!validationBpForm(true)) {
			return;
		}

		//if (confirm("정말로 저장 하시겠습니까?")) {
		$("#approval_state").val("3");
		doSubmit4File("BpModel",
				"<c:url value='/usermg/bp/Bp_Ajax_Create.do'/>",
				"fn_callback_create");
		//}
	}

	/* 등록 Callback */
	function fn_callback_create(data) {
		doDivSH("hide", "bp_num_input", 0);
		doDivSH("hide", "create_button_area", 0);
		doDivSH("hide", "bp_user_create_text", 0);
		doDivSH("show", "update_button_area", 0);
		doDivSH("show", "bp_user_create_button", 0);
	}

	/* 수정 */
	function fn_update() {

		if (!validationBpForm(false)) {
			return;
		}

		//if (confirm("정말로 수정 하시겠습니까?")) {
		$("#approval_state").val("");
		doSubmit4File("BpModel",
				"<c:url value='/usermg/bp/Bp_Ajax_Update.do'/>",
				"fn_callback_update");
		//}
	}

	/* 수정 Callback */
	function fn_callback_update(data) {
		fn_user_readList();
	}

	/* 승인 */
	function fn_approval() {
		if (confirm("정말로 승인요청 하시겠습니까?")) {
			$("#approval_state").val("0");
			doSubmit4File("BpModel",
					"<c:url value='/usermg/bp/Bp_Ajax_Update.do'/>",
					"fn_callback_approval", "승인요청 되었습니다.");
		}
	}

	/* 승인 Callback */
	function fn_callback_approval(data) {
		// 화면 종료
		window.close();
	}

	/* 업체 담당자 목록 Ajax 조회 */
	function fn_user_readList() {
		doSubmit("BpModel",
				"<c:url value='/usermg/user/BpUser_Ajax_ReadList.do'/>",
				"fn_callback_user_readList");
	}

	/* 업체 담당자 목록 Ajax 조회 Callback */
	function fn_callback_user_readList(data) {
		$("#bp_user_list_area").html(data);
		doDivSH("hide", "bp_user_detail_area", 0);
	}

	/* 업체 담당자 선택  */
	function fn_read_user(bp_user_id) {

		$("#bp_user_id").val(bp_user_id);

		doSubmit("BpModel", "/usermg/user/BpAddUser_Ajax_Read.do",
				"fn_callback_read_user");
	}

	function fn_read_user_my() {
		$("#bp_user_id").val("");
		doSubmit("BpModel", "/common/top/MyBpAddUser_Ajax_Read.do",
				"fn_callback_read_user");
	}

	/* 업체 담당자 선택 Callback */
	function fn_callback_read_user(data) {

		//BP 사용자 변수 초기화
		isBpUserIdValid = false;
		bpUserIdValidMsg = "";
		tempBpUserId = "";

		//화면 출력
		doDivSH("empty", "bp_user_detail_area", 0);
		$("#bp_user_detail_area").html(data);
		doDivSH("show", "bp_user_detail_area", 0);
	}

	//협력SKT 사용자 선택 팝업 초기화
	function init_sktuser_bp_popup(){
		doModalPopup("img[id=open_sktuser_bp_popup]", 1, "click", 523, 590, "/usermg/user/SktUserBp_Popup_Read.do");
	}
	
	/* SKT 사용자 선택 결과 처리 */
	function fn_sktuser_bp_popup_callback(user_id, user_name) {
		$("#approval_user_id").val(user_id);
		$("#approval_user_name").val(user_name);
	}
	
	function fn_sys_list(bp_user_id) {
		
		var option = "width=783px, height=528px, scrollbars=yes, resizable=no, statusbar=no";
		var form = document.getElementById("BpModel");
		var sWin = window.open("", "fn_sys_list", option);
		
		$("#bp_user_id").val(bp_user_id);
		
		form.target = "fn_sys_list";
		form.action = "/usermg/bp/Bp_Popup_System_ReadList.do";
		form.submit();
		sWin.focus();
	}

</script>

</head>
<body>

	<form:form commandName="BpModel" method="post" onsubmit="return false;">
		<!-- 페이징 -->
		<form:hidden path="pageIndex" />

		<!-- return URL -->
		<form:hidden path="retUrl" />
		<form:hidden path="approval_state" value="0" />
		<form:hidden path="bp_num" value="${BpModel.bp_num_temp }" />
		<form:hidden path="bp_num_temp" />

		<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
			<div style="padding: 10px;">
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
			<div>
		</sec:authorize>

		
			
			<!--기본정보, 상세정보 DIV 시작(스크롤지정)-->
			<div class="popup_contents">
			<!--기본정보, 상세정보 DIV 시작(스크롤지정)-->
			
				<div class="fl_wrap">
					<h1 class="tit ">업체 정보</h1>
				</div>
				
				
				<div class="con_area">
					<div class="con_detail">
						<!--버튼위치영역 -->
						<div class="con_top_btn">
							<div id="update_button_area" style="${registerFlag == '수정' ? 'display:block;' : 'display:none;'}">
								<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
									<span><a href="javascript:fn_approval();">승인요청</a></span>
									<span><a href="javascript:fn_update();">수정</a></span>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
									<span><a href="javascript:fn_update();">수정</a></span>
								</sec:authorize>
							</div>
			
							<div id="create_button_area" style="${registerFlag == '수정' ? 'display:none;' : 'display:block;'}">
								<span><a href="javascript:fn_create();">저장</a></span>
							</div>
						</div>
						
						<table class="tbl_type11">
							<colgroup>
								<col width="9%" />
								<col width="20%" />
								<col width="9%" />
								<col width="16%" />
								<col width="7%" />
								<col width="17%" />
								<col width="7%" />
								<col width="15%" />
							</colgroup>
							<tr>
								<th>업체명<span class='necessariness'>*</span></th>
								<td><form:input path="bp_name" maxlength="30" class="new_inp inp_w90" /></td>
								<th>사업자번호<span class='necessariness'>*</span></th>
								<td><c:out value="${BpModel.bp_num_temp}" /></td>
								<td colspan="6">&nbsp;</td>
							</tr>
							<tr>
								<th>전화번호<span class='necessariness'>*</span></th>
								<td>
									<form:input path="bp_tel1" maxlength="4" class="new_inp inp_w20" onkeydown="fn_numbersonly(event);" style="ime-mode:disabled" /> 
									<form:input path="bp_tel2" maxlength="4" class="new_inp inp_w25" onkeydown="fn_numbersonly(event);" style="ime-mode:disabled" /> 
									<form:input path="bp_tel3" maxlength="4" class="new_inp inp_w25" onkeydown="fn_numbersonly(event);" style="ime-mode:disabled" />
								</td>
	
								<th>설립일자</th>
								<td><form:input path="bp_established_day" maxlength="10" class="new_inp inp_bp_w3 fl_left" readonly="true" /></td>
	
								<th>업체구분</th>
								<td><form:radiobuttons path="bp_gubun" items="${BpModel.bpGubunList}" itemLabel="codeName" itemValue="code" /></td>
	
								<th>자본금</th>
								<td><form:input path="bp_assets" maxlength="15" class="new_inp inp_w90"  onkeydown="fn_numbersonly(event);" style="ime-mode:disabled"/></td>
							</tr>
							<tr>
								<th>주소</th>
								<td colspan="3"><form:input path="bp_addr" maxlength="100" class="new_inp inp_w90" /></td>
	
								<th>회사형태</th>
								<td><form:radiobuttons path="bp_forms_ltd" items="${BpModel.bpFormsLtdList}" itemLabel="codeName" itemValue="code" /></td>
								<th>법인여부</th>
								<td><form:radiobuttons path="bp_forms_corp" items="${BpModel.bpFormsCorpList}" itemLabel="codeName" itemValue="code" /></td>
	
							</tr>
							<tr>
								<th>전문분야1</th>
								<td colspan="3"><form:input path="bp_expertise_areas1" maxlength="100" class="new_inp inp_w90" /></td>
								<th>홈페이지</th>
								<td colspan="3"><form:input path="bp_url" maxlength="100" class="new_inp inp_w90" /></td>
							</tr>
							<tr>
								<th>전문분야2</th>
								<td colspan="3"><form:input path="bp_expertise_areas2" maxlength="100" class="new_inp inp_w90" /></td>
								<td colspan="4">&nbsp;</td>
							</tr>
							<tr>
								<th>승인 담당자<span class='necessariness'>*</span></th>
								<td colspan="7"><form:hidden path="approval_user_id" /> <form:input path="approval_user_name" readOnly="true" class="new_inp inp_bp_w2" /> <img src="/images/pop_btn_search1.gif" alt="선택"
									style="cursor: pointer; vertical-align: middle;" id="open_sktuser_bp_popup" align="absmiddle" style="vertical-align: middle;" /></td>
							</tr>
	
						</table>
					
						<!--상세정보 -->
						
						<div class="fl_wrap">
						<h3 class="stitle fl_left">업체 담당자</h3>						
							<div id="bp_user_create_button" class="fl_right mt20" style="${registerFlag == '수정' ? 'display:block;' : 'display:none;'}">
								<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
									<span class="btn_line_blue2 fl_right mr00"><a href="javascript:fn_read_user('');">추가</a></span>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
									<span class="btn_line_blue2 fl_right mr00"><a href="javascript:fn_read_user_my();">추가</a></span>
								</sec:authorize>
							</div>
							<div id="bp_user_create_text" class="fl_right mt20" style="${registerFlag == '수정' ? 'display:none;' : 'display:block;'}">
								<div class="help_notice" style="float: right;">업체 정보를 저장하시면 담당자를 추가하실 수 있습니다.</div>
							</div>
						</div>
						<div class="fakeContainer">
							<table id="scrollTable" class="tbl_type12">
								<thead>
									<tr>
										<th scope="col">상태</th>
										<th scope="col">이름</th>
										<th scope="col">아이디</th>
										<th scope="col">이동전화</th>
										<th scope="col">이메일</th>
									
										<c:if test = "${BpModel.cnt_bp_user_id > 0 and BpModel.approval_state != '3'}">
										<th scope="col">담당시스템</th>
										</c:if>
					
									</tr>
								</thead>
								<tbody id="bp_user_list_area">
									<tr>
										<td colspan="5">&nbsp;</td>
									</tr>
								</tbody>
							</table>
						</div>
						<br /> <br />
						
						<div id="bp_user_detail_area" class="con_Div2">
							<form:hidden path="bp_user_id" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>

</body>
</html>

