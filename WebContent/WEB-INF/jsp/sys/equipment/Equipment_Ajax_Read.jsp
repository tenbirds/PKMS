<%--
/**
 * Ajax를 이용하여 표시되는 시스템 장비 상세
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type='text/javascript' src='/js/pkgmg/pkgmg.common.js'></script>

<c:set var="registerFlag" value="${empty SysModel.equipment_seq ? '등록' : '수정'}" />

<script type="text/javaScript" defer="defer">
	$(document).ready(function($) {
		$("input[id=name]").focus();
	});

	var sel_id = "sysUserOperId";

	function init_EquipmentRead() {
		doCalendar("warehousing_day");
		var url = "/sys/system/SystemUser_Ajax_Read.do";
		doSubmit("SysModel", url, "fn_callback_systemUser_read");
	}
	
	function fn_callback_systemUser_read(data) {
		$("#system_user_area").html(data);
		fn_initSystemUser("운용");
	}

	function isValidation() {

		if (!isNullAndTrim_J($("#name"), "장비명은 필수 입력사항 입니다."))
			return false;

		var selectbox = $("#sel_" + sel_id).get(0);
		if (selectbox.length == 0) {
			alert("운용 담당자는 필수 입력사항 입니다.");
			return false;
		}
		
		return true;
	}

	// 장비 생성
	function fn_create() {

		if (!isValidation()) {
			return;
		}

		fn_sysUser_select_setting();
		
		doSubmit4File("SysModel", "/sys/equipment/Equipment_Ajax_Create.do",
				"fn_callback_create_equipment");
	}

	// 장비 생성 callback
	function fn_callback_create_equipment(data) {
		var equipment_seq = $("input[id=param1]").val();
		document.getElementById("equipment_seq").value = equipment_seq;
		fn_readListEquipment();
	}

	// 장비 수정
	function fn_update() {

		if (!isValidation()) {
			return;
		}

		fn_sysUser_select_setting();
		
		doSubmit4File("SysModel", "/sys/equipment/Equipment_Ajax_Update.do",
				"fn_callback_update_equipment");
	}

	// 장비 수정 callback
	function fn_callback_update_equipment(data) {
		fn_readListEquipment();
	}

	// 장비 삭제
	function fn_delete() {
		if (confirm("정말로 삭제 하시겠습니까?\n삭제 시 복구할 수 없습니다.")) {
			doSubmit4File("SysModel",
					"/sys/equipment/Equipment_Ajax_Delete.do",
					"fn_callback_delete_equipment");
		}
	}

	// 장비 철거
	function fn_remove() {
		if (confirm("정말로 철거 하시겠습니까?")) {
			doSubmit4File("SysModel",
					"/sys/equipment/Equipment_Ajax_Remove.do",
					"fn_callback_delete_equipment");
		}
	}
	
	// 장비 삭제 callback
	function fn_callback_delete_equipment(data) {
		document.getElementById("equipment_seq").value = "";
		fn_readListSystem();
	}

	// 선택된 운용 담당자 추가
	function fn_add_sysUser() {

		var system_user_ids = $("input[name=system_user_ids]");
		var system_user_names = $("input[name=system_user_names]");
		var selectbox = $("#sel_" + sel_id).get(0);
		
		for ( var x = 0; x < system_user_ids.length; x++) {
			if (system_user_ids[x].checked) {
				var duplicate = false;

				// 이미 존재 하는지 여부 체크
				for ( var y = 0; y < selectbox.length; y++) {
					if (selectbox.options[y].value == system_user_ids[x].value) {
						duplicate = true;
					}
				}

				// 기존에 없는 담당자만 추가
				if (!duplicate) {
					selectbox.options[selectbox.length] = new Option(
							system_user_names[x].value,
							system_user_ids[x].value);
				}
			}
		}
	}
	
	// 선택된 운용 담당자 제거
	function fn_remove_sysUser() {
		var selectbox = $("#sel_" + sel_id).get(0);
		var count = selectbox.length;
		for ( var x = count - 1; x >= 0; x--) {
			if (selectbox.options[x].selected) {
				selectbox.options[x] = null;
			}
		}
	}
	
	// 운용 담당자 selectbox 강제 선택 (전송을 위해)
	function fn_sysUser_select_setting() {
		var selectbox = $("#sel_" + sel_id).get(0);
		for ( var y = 0; y < selectbox.length; y++) {
			$(selectbox.options[y]).attr("selected", true);
		}
	}
	
	// 현재 선택된 장비 내용을 보전하고 화면은 생성 모드로 변환
	function fn_copy(){
		if(confirm("화면에 있는 내용을 복사하여 등록화면으로 이동합니다. 이동 후 저장버튼을 클릭해야만 등록됩니다.\n복사 화면으로 이동 하시겠습니까?")) {
			fn_removeListSelection("equipment_list_area");
			fn_toggleButton("", "system");
			$("#sys_detail_title").html("장비 복사");
			doDivSH("hide", "reg_info", 0);
		}
	}
	
	function fn_checkbox_yn_click(obj, str) {
		if($(obj).is(":checked")) {
			$("input[name="+str+"_yn]").val("Y");
		} else {
			$("input[name="+str+"_yn]").val("N");
		}

	}
</script>


<input type="hidden" id="retUrl" name="retUrl"/>
<input type="hidden" id="pkg_seq" name="pkg_seq"/>

<!--기본정보 테이블 -->
<h3 class="stitle">기본정보</h3>
<table class="tbl_type11">
	<tr>
		<th>장비명 <span class='necessariness'>*</span></th>
		<td>
			<input type="text" id="name" name="name" value="${SysModel.name}" maxlength="25" class="new_inp new_inp_w3" />
<%-- 			<c:choose> --%>
<%-- 				<c:when test="${SysModel.cha_yn == 'Y'}"> --%>
<!-- 					<label for="cha_yn" > -->
<!-- 						<input type="checkbox" style="position:relative; top:3px;" name="cha_yn" id="cha_yn" onclick="fn_checkbox_yn_click(this, 'cha');" checked="checked" /> 과금장비 -->
<!-- 					</label> -->
<%-- 				</c:when> --%>
<%-- 				<c:otherwise> --%>
<!-- 					<label for="cha_yn" > -->
<!-- 						<input type="checkbox" style="position:relative; top:3px;" name="cha_yn" id="cha_yn" onclick="fn_checkbox_yn_click(this, 'cha');" /> 과금장비 -->
<!-- 					</label> -->
<%-- 				</c:otherwise> --%>
<%-- 			</c:choose> --%>
		</td>
		<th>입고일</th>
		<td>
			<input type="text" id="warehousing_day" name="warehousing_day" value="${SysModel.warehousing_day}" maxlength="30" class="fl_left new_inp new_inp_w2" readonly="readonly" />
			<!--  <div class="fl_left ml15 btn_line_blue"><a href="#" id="">달력</a></div>-->
		</td>
	</tr>
	<tr>
		<th>국사 <span class='necessariness'>*</span></th>
		<td>
			<select id="idc_seq" name="idc_seq"  style="width: 310px; padding: 0px;">
				<c:forEach var="result" items="${SysModel.idcList}" varStatus="loop">
					<c:choose>
						<c:when test="${result.idc_seq == SysModel.idc_seq}">
							<option value="${result.idc_seq}" selected="selected">
						</c:when>
						<c:otherwise>
							<option value="${result.idc_seq}">
						</c:otherwise>
					</c:choose>

						<c:out value="${result.idc_name}" />
						&nbsp;&nbsp;(
						<c:out value="${result.team_name}" />
						-
						<c:out value="${result.central_name}" />
						)
					</option>
				</c:forEach>
			</select>
		</td>
		<th>서비스 지역</th>
		<td><input type="text" id="service_area" name="service_area" value="${SysModel.service_area}" maxlength="30" class="new_inp new_inp_w3" /></td>
	</tr>
</table>
<!--담당자정보 테이블 -->
<h3 class="stitle">운용 담당자정보</h3>
<div class="fl_left name_move1">
	<table id="user_table" class="tbl_type11">
		<colgroup>
			<col width="25%">
			<col width="*">
		</colgroup>
		<tr>
			<th class="th_center th_gray">운용 <span class='necessariness'>*</span></th>
			<td>
				<select id="sel_sysUserOperId" name="sel_sysUserOperId" multiple="multiple" size="30" class="select_w_100" style="height:260px;">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserOperId}" varStatus="loop">
							<option value="${SysModel.sel_sysUserOperId[loop.count-1]}">
								<c:out value="${SysModel.sel_sysUserOperNames[loop.count-1]}" />
							</option>
						</c:forEach>
					</c:if>
				</select>
			</td>
		</tr>
	</table>
</div>
<div class="fl_left name_move2">
	<p><a href="javascript:fn_add_sysUser();"><img src="/images/info_arrow_left.png" alt="추가"></a></p>
	<p><a href="javascript:fn_remove_sysUser();"><img src="/images/info_arrow_right.png" alt="삭제"></a></p>
</div>
<!--추가,삭제버튼
<ul id="system_user_button">
	<li><a href="javascript:fn_add_sysUser();"><img src="/images/ico_addarrow.gif" alt="추가" /></a></li>
	<li><a href="javascript:fn_remove_sysUser();"><img src="/images/ico_deletearrow.gif" alt="삭제" /></a></li>
</ul> -->
<div class="fl_left name_move3">

	<div id="user_table" style="margin:0;padding:0;">
		<div id="system_user_area"></div>
	</div>
</div>
<div class="fl_wrap"></div>


<div id="reg_info">
<c:choose>
	<c:when test="${registerFlag == '수정'}">
	
		<!-- 
		<table class="tbl_type3" style="width: 99%" cellspacing="0">
			<caption>적용이력</caption>
			<!--셀넓이조절가능 --
			<colgroup>
				<col width="120px;" />
				<col/>
				<col width="150px;" />
				<col width="60px;" />
				<col width="150px;" />
				<col width="100px;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">버전</th>
					<th scope="col">검증 요청 제목</th>
					<th scope="col">검증 기간</th>
					<th scope="col">구분</th>
					<th scope="col">적용기간</th>
					<th scope="col" class="tbl_rline">상태</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(SysModel.pkgEquipmentHistoryList) == 0}">
					<tr>
						<td colspan="6">적용 이력 정보가 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach var="EquipmentHistoryModel" items="${SysModel.pkgEquipmentHistoryList}" varStatus="status">
					<tr style="background:${EquipmentHistoryModel.work_gubun == 'S' ? '#fafad2' : '#fff0f5'};">
						<td><c:out value="${EquipmentHistoryModel.pkg_ver}"></c:out>&nbsp;</td>
						<td align="left" style="padding:0 0 0 10px;"><a href="javascript:fn_pkg_read('read', '${EquipmentHistoryModel.pkg_seq}', 'SysModel')"><c:out value="${fn:substring(EquipmentHistoryModel.pkg_title, 0, 30)}"/><c:out value="${fn:length(EquipmentHistoryModel.pkg_title) > 30 ? '...' : ''}" /></a>&nbsp;</td>
						<td>
							<c:out value="${EquipmentHistoryModel.verify_date_start}"></c:out> ~ 
							<c:out value="${EquipmentHistoryModel.verify_date_end}"></c:out>
						</td>
						<td><c:out value="${EquipmentHistoryModel.work_gubun == 'S' ? '초도' : '확대'}" />
						&nbsp;</td>
						<td>
							<c:out value="${EquipmentHistoryModel.work_date}" />
							[<c:out value="${EquipmentHistoryModel.start_time1}" />:<c:out value="${EquipmentHistoryModel.start_time2}" /> ~ 
							<c:out value="${EquipmentHistoryModel.end_time1}" />:<c:out value="${EquipmentHistoryModel.end_time2}" />]
						</td>
						<td><c:out value="${EquipmentHistoryModel.pkg_status_name}"></c:out>&nbsp;</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	 	-->
	 	
		<!--등록정보 테이블 -->
		<%-- <table class="tbl_type22 w_100">
			<col width="10%">
			<col width="40%">
			<col width="10%">
			<col width="40%">
			<tr>
				<th>등록자(등록일)</th>
				<td></td>
				<th>수정자(수정일)</th>
				<td><c:out value="${SysModel.update_user}" /> (<c:out value="${SysModel.update_date}" />)</td>
			</tr>
		</table> --%>
		<div class="write_info2 fl_wrap">
			등록자(등록일)
			<span class="name2">
				<c:out value="${SysModel.reg_user}" /> (<c:out value="${SysModel.reg_date}" />)
			</span>
			수정자(수정일)
			<span class="name2">
				<c:out value="${SysModel.update_user}" /> (<c:out value="${SysModel.update_date}" />)
			</span>
		</div>
	</c:when>
</c:choose>
</div>






