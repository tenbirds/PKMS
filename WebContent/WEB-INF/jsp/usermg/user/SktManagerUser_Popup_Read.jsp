<%--
/**
 * SKT 담당자 선택 팝업 페이지
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

		doAuthTree();
		var treeData = eval($("#treeScript").val() + ";");
		var rootNode = $("#tree").dynatree("getRoot");
		rootNode.removeChildren(true);
		rootNode.addChild(treeData);

		doTable("scrollTable", "tbl_type11", "1", "0", [ "40", "100", "190" ]);
	});

	/* 팝업 닫기 */
	function fn_popup_close() {
		parent.$('img[id=open_sktuser_popup]').closeDOMWindow();
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
		parent.fn_sktuser_popup_callback(empno, hname);
	}
	
	function fn_init() {
		parent.fn_sktuser_popup_callback("", "");
		fn_popup_close();
	}
	
	function fn_readList(mode) {
		if (mode == "search") {

			var searchKeyword = document.getElementById("searchKeyword");
			searchKeyword.value = ltrim(searchKeyword.value);
			searchKeyword.value = rtrim(searchKeyword.value);

			if (searchKeyword.value == "") {
				alert("검색 할 단어를 입력 하세요.");
				searchKeyword.focus();
				return;
			}
			document.getElementById("indept").value = "";
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

	function onClickTree(key) {
		document.getElementById("indept").value = key;
		document.getElementById("searchKeyword").value = "";
		fn_readList('select');
	}
</script>
</head>

<body>

	<form:form commandName="SktUserModel" method="post" onsubmit="return false;">


		<form:hidden path="empno" />
		<form:hidden path="indept" />
		<form:hidden path="sosok" />
		<form:hidden path="hname" />
		<form:hidden path="treeScript" />


		<!--팝업 전체레이아웃 시작-->
		<div style="width: 780px;" id="pop_wrap">

			<!--팝업 content -->
			<div id="pop_content">

				<!--타이틀 -->
				<h4 class="ly_header">SKT 사용자 선택</h4>

				<fieldset>

					<!--사용자목록 -->
					<div style="width: 350px; height: 460px; float: left; vertical-align: top; border: 1px solid #dcdcdc;">
						<fieldset class="detail_fieldset">
							<legend>조직 목록</legend>
							<div id="tree" style="padding: 0px 0px 0px 0px; width: 328px; height: 440px; float: left; overflow: auto;"></div>
						</fieldset>
					</div>

					<div style="margin-left: 5px; width:380px;height: 460px; float: left; vertical-align: top; border: 1px solid #dcdcdc;">
						<fieldset style="height: 100%;">
							<legend>사용자</legend>

							<div>
								<table class="tbl_type11 fl_left">
									<colgroup>
										<col width="15%">
										<col width="*">
										<col width="25%">
									</colgroup>
									<tr>
										<th>검색</th>
										<td>
										<form:select path="searchCondition" items="${SktUserModel.searchConditionsList}" itemLabel="codeName" itemValue="code" cssClass="new_inp inp_w30 fl_left" /> 
										<form:input path="searchKeyword" cssClass="new_inp inp_w50 fl_left ml05" onkeypress="if(event.keyCode == 13) { fn_readList('search'); return;}" />
										</td>
										<td>
											<div class="btn_line_blue">
												<a href="javascript:fn_readList('search');">조회</a>
											</div>
										</td>
									</tr>
								</table>

								<!--조회버튼 -->
								
							</div>
														
							<!--테이블, 페이지 DIV 시작 -->
							<div class="con_Div2" style="padding-top: 5px;">
								<div class="fakeContainer" style="width:100%; height: 380px;overflow-y:scroll;">

									<table id="scrollTable" class="tbl_type11 w100">
										<thead>
											<tr>
												<th scope="col" class="th_center">선택</th>
												<th scope="col" class="th_center">이름</th>
												<th scope="col" class="th_center">소속</th>
											</tr>
										</thead>
										<tbody id="user_list_area">
											<tr>
												<td colspan="3" style="text-align:center">결과 목록이 없습니다.</td>
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
