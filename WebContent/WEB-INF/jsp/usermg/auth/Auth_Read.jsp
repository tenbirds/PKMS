<%--
/**
 * 권한 관리 페이지
 * 
 * @author : skywarker
 * @Date : 2012. 4. 24.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.pkms.usermg.auth.model.AuthModel"%>
<%@ page import="com.pkms.org.model.OrgModel"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="stylesheet" type="text/css" href="/css/dynatree/ui.dynatree.css" />
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
	/* 주석 */
	$(document).ready(function($) {
		doAuthTree("", true);
// 		doTable("scrollTable", "tbl_type", "1", "0", [ "220", "175" ]);
		fn_select_auth(2, "<%=AuthModel.AUTH_ROLE.ROLE_MANAGER%>");
	});

	function fn_read() {
		var form = document.getElementById("AuthModel");
		form.action = "/usermg/auth/AuthUser_Read.do";
		form.submit();

	}

	function fn_readList() {
		doSubmit("AuthModel", "/usermg/auth/Auth_Ajax_ReadList.do",
				"fn_callback_readList");
	}

	function fn_callback_readList(data) {
		try {
			var result = $("input[id=param1]").val();
			var treeData = eval(result + ";");
			var rootNode = $("#tree").dynatree("getRoot");
			rootNode.removeChildren(true);
			rootNode.addChild(treeData);
			//$("#tree").dynatree("getTree").reactivate();
			displayUserList();
		} catch (e) {
			alert("시스템 오류가 발생 하여 사용자 목록 조회에 실패 하였습니다.");
//			alert(e);
		}
	}

	function onClickTree(key) {
		//alert("click2:" + key);
	}

	function fn_update() {

		//if (confirm("정말로 저장 하시겠습니까?")) {

		var selectedNodes = $("#tree").dynatree("getRoot").tree
				.getSelectedNodes();
		var selectedKeys = $.map(selectedNodes, function(node) {
			if (!node.data.isFolder) {
				return node.data.key;
			}
		});

		$("#selectedKeys").val(selectedKeys);

		doSubmit4File("AuthModel",
				"<c:url value='/usermg/auth/Auth_Ajax_Update.do'/>",
				"fn_callback_update", "적용되었습니다.");
		//}
	}

	function fn_callback_update(data) {
		fn_readList();
	}

	function fn_select_auth(index, auth_role_value) {
		var auth_role = document.getElementById("auth_role");
		if (auth_role.value == auth_role_value) {
			return;
		}
		for ( var i = 1; i <= 3; i++) {
			document.getElementById("authority_tab" + i).src = "/images/authority_tab"
					+ i + "_off.png";
		}
		document.getElementById("authority_tab" + index).src = "/images/authority_tab"
				+ index + "_on.png";
		auth_role.value = auth_role_value;
		fn_readList();
	}

	function searchList() {
		var name = $("#searchKeyword").val();
		$('#auth_user_list_area tr').hide();
		$('#auth_user_list_area tr:contains("' + name + '")').show();
	}

	function selectList(key) {
		var tree = $("#tree").dynatree("getTree");
		tree.activateKey(key);
	}

	function displayUserList() {
		$("#searchKeyword").val("");
		var selectedNodes = $("#tree").dynatree("getRoot").tree
				.getSelectedNodes();
		var selectedKeys = $.map(selectedNodes, function(node) {
			if (!node.data.isFolder) {
				return '<tr id="' + node.data.title + node.data.key
						+ '" onclick="javascript:selectList(' + node.data.key
						+ ');"><td>' + node.data.group + '</td><td>'
						+ node.data.title + '</td></tr>';
			}
		});
		selectedKeys.sort();
		$("#auth_user_list_area").html(selectedKeys.join(""));
	}

	function onSelectTree() {
		displayUserList();
	}
</script>

</head>
<body>
	<form:form commandName="AuthModel" method="post" onsubmit="return false;">

		<form:hidden path="auth_role" />
		<form:hidden path="option" />
		<form:hidden path="selectedKeys" />

		<!--탭, 테이블, 페이지 DIV 시작 -->
		<div class="con_Div2">

			<!--탭 div 시작 -->
			<!-- <div class="con_Div6">
				<ul class="m_tab">
					<li class="m_tab5" onclick="javascript:fn_read();"></li>
					<li class="m_tab4_on"></li>
				</ul>
			</div> -->


			<!--조회, 테이블, 페이지수 div 시작 -->
			<div class="con_area fl_wrap">
				<div class="fl_wrap">
					<!--권한선택 -->
					<div class="fl_left mg20">
						<ul>
							<li><a href="javascript:fn_select_auth('2', '<%=AuthModel.AUTH_ROLE.ROLE_MANAGER%>');"><img id="authority_tab2" src="/images/authority_tab2_off.png" alt="" /></a></li>
							<li><a href="javascript:fn_select_auth('3', '<%=AuthModel.AUTH_ROLE.ROLE_OPERATOR%>');"><img id="authority_tab3" src="/images/authority_tab3_on.png" alt="" /></a></li>
							<li><a href="javascript:fn_select_auth('1', '<%=AuthModel.AUTH_ROLE.ROLE_ADMIN%>');"><img id="authority_tab1" src="/images/authority_tab1_off.png" alt="" /></a></li>
						</ul>
					</div>
	
					<!--사용자목록 -->
					<fieldset style="width: 40%; height: 530px; float: left;margin-top:-10px;">
						<h3 class="stitle">조직도</h3>
						<div id="tree" class="detail_fieldset" style="padding: 0px 0px 0px 0px; width: 100%; height: 500px; clear: both; overflow: auto;"></div>
					</fieldset>
	
					<fieldset style="width: 39%; height: 530px;float: left;margin-top:-10px;">
						<h3 class="stitle">사용자 목록</h3>
						<div class="search">
							<table class="tbl_type12" style="width:100%;">
								<colgroup>
								<col width="30%">
								<col width="*">
								<col width="22%">
								</colgroup>
								<tr>
									<th>소속 또는 이름</th>
									<td><input id="searchKeyword" onkeypress="if(event.keyCode == 13) { searchList(); return;}" Class="new_inp inp_w90" /></td>
									<td>
										<!--조회버튼 -->
										<div class="btn_line_blue2">
											<a href="javascript:searchList();">조회</a>
										</div>
									</td>
								</tr>
							</table>
	<!-- 
							조회버튼
							<div class="btn_sear">
								<a href="javascript:searchList();"></a>
							</div> -->
						</div>
	
						<!--테이블, 페이지 DIV 시작 -->
						<div class="fakeContainer" style="padding-top:5px;width: 100%; height: 445px;overflow:auto;">
							<table id="scrollTable" class="tbl_type" style="width: 98%;">
								<thead>
									<tr>
										<th scope="col">소속</th>
										<th scope="col">이름</th>
									</tr>
								</thead>
								<tbody id="auth_user_list_area">
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>
								</tbody>
							</table>
						</div>
						
	
					</fieldset>
				</div>

				<div class="help mg10">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<colgroup>
							<col width="15%">
							<col width="*">
						</colgroup>
						<tr>
							<td class="help_title"><span>도움말</span></td>
							<td class="help_tex">
								해당 권한이 부여된 사용자 모두가 검색되므로 잘못 부여된 경우를 찾아내거나 조직도에서 선택한 전체에게 권한을 부여하고 싶은 경우 유용합니다.<br/>
								검색 결과는 해당 권한이 부여된 사용자에 한정됩니다.<br/>
								[참고] 사용자별로 권한을 부여하고 싶은 경우 "사용자별"을 이용하시면 유용합니다.
							</td>
						</tr>
					</table>
				</div>
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

