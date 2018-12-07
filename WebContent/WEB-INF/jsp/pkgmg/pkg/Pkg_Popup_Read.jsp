<%--
/**
 * PKG 검증요청 등록/수정/상세
 * 
 * @author : scott
 * @Date : 2012. 4. 17.
 */
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="registerFlag" value="${empty PkgModel.pkg_seq ? '등록' : '수정'}" />

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />

<script type="text/javascript" src="/js/jquery/jquery.qtip-1.0.0-rc3.min.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javascript" src="/js/pkgmg/pkgmg.pkg.js"></script>
<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script> 
<script type="text/javaScript">

/*
 * 
 * PKG검증요청 팝업에서만 사용되는 권한
 * PKG검증요청 팝업은 시스템에서 부여된 권한으로 제어되는 것이 아니고
 * 시스템별 담당에 의한 권한이 부여됨
 */
var GRANTED;
var txtflg = true;
$(document).ready(function($) {
	
	if($("textarea[id=content]").val() != '' && $("textarea[id=content]").val() != null){
		txtflg = false;
	}
	
	GRANTED = $("input[id=granted]").val();
	
	if(GRANTED == "NONE") {
		alert("조회 권한이 없는 사용자입니다. 정상적인 경로로 다시 접속하세요.");
		window.close();
		return;
	} else if(GRANTED == "OPERATOR" && $("input[id=system_seq]").val() == '') {
		alert("사업기획/개발/운용 담당자는 신규 등록이 불가합니다. ");
		window.close();
		return;
	}
	
	// 초기 화면 Init
	fn_init();

	init_system_auth_popup();

	// Tab Click 이벤트 제어
	$("#pop_tab li").click(fn_tab_click); // li 클릭했을때 

	// Flow Click 이벤트 제어
	$("#flow_ul li").click(fn_flow_click);

	
	// 보완적용내역 엑셀 업로드
	doModalPopup("img[id=open_3_1]", 1, "click", 520, 490, "/pkgmg/pkg/PkgSupplement_Popup_ReadList.do");
	
	doCalendar("start_date_04");
	doCalendar("start_date_07");
	doCalendar("start_date_08");
	doCalendar("end_date_04");
	doCalendar("end_date_07");
	doCalendar("end_date_08");
	doCalendar("col27");
	doCalendar("col28");
	
	$('textarea[maxlength]').keydown(function(){
        var max = parseInt($(this).attr('maxlength'));
        var str = $(this).val();
		if(str.length > max) {
		    $(this).val(str.substr(0, max));
			alert("최대 [" + max + " 자]까지 입력 가능합니다.");
		}
	});
	// pkg_tab_3_2 load....
	//fn_callback_detail_variable_update();
	
	$('h4[title]').qtip();
	$(':button[title]').qtip();

});

	function fnSelectBoxCnt(){
		var selectedIdx = document.forms[0].selectID.selectedIndex;
		for(var i=1; i<selectedIdx+2; i++){
			document.getElementById("display_file"+i).style.display = "";
		}
		for(var j=selectedIdx+2; j<=10; j++){
			document.getElementById("display_file"+j).style.display = "none";
		}
			
		if(selectedIdx == 0){
			selectedIdx = 1;
		}
		
		document.getElementById("selectRowspan").rowSpan = selectedIdx+1;
	}
	
	function fnSelBox29(){ //개발 근거 문서
		var selIdx = document.forms[0].selID29.selectedIndex;
		for(var i=1; i<selIdx+2; i++){
			document.getElementById("display_29_"+i).style.display = "";
		}
		for(var j=selIdx+2; j<=5; j++){
			document.getElementById("display_29_"+j).style.display = "none";
		}		
	}
	
	function fnSelBox10(){ //신규 기능 규격서
		var selIdx = document.forms[0].selID10.selectedIndex;
		for(var i=1; i<selIdx+2; i++){
			document.getElementById("display_10_"+i).style.display = "";
		}
		for(var j=selIdx+2; j<=5; j++){
			document.getElementById("display_10_"+j).style.display = "none";
		}
	}
	
	function fnSelBox30(){ //보완 내역서
		var selIdx = document.forms[0].selID30.selectedIndex;
		for(var i=1; i<selIdx+2; i++){
			document.getElementById("display_30_"+i).style.display = "";
		}
		for(var j=selIdx+2; j<=5; j++){
			document.getElementById("display_30_"+j).style.display = "none";
		}
	}
	
	function fnSelBox31(){//시험 절차서
		var selIdx = document.forms[0].selID31.selectedIndex;
		for(var i=1; i<selIdx+2; i++){
			document.getElementById("display_31_"+i).style.display = "";
		}
		for(var j=selIdx+2; j<=5; j++){
			document.getElementById("display_31_"+j).style.display = "none";
		}
	}
	
	function fnSelBox32(){ //코드 리뷰 및 SW아키텍처 리뷰
		var selIdx = document.forms[0].selID32.selectedIndex;
		for(var i=1; i<selIdx+2; i++){
			document.getElementById("display_32_"+i).style.display = "";
		}
		for(var j=selIdx+2; j<=5; j++){
			document.getElementById("display_32_"+j).style.display = "none";
		}
	}
	
	function fnSelBox12(){ //기능 검증 결과
		var selIdx = document.forms[0].selID12.selectedIndex;
		for(var i=1; i<selIdx+2; i++){
			document.getElementById("display_12_"+i).style.display = "";
		}
		for(var j=selIdx+2; j<=5; j++){
			document.getElementById("display_12_"+j).style.display = "none";
		}
	}
	
	function fnSelBox41(){ //코드 리뷰 및 SW아키텍처 리뷰
		var selIdx = document.forms[0].selID41.selectedIndex;
		for(var i=1; i<selIdx+2; i++){
			document.getElementById("display_41_"+i).style.display = "";
		}
		for(var j=selIdx+2; j<=5; j++){
			document.getElementById("display_41_"+j).style.display = "none";
		}
	}
	
	function fnPopupProgress(pkg_seq){
		var option = "width=697px, height=197px, scrollbars=no, resizable=no, statusbar=no";
		var form = document.getElementById("PkgModel");
		var sWin = window.open("", "fnPopupProgress", option);
		
		$("#pkg_seq").val(pkg_seq);
		
		form.target = "fnPopupProgress";
		form.action = "/pkgmg/pkg/Pkg_Popup_Progress_Read.do";
		form.submit();
		sWin.focus();
	}
	
	function contentClick(obj){
		if(txtflg){
			obj.value = "";
			txtflg=false;
		}
	}
	
	function systemUpdateLink(group1_seq, group2_seq, group3_seq, system_seq){
		alert("팝업창이 아닌 메인창에서 수정페이지로 이동됩니다.");
		window.opener.system_m(group1_seq, group2_seq, group3_seq, system_seq);
		
	}

</script>
</head>

<body>
<form:form commandName="PkgModel" method="post" enctype="multipart/form-data">

<form:hidden path="granted" />

<form:hidden path="retUrl" />
<form:hidden path="pkg_tab_num" />

<form:hidden path="pkg_seq" />
<form:hidden path="system_seq" />
<form:hidden path="old_system_seq" />
<form:hidden path="tpl_seq" />
<form:hidden path="tpl_ver" />
<form:hidden path="useY_tpl_ver" />

<form:hidden path="status" />
<form:hidden path="selected_status" />
<form:hidden path="max_status" />
<form:hidden path="turn_down" />
<form:hidden path="pe_status" />
<form:hidden path="pkg_detail_seq" />
<form:hidden path="ord" />
<form:hidden path="new_pn_cr_seq" />

<form:hidden path="tempmgModelListSize" />
<form:hidden path="status_crud_callback" />
<form:hidden path="pkg_detail_count" />
<form:hidden path="ok" id ="ok"/>
<form:hidden path="nok" id="nok"/>
<form:hidden path="cok" id="cok"/>

<form:hidden path="status_operation" />
<form:hidden path="detail_variable_add" id="detail_variable_add"/>

<form:hidden path="urgency_yn"/>
<form:hidden path="urgency_verifi"/>

<form:hidden path="system_user_name"/>
<form:hidden path="dev_system_user_name"/>

<form:hidden path="pkg_road_map_seq_04"/>
<form:hidden path="pkg_road_map_seq_07"/>
<form:hidden path="pkg_road_map_seq_08"/>

<form:hidden path="road_map_seq_04"/>
<form:hidden path="road_map_seq_07"/>
<form:hidden path="road_map_seq_08"/>

<form:hidden path="system_name_org"/>

<form:hidden path="ok_dev"/>
<form:hidden path="nok_dev"/>
<form:hidden path="cok_dev"/>
<form:hidden path="bypass_dev"/>

<form:hidden path="status_dev" />
<form:hidden path="vol_yn" />
<form:hidden path="sec_yn" />
<form:hidden path="cha_yn" />
<form:hidden path="non_yn" />
<form:hidden path="on_yn" />

<form:hidden path="dev_yn_bak" />

<!-- 화면에서만 사용 -->
<input type="hidden" id="new_pn_cr_no" />
<input type="hidden" id="td_no"/>

<!-- 패키지검증의 검증타입 -->
<form:hidden path="verify_type"/>
<form:hidden path="result_quest_input"/>
<form:hidden path="result_item_input"/>
<form:hidden path="result_quest_radio"/>
<form:hidden path="result_item_radio"/>
<form:hidden path="end"/>
					
<div style="width:1175px; top:0px; left:0px; overflow:hidden;" id="pop_wrap">
	<!--팝업 content -->
	<div id="pop_content" style="height:840px;">

		<!--타이틀 -->
		<h4 class="ly_header">PKG 검증요청 <%-- [pkg_seq: ${PkgModel.pkg_seq}] --%>
				<span class="pop_header_reg_update">
			<c:if test="${not empty PkgModel.reg_date}">
					등록: ${PkgModel.reg_user_name} (${PkgModel.reg_date})
			</c:if>

			<c:if test="${not empty PkgModel.update_date}">
					| 수정: ${PkgModel.update_user_name} (${PkgModel.update_date})
			</c:if>
				</span>
		</h4>
		<fieldset style="padding-top:0px;">
		<!--탭, 테이블, 플로우 시작 -->
		<div class="pop_con_Div7">

		<!--탭시작 -->
		<div class="pop_con_Div4">
			<ul id="pop_tab" class="pop_tab">
				<li class="pop_tab8_text" title="로드맵"></li> <!-- 로드맵 -->
				<li class="pop_tab1_text" title="기본정보"></li> <!-- 상용 기본정보 -->
				<li class="pop_tab2_text" title="검증 산출물 등록"></li> <!-- 공급사 검증내역  파일 첨부-->
				<li class="pop_tab3_text" title="보완적용내역"></li> <!-- 상용화 검증내역 -->
				<li class="pop_tab4_text" title="시스템 정보"></li> <!-- 담당자 -->
				<li id="pkg_1_button"      class="pop_tab5" style="display:none;"><img src="/images/btn_pop_emv.gif" alt="검증요청"></li>
				<li id="pkg_delete_button" class="pop_tab6" style="display:none;"><img src="/images/btn_pop_delete.gif" alt="삭제"></li>
				<li id="pkg_copy_button"   class="pop_tab7" style="display:none;"><img src="/images/btn_pop_copy.gif" alt="복사하기"></li>
			</ul>
		</div>

		<!-- pkg > Tab8(로드맵) -->
		<div id="pkg_tab_8" class="pop_con_Div5" style="display: none;">
			<div class="pop_tbl_tit">로드맵</div>
		
			<div class="pop_basetbl" style="width:100%;">
				<table class="pop_tbl_type1" style="border-spacing: 0px;">
					<thead>
					<tr>
						<th scope="col">시스템 <span class='necessariness'>*</span></th>
						<td colspan="2">
							${PkgModel.system_name}
						</td>
					</tr>
					<tr>
						<th scope="col">제목 <span class='necessariness'>*</span></th>
						<td colspan="2">
							${PkgModel.title}
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="6">로드맵 일정 <span class='necessariness'>*</span></th>
						<th rowspan="2">
							패키지 개발
						</th>
						<td>
							<form:input path="start_date_04" maxlength="50" class="new_inp inp_w30 fl_left" readonly="true" />
							<span class="fl_left mg05"> ~ </span> 
							<form:input path="end_date_04" maxlength="50" class="new_inp inp_w30 fl_left" readonly="true" />
						</td>
					</tr>
					<tr>
						<td>
							<form:input path="comment_04" maxlength="50" class="new_inp" style="width:418px"/>
						</td>
					</tr>
					<tr>
						<th rowspan="2">
							패키지 검증
						</th>
						<td>
							<form:input path="start_date_07" maxlength="50" class="new_inp inp_w30 fl_left" readonly="true" />
							<span class="fl_left mg05"> ~ </span> 
							<form:input path="end_date_07" maxlength="50"  class="new_inp inp_w30 fl_left"  readonly="true" />
						</td>
					</tr>
					<tr>
						<td>
							<form:input path="comment_07" maxlength="50" class="new_inp " style="width:418px"/>
						</td>
					</tr>
					<tr>
						<th rowspan="2">
							패키지 적용
						</th>
						<td>
							<form:input path="start_date_08" maxlength="50"  class="new_inp inp_w30 fl_left"  readonly="true" />
							<span class="fl_left mg05"> ~ </span> 
							<form:input path="end_date_08" maxlength="50"  class="new_inp inp_w30 fl_left"  readonly="true" />
						</td>
					</tr>
					<tr>
						<td>
							<form:input path="comment_08" maxlength="50" class="new_inp inp" style="width:418px"/>
						</td>
					</tr>
					</thead>
				</table>
			</div>
			<div class="help_notice" id="tab1_comment" style="display:none;">
				기본정보를 등록하시면 로드맵 저장이 가능합니다.
			</div>
			<div class="btn_bg1" id="tab2_save" style="cursor:pointer;" onClick="fn_tab8();">저장</div>
		</div>

			<!-- pkg > Tab1(기본정보) -->
			<div id="pkg_tab_1" class="pop_con_Div5" style="overflow-y:auto; overflow-x:hidden;">
				<!--타이틀 -->
				<div class="pop_tbl_tit">상용기본정보	</div>

				<!--기본정보, 상세정보 div 시작 -->
				<div class="pop_basetbl" style="height: auto; overflow-y:auto;">
					<!--기본정보 -->
					<table class="pop_tbl_type1" style="border-spacing: 0px">
						<colgroup>
							<col width="100">
							<col width="300">
							<col width="100">
							<col width="300">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">개발 검증요청</th>
								<td colspan="3">
									<c:choose>
										<c:when test="${PkgModel.status <= 1 && PkgModel.status_dev < 21}">
											<form:radiobuttons path="dev_yn" items="${PkgModel.dev_yn_list}" itemLabel="codeName" itemValue="code" />
										</c:when>
										<c:otherwise>
											<form:radiobuttons path="dev_yn" items="${PkgModel.dev_yn_list}" itemLabel="codeName" itemValue="code" disabled="true"/>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<th scope="col">시스템 <span class='necessariness'>*</span></th>
								<td colspan="3">
									<div class="pop_system">
										<form:input path="system_name" maxlength="50" class="inp" style="width:500px" readonly="true" />
									</div>
									<!--버튼위치영역 -->
									<div class="pop_btn_location">
										<c:if test="${PkgModel.status == '0'}">
											<span><img src="/images/pop_btn_select.gif" alt="선택" style="cursor: pointer;" id="open_system_popup"/></span> 
										</c:if>
										<c:if test="${PkgModel.system_seq != ''}">
											<span style="display:none;"><img src="/images/pop_btn_systemhistory.gif" alt="시스템이력" /></span>
										</c:if>
									</div>
								</td>
							</tr>
						</thead>
						<tbody>
						<tr>
							<th scope="col">제목 <span class='necessariness'>*</span></th>
							<td>
								<form:input path="title" maxlength="100" class="inp" style="width:250px" />
							</td>
							<th scope="col">대상 시스템</th>
							<td>
								<c:forEach var="result" items="${PkgEqList}" varStatus="status">
									<c:if test = "${result.work_gubun eq 'S'}">									
										<input type="hidden" id="s_target_system" name="s_target_system" value="초도:${result.idc_name} ${result.eq_name} 등 ${result.eq_cnt}식"/>
										<p align="left">초도:${result.idc_name} ${result.eq_name} 등 ${result.eq_cnt}식</p>
									</c:if>
									<c:if test = "${result.work_gubun eq 'E'}">
										<input type="hidden" id="e_target_system" name="e_target_system" value="확대:${result.idc_name} ${result.eq_name} 등 ${result.eq_cnt}식"/>
										<p align="left">확대:${result.idc_name} ${result.eq_name} 등 ${result.eq_cnt}식</p>
									</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th scope="col">상용대표담당자</th>
							<td>
								${PkgModel.system_user_name}
							</td>
							
							<th scope="col">초도작업</th>
							<td>
								<c:forEach var="result" items="${PkgTimeList}" varStatus="status">
									<c:if test = "${result.work_gubun eq 'S'}">
										<input type="hidden" id="s_work_day" name="s_work_day" value="초도:${result.min_date} ~ ${result.max_date}"/>
										<p align="left">초도:${result.min_date} ~ ${result.max_date}</p>
									</c:if>
									<c:if test = "${result.work_gubun eq 'E'}">
										<input type="hidden" id="e_work_day" name="e_work_day" value="확대:${result.min_date} ~ ${result.max_date}"/>
										<p align="left">확대:${result.min_date} ~ ${result.max_date}</p>
									</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<th scope="col">개발대표담당자</th>
							<td>
								${PkgModel.dev_system_user_name}
							</td>
							<th scope="col">패키지개발 기간</th>
							<td>
								${PkgModel.start_date_04} ~ ${PkgModel.end_date_04}
							</td>
						</tr>
						<tr>
							<th scope="col">PKG 버전  <span class='necessariness'>*</span></th>
							<td>
								<form:input path="ver" maxlength="10" class="inp" style="width:250px" />
							</td>
							<th scope="col">버전 구분  <span class='necessariness'>*</span></th>
							<td>
								<form:radiobuttons path="ver_gubun" items="${PkgModel.ver_gubun_list}" itemLabel="codeName" itemValue="code" />
							</td>
						</tr>
						<tr>
							<th scope="col">주요 보완내역 <span class='necessariness'>*</span></th>
							
							<td colspan="3">
							
							<c:choose>
								<c:when test="${PkgModel.content ne '' && PkgModel.content != null}">
									<form:textarea path="content" rows="10" class="inp_w1" style="width:98.5%;" maxlength="550" />
								</c:when>
								<c:otherwise>									
<textarea name="content" rows="10" class="inp_w1" style="width:98.5%;" maxlength="550" onfocus="contentClick(this)">
Ex) 예시
1. 신규 1항목
  ㅇIP readdress 기능 : IMS L4 우회소통시 활용
2. 보완 2항목
  ㅇChange Noti Req 메시지 수신시 CSSM Down 보완
  ㅇRTRV-CLOG-DATA 실행중 CPDM Down 보완
3. 개선 3항목
  ㅇICMP echo request drop 기능 (보안팀 요청)
  ㅇSGW restoration pool 확장 : SGW 고장시 착신서비스 연속성 보장
  ㅇDNS readdress cache 확장 (기존 세션당 2개 -> 10개)
</textarea>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
						<tr>
							<th scope="col">RM/CEM<br/>관련 feature</th>
							<td colspan="3">
								<form:textarea path="rm_issue_comment" rows="5" class="inp_w1" style="width:98.5%;" maxlength="550" />
							</td>
						</tr>
						<tr>
							<th scope="col" >PKG 적용시<br/>RM 검토</th>
							<td colspan="3">
								<table style="width:600px; color:white; border-color:white; border:0px; padding:0 0 0 0;">
									<tr>
										<td style="width:100px; color:white; border-color:white; border-spacing:0px; padding:0 0 0 0;">
											<form:select path="ser_yn" items="${PkgModel.ser_yn_list}" itemLabel="codeName" itemValue="code" onChange="fn_ser_yn();" />
										</td>
										<td style="width:500px; float:left; color:white; border-color:white; border-spacing:0px; padding:0 0 0 0;">
											<form:input path="ser_content" maxlength="100" class="inp" style="width:400px;"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th scope="col">서비스중단시간</th>
							<td colspan="3"><form:input path="ser_downtime" maxlength="8" class="inp" style="width:100px; ime-mode:disabled;" onkeydown="fn_numbersonly(event);" /> (분)</td>
						</tr>
						<tr>
							<th scope="col">로밍 영향도</th>
							<td colspan="3">
								<table style="width:600px; color:white; border-color:white; border:0px; padding:0 0 0 0;">
									<tr>
										<td style="width:100px; color:white; border-color:white; border-spacing:0px; padding:0 0 0 0;">
											<form:select path="roaming_link" items="${PkgModel.roaming_link_list}" itemLabel="codeName" itemValue="code" onChange="fn_roaming_link();" />
										</td>
										<td style="width:500px; float:left; color:white; border-color:white; border-spacing:0px; padding:0 0 0 0; margin-left: 4px;">
											<form:input path="roaming_link_comment" maxlength="100" class="inp" style="width:400px;"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th scope="col">우회 소통</th>
							<td colspan="3">
								<form:radiobuttons path="bypass_traffic" items="${PkgModel.bypass_traffic_list}" itemLabel="codeName" itemValue="code" />
							</td>
						</tr>
<%-- 						<form:hidden path="pe_yn"/> --%>
						<tr>
							<th scope="col" style="color: blue; font-size: 12.5px;">과금영향도</th>
							<td colspan="3">
								<table style="width:600px; color:white; border-color:white; border:0px; padding:0 0 0 0;">
									<tr>
										<td style="width:100px; color:white; border-color:white; border-spacing:0px; padding:0 0 0 0;">
											<form:select path="pe_yn" items="${PkgModel.pe_yn_list}" itemLabel="codeName" itemValue="code" onChange="fn_pe_yn();" />
										</td>
										<td style="width:500px; float:left; color:white; border-color:white; border-spacing:0px; padding:0 0 0 0;">
											<form:input path="pe_yn_comment" maxlength="100" class="inp" style="width:400px; "/>
											<div id="pe_yn_tip" style="color: #dc8527; padding-left:33px; display:none; background:url(/images/ico_notice.png) no-repeat 10px 0px; height:16px;">
											OWMS로 과금 검증 요청되며, 고객빌링팀/IT기술원의 확인을 받는 절차입니다. 과금사고 방지를 위해 필요. 과금접수를 통해 OWMS에 접속하여 저장해야 과금 검증이 요청됩니다.
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
<!-- status가 0보다 크면 즉 임시저장단계 보다 단계가 더 진행된다면 이걸 뿌려라. -->
						<c:if test="${PkgModel.status > 0}">
							<tr>
								<th scope="col">검증항목<br/>및<br/>진도율</th>
								<%-- <td><a href="javascript:fnPopupProgress('${PkgModel.pkg_seq }');"><img src="/images/btn_identify.gif" alt="확인" style="cursor: pointer; vertical-align: middle;" id="open_bp_popup" align="absmiddle" style="vertical-align: middle;" /></a></td> --%>
								<td colspan="3">
									<table class="pop_tbl_progress_type1" style="width: 98.5%; border-spacing: 0px" >
										<colgroup>
											<col width="20%">
											<col width="20%">
											<col width="20%">
											<col width="20%">
											<col width="20%">
										</colgroup>
										<tr>	
											<th></th>
											<th>항목수</th>
											<th>검증내역개수</th>
											<th>개선내역개수</th>
											<th>검증진도율</th>
										</tr>
										<tr>
											<th>신규</th>
											<td>${PkgModelProgress.new_col1 }</td>
											<td>${PkgModelProgress.new_col2 }</td>
											<td>${PkgModelProgress.new_col3 }</td>
											<td>
												<c:choose>
													<c:when test="${PkgModelProgress.new_col4 > 0 }">
														<fmt:formatNumber value="${PkgModelProgress.new_col4 / PkgModelProgress.new_col1 * 100 }" pattern="0" />
													</c:when>
													<c:otherwise>
														0
													</c:otherwise>
												</c:choose>
												%
											</td>
										</tr>
										<tr>
											<th>보완</th>
											<td>${PkgModelProgress.pn_col1 }</td>
											<td>${PkgModelProgress.pn_col2 }</td>
											<td>${PkgModelProgress.pn_col3 }</td>
											<td>
												<c:choose>
													<c:when test="${PkgModelProgress.pn_col4 > 0 }">
														<fmt:formatNumber value="${PkgModelProgress.pn_col4 / PkgModelProgress.pn_col1 * 100 }" pattern="0" />
													</c:when>
													<c:otherwise>
														0
													</c:otherwise>
												</c:choose>
												%
											</td>
										</tr>
										<tr>
											<th>개선</th>
											<td>${PkgModelProgress.cr_col1 }</td>
											<td>${PkgModelProgress.cr_col2 }</td>
											<td>${PkgModelProgress.cr_col3 }</td>
											<td>
												<c:choose>
													<c:when test="${PkgModelProgress.cr_col4 > 0 }">
														<fmt:formatNumber value="${PkgModelProgress.cr_col4 / PkgModelProgress.cr_col1 * 100 }" pattern="0" />
													</c:when>
													<c:otherwise>
														0
													</c:otherwise>
												</c:choose>
												%
											</td>
										</tr>
										<tr>
											<th>합계</th>
											<td>${PkgModelProgress.new_col1 + PkgModelProgress.pn_col1 + PkgModelProgress.cr_col1 }</td>
											<td>${PkgModelProgress.new_col2 + PkgModelProgress.pn_col2 + PkgModelProgress.cr_col2 }</td>
											<td>${PkgModelProgress.new_col3 + PkgModelProgress.pn_col3 + PkgModelProgress.cr_col3 }</td>
											<td>
												<c:choose>
													<c:when test="${(PkgModelProgress.new_col4 + PkgModelProgress.pn_col4 + PkgModelProgress.cr_col4) / (PkgModelProgress.new_col1 + PkgModelProgress.pn_col1 + PkgModelProgress.cr_col1) * 100 != 'NaN'}">
														<fmt:formatNumber value="${(PkgModelProgress.new_col4 + PkgModelProgress.pn_col4 + PkgModelProgress.cr_col4) / (PkgModelProgress.new_col1 + PkgModelProgress.pn_col1 + PkgModelProgress.cr_col1) * 100 }" pattern="0" />
													</c:when>
													<c:otherwise>
														0
													</c:otherwise>
												</c:choose>
												%
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</c:if>
						</tbody>
					</table>

					<!--과금상세 -->
					<!-- 과금영향도가 영향도있음 이면 나오기-->
					<%-- <div id="pkg_tab_1_1" class="pop_detail" style="width:724px; display:none;">
						<table cellspacing="0" class="pop_tbl_type2"  style="width:724px;">
							<thead>
							<tr>
								<th scope="col">변경내역</th>
								<td><form:input path="pe_edit_title" class="inp" style="width:500px" maxlength="300"  onkeydown="fn_detail_variable_textarea_check(this);" /></td>
							</tr>
							</thead>
							<tbody>
							<tr>
								<th scope="col">확인/지원<br>
								요청확인</th>
								<td><form:textarea path="pe_content" rows="4" id="pe_content" style="width:550px" maxlength="550" onkeydown="fn_detail_variable_textarea_check(this);" /></td>
							</tr>
							<tr>
								<th scope="col">테스트<br>요청 일자</th>
								<td>
									<form:input path="pe_test_date" class="inp" style="width:70px" readonly="true" />
								</td>
							</tr>
							<tr>
								<th scope="col">테스트<br>
								장비정보</th>
								<td>
									IP	<form:input path="pe_equip_ip" class="inp" style="width:140px"   maxlength="30" onkeydown="fn_numbersonly(event);fn_detail_variable_textarea_check(this);"  /> &nbsp;&nbsp;&nbsp;
									PORT <form:input path="pe_equip_port" class="inp"  style="width:105px;ime-mode:disabled;" onkeydown="fn_numbersonly(event);fn_detail_variable_textarea_check(this);" onkeyup="fn_number_validator(this);" maxlength="10" />
								</td>
							</tr>
							<tr>
								<th scope="col">테스트<br>단말번호</th>
								<td>
									1
									<form:input path="pe_no_1" class="inp" style="width:143px"  maxlength="20" onkeydown="fn_detail_variable_textarea_check(this);"/>
									2
									<form:input path="pe_no_2" class="inp" style="width:143px"  maxlength="20" onkeydown="fn_detail_variable_textarea_check(this);"/>
									<br>
									3
									<form:input path="pe_no_3" class="inp" style="width:143px"  maxlength="20" onkeydown="fn_detail_variable_textarea_check(this);"/>
									4
									<form:input path="pe_no_4" class="inp" style="width:143px"  maxlength="20" onkeydown="fn_detail_variable_textarea_check(this);"/>
									<br>
									5
									<form:input path="pe_no_5" class="inp" style="width:143px"  maxlength="20" onkeydown="fn_detail_variable_textarea_check(this);"/>
								</td>
							</tr>
							<tr>
								<th scope="col">긴급요청</th>
								<td>
									<form:select path="pe_gubun" items="${PkgModel.pe_gubun_list}" itemLabel="codeName" itemValue="code" />
								</td>
							</tr>
							</tbody>
						</table>
					</div> --%>
				</div>
				<div class="help_notice" id="tab1_comment" style="display:none;">
					기본정보를 등록하시면 다음 단계로 진행이 가능합니다.
				</div>
				
				<sec:authorize ifAnyGranted = "ROLE_BP">
					<c:if test="${PkgModel.status != '7' && PkgModel.status != '8' && PkgModel.status != '9'}">
						<div class="btn_bg1" id="tab1_save" style="cursor:pointer;" onClick="fn_tab1();">저장</div>
					</c:if>	
				</sec:authorize>
				<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR" >
					<div class="btn_bg1" id="tab1_save" style="cursor:pointer;" onClick="fn_tab1();">저장</div>
				</sec:authorize>
				<c:if test="${PkgModel.status == '0' && PkgModel.pkg_seq != '' && (PkgModel.master_file_id == '' || PkgModel.pkg_detail_count == 0)}">
					<div class="btn_bg1" style="cursor:pointer;" onClick="fn_pkg_1_urgency();">긴급검증요청</div>
				</c:if>
				
				<div class="btn_bg1" style="cursor:pointer;" onClick="fn_print('main');">PKG 요약</div>
				<div class="btn_bg1" style="cursor:pointer;" onClick="fn_print_dev();">개발검증 요약</div>
				<!--기본정보,상세정보 끝 -->
				<!-- <div class="tab1_edge_cen"></div> -->
			</div>

			<!-- pkg > Tab2(첨부파일) -->
			<div id="pkg_tab_2" class="pop_con_Div5" style="display: none; height: auto;">
				<!--타이틀 -->
				<div class="pop_tbl_tit">검증내역</div>

				<!--기본정보, 상세정보 div 시작 -->
				<div id="pkg_tab_2_data" class="pop_basetbl" style="overflow-y:auto; height: auto;">
					<!--기본정보 -->
					
					<form:hidden path="master_file_id" />
					<form:hidden path="deleteList"/>
					
					<form:hidden path="col1" />
					<form:hidden path="col2" />
					<form:hidden path="col3" />
					<form:hidden path="col5" />
					<form:hidden path="col7" />
					<form:hidden path="col9" />
					<form:hidden path="col11" />
					<form:hidden path="col13" />
					<form:hidden path="col15" />
					<form:hidden path="col17" />
					<form:hidden path="col19" />
					<form:hidden path="col21" />
					<form:hidden path="col23" />
					<form:hidden path="col25" />
					
					<form:hidden path="col33" />
					<form:hidden path="col34" />
					<form:hidden path="col35" />
					<form:hidden path="col36" />
					<form:hidden path="col37" />
					<form:hidden path="col38" />
					<form:hidden path="col40" />
					<form:hidden path="col42" />
					
					
					
					<ul class="acc" id="acc">
						<li>
							<h3 class="">공급사 자체 검증 <a class="btn-close" href="javascript:div_open_close('1')" id="oc_1">닫기</a></h3>
							<div id="acc_1" class="acc-section">
					
									<table class="pop_tbl_type1" style="border-spacing: 0px">
										<colgroup>
											<col width="200" />
											<col width="*" />
										</colgroup>
										<tbody>
										<tr>
											<th scope="col" >보완내역별 시험 결과 <span class='necessariness'>*</span></th>
											<td>
												<form:input path="col4" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file37}" name="file37" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file37}" name="file37" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
													
													
												</div>
											</td>
										</tr>
										<tr>
											<th scope="col">Regression Test 및<br/>기본 검증 결과 <span class='necessariness'>*</span></th>
											<td>
												<form:input path="col6" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file5}" name="file5" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file5}" name="file5" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										<tr>
											<th scope="col">성능 용량 시험 결과</th>
											<td>
												<form:input path="col8" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<ui:file attachFileModel="${PkgModel.file6}" name="file6" size="50" mode="update" />
														</c:when>
														<c:otherwise>
															<ui:file attachFileModel="${PkgModel.file6}" name="file6" size="50" mode="create" />
														</c:otherwise>
													</c:choose>
												</div>
											</td>
										</tr>
										</tbody>
									</table>
							</div>
						</li>
						
						<li>
							<h3 class="mag_top1">개발 검증 <a class="btn-close" href="javascript:div_open_close('2')" id="oc_2">닫기</a></h3>
							<div id="acc_2" class="acc-section" >
					
									<table class="pop_tbl_type1" style="width:825px; border-spacing: 0px">
										<colgroup>
											<col width="200" />
											<col width="*"/>
										</colgroup>
										<tbody>
										<tr>
											<th scope="col">개발 검증 일자</th>
											<td>
												<form:input path="col27" maxlength="50"  class="new_inp inp_w30 fl_left"  readonly="true" />
												<span class="fl_left mg05"> ~ </span> 
												<form:input path="col28" maxlength="50"  class="new_inp inp_w30 fl_left" readonly="true" />
											</td>
										</tr>
										<tr>
											<th scope="col">개발 근거 문서</th>
											<td>
												<form:input path="col29" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_29_1"><ui:file attachFileModel="${PkgModel.file40}" name="file40" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_29_1"><ui:file attachFileModel="${PkgModel.file40}" name="file40" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file41 == null }">
																<div id="display_29_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file41}" name="file41" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_29_2"><br/><ui:file attachFileModel="${PkgModel.file41}" name="file41" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_29_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file41}" name="file41" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file42 == null }">
																<div id="display_29_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file42}" name="file42" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_29_3"><br/><ui:file attachFileModel="${PkgModel.file42}" name="file42" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_29_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file42}" name="file42" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file43 == null }">
																<div id="display_29_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file43}" name="file43" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_29_4"><br/><ui:file attachFileModel="${PkgModel.file43}" name="file43" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_29_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file43}" name="file43" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file44 == null }">
																<div id="display_29_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file44}" name="file44" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_29_5"><br/><ui:file attachFileModel="${PkgModel.file44}" name="file44" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_29_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file44}" name="file44" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
												
												<p class="mg05 fl_left">
													<select id="selID29" onchange="fnSelBox29();">
														<c:forEach var="selectItem" begin="1" end="5" step="1">
															<c:choose>
																<c:when test="${PkgModel.file40 == null }">
																	<option><c:out value="${selectItem}" /></option>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${PkgModel.file41 == null }">
																			<option><c:out value="${selectItem}" /></option>
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${PkgModel.file42 == null }">
																					<c:if test="${status.index != 2}">
																						<option><c:out value="${selectItem}" /></option>
																					</c:if>
																					<c:if test="${status.index == 2}">
																						<option selected><c:out value="2" /></option>
																					</c:if>
																				</c:when>
																				<c:otherwise>
																					<c:choose>
																						<c:when test="${PkgModel.file43 == null }">
																							<c:if test="${status.index != 3}">
																								<option><c:out value="${selectItem}" /></option>
																							</c:if>
																							<c:if test="${status.index == 3}">
																								<option selected><c:out value="3" /></option>
																							</c:if>
																						</c:when>
																						<c:otherwise>
																							<c:choose>
																								<c:when test="${PkgModel.file44 == null }">
																									<c:if test="${status.index != 4}">
																										<option><c:out value="${selectItem}" /></option>
																									</c:if>
																									<c:if test="${status.index == 4}">
																										<option selected><c:out value="4" /></option>
																									</c:if>
																								</c:when>
																								<c:otherwise>
																									<option selected><c:out value="${selectItem}" /></option>
																								</c:otherwise>
																							</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																	</c:choose>		
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</p>
												
											</td>
										</tr>
										<tr>
											<th scope="col">신규 기능 규격서</th>
											<td>
												<form:input path="col10" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_10_1"><ui:file attachFileModel="${PkgModel.file7}" name="file7" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_10_1"><ui:file attachFileModel="${PkgModel.file7}" name="file7" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file45 == null }">
																<div id="display_10_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file45}" name="file45" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_10_2"><br/><ui:file attachFileModel="${PkgModel.file45}" name="file45" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_10_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file45}" name="file45" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file46 == null }">
																<div id="display_10_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file46}" name="file46" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_10_3"><br/><ui:file attachFileModel="${PkgModel.file46}" name="file46" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_10_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file46}" name="file46" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file47 == null }">
																<div id="display_10_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file47}" name="file47" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_10_4"><br/><ui:file attachFileModel="${PkgModel.file47}" name="file47" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_10_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file47}" name="file47" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file48 == null }">
																<div id="display_10_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file48}" name="file48" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_10_5"><br/><ui:file attachFileModel="${PkgModel.file48}" name="file48" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_10_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file48}" name="file48" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
												<p class="mg05 fl_left">
													<select id="selID10" onchange="fnSelBox10();">
														<c:forEach var="selectItem" begin="1" end="5" step="1" varStatus="status">
															<c:choose>
																<c:when test="${PkgModel.file7 == null }">
																	<option><c:out value="${selectItem}" /></option>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${PkgModel.file45 == null }">
																			<option><c:out value="${selectItem}" /></option>
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${PkgModel.file46 == null }">
																					<c:if test="${status.index != 2}">
																						<option><c:out value="${selectItem}" /></option>
																					</c:if>
																					<c:if test="${status.index == 2}">
																						<option selected><c:out value="2" /></option>
																					</c:if>
																				</c:when>
																				<c:otherwise>
																					<c:choose>
																						<c:when test="${PkgModel.file47 == null }">
																							<c:if test="${status.index != 3}">
																								<option><c:out value="${selectItem}" /></option>
																							</c:if>
																							<c:if test="${status.index == 3}">
																								<option selected><c:out value="3" /></option>
																							</c:if>
																						</c:when>
																						<c:otherwise>
																							<c:choose>
																								<c:when test="${PkgModel.file48 == null }">
																									<c:if test="${status.index != 4}">
																										<option><c:out value="${selectItem}" /></option>
																									</c:if>
																									<c:if test="${status.index == 4}">
																										<option selected><c:out value="4" /></option>
																									</c:if>
																								</c:when>
																								<c:otherwise>
																									<option selected><c:out value="${selectItem}" /></option>
																								</c:otherwise>
																							</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																	</c:choose>		
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</p>
												
												
											</td>
										</tr>
										<tr>
											<th scope="col" >보완 내역서</th>
											<td>
												<form:input path="col30" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_30_1"><ui:file attachFileModel="${PkgModel.file49}" name="file49" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_30_1"><ui:file attachFileModel="${PkgModel.file49}" name="file49" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file50 == null }">
																<div id="display_30_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file50}" name="file50" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_30_2"><br/><ui:file attachFileModel="${PkgModel.file50}" name="file50" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_30_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file50}" name="file50" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file51 == null }">
																<div id="display_30_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file51}" name="file51" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_30_3"><br/><ui:file attachFileModel="${PkgModel.file51}" name="file51" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_30_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file51}" name="file51" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file52 == null }">
																<div id="display_30_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file52}" name="file52" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_30_4"><br/><ui:file attachFileModel="${PkgModel.file52}" name="file52" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_30_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file52}" name="file52" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file53 == null }">
																<div id="display_30_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file53}" name="file53" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_30_5"><br/><ui:file attachFileModel="${PkgModel.file53}" name="file53" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_30_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file53}" name="file53" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
												<p class="mg05 fl_left">
													<select id="selID30" onchange="fnSelBox30();">
														<c:forEach var="selectItem" begin="1" end="5" step="1">
															<c:choose>
																<c:when test="${PkgModel.file49 == null }">
																	<option><c:out value="${selectItem}" /></option>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${PkgModel.file50 == null }">
																			<option><c:out value="${selectItem}" /></option>
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${PkgModel.file51 == null }">
																					<c:if test="${status.index != 2}">
																						<option><c:out value="${selectItem}" /></option>
																					</c:if>
																					<c:if test="${status.index == 2}">
																						<option selected><c:out value="2" /></option>
																					</c:if>
																				</c:when>
																				<c:otherwise>
																					<c:choose>
																						<c:when test="${PkgModel.file52 == null }">
																							<c:if test="${status.index != 3}">
																								<option><c:out value="${selectItem}" /></option>
																							</c:if>
																							<c:if test="${status.index == 3}">
																								<option selected><c:out value="3" /></option>
																							</c:if>
																						</c:when>
																						<c:otherwise>
																							<c:choose>
																								<c:when test="${PkgModel.file53 == null }">
																									<c:if test="${status.index != 4}">
																										<option><c:out value="${selectItem}" /></option>
																									</c:if>
																									<c:if test="${status.index == 4}">
																										<option selected><c:out value="4" /></option>
																									</c:if>
																								</c:when>
																								<c:otherwise>
																									<option selected><c:out value="${selectItem}" /></option>
																								</c:otherwise>
																							</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																	</c:choose>		
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</p>
												
												
											</td>
										</tr>
										<tr>
											<th scope="col">시험 절차서 </th>
											<td>
												<form:input path="col31" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_31_1"><ui:file attachFileModel="${PkgModel.file54}" name="file54" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_31_1"><ui:file attachFileModel="${PkgModel.file54}" name="file54" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file55 == null }">
																<div id="display_31_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file55}" name="file55" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_31_2"><br/><ui:file attachFileModel="${PkgModel.file55}" name="file55" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_31_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file55}" name="file55" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file56 == null }">
																<div id="display_31_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file56}" name="file56" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_31_3"><br/><ui:file attachFileModel="${PkgModel.file56}" name="file56" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_31_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file56}" name="file56" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file57 == null }">
																<div id="display_31_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file57}" name="file57" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_31_4"><br/><ui:file attachFileModel="${PkgModel.file57}" name="file57" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_31_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file57}" name="file57" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file58 == null }">
																<div id="display_31_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file58}" name="file58" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_31_5"><br/><ui:file attachFileModel="${PkgModel.file58}" name="file58" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_31_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file58}" name="file58" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
												<p class="mg05 fl_left">
													<select id="selID31" onchange="fnSelBox31();">
														<c:forEach var="selectItem" begin="1" end="5" step="1">
															<c:choose>
																<c:when test="${PkgModel.file54 == null }">
																	<option><c:out value="${selectItem}" /></option>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${PkgModel.file55 == null }">
																			<option><c:out value="${selectItem}" /></option>
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${PkgModel.file56 == null }">
																					<c:if test="${status.index != 2}">
																						<option><c:out value="${selectItem}" /></option>
																					</c:if>
																					<c:if test="${status.index == 2}">
																						<option selected><c:out value="2" /></option>
																					</c:if>
																				</c:when>
																				<c:otherwise>
																					<c:choose>
																						<c:when test="${PkgModel.file57 == null }">
																							<c:if test="${status.index != 3}">
																								<option><c:out value="${selectItem}" /></option>
																							</c:if>
																							<c:if test="${status.index == 3}">
																								<option selected><c:out value="3" /></option>
																							</c:if>
																						</c:when>
																						<c:otherwise>
																							<c:choose>
																								<c:when test="${PkgModel.file58 == null }">
																									<c:if test="${status.index != 4}">
																										<option><c:out value="${selectItem}" /></option>
																									</c:if>
																									<c:if test="${status.index == 4}">
																										<option selected><c:out value="4" /></option>
																									</c:if>
																								</c:when>
																								<c:otherwise>
																									<option selected><c:out value="${selectItem}" /></option>
																								</c:otherwise>
																							</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																	</c:choose>		
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</p>
											
											</td>
										</tr>
										<tr>
											<th scope="col">코드 리뷰 및 SW<br/>아키텍처 리뷰  </th>
											<td>
												<form:input path="col32" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_32_1"><ui:file attachFileModel="${PkgModel.file59}" name="file59" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_32_1"><ui:file attachFileModel="${PkgModel.file59}" name="file59" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file60 == null }">
																<div id="display_32_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file60}" name="file60" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_32_2"><br/><ui:file attachFileModel="${PkgModel.file60}" name="file60" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_32_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file60}" name="file60" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file61 == null }">
																<div id="display_32_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file61}" name="file61" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_32_3"><br/><ui:file attachFileModel="${PkgModel.file61}" name="file61" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_32_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file61}" name="file61" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file62 == null }">
																<div id="display_32_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file62}" name="file62" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_32_4"><br/><ui:file attachFileModel="${PkgModel.file62}" name="file62" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_32_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file62}" name="file62" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file63 == null }">
																<div id="display_32_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file63}" name="file63" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_32_5"><br/><ui:file attachFileModel="${PkgModel.file63}" name="file63" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_32_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file63}" name="file63" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
												<p class="mg05 fl_left">
													<select id="selID32" onchange="fnSelBox32();">
														<c:forEach var="selectItem" begin="1" end="5" step="1">
															<c:choose>
																<c:when test="${PkgModel.file59 == null }">
																	<option><c:out value="${selectItem}" /></option>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${PkgModel.file60 == null }">
																			<option><c:out value="${selectItem}" /></option>
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${PkgModel.file61 == null }">
																					<c:if test="${status.index != 2}">
																						<option><c:out value="${selectItem}" /></option>
																					</c:if>
																					<c:if test="${status.index == 2}">
																						<option selected><c:out value="2" /></option>
																					</c:if>
																				</c:when>
																				<c:otherwise>
																					<c:choose>
																						<c:when test="${PkgModel.file62 == null }">
																							<c:if test="${status.index != 3}">
																								<option><c:out value="${selectItem}" /></option>
																							</c:if>
																							<c:if test="${status.index == 3}">
																								<option selected><c:out value="3" /></option>
																							</c:if>
																						</c:when>
																						<c:otherwise>
																							<c:choose>
																								<c:when test="${PkgModel.file63 == null }">
																									<c:if test="${status.index != 4}">
																										<option><c:out value="${selectItem}" /></option>
																									</c:if>
																									<c:if test="${status.index == 4}">
																										<option selected><c:out value="4" /></option>
																									</c:if>
																								</c:when>
																								<c:otherwise>
																									<option selected><c:out value="${selectItem}" /></option>
																								</c:otherwise>
																							</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																	</c:choose>		
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</p>
												
											</td>
										</tr>
										<tr>
											<th scope="col">기능 검증 결과 </th>
											<td>
												<form:input path="col12" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
											
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_12_1"><ui:file attachFileModel="${PkgModel.file8}" name="file8" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_12_1"><ui:file attachFileModel="${PkgModel.file8}" name="file8" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file64 == null }">
																<div id="display_12_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file64}" name="file64" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_12_2"><br/><ui:file attachFileModel="${PkgModel.file64}" name="file64" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_12_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file64}" name="file64" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file65 == null }">
																<div id="display_12_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file65}" name="file65" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_12_3"><br/><ui:file attachFileModel="${PkgModel.file65}" name="file65" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_12_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file65}" name="file65" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file66 == null }">
																<div id="display_12_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file66}" name="file66" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_12_4"><br/><ui:file attachFileModel="${PkgModel.file66}" name="file66" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_12_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file66}" name="file66" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file67 == null }">
																<div id="display_12_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file67}" name="file67" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_12_5"><br/><ui:file attachFileModel="${PkgModel.file67}" name="file67" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_12_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file67}" name="file67" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
												<p class="mg05 fl_left">
													<select id="selID12" onchange="fnSelBox12();">
														<c:forEach var="selectItem" begin="1" end="5" step="1">
															<c:choose>
																<c:when test="${PkgModel.file8 == null }">
																	<option><c:out value="${selectItem}" /></option>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${PkgModel.file64 == null }">
																			<option><c:out value="${selectItem}" /></option>
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${PkgModel.file65== null }">
																					<c:if test="${status.index != 2}">
																						<option><c:out value="${selectItem}" /></option>
																					</c:if>
																					<c:if test="${status.index == 2}">
																						<option selected><c:out value="2" /></option>
																					</c:if>
																				</c:when>
																				<c:otherwise>
																					<c:choose>
																						<c:when test="${PkgModel.file66 == null }">
																							<c:if test="${status.index != 3}">
																								<option><c:out value="${selectItem}" /></option>
																							</c:if>
																							<c:if test="${status.index == 3}">
																								<option selected><c:out value="3" /></option>
																							</c:if>
																						</c:when>
																						<c:otherwise>
																							<c:choose>
																								<c:when test="${PkgModel.file67 == null }">
																									<c:if test="${status.index != 4}">
																										<option><c:out value="${selectItem}" /></option>
																									</c:if>
																									<c:if test="${status.index == 4}">
																										<option selected><c:out value="4" /></option>
																									</c:if>
																								</c:when>
																								<c:otherwise>
																									<option selected><c:out value="${selectItem}" /></option>
																								</c:otherwise>
																							</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																	</c:choose>		
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</p>
												
											</td>
										</tr>
										<tr>
											<th scope="col">성능용량 시험결과  </th>
											<td>
												<form:input path="col41" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												<div class="pop_system mt_5">
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<div id="display_41_1"><ui:file attachFileModel="${PkgModel.file69}" name="file69" size="50" mode="update" /></div>
													</c:when>
													<c:otherwise>
														<div id="display_41_1"><ui:file attachFileModel="${PkgModel.file69}" name="file69" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file70 == null }">
																<div id="display_41_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file70}" name="file70" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_41_2"><br/><ui:file attachFileModel="${PkgModel.file70}" name="file70" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_41_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file70}" name="file70" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file71 == null }">
																<div id="display_41_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file71}" name="file71" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_41_3"><br/><ui:file attachFileModel="${PkgModel.file71}" name="file71" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_41_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file71}" name="file71" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file72 == null }">
																<div id="display_41_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file72}" name="file72" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_41_4"><br/><ui:file attachFileModel="${PkgModel.file72}" name="file72" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_41_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file72}" name="file72" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${registerFlag == '수정'}">
														<c:choose>
															<c:when test="${PkgModel.file73 == null }">
																<div id="display_41_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file73}" name="file73" size="50" mode="update" /></div>
															</c:when>
															<c:otherwise>
																<div id="display_41_5"><br/><ui:file attachFileModel="${PkgModel.file73}" name="file73" size="50" mode="update" /></div>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<div id="display_41_5" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file73}" name="file73" size="50" mode="create" /></div>
													</c:otherwise>
												</c:choose>
												</div>
												<p class="mg05 fl_left">
													<select id="selID41" onchange="fnSelBox41();">
														<c:forEach var="selectItem" begin="1" end="5" step="1">
															<c:choose>
																<c:when test="${PkgModel.file69 == null }">
																	<option><c:out value="${selectItem}" /></option>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${PkgModel.file70 == null }">
																			<option><c:out value="${selectItem}" /></option>
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${PkgModel.file71 == null }">
																					<c:if test="${status.index != 2}">
																						<option><c:out value="${selectItem}" /></option>
																					</c:if>
																					<c:if test="${status.index == 2}">
																						<option selected><c:out value="2" /></option>
																					</c:if>
																				</c:when>
																				<c:otherwise>
																					<c:choose>
																						<c:when test="${PkgModel.file72 == null }">
																							<c:if test="${status.index != 3}">
																								<option><c:out value="${selectItem}" /></option>
																							</c:if>
																							<c:if test="${status.index == 3}">
																								<option selected><c:out value="3" /></option>
																							</c:if>
																						</c:when>
																						<c:otherwise>
																							<c:choose>
																								<c:when test="${PkgModel.file73 == null }">
																									<c:if test="${status.index != 4}">
																										<option><c:out value="${selectItem}" /></option>
																									</c:if>
																									<c:if test="${status.index == 4}">
																										<option selected><c:out value="4" /></option>
																									</c:if>
																								</c:when>
																								<c:otherwise>
																									<option selected><c:out value="${selectItem}" /></option>
																								</c:otherwise>
																							</c:choose>
																						</c:otherwise>
																					</c:choose>
																				</c:otherwise>
																			</c:choose>
																		</c:otherwise>
																	</c:choose>		
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</p>
											
												
												
											</td>
										</tr>
										</tbody>
									</table>
									
							</div>
						</li>
						<li>
							<h3 class="mag_top1">상용화 검증 <a class="btn-open" href="javascript:div_open_close('3');" id="oc_3">열기</a></h3>
							<div id="acc_3" class="acc-section">
							
					
										<table class="pop_tbl_type1" style="width:825px; border-spacing: 0px;">
											<colgroup>
												<col width="200" />
												<col width="*" />
											</colgroup>
											<tbody>
											<tr>
												<th scope="col">보완내역서, 기능 변경 요청서</th>
												<td>
													<form:input path="col14" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												
													<div class="pop_system mt_5">
														<c:choose>
															<c:when test="${registerFlag == '수정'}">
																<ui:file attachFileModel="${PkgModel.file3}" name="file3" size="50" mode="update" />
															</c:when>
															<c:otherwise>
																<ui:file attachFileModel="${PkgModel.file3}" name="file3" size="50" mode="create" />
															</c:otherwise>
														</c:choose>
													</div>
												</td>
											</tr>
											<tr>
												<th scope="col">보완내역별 검증 결과</th>
												<td>
													<form:input path="col16" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												
													<div class="pop_system mt_5">
														<c:choose>
															<c:when test="${registerFlag == '수정'}">
																<ui:file attachFileModel="${PkgModel.file38}" name="file38" size="50" mode="update" />
															</c:when>
															<c:otherwise>
																<ui:file attachFileModel="${PkgModel.file38}" name="file38" size="50" mode="create" />
															</c:otherwise>
														</c:choose>
													</div>
												</td>
											</tr>
											
											<tr>
												<th scope="col">서비스 영향도 (로밍 포함)</th>
												<td>
													<form:input path="col18" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												
													<div class="pop_system mt_5">
														<c:choose>
															<c:when test="${registerFlag == '수정'}">
																<ui:file attachFileModel="${PkgModel.file12}" name="file12" size="50" mode="update" />
															</c:when>
															<c:otherwise>
																<ui:file attachFileModel="${PkgModel.file12}" name="file12" size="50" mode="create" />
															</c:otherwise>
														</c:choose>
													</div>
												</td>
											</tr>
											
											<tr>
												<th scope="col">과금 영향도</th>
												<td>
													<form:input path="col20" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												
													<div class="pop_system mt_5">
														<c:choose>
															<c:when test="${registerFlag == '수정'}">
																<ui:file attachFileModel="${PkgModel.file39}" name="file39" size="50" mode="update" />
															</c:when>
															<c:otherwise>
																<ui:file attachFileModel="${PkgModel.file39}" name="file39" size="50" mode="create" />
															</c:otherwise>
														</c:choose>
													</div>
												</td>
											</tr>
											
											<tr>
												<th scope="col">작업절차서,<br/>S/W 블록 내역 (list 및 size)</th>
												<td>
													<form:input path="col22" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												
													<div class="pop_system mt_5">
														<c:choose>
															<c:when test="${registerFlag == '수정'}">
																<ui:file attachFileModel="${PkgModel.file1}" name="file1" size="50" mode="update" />
															</c:when>
															<c:otherwise>
																<ui:file attachFileModel="${PkgModel.file1}" name="file1" size="50" mode="create" />
															</c:otherwise>
														</c:choose>
													</div>
												</td>
											</tr>
											
											<tr>
												<th scope="col">PKG 적용 후 check list</th>
												<td>
													<form:input path="col24" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
													
													<div class="pop_system mt_5">
														<c:choose>
															<c:when test="${registerFlag == '수정'}">
																<ui:file attachFileModel="${PkgModel.file14}" name="file14" size="50" mode="update" />
															</c:when>
															<c:otherwise>
																<ui:file attachFileModel="${PkgModel.file14}" name="file14" size="50" mode="create" />
															</c:otherwise>
														</c:choose>
													</div>
												</td>
											</tr>
											
											<tr>
												<th scope="col">CoD/PoD 변경 사항,<br/>운용팀 공지사항</th>
												<td>
													<form:input path="col26" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												
													<div class="pop_system mt_5">
														<c:choose>
															<c:when test="${registerFlag == '수정'}">
																<ui:file attachFileModel="${PkgModel.file15}" name="file15" size="50" mode="update" />
															</c:when>
															<c:otherwise>
																<ui:file attachFileModel="${PkgModel.file15}" name="file15" size="50" mode="create" />
															</c:otherwise>
														</c:choose>
													</div>
												</td>
											</tr>
											
											<tr>
												<th scope="col">보안Guide 적용확인서 <span class='necessariness'>*</span></th>
												<td>
													<form:input path="col39" style="width:95%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
												
													<div class="pop_system mt_5">
														<c:choose>
															<c:when test="${registerFlag == '수정'}">
																<ui:file attachFileModel="${PkgModel.file68}" name="file68" size="50" mode="update" />
															</c:when>
															<c:otherwise>
																<ui:file attachFileModel="${PkgModel.file68}" name="file68" size="50" mode="create" />
															</c:otherwise>
														</c:choose>
													</div>
												</td>
											</tr>
											
											</tbody>
										</table>
										
								</div>
							</li>
							<li>
							<h3 class="mag_top1">추가첨부  <a class="btn-open" href="javascript:div_open_close('4');" id="oc_4">열기</a></h3>
							<div id="acc_4" class="acc-section mb05">
					
										<table class="pop_tbl_type4" style="width:825px; border-spacing: 0px;">
											<colgroup>
												<col width="200" />
												<col width="*" />
											</colgroup>
											<tr>
												<c:choose>
													<c:when test="${PkgModel.file27 == null }">
														<th id="selectRowspan" scope="col" height="30" rowspan="2">추가첨부</th>
													</c:when>
													<c:otherwise>
														<th id="selectRowspan" scope="col" height="30" rowspan="6">추가첨부</th>
													</c:otherwise>
												</c:choose>
												<td style="padding:6px 10px 4px;border-top:1px solid #e5e5e5; color:#4c4c4c;">
													<select id="selectID" onchange="fnSelectBoxCnt();">
														<c:forEach var="selectItem" begin="1" end="10" step="1">
															<c:choose>
																<c:when test="${PkgModel.file27 == null }">
																	<option><c:out value="${selectItem}" /></option>
																</c:when>
																<c:otherwise>
																	<option selected><c:out value="${selectItem}" /></option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
												</td>
												<td style="padding:6px 10px 4px;border-top:1px solid #e5e5e5; color:#4c4c4c;"></td>
											</tr>
											<tr>
												<td>
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<div id="display_file1"><ui:file attachFileModel="${PkgModel.file27}" name="file27" size="17" mode="update" /></div>
														</c:when>
														<c:otherwise>
															<div id="display_file1"><ui:file attachFileModel="${PkgModel.file27}" name="file27" size="17" mode="create" /></div>
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<c:choose>
																<c:when test="${PkgModel.file27 == null }">
																	<div id="display_file2" style="display:none;"><ui:file attachFileModel="${PkgModel.file28}" name="file28" size="17" mode="update" /></div>
																</c:when>
																<c:otherwise>
																	<div id="display_file2"><ui:file attachFileModel="${PkgModel.file28}" name="file28" size="17" mode="update" /></div>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<div id="display_file2" style="display:none;"><ui:file attachFileModel="${PkgModel.file28}" name="file28" size="17" mode="create" /></div>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
											<tr>
												<td>
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<c:choose>
																<c:when test="${PkgModel.file27 == null }">
																	<div id="display_file3" style="display:none;"><ui:file attachFileModel="${PkgModel.file29}" name="file29" size="17" mode="update" /></div>
																</c:when>
																<c:otherwise>
																	<div id="display_file3"><ui:file attachFileModel="${PkgModel.file29}" name="file29" size="17" mode="update" /></div>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<div id="display_file3" style="display:none;"><ui:file attachFileModel="${PkgModel.file29}" name="file29" size="17" mode="create" /></div>
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<c:choose>
																<c:when test="${PkgModel.file27 == null }">
																	<div id="display_file4" style="display:none;"><ui:file attachFileModel="${PkgModel.file30}" name="file30" size="17" mode="update" /></div>
																</c:when>
																<c:otherwise>
																	<div id="display_file4"><ui:file attachFileModel="${PkgModel.file30}" name="file30" size="17" mode="update" /></div>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<div id="display_file4" style="display:none;"><ui:file attachFileModel="${PkgModel.file30}" name="file30" size="17" mode="create" /></div>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
											<tr>
												<td>
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<c:choose>
																<c:when test="${PkgModel.file27 == null }">
																	<div id="display_file5" style="display:none;"><ui:file attachFileModel="${PkgModel.file31}" name="file31" size="17" mode="update" /></div>
																</c:when>
																<c:otherwise>
																	<div id="display_file5"><ui:file attachFileModel="${PkgModel.file31}" name="file31" size="17" mode="update" /></div>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<div id="display_file5" style="display:none;"><ui:file attachFileModel="${PkgModel.file31}" name="file31" size="17" mode="create" /></div>
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<c:choose>
																<c:when test="${PkgModel.file27 == null }">
																	<div id="display_file6" style="display:none;"><ui:file attachFileModel="${PkgModel.file32}" name="file32" size="17" mode="update" /></div>
																</c:when>
																<c:otherwise>
																	<div id="display_file6"><ui:file attachFileModel="${PkgModel.file32}" name="file32" size="17" mode="update" /></div>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<div id="display_file6" style="display:none;"><ui:file attachFileModel="${PkgModel.file32}" name="file32" size="17" mode="create" /></div>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
											<tr>
												<td>
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<c:choose>
																<c:when test="${PkgModel.file27 == null }">
																	<div id="display_file7" style="display:none;"><ui:file attachFileModel="${PkgModel.file33}" name="file33" size="17" mode="update" /></div>
																</c:when>
																<c:otherwise>
																	<div id="display_file7"><ui:file attachFileModel="${PkgModel.file33}" name="file33" size="17" mode="update" /></div>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<div id="display_file7" style="display:none;"><ui:file attachFileModel="${PkgModel.file33}" name="file33" size="17" mode="create" /></div>
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<c:choose>
																<c:when test="${PkgModel.file27 == null }">
																	<div id="display_file8" style="display:none;"><ui:file attachFileModel="${PkgModel.file34}" name="file34" size="17" mode="update" /></div>
																</c:when>
																<c:otherwise>
																	<div id="display_file8"><ui:file attachFileModel="${PkgModel.file34}" name="file34" size="17" mode="update" /></div>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<div id="display_file8" style="display:none;"><ui:file attachFileModel="${PkgModel.file34}" name="file34" size="17" mode="create" /></div>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
											<tr>
												<td>
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<c:choose>
																<c:when test="${PkgModel.file27 == null }">
																	<div id="display_file9" style="display:none;"><ui:file attachFileModel="${PkgModel.file35}" name="file35" size="17" mode="update" /></div>
																</c:when>
																<c:otherwise>
																	<div id="display_file9"><ui:file attachFileModel="${PkgModel.file35}" name="file35" size="17" mode="update" /></div>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<div id="display_file9" style="display:none;"><ui:file attachFileModel="${PkgModel.file35}" name="file35" size="17" mode="create" /></div>
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${registerFlag == '수정'}">
															<c:choose>
																<c:when test="${PkgModel.file27 == null }">
																	<div id="display_file10" style="display:none;"><ui:file attachFileModel="${PkgModel.file36}" name="file36" size="17" mode="update" /></div>
																</c:when>
																<c:otherwise>
																	<div id="display_file10"><ui:file attachFileModel="${PkgModel.file36}" name="file36" size="17" mode="update" /></div>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<div id="display_file10" style="display:none;"><ui:file attachFileModel="${PkgModel.file36}" name="file36" size="17" mode="create" /></div>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>						
										</table>
									
							</div>
						</li>
					</ul>

				</div>
				<!--저장버튼 -->
				<div id="tab2_comment" class="help_notice">
					기본정보를 등록하시면 첨부파일을 등록하실 수 있습니다.
				</div>
				<sec:authorize ifAnyGranted = "ROLE_BP">
					<c:if test="${PkgModel.status != '7' && PkgModel.status != '8' && PkgModel.status != '9'}">
						<div id="tab2_save" class="btn_bg1" style="cursor:pointer; display:none;" onClick="fn_tab2();">저장</div>
					</c:if>	
				</sec:authorize>
				<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR" >
					<div id="tab2_save" class="btn_bg1" style="cursor:pointer; display:none;" onClick="fn_tab2();">저장</div>
				</sec:authorize>
				
				<!--기본정보,상세정보 끝 -->
<!-- 				<div class="tab1_edge_cen2"></div> -->
			</div>
<!--  -->

			<!-- pkg 보완적용내역 -->
			<div id="pkg_tab_3" class="pop_con_Div5">

				<!--타이틀 -->
				<div class="pop_tbl_tit">보완적용내역</div>
				<div class="pop_basetbl2">
					<!--버튼 -->
					<div id="tab3_upload_init" class="pop_btn_location1">
						<span><img src="/images/pop_btn_templetdown.gif" alt="템플릿다운로드" style="cursor:pointer;" onClick="fn_template_download();"></span>

						<!--조회결과 -->
						<div class="pop_resultbox">
							<div class="pop_result">기본정보를 등록하시면 엑셀업로드가 가능합니다.</div>
						</div>
					</div>

					<div id="tab3_upload_update" class="pop_btn_location1" style="display:none;">
						<div id="excel_button_exist" style="display:none">
							<span><img src="/images/pop_btn_datadown.gif" alt="등록된 데이터 다운로드" style="cursor:pointer;" id="open_3_2" onclick="fn_excel_download();"></span>
							<span><img src="/images/pop_btn_templetdown.gif" alt="템플릿다운로드" style="cursor:pointer;" id="open_3_3" onclick="fn_template_download();"></span>
							
							<sec:authorize ifAnyGranted = "ROLE_BP">
								<c:if test="${PkgModel.status != '7' && PkgModel.status != '8' && PkgModel.status != '9'}">
									<span id="tab3_upload_update_excel"><img src="/images/pop_btn_excelup.gif" alt="엑셀업로드" style="cursor:pointer;" id="open_3_1"></span>
								</c:if>								
							</sec:authorize>
							<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR" >
								<span id="tab3_upload_update_excel"><img src="/images/pop_btn_excelup.gif" alt="엑셀업로드" style="cursor:pointer;" id="open_3_1"></span>
							</sec:authorize>
							
							<span id="tab3_upload_update_oknet"><a href="http://edds.sktelecom.com/" target="_new"><img src="/images/pop_btn_securityclear.gif" alt="문서보안링크" style="cursor:pointer;" id="open_3_4"></a></span>
						</div>
						<div id="excel_button_none" style="display:block">
							<span><img src="/images/pop_btn_templetdown.gif" alt="템플릿다운로드" style="cursor:pointer;" id="open_3_3" onclick="fn_template_download();"></span>
							<span><img src="/images/pop_btn_excelup.gif" alt="엑셀업로드" style="cursor:pointer;" id="open_3_1"></span>
							<span id="excel_button_none_oknet" ><a href="http://edds.sktelecom.com/" target="_new"><img src="/images/pop_btn_securityclear.gif" alt="문서보안링크" style="cursor:pointer;" id="open_3_4"></a></span>
						</div>

						<!--조회결과 -->
						<div class="pop_resultbox"> 
							<div class="pop_result_none" id="pop_result_none" style="display:block">템플릿을 다운받아 작성 후 업로드 하시면 자동 등록됩니다. <br/>반드시 사용가능 템플릿 버전을 사용하셔야 합니다. <br/><font color="red">[사용가능 템플릿 버전: ${PkgModel.useY_tpl_ver }]</font></div>
							<div class="pop_result" id="pop_result" style="display:none">총 <span id="pkg_detail_count_view"></span>건의 보완적용내역이 있습니다. <font color="red" id="apply_tpl_ver">[적용된 템플릿 버전: ${PkgModel.tpl_ver }]</font></div>
						</div>
					</div>
					
			<!-- 말풍선 내용  pkg 상용화검증내역 말풍선 -->
 			<div id="tip_message_mapp" class="tip_message_mapp" style="visibility:hidden;">
				<table style="border: 3px solid #F9E98E; color: #A27D35; background-color: #FBF7AA;">
					<tr>
						<td width="200px" height="100px">
							&nbsp;PN/CR매핑은 신규,보완,개선 분류에 따라 완료된 해당 PN/CR 목록이 보입니다. 
							<br/>&nbsp;보완적용내역과 매핑할 PN/CR이 있다면 클릭후 매핑하시면 됩니다.
						</td>
					</tr>
				</table>
			</div> 
					<!-- pkg 보완적용내역 상세, 신규/보안/개선 매핑 -->
					
					<div id="pkg_tab_3_2" style="display:none;">
					
						<div>
							<h3 class="table_ttl">보완적용내역 목록</h3>
							<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR" >
								<div style="float:right; margin-right:20px;"><span><a href="javascript:fn_radio_update();"><img src="/images/btn_save.gif" alt="저장" /></a></span></div>
							</sec:authorize>
						</div>
							
						<div class="fakeContainer" style="width:825px;height:240px;border:1px solid #ddd">
							<table id="pkg_tab_3_2_table" class="pop_tbl_type3">
								<colgroup>
									<col width="5%">
									<col width="5%">
									<col width="5%">
									<col width="5%">
									<col width="*">
									<col width="20%">
									<col width="20%">
									<col width="10%">
									<col width="10%">
								</colgroup>
								<thead>
								<tr>
									<th scope="col">PN/CR 매핑</th>
									<c:forEach var="result" items="${PkgModel.tempmgModelList}" varStatus="status">
										<c:if test="${status.index < 5}">
											<th scope="col">${result.title}</th>
										</c:if>
									</c:forEach>
									<c:forEach var="result" items="${PkgModel.tempmgModelList}" varStatus="status">
										<c:if test="${status.index == 21}">
											<th scope="col">${result.title}</th>
										</c:if>
									</c:forEach>
									<c:forEach var="result" items="${PkgModel.tempmgModelList}" varStatus="status">
										<c:if test="${18 < status.index and status.index < 21}">
											<th scope="col">${result.title}</th>
										</c:if>
									</c:forEach>
								</tr>
								</thead>
								<tbody>
							<c:forEach var="result" items="${PkgModel.pkgDetailVariableModelList}" varStatus="st">
								<c:set var="rt" value="${PkgModel.pkgDetailModelList[st.index]}" />
								<tr>
								<c:choose>
								    <c:when test="${rt.new_pn_cr_seq == 0}">
										<td id="td_detail_no_${rt.pkg_detail_seq}" class="td_detail_no_${rt.no} td_no2" style=" background-color:#FFDEA9; cursor:pointer;" onclick="fn_newpncr_click('${rt.pkg_detail_seq}', '${rt.no}', '${rt.new_pn_cr_gubun}', '');" onMouseOver="tip_message_over('tip_message_mapp')" onMouseMove="tip_message_over('tip_message_mapp')" onMouseOut="tip_message_out('tip_message_mapp')">
											<img id="img_detail_no_${rt.no}" src="/images/ico_maping_no.png" />
										</td>
								    </c:when>
								    <c:otherwise>
										<td id="td_detail_no_${rt.pkg_detail_seq}" class="td_detail_no_${rt.no} td_no3" style=" background-color:white; cursor:pointer;" onclick="fn_newpncr_click('${rt.pkg_detail_seq}', '${rt.no}', '${rt.new_pn_cr_gubun}', '${rt.new_pn_cr_seq}');" onMouseOver="tip_message_over('tip_message_mapp')" onMouseMove="tip_message_over('tip_message_mapp')" onMouseOut="tip_message_out('tip_message_mapp')">
											${rt.new_pn_cr_seq}
										</td>
								    </c:otherwise>
								</c:choose>
								
									<td>${rt.no}</td>
									<td>${rt.new_pn_cr_gubun}</td>
								<!-- ****************************라디오 버튼 시작**************************** -->	
								<c:forEach var="rt_sub" items="${PkgModel.pkgDetailVariableModelList[st.index]}" varStatus="st_sub">
									<c:if test="${st_sub.index < 3 }">
										<c:choose>
											<c:when test="${st_sub.index == 2}">
												<td>
													<span>
														<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'OK'}">checked</c:if> onclick="fn_detail_radio('${rt_sub.pkg_detail_seq}','ok')" id="${rt_sub.pkg_detail_seq}_ok" value="${rt_sub.pkg_detail_seq}" class="radio_OK "/>
														<label>OK</label>
													</span>
													<span>
														<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'NOK'}">checked</c:if> onclick="fn_detail_radio('${rt_sub.pkg_detail_seq}','nok')" id="${rt_sub.pkg_detail_seq}_nok" value="${rt_sub.pkg_detail_seq}" class="radio_NOK"/>
														<label>NOK</label>
													</span>
													<span>
														<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'COK'}">checked</c:if> onclick="fn_detail_radio('${rt_sub.pkg_detail_seq}','cok')" id="${rt_sub.pkg_detail_seq}_cok" value="${rt_sub.pkg_detail_seq}" class="radio_COK"/>
														<label>COK</label>
													</span>
												</td>
											</c:when>
											<c:otherwise>
												<td id="td_detail_variable_${rt_sub.pkg_detail_seq}_${st_sub.index}" class="td_detail_variable_${rt_sub.pkg_detail_seq}_${st_sub.index}" style="cursor:pointer; ${(st_sub.index == 0) ? 'text-align:center' : 'text-align:left; padding:0 0 0 5px;' }" onclick="fn_detail_variable_click('${rt_sub.pkg_detail_seq}');">
														<c:out value="${fn:substring(rt_sub.content, 0 , 35)}" />
														<c:out value="${fn:length(rt_sub.content) > 35 ? '...' : ''}" />
												</td>
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${st_sub.index == 19}">
										<td style="text-align: center;">
											<span>
												<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'OK'}">checked</c:if> onclick="fn_detail_radio_dev('${rt_sub.pkg_detail_seq}','ok')" id="${rt_sub.pkg_detail_seq}_ok_dev" value="${rt_sub.pkg_detail_seq}" class="radio_OK_dev"/>
												<label>OK</label>
											</span>	
											<span>
												<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'NOK'}">checked</c:if> onclick="fn_detail_radio_dev('${rt_sub.pkg_detail_seq}','nok')" id="${rt_sub.pkg_detail_seq}_nok_dev" value="${rt_sub.pkg_detail_seq}" class="radio_NOK_dev"/>
												<label>NOK</label>
											</span>
											<span>
												<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'COK'}">checked</c:if> onclick="fn_detail_radio_dev('${rt_sub.pkg_detail_seq}','cok')" id="${rt_sub.pkg_detail_seq}_cok_dev" value="${rt_sub.pkg_detail_seq}" class="radio_COK_dev"/>
												<label>COK</label>
											</span>
											<span>
												<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'BYPASS'}">checked</c:if> onclick="fn_detail_radio_dev('${rt_sub.pkg_detail_seq}','bypass')" id="${rt_sub.pkg_detail_seq}_bypass_dev" value="${rt_sub.pkg_detail_seq}" class="radio_BYPASS_dev"/>
												<label>BYPASS</label>
											</span>
										</td>
									</c:if>
								</c:forEach>
								<c:forEach var="rt_sub" items="${PkgModel.pkgDetailVariableModelList[st.index]}" varStatus="st_sub">									
									<c:if test="${16 < st_sub.index and 19 > st_sub.index}">
										<td id="td_detail_variable_${rt_sub.pkg_detail_seq}_${st_sub.index}" class="td_detail_variable_${rt_sub.pkg_detail_seq}_${st_sub.index}" style="cursor:pointer; ${(st_sub.index == 0) ? 'text-align:center;' : 'text-align:center; padding:0 0 0 5px;' }" onclick="fn_detail_variable_click('${rt_sub.pkg_detail_seq}');">
												<c:out value="${fn:substring(rt_sub.content, 0 , 35)}" />
												<c:out value="${fn:length(rt_sub.content) > 35 ? '...' : ''}" />
										</td>
									</c:if>									
								</c:forEach>
								</tr>
							</c:forEach>
								</tbody>
							</table>
						</div>
					</div>

					<div id="pkg_tab_3_3" style="display:none;">
						<!--상세테이블 -->
						<div class="pop_detail" style="width:819px;height:320px;">
							<!--타이틀 -->
							<div class="pop_sm_title_1">신규/보안/개선 매핑 [<span id="newpncr_detail_no"></span>]</div>
	
							<!--조회영역 -->
							<div class="pop_search" style="width:813px">
								<table class="sear_table1" style="width:705px">
									<tr>
										<th>시스템</th>
										<td colspan="3"><span id="newpncr_system"></span></td>
									</tr>
									<tr>
										<th>유형</th>
										<td><form:select path="newPnCr" items="${PkgModel.new_pncr_gubunList}" itemLabel="codeName" itemValue="code" /></td>
										<th>제목</th>
										<td><form:input path="newPnCr_title" class="inp" /></td>
									</tr>
								</table>
	
								<!--조회버튼 -->
								<div class="pop_btn_sear" style="cursor:pointer;" onclick="fn_newpncr_readList();"></div>
							</div>
	
							<div style="width:820px; height:205px; overflow-x:hidden; overflow-y:auto">
								<table class="pop_tbl_type3">
									<thead>
									<tr>
										<th style="width:50px;">&nbsp;</th>
										<th style="width:70px;">번호</th>
										<th style="width:50px;">유형</th>
										<th>제목</th>
									</tr>
									</thead>
									<tbody id="newpncr_list_area">
									</tbody>
								</table>
							</div>
							<div class="btn_location_newpncr" id="tab3_3_save" style="display:none;">
								<sec:authorize ifAnyGranted = "ROLE_BP">
								<c:if test="${PkgModel.status != '7' && PkgModel.status != '8' && PkgModel.status != '9'}">
									<span id="btn_modify"><a href="javascript:fn_newpncr_update();"><img src="/images/btn_save.gif" alt="저장" /></a></span>
								</c:if>
								</sec:authorize>
								<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR" >
									<span id="btn_modify"><a href="javascript:fn_newpncr_update();"><img src="/images/btn_save.gif" alt="저장" /></a></span>
								</sec:authorize>
							</div>
						</div>
					</div>

					<div id="pkg_tab_3_4" style="display:none;height:440px;">
						<!--상세테이블 -->
						<div class="pop_detail" style="width:819px;height:390px;">
							<!--타이틀 -->
							<div class="pop_sm_title_1">
								상세정보
							</div>
							<div id="pkg_tab_3_2_div" class="fakeContainer" style="width:805px;height:320px; overflow-x:scroll; overflow-y:hidden">
							</div>
							<div class="btn_location_pkg_detail" id="tab3_4_save" style="display:none;">
								<div class="help_notice">
									한 셀당 최대 1,200 자까지 입력 가능합니다.
								</div>
								<sec:authorize ifAnyGranted = "ROLE_BP">
								<c:if test="${PkgModel.status ne '4' && PkgModel.status ne '5' && PkgModel.status ne '6' &&
											  PkgModel.status ne '7' && PkgModel.status ne '8' && PkgModel.status ne '9'}">
									<span><a href="javascript:fn_detail_add_variable_click('add');"><img src="/images/btn_plus.gif" alt="추가" /></a></span>
									<span><a href="javascript:fn_detail_variable_update();"><img src="/images/btn_save.gif" alt="저장" /></a></span>
								</c:if>									
								</sec:authorize>
								<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR" >
									<span><a href="javascript:fn_detail_add_variable_click('add');"><img src="/images/btn_plus.gif" alt="추가" /></a></span>
									<span><a href="javascript:fn_detail_variable_update();"><img src="/images/btn_save.gif" alt="저장" /></a></span>
								</sec:authorize>
							</div>
						</div>
					</div>

				</div>
				<!-- 타이틀, 테이블 끝 -->
<!-- 				<div class="tab1_edge_cen3"></div> -->

				<!--기본정보,상세정보 끝 -->
<!-- 				<div class="tab1_edge_cen3"></div> -->
			</div>


			<!-- pkg 담당자 -->
			<div id="pkg_tab_4" class="pop_con_Div5" style="display:none;">
				<!--타이틀 -->
				<div class="pop_tbl_tit">담당자</div>

				<!--테이블 -->
				<div class="help_notice" id="pkg_user_comment">
					기본정보를 등록하시면 담당자 정보는 자동으로 조회됩니다.
				</div>
				
				<div class="pop_basetbl3" id="pkg_user_table">
					<table class="pop_tbl_type1" style="width: 100%; border-spacing: 0px;">
						<caption>시스템 개요</caption>
						<colgroup>
							<col width="110px;">
							<col width="*">
							<col width="110px;">
							<col width="*">
						</colgroup>
						<thead>
							<tr>
								<th style="width: 110px;">시스템명</th>
								<td>${SysModel.name}</td>
								<th>Full Name</th>
								<td>${SysModel.full_name}</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th>영향 시스템</th>
								<td>${SysModel.impact_systems}</td>
								<th>공급사</th>
								<td>${SysModel.supply}</td>
							</tr>
							<tr>
								<th>한줄설명</th>
								<td><textarea rows="3" style="width: 270px;" readonly="readonly" >${SysModel.oneLine_explain }</textarea></td>
								<th>비고</th>
								<td><textarea rows="3" style="width: 270px;" readonly="readonly">${SysModel.note }</textarea></td>
							</tr>
							<tr>
								<th>시스템 매뉴얼</th>
								<td>
									<ui:file attachFileModel="${SysModel.attachFile1}" name="attachFile1" size="50" mode="view" />
								</td>
								<th>PKG 표준절차서</th>
								<td>
									<ui:file attachFileModel="${SysModel.attachFile3}" name="attachFile3" size="50" mode="view" />
								</td>
							</tr>
							<tr>
								<th>교육자료</th>
								<td>
									${SysModel.edu_data}
									<c:if test="${SysModel.attachFile16 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile16}" name="attachFile16" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile17 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile17}" name="attachFile17" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile18 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile18}" name="attachFile18" size="50" mode="view" />
									</c:if>
								</td>
								<th>관련규격</th>
								<td>
									${SysModel.standard}
									<c:if test="${SysModel.attachFile2 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile2}" name="attachFile2" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile19 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile19}" name="attachFile19" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile20 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile20}" name="attachFile20" size="50" mode="view" />
									</c:if>
								</td>
							</tr>
							<tr>
								<th>성능용량</th>
								<td>
									${SysModel.per_capa}
									<c:if test="${SysModel.attachFile21 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile21}" name="attachFile21" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile22 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile22}" name="attachFile22" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile23 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile23}" name="attachFile23" size="50" mode="view" />
									</c:if>
								</td>
								<th>시설현황</th>
								<td>
									${SysModel.job_history}
									<c:if test="${SysModel.attachFile24 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile24}" name="attachFile24" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile25 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile25}" name="attachFile25" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile26 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile26}" name="attachFile26" size="50" mode="view" />
									</c:if>
								</td>
							</tr>
							<tr>
								<th>RM방안</th>
								<td>
									${SysModel.rm_plan}
									<c:if test="${SysModel.attachFile27 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile27}" name="attachFile27" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile28 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile28}" name="attachFile28" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile29 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile29}" name="attachFile29" size="50" mode="view" />
									</c:if>
								</td>
								<th>추가첨부</th>
								<td>
									<c:if test="${SysModel.attachFile6 != null }">
										<ui:file attachFileModel="${SysModel.attachFile6}" name="attachFile6" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile7 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile7}" name="attachFile7" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile8 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile8}" name="attachFile8" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile9 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile9}" name="attachFile9" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile10 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile10}" name="attachFile10" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile11 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile11}" name="attachFile11" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile12 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile12}" name="attachFile12" size="50" mode="view" />
									</c:if>
									<c:if test="${SysModel.attachFile13 != null }">
										<br/><ui:file attachFileModel="${SysModel.attachFile13}" name="attachFile13" size="50" mode="view" />
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="4" style="text-align:center;">
									<a style="font-weight:bold;color:red;" onclick="systemUpdateLink(${PkgModel.group1_seq }, ${PkgModel.group2_seq }, ${PkgModel.group3_seq }, ${PkgModel.system_seq })">시스템 수정하기</a>
								</td>
							</tr>
						</tbody>
					</table>
					<br/>					
					<table class="pop_tbl_type3" style="border-spacing: 0px;">
						<caption>사업계획 담당자</caption>
						<colgroup>
							<col width="120px">
							<col width="*">
							<col width="120px">
							<col width="200px">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">이름</th>
								<th scope="col">소속</th>
								<th scope="col">전화번호</th>
								<th scope="col">이메일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${PkgModel.systemUserModelList}" varStatus="status">
								<c:if test="${result.charge_gubun == 'PU'}">
									<tr>
										<td align="center">${result.user_name}&nbsp;</td>
										<td align="center">${result.sosok}&nbsp;</td>
										<td align="center">${result.user_phone}&nbsp;</td>
										<td align="center">${result.user_email}&nbsp;</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
					<br/>
					<table class="pop_tbl_type3" style="border-spacing: 0px;">
						<caption>개발검증 및 승인담당자</caption>
						<colgroup>
							<col width="100px">
							<col width="100px">
							<col width="*">
							<col width="120px">
							<col width="170px">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">담당</th>
								<th scope="col">이름</th>
								<th scope="col">소속</th>
								<th scope="col">전화번호</th>
								<th scope="col">이메일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${PkgModel.systemUserModelList}" varStatus="status">
								<c:if test="${result.charge_gubun == 'DV'}">
									<tr> 
										<td align="center">개발검증&nbsp;</td>
										<td align="center">${result.user_name}&nbsp;</td>
										<td align="center">${result.sosok}&nbsp;</td>
										<td align="center">${result.user_phone}&nbsp;</td>
										<td align="center">${result.user_email}&nbsp;</td>
									</tr>
								</c:if>
								<c:if test="${result.charge_gubun == 'DA'}">
									<tr> 
										<td align="center">개발승인&nbsp;</td>
										<td align="center">${result.user_name}&nbsp;</td>
										<td align="center">${result.sosok}&nbsp;</td>
										<td align="center">${result.user_phone}&nbsp;</td>
										<td align="center">${result.user_email}&nbsp;</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
					<br/>
					<table class="pop_tbl_type3" style="border-spacing: 0px;">
						<caption>상용검증 및 승인담당자</caption>
						<colgroup>
							<col width="100px">
							<col width="100px">
							<col width="*">
							<col width="120px">
							<col width="170px">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">담당</th>
								<th scope="col">이름</th>
								<th scope="col">소속</th>
								<th scope="col">전화번호</th>
								<th scope="col">이메일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${PkgModel.systemUserModelList}" varStatus="status">
								<c:if test="${result.charge_gubun == 'VU'}">
									<tr> 
										<td align="center">기능검증&nbsp;</td>
										<td align="center">${result.user_name}&nbsp;</td>
										<td align="center">${result.sosok}&nbsp;</td>
										<td align="center">${result.user_phone}&nbsp;</td>
										<td align="center">${result.user_email}&nbsp;</td>
									</tr>
								</c:if>
								<c:if test="${result.charge_gubun == 'VO'}">
									<tr>
										<td align="center">용량검증&nbsp;</td>
										<td align="center">${result.user_name}&nbsp;</td>
										<td align="center">${result.sosok}&nbsp;</td>
										<td align="center">${result.user_phone}&nbsp;</td>
										<td align="center">${result.user_email}&nbsp;</td>
									</tr>
								</c:if>
								<c:if test="${result.charge_gubun == 'SE'}">
									<tr>
										<td align="center">보안검증&nbsp;</td>
										<td align="center">${result.user_name}&nbsp;</td>
										<td align="center">${result.sosok}&nbsp;</td>
										<td align="center">${result.user_phone}&nbsp;</td>
										<td align="center">${result.user_email}&nbsp;</td>
									</tr>
								</c:if>
								<c:if test="${result.charge_gubun == 'CH'}">
									<tr>
										<td align="center">과금검증&nbsp;</td>
										<td align="center">${result.user_name}&nbsp;</td>
										<td align="center">${result.sosok}&nbsp;</td>
										<td align="center">${result.user_phone}&nbsp;</td>
										<td align="center">${result.user_email}&nbsp;</td>
									</tr>
								</c:if>
								<c:if test="${result.charge_gubun == 'NO'}">
									<tr>
										<td align="center">비기능검증&nbsp;</td>
										<td align="center">${result.user_name}&nbsp;</td>
										<td align="center">${result.sosok}&nbsp;</td>
										<td align="center">${result.user_phone}&nbsp;</td>
										<td align="center">${result.user_email}&nbsp;</td>
									</tr>
								</c:if>
								<c:if test="${result.charge_gubun == 'AU'}">
									<tr> 
										<td align="center">상용승인&nbsp;</td>
										<td align="center">${result.user_name}&nbsp;</td>
										<td align="center">${result.sosok}&nbsp;</td>
										<td align="center">${result.user_phone}&nbsp;</td>
										<td align="center">${result.user_email}&nbsp;</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
					<br/>
					<table class="pop_tbl_type3" style="border-spacing: 0px;">
						<caption>협력업체 담당자</caption>
						<colgroup>
							<col width="120px">
							<col width="*">
							<col width="120px">
							<col width="200px">
						</colgroup>
						<thead>
							<tr>
								<th scope="col">이름</th>
								<th scope="col">소속</th>
								<th scope="col">전화번호</th>
								<th scope="col">이메일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${PkgModel.systemUserModelList}" varStatus="status">
								<c:if test="${result.charge_gubun == 'BU'}">
									<tr>
										<td align="center">${result.user_name}&nbsp;</td>
										<td align="center">${result.sosok}&nbsp;</td>
										<td align="center">${result.user_phone}&nbsp;</td>
										<td align="center">${result.user_email}&nbsp;</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
					<br/>
					<table class="pop_tbl_type1" style="width: 845px; border-spacing: 0px;">
						<colgroup>
							<col width="150px;">
							<col width="*">
						</colgroup>
						<tr>
							<th scope="col">영업담당자 정보</th>
							
						</tr>
						<tr>
							<td align="center"><form:textarea path="sales_user_info" rows="5" class="inp_w1" style="width:98.5%;" readonly="readonly" /></td>
						</tr>
					</table>
					
					<table class="pop_tbl_type3 mag_top1; border-spacing: 0px;">
						<caption>운용 담당자</caption>
						<colgroup>
							<col width="200px">
							<col width="80px">
							<col width="*">
							<col width="120px">
							<col width="170px">
						</colgroup>
						<thead>
						<tr>
							<th scope="col">국사</th>
							<th scope="col">이름</th>
							<th scope="col">부서</th>
							<th scope="col">전화번호</th>
							<th scope="col">이메일</th>
						</tr>
						</thead>
						<tbody>
							<c:forEach var="entry" items="${IdcData.equipmentUserMap}" varStatus="status">
								<c:set var="sysModel" value="${entry.key}" />
								<c:forEach var="equipmentUserModel" items="${entry.value}" varStatus="mapStatus">
								<tr>
									<c:if test="${mapStatus.first}">
									<td align="center" rowspan="${fn:length(entry.value)}">${sysModel.idc_name}
									</c:if>
									<td align="center">${equipmentUserModel.user_name}&nbsp;</td>
									<td align="center">${equipmentUserModel.sosok}&nbsp;</td>
									<td align="center">${equipmentUserModel.user_phone}&nbsp;</td>
									<td align="center">${equipmentUserModel.user_email}&nbsp;</td>
								</tr>
								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
					<br/>
				</div>
				<!--기본정보,상세정보 끝 -->
<!-- 				<div class="tab1_edge_cen4"></div> -->
			</div>

			<div id="pkg_tab_flow" class="pop_con_Div9" style="float: right;">
				<ul id="flow_ul" class="pop_flow">
					<li id="pop_flow2"  class="pop_flow2_off"  title="상용검증 접수 및 계획"><!-- <a href="#"></a> --></li>
<!-- 					<li id="pop_flow25" class="pop_flow25_off"><a href="#"></a></li> -->
					<li id="pop_flow26" class="pop_flow26_off" title="검증계획 승인"><a href="#"></a></li>
					<li id="pop_flow3"  class="pop_flow3_off"  title="상용검증완료 "><!--<a href="#"></a>--></li>
					<li id="pop_flow4"  class="pop_flow4_off"  title="초도일정 수립"><!--<a href="#"></a>--></li>
					<li id="pop_flow5"  class="pop_flow5_off"  title="초도승인 요청"><!--<a href="#"></a>--></li>
					<li id="pop_flow6"  class="pop_flow6_off"  title="초도승인 완료"><!--<a href="#"></a>--></li>
					<li id="pop_flow7"  class="pop_flow7_off"  title="초도적용 완료"><!--<a href="#"></a>--></li>
					<li id="pop_flow8"  class="pop_flow8_off"  title="확대일정 수립"><!--<a href="#"></a>--></li>
					<li id="pop_flow9"  class="pop_flow9_off"  title="확대적용 완료"><!--<a href="#"></a>--></li>
					<li id="pop_flow10" class="pop_flow10_off" title="완료"><!-- <a href="#"></a> --></li>
				</ul>
				
				<!-- Flow 검증접수 pkg_tab_flow_2 -->
				<div id="pkg_tab_flow_2" class="ly_help ly_pos1" style="display:none;width:840px;height:750px;top:45px;left:235px;">
				</div>
				
				<!-- Flow 검증계획요청 pkg_tab_flow_25 -->
<!-- 				<div id="pkg_tab_flow_25" class="ly_help ly_pos1" style="display:none;width:840px;height:750px;top:125px;left:235px;"></div> -->
				
				<!-- Flow 검증계획승인 pkg_tab_flow_26 -->
				<div id="pkg_tab_flow_26" class="ly_help ly_pos1" style="display:none;width:840px;height:750px;top:120px;left:235px;">
				</div>

				<!-- Flow 검증완료 pkg_tab_flow_3 -->
				<div id="pkg_tab_flow_3" class="ly_help ly_pos1" style="display:none; width:730px; height:750px; top:80px; left:340px;">
				</div>

				<!-- Flow 초도일정수립 pkg_tab_flow_4 -->
				<div id="pkg_tab_flow_4" class="ly_help ly_pos1" style="display:none;width:760px;height:695px;top:175px;left:315px;">
				</div>

				<!-- Flow 초도적용 승인요청 pkg_tab_flow_5 -->
				<div id="pkg_tab_flow_5" class="ly_help ly_pos1" style="display:none;width:720px;height:330px;top:335px;left:355px;">
				</div>

				<!-- Flow 초도 승인 pkg_tab_flow_6 -->
				<div id="pkg_tab_flow_6" class="ly_help ly_pos1" style="display:none;width:760px;height:330px;top:410px;left:315px;">
				</div>

				<!-- Flow 초도결과 등록 pkg_tab_flow_7 -->
				<div id="pkg_tab_flow_7" class="ly_help ly_pos1" style="display:none;width:740px;height:600px;top:255px;left:335px;">
				</div>

				<!-- Flow 확대일정수립 pkg_tab_flow_8 -->
				<div id="pkg_tab_flow_8" class="ly_help ly_pos1" style="display:none;width:760px;height:695px;top:80px;left:315px;">
				</div>

				<!-- Flow 확대결과 등록 pkg_tab_flow_9 -->
				<div id="pkg_tab_flow_9" class="ly_help ly_pos1" style="display:none;width:720px;height:685px;top:85px;left:355px;">
				</div>
				
				<!-- Flow 완료 pkg_tab_flow_10 -->
				<div id="pkg_tab_flow_10" class="ly_help ly_pos1" style="display:none;width:720px;height:680px;top:105px;left:315px;">
				</div>
			</div>
			
			<!-- pkg Flow -->
			<div id="pkg_tab_flow" class="pop_con_Div6">
				<!--플로우 -->
				<ul id="flow_ul" class="pop_flow">
					<li id="pop_flow1" class="pop_flow1_off" title="요청중"><!-- <a href="#"></a> --></li>
					
					<!-- 개발검증 flow-->
					<li id="pop_flow21" class="pop_flow21_off" title="개발검증 접수"><!-- <a href="#"></a> --></li>
					<li id="pop_flow22" class="pop_flow22_off" title="개발검증 완료"><!-- <a href="#"></a> --></li>
					<li id="pop_flow23" class="pop_flow23_off" title="개발완료 보고"><!-- <a href="#"></a> --></li>
					<li id="pop_flow24" class="pop_flow24_off" title="개발완료 승인"><!-- <a href="#"></a> --></li>
					
					<!-- 상용 계획승인 검증 flow-->
					<li id="pop_flow34" class="pop_flow34_off" title="비기능 검증"><!-- <a href="#"></a> --></li>
					<li id="pop_flow31" class="pop_flow31_off" title="용령검증"><!-- <a href="#"></a> --></li>
					<li id="pop_flow33" class="pop_flow33_off" title="과금검증"><!-- <a href="#"></a> --></li>
					<li id="pop_flow32" class="pop_flow32_off" title="보안검증"><!-- <a href="#"></a> --></li>
				</ul>
				
				<!-- Flow 검증접수 pkg_tab_flow_21 -->
				<div id="pkg_tab_flow_21" class="ly_help ly_pos1" style="display:none;width:720px;height:340px;top:115px;left:270px;">
				</div>
				<!-- Flow 검증접수 pkg_tab_flow_22 -->
				<div id="pkg_tab_flow_22" class="ly_help ly_pos1" style="display:none;width:720px;height:670px;top:70px;left:270px;">
				</div>
				<!-- Flow 검증접수 pkg_tab_flow_23 -->
				<div id="pkg_tab_flow_23" class="ly_help ly_pos1" style="display:none;width:720px;height:530px;top:270px;left:270px;">
				</div>
				<!-- Flow 검증접수 pkg_tab_flow_24 -->
				<div id="pkg_tab_flow_24" class="ly_help ly_pos1" style="display:none;width:720px;height:530px;top:345px;left:270px;">
				</div>
				
				<!-- Flow 검증접수 pkg_tab_flow_34 -->
				<div id="pkg_tab_flow_34" class="ly_help ly_pos1" style="display:none;width:760px;height:620px;top:255px;left:225px;">
				</div>
				<!-- Flow 검증접수 pkg_tab_flow_31 -->
				<div id="pkg_tab_flow_31" class="ly_help ly_pos1" style="display:none;width:760px;height:620px;top:255px;left:225px;">
				</div>
				<!-- Flow 검증접수 pkg_tab_flow_33 -->
				<div id="pkg_tab_flow_33" class="ly_help ly_pos1" style="display:none;width:760px;height:620px;top:255px;left:225px;">
				</div>
				<!-- Flow 검증접수 pkg_tab_flow_32 -->
				<div id="pkg_tab_flow_32" class="ly_help ly_pos1" style="display:none;width:760px;height:220px;top:575px;left:225px;">
				</div>
			</div>
		</div>

		<!--상단닫기버튼 -->
		<a class="close_layer" onClick="window.close();" href="#"><img alt=레이어닫기 src="/images/pop_btn_close2.gif" width="15" height="14"></a>
		</fieldset>

		<!--하단 닫기버튼 
		<div id="pop_footer">
			<input alt=닫기 src="/images/pop_btn_close.gif" type="image">
		</div>
		-->
	</div>
	<span class="shadow shadow2">
	</span>
	<span class="shadow shadow3">
	</span>
	<span class="shadow shadow4">
	</span>
</div>
		</form:form>
<!--팝업 전체레이아웃 "끝-->

	<iframe src="" name="PKG_OWMS_IFRAME" id="PKG_OWMS_IFRAME" width="1" height="1"></iframe>
</body>
</html>

