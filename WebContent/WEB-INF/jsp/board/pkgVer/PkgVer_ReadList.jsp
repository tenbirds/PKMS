<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script>  
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javaScript"  defer="defer">
$(document).ready(function($) {
	
	doCalendar("search_reg_date_start");
	doCalendar("search_reg_date_end");
	
	 init_system_popup_pkgVer();
	
	/* 테이블 스크롤 제어 */
	//doTable(tableId, className, fixHeaderRow, fixLeftCol, widthArr);
	doTable("scrollTable", "tbl_type", "1", "0", ["85", "85", "90", "200", "110", "100", "100", "110", "100", "120", "100"]);
});

/* 수정 / 신규 화면 */
function fn_read(id) {
	var form = document.getElementById('PkgVerModel');
	form.system_seq.value = id;
	
	form.action = "<c:url value='/board/pkgVer/PkgVer_Read.do'/>";
	form.submit();
}

/* 목록 페이지 이동 */
function fn_readList(pageIndex) {
	
	if(($("#search_reg_date_start").val().replace(/-/g,""))>$("#search_reg_date_end").val().replace(/-/g,"")){
		alert("시작일가 종료일자보다 나중입니다.");
		$("#search_reg_date_end").focus();
		return ;
	}  
	
	document.getElementById('pageIndex').value = pageIndex;
	
	var form = document.getElementById('PkgVerModel');
	form.action = "<c:url value='/board/pkgVer/PkgVer_ReadList.do'/>";
	form.submit();
}

//시스템 선택 결과 처리
function fn_system_popup_callback(system_key, system_name) {
	$("#search_system_seq").val(system_key);
	$("#search_system_name").val(system_name);
}


</script>
</head>

<body>
 <form:form commandName="PkgVerModel" method="post" onsubmit="return false;">
 	<form:hidden path="system_seq"/>
	<!-- 페이징 -->
 	<form:hidden path="pageIndex"/> 
	<!--조회 -->
		<div class="search">
			<table class="sear_table1" >
				<tr>
					<th>등록일자</th>
					<td>
						<form:input path="search_reg_date_start" readonly="true" size="10"/>
						<form:input path="search_reg_date_end" readonly="true" size="10"/>  
					</td>
					<th>시스템</th>
					<td>
						<form:hidden path="search_system_seq" />
						<form:input path="search_system_name" readonly="true" size="40"/>
						<span><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup" align="absmiddle" /></span>
					</td>
				</tr>
				
			</table>
			
			<!--조회버튼 -->
			<div class="btn_sear"><a href="javascript:fn_readList(1)"></a></div>
		</div>
		
		<div class="btn_location">
			 <sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR"> 
				<span><a href="javascript:fn_read('')"><img src="/images/btn_new.gif" alt="신규" /></a></span>
				
			</sec:authorize> 
		</div>
		
		<!--테이블, 페이지 DIV 시작 -->
		<div class="con_Div2">
			
			<!-- 스크롤 DIV -->
			<div class="fakeContainer" style="width:1200px;height:500px">
			<!--테이블 -->
			 <table id="scrollTable" class="tbl_type" border="1">
				<thead>
				<tr>
					<th scope="col">중분류</th>
					<th scope="col">시스템</th>
					<th scope="col">PKG Version</th>
					<th scope="col">PKG 주요내역</th>
					<th scope="col">SKT 담당자</th>
					<th scope="col">SKT 연락처</th>
					<th scope="col">BP사</th>
					<th scope="col">BP 담당자</th>
					<th scope="col">BP 연락처</th>
					<th scope="col">Last Update</th>
					<th scope="col">비고</th>
				</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty pkgVerList}">
							<c:forEach var="pkgVerList2" items="${pkgVerList}" varStatus="status">
								<tr>
									<td rowspan="${fn:length(pkgVerList2.value)}">
										<c:out value="${pkgVerList2.key}" />
									</td>
									
								<c:forEach var="value" items="${pkgVerList2.value}" varStatus="status">
									<td>
										<a href = "javascript:fn_read('<c:out value="${value.system_seq}" />')">
											<c:out value="${value.system_name}" />
										</a>
									</td>
									<td><c:out value="${value.pkg_ver}" /></td>
									<td><c:out value="${value.content}" /></td>
									<td>
										<c:forTokens items="${value.sys_skt_user}" delims="," var="sys_skt_user">
											${sys_skt_user}<br/>
										</c:forTokens>
									</td>
									<td>
										<c:forTokens items="${value.skt_phone}" delims="," var="skt_phone">
											${skt_phone}<br/>
										</c:forTokens>
									</td>
									<td>
										<c:forTokens items="${value.bp_name}" delims="," var="bp_name">
											${bp_name}<br/>
										</c:forTokens>
									</td>
									<td>
										<c:forTokens items="${value.sys_bp_user}" delims="," var="sys_bp_user">
											${sys_bp_user}<br/>
										</c:forTokens>
									</td>
									<td>
										<c:forTokens items="${value.bp_phone}" delims="," var="bp_phone">
											${bp_phone}<br/>
										</c:forTokens>
									</td>
									<c:choose>
										<c:when test="${empty value.update_date}">
											<td>미입력</td>
										</c:when>
										<c:otherwise>
											<td>
												<c:out value="${value.update_date}"/>,<br/>
												<c:out value="${value.update_name} 입력" />
											</td>
										</c:otherwise>
									</c:choose>
									<td><c:out value="${value.ver_note}" /></td>
								</tr>
								</c:forEach>
							</c:forEach>
						</c:when>
						<c:otherwise> 
							<tr>
								<td colspan="11">등록된 PKG 버전 목록이 없습니다.</td>
							</tr>
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