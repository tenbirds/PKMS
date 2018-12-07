<%--
/**
 * 국사 선택 팝업 페이지
 * 
 * @author : skywarker
 * @Date : 2012. 5. 11.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<html>
<head>

<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />

<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script>

<style>

#idc_table tr:hover {
	background-color: #eee;
}

#idc_table td:hover {
	cursor: pointer;
}
</style>

<script type="text/javaScript" defer="defer">

	var idc_seq = "";
	var idc_name = "";

	$(document).ready(function($) {
		
		doTable("idc_table", "tbl_type", "1", "0", [ "50", "150", "300"]);
		
		$('#idc_table tr').click(function() {
			idc_seq = $(this).find("input[type=radio]").attr("value");
			idc_name = $("#hidden_idc_name"+idc_seq).attr("value");
			if (idc_seq != "" && !(typeof idc_seq == "undefined")) {
				document.getElementById("radio_idc_seq"+idc_seq).checked = true;
			}
		});
	});

	/* 팝업 닫기 */
	function fn_popup_close() {
		parent.$('img[id=open_idc_popup]').closeDOMWindow();
	}

	function fn_confirm() {
		if (idc_seq != "" && !(typeof idc_seq == "undefined")) {
			parent.fn_idc_popup_callback(idc_seq, idc_name);
		}
		fn_popup_close();
	}
	
	/* 목록 페이지 이동 */
	function fn_readList(pageIndex) {
		// 검색어없이 검색 시 전체 검색 
		document.getElementById("pageIndex").value = pageIndex;

		var form = document.getElementById("IdcModel");
		form.action = "<c:url value='/sys/idc/Idc_Popup_ReadList.do'/>";
		form.submit();
	}
</script>
</head>

<body>

	<form:form commandName="IdcModel" method="post" onsubmit="return false;">
		<!-- 페이징 -->
		<form:hidden path="pageIndex" />

		<!-- return URL -->
		<form:hidden path="retUrl" />

		<form:hidden path="idc_seq" />

		<!--팝업 전체레이아웃 시작-->
		<div style="width: 560px; top: 20px; left: 20px" id="pop_wrap">

			<!--팝업 content -->
			<div id="pop_content">
				<form method="post" action="">

					<!--타이틀 -->
					<h4 class="ly_header">국사 선택</h4>

					<fieldset>

						<!--조회영역 -->
						<div class="pop_search">
							<table class="sear_table1">
								<tr>
									<th>검색</th>
									<td><form:select path="searchCondition" items="${IdcModel.searchConditionsList}" itemLabel="codeName" itemValue="code" /> <form:input path="searchKeyword" cssClass="txt" onkeypress="if(event.keyCode == 13) { fn_readList(1);}" /></td>
								</tr>
							</table>

							<!--조회버튼 -->
							<div class="btn_sear">
								<a href="javascript:fn_readList(1);"></a>
							</div>
						</div>

						<br /> <br />

						<!--테이블 -->
						<div style="height: 400px; vertical-align: top;">
							<div class="fakeContainer" style="width: 95%; height: 400px;">
						
							<table id="idc_table" class="tbl_type" border="1">
								<thead>
									<tr>
										<th style="width: 50px;" scope="col">선택</th>
										<th style="width: 150px;" scope="col">지역코드</th>
										<th style="width: 300px;" scope="col">국사명</th>
									</tr>
								</thead>
								<c:if test="${IdcModel.totalCount == 0}">
									<tr>
										<td colspan="7">검색 결과가 없습니다.</td>
									</tr>
								</c:if>
								<c:forEach var="result" items="${IdcModelList}" varStatus="status">
									<tr>
										<td>
											<input type="radio" id="radio_idc_seq<c:out value="${result.idc_seq}" />" name="radio_idc_seq" value="${result.idc_seq}">
											<input type="hidden" id="hidden_idc_name<c:out value="${result.idc_seq}" />" name="hidden_idc_name<c:out value="${result.idc_seq}" />" value="${result.idc_name}">
										</td>
										<td><c:out value="${result.location_code}" />&nbsp;</td>
										<td><c:out value="${result.idc_name}" />&nbsp;</td>
									</tr>
								</c:forEach>

								<tbody>

								</tbody>
							</table>
							</div>
						</div>
						
						<!--상단닫기버튼 -->
						<a class="close_layer" href="javascript:fn_popup_close();"> <img alt="닫기" src="/images/pop_btn_close2.gif" width="15" height="14"></a>
					</fieldset>
				</form>

				<!--하단 닫기버튼 -->
				<div id="pop_footer">
					<a href="javascript:fn_confirm();"> <img alt="확인" src="/images/btn_identify.gif" ></a>
				</div>

			</div>


			<span class="shadow shadow2"> </span> <span class="shadow shadow3"> </span> <span class="shadow shadow4"> </span>
		</div>
		<!--팝업 전체레이아웃 "끝-->
	</form:form>
</body>
</html>
