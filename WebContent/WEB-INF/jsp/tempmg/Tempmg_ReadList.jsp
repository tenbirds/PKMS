<%--
/**
 * 템플릿 목록
 * @author : 009
 * @Date : 2012. 4. 23.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script> 

<script type="text/javaScript" defer="defer">
$(document).ready(function($) {
	/* 테이블 스크롤 제어 */
	//doTable(tableId, className, fixHeaderRow, fixLeftCol, widthArr);
// 	doTable("scrollTable", "tbl_type", "1", "0", ["300", "300", "300", "300"]);
});

/* 등록/상세/수정 화면 */
function fn_read(tpl_seq) {
	$("input[id=tpl_seq]").val(tpl_seq);

	var form = document.getElementById("TempmgModel");
	form.action = "<c:url value='/tempmg/Tempmg_Read.do'/>";
	form.submit();
}

/* 목록 페이지 이동 */
function fn_readList(pageIndex) {
	// 검색어없이 검색 시 전체 검색 
	document.getElementById("pageIndex").value = pageIndex;
	
	var form = document.getElementById("TempmgModel");
	form.action = "<c:url value='/tempmg/Tempmg_ReadList.do'/>";
	form.submit();
}

//엑셀 다운로드
function fn_excel_download() {
	doSubmit("TempmgModel", "/tempmg/Tempmg_ExcelDownload.do", "fn_callback_excel_download");
}

// 엑셀 다운로드 callback
function fn_callback_excel_download(data) {
	var excel_file_name = $("input[id=param1]").val();
	downloadFile(excel_file_name, excel_file_name, "");
}

</script>
</head>

<body>
<form:form commandName="TempmgModel" method="post" onsubmit="onSubmitReturnFalse();">
<!-- 페이징 -->
<form:hidden path="pageIndex"/>

<!-- tpl_seq -->
<form:hidden path="tpl_seq" />

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
					<form:select path="searchCondition" items="${TempmgModel.searchConditionsList}" itemLabel="codeName" itemValue="code" />
					<form:input path="searchKeyword" cssClass="txt" onkeypress="if(event.keyCode == 13) { fn_readList(1); }"/>
				</td>
			</tr>
		</table>
		<!--조회버튼 -->
		<div class="new_btn_sear03"><a href="javascript:fn_readList(1);"> 조회</a></div>
	</div>
</div>

<!--테이블, 페이지 DIV 시작 -->
<div class="con_Div2 con_area mt20">
	<div class="con_detail_long">
		<!--버튼위치 -->
		<div class="con_top_btn">
			<span><a href="javascript:fn_read('');">신규</a></span>
			<span><a href="javascript:fn_excel_download();">엑셀다운로드</a></span>
		</div>
						
		<div class="fakeContainer" >
	
			<table id="scrollTable" class="tbl_type12">
				<thead>
					<tr>
						<th scope="col">사용여부</th>
						<th scope="col">템플릿 버전</th>
						<th scope="col">가변 항목 개수</th>
						<th scope="col">등록일</th>
					</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${not empty TempmgModelList}">
						<c:forEach var="result" items="${TempmgModelList}" varStatus="status">
							<tr>
								<td>
									<c:choose>
										<c:when test="${result.use_yn == '사용'}">
											<font color="red"><c:out value="${result.use_yn}" /></font>
										</c:when>
										<c:otherwise>
											<c:out value="${result.use_yn}" />
										</c:otherwise>
									</c:choose>
								</td>
								<td><a href="javascript:fn_read('<c:out value="${result.tpl_seq}"/>')"><c:out value="${result.tpl_ver}" /></a></td>
								<td><c:out value="${result.cnt}" /></td>
								<td><c:out value="${result.reg_date}" /></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
						<td colspan="4">검색 결과가 없습니다.</td>
					</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
		</div>
	
		<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
	</div>
</div>
</form:form>
</body>
</html>
