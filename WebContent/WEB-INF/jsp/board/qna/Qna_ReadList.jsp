<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
		form.retUrl.value = "/board/qna/Qna_Read";
	} else if (gubun == "view") {
		form.retUrl.value = "/board/qna/Qna_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}
	
	form.qna_seq.value = id;
	form.action = "<c:url value='/board/qna/Qna_Read.do'/>";
	form.submit();
}

/* 목록 페이지 이동 */
function fn_readList(pageIndex) {
	document.getElementById('pageIndex').value = pageIndex;
	
	var form = document.getElementById('listFrom');
	form.action = "<c:url value='/board/qna/Qna_ReadList.do'/>";
	form.submit();
}

</script>
</head>

<body>
	<form:form modelAttribute="QnaModel" id="listFrom" name="listFrom" method="post" onsubmit="return false;">
	<form:hidden path="qna_seq"/>
	<form:hidden path="retUrl" />
	<form:hidden path="pageIndex" />
	
		
		<%-- <div class="search">
			<table class="sear_table1" >
				<tr>
					<th>검색</th>
					<td>
						<form:select path="searchCondition"  items="${QnaModel.searchConditionsList}" itemLabel="codeName" itemValue="code" />
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
							<form:select path="searchCondition" items="${QnaModel.searchConditionsList}" itemLabel="codeName" itemValue="code"  cssStyle="width:10%;" />
							<form:input path="searchKeyword" cssClass="inp_w50" onkeypress="if(event.keyCode == 13) { fn_readList(1); return;}"/>
						</td>
					</tr>
				</table>
				<!--조회버튼 -->
				<div class="new_btn_sear03"><a href="javascript:fn_readList(1)">조회</a></div>
			</div>
		</div>

		<!--버튼위치 -->
		<!-- <div class="btn_location">
			<span><a href="javascript:fn_read('read', '')"><img src="/images/btn_new.gif" alt="저장" /></a></span>
		</div>
		 -->
		<!--테이블, 페이지 DIV 시작 -->
		<div class="con_Div2 con_area mt20">
			<div class="con_detail">
				<!--버튼위치 -->
				<div class="con_top_btn">
					<sec:authorize ifAnyGranted="ROLE_ADMIN">
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
							<c:when test="${not empty qnaModelList}">
								<c:forEach var="qnaModelList" items="${qnaModelList}" varStatus="status">
									<tr>
										<td><c:out value="${qnaModelList.qna_seq}" /></td>
										<td style="text-align: left;">
											<a href="javascript:fn_read('view', '<c:out value="${qnaModelList.qna_seq}"/>')">
												<c:out value="${fn:substring(qnaModelList.title, 0, 48)}" />
												<c:out value="${fn:length(qnaModelList.title) > 48 ? '...' : ''}" />
											</a>
											<c:if test="${qnaModelList.cnt != 0}">
												&nbsp;[${qnaModelList.cnt}]
											</c:if>
										</td>
										<td><c:out value="${qnaModelList.reg_name}" /></td>
										<td><c:out value="${qnaModelList.reg_date}" /></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr><td colspan="4">등록된 QnA가 없습니다.</td></tr>
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