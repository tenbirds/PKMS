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

function fn_read(seq, ver, type, sysSeq) {
	$("#verify_seq").val(seq);
	$("#verify_ver").val(ver);
	$("#verify_type").val(type);
	$("#system_seq").val(sysSeq);
	
	var form = document.getElementById("verifyTemModel");
	form.action = "<c:url value='/verify_tem_mg/verifyTempMg_Read.do'/>";
	form.submit();
}

function fn_readList(pageIndex) {
	// 검색어없이 검색 시 전체 검색 
	document.getElementById("pageIndex").value = pageIndex;
	
	var form = document.getElementById("verifyTemModel");
	var url = "";
	if($("#verify_type").val() == "vol"){
		url = "<c:url value='/verify_tem_mg/verifyTempVol_ReadList.do'/>";
	}else if($("#verify_type").val() == "sec"){
		url = "<c:url value='/verify_tem_mg/verifyTempSec_ReadList.do'/>";
	}else if($("#verify_type").val() == "cha"){
		url = "<c:url value='/verify_tem_mg/verifyTempCha_ReadList.do'/>";
	}else if($("#verify_type").val() == "non"){
		url = "<c:url value='/verify_tem_mg/verifyTempNon_ReadList.do'/>";
	}
	form.action = url;
	form.submit();
}

</script>
</head>
<body>
<form:form commandName="verifyTemModel" method="post" onsubmit="onSubmitReturnFalse();">
<form:hidden path="pageIndex"/><!-- 페이징 -->
<form:hidden path="verify_seq" />
<form:hidden path="verify_ver" />
<form:hidden path="verify_type" />
<form:hidden path="system_seq" />
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
					템플릿 명  <form:input path="searchKeyword" cssClass="txt" onkeypress="if(event.keyCode == 13) { fn_readList(1); }"/>
				</td>
			</tr>
		</table>
	
		<!--조회버튼 -->
		<div class="new_btn_sear03"><a href="javascript:fn_readList(1);">조회</a></div>
	</div>
</div>

<!--테이블, 페이지 DIV 시작 -->
<div class="con_Div2 con_area mt20">
	<div class="con_detail_long">
		<!--버튼위치 -->
		<div class="con_top_btn">
			<span><a href="javascript:fn_read('','0','${verifyTemModel.verify_type}');">신규</a></span>
		</div>
		
		<table id="scrollTable" class="tbl_type12">
			<thead>
				<tr>
					<th scope="col">템플릿 명</th>
					<th scope="col">템플릿 버전</th>
					<th scope="col">등록된 시스템</th>
					<th scope="col">등록자</th>
					<th scope="col">등록일</th>
				</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${not empty readList_verifyTem}">
					<c:forEach var="result" items="${readList_verifyTem}" varStatus="status">
						<tr>
							<td class="td_left"><a href="javascript:fn_read('<c:out value="${result.verify_seq}"/>','<c:out value="${result.verify_ver}"/>','${verifyTemModel.verify_type}','<c:out value="${result.system_seq}"/>')"><c:out value="${result.verify_title}" /></a></td>
							<td>버전 <c:out value="${result.verify_ver}" /></td>
							<td class="td_left"><c:out value="${result.system_name}" /></td>
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