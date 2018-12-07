<%--
/**
 * 권한 관리 페이지
 * 
 * @author : skywarker
 * @Date : 2012. 4. 24.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pkms.org.model.OrgModel"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="/css/dynatree/ui.dynatree.css" />
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />

<script type="text/javascript" src="/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.dynatree.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script>



<script type="text/javaScript" defer="defer">
	
	var m_mode = "";
	
	$(document).ready(
			function($) {
				doAuthTree();
				fn_readTree();
// 				doTable("scrollTable", "tbl_type", "1", "0", [ "180", "215","80", "80", "80" ]);
			});

	function fn_read() {
		var form = document.getElementById("AuthUserModel");
		form.action = "<c:url value='/usermg/auth/Auth_Read.do'/>";
		form.submit();
	}

	function fn_readTree() {
		doSubmit("AuthUserModel",
				"<c:url value='/usermg/auth/AuthUser_Ajax_ReadTree.do'/>",
				"fn_callback_readTree");
	}

	function fn_callback_readTree(data) {
		try {
			var result = $("input[id=param1]").val();
			var treeData = eval(result + ";");
			var rootNode = $("#tree").dynatree("getRoot");
			rootNode.removeChildren(true);
			rootNode.addChild(treeData);
		} catch (e) {
			alert("시스템 오류가 발생 하여 조직 목록 조회에 실패 하였습니다.");
		}
	}

	function fn_readList(mode) {
		m_mode = mode;
		if (mode == "search") {
			
			var searchKeyword = document.getElementById("searchKeyword");
			searchKeyword.value = ltrim(searchKeyword.value);
			searchKeyword.value = rtrim(searchKeyword.value);

			if (searchKeyword.value == "") {
				alert("검색 할 단어를 입력 하세요.");
				searchKeyword.focus();
				return;
			}
			document.getElementById("auth_user_group_id").value = "";
		}
		doSubmit("AuthUserModel",
				"<c:url value='/usermg/auth/AuthUser_Ajax_ReadList.do'/>",
				"fn_callback_readList");

	}

	function fn_callback_readList(data) {
		$("#auth_user_list_area").html(data);
	}

	function onActivateTree(key) {
		document.getElementById("auth_user_group_id").value = key;
		document.getElementById("searchKeyword").value = "";
		fn_readList('select');
	}

	function fn_update() {

		//if (confirm("정말로 저장 하시겠습니까?")) {
			doSubmit4File("AuthUserModel",
					"<c:url value='/usermg/auth/AuthUser_Ajax_Update.do'/>",
					"fn_callback_update", "적용되었습니다.");
		//}
	}

	function fn_callback_update(data) {
		fn_readList(m_mode);
	}
</script>

</head>
<body>
	<form:form commandName="AuthUserModel" method="post" onsubmit="return false;">

		<form:hidden path="option" value="<%=OrgModel.ORG_OPTION.GROUP_ONLY%>" />
		<form:hidden path="auth_user_group_id" />

		<!--탭, 테이블, 페이지 DIV 시작 -->
		<div class="con_Div2">

			<!--탭 div 시작 -->
			<!-- <div class="con_Div6">
				<ul class="m_tab">
					<li class="m_tab5_on"></li>
					<li class="m_tab4" onclick="javascript:fn_read();"></li>
				</ul>
			</div> -->

			<!--조회, 테이블, 페이지수 div 시작 -->
			<div class="con_area fl_wrap">
				<div class="fl_wrap">
					<!--사용자목록 -->
					<div style="margin-left: 10px; width:40%; float: left; vertical-align: top;">
						<h3 class="stitle">조직 목록</h3>
						<fieldset class="detail_fieldset">
							<div id="tree" style="padding: 0px 0px 0px 0px; width: 100%; height: 500px; float: left; overflow: auto;"></div>
						</fieldset>
					</div>
	
	 
					<div style="margin-left: 5px; width: 55%; height: 500px; float: left; vertical-align: top; " >
	 
						<h3 class="stitle ml20">사용자 목록</h3>
						<fieldset style="height: 100%;">
							<div class="search">
								<table class="tbl_type12" style="width:100%;">
									<colgroup>
										<col width="12%">
										<col width="13%">
										<col width="*">
										<col width="16%">
									</colgroup>
									<tr>
										<th>검색</th>
										<td>
											<form:select path="searchCondition" items="${AuthUserModel.searchConditionsList}" itemLabel="codeName" itemValue="code" cssClass="inp_w20" /> 
										</td>
										<td class="td_left">
											<form:input path="searchKeyword" cssClass="inp_w90 mt01" onkeypress="if(event.keyCode == 13) { fn_readList('search'); return;}" />
										</td>
										<td>
											<!--조회버튼 -->
											<div class="btn_line_blue2">
												<a href="javascript:fn_readList('search');">조회</a>
											</div>
										</td>
									</tr>
								</table>
	
								
							</div>
	
							<!--테이블, 페이지 DIV 시작 --> 
							<div class="con_Div2" style="padding-top: 5px;width:100%; height: 480px;">
	
								<div class="fakeContainer" style="width: 95%; overflow-y:auto; overflow-x:hidden; height: 450px">
	
									<table id="scrollTable" class="tbl_type">
										<thead>
											<tr>
												<th scope="col">이름</th>
												<th scope="col">소속</th>
												<th scope="col">관리</th>
												<th scope="col">검증</th>
												<th scope="col">운용</th>
											</tr>
										</thead>
										<tbody id="auth_user_list_area">
											<tr>
												<td colspan="5">결과 목록이 없습니다.</td>
											</tr>
										</tbody>
									</table>
								</div>
	
							</div>
						</fieldset>
					</div>
				</div>
				<div class="help mg10">
					<table width="96%" border="0" cellspacing="0" cellpadding="0">
						<colgroup>
							<col width="15%">
							<col width="*">
						</colgroup>
						<tr>
							<td class="help_title"><span>도움말</span></td>
							<td class="help_tex">
								조직도 선택 및 검색에 의해 출력된 사용자 목록에 한해 일괄(목록 전체) 적용됩니다. 따라서 조직도 선택 및 검색 결과를 최소로 해야 실수를 방지할 수 있습니다.<br/>
								[참고] 부서 전체, 조직도에서 선택한 전체에게 권한을 부여하고 싶은 경우 "권한별"을 이용하시면 유용합니다.
							</td>
						</tr>
					</table>
				</div>

				<!--사용자 선택 테이블 -->
				<!-- 
				<div style="margin-left: 15px; font: 12px Arial, Helvetica, sans-serif; color: #333333;">
					<font color="white">*</font><br /> <font color="white">*</font><br /> <font color="white">*</font><br /> 조직도 선택 및 검색에 의해 출력된 사용자 중에 수정하고자 하는 사용자를 체크, 권한 변경 후 저장하시면 체크된 모든 사용자의 권한이 일괄 적용됩니다.
				</div>
				 -->
				<!--저장버튼 -->
				<div class="pop_btn_org2 fl_right mr10">
					<a href="javascript:fn_update()">저장</a>
				</div>
			</div>
			<!--조회, 테이블, 페이지수 div 끝 -->
		</div>

	</form:form>
</body>
</html>

