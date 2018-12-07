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
	//달력
	doCalendar("search_date_start");
	doCalendar("search_date_end");
	
	//시스템 검색
	init_system_popup();
	
	/* 테이블 스크롤 제어 */
	//doTable(tableId, className, fixHeaderRow, fixLeftCol, widthArr);
// 	doTable("scrollTable", "tbl_type", "1", "0", ["50", "250", "330", "100", "70", "100", "150", "150"]);
});

/* 수정 화면 */
function fn_read(gubun, id) {
	var form = document.getElementById('SolutionReportModel');
	
	if (gubun == "read") {
		form.retUrl.value = "/board/report/SolutionReport_Read";
	} else if (gubun == "view") {
		form.retUrl.value = "/board/report/SolutionReport_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}	

	form.solution_report_seq.value = id;
	form.action = "<c:url value='/board/report/Solution_Report_Read.do'/>";
	form.submit();
}

/* 목록 페이지 이동 */
function fn_readList(pageIndex) {
	
	if(($("#search_date_start").val().replace(/-/g,""))>$("#search_date_end").val().replace(/-/g,"")){
		alert("시작일이 종료일자보다 나중입니다.");
		$("#search_date_end").focus();
		return ;
	}  
	
	document.getElementById('pageIndex').value = pageIndex;
	
	var form = document.getElementById('SolutionReportModel');
	form.action = "<c:url value='/board/report/Solution_Report_ReadList.do'/>";
	form.submit();
}
function fn_searchList(){
	
	if(!validate())
		return;
	
	fn_readList(1);
}
function validate(){
	
	if($("#search_date_start").val()!=""){
		if($("#search_date_end").val()==""){
			alert("검색 시작일을 선택해주세요.");
			$("#search_date_end").focus();
			return false;
		}
	}
	if($("#search_date_end").val()!=""){
		if($("#search_date_start").val()==""){
			alert("검색 종료일을 선택해주세요.");
			$("#search_date_start").focus();
			return false;
		}
	}
	
	return true;
}

//시스템 선택 결과 처리
function fn_system_popup_callback(system_key, system_name){
	$("#search_system_seq").val(system_key);
	$("#search_system_name").val(system_name);
}
function fn_sktuser_popup_callback(user_id, user_name){
	$("#search_system_user_id").val(user_id);
	$("#search_system_user_name").val(user_name);
}
function fn_bp_popup_callback(bp_num, bp_name){
	$("#search_bp_name").val(bp_name);
}
</script>
</head>

<body>
	<form:form commandName="SolutionReportModel" method="post" onsubmit="return false;">
		<form:hidden path="solution_report_seq"/>
		<form:hidden path="retUrl" />

		<!-- 페이징 -->
		<form:hidden path="pageIndex"/>

	
		<!--조회 -->
		<div class="new_con_Div32">
			<div class="new_search fl_wrap">
				<table class="new_sear_table1 fl_wrap">
					<tr>
						<th>시스템</th>
						<td>
							<form:hidden path="search_system_seq" />
							<form:input path="search_system_name" readonly="true" class="fl_left" />
							<span class="fl_left mg03"><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup" align="absmiddle" /></span>
						</td>
						<th>내용 분류</th>
						<td>
							<form:select path="search_content_gubun"  items="${SolutionReportModel.search_content_gubunList}" itemLabel="codeName" itemValue="code"/>
						</td>
						<th>
							제목
						</th>
						<td>
							<form:input path="search_title" maxlength="50"/>
						</td>
					</tr>
					<tr>
						<th>
							등록일
						</th>
						<td>
							<form:input path="search_date_start" readonly="true"  class="inp_w30 fl_left" size="7"/>
							<span class="fl_left mg05"> ~ </span> 
							<form:input path="search_date_end" readonly="true"  class="inp_w30 fl_left" size="7"/>
						</td>
						<th>
							등록자
						</th>
						<td>
							<form:input path="search_reg_name" maxlength="50"/>
						</td>
						<th>
							등록팀
						</th>
						<td>
							<form:input path="search_reg_sosok" maxlength="50"/>
						</td>
					</tr>
				</table>
				<!--조회버튼 -->
				<div class="new_btn_sear"><a href="javascript:fn_searchList()">조회</a></div>
			</div>
		</div>
	
		<!--버튼위치
		<div class="btn_location">
			<span>
				<a href="javascript:fn_read('read', '')">
					<img src="/images/btn_new.gif" alt="신규" />
				</a>
			</span>
		</div>
		 -->
		 
		<div class="con_Div2 con_area mt20">
			<div class="con_detail">
				<!--버튼위치 -->
				<div class="con_top_btn">
					<sec:authorize ifAnyGranted="ROLE_ADMIN">
						<span><a href="javascript:fn_read('read', '')">신규</a></span>
					</sec:authorize>
				</div>
				
				<div style="height:500px">
					<table id="scrollTable" class="tbl_type12">
						<colgroup>
							<col width="5%">
							<col width="25%">
							<col width="20%">
							<col width="10%">
							<col width="10%">
							<col width="10%">
							<col width="10%">
							<col width="10%">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">번호</th>
								<th scope="col">시스템</th>
								<th scope="col">제목</th>
								<th scope="col">내용분류</th>
								<th scope="col">완료여부</th>
								<th scope="col">등록자</th>
								<th scope="col">등록팀</th>
								<th scope="col">등록일</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty solutionReportList}">
									<c:forEach var="srList" items="${solutionReportList}">
										<tr>
											<td>
												<c:out value="${srList.solution_report_seq}"/>
											</td>
											<td align="left">
												<c:out value="${srList.system_name}"/>
											</td>
											<td align="left">
												<a href="javascript:fn_read('view','<c:out value="${srList.solution_report_seq}"/>')">
													<c:out value="${fn:substring(srList.title, 0, 40)}"/>
													<c:out value="${fn:length(srList.title) > 40 ? '...' : ''}"/>
												</a>
											</td>
											<td>
												<c:out value="${srList.content_gubun}"/>
											</td>
											<td>
												<c:choose>
													<c:when test="${srList.result_yn == 'Y'}">
														완료
													</c:when>
													<c:otherwise>
														미완료
													</c:otherwise>
												</c:choose>
											</td>
											<td><c:out value="${srList.reg_name}"/></td>
											<td><c:out value="${srList.reg_sosok}"/></td>
											<td><c:out value="${srList.reg_date}"/></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr><td colspan="8">등록된 게시글이 없습니다.</td></tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
				<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
			</div>
		</div>
	</form:form>
</body>
</html>