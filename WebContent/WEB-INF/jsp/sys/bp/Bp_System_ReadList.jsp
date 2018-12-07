<%--
/**
 * 담당시스템 조회 페이지
 * 
 * @author : junhee
 * @Date : 2012. 11. 27.
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
// 				doTable("scrollTable", "tbl_type", "1", "0", [ "120","120", "160", "400", "400"]);
		});
		
	/* 목록 페이지 이동 */
	function fn_readList(pageIndex) {
		// 검색어없이 검색 시 전체 검색 
// 		document.getElementById("pageIndex").value = pageIndex;
		$("#pageIndex").val(pageIndex);
		
		var form = document.getElementById("BpModel");
		form.action = "<c:url value='/usermg/bp/Bp_System_ReadList.do'/>";
		form.submit();
	}
	
	function system_m(group1_seq, group2_seq, group3_seq, system_seq) {
		$("#group1_seq").val(group1_seq);
		$("#group2_seq").val(group2_seq);
		$("#group3_seq").val(group3_seq);
		$("#system_seq").val(system_seq);
		
		var form = document.getElementById("BpModel");
		form.action = "<c:url value='/sys/group1/Group1_ReadList.do'/>";
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
		<form:hidden path="group1_seq" />
		<form:hidden path="group2_seq" />
		<form:hidden path="group3_seq" />
		<form:hidden path="system_seq" />
		
		<!--조회 -->
		<div class="new_con_Div32 new_pkms_sysSearchForm">
			<div class="new_search fl_wrap">
				<table class="new_sear_table2">
					<colgroup>
					<col width="10%">
					<col width="40%">
					<col width="10%">
					<col width="40%">
					</colgroup>
					<tbody>
					<tr>
						<th>분류</th>
						<td>
							<form:radiobuttons path="companyCondition" onclick="javascript:change();" items="${BpModel.companyConditionsList}" itemLabel="codeName" itemValue="code" />
						</td>
						<th>검색</th>
						<td><form:select path="searchCondition" items="${BpModel.searchConditionsList}" itemLabel="codeName" itemValue="code" /> <form:input path="searchKeyword" cssClass="txt"
								onkeypress="if(event.keyCode == 13) { fn_readList(1);}" /></td>
					</tr>
					</tbody>
				</table>
	
				<!--조회버튼 -->
				<div class="new_btn_sear03">
					<a href="javascript:fn_readList(1);">조회</a>
				</div>
			</div>
		</div>
		<!--버튼위치 -->
		<div class="btn_location" style="height: 0px; margin-top: 0px; padding-top: 10px;">

		</div>
		<!--테이블, 페이지 DIV 시작 -->
		<div class="con_Div2 con_area mt30 mb40">
			
			<div class="con_detail">
				<table id="scrollTable" class="tbl_type12">
					<colgroup width="10%">
					<colgroup width="15%">
					<colgroup width="10%">
					<colgroup width="30%">
					<colgroup width="35%">
<%-- 					<colgroup width="440"> --%>
					<thead>
						<tr>
							<c:if test= "${BpModel.companyCondition == 'bp'}">
								<th scope="col">담당자명</th>
								<th scope="col">소속명</th>
								<th scope="col">담당자아이디</th>
							</c:if>
							<c:if test= "${BpModel.companyCondition == 'skt'}">
								<c:if test= "${BpModel.searchCondition == '0'}">
									<th scope="col" colspan="3">소속명</th>
								</c:if>
								<c:if test= "${BpModel.searchCondition == '1'}">
									<th scope="col">담당자명</th>
									<th scope="col">소속명</th>
									<th scope="col">사번</th>
								</c:if>
							</c:if>
							<c:if test= "${BpModel.companyCondition == 'operator'}">
								<c:if test= "${BpModel.searchCondition == '0'}">
									<th scope="col" colspan="3">소속명</th>
								</c:if>
								<c:if test= "${BpModel.searchCondition == '1'}">
									<th scope="col">담당자명</th>
									<th scope="col">소속명</th>
									<th scope="col">사번</th>
								</c:if>
							</c:if>
							<c:choose>
								<c:when test="${BpModel.companyCondition == 'operator'}">
									<th scope="col">담당 시스템</th>
									<th scope="col">담당 장비</th>
								</c:when>
								<c:otherwise>
									<th scope="col" colspan="2">담당 시스템</th>
								</c:otherwise>
							</c:choose>
						</tr>
					</thead>
					<tbody>
						<c:if test="${BpModel.totalCount == 0}">
							<tr>
								<td colspan="5" class="td_center">검색 결과가 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach var="result" items="${BpModelList}" varStatus="status">
							<tr>
								<c:if test= "${BpModel.companyCondition == 'bp'}">
									<td class="td_center"><c:out value ="${result.bp_user_name }"/></td>
									<td class="td_center"><c:out value ="${result.bp_name }"/></td>
									<td class="td_center"><c:out value ="${result.bp_user_id }"/></td>
								</c:if>
								<c:if test= "${BpModel.companyCondition == 'skt'}">
									<c:if test= "${BpModel.searchCondition == '0'}">
										<td colspan="3" class="td_center"><c:out value ="${result.sosok }"/></td>
									</c:if>
									<c:if test= "${BpModel.searchCondition == '1'}">
										<td class="td_center"><c:out value ="${result.hname }"/></td>
										<td class="td_center"><c:out value ="${result.sosok }"/></td>
										<td class="td_center"><c:out value ="${result.empno }"/></td>
									</c:if>
								</c:if>
								<c:if test= "${BpModel.companyCondition == 'operator'}">
									<c:if test= "${BpModel.searchCondition == '0'}">
										<td colspan="3" class="td_center"><c:out value ="${result.sosok }"/></td>
									</c:if>
									<c:if test= "${BpModel.searchCondition == '1'}">
										<td class="td_center"><c:out value ="${result.hname }"/></td>
										<td class="td_center"><c:out value ="${result.sosok }"/></td>
										<td class="td_center"><c:out value ="${result.empno }"/></td>
									</c:if>
								</c:if>
								<c:choose>
									<c:when test="${BpModel.companyCondition == 'operator'}">
										<td>
											<a href="javascript:system_m(${result.group1_seq }, ${result.group2_seq }, ${result.group3_seq }, ${result.system_seq } );">
												<c:out value ="${result.group_depth }"/>
											</a>
										</td>
										<td>
											<c:out value ="${result.equipment_name }"/>
										</td>
									</c:when>
									<c:otherwise>
										<td colspan="2">
											<a href="javascript:system_m(${result.group1_seq }, ${result.group2_seq }, ${result.group3_seq }, ${result.system_seq } );">
												<c:out value ="${result.group_depth }"/>
											</a>
										</td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
			
		</div>
	</form:form>
</body>
</html>
