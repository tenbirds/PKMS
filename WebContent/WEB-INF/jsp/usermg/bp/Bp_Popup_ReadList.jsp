<%--
/**
 * 협력업체 선택 팝업 페이지
 * 
 * @author : skywarker
 * @Date : 2012. 4. 19.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<link rel="stylesheet" type="text/css" href="/css/supertable/supertable.css" />

<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>

<style>
#scrollTable tr:hover {
	background-color: #eee;
}

#scrollTable td:hover {
	cursor: pointer;
}
</style>

<script type="text/javaScript" defer="defer">
	var bp_num = "";
	var bp_name = "";

	$(document).ready(function($) {
		doTable("scrollTable", "tbl_type", "1", "0", [ "50", "235", "100", "200", "120" ]);
       	$('#bp_list_area tr').click(function() {
       		bp_num = $(this).find("input[type=radio]").attr("value");
       		bp_name = $("#hidden_bp_name" + bp_num).attr("value");
       		if (bp_num != "" && !(typeof bp_num == "undefined")) {
       			document.getElementById("radio_bp_num" + bp_num).checked = true;
       		}
       	});
	});


	/* 팝업 닫기 */
	function fn_popup_close() {
		parent.$('img[id=open_bp_popup<c:if test="${not empty BpModel}"><c:out value='${BpModel.cur_bp_idx}' default='' /></c:if>]').closeDOMWindow();
	}

	function fn_confirm() {
		if (bp_num != "" && !(typeof bp_num == "undefined")) {
			parent.fn_bp_popup_callback(bp_num, bp_name<c:if test="${not empty BpModel}">, "<c:out value='${BpModel.cur_bp_idx}' default='' />"</c:if>);
		}
		fn_popup_close();
	}

	function fn_init() {
		parent.fn_bp_popup_callback("", ""<c:if test="${not empty BpModel}">, "<c:out value='${BpModel.cur_bp_idx}' default='' />"</c:if>);
		fn_popup_close();
	}
	
	/* 목록 페이지 이동 */
	function fn_readList(pageIndex) {
		// 검색어없이 검색 시 전체 검색 
		document.getElementById("pageIndex").value = pageIndex;

		var form = document.getElementById("BpModel");
		form.action = "<c:url value='/usermg/bp/Bp_Popup_ReadList.do'/>";
		form.submit();
	}
</script>
</head>

<body>

	<form:form commandName="BpModel" method="post" onsubmit="return false;">
		<!-- 페이징 -->
		<form:hidden path="pageIndex" />

		<!-- return URL -->
		<form:hidden path="retUrl" />

		<form:hidden path="bp_num" />
		<form:hidden path="cur_bp_idx" />

		<!--팝업 전체레이아웃 시작-->
		<div style="width: 780px;" id="pop_wrap">

			<!--팝업 content -->
			<div id="pop_content">

				<!--타이틀 -->
				<h4 class="ly_header">업체 선택</h4>

				<fieldset>

					<div style="height: 425px; padding: 5px; float: left; vertical-align: top; border: 1px solid #dcdcdc;">

						<!--조회영역 -->
						<div class="pop_search" style="width: 730px;">
							<table class="sear_table1">
								<tr>
									<th>검색</th>
									<td><form:select path="searchCondition" items="${BpModel.searchConditionsList}" itemLabel="codeName" itemValue="code" /> <form:input path="searchKeyword" cssClass="txt" onkeypress="if(event.keyCode == 13) { fn_readList(1);}" /></td>
								</tr>
							</table>

							<!--조회버튼 -->
							<div class="btn_sear">
								<a href="javascript:fn_readList(1);"></a>
							</div>
						</div>

						<!--테이블, 페이지 DIV 시작 -->
						<div class="con_Div2" style="padding-top: 5px; width:740px;">

							<div class="fakeContainer" style="width: 725px; height: 365px;">
								<table id="scrollTable" class="tbl_type" border="1">
									<thead>
										<tr>
											<th scope="col">선택</th>
											<th scope="col">업체명</th>
											<th scope="col">사업자등록번호</th>
											<th scope="col">주소</th>
											<th scope="col">전화번호</th>
										</tr>
									</thead>
									<tbody id="bp_list_area">
										<c:if test="${BpModel.totalCount == 0}">
											<tr>
												<td colspan="5">검색 결과가 없습니다.</td>
											</tr>
										</c:if>
										<c:forEach var="result" items="${BpModelList}" varStatus="status">
											<tr>
												<td><input type="radio" id="radio_bp_num<c:out value="${result.bp_num}" />" name="radio_bp_num" value="${result.bp_num}"> <input type="hidden" id="hidden_bp_name<c:out value="${result.bp_num}" />"
													name="hidden_bp_name<c:out value="${result.bp_num}" />" value="${result.bp_name}"></td>
												<td><c:out value="${fn:substring(result.bp_name,0,18)}" /> <c:out value="${fn:length(result.bp_name) > 18 ? '...' : ''}" /></td>
												<td><c:out value="${result.bp_num}" /></td>
												<td><c:out value="${fn:substring(result.bp_addr,0,18)}" /> <c:out value="${fn:length(result.bp_addr) > 18 ? '...' : ''}" /></td>
												<td><c:out value="${result.bp_tel1}" />&nbsp;<c:out value="${result.bp_tel2}" />&nbsp;<c:out value="${result.bp_tel3}" /></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</fieldset>

				<!--상단닫기버튼 -->
				<a class="close_layer" href="javascript:fn_popup_close();"> <img alt="닫기" src="/images/pop_btn_close2.gif" width="15" height="14"></a>

				<!--하단 닫기버튼 -->
				<div id="pop_footer">
					<a href="javascript:fn_confirm();"> <img alt="확인" src="/images/btn_select.gif"></a>
					&nbsp;
					<a href="javascript:fn_init();"> <img alt="확인" src="/images/btn_initialization.gif"></a>
				</div>

			</div>


			<span class="shadow shadow2"> </span> <span class="shadow shadow3"> </span> <span class="shadow shadow4"> </span>
		</div>
		<!--팝업 전체레이아웃 "끝-->
	</form:form>
</body>
</html>
