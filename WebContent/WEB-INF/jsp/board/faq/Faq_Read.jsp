<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<c:set var="registerFlag" value="${empty FaqModel.faq_seq ? '등록' : '수정'}" />
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javaScript"  defer="defer">

$(document).ready(function($) {
	
	$('textarea[maxlength]').keydown(function(){
	    var max = parseInt($(this).attr('maxlength'));
	    var str = $(this).val();
		if(str.length > max) {
		    $(this).val(str.substr(0, max-1));
			alert("최대 [" + max + " 자]까지 입력 가능합니다.");
		}
	});
	
});
/* 수정 화면 */
function fn_read(id) {
	var form = document.getElementById('FaqModel');

	form.id.value = id;
	form.retUrl.value = "/board/faq/Faq_View";
	form.action = "<c:url value='/board/faq/Faq_Read.do'/>";
	form.submit();
}

function fn_create() {
	
	if(!fn_Validation())
		return;
	
	doSubmit4File("FaqModel", "/board/faq/Faq_Ajax_Create.do", "fn_callback_create");
}
function fn_update() {
	
	if(!fn_Validation())
		return;
	
	doSubmit4File("FaqModel", "/board/faq/Faq_Ajax_Update.do", "fn_callback_update");
}
function fn_delete(){
	if (confirm("삭제하시겠습니까?")) {
		doSubmit4File("FaqModel", "/board/faq/Faq_Ajax_Delete.do", "fn_callback_delete", "삭제 되었습니다.");
	}
}
function fn_callback_create(data) {
	fn_readList();
}
function fn_callback_update(data) {
	fn_read();
}
function fn_callback_delete(data) {
	fn_readList();
}
function fn_readList() {
	var form  = document.getElementById('FaqModel');

	form.action = "/board/faq/Faq_ReadList.do";
	form.submit();
}
function fn_Validation(){
	
	var title = document.getElementById("title");
	var content = document.getElementById("content");
	
	if(!isNullAndTrim(title, "제목을 입력해주세요.")){
		$("#title").focus();
		return false;
	}
	if(!isNullAndTrim(content, "내용을 입력해주세요.")){
		$("#content").focus();
		return false;
	}
	
	return true;
}
</script>
</head>
<body>
	<form:form commandName="FaqModel" method="post" enctype="multipart/form-data" onsubmit="return false;">
	
		<form:hidden path="faq_seq"/>
		<!-- return URL -->
		<form:hidden path="retUrl" />
		<!-- 첨부파일 -->
		<form:hidden path="master_file_id" />
		
		<!-- 검색조건 유지 -->
		<form:hidden path="searchCondition"/>
		<form:hidden path="searchKeyword"/>
		
		<form:hidden path="deleteList"/>
		
		<%-- <div class="btn_location">
			<c:choose>
				<c:when test="${registerFlag == '등록'}">
					<span><a href="javascript:fn_create()"><img src="/images/btn_save.gif" alt="저장" /></a></span>
				</c:when>
				<c:otherwise>
					<span><a href="javascript:fn_delete()"><img src="/images/btn_delete.gif" alt="삭제" /></a></span>
					<span><a href="javascript:fn_update()"><img src="/images/btn_modify.gif" alt="수정" /></a></span>
				</c:otherwise>
			</c:choose>
			<span><a href="javascript:fn_readList()"><img src="/images/btn_list.gif" alt="목록" /></a></span>
		</div> --%>
		
		<div class="con_Div32">
			<div class="con_area">
				<div class="con_detail">
					<div class="con_top_btn fl_wrap">
						<c:choose>
							<c:when test="${registerFlag == '등록'}">
								<span><a href="javascript:fn_create()">저장</a></span>
							</c:when>
							<c:otherwise>
								<span><a href="javascript:fn_delete()">삭제</a></span>
								<span><a href="javascript:fn_update()">수정</a></span>
							</c:otherwise>
						</c:choose>
						<span><a href="javascript:fn_readList()">목록</a></span>
					</div>
					<!--기본정보 -->
		
					<h3 class="stitle">기본정보</h3>
					<table class="tbl_type11"> 
						<colgroup>
							<col width="10%">
							<col width="90%">
						</colgroup>
						<tr>
							<th>제목 <span  class='necessariness'>*</span></th>
							<td><form:input path="title" class="new_inp" style="width:96%;" maxlength="50"/></td>
						</tr>
						<tr>
							<th>내용 <span  class='necessariness'>*</span></th>
							<td><form:textarea path="content" rows="10" class="textarea_w95" maxlength="15000"/></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form:form>
	
</body>
</html>