<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="any" uri="http://www.anychart.com" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>PKMS (패키지 관리 시스템)</title>
<link type="text/css" rel="stylesheet" href="/css/base.css" />
<link rel="stylesheet" type="text/css" href="/css/base1.css" />
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />

<script type="text/javascript" src="/js/jquery/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/js/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/file.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script>
<script type='text/javascript' src='/js/jquery/fullcalendar/fullcalendar.min.js'></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type='text/javascript' src='/js/pkgmg/pkgmg.common.js'></script>
 
<script type="text/javascript">
	$(document).ready(function($) {
		doLoadingInitStart();
		
		doLoadingInitEnd();
	});
	function fn_readList(){
		var cc = $("input[name=chartCondition]:checked").val();
		
		var form = document.getElementById("StatsModel");
		form.action = "/Chart_Popup_Read.do";
		form.submit();
	}
</script>
</head>
<body>
<form:form commandName="StatsModel" method="post" onsubmit="return false;">

	<form:hidden path="start_date"/>
	<form:hidden path="end_date"/>
	<form:hidden path="userCondition"/>
	<form:hidden path="search_system_seq"/>
	<form:hidden path="dateCondition"/>
	<form:hidden path="kind_group1"/>
	<form:hidden path="kind_group2"/>
	<form:hidden path="kind_group3"/>
	<form:hidden path="kind_system"/>
	<form:hidden path="kind_user"/>
	<form:hidden path="kind_idc"/>
	<form:hidden path="kind_equipment"/>

	<div class="search">
		<table class="sear_table1">
			<tr>
				<th>구분</th>
				<td>
					<form:radiobuttons path="chartCondition" items="${StatsModel.chartConditionsList}" itemLabel="codeName" itemValue="code" onclick="javascript:fn_readList()" cssStyle="vertical-align: middle;"/>
				</td>
			</tr>
		</table>
	</div>
	<%-- ${StatsModel.chartGunun4X }
	${StatsModel.chartGunun4Y } --%>
	
	<div>
		<any:chart height="500" width="1000">
		<anychart>
		<charts>
			<chart>
				<chart_settings>
					<title>
						<text><![CDATA[ PKG 통계  ]]></text>
					</title>
					<axes>
						<y_axis>
							<title>
								<text><![CDATA[ ${StatsModel.chartGunun4Y} ]]></text>
							</title>
						</y_axis>
						<x_axis>
							<title>
								<text><![CDATA[ ${StatsModel.chartGunun4X}  ]]></text>
							</title>
						</x_axis>
					</axes>
					<legend enabled="true">
						<title>
							<text><![CDATA[ 항목  ]]></text>
						</title>
					</legend>
				</chart_settings>
				<data>
					<c:set value="${StatsModel.chartCondition}" var="ChartCond"/>
					<c:forEach var="list" items="${StatsModelList}">
						<c:forEach var="map" items="${list.kindStatsSet}">
							<series name="${map.group_name}">
							<c:choose>
								<c:when test="${ChartCond == '0'}">
									<point name="${list.date_unit}" y="${map.pkg_count}" />
								</c:when>
								<c:when test="${ChartCond == '1'}">
									<point name="${list.date_unit}" y="${map.pkg_verify_count}" />
								</c:when>
								<c:when test="${ChartCond == '2'}">
									<point name="${list.date_unit}" y="${map.equipmentPkgStartCount}" />
								</c:when>
								<c:when test="${ChartCond == '3'}">
									<point name="${list.date_unit}" y="${map.equipmentPkgEndCount}" />
								</c:when>
								<c:when test="${ChartCond == '4'}">
									<point name="${list.date_unit}" y="${map.equipmentPkgRevertCount}" />
								</c:when>
								<c:when test="${ChartCond == '5'}">
									<point name="${list.date_unit}" y="${map.newCount}" />
								</c:when>
								<c:when test="${ChartCond == '6'}">
									<point name="${list.date_unit}" y="${map.pnCount}" />
								</c:when>
								<c:when test="${ChartCond == '7'}">
									<point name="${list.date_unit}" y="${map.crCount}" />
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							</series>
						</c:forEach>
			        </c:forEach>
				</data>
			</chart>
		</charts>
		</anychart>		
		</any:chart>
	</div>
</form:form>

</body>
</html>