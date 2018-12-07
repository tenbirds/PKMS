<%--
/**
 * 시스템 관리 메인 페이지
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="/css/supertable/supertable.css" />
<link rel="stylesheet" type="text/css" href="/css/dynatree/ui.dynatree.css">
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<link rel="stylesheet" type="text/css" href="/css/system.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery.scrollbar/jquery.mCustomScrollbar.css"/>
<style type="text/css">
.sys_btn_location_create {
	width: 215px;
	text-align: right;
	margin-top: -25px;
}
.sys_btn_location_copy {
	width: 215px;
	text-align: right;
	margin: -25px 0px 0px -50px;
}
</style>

<script type="text/javascript" src="/js/jquery.scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.dynatree.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>

<script type="text/javaScript" defer="defer">
	var empty_list = "<li></li><li></li><li></li><li></li><li></li><li></li>";//기본 출력 row 
	var selected_system_user_id = "";

	$(document).ready(function($) {	
		var group1_seq = $("#group1_seq").val();
		var group2_seq = $("#group2_seq").val();
		var group3_seq = $("#group3_seq").val();
		var system_seq = $("#system_seq").val();

// 		system_setting(9, 22, 69, 493);
		system_setting(group1_seq, group2_seq, group3_seq, system_seq);
	});
	
	function system_setting(group1_seq, group2_seq, group3_seq, system_seq){
		if(group1_seq != ""){
			// 최초 화면 로딩 시 대분류 조회
			fn_readListGroup1();
			
			//최초 화면 로딩 시 버튼 세팅
			doDivSH("hide", "group3_new_button", 0);
			doDivSH("hide", "system_new_button", 0);
			doDivSH("hide", "equipment_new_button", 0);
			
			setTimeout("fn_selectGroup1("+group1_seq+")", 200);
			if(group2_seq != ""){
// 				alert("group2_seq : " + group2_seq);
				setTimeout("fn_selectGroup2("+group2_seq+")", 450);
				if(group3_seq != ""){
// 					alert("group3_seq : " + group3_seq);
					setTimeout("fn_selectGroup3("+group3_seq+")", 700);
					if(system_seq != ""){
// 						alert("system_seq" + system_seq);
						setTimeout("fn_selectSystem("+system_seq+")", 900);
					}
				}
			}
		}else{
			// 최초 화면 로딩 시 대분류 조회
			fn_readListGroup1();
			
			//최초 화면 로딩 시 버튼 세팅
			doDivSH("hide", "group3_new_button", 0);
			doDivSH("hide", "system_new_button", 0);
			doDivSH("hide", "equipment_new_button", 0);
		}
	}
	
	function fn_toggleButton(key, kind) {
		if(kind == "system"){
			<sec:authorize ifNotGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_BP">
				doDivSH("hide", "sys_detail_create_button", 0);
				doDivSH("hide", "sys_detail_update_button", 0);
				doDivSH("hide", "sys_detail_copy_update_button", 0);
				return;
			</sec:authorize>
		}else{
			<sec:authorize ifNotGranted="ROLE_ADMIN">
				doDivSH("hide", "sys_detail_create_button", 0);
				doDivSH("hide", "sys_detail_update_button", 0);
				doDivSH("hide", "sys_detail_copy_update_button", 0);
				return;
			</sec:authorize>
		}
		if (key == "") {
			doDivSH("show", "sys_detail_create_button", 0);
			doDivSH("hide", "sys_detail_update_button", 0);
			doDivSH("hide", "sys_detail_copy_update_button", 0);
		} else {
			doDivSH("hide", "sys_detail_create_button", 0);
			if(kind == "system"){
				doDivSH("hide", "sys_detail_update_button", 0);
				doDivSH("show", "sys_detail_copy_update_button", 0);
			}else{
				doDivSH("show", "sys_detail_update_button", 0);
				doDivSH("hide", "sys_detail_copy_update_button", 0);
			}
		}
	}

	// ================================================================================================
	// 대분류 선택 시 화면 제어
	function fn_group1_dp_control(key) {
		fn_removeListSelection("group1_list_area");
		if (key == "") {
			$("#sys_detail_title").html("대분류 생성");
			doDivSH("hide", "group2_new_button", 0);
		} else {
			var item = $("#group1_" + key);
			item.toggleClass("sys_line_on", true);
			item.focus();
			$("#sys_detail_title").html("대분류 상세");
			doDivSH("show", "group2_new_button", 0);
		}
		doDivSH("hide", "group3_new_button", 0);
		doDivSH("hide", "system_new_button", 0);
		doDivSH("hide", "equipment_new_button", 0);

		$("#group2_seq").val("");
		$("#group3_seq").val("");
		$("#system_seq").val("");
		$("#equipment_seq").val("");

		$("#group3_list_area").html(empty_list);
		$("#system_list_area").html(empty_list);
		$("#equipment_list_area").html(empty_list);

		fn_toggleButton(key);
	}

	//대분류 목록 조회
	function fn_readListGroup1() {
		doSubmit("SysModel",
				"<c:url value='/sys/group1/Group1_Ajax_ReadList.do'/>",
				"fn_callback_readListGroup1");
	}

	//대분류 목록 조회 callback
	function fn_callback_readListGroup1(data) {
		$("#group1_list_area").html(data);
		var group1_seq = $("#group1_seq").val();
		if (group1_seq == "") {
			$("#group1_seq").val($("#group1_seq_select").val());
		}
		fn_selectGroup1($("#group1_seq").val());
	}

	//대분류 선택
	function fn_selectGroup1(group1_seq) {
		fn_group1_dp_control(group1_seq);
		$("#group1_seq").val(group1_seq);
		fn_readGroup1();
		fn_readListGroup2();
	}

	//대분류 상세 조회
	function fn_readGroup1() {
		doSubmit("SysModel",
				"<c:url value='/sys/group1/Group1_Ajax_Read.do'/>",
				"fn_callback_readGroup1");
	}

	//대분류 상세 조회 callback
	function fn_callback_readGroup1(data) {
		$("#sys_detail_area").html(data);
	}

	// ================================================================================================
	// 중분류 선택 시 화면 제어
	function fn_group2_dp_control(key) {
		fn_removeListSelection("group2_list_area");
		if (key == "") {
			$("#sys_detail_title").html("중분류 생성");
			doDivSH("hide", "group3_new_button", 0);
		} else {
			var item = $("#group2_" + key);
			item.toggleClass("sys_line_on");
			item.focus();
			$("#sys_detail_title").html("중분류 상세");
			doDivSH("show", "group3_new_button", 0);
		}
		doDivSH("hide", "system_new_button", 0);
		doDivSH("hide", "equipment_new_button", 0);

		$("#group3_seq").val("");
		$("#system_seq").val("");
		$("#equipment_seq").val("");

		$("#system_list_area").html(empty_list);
		$("#equipment_list_area").html(empty_list);

		fn_toggleButton(key);
	}

	//중분류 목록 조회
	function fn_readListGroup2() {
		doSubmit("SysModel",
				"<c:url value='/sys/group2/Group2_Ajax_ReadList.do'/>",
				"fn_callback_readListGroup2");
	}

	//중분류 목록 조회 callback
	function fn_callback_readListGroup2(data) {
		$("#group2_list_area").html(data);
		var group2_seq = $("#group2_seq").val();
		if (group2_seq != "") {
			fn_selectGroup2(group2_seq);
		}
	}

	//중분류 선택
	function fn_selectGroup2(group2_seq) {
		fn_group2_dp_control(group2_seq);
		$("#group2_seq").val(group2_seq);
		fn_readGroup2();
		fn_readListGroup3();
	}

	//중분류 상세 조회
	function fn_readGroup2() {
		doSubmit("SysModel",
				"<c:url value='/sys/group2/Group2_Ajax_Read.do'/>",
				"fn_callback_readGroup2");
	}

	//중분류 상세 조회 callback
	function fn_callback_readGroup2(data) {
		$("#sys_detail_area").html(data);
	}

	// ================================================================================================
	// 소분류 선택 시 화면 제어
	function fn_group3_dp_control(key) {
		fn_removeListSelection("group3_list_area");
		if (key == "") {
			$("#sys_detail_title").html("소분류 생성");
			doDivSH("hide", "system_new_button", 0);
		} else {
			var item = $("#group3_" + key);
			item.toggleClass("sys_line_on");
			item.focus();
			$("#sys_detail_title").html("소분류 상세");
			doDivSH("show", "system_new_button", 0);
		}
		doDivSH("hide", "equipment_new_button", 0);
		$("#system_seq").val("");
		$("#equipment_seq").val("");

		$("#equipment_list_area").html(empty_list);

		fn_toggleButton(key);
	}

	//소분류 목록 조회
	function fn_readListGroup3() {
		doSubmit("SysModel",
				"<c:url value='/sys/group3/Group3_Ajax_ReadList.do'/>",
				"fn_callback_readListGroup3");
	}

	//소분류 목록 조회 callback
	function fn_callback_readListGroup3(data) {
		$("#group3_list_area").html(data);
		var group3_seq = $("#group3_seq").val();
		if (group3_seq != "") {
			fn_selectGroup3(group3_seq);
		}
	}

	//소분류 선택
	function fn_selectGroup3(group3_seq) {
		fn_group3_dp_control(group3_seq);
		$("#group3_seq").val(group3_seq);
		fn_readGroup3();
		fn_readListSystem();
	}

	//소분류 상세 조회
	function fn_readGroup3() {
		doSubmit("SysModel",
				"<c:url value='/sys/group3/Group3_Ajax_Read.do'/>",
				"fn_callback_readGroup3");
	}

	//소분류 상세 조회 callback
	function fn_callback_readGroup3(data) {
		$("#sys_detail_area").html(data);
	}

	// ================================================================================================
	// 시스템 선택 시 화면 제어
	function fn_system_dp_control(key) {
		fn_removeListSelection("system_list_area");
		if (key == "") {
			$("#sys_detail_title").html("시스템 생성");
			doDivSH("hide", "equipment_new_button", 0);
		} else {
			var item = $("#system_" + key);
			item.toggleClass("sys_line_on");
			item.focus();
			$("#sys_detail_title").html("시스템 상세");
			doDivSH("show", "equipment_new_button", 0);
			doDivSH("hide", "remove_equipment", 0);
		}
		$("#equipment_seq").val("");

		fn_toggleButton(key, "system");
	}

	//시스템 목록 조회
	function fn_readListSystem() {
		doSubmit("SysModel",
				"<c:url value='/sys/system/System_Ajax_ReadList.do'/>",
				"fn_callback_readListSystem");
	}

	//시스템 목록 조회 callback
	function fn_callback_readListSystem(data) {
		$("#system_list_area").html(data);
		var system_seq = $("#system_seq").val();
		if (system_seq != "") {
			fn_selectSystem(system_seq);
		}
	}

	//시스템 선택
	function fn_selectSystem(system_seq) {
		fn_system_dp_control(system_seq);
		$("#system_seq").val(system_seq);
		fn_readSystem();
		fn_readListEquipment();
	}

	//시스템 상세 조회
	function fn_readSystem() {
		doSubmit("SysModel",
				"<c:url value='/sys/system/System_Ajax_Read.do'/>",
				"fn_callback_readSystem");
	}

	//시스템 상세 조회 callback
	function fn_callback_readSystem(data) {
		$("#sys_detail_area").html(data);

		doModalPopup("#open_bp_popup",  1, "click", 783, 576, "/usermg/bp/Bp_Popup_ReadList.do");
		doModalPopup("#open_bp_popup1", 1, "click", 783, 576, "/usermg/bp/Bp_Popup_ReadList.do?cur_bp_idx=1");
		doModalPopup("#open_bp_popup2", 1, "click", 783, 576, "/usermg/bp/Bp_Popup_ReadList.do?cur_bp_idx=2");
		doModalPopup("#open_bp_popup3", 1, "click", 783, 576, "/usermg/bp/Bp_Popup_ReadList.do?cur_bp_idx=3");
		doModalPopup("#open_bp_popup4", 1, "click", 783, 576, "/usermg/bp/Bp_Popup_ReadList.do?cur_bp_idx=4");
		
		selected_system_user_id = "";
		fn_systemUserHandleInit();
		searchTree('init');
	}

	// ================================================================================================
	// 장비 선택 시 화면 제어
	function fn_equipment_dp_control(key) {
		fn_removeListSelection("equipment_list_area");
		if (key == "") {
			$("#sys_detail_title").html("장비 생성");
		} else {
			var item = $("#equipment_" + key);
			item.toggleClass("sys_line_on");
			item.focus();
			$("#sys_detail_title").html("장비 상세");
			doDivSH("show", "remove_equipment", 0);
		}

		fn_toggleButton(key, "system");
	}

	//장비 목록 조회
	function fn_readListEquipment() {
		doSubmit("SysModel",
				"<c:url value='/sys/equipment/Equipment_Ajax_ReadList.do'/>",
				"fn_callback_readListEquipment");
	}

	//장비 목록 조회 callback
	function fn_callback_readListEquipment(data) {
		$("#equipment_list_area").html(data);
		var equipment_seq = $("#equipment_seq").val();
		if (equipment_seq != "") {
			fn_selectEquipment(equipment_seq);
		}
	}

	//장비 선택
	function fn_selectEquipment(equipment_seq) {
		fn_equipment_dp_control(equipment_seq);
		$("#equipment_seq").val(equipment_seq);
		fn_readEquipment();
	}

	//장비 상세 조회
	function fn_readEquipment() {
		doSubmit("SysModel",
				"<c:url value='/sys/equipment/Equipment_Ajax_Read.do'/>",
				"fn_callback_readEquipment");
	}

	//장비 상세 조회 callback
	function fn_callback_readEquipment(data) {
		$("#sys_detail_area").html(data);
		init_EquipmentRead();
	}
	// ================================================================================================

	function fn_removeListSelection(area){
		var nodeList = $("#"+area).find("li");
		for ( var i = 0; i < nodeList.length; i++) {
			$("#"+area).find(nodeList[i]).removeClass("sys_line_on");
		}
	}
	
	//시스템 검색
	function fn_searchList() {
		doDivSH("slideDown", "systemSearch", 100);
		
		var searchInput = document.getElementById("searchInput");
		searchInput.value = ltrim(searchInput.value);
		searchInput.value = rtrim(searchInput.value);

		if (searchInput.value == "") {
			alert("검색 할 단어를 입력 하세요.");
			searchInput.focus();
			return;
		}
		
		doSubmit("SysModel",
				"<c:url value='/sys/group1/Group1_Ajax_systemSearch.do'/>",
				"fn_callback_systemSearch");
	}
	
	function fn_callback_systemSearch(data){
		$("#systemSearch").html(data);
	}
	
	function close_sysSearch(){
		doDivSH("slideUp", "systemSearch", 100);
	}

</script>
</head>

<body>
	<form:form commandName="SysModel" method="post" enctype="multipart/form-data" onsubmit="return false;">

		<form:hidden path="group1_seq" />
		<form:hidden path="group2_seq" />
		<form:hidden path="group3_seq" />
		<form:hidden path="system_seq" />
		<form:hidden path="equipment_seq" />
		
		
		<!-- Start :: 2016-09-27 검색 및 결과 창 추가 -->
		<div class="new_con_Div32 new_pkms_sysSearchForm">
			<div class="new_search fl_wrap">
				<table class="new_sear_table2">
					<colgroup>
					<col width="10%">
					<col width="30%">
					<col width="10%">
					<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th>분류</th>
							<td>
								<form:radiobuttons path="companyCondition" items="${SysModel.companyConditionsList}" itemLabel="codeName" itemValue="code" />
							</td>
							<th>검색</th>
							<td>
								<form:select path="searchGubun" items="${SysModel.searchGubunsList}" itemLabel="codeName" itemValue="code" />
								<form:input path="searchInput" cssClass="txt" onkeypress="if(event.keyCode == 13) { fn_searchList();}" />
							</td>
						</tr>
					</tbody>
				</table>
				<!--조회버튼 -->
				<div class="new_btn_sear03"><a href="javascript:fn_searchList();" class="line_1">조 회</a></div>
			</div>
			
			<div class="new_pkms_sysSearchResultContainer" id="systemSearch">				
				<div class="new_pkms_sysSearchResult new_pkms_sysSearchResultHeader">
					<table>
						<colgroup>
							<col style="width: 50%" />
							<col style="width: 20%" />
							<col style="width: 30%" />
						</colgroup>
						<thead>
							<tr>
								<th>해당 경로</th>
								<th>담당자</th>
								<th>소속명</th>
							</tr>
						</thead>
					</table>
				</div><!-- .pkms_sysSearchResultHeader -->
				
				<div class="new_pkms_sysSearchResult new_pkms_sysSearchResultBody">
					<table>
						<colgroup>
							<col style="width: 50%" />
							<col style="width: 20%" />
							<col style="width: 30%" />
						</colgroup>
						<tbody>
							<tr>
								<td>대분류/중분류/소분류/시스템</td>
								<td>홍길동</td>
								<td>ITC Solution 팀</td>
							</tr>
						</tbody>
					</table>
				</div><!-- .pkms_sysSearchResultBody -->
				<div class="new_pkms_sysSearchResultClose btn_line_blue2">
					<a href="javascript:close_sysSearch();">닫기</a>
				</div>
			</div>
		</div>
		<!-- End :: 2016-09-27 검색 및 결과 창 추가 -->
		
		
		<!--분류, 상세정보 DIV 시작(스크롤지정) 시작-->
		<div class="con_Div32">
			<!--대분류 시작 -->
			<div class="sys_group">

				<!--대분류 타이틀,리스트,버튼 div 시작 -->
				<div class="sys_group1">
					<!--대분류타이틀 -->
					<div class="sys_title_wrap">
						<div class="sys_title">대분류</div>
						<div class="sys_btn_new">
							<sec:authorize ifAnyGranted="ROLE_ADMIN">
								<a href="javascript:fn_selectGroup1('');">신규</a>
							</sec:authorize>
						</div>
						
						<!--대분류 신규버튼 
						<div class="sys_btn_location_create" style="height: 25px;">
							<sec:authorize ifAnyGranted="ROLE_ADMIN">
								<a href="javascript:fn_selectGroup1('');">신규</a>
							</sec:authorize>
						</div>-->
					</div>


					<!--대분류리스트 -->
					<ul id="group1_list_area" class="sys_line">
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
					</ul>


				</div>
				<!--대분류 타이틀,리스트,버튼 div 시작 -->

				<!--arrow버튼
				<div class="sys_arrow">
					<img src="/images/sys_arrow.png" alt="" />
				</div> -->

			</div>
			<!--대분류 끝 -->



			<!--중분류 시작 -->
			<div class="sys_group">

				<!--중분류 타이틀,리스트,버튼 div 시작 -->
				<div class="sys_group1">
					<!--중분류 타이틀 -->
					<div class="sys_title_wrap">
						<div class="sys_title">중분류</div>
	
						<!--중분류 신규버튼 -->
						<div id="group2_new_button" class="sys_btn_new">
							<sec:authorize ifAnyGranted="ROLE_ADMIN">
								<a href="javascript:fn_selectGroup2('');">신규</a>
							</sec:authorize>
						</div>
						<!-- <div id="group2_new_button" class="sys_btn_location_create">
							<sec:authorize ifAnyGranted="ROLE_ADMIN">
								<a href="javascript:fn_selectGroup2('');"><img src="/images/btn_sys_new.gif" alt="신규" /></a>
							</sec:authorize>
						</div> -->
					</div>

					<!--중분류 리스트 -->
					<ul id="group2_list_area" class="sys_line" >
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
					</ul>

				</div>
				<!--중분류 타이틀,리스트,버튼 div 시작 -->

				<!--arrow버튼
				<div class="sys_arrow">
					<img src="/images/sys_arrow.png" alt="" />
				</div> -->

			</div>
			<!--중분류 끝 -->


			<!--소분류 시작 -->
			<div class="sys_group">

				<!-- 소분류 타이틀,리스트,버튼 div 시작 -->
				<div class="sys_group1">
					<!--소분류 타이틀 -->
					<div class="sys_title_wrap">
						<div class="sys_title">소분류</div>
	
						<!--소분류 신규버튼 -->
						<div id="group3_new_button" class="sys_btn_new">
							<sec:authorize ifAnyGranted="ROLE_ADMIN">
								<a href="javascript:fn_selectGroup3('');">신규</a>
							</sec:authorize>
						</div>
	
						<!--소분류 신규버튼 
						<div id="group3_new_button" class="sys_btn_location_create" style="height: 25px;">
							<sec:authorize ifAnyGranted="ROLE_ADMIN">
								<a href="javascript:fn_selectGroup3('');"><img src="/images/btn_sys_new.gif" alt="신규" /></a>
							</sec:authorize>
						</div>-->
					</div>

					<!--소분류 리스트 -->
					<ul id="group3_list_area" class="sys_line">
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
					</ul>

				</div>
				<!--소분류 타이틀,리스트,버튼 div 시작 -->

				<!--arrow버튼 
				<div class="sys_arrow">
					<img src="/images/sys_arrow.png" alt="" />
				</div>-->

			</div>
			<!--소분류 끝 -->



			<!--시스템 시작 -->
			<div class="sys_group">

				<!-- 시스템 타이틀,리스트,버튼 div 시작 -->
				<div class="sys_group1">
					<!--시스템 타이틀 -->
					<div class="sys_title_wrap">
						<div class="sys_title">시스템</div>
	
						<!--시스템 신규버튼 -->
						<div id="system_new_button" class="sys_btn_new">
							<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
								<a href="javascript:fn_selectSystem('');">신규</a>
							</sec:authorize>
						</div>
	
						<!--시스템 신규버튼 
						<div id="system_new_button" class="sys_btn_location_create" style="height: 25px;">
							<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
								<a href="javascript:fn_selectSystem('');"><img src="/images/btn_sys_new.gif" alt="신규" /></a>
							</sec:authorize>
						</div>-->
					</div>

					<!--시스템 리스트 -->
					<ul id="system_list_area" class="sys_line" >
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
					</ul>

				</div>
				<!--시스템 타이틀,리스트,버튼 div 시작 -->

				<!--arrow버튼 
				<div class="sys_arrow">
					<img src="/images/sys_arrow.png" alt="" />
				</div>-->

			</div>
			<!--시스템 끝 -->


			<!--장비 시작 -->
			<div class="sys_group">

				<!--장비 타이틀,리스트,버튼 div 시작 -->
				<div class="sys_group1_last">
					<!--장비 타이틀 -->
					<div class="sys_title_wrap">
						<div class="sys_title">장비</div>
	
						<!--장비 신규버튼 -->
						<div id="equipment_new_button" class="sys_btn_new">
							<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
								<a href="javascript:fn_selectEquipment('');">신규</a>
							</sec:authorize>
						</div>
						<!--장비 신규버튼 
						<div id="equipment_new_button" class="sys_btn_location_create" style="height: 25px;">
							<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
								<a href="javascript:fn_selectEquipment('');"><img src="/images/btn_sys_new.gif" alt="신규" /></a>
							</sec:authorize>
						</div>-->
					</div>

					<!--장비 리스트 -->
					<ul id="equipment_list_area" class="sys_line">
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
<!-- 						<li></li> -->
					</ul>

				</div>
				<!-- 장비 타이틀,리스트,버튼 div 시작 -->

			</div>
			<!--장비 끝 -->


		</div>
		<!--분류, 상세정보 DIV 시작(스크롤지정) 끝-->


		<!--상세 시작-->
		<div class="con_Div32">
			<fieldset class="new_fieldset">
				<legend id="sys_detail_title" class="title_bul">대분류</legend>
				<div class="con_area">
					<div class="con_detail">
						<div id="sys_detail_create_button" class="con_top_btn fl_wrap">
							<a href="javascript:fn_create();">저 장</a>
						</div>
						<div id="sys_detail_update_button" class="con_top_btn fl_wrap">
							<a href="javascript:fn_update();">수 정</a> <a href="javascript:fn_delete();">삭 제</a>
						</div>
						<div id="sys_detail_copy_update_button" class="con_top_btn fl_wrap">
							<a href="javascript:fn_copy();">복 사</a>
							<a href="javascript:fn_update();">수 정</a>
							<span id="remove_equipment" style="display: none;">
								<a href="javascript:fn_remove();">철 거</a>
							</span>
							<span><a href="javascript:fn_delete();">삭 제</a></span>
							<!-- 
							<table style="width: 100%;">
								<tr>
									<td align="right" style="padding: 0px 0px 5px 0px;">
										<span><a href="javascript:fn_copy();"><img src="/images/btn_copy.gif" alt="복사" /></a></span>
										<span><a href="javascript:fn_update();"><img src="/images/btn_modify.gif" alt="수정" /></a></span>
										<span id="remove_equipment" style="display: none;">
											<a href="javascript:fn_remove();">
												<img src="/images/btn_remove.gif" alt="철거" />
											</a>
										</span>
										<span><a href="javascript:fn_delete();"><img src="/images/btn_delete.gif" alt="삭제" /></a></span>
									</td>
								</tr>
							</table>
							 -->
						</div>
		
		
		
						<div id="sys_detail_area"></div>
					</div>
				</div>
			</fieldset>
		</div>
		<!--상세 끝-->

	</form:form>
	
	
	<!-- 커스텀 스크롤바 -->
  	<script>
  		$(document).ready(function(){
  			$(".pkms_sysSearchResultBody").mCustomScrollbar({
		       theme:"minimal-dark"
		     });
  		});
  		
  		/* $('.btn_sear').click(function(e){
  			e.preventDefault();
  			fn_searchList();
  			$('.pkms_sysSearchResultContainer').slideDown();
  		});
  		
  		$('.pkms_sysSearchResultClose a').click(function(e){
  			e.preventDefault();
  			$('.pkms_sysSearchResultContainer').slideUp();
  		}) */
  		
  	</script>
</body>
</html>
