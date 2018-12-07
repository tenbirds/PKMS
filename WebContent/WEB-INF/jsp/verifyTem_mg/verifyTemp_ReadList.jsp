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
	
});

function fn_read(quest_seq, verify_type) {
	$("input[id=quest_seq]").val(quest_seq);
	$("input[id=verify_type]").val(verify_type);

	var form = document.getElementById("verifyTemModel");
	form.action = "<c:url value='/verify_tem_mg/verifyTemp_Read.do'/>";
	form.submit();
}

function fn_readList(pageIndex) {
	// 검색어없이 검색 시 전체 검색 
	document.getElementById("pageIndex").value = pageIndex;
	
	var form = document.getElementById("verifyTemModel");
	form.action = "<c:url value='/verify_tem_mg/verifyTemp_ReadList.do'/>";
	form.submit();
}

</script>
</head>
<body>
<form:form commandName="verifyTemModel" method="post" onsubmit="onSubmitReturnFalse();">
<form:hidden path="pageIndex"/><!-- 페이징 -->
<form:hidden path="quest_seq" />
<form:hidden path="verify_type" />

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
					<form:select path="searchCondition" >
						<form:option value="vol">용량 검증</form:option>
						<form:option value="sec">보안 검증</form:option>
						<form:option value="cha">과금 검증</form:option>
						<form:option value="non">비기능 검증</form:option>
					</form:select>
				</td>
			</tr>
		</table>
		<!--조회버튼 -->
		<div class="new_btn_sear03">
			<a href="javascript:fn_readList(1);">조회</a>
		</div>
	</div>
</div>

<!--테이블, 페이지 DIV 시작 -->
<div class="con_Div2 con_area mt20">
	<div class="con_detail_long">
		<!--버튼위치 -->
		<div class="con_top_btn">
			<span><a href="javascript:fn_read('');">신규</a></span>
		</div>
		
		<table id="scrollTable" class="tbl_type12">
			<thead>
				<tr>
					<th scope="col">검증 타입</th>
					<th scope="col">검증 항목 주제</th>
					<th scope="col">질문 타입</th>
					<th scope="col">등록자</th>
					<th scope="col">등록일</th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${not empty readList_quest}">
					<c:forEach var="result" items="${readList_quest}" varStatus="status">
						<tr>
							<td><c:out value="${result.verify_type}" /></td>
							<td style="text-align: left;"><a href="javascript:fn_read('<c:out value="${result.quest_seq}"/>','${result.verify_type}')"><c:out value="${result.quest_title}" /></a></td>
							<td><c:out value="${result.quest_type}" /></td>
							<td><c:out value="${result.reg_name}" /></td>
							<td><c:out value="${result.reg_date}" /></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
					<td colspan="5">검색 결과가 없습니다.</td>
				</tr>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
		
	</div>

	
</div>

</form:form>

</body>
</html>