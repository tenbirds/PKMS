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
	
// 	init_sktuser_popup();
// 	init_system_popup();
	
	init_sktuser_popup_detail();
	init_system_popup_detail();
	/* 테이블 스크롤 제어 */
	//doTable(tableId, className, fixHeaderRow, fixLeftCol, widthArr);
// 	doTable("scrollTable", "tbl_type11", "1", "0", ["100", "100", "50", "85", "90", "85", "90", "290", "100", "100", "100"]);
});

/* 수정 화면 */
function fn_read(gubun, id) {
	var form = document.getElementById('NewpncrModel');
	
	if (gubun == "read") {
		form.retUrl.value = "/newpncrmg/Newpncr_Read";
	} else if (gubun == "view") {
		form.retUrl.value = "/newpncrmg/Newpncr_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}
	
	form.new_pn_cr_seq.value = id;
	form.action = "<c:url value='/newpncrmg/Newpncr_Read.do'/>";
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
	
	var form = document.getElementById('NewpncrModel');
	form.action = "<c:url value='/newpncrmg/Newpncr_ReadList.do'/>";
	form.submit();
}
/* SKT 사용자 선택 결과 처리 */
function fn_sktuser_popup_callback(user_id, user_name){
	$("#search_reg_user").val(user_id);
	$("#search_reg_name").val(user_name);
// 	alert("사람 ID = " + $("#search_reg_user").val());
// 	alert("사람 이름 = " + $("#search_reg_name").val());
}

/* SKT 부서 선택 결과 처리 */
function fn_sktuser_popup_detail_callback(indept, indept_name){
	$("#search_indept").val(indept);
	$("#search_reg_name").val(indept_name);
	alert("부서를 기준으로 검색합니다.");
// 	alert("부서 번호 = " + $("#search_indept").val());
// 	alert("부서 이름 = " + $("#search_reg_name").val());
}
//시스템 선택 결과 처리
function fn_system_popup_callback(system_key, system_name, gubun){
	$("#search_system_seq").val(system_key);
	$("#search_system_name").val(system_name);
	$("#system_search_gubun").val(gubun);
	if(gubun == "1"){
		alert("대분류를 기준으로 검색합니다.");	
	}
	if(gubun == "2"){
		alert("중분류를 기준으로 검색합니다.");	
	}
	if(gubun == "3"){
		alert("소분류를 기준으로 검색합니다.");	
	}
	if(gubun == "4"){
		alert("시스템을 기준으로 검색합니다.");	
	}
// 	alert("구분"+$("#system_search_gubun").val());
// 	alert("시스템 키"+$("#search_system_seq").val());
// 	alert("시스템 이름"+$("#search_system_name").val());
}
//보완적용내역 다운로드
function fn_list_excelDownload() {
	 
	doSubmit("NewpncrModel", "/newpncrmg/Newpncr_ExcelDownload.do", "fn_callback_file_download_newpncr");
	 
}
//템플릿 다운로드(템플릿 다운로드)

function fn_callback_file_download_newpncr(data) {
	var file_name = $("input[id=param1]").val();
	downloadFile(file_name, file_name, "");
}
</script>
</head>

<body>
	<form:form commandName="NewpncrModel" method="post" onsubmit="return false;">
	<form:hidden path="new_pn_cr_seq"/>
	<!-- 페이징 -->
	<form:hidden path="pageIndex"/>
	<!-- return URL -->
	<form:hidden path="retUrl" />
	<!--조회 -->
	<div class="new_con_Div32 new_pkms_sysSearchForm">
		
		<div class="new_search fl_wrap">
			<table class="new_sear_table1 fl_wrap">
				<colgroup>
					<col width="6%">
					<col width="27%">
					<col width="5%">
					<col width="27%">
					<col width="4%">
					<col width="12%">
					<col width="4%">
					<col width="15%">
				</colgroup>
				<tbody>
				<tr>
					<th >등록일자</th>
					<td>						
						<form:input path="search_reg_date_start" class="inp_w30 fl_left" readOnly="true" />
						<span class="fl_left mg05"> ~ </span> 
						<form:input path="search_reg_date_end" class="inp_w30 fl_left" readOnly="true" />
					</td>
					<th>시스템</th>
					<td>
						<form:hidden path="system_search_gubun" />
						<form:hidden path="search_system_seq" />
						<form:input path="search_system_name" readonly="true" class="inp_w70 fl_left"/>
						<span class="fl_left mg02"><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup" align="absmiddle" /></span>
					</td>
					<th class="w1">유형</th>
					<td>
						<form:select path="searchNew_pncr_gubun" items="${NewpncrModel.searchNew_pncr_gubunList}" itemLabel="codeName" itemValue="code"></form:select>
					</td>
				</tr>
				<tr>
					<th>등록자</th>
					<td>
						<form:hidden path="search_reg_user"/>
						<form:hidden path="search_indept"/>
						<form:input path="search_reg_name" readonly="true" class="inp_w70 fl_left"/>
						<span class="fl_left mg02"><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_sktuser_popup" align="absmiddle" /></span>
					</td>
					<th class="w1">검색</th>
					<td>
						<form:select path="searchCondition" items="${NewpncrModel.searchConditionsList}" itemLabel="codeName" itemValue="code"></form:select>
						<form:input path="searchKeyword" cssClass="inp_w60" onkeypress="if(event.keyCode == 13) { fn_readList(1); return;}"/>
					</td>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
					<th class="w1">조회</th>
					<td>
						<form:select path="searchRoleCondition" items="${NewpncrModel.searchRoleConditionList}" itemLabel="codeName" itemValue="code"></form:select>
					</td>
					<th class="w1">상태</th>
					<td>
						<form:select path="search_state" items="${NewpncrModel.search_stateList}" itemLabel="codeName" itemValue="code"></form:select>
					</td>
					</sec:authorize>
				</tr>
				</tbody>
			</table>
			<!--조회버튼 -->
			<div class="new_btn_sear"><a href="javascript:fn_readList(1)">검색</a></div>
		</div>
	
	
		<br>
		
		<div class="con_Div2 con_area">	
			<div class="con_detail fl_wrap">
				<div class="con_top_btn fl_right">
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
						<span class="con_top_btn">
							<a href="javascript:fn_read('read', '')">신규</a>
							<a href="javascript:fn_list_excelDownload();">엑셀 다운로드</a>
						</span>
					</sec:authorize>
				</div>
			<!--테이블, 페이지 DIV 시작 -->
				
				<!-- 스크롤 DIV -->
				<div class="fakeContainer" style="width:100%;">
					<!--테이블 -->
					<table class="tbl_type12">
						<colgroup>
							<col width="5%">
							<col width="6%">
							<col width="3%">
							<col width="10%">
							<col width="11%">
							<col width="11%">
							<col width="11%">
							<col width="22%">
							<col width="5%">
							<col width="8%">
							<col width="8%">
						</colgroup>
						<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">상태</th>
							<th scope="col" class="th_height">유형</th>
							<th scope="col">대분류</th>
							<th scope="col">중분류</th>
							<th scope="col">소분류</th>
							<th scope="col">시스템</th>
							<th scope="col">제목</th>
							<th scope="col" class="th_height">등록자</th>
							<th scope="col">등록팀</th>
							<th scope="col">등록일</th>
						</tr>
						</thead>
						<tbody>
						<c:choose>
							<c:when test="${not empty newpncrModelList}">
								<c:forEach var="newpncrModelList" items="${newpncrModelList}" varStatus="status">
									<tr>
										<td><c:out value="${newpncrModelList.new_pn_cr_no}" /></td>
										<td><c:out value="${newpncrModelList.state}" /></td>
										<td><c:out value="${newpncrModelList.new_pncr_gubun}" /></td>
										<td><c:out value="${newpncrModelList.group1_name}" /></td>
										<td><c:out value="${newpncrModelList.group2_name}" /></td>
										<td><c:out value="${newpncrModelList.group3_name}" /></td>
										<td><c:out value="${newpncrModelList.system_name}" /></td>
										<td style="text-align: left;">
											<a href="javascript:fn_read('view', '<c:out value="${newpncrModelList.new_pn_cr_seq}"/>')">
												<c:out value="${fn:substring(newpncrModelList.title, 0, 20)}"/>
												<c:out value="${fn:length(newpncrModelList.title) > 20 ? '..' : ''}"/>
											</a>
										</td>
										<td><c:out value="${newpncrModelList.reg_name}" /></td>
										<td><c:out value="${newpncrModelList.sosok}" /></td>
										<td><c:out value="${newpncrModelList.reg_date}" /></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr><td colspan="11">등록된 신규/보안/개선 목록이 없습니다.</td></tr>
							</c:otherwise>
						</c:choose>
						</tbody>
					</table>
				</div>
				
				<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
				
			</div>
		</div>	
	</div>
	</form:form>
</body>
</html>