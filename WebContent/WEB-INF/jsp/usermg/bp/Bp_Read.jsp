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

<html>
<head>

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
				init_sktmanageruser_popup();
// 				doTable("scrollTable", "tbl_type", "1", "0", [ "185", "200","200", "200", "380" ]);
			});

	/* 사업자등록번호 중복 체크 */
	function fn_check_duplicate_bp_num() {
		doSubmit("BpModel",
				"<c:url value='/usermg/bp/Bp_Ajax_Duplicate_Read.do'/>",
				"fn_callback_duplicate_bp_num");
	}

	function fn_callback_duplicate_bp_num(data) {

		var result = $("input[id=param1]").val();
		var bp_num_value = document.getElementById("bp_num").value;
		tempBpNum = bp_num_value;

		if (result == "true") {
			var msg = "[" + bp_num_value + "]는 등록 가능한 사업자등록번호 입니다.";
			$("#check_bp_num_msg").html("<font color=green>" + msg + "</font>");
			alert(msg);
			isBpNumValid = true;
			isBpNumDuplicate = false;
		} else {
			bpNumValidMsg = "입력하신 사업자번호[" + bp_num_value + "]는 이미 등록되어 있습니다.";
			$("#check_bp_num_msg").html(
					"<font color=red>" + bpNumValidMsg + "</font>");
			isBpNumValid = false;
			isBpNumDuplicate = true;
		}

	}

	/* 사업자등록번호 유효성 체크 */
	function fn_check_bp_num() {

		isBpNumValid = false;

		var bp_num_value = document.getElementById("bp_num").value;

		if (bp_num_value == "") {
			bpNumValidMsg = "사업자등록번호를 입력하세요!";
			$("#check_bp_num_msg").html(
					"<font color=red>" + bpNumValidMsg + "</font>");
			return;
		}
		

		 
		if (bp_num_value.length != 10) {
			bpNumValidMsg = "사업사등록번호는 10자리 이여야 합니다!";
			$("#check_bp_num_msg").html(
					"<font color=red>" + bpNumValidMsg + "</font>");
			return;
		}

		var sumMod = 0;

		sumMod = 0;
		sumMod += parseInt(bp_num_value.substring(0, 1));
		sumMod += parseInt(bp_num_value.substring(1, 2)) * 3 % 10;
		sumMod += parseInt(bp_num_value.substring(2, 3)) * 7 % 10;
		sumMod += parseInt(bp_num_value.substring(3, 4)) * 1 % 10;
		sumMod += parseInt(bp_num_value.substring(4, 5)) * 3 % 10;
		sumMod += parseInt(bp_num_value.substring(5, 6)) * 7 % 10;
		sumMod += parseInt(bp_num_value.substring(6, 7)) * 1 % 10;
		sumMod += parseInt(bp_num_value.substring(7, 8)) * 3 % 10;
		sumMod += Math.floor(parseInt(bp_num_value.substring(8, 9) * 5 / 10));
		sumMod += parseInt(bp_num_value.substring(8, 9)) * 5 % 10;
		sumMod += parseInt(bp_num_value.substring(9, 10));

		if (sumMod % 10 != 0) {
			bpNumValidMsg = "사업사등록번호[" + bp_num_value + "]가 유효하지 않습니다!";
			$("#check_bp_num_msg").html(
					"<font color=red>" + bpNumValidMsg + "</font>");
			return;
		}
 
		if (tempBpNum == bp_num_value) {
			if (isBpNumDuplicate) {
				bpNumValidMsg = "입력하신 사업자번호[" + bp_num_value
						+ "]는 이미 등록되어 있습니다.";
				$("#check_bp_num_msg").html(
						"<font color=red>" + bpNumValidMsg + "</font>");
			} else {
				var msg = "[" + bp_num_value + "]는 등록 가능한 사업자등록번호 입니다.";
				$("#check_bp_num_msg").html(
						"<font color=green>" + msg + "</font>");
				isBpNumValid = true;
			}

		} else {
			bpNumValidMsg = "사업사등록번호[" + bp_num_value + "]의 중복을 확인 하세요.";
			$("#check_bp_num_msg")
					.html(
							"<a href='javascript:fn_check_duplicate_bp_num();'><img src='/images/two_btn_overlap.gif' alt='중복확인'  align='absmiddle' style='vertical-align: middle;' /></a> <font color=orange>"
									+ bpNumValidMsg + "</font>");
		}
	 
		return;
	}

	/* 등록,수정 값 유효성 체크 */
	function validationBpForm(create) {

		if (create) {
			var bp_num = document.getElementById("bp_num");
			if (!isNullAndTrim(bp_num, "사업자등록번호는 필수 입력사항 입니다."))
				return false;
			if (!isBpNumValid) {
				alert(bpNumValidMsg);
				return false;
			}
		}

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
		$("#approval_state").val("1");
		doSubmit4File("BpModel",
				"<c:url value='/usermg/bp/Bp_Ajax_Create.do'/>",
				"fn_callback_create");
	}

	/* 등록 Callback */
	function fn_callback_create(data) {

		doDivSH("hide", "bp_num_input", 0);
		doDivSH("hide", "create_button_area", 0);
		doDivSH("hide", "bp_user_create_text", 0);
		doDivSH("empty", "check_bp_num_msg", 0);

		doDivSH("show", "bp_num_text", 0);
		doDivSH("show", "update_button_area", 0);
		doDivSH("show", "bp_user_create_button", 0);

		$("#bp_num_text").html(document.getElementById("bp_num").value);
	}

	/* 목록 */
	function fn_readList() {
		var form = document.getElementById("BpModel");
		form.action = "<c:url value='/usermg/bp/Bp_ReadList.do'/>";
		form.submit();
	}

	/* 수정 */
	function fn_update() {

		if (!validationBpForm(false)) {
			return;
		}

		$("#approval_state").val("");
		doSubmit4File("BpModel", "/usermg/bp/Bp_Ajax_Update.do",
				"fn_callback_update");
	}

	/* 수정 Callback */
	function fn_callback_update(data) {
		var state = $("#approval_state_save").val();
		if ($("#use_yn").val() == "Y" && (state == "1" || state == "0")) {
			doDivSH("show", "bp_user_create_button", 0);
		} else {
			doDivSH("hide", "bp_user_create_button", 0);
		}
		fn_user_readList();
	}

	/* 승인 */
	function fn_approval() {
		/*
	 		승인시에도 해당 필수값들을 한번 체크해줍니다 
		*/
		if (!validationBpForm(false)) {
			return;
		}

		if (confirm("정말로 승인 하시겠습니까?")) {
			$("#approval_state").val("1");
			doSubmit4File("BpModel",
					"<c:url value='/usermg/bp/Bp_Ajax_Update.do'/>",
					"fn_callback_approval", "승인되었습니다");
		}
	}

	/* 승인 Callback */
	function fn_callback_approval(data) {
		doDivSH("hide", "approval_button", 0);
		doDivSH("show", "update_button", 0);
		doDivSH("show", "bp_user_create_button", 0);
		$("#approval_state_area").html("<font color=\"green\">승인완료</font>");
		fn_user_readList();
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

		document.getElementById("bp_user_id").value = bp_user_id;

		doSubmit("BpModel",
				"<c:url value='/usermg/user/BpUser_Ajax_Read.do'/>",
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

	/* SKT 사용자 선택 결과 처리 */
	function fn_sktuser_popup_callback(user_id, user_name) {
		$("#approval_user_id").val(user_id);
		$("#approval_user_name").val(user_name);
	}
</script>

</head>
<body>

	<form:form commandName="BpModel" method="post" onsubmit="return false;">

		<!-- 페이징 -->
		<form:hidden path="pageIndex" />

		<!-- 검색조건 유지 -->
		<form:hidden path="searchFilter" />
		<form:hidden path="searchCondition" />
		<form:hidden path="searchKeyword" />
		<form:hidden path="approval_state" />
		<input type="hidden" id="approval_state_save" value="${BpModel.approval_state}">

		

		<!--기본정보, 상세정보 DIV 시작(스크롤지정 안함)-->
		<div class="new_con_Div32 mt20">

			<!--기본정보 -->
			<div class="con_area">
				<div class="con_detail">
					<!--버튼위치영역 -->
					<div class="con_top_btn" >
			
						<div id="update_button_area" style="${registerFlag == '수정' ? 'display:block;' : 'display:none;'}">
			
							<sec:authorize ifAnyGranted="ROLE_ADMIN">
								<div id="update_button" style="${BpModel.approval_count == 0 ? 'display:block;' : 'display:none;'}">
									<span><a href="javascript:fn_update();">수정</a></span> 
									<span><a href="javascript:fn_readList();">목록</a></span>
								</div>
								<div id="approval_button" style="${BpModel.approval_count == 0 ? 'display:none;' : 'display:block;'}">
									<span><a href="javascript:fn_approval();">승인</a></span> 
									<span><a href="javascript:fn_readList();">목록</a></span>
								</div>
							</sec:authorize>
			
							<sec:authorize ifNotGranted="ROLE_ADMIN">
								<div id="update_button" style="${BpModel.approval_user_id == BpModel.session_user_id && BpModel.approval_count > 0 ? 'display:none;' : 'display:block;'}">
									<span><a href="javascript:fn_readList();">목록</a></span>
								</div>
								<sec:authorize ifAnyGranted="ROLE_MANAGER">
								<div id="approval_button" style="${BpModel.approval_user_id == BpModel.session_user_id && BpModel.approval_count > 0 ? 'display:block;' : 'display:none;'}">
									<span><a href="javascript:fn_approval();">승인</a></span> 
									<span><a href="javascript:fn_readList();">목록</a></span>
								</div>
								</sec:authorize>
							</sec:authorize>
			
						</div>
			
						<div id="create_button_area" style="${registerFlag == '수정' ? 'display:none;' : 'display:block;'}" class="fl_right">
							<span><a href="javascript:fn_create();">저장</a></span> 
							<span><a href="javascript:fn_readList();">목록</a></span>
						</div>
			
					</div>
					
					<h3 class="stitle" id="sys_detail_title">업체 정보</h3>
					<table class="tbl_type11">
						<colgroup>
						<col width="8%">
						<col width="15%">
						<col width="8%">
						<col width="15%">
						<col width="8%">
						<col width="17%">
						<col width="8%">
						<col width="13%">
						</colgroup>
						<tr>
							<th>업체명<span class='necessariness'>*</span></th>
							<td><form:input path="bp_name" maxlength="50" class="new_inp inp_w80" /></td>
							<th>사업자번호<span class='necessariness'>*</span></th>
							<td>
								<div id="bp_num_text" style="${registerFlag == '수정' ? 'display:block;' : 'display:none;'}">
									<c:out value="${BpModel.bp_num}" />
								</div>
								<div id="bp_num_input" style="${registerFlag == '수정' ? 'display:none;' : 'display:block;'}">
									<form:input path="bp_num" maxlength="10" class="new_inp new_inp_w6" onkeyup="fn_check_bp_num();"  onkeydown="fn_numbersonly(event);" style="ime-mode:disabled" />
								</div>
							</td>
							<td colspan="6">
								<div id="check_bp_num_msg">&nbsp;</div>
							</td>
						</tr>
						<tr>
							<th>전화번호<span class='necessariness'>*</span></th>
							<td>
								<form:input path="bp_tel1" maxlength="4" class="new_inp inp_w30px" onkeydown="fn_numbersonly(event);" style="ime-mode:disabled"/>
								<form:input path="bp_tel2" maxlength="4" class="new_inp inp_w30px" onkeydown="fn_numbersonly(event);" style="ime-mode:disabled"/>
								<form:input path="bp_tel3" maxlength="4" class="new_inp inp_w30px" onkeydown="fn_numbersonly(event);" style="ime-mode:disabled"/>
							</td>
	
							<th>설립일자</th>
							<td><form:input path="bp_established_day" maxlength="10" class="new_inp inp_bp_w3 fl_left" readonly="true" /></td>
	
							<th>업체구분</th>
							<td><span class="mt10"><form:radiobuttons path="bp_gubun" items="${BpModel.bpGubunList}" itemLabel="codeName" itemValue="code" /></span></td>
	
							<th>자본금</th>
							<td><form:input path="bp_assets" maxlength="15" class="new_inp new_inp_w6" onkeydown="fn_numbersonly(event);" style="ime-mode:disabled"/></td>
						</tr>
						<tr>
							<th>주소</th>
							<td colspan="3"><form:input path="bp_addr" maxlength="100" class="new_inp new_inp_w6" /></td>
	
							<th>회사형태</th>
							<td><span class="mt10"><form:radiobuttons path="bp_forms_ltd" items="${BpModel.bpFormsLtdList}" itemLabel="codeName" itemValue="code" /></span></td>
							<th>법인여부</th>
							<td><span class="mt10"><form:radiobuttons path="bp_forms_corp" items="${BpModel.bpFormsCorpList}" itemLabel="codeName" itemValue="code" /></span></td>
	
						</tr>
						<tr>
							<th>전문분야1</th>
							<td colspan="3"><form:input path="bp_expertise_areas1" maxlength="100" class="new_inp new_inp_w6" /></td>
							<th>홈페이지</th>
							<td colspan="3"><form:input path="bp_url" maxlength="100" class="new_inp new_inp_w6" /></td>
						</tr>
						<tr>
							<th>전문분야2</th>
							<td colspan="3"><form:input path="bp_expertise_areas2" maxlength="100" class="new_inp new_inp_w6" /></td>
							<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
								<th>상태</th>
								<td>
									<div id="approval_state_area">
										<c:choose>
											<c:when test="${BpModel.approval_state == '1'}">
												<font color="green">승인완료</font>
											</c:when>
											<c:when test="${BpModel.approval_state == '0'}">
												<font color="orange">승인요청</font>
											</c:when>
											<c:when test="${BpModel.approval_state == '3'}">
												<font color="gray">임시저장</font>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
									</div>
								</td>
								<th>접속허용여부</th>
								<td><form:select path="use_yn" items="${BpModel.useYnItems}" itemLabel="codeName" itemValue="code" /></td>
							</sec:authorize>
							<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_MANAGER">
								<c:choose>
									<c:when test="${!(empty BpModel.approval_user_id)  && BpModel.approval_user_id == BpModel.session_user_id}">
										<th>상태</th>
										<td colspan="3">
											<div id="approval_state_area">
												<c:choose>
													<c:when test="${BpModel.approval_state == '1'}">
														<font color="green">승인완료</font>
													</c:when>
													<c:when test="${BpModel.approval_state == '0'}">
														<font color="orange">승인요청</font>
													</c:when>
													<c:when test="${BpModel.approval_state == '3'}">
														<font color="green">임시저장</font>
													</c:when>
													<c:otherwise>
													</c:otherwise>
												</c:choose>
											</div>
										</td>
									</c:when>
									<c:otherwise>
										<td colspan="4">&nbsp;</td>
									</c:otherwise>
								</c:choose>
							</sec:authorize>
						</tr>
						<tr>
							<th>승인 담당자<span class='necessariness'>*</span></th>
							<td colspan="7">
								<form:hidden path="approval_user_id" />
								<form:input path="approval_user_name" readOnly="true" class="new_inp inp_bp_w2 fl_left mr05" /> 
								<img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;margin-top:2px;" id="open_sktuser_popup" />
<!-- 								<div class="btn_line_blue22 fl_left"><span id="open_sktuser_popup">검색</span></div> -->
							</td>
						</tr>
	
					</table>
				
			

					<!--상세정보 -->
					<h3 class="stitle con_width35 fl_left">업체 담당자</h3>
					<table class="fl_right mt20" style="width:60%;">
						<tr>
							<td align="right">
								<div id="bp_user_create_button" style="${registerFlag == '수정' && BpModel.approval_count == 0 && BpModel.use_yn == 'Y' && BpModel.approval_state != '3' ? 'display:block;' : 'display:none;'}">
									<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
										<span><a href="javascript:fn_read_user('');"><img src="/images/two_btn_add.gif" alt="추가" /></a></span>
									</sec:authorize>
								</div>
								<div id="bp_user_create_text" style="${registerFlag == '수정' ? 'display:none;' : 'display:block;'}margin-bottom:5px;">
									<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
										<div class="help_notice" style="float: right;">업체 정보를 저장하시면 담당자를 추가하실 수 있습니다.</div>
									</sec:authorize>
								</div>
							</td>
						</tr>
					</table>
	
<!-- 						<div class="fakeContainer"> -->
					<table id="scrollTable" class="tbl_type12 w100">
						<colgroup>
						<col width="15%">
						<col width="15%">
						<col width="20%">
						<col width="20%">
						<col width="30%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">상태</th>
								<th scope="col">이름</th>
								<th scope="col">아이디</th>
								<th scope="col">이동전화</th>
								<th scope="col">이메일</th>
							</tr>
						</thead>
						<tbody id="bp_user_list_area">
							<tr>
								<td colspan="5" align="center">&nbsp;</td>
							</tr>
						</tbody>
					</table>
<!-- 						</div> -->
					<br /> <br />
					<div id="bp_user_detail_area" class="con_Div2">
						<form:hidden path="bp_user_id" />
					</div>
				</div>
			</div>
		</div>

	</form:form>



</body>
</html>

