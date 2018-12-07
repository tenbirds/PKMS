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
	
	doCalendar("request_search_date_start");
	doCalendar("request_search_date_end");
	
	init_sktmanageruser_popup();
	init_system_popup();
	init_bp_popup();
	
	/* 테이블 스크롤 제어 */
	//doTable(tableId, className, fixHeaderRow, fixLeftCol, widthArr);
	doTable("scrollTable", "tbl_type", "1", "0", ["50", "130", "300", "150", "100", "150", "100", "100", "100"]);
});

/* 수정 화면 */
function fn_read(id) {
	var form = document.getElementById('requestModel');
	
	form.request_seq.value = id;
	form.action = "<c:url value='/board/request/Request_Read.do'/>";
	form.submit();
}

/* 목록 페이지 이동 */
function fn_readList(pageIndex) {
	
	if(($("#request_search_date_start").val().replace(/-/g,""))>$("#request_search_date_end").val().replace(/-/g,"")){
		alert("시작일이 종료일자보다 나중입니다.");
		$("#request_search_date_end").focus();
		return ;
	}  
	
	
	document.getElementById('pageIndex').value = pageIndex;
	
	var form = document.getElementById('requestModel');
	form.action = "<c:url value='/board/request/Request_ReadList.do'/>";
	form.submit();
}
function fn_search(){
	alert("요청해야함");
	return;
}
function fn_searchList(){
	
	if(!validate())
		return;
	
	fn_readList(1);
}
function validate(){
	
	if($("#request_search_date_start").val()!=""){
		if($("#request_search_date_end").val()==""){
			alert("요청 이용 종료일을 선택해주세요.");
			$("#request_search_date_end").focus();
			return false;
		}
	}
	if($("#request_search_date_end").val()!=""){
		if($("#request_search_date_start").val()==""){
			alert("요청 이용 종료일을 선택해주세요.");
			$("#request_search_date_start").focus();
			return false;
		}
	}
	
	return true;
}
function fn_callback_update(){
	fn_readList(1);
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
	<form:form commandName="requestModel" method="post" onsubmit="return false;">
	<form:hidden path="request_seq"/>
	<!-- 페이징 -->
	<form:hidden path="pageIndex"/>

	
		<!--조회 -->
		<div class="search">
			<table class="sear_table1">
				<tr>
					<th>요청일자</th>
					<td>
						<form:input path="request_search_date_start" readonly="true" size="7"/> ~ 
						<form:input path="request_search_date_end" readonly="true" size="7"/>
					</td>
					<th>요청 상태</th>
					<td>
						<form:select path="search_request_state" items="${requestModel.search_request_stateList}" itemLabel="codeName" itemValue="code"/>
					</td>
					<th>제목</th>
					<td>
						<form:input path="searchKeyword" cssClass="txt" onkeypress="if(event.keyCode == 13) { fn_readList(1); return;}"/>
					</td>
				</tr>
				<tr>
					<th>시스템</th>
					<td>
						<form:hidden path="search_system_seq" />
						<form:input path="search_system_name" readonly="true" />
						<span><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup" align="absmiddle" /></span>
					</td>
					<th>협력업체</th>
					<td>
					<sec:authorize ifAnyGranted = "ROLE_BP">
						<form:input path="search_bp_name" value="${requestModel.session_user_group_name}" readonly="true"/>
					</sec:authorize>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR" >
						<form:input path="search_bp_name" readonly="true"/>
						<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup" align="absmiddle"></span>
					</sec:authorize>
					</td>
					<th>SKT 승인매니져</th>
					<td>
					<sec:authorize ifAnyGranted = "ROLE_BP">
						<form:hidden path="search_system_user_id"/>
						<form:input path="search_system_user_name" readonly="true"/>
						<span><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_sktuser_popup" align="absmiddle" /></span>
					</sec:authorize>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
 						<form:checkbox path="searchAllManager" label="전체"/>
					</sec:authorize>
					</td>
				</tr>
			</table>

			<!--조회버튼 -->
			<div class="btn_sear"><a href="javascript:fn_searchList()"></a></div>
		</div>
	
		<!--버튼위치 -->
		<div class="btn_location">
			<sec:authorize ifAnyGranted = "ROLE_BP, ROLE_MANAGER">
				<span><a href="javascript:fn_read('')"><img src="/images/btn_new.gif" alt="신규" /></a></span>
			</sec:authorize>
		</div>
		
		<div class="con_Div2">
			<div class="fakeContainer" style="width:1200px;height:500px">
				<table id="scrollTable" class="tbl_type" border="1">
					<thead>
					<tr>
						<th scope="col">상태</th>
						<th scope="col">시스템</th>
						<th scope="col">제목</th>
						<th scope="col">SKT 승인 담당자</th>
						<th scope="col">협력업체</th>
						<th scope="col">검증 분류</th>
						<th scope="col">이용시작일</th>
						<th scope="col">이용종료일</th>
						<th scope="col">등록자</th>
					</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty requestModelList}">
								<c:forEach var="requestModelList" items="${requestModelList}">
									<tr>
										<td><c:out value="${requestModelList.request_state}"/></td>
										<td align="left"><c:out value="${requestModelList.system_name}"/></td>
										<td align="left">
											<a href="javascript:fn_read('<c:out value="${requestModelList.request_seq}"/>')">
											<c:out value="${fn:substring(requestModelList.goal, 0, 20)}"/>
											<c:out value="${fn:length(requestModelList.goal) > 20 ? '...' : ''}"/>
										</a>
										</td>
										<td><c:out value="${requestModelList.system_user_id}"/></td>
										<td><c:out value="${requestModelList.ptn_name1}"/></td>
										<td><c:out value="${requestModelList.request_class}"/></td>
										<td><c:out value="${requestModelList.request_date_start}"/></td>
										<td><c:out value="${requestModelList.request_date_end}"/></td>
<%-- 										<td><c:out value="${requestModelList.request_name}"/></td> --%>
										<td><c:out value="${requestModelList.reg_user_name}"/></td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr><td colspan="10">등록된 검증센터 이용 요청이 없습니다.</td></tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
		</div>
	</form:form>
</body>
</html>