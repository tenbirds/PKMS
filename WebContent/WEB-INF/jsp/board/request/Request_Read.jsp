<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty requestModel.request_seq ? '등록' : '수정'}" />
<sec:authentication property="principal.username" var="sessionId"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javaScript"  defer="defer">

	var mode = "<c:out value="${registerFlag}" />";

	$(document).ready(function($) {
		doLoadingInitStart();
		
		doCalendar("request_date_start");
		doCalendar("request_date_end");
		doCalendar("equip_date_start");
		doCalendar("equip_date_end");
		if (mode == '수정') {
			fn_reqUser_readList();
		}
		
		init_system_popup();
		init_sktmanageruser_popup();
		init_bp_popup();
		
		$('textarea[maxlength]').keydown(function(){
	        var max = parseInt($(this).attr('maxlength'));
	        var str = $(this).val();
			if(str.length > max) {
			    $(this).val(str.substr(0, max-1));
				alert("최대 [" + max + " 자]까지 입력 가능합니다.");
			}
		});
		
		doLoadingInitEnd();
	});

function fn_create() {
	
	if(!fn_validataion())
		return;
	
	doSubmit4File("requestModel", "/board/request/Request_Create.do", "fn_callback_create");
}
function fn_validataion(){
	
	var goal = document.getElementById("goal");
	var ptn_name1 = document.getElementById("ptn_name1");
	var system_seq = document.getElementById("system_seq");
	var system_user_id = document.getElementById("system_user_id");
	
	if(!isNullAndTrim(goal, "제목을 입력해주세요.")){
		$("#goal").focus();
		return;
	}
	if(!isNullAndTrim(ptn_name1, "회사명을 입력해주세요.")){
		$("#ptn_name1").focus();
		return;
	}
	if(!isNullAndTrim(system_seq, "시스템을 선택해주세요.")){
		$("#system_name").focus();
		return;
	}
	if(!isNullAndTrim(system_user_id, "SKT 승인 매니져를 입력해주세요.")){
		$("#system_user_id").focus();
		return;
	}
 
	
	if($("#request_date_start").val()==""){
		alert("요청 이용 시작일을 선택해주세요.");	
		$("#request_date_start").focus();
		return false;
	}

	if($("#request_date_end").val()==""){
		alert("요청 이용 종료일을 선택해주세요.");
		$("#request_date_end").focus();
		return false;
	}
	
	if($("#request_date_start").val()!=""){
		if($("#request_date_end").val()==""){
			alert("반입일을 선택해주세요.");
			$("#request_date_end").focus();
			return false;
		}
	}
	
	if($("#request_date_end").val()!=""){
		if($("#request_date_start").val()==""){
			alert("반출일일을 선택해주세요.");
			$("#request_date_start").focus();
			return false;
		}
	}
	
	if(($("#request_date_start").val().replace(/-/g,""))>$("#request_date_end").val().replace(/-/g,"")){
		alert("시작일자가 종료일자보다 나중입니다.");
		$("#requip_date_end").focus();
		return false;
	}
	return true;
}
function fn_update(key) {

	if(!fn_validataion())
	return;
	
	var form = document.getElementById('requestModel');
	form.stateInfo.value = key;	

	var message = "";
	
	// key = 요청상태 1 : 요청, 2 : 승인, 3 : 반려
	
	if(key==""){
		message = "수정 되었습니다.";
	}else if(key=="2"){
		message = "승인 되었습니다.";
	}else if(key=="3"){
		message = "반려 되었습니다.";
	}
	
	doSubmit4File("requestModel", "/board/request/Request_Update.do", "fn_callback_update", message);
}
function fn_callback_create(data) {
	var form  = document.getElementById('requestModel');
	
	var seq = $("input[id=param1]").val();
	
	form.request_seq.value = seq;
	form.action = "/board/request/Request_Read.do";
	form.submit();
	
}
function fn_callback_update(data) {
	fn_read();
}
function fn_read(){
	var form  = document.getElementById('requestModel');
	form.action = "/board/request/Request_Read.do";
	form.submit();
}
function fn_callback_delete(data) {
	fn_readList();
}
function fn_readList() {
	var form  = document.getElementById('requestModel');

	form.action = "/board/request/Request_ReadList.do";
	form.submit();
}
function fn_read_reqUser(ord){
	
	var retUrl = document.getElementById("retUrl");
	retUrl.value = "<c:url value='/board/request/RequestUser_Ajax_Read'/>";
	
	document.getElementById('ord').value = ord;
	doSubmit4File("requestModel", "/board/request/RequestUser_Ajax_read.do", "fn_callback_read_reqUser");
}
function fn_callback_read_reqUser(data){
	doDivSH("show", "request_detail_area", 0);
	$("#request_detail_area").html(data);
}
function fn_reqUser_readList(){
	doSubmit4File("requestModel", "/board/request/RequestUser_Ajax_readList.do", "fn_callback_reqUser_readList");
}
function fn_callback_reqUser_readList(data){
	$("#request_user_list_area").html(data);
	doDivSH("hide", "request_detail_area", 0);
}
function fn_delete(){
	if (confirm("삭제하시겠습니까?")) {
		doSubmit4File("requestModel", "/board/request/Request_Delete.do", "fn_callback_delete", "삭제 되었습니다.");
	}
}
//시스템 선택 결과 처리
function fn_system_popup_callback(system_key, system_name){
	$("#system_seq").val(system_key);
	$("#system_name").val(system_name);
}
function fn_sktuser_popup_callback(user_id, user_name){
	$("#system_user_id").val(user_id);
	$("#system_user_name").val(user_name);
}
function fn_bp_popup_callback(bp_num, bp_name){
	$("#ptn_name1").val(bp_name);
}
</script>
</head>
<body>

<form:form commandName="requestModel" method="post" onsubmit="return false;">
	<form:hidden path="request_seq"/>
	<!-- 페이징 -->
	<form:hidden path="pageIndex" />
	<!-- return URL -->
	<form:hidden path="retUrl" />
		<!-- 승인을 위한 키값 -->
	<form:hidden path="stateInfo"/>
	
	<!-- 검색조건 -->
	<form:hidden path="request_search_date_start"/>
	<form:hidden path="request_search_date_end"/>
	<form:hidden path="search_system_seq"/>
	<form:hidden path="search_system_name"/>
	<form:hidden path="search_system_user_id"/>
	<form:hidden path="search_system_user_name"/>
	<form:hidden path="search_bp_name"/>
	<form:hidden path="search_request_state"/>
	<form:hidden path="searchAllManager"/>
	<form:hidden path="searchKeyword"/>
	
	<div class="btn_location">
		<c:choose>
			<c:when test="${registerFlag == '등록'}">
				<span><a href="javascript:fn_create()"><img src="/images/btn_save.gif" alt="저장" /></a></span>
			</c:when>
			<c:otherwise>
				<c:if test="${requestModel.request_state eq '1'}">
					<sec:authorize ifAnyGranted = "ROLE_ADMIN" >
						<a href="javascript:fn_update('2')"><img src="/images/btn_approbation.gif" alt="승인" /></a>
						<a href="javascript:fn_update('3')"><img src="/images/btn_return.gif" alt="반려" /></a>
					</sec:authorize>
					<sec:authorize ifNotGranted = "ROLE_ADMIN" >
					<sec:authorize ifAnyGranted = "ROLE_MANAGER" >
						<c:if test="${requestModel.system_user_id eq sessionId }">
							<a href="javascript:fn_update('2')"><img src="/images/btn_approbation.gif" alt="승인" /></a>
							<a href="javascript:fn_update('3')"><img src="/images/btn_return.gif" alt="반려" /></a>
						</c:if>
					</sec:authorize>
					</sec:authorize>
				</c:if>
				<c:if test="${requestModel.reg_user eq sessionId}">
					<c:if test="${requestModel.request_state eq '1'}">
						<span><a href="javascript:fn_update('')"><img src="/images/btn_save.gif" alt="저장" /></a></span>
						<span><a href="javascript:fn_delete()"><img src="/images/btn_delete.gif" alt="삭제" /></a></span>
					</c:if>
				</c:if>
				<c:if test="${requestModel.request_state eq '2'}">					
					<sec:authorize ifAnyGranted = "ROLE_MANAGER" >						
							<span><a href="javascript:fn_update('')"><img src="/images/btn_save.gif" alt="저장" /></a></span>
							<span><a href="javascript:fn_delete()"><img src="/images/btn_delete.gif" alt="삭제" /></a></span>						
					</sec:authorize>
				</c:if>
			</c:otherwise>
		</c:choose>
		<span><a href="javascript:fn_readList()"><img src="/images/btn_list.gif" alt="목록" /></a></span>
	</div>

	<div class="con_Div3">
		
		<fieldset>
			<legend>
				기본정보
			</legend>
			<table class="tbl_type1" style="width: 100%">
				<tr>
					<th>제목 <span class='necessariness'>*</span></th>
					<td><form:input path="goal" class="inp inp_w3" maxlength="60"/></td>
					<th>회사명 <span  class='necessariness'>*</span></th>
					<td>
						<sec:authorize ifAnyGranted = "ROLE_BP">
							<form:input path="ptn_name1" class="inp inp_w2" readonly="true" value="${requestModel.session_user_group_name}"/>
						</sec:authorize>
						<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR" >
							<form:input path="ptn_name1" class="inp inp_w2" readonly="true"/>
							<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup" align="absmiddle"></span>
						</sec:authorize>
						
						
					</td>
				</tr>
				<tr>
					<th>시스템 <span  class='necessariness'>*</span></th>
					<td>
						<form:hidden path="system_seq"/>
						<form:input path="system_name" class="inp inp_w2" readonly="true"/>
						<span><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup" align="absmiddle" /></span>
					</td>
					<th>승인매니져 <span class='necessariness'>*</span></th>
					<td>
						<sec:authorize ifAnyGranted = "ROLE_BP">
							<form:hidden path="system_user_id" />  
							<form:input path="system_user_name" class="inp inp_w2" readonly="true"/>  
							<span><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_sktuser_popup" align="absmiddle" /></span>
						</sec:authorize>
						
						<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_OPERATOR, ROLE_MANAGER" >
							<form:hidden path="system_user_id" value="${requestModel.session_user_id}"/>
							<form:input path="system_user_name" class="inp inp_w2"  readonly="true" value="${requestModel.session_user_name}"/>
							<sec:authorize ifAnyGranted = "ROLE_MANAGER" >
								<span><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_sktuser_popup" align="absmiddle" /></span>
							</sec:authorize>
						</sec:authorize>
					</td>
				</tr>
				<tr>
					<th>이용기간 <span class='necessariness'>*</span></th>
					<td>
						<form:input path="request_date_start" id="request_date_start" readonly="true" size="7"/> ~ 
						<form:input path="request_date_end" id ="request_date_end" readonly="true" size="7"/>
					</td>
					<th>출입시간</th>
					<td>
						<form:input path="reg_time_h" size="7" onkeyup="javascript:fn_number_validator(this)" maxlength="2"/> 시
						<form:input path="reg_time_m" size="7" onkeyup="javascript:fn_number_validator(this)" maxlength="2"/> 분
					</td>
				</tr>
				<tr>
					<th>검증 분류</th>
					<td><form:select path="request_class"  items="${requestModel.request_classList}" itemLabel="codeName" itemValue="code"/></td>
					<th>합동 협력사</th>
					<td><form:input path="ptn_name2" class="inp inp_w3" maxlength="15"/></td>
				</tr>
				<tr>
					<th>검증내용</th>
					<td><form:textarea path="content" rows="5" cssStyle="width:470px;" maxlength="15000"/></td>
					<th>특이사항</th>
					<td><form:textarea path="request_issue" rows="5" cssStyle="width:470px;" maxlength="15000"/></td>
				</tr>
			</table>
		</fieldset>	
		<fieldset class="detail_fieldset">
			<legend>장비 반입/반출 (반입/반출 장비가 있을경우 기재해 주세요)</legend>
			<table class="tbl_type1" style="width: 100%">
				<tr>
					<th>장비사양</th>
					<td style="width: 465px;"><form:input path="equip_spec" class="inp inp_w3" maxlength="30"/></td>
					<th>반입일</th>
					<td><form:input path="equip_date_start"  size="7" readonly="true"/></td>
					<th>반출일</th>
					<td><form:input path="equip_date_end"  size="7" readonly="true"/></td>
				</tr>
			</table>
		</fieldset>
		<fieldset class="detail_fieldset">
			<legend>검증 요청자</legend>
			<table class="tbl_type2" style="width: 100%">
				<tr>
					<td colspan="4" align="right" >
						<c:if test="${requestModel.request_state eq '1'}">
							<div id="bp_user_create_button" style="${registerFlag == '수정' ? 'display:block;' : 'display:none;'}">
								<span><a href="javascript:fn_read_reqUser('')"><img src="/images/two_btn_add.gif" alt="추가" /></a></span>
							</div>
						</c:if>
						<div class="help_notice" style="float: right; ${registerFlag == '수정' ? 'display:none;' : 'display:block;'}">
							기본정보를 등록하신 이후에 요청자 정보를 추가 하실 수 있습니다.
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<table class="tbl_type">
							<thead>
								<tr>
									<th>검증요청자</th>
									<th>전화번호</th>
								</tr>
							</thead>
							<tbody id="request_user_list_area">
								<tr>
									<td colspan="2">등록된 요청자가 없습니다.</td>
								</tr>
							</tbody>
						</table>
						<br />
						<div id="request_detail_area" class="con_Div2">
							<form:hidden path="ord" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
		
	</div>
	
	</form:form>
</body>
</html>