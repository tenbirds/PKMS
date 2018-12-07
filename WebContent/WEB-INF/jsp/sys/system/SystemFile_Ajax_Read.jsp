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

<!-- <link rel="stylesheet" href="/css/zTree/demo.css" type="text/css"> -->
<link rel="stylesheet" href="/css/zTree/zTreeStyle/zTreeStyle.css" type="text/css">

<script type="text/javascript" src="/js/jquery/zTree/jquery.ztree.core.js"></script>
        <style>
                #dropzone
    {
        border:2px dotted #3292A2;
        width:90%;
        height:240px;
        color:#92AAB0;
        text-align:center;
        font-size:24px;

    }

        </style>



<script type="text/javaScript" defer="defer">
/* 
 * zTree 테스트 
 *
// zTree 설정 
var setting = {
    data: {
        simpleData: {
            enable: true
        }
    }
};
//Data
var zNodes =[
    { id:1, pId:0, name:"pNode 1", open:true},
    { id:11, pId:1, name:"pNode 11"},
    { id:111, pId:11, name:"leaf node 111"},
    { id:112, pId:11, name:"leaf node 112"},
    { id:113, pId:11, name:"leaf node 113"},
    { id:114, pId:11, name:"leaf node 114"},
    { id:12, pId:1, name:"pNode 12"},
    { id:121, pId:12, name:"leaf node 121"},
    { id:122, pId:12, name:"leaf node 122"},
    { id:123, pId:12, name:"leaf node 123"},
    { id:124, pId:12, name:"leaf node 124"},
    { id:13, pId:1, name:"pNode 13 - no child", isParent:true},
    { id:2, pId:0, name:"pNode 2"},
    { id:21, pId:2, name:"pNode 21", open:true},
    { id:211, pId:21, name:"leaf node 211"},
    { id:212, pId:21, name:"leaf node 212"},
    { id:213, pId:21, name:"leaf node 213"},
    { id:214, pId:21, name:"leaf node 214"},
    { id:22, pId:2, name:"pNode 22"},
    { id:221, pId:22, name:"leaf node 221"},
    { id:222, pId:22, name:"leaf node 222"},
    { id:223, pId:22, name:"leaf node 223"},
    { id:224, pId:22, name:"leaf node 224"},
    { id:23, pId:2, name:"pNode 23"},
    { id:231, pId:23, name:"leaf node 231"},
    { id:232, pId:23, name:"leaf node 232"},
    { id:233, pId:23, name:"leaf node 233"},
    { id:234, pId:23, name:"leaf node 234"},
    { id:3, pId:0, name:"pNode 3 - no child", isParent:true}
];
// zTree 초기화

$(document).ready(function(){
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    
}); */

//zTree Data
var resultTreeData;
$(document).ready(function(){
	
	

	
	//zTree Data get
	var url="/sys/system/SystemFileData_Ajax_Read.do";    
    $.ajax({
        type:"POST",  
        url:url,  
        async:false,
        success:function(data){
        	console.log(data);
        	resultTreeData = data;
        },   
        error:function(e){  
            console.log(e.responseText);  
        }
    });
    
    // zTree 설정
     var setting = {
         data: {
             simpleData: {
                 enable: true
             }
         },
         callback: {
             //beforeClick: beforeClick  // 마우스 클릭 콜백함수 지정
         }
     };
    
     function beforeClick(treeId, treeNode, clickFlag) {
         location.href=treeNode.web;
     }
     
	// zTree 초기화
	 $.fn.zTree.init($("#treeDemo"), setting, resultTreeData);
	
	
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

          F_FileMultiUpload(files, obj);
     });





});

	// 파일 멀티 업로드
	function F_FileMultiUpload(files, obj) {
	     if(confirm(files.length + "개의 파일을 업로드 하시겠습니까?") ) {
	         var data = new FormData();
	         for (var i = 0; i < files.length; i++) {
	            data.append('file', files[i]);
	         }
	
	         var url = "<서버 파일업로드 URL>";
	         $.ajax({
	            url: url,
	            method: 'post',
	            data: data,
	            dataType: 'json',
	            processData: false,
	            contentType: false,
	            success: function(res) {
	                F_FileMultiUpload_Callback(res.files);
	            }
	         });
	     }
	}
	
	// 파일 멀티 업로드 Callback
	function F_FileMultiUpload_Callback(files) {
	     for(var i=0; i < files.length; i++)
	         console.log(files[i].file_nm + " - " + files[i].file_size);
	}


	function fn_initSystemFile(title) {
		try {
			
			//doAuthTree();
			var treeData = eval(document.getElementById("treeScript").value + ";");
			var rootNode = $("#tree").dynatree("getRoot");
			rootNode.removeChildren(true);
			rootNode.addChild(treeData);
		} catch (e) {
			alert("시스템 오류가 발생 하여 조직 목록 조회에 실패 하였습니다.");
		}
	}
	
	function onActivateFileTree(key) {
		document.getElementById("group_id").value = key;
		document.getElementById("searchKeyword").value = "";
		fn_systemFile_readList('select');
	}
	
	function fn_systemFile_readList(mode) {
		m_mode = mode;
		if (mode == "search") {
			
			var searchKeyword = document.getElementById("searchFileKeyword");
			searchKeyword.value = ltrim(searchKeyword.value);
			searchKeyword.value = rtrim(searchKeyword.value);

			if (searchKeyword.value == "") {
				alert("검색 할 단어를 입력 하세요.");
				searchKeyword.focus();
				return;
			}
			document.getElementById("group_id").value = "";
		}
		doSubmit("SysModel",
				"/sys/system/SystemFile_Ajax_ReadList.do",
				"fn_callback_systemFile_readList");

	}

	function fn_callback_systemFile_readList(data) {
		$("#system_file_list_area").html(data);
	}

	
</script>

<input type="hidden" id="fileTreeScript" name="treeScript" value="${SysModel.treeScript}">


<!--관련정보 띄우기 -->
<div class="sysc_div2_pop" style="width:1125px !important">


	<div id="system_file_title" class="sysc_div2_pop_title">첨부파일</div>


	<!--tree -->
	<div id="fileTree" class="sysc_div4">
		<ul id="treeDemo" class="ztree" ></ul>



	</div>

	<!--리스트 -->

	
		<div id="dropzone" class="sysc_div5" style="width:746px !important">
	
			<!--검색조건 -->
			<div class="sysc_div5_search">
				<div class="sysc_inp">
					파일리스트 출력및 drag & drop
					http://www.dropzonejs.com/
	<!-- 				<input id="searchFileKeyword" name="searchFileKeyword" type="text" class="inp" style="width: 220px" size="5" onkeypress="if(event.keyCode == 13) { fn_systemFile_readList('search'); return;}" /> -->
	<!-- 				<span><a href="javascript:fn_systemFile_readList('search');"><img src="/images/btn_sys_search.gif" alt="검색" width="45" height="23" align="absmiddle" align="absmiddle" style="vertical-align: middle;" /></a></span> -->
				</div>
			</div>
	
			<!--해당리스트 -->
			<div id="system_File_list_area" style="padding:0px 10px 0px 10px; height:195px; clear:both; overflow:auto;">
			
				<table id="scrollTable" class="tbl_type3"   border="1" >
	
						<colgroup>
							<col width="40%" />
							<col width="50%" />
							<col width="*" />
						</colgroup>
						<tr class="pop_tbl_type3">
							<th>구분명</th>
							<th>파일명</th>
							<th>파일사이즈</th>
						</tr>

					<tbody>
						
					</tbody>
				</table>
				
			
			</div>
	
		</div>


</div>




