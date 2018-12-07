<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<c:set var="registerFlag" value="${empty verifyTemModel.quest_seq ? '등록' : '수정'}" />
<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script> 
<script type="text/javaScript" defer="defer">
var is_in_S = '${S_gubun}';
$(document).ready(function($) {
	var elements = $('select option').filter('.veri_selec');
	elements.each(function(){
		if('${verifyTemModel.verify_type}' == $(this).val()){
			$(this).attr("selected",":selected");
		}
	});	
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

function fn_create(){
	if($("#quest_title").val()==""){
		alert("검증 항목 주제는 필수 입력 사항입니다.");
		return;
	}
	if (confirm("등록 하시겠습니까?")) {
		doSubmit4File("verifyTemModel", "/verify_tem_mg/verifyTemp_Create.do", "fn_readList", "등록 되었습니다.");
	}
}

function fn_readList() {
	var form = document.getElementById("verifyTemModel");
	form.action = "<c:url value='/verify_tem_mg/verifyTemp_ReadList.do'/>";
	form.submit();
}

function fn_delete(){
	if (confirm("이 항목을 삭제하면 해당 항목이 검증완료된 PKG에 포함되있을 경우 PKG 검증결과에서도 삭제됩니다.\n삭제 하시겠습니까?")) {
		doSubmit4File("verifyTemModel", "/verify_tem_mg/verifyTemp_Delete.do", "fn_readList", "삭제 되었습니다.");
	}
}
function fn_update(){
	if(is_in_S == "완료"){
		alert("이 검증 항목은 이미 PKG검증 완료가 된 항목이므로 수정할 수 없습니다!");
		return;
	}else{
		if (confirm("정보를 수정 하면 해당 항목을 사용중인 PKG검증에 등록된 검증결과가 모두 삭제되어 검증결과를 다시 저장해야 합니다. \n수정 하시겠습니까?")) {
			doSubmit4File("verifyTemModel", "/verify_tem_mg/verifyTemp_Update.do", "fn_readList", "수정 되었습니다.");
		}
	}
	
}
</script>
</head>
<body>

<form:form commandName="verifyTemModel" method="post"  enctype="multipart/form-data" onsubmit="return false;">
	<input type="hidden" id="item_content" name="item_content">
	<form:hidden path="quest_seq" />
	<form:hidden path="searchCondition"/><!--조회조건 -->
			
	<div class="con_Div32">
		<div class="con_area">
			<div class="con_detail_long">
				<div class="con_top_btn fl_wrap">
					<!--버튼위치영역 -->
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
						
				<!--기본정보 -->
				<h3 class="stitle">검증 항목 정보</h3>
				<table class="tbl_type11">
					<colgroup>
						<col width="15%">
						<col width="*">
						<col width="*">
					</colgroup>
					<tr>
						<th>검증 타입 <span class='necessariness'>*</span></th>
						<td colspan="2">
							<c:if test="${registerFlag == '등록'}">
								<select id="verify_type" name="verify_type">
									<option value="vol" class="veri_selec">용량 검증</option>
									<option value="sec" class="veri_selec">보안 검증</option>
									<option value="cha" class="veri_selec">과금 검증</option>
									<option value="non" class="veri_selec">비기능 검증</option>
								</select>
							</c:if>
							<c:if test="${registerFlag == '수정'}">
								<c:out value="${verifyTemModel.verify_type}" />
							</c:if>
						</td>
					</tr>
					<tr>
						<th>검증 항목 주제 <span class='necessariness'>*</span></th>
						<td colspan="2">
						<form:input path="quest_title" class="new_inp inp_w100"  maxlength="200"/>
						</td>
					</tr>
					<tr>
						<th>질문 타입 <span class='necessariness'>*</span></th>
						<td colspan="2">
							<form:select path="quest_type" onchange="fn_itemclick()">
								<form:option value="0">선택형</form:option>
								<form:option value="1">단답형</form:option>
							</form:select>
						</td>
					</tr>
					<tr>
						<th>
							답변 항목 <span class='necessariness'>*</span>
						</th>
						<td id="answer_input" style="display: none;"> 
							<input type="text" width="800px;" class="new_inp" maxlength="200" readonly="readonly"/>
						</td>
						<td id="answer_select" style="display: none;"> 
							<div><input type="text" class="new_inp mb02" value="OK" readonly="readonly"/></div>
							<div><input type="text" class="new_inp mb02" value="NOK" readonly="readonly"/></div>
							<div><input type="text" class="new_inp mb02" value="COK" readonly="readonly"/></div>
							<div><input type="text" class="new_inp" value="BYPASS" readonly="readonly"/></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</form:form>
	
</body>
</html>