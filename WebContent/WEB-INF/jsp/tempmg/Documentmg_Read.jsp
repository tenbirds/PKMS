<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty DocumentmgModel.doc_seq ? '등록' : '수정'}" />

<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>

<script type="text/javaScript"  defer="defer">
	$(document).ready(function($) {

			 attachList();
		
	});

/* 수정 화면 */
// function fn_read(id) {
// 	var form = document.getElementById('DocumentmgModel');
// 	form.doc_seq.value = id;
// 	form.action = "<c:url value='/board/Documentmg_ReadList.do'/>";
// 	form.submit();
// }

  

function clearText(field){
if(field.defaultValue == field.value) field.value = '';
else if(field.value == '') field.value = field.defaultValue;
}


function fn_create() {
	if(!fn_Validation())
		return;
	
	$(window).unbind('beforeunload');
	doSubmit4File("docFrom", "/tempmg/documentmg_Create.do", "fn_callback_create");
}
function fn_update() {
	
	var doc_name = $("#doc_name").val();
	
	if(doc_name != null && doc_name.length !=0){
		
	$(window).unbind('beforeunload');
	doSubmit4File("docFrom", "/tempmg/documentmg_Create.do", "fn_callback_update");
		
	}else{
		alert("제목을 입력해주세요.");
		$("#doc_name").focus();
		return false;
	}
}

function fn_callback_create(data) {
	doc_readList();
}
function fn_callback_update(data) {
		var form = document.getElementById('docFrom');
		form.id.value = $("#doc_seq").text();
		form.action = "<c:url value='/board/Documentmg_Read.do'/>";
		form.submit();

}

function doc_readList() {
	var seq = $("#doc_seq").val();
	if(seq!=null && seq!='new' ){
		$(window).unbind('beforeunload'); //파일 자동 삭제 안되게
	}
	if($("#flagtype").val() == "등록"){
		pageOutdelFiles( seq) ;		
	}


	document.getElementById('pageIndex').value = 1;
	var form  = document.getElementById('docFrom');
	form.doc_seq.value ='';
	form.doc_name.value ='';
	form.action = "<c:url value='/board/Documentmg_ReadList.do'/>";
	form.submit();
}




function fn_Validation(){
	var doc_name = $("#doc_name").text();
	
	if(doc_name != null && doc_name.length !=0){
		alert("제목을 입력해주세요.");
		$("#doc_name").focus();
		return false;
	}
	
	return true;
}








































function drag(obj) {
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
        if(files.length < 1){
             return;
        }

        F_FileMultiUpload(files, obj);
        e.preventDefault();
   });
	}





//파일 멀티 업로드
function F_FileMultiUpload(files, obj) {
var fileCount = files.length;//업로드 하는 파일개수
if(Number(fileCount) > 1){ // 최대 업로드 개수 > 업로드 파일 개수
	alert("파일은 1개만 올릴수 없습니다.");
	return;
}


if(confirm( "파일을 업로드 하시겠습니까?") ) {
//파일 유효성 체크 start

	var boo = true; 
		for (var i = 0; i < fileCount; i++) {
			if(!fn_upload(files[i], "tree")){
				boo = false;         		
			}
		}

	    if(!boo){
			if ($.browser.msie) { // ie 일때 input[type=file] init.
//				$("#btn_upload").replaceWith( $("#btn_upload").clone(true) ); 
			} else { // other browser 일때 input[type=file] init.
//				$("#btn_upload").val(""); 
			}
	     	alert("파일형식이 금지된 파일이 포함되어 있습니다.");
	     	return;
	    }
//파일 유효성 체크 end
   	    
    var data = new FormData();
    for (var i = 0; i < files.length; i++) {
       data.append('files', files[i]);
//       console.log( files[i]);
    }
    
    data.append("prefix", obj.attr("name") );
    data.append('other_seq', $("#doc_seq").val());//업로드시 sysmodel을 이용하기때문에 변수명 변경



    
	$.ajax({
   	 url:"/tempmg/doc_file_add.do",
       data: data,
       type:'POST',
		data: data,
		async:false,
		cache:false,
		contentType:false,
		processData:false,
       success: function(res) {
       	$("#doc_seq").val(res);
       	attachList();

       },   
        error:function(e){  
            console.log(e.responseText);  
        }
    });
}
}


function delNewFile(doc_seq, file_path, file_name) {
$.ajax({
       type:"POST",  
       url:"/tempmg/doc_file_del.do",  
	   data:{
		   "doc_seq" : doc_seq
		   ,"file_path": file_path
		   ,"file_name" : file_name
		   ,"prefix" : "One"
		   },
       success:function(data){
	        	$("#").val(doc_seq);
	        	 attachList();
       },   
       error:function(e){  
           console.log(e.responseText);  
       }
   });

}




function pageOutdelFiles( doc_seq) { // 저장하지 않고 나갈경우 업로드 된 파일 삭제
$.ajax({
       type:"POST",  
       url:"/tempmg/doc_file_del.do",  
	   data:{   "doc_seq" : doc_seq  ,"prefix" : "All" },
       success:function(data){
       },   
       error:function(e){  
           console.log(e.responseText);  
       }
   });

}





function attachList(){ //첨부 리스트
	var seq = $("#doc_seq").val();
    
    if(seq =="new"){
    	seq = '';
    }
    
$.ajax({
   type:"POST",  
   url:"/board/Documentmg_Ajax_Read.do", 
   data:{"doc_seq" : seq},
   success:function(data){
        var html='';	
        
		html +=	'<th>파일 첨부 <span  class=\'necessariness\'></span></th>';
		html +=	'<td colspan="3">';		
		
		if(data.length != 0 && data.file_name != null ){	
			html +='<a href=\"#\" onclick=\"javascript:downloadFile(\''+data.file_name+'\',\''+data.file_org_name+'\',\''+data.file_path+'\'); return false;\">'+data.file_org_name+'</a>';
			html +='<input type=\"button\" onClick=\"javascript:delNewFile(\''+ $("#doc_seq").val() +'\',\''+ data.file_path+'\',\''+data.file_name+'\');\" class=\"btn_del\" title=\"삭제\">';
		}else{		
			html += '<input name=\"docAttach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
			html += '<input type=\"file\" name=\"docAttach\" size=\"50\" onchange=\"javascript:docfile_upload(this)\">';
		}
		
		html +=	'</td>';

		$('#docAttachTmp ').empty();
		$('#docAttachTmp').append(html);
		
		drag($("#dropzone0"));//드래그앤드롭 활성화
       
	
   },   
   error:function(e){  
       console.log(e.responseText);  
   }
});


    

}



//bt_btn  // 버튼 클릭시 예외
//fn_save_101()
//페이지 나갈때 저장한하면 업로드 파일 전부 del
// $(window).on("beforeunload", function() {
// 	var doc_seq = $("#doc_seq").val();
// 	var doc_name = $("#doc_name").val();
// 	if(doc_seq != null && doc_seq.length != 0  ){
// 	}else{
// 	 pageOutdelFiles( $("#doc_seq").val()) ;		
// 	}
// 		return;
// 	});


$(window).on("beforeunload", function() { //submit 를 제외한 모든경우 업로드 한 파일 지우기
	var doc_seq = $("#doc_seq").val();
	alert(doc_seq);
	if(doc_seq != null && doc_seq.length != 0){
		if($("#flagtype").val()  == "등록"  ){
			pageOutdelFiles( doc_seq) ;			
		}
	}
		
	
	return;
});

// $('DocumentmgModel').submit(function(){ //예외 조건
// $(window).unbind('beforeunload');
// });




function docfile_upload(file){
// 			alert($("#doc_seq").val());
		var value = file.value;
		var Extension = value.substring(value.lastIndexOf(".") + 1);
		var chk = Extension.toLowerCase(Extension);
		
		var filechk = ["xlsx","xlsm","xlsb","xls","mht","mhtml","xltx","xltm","xlt",
		         	  "csv","prn","dif","slk","xlam","xla","xps","docx","docm","doc",
		         	  "dotx","dotm","dot","rtf","wps","ppt","pptx","pptm","png",
		         	  "potx","potm","pot","thmx","ppsx","ppsm","pps","ppam","ppa","tif",
		         	  "wmf","emf","jpg","gif","bmp","pdf","txt","hwp","zip",
		         	  "xlss","xlsxx","docc","docxx","pptt","pptxx"];
		var j = 0;
		for(var i = 0; i < filechk.length; i++){
			if(chk == filechk[i]){
				j++;
			}
		}
		if(j==0) {
			alert(chk+"의 형식은 업로드 금지 파일입니다.");
			return
		}
		
		file = file.files[0]; //파일로 변환입력
		
	    var data = new FormData();
	    data.append('files', file);
	    data.append("prefix", "docAttach");
	    data.append('other_seq', $("#doc_seq").val());//업로드시 sysmodel을 이용하기때문에 변수명 변경
	    
	  
	    
	$.ajax({
		url:"/tempmg/doc_file_add.do",
		data: data,
		type:'POST',
		data: data,
		async:false,
		cache:false,
		contentType:false,
		processData:false,
		success: function(res) {
			$("#doc_seq").val(res);
			attachList();
		},   
		error:function(e){  
			console.log(e.responseText);  
		}
	});
		
}






</script>
</head>
<body>

	<form:form commandName="DocumentmgModel" id="docFrom" name="docFrom" method="post"  enctype="multipart/form-data" onsubmit="return false;">
		<input id="pageIndex" name="pageIndex" type="hidden" value="1">
		<input id="doc_seq" name="doc_seq" type="hidden" value="${DocumentmgRead.doc_seq }">
		<input id="flagtype" name="flagtype" type="hidden" value="${registerFlag}">
		
		<sec:authentication property="principal.username" var="sessionId"/>
		
		<div class="con_Div32">
			<div class="con_area">
				<div class="con_detail">
					<div class="con_top_btn fl_wrap">
						<c:choose>
							<c:when test="${registerFlag == '등록' && DocumentmgRead.doc_seq == 'new' }">
								<span><a href="javascript:fn_create()">저장</a></span>
							</c:when>
							<c:otherwise>
<!-- 								<span><a href="javascript:fn_delete()">삭제</a></span> -->
								<span><a href="javascript:fn_update()">수정</a></span>
							</c:otherwise>
						</c:choose>
						<span><a href="javascript:doc_readList()">목록</a></span>
					</div>
					<!--기본정보 -->
					<c:choose>
						<c:when test="${registerFlag == '등록' && DocumentmgRead.doc_seq == 'new' }">
							<h3 class="stitle">문서템플릿 - 등록</h3>
						</c:when>
						<c:otherwise>
							<h3 class="stitle">문서템플릿 - 상세</h3>
						</c:otherwise>		
					</c:choose>
					<table class="tbl_type11"> 
						<colgroup>
							<col width="10%">
							<col width="40%">
							<col width="10%">
							<col width="40%">
						</colgroup>
						<tbody>
						<tr>
							<th>문서 구분 <span  class='necessariness'>*</span></th>
							<td colspan="3">
							<input id="doc_name" name="doc_name" class="new_inp inp_w90" type="text" value="${DocumentmgRead.doc_name }" onFocus="clearText(this)" onBlur="clearText(this)" maxlength="60">
							</td>
						</tr>
						<tr>
							<th>버전</th>
							<td colspan="3">
								<input id="doc_version" name="doc_version" type="text" value="${DocumentmgRead.doc_version }" maxlength="60">
								</p>
							</td>
						</tr>
						<tr id="docAttachTmp" >
							<th>파일 첨부 <span  class='necessariness'></span></th>
							<td colspan="3">
							<textarea id="FILE_ORG_NAME" name="content" maxlength="15000" class="textarea_w95 mr05" rows="10"></textarea>
							</td>
						</tr>
						

					</tbody>	
						
						
					</table>
				</div>
			</div>
		</div>
	</form:form>
	
</body>
</html>