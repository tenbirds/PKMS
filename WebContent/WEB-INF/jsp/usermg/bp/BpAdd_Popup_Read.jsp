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
<c:set var="step" value="${empty BpModel.bp_num ? '등록' : '수정'}" />

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
	width: 440px;
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
	var bpNumValidMsg = "";

	$(document).ready(function($) {
		$("#bp_num").focus();
	});

	function fn_confirm_bpnum() {
		var bp_num = document.getElementById("bp_num");
		if (!isNullAndTrim(bp_num, "사업자등록번호를 입력 하세요.")) {
			return;
		}
		if (!isBpNumValid) {
			alert(bpNumValidMsg);
			return;
		}
		
		doSubmit("BpModel",
				"/usermg/bp/BpAdd_Ajax_Duplicate_Read.do",
				"fn_callback_duplicate_bp_num");
	}

	function fn_callback_duplicate_bp_num(data) {
		
		var result = $("input[id=param1]").val();

		var bp_num_value = $("#bp_num").val();
		
		if (result == "true") {
			var msg = "[" + bp_num_value + "]는 등록 가능한 사업자등록번호 입니다.";
			alert(msg);
			$("#bp_num_temp").val(bp_num_value);
			fn_read('');
			return;
		} else if (result == "temp") {
			alert("기존에 임시 저장된 정보가 있습니다.\n수정화면으로 이동 합니다.");
			$("#bp_num_temp").val(bp_num_value);
			fn_read(bp_num_value);
			return;
		} else if (result == "disabled") {
			bpNumValidMsg = "입력하신 사업자번호[" + bp_num_value
					+ "]는 사용 중지된 사업자등록번호 입니다. 관리자에게 문의 하세요.";
		} else {
// 			bpNumValidMsg = "입력하신 사업자번호[" + bp_num_value
// 					+ "]는 이미 승인요청 또는 완료된 사업자등록번호 입니다.\n 수정페이지로 이동합니다.";
			alert("이미 승인요청 또는 완료된 사업자등록번호 입니다.\n수정화면으로 이동 합니다.");
			$("#bp_num_temp").val(bp_num_value);
			fn_UserAdd(bp_num_value);
			return;
		}
		$("#check_bp_num_msg").html(
				"<font color=red>" + bpNumValidMsg + "</font>");
		isBpNumValid = false;
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

		bpNumValidMsg = "사업사등록번호[" + bp_num_value + "]는 유효한 번호 입니다. 확인 버튼을 클릭 하세요.";
		$("#check_bp_num_msg").html(
				"<font color=orange>" + bpNumValidMsg + "</font>");
		
		isBpNumValid = true;
		return;
	}

	function fn_read(bp_num) {

		var retUrl = document.getElementById("retUrl");
		retUrl.value = "/usermg/bp/BpAddDetail_Popup_Read";

		$("#bp_num").val(bp_num);

		var form = document.getElementById("BpModel");
		form.action = "/common/login/BpAddDetail_Popup_Read.do";
		form.submit();
	}
	
	//--이미 승인요청 또는 완료된 사업자등록번호--
	function fn_UserAdd(bp_num) {

		$("#bp_num").val(bp_num);

		var form = document.getElementById("BpModel");
		form.action = "/common/login/BpAddDetail_Popup_UserAdd.do";
		form.submit();
	}
</script>

</head>
<body>

	<form:form commandName="BpModel" method="post" onsubmit="return false;">
		<!-- 페이징 -->
		<form:hidden path="pageIndex" />

		<!-- return URL -->
		<form:hidden path="retUrl" />

		<form:hidden path="bp_num_temp" />
		
		<div class="popup_contents">
			<div class="fl_wrap">
				<h1 class="tit ml15">사업자등록번호 확인</h1>
			</div>
			
			<div class="popup_search">
				<table class="popup_sear_table1">
					<colgroup>
					<col width="15%">
					<col width="20%">
					<col width="*">
					</colgroup>
					<tr>
						<th>사업자번호<span class='necessariness'>*</span></th>
						<td style="width: 200px"><form:input path="bp_num" maxlength="10" class="inp inp_bp_w2" onchange="javascript:fn_check_bp_num()" onkeyup="javascript:fn_check_bp_num()" onmouseup="javascript:fn_check_bp_num()"
								onkeypress="if (event.keyCode<48|| event.keyCode>57)  event.returnValue=false;" style="IME-MODE:disabled;" /></td>
						<td>
							<div id="check_bp_num_msg">&nbsp;</div>
						</td>
					</tr>
				</table>
				<div id="pop_footer" class="mt10">
					<span class="pop_line_blue3"><a href="javascript:fn_confirm_bpnum();">확인</a></span>
				</div>
			</div>

		</div>
		
		<%-- <div style="padding: 10px;">
			<fieldset>
				<legend>사업자등록번호 확인</legend>
				<table class="tbl_type1" style="width: 100%;">
					<tr>
						<th>사업자번호<span class='necessariness'>*</span></th>
						<td style="width: 200px"><form:input path="bp_num" maxlength="10" class="inp inp_bp_w2" onchange="javascript:fn_check_bp_num()" onkeyup="javascript:fn_check_bp_num()" onmouseup="javascript:fn_check_bp_num()"
								onkeypress="if (event.keyCode<48|| event.keyCode>57)  event.returnValue=false;" style="IME-MODE:disabled;" /></td>
						<td>
							<div id="check_bp_num_msg">&nbsp;</div>
						</td>
					</tr>
				</table>
			</fieldset>
			<div id="pop_footer" style="border: 0px;">
				<a href="javascript:fn_confirm_bpnum();"> <img alt="확인" src="/images/btn_identify.gif"></a>
			</div>

		</div> --%>

	</form:form>

</body>
</html>

