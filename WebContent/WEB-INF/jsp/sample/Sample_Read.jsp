<%--
/**
 * ReadList Sample
 * 새로운 페이지 작성 시 Sample이 되는 템플릿 jsp
 * 
 * @author : scott
 * @Date : 2012. 3. 9.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<c:set var="registerFlag" value="${empty SampleModel.id ? '등록' : '수정'}" />

<html>
<head>
<script type="text/javaScript" defer="defer">
/* 등록 */
function fn_create() {
	
	if (document.getElementById("user_name").value == '') {
		alert('이름을 입력 하세요.');
		return;
	}

	
	if (confirm("레알?")) {
		doSubmit("SampleModel", "<c:url value='/sample/Sample_Create.do'/>",
				"fn_callback_create");
	}
}

/* 목록 */
function fn_readList() {
	var form = document.getElementById("SampleModel");
	form.action = "<c:url value='/sample/Sample_ReadList.do'/>";
	form.submit();
}

/* 수정 */
function fn_update() {
	if (confirm("레알?")) {
		doSubmit("SampleModel", "<c:url value='/sample/Sample_Update.do'/>",
				"fn_callback_update");
	}
}

/* 삭제 */
function fn_delete() {
	if (confirm("레알?")) {
		doSubmit("SampleModel", "<c:url value='/sample/Sample_Delete.do'/>",
				"fn_callback_delete");
	}
}

/* Callback Create */
function fn_callback_create(data) {
	alert("create callback!!!");
	fn_readList();
}

/* Callback Update */
function fn_callback_update(data) {
	alert("update callback!!!");
	//window.location.reload();
	fn_read("read");
}

/* Callback Delete */
function fn_callback_delete(data) {
	alert("delete callback!!!");
	fn_readList();
}

function fn_read(gubun) {
	var retUrl = document.getElementById("retUrl");

	if (gubun == "read") {
		retUrl.value = "<c:url value='/sample/Sample_Read'/>";
	} else if (gubun == "view") {
		retUrl.value = "<c:url value='/sample/Sample_View'/>";
	} else {
		alert("you must have param selected read or view");
		return;
	}
	
	var form = document.getElementById("SampleModel");
	form.action = "<c:url value='/sample/Sample_Read.do'/>";
	form.submit();
}

</script>

</head>
<body>
<form:form commandName="SampleModel" method="post" enctype="multipart/form-data">

<!-- 첨부파일 -->
<form:hidden path="file_id" />

<!-- 페이징 -->
<form:hidden path="pageIndex"/>

<!-- return URL -->
<form:hidden path="retUrl" />

<!-- 검색조건 유지 -->
<form:hidden path="searchCondition"/>
<form:hidden path="searchKeyword"/>

<!--버튼위치영역 -->
<div class="btn_location">
	<c:choose>
		<c:when test="${registerFlag == '수정'}">
			<span><a href="javascript:fn_update();"><img src="/images/btn_modify.gif" alt="수정" /></a></span>
			<span><a href="javascript:fn_delete();"><img src="/images/btn_delete.gif" alt="삭제" /></a></span>
		</c:when>
		<c:otherwise>
			<span><a href="javascript:fn_create();"><img src="/images/btn_save.gif" alt="저장" /></a></span>
		</c:otherwise>
	</c:choose>

	<span><a href="javascript:fn_readList();"><img src="/images/btn_list.gif" alt="목록" /></a></span>
	<span><a href="javascript:window.location.reload();"><img src="/images/btn_identify.gif" alt="확인" /></a></span>

</div>

<!--기본정보, 상세정보 DIV 시작(스크롤지정)-->
<div class="con_Div3">
	<!--기본정보 -->
	<fieldset>
		<legend>기본정보 <c:out value="${registerFlag}" /></legend>

		<table class="tbl_type1">
			<c:choose>
				<c:when test="${registerFlag == '수정'}">
					<tr>
						<th>아이디</th>
						<td><c:out value="${SampleModel.id}" /></td>
						<form:hidden path="id"/>
					</tr>
					<tr>
						<th>이름</th>
						<td><c:out value="${SampleModel.user_name}" /></td>
						<form:hidden path="user_name"/>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<th>이름</th>
						<td><form:input path="user_name" maxlength="30" class="inp inp_w1" /></td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<th>사용여부</th>
				<td>
					<form:select path="use_yn" items="${SampleModel.useYnItems}" itemLabel="codeName" itemValue="code" />
				</td>
			</tr>
			<tr>
				<th>수신 여부</th>
				<td><form:checkbox path="recv_yn" label="얼씨구"/></td>
			</tr>
			<tr>
				<th>성별</th>
				<td>
					<form:radiobuttons path="sex" items="${SampleModel.sexList}" itemLabel="codeName" itemValue="code" />
				</td>
			</tr>
				<c:choose>
					<c:when test="${registerFlag == '수정'}">
						<tr>
							<th>등록자</th>
							<td><c:out value="${SampleModel.reg_user}" /></td>
						</tr>
						<tr>
							<th>수정자</th>
							<td><c:out value="${SampleModel.update_user}" /></td>
						</tr>
					</c:when>
				</c:choose>
			
			<tr>
				<th>비밀번호</th>
				<td><form:password path="passwd" maxlength="20" class="inp inp_w2" /></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><form:password path="passwd" maxlength="20" class="inp inp_w3" /></td>
			</tr>
		</table>
	</fieldset>

	<!--상세정보 -->
	<fieldset class="detail_fieldset">
		<legend>첨부파일 및 상세정보</legend>

		<table class="tbl_type1">
			<tr>
				<th>샘플 파일1</th>
				<td>
					<c:choose>
						<c:when test="${registerFlag == '수정'}">
							<ui:file attachFileModel="${SampleModel.file1}" name="file1" mode="update" />
						</c:when>
						<c:otherwise>
							<ui:file attachFileModel="${SampleModel.file1}" name="file1" mode="create" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th>샘플 파일2</th>
				<td>
					<c:choose>
						<c:when test="${registerFlag == '수정'}">
							<ui:file attachFileModel="${SampleModel.file2}" name="file2" mode="update" />
						</c:when>
						<c:otherwise>
							<ui:file attachFileModel="${SampleModel.file2}" name="file2" mode="create" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th>설명</th>
				<td><form:textarea path="description" rows="14" cols="58" class="inp_w1" /></td>
			</tr>
		</table>
	</fieldset>
</div>

</form:form>
</body>
</html>

