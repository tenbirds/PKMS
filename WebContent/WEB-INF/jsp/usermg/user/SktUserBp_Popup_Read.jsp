<%--
/**
 * 협력업체 등록 시 사용되는 SKT 담당자 선택 팝업 페이지
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

<link rel="stylesheet" type="text/css" href="/css/dynatree/ui.dynatree.css">
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<link rel="stylesheet" type="text/css" href="/css/supertable/supertable.css" />

<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.dynatree.js"></script>


<style>
#scrollTable tr:hover {
	background-color: #eee;
}

#scrollTable td:hover {
	cursor: pointer;
}
</style>

<script type="text/javaScript" defer="defer">
	var empno = "";
	var hname = "";
	var sosok = "";

	$(document).ready(function($) {
		doTable("scrollTable", "tbl_type", "1", "0", [ "40", "120", "270" ]);
	});

	/* 팝업 닫기 */
	function fn_popup_close() {
		parent.$('img[id=open_sktuser_bp_popup]').closeDOMWindow();
	}

	function fn_confirm() {
		if (empno != "" && !(typeof empno == "undefined")) {
			$("#empno").val(empno);
			$("#hname").val(hname);
			$("#sosok").val(sosok);
			doSubmit("SktUserModel", "/usermg/user/SktUser_Ajax_ReadEmpno.do",
			"fn_callback_confirm");
		}
		fn_popup_close();
	}

	function fn_callback_confirm(data){
		empno = $("#param1").val();
		parent.fn_sktuser_bp_popup_callback(empno, hname);
	}
	
	function fn_init() {
		parent.fn_sktuser_bp_popup_callback("", "");
		fn_popup_close();
	}
	
	function fn_readList(mode) {

		var searchKeyword = document.getElementById("searchKeyword");
		searchKeyword.value = ltrim(searchKeyword.value);
		searchKeyword.value = rtrim(searchKeyword.value);

		if (searchKeyword.value == "") {
			alert("검색 할 단어를 입력 하세요.");
			searchKeyword.focus();
			return;
		}

		doSubmit("SktUserModel", "/usermg/user/SktManagerUser_Ajax_ReadList.do",
				"fn_callback_readList");

	}

	function fn_callback_readList(data) {
		$("#user_list_area").html(data);
		$("#user_list_area tr").click(function() {
			empno = $(this).find("input[type=radio]").attr("value");
			hname = $("#hidden_hname" + empno).attr("value");
			sosok = $("#hidden_sosok" + empno).attr("value");
			if (empno != "" && !(typeof empno == "undefined")) {
				document.getElementById("radio_empno" + empno).checked = true;
			}
		});
	}

</script>
</head>

<body>

	<form:form commandName="SktUserModel" method="post" onsubmit="return false;">

		<form:hidden path="empno" />
		<form:hidden path="sosok" />
		<form:hidden path="hname" />
		<form:hidden path="treeScript" />


		<!--팝업 전체레이아웃 시작-->
		<div style="width: 520px;" id="pop_wrap">

			<!--팝업 content -->
			<div id="pop_content">

				<!--타이틀 -->
				<h4 class="ly_header">SKT 사용자 선택</h4>

				<fieldset>

					<div style="margin-left: 5px; height: 460px; float: left; vertical-align: top; border: 1px solid #dcdcdc;">
						<fieldset style="height: 100%;">
							<legend>사용자</legend>
							<div class="search">
								<table class="sear_table1" style="width: 390px;">
									<colgroup>
									<col width="15%">
									<col width="15%">
									<col width="*">
									<col width="15%">
									</colgroup>
									<tr>
										<th style="width: 60px;">검색</th>
										<td>
											<form:select path="searchCondition" items="${SktUserModel.searchConditionsList}" itemLabel="codeName" itemValue="code" />
										</td>
										<td>	
											<form:input path="searchKeyword" cssClass="txt inp_w90" onkeypress="if(event.keyCode == 13) { fn_readList('search'); return;}" />
										</td>
									</tr>
								</table>

								<!--조회버튼 -->
								<div class="btn_line_blue mt03">
									<a href="javascript:fn_readList('search');">조회</a>
								</div>
							</div>
							<!--테이블, 페이지 DIV 시작 -->
							<div class="con_Div2" style="padding-top:5px;">
								<div class="fakeContainer" style="width: 450px; height: 380px">

									<table id="scrollTable" class="tbl_type" border="1">
										<thead>
											<tr>
												<th scope="col">선택</th>
												<th scope="col">이름</th>
												<th scope="col">소속</th>
											</tr>
										</thead>
										<tbody id="user_list_area">
											<tr>
												<td colspan="3" align="center">결과 목록이 없습니다.</td>
											</tr>
										</tbody>
									</table>
								</div>

							</div>


						</fieldset>
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
