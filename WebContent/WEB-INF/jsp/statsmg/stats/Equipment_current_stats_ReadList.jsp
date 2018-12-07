<%@page import="java.nio.channels.FileChannel.MapMode"%>
<%@page import="java.util.HashMap"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <%
	HashMap map = (HashMap)request.getAttribute("central");
%> --%>
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
// 	$(document).ready(function($) {
// 		doTable("equipment_tbl", "tbl_type3", "3", "6", [ "100", "100", "100", "120", "80", "70", 
// 		                                                  "40","40","40","40","40","40","40","40","40","40","40","40","40","40",
// 		                                                  "200","200","200","200" ]);
// 	});

	function system_m(group1_seq, group2_seq, group3_seq, system_seq) {
		$("#group1_seq").val(group1_seq);
		$("#group2_seq").val(group2_seq);
		$("#group3_seq").val(group3_seq);
		$("#system_seq").val(system_seq);
		
		var form = document.getElementById("StatsModel");
		form.action = "<c:url value='/sys/group1/Group1_ReadList.do'/>";
		form.submit();
	}
	
	/*Excel down*/
	function fn_list_excel_download() {
		 
		doSubmit("StatsModel", "/statsmg/stats/Equipment_excelDown.do", "fn_callback_file_download");
		 
	}
</script>
</head>

<body>
	<form:form commandName="StatsModel" method="post" onsubmit="return false;">
		<form:hidden path="group1_seq" />
		<form:hidden path="group2_seq" />
		<form:hidden path="group3_seq" />
		<form:hidden path="system_seq" />
		

<div class="con_Div2 con_area mt20">
	<div class="con_detail">
		<!--버튼위치 -->
		<div class="con_top_btn">
			<a href="javascript:fn_list_excel_download();">엑셀 다운로드</a>
		</div>
		<div style="height: 600px; clear: both; overflow: auto;">
			<table class="tbl_type12" style="width: 9110px;">
				<colgroup>
					<!-- 대,중,소,시스템 -->
					<col width="80px;">
					<col width="90px;">
					<col width="110px;">
					<col width="140px;">
					
					<!-- 국사 13개-->
					<col width="50px;">
					<col width="30px;">
					<col width="30px;">
					<col width="30px;">
					<col width="30px;">
					<col width="30px;">
					<col width="30px;">
					<col width="30px;">
					<col width="30px;">
					<col width="30px;">
					<col width="30px;">
					<col width="30px;">
					<col width="60px;">
					
					<!-- 담당자 12개 -->
					<col width="110px;">
					<col width="110px;">
					<col width="260px;">
					<col width="200px;">
					<col width="110px;">
					<col width="500px;">
					<col width="350px;">
					<col width="280px;">
					<col width="150px;">
					<col width="220px;">
					<col width="110px;">
					<col width="110px;">
					<col width="110px;">
					
					<!-- 시스템 정보 -->
					<col width="150px;">
					<col width="360px;">
					<col width="1000px;">
					<col width="360px;">
					
					<!-- 첨부파일 -->
					<col width="350px;">
					<col width="350px;">
					<col width="350px;">
					<col width="350px;">
					<col width="350px;">
					<col width="350px;">
					<col width="350px;">
					<col width="350px;">
					
					<!-- 대표담당자 -->
					<col width="40px;">
					<col width="40px;">
					<col width="40px;">
					<!-- 로드맵 입력 -->
					<col width="110px;">
					<!-- 템플릿 등록 -->
					<col width="50px;">
					<col width="40px;">
					<col width="40px;">
					<!-- 템플릿 등록 항목-->
					<col width="200px;">
					<col width="200px;">
					<col width="200px;">
					
				</colgroup>
				<tr>
					<th rowspan="2">대분류</th>
					<th rowspan="2">중분류</th>
					<th rowspan="2">소분류</th>
					<th rowspan="2">시스템</th>
					<th colspan="5">수도권</th>
					<th colspan="2">부산</th>
					<th colspan="2">대구</th>
					<th class="th_height">서부</th>
					<th colspan="2">중부</th>
					<th>PKMS</th>
					<th colspan="13">담당자</th>
					<th rowspan="2">공급사</th>
					<th rowspan="2">시스템 Full Name</th>
					<th rowspan="2">한줄설명</th>
					<th rowspan="2">영향시스템</th>
					<th colspan="8">첨부파일</th>
					<th colspan="3">대표 담당자</th>
					<th rowspan="2">16년 로드맵 입력</th>
					<th colspan="3">템플릿 등록</th>
					<th colspan="3">템플릿 등록 항목</th>
				</tr>
				<tr>
					<th>보라매</th>
					<th class="th_height">성수</th>
					<th class="th_height">분당</th>
					<th class="th_height">수유</th>
					<th class="th_height">인천</th>
					
					<th class="th_height">센텀</th>
					<th class="th_height">부암</th>
					
					<th class="th_height">태평</th>
					<th class="th_height">본리</th>
					
					<th class="th_height">서부</th>
					
					<th class="th_height">둔산</th>
					<th class="th_height">부사</th>
					
					<th>검증센터</th>
					
					<!-- 담당자 -->
					<th>개발검증</th>
					<th>개발승인</th>
					<th>상용검증</th>
					<th>상용승인</th>
					<th>사업기획</th>
					<th>장비운용</th>
					<th>협력업체</th>
					<th>협력업체1</th>
					<th>영업담당</th>
					<th>용량검증</th>
					<th>보안검증</th>
					<th>과금검증</th>
					<th>비기능검증</th>
					
					<!-- 첨부파일 -->
					<th>시스템 매뉴얼</th>
					<th>PKG 표준절차서</th>
					<th>교육자료</th>
					<th>관련규격</th>
					<th>성능용량</th>
					<th>시설현황</th>
					<th>RM 방안</th>
					<th>기타</th>
					<!-- 대표 담당자 -->
					<th>상용</th>
					<th>개발</th>
					<th>BP</th>
					<!-- 템플릿 등록 -->
					<th>비기능</th>
					<th>용량</th>
					<th>과금</th>
					<!-- 템플릿 등록 항목 -->
					<th>비기능</th>
					<th>용량</th>
					<th>과금</th>
					
				</tr>
				<c:forEach var="result" items="${EquipmentModelList}" varStatus="status">
					<tr>
						<td><c:out value="${result.group1_name}" /></td>
						<td><c:out value="${result.group2_name}" /></td>
						<td><c:out value="${result.group3_name}" /></td>
						<td>
							<a href="javascript:system_m(${result.group1_seq }, ${result.group2_seq }, ${result.group3_seq }, ${result.system_seq } );">
								<c:out value="${result.system_name}" />
							</a>
						</td>
						
						<td><c:out value="${result.boramae_cnt}" /></td>
						<td><c:out value="${result.sungsu_cnt}" /></td>
						<td><c:out value="${result.bundang_cnt}" /></td>
						<td><c:out value="${result.suyu_cnt}" /></td>
						<td><c:out value="${result.inchun_cnt}" /></td>
						
						<td><c:out value="${result.senterm_cnt}" /></td>
						<td><c:out value="${result.buam_cnt}" /></td>
						
						<td><c:out value="${result.taepyong_cnt}" /></td>
						<td><c:out value="${result.bonri_cnt}" /></td>
						
						<td><c:out value="${result.seobu_cnt}" /></td>
						
						<td><c:out value="${result.dunsan_cnt}" /></td>
						<td><c:out value="${result.busa_cnt}" /></td>
						
						<td><c:out value="${result.pkms_cnt}" /></td>
						
						<!-- 담당자 -->
						<td><c:out value="${result.dv_user}" /></td>
						<td><c:out value="${result.da_user}" /></td>
						<td><c:out value="${result.vu_user}" /></td>
						<td><c:out value="${result.au_user}" /></td>
						<td><c:out value="${result.pu_user}" /></td>
						<td><c:out value="${result.eq_user}" /></td>
						<td><c:out value="${result.bpu0}" /></td>
						<td><c:out value="${result.bpu1}" /></td>
						<td><c:out value="${result.sales_user_info}" /></td>
						<td><c:out value="${result.vo_user}" /></td>
						<td><c:out value="${result.se_user}" /></td>
						<td><c:out value="${result.ch_user}" /></td>
						<td><c:out value="${result.no_user}" /></td>
						
						<!-- 공급사 외 3항목 -->
						<td><c:out value="${result.supply}" /></td>
						<td><c:out value="${result.full_name}" /></td>
						<td><c:out value="${result.oneline_explain}" /></td>
						<td><c:out value="${result.impact_systems}" /></td>
						
						<!-- 첨부파일 -->
						<td><c:out value="${result.file1}" /></td>
						<td><c:out value="${result.file2}" /></td>
						<td><c:out value="${result.file3}" /></td>
						<td><c:out value="${result.file4}" /></td>
						<td><c:out value="${result.file5}" /></td>
						<td><c:out value="${result.file6}" /></td>
						<td><c:out value="${result.file7}" /></td>
						<td><c:out value="${result.file8}" /></td>
						<!-- 대표 담당자 -->
						<td><c:out value="${result.system_user_name}" /></td>
						<td><c:out value="${result.dev_system_user_name}" /></td>
						<td><c:out value="${result.bp_user_name}" /></td>
						
						<td><c:out value="${result.road_map_ox}" /></td>
						<!-- 템플릿 등록 -->
						<td><c:out value="${result.non_ox}" /></td>
						<td><c:out value="${result.vol_ox}" /></td>
						<td><c:out value="${result.cha_ox}" /></td>
						<!-- 템플릿 등록 항목 -->
						<td><c:out value="${result.non_title}" /></td>
						<td><c:out value="${result.vol_title}" /></td>
						<td><c:out value="${result.cha_title}" /></td>
					</tr>
				</c:forEach>
			</table>
			<%-- <table id="equipment_tbl" class="tbl_type3" border="1">
			<tr class="pop_tbl_type1">
				<th rowspan="3" style="background-color:#BEEFFF">대분류</th>
				<th rowspan="3" style="background-color:#BEEFFF">중분류</th>
				<th rowspan="3" style="background-color:#BEEFFF">소분류</th>
				<th rowspan="3" style="background-color:#BEEFFF">시스템</th>
				<th rowspan="3" style="background-color:#BEEFFF">시스템공급사</th>
				<th rowspan="3" style="background-color:#BEEFFF">담당자</th>
				<c:set var="loop" value="false" />
				<c:forEach var="result_idc" items="${EquipmentIdcModelList}" varStatus="status_idc">
					<th style="background-color:#D5C2EE">
						${fn:substring(result_idc.central_name, 0, fn:indexOf(result_idc.central_name, 'Network')) }
					</th>
					<c:if test="${result_idc.central_name == '수도권Network본부' and not loop}">
						<th style="background-color:#D5C2EE" colspan="<%=map.get("seoul") %>">수도권</th>
						<c:set var="loop" value="true" />
					</c:if>
				</c:forEach>
				<c:set var="loop" value="false" />
				<c:forEach var="result_idc" items="${EquipmentIdcModelList}" varStatus="status_idc">
					<c:if test="${result_idc.central_name == '부산Network본부' and not loop}">
						<th style="background-color:#D5C2EE" colspan="<%=map.get("busan") %>">부산</th>
						<c:set var="loop" value="true" />
					</c:if>
				</c:forEach>
				<c:set var="loop" value="false" />
				<c:forEach var="result_idc" items="${EquipmentIdcModelList}" varStatus="status_idc">
					<c:if test="${result_idc.central_name == '대구Network본부' and not loop}">
						<th style="background-color:#D5C2EE" colspan="<%=map.get("daegu") %>">대구</th>
						<c:set var="loop" value="true" />
					</c:if>
				</c:forEach>
				<c:set var="loop" value="false" />
				<c:forEach var="result_idc" items="${EquipmentIdcModelList}" varStatus="status_idc">
					<c:if test="${result_idc.central_name == '서부Network본부' and not loop}">
						<th style="background-color:#D5C2EE" colspan="<%=map.get("seobu") %>">서부</th>
						<c:set var="loop" value="true" />
					</c:if>
				</c:forEach>
				<c:set var="loop" value="false" />
				<c:forEach var="result_idc" items="${EquipmentIdcModelList}" varStatus="status_idc">
					<c:if test="${result_idc.central_name == '중부Network본부' and not loop}">
						<th style="background-color:#D5C2EE" colspan="<%=map.get("jungbu") %>">중부</th>
						<c:set var="loop" value="true" />
					</c:if>
				</c:forEach>
				<th rowspan="3">시설현황 상세설명</th>
				<th rowspan="3">2015 증감설 계획</th>
				<th rowspan="3">2016 PKG 계획</th>
				<th rowspan="3">비고</th>
			</tr>
			<tr class="pop_tbl_type1">
				<c:forEach var="result_idc" items="${EquipmentIdcModelList}" varStatus="status_idc">
					<th style="background-color:#D5C2EE">
						<c:choose>
							<c:when test="${fn:indexOf(result_idc.team_name, 'ICT') > -1}">ICT</c:when>
							<c:otherwise>Data</c:otherwise>
						</c:choose>
					</th>
				</c:forEach>
			</tr>
			<tr class="pop_tbl_type1">
				<c:forEach var="result_idc" items="${EquipmentIdcModelList}" varStatus="status_idc">
					<th style="background-color:#D5C2EE">${result_idc.idc_name }</th>
				</c:forEach>
			</tr>
			
			<c:set var="var_group3_name" value="${EquipmentModelList[0].group3_name }" />
			<c:set var="var_group1_name" value="${EquipmentModelList[0].group1_name }" />
			<c:forEach var="result_equipment" items="${EquipmentModelList}" varStatus="status_equipment">
				<tr class="pop_tbl_type3">
					<td>${result_equipment.group1_name }</td>
					<td>${result_equipment.group2_name }</td>
					<td>${result_equipment.group3_name }</td>
					<td>${result_equipment.system_name }</td>
					<td>${result_equipment.supply }</td>
					<td style="background-color:#AAFA82">${result_equipment.system_user_name }</td>	
					<c:forEach var="result_idc" items="${EquipmentIdcModelList}" varStatus="status_idc">
						<td>
							<c:forEach var="result_cnt" items="${EquipmentCntModelList}" varStatus="status_cnt">
								<c:if test="${result_equipment.system_seq == result_cnt.system_seq and status_idc.index+1 == result_cnt.idc_seq }">
									${result_cnt.equipment_cnt }
								</c:if>
							</c:forEach>
						</td>
					</c:forEach>
					<td>${result_equipment.job_history }</td>
					<td>${result_equipment.thisYear_job_plan }</td>
					<td>${result_equipment.nextYear_pkg_plan }</td>
					<td>${result_equipment.note }</td>
				</tr>
				
				<!-- 소계(소분류 기준) -->
				<c:if test="${var_group3_name != EquipmentModelList[status_equipment.index+1].group3_name }">
				<c:set var="var_group3_name" value="${EquipmentModelList[status_equipment.index+1].group3_name }" />
				<tr class="pop_tbl_type3">
					<td></td>
					<td></td>
					<td></td>
					<td colspan="2" style="background-color:#f5deb3;">소계</td>
					<td style="background-color:#f5deb3;"></td>
					<c:forEach var="result_idc" items="${EquipmentIdcModelList}" varStatus="status_idc">
						<td style="background-color:#f5deb3;">
							<c:forEach var="result_group3_sum" items="${EquipmentGroup3SumModelList}" varStatus="status_group3_sum">
								<c:if test="${result_equipment.group3_seq == result_group3_sum.group3_seq and status_idc.index+1 == result_group3_sum.idc_seq }"> 
									${result_group3_sum.equipment_group3_sum }
								</c:if>
							</c:forEach>
						</td>	
					</c:forEach>
					<c:forEach var="temp_td_color" begin="1" end="4" step="1">
						<td style="background-color:#f5deb3;"></td>
					</c:forEach>
				</tr>	
				</c:if>
				
				<!-- 소계(대분류 기준) -->
				<c:if test="${var_group1_name != EquipmentModelList[status_equipment.index+1].group1_name }">
				<c:set var="var_group1_name" value="${EquipmentModelList[status_equipment.index+1].group1_name }" />
				<tr class="pop_tbl_type3" style="font-size:13px; background-color:#87cefa;">
					<td colspan="5">${result_equipment.group1_name } 소계</td>
					<td></td>
					<c:forEach var="result_idc" items="${EquipmentIdcModelList}" varStatus="status_idc">
						<td>
							<c:forEach var="result_group1_sum" items="${EquipmentGroup1SumModelList}" varStatus="status_group1_sum">
								<c:if test="${result_equipment.group1_seq == result_group1_sum.group1_seq and status_idc.index+1 == result_group1_sum.idc_seq }">
									${result_group1_sum.equipment_group1_sum }		
								</c:if>
							</c:forEach>
						</td>
					</c:forEach>
					<c:forEach var="temp_td_color" begin="1" end="4" step="1">
						<td style="background-color:#87cefa;"></td>
					</c:forEach>
				</tr>
				</c:if>		
			</c:forEach>
			</table> --%>
		</div>
	</div>
</div>
	</form:form>
</body>
</html>