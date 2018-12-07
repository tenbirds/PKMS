<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
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
function fn_read(id) {
	var form = document.listFrom;
	form.doc_seq.value = id;
	form.action = "<c:url value='/board/Documentmg_Read.do'/>";
	form.submit();
}

/* 목록 페이지 이동 */
function fn_readList(pageIndex) {
	document.getElementById('pageIndex').value = pageIndex;
	var form = document.getElementById('listFrom');
	form.action = "<c:url value='/board/Documentmg_ReadList.do'/>";
	form.submit();
}

function fn_delete(){
	if (confirm("삭제하시겠습니까?")) {
		doSubmit4File("listFrom", "/tempmg/documentmg_Delete.do", "fn_callback_delete", "삭제 되었습니다.");
	}
}

function fn_callback_delete(data) {
// 	fn_readList(1);
	var form = document.getElementById('listFrom');
	form.action = "<c:url value='/board/Documentmg_ReadList.do'/>";
	form.submit();
}

function fn_readList(pageIndex) {
	document.getElementById('pageIndex').value = pageIndex;
	var form  = document.getElementById('listFrom');

	form.action = "<c:url value='/board/Documentmg_ReadList.do'/>";
	form.submit();
}


		
</script>
</head>

<body>
	<form:form commandName="documentmgModel" id="listFrom" name="listFrom" method="post" onsubmit="return false;">
	<form:hidden path="pageIndex" />
	<form:hidden path="doc_seq"/>
	<form:hidden path="retUrl" />
	
		<!--조회 -->
		<div class="new_con_Div32">
			<div class="new_search fl_wrap">
				<table class="new_sear_table2" style="width:90%;">
					<colgroup>
					<col width="15%">
					<col width="*">
					</colgroup>
					<tr>
						<th>문서 구분 검색</th>
						<td>
						 <input type="text" id="doc_name" name="doc_name" class="fl_left">
						
<%-- 							<form:select path="searchCondition" items="${noticeModel.searchConditionsList}" itemLabel="codeName" itemValue="code"  cssStyle="width:10%;" /> --%>
<%-- 							<form:input path="searchKeyword" cssClass="inp_w50" onkeypress="if(event.keyCode == 13) { fn_readList(1); return;}"/> --%>
						</td>
					</tr>
				</table>
				<!--조회버튼 -->
				<div class="new_btn_sear03"><a href="javascript:fn_readList(1)">조회</a></div>
			</div>
		</div>


		<div class="con_Div2 con_area mt20">
			<div class="con_detail">
				<!--버튼위치 -->
				<div class="con_top_btn">
					<sec:authorize ifAnyGranted="ROLE_ADMIN">
				<span class="left"><a href="javascript:fn_delete()">삭제</a></span> 
						<span><a href="javascript:fn_read('new')">신규</a></span>
					</sec:authorize>
				</div>
				<!--버튼위치 -->
				<%-- <div class="btn_location">
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER">
						<span><a href="javascript:fn_read('read', '')"><img src="/images/btn_new.gif" alt="신규" /></a></span>
					</sec:authorize>
				</div> --%>
				<!--테이블, 페이지 DIV 시작 -->
				<!-- 스크롤 DIV -->
<!-- 				<div class="fakeContainer"> -->
					<!--테이블 -->
					<table id="scrollTable" class="tbl_type12">
						<colgroup>
							<col width="10%">
							<col width="*">
							<col width="25%">
							<col width="10%">
							<col width="10%">
							<col width="15%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">No</th>
								<th scope="col">문서구분</th>
								<th scope="col">문서명</th>
								<th scope="col">버전</th>
								<th scope="col">등록자</th>
								<th scope="col">등록일</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty DocumentmgReadList}">
									<c:forEach var="readList" items="${DocumentmgReadList}" varStatus="status">
										<tr>
											<td>
											<input type="checkbox" id="chk_doc_seq" name="chk_doc_seq" value="${readList.doc_seq}">
											</td>
											<td onclick="javascript:fn_read('${readList.doc_seq}')" ><c:out value="${readList.doc_name}" /></td>
											<td  >
		<a href="#" onclick="javascript:downloadFile('${readList.file_name}','${readList.file_org_name}','${readList.file_path}'); return false;"><c:out value="${readList.file_org_name}" /></a>
									
											</td>
											<td ><c:out value="${readList.doc_version}" /></td>
<%-- 											<td onclick="javascript:fn_read('${readList.doc_seq}')" ><c:out value="${readList.reg_user_id}" /></td> --%>
											<td  ><c:out value="${readList.reg_user_name}" /></td>
											<td  ><c:out value="${readList.reg_date}" /></td>
<!-- 											<td class="td_left"> -->
<%-- 												<a href="javascript:fn_read('view', '<c:out value="${readList.notice_seq}"/>')"> --%>
<%-- 													<c:out value="${fn:substring(noticeModelList.title, 0 , 55)}" /> --%>
<%-- 													<c:out value="${fn:length(noticeModelList.title) > 55 ? '...' : ''}" /> --%>
<!-- 												</a> -->
<!-- 											</td> -->
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr><td colspan="4">등록된 공지사항이 없습니다.</td></tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
<!-- 				</div> -->
				<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
			</div>
		</div>
	</form:form>
	
</body>
</html>