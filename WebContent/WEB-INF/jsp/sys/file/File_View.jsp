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

/* 수정 화면 */
function fn_read(gubun, id) {
	var form = document.getElementById('NoticeModel');

	if (gubun == "read") {
		form.retUrl.value = "/board/notice/Notice_Read";
	} else if (gubun == "view") {
		form.retUrl.value = "/board/notice/Notice_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}
	form.id.value = id;
	form.action = "<c:url value='/board/notice/Notice_Read.do'/>";
	form.submit();
}
function fn_delete(){
	if (confirm("삭제하시겠습니까?")) {
		doSubmit4File("NoticeModel", "/board/notice/Notice_Ajax_Delete.do", "fn_callback_delete", "삭제 되었습니다.");
	}
}
function fn_callback_create(data) {
	fn_readList();
}
function fn_callback_update(data) {
	;
}
function fn_callback_delete(data) {
	fn_readList();
}
function fn_readList() {
	var form  = document.getElementById('NoticeModel');

	form.action = "/board/notice/Notice_ReadList.do";
	form.submit();
}
</script>
</head>
<body>

	<form:form commandName="NoticeModel" method="post"  enctype="multipart/form-data" onsubmit="return false;">
	
		<form:hidden path="notice_seq"/>
		<!-- 첨부파일 -->
		<form:hidden path="master_file_id" />
		<form:hidden path="deleteList" />
		
		<!-- 페이징 -->
		<form:hidden path="pageIndex"/>
		
		<!-- return URL -->
		<form:hidden path="retUrl" />
		
		<!-- 검색조건 유지 -->
		<form:hidden path="searchCondition"/>
		<form:hidden path="searchKeyword"/>
		
		<sec:authentication property="principal.username" var="sessionId"/>
		
		<div class="btn_location">
			<c:choose>
				<c:when test="${sessionId eq NoticeModel.reg_user}">
					<span><a href="javascript:fn_read('read', '<c:out value="${NoticeModel.notice_seq}"/>')"><img src="/images/btn_modify.gif" alt="수정" /></a></span>
				</c:when>
				<c:otherwise>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN">
						<span><a href="javascript:fn_read('read', '<c:out value="${NoticeModel.notice_seq}"/>')"><img src="/images/btn_modify.gif" alt="수정" /></a></span>
					</sec:authorize>
				</c:otherwise>
			</c:choose>
			<span><a href="javascript:fn_readList()"><img src="/images/btn_list.gif" alt="목록" /></a></span>
		</div>
		
		<div class="con_Div3">
			<!--기본정보 -->
			<fieldset>
			<legend>
				기본정보
			</legend>

			<table class="tbl_type1" style="width: 100%"> 
				<tr>
					<th>제목</th>
					<td colspan="3">${NoticeModel.title}</td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3">${NoticeModel.content}</td>
				</tr>
				<sec:authorize ifAnyGranted = "ROLE_ADMIN">
				<c:if test="${NoticeModel.main_pop_yn}">
				<tr>
					<th>메인팝업기간</th>
					<td colspan="3">
						${NoticeModel.main_date_start} ~ ${NoticeModel.main_date_end}
					</td>
				</tr>
				</c:if>
				</sec:authorize>
				<tr>
					<th>등록자</th>
					<td class="inp_w3">${NoticeModel.reg_name}</td>
					<th>등록 시간</th>
					<td>${NoticeModel.reg_date}</td>
				</tr>
				<tr>
					<th>수정자</th>
					<td class="inp_w3">${NoticeModel.update_name}</td>
					<th>수정 시간</th>
					<td>${NoticeModel.update_date}</td>
				</tr>
				<tr>
					<th>첨부파일1</th>
					<td>
						<ui:file attachFileModel="${NoticeModel.file1}" name="file1" mode="view" />
					</td>
					<th>첨부파일2</th>
					<td>
						<ui:file attachFileModel="${NoticeModel.file2}" name="file2" mode="view" />
					</td>
				</tr>
				<tr>
					<th>첨부파일3</th>
					<td>
						<ui:file attachFileModel="${NoticeModel.file3}" name="file3" mode="view" />
					</td>
					<th>첨부파일4</th>
					<td>
						<ui:file attachFileModel="${NoticeModel.file4}" name="file4" mode="view" />
					</td>
				</tr>
				<tr>
					<th>첨부파일5</th>
					<td colspan="3">
						<ui:file attachFileModel="${NoticeModel.file5}" name="file5" mode="view" />
					</td>
				</tr>
			</table>

			</fieldset>
		</div>
	</form:form>
</body>
</html>