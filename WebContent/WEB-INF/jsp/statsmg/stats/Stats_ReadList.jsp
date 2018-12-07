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
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type='text/javascript' src='/js/pkgmg/pkgmg.common.js'></script>


<script type="text/javaScript" defer="defer">
	$(document).ready(function($) {
		// Calendar Init
		doCalendar("start_date");
		doCalendar("end_date");

		init_system_popup();

		$('.defaultDOMWindow').openDOMWindow({
			eventType : 'click',
			loader : 1,
			loaderHeight : 16,
			loaderWidth : 17,
			borderSize : '1',
			windowBGColor : '#fffacd',
			overlayOpacity : 0
		});
	});

	/* 팝업 닫기 */
	function fn_popup_close() {
		parent.$('.defaultDOMWindow').closeDOMWindow();
	}

	/* 조회 */
	function fn_readList() {

// 		if (!fn_kind_check()) {
// 			alert("분류는 하나 이상 선택 하셔야 합니다.");
// 			return;
// 		}

		$("#sessionCondition").val(false);
		
		var form = document.getElementById("StatsModel");
		form.target = "_self";
		form.action = "/statsmg/stats/Stats_ReadList.do";
		form.submit();
	}

	//시스템 선택 결과 처리
	function fn_system_popup_callback(system_key, system_name) {
		$("#search_system_seq").val(system_key);
		$("#search_system_name").val(system_name);
	}

	function fn_kind_check() {
		if ($('input[name=kind_group1]').is(":checked")) {
			return true;
		}
		if ($('input[name=kind_group2]').is(":checked")) {
			return true;
		}
		if ($('input[name=kind_group3]').is(":checked")) {
			return true;
		}
		if ($('input[name=kind_system]').is(":checked")) {
			return true;
		}
		if ($('input[name=kind_user]').is(":checked")) {
			return true;
		}
		if ($('input[name=kind_idc]').is(":checked")) {
			return true;
		}
		if ($('input[name=kind_equipment]').is(":checked")) {
			return true;
		}
		return false;
	}
	/*Excel down*/
	function fn_list_excel_download() {
		 
		doSubmit("StatsModel", "/statsmg/stats/Stats_excelDown.do", "fn_callback_file_download");
		 
	}
	function fn_doChart(){
		//alert("ss");
		
		var option = "width=1050px, height=600px, scrollbars=yes, resizable=yes, statusbar=no";
		var sWin = window.open("", "ChartRead", option);
					
		var form = document.getElementById("StatsModel");
		form.target = "ChartRead";
		form.action = "/Chart_Popup_Read.do";
		form.submit();
		sWin.focus();
		
	}
</script>
</head>

<body>
	<form:form commandName="StatsModel" method="post" onsubmit="return false;">
		<form:hidden path="pkg_seq" />
		<form:hidden path="retUrl" value="xxxxx" />
		<form:hidden path="sessionCondition" />
		
		<!-- 조회 -->
		<div class="search">
			<table class="sear_table3">
				<colgroup>
					<col width="60px" />
					<col width="110px" />
					<col width="60px" />
					<col width="120px" />
					<col width="60px" />
					<col width="290px" />
					<col width="80px" />
					<col width="250px" />
				</colgroup>
				<tr>
					<th>분류</th>
					<td><form:radiobuttons path="termCondition" items="${StatsModel.termConditionsList}" itemLabel="codeName" itemValue="code" /></td>
					<th>담당</th>
					<td><form:radiobuttons path="userCondition" items="${StatsModel.userConditionsList}" itemLabel="codeName" itemValue="code" /></td>
					<th>기간</th>
					<td>
						<form:input path="start_date" class="inp" style="width:70px" readOnly="true" /> ~ <form:input path="end_date" class="inp" style="width:70px" readOnly="true" />
					</td>
					<th>시스템</th>
					<td>
						<form:hidden path="search_system_seq" />
						<form:input path="search_system_name" readonly="true" />
							<span>
								<img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup" align="absmiddle" style="vertical-align: middle;" />
							</span>
					</td>
				</tr>
				<%-- <tr>
					<th>기간</th>
					<td>
						<form:input path="start_date" class="inp" style="width:70px" readOnly="true" /> ~ <form:input path="end_date" class="inp" style="width:70px" readOnly="true" />
					</td>
					<th>날짜</th>
					<td>
						<form:radiobuttons path="dateCondition" items="${StatsModel.dateConditionsList}" itemLabel="codeName" itemValue="code" />
					</td>
					<th>표시항목</th>
					<td>
						<form:checkbox path="kind_group1" label="대분류" />
						<form:checkbox path="kind_group2" label="중분류" />
						<form:checkbox path="kind_group3" label="소분류" />
						<form:checkbox path="kind_system" label="시스템" />
						<form:checkbox path="kind_user" label="대표 담당자" /> 
						<form:checkbox path="kind_title" label="PKG제목" />
						<form:checkbox path="kind_content" label="개선내역요약" />
						<form:checkbox path="kind_sbt" label="SBT(서비스 중단시간)" />
						<form:checkbox path="kind_verify_day" label="검증 소요일" />
						<form:checkbox path="kind_daytime" label="주간/야간" />
					</td>
				</tr> --%>

			</table>

			<!--조회버튼 -->
			<div class="btn_sear">
				<a href="javascript:fn_readList();"></a>
			</div>
		</div>

		<!--버튼위치 -->
		<div class="btn_location">
		<!--그래프 버튼-->
		<!--<span><a href="javascript:fn_doChart()"><img alt="그래프" src="/images/btn_graph.gif"></a></span> -->
			<span><a href="javascript:fn_list_excel_download();"><img src="/images/btn_excelldown.gif" alt="저장" /></a></span>
		</div>

		<!--테이블, 페이지 DIV 시작 -->
		<div style="padding: 0px 0px 0px 0px; height: 580px; clear: both; overflow: auto;">
			<table class="sear_table2" style="width: 8750px;">
				<colgroup>
<!-- 					순번 -->
					<col width="50px" />
					<c:if test="${StatsModel.termCondition=='VERIFY'}"> 	
<!-- 						검증 일시 -->
						<col width="100px"/>
					</c:if>
					
<!-- 					PKG제목 -->
					<col width="150px"/>
<!-- 					시스템 -->
					<col width="100px"/>
					
					<c:if test="${StatsModel.termCondition=='APPLY'}">
<!-- 						PKG 적용 -->
						<col width="100px"/>
						<col width="100px"/>
						<col width="100px"/>
						<col width="100px"/>
						<col width="100px"/>
					</c:if>
					<c:if test="${StatsModel.termCondition=='VERIFY'}"> 	
<!-- 						PKG 검증 -->
						<col width="50px"/>
						<col width="100px"/>
					</c:if>
					
<!-- 					신규/보완/개선 -->
					<col width="50px"/>
					<col width="50px"/>
					<col width="50px"/>
					<col width="50px"/>
<!-- 					주요보완내역 -->
					<col width="150px"/>
	
<!-- 					대분류 -->
					<col width="150px"/>
<!-- 					중분류 -->
					<col width="150px"/>
<!-- 					소분류 -->
					<col width="150px"/>
					
<!-- 					대표담당자 -->
					<col width="100px"/>
					
<!-- 					버전 -->
					<col width="100px"/>
<!-- 					버전구분 -->
					<col width="100px"/>
					
<!-- 					SBT -->
					<col width="100px"/>
					
<!-- 					로밍영향도 -->
					<col width="100px"/>
<!-- 					과금영향도 -->
					<col width="100px"/>
					
<!-- 					검증 소요일 -->
					<col width="100px"/>
<!-- 					검증요청 범위 -->
					<col width="100px"/>
					
<!-- 					검증 대상 수행 여부 -->
					<col width="60px"/>
					<col width="60px"/>
					<col width="60px"/>
					<col width="60px"/>
					<col width="60px"/>
<!-- 					검증 항목 Comment -->
					<col width="100px"/>
					<col width="100px"/>
					<col width="100px"/>
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						보완 내역별 시험 결과 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						Regression Test 및 기본 검증 결과 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						성능 용량 시험 결과 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						개발 근거 문서 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						신규 기능 규격서 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						보완 내역서 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						시험 절차서 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						코드 리뷰 및 SW 아키텍처 리뷰 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						기능 검증 결과 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						성능 용량 시험 결과 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						보안내역서, 기능 변경 요청서 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						보안내역별 검증 결과 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						서비스 영향도(로밍 포함) -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						과금 영향도 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						작업절차서, S/W블록 내역 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						PKG 적용 후 check list -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						CoD/PoD 변경사항, 운용팀 공지사항 -->
					<col width="100px"/>
					<col width="100px"/>
					
<!-- 						보안 Guide 적용확인서 -->
					<col width="100px"/>
					<col width="100px"/>
				</colgroup>
				
				
				
				<%-- <colgroup>
					<!-- 순번 -->
					<col width="80px" />
					<c:if test="${StatsModel.termCondition=='VERIFY'}"> 	
						<col width="150px" />
					</c:if>
					<c:forEach var="i" begin="5" end="${StatsModel.kind_count}">
						<col width="80px" />
					</c:forEach>
					
					<!-- pkg 제목 -->
					<col width="80px" />8
					<!-- 시스템 -->
					<col width="80px" />
					<!-- PKG 적용 -->
					<c:if test="${StatsModel.termCondition=='APPLY'}">
						<col width="80px" />
						<col width="80px" />
						<col width="80px" />
						<col width="80px" />
						<col width="80px" />
					</c:if>
					<!-- 검증 -->
					<c:if test="${StatsModel.termCondition=='VERIFY'}"> 	
						<col width="80px" />
						<col width="100px" />
					</c:if>
					<!-- 신규/보완/개선 -->
					<col width="70px" />
					<col width="70px" />
					<col width="70px" />
					<col width="70px" />
					<!-- pkg 주요보완내역 -->
					<col width="80px" />
					<!-- 대/중/소 분류 -->
					<col width="80px" />
					<col width="80px" />
					<col width="80px" />
					<!-- 대표 담당자 -->
					<col width="80px" />
					<!-- 버젼/버젼구분 -->
					<col width="80px" />
					<col width="80px" />
					<!-- 서비스 중단시간 -->
					<col width="80px" />
					<!-- 로밍/과금 영향도 -->
					<col width="80px" />
					<col width="80px" />
					<!-- 검증소요일 -->
					<col width="80px" />

				</colgroup> --%>
				
				<!-- 내용 -->
				<tr>
<%-- 					<c:if test="${StatsModel.kind_title}"> --%>
<!-- 					<th >PKG제목</th>	 -->
<%-- 					</c:if> --%>
					<th rowspan="2">순번</th>
					<c:if test="${StatsModel.termCondition=='VERIFY'}"> 	
						<th rowspan="2">검증 일시</th>
					</c:if>
					
					<th rowspan="2">PKG제목</th>
					<th rowspan="2">시스템</th>
					
					<c:if test="${StatsModel.termCondition=='APPLY'}">
						<th colspan="5">PKG 적용</th>
					</c:if>
					<c:if test="${StatsModel.termCondition=='VERIFY'}"> 	
						<th colspan="2">PKG 검증</th>
					</c:if>
					
					<th colspan="4">신규/보완/개선</th>
					<th rowspan="2">주요보완내역</th>

					<th rowspan="2">대분류</th>
					<th rowspan="2">중분류</th>
					<th rowspan="2">소분류</th>
					
					<th rowspan="2">대표담당자</th>
					
					<th rowspan="2">버전</th>
					<th rowspan="2">버전구분</th>
					
					<th rowspan="2">SBT</th>
					
					<th rowspan="2">로밍영향도</th>
					<th rowspan="2">과금영향도</th>
					
					<th rowspan="2">검증 소요일</th>
					<th rowspan="2">검증요청 범위</th>
					
					<th colspan="5">검증 대상 수행 여부</th>
					<th colspan="5">검증 항목 Comment</th>
					
					<th colspan="2">
						보완 내역별 시험 결과
					</th>
					<th colspan="2">
						Regression Test 및 기본 검증 결과
					</th>
					<th colspan="2">
						성능 용량 시험 결과
					</th>
					<th colspan="2">
						개발 근거 문서
					</th>
					<th colspan="2">
						신규 기능 규격서
					</th>
					<th colspan="2">
						보완 내역서
					</th>
					<th colspan="2">
						시험 절차서
					</th>
					<th colspan="2">
						코드 리뷰 및 SW 아키텍처 리뷰
					</th>
					<th colspan="2">
						기능 검증 결과
					</th>
					<th colspan="2">
						성능 용량 시험 결과
					</th>
					<th colspan="2">
						보안내역서, 기능 변경 요청서
					</th>
					<th colspan="2">
						보안내역별 검증 결과
					</th>
					<th colspan="2">
						서비스 영향도(로밍 포함)
					</th>
					<th colspan="2">
						과금 영향도
					</th>
					<th colspan="2">
						작업절차서, S/W블록 내역
					</th>
					<th colspan="2">
						PKG 적용 후 check list
					</th>
					<th colspan="2">
						CoD/PoD 변경사항, 운용팀 공지사항
					</th>
					<th colspan="2">
						보안 Guide 적용확인서
					</th>
				</tr>
				
				<tr>
					<c:if test="${StatsModel.termCondition=='APPLY'}">
						<th>초도일자</th>
						<th>초도적용장비 수</th>
						<th>확대일자</th>
						<th>확대적용장비 수</th>
						<th>PKG 완료여부</th>
					</c:if>
					<c:if test="${StatsModel.termCondition=='VERIFY'}">
						<th>건수</th>
						<th>검증분야 개수</th>
					</c:if>
					
					<th>신규</th>
					<th>보완</th>
					<th>개선</th>
					<th>전체</th>
					
					<th>기능</th>
					<th>비기능</th>
					<th>용량</th>
					<th>과금</th>
					<th>보안</th>
					
					<th>기능</th>
					<th>비기능</th>
					<th>용량</th>
					<th>과금</th>
					<th>보안</th>
					
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
					<th>Comment</th>
					<th>첨부 파일</th>
				</tr>

				<c:if test="${fn:length(StatsModelList) == 0}">
					<tr class="pop_tbl_type3">
						<td colspan="${StatsModel.kind_count + 9}" height="30">검색 결과가 없습니다.</td>
					</tr>
				</c:if>

				<c:forEach var="dateStats" items="${StatsModelList}" varStatus="status1">
					<c:forEach var="stats" items="${dateStats.kindStatsMap}" varStatus="status2">
						<tr class="pop_tbl_type3">
<%-- 							<c:if test="${status2.first}">
								<td rowspan="${fn:length(dateStats.kindStatsMap)}">
									<c:choose>
										<c:when test="${StatsModel.dateCondition == 'MM'}">
											<c:out value="${dateStats.date_unit}" />&nbsp;월
										</c:when>
										<c:when test="${StatsModel.dateCondition == 'WW'}">
											<c:choose>
												<c:when test="${fn:length(dateStats.date_unit) == 23}">
													<c:out value="${fn:substring(dateStats.date_unit,0,10)}" /><br />
													<c:out value="${fn:substring(dateStats.date_unit,10,23)}" />
												</c:when>
												<c:otherwise>
													<c:out value="${dateStats.date_unit}" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:out value="${dateStats.date_unit}" />
										</c:otherwise>
									</c:choose>
								</td>
							</c:if> --%>
							
<!-- 							전에 쓰던거 -->
<%-- 							<c:if test="${StatsModel.kind_title}"> --%>
<!-- 									<td> -->
<%-- 										<c:out value="${fn:substring(stats.value.pkg_name, 0, 12)}" /> --%>
<%-- 										<c:out value="${fn:length(stats.value.pkg_name) > 12 ? '...' : ''}" />														 --%>
<!-- 									</td> -->
<%-- 							</c:if> --%>
							<!-- 순번 -->
							<td style="text-align: center;"><c:out value="${stats.value.rownum}" />111</td>
							<!-- 검증 기간 -->
							<c:if test="${StatsModel.termCondition=='VERIFY'}"> 	
								<td style="text-align: center;"><c:out value="${stats.value.verify_start}" /> ~ <c:out value="${stats.value.verify_end}" /></td>
							</c:if>
							
							<!-- 제목 -->
							<td>
								<c:out value="${fn:substring(stats.value.pkg_name, 0, 15)}" />
								<c:out value="${fn:length(stats.value.pkg_name) > 15 ? '...' : ''}" />														
							</td>
							<!-- 시스템 -->
							<td><c:out value="${stats.value.system_name}" /></td>
							
							<!-- pkg적용 -->
							<c:if test="${StatsModel.termCondition=='APPLY'}">
								<td style="text-align: center;">
									<a href="#pkgContent${status1.count}${status2.count}" class="defaultDOMWindow">
										<c:out value="${stats.value.chodo_workdate}" />
									</a>
								</td>
								<td style="text-align: center;">
									<a href="#pkgContent${status1.count}${status2.count}" class="defaultDOMWindow">
										<c:out value="${stats.value.chodo}" />
									</a>
								</td>
								<td style="text-align: center;">
									<a href="#pkgContent${status1.count}${status2.count}" class="defaultDOMWindow">
										<c:out value="${stats.value.hwakdae_workdate}" />
									</a>
								</td>
								<td style="text-align: center;">
									<a href="#pkgContent${status1.count}${status2.count}" class="defaultDOMWindow">
										<c:out value="${stats.value.hwakdae}" />
									</a>
								</td>
								<td style="text-align: center;">
									<a href="#pkgContent${status1.count}${status2.count}" class="defaultDOMWindow">
										<c:out value="${stats.value.pkg_end_ox}" />
									</a>
								</td>
								
							</c:if>
							
							<!-- pkg검증 -->
							<c:if test="${StatsModel.termCondition=='VERIFY'}">
								<td style="text-align: center;"><a href="#pkgContent${status1.count}${status2.count}" class="defaultDOMWindow">
								<c:out value="${stats.value.pkg_count}" /></a></td>
								
								<td style="text-align: center;"><a href="#pkgContent${status1.count}${status2.count}" class="defaultDOMWindow">
								<c:out value="${stats.value.pkg_verify_count}" /></a></td>
							</c:if>
							
							<!-- 신규보완개선 -->
							<td style="text-align: center;">
								<a href="#pkgContent${status1.count}${status2.count}" class="defaultDOMWindow">
									<c:out value="${stats.value.newCount}" />
								</a>
								
								<div id="pkgContent${status1.count}${status2.count}" style="display: none;">
									<div style="width: 100%; text-align: right;">
										<a class="close_layer" style="text-align: right:" href="javascript:fn_popup_close();">
											<img alt="닫기" src="/images/pop_btn_close2.gif" width="15" height="14">
										</a>
									</div>
									<p style='line-height: 1.6em;'>
										<c:if test="${StatsModel.kind_group1}">
											<b>대분류: </b>
											<c:out value="${stats.value.group1_name}" />
											<br />
										</c:if>
										<c:if test="${StatsModel.kind_group2}">
											<b>중분류: </b>
											<c:out value="${stats.value.group2_name}" />
											<br />
										</c:if>
										<c:if test="${StatsModel.kind_group3}">
											<b>소분류: </b>
											<c:out value="${stats.value.group3_name}" />
											<br />
										</c:if>
										<c:if test="${StatsModel.kind_system}">
											<b>시스템: </b>
											<c:out value="${stats.value.system_name}" />
											<br />
										</c:if>
										<c:if test="${StatsModel.kind_user}">
											<b>담당자: </b>
											<c:out value="${stats.value.system_user_name}" />
											<br />
										</c:if>
										<br /> 
										<c:if test="${StatsModel.termCondition=='VERIFY'}">
										<b>* PKG 검증</b>
										<br />
											&nbsp;&nbsp;&nbsp;&nbsp;건수:${stats.value.pkg_count}
											&nbsp;&nbsp;&nbsp;&nbsp;검증분야 개수: ${stats.value.pkg_verify_count}
										<br />
										</c:if>
										<br />
										<c:if test="${StatsModel.termCondition=='APPLY'}"> 
										<b>* PKG 적용</b>
										<br />
											&nbsp;&nbsp;&nbsp;&nbsp;초도 : ${stats.value.chodo}
											&nbsp;&nbsp;&nbsp;&nbsp;확대 : ${stats.value.hwakdae}
											&nbsp;&nbsp;&nbsp;&nbsp;원복 : ${stats.value.equipmentPkgRevertCount}
										<br />
										</c:if>
										<br />
										<b>* 신규/보완/개선</b>
										<br />
											&nbsp;&nbsp;&nbsp;&nbsp;신규 : ${stats.value.newCount}
											&nbsp;&nbsp;&nbsp;&nbsp;보완 : ${stats.value.pnCount}
											&nbsp;&nbsp;&nbsp;&nbsp;개선 : ${stats.value.crCount}
										<br />
										<br />
										<b>* 장비</b>
										<br />
										<c:forEach var="substats" items="${stats.value.subStatsMap}" varStatus="status">
											&nbsp;&nbsp;&nbsp;&nbsp;
											<c:out value="${substats.value.system_name}" />&nbsp;
											<c:if test="${substats.value.equipmentPkgStartCount > 0}">
												(초도: <c:out value="${substats.value.equipmentPkgStartCount}" />)&nbsp;
											</c:if>
											<c:if test="${substats.value.equipmentPkgEndCount > 0}">
												(확대: <c:out value="${substats.value.equipmentPkgEndCount}" />)&nbsp;
											</c:if>
											<c:if test="${substats.value.equipmentPkgRevertCount > 0}">
												(원복: <c:out value="${substats.value.equipmentPkgRevertCount}" />)&nbsp;
											</c:if>
											<br />
										</c:forEach>
										<br />
										<br /> <b>* PKG 목록</b>
										<br />
									</p>
									<div style="padding: 10px;">
									<table style="width: 450px;" class="tbl_type5">
											<colgroup>
												<col width="50px;" />
												<col width="400px;" />
											</colgroup>
											<thead>
												<tr>
													<th class="tbl_lline" style="border-bottom: 2px solid #d8d7d7;" scope="col">번호</th>
													<th class="tbl_rline" style="border-bottom: 2px solid #d8d7d7;" scope="col">제목</th>
												</tr>
											</thead>
	
											<c:forEach var="pkgSeq" items="${stats.value.pkgSeqMap}" varStatus="status">
												<tr>
													<td align="center"><c:out value="${pkgSeq.key}" /></td>
													<td align="left"><a href="javascript:fn_pkg_read('read', '${pkgSeq.key}', 'StatsModel')">
														<c:out value="${fn:substring(pkgSeq.value, 0, 32)}" />
														<c:out value="${fn:length(pkgSeq.value) > 32 ? '...' : ''}" />
														</a>
													</td>
												</tr>
											</c:forEach>
									</table>
									</div>
								</div>
							</td>
							<td style="text-align: center;">
								<a href="#pkgContent${status1.count}${status2.count}" class="defaultDOMWindow">
									&nbsp;<c:out value="${stats.value.pnCount}" />&nbsp;
								</a>
							</td>
							<td style="text-align: center;">
								<a href="#pkgContent${status1.count}${status2.count}" class="defaultDOMWindow">
									&nbsp;<c:out value="${stats.value.crCount}" />&nbsp;
								</a>
							</td>
							<td style="text-align: center;">
								<a href="#pkgContent${status1.count}${status2.count}" class="defaultDOMWindow">
									&nbsp;<c:out value="${stats.value.newCount + stats.value.pnCount + stats.value.crCount}" />&nbsp;
								</a>
							</td>
							
							<!-- 주요보완내역 -->
							<td>
								<c:out value="${fn:substring(stats.value.content, 0, 15)}" />
								<c:out value="${fn:length(stats.value.content) > 15 ? '...' : ''}" />
							</td>
							<!-- 대/중/소 분류 -->
							<td><c:out value="${stats.value.group1_name}" /></td>
							<td><c:out value="${stats.value.group2_name}" /></td>
							<td><c:out value="${stats.value.group3_name}" /></td>
							<!-- 대표담당자 -->
							<td style="text-align: center;"><c:out value="${stats.value.system_user_name}" /></td>
							<!-- 버전/버전구분 -->
							<td>${stats.value.ver}</td>
							<td style="text-align: center;">${stats.value.ver_gubun}</td>
							<!-- 서비스 중단시간 -->
							<td style="text-align: center;"><c:out value="${stats.value.sbt}" /></td>
							<!-- 로밍/과금 영향도 -->
							<td style="text-align: center;">${stats.value.roaming_link}</td>
							<td style="text-align: center;">${stats.value.pe_yn}</td>
							<!-- 검증 소요일 -->
							<td style="text-align: center;"><c:out value="${stats.value.verify_day}" /> 일</td>
							<!-- 검증요청 범위 -->
							<td style="text-align: center;"><c:out value="${stats.value.dev_yn}" /></td>
							<!-- 검증 대상 수행 여부 -->
							<td style="text-align: center;"><c:out value="${stats.value.on_yn}" /></td>
							<td style="text-align: center;"><c:out value="${stats.value.non_yn}" /></td>
							<td style="text-align: center;"><c:out value="${stats.value.vol_yn}" /></td>
							<td style="text-align: center;"><c:out value="${stats.value.cha_yn}" /></td>
							<td style="text-align: center;"><c:out value="${stats.value.sec_yn}" /></td>
							<!-- 검증 항목 comment -->
							<td>
								<c:out value="${fn:substring(stats.value.on_comment, 0, 15)}" />
								<c:out value="${fn:length(stats.value.on_comment) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.non_comment, 0, 15)}" />
								<c:out value="${fn:length(stats.value.non_comment) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.vol_comment, 0, 15)}" />
								<c:out value="${fn:length(stats.value.vol_comment) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.cha_comment, 0, 15)}" />
								<c:out value="${fn:length(stats.value.cha_comment) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.sec_comment, 0, 15)}" />
								<c:out value="${fn:length(stats.value.sec_comment) > 15 ? '...' : ''}" />
							</td>
							<!-- comment/첨부파일 -->
							<td>
								<c:out value="${fn:substring(stats.value.col4, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col4) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col4_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col4_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col6, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col6) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col6_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col6_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col8, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col8) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col8_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col8_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col29, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col29) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col29_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col29_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col10, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col10) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col10_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col10_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col30, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col30) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col30_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col30_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col31, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col31) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col31_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col31_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col32, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col32) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col32_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col32_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col12, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col12) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col12_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col12_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col41, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col41) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col41_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col41_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col14, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col14) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col14_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col14_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col16, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col16) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col16_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col16_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col18, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col18) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col18_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col18_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col20, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col20) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col20_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col20_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col22, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col22) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col22_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col22_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col24, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col24) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col24_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col24_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col26, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col26) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col26_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col26_file) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col39, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col39) > 15 ? '...' : ''}" />
							</td>
							<td>
								<c:out value="${fn:substring(stats.value.col39_file, 0, 15)}" />
								<c:out value="${fn:length(stats.value.col39_file) > 15 ? '...' : ''}" />
							</td>
						</tr>
					</c:forEach>
				</c:forEach>

			</table>
		</div>

	</form:form>
</body>
</html>
