<%--
/**
 * Ajax를 이용하여 표시되는 시스템 상세
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="registerFlag" value="${empty SysModel.system_seq ? '등록' : '수정'}" />
<c:set var="fileFlag" value="${empty SysModel.system_seq ? 'create' : 'update'}" />
<!-- <script type="text/javascript" src="/js/pkgmg/jsgantt.js"></script> -->
<link rel="stylesheet" type="text/css" href="/css/jsgantt.css"/>
<script type='text/javascript' src='/js/pkgmg/pkgmg.common.js'></script>
<script type="text/javascript" src="/js/jquery/jquery.qtip-1.0.0-rc3.min.js"></script>

<link rel="stylesheet" href="/css/zTree/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/js/jquery/zTree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="/js/jquery/zTree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="/js/jquery/zTree/jquery.ztree.exedit.js"></script>


<style>
#dropzone{
	border:2px dotted #3292A2;
	height:240px;
	color:#92AAB0;
	text-align:center;
	font-size:24px;
	
}

input.upload {  
  opacity: 0;       /*input type="file" tag 투명하게 처리 */
  position: relative;
/*   background-color: blue; */
  width: 100px;
  z-index:10;
}
button.replace {    /*button tag 에 원하는 스타일 적용*/
  position: absolute;
  display:block;
  width: 100px;
  height: 25px;
  border-radius: 3px;
  font-weight: 600;
  border-color: transparent;
  font-size: 12px;
  background: gray;
  color: #fff;
  cursor: pointer;
}

</style>
        
        
<script type="text/javaScript" defer="defer">
	
// 	doModalPopup("#road_map_write", 1, "click", 900, 720, "/sys/system/System_Popup_RoadMap.do?system_seq=${SysModel.system_seq}");
	
	/* var g = new JSGantt.GanttChart('g',document.getElementById('GanttCharthIV'), 'day');
	if (g) {
		  '<c:forEach items="${roadMapList}" var="list" varStatus="status">';
			  g.AddTaskItem(new JSGantt.TaskItem('${list.road_map_seq}', '${list.code_desc}','${list.start_date}','${list.end_date}', '','', '','','',0,0,'',0,'','',
					    
					  	new Array(
					  			<c:forEach items="${list.list}" var="list2" varStatus="status2">
					  			new JSGantt.DelayItem('${list2.road_map_seq}','${list2.code_desc}','${list2.start_date}','${list2.end_date}','','','','')<c:if test="${!status2.last}">,</c:if>
					  			</c:forEach>	
					  	)));
			  g.Draw();	
			  g.DrawDependencies();
			  $("DIV.scroll2").css("width", "900px");
			  
		  '</c:forEach>';
	}else{
		  alert("not defined");
	}
	
	JSGantt.changeFormat("week", g); */
	
	$(document).ready(function($) {
		
		$("input[id=name]").focus();
		/* '<c:forEach items="${roadMapList}" var="list" varStatus="status">';
			doModalPopup("#taskbar_${list.road_map_seq}", 1, "click", 500, 500, "/sys/system/System_Popup_RoadMapDetail.do?road_map_seq=${list.road_map_seq}");
				
			$("#taskbar_${list.road_map_seq}").qtip({
				content : "<p style='line-height:1.6em'>"
						+ '${list.tooltip}'
						+ "</p>",
				position : {
					target : 'mouse'
				},
				style : {
					name : 'cream',
					tip : true
				}
			});
			'<c:forEach items="${list.list}" var="list2" varStatus="status2">'
				doModalPopup("#taskbar_${list2.road_map_seq}", 1, "click", 500, 500, "/sys/system/System_Popup_RoadMapDetail.do?road_map_seq=${list2.road_map_seq}");
				$("#taskbar_${list2.road_map_seq}").qtip({
					content : "<p style='line-height:1.6em'>"
							+ '${list2.tooltip}'
							+ "</p>",
					position : {
						target : 'mouse'
					},
					style : {
						name : 'cream',
						tip : true
					}
				});
			'</c:forEach>';	
		'</c:forEach>'; */
		
		$("#btn_upload").change(function() {
			if(this.files.length == 0){
				return;
			}
			btnUpload(this);
		});
		
	});

	var count = ${SysModel.totalCount};
	if(count == 0){
		var sel_id_array = new Array("sysUserVerifyId", "sysUserApprovalId","devsysUserVerifyId",
				"devsysUserApprovalId",	
// 				"sysUserBizId", 
				"sysUserBpId",
				"locsysUserVerifyId",
// 				"locsysUserApprovalId",
				"sysUserMoId",
				"sysUserBpId1", "sysUserBpId2", "sysUserBpId3", "sysUserBpId4",
				"sysUserVolId", "sysUserSecId", "sysUserChaId" 
// 				,"sysUserNonId"
				);
	}else if(count > 0){
		var sel_id_array = new Array("sysUserVerifyId", "sysUserApprovalId","devsysUserVerifyId",
				"devsysUserApprovalId", 
// 				"sysUserBizId", 
				"sysUserBpId",
				"locsysUserVerifyId",
// 				"locsysUserApprovalId",
				"sysUserMoId",
				"sysUserBpId1", "sysUserBpId2", "sysUserBpId3", "sysUserBpId4", "sysUserOperId",
				"sysUserVolId", "sysUserSecId", "sysUserChaId" 
// 				,"sysUserNonId"
				);
	}
	
	function fn_select_user(sel_id) {
		
		if (sel_id.match("^sysUserBpId")) {
			var bp_idx = sel_id.substring("sysUserBpId".length);
			if (!isNullAndTrim_J($("#bp_num"+bp_idx), "시스템 개요에서 협력업체를 먼저 선택하여 주세요.")) {
				$("#"+sel_id).attr("checked", false);
				$("#"+sel_id).attr("checked", true);
				return false;
			}
			$("#sel_bp_num").val($("#bp_num"+bp_idx).val());
		}
		
		if (sel_id.match("^sysUserOperId")) {
			if(confirm("장비담당자를 일괄변경하게 되면 기존 담당자는 제거 됩니다.")) {
			} else {
				$("#"+sel_id).attr("checked", false);
				$("#"+sel_id).attr("checked", true);
				return false;
			}
		}
		
// 		fn_systemUser_read();
		fn_systemUser_read_popup(sel_id);
// 		fn_user_selbox_disable();
		$("#" + sel_id).attr("checked", true);
// 		$("#sel_" + sel_id).attr("disabled", false);
// 		$("#th_" + sel_id).css("background-color", "#F9D537");
	}

	
		


	
	function open_effect_system_popup(){	
		doModalPopup("#impact_systems", 1, "", 478, 680, "/sys/system/System_Effect_Popup_Read_Detail.do");
	}
	
	//시스템 선택 결과 처리
	function fn_system_popup_callback(system_key, system_name){
		console.log("!!!!!!!!!!");
// 		$("#system_seq").val(system_key);
// 		$("#system_name").val(system_name);

// 		if('${registerFlag}' == "수정"){
// 			$("#registerFlag").val("1");
// 		}
// 		doSubmit2("NewpncrModel", "/newpncrmg/Newpncr_Ajax_Chart_Read.do", "fn_PncrChart_rollback");
	}
	
	function fn_system_relation_popup_callback(system_key, system_name){
		var temp_relation_id = $("#temp_relation_id").val();
		
		console.log("----------------"+temp_relation_id);
// 		$("#" + temp_relation_id).val(system_name);
// 		$("#relation_system_seq" + temp_relation_id.replace("relation_system","")).val(system_key);
// 		if('${registerFlag}' == "수정"){
// 			$("#registerFlag").val("1");
// 		}
// 		doSubmit2("NewpncrModel", "/newpncrmg/Newpncr_Ajax_Chart_Read.do", "fn_PncrChart_rollback");
	}
	
	
	
	
	
	function fn_systemUser_read_popup(sel_id) {
		$("#sel_id").val(sel_id);
		
		if(sel_id == 'devsysUserVerifyId'){
			$("#select_system_user_id").val($("#dev_system_user_id").val());
		}else if(sel_id == 'sysUserVerifyId'){
			$("#select_system_user_id").val($("#system_user_id").val());
		}else if(sel_id == 'sysUserBpId'){		
			$("#select_system_user_id").val($("#bp_system_user_id").val());
		}else{
			$("#select_system_user_id").val(null);
		}
		var sel_system_user_id =  $("#select_system_user_id").val();
// 		var selectbox = $("#sel_" + sel_id).get(0);
		var total_cnt= $("#" + sel_id + "_cnt").val();
		var sel_ids="";
		var sel_names="";

		for(var i=0; i < total_cnt; i++){
			if(i == 0){
				sel_ids= $("#sel_"+ sel_id +"_"+(i+1)).val();
				sel_names= $("#sel_"+ sel_id +"_Names_"+(i+1)).val();
			}else{
				sel_ids=sel_ids+","+$("#sel_"+ sel_id +"_"+(i+1)).val();
				sel_names=sel_names+","+$("#sel_"+ sel_id +"_Names_"+(i+1)).val();
			}
		}

// 		for ( var y = 0; y < selectbox.length; y++) {
// 			if(y==0){				
// 				sel_ids = selectbox.options[y].value;
// 				sel_names = selectbox.options[y].text;
// 			}else{
// 				sel_ids = sel_ids+","+selectbox.options[y].value;
// 				sel_names = sel_names+","+selectbox.options[y].text;
// 			}
// 		}
		$("#sel_ids").val(sel_ids);
		$("#sel_names").val(sel_names);
		
// 		var url_id = "#th_"+sel_id;
		var sys_seq = $("#system_seq").val();
// 		var url = "";
		/* if (sel_id.match("^sysUserBpId")) {
			var bp_num = $("#sel_bp_num").val();
			url = "/sys/system/System_Popup_Bp.do";
			doModalPopup(url_id, 1, "click", 1000, 520,
					url + "?system_seq=${SysModel.system_seq}&sel_id=" + sel_id
					+"&sel_ids="+sel_ids+"&sel_names="+sel_names+"&select_system_user_id="+sel_system_user_id
					+"&sel_bp_num="+bp_num);
		}else{
			url = "/sys/system/System_Popup_SKTUser.do";
			doModalPopup(url_id, 1, "click", 1000, 520,
					url + "?system_seq=${SysModel.system_seq}&sel_id=" + sel_id +
					"&sel_ids="+sel_ids+"&sel_names="+sel_names +"&select_system_user_id="+sel_system_user_id);
				url = "/sys/system/System_Popup_SKTUser.do";
		} */
		
		var url = "/sys/system/System_Popup_SKTUser.do";
		if (sel_id.match("^sysUserBpId")) {
			url = "/sys/system/System_Popup_Bp.do";
		}
		
		var option = "width=1000px, height=550px, scrollbars=no, resizable=no, statusbar=no";
		var form = document.getElementById("SysModel");
		var sWin = window.open("", "user_list", option);
		
		form.target = "user_list";
		form.action = url;
		form.submit();
		sWin.focus();
		
//ing// 		doModalPopup("img[name=open_relation_system_popup", 1, "click", 478, 680, "/sys/system/System_relation_Popup_Read_Detail.do");
		
		
	}
	
	/* function fn_systemUser_read() {
		var url = "/sys/system/SystemUser_Ajax_Read.do";
		if (selected_system_user_id.match("^sysUserBpId")) {
			url = "/sys/system/SystemBpUser_Ajax_Read.do";
		}
		doSubmit("SysModel", url, "fn_callback_systemUser_read");
	} */

	/* function fn_callback_systemUser_read(data) {
		$("#system_user_area").html(data);
		fn_initSystemUser($("#label_" + selected_system_user_id).text());
	} */
/* 	
	function fn_systemFile_read() {
		var url = "/sys/system/SystemFile_Ajax_Read.do";
		
		doSubmit("SysModel", url, "fn_callback_systemFile_read");
	}
 */
	function fn_callback_systemFile_read(data) {
		$("#file_area").html(data);
		//fn_initSystemUser($("#label_" + selected_system_user_id).text());
	}

	// 담당자 선택 박스 disable
// 	function fn_user_selbox_disable() {
// 		for ( var i = 0; i < sel_id_array.length; i++) {
// 			$("#sel_" + sel_id_array[i]).attr("disabled", true);
// 			$("#th_" + sel_id_array[i]).css("background-color", "#ebebeb");
// 		}
// 	}

	function isValidation() {

		if (!isNullAndTrim_J($("#name"), "시스템명은 필수 입력사항 입니다."))
			return false;

		if (!isNullAndTrim_J($("#bp_num"), "업체명은 필수 입력사항 입니다."))
			return false;
	
		var sel_id_array_check = new Array("sysUserVerifyId", "sysUserApprovalId", "sysUserBpId");
		for ( var x = 0; x < sel_id_array_check.length; x++) {
			var selectbox = $("#sel_" + sel_id_array_check[x]+"_1").val();
			if (selectbox == null || selectbox == '' || typeof selectbox == "undefined") {
				alert($("#label_" + sel_id_array_check[x]).text() + "의 담당자 선택은 필수 입력사항 입니다."+sel_id_array_check[x]);
				return false;
			}
		}

		var systemUserId = $("#system_user_id").val();
		
		if(systemUserId == "" || typeof systemUserId == "undefined"){
			alert("대표 담당자는 필수 입력사항 입니다.");
			return false;
		}
		
		return true;
	}

	// 시스템 생성
	function fn_create() {

		if (!isValidation()) {
			return;
		}

		fn_sysUser_select_setting();
		
		doSubmit4File("SysModel", "/sys/system/System_Ajax_Create.do",
				"fn_callback_create_system");
	}

	// 시스템 생성 callback
	function fn_callback_create_system(data) {
		var system_seq = $("input[id=param1]").val();
		$("#system_seq").val(system_seq);
		fn_readListSystem();
	}

	// 시스템 수정
	function fn_update() {

		if (!isValidation()) {
			return;
		}

		fn_sysUser_select_setting();

		doSubmit4File("SysModel", "/sys/system/System_Ajax_Update.do",
				"fn_callback_update_system");
	}

	// 시스템 수정 callback
	function fn_callback_update_system(data) {
		fn_readListSystem();
	}

	// 시스템 삭제
	function fn_delete() {
		if (confirm("정말로 삭제 하시겠습니까?")) {
			doSubmit4File("SysModel", "/sys/system/System_Ajax_Delete.do",
					"fn_callback_delete_system");
		}
	}

	// 시스템 삭제 callback
	function fn_callback_delete_system(data) {
		$("#system_seq").val("");
		fn_readListGroup3();
	}

	// BP 선택 팝업에서 호출되는 callback
	function fn_bp_popup_callback(bp_num, bp_name, bp_idx) {
		$("#bp_num"+bp_idx).val(bp_num);
		$("#bp_name"+bp_idx).val(bp_name);

		//업체 변경 시 현재 조회 화면이 업체 담당자 조회 이면 화면 갱신
		/* if (selected_system_user_id.match("^sysUserBpId")) {
			if(bp_num == ""){
				$("#sysUserBpId").attr("checked", false);
				$("#system_user_area").html("");
				fn_user_selbox_disable();
			}else{
				fn_systemUser_read();
			}
		} */

		//업체 변경 시 기존 업체 담당자 제거
		if (bp_idx == '') {
			/* var selectbox = $("#sel_sysUserBpId").get(0);
			var count = selectbox.length;
			for ( var x = count - 1; x >= 0; x--) {
				selectbox.options[x] = null;
			} */
			var count = $("#sysUserBpId_cnt").val();
			
			$("#div_sysUserBpId").empty();
			for(var x=0; x<=count; x++){
				$("#sel_sysUserBpId_"+(x+1)).val("");
				$("#sel_sysUserBpId_Names_"+(x+1)).val("");
				$("#sysUserBpId_cnt").val("");
			}
		}
	}

	// 선택된 각 담당자 추가
	/* function fn_add_sysUser() {

		if (selected_system_user_id == "") {
			return;
		}

		var system_user_ids = $("input[name=system_user_ids]");
		var system_user_names = $("input[name=system_user_names]");
		var selectbox = $("#sel_" + selected_system_user_id).get(0);

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
		fn_systemUserHandle();
		fn_devsystemUserHandle();
		fn_bpsystemUserHandle();
	} */

	// 선택된 각 담당자 제거
	/* function fn_remove_sysUser() {
		if (selected_system_user_id == "") {
			return;
		}
		var selectbox = $("#sel_" + selected_system_user_id).get(0);
		var count = selectbox.length;
		for ( var x = count - 1; x >= 0; x--) {
			if (selectbox.options[x].selected) {
				selectbox.options[x] = null;
			}
		}
		fn_systemUserHandle();
		fn_devsystemUserHandle();
	} */

	// 각 담당자 selectbox 강제 선택 (전송을 위해)
	function fn_sysUser_select_setting() {
		for ( var x = 0; x < sel_id_array.length; x++) {
			$("#sel_" + sel_id_array[x]).attr("disabled", false);
/* 			var selectbox = $("#sel_" + sel_id_array[x]).get(0);
			for ( var y = 0; y < selectbox.length; y++) {
				$(selectbox.options[y]).attr("selected", true);
			} */
		}
// 		$("#system_user_id").val($("#select_system_user option:selected").val());
// 		$("#dev_system_user_id").val($("#dev_select_system_user option:selected").val());
// 		$("#bp_system_user_id").val($("#bp_select_system_user option:selected").val());
		
	}
	
	// 현재 선택된 시스템 내용을 보전하고 화면은 생성 모드로 변환
	function fn_copy(){
		if(confirm("화면에 있는 내용을 복사하여 등록화면으로 이동합니다. 이동 후 저장버튼을 클릭해야만 등록됩니다.\n복사 화면으로 이동 하시겠습니까?")) {
			fn_removeListSelection("system_list_area");
			$("#equipment_list_area").html(empty_list);
			doDivSH("hide", "equipment_new_button", 0);
			fn_toggleButton("", "system");
			$("#sys_detail_title").html("시스템 복사");
			doDivSH("hide", "reg_info", 0);
		}
	}
	
	function fn_historyToggle(key){
		doDivSH("slideToggle", "history_" + key, 300);
	}
	
	//대표 담당자 처리
	function fn_systemUserHandleInit(){
// 		fn_systemUserHandle($("#system_user_id").val());
// 		fn_devsystemUserHandle($("#dev_system_user_id").val());
// 		fn_bpsystemUserHandle($("#bp_system_user_id").val());
	}
	
	function fn_systemUserHandle(systemUserId){
		var map = new Map();
		
		for ( var x = 0; x < sel_id_array.length; x++) {
			
			if (sel_id_array[x] == "sysUserVerifyId") {
				
				var selectbox = $("#sel_" + sel_id_array[x]).get(0);
				
				for ( var y = 0; y < selectbox.length; y++) {
					map.put(selectbox.options[y].value, selectbox.options[y].text);
				}
			}
		}
		
		var selectId = systemUserId;
		if(systemUserId == "" || typeof systemUserId == "undefined"){
			selectId = $("#select_system_user option:selected").val();
		}
		
		$("#select_system_user").empty().data('options'); 
		
		var keys = map.keys();
		for(var i = 0; i<keys.length; i++){
			$("#select_system_user").append("<option value='"+keys[i]+"'>"+map.get(keys[i])+"</option>");
		}
		$("#select_system_user").val(selectId);
	}
	
	function fn_devsystemUserHandle(dev_systemUserId){
		var dev_map = new Map();
		
		for ( var x = 0; x < sel_id_array.length; x++) {
			
			if (sel_id_array[x] == "devsysUserVerifyId") {
				
				var dev_selectbox = $("#sel_" + sel_id_array[x]).get(0);
				
				for ( var y = 0; y < dev_selectbox.length; y++) {
					dev_map.put(dev_selectbox.options[y].value, dev_selectbox.options[y].text);
				}
			}
		}
		
		var dev_selectId = dev_systemUserId;
		if(dev_systemUserId == "" || typeof dev_systemUserId == "undefined"){
			dev_selectId = $("#dev_select_system_user option:selected").val();
		}
		
		$("#dev_select_system_user").empty().data('options'); 
		
		var dev_keys = dev_map.keys();
		for(var i = 0; i<dev_keys.length; i++){
			$("#dev_select_system_user").append("<option value='"+dev_keys[i]+"'>"+dev_map.get(dev_keys[i])+"</option>");
		}
		$("#dev_select_system_user").val(dev_selectId);
	}
	
	function fn_bpsystemUserHandle(bp_systemUserId){
		var bp_map = new Map();
		
		for ( var x = 0; x < sel_id_array.length; x++) {
			
			if (sel_id_array[x] == "sysUserBpId") {
				
				var bp_selectbox = $("#sel_" + sel_id_array[x]).get(0);
				
				for ( var y = 0; y < bp_selectbox.length; y++) {
					bp_map.put(bp_selectbox.options[y].value, bp_selectbox.options[y].text);
				}
			}
		}
		
		var bp_selectId = bp_systemUserId;
		if(bp_systemUserId == "" || typeof bp_systemUserId == "undefined"){
			bp_selectId = $("#bp_select_system_user option:selected").val();
		}
		
		$("#bp_select_system_user").empty().data('options'); 
		
		var bp_keys = bp_map.keys();
		for(var i = 0; i<bp_keys.length; i++){
			$("#bp_select_system_user").append("<option value='"+bp_keys[i]+"'>"+bp_map.get(bp_keys[i])+"</option>");
		}
		$("#bp_select_system_user").val(bp_selectId);
	}
	
	//첨부파일 추가첨부
	function fnSelectBoxCnt(){
		var selectedIdx = document.forms[0].selectID.selectedIndex;
		for(var i=1; i<selectedIdx+2; i++){
			document.getElementById("display_file"+i).style.display = "";
		}
		for(var j=selectedIdx+2; j<=8; j++){
			document.getElementById("display_file"+j).style.display = "none";
		}
		
		document.getElementById("selectRowspan").rowSpan = selectedIdx+2;
	}
	
	/* function fn_pop_roadMap(system_seq){
		window.open("/sys/system/System_Popup_RoadMap.do?system_seq="+system_seq,"roadMap","width=900, height=720, scrollbars=yes, resizable=no, statusbar=no");
	} */
	
	/*
		제  목 : 시스템 관리 추가 작성  
		일  자 : 2017-09-14
		작성자 : 이성호(shlee)
	*/
	
	// Drag & Drop File Upload
	  var obj = $("#dropzone");

   obj.on('dragenter', function (e) {
        e.stopPropagation();
        e.preventDefault();
        $(this).css('border', '2px solid #5272A0');
   });

   obj.on('dragleave', function (e) {
        e.stopPropagation();
        e.preventDefault();
        $(this).css('border', '2px dotted #8296C2');
   });

   obj.on('dragover', function (e) {
        e.stopPropagation();
        e.preventDefault();
   });

   obj.on('drop', function (e) {
        e.preventDefault();
        $(this).css('border', '2px dotted #8296C2');

        var files = e.originalEvent.dataTransfer.files;
        if(files.length < 1)
             return;
        
        if($("#parent_tree_id").val() != ""){
        	F_FileMultiUpload(files, obj);
        }else{
        	alert("업로드할 시스템을 선택하세요.");
        }
   });
	// 파일 멀티 업로드   
   function btnUpload(th){
	    F_FileMultiUpload(th.files, obj)
	}
   
	// 파일 멀티 업로드
	function F_FileMultiUpload(files, obj) {
		var boo = true;

		for (var i = 0; i < files.length; i++) {
			if(!fn_upload(files[i], "tree")){
				boo = false;         		
			}
		}
	    if(!boo){

			if ($.browser.msie) { // ie 일때 input[type=file] init.
				$("#btn_upload").replaceWith( $("#btn_upload").clone(true) ); 
			} else { // other browser 일때 input[type=file] init.
				$("#btn_upload").val(""); 
			}
	     	alert("파일형식이 금지된 파일이 포함되어 있습니다.");
	     	return;
	    }
	       
         var data = new FormData();
         for (var i = 0; i < files.length; i++) {
            data.append('files', files[i]);
         }
         data.append('master_file_id', $("#master_file_id").val());
         data.append('parent_tree_id', $("#parent_tree_id").val());

         $.ajax({
        	 url:"/sys/system/tree_file_add.do",
            data: data,
            type:'POST',
			data: data,
			async:false,
			cache:false,
			contentType:false,
			processData:false,
            success: function(res) {
            	searchTree();
            },   
	        error:function(e){  
	            console.log(e.responseText);  
	        }
         });
	}
	
	// 파일 멀티 업로드 Callback
	function F_FileMultiUpload_Callback(files) {
// 	     for(var i=0; i < files.length; i++)
// 	         console.log(files[i].file_nm + " - " + files[i].file_size);
	}
	var treeObj;
	
	function searchTree(type){

		$.ajax({
	        type:"POST",  
	        url:"/sys/system/SystemFileData_Ajax_Read.do",  
	        data:{"master_file_id" : $("#master_file_id").val()},
	        success:function(data){
// 	        	console.log(data);

	        	var setting = {
	            		view: {
	        				removeHoverDom: removeHoverDom,
	        				selectedMulti: false
	        			},
	        			edit: {
	        				enable: true,
	        				editNameSelectAll: true,
	        				showRemoveBtn: showRemoveBtn,
	        				showRenameBtn: showRenameBtn,
	        				drag : {
	        					isMove : true,
	        					prev : false,
	        					inner : true,
	        					next : false
	        				}
	        			},
	        			data: {
	        				simpleData: {
	        					enable: true
	        				}
	        			},
	        			callback: {
	        				beforeDrag: beforeDrag,
	        				beforeDrop: beforeDrop,
	        				beforeEditName: beforeEditName,
	        				beforeRemove: beforeRemove,
	        				beforeRename: beforeRename,
	        				onRemove: onRemove,
	        				onRename: onRename,
	        				beforeClick: beforeClick
	        			}
	        		};
	            
	        	treeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
	        	var nodes = treeObj.getNodes();
	        	if(nodes.length > 0){
// 	        		$("#parent_tree_id").val()
					if($("#parent_tree_id").val() != ""){
						if(!type){
	        				treeObj.selectNode(nodes[$("#select_tree_node").val()]);
						}
	        			beforeClick('', nodes[$("#select_tree_node").val()], true);
					}else{
						$("#parent_tree_id").val(nodes[0].id);
						$("#select_tree_node").val(0);
						if(!type){
							treeObj.selectNode(nodes[0]);
						}
						beforeClick('', nodes[0], true);
					}
					
	        	}
	        	// 파일명 길어질 경우 우측 rename, remove 버튼이 안보이는 현상
	        	var wdt = 0;
	        	$( ".node_name" ).each(function( index ) {
	        		  if($( this ).width() >= wdt){
	        		  	wdt = $( this ).width();
	        		  }
	        		});
	        	
				if(wdt > 250){ // 파일명 길이가 250px 이상 할때 우측 버튼을 보이게 하기 위해 넓이를 재설정한다.
	        		$(".ztree").width(wdt+110);
				}
				if(wdt <= 250){
					$(".ztree").width(350);
				}
	        	
	        },   
	        error:function(e){  
	            console.log(e.responseText);  
	        }
	    });

	}
	
	function beforeDrag(treeId, treeNodes) {
		for (var i=0,l=treeNodes.length; i<l; i++) {
			if (treeNodes[i].drag === "false") {
				return false;
			}
		}
		return true;
	}
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		if(targetNode == null){
			return false;
		}
		
		if(treeNodes[0].pId == targetNode){
			return false;
		}
		
		var data = {
        	"master_file_id": treeNodes[0].id,
        	"attach_file_id": treeNodes[0].attach_file_id
        };
		
		if(targetNode.isParent){
			data.parent_tree_id = targetNode.id;			
		}else{
			data.parent_tree_id = targetNode.getParentNode().id;
		}
		
		$.ajax({
	        type:"POST",  
	        url:"/sys/system/tree_file_move.do",
	        data:data,
	        success:function(result){

	        	if(targetNode.isParent){
	    			$("#parent_tree_id").val(targetNode.id);
	    			$("#select_tree_node").val(targetNode.getIndex());
	    		}else{
	    			$("#parent_tree_id").val(targetNode.getParentNode().id);
	    			$("#select_tree_node").val(targetNode.getParentNode().getIndex());
	    		}
	        	
	        	searchTree();
	        },   
	        error:function(e){  
	            console.log(e.responseText);  
	        }
	    });
		
		
		return targetNode ? targetNode.drop !== "false" : "true";
		
	}
	
	var log, className = "dark";
	
	function beforeEditName(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		setTimeout(function() {
			if (confirm("Start node '" + treeNode.name + "' editorial status?")) {
				setTimeout(function() {
					zTree.editName(treeNode);
				}, 0);
			}
		}, 0);
		return false;
	}
	function removeBtn(treeNodeid, attach_file_id,file_path) {

		$.ajax({
	        type:"POST",  
	        url:"/sys/system/tree_file_delete.do",
	        data:{
	        	"master_file_id": treeNodeid,
	        	"attach_file_id": attach_file_id,
	        	"file_path": file_path
	        },
	        success:function(data){
	        	searchTree();
	        },   
	        error:function(e){  
	            console.log(e.responseText);  
	        }
	    });
		
	}	
	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.selectNode(treeNode);
		
		$.ajax({
	        type:"POST",  
	        url:"/sys/system/tree_file_delete.do",
	        data:{
	        	"master_file_id": treeNode.id,
	        	"attach_file_id": treeNode.attach_file_id,
	        	"file_path": treeNode.file_path
	        },
	        success:function(data){
	        	searchTree();
	        },   
	        error:function(e){  
	            console.log(e.responseText);  
	        }
	    });
		
		
	}
	function onRemove(e, treeId, treeNode) {
	}
	function beforeRename(treeId, treeNode, newName, isCancel) {
		className = (className === "dark" ? "":"dark");
		if (newName.length == 0) {
			setTimeout(function() {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.cancelEditName();
				alert("Node name can not be empty.");
			}, 0);
			return false;
		}
		return true;
	}
	function onRename(e, treeId, treeNode, isCancel) {
		var data = {
        	"master_file_id": treeNode.id,
        	"attach_file_id": treeNode.attach_file_id,
        	"file_org_name": treeNode.name
        };

		$.ajax({
	        type:"POST",  
	        url:"/sys/system/tree_file_update.do",
	        data:data,
	        success:function(result){
	        	if(treeNode.isParent){
	    			$("#parent_tree_id").val(treeNode.id);
	    			$("#select_tree_node").val(treeNode.getIndex());
	    		}else{
	    			$("#parent_tree_id").val(treeNode.getParentNode().id);
	    			$("#select_tree_node").val(treeNode.getParentNode().getIndex());
	    		}
	        	searchTree();
	        },   
	        error:function(e){  
	            console.log(e.responseText);  
	        }
	    });
		
	}
	function showRemoveBtn(treeId, treeNode) {
		return !treeNode.isParent;
	}
	function showRenameBtn(treeId, treeNode) {
		return !treeNode.isParent;
	}
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	}
	
	function beforeClick(treeId, treeNode, clickFlag) {
		if(treeNode.isParent){
			$("#parent_tree_id").val(treeNode.id);
			$("#select_tree_node").val(treeNode.getIndex());
		}else{
			$("#parent_tree_id").val(treeNode.getParentNode().id);
			$("#select_tree_node").val(treeNode.getParentNode().getIndex());
		}

		var list;
		var system;
		
		$("#fileList").empty();
		
		if(treeNode.isParent){
			
			if(treeNode.check_Child_State == 0){
				list = treeNode.children;
				system = treeNode.name;
			}else{
				$("#fileList").append("<tr><td colspan='6' style='text-align:center'>등록된 자료가 없습니다.</td></tr>");
				return;
			}
			
		}else{
			list = treeNode.getParentNode().children;
			system = treeNode.getParentNode().name;
		}
		
		var html = "";
			
		for(var i=0; i < list.length; i++){
			html = "<tr>";
			html += "<td>"+"<span class=\"td_center\">"+system+"</span>"+"</td>";
			html += "<td style='text-align: left; text-decoration: underline; color:blue;'>"
			html += "<a href=\"#\" onclick=\"javascript:downloadFile('"+list[i].file_name+"','"+list[i].name+"','"+list[i].file_path+"') ; return false; \">"+list[i].name+"</a>";
			html += "</td>";
			html += "<td>"+"<span class=\"td_center\">"+byteCalculation(list[i].file_size)+"</span>"+"</td>";
			html += "<td>"+"<span class=\"td_center\">"+list[i].reg_date+"</span>"+"</td>";
			html += "<td>"+"<span class=\"td_center\">"+list[i].reg_user+"</span>"+"</td>";
			html += "<td>"+"<span class=\"td_center btn_org\">"+"<a href=\"#\"  onclick=\"javascript:removeBtn('"+list[i].id+"','"+list[i].attach_file_id+"','"+list[i].file_path+"');\">"+"Delete"+"</a>"+"</span>"+"</td>";
			html += "</tr>";
			
			$("#fileList").append(html);
		}
		
	}
	function byteCalculation(bytes) {
        var bytes = parseInt(bytes);
        var s = ['bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];
        var e = Math.floor(Math.log(bytes)/Math.log(1024));
       
        if(e == "-Infinity") return "0 "+s[0]; 
        else 
        return (bytes/Math.pow(1024, Math.floor(e))).toFixed(2)+" "+s[e];
	}
	
	function fn_callback_select_user_popup(sel_ids, sel_names,select_user){
		var sel_id = $("#sel_id").val();


		var selectbox = $("#sel_" + sel_id).get(0);

		var sel_ids_real = sel_ids.split(",");
		var sel_names_real = sel_names.split(",");
		
		/* var map = new Map();
		for ( var x = 0; x < sel_ids_real.length; x++) {
			map.put(sel_ids_real[x], sel_names_real[x]);
		}
		var keys = map.keys(); */

		$("#div_"+sel_id).empty(); 
// 		$("#div_"+ sel_id).removeClass("devsysUser_LD sysc_selectm_bp ico-red");
// 		$("#div_"+ sel_id).addClass("devsysUser_LD sysc_selectm_bp ico-red");
		var html_val="";
		for(var i = 0; i<sel_ids_real.length; i++){
			if(sel_ids_real[i] == select_user){
				html_val = "<input type=\"hidden\" id=\"sel_"+sel_id+"_"+(i+1)+"\" name=\"sel_"+sel_id+"\" value='"+sel_ids_real[i]+"' />";
				html_val += "<input type=\"hidden\" id=\"sel_"+sel_id+"_Names_"+(i+1)+"\" value='"+sel_names_real[i]+"' />";
				html_val += "<span class=\"txt_red\">"+"대표"+"</span>"+"<span class=\"txt_name\">"+sel_names_real[i]+"</span>";
			}else{				
				html_val = "<input type=\"hidden\" id=\"sel_"+sel_id+"_"+(i+1)+"\" name=\"sel_"+sel_id+"\" value='"+sel_ids_real[i]+"' />";
				html_val += "<input type=\"hidden\" id=\"sel_"+sel_id+"_Names_"+(i+1)+"\" value='"+sel_names_real[i]+"' />";
				html_val += "<span class=\"txt_name\">"+sel_names_real[i]+"</span>"; 
			}
			if(i == (sel_ids_real.length-1)){
				html_val += "<input type=\"hidden\" id=\""+sel_id+"_cnt\" value='"+sel_ids_real.length+"' />";
			}
				$("#div_"+sel_id).append(html_val);
		}
		
		if(sel_id == 'devsysUserVerifyId'){
// 			$("#div_"+ sel_id).addClass("devsysUser_LD sysc_selectm_bp ico-red");
			$("#dev_system_user_id").val(select_user);
// 			fn_devsystemUserHandle(select_user);
		}else if(sel_id == 'sysUserVerifyId'){
// 			$("#div_"+ sel_id).addClass("devsysUser_LD sysc_selectm_bp ico-orange");
			$("#system_user_id").val(select_user);
// 			fn_systemUserHandle(select_user);
		}else if(sel_id == 'sysUserBpId'){
// 			$("#div_"+ sel_id).addClass("devsysUser_LD sysc_selectm_bp ico-green");
			$("#bp_system_user_id").val(select_user);
// 			fn_bpsystemUserHandle(select_user);
		}
		$("#select_system_user_id").val(null);		
// 		fn_user_selbox_disable();
// 		$("#"+sel_id).closeDOMWindow();
	}
</script>

<input type="hidden" id="master_file_id" name="master_file_id" value="${SysModel.master_file_id}">
<input type="hidden" id="deleteList" name="deleteList" value="${SysModel.deleteList}">
<input type="hidden" id="system_user_id" name="system_user_id" value="${SysModel.system_user_id}">
<input type="hidden" id="dev_system_user_id" name="dev_system_user_id" value="${SysModel.dev_system_user_id}">
<input type="hidden" id="bp_system_user_id" name="bp_system_user_id" value="${SysModel.bp_system_user_id}">
<input type="hidden" id="retUrl" name="retUrl"/>
<input type="hidden" id="pkg_seq" name="pkg_seq"/>

<input type="hidden" id="sel_id" name="sel_id"/>
<input type="hidden" id="sel_ids" name="sel_ids"/>
<input type="hidden" id="sel_names" name="sel_names"/>
<input type="hidden" id="select_system_user_id" name="select_system_user_id"/>
<!-- 
<table style="width: 100%">
	<tr>
		<td align="left">
			<span class="caption">로드 맵</span>
		</td>
		<td align="right" style="padding: 0px 0px 5px 0px;">
			<span>
				<img src="/images/btn_load_map_add.gif" alt="로드 맵 작성" id="road_map_write" style="cursor: pointer; padding-right: 5px; padding-top:15px;"/>
			</span>
		</td>
	</tr>
</table>
<div style="position:relative; width:300px;" class="gantt" id="GanttCharthIV"></div>
	<c:if test="${empty roadMapList}">
		<table class="tbl_type3" style="width: 1150px;">
			<tr>
				<td>작성 된 로드맵이 없습니다.</td>
			</tr>
		</table>
	</c:if>
<br/>
 -->
<!--시스템 개요 테이블 -->
<h3 class="stitle">시스템 개요</h3>
<table class="tbl_type11 w_100">
	<colgroup>
		<col width="10%">
		<col width="43%">
		<col width="10%">
		<col width="43%">
	</colgroup>
	<tr>
		<th>시스템명 <span class='necessariness'>*</span></th>
		<td>
			<input type="text" id="name" name="name" value="${SysModel.name}" maxlength="15" class="new_inp new_inp_w3" />
		</td>
		<th>Full Name</th>
		<td><input type="text" id="full_name" name="full_name" value="${SysModel.full_name }" maxlength="150" class="new_inp new_inp_w3" /></td>
	</tr>
	<tr>
		<th>과금영향도 <span class='necessariness'>*</span></th>
 		<td>
 			<div class="fl_left mt05">
 			<c:if test="${SysModel.pe_type eq 'Y' }">
 			<input type="radio" class="fl_left" value="Y"  name="pe_type"checked="checked" > <span class="mg03 ml05 mr20">있음 </span> 			
 			<input type="radio" class="fl_left" value="N"  name="pe_type" > <span class="mg03 ml05 mr20">없음 </span>
 			</c:if>
 			 <c:if test="${SysModel.pe_type ne 'Y' }">
 			<input type="radio" class="fl_left" value="Y" name="pe_type" > <span class="mg03 ml05 mr20">있음 </span> 			
 			<input type="radio" class="fl_left" value="N" name="pe_type" checked="checked" > <span class="mg03 ml05 mr20">없음 </span>
 			</c:if>


<!-- 			<input type="radio" class="fl_left" value="N" > <span class="mg03 ml05 mr20">없음 </span> -->
<!--  			<input type="radio" class="fl_left"> <span class="mg03 ml05 mr20">주 </span> -->
<!--  			<input type="radio" class="fl_left"> <span class="mg03 ml05 mr20"> 부 </span></div> -->
<!--  			<div class="fl_left"><input type="text" id="" name=" " value=" " maxlength="150" class="new_inp inp_w70" /></div> -->
 		</td>
<!-- 		<th>로밍영향도</th> -->
<!-- 		<td><input type="radio" class="fl_left"> <span class="mg03 ml05 mr25">없음 </span><input type="radio" class="fl_left"> <span class="mg03 ml05 mr25">있음  </span></td> -->
	</tr>
	<tr>
		<th>영향 시스템</th>
 		<td>
<%-- ing	 		<input type="text" id="impact_systems" name="impact_systems" onclick="javascript:open_effect_system_popup()" value="${SysModel.impact_systems}" maxlength="100" class="new_inp inp_w70 fl_left" /> --%>
	 			 		<input type="text" id="impact_systems" name="impact_systems" value="${SysModel.impact_systems}" maxlength="100" class="new_inp inp_w70 fl_left" />
<!-- 	 		<span class="btn_line_blue ml10"><a href="#">선택</a></span> -->
 		</td>
		<th>공급사</th>
		<td><input type="text" id="supply" name="supply" value="${SysModel.supply }" maxlength="100" class="new_inp new_inp_w3" /></td>
	</tr>
	<tr>
		<td colspan="4" ><div class="help_notice">영향 시스템은 복수 입력 시 콤마로 구분하여 주세요.</div></td>
	</tr>
	<tr>
		<th colspan="1" rowspan="2">비고</th>
		<td colspan="1" rowspan="2"><textarea id="note" name="note" rows="5" cols="50" style="width:90%;">${SysModel.note }</textarea></td>
		<th colspan="1" rowspan="2">한줄설명</th>
		<td colspan="1" rowspan="2"><textarea id="oneLine_explain" name="oneLine_explain" rows="5" cols="50"  style="width:90%;">${SysModel.oneLine_explain }</textarea></td>
	</tr>
<!-- 	<tr> -->
<!-- 		<th colspan="1" rowspan="3">한줄설명</th> -->
<%-- 		<td colspan="1" rowspan="3"><textarea id="oneLine_explain" name="oneLine_explain" rows="5" cols="50" >${SysModel.oneLine_explain }</textarea></td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
		
<!-- 	</tr> -->
	<tr>
		<td colspan="3">
			<ul class="sm_sc_box">
				<li class="sc-box" >
					<div id="file_area"></div>
				</li>
			</ul>
		
		</td>
	</tr>
	<tr>
<!-- 		<th>시스템 매뉴얼</th> -->
<%-- 		<td><ui:file attachFileModel="${SysModel.attachFile1}" name="attachFile1" size="15" mode="${fileFlag}" /></td> --%>
	</tr>
	<tr>
<!-- 		<th>pkg 표준절차서</th> -->
<%-- 		<td><ui:file attachFileModel="${SysModel.attachFile3}" name="attachFile3" size="15" mode="${fileFlag}" /></td> --%>
	</tr>

	<%--  
	<tr>
		<th>비상연락망</th>
		<td><ui:file attachFileModel="${SysModel.attachFile30}" name="attachFile30" size="15" mode="${fileFlag}" /></td>
		<th>CoD PoD</th>
		<td><ui:file attachFileModel="${SysModel.attachFile31}" name="attachFile31" size="15" mode="${fileFlag}" /></td>
	</tr>
	 --%>
	 <%-- 
	<tr>
		<th colspan="1" rowspan="4">교육자료</th>
		<td><input type="text" id="edu_data" name="edu_data" value="${SysModel.edu_data}" maxlength="15" class="inp" style="width: 320px;" /></td>
		<th colspan="1" rowspan="4">관련규격</th>
		<td><input type="text" id="standard" name="standard" value="${SysModel.standard}" maxlength="15" class="inp" style="width: 320px;" /></td>
	</tr>
	
	<tr>
		<td><ui:file attachFileModel="${SysModel.attachFile16}" name="attachFile16" size="15" mode="${fileFlag}" /></td>
		<td><ui:file attachFileModel="${SysModel.attachFile2}" name="attachFile2" size="15" mode="${fileFlag}" /></td>
	</tr>
	<tr>
		<td><ui:file attachFileModel="${SysModel.attachFile17}" name="attachFile17" size="15" mode="${fileFlag}" /></td>
		<td><ui:file attachFileModel="${SysModel.attachFile19}" name="attachFile19" size="15" mode="${fileFlag}" /></td>
	</tr>
	<tr>
		<td><ui:file attachFileModel="${SysModel.attachFile18}" name="attachFile18" size="15" mode="${fileFlag}" /></td>
		<td><ui:file attachFileModel="${SysModel.attachFile20}" name="attachFile20" size="15" mode="${fileFlag}" /></td>
	</tr>
	 --%>
	<%--
	<tr>
		<th colspan="1" rowspan="4">성능용량</th>
		<td><input type="text" id="per_capa" name="per_capa" value="${SysModel.per_capa}" maxlength="15" class="inp" style="width: 320px;" /></td>
		<th colspan="1" rowspan="4">시설현황</th>
		<td><input type="text" id="job_history" name="job_history" value="${SysModel.job_history}" maxlength="15" class="inp" style="width: 320px;" /></td>
	</tr>
	 
	<tr>
		<td><ui:file attachFileModel="${SysModel.attachFile21}" name="attachFile21" size="15" mode="${fileFlag}" /></td>
		<td><ui:file attachFileModel="${SysModel.attachFile24}" name="attachFile24" size="15" mode="${fileFlag}" /></td>
	</tr>
	<tr>
		<td><ui:file attachFileModel="${SysModel.attachFile22}" name="attachFile22" size="15" mode="${fileFlag}" /></td>
		<td><ui:file attachFileModel="${SysModel.attachFile25}" name="attachFile25" size="15" mode="${fileFlag}" /></td>
	</tr>
	<tr>
		<td><ui:file attachFileModel="${SysModel.attachFile23}" name="attachFile23" size="15" mode="${fileFlag}" /></td>
		<td><ui:file attachFileModel="${SysModel.attachFile26}" name="attachFile26" size="15" mode="${fileFlag}" /></td>
	</tr>
 --%>
<%-- 
	<tr>
		<th rowspan="4">RM방안</th>
		<td><input type="text" id="rm_plan" name="rm_plan" value="${SysModel.rm_plan}" maxlength="15" class="inp" style="width: 320px;" /></td>
		<c:choose>
			<c:when test="${SysModel.attachFile6 == null}">
				<th id="selectRowspan" rowspan="2">추가첨부</th>
			</c:when>
			<c:otherwise>
				<th id="selectRowspan" rowspan="9">추가첨부</th>
			</c:otherwise>
		</c:choose>
		<td>
 			<table border="0" cellpadding="0" cellspacing="0">
 				<tr>
 					<td>
 						<select id="selectID" onchange="fnSelectBoxCnt();">
							<c:forEach var="selectItem" begin="1" end="8" step="1">	
								<c:choose>
									<c:when test="${SysModel.attachFile6 == null}">
										<option><c:out value="${selectItem}" /></option>
									</c:when>
									<c:otherwise>
										<option selected><c:out value="${selectItem}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
 					</td>
 					<td><div class="help_notice">첨부파일은 최대8개 까지 추가 등록 가능 합니다.</div></td>
 				</tr>
 			</table>
 		</td>
	</tr>
	<tr>
		<td><ui:file attachFileModel="${SysModel.attachFile27}" name="attachFile27" size="15" mode="${fileFlag}" /></td>
 		<td><div id="display_file1"><ui:file attachFileModel="${SysModel.attachFile6}" name="attachFile6" size="15" mode="${fileFlag}" /></div></td>
	</tr>
	<tr>
		<td><ui:file attachFileModel="${SysModel.attachFile28}" name="attachFile28" size="15" mode="${fileFlag}" /></td>
 		<td>
			<c:choose>
				<c:when test="${SysModel.attachFile6 == null}">
					<div id="display_file2" style="display:none;"><ui:file attachFileModel="${SysModel.attachFile7}" name="attachFile7" size="15" mode="${fileFlag}" /></div>
				</c:when>
				<c:otherwise>
					<div id="display_file2"><ui:file attachFileModel="${SysModel.attachFile7}" name="attachFile7" size="15" mode="${fileFlag}" /></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td><ui:file attachFileModel="${SysModel.attachFile29}" name="attachFile29" size="15" mode="${fileFlag}" /></td>
 		<td>
			<c:choose>
				<c:when test="${SysModel.attachFile6 == null}">
					<div id="display_file3" style="display:none;"><ui:file attachFileModel="${SysModel.attachFile8}" name="attachFile8" size="15" mode="${fileFlag}" /></div>
				</c:when>
				<c:otherwise>
					<div id="display_file3"><ui:file attachFileModel="${SysModel.attachFile8}" name="attachFile8" size="15" mode="${fileFlag}" /></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	 --%>
	
</table>


<!-- 
	제  목 : 시스템 관리 추가 작성  
	일  자 : 2017-09-14
	작성자 : 이성호(shlee)
 -->
<!-- 시스템 파일 트리 -->
<h3 class="stitle">시스템 자료</h3>
<div class="fl_wrap">
	<!-- 시스템 트리 -->
	<div id="fileTree" class="sysc_div4 fl_left" style="width:40%;">
		<ul id="treeDemo" class="ztree" ></ul>
	</div>

	<!-- file Drag & Drop -->
	<div id="dropzone" class="sysc_div444 fl_right" style="width:58%;">
		<div id="system_File_list_area" style="padding:0px 10px 0px 10px; height:235px; clear:both;">
			<input type="hidden" id="parent_tree_id" value=""/>
			<input type="hidden" id="select_tree_node" value=""/>
			<div class="fl_wrap mt05">
<!-- 			File Drag & Drop -->
				<div class="help_notice">파일 업로드는 드래그앤드랍으로도 가능합니다.</div>
				<div class="fl_right mt10">
					<button class="replace" onclick="return false;">파일 업로드</button>
<!-- 					<a href="#" onclick="return false;">파일 업로드</a> -->
					<input type="file" value="파일 업로드" class="upload" id="btn_upload" multiple>
				</div>
			</div>
			<div id="scrollTable">
				<table class="tbl_type11 w_100" >
					<colgroup>
						<col width="20%" />
						<col width="30%" />
						<col width="12%" />
						<col width="13%" />
						<col width="10%" />
						<col width="15%" />
					</colgroup>
					<tr>
						<th class="th_center">자료구분</th>
						<th class="th_center">파일명</th>
						<th class="th_center">파일크기</th>
						<th class="th_center">등록일</th>
						<th class="th_center">등록자</th>
						<th class="th_center">삭제</th>
					</tr>
				</table>
			</div>
			<div class="list_scroll">
				<table class="tbl_type22" >
					<colgroup>
						<col width="20%" />
						<col width="32%" />
						<col width="13%" />
						<col width="14%" />
						<col width="11%" />
						<col width="10%" />
					</colgroup>
					<!-- tbody id="fileList"> -->
					<tbody id="fileList">
						<tr>
							<td class="td_center">시스템 매뉴얼</td>
							<td>PKMS_매뉴얼파일1.ppt</td>
							<td align="center">1KB</td>
							<td align="center">2018-04-01</td>
							<td  align="center">홍길동</td>
							<td align="center" class="btn_org"><a href="#">Delete</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- //시스템 파일 트리 -->





<!--담당자정보 테이블 -->

<h3 class="stitle">담당자</h3>

<!-- New Form : S -->
<div>
	<table id="user_table" class="tbl_type11 w_100">
		<colgroup>
			<col width="10%">
			<col width="43%">
			<col width="10%">
			<col width="43%">
		</colgroup>
		<tr>
			<th class="th_gray th_center">DVT</th>
			<td>
				<div id="div_devsysUserVerifyId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_devsysUserVerifyId}" varStatus="loop">
								<c:choose>
									<c:when test="${SysModel.sel_devsysUserVerifyId[loop.count-1] eq SysModel.dev_system_user_id}">
										<input type="hidden" id="sel_devsysUserVerifyId_${loop.count}" name="sel_devsysUserVerifyId" value="${SysModel.sel_devsysUserVerifyId[loop.count-1]}" />
										<input type="hidden" id="sel_devsysUserVerifyId_Names_${loop.count}" value="${SysModel.sel_devsysUserVerifyNames[loop.count-1]}" />
										<span class="txt_red">대표</span><span class="txt_name"><c:out value="${SysModel.sel_devsysUserVerifyNames[loop.count-1]}" /></span>
									</c:when>
									<c:otherwise>
										<input type="hidden" id="sel_devsysUserVerifyId_${loop.count}" name="sel_devsysUserVerifyId" value="${SysModel.sel_devsysUserVerifyId[loop.count-1]}" />
										<input type="hidden" id="sel_devsysUserVerifyId_Names_${loop.count}" value="${SysModel.sel_devsysUserVerifyNames[loop.count-1]}" />
										<span class="txt_name"><c:out value="${SysModel.sel_devsysUserVerifyNames[loop.count-1]}" /></span>
									</c:otherwise>
								</c:choose>
								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="devsysUserVerifyId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('devsysUserVerifyId')" id="th_devsysUserVerifyId" class="fl_right mt10 btn_line_blue">
					<span id="devsysUserVerifyId">선택</span>
				</div>
			</td>

			<th class="th_gray th_center">DVT승인</th>
			<td>
				<div id="div_devsysUserApprovalId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_devsysUserApprovalId}" varStatus="loop">
								<input type="hidden" id="sel_devsysUserApprovalId_${loop.count}" name="sel_devsysUserApprovalId" value="${SysModel.sel_devsysUserApprovalId[loop.count-1]}" />
								<input type="hidden" id="sel_devsysUserApprovalId_Names_${loop.count}" value="${SysModel.sel_devsysUserApprovalNames[loop.count-1]}" />
								<span class="txt_name"><c:out value="${SysModel.sel_devsysUserApprovalNames[loop.count-1]}" /></span>

								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="devsysUserApprovalId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('devsysUserApprovalId')" id="th_devsysUserApprovalId" class="fl_right mt10 btn_line_blue">
					<span id="devsysUserApprovalId">선택</span>
				</div>
			</td>
		</tr>
		<tr>
			<th class="th_gray th_center">CVT <span class='necessariness'>*</span> </th>
			<td>
				<div id="div_sysUserVerifyId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_sysUserVerifyId}" varStatus="loop">
								<c:choose>
									<c:when test="${SysModel.sel_sysUserVerifyId[loop.count-1] eq SysModel.system_user_id}">
										<input type="hidden" id="sel_sysUserVerifyId_${loop.count}" name="sel_sysUserVerifyId" value="${SysModel.sel_sysUserVerifyId[loop.count-1]}" />
										<input type="hidden" id="sel_sysUserVerifyId_Names_${loop.count}" value="${SysModel.sel_sysUserVerifyNames[loop.count-1]}" />
										<span class="txt_red">대표</span><span class="txt_name"><c:out value="${SysModel.sel_sysUserVerifyNames[loop.count-1]}" /></span>
									</c:when>
									<c:otherwise>
										<input type="hidden" id="sel_sysUserVerifyId_${loop.count}" name="sel_sysUserVerifyId" value="${SysModel.sel_sysUserVerifyId[loop.count-1]}" />
										<input type="hidden" id="sel_sysUserVerifyId_Names_${loop.count}" value="${SysModel.sel_sysUserVerifyNames[loop.count-1]}" />
										<span class="txt_name"><c:out value="${SysModel.sel_sysUserVerifyNames[loop.count-1]}" /></span>
									</c:otherwise>
								</c:choose>
								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="sysUserVerifyId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('sysUserVerifyId')" id="th_sysUserVerifyId" class="fl_right mt10 btn_line_blue">
					<span id="sysUserVerifyId">선택</span>
				</div>
				
			</td><th class="th_gray th_center">CVT승인 <span class='necessariness'>*</span></th>
			<td>
				<div id="div_sysUserApprovalId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_sysUserApprovalId}" varStatus="loop">
								<input type="hidden" id="sel_sysUserApprovalId_${loop.count}" name="sel_sysUserApprovalId" value="${SysModel.sel_sysUserApprovalId[loop.count-1]}" />
								<input type="hidden" id="sel_sysUserApprovalId_Names_${loop.count}" value="${SysModel.sel_sysUserApprovalNames[loop.count-1]}" />
								<span class="txt_name"><c:out value="${SysModel.sel_sysUserApprovalNames[loop.count-1]}" /></span>

								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="sysUserApprovalId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('sysUserApprovalId')" id="th_sysUserApprovalId" class="fl_right mt10 btn_line_blue">
					<span id="sysUserApprovalId">선택</span>
				</div>
			</td>
		</tr>
		<tr>
			<th class="th_gray th_center">현장</th>
			<td>
				<div id="div_locsysUserVerifyId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_locsysUserVerifyId}" varStatus="loop">
								<input type="hidden" id="sel_locsysUserVerifyId_${loop.count}" name="sel_locsysUserVerifyId" value="${SysModel.sel_locsysUserVerifyId[loop.count-1]}" />
								<input type="hidden" id="sel_locsysUserVerifyId_Names_${loop.count}" value="${SysModel.sel_locsysUserVerifyNames[loop.count-1]}" />
								<span class="txt_name"><c:out value="${SysModel.sel_locsysUserVerifyNames[loop.count-1]}" /></span>
								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="locsysUserVerifyId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('locsysUserVerifyId')" id="th_locsysUserVerifyId" class="fl_right mt10 btn_line_blue">
					<span id="locsysUserVerifyId">선택</span>
				</div>
			</td>
			<th class="th_gray th_center">현장승인</th>
			<td>
				<div id="div_locsysUserApprovalId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_locsysUserApprovalId}" varStatus="loop">
								<input type="hidden" id="sel_locsysUserApprovalId_${loop.count}" name="sel_locsysUserApprovalId" value="${SysModel.sel_locsysUserApprovalId[loop.count-1]}" />
								<input type="hidden" id="sel_locsysUserApprovalId_Names_${loop.count}" value="${SysModel.sel_locsysUserApprovalNames[loop.count-1]}" />
								<span class="txt_name"><c:out value="${SysModel.sel_locsysUserApprovalNames[loop.count-1]}" /></span>
								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="locsysUserApprovalId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('locsysUserApprovalId')" id="th_locsysUserApprovalId" class="fl_right mt10 btn_line_blue">
					<span id="locsysUserApprovalId">선택</span>
				</div>
			</td>
		</tr>
		<tr>
			<th class="th_gray th_center">과금</th>
			<td>
				<div id="div_sysUserChaId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_sysUserChaId}" varStatus="loop">
								<input type="hidden" id="sel_sysUserChaId_${loop.count}" name="sel_sysUserChaId" value="${SysModel.sel_sysUserChaId[loop.count-1]}" />
								<input type="hidden" id="sel_sysUserChaId_Names_${loop.count}" value="${SysModel.sel_sysUserChaNames[loop.count-1]}" />
								<span class="txt_name"><c:out value="${SysModel.sel_sysUserChaNames[loop.count-1]}" /></span>
								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="sysUserChaId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('sysUserChaId')" id="th_sysUserChaId" class="fl_right mt10 btn_line_blue">
					<span id="sysUserChaId">선택</span>
				</div>
			</td>

			<th class="th_gray th_center">
				과금승인
<!-- 				<span class='necessariness'>*</span></th> -->
			<td>
				<div id="div_sysUserChaApprovalId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_sysUserChaApprovalId}" varStatus="loop">
								<input type="hidden" id="sel_sysUserChaApprovalId_${loop.count}" name="sel_sysUserChaApprovalId" value="${SysModel.sel_sysUserChaApprovalId[loop.count-1]}" />
								<input type="hidden" id="sel_sysUserChaApprovalId_Names_${loop.count}" value="${SysModel.sel_sysUserChaApprovalNames[loop.count-1]}" />
								<span class="txt_name"><c:out value="${SysModel.sel_sysUserChaApprovalNames[loop.count-1]}" /></span>
								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="sysUserChaApprovalId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('sysUserChaApprovalId')" id="sel_sysUserChaApprovalId" class="fl_right mt10 btn_line_blue">
					<span id="sysUserChaApprovalId">선택</span>
				</div>
			</td>






		</tr>
		<tr>
			<th class="th_gray th_center">용량</th>
			<td>
				<div id="div_sysUserVolId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_sysUserVolId}" varStatus="loop">
								<input type="hidden" id="sel_sysUserVolId_${loop.count}" name="sel_sysUserVolId" value="${SysModel.sel_sysUserVolId[loop.count-1]}" />
								<input type="hidden" id="sel_sysUserVolId_Names_${loop.count}" value="${SysModel.sel_sysUserVolNames[loop.count-1]}" />
								<span class="txt_name"><c:out value="${SysModel.sel_sysUserVolNames[loop.count-1]}" /></span>
								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="sysUserVolId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('sysUserVolId')" id="th_sysUserVolId" class="fl_right mt10 btn_line_blue">
					<span id="sysUserVolId">선택</span>
				</div>
			</td>


			<th class="th_gray th_center">
				용량승인
<!-- 				<span class='necessariness'>*</span> -->
			</th>
			<td>
				<div id="div_sysUserVolApprovalId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_sysUserVolApprovalId}" varStatus="loop">
								<input type="hidden" id="sel_sysUserVolApprovalId_${loop.count}" name="sel_sysUserVolApprovalId" value="${SysModel.sel_sysUserVolApprovalId[loop.count-1]}" />
								<input type="hidden" id="sel_sysUserVolApprovalId_Names_${loop.count}" value="${SysModel.sel_sysUserVolApprovalNames[loop.count-1]}" />
								<span class="txt_name"><c:out value="${SysModel.sel_sysUserVolApprovalNames[loop.count-1]}" /></span>
								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="sysUserVolApprovalId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('sysUserVolApprovalId')" id="th_sysUserVolApprovalId" class="fl_right mt10 btn_line_blue">
					<span id="sysUserVolApprovalId">선택</span>
				</div>
			</td>
			
						
			
			
			</tr>
			
			<tr>
			<th class="th_gray th_center">관제</th>
			<td>
				<div id="div_sysUserMoId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_sysUserMoId}" varStatus="loop">
								<input type="hidden" id="sel_sysUserMoId_${loop.count}" name="sel_sysUserMoId" value="${SysModel.sel_sysUserMoId[loop.count-1]}" />
								<input type="hidden" id="sel_sysUserMoId_Names_${loop.count}" value="${SysModel.sel_sysUserMoNames[loop.count-1]}" />
								<span class="txt_name"><c:out value="${SysModel.sel_sysUserMoNames[loop.count-1]}" /></span>
								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="sysUserMoId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('sysUserMoId')" id="th_sysUserMoId" class="fl_right mt10 btn_line_blue">
					<span id="sysUserMoId">선택</span>
				</div>
			</td>
						
			<th class="th_gray th_center">보안</th>
			<td>
				<div id="div_sysUserSecId" class="manager_list fl_left">
					<div class="fl_left">
						<c:if test="${registerFlag == '수정'}">
							<c:forEach var="result" items="${SysModel.sel_sysUserSecId}" varStatus="loop">
								<input type="hidden" id="sel_sysUserSecId_${loop.count}" name="sel_sysUserSecId" value="${SysModel.sel_sysUserSecId[loop.count-1]}" />
								<input type="hidden" id="sel_sysUserSecId_Names_${loop.count}" value="${SysModel.sel_sysUserSecNames[loop.count-1]}" />
								<span class="txt_name"><c:out value="${SysModel.sel_sysUserSecNames[loop.count-1]}" /></span>
								<c:if test="${loop.last}">
									<input type="hidden" value="${loop.count}" id="sysUserSecId_cnt">
								</c:if>
							</c:forEach>
						</c:if>
					</div>
				</div>
				<div onclick="javascript:fn_select_user('sysUserSecId')" id="th_sysUserSecId" class="fl_right mt10 btn_line_blue">
					<span id="sysUserSecId">선택</span>
				</div>
			</td>
		</tr>
	</table>
	<!-- 
	<table id="user_table" class="tbl_type11 w_100">
		<tr>
			<th onclick="javascript:fn_select_user('devsysUserVerifyId')" id="th_devsysUserVerifyId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="devsysUserVerifyId" />
				<label id="label_devsysUserVerifyId" for="devsysUserVerifyId">개발검증</label>
			</th>
			<td>
				!-- 다른컬럼 대표아이콘 넣을 때 다른건 다 동일, ico-red ico-orange ico-green 만 차례로 바꿔서 넣으면 됨 --
				<div id="div_devsysUserVerifyId" class="devsysUser_LD sysc_selectm_bp ico-red">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_devsysUserVerifyId}" varStatus="loop">
							<c:choose>
								<c:when test="${SysModel.sel_devsysUserVerifyId[loop.count-1] eq SysModel.dev_system_user_id}">
									<input type="hidden" id="sel_devsysUserVerifyId_${loop.count}" name="sel_devsysUserVerifyId" value="${SysModel.sel_devsysUserVerifyId[loop.count-1]}" />
									<input type="hidden" id="sel_devsysUserVerifyId_Names_${loop.count}" value="${SysModel.sel_devsysUserVerifyNames[loop.count-1]}" />
									<span class="sys_leader"><c:out value="${SysModel.sel_devsysUserVerifyNames[loop.count-1]}" /></span>
								</c:when>
								<c:otherwise>
									<input type="hidden" id="sel_devsysUserVerifyId_${loop.count}" name="sel_devsysUserVerifyId" value="${SysModel.sel_devsysUserVerifyId[loop.count-1]}" />
									<input type="hidden" id="sel_devsysUserVerifyId_Names_${loop.count}" value="${SysModel.sel_devsysUserVerifyNames[loop.count-1]}" />
										<span><c:out value="${SysModel.sel_devsysUserVerifyNames[loop.count-1]}" /></span>
								</c:otherwise>
							</c:choose>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="devsysUserVerifyId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
	
		</tr>
		<tr>
			<th onclick="javascript:fn_select_user('devsysUserApprovalId')" id="th_devsysUserApprovalId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="devsysUserApprovalId" />
				<label id="label_devsysUserApprovalId" for="devsysUserApprovalId">개발승인</label>
			</th>
			<td>
				<div id="div_devsysUserApprovalId" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_devsysUserApprovalId}" varStatus="loop">
							<input type="hidden" id="sel_devsysUserApprovalId_${loop.count}" name="sel_devsysUserApprovalId" value="${SysModel.sel_devsysUserApprovalId[loop.count-1]}" />
							<input type="hidden" id="sel_devsysUserApprovalId_Names_${loop.count}" value="${SysModel.sel_devsysUserApprovalNames[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_devsysUserApprovalNames[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="devsysUserApprovalId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		<tr>
			<th onclick="javascript:fn_select_user('sysUserVerifyId')" id="th_sysUserVerifyId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserVerifyId" />
				<label id="label_sysUserVerifyId" for="sysUserVerifyId">상용검증</label><span class='necessariness'>*</span>
			</th>
			<td>
				<div id="div_sysUserVerifyId" class="devsysUser_LD sysc_selectm_bp ico-orange">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserVerifyId}" varStatus="loop">
							<c:choose>
								<c:when test="${SysModel.sel_sysUserVerifyId[loop.count-1] eq SysModel.system_user_id}">
									<input type="hidden" id="sel_sysUserVerifyId_${loop.count}" name="sel_sysUserVerifyId" value="${SysModel.sel_sysUserVerifyId[loop.count-1]}" />
									<input type="hidden" id="sel_sysUserVerifyId_Names_${loop.count}" value="${SysModel.sel_sysUserVerifyNames[loop.count-1]}" />
									<span class="sys_leader"><c:out value="${SysModel.sel_sysUserVerifyNames[loop.count-1]}" /></span>
								</c:when>
								<c:otherwise>
									<input type="hidden" id="sel_sysUserVerifyId_${loop.count}" name="sel_sysUserVerifyId" value="${SysModel.sel_sysUserVerifyId[loop.count-1]}" />
									<input type="hidden" id="sel_sysUserVerifyId_Names_${loop.count}" value="${SysModel.sel_sysUserVerifyNames[loop.count-1]}" />
										<span><c:out value="${SysModel.sel_sysUserVerifyNames[loop.count-1]}" /></span>
								</c:otherwise>
							</c:choose>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserVerifyId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		<tr>
			<th onclick="javascript:fn_select_user('sysUserApprovalId')" id="th_sysUserApprovalId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserApprovalId" />
				<label id="label_sysUserApprovalId" for="sysUserApprovalId">상용승인</label><span class='necessariness'>*</span>
			</th>
			<td>
				<div id="div_sysUserApprovalId" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserApprovalId}" varStatus="loop">
							<input type="hidden" id="sel_sysUserApprovalId_${loop.count}" name="sel_sysUserApprovalId" value="${SysModel.sel_sysUserApprovalId[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserApprovalId_Names_${loop.count}" value="${SysModel.sel_sysUserApprovalNames[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_sysUserApprovalNames[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserApprovalId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		<tr>
			<th onclick="javascript:fn_select_user('locsysUserVerifyId')" id="th_locsysUserVerifyId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="locsysUserVerifyId" />
				<label id="label_locsysUserVerifyId" for="locsysUserVerifyId">현장담당</label>
			</th>
			<td>
				<div id="div_locsysUserVerifyId" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_locsysUserVerifyId}" varStatus="loop">
							<input type="hidden" id="sel_locsysUserVerifyId_${loop.count}" name="sel_locsysUserVerifyId" value="${SysModel.sel_locsysUserVerifyId[loop.count-1]}" />
							<input type="hidden" id="sel_locsysUserVerifyId_Names_${loop.count}" value="${SysModel.sel_locsysUserVerifyNames[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_locsysUserVerifyNames[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="locsysUserVerifyId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		<%-- <tr>
			<th onclick="javascript:fn_select_user('locsysUserApprovalId')" id="th_locsysUserApprovalId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="locsysUserApprovalId" />
				<label id="label_locsysUserApprovalId" for="locsysUserApprovalId">현장승인</label>
			</th>
			<td>
				<div id="div_locsysUserApprovalId" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_locsysUserApprovalId}" varStatus="loop">
							<input type="hidden" id="sel_locsysUserApprovalId_${loop.count}" name="sel_locsysUserApprovalId" value="${SysModel.sel_locsysUserApprovalId[loop.count-1]}" />
							<input type="hidden" id="sel_locsysUserApprovalId_Names_${loop.count}" value="${SysModel.sel_locsysUserApprovalNames[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_locsysUserApprovalNames[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="locsysUserApprovalId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr> --%>
		<tr>
			<th onclick="javascript:fn_select_user('sysUserMoId')" id="th_sysUserMoId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserMoId" />
				<label id="label_sysUserMoId" for="sysUserMoId">상황관제</label>
			</th>
			<td>
				<div id="div_sysUserMoId" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserMoId}" varStatus="loop">
							<input type="hidden" id="sel_sysUserMoId_${loop.count}" name="sel_sysUserMoId" value="${SysModel.sel_sysUserMoId[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserMoId_Names_${loop.count}" value="${SysModel.sel_sysUserMoNames[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_sysUserMoNames[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserMoId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		<tr>
			<th onclick="javascript:fn_select_user('sysUserBizId')" id="th_sysUserBizId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserBizId" />
				<label id="label_sysUserBizId" for="sysUserBizId">사업기획</label>
			</th>
			<td>
				<div id="div_sysUserBizId" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserBizId}" varStatus="loop">
							<input type="hidden" id="sel_sysUserBizId_${loop.count}" name="sel_sysUserBizId" value="${SysModel.sel_sysUserBizId[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserBizId_Names_${loop.count}" value="${SysModel.sel_sysUserBizNames[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_sysUserBizNames[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserBizId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		<tr>
			<th onclick="javascript:fn_select_user('sysUserBpId')" id="th_sysUserBpId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserBpId" />
				<label id="label_sysUserBpId" for="sysUserBpId">협력업체</label>
				<span class='necessariness'>*</span>
			</th>
			<td>
				<div id="div_sysUserBpId" class="devsysUser_LD sysc_selectm_bp ico-green">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserBpId}" varStatus="loop">
							<c:choose>
								<c:when test="${SysModel.sel_sysUserBpId[loop.count-1] eq SysModel.bp_system_user_id}">
									<input type="hidden" id="sel_sysUserBpId_${loop.count}" name="sel_sysUserBpId" value="${SysModel.sel_sysUserBpId[loop.count-1]}" />
									<input type="hidden" id="sel_sysUserBpId_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames[loop.count-1]}" />
									<span class="sys_leader"><c:out value="${SysModel.sel_sysUserBpNames[loop.count-1]}" /></span>
								</c:when>
								<c:otherwise>
									<input type="hidden" id="sel_sysUserBpId_${loop.count}" name="sel_sysUserBpId" value="${SysModel.sel_sysUserBpId[loop.count-1]}" />
									<input type="hidden" id="sel_sysUserBpId_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames[loop.count-1]}" />
										<span><c:out value="${SysModel.sel_sysUserBpNames[loop.count-1]}" /></span>
								</c:otherwise>
							</c:choose>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserBpId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>	
		<tr>
			<th onclick="javascript:fn_select_user('sysUserBpId1')" id="th_sysUserBpId1" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserBpId1" />
				<label id="label_sysUserBpId1" for="sysUserBpId1">협력업체1</label>
			</th>
			<td>
				<div id="div_sysUserBpId1" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserBpId1}" varStatus="loop">
							<input type="hidden" id="sel_sysUserBpId1_${loop.count}" name="sel_sysUserBpId1" value="${SysModel.sel_sysUserBpId1[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserBpId1_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames1[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_sysUserBpNames1[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserBpId1_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>	
		<tr>
			<th onclick="javascript:fn_select_user('sysUserBpId2')" id="th_sysUserBpId2" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserBpId2" />
				<label id="label_sysUserBpId2" for="sysUserBpId2">협력업체2</label>
			</th>
			<td>
				<div id="div_sysUserBpId2" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserBpId2}" varStatus="loop">
							<input type="hidden" id="sel_sysUserBpId2_${loop.count}" name="sel_sysUserBpId2" value="${SysModel.sel_sysUserBpId2[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserBpId2_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames2[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_sysUserBpNames2[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserBpId2_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>	
		<tr>
			<th onclick="javascript:fn_select_user('sysUserBpId3')" id="th_sysUserBpId3" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserBpId3" />
				<label id="label_sysUserBpId3" for="sysUserBpId3">협력업체3</label>
			</th>
			<td>
				<div id="div_sysUserBpId3" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserBpId3}" varStatus="loop">
							<input type="hidden" id="sel_sysUserBpId3_${loop.count}" name="sel_sysUserBpId3" value="${SysModel.sel_sysUserBpId3[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserBpId3_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames3[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_sysUserBpNames3[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserBpId3_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>	
		<tr>
			<th onclick="javascript:fn_select_user('sysUserBpId4')" id="th_sysUserBpId4" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserBpId4" />
				<label id="label_sysUserBpId4" for="sysUserBpId4">협력업체4</label>
			</th>
			<td>
				<div id="div_sysUserBpId4" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserBpId4}" varStatus="loop">
							<input type="hidden" id="sel_sysUserBpId4_${loop.count}" name="sel_sysUserBpId4" value="${SysModel.sel_sysUserBpId4[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserBpId4_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames4[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_sysUserBpNames4[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserBpId4_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		!-- 검증4타입 --
		<tr>
			<th onclick="javascript:fn_select_user('sysUserVolId')" id="th_sysUserVolId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserVolId" />
				<label id="label_sysUserVolId" for="sysUserVolId">용량검증</label>
			</th>
			<td>
				<div id="div_sysUserVolId" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserVolId}" varStatus="loop">
							<input type="hidden" id="sel_sysUserVolId_${loop.count}" name="sel_sysUserVolId" value="${SysModel.sel_sysUserVolId[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserVolId_Names_${loop.count}" value="${SysModel.sel_sysUserVolNames[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_sysUserVolNames[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserVolId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		<tr>
			<th onclick="javascript:fn_select_user('sysUserSecId')" id="th_sysUserSecId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserSecId" />
				<label id="label_sysUserSecId" for="sysUserSecId">보안검증</label>
			</th>
			<td>
				<div id="div_sysUserSecId" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserSecId}" varStatus="loop">
							<input type="hidden" id="sel_sysUserSecId_${loop.count}" name="sel_sysUserSecId" value="${SysModel.sel_sysUserSecId[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserSecId_Names_${loop.count}" value="${SysModel.sel_sysUserSecNames[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_sysUserSecNames[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserSecId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		<tr>
			<th onclick="javascript:fn_select_user('sysUserChaId')" id="th_sysUserChaId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserChaId" />
				<label id="label_sysUserChaId" for="sysUserChaId">과금검증</label>
			</th>
			<td>
				<div id="div_sysUserChaId" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserChaId}" varStatus="loop">
							<input type="hidden" id="sel_sysUserChaId_${loop.count}" name="sel_sysUserChaId" value="${SysModel.sel_sysUserChaId[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserChaId_Names_${loop.count}" value="${SysModel.sel_sysUserChaNames[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_sysUserChaNames[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserChaId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		<tr>
			<th onclick="javascript:fn_select_user('sysUserNonId')" id="th_sysUserNonId" style="text-align: left; padding: 5px;">
				<input type="radio" name="radio" id="sysUserNonId" />
				<label id="label_sysUserNonId" for="sysUserNonId">비기능검증</label>
			</th>
			<td>
				<div id="div_sysUserNonId" class="devsysUser_LD sysc_selectm_bp">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserNonId}" varStatus="loop">
							<input type="hidden" id="sel_sysUserNonId_${loop.count}" name="sel_sysUserNonId" value="${SysModel.sel_sysUserNonId[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserNonId_Names_${loop.count}" value="${SysModel.sel_sysUserNonNames[loop.count-1]}" />
							<span><c:out value="${SysModel.sel_sysUserNonNames[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserNonId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</td>
		</tr>
		
		!-- 검증4타입 --
	</table>
	-->
	
	<%-- <div>
		<table class="tbl_type1 mt_5" style="width: 99%">
			<colgroup>
				<col width="110px;">
				<col width="*">
			</colgroup>
			<tr>
				<th style="padding: 0px;">대표 상용담당자<span class='necessariness'>*</span></th>
				<td>
					<select id="select_system_user" name="select_system_user"  style="width: 195px; padding: 0px;"></select>
				</td>
			</tr>
			<tr>
				<th style="padding: 0px;">대표 개발담당자<span class='necessariness'>*</span></th>
				<td>
					<select id="dev_select_system_user" name="dev_select_system_user"   style="width: 195px; padding: 0px;"></select>
				</td>
			</tr>
			<tr>
				<th style="padding: 0px;">대표 BP담당자<span class='necessariness'>*</span></th>
				<td>
					<select id="bp_select_system_user" name="bp_select_system_user"  style="width: 195px; padding: 0px;"></select>
				</td>
			</tr>
		</table>
	</div> --%>
</div>

<!-- New Form : E -->
<table class="tbl_type11 w_100">
	<c:if test="${SysModel.totalCount > 0}">
		<colgroup>
			<col width="20%">
			<col width="*">
		</colgroup>
		<tr>
<!-- 			<th onclick="javascript:fn_select_user('sysUserOperId')" id="th_sysUserOperId" style="text-align: left; padding: 5px; width: 190px;" rowspan="2"> -->
			<th  style="text-align: left; padding: 5px; width: 190px;" rowspan="2">			
				<!-- <input type="radio" name="radio" id="sysUserOperId" />
				<label id="label_sysUserOperId" for="sysUserOperId"></label> -->
				
				국사별 장비 운용담당자 변경
			</th>
			<td>
				<select id="idc_seq" name="idc_seq" class="fl_left mr10">
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
							&nbsp;(
								<c:out value="${result.team_name}" />
							)
						</option>
					</c:forEach>
				</select>
<!-- 				<div class="manager_list3 fl_left"> -->
<!-- 					<div class="fl_left"><span class="txt_red">대표</span><span class="txt_name">담당자명</span><span class="txt_team">담당자팀</span></div> -->
<!-- 				</div> -->
				<div class="fl_right ml15 btn_line_blue"><span onclick="javascript:fn_select_user('sysUserOperId')" id="th_sysUserOperId" >선택</span></div>
			</td>
		</tr>
		<tr>
		
		
	<!--  		<td>
				<div id="div_sysUserOperId" class="devsysUser_LD sysc_selectm_bp">
				</div>
				 <select id="sel_sysUserOperId" name="sel_sysUserOperId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp" style="width: 100%;">
				</select> 		
			</td>
		</tr>-->
		
		
		
		
	</c:if>
</table>



<h3 class="stitle">협력업체</h3>

<table class="tbl_type11 w_100">
	<colgroup>
			<col width="10%">
			<col width="40%">
			<col width="10%">
			<col width="40%">
			<%-- <col width="7%">
			<col width="22%"> --%>
		</colgroup>
	<tr>
		<th class="th_gray th_center">협력업체 <span class='necessariness'>*</span>
			<input type="hidden" id="sel_bp_num" name="sel_bp_num" value="${SysModel.sel_bp_num}">
		</th>
		<td>
			<input type="hidden" id="bp_num" name="bp_num" value="${SysModel.bp_num}">
			<input type="text" id="bp_name" name="bp_name" value="${SysModel.bp_name}" maxlength="30" readOnly="readonly" class="new_inp inp_w80">
			<div class="fl_right btn_line_blue position_01" id="open_bp_popup">
				<span>선택</span>
			</div>
		</td>
		<th class="th_gray th_center">담당자</th>
		<td>
			<div id="div_sysUserBpId" class="manager_list2 fl_left">
				<div class="fl_left">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserBpId}" varStatus="loop">
							<c:choose>
								<c:when test="${SysModel.sel_sysUserBpId[loop.count-1] eq SysModel.bp_system_user_id}">
									<input type="hidden" id="sel_sysUserBpId_${loop.count}" name="sel_sysUserBpId" value="${SysModel.sel_sysUserBpId[loop.count-1]}" />
									<input type="hidden" id="sel_sysUserBpId_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames[loop.count-1]}" />
									<span class="txt_red">대표</span><span class="txt_name"><c:out value="${SysModel.sel_sysUserBpNames[loop.count-1]}" /></span>
								</c:when>
								<c:otherwise>
									<input type="hidden" id="sel_sysUserBpId_${loop.count}" name="sel_sysUserBpId" value="${SysModel.sel_sysUserBpId[loop.count-1]}" />
									<input type="hidden" id="sel_sysUserBpId_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames[loop.count-1]}" />
									<span class="txt_name"><c:out value="${SysModel.sel_sysUserBpNames[loop.count-1]}" /></span>
								</c:otherwise>
							</c:choose>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserBpId_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</div>
			<div onclick="javascript:fn_select_user('sysUserBpId')" id="th_sysUserBpId" class="fl_right mt10 btn_line_blue">
				<span id="sysUserBpId">선택</span>
			</div>
		</td>
		<!-- <th class="th_gray th_center">영업담당</th>
		<td>
		<input type="text" id="sales_user_info" name="sales_user_info" value="" maxlength="30" readOnly="readonly" class="new_inp new_inp_w6">
		<textarea id="sales_user_info" name="sales_user_info" style="width: 98.5%;" rows="5">${SysModel.sales_user_info}</textarea>
		</td> -->
	</tr>
	<tr>
		<th class="th_gray th_center">협력업체 1</th>
		<td>
			<input type="hidden" id="bp_num1" name="bp_num1" value="${SysModel.bp_num1}">
			<input type="text" id="bp_name1" name="bp_name1" value="${SysModel.bp_name1}" maxlength="30" readOnly="true" class="new_inp inp_w80">
			<div class="fl_right btn_line_blue" id="open_bp_popup1">
				<span>선택</span>
			</div>
		</td>
		<th class="th_gray th_center">담당자</th>
		<td>
			<div id="div_sysUserBpId1" class="manager_list2 fl_left">
				<div class="fl_left">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserBpId1}" varStatus="loop">
							<input type="hidden" id="sel_sysUserBpId1_${loop.count}" name="sel_sysUserBpId1" value="${SysModel.sel_sysUserBpId1[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserBpId1_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames1[loop.count-1]}" />
							<span class="txt_name"><c:out value="${SysModel.sel_sysUserBpNames1[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserBpId1_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</div>
			<div onclick="javascript:fn_select_user('sysUserBpId1')" id="th_sysUserBpId1" class="fl_right mt10 btn_line_blue">
				<span id="sysUserBpId1">선택</span>
			</div>
		</td>
		<!-- <th class="th_gray th_center">영업담당</th>
		<td><input type="text" id="sales_user_info" name="sales_user_info" value="" maxlength="30" readOnly="readonly" class="new_inp new_inp_w6"><textarea id="sales_user_info" name="sales_user_info" style="width: 98.5%;" rows="5">${SysModel.sales_user_info}</textarea></td> -->
	</tr>
	<tr>
		<th class="th_gray th_center">협력업체 2</th>
		<td>
			<input type="hidden" id="bp_num2" name="bp_num2" value="${SysModel.bp_num2}">
			<input type="text" id="bp_name2" name="bp_name2" value="${SysModel.bp_name2}" maxlength="30" readOnly="true" class="new_inp inp_w80">
			<div class="fl_right btn_line_blue" id="open_bp_popup2">
				<span>선택</span>
			</div>
		</td>
		<th class="th_gray th_center">담당자</th>
		<td>
			<div id="div_sysUserBpId2" class="manager_list2 fl_left">
				<div class="fl_left">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserBpId2}" varStatus="loop">
							<input type="hidden" id="sel_sysUserBpId2_${loop.count}" name="sel_sysUserBpId2" value="${SysModel.sel_sysUserBpId2[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserBpId2_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames2[loop.count-1]}" />
							<span class="txt_name"><c:out value="${SysModel.sel_sysUserBpNames2[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserBpId2_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</div>
			<div onclick="javascript:fn_select_user('sysUserBpId2')" id="th_sysUserBpId2" class="fl_right mt10 btn_line_blue">
				<span id="sysUserBpId2">선택</span>
			</div>
		</td>
		<!-- <th class="th_gray th_center">영업담당</th>
		<td><input type="text" id="sales_user_info" name="sales_user_info" value="" maxlength="30" readOnly="readonly" class="new_inp new_inp_w6"><textarea id="sales_user_info" name="sales_user_info" style="width: 98.5%;" rows="5">${SysModel.sales_user_info}</textarea></td> -->
	</tr>
	<tr>
		<th class="th_gray th_center">협력업체 3</th>
		<td>
			<input type="hidden" id="bp_num3" name="bp_num3" value="${SysModel.bp_num3}">
			<input type="text" id="bp_name3" name="bp_name3" value="${SysModel.bp_name3}" maxlength="30" readOnly="true" class="new_inp inp_w80">
			<div class="fl_right btn_line_blue" id="open_bp_popup3">
				<span>선택</span>
			</div>
		</td>
		<th class="th_gray th_center">담당자</th>
		<td>
			<div id="div_sysUserBpId3" class="manager_list2 fl_left">
				<div class="fl_left">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserBpId3}" varStatus="loop">
							<input type="hidden" id="sel_sysUserBpId3_${loop.count}" name="sel_sysUserBpId3" value="${SysModel.sel_sysUserBpId3[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserBpId3_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames3[loop.count-1]}" />
							<span class="txt_name"><c:out value="${SysModel.sel_sysUserBpNames3[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserBpId3_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</div>
			<div onclick="javascript:fn_select_user('sysUserBpId3')" id="th_sysUserBpId3" class="fl_right mt10 btn_line_blue">
				<span id="sysUserBpId3">선택</span>
			</div>
		</td>
		<!-- <th class="th_gray th_center">영업담당</th>
		<td><input type="text" id="sales_user_info" name="sales_user_info" value="" maxlength="30" readOnly="readonly" class="new_inp new_inp_w6"><textarea id="sales_user_info" name="sales_user_info" style="width: 98.5%;" rows="5">${SysModel.sales_user_info}</textarea></td> -->
	</tr>
	<tr>
		<th class="th_gray th_center">협력업체 4</th>
		<td>
			<input type="hidden" id="bp_num4" name="bp_num4" value="${SysModel.bp_num4}">
			<input type="text" id="bp_name4" name="bp_name4" value="${SysModel.bp_name4}" maxlength="30" readOnly="true" class="new_inp inp_w80">
			<div class="fl_right btn_line_blue" id="open_bp_popup4">
				<span>선택</span>
			</div>
		</td>
		<th class="th_gray th_center">담당자</th>
		<td>
			<div id="div_sysUserBpId4" class="manager_list2 fl_left">
				<div class="fl_left">
					<c:if test="${registerFlag == '수정'}">
						<c:forEach var="result" items="${SysModel.sel_sysUserBpId4}" varStatus="loop">
							<input type="hidden" id="sel_sysUserBpId4_${loop.count}" name="sel_sysUserBpId4" value="${SysModel.sel_sysUserBpId4[loop.count-1]}" />
							<input type="hidden" id="sel_sysUserBpId4_Names_${loop.count}" value="${SysModel.sel_sysUserBpNames4[loop.count-1]}" />
							<span class="txt_name"><c:out value="${SysModel.sel_sysUserBpNames4[loop.count-1]}" /></span>
							<c:if test="${loop.last}">
								<input type="hidden" value="${loop.count}" id="sysUserBpId4_cnt">
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			</div>
			<div onclick="javascript:fn_select_user('sysUserBpId4')" id="th_sysUserBpId4" class="fl_right mt10 btn_line_blue">
				<span id="sysUserBpId4">선택</span>
			</div>
		</td>
<!-- 		<th class="th_gray th_center">영업담당</th>
		<td><input type="text" id="sales_user_info" name="sales_user_info" value="" maxlength="30" readOnly="readonly" class="new_inp new_inp_w6"><textarea id="sales_user_info" name="sales_user_info" style="width: 98.5%;" rows="5">${SysModel.sales_user_info}</textarea></td>
 -->	
	</tr>
	<!-- <tr>
		<td colspan="5">
			<ul class="sm_sc_box">
				<li class="sc-box" >
					<div id="file_area"></div>
				</li>
			</ul>
		
		</td>
	</tr> -->
	
</table>

<!--기본정보 테이블 -->
<%-- <table class="tbl_type1" style="width: 99%">
	<caption>시스템 개요</caption>
	<tr>
		<th>시스템명<span class='necessariness'>*</span></th>
		<td style="width: 450px;">
			<input type="text" id="name" name="name" value="${SysModel.name}" maxlength="15" class="inp" style="width: 215px;" />
		</td>
		<th>시스템 매뉴얼</th>
		<td><ui:file attachFileModel="${SysModel.attachFile1}" name="attachFile1" size="15" mode="${fileFlag}" /></td>
	</tr>
	<tr>
		<th>협력업체<span class='necessariness'>*</span>
			<input type="hidden" id="sel_bp_num" name="sel_bp_num" value="${SysModel.sel_bp_num}">
		</th>
		<td>
			<input type="hidden" id="bp_num" name="bp_num" value="${SysModel.bp_num}">
			<input type="text" id="bp_name" name="bp_name" value="${SysModel.bp_name}" maxlength="30" readOnly="true" class="inp" style="width: 215px;" />
			<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup" align="absmiddle" style="vertical-align: middle;"/></span>
		</td>
		<th>관련규격</th>
		<td><ui:file attachFileModel="${SysModel.attachFile2}" name="attachFile2" size="15" mode="${fileFlag}" /></td>
	</tr>
	<tr>
		<th>협력업체 1</th>
		<td>
			<input type="hidden" id="bp_num1" name="bp_num1" value="${SysModel.bp_num1}">
			<input type="text" id="bp_name1" name="bp_name1" value="${SysModel.bp_name1}" maxlength="30" readOnly="true" class="inp" style="width: 215px;" />
			<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup1" align="absmiddle" style="vertical-align: middle;"/></span>
		</td>
		<th>PKG 표준절차서</th>
		<td><ui:file attachFileModel="${SysModel.attachFile3}" name="attachFile3" size="15" mode="${fileFlag}" /></td>
	</tr>
	<tr>
		<th>협력업체 2</th>
		<td>
			<input type="hidden" id="bp_num2" name="bp_num2" value="${SysModel.bp_num2}">
			<input type="text" id="bp_name2" name="bp_name2" value="${SysModel.bp_name2}" maxlength="30" readOnly="true" class="inp" style="width: 215px;" />
			<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup2" align="absmiddle" style="vertical-align: middle;"/></span>
		</td>
 		<c:choose>
			<c:when test="${SysModel.attachFile6 == null}">
				<th id="selectRowspan" rowspan="2">추가첨부</th>
			</c:when>
			<c:otherwise>
				<th id="selectRowspan" rowspan="11">추가첨부</th>
			</c:otherwise>
		</c:choose>
 		<td>
 			<table border="0" cellpadding="0" cellspacing="0">
 				<tr>
 					<td>
 						<select id="selectID" onchange="fnSelectBoxCnt();">
							<c:forEach var="selectItem" begin="1" end="10" step="1">	
								<c:choose>
									<c:when test="${SysModel.attachFile6 == null}">
										<option><c:out value="${selectItem}" /></option>
									</c:when>
									<c:otherwise>
										<option selected><c:out value="${selectItem}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
 					</td>
 					<td><div class="help_notice">첨부파일은 최대10개 까지 추가 등록 가능 합니다.</div></td>
 				</tr>
 			</table>
 		</td>
 	</tr>
	<tr>
		<th>협력업체 3</th>
		<td>
			<input type="hidden" id="bp_num3" name="bp_num3" value="${SysModel.bp_num3}">
			<input type="text" id="bp_name3" name="bp_name3" value="${SysModel.bp_name3}" maxlength="30" readOnly="true" class="inp" style="width: 215px;" />
			<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup3" align="absmiddle" style="vertical-align: middle;"/></span>
		</td>
 		<td><div id="display_file1"><ui:file attachFileModel="${SysModel.attachFile6}" name="attachFile6" size="15" mode="${fileFlag}" /></div></td>
	</tr>
	<tr>
		<th>협력업체 4</th>
		<td>
			<input type="hidden" id="bp_num4" name="bp_num4" value="${SysModel.bp_num4}">
			<input type="text" id="bp_name4" name="bp_name4" value="${SysModel.bp_name4}" maxlength="30" readOnly="true" class="inp" style="width: 215px;" />
			<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup4" align="absmiddle" style="vertical-align: middle;"/></span>
		</td>
		<td>
			<c:choose>
				<c:when test="${SysModel.attachFile6 == null}">
					<div id="display_file2" style="display:none;"><ui:file attachFileModel="${SysModel.attachFile7}" name="attachFile7" size="15" mode="${fileFlag}" /></div>
				</c:when>
				<c:otherwise>
					<div id="display_file2"><ui:file attachFileModel="${SysModel.attachFile7}" name="attachFile7" size="15" mode="${fileFlag}" /></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
 		<th>영향 시스템</th>
 		<td><input type="text" id="impact_systems" name="impact_systems" value="${SysModel.impact_systems}" maxlength="100" class="inp" style="width: 215px;" /></td>	
		<td>
			<c:choose>
				<c:when test="${SysModel.attachFile6 == null}">
					<div id="display_file3" style="display:none;"><ui:file attachFileModel="${SysModel.attachFile8}" name="attachFile8" size="15" mode="${fileFlag}" /></div>
				</c:when>
				<c:otherwise>
					<div id="display_file3"><ui:file attachFileModel="${SysModel.attachFile8}" name="attachFile8" size="15" mode="${fileFlag}" /></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td colspan="2"><div class="help_notice">영향 시스템은 복수 입력 시 콤마로 구분하여 주세요.</div></td>
		<td>
			<c:choose>
				<c:when test="${SysModel.attachFile6 == null}">
					<div id="display_file4" style="display:none;"><ui:file attachFileModel="${SysModel.attachFile9}" name="attachFile9" size="15" mode="${fileFlag}" /></div>
				</c:when>
				<c:otherwise>
					<div id="display_file4"><ui:file attachFileModel="${SysModel.attachFile9}" name="attachFile9" size="15" mode="${fileFlag}" /></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th>공급사</th>
		<td><input type="text" id="supply" name="supply" value="${SysModel.supply }" maxlength="100" class="inp" style="width: 215px;" /></td>
		<td>
			<c:choose>
				<c:when test="${SysModel.attachFile6 == null}">
					<div id="display_file5" style="display:none;"><ui:file attachFileModel="${SysModel.attachFile10}" name="attachFile10" size="15" mode="${fileFlag}" /></div>
				</c:when>
				<c:otherwise>
					<div id="display_file5"><ui:file attachFileModel="${SysModel.attachFile10}" name="attachFile10" size="15" mode="${fileFlag}" /></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>	
	<tr>
		<th>Full Name</th>
		<td><input type="text" id="full_name" name="full_name" value="${SysModel.full_name }" maxlength="150" class="inp" style="width: 400px;" /></td>
		<td>
			<c:choose>
				<c:when test="${SysModel.attachFile6 == null}">
					<div id="display_file6" style="display:none;"><ui:file attachFileModel="${SysModel.attachFile11}" name="attachFile11" size="15" mode="${fileFlag}" /></div>
				</c:when>
				<c:otherwise>
					<div id="display_file6"><ui:file attachFileModel="${SysModel.attachFile11}" name="attachFile11" size="15" mode="${fileFlag}" /></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th rowspan="3">한줄설명</th>
		<td rowspan="3"><textarea id="oneLine_explain" name="oneLine_explain" rows="5" cols="50" >${SysModel.oneLine_explain }</textarea></td>
		<td>
			<c:choose>
				<c:when test="${SysModel.attachFile6 == null}">
					<div id="display_file7" style="display:none;"><ui:file attachFileModel="${SysModel.attachFile12}" name="attachFile12" size="15" mode="${fileFlag}" /></div>
				</c:when>
				<c:otherwise>
					<div id="display_file7"><ui:file attachFileModel="${SysModel.attachFile12}" name="attachFile12" size="15" mode="${fileFlag}" /></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td>
			<c:choose>
				<c:when test="${SysModel.attachFile6 == null}">
					<div id="display_file8" style="display:none;"><ui:file attachFileModel="${SysModel.attachFile13}" name="attachFile13" size="15" mode="${fileFlag}" /></div>
				</c:when>
				<c:otherwise>
					<div id="display_file8"><ui:file attachFileModel="${SysModel.attachFile13}" name="attachFile13" size="15" mode="${fileFlag}" /></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td>
			<c:choose>
				<c:when test="${SysModel.attachFile6 == null}">
					<div id="display_file9" style="display:none;"><ui:file attachFileModel="${SysModel.attachFile14}" name="attachFile14" size="15" mode="${fileFlag}" /></div>
				</c:when>
				<c:otherwise>
					<div id="display_file9"><ui:file attachFileModel="${SysModel.attachFile14}" name="attachFile14" size="15" mode="${fileFlag}" /></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th rowspan="3">시설현황<br/>상세설명</th>
		<td rowspan="3"><textarea id="job_history" name="job_history" rows="5" cols="50" >${SysModel.job_history }</textarea></td>
		<td>
			<c:choose>
				<c:when test="${SysModel.attachFile6 == null}">
					<div id="display_file10" style="display:none;"><ui:file attachFileModel="${SysModel.attachFile15}" name="attachFile15" size="15" mode="${fileFlag}" /></div>
				</c:when>
				<c:otherwise>
					<div id="display_file10"><ui:file attachFileModel="${SysModel.attachFile15}" name="attachFile15" size="15" mode="${fileFlag}" /></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<th rowspan="3">2015<br/>증감설 계획</th>
		<td rowspan="3"><textarea id="thisYear_job_plan" name="thisYear_job_plan" rows="5" cols="50" >${SysModel.thisYear_job_plan }</textarea></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<th>2016<br/>PKG계획</th>
		<td><textarea id="nextYear_pkg_plan" name="nextYear_pkg_plan" rows="5" cols="50" >${SysModel.nextYear_pkg_plan }</textarea></td>
	</tr>
	<tr>
		<th>비고</th>
		<td><textarea id="note" name="note" rows="5" cols="50" >${SysModel.note }</textarea></td>
	</tr>
</table> --%>

<!--적용이력 테이블 
<div id="reg_info">
<c:choose>
	<c:when test="${registerFlag == '수정'}">
		<br />
		<br />
		<table class="tbl_type3" style="width: 1150px;">
			<caption>적용이력</caption>
			<!--셀넓이조절가능 --
			<colgroup>
				<col width="200px" />
				<col width="380px;" />
				<col width="200px;" />
				<col width="60px;" />
				<col width="60px;" />
				<col width="125px;" />
				<col width="125px;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">버전</th>
					<th scope="col">검증 요청 제목</th>
					<th scope="col">검증기간</th>
					<th scope="col">검증개수</th>
					<th scope="col">개선개수</th>
					<th scope="col">상태</th>
					<th scope="col" class="tbl_rline">완료일</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${fn:length(SysModel.pkgHistoryList) == 0}">
					<tr>
						<td colspan="7">적용 이력 정보가 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach var="pkgModel" items="${SysModel.pkgHistoryList}" varStatus="status">
					<tr onMouseOver="this.style.cursor='pointer'">
						<td onclick="javascript:fn_historyToggle('${pkgModel.pkg_seq}')" class="tbl_lline"><c:out value="${pkgModel.ver}"></c:out>&nbsp;</td>
						<td align="left" style="padding:0 0 0 10px;"><a href="javascript:fn_pkg_read('read', '${pkgModel.pkg_seq}', 'SysModel')"><c:out value="${fn:substring(pkgModel.title, 0, 30)}"/><c:out value="${fn:length(pkgModel.title) > 30 ? '...' : ''}" /></a>&nbsp;</td>
						<td onclick="javascript:fn_historyToggle('${pkgModel.pkg_seq}')">
							<c:out value="${pkgModel.verify_date_start}"></c:out> ~ 
							<c:out value="${pkgModel.verify_date_end}"></c:out>
						</td>
						<td onclick="javascript:fn_historyToggle('${pkgModel.pkg_seq}')"><c:out value="${pkgModel.total_verify }"></c:out></td>
						<td onclick="javascript:fn_historyToggle('${pkgModel.pkg_seq}')"><c:out value="${pkgModel.total_improve }"></c:out></td>
						<td onclick="javascript:fn_historyToggle('${pkgModel.pkg_seq}')"><c:out value="${pkgModel.status_name}"></c:out>&nbsp;</td>
						<td onclick="javascript:fn_historyToggle('${pkgModel.pkg_seq}')"><c:out value="${fn:substring(pkgModel.update_date,0,10) }"></c:out></td>
					</tr>
					<tr>
						<td colspan="7" class="tbl_col" >
							<div id="history_${pkgModel.pkg_seq}" style="display:none; width:800px; padding-left: 300px;">
							<table style="width:800px;" class="tbl_type5">
								<colgroup>
									<col width="100px;" />
									<col width="250px;" />
									<col width="250px;" />
									<col width="200px;" />
								</colgroup>
								<thead>
									<tr>
										<th class="tbl_lline" style="border-bottom: 2px solid #d8d7d7;" scope="col">구분</th>
										<th style="border-bottom: 2px solid #d8d7d7;" scope="col">팀</th>
										<th style="border-bottom: 2px solid #d8d7d7;" scope="col">장비</th>
										<th class="tbl_rline" style="border-bottom: 2px solid #d8d7d7;" scope="col">적용기간</th>
									</tr>
								</thead>
								<c:if test="${fn:length(pkgModel.pkgEquipmentModelList) == 0}">
									<tr>
										<td class="tbl_lline" colspan="4">해당 적용이력에 대한 장비 적용 정보가 없습니다.</td>
									</tr>
								</c:if>
								<c:forEach var="pkgEquipmentModel" items="${pkgModel.pkgEquipmentModelList}" varStatus="mapStatus">
									<tr>
										<td class="tbl_lline" style="background:${pkgEquipmentModel.work_gubun == 'S' ? '#fafad2' : '#fff0f5'};"><c:out value="${pkgEquipmentModel.work_gubun == 'S' ? '초도' : '확대'}"></c:out>&nbsp;</td>
										<td style="background:${pkgEquipmentModel.work_gubun == 'S' ? '#fafad2' : '#fff0f5'}; text-align: left; padding-left: 5px;"><c:out value="${pkgEquipmentModel.team_name}"></c:out>&nbsp;</td>
										<td style="background:${pkgEquipmentModel.work_gubun == 'S' ? '#fafad2' : '#fff0f5'}; text-align: left; padding-left: 5px;">
											<c:out value="${fn:substring(pkgEquipmentModel.equipment_name,0,15)}" />
											<c:out value="${fn:length(pkgEquipmentModel.equipment_name) > 15 ? '...' : ''}" />
											&nbsp;
											</td>
										<td style="background:${pkgEquipmentModel.work_gubun == 'S' ? '#fafad2' : '#fff0f5'};">
											<c:out value="${pkgEquipmentModel.work_date}" />
											[<c:out value="${pkgEquipmentModel.start_time1}" />:<c:out value="${pkgEquipmentModel.start_time2}" /> ~ 
											<c:out value="${pkgEquipmentModel.end_time1}" />:<c:out value="${pkgEquipmentModel.end_time2}" />]
										</td>
									</tr>
								</c:forEach>
							</table>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table> -->
<br/><br/>
		<!--등록정보 테이블 -->
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
