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
<script type='text/javascript' src='/js/pkgmg/pkgmg.common.js'></script>

<script type="text/javaScript" defer="defer">
	$(document).ready(
			function($) {
				// Calendar Init
				doCalendar("date_start");
				doCalendar("date_end");

				// 테이블 스크롤 Init
// 				doTable("m_tab2_table", "tbl_type11", "1", "0", [ "90", "180", "100", "280", "60", "100", "150", "110" ]);
				 
			});

	/* 목록 페이지 이동 */
	function fn_readList(pageIndex) {
		// 검색어없이 검색 시 전체 검색 
		$("#pageIndex").val(pageIndex);
		$("#sessionCondition").val(false);

		var form = document.getElementById("ScheduleModel");
		form.target = "_self";
		form.action = "<c:url value='/pkgmg/schedule/Schedule_ReadList.do'/>";
		form.submit();
	}

	function fn_group2_readList() {
		doSubmit("ScheduleModel", "/pkgmg/common/Group2_Select_Ajax_Read.do", "fn_callback_group2_readList");
	}

	function fn_callback_group2_readList(data) {
		$("select[id=group2Condition]").html(data);
	}

	// 화면 reload, 팝업창에서 부모창 reload 제어
	function fn_reload() {
		fn_readList(document.getElementById("pageIndex").value);
	}
	
 
</script>

</head>
<body>
	<form:form commandName="ScheduleModel" method="post" onsubmit="return false;">
		<form:hidden path="pageIndex" />
		<form:hidden path="retUrl" />
		<form:hidden path="pkg_seq" />
		<form:hidden path="sessionCondition" />

		<!--탭, 테이블, 페이지 DIV 시작 -->
		<div class="con_Div2">
			<!--탭 div 시작 -->
			<div class="con_Div6">
				<ul class="m_tab">
					<li class="m_tab1"><a href="/pkgmg/pkg/Pkg_ReadList.do"></a></li>
					<li class="m_tab2_on"></li>
					<li class="m_tab3"><a href="/pkgmg/diary/Diary_ReadList.do"></a></li>
				</ul>
			</div>
			<!--조회, 테이블, 페이지수 div 시작 -->
			<div class="new_con_Div71">

				<!-- m_tab_search PKG현황, 일정목록 검색 -->
				<div id="m_tab_search" class="new_con_Div32 new_pkms_sysSearchForm">
					<!--조회영역 -->
					<div class="new_search fl_wrap">
						<table class="new_sear_table1 fl_wrap">
							<colgroup>
								<col width="*">
								<col width="45%">
								<col width="*">
								<col width="*">
								<col width="*">
								<col width="*">
							</colgroup>
							<tr>
								<th class="w1">기간</th>
								<td>
									<form:select path="dateCondition" items="${ScheduleModel.workDateConditionsList}" itemLabel="codeName" itemValue="code" class="inp_w20 mr03 fl_left"/> 
									<form:input path="date_start" class="inp_w20 fl_left" readOnly="true" />
									<span class="fl_left mg05"> ~ </span> 
									<form:input path="date_end" class="inp_w20 fl_left" readOnly="true" />
								</td>
								<th>대분류</th>
								<td><form:select path="group1Condition" style="width:80%" onchange="fn_group2_readList();">
										<form:option value="" label="전 체" />
										<form:options items="${Group1List}" itemLabel="codeName" itemValue="code" />
									</form:select>
								</td>
								<th class="w1">상태</th>
								<td><form:select path="statusCondition" items="${ScheduleModel.statusConditionsList}" itemLabel="codeName" itemValue="code" /></td>
							</tr>
							<tr>
								<th class="w1">검색</th>
								<td>
									<form:select path="searchCondition" items="${ScheduleModel.searchConditionsList}" itemLabel="codeName" itemValue="code" /> 
									<form:input path="searchKeyword" onkeypress="if(event.keyCode == 13) { fn_readList(1); }" style="width:150px;" />
								</td>
								<th>중분류</th>
								<td>
									<form:select path="group2Condition" style="width:80%">
										<form:option value="" label="전 체" />
										<form:options items="${Group2List}" itemLabel="codeName" itemValue="code" />
									</form:select>
								</td>
								<th class="w1">담당</th>
								<td><form:radiobuttons path="userCondition" items="${ScheduleModel.userConditionsList}" itemLabel="codeName" itemValue="code" /></td>
							</tr>
						</table>
					
						<!--조회버튼 -->
						<div class="new_btn_sear"><a href="javascript:fn_readList('1');">조 회</a></div>
					</div>
				</div>

				<!-- m_tab2 일정목록 -->
				<div id="m_tab2">
					<!--버튼위치 -->
					<div class="btn_location">
						<p class="mt20 ml10 fl_left">
						!!진행 상태가 <span class="txt_red">붉은색</span> - <b>진행중</b>, &nbsp;<span class="txt_blue">파란색</span> - <b>완료</b> 입니다.
						</p>
						<span class="con_top_btn"><a href="javascript:fn_schedule_list_excel_download();">엑셀 다운로드</a></span>
					</div>

					<div class="fakeContainer" style="width: 98%;height: 430px; overflow:auto; overflow-x:hidden;">
						<table id="m_tab2_table" class="tbl_type12" style="width: 100%;">
							<colgroup>
								<col width="5%">
								<col width="20%">
								<col width="10%">
								<col width="30%">
								<col width="5%">
								<col width="10%">
								<col width="10%">
								<col width="10%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col" class="th_height">상태</th>
									<th scope="col">시스템</th>
									<th scope="col">버전</th>
									<th scope="col">제목</th>
									<!-- <th scope="col">검증일자</th> -->
									<th scope="col" class="th_height">구분</th>
									<th scope="col">장비</th>
									<th scope="col" class="th_height">장비별<br>적용일자</th>
									<th scope="col" class="th_height">장비별<br>적용결과</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${paginationInfo.totalRecordCount == 0}">
									<tr>
										<td class="td_center" colspan="8" height="30">검색 결과가 없습니다.</td>
									</tr>
								</c:if>
								<c:forEach var="pkg" items="${ScheduleModelList}" varStatus="status">
									<tr class="pop_tbl_type3_1" >
										<td rowspan="${fn:length(pkg.pkgEquipmentModelList)}" style="text-align:center;">
											<c:choose>
												 <c:when test="${pkg.status ==9 or pkg.status==99 or pkg.pe_status==9 }"> 
													<font color='blue'><b>${pkg.status_name}</b></font>
												 </c:when>
												 <c:otherwise>
													<font color='red'><b>${pkg.status_name}</b></font>
												 </c:otherwise>
											</c:choose>
											 
										</td>
										<td rowspan="${fn:length(pkg.pkgEquipmentModelList)}" style="text-align:left; padding-left: 5px;">
											<c:out value="${pkg.group_depth}" />
										</td>
										<td rowspan="${fn:length(pkg.pkgEquipmentModelList)}" style="text-align:center;">
											<c:out value="${pkg.ver}" />
										</td>
										<td rowspan="${fn:length(pkg.pkgEquipmentModelList)}" style="text-align:left; padding-left: 5px;">
											<a href="javascript:fn_pkg_read('read', '${pkg.pkg_seq}', 'ScheduleModel')">
											<c:out value="${fn:substring(pkg.title,0,20)}" />
											<c:out value="${fn:length(pkg.title) > 20 ? '...' : ''}" />
											&nbsp;
											</a>
										</td>
										<%-- <td rowspan="${fn:length(pkg.pkgEquipmentModelList)}" style="text-align:center;"><c:out value="${pkg.verify_date_start}"></c:out> ~ <c:out value="${pkg.verify_date_end}"></c:out></td> --%>

									<c:if test="${fn:length(pkg.pkgEquipmentModelList) == 0}">
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
									</c:if>								


									<c:forEach var="pkgEquip" items="${pkg.pkgEquipmentModelList}" varStatus="status">
										<c:if test="${!status.first}">
									<tr class="pop_tbl_type3_1">
										</c:if>
										<td style="background:${pkgEquip.work_gubun == 'S' ? '#fafad2' : '#fff0f5'}; text-align:center;"><c:out value="${pkgEquip.work_gubun == 'S' ? '초도' : '확대'}" />&nbsp;</td>
										<td style="background:${pkgEquip.work_gubun == 'S' ? '#fafad2' : '#fff0f5'}; padding-left: 5px;">
											<c:out value="${fn:substring(pkgEquip.equipment_name,0,12)}" />
											<c:out value="${fn:length(pkgEquip.equipment_name) > 12 ? '...' : ''}" />
											&nbsp;
										</td>
										<td style="background:${pkgEquip.work_gubun == 'S' ? '#fafad2' : '#fff0f5'}; text-align:center;">
											<c:out value="${pkgEquip.work_date}" />
											<c:out value="${pkgEquip.start_time1}" />:<c:out value="${pkgEquip.start_time2}" /> ~ 
											<c:out value="${pkgEquip.end_time1}" />:<c:out value="${pkgEquip.end_time2}" />
											
										</td>
										<td style="background:${pkgEquip.work_gubun == 'S' ? '#fafad2' : '#fff0f5'}; text-align:center;">
											<c:choose>
												 <c:when test="${pkg.status == 7 or pkg.status== 9 }"> 
													양호
												 </c:when>
												<c:when test="${pkg.status == 99 }">
													불량 
												</c:when>
												 <c:otherwise>
													-
												 </c:otherwise>
											</c:choose>
										</td>
									</tr>
									</c:forEach>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div id="m_tab_pagination">
					<!--페이지수 -->
					<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
				</div>

			</div>
			<!--조회, 테이블, 페이지수 div 끝 -->
		</div>
		<!--탭, 테이블, 페이지 DIV 시작 -->
	</form:form>
</body>
</html>
