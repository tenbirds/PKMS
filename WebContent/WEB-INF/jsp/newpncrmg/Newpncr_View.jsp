<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty NewpncrModel.new_pn_cr_seq ? '등록' : '수정'}" />
<sec:authentication property="principal.username" var="sessionId"/>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/pkgmg/jsgantt.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.qtip-1.0.0-rc3.min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/jsgantt.css"/>
<script type="text/javaScript"  defer="defer">


var	g;
$(document).ready(function($) {
// 		$("#subList${status.index}").before("<br/><legend><span class='necessariness'>*</span>"+' ${list[status.index].name}'+" 일정</legend>");
	g = new JSGantt.GanttChart('g',document.getElementById('chartResult'), 'day');
	if (g) {
		 '<c:forEach items="${readListSubChart}" var="list" varStatus="status">';
			  g.AddTaskItem(new JSGantt.TaskItem('${list.road_map_seq}', '${list.code_desc}','${list.start_date}','${list.end_date}', '','', 'detailShowHide(\'${list.road_map_seq}\')','','',0,0,'',0,'','${list.mapping}',
					  new Array(
					  			<c:forEach items="${list.list}" var="list2" varStatus="status2">
					  			new JSGantt.DelayItem('${list2.road_map_seq}','${list2.code_desc}','${list2.start_date}','${list2.end_date}','','detailShowHide(\'${list2.road_map_seq}\')','','${list2.mapping}')<c:if test="${!status2.last}">,</c:if>
					  			</c:forEach>	
					  	)));
			  g.Draw();	
			  g.DrawDependencies();
			  $("DIV.scroll2").css("width", "730px");
	  	'</c:forEach>';
	}else{
		  alert("not defined");
	}
JSGantt.changeFormat("week", g);
$("DIV.scroll2").css("width", "730px");

'<c:forEach items="${readListSubChart}" var="list" varStatus="status">';
$("#taskbar_${list.road_map_seq}").qtip({
	content : "<p style='line-height:1.6em'>"
			+ '${list.tooltip}'
			+ "</p>",
	position : {
		target : 'mouse'
	},
	style : {
		name : 'cream',
		tip : true
	}
});
'<c:forEach items="${list.list}" var="list2" varStatus="status2">';
	$("#taskbar_${list2.road_map_seq}").qtip({
		content : "<p style='line-height:1.6em'>"
				+ '${list2.tooltip}'
				+ "</p>",
		position : {
			target : 'mouse'
		},
		style : {
			name : 'cream',
			tip : true
		}
	});
'</c:forEach>';
'</c:forEach>';

	fn_refine_confirm_select();
});

function detailShowHide(road_map_seq){
// 	alert(road_map_seq);
	if($("#subListDetail"+road_map_seq).css("display")=="none"){
		$("#subListDetail"+road_map_seq).show();	
	}else{
		$("#subListDetail"+road_map_seq).hide();
	}
}

function fn_readList() {
	$("#reject_flag").val("");
	var form = document.getElementById('NewpncrModel');
	form.action = "<c:url value='/newpncrmg/Newpncr_ReadList.do'/>";
	form.submit();
}
function fn_read(gubun, id) {
	var form = document.getElementById('NewpncrModel');
	
	if (gubun == "read") {
		form.retUrl.value = "/newpncrmg/Newpncr_Read";
	} else if (gubun == "view") {
		form.retUrl.value = "/newpncrmg/Newpncr_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}
	
	form.new_pn_cr_seq.value = id;
	form.action = "<c:url value='/newpncrmg/Newpncr_Read.do'/>";
	form.submit();
}
function fn_update(key){
	
	var message = "";
	var form = document.getElementById('NewpncrModel');
	form.stateInfo.value = key;	
	if(key=="approveRequest"){
		message = "협력사 검토 요청 되었습니다.";
	}else if(key=="confirm"){
		
		if($("#bp_comment").val()==""){
			alert("검토의견을 저장하신 후 진행해주세요.");
			$("#bp_comment").focus();
			return;
		}
		
		if($("#bp_comment").val()!=""){ //bp_comment 입력창에 글씨든 undefined이든 뭔가가있을때
			if(typeof($("#bp_comment").val())=="undefined"){ //그게 undefined이면
				if($("#bp_repl").val()!="" && typeof($("#bp_repl").val())!="undefined"){ //bp_repl 입력창에 글씨를 쓰고 검토완료를 누를경우
					alert("의견을 저장해주세요.");
					$("#bp_repl").focus();
					return;
				}
				else{
					message = "검토 완료 되었습니다.";
				}
			}
			else{ //그게 undefined가 아니면
				alert("의견을 저장해주세요.");
				$("#bp_comment").focus();
				return;
			}
		}
	 	
	  /*if($("#bp_repl").val()!=""){
			alert("의견을 저장해주세요.");
			message = "검토 완료 되었습니다."; 
			return;
		}*/
		
	}else if(key=="saveRepl"){
		if($("#bp_comment").val()==""){
			alert("의견을 입력하세요.");
			$("#bp_comment").focus();
			return;
		}
		message = "의견이 저장되었습니다.";
		doSubmit4File("NewpncrModel", "/newpncrmg/Newpncr_Update.do","fn_updateReplCallback", message);
		return;
		
	}else if (key=="bp_reject"){
		
		if($("#bp_comment").val()==""){
			alert("의견은 필수 입니다.");
			$("#bp_comment").focus();
			return;
		}
		
		form.stateInfo.value = "reject";
		message = "반려 되었습니다.";
	}else if(key=="refine"){
		message = "정제 되었습니다.";
	}else if(key=="reject_1"){ //협력사 검토 요청전 반려
		message = "반려 되었습니다.";
	}else if(key=="reject_2"){ //정제 단계에서의 반려
		message = "반려 되었습니다.";
	}else if(key=="reject_9"){ //협력사 검토 요청후 반려(검토완료 전)
		message = "반려 되었습니다.";
	}else if(key=="hold"){
		message = "보류 되었습니다.";
	}
	
	// key = 요청상태 update : 수정, approveRequest : 동의 요청, confirm : 동의, refine : 정제,  reject 반려, hold 보류
	doSubmit4File("NewpncrModel", "/newpncrmg/Newpncr_Update.do", "fn_callback_update", message);
}
function fn_callback_update(){
	fn_readList();
}
function fn_copy(){
	if (confirm("해당 NEW/PN/CR 의 정보를 복사하여 새로운 NEW/PN/CR을 생성합니다. 복사하시겠습니까?")) {
		doSubmit4File("NewpncrModel", "/newpncrmg/Newpncr_Duplication_Create.do", "fn_callback_copy", "복사 되었습니다. 수정화면으로 이동됩니다.");
	}
}
function fn_callback_copy(data){
	
	var form  = document.getElementById('NewpncrModel');
	
	var seq = $("input[id=param1]").val();
	form.new_pn_cr_seq.value = seq;
	form.retUrl.value = "/newpncrmg/Newpncr_Read";
	form.action = "/newpncrmg/Newpncr_Read.do";
	form.submit();
}
function fn_refine_confirm_select(){
	
	var state = $("#state").val();
// 	alert(state);
	if(state==0){
		var req_flag = $(':radio[name="manager_confirm_yn"]:checked').val();
		if(req_flag==1){
			doDivSH("show", "manager_confirm_flag_Y", 0);
			doDivSH("hide", "manager_confirm_flag_N", 0);
			$("#manager_comment_th").html("성능개선 담당 <br>1차 검토");
		}else if(req_flag==2){
			doDivSH("hide", "manager_confirm_flag_Y", 0);
			doDivSH("show", "manager_confirm_flag_N", 0);
			$("#manager_comment_th").html("반려 의견");
		}else{
			$("#manager_comment_th").html("검토 요청 의견");
		}
	}
	
	var flag = $(':radio[name="refine_confirm_yn"]:checked').val();
// 	alert(flag);
	if(flag==1){
		doDivSH("show", "refineCheck", 0);
		doDivSH("show", "refine_confirm_flag_Y", 0);
		doDivSH("hide", "manager_confirm_flag_N", 0); //check 140227
		doDivSH("hide", "refine_confirm_flag_N", 0);
		doDivSH("hide", "refine_confirm_flag_H", 0);
// 		doDivSH("show", "hold_tbody", 0);
		$("#refine_comment_th").html("정제 의견");
	}else if(flag==2){
		doDivSH("show", "refineCheck", 0);
		doDivSH("hide", "refine_confirm_flag_Y", 0);
		doDivSH("show", "manager_confirm_flag_N", 0); //check 140227
		doDivSH("hide", "refine_confirm_flag_H", 0);
		doDivSH("show", "refine_confirm_flag_N", 0);
// 		doDivSH("show", "hold_tbody", 0);
		$("#refine_comment_th").html("반려 의견");
	}else if(flag==3){
// 		doDivSH("hide", "hold_tbody", 0);
		doDivSH("hide", "refineCheck", 0);
		doDivSH("show", "refine_confirm_flag_H", 0);
		doDivSH("hide", "refine_confirm_flag_N", 0);
		doDivSH("hide", "refine_confirm_flag_Y", 0);
	}else{
		doDivSH("hide", "refine_confirm_flag_H", 0);
		doDivSH("hide", "refine_confirm_flag_N", 0);
		doDivSH("hide", "refine_confirm_flag_Y", 0);
	}
	
	fn_view_comment(state);
}
function fn_view_comment(state){
	
	var flag = $("#reject_flag").val();
	var reject_text = "반려 사유";
// 	alert(state);
	
	if(state=="1"){
		if(flag=="Y"){
			doDivSH("hide", "manager_comment_div", 0);
			$("#manager_comment_th").html(reject_text);
		}else{
			doDivSH("hide", "manager_comment_div", 0);	
		}
	}else if(state=="2"){
		doDivSH("hide", "manager_comment_div", 0);
	}else if(state=="3"||state=="9"){
		if(flag=="Y"){
			doDivSH("hide", "manager_comment_div", 0);
			doDivSH("hide", "refine_comment_div", 0);
			$("#refine_comment_th").html("반려 의견");
		}else{
			doDivSH("hide", "manager_comment_div", 0);
			doDivSH("hide", "refine_comment_div", 0);
			$("#refine_comment_th").html("정제 의견");
		}
	}else if(state=="4"){
// 		doDivSH("hide", "refine_comment_div", 0);
// 		$("#refine_comment_th").html("반려 의견");
	}
	
	
}

/* 답글 추가 0909 - ksy
 */
function fn_createRepl(){
	
	if(typeof($("#manager_repl").val()) != 'undefined'){
		if($("#manager_repl").val()==""){
			alert("의견을 입력하세요.");
			$("#manager_repl").focus();
			return;
		}
	}
	if(typeof($("#bp_repl").val()) != 'undefined'){
		if($("#bp_repl").val()==""){
			alert("의견을 입력하세요.");
			$("#bp_repl").focus();
				return;
		}
	}
	doSubmit4File("NewpncrModel", "/newpncrmg/Newpncr_CreateRepl.do","fn_updateReplCallback","의견이 저장되었습니다.");
}

function fn_updateRepl(re_seq, status){//status:manager_repl${status.index}
	$("#new_pn_cr_re_seq").val(re_seq);
	if(status.match("bp")==null){ //bp라는 글자가 없으면
		$("#replstatusM").val(status);
	}else{
		$("#replstatusB").val(status);
	}
	doSubmit4File("NewpncrModel", "/newpncrmg/Newpncr_UpdateRepl.do","fn_updateReplCallback","의견이 수정되었습니다.");
}

function fn_updateReplCallback(){
	fn_read("view",$("#new_pn_cr_seq").val());
}
</script>
</head>
<body>
	<form:form commandName="NewpncrModel" method="post" enctype="multipart/form-data" onsubmit="return false;">
	
	<form:hidden path="new_pn_cr_seq"/>
	<form:hidden path="new_pn_cr_re_seq"/>
	<!-- 첨부파일 -->
	<form:hidden path="master_file_id" />
	<!-- 페이징 -->
	<form:hidden path="pageIndex"/>
	<!-- return URL -->
	<form:hidden path="retUrl" />
	<!-- 검색조건 유지 -->
	<form:hidden path="searchCondition"/>
	<form:hidden path="searchKeyword"/>
	<form:hidden path="search_reg_date_start"/>
	<form:hidden path="search_reg_date_end"/>
	<form:hidden path="search_system_seq"/>
	<form:hidden path="search_system_name"/>
	<form:hidden path="search_reg_user"/>
	<form:hidden path="search_reg_name"/>
	<form:hidden path="searchRoleCondition"/>
	<form:hidden path="searchNew_pncr_gubun"/>
	<!-- 업데이트 상태값 -->
	<form:hidden path="stateInfo"/>
	<!-- 상태값 -->
	<form:hidden path="state"/>
	<form:hidden path="reject_flag"/>
	<form:hidden path="userChargeGubun"/>
	<form:hidden path="reject_comment"/>
	
	<!-- 매니저답글,BP답글 구분 -->
	<form:hidden path="replstatusB"/>
	<form:hidden path="replstatusM"/>
	
	<form:hidden path="deleteList"/>
	
<%-- ${NewpncrModel.state} & 
${NewpncrModel.reject_flag }<br/>
${NewpncrModel.userChargeGubun} --%>
	
	
	<div class="new_con_Div32">
		<!--기본정보 -->
		<div class="con_area">
			<div class="con_detail">
								
				<div class="con_top_btn">
					<c:if test="${NewpncrModel.reg_user eq sessionId}">
						<c:if test="${NewpncrModel.state eq '0'}">
							<span><a href="javascript:fn_read('read', '<c:out value="${NewpncrModel.new_pn_cr_seq}"/>')">수정</a></span>
							<span><a href="javascript:fn_copy()">복사하기</a></span>
						</c:if>
					</c:if>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN">
						<c:if test="${NewpncrModel.state eq '0'}">
							<span id="manager_confirm_flag_Y"><a href="javascript:fn_update('approveRequest')">협력사 검토 요청</a></span>
							<span id="manager_confirm_flag_N"><a href="javascript:fn_update('reject_1')">반려</a></span>
						</c:if>
						
						<c:if test="${NewpncrModel.state eq '1' && NewpncrModel.reject_flag eq 'N'}">  <!-- 협력사검토요청단계이면서 검토완료하기전 -->
							<span style="color: red;">*협력사 검토요청 상태입니다.</span>
							<span id="refine_confirm_flag_Y"><a href="javascript:fn_update('refine')">정제</a></span>
							<span id="manager_confirm_flag_N"><a href="javascript:fn_update('reject_9')">반려</a></span>
							<!-- <span id="refine_confirm_flag_H"><a href="javascript:fn_update('hold')"><img src="/images/btn_hold.gif" alt="보류" /></a></span> -->
						</c:if>
						
						<c:if test="${NewpncrModel.state eq '2' || NewpncrModel.state eq '4'}">
							<span id="refine_confirm_flag_Y"><a href="javascript:fn_update('refine')">정제</a></span>
							<span id="refine_confirm_flag_N"><a href="javascript:fn_update('reject_2')">반려</a></span>
							<span id="refine_confirm_flag_H"><a href="javascript:fn_update('hold')">보류</a></span>
						</c:if>
					</sec:authorize>
					<sec:authorize ifNotGranted = "ROLE_ADMIN">
						<sec:authorize ifAnyGranted = "ROLE_MANAGER">
							<c:if test="${NewpncrModel.userChargeGubun}">
								<c:if test="${NewpncrModel.state eq '0'}">
									<span id="manager_confirm_flag_Y"><a href="javascript:fn_update('approveRequest')">협력사 검토 요청</a></span>
									<span id="manager_confirm_flag_N"><a href="javascript:fn_update('reject_1')">반려</a></span>
								</c:if>
								<c:if test="${NewpncrModel.state eq '2' || NewpncrModel.state eq '4'}">
									<span id="refine_confirm_flag_Y"><a href="javascript:fn_update('refine')">정제</a></span>
									<span id="refine_confirm_flag_N"><a href="javascript:fn_update('reject_2')">반려</a></span>
									<span id="refine_confirm_flag_H"><a href="javascript:fn_update('hold')">보류</a></span>
								</c:if>
								<c:if test="${NewpncrModel.state eq '1' }">
									<c:if test="${NewpncrModel.reject_flag eq 'N' }">
										<span style="color: red;">*협력사 검토요청 상태입니다.</span>
										<span id="refine_confirm_flag_Y"><a href="javascript:fn_update('refine')">정제</a></span>
									</c:if>
									<span id="refine_confirm_flag_N"><a href="javascript:fn_update('reject_2')">반려</a></span>
									<span id="refine_confirm_flag_H"><a href="javascript:fn_update('hold')">보류</a></span>
								</c:if>
							</c:if>
						</sec:authorize>
					</sec:authorize>
					
					<sec:authorize ifAnyGranted = "ROLE_BP">
						<c:if test="${(NewpncrModel.state eq '1' && NewpncrModel.reject_flag eq'N')}">
							<span><a href="javascript:fn_update('confirm')">검토완료</a></span>
						</c:if>
					</sec:authorize>
						<span><a href="javascript:fn_readList()">목록</a></span>
				</div>
				
				<h3 class="stitle">기본정보</h3>
				
				<table class="tbl_type11">
					<colgroup>
						<col width="10%">
						<col width="23%">
						<col width="10%">
						<col width="23%">
						<col width="10%">
						<col width="24%">
					</colgroup>
					<tr>
						<th>제목</th>
						<td colspan="5">
							<form:hidden path="title"/>
							<c:out value="${NewpncrModel.title}"/>
						</td>
					</tr>
					<tr>
						<th>대분류</th>
						<td>
							<c:out value="${NewpncrModel.group1_name}"/>
						</td>
						<th>중분류</th>
						<td>
							<c:out value="${NewpncrModel.group2_name}"/>
						</td>
						<th>소분류</th>
						<td>
							<c:out value="${NewpncrModel.group3_name}"/>
						</td>
					</tr>
					<tr>
						<th>시스템</th>
						<td>
							<form:hidden path="system_seq"/><c:out value="${NewpncrModel.system_name}"/>
						</td>
						<th>유형</th>
						<td>${NewpncrModel.new_pncr_gubun}</td>
						<th>우선순위</th>
						<td>${NewpncrModel.priority_name}</td>
					</tr>
				</table>
		
		
				<h3 class="stitle">상세정보</h3>
				
				<table class="tbl_type11">
					<col width="10%">
					<col width="40%">
					<col width="10%">
					<col width="40%">
					<tr>
						<th>문제구분</th>
						<td colspan="3">${NewpncrModel.problem_gubun_name}</td>
					</tr>
					<tr>
						<th>문제점</th>
						<td colspan="3">${NewpncrModel.problem}</td>
					</tr>
					<tr>
						<th>요구사항</th>
						<td colspan="3">${NewpncrModel.requirement}</td>
					</tr>
					<tr>
						<th style="width: 107px;">첨부파일1</th>
						<td style="width: 470px;">
							<ui:file attachFileModel="${NewpncrModel.file1}" name="file1" mode="view" />
							
						</td>
						<th style="width: 107px;">첨부파일2</th>
						<td style="width: 470px;">
							<ui:file attachFileModel="${NewpncrModel.file2}" name="file2" mode="view" />
						</td>
					</tr>
					<tr>
						<th style="width: 107px;">첨부파일3</th>
						<td style="width: 470px;">
							<ui:file attachFileModel="${NewpncrModel.file3}" name="file3" mode="view" />
						</td>
						<th>번호</th>
						<td><c:out value="${NewpncrModel.new_pn_cr_no}" /></td>
					</tr>
					<tr>
						<th>등록자</th>
						<td><c:out value="${NewpncrModel.reg_name}" /></td>
						<th>등록일</th>
						<td><c:out value="${NewpncrModel.reg_date}" /></td>
					</tr>
					<tr>
						<th>등록팀</th>
						<td colspan="3"><c:out value="${NewpncrModel.sosok}" /></td>
					</tr>
				</table>
				
<!-- *** 답글 추가 및 수정 0902 - ksy *** -->
				<h3 class="stitle">의견 정보</h3>
				
				<c:if test="${NewpncrModel.state eq '0'}">
					<c:if test="${NewpncrModel.userChargeGubun}">				
						<div id="manager_comment_div" class="input_radio mt10">
							<span>
								<input name="manager_confirm_yn" type="radio" value="1" onclick="fn_refine_confirm_select();" checked="checked" id="manager_confirm_y" /> 
								<label for="manager_confirm_y">검토요청</label>
							</span>
							<span>
								<input name="manager_confirm_yn" type="radio" value="2" onclick="fn_refine_confirm_select();" id="manager_confirm_n" /> 
								<label for="manager_confirm_n">반려</label>
							</span>
						</div>
					</c:if>
				</c:if>
				<table class="tbl_type11">
					<colgroup>
						<col width="10%" />
						<col width="40%" />
						<col width="10%" />
						<col width="40%" />
					</colgroup>
					<tr>
						<th rowspan="2" id="manager_comment_th" class="th_height">성능개선 담당<br/>1차 검토</th>
						<td rowspan="2" class="mg05">
							<c:if test="${NewpncrModel.state eq '0'}">
								<sec:authorize ifAnyGranted = "ROLE_ADMIN">
									<form:textarea path="manager_comment" rows="5" class="textarea_w90" maxlength="15000"/>
								</sec:authorize>
								<sec:authorize ifNotGranted = "ROLE_ADMIN">
									<sec:authorize ifAnyGranted = "ROLE_MANAGER">
										<c:if test="${NewpncrModel.userChargeGubun}">
											<form:textarea path="manager_comment" rows="5" class="textarea_w90" maxlength="15000"/>										
										</c:if>
										<c:if test="${!NewpncrModel.userChargeGubun}">
											${NewpncrModel.manager_comment}
										</c:if>
									</sec:authorize>
								</sec:authorize>
									
							</c:if>
							<c:if test="${NewpncrModel.state ne '0'}">
								${NewpncrModel.manager_comment}
							</c:if>
							<c:if test="${(NewpncrModel.reject_flag eq 'Y' && NewpncrModel.state eq '1')}"><!-- 검토요청단계에서 반려했을시에만 적용 -->
								${NewpncrModel.reject_comment}
							</c:if>
						</td>
						<th>첨부파일1</th>
						<td>
							<sec:authorize ifAnyGranted = "ROLE_ADMIN">
								<c:if test="${NewpncrModel.state eq '0'}">
									<ui:file attachFileModel="${NewpncrModel.file4}" name="file4" mode="update" />
								</c:if>
								<c:if test="${NewpncrModel.state ne '0'}">
									<ui:file attachFileModel="${NewpncrModel.file4}" name="file4" mode="view" />
								</c:if>
							</sec:authorize>
							<sec:authorize ifNotGranted = "ROLE_ADMIN">
								<sec:authorize ifAnyGranted = "ROLE_MANAGER, ROLE_OPERATOR">
									<c:if test="${NewpncrModel.userChargeGubun}">
										<c:if test="${NewpncrModel.state eq '0'}">
											<ui:file attachFileModel="${NewpncrModel.file4}" name="file4" mode="update" />		
										</c:if>
										<c:if test="${NewpncrModel.state ne '0'}">
											<ui:file attachFileModel="${NewpncrModel.file4}" name="file4" mode="view" />		
										</c:if>
									</c:if>
									<c:if test="${!NewpncrModel.userChargeGubun}">
										<c:if test="${NewpncrModel.state ne '0'}">
											<ui:file attachFileModel="${NewpncrModel.file4}" name="file4" mode="view" />
										</c:if>
									</c:if>
								</sec:authorize>
							</sec:authorize>
							<sec:authorize ifAnyGranted = "ROLE_BP">
								<ui:file attachFileModel="${NewpncrModel.file4}" name="file4" mode="view" />
							</sec:authorize>
						</td>
					</tr>
					<tr>
						<th>첨부파일2</th>
						<td>
							<sec:authorize ifAnyGranted = "ROLE_ADMIN">
								<c:if test="${NewpncrModel.state eq '0'}">
									<ui:file attachFileModel="${NewpncrModel.file5}" name="file5" mode="update" />
								</c:if>
								<c:if test="${NewpncrModel.state ne '0'}">
									<ui:file attachFileModel="${NewpncrModel.file5}" name="file5" mode="view" />
								</c:if>
							</sec:authorize>
							<sec:authorize ifNotGranted = "ROLE_ADMIN">
								<sec:authorize ifAnyGranted = "ROLE_MANAGER, ROLE_OPERATOR">
									<c:if test="${NewpncrModel.userChargeGubun}">
										<c:if test="${NewpncrModel.state eq '0'}">
											<ui:file attachFileModel="${NewpncrModel.file5}" name="file5" mode="update" />	
										</c:if>
										<c:if test="${NewpncrModel.state ne '0'}">
											<ui:file attachFileModel="${NewpncrModel.file5}" name="file5" mode="view" />	
										</c:if>	
									</c:if>
									<c:if test="${!NewpncrModel.userChargeGubun}">
										<c:if test="${NewpncrModel.state ne '0'}">
											<ui:file attachFileModel="${NewpncrModel.file5}" name="file5" mode="view" />
										</c:if>
									</c:if>
								</sec:authorize>
							</sec:authorize>
							<sec:authorize ifAnyGranted = "ROLE_BP">
								<ui:file attachFileModel="${NewpncrModel.file5}" name="file5" mode="view" />
							</sec:authorize>
						</td>
					</tr>
				</table> <!-- 매니저가 신규등록시 검토요청상태에서 검토요청의견이나 반려의견을 적고 검토요청이나 반려를 하면 한번 new_pn_cr 테이블로 들어감 -->
			
			<!-- 처음 BP검토의견 입력할때 첨부파일 등록을 위한- new_pn_cr 테이블로 들어가므로 한번만 실행되도록--> 
			<c:if test="${(NewpncrModel.state eq '1' && NewpncrModel.reject_flag eq 'N' )|| (NewpncrModel.state eq '2') || (NewpncrModel.state eq '3') || (NewpncrModel.state eq '4' || (NewpncrModel.state eq '9')) }">
			<!-- <fieldset class="detail_fieldset"> -->
				<table class="tbl_type11">
					<colgroup>
						<col width="10%" />
						<col width="40%" />
						<col width="10%" />
						<col width="40%" />
					</colgroup>
					<tr>
						<th rowspan="2" id="bp_comment_th">BP 검토 의견</th>
						<td rowspan="2" class="mg05">
							<c:if test="${NewpncrModel.state eq '1'}">
								<c:if test="${empty NewpncrModel.bp_comment}">
									<sec:authorize ifAnyGranted = "ROLE_BP">
										<form:textarea path="bp_comment" rows="5" class="textarea_w90" maxlength="15000"/>
									</sec:authorize>
								</c:if>
							</c:if>
							<c:if test="${not empty NewpncrModel.bp_comment}">
								${NewpncrModel.bp_comment}
							</c:if>
						</td>
						<th>첨부파일1</th>
						<td>
							<c:if test="${NewpncrModel.state eq '1'}">
								<c:if test="${empty NewpncrModel.bp_comment}">
									<sec:authorize ifAnyGranted = "ROLE_BP">
										<ui:file attachFileModel="${NewpncrModel.file6}" name="file6" mode="update" />
									</sec:authorize>
								</c:if>
							</c:if>
							<c:if test="${not empty NewpncrModel.bp_comment}">
								<ui:file attachFileModel="${NewpncrModel.file6}" name="file6" mode="view" />
							</c:if>
						</td>
						<td>
							<sec:authorize ifAnyGranted = "ROLE_BP">
								<c:if test="${empty NewpncrModel.bp_comment && NewpncrModel.state eq '1'}">
									<input type="button" name="저장" value="저장" onclick="fn_update('saveRepl');"/>
								</c:if>
							</sec:authorize>
						</td>
					</tr>
					<tr>
						<th>첨부파일2</th>
						<td colspan="3">
							<c:if test="${NewpncrModel.state eq '1'}">
								<c:if test="${empty NewpncrModel.bp_comment}">
									<sec:authorize ifAnyGranted = "ROLE_BP">
										<ui:file attachFileModel="${NewpncrModel.file7}" name="file7" mode="update" />
									</sec:authorize>
								</c:if>
							</c:if>
							<c:if test="${not empty NewpncrModel.bp_comment}">
								<ui:file attachFileModel="${NewpncrModel.file7}" name="file7" mode="view" />								
							</c:if>
						</td>
					</tr>
				</table>
			</c:if>
				
	<!--시작- 이부분에 댓글입력된 의견들이 리스트형태로 뿌려지는 공간.. -->
		<c:if test="${(NewpncrModel.state ne '0')}"> 
			<c:choose>
				<c:when test="${not empty ReadListRepl}">
					<c:forEach var="readListRepl" items="${ReadListRepl}" varStatus="status">
						<c:if test="${(not empty readListRepl.manager_repl)}"> 
						<table class="tbl_type11" >
							<colgroup>
								<col width="10%" />
								<col width="*" />
								<col width="10%" />
							</colgroup>
							<tr>
								<th rowspan="2" class="th_height">성능개선 담당<br/>${status.index + 2}차 검토</th>
								<td rowspan="2" class="mg05">
									<font color="gray" >등록자: ${readListRepl.reg_name_re}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 등록일: ${readListRepl.reg_date_re}
									<c:if test="${not empty readListRepl.update_date_re }">&nbsp;&nbsp; 수정일: ${readListRepl.update_date_re}</c:if></font>
									<c:if test="${(readListRepl.reg_user_re eq sessionId)}">
										<c:if test="${(NewpncrModel.state ne '3'&&NewpncrModel.reject_flag ne 'Y')}">
											<textarea id="manager_repl${status.index}" name="manager_repl${status.index}" rows="5" cols="" style="width: 457px;" maxlength="15000">${readListRepl.manager_repl}</textarea>
										</c:if>
										<c:if test="${NewpncrModel.state eq '3'||NewpncrModel.reject_flag eq 'Y'}">
											<br/>${readListRepl.manager_repl}
										</c:if>
									</c:if>
									<c:if test="${(readListRepl.reg_user_re ne sessionId)}">
										<br/>${readListRepl.manager_repl}
									</c:if>
								</td>
								<td style="vertical-align: top; padding-top: 10pt;">
								<c:if test="${(readListRepl.reg_user_re eq sessionId)&&(NewpncrModel.state ne '3'&&NewpncrModel.reject_flag ne 'Y')}">
									<c:if test="${(not empty readListRepl.manager_repl)}"> 
										<input type="button" name="의견수정" value="수정" onclick="fn_updateRepl('${readListRepl.new_pn_cr_re_seq}','manager_repl${status.index}');"/>
									</c:if>
								</c:if>
								</td>
							</tr>
						</table>
						</c:if>
								
						<c:if test="${(not empty readListRepl.bp_repl)}"> 
						<table class="tbl_type11">
							<colgroup>
								<col width="10%" />
								<col width="*" />
								<col width="10%" />
							</colgroup>
							<tr>
								<th rowspan="2">BP 검토 의견</th>
								<td rowspan="2" class="mg05">
									<font color="gray" >등록자: ${readListRepl.reg_name_re}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 등록일: ${readListRepl.reg_date_re}
									<c:if test="${not empty readListRepl.update_date_re }">&nbsp;&nbsp; 수정일: ${readListRepl.update_date_re}</c:if></font>
									<c:if test="${(readListRepl.reg_user_re eq sessionId)}">
										<c:if test="${(NewpncrModel.state ne '3'&&NewpncrModel.reject_flag ne 'Y')}">
											<textarea id="bp_repl${status.index}" name="bp_repl${status.index}" rows="5" cols="" style="width: 457px;" maxlength="15000">${readListRepl.bp_repl}</textarea>
										</c:if>
									<c:if test="${NewpncrModel.state eq '3'||NewpncrModel.reject_flag eq 'Y'}">
										<br/>${readListRepl.bp_repl}
									</c:if>
									</c:if>
									<c:if test="${(readListRepl.reg_user_re ne sessionId)}">
										<br/>${readListRepl.bp_repl}
									</c:if>
								</td>
								<td>
								<c:if test="${(readListRepl.reg_user_re eq sessionId)&&(NewpncrModel.state ne '3'&&NewpncrModel.reject_flag ne 'Y')}">
									<c:if test="${(not empty readListRepl.bp_repl)}">
										<input type="button" name="의견수정" value="수정" onclick="fn_updateRepl('${readListRepl.new_pn_cr_re_seq}','bp_repl${status.index}');"/>
									</c:if>
								</c:if>
								</td>
							</tr>
						</table>
						</c:if>
					</c:forEach>
				</c:when>
			</c:choose>
		</c:if> <!--끝- 이부분에 댓글입력된 의견들이 리스트형태로 뿌려지는 공간.. -->
				
<!--시작- 답글 입력만 하는 곳 -->				
	<!-- 매니저 로그인시 검토요청답글 입력 창 띄우기 -->
	<c:if test="${(NewpncrModel.state eq '1' && NewpncrModel.reject_flag eq 'N' ) || NewpncrModel.state eq '2' && NewpncrModel.reject_flag eq 'N'}">
		<c:if test="${not empty NewpncrModel.bp_comment}">
			<sec:authorize ifAnyGranted = "ROLE_ADMIN"><!-- 관리자일때 -->
				<table class="tbl_type11">
					<colgroup>
						<col width="10%" />
						<col width="*" />
						<col width="10%" />
					</colgroup>
					<tr>
						<th rowspan="2" class="th_height">성능개선 담당<br/>검토 의견</th>
						<td rowspan="2" class="mg05">
							<form:textarea path="manager_repl" rows="5" class="textarea_w90" maxlength="15000"/>
						</td>
						
						<td style="vertical-align: top;">
							<input type="button" name="의견저장" value="저장" onclick="fn_createRepl();"/>
						</td>
					</tr>
				</table>
			</sec:authorize>
		</c:if>
	</c:if>
	
	<!-- BP 로그인시 검토요청답글 입력 창 띄우기 -->
	<c:if test="${not empty NewpncrModel.bp_comment}">   <!-- 기존 테이블에 비피의견 값이 있다면 -->
		<c:if test="${(NewpncrModel.state eq '1' && NewpncrModel.reject_flag eq 'N' ) || NewpncrModel.state eq '2' && NewpncrModel.reject_flag eq 'N'}">
			<sec:authorize ifAnyGranted = "ROLE_BP">
				<table class="tbl_type11">
					<colgroup>
						<col width="10%" />
						<col width="*" />
						<col width="10%" />
					</colgroup>
					<tr>
						<th rowspan="2">BP 검토 의견</th>
						<td rowspan="2">
							<form:textarea path="bp_repl" rows="5" class="textarea_w90" maxlength="15000"/>
						</td>
						<td style="vertical-align: top;">
							<input type="button" name="의견저장" value="저장" onclick="fn_createRepl();"/>
						</td>
					</tr>
				</table>
			</sec:authorize>
		</c:if>
	</c:if>
<!--끝- 답글 입력만 하는 곳 -->	
	
		
			<c:if test="${(NewpncrModel.state eq '9') || (NewpncrModel.state eq '1' && NewpncrModel.reject_flag eq 'N') || (NewpncrModel.state eq '2') || NewpncrModel.state eq '3' || (NewpncrModel.state eq '4')}">
			<h3 class="stitle">성능개선 담당 최종 검토</h3>
			<div id="refine_comment_div" class="fl_wrap">
				<sec:authorize ifAnyGranted = "ROLE_ADMIN">
					<c:choose>
					<c:when test="${(NewpncrModel.state eq '1' && NewpncrModel.reject_flag eq 'N')}">
					<div class="input_radio mt10">
						<span>
							<input name="refine_confirm_yn" type="radio" value="1" onclick="fn_refine_confirm_select();" checked="checked" /> 
							<label>반영</label>
						</span>
						<span>
							<input name="refine_confirm_yn" type="radio" value="2" onclick="fn_refine_confirm_select();" /> 
							<label>반려</label>
						</span>
					</div>
					<%-- <input name="refine_confirm_yn" type="radio" value="3" onclick="fn_refine_confirm_select();" <c:if test="${NewpncrModel.state eq '4'}">  disabled="disabled" </c:if>/> 보류 --%>
					</c:when>

					<c:when test="${(NewpncrModel.state eq '2' && NewpncrModel.reject_flag eq 'N')||(NewpncrModel.state eq '4' && NewpncrModel.reject_flag eq 'N')}">
					<div class="input_radio mt10">
						<span>
							<input name="refine_confirm_yn" type="radio" value="1" onclick="fn_refine_confirm_select();" checked="checked" /> 
							<label>반영</label>
						</span>
						<span>
							<input name="refine_confirm_yn" type="radio" value="2" onclick="fn_refine_confirm_select();" /> 
							<label>반려</label>
						</span>
						<span>
							<input name="refine_confirm_yn" type="radio" value="3" onclick="fn_refine_confirm_select();"/> 
							<label>보류</label>
						</span>
					</div>
					</c:when>
					</c:choose>
				</sec:authorize>
				<sec:authorize ifNotGranted = "ROLE_ADMIN">
					<sec:authorize ifAnyGranted = "ROLE_MANAGER">
						<c:if test="${NewpncrModel.userChargeGubun}">
							<c:choose>
							<c:when test="${(NewpncrModel.state eq '1' && NewpncrModel.reject_flag eq 'N')}">
							<div class="input_radio mt10">
								<span>
									<input name="refine_confirm_yn" type="radio" value="1" onclick="fn_refine_confirm_select();" checked="checked" /> 
									<label>반영</label>
								</span>
								<span>
									<input name="refine_confirm_yn" type="radio" value="2" onclick="fn_refine_confirm_select();" /> 
									<label>반려</label>
								</span>
							</div>
							<%-- <input name="refine_confirm_yn" type="radio" value="3" onclick="fn_refine_confirm_select();" <c:if test="${NewpncrModel.state eq '4'}">  disabled="disabled" </c:if>/> 보류 --%>
							</c:when>
							<c:otherwise>
							<div class="input_radio mt10">
								<span>
									<input name="refine_confirm_yn" type="radio" value="1" onclick="fn_refine_confirm_select();" checked="checked" /> 
									<label>반영</label>
								</span>
								<span>
									<input name="refine_confirm_yn" type="radio" value="2" onclick="fn_refine_confirm_select();" /> 
									<label>반려</label>
								</span>
								<span>
									<input name="refine_confirm_yn" type="radio" value="3" onclick="fn_refine_confirm_select();" <c:if test="${NewpncrModel.state eq '4'}">  disabled="disabled" </c:if>/> 
									<label>보류</label>
								</span>
							</div>
							</c:otherwise>
							</c:choose>
						</c:if>
					</sec:authorize>
				</sec:authorize>
			</div>
				
			<div id="refineCheck">		
			<table class="tbl_type11">
<!-- <tbody id="hold_tbody"> -->
					<colgroup>
						<col width="10%" />
						<col width="40%" />
						<col width="10%" />
						<col width="40%" />
					</colgroup>
					<tr>
						<th rowspan="2" id="refine_comment_th">정제 의견</th>
						<td rowspan="2" class="mg05">
							<c:if test="${NewpncrModel.state eq '1' || NewpncrModel.state eq '2' || NewpncrModel.state eq '4'}">
								<sec:authorize ifAnyGranted = "ROLE_ADMIN">
									<form:textarea path="refine_comment" rows="5" class="textarea_w90" maxlength="15000"/>
								</sec:authorize>
								<sec:authorize ifNotGranted = "ROLE_ADMIN">
									<sec:authorize ifAnyGranted = "ROLE_MANAGER">
										<c:if test="${NewpncrModel.userChargeGubun}">
											<form:textarea path="refine_comment" rows="5" class="textarea_w90" maxlength="15000"/>			
										</c:if>
										<c:if test="${!NewpncrModel.userChargeGubun}">
											${NewpncrModel.refine_comment}			
										</c:if>
									</sec:authorize>
								</sec:authorize>
								<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_MANAGER">
									${NewpncrModel.refine_comment}
								</sec:authorize>
							</c:if>
								<c:if test="${NewpncrModel.reject_flag eq 'N'}">
									${NewpncrModel.refine_comment}
								</c:if>
								<c:if test="${NewpncrModel.reject_flag eq 'Y'}">
									${NewpncrModel.reject_comment}
								</c:if>
							
						</td>
						<th>첨부파일1</th>
						<td>
							<c:if test="${NewpncrModel.state eq '1' || NewpncrModel.state eq '2' || NewpncrModel.state eq '4'}">
								<sec:authorize ifAnyGranted = "ROLE_ADMIN">
									<ui:file attachFileModel="${NewpncrModel.file8}" name="file8" mode="update" />
								</sec:authorize>
								<sec:authorize ifNotGranted = "ROLE_ADMIN">
									<sec:authorize ifAnyGranted = "ROLE_MANAGER">
										<c:if test="${NewpncrModel.userChargeGubun}">
											<ui:file attachFileModel="${NewpncrModel.file8}" name="file8" mode="update" />		
										</c:if>
										<c:if test="${!NewpncrModel.userChargeGubun}">
											<ui:file attachFileModel="${NewpncrModel.file8}" name="file8" mode="view" />
										</c:if>
									</sec:authorize>
								</sec:authorize>
								<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_MANAGER">
									<ui:file attachFileModel="${NewpncrModel.file8}" name="file8" mode="view" />
								</sec:authorize>
							</c:if>
							<c:if test="${NewpncrModel.state eq '1' || NewpncrModel.state ne '2'}">
								<ui:file attachFileModel="${NewpncrModel.file8}" name="file8" mode="view" />
							</c:if>
						</td>
					</tr>
					<tr>
						<th>첨부파일2</th>
						<td colspan="3">
							<c:if test="${NewpncrModel.state eq '1' || NewpncrModel.state eq '2' || NewpncrModel.state eq '4'}">
								<sec:authorize ifAnyGranted = "ROLE_ADMIN">
										<ui:file attachFileModel="${NewpncrModel.file9}" name="file9" mode="update" />
									</sec:authorize>
									<sec:authorize ifNotGranted = "ROLE_ADMIN">
										<sec:authorize ifAnyGranted = "ROLE_MANAGER">
											<c:if test="${NewpncrModel.userChargeGubun}">
												<ui:file attachFileModel="${NewpncrModel.file9}" name="file9" mode="update" />		
											</c:if>
											<c:if test="${!NewpncrModel.userChargeGubun}">
												<ui:file attachFileModel="${NewpncrModel.file9}" name="file9" mode="view" />
											</c:if>
										</sec:authorize>
									</sec:authorize>
								<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_MANAGER">
									<ui:file attachFileModel="${NewpncrModel.file9}" name="file9" mode="view" />
								</sec:authorize>
							</c:if>
							<c:if test="${NewpncrModel.state eq '1' || NewpncrModel.state ne '2'}">
								<ui:file attachFileModel="${NewpncrModel.file9}" name="file9" mode="view" />
							</c:if>
						</td>
					</tr>
<!-- </tbody> -->
			</table>
		</div>
		</c:if>
		
		<h3 class="stitle">로드맵 정보</h3>
			
					<div id="chartResult"></div>
					
					<input type="hidden" id="statusIndex"/>
					<h3 class="stitle" id="chartTitle"></h3>
					<c:forEach items="${readListSubChart}" var="list" varStatus="status">
						<div id="subListDetail${list.road_map_seq }" style="display: none;">
						<br/>
						<h3 class="stitle"><span class='necessariness'>*</span>로드맵 맵핑</h3>
						<input type="hidden" name="road_map_seqs" value="${list.road_map_seq }"/>
								<table class="tbl_type11">
									<tr>
										<th style="width: 100px;">날짜</th>
										<td style="text-align: left; padding-left: 5px; width: 470px;"><input type="text" value="${list.start_date }" readonly="readonly"/> ~ <input type="text" value="${list.end_date }" readonly="readonly"/></td>
										<c:if test="${(NewpncrModel.state eq '1' && NewpncrModel.reject_flag eq 'N') || NewpncrModel.state eq '2' || NewpncrModel.state eq '4'}">
											<sec:authorize ifAnyGranted = "ROLE_MANAGER">
												<th>매핑여부</th>
												<td style="text-align: left; padding-left: 5px;"><input type="checkbox" value="${list.road_map_seq }" name="check_seq" <c:if test="${list.mapping eq 'Y'}">checked="checked"</c:if>/>매핑 하겠습니다.</td>
											</sec:authorize>
											<sec:authorize ifNotGranted = "ROLE_ADMIN">
												<sec:authorize ifAnyGranted = "ROLE_MANAGER">
													<th>매핑여부</th>
													<td style="text-align: left; padding-left: 5px;"><input type="checkbox" value="${list.road_map_seq }" name="check_seq" <c:if test="${list.mapping eq 'Y'}">checked="checked"</c:if>/>매핑 하겠습니다.</td>
												</sec:authorize>
											</sec:authorize>
										</c:if>
									</tr>
									<tr>
										<th>내용</th>
										<td style="text-align: left; padding-left: 5px;" colspan="3"><textarea rows="10" style="width: 100%;" readonly="readonly">${list.content }</textarea></td>
									</tr>
								</table>
						</div>
						<c:forEach items="${list.list}" var="list2" varStatus="status2">
							<div id="subListDetail${list2.road_map_seq }" style="display: none;">
							<br/>
							<h3 class="stitle"><span class='necessariness'>*</span>로드맵 맵핑</h3>
								<input type="hidden" name="road_map_seqs" value="${list2.road_map_seq }"/>
								<table class="tbl_type11">
									<tr>
										<th style="width: 100px;">날짜</th>
										<td style="text-align: left; padding-left: 5px; width: 470px;""><input type="text" value="${list2.start_date }" readonly="readonly"/> ~ <input type="text" value="${list2.end_date }" readonly="readonly"/></td>
										<c:if test="${(NewpncrModel.state eq '1' && NewpncrModel.reject_flag eq 'N') || NewpncrModel.state eq '2' || NewpncrModel.state eq '4'}">
											<sec:authorize ifAnyGranted = "ROLE_MANAGER">
												<th>매핑여부</th>
												<td style="text-align: left; padding-left: 5px;"><input type="checkbox" value="${list.road_map_seq }" name="check_seq" <c:if test="${list.mapping eq 'Y'}">checked="checked"</c:if>/>매핑 하겠습니다.</td>
											</sec:authorize>
											<sec:authorize ifNotGranted = "ROLE_ADMIN">
												<sec:authorize ifAnyGranted = "ROLE_MANAGER">
													<th>매핑여부</th>
													<td style="text-align: left; padding-left: 5px;"><input type="checkbox" value="${list.road_map_seq }" name="check_seq" <c:if test="${list.mapping eq 'Y'}">checked="checked"</c:if>/>매핑 하겠습니다.</td>
												</sec:authorize>
											</sec:authorize>
										</c:if>
									</tr>
									<tr>
										<th>내용</th>
										<td style="text-align: left; padding-left: 5px;" colspan="3"><textarea rows="10" style="width: 100%;" readonly="readonly">${list2.content }</textarea></td>
									</tr>
								</table>
							</div>
						</c:forEach>
					</c:forEach>
				
			</div>
		</div>
	</div>
	
	</form:form>
</body>
</html>