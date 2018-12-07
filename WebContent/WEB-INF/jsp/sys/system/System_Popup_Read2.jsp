<%--
/**
 * 시스템 선택 팝업 페이지
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
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<link type="text/css" rel="stylesheet" href="/css/main.css" />

<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.dynatree.js"></script>

<script type="text/javaScript" defer="defer">
	var system_key = "";
	var system_name = "";

	$(document).ready(function($) {
		doAuthTree();
		var treeData = eval($("#treeScript").val() + ";");
		var rootNode = $("#tree").dynatree("getRoot");
		rootNode.removeChildren(true);
		rootNode.addChild(treeData);
	});

	/* 팝업 닫기 */
	function fn_popup_close() {
		parent.$('img[id=open_system_popup]').closeDOMWindow();
	}

	function fn_confirm() {
		if (isSelection()) {
			parent.fn_system_popup_callback2(system_key, system_name);
		} else {
			return;
		}
		fn_popup_close();
	}

	function fn_init() {
		parent.fn_system_popup_callback("", "");
		fn_popup_close();
	}

	function onDblClickTree(key, name, node) {
		onClickTree(key, name, node);

		if (!isSelection()) {
			return;
		}
		fn_confirm();
	}
	
	function onClickTree(key, name, node) {
		system_key = key;
		try {
			node = node.getParent();
			name = node.data.title + ">" + name;
			node = node.getParent();
			name = node.data.title + ">" + name;
			system_name = name;
		} catch (e) {
			system_name = name;
		}
	}

	function isSelection() {
		if (system_key != "x" && system_key != ""
				&& !(typeof system_key == "undefined")) {
			return true;
		}
		alert("시스템을 선택하여 주세요.");
		return false;
	}
	
	function fn_reload(){
		var form = document.getElementById("SysModel");
		form.action = "/sys/system/System_Popup_Read.do";
		form.submit();
	}
	
</script>
</head>

<body style="background-color: transparent">

	<form:form commandName="SysModel" method="post" onsubmit="return false;">


		<form:hidden path="treeScript" />

		<!--팝업 전체레이아웃 시작-->
		<div style="width: 475px;" id="pop_wrap">

			<!--팝업 content -->
			<div id="pop_content">

				<!--타이틀 -->
				<h4 class="ly_header">시스템 선택</h4>

				<ul class="main_select" style="width: 450px; margin: 10px 0px 0px 10px; position: absolute;">
					<li class="select_tex1">담당</li>
					<li class="select_line"></li>
					<li><form:radiobuttons onclick="javascript:fn_reload();" path="userCondition" items="${SysModel.userConditionList}" itemLabel="codeName" itemValue="code" /></li>
				</ul>

				<fieldset style="width: 450px; padding-top: 40px;">

					<!--시스템목록 -->
					<div style="width: 450px; height: 515px; float: left; vertical-align: top; border: 1px solid #dcdcdc;">
						<fieldset class="detail_fieldset">
							<legend>시스템 목록</legend>
														
							<div id="tree" style="padding: 0px 0px 0px 0px; width: 428px; height: 495px; float: left; overflow: auto;"></div>
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
