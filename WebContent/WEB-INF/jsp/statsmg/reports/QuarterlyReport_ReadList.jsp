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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<link type="text/css" rel="stylesheet" href="/css/fullcalendar/fullcalendar.css" />

<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script>
<script type='text/javascript' src='/js/jquery/fullcalendar/fullcalendar.min.js'></script>


<script type="text/javaScript" defer="defer">
	$(document).ready(function($) {
		// Calendar Init
		doCalendar("start_date");
		doCalendar("end_date");
	});

	/* 조회 */
	function fn_readList() {
		var form = document.getElementById("QuarterlyReportModel");
		form.action = "/statsmg/reports/QuarterlyReport_ReadList.do";
		form.submit();
	}
	/*Excel down*/
	function fn_list_excel_download() {
		 
		doSubmit("QuarterlyReportModel", "/statsmg/reports/QuarterlyReport_excelDown.do", "fn_callback_file_download");
		 
	}
</script>
</head>

<body>
	<form:form commandName="QuarterlyReportModel" method="post" onsubmit="return false;">

<!--조회 -->
<div class="new_con_Div32">
	<div class="new_search fl_wrap">
		<table class="new_sear_table2" style="width:90%;">
			<colgroup>
				<col width="10%" />
				<col width="30%" />
				<col width="10%" />
				<col width="20%" />
				<col width="10%" />
				<col width="20%" />
			</colgroup>
			<tr>
				<th>기간</th>
				<td>
					<form:input path="start_date" class="fl_left" style="width:70px" readOnly="true" /> <span class="mg05 mt10"> ~ </span> <form:input path="end_date" class="fl_left" style="width:70px" readOnly="true" />
				</td>
				<th>분류</th>
				<td><form:radiobuttons path="kindCondition" items="${QuarterlyReportModel.kindConditionsList}" itemLabel="codeName" itemValue="code" /></td>
				<th>담당</th>
				<td><form:radiobuttons path="userCondition" items="${QuarterlyReportModel.userConditionsList}" itemLabel="codeName" itemValue="code" /></td>
			</tr>
		</table>

		<!--조회버튼 -->
		<div class="new_btn_sear03"><a href="javascript:fn_readList();">조회</a></div>
	</div>
</div>

<!--테이블, 페이지 DIV 시작 -->
<div class="con_Div2 con_area mt20">
	<div class="con_detail">
		<!--버튼위치 -->
		<div class="con_top_btn">
			<span><a href="javascript:fn_list_excel_download();">저장</a></span>
		</div>
		<table id="scrollTable" class="tbl_type12">
			<colgroup>
				<col width="25%" />
				<col width="20%" />
				<col width="15%" />
				<col width="20%" />
				<col width="20%" />
			</colgroup>
			<tr>
				<th>분류</th>
				<th>PKG 시스템</th>
				<th>PKG 장비</th>
				<th>PKG 적용 건수</th>
				<th>신규/보안/개선 건수</th>
			</tr>
			<c:if test="${fn:length(QuarterlyReportModelList) == 0}">
				<tr>
					<td colspan="6" height="30">검색 결과가 없습니다.</td>
				</tr>
			</c:if>
			<c:forEach var="stats" items="${QuarterlyReportModelList}" varStatus="status">
				<tr>
					<td><c:out value="${stats.group_name}" /></td>
					<c:choose>
						<c:when test="${stats.equipmentPkgStartCount + stats.equipmentPkgEndCount + stats.equipmentPkgRevertCount > 0 }">
							<td>
							<c:out value="${stats.pkgSystemName}" />
							<c:if test="${stats.pkgSystemCount > 1}">
								&nbsp;외 <c:out value="${stats.pkgSystemCount - 1}" />종
							</c:if>
							 </td>
							<td><c:out value="${stats.pkgEquipmentCount}" />식</td>
							<td>초도(<c:out value="${stats.equipmentPkgStartCount}" />) / 확대(<c:out value="${stats.equipmentPkgEndCount}" />) / 원복(<c:out value="${stats.equipmentPkgRevertCount}" />)
							</td>
						</c:when>
						<c:otherwise>
							<td>-</td>
							<td>-</td>
							<td>-</td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${stats.newCount + stats.pnCount + stats.crCount > 0 }">
							<td>NEW(<c:out value="${stats.newCount}" />) / PN(<c:out value="${stats.pnCount}" />) / CR(<c:out value="${stats.crCount}" />)</td>
						</c:when>
						<c:otherwise>
							<td>-</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
	</form:form>
</body>
</html>
