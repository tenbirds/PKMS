<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javaScript"  defer="defer">
$(document).ready(function($) {
	$("#content").val("");
});

/* 수정 화면 */
function fn_read(gubun) {
	var form = document.getElementById('FaqModel');

	if (gubun == "read") {
		form.retUrl.value = "/board/faq/Faq_Read";
	} else if (gubun == "view") {
		form.retUrl.value = "/board/faq/Faq_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}
	form.action = "<c:url value='/board/faq/Faq_Read.do'/>";
	form.submit();
}
function fn_delete(gubun, id){
	var form = document.getElementById('FaqModel');
	form.faq_comment_seq.value = id;
	
	if (confirm("삭제하시겠습니까?")) {
		if(gubun=="faq"){
			doSubmit4File("FaqModel", "/board/faq/Faq_Ajax_Delete.do", "fn_callback_delete", "삭제 되었습니다.");	
		}else if(gubun=="comment"){
			doSubmit4File("FaqModel", "/board/comment/Comment_Delete.do", "fn_callback_create", "삭제 되었습니다.");
		}
		
	}
}
function fn_callback_create(data) {
	$("#content").val("");
	fn_read('view');
}
function fn_callback_delete(data) {
	fn_readList();
}
function fn_readList() {
	var form  = document.getElementById('FaqModel');

	form.action = "/board/faq/Faq_ReadList.do";
	form.submit();
}
function fn_create() {
	
	var content = document.getElementById("content");
	
	if(!isNullAndTrim(content, "내용을 입력해주세요.")){
		$("#content").focus();
		return;
	}
	
	
	doSubmit4File("FaqModel", "/board/comment/Comment_Create.do", "fn_callback_create");
}

</script>
</head>
<body>

	<!-- FAQ폼 시작 -->
	<form:form commandName="FaqModel" name="inputFrom" method="post"  enctype="multipart/form-data" onsubmit="return false;">
	
		<form:hidden path="faq_seq"  />
		
		<!-- return URL -->
		<form:hidden path="retUrl" />
		<!-- 첨부파일 -->
		<form:hidden path="master_file_id" />
		
		<!-- 검색조건 유지 -->
		<form:hidden path="searchCondition"/>
		<form:hidden path="searchKeyword"/>
		
		<form:hidden path="deleteList"/>
		
		<sec:authentication property="principal.username" var="sessionId"/>
		
		<%-- <div class="btn_location">
			<c:choose>
				<c:when test="${sessionId eq FaqModelData.reg_user}">
					<span><a href="javascript:fn_read('read', '<c:out value="${FaqModelData.faq_seq}"/>')"><img src="/images/btn_modify.gif" alt="수정" /></a></span>
				</c:when>
				<c:otherwise>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN">
						<span><a href="javascript:fn_read('read', '<c:out value="${FaqModelData.faq_seq}"/>')"><img src="/images/btn_modify.gif" alt="수정" /></a></span>
					</sec:authorize>
				</c:otherwise>
			</c:choose>
			<span><a href="javascript:fn_readList()"><img src="/images/btn_list.gif" alt="목록" /></a></span>
		</div> --%>
		
		<div class="con_Div32">
			<div class="con_area">
				<div class="con_detail">
					<div class="con_top_btn fl_wrap">
						<c:choose>
							<c:when test="${sessionId eq FaqModelData.reg_user}">
								<span><a href="javascript:fn_read('read', '<c:out value="${FaqModelData.faq_seq}"/>')">수정</a></span>
							</c:when>
							<c:otherwise>
								<sec:authorize ifAnyGranted = "ROLE_ADMIN">
									<span><a href="javascript:fn_read('read', '<c:out value="${FaqModelData.faq_seq}"/>')">수정</a></span>
								</sec:authorize>
							</c:otherwise>
						</c:choose>
						<span><a href="javascript:fn_readList()">목록</a></span>
					</div>
					<!--기본정보 -->
					<h3 class="stitle">FAQ 상세</h3>
					<table class="tbl_type11"> 
						<colgroup>
							<col width="10%">
							<col width="40%">
							<col width="10%">
							<col width="40%">
						</colgroup>
						<tr>
							<th>제목</th>
							<td colspan="3">${FaqModelData.title}</td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="mg20 line_height25">
									${FaqModelData.content}
								</div>
							</td>
						</tr>
						<tr>
							<th>등록자</th>
							<td>${FaqModelData.reg_name}</td>
							<th>등록 시간</th>
							<td>${FaqModelData.reg_date}</td>
						</tr>
						<tr>
							<th>수정자</th>
							<td>${FaqModelData.update_name}</td>
							<th>수정 시간</th>
							<td>${FaqModelData.update_date}</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	<!-- FAQ폼 끝 -->
	</form:form>
	
</body>
</html>