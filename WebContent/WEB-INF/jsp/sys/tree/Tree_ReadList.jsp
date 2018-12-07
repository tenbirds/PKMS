<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<link rel="stylesheet" href="/css/zTree/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="/js/jquery/zTree/jquery.ztree.core.js"></script>
<script type="text/javaScript"  defer="defer">
	
	$(document).ready(function(){
		searchTree();
	});

	function searchTree(){
		
		$("#btn_save").show();
// 		$("#btn_delete").hide();
// 		$("#btn_save").hide();
		
		$.ajax({
	        type:"POST",  
	        url:"/sys/tree/Tree_Ajax_Read.do",  
	        success:function(data){
	        	console.log(data);
	        	
	        	var setting = {
	       	         data: {
	       	             simpleData: {
	       	                 enable: true
	       	             }
	       	         },
	       	         callback: {
	       	             beforeClick: myBeforeClick  // 마우스 클릭 콜백함수 지정
	       	         }
	       	     };
	       	    
	       		 var treeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
	       		 var nodes = treeObj.getNodes();

	       		 if(nodes.length > 0){
	       			treeObj.selectNode(nodes[0]);
	       			
// 	       			$("#name").attr("disabled",true);
// 	       			$("#open").attr("disabled",true);
// 	       			$("#use_yn").attr("disabled",true);
	       			$("#id").val(nodes[0].id);
	       			$("#name").val(nodes[0].name);
	       			$("#open").val(nodes[0].open_state);
	       			$("#use_yn").val(nodes[0].use_yn);
	       		}
	       		 
	        },   
	        error:function(e){  
	            console.log(e.responseText);  
	        }
	    });
	}
	
	
	function add(){
		
		$("#name").attr("disabled",false);
		$("#open").attr("disabled",false);
		$("#use_yn").attr("disabled",false);
		$("#id").val("");
		$("#pid").val("");
		$("#name").val("");
		$("#open").val("Y");
		$("#use_yn").val("true");
		$("#function").val("I");
		
		$("#btn_delete").hide();
		$("#btn_save").show();
	}
	
	function save(){
		
		if($("#name").val() == ""){
			alert("자료명을 입력해 주세요.");
			return;
		}
		
		var url="";
		
		if("I" == $("#function").val()){
			url="/sys/tree/Tree_Create.do";
		}else{
			url="/sys/tree/Tree_Update.do";
		}
		 
	    $.ajax({
	        type:"POST",  
	        url:url,
	        data:{
	        	id : $("#id").val()-0
	        	,pId : 1
	        	,name : $("#name").val()
	        	,open : $("#open").val()+""
	        	,isParent : true+""
	        	,use_yn : $("#use_yn").val()
	        	,type : "SYSTEM"
	        },
	        async:false,
	        success:function(data){
        		searchTree();
        		if("I" == $("#function").val()){
        			alert("등록되었습니다.");
        		}else{
        			alert("수정되었습니다.");
        		}
	        },   
	        error:function(e){  
	            console.log(e.responseText);  
	        }
	    });
	    
		
	}
	
	function delete_tree(){
		 
		if (confirm("삭제하시겠습니까?")) {
		    $.ajax({
		        type:"POST",  
		        url:"/sys/tree/Tree_Delete.do",
		        data:{
		        	id : $("#id").val()
		        },
		        async:false,
		        success:function(data){
	        		searchTree();
	        		alert("삭제되었습니다.");
		        },   
		        error:function(e){  
		            console.log(e.responseText);  
		        }
		    });
		}
	}
	
	function myBeforeClick(treeId, treeNode, clickFlag) {
		
// 		$("#name").attr("disabled",treeNode.isParent);
// 		$("#open").attr("disabled",treeNode.isParent);
// 		$("#use_yn").attr("disabled",treeNode.isParent);
		
		$("#id").val(treeNode.id);
		$("#pid").val(treeNode.pId);
		
		
		$("#name").val(treeNode.name);
		$("#open").val(treeNode.open_state);
		$("#use_yn").val(treeNode.use_yn);
		$("#function").val("U");
		
// 		if(treeNode.isParent){
// 			$("#btn_delete").hide();
// 			$("#btn_save").hide();
// 		}else{
			$("#btn_delete").show();
			$("#btn_save").show();
// 		}
		
		
	};
	
	
	

</script>
</head>

<body>
	<input type="hidden" id="fileTreeScript" name="treeScript" value="${SysModel.treeScript}">
	
	<!-- Box -->
	<div class="con_Div2 con_area mt20">
	
		
		<div class="con_detail fl_wrap">
			
			<!--버튼위치 -->
			<div class="con_top_btn fl_wrap">
				<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER">
					<span><a href="javascript:add();">신규</a></span>
					<span id="btn_save" style="display: none;"><a href="javascript:save();">저장</a></span>
					<span id="btn_delete" style="display: none;"><a href="javascript:delete_tree();">삭제</a></span>
				</sec:authorize>
			</div>
			
			
			<!--tree -->
			<div id="fileTree" class="sysc_div4 fl_left mt40" style="width:25%; height:660px; float: left; border: 1px solid #ddd; overflow: auto;" >
				<ul id="treeDemo" class="ztree" ></ul>
			</div>

			<!-- input box -->
<!-- 			<div id="" style="width: 68%; height:660px; float: right;"> -->
				
			<!--기본정보 -->
			<table class="tbl_type11 fl_right" style="width:73%;"> 
				<input id="id" name="id" type="hidden" value="">
				<input id="pid" name="pid" type="hidden" value="">
				<input id="function" name="function" type="hidden" value="">
				<colgroup>
					<col width="20%">
					<col width="*">
				</colgroup>
				<tr>
					<th>자료명<span class="necessariness">*</span></th>
					<td>
						<input id="name" name="name" class="new_inp inp_w90" type="text" value="" maxlength="60" >
					</td>
				</tr>
				<tr>
					<th style="width: 100px;">Open여부<span class="necessariness">*</span></th>
					<td>
						<select id="open" name="open">
							<option value="true">Y</option>
							<option value="false">N</option>
						</select>
					</td>
				</tr>
				<tr>
					<th style="width: 100px;">사용여부<span class="necessariness">*</span></th>
					<td>
						<select id="use_yn" name="use_yn">
							<option value="Y">Y</option>
							<option value="N">N</option>
						</select>
					</td>						
				</tr>
				<!-- 
				<tr>
					<th>등록자</th>
					<td class="inp_w3"></td>
				</tr>
				<tr>
					<th>등록일</th>
					<td class="inp_w3"></td>
				</tr>
				 -->
			</table>
			
			</div>
			
		</div>
	
</body>
</html>