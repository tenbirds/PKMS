<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>

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
	

	
	$(document).ready(function($) {
		searchTree();
	});
	
	
	
	
	
	function searchTree(){
		$.ajax({
	        type:"POST",  
	        url:"/sys/system/SystemFileData_Ajax_Read2.do",  
	        data:{},
	        success:function(data){
	        	console.log(data);
	        	var setting = {
	            		view:{selectedMulti: false},
	        			data:{simpleData: {enable: true }},
	        			callback:{	beforeClick: beforeClick }
	        		};
	            
	        	treeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
	        	var nodes = treeObj.getNodes();
	        	if(nodes.length > 0){
	        		
	        		
						if($("#parent_tree_id").val() != ""){
							console.log("1번이다.");
// 							if(!type){
// 		        				treeObj.selectNode(nodes[$("#select_tree_node").val()]);
// 							}
		        			beforeClick('', nodes[$("#select_tree_node").val()], true);
						}else{
							$("#parent_tree_id").val(nodes[0].id);
							$("#select_tree_node").val(0);
// 							if(!type){
// 								treeObj.selectNode(nodes[0]);
// 							}


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
	
	
	
	
	function afterClick(treeId, treeNode, clickFlag) {	

	
	}
	
	
	function submitBtn() {	// 수정 E/ 저장:N
		
    	var pId 			= $("#parent_tree_id").val(); //사용분류(부모pid)
    	var section 		= $("#section option:selected").val(); //분야
    	var required 		= $(":radio[id^='required']:checked").val(); //필수 여부
    	var status 			= $(":radio[id^='status']:checked").val();//상태(사용유무)
    	var name 			= $("#name").val();//첨부항목명
    	var maxAttachSize 	= $("#maxAttachSize option:selected").val();//첨부가능 최대수
    	var id 				= $("#listTypeId").val();//id전달
    	var eng_name 		= $("#eng_name").val();//영문명-파일 첨부할때 ,ID 로 사용
    	var reg_user 		= $("#Session_user_id").val();
    	var update_user 	= $("#Session_user_id").val();
    	
    	var type 			= $("#listType").val();//3depth 생성할때 사용
    	var list_type 		= $("#list_type").val();//2depth 생성 할때 입력받는 값
    	
    	if(section == 4 ){
    		alert("분야 를 선택해 주세요 ");
    		return;
    	}
    	
    	if(name == "" || name == null){
    		alert("첨부항목명을 입력해 주세요 ");
    		return;
    	}
    	

    	if((eng_name == "" || eng_name == null) && ($("#nowMenu_id").val() != 1000 ) && (id == "NEW")  ){
    		alert("영문명을 입력해 주세요 입력하신 이후로 수정할수 없습니다.");
    		return;
    	}
    	
    	if((eng_name != "" || eng_name != null) && ($("#nowMenu_id").val() != 1000 ) && (id == "NEW")  ){
    		if(!engNameCheck(eng_name)){
    			alert("중복된 영문명이 있습니다. 다른 이름으로 입력해 주세요.");
	    		return;
    		}
    	}
    	
    	
    	
    	if((list_type == "" || list_type == null) && pId == 1000 ){
    		alert("분류 TYPE을 입력해 주세요 ");
    		return;
    	}
    	
    	if((list_type != "" && list_type != null) && pId == 1000 ){
    		type = list_type;
    	}

		
    	var msg="";
    	if(id == "NEW" ){
    		msg = "저장하시겠습니까?";
    	}else{
    		msg = "수정사항을 저장하시겠습니까?";
    	}
    	
    	
		if(confirm(msg)){
			
			$.ajax({
		        type:"POST",  
		        url:"/sys/system/pkg_tree_list_add.do",
		        data:{
		        	"pId"			: pId,
		        	"section"		: section,
		        	"required"		: required,
		        	"status"		: status,
		        	"name"			: name,
		        	"maxAttachSize"	: maxAttachSize,
		        	"type"			: type,
		        	"id"			: id,
		        	"eng_name"		: eng_name
		        },
		        success:function(data){
		        	searchTree();
		        },   
		        error:function(e){  
		            console.log(e.responseText);  
		        }
		    });
		}
		
	}

	
	function deleteBtn(deleteId, name) {	// 수정 E/ 저장:N
		
    	var msg = "[ "+ name + " ] 을 삭제 하시겠습니까?"+deleteId;

		if(confirm(msg)){
			
			$.ajax({
		        type:"POST",  
		        url:"/sys/system/pkg_tree_list_delete.do",
		        data:{
		        	"id"	: deleteId//id전달
		        },
		        success:function(data){
		        	searchTree();
		        },   
		        error:function(e){  
		            console.log(e.responseText);  
		        }
		    });
		}
		
		
	}
	
	
	function engNameCheck(engName){ // 영문명 중복체크
	
		        	var resultVal = false;
		if(engName != null && engName !=''){
			$.ajax({
		        type:"POST",  
		        url:"/sys/system/engNameCheck.do",
		        async: false,//동기 결과값 리턴 해야 하기때문에
		        data:{
		        	"eng_name"	: engName
		        },
		        success:function(data){
		        	console.log(data);
		        	if(data){
			        	resultVal = true;	    
			        	console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+resultVal);
// 			        	return resultVal;
		        	}
// 		        	else{
// 		        		return resultVal;
// 		        	}
    	console.log("22222222start222222222222222222222222222222222222");
    	console.log(resultVal);
    	console.log("222222222end22222222222222222222222222222222222");
		        },   
		        error:function(e){  
		            console.log(e.responseText);  
		        }
		    });
			
		}
		return resultVal;
		
	}
	
	

	function fn_new_create(type) {
		$("#pkgStatus").empty();
		
		jQuery('#editSave').show();  
		jQuery('#editEdit').hide();  
		jQuery('#editBtn').hide();  
		
		$('#twoTitle').text(" 리스트의 항목 추가");
		$('#listTypeId').val("NEW");//새로 추가 하기때문에 id 는 null 로 전달
// test를 위해 임시 변수
		var maxSize = 4;


		var html = "<tr>";
		html += "<td style=\"width:12%;\">사용분류</td>";
		html += "<td style=\"width:38%;\">"+$('#nowMenu_name').val()+"</td>";
		html += "<td style=\"width:12%;\">분야</td>";
		html += "<td style=\"width:38%;\"><select name=\"section\" id=\"section\">";
		for(var fieldlist=1;fieldlist<5;fieldlist++){
			if(maxSize == fieldlist){
				html += "<option value="+fieldlist+" selected=\"selected\">"+fieldlistValue(fieldlist)+"</option>";
			}else{
				html += "<option value="+fieldlist+">"+fieldlistValue(fieldlist)+"</option>";
			}
		}
		html += "</select></td>";
		html += "</tr>";
		html += "<tr>";
		html += "<td>필수여부</td>";

			html += "<td><input type=\"radio\" name=\"required\" id=\"required\" value=\"Y\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">Y</span>";
			html += "<input type=\"radio\" name=\"required\" id=\"required\" value=\"N\" checked=\"checked\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">N</span></td>";

		html += "<td>상태</td>";
		
			html += "<td><input type=\"radio\" name=\"status\" id=\"status\" value=\"Y\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">사용</span>";
			html += "<input type=\"radio\" name=\"status\" id=\"status\" value=\"N\" checked=\"checked\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">미사용</span></td>";

		html += "</tr>";
		html += "<tr>";
		html += "<td>첨부항목명</td>";

		html += "<td><input type=\"text\" name=\"name\" id=\"name\" value=\"\" class=\"inp_w80\" /></td>";
		if($('#nowMenu_id').val() != 1000){
			html += "<td>영문명  </td>";
			html += "<td><input type=\"text\" name=\"eng_name\" id=\"eng_name\"  value='' class=\"inp_w80\" placeholder ='입력이후 수정할수 없습니다.' ></td>";
		}else{
			html += "<td>분류 TYPE  </td>";
			html += "<td><input type=\"text\" name=\"list_type\" id=\"list_type\"  value='' placeholder ='' ></td>";
		}	
			
		html += "</tr>";

		var checkParent = $("#parent_tree_id").val();
		
		if(checkParent != 1000){			
		html += "<tr>";
		html += "<td>최대첨부</td>";
		html += "<td><select name=\"maxAttachSize\" id=\"maxAttachSize\">";
		for(var size=1;size<21;size++){
			if(size == 5){ // 기본 5
				html += "<option value="+size+" selected=\"selected\">"+size+"</option>";
			}else{
				html += "<option value="+size+">"+size+"</option>";
			}
		}
		html += "</select></td></tr>";
		}else{
		html += "<tr style=\"display:none;\">";	
		html += "<td><select name=\"maxAttachSize\" id=\"maxAttachSize\">";
		html += "<option value='5' selected=\"selected\"></option>";
		html += "</select></td></tr>";
		}
		
		$("#pkgStatus").append(html);
		
	}
	
	
	
	
	
	
	function fieldlistValue(list) {

		switch (list) {
		  
		  case 1    : return "Core";
// 		               break;
		  case 2   : return "유선";
// 		               break;
		  case 3  : return "Access";
// 		               break;
		  default    : return "선택";
// 		               break;
		}
		
		
	}
	

	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	}
	
	
	function beforeClick(treeId, treeNode, clickFlag) {
		
		
		if ( typeof treeNode == "undefined" ){
			
			var form = document.getElementById('MenuList');
			form.action = "/verify_tem_mg/verifyTempMenuList_ReadList.do";
			form.submit();
			
		}

		
// 		console.log("**********************start***************************");
// 		console.log(treeNode);
// 		console.log(treeId);
// 		console.log(clickFlag);
// 		console.log("********************end*****************************");
		if( treeNode.isParent){
// 			var checkChildren = treeNode.children;
// 			if(checkChildren.length < 1){
// 				$("#parent_tree_id").val(treeNode.getParentNode().id);
// 			}else{
				$("#parent_tree_id").val(treeNode.id);
// 			}
			$("#select_tree_node").val(treeNode.getIndex());
		}else{
			$("#parent_tree_id").val(treeNode.getParentNode().id);
			$("#select_tree_node").val(treeNode.getParentNode().getIndex());
		}
		
		

		$("#pkgStatus").empty();
		$("#fileList").empty();
		
			var html = "";
			var html2 = "";
			
		
// 		if(treeNode.isParent){
		if( ( !(treeNode.pId > 1000) || treeNode.id == 1000 ) ){

	jQuery('#editBtn').show();  


	
	jQuery('#attachDropzone').show();  
	jQuery('#attachRequired').show(); 
	jQuery('#editSave').hide();  
	jQuery('#editEdit').show(); 
	$('#twoTitle').text(treeNode.name + " 리스트 수정 ");
	

			html = "<tr>";
			html += "<td style=\"width:12%;\">사용분류</td>";
			if( treeNode.pId == null){
				html += "<td style=\"width:38%;\">"+treeNode.name+"</td>";				
			}else{
				html += "<td style=\"width:38%;\">"+treeNode.getParentNode().name+"</td>";
			}
// 			treeNode.name
			$('#nowMenu_name').val(treeNode.name);  //현재 메뉴 
			$('#nowMenu_id').val(treeNode.id);
			
			html += "<td style=\"width:12%;\">분야</td>";
			html += "<td style=\"width:38%;\"><select name=\"section\" id=\"section\">";
			for(var fieldlist=1;fieldlist<5;fieldlist++){
				if(treeNode.maxlistsize == fieldlist){
					html += "<option value="+fieldlist+" selected=\"selected\">"+fieldlistValue(fieldlist)+"</option>";
				}else{
					html += "<option value="+fieldlist+">"+fieldlistValue(fieldlist)+"</option>";
				}
			}
			html += "</select></td>";
			html += "</tr>";
			html += "<tr>";
			html += "<td>필수여부</td>";
			if(treeNode.required == "Y"){
				html += "<td><input type=\"radio\" name=\"required\" id=\"required\" value=\"Y\" checked=\"checked\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">Y</span>";
				html += "<input type=\"radio\" name=\"required\" id=\"required\" value=\"N\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">N</span></td>";
			}else{
				html += "<td><input type=\"radio\" name=\"required\" id=\"required\" value=\"Y\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">Y</span>";
				html += "<input type=\"radio\" name=\"required\" id=\"required\" value=\"N\" checked=\"checked\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">N</span></td>";
			}
			html += "<td>상태</td>";
			if(treeNode.status == "Y"){
				html += "<td><input type=\"radio\" name=\"status\" id=\"status\" value=\"Y\" checked=\"checked\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">사용</span> ";
				html += "<input type=\"radio\" name=\"status\" id=\"status\" value=\"N\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">미사용 </span></td>";
			}else{
				html += "<td><input type=\"radio\" name=\"status\" id=\"status\" value=\"Y\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">사용</span>";
				html += "<input type=\"radio\" name=\"status\" id=\"status\" value=\"N\" checked=\"checked\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">미사용</span></td>";
			}
			html += "</tr>";
			html += "<tr>";
			html += "<td>첨부항목명</td>";
			html += "<td><input type=\"text\" name=\"name\" id=\"name\" class=\"inp_w80\" value='"+treeNode.name+"' ></td>";
			html += "</tr>";
			
				
// 			$('#twoTitle').text(treeNode.name + " 리스트 수정 ");
			
			html += "<tr style=\"display:none;\">";	
			html += "<td><select name=\"maxAttachSize\" id=\"maxAttachSize\">";
			html += "<option value='5' selected=\"selected\"></option>";
			html += "</select></td></tr>";
			
			$('#listType').val(treeNode.type);
			$('#listTypeId').val(treeNode.id);
		
		
		
		
		
		
		
			$('#twoTitle2').text(treeNode.name + " 리스트의 항목");
			
			var list;
			var system;
			
			if(treeNode.isParent){
				
				if(treeNode.check_Child_State == 0){
					list = treeNode.children;
					system = treeNode.name;
				}else{
					jQuery('#editEdit').hide(); 
					$("#fileList").append("<tr><td colspan='10' align='center'>등록된 자료가 없습니다.</td></tr>");
					return;
				}
				
			}else{
				list = treeNode.getParentNode().children;
				system = treeNode.getParentNode().name;
			}
			
			
			for(var a = 0; a < list.length; a++){
				var html2te = "<tr><td>"+treeNode.name+"</td>";
				html2te += "<td>";
				html2te += "";//분야 컬럼은 아직 없음.
				html2te += "</td><td>"+treeNode.status+"</td><td>"+list[a].name+"</td><td>"+list[a].required+"</td>";
				if(treeNode.id == 1000){
					html2te += "<td></td><td></td><td></td>";	
				}else{
					html2te += "<td>"+list[a].maxlistsize+"</td><td>"+list[a].reg_user+"</td><td>"+list[a].reg_date+"</td>";
				}
				html2te += "<td>"+list[a].type+"</td>";

				html2te += "<td class=\"btn_wrap\"><a href=\"javascript:deleteBtn('"+list[a].id +"','"+ list[a].name +"')\" class=\"btn btn_red\">삭제</a></td></tr>";				
// 				html2te += "<td><a href=\"javascript:deleteBtn('"+list[a].id +"','"+ list[a].name +"')\"><img src=\"/images/btn_delete.gif\" alt=\"삭제\" /></a></td></tr>";				
				html2 += html2te;
			}
			
			

			
		}else{

// 			$('#twoTitle').text("");
			jQuery('#editBtn').hide();  
		 	jQuery('#attachDropzone').hide();  
		 	jQuery('#attachRequired').show(); 

		 	jQuery('#editSave').hide();  
		 	jQuery('#editEdit').show(); 
			$('#twoTitle2').text(treeNode.name + " 항목 수정");
			
// 		 	(treeNode.pId)
					$('#nowMenu_name').val(treeNode.name);  //현재 메뉴 
					$('#nowMenu_id').val(treeNode.id);
					
					html = "<tr>";
					html += "<td style=\"width:12%;\">사용분류</td>";
					html += "<td style=\"width:38%;\">"+treeNode.getParentNode().name+"</td>";
					html += "<td style=\"width:12%;\">분야</td>";
					html += "<td style=\"width:38%;\"><select name=\"section\" id=\"section\">";
					for(var fieldlist=1;fieldlist<5;fieldlist++){
						if(treeNode.maxlistsize == fieldlist){
							html += "<option value="+fieldlist+" selected=\"selected\">"+fieldlistValue(fieldlist)+"</option>";
						}else{
							html += "<option value="+fieldlist+">"+fieldlistValue(fieldlist)+"</option>";
						}
					}
					html += "</select></td>";
					html += "</tr>";
					html += "<tr>";
					html += "<td>필수여부</td>";
					if(treeNode.required == "Y"){
						html += "<td><input type=\"radio\" name=\"required\" id=\"required\" value=\"Y\" checked=\"checked\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">Y</span>";
						html += "<input type=\"radio\" name=\"required\" id=\"required\" value=\"N\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">N</span></td>";
					}else{
						html += "<td><input type=\"radio\" name=\"required\" id=\"required\" value=\"Y\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">Y</span>";
						html += "<input type=\"radio\" name=\"required\" id=\"required\" value=\"N\" checked=\"checked\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">N</span></td>";
					}
					html += "<td>상태</td>";
					if(treeNode.status == "Y"){
						html += "<td><input type=\"radio\" name=\"status\" id=\"status\" value=\"Y\" checked=\"checked\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">사용 </span>";
						html += "<input type=\"radio\" name=\"status\" id=\"status\" value=\"N\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">미사용 </span></td>";
					}else{
						html += "<td><input type=\"radio\" name=\"status\" id=\"status\" value=\"Y\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">사용</span>";
						html += "<input type=\"radio\" name=\"status\" id=\"status\" value=\"N\" checked=\"checked\" class=\"fl_left mt00\" /><span class=\"fl_left mg03\">미사용</span></td>";
					}
					html += "</tr>";
					html += "<tr>";
					html += "<td>첨부항목명</td>";
					html += "<td><input type=\"text\" name=\"name\" id=\"name\" class=\"inp_w80\" value='"+treeNode.name+"' ></td>";
					html += "<td>영문명</td>";
					html += "<td>"+treeNode.eng_name+"</td>";
					html += "</tr>";
					html += "<tr>";	
					html += "<td>최대첨부</td>";
					html += "<td><select name=\"maxAttachSize\" id=\"maxAttachSize\">";
					for(var size=1;size<21;size++){
						if(treeNode.maxlistsize == size){
							html += "<option value="+size+" selected=\"selected\">"+size+"</option>";
						}else{
							html += "<option value="+size+">"+size+"</option>";
						}
					}
					html += "</select></td></tr>";
					$('#listType').val(treeNode.type);
					$('#listTypeId').val(treeNode.id);
// 					html2 = "";
					
// 					$('#twoTitle2').text(treeNode.name + " 리스트의 항목");
					
// 					var list;
// 					var system;
					
// 					if(treeNode.isParent){
						
// 						if(treeNode.check_Child_State == 0){
// 							list = treeNode.children;
// 							system = treeNode.name;
// 						}else{
// 							$("#fileList").append("<tr><td colspan='5'>등록된 자료가 없습니다.</td></tr>");
// 							return;
// 						}
						
// 					}else{
// 						list = treeNode.getParentNode().children;
// 						system = treeNode.getParentNode().name;
// 					}
					
					
// 					for(var a = 0; a < list.length; a++){
// 						var html2te = "<tr class=\"pop_tbl_type3\"><td>"+treeNode.name+"</td>";
// 						html2te += "<td>core?</td><td>"+treeNode.status+"</td><td>"+list[a].name+"</td><td>"+list[a].required+"</td>";
// 						if(treeNode.id == 1000){
// 							html2te += "<td></td><td>등록자</td><td>등록일</td>";	
// 						}else{
// 							html2te += "<td>"+list[a].maxlistsize+"</td><td>등록자</td><td>등록일</td>";
// 						}

// 						html2te += "<td><a href=\"javascript:deleteBtn('"+list[a].id +"','"+ list[a].name +"')\"><img src=\"/images/btn_delete.gif\" alt=\"삭제\" /></a></td></tr>";				
// 						html2 += html2te;
// 					}
		}

			
			$("#pkgStatus").append(html);
			$("#fileList").append(html2);
	}	
	
	
	
	
	
		
</script>


</head>
<body>
<form id="MenuList">


	<input type="hidden" id="Session_user_id" name="Session_user_id" value="${sessionId}">
<div>



<!-- <div align="left" style="float: left; height:780px; margin-left: 35px; border:1px solid black; overflow-y: auto; overflow-x: hidden;"> -->
<div class="con_Div2 con_area">		
	<!-- 시스템 트리 -->
			
	<!-- 시스템 파일 트리 -->
	<div class="con_detail_long fl_wrap">	
		
		<div class="sysc_div4" style="width:27%; height:660px; float: left; " >
			<h3 class="stitle">시스템 자료</h3>
			<div id="fileTree" class="sysc_div4" style="height:660px;border: 1px solid #ddd;overflow-y: auto;overflow-x: hidden;" >	
		<!-- 	<div id="fileTree" class="sysc_div4"  class="sysc_div4" style="width: 300px; height:660px; float: left; border: 1px solid #feba78; overflow: auto;" > -->
				<ul id="treeDemo" class="ztree" ></ul>
			</div>
		</div>
		
		
		<!-- file Drag & Drop -->
		<div align="left" class="fl_right" style="width:72%;">
	
		
			<!--버튼위치영역 -->
				
				<h3 class="stitle"><span id="twoTitle"> </span></h3>
				
				<div id="attachRequired" class="new_search fl_wrap" style="display:none; width:100%;">
				
					<table  class="new_sear_table1" style="width:98%;border-bottom: none;">
					<tbody id="pkgStatus" style="height:auto;">
					
					</tbody>
					</table>
				</div>
				<div class="con_width100 fl_wrap">
					<div class="con_top_btn mt10">
						<span id="editEdit" class="con_top_btn" style="display:none" >
						<a href="javascript:submitBtn()">수정</a>
						</span>
						
						<span id="editSave" class="con_top_btn"  style="display:none" >
						<a href="javascript:submitBtn()">저장</a>
						</span>
	<!-- 				</div> -->
	<!-- 				<br/>	 -->
	<!-- 				<div class="btn_location"  style=" width: 790px; float: left; " align="left" > -->
						<span id="editBtn" class="con_top_btn" style="display:none" ><a href="javascript:fn_new_create()">신규등록</a></span>
						<input type="hidden" name="nowMenu_name" id="nowMenu_name"  value='' >
						<input type="hidden" name="nowMenu_id" id="nowMenu_id"  value='' >
					</div>
				</div>
			
					
				<div id="attachDropzone" style="display:none;width:100%;" >
					<h3 class="stitle mt10"><span id="twoTitle2"></span></h3>
					<div id="system_File_list_area" style="padding:0px 10px 0px 10px;  clear:both;">
						<input type="hidden" id="parent_tree_id" value=""/>
						<input type="hidden" id="select_tree_node" value=""/>
						<input type="hidden" id="listType" value="" >
						<input type="hidden" id="listTypeId" value="" >
						<table class="tbl_type12">
								<colgroup>
									<col width="10%" />
									<col width="5%" />
									<col width="5%" />
									<col width="22%" />
									<col width="7%" />
									<col width="8%" />
									<col width="10%" />
									<col width="13%" />
									<col width="9%" />
									<col width="11%" />
								</colgroup>
							  <thead>
						      	<tr>
						          <th>사용분류</th>
						          <th class="th_height">분야</th>
						          <th class="th_height">상태</th>
						          <th>첨부항목</th>
						          <th class="th_height">필수<br>여부</th>
						          <th class="th_height">최대<br>첨부</th>
						          <th>등록자</th>
						          <th>등록일</th>
						          <th>TYPE</th>
						          <th>버튼</th>
						        </tr>
						    </thead>
							<tbody id="fileList">
							</tbody>
						</table>
					</div>
				
				</div>
			
			
			</div>
		
		</div>
	</div>	
</div>
<%-- </form:form> --%>
</form>
</body>
</html>
