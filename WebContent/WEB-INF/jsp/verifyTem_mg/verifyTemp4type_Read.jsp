<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="registerFlag" value="${empty verifyTemModel.verify_seq ? '등록' : '수정'}" />
<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script> 
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javaScript" defer="defer">
var registerFlag = '${registerFlag}';
var ori_title = "";
var used_tem = '${used_tem}';
$(document).ready(function($) {
	init_system_popup_detail();
	if(registerFlag == "수정"){
		$("#verify_title").val('${readList_veriTem[0].verify_title}');
		$("#system_seq").val('${readList_veriTem[0].system_seq}');
		ori_title = '${readList_veriTem[0].verify_title}';
		
		var list_quest = new Array();
		<c:forEach items="${readList_veriTem}" var="list">
			list_quest.push("${list.quest_seq}");
		</c:forEach>
		for ( var i = 0; i < list_quest.length; i++) {
			$("input:checkbox").each(function (index) {  
		    	if(list_quest[i] == $(this).val()){
		    		$(this).attr("checked",":checked");
		    	}
		    });
		}
	}
	
});


function fn_readList() {
	var form = document.getElementById("verifyTemModel");
	var url = "";
	if($("#verify_type").val() == "vol"){
		url = "<c:url value='/verify_tem_mg/verifyTempVol_ReadList.do'/>";
	}else if($("#verify_type").val() == "sec"){
		url = "<c:url value='/verify_tem_mg/verifyTempSec_ReadList.do'/>";
	}else if($("#verify_type").val() == "cha"){
		url = "<c:url value='/verify_tem_mg/verifyTempCha_ReadList.do'/>";
	}else if($("#verify_type").val() == "non"){
		url = "<c:url value='/verify_tem_mg/verifyTempNon_ReadList.do'/>";
	}
	form.action = url;
	form.submit();
}

function fn_delete(){
	if (confirm("이 항목을 삭제하면 해당 항목이 검증완료된 PKG에 포함되있을 경우 PKG 검증결과에서도 삭제됩니다.\n삭제 하시겠습니까?")) {
		doSubmit4File("verifyTemModel", "/verify_tem_mg/verifyTemp_Delete.do", "fn_callback_delete", "삭제 되었습니다.");
	}
}
function fn_callback_delete(){
	fn_readList();
}

function fn_create() {  
    var queSeq = "";  
    $("#verify_ver").val(1);
    $("input:checkbox:checked").each(function (index) {  
    	queSeq += "," + $(this).val() ;  
    });  
    $("#quest_seq_space").val(queSeq.substring(1,queSeq.length));
    
    if($("#verify_title").val() == "" || $("#verify_title").val() == null){
		alert("템플릿 명은 필수 입력 사항입니다.");
		$("#verify_title").focus();
		return;
	}
    if($("#system_seq").val() == "" || $("#system_seq").val() == null){
		alert("시스템 선택은 필수 입니다.");
		return;
	}
    if($("#quest_seq_space").val() == "" || $("#quest_seq_space").val() == null){
		alert("검증 항목을 1개 이상 추가 해야 합니다.");
		return;
	}
    
    if (confirm("등록 하시겠습니까?")) {
		doSubmit4File("verifyTemModel", "/verify_tem_mg/verifyTempMg_Create.do", "fn_callback_create", "등록 되었습니다.");
	}
}  
function fn_callback_create(){
	fn_readList();
}

function fn_update(){
	$("#verify_seq").val('${readList_veriTem[0].verify_seq}');
	$("#verify_ver").val('${readList_veriTem[0].verify_ver}');
	
	if(ori_title == $("#verify_title").val()){
		$("#verify_title_ori").val("Y");
	}else{
		$("#verify_title_ori").val("N");
	}
	var queSeq = "";  
    $("input:checkbox:checked").each(function (index) {  
    	queSeq += "," + $(this).val() ;  
    });  
    $("#quest_seq_space").val(queSeq.substring(1,queSeq.length));
    
    if($("#verify_title").val() == "" || $("#verify_title").val() == null){
		alert("템플릿 명은 필수 입력 사항입니다.");
		$("#verify_title").focus();
		return;
	}
    if($("#system_seq").val() == "" || $("#system_seq").val() == null){
		alert("시스템 선택은 필수 입니다.");
		return;
	}
    if($("#quest_seq_space").val() == "" || $("#quest_seq_space").val() == null){
		alert("검증 항목을 1개 이상 추가 해야 합니다.");
		return;
	}
    
    if (confirm("템플릿을 변경 하면 템플릿을 사용중인 PKG검증에 영향을 미치므로 새 버전으로 등록 됩니다. \n등록 하시겠습니까?")) {
		doSubmit4File("verifyTemModel", "/verify_tem_mg/verifyTempMg_Update.do", "fn_callback_create", "등록 되었습니다.");
	} 
}
function fn_delete(){
	$("#verify_seq").val('${readList_veriTem[0].verify_seq}');
	$("#verify_ver").val('${readList_veriTem[0].verify_ver}');
	
	if(used_tem == "사용중"){
    	alert("이 검증템플릿은 PKG검증에서 사용중이므로 삭제할 수 없습니다!");
    	return;
    }else{
		if (confirm("템플릿을 삭제 하시겠습니까?")) {
			doSubmit4File("verifyTemModel", "/verify_tem_mg/verifyTempMg_Delete.do", "fn_callback_create", "삭제 되었습니다.");
		}
    }
}

//시스템 선택 결과 처리
function fn_system_popup_callback(system_key, system_name, gubun){
	$("#system_seq").val(system_key);
	$("#system_name").val(system_name);
}

</script>
</head>
<body>

<form:form commandName="verifyTemModel" method="post"  enctype="multipart/form-data" onsubmit="return false;">
	<form:hidden path="verify_seq" />
	<form:hidden path="verify_ver" />
	<form:hidden path="verify_type" />
	<form:hidden path="verify_title_ori" />
	<input type="hidden" id="quest_seq_space" name="quest_seq_space">
	<form:hidden path="searchCondition"/><!--조회조건 -->
	
	<h2 class="h2_title">검증 템플릿 정보</h2>
	<div class="con_Div2 con_area">
		<div class="con_detail_long fl_wrap">
			<p class="txt_red mb10">* 추가할 검증 항목 주제가 있을 경우 우측 에서 바로 추가 저장 할 수있습니다.</p>
			<div class="fl_left" style="width:55%;">
				<!--버튼위치영역 -->
				<div class="con_top_btn">
					<c:choose>
						<c:when test="${registerFlag == '등록'}">
							<span><a href="javascript:fn_create()">저장</a></span>
						</c:when>
						<c:otherwise>
								<span id="btn_modify"><a href="javascript:fn_update();">수정</a></span>
								<span><a href="javascript:fn_delete()">삭제</a></span>
						</c:otherwise>
					</c:choose>
					<span><a href="javascript:fn_readList();">목록</a></span>
				</div>
				<h3 class="stitle">
				<c:choose>
					<c:when test="${verifyTemModel.verify_type == 'vol'}">용량 검증</c:when>
					<c:when test="${verifyTemModel.verify_type == 'sec'}">보안 검증</c:when>
					<c:when test="${verifyTemModel.verify_type == 'cha'}">과금 검증</c:when>
					<c:when test="${verifyTemModel.verify_type == 'non'}">비기능 검증</c:when>
				</c:choose> 
				항목 템플릿 저장
				</h3>
				<table class="tbl_type11">
					<tr>
						<th style="width: 100px; height: 50px;">템플릿 명 <span class='necessariness'>*</span></th>
						<td width="400px;">
							<form:input path="verify_title"  class="new_inp inp_w100" maxlength="200"/>
						</td>
					</tr>
					<tr>
						<th>시스템 <span class='necessariness'>*</span></th>
						<td>
							<form:hidden path="system_seq" />
							<form:input path="system_name" maxlength="50" class="new_inp inp_w80 fl_left" readonly="true" />
							<span class="ml03 fl_left"><img src="/images/pop_btn_select.gif" alt="선택" style="cursor: pointer;" id="open_system_popup"/></span>
						</td>
					</tr>
					<tr>
						<th style="width: 100px; height: 50px;">검증 타입 <span class='necessariness'>*</span></th>
						<td>
							<c:choose>
								<c:when test="${verifyTemModel.verify_type == 'vol'}">용량 검증</c:when>
								<c:when test="${verifyTemModel.verify_type == 'sec'}">보안 검증</c:when>
								<c:when test="${verifyTemModel.verify_type == 'cha'}">과금 검증</c:when>
								<c:when test="${verifyTemModel.verify_type == 'non'}">비기능 검증</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th>검증 항목 <span class='necessariness'>*</span></th>
						<td>
							<div style="border:1px solid #ddd;padding:0 0 10px 10px;"><br/>
								<div id="readList_quest1" style="overflow-y:auto;height:350px;">
									<c:choose>
										<c:when test="${not empty readList_quest}">
											<c:forEach var="result" items="${readList_quest}" varStatus="">
												<div class="input_checkbox fl_wrap">
													<span>
														<input type="checkbox" id="quest_seq" name="quest_seq" value="${result.quest_seq}"/>
														<label><c:out value="${result.quest_title}" /></label>
													</span>
												</div>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<div style="height: 20px;">
												<font color="red">등록된 검증 항목이 없습니다. <br/>
												검증 항목 등록 페이지로 이동하여 관련 항목을 추가해 주세요.</font>
											</div>
										</c:otherwise>
									</c:choose>
								</div>
								<div id="readList_quest2"></div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			
				
			<div align="left" class="fl_right" style="width:43%;height:595px;padding-left:1%;border-left:1px solid #ddd;">
			<!--버튼위치영역 -->
				<div class="con_top_btn">
					<span><a href="javascript:fn_create_()">저장</a></span>
					<span><a href="javascript:fn_readList_();">목록</a></span>
				</div>
				<h3 class="stitle">
					<c:choose>
						<c:when test="${verifyTemModel.verify_type == 'vol'}">용량 검증</c:when>
						<c:when test="${verifyTemModel.verify_type == 'sec'}">보안 검증</c:when>
						<c:when test="${verifyTemModel.verify_type == 'cha'}">과금 검증</c:when>
						<c:when test="${verifyTemModel.verify_type == 'non'}">비기능 검증</c:when>
					</c:choose> 
					항목 저장
				</h3>
				<table class="tbl_type11">
					<tr>
						<th style="width: 110px; height: 50px;">검증 타입 <span class='necessariness'>*</span></th>
						<td></td>
						<td>
							<c:choose>
								<c:when test="${verifyTemModel.verify_type == 'vol'}">용량 검증</c:when>
								<c:when test="${verifyTemModel.verify_type == 'sec'}">보안 검증</c:when>
								<c:when test="${verifyTemModel.verify_type == 'cha'}">과금 검증</c:when>
								<c:when test="${verifyTemModel.verify_type == 'non'}">비기능 검증</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th style="width: 110px; height: 50px;">검증 항목 주제 <span class='necessariness'>*</span></th>
						<td></td>
						<td>
						<form:input path="quest_title" class="new_inp inp_w80" maxlength="200"/>
						</td>
					</tr>
					<tr>
						<th style="width: 110px; height: 50px;">질문 타입 <span class='necessariness'>*</span></th>
						<td></td>
						<td>
							<form:select path="quest_type" onchange="fn_itemclick()">
								<form:option value="0">선택형</form:option>
								<form:option value="1">단답형</form:option>
							</form:select>
						</td>
					</tr>
					<tr>
						<th style="width: 110px; height: 50px;">
							답변 항목 <span class='necessariness'>*</span>
						</th>
						<td> 
						</td>
						<td id="answer_input" style="display: none;"> 
							<input type="text" width="410px;" class="inp" maxlength="200" readonly="readonly"/>
						</td>
						<td id="answer_select" style="display: none;"> 
							<div><input type="text" class="new_inp mb03" value="OK" readonly="readonly"/></div>
							<div><input type="text" class="new_inp mb03" value="NOK" readonly="readonly"/></div>
							<div><input type="text" class="new_inp mb03" value="COK" readonly="readonly"/></div>
							<div><input type="text" class="new_inp" value="BYPASS" readonly="readonly"/></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
<script type="text/javaScript" defer="defer">
$(document).ready(function($) {
	fn_itemclick();
});
function fn_itemclick(){
	if($("#quest_type").val() == '0'){
		$("#answer_input").hide();
		$("#answer_select").show();
	}else{
		$("#answer_select").hide();
		$("#answer_input").show();
	}
}
function fn_create_(){
	if($("#quest_title").val()==""){
		alert("검증 항목 주제는 필수 입력 사항입니다.");
		return;
	}
	if (confirm("등록 하시겠습니까?")) {
		doSubmit4File("verifyTemModel", "/verify_tem_mg/verifyTemp_Create.do", "fn_callback_create_", "등록 되었습니다.");
	}
}

function fn_callback_create_(){
	doSubmit4File("verifyTemModel", "/verify_tem_mg/read_Ajax_verifyQue_List.do", "fn_callback_verifyQue_List","");
}
function fn_callback_verifyQue_List(data){
	$("#readList_quest1").empty();
	$("#quest_title").val("");
	$("#quest_type").val("0");
	$("#readList_quest2").html(data);
	fn_itemclick();
}

function fn_readList_() {
	var form = document.getElementById("verifyTemModel");
	form.action = "<c:url value='/verify_tem_mg/verifyTemp_ReadList.do'/>";
	form.submit();
}
</script>
	
	
	
	
</form:form>
	
</body>
</html>