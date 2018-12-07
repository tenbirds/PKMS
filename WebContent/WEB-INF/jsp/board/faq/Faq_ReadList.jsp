<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script> 
<script type="text/javaScript"  defer="defer">
$(document).ready(function($) {
	/* 테이블 스크롤 제어 */
	//doTable(tableId, className, fixHeaderRow, fixLeftCol, widthArr);
// 	doTable("scrollTable", "tbl_type", "1", "0", ["150", "750", "150", "150"]);
});

/* 수정 화면 */
function fn_read(gubun, id) {
	var form = document.getElementById('listFrom');
	
	if (gubun == "read") {
		form.retUrl.value = "/board/faq/Faq_Read";
	} else if (gubun == "view") {
		form.retUrl.value = "/board/faq/Faq_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}
	
	form.faq_seq.value = id;
	form.action = "<c:url value='/board/faq/Faq_Read.do'/>";
	form.submit();
}

/* 목록 페이지 이동 */
function fn_readList(pageIndex) {
	document.getElementById('pageIndex').value = pageIndex;
	
	var form = document.getElementById('listFrom');
	form.action = "<c:url value='/board/faq/Faq_ReadList.do'/>";
	form.submit();
}

</script>
</head>

<body>
	<form:form modelAttribute="FaqModel" id="listFrom" name="listFrom" method="post" onsubmit="return false;">
	<form:hidden path="faq_seq"/>
	<form:hidden path="retUrl" />
	<form:hidden path="pageIndex" />
	
		<!--조회 -->
		<%-- <div class="search">
			<table class="sear_table1" >
				<tr>
					<th>검색</th>
					<td>
						<form:select path="searchCondition"  items="${FaqModel.searchConditionsList}" itemLabel="codeName" itemValue="code" />
					</td>
					<td><form:input path="searchKeyword" cssClass="txt" onkeypress="if(event.keyCode == 13) { fn_readList(1); return;}"/></td>
				</tr>
			</table>

			<!--조회버튼 -->
			<div class="btn_sear"><a href="javascript:fn_readList(1)"></a></div>
		</div> --%>
		<!--조회 -->
		<div class="new_con_Div32">
			<div class="new_search fl_wrap">
				<table class="new_sear_table2" style="width:90%;">
					<colgroup>
					<col width="10%">
					<col width="90%">
					</colgroup>
					<tr>
						<th>검색</th>
						<td>
							<form:select path="searchCondition"  items="${FaqModel.searchConditionsList}" itemLabel="codeName" itemValue="code"  cssStyle="width:10%;" />
							<form:input path="searchKeyword" cssClass="inp_w50" onkeypress="if(event.keyCode == 13) { fn_readList(1); return;}"/>
						</td>
					</tr>
				</table>
				<!--조회버튼 -->
				<div class="new_btn_sear03"><a href="javascript:fn_readList(1)">조회</a></div>
			</div>
		</div>

		<!--버튼위치 -->
		<%-- <div class="btn_location">
			<sec:authorize ifAnyGranted = "ROLE_ADMIN">
				<span><a href="javascript:fn_read('read', '')"><img src="/images/btn_new.gif" alt="저장" /></a></span>
			</sec:authorize>
		</div> --%>

		<!--테이블, 페이지 DIV 시작 -->
		<!--테이블, 페이지 DIV 시작 -->
		<div class="con_Div2 con_area mt20">
			<div class="con_detail">
				<!--버튼위치 -->
				<div class="con_top_btn">
					<sec:authorize ifAnyGranted = "ROLE_ADMIN">
						<span><a href="javascript:fn_read('read', '')">신규</a></span>
					</sec:authorize>
				</div>
				<!--테이블 -->
				<table id="scrollTable" class="tbl_type12">
					<colgroup>
						<col width="10%">
						<col width="*">
						<col width="10%">
						<col width="20%">
					</colgroup>
				<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">제목</th>
					<th scope="col">이름</th>
					<th scope="col">등록일</th>
				</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty faqModelList}">
							<c:forEach var="faqModelList" items="${faqModelList}" varStatus="status">
								<tr>
									<td><c:out value="${faqModelList.faq_seq}" /></td>
									<td style="text-align: left;">
										<a href="javascript:fn_read('view', '<c:out value="${faqModelList.faq_seq}"/>')">
											<c:out value="${fn:substring(faqModelList.title, 0, 48)}" />
											<c:out value="${fn:length(faqModelList.title) > 48 ? '...' : ''}" />
										</a>
										<c:if test="${faqModelList.cnt != 0}">
											&nbsp;[${faqModelList.cnt}]
										</c:if>
									</td>
									<td><c:out value="${faqModelList.reg_name}" /></td>
									<td><c:out value="${faqModelList.reg_date}" /></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr><td colspan="4">등록된 FAQ가 없습니다.</td></tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			</div>
			
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
			
		</div>
	</form:form>
	
</body>
</html>