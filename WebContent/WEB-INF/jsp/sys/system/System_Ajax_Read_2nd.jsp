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
<script type="text/javascript" src="/js/pkgmg/jsgantt.js"></script>
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
	width:90%;
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
	
	doModalPopup("#road_map_write", 1, "click", 900, 720, "/sys/system/System_Popup_RoadMap.do?system_seq=${SysModel.system_seq}");
	
	var g = new JSGantt.GanttChart('g',document.getElementById('GanttCharthIV'), 'day');
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
	
	JSGantt.changeFormat("week", g);
	
	$(document).ready(function($) {
		
		$("input[id=name]").focus();
		'<c:forEach items="${roadMapList}" var="list" varStatus="status">';
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
		'</c:forEach>';
		
		$("#btn_upload").change(function() {
			btnUpload(this);
		});
		
	});

	var count = ${SysModel.totalCount};
	if(count == 0){
		var sel_id_array = new Array("sysUserVerifyId", "sysUserApprovalId","devsysUserVerifyId",
				"devsysUserApprovalId",	"sysUserBizId", "sysUserBpId",
				"sysUserBpId1", "sysUserBpId2", "sysUserBpId3", "sysUserBpId4",
				"sysUserVolId", "sysUserSecId", "sysUserChaId", "sysUserNonId");	
	}else if(count > 0){
		var sel_id_array = new Array("sysUserVerifyId", "sysUserApprovalId","devsysUserVerifyId",
				"devsysUserApprovalId", "sysUserBizId", "sysUserBpId",
				"sysUserBpId1", "sysUserBpId2", "sysUserBpId3", "sysUserBpId4", "sysUserOperId",
				"sysUserVolId", "sysUserSecId", "sysUserChaId", "sysUserNonId");
	}
	
	function fn_select_user(sel_id) {
		if (sel_id.match("^sysUserBpId")) {
			var bp_idx = sel_id.substring("sysUserBpId".length);
			if (!isNullAndTrim_J($("#bp_num"+bp_idx), "시스템 개요에서 협력업체를 먼저 선택하여 주세요.")) {
				$("#"+sel_id).attr("checked", false);
				$("#" + selected_system_user_id).attr("checked", true);
				return false;
			}
			$("#sel_bp_num").val($("#bp_num"+bp_idx).val());
		}
		
		if (sel_id.match("^sysUserOperId")) {
			if(confirm("장비담당자를 일괄변경하게 되면 기존 담당자는 제거 됩니다.")) {
			} else {
				$("#"+sel_id).attr("checked", false);
				$("#" + selected_system_user_id).attr("checked", true);
				return false;
			}
		}
		
		selected_system_user_id = sel_id;
		fn_systemUser_read();
		fn_user_selbox_disable();
		$("#" + sel_id).attr("checked", true);
		$("#sel_" + sel_id).attr("disabled", false);
		$("#th_" + sel_id).css("background-color", "#F9D537");
	}

	function fn_systemUser_read() {
		var url = "/sys/system/SystemUser_Ajax_Read.do";
		if (selected_system_user_id.match("^sysUserBpId")) {
			url = "/sys/system/SystemBpUser_Ajax_Read.do";
		}
		doSubmit("SysModel", url, "fn_callback_systemUser_read");
	}

	function fn_callback_systemUser_read(data) {
		$("#system_user_area").html(data);
		fn_initSystemUser($("#label_" + selected_system_user_id).text());
	}
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
	function fn_user_selbox_disable() {
		for ( var i = 0; i < sel_id_array.length; i++) {
			$("#sel_" + sel_id_array[i]).attr("disabled", true);
			$("#th_" + sel_id_array[i]).css("background-color", "#ebebeb");
		}
	}

	function isValidation() {

		if (!isNullAndTrim_J($("#name"), "시스템명은 필수 입력사항 입니다."))
			return false;

		if (!isNullAndTrim_J($("#bp_num"), "업체명은 필수 입력사항 입니다."))
			return false;
/*		
		var downtime = $("#downtime");
		if(downtime.val() != ""){
			if (!isNumber_J(downtime, "서비스 중지 시간은 숫자 이여야 합니다."))
				return false;
		}
*/		
		var sel_id_array_check = new Array("sysUserVerifyId", "sysUserApprovalId", "sysUserBpId");
		for ( var x = 0; x < sel_id_array_check.length; x++) {
			var selectbox = $("#sel_" + sel_id_array_check[x]).get(0);
			if (selectbox.length == 0) {
				alert($("#label_" + sel_id_array_check[x]).text() + "의 담당자 선택은 필수 입력사항 입니다.");
				return false;
			}
		}

		var systemUserId = $("#select_system_user option:selected").val();
		
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
		if (selected_system_user_id.match("^sysUserBpId")) {
			if(bp_num == ""){
				$("#sysUserBpId").attr("checked", false);
				$("#system_user_area").html("");
				fn_user_selbox_disable();
			}else{
				fn_systemUser_read();
			}
		}

		//업체 변경 시 기존 업체 담당자 제거
		if (bp_idx == '') {
			var selectbox = $("#sel_sysUserBpId").get(0);
			var count = selectbox.length;
			for ( var x = count - 1; x >= 0; x--) {
				selectbox.options[x] = null;
			}
		}
	}

	// 선택된 각 담당자 추가
	function fn_add_sysUser() {

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
	}

	// 선택된 각 담당자 제거
	function fn_remove_sysUser() {
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
	}

	// 각 담당자 selectbox 강제 선택 (전송을 위해)
	function fn_sysUser_select_setting() {
		for ( var x = 0; x < sel_id_array.length; x++) {
			$("#sel_" + sel_id_array[x]).attr("disabled", false);
			var selectbox = $("#sel_" + sel_id_array[x]).get(0);
			for ( var y = 0; y < selectbox.length; y++) {
				$(selectbox.options[y]).attr("selected", true);
			}
		}
		$("#system_user_id").val($("#select_system_user option:selected").val());
		$("#dev_system_user_id").val($("#dev_select_system_user option:selected").val());
		$("#bp_system_user_id").val($("#bp_select_system_user option:selected").val());
		
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
		fn_systemUserHandle($("#system_user_id").val());
		fn_devsystemUserHandle($("#dev_system_user_id").val());
		fn_bpsystemUserHandle($("#bp_system_user_id").val());
	}
	
	function fn_systemUserHandle(systemUserId){
		var map = new Map();
		
		for ( var x = 0; x < sel_id_array.length; x++) {
			
			if (sel_id_array[x] == "sysUserVerifyId" || sel_id_array[x] =="sysUserApprovalId") {
				
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
			
			if (sel_id_array[x] == "devsysUserVerifyId" || sel_id_array[x] =="devsysUserApprovalId") {
				
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
			
			if (sel_id_array[x] == "sysUserBpId" || sel_id_array[x] =="sysUserBpId") {
				
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
	
	function fn_pop_roadMap(system_seq){
		window.open("/sys/system/System_Popup_RoadMap.do?system_seq="+system_seq,"roadMap","width=900, height=720, scrollbars=yes, resizable=no, statusbar=no");
	}
	
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
// 	    console.log(th.files);
// 	    console.log($th.files[0]);
	}
   
	// 파일 멀티 업로드
	function F_FileMultiUpload(files, obj) {
// 	     if(confirm(files.length + "개의 파일을 업로드 하시겠습니까?") ) {
// 	console.log(files);
	         var data = new FormData();
	         for (var i = 0; i < files.length; i++) {
	            data.append('files', files[i]);
	            console.log(files[i]);
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
	            	
	            	console.log("success");
	            	searchTree();
// 	                F_FileMultiUpload_Callback(res.files);
	            },   
		        error:function(e){  
		            console.log(e.responseText);  
		        }
	         });
// 	     }
	}
	
	// 파일 멀티 업로드 Callback
	function F_FileMultiUpload_Callback(files) {
	     for(var i=0; i < files.length; i++)
	         console.log(files[i].file_nm + " - " + files[i].file_size);
	}
	var treeObj;
	
	function searchTree(type){

		$.ajax({
	        type:"POST",  
	        url:"/sys/system/SystemFileData_Ajax_Read.do",  
	        data:{"master_file_id" : $("#master_file_id").val()},
	        success:function(data){
	        	console.log(data);

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
	        	console.log(result);
	        	
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
		console.log("beforeEditName");
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
	        	console.log(data);
	        	searchTree();
	        },   
	        error:function(e){  
	            console.log(e.responseText);  
	        }
	    });
		
		
	}
	function onRemove(e, treeId, treeNode) {
// 		console.log("onRemove");
	}
	function beforeRename(treeId, treeNode, newName, isCancel) {
		console.log("beforeRename");
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
		console.log(e);
		console.log(treeNode);
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
				$("#fileList").append("<tr><td colspan='5'>등록된 자료가 없습니다.</td></tr>");
				return;
			}
			
		}else{
			list = treeNode.getParentNode().children;
			system = treeNode.getParentNode().name;
		}
		
		var html = "";
			
		for(var i=0; i < list.length; i++){
			html = "<tr>";
			html += "<td>"+system+"</td>";
			html += "<td style='text-align: left; text-decoration: underline; color:blue;'>"
			html += "<a href=\"#\" onclick=\"javascript:downloadFile('"+list[i].file_name+"','"+list[i].name+"','"+list[i].file_path+"') ; return false; \">"+list[i].name+"</a>";
			html += "</td>";
			html += "<td>"+byteCalculation(list[i].file_size)+"</td>";
			html += "<td>"+list[i].reg_date+"</td>";
			html += "<td>"+list[i].reg_user+"</td>";
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

	
	
	

</script>

<input type="hidden" id="master_file_id" name="master_file_id" value="${SysModel.master_file_id}">
<input type="hidden" id="system_user_id" name="system_user_id" value="${SysModel.system_user_id}">
<input type="hidden" id="dev_system_user_id" name="dev_system_user_id" value="${SysModel.dev_system_user_id}">
<input type="hidden" id="bp_system_user_id" name="bp_system_user_id" value="${SysModel.bp_system_user_id}">

<input type="hidden" id="retUrl" name="retUrl"/>
<input type="hidden" id="pkg_seq" name="pkg_seq"/>



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
<!--시스템 개요 테이블 -->
<table class="tbl_type1" style="width: 99%">
	<caption>시스템 개요</caption>
	<tr>
		<th>시스템명<span class='necessariness'>*</span></th>
		<td style="width: 450px;">
			<input type="text" id="name" name="name" value="${SysModel.name}" maxlength="15" class="inp" style="width: 320px;" />
		</td>
		<th>Full Name</th>
		<td><input type="text" id="full_name" name="full_name" value="${SysModel.full_name }" maxlength="150" class="inp" style="width: 320px;" /></td>
	</tr>
	<tr>
		<th>영향 시스템</th>
 		<td><input type="text" id="impact_systems" name="impact_systems" value="${SysModel.impact_systems}" maxlength="100" class="inp" style="width: 320px;" /></td>
		<th>공급사</th>
		<td><input type="text" id="supply" name="supply" value="${SysModel.supply }" maxlength="100" class="inp" style="width: 320px;" /></td>
	</tr>
	<tr>
		<td colspan="2" rowspan="1"><div class="help_notice">영향 시스템은 복수 입력 시 콤마로 구분하여 주세요.</div></td>
		<th colspan="1" rowspan="2">비고</th>
		<td colspan="1" rowspan="2"><textarea id="note" name="note" rows="5" cols="50" >${SysModel.note }</textarea></td>
	</tr>
	<tr>
		<th colspan="1" rowspan="3">한줄설명</th>
		<td colspan="1" rowspan="3"><textarea id="oneLine_explain" name="oneLine_explain" rows="5" cols="50" >${SysModel.oneLine_explain }</textarea></td>
	</tr>
	<tr>
		<th>영업담당자 정보</th>
		<td colspan="1"><textarea id="sales_user_info" name="sales_user_info" style="width: 98.5%;" rows="5">${SysModel.sales_user_info}</textarea></td>
	</tr>
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
	<tr>
		<th>협력업체<span class='necessariness'>*</span>
			<input type="hidden" id="sel_bp_num" name="sel_bp_num" value="${SysModel.sel_bp_num}">
		</th>
		<td>
			<input type="hidden" id="bp_num" name="bp_num" value="${SysModel.bp_num}">
			<input type="text" id="bp_name" name="bp_name" value="${SysModel.bp_name}" maxlength="30" readOnly="readonly" class="inp" style="width: 215px;" />
			<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup" align="absmiddle" style="vertical-align: middle;"/></span>
		</td>
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
		<th>협력업체 1</th>
		<td>
			<input type="hidden" id="bp_num1" name="bp_num1" value="${SysModel.bp_num1}">
			<input type="text" id="bp_name1" name="bp_name1" value="${SysModel.bp_name1}" maxlength="30" readOnly="true" class="inp" style="width: 215px;" />
			<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup1" align="absmiddle" style="vertical-align: middle;"/></span>
		</td>
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
		<th>협력업체 2</th>
		<td>
			<input type="hidden" id="bp_num2" name="bp_num2" value="${SysModel.bp_num2}">
			<input type="text" id="bp_name2" name="bp_name2" value="${SysModel.bp_name2}" maxlength="30" readOnly="true" class="inp" style="width: 215px;" />
			<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup2" align="absmiddle" style="vertical-align: middle;"/></span>
		</td>
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
		<th>협력업체 3</th>
		<td>
			<input type="hidden" id="bp_num3" name="bp_num3" value="${SysModel.bp_num3}">
			<input type="text" id="bp_name3" name="bp_name3" value="${SysModel.bp_name3}" maxlength="30" readOnly="true" class="inp" style="width: 215px;" />
			<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup3" align="absmiddle" style="vertical-align: middle;"/></span>
		</td>
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
		<th>협력업체 4</th>
		<td>
			<input type="hidden" id="bp_num4" name="bp_num4" value="${SysModel.bp_num4}">
			<input type="text" id="bp_name4" name="bp_name4" value="${SysModel.bp_name4}" maxlength="30" readOnly="true" class="inp" style="width: 215px;" />
			<span><img src="/images/pop_btn_search1.gif" alt="업체 선택" style="cursor: pointer;" id="open_bp_popup4" align="absmiddle" style="vertical-align: middle;"/></span>
		</td>
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
	<%-- 
	<tr>
		<th>영업담당자 정보</th>
		<td colspan="1"><textarea id="sales_user_info" name="sales_user_info" style="width: 98.5%;" rows="5">${SysModel.sales_user_info}</textarea></td>
	</tr>
	<tr>
		<td colspan="3">
			<ul class="sm_sc_box">
				<li class="sc-box" >
					<div id="file_area"></div>
				</li>
			</ul>
		
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
<div>
	<!-- 시스템 트리 -->
	<div id="fileTree" class="sysc_div4">
		<ul id="treeDemo" class="ztree" ></ul>
	</div>

	<!-- file Drag & Drop -->
	<div id="dropzone" class="sysc_div4" style="width:746px !important">
		<div id="system_File_list_area" style="padding:0px 10px 0px 10px; height:235px; clear:both;">
			<input type="hidden" id="parent_tree_id" value=""/>
			<input type="hidden" id="select_tree_node" value=""/>
			<div style="position: relative; margin-bottom: 3px;margin-top: 3px;"> 
				<form style="float: right;">
					<button class="replace">파일 업로드</button>
					<input type="file" value="파일 업로드" class="upload" id="btn_upload" multiple>
				</form>
			</div>
			<table id="scrollTable" class="tbl_type3" style="width: 100%;"  border="1" >
					<colgroup>
						<col width="20%" />
						<col width="47%" />
						<col width="10%" />
						<col width="13" />
						<col width="10" />
					</colgroup>
					<tr class="pop_tbl_type3">
						<th>자료구분</th>
						<th>파일명</th>
						<th>파일크기</th>
						<th>등록일</th>
						<th>등록자</th>
					</tr>
				<tbody id="fileList" style="height:240px;">
				</tbody>
			</table>
		</div>
	</div>
</div>
<!-- //시스템 파일 트리 -->


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

<div class="mag_top1">
	<h3 class="table_ttl">담당자정보</h3><div class="help_notice" style="float: right; position:relaitve; margin-top:-5px; margin-right:15px;">담당자 추가 후 반드시 저장/수정 버튼을 클릭해야 등록됩니다.</div>
</div>
<!--담당자정보 테이블 -->


<ul class="sm_sc_box">
	<li class="sc-box sc_list">			
			<table id="user_table" class="tbl_type1" style="width: 100%">
				<colgroup>
					<col width="100px;">
					<col width="*">
				</colgroup>
				<tr>
					<th onclick="javascript:fn_select_user('devsysUserVerifyId')" id="th_devsysUserVerifyId" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="devsysUserVerifyId" />
						<label id="label_devsysUserVerifyId" for="devsysUserVerifyId">개발검증</label>
					</th>
					<td>
						<select id="sel_devsysUserVerifyId" name="sel_devsysUserVerifyId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_devsysUserVerifyId}" varStatus="loop">
									<option value="${SysModel.sel_devsysUserVerifyId[loop.count-1]}">
										<c:out value="${SysModel.sel_devsysUserVerifyNames[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
			
				</tr>
				<tr>
					<th onclick="javascript:fn_select_user('devsysUserApprovalId')" id="th_devsysUserApprovalId" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="devsysUserApprovalId" />
						<label id="label_devsysUserApprovalId" for="devsysUserApprovalId">개발승인</label>
					</th>
					<td>
						<select id="sel_devsysUserApprovalId" name="sel_devsysUserApprovalId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_devsysUserApprovalId}" varStatus="loop">
									<option value="${SysModel.sel_devsysUserApprovalId[loop.count-1]}">
										<c:out value="${SysModel.sel_devsysUserApprovalNames[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<th onclick="javascript:fn_select_user('sysUserVerifyId')" id="th_sysUserVerifyId" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="sysUserVerifyId" />
						<label id="label_sysUserVerifyId" for="sysUserVerifyId">상용검증</label><span class='necessariness'>*</span>
					</th>
					<td>
						<select id="sel_sysUserVerifyId" name="sel_sysUserVerifyId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserVerifyId}" varStatus="loop">
									<option value="${SysModel.sel_sysUserVerifyId[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserVerifyNames[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<th onclick="javascript:fn_select_user('sysUserApprovalId')" id="th_sysUserApprovalId" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="sysUserApprovalId" />
						<label id="label_sysUserApprovalId" for="sysUserApprovalId">상용승인</label><span class='necessariness'>*</span>
					</th>
					<td>
						<select id="sel_sysUserApprovalId" name="sel_sysUserApprovalId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserApprovalId}" varStatus="loop">
									<option value="${SysModel.sel_sysUserApprovalId[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserApprovalNames[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<th onclick="javascript:fn_select_user('sysUserBizId')" id="th_sysUserBizId" style="text-align: left; padding: 5px;"><input type="radio" name="radio" id="sysUserBizId" /><label id="label_sysUserBizId" for="sysUserBizId">사업기획</label></th>
					<td><select id="sel_sysUserBizId" name="sel_sysUserBizId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserBizId}" varStatus="loop">
									<option value="${SysModel.sel_sysUserBizId[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserBizNames[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
					</select></td>
				</tr>
				<tr>
					<th onclick="javascript:fn_select_user('sysUserBpId')" id="th_sysUserBpId" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="sysUserBpId" />
						<label id="label_sysUserBpId">협력업체</label>
						<span class='necessariness'>*</span>
					</th>
					<td>
						<select id="sel_sysUserBpId" name="sel_sysUserBpId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserBpId}" varStatus="loop">
									<option value="${SysModel.sel_sysUserBpId[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserBpNames[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>	
				<tr>
					<th onclick="javascript:fn_select_user('sysUserBpId1')" id="th_sysUserBpId1" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="sysUserBpId1" />
						<label id="label_sysUserBpId1">협력업체1</label>
					</th>
					<td>
						<select id="sel_sysUserBpId1" name="sel_sysUserBpId1" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserBpId1}" varStatus="loop">
									<option value="${SysModel.sel_sysUserBpId1[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserBpNames1[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>	
				<tr>
					<th onclick="javascript:fn_select_user('sysUserBpId2')" id="th_sysUserBpId2" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="sysUserBpId2" />
						<label id="label_sysUserBpId2">협력업체2</label>
					</th>
					<td>
						<select id="sel_sysUserBpId2" name="sel_sysUserBpId2" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserBpId2}" varStatus="loop">
									<option value="${SysModel.sel_sysUserBpId2[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserBpNames2[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>	
				<tr>
					<th onclick="javascript:fn_select_user('sysUserBpId3')" id="th_sysUserBpId3" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="sysUserBpId3" />
						<label id="label_sysUserBpId3">협력업체3</label>
					</th>
					<td>
						<select id="sel_sysUserBpId3" name="sel_sysUserBpId3" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserBpId3}" varStatus="loop">
									<option value="${SysModel.sel_sysUserBpId3[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserBpNames3[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>	
				<tr>
					<th onclick="javascript:fn_select_user('sysUserBpId4')" id="th_sysUserBpId4" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="sysUserBpId4" />
						<label id="label_sysUserBpId4">협력업체4</label>
					</th>
					<td>
						<select id="sel_sysUserBpId4" name="sel_sysUserBpId4" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserBpId4}" varStatus="loop">
									<option value="${SysModel.sel_sysUserBpId4[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserBpNames4[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<!-- 검증4타입 -->
				<tr>
					<th onclick="javascript:fn_select_user('sysUserVolId')" id="th_sysUserVolId" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="sysUserVolId" />
						<label id="label_sysUserVolId">용량검증</label>
					</th>
					<td>
						<select id="sel_sysUserVolId" name="sel_sysUserVolId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserVolId}" varStatus="loop">
									<option value="${SysModel.sel_sysUserVolId[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserVolNames[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<th onclick="javascript:fn_select_user('sysUserSecId')" id="th_sysUserSecId" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="sysUserSecId" />
						<label id="label_sysUserSecId">보안검증</label>
					</th>
					<td>
						<select id="sel_sysUserSecId" name="sel_sysUserSecId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserSecId}" varStatus="loop">
									<option value="${SysModel.sel_sysUserSecId[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserSecNames[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<th onclick="javascript:fn_select_user('sysUserChaId')" id="th_sysUserChaId" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="sysUserChaId" />
						<label id="label_sysUserChaId">과금검증</label>
					</th>
					<td>
						<select id="sel_sysUserChaId" name="sel_sysUserChaId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserChaId}" varStatus="loop">
									<option value="${SysModel.sel_sysUserChaId[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserChaNames[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<th onclick="javascript:fn_select_user('sysUserNonId')" id="th_sysUserNonId" style="text-align: left; padding: 5px;">
						<input type="radio" name="radio" id="sysUserNonId" />
						<label id="label_sysUserNonId">비기능검증</label>
					</th>
					<td>
						<select id="sel_sysUserNonId" name="sel_sysUserNonId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
							<c:if test="${registerFlag == '수정'}">
								<c:forEach var="result" items="${SysModel.sel_sysUserNonId}" varStatus="loop">
									<option value="${SysModel.sel_sysUserNonId[loop.count-1]}">
										<c:out value="${SysModel.sel_sysUserNonNames[loop.count-1]}" />
									</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<!-- 검증4타입 -->
			</table>
			
			<table class="tbl_type1 mt_5" style="width: 100%">
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

	</li>
	<li class="sc-box bt_area">
			<!--추가,삭제버튼 -->
			<ul id="system_user_button">
				<li><a href="javascript:fn_add_sysUser();"><img src="/images/ico_addarrow.gif" alt="추가" /></a></li>
				<li><a href="javascript:fn_remove_sysUser();"><img src="/images/ico_deletearrow.gif" alt="삭제" /></a></li>
			</ul>
	</li>
	<li class="sc-box" style="width: 760px; padding: 0px;">
		<div id="system_user_area" style="width: 760px; padding: 0px;"></div>
	</li>
</ul>

<table class="tbl_type2" style="margin-top: 15px;">
	<c:if test="${SysModel.totalCount > 0}">
		<tr>
			<th onclick="javascript:fn_select_user('sysUserOperId')" id="th_sysUserOperId" style="text-align: left; padding: 5px; width: 190px;" rowspan="2">
				<input type="radio" name="radio" id="sysUserOperId" />
				<label id="label_sysUserOperId">국사별 장비 담당자 변경</label>
			</th>
			<td>
				<select id="idc_seq" name="idc_seq"  style="padding: 0px; width: 98.5%">
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
			</td>
		</tr>
		<tr>
			<td>
				<select id="sel_sysUserOperId" name="sel_sysUserOperId" disabled="disabled" multiple="multiple" size="40" class="sysc_selectm_bp">
				</select>			
			</td>
		</tr>
	</c:if>
</table>


<!--적용이력 테이블 -->
<div id="reg_info">
<c:choose>
	<c:when test="${registerFlag == '수정'}">
		<br />
		<br />
		<table class="tbl_type3" style="width: 1150px;">
			<caption>적용이력</caption>
			<!--셀넓이조절가능 -->
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
		</table>

		<!--등록정보 테이블 -->
		<table class="tbl_type1 mag_top1" style="width: 99%">
			<caption>등록정보</caption>
			<tr>
				<th>등록자</th>
				<td width="380"><c:out value="${SysModel.reg_user}" /></td>
				<th>등록일</th>
				<td><c:out value="${SysModel.reg_date}" /></td>
			</tr>
			<tr>
				<th>수정자</th>
				<td><c:out value="${SysModel.update_user}" /></td>
				<th>수정일</th>
				<td><c:out value="${SysModel.update_date}" /></td>
			</tr>
		</table>


	</c:when>
</c:choose>
</div>
