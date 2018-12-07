<%--
/**
 * 협력업체 목록 페이지
 * 
 * @author : skywarker
 * @Date : 2012. 4. 19.
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

<script type="text/javaScript" defer="defer">
	$(document).ready(
			function($) {
				/* 테이블 스크롤 제어 */
				//doTable(tableId, className, fixHeaderRow, fixLeftCol, widthArr);
// 				doTable("scrollTable", "tbl_type11", "1", "0", [ "140", "100","200", "80", "130", "380", "100" ]);
			});

	/* 등록/상세/수정 화면 */
	function fn_read(bp_num) {

		var retUrl = document.getElementById("retUrl");
		retUrl.value = "<c:url value='/usermg/bp/Bp_Read'/>";

		document.getElementById("bp_num").value = bp_num;

		var form = document.getElementById("BpModel");
		form.action = "<c:url value='/usermg/bp/Bp_Read.do'/>";
		form.submit();
	}

	/* 목록 페이지 이동 */
	function fn_readList(pageIndex) {
		// 검색어없이 검색 시 전체 검색 
		document.getElementById("pageIndex").value = pageIndex;

		var form = document.getElementById("BpModel");
		form.action = "<c:url value='/usermg/bp/Bp_ReadList.do'/>";
		form.submit();
	}
</script>
</head>

<body>
	<form:form commandName="BpModel" method="post" onsubmit="return false;">
		<!-- 페이징 -->
		<form:hidden path="pageIndex" />

		<!-- return URL -->
		<form:hidden path="retUrl" />

		<form:hidden path="bp_num" />

		<!--조회 -->
		<div class="new_con_Div32">
			<div class="new_search fl_wrap">
				<table class="new_sear_table2">
					<colgroup>
					<col width="10%">
					<col width="30%">
					<col width="10%">
					<col width="*">
					</colgroup>
					<tr>
						<th>상태</th>
						<td><form:select path="searchFilter" items="${BpModel.approvalStateFilterList}" itemLabel="codeName" itemValue="code" /></td>
						<th>검색</th>
						<td><form:select path="searchCondition" items="${BpModel.searchConditionsList}" itemLabel="codeName" itemValue="code" /> <form:input path="searchKeyword" cssClass="txt"
								onkeypress="if(event.keyCode == 13) { fn_readList(1);}" /></td>
					</tr>
				</table>
	
				<!--조회버튼 -->
				<div class="new_btn_sear03"><a href="javascript:fn_readList(1);" class="line_1">검색</a></div>
			</div>
		</div>
		<br>

		<!--테이블, 페이지 DIV 시작 -->
		<div class="con_Div2 con_area">
			<div class="con_detail">
				<!--버튼위치 -->
				<div class="con_top_btn">
					<sec:authorize ifAnyGranted="ROLE_ADMIN">
						<span><a href="javascript:fn_read('');">신규</a></span>
					</sec:authorize>
				</div>
<!-- 				<div class="fakeContainer" > -->
				<table id="scrollTable" class="tbl_type12" >
					<colgroup>
					<col width="10%">
					<col width="10%">
					<col width="12%">
					<col width="10%">
					<col width="10%">
					<col width="35%">
					<col width="13%">
					</colgroup>
					<thead>
						<tr>
							<th scope="col" class="th_center">상태</th>
							<th scope="col" class="th_center">접속허용여부</th>
							<th scope="col" class="th_center">업체명</th>
							<th scope="col" class="th_center">업체구분</th>
							<th scope="col" class="th_center">사업자등록번호</th>
							<th scope="col" class="th_center">주소</th>
							<th scope="col" class="th_center">전화번호</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${BpModel.totalCount == 0}">
							<tr>
								<td colspan="7" class="td_center">검색 결과가 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach var="result" items="${BpModelList}" varStatus="status">
							<tr>
								<td class="td_center"><c:choose>
										<c:when test="${result.approval_state == '3'}">
											<font color="gray">임시저장</font>
										</c:when>
										<c:when test="${result.approval_count == 0}">
											<font color="green">승인완료</font>
										</c:when>
										<c:when test="${result.approval_count == 1 && result.approval_state == '0'}">
											<font color="orange">업체 승인요청</font>
										</c:when>
										<c:when test="${result.approval_count > 1 && result.approval_state == '0'}">
											<font color="orange">업체/담당자 승인요청</font>
										</c:when>
										<c:otherwise>
											<font color="orange">담당자 승인요청</font>
										</c:otherwise>
									</c:choose></td>
								<td class="td_center"><c:out value="${result.use_yn == 'Y' ? '예' : '아니오'}" />&nbsp;</td>
								<td><a href="javascript:fn_read('<c:out value="${result.bp_num}"/>')"><c:out value="${result.bp_name}" />&nbsp;</a></td>
								<td class="td_center"><c:out value="${result.bp_gubun == 'S' ? '공급사' : '제조사'}" />&nbsp;</td>
								<td><a href="javascript:fn_read('<c:out value="${result.bp_num}"/>')"><c:out value="${result.bp_num}" />&nbsp;</a></td>
								<td style='overflow:hidden; white-space:nowrap; text-overflow:ellipsis;'><c:out value="${result.bp_addr}" />&nbsp;</td>
								<td><c:out value="${result.bp_tel1}" />&nbsp; <c:out value="${result.bp_tel2}" />&nbsp; <c:out value="${result.bp_tel3}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
<!-- 				</div> -->
			</div>
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />

		</div>
	</form:form>
</body>
</html>
