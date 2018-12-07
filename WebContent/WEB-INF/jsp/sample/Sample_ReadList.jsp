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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script> 
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>


<script type="text/javaScript" defer="defer">
$(document).ready(function($) {
	/* 테이블 스크롤 제어 */
	//doTable(tableId, className, fixHeaderRow, fixLeftCol, widthArr);
	doTable("scrollTable", "tbl_type", "1", "0", ["100", "200", "250", "450", "200"]);
	
	init_sktuser_popup();
	init_system_popup();
});

/* 등록/상세/수정 화면 */
function fn_read(gubun, id) {
	var retUrl = document.getElementById("retUrl");

	if (gubun == "read") {
		retUrl.value = "<c:url value='/sample/Sample_Read'/>";
	} else if (gubun == "view") {
		retUrl.value = "<c:url value='/sample/Sample_View'/>";
	} else {
		alert("you must have param selected read or view");
		return;
	}
	
	document.getElementById("id").value = id;

	var form = document.getElementById("SampleModel");
	form.action = "<c:url value='/sample/Sample_Read.do'/>";
	form.submit();
}

/* 목록 페이지 이동 */
function fn_readList(pageIndex) {
	// 검색어없이 검색 시 전체 검색 
	document.getElementById("pageIndex").value = pageIndex;
	
	var form = document.getElementById("SampleModel");
	form.action = "<c:url value='/sample/Sample_ReadList.do'/>";
	form.submit();
}


/* SKT 사용자 선택 결과 처리 */
function fn_sktuser_popup_callback(user_id, user_name){
	$("#sktuser").html(user_id+", "+ user_name);
}

//시스템 선택 결과 처리
function fn_system_popup_callback(system_key, system_name){
	$("#system").html(system_key+", "+ system_name);
}

</script>
</head>

<body>
<form:form commandName="SampleModel" method="post">
<!-- 페이징 -->
<form:hidden path="pageIndex"/>

<!-- return URL -->
<form:hidden path="retUrl" />

<form:hidden path="id" />

<!--조회 -->
<div class="search">
	<table class="sear_table1">
		<tr>
			<th>검색</th>
			<td>
				<form:select path="searchCondition" items="${SampleModel.searchConditionsList}" itemLabel="codeName" itemValue="code" />
				<form:input path="searchKeyword" cssClass="txt" onkeypress="if(event.keyCode == 13) { fn_readList(1); return;}"/>
			</td>
		</tr>
	</table>

	<!--조회버튼 -->
	<div class="btn_sear">
		<a href="javascript:fn_readList(1);"></a>
	</div>
</div>

<!--버튼위치 -->
<div class="btn_location">
	<span><a href="javascript:fn_read('read', '');"><img src="/images/btn_new.gif" alt="저장" /></a></span>
	<span><a href="#"><img src="/images/btn_excelldown.gif" alt="저장" /></a></span>
</div>

<!--테이블, 페이지 DIV 시작 -->
<div class="con_Div2">
	<div class="fakeContainer" style="width:1200px;height:400px">
<!-- 	
	<c:out value="${SampleModel.totalCount}" />
 -->	
	<table id="scrollTable" class="tbl_type" border="1">
		<thead>
			<tr>
				<th scope="col">ID</th>
				<th scope="col">이름</th>
				<th scope="col">사용 여부</th>
				<th scope="col">설명</th>
				<th scope="col">등록자</th>
			</tr>
		</thead>
		<tbody>
		<c:if test="${SampleModel.totalCount == 0}">
			<tr>
				<td colspan="5">검색 결과가 없습니다.</td>
			</tr>
		</c:if>
		<c:forEach var="result" items="${SampleModelList}" varStatus="status">
			<tr>
				<td><a href="javascript:fn_read('read', '<c:out value="${result.id}"/>')"><c:out value="${result.id}" /></a></td>
				<td><c:out value="${result.user_name}" />&nbsp;</td>
				<td><c:out value="${result.use_yn}" />&nbsp;</td>
				<td><c:out value="${result.description}" />&nbsp;</td>
				<td><c:out value="${result.reg_user}" />&nbsp;</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>

	<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />


	<sec:authorize ifAllGranted = "ROLE_ADMIN, ROLE_MANAGER">
		관리자, 검증자 모두 포함되어야<br/>
	</sec:authorize>
	
	<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
		관리자, 검증자, 운용자, BP 중에 하나라도 포함되면 - 로그인한 사용자 ID: 	<sec:authentication property="principal.username"/><br/>
	</sec:authorize>
	
	<sec:authorize ifNotGranted = "ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
		검증자 , 운용자, BP 중에 하나라도 포함되면 안 보여줌<br/>
	</sec:authorize>

	<br />	
	<br />	
	<span>SKT 사용자 조회 테스트 <img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_sktuser_popup" align="absmiddle" /></span>
	<div id="sktuser"></div>
	
	<br />	
	<br />	
	<span>시스템 조회 테스트 <img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup" align="absmiddle" /></span>
	<div id="system"></div>

</div>
</form:form>
</body>
</html>
