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
<%@ page import="com.wings.util.DateUtil"%>
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
		doCalendar("base_date");
	});

	/* 조회 */
	function fn_readList() {
		var form = document.getElementById("WeeklyReportModel");
		form.action = "/statsmg/reports/WeeklyReport_ReadList.do";
		form.submit();
	}
	/*Excel down*/
	function fn_list_excel_download() {
		 
		doSubmit("WeeklyReportModel", "/statsmg/reports/WeeklyReport_excelDown.do", "fn_callback_file_download");
		 
	}
</script>
</head>

<body>
	<form:form commandName="WeeklyReportModel" method="post" onsubmit="return false;">


<!--조회 -->
<div class="new_con_Div32">
	<div class="new_search fl_wrap">
		<table class="new_sear_table2" style="width:90%;">
			<colgroup>
				<col width="10%" />
				<col width="*" />
				<col width="10%" />
				<col width="*" />
			</colgroup>
			<tr>
				<th>기준일</th>
				<td>
					<form:input path="base_date" class="fl_left inp_w100px" readOnly="true" />
				</td>
				<th>담당</th>
				<td><form:radiobuttons path="userCondition" items="${WeeklyReportModel.userConditionsList}" itemLabel="codeName" itemValue="code" /></td>
			</tr>
		</table>
		<!--조회버튼 -->
		<div class="new_btn_sear03">
			<a href="javascript:fn_readList();">조회</a>
		</div>
	</div>
</div>

	<!--테이블, 페이지 DIV 시작 -->
	<div class="con_Div2 con_area mt20">
		<div class="con_detail">
			<!--버튼위치 -->
			<div class="con_top_btn">
				<span><a href="javascript:fn_list_excel_download();">엑셀 다운로드</a></span>
			</div>
			<table id="scrollTable" class="tbl_type12">
				<colgroup>
					<col width="7%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="10%" />
					<col width="26%" />
					<col width="7%" />
					<col width="8%" />
					<col width="12%" />
				</colgroup>
				<tr>
					<th>대표담당자</th>
					<th>대분류</th>
					<th>중분류</th>
					<th>소분류</th>
					<th>시스템</th>
					<th>일정 및 국사별 PKG 적용건수</th>
					<th>과금관련</th>
					<th>서비스 중단</th>
					<th>신규/보안/개선 건수</th>
				</tr>
				<tr>
					<th colspan="9" style="text-align: left; padding-left: 5px;" >실적 (<c:out value="${WeeklyReportModel.start_date_before}" /> ~ <c:out value="${WeeklyReportModel.end_date_before}" />)</th>
				</tr>
				<c:if test="${fn:length(WeeklyReportModelListBefore) == 0}">
					<td colspan="9" >검색 결과가 없습니다.</td>
				</c:if>
				<c:forEach var="report" items="${WeeklyReportModelListBefore}" varStatus="status">
				<tr>
					<td><c:out value="${report.system_user_name}" /></td>
					<td><c:out value="${report.group1_name}" /></td>
					<td><c:out value="${report.group2_name}" /></td>
					<td><c:out value="${report.group3_name}" /></td>
					<td>
						<c:out value="${report.system_name}" /><br />
						(PKG 건수: <c:out value="${report.pkg_count}" />)<br />
						(검증 건수:<c:out value="${report.pkg_verify_count}" />)
					</td>
					<td style="text-align: left;">
						<c:forEach var="subReport" items="${report.subReportMap}" varStatus="status">
							[<c:out value="${subReport.value.work_date}" />]&nbsp;<c:out value="${subReport.value.idc_name}" />&nbsp;
							<c:if test="${subReport.value.equipmentPkgStartCount > 0}">
								(초도: <c:out value="${subReport.value.equipmentPkgStartCount}" />)&nbsp;
							</c:if>								
							<c:if test="${subReport.value.equipmentPkgEndCount > 0}">
								(확대: <c:out value="${subReport.value.equipmentPkgEndCount}" />)&nbsp;
							</c:if>								
							<c:if test="${subReport.value.equipmentPkgRevertCount > 0}">
								(원복: <c:out value="${subReport.value.equipmentPkgRevertCount}" />)&nbsp;
							</c:if>								
							<br />
						</c:forEach>
					</td>
					<td>*</td>
					<td><c:out value="${report.downtime}" /></td>
					<td>NEW(<c:out value="${report.newCount}" />) / PN(<c:out value="${report.pnCount}" />) / CR(<c:out value="${report.crCount}" />)</td>
				</tr>
				</c:forEach>					
				<tr>
					<th colspan="9" style="text-align: left; padding-left: 5px;">계획 (<c:out value="${WeeklyReportModel.start_date_after}" /> ~ <c:out value="${WeeklyReportModel.end_date_after}" />)</th>
				</tr>
				<c:if test="${fn:length(WeeklyReportModelListAfter) == 0}">
					<td colspan="9" >검색 결과가 없습니다.</td>
				</c:if>
				<c:forEach var="report" items="${WeeklyReportModelListAfter}" varStatus="status">
				<tr>
					<td><c:out value="${report.system_user_name}" /></td>
					<td><c:out value="${report.group1_name}" /></td>
					<td><c:out value="${report.group2_name}" /></td>
					<td><c:out value="${report.group3_name}" /></td>
					<td>
						<c:out value="${report.system_name}" /><br />
						(PKG 건수: <c:out value="${report.pkg_count}" />)<br />
						(검증 건수:<c:out value="${report.pkg_verify_count}" />)
					</td>
					<td style="text-align: left; padding-left: 5px;">
						<c:forEach var="subReport" items="${report.subReportMap}" varStatus="status">
							[<c:out value="${subReport.value.work_date}" />]&nbsp;<c:out value="${subReport.value.idc_name}" />&nbsp;
							<c:if test="${subReport.value.equipmentPkgStartCount > 0}">
								(초도: <c:out value="${subReport.value.equipmentPkgStartCount}" />)&nbsp;
							</c:if>								
							<c:if test="${subReport.value.equipmentPkgEndCount > 0}">
								(확대: <c:out value="${subReport.value.equipmentPkgEndCount}" />)&nbsp;
							</c:if>								
							<c:if test="${subReport.value.equipmentPkgRevertCount > 0}">
								(원복: <c:out value="${subReport.value.equipmentPkgRevertCount}" />)&nbsp;
							</c:if>								
							<br />
						</c:forEach>
					</td>
					<td>*</td>
					<td><c:out value="${report.downtime}" /></td>
					<td>NEW(<c:out value="${report.newCount}" />) / PN(<c:out value="${report.pnCount}" />) / CR(<c:out value="${report.crCount}" />)</td>
				</tr>
				</c:forEach>					
			</table>
		</div>
	</div>

	</form:form>
</body>
</html>
