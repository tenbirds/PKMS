<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

			});

	/* 목록 페이지 이동 */
	function fn_readList(pageIndex) {
		
		if(($("#date_start").val().replace(/-/g,""))>$("#date_end").val().replace(/-/g,"")){
			alert("시작일가 종료일자보다 나중입니다.");
			$("#date_end").focus();
			return;
		}
		
		// 검색어없이 검색 시 전체 검색 
		document.getElementById("pageIndex").value = pageIndex;
		$("#sessionCondition").val(false);

		var form = document.getElementById("PkgModel");
		form.target = "_self";
		form.action = "<c:url value='/pkgmg/pkg/Pkg_ReadList.do'/>";
		form.submit();
	}

	function fn_group2_readList() {
		doSubmit("PkgModel", "/pkgmg/common/Group2_Select_Ajax_Read.do", "fn_callback_group2_readList");
	}

	function fn_callback_group2_readList(data) {
		$("select[id=group2Condition]").html(data);
	}
	
	// 화면 reload, 팝업창에서 부모창 reload 제어
	function fn_reload() {
		fn_readList(document.getElementById("pageIndex").value);
	}
	
	function system_m(group1_seq, group2_seq, group3_seq, system_seq) {
		$("#group1_seq").val(group1_seq);
		$("#group2_seq").val(group2_seq);
		$("#group3_seq").val(group3_seq);
		$("#system_seq").val(system_seq);
		
		var form = document.getElementById("PkgModel");
		form.target = "_self";
		form.action = "<c:url value='/sys/group1/Group1_ReadList.do'/>";
		form.submit();
	}

</script>

</head>
<body>
	<form:form commandName="PkgModel" method="post" onsubmit="return false;">
		<form:hidden path="pageIndex" />
		<form:hidden path="retUrl" />
		<form:hidden path="pkg_seq" />
		<form:hidden path="sessionCondition" />
		<form:hidden path="group1_seq" />
		<form:hidden path="group2_seq" />
		<form:hidden path="group3_seq" />
		<form:hidden path="system_seq" />

		<!--탭, 테이블, 페이지 DIV 시작 -->
		<div class="con_Div2">
			<!--탭 div 시작 -->
			<div class="con_Div6">
				<ul class="m_tab">
					<li class="m_tab1_on"></li>
					<li class="m_tab2"><a href="/pkgmg/schedule/Schedule_ReadList.do"></a></li>
					<li class="m_tab3"><a href="/pkgmg/diary/Diary_ReadList.do"></a></li>
					<li class="m_tab6" onclick="javascript:fn_readDetailSearch();"></li>
					<!-- <li class="m_tab7"><a href="http://www.oknet.co.kr" target="_new"></a></li> -->
					<li class="m_tab7"><a href="http://150.23.15.43" target="_new"></a></li>
				</ul>
			</div>

			<!--조회, 테이블, 페이지수 div 시작 -->
			<div class="new_con_Div71">
				
			<!-- PKG현황, 일정목록 검색 -->
			<div class="new_con_Div32 new_pkms_sysSearchForm">
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
						<tbody>
							<tr>
								<th class="w1">기간</th>
								<td>
									<span class="fl_left mg05">[요청or적용]</span>
									<form:input path="date_start" class="inp_w20 fl_left" readOnly="true" />
									<span class="fl_left mg05"> ~ </span> 
									<form:input path="date_end" class="inp_w20 fl_left" readOnly="true" />
								</td>
								<th>대분류</th>
								<td>
									<form:select path="group1Condition" style="width:80%;" onchange="fn_group2_readList();">
										<form:option value="" label="전 체" />
										<form:options items="${Group1List}" itemLabel="codeName" itemValue="code" />
									</form:select>
								</td>
								<th class="w1">상태</th>
								<td>
									<form:select path="statusCondition" items="${PkgModel.statusConditionsList}" itemLabel="codeName" itemValue="code" />
								</td>
							</tr>
							<tr>
								<th class="w1">검색</th>
								<td>
									<form:select path="searchCondition" items="${PkgModel.searchConditionsList}" itemLabel="codeName" itemValue="code" />
									<span class="fl_left mg05">[제목or시스템]</span>
									<form:input path="searchKeyword" onkeypress="if(event.keyCode == 13) { fn_readList(1); }" style="width:150px;" />
								</td>
								<th>중분류</th>
								<td>
									<form:select path="group2Condition" style="width:80%;">
										<form:option value="" label="전 체" />
										<form:options items="${Group2List}" itemLabel="codeName" itemValue="code" />
									</form:select>
								</td>
								<th class="w1">담당</th>
								<td><form:radiobuttons path="userCondition" items="${PkgModel.userConditionsList}" itemLabel="codeName" itemValue="code" /></td>
							</tr>
						<tbody>
					</table>

					<div class="new_btn_sear"><a href="javascript:fn_readList('1');">조 회</a></div>
					<!--조회버튼 --
					<div class="btn_sear">
						<a href="javascript:fn_readList('1');"></a>
					</div>
					-조회버튼 -->
				</div>
				
			</div>

				<!-- m_tab_search PKG현황, 일정목록 검색 -->
				<%-- <div id="m_tab_search">
					<!--조회영역 -->
					<div class="search">
						<table class="sear_table1">
							<tr>
								<th style="width:70px;">기간</th>
								<td style="width:390px;">
									<form:select path="dateCondition" items="${PkgModel.dateConditionsList}" itemLabel="codeName" itemValue="code" style="width:80px;" /> 
									[요청or적용]
									<form:input path="date_start" class="inp" style="width:70px" readOnly="true" /> ~ <form:input path="date_end" class="inp" style="width:70px" readOnly="true" />
								</td>
								<th style="width:70px;">대분류</th>
								<td style="width:250px;"><form:select path="group1Condition" style="width:220px;" onchange="fn_group2_readList();">
										<form:option value="" label="전 체" />
										<form:options items="${Group1List}" itemLabel="codeName" itemValue="code" />
									</form:select>
								</td>
								<th style="width:50px;">상태</th>
								<td><form:select path="statusCondition" items="${PkgModel.statusConditionsList}" itemLabel="codeName" itemValue="code" /></td>
							</tr>
							<tr>
								<th style="width:70px;">검색</th>
								<td style="width:390px;">
									<form:select path="searchCondition" items="${PkgModel.searchConditionsList}" itemLabel="codeName" itemValue="code" style="width:70px;" />
									[제목or시스템]
									<form:input path="searchKeyword" onkeypress="if(event.keyCode == 13) { fn_readList(1); }" style="width:280px;" />
								</td>
								<th style="width:70px;">중분류</th>
								<td style="width:250px;"><form:select path="group2Condition" style="width:220px;">
										<form:option value="" label="전 체" />
										<form:options items="${Group2List}" itemLabel="codeName" itemValue="code" />
									</form:select>
								</td>
								<th style="width:50px;">담당</th>
								<td><form:radiobuttons path="userCondition" items="${PkgModel.userConditionsList}" itemLabel="codeName" itemValue="code" /></td>
							</tr>
						</table>

						<!--조회버튼 -->
						<div class="btn_sear">
							<a href="javascript:fn_readList('1');"></a>
						</div>
					</div>
				</div>
 --%>
				<!-- m_tab1 PKG현황 -->
				<div id="m_tab1">
				
					<!--버튼위치 -->
					<div class="btn_location">
						<!-- <div class="help_notice" style="">
							(* 진행 상태가 <font color="red">붉은색</font>은 '<b>진행중</b>'이며 <font color="blue">파란색</font>은 '<b>완료</b>' / 바탕이 <font color="#fde7d1;">분홍색</font>이면 <b>'긴급등록'</b> / 바탕이 <font color="#ffaa00;">주황색</font>이면 <b>'승인대기'</b> 입니다)
						</div> -->
						<p class="mt20 ml10 fl_left">
						!!상태가 <span class="txt_red">붉은색</span> - <b>진행중</b>, &nbsp;<span class="txt_blue">파란색</span> - <b>완료</b>, &nbsp;바탕이  &nbsp;<span style="background:#fec5e0;">분홍색</span> - <b>‘긴급등록’</b>,  &nbsp;<span style="background:#ffaa00;">주황색</span> - <b>‘승인대기’</b> 입니다.
						</p>
<!-- 						<span class="con_top_btn"><a href="javascript:fn_pkg_read('read', '');">신규</a> -->
						<span class="con_top_btn"> <a href="javascript:fn_list_excel_download();">엑셀 다운로드</a></span>
					</div>

					<div class="fakeContainer" style="width:100%;height:430px; overflow:auto; overflow-x:hidden;">
						<table id="m_tab1_table" class="tbl_type12 w_100">
							<colgroup>
								<col width="10%">
								<col width="6%">
								<col width="18%">
								<col width="5%">
								<col width="18%">
								<col width="8%">
								<col width="8%">
								<col width="13%">
								<col width="7%">
								<col width="7%">
							</colgroup>
							<thead>
								<tr>
									<th class="th_height">상태</th>
									<th class="th_height">진도율</th>
									<th class="th_height">시스템</th>
									<th class="th_height">버전</th>
									<th class="th_height">제목</th>
									<th class="th_height">대표<br>담당자</th>
									<th class="th_height">작성자</th>
									<th class="th_height">요청/<br>검증일자</th>
									<th class="th_height">검증<br>내역<br/>개수</th>
									<th class="th_height">개선<br>내역<br/>개수</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${paginationInfo.totalRecordCount == 0}">
									<tr>
										<td colspan="10" height="30">검색 결과가 없습니다.</td>
									</tr>
								</c:if>
								<c:forEach var="result" items="${PkgModelList}" varStatus="status">
									<tr
										<c:choose>
											<c:when test="${result.pkg_user_status == 'R'}">
												style="background-color: #ffaa00;"
											</c:when>
											<c:when test="${result.urgency_yn == 'Y' || result.urgency_verifi == 'Y'}">
												style="background-color: #fde7d1;"
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
									>
										<td>
											<c:if test="${result.urgency_yn == 'Y'}">
												<b>[긴급]</b>
											</c:if>
											<c:choose>
												 <c:when test="${result.status ==9 or result.status==99 or result.pe_status==9 }"> 
													<font color='blue'><b>${result.status_name}</b></font>
												 </c:when>
												 <c:otherwise>
													<font color='red'><b>${result.status_name}</b></font>
												 </c:otherwise>
											</c:choose>
										</td>
										<td><fmt:formatNumber value="${result.total_progress }" pattern="0" />%</td>
										<td class="td_left">
											<div style="width:200px;overflow:hidden; white-space:nowrap; text-overflow:ellipsis;">
											<c:out value="${result.group_depth}" />
											</div>
										</td>
										<td>
											<c:out value="${result.ver}" />
										</td>
										<td class="td_left">
											<div style="width:200px;overflow:hidden; white-space:nowrap; text-overflow:ellipsis;">
												<a href="javascript:fn_pkg_read('read', '${result.pkg_seq}')">
												<c:out value="${result.title}" />
												<!-- 
												<c:out value="${fn:substring(result.title, 0, 35)}" />
												<c:out value="${fn:length(result.title) > 35 ? '...' : ''}" />
												 -->
												 </a>
											</div>
										</td>
										<td><c:out value="${result.system_user_name}" /></td>
										<td>${result.reg_user_name}</td>
										<td>
											<c:choose>
												<c:when test="${result.status == 0 or result.status == 1}">
													${result.reg_date}
												</c:when>
												<c:otherwise>
													${result.verify_date_start}
												</c:otherwise>
											</c:choose>
										</td>
										<td>${result.total_verify }</td>
										<td>${result.total_improve }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div id="m_tab_pagination" class="mt10">
					<!--페이지수 -->
					<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
				</div>

			</div>

		</div>
		<!--조회, 테이블, 페이지수 div 끝 -->
	</form:form>
</body>
</html>
