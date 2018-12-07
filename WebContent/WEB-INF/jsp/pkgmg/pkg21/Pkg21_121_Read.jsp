<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/ui.core.js"></script>
<script type='text/javascript' src='/js/pkgmg/pkgmg.pkg21.js'></script>

<script type="text/javaScript" defer="defer">
	$(document).ready(
		function($) {
			// Calendar Init
			doCalendar("col1");
			doCalendar("col2");
			
			$('textarea[maxlength]').keydown(function(){
		        var max = parseInt($(this).attr('maxlength'));
		        var str = $(this).val();
				if(str.length > max) {
				    $(this).val(str.substr(0, max));
					alert("최대 [" + max + " 자]까지 입력 가능합니다.");
				}
			});
			
			if($("#status").val() > 121){
				doCalendar("col4");
				doCalendar("col5");
				
				doCalendar("col9");
				doCalendar("col10");
			}

			
			
			attachList();//첨부
		}
	);
	
	
	
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
	var lastfilenum = $("#"+ obj.attr("name")+"_count").val();	//마지막 count
	var maxsize = $("#"+ obj.attr("name")+"_count").attr("name");	//maxsize;
	
	if((Number(fileCount)+Number(lastfilenum)) > maxsize){ // 최대 업로드 개수 > 업로드 파일 개수
		alert(maxsize+"개 이상의 파일을 올릴수 없습니다.");
		return;
	}
	
	
	if(confirm(fileCount + "개의 파일을 업로드 하시겠습니까?") ) {
//파일 유효성 체크 start

   	var boo = true; 
  		for (var i = 0; i < fileCount; i++) {
  			if(!fn_upload(files[i], "tree")){
  				boo = false;         		
  			}
  			

  			
			if(obj.attr("name") == "cvtResult"){
	  			var chvalue = files[i].name;
				var Extension = chvalue.substring(chvalue.lastIndexOf(".") + 1);
				var chk = Extension.toLowerCase(Extension);
				
				var filechk =["xlsx"];
				
				var j = 0;
				for(var i = 0; i < filechk.length; i++){
					if(chk == filechk[i]){
						j++;
					}
				}
				if(j==0) {
					boo = false;
				}
			}
  			
  			
  		}

  	    if(!boo){
  			if ($.browser.msie) { // ie 일때 input[type=file] init.
//   				$("#btn_upload").replaceWith( $("#btn_upload").clone(true) ); 
  			} else { // other browser 일때 input[type=file] init.
//   				$("#btn_upload").val(""); 
  			}
  	     	alert("파일형식이 금지된 파일이 포함되어 있습니다.");
  	     	return;
  	    }
//파일 유효성 체크 end
       	    
        var data = new FormData();
        for (var i = 0; i < files.length; i++) {
           data.append('files', files[i]);
        }
        
        var paidcall= obj.attr("name");
        data.append("prefix", obj.attr("name") );
        data.append('master_file_id', $("#master_file_id").val());
        
        //20181119 eryoon 파일 이름 변경
        var sysUserNm = "${Pkg21Model.system_user_name}";
        var pkgTitle = "${Pkg21Model.title}";
        data.append("sysUserNm", sysUserNm);
        data.append("pkgTitle", pkgTitle);
        
       if( paidcall != "cvtPComAttach" && paidcall != "cvtBillComAttach" && paidcall != "cvtVolComAttach" && paidcall != "cvtNotiComAttach"){
        data.append('parent_tree_id', $("#"+ obj.attr("name")).val());
       }else{
    	data.append('parent_tree_id', '');
       }
        
 		 $.ajax({
       	 url:"/common/attachfile/new_file_add.do",
           data: data,
           type:'POST',
			data: data,
			async:false,
			cache:false,
			contentType:false,
			processData:false,
           success: function(res) {
        	if(res == "error"){
           		alert("파일을 확인해 주십시요.");
           		return;
           	}
           	$("#master_file_id").val(res);
           	attachList();

           },   
	        error:function(e){  
	            console.log(e.responseText);  
	        }
        });
    }
}


function delNewFile(attach_file_id, master_file_id, file_path, file_name) {
// 	console.log(file_name);
	$.ajax({
	       type:"POST",  
	       url:"/common/attachfile/new_file_del.do",  
		   data:{
			   "master_file_id" : master_file_id
			   ,"attach_file_id" : attach_file_id
			   ,"file_path": file_path
			   ,"file_name" : file_name
			   },
	       success:function(data){
		        	$("#master_file_id").val(master_file_id);
		        	 attachList();
	       },   
	       error:function(e){  
	           console.log(e.responseText);  
	       }
	   });

}




function pageOutdelFiles( master_file_id) {
	$.ajax({
	       type:"POST",  
	       url:"/common/attachfile/new_file_del.do",  
		   data:{   "master_file_id" : master_file_id   },
	       success:function(data){
	    	   
	       },   
	       error:function(e){  
	           console.log(e.responseText);  
	       }
	   });

}



function attachfiles(menuid,list,type){ // 파일 내리기
	var html='';
	var html1='';
	var html2='';
	var count=0;
	
	if(type == "listfile"){
		for(var i=0; i < list.length; i++){
			if( menuid == list[i].pId  && list[i].pId != null){	
				++count;
				html1 +='<div id=\"'+list[i].attach_file_id+'\">';
				html1 +='<a href=\"#\" onclick=\"javascript:downloadFile(\''+list[i].file_name+'\',\''+list[i].name+'\',\''+list[i].file_path+'\'); return false;\">'+list[i].name+'</a>';
				html1 +='<input type=\"button\" onClick=\"javascript:delNewFile(\'' + list[i].attach_file_id +'\',\''+ $("#master_file_id").val() +'\',\''+ list[i].file_path+'\',\''+list[i].file_name+'\');\" class=\"btn_del\" title=\"삭제\">';
				html1 +='</div>';
			}
		}
		html = html1;
	}else{
		for(var i=0; i < list.length; i++){
			if(list[i].attach_file_id  != null && "cvtPComAttach" == list[i].attach_file_id.substring(0,13)  && type == "cvtPCom"){//				ttm 첨부
					++count;
					html2 +='<div id=\"'+list[i].attach_file_id+'\">';
					html2 +='<a href=\"#\" onclick=\"javascript:downloadFile(\''+list[i].file_name+'\',\''+list[i].name+'\',\''+list[i].file_path+'\'); return false;\">'+list[i].name+'</a>';
					html2 +='<input type=\"button\" onClick=\"javascript:delNewFile(\'' + list[i].attach_file_id +'\',\''+ $("#master_file_id").val() +'\',\''+ list[i].file_path+'\',\''+list[i].file_name+'\');\" class=\"btn_del\" title=\"삭제\">';
					html2 +='</div>';
			}
			
			if(list[i].attach_file_id  != null && "cvtBillComAttach" == list[i].attach_file_id.substring(0,16) && type == "cvtBillCom" ){//				ttm 첨부
				++count;
				html2 +='<div id=\"'+list[i].attach_file_id+'\">';
				html2 +='<a href=\"#\" onclick=\"javascript:downloadFile(\''+list[i].file_name+'\',\''+list[i].name+'\',\''+list[i].file_path+'\'); return false;\">'+list[i].name+'</a>';
				html2 +='<input type=\"button\" onClick=\"javascript:delNewFile(\'' + list[i].attach_file_id +'\',\''+ $("#master_file_id").val() +'\',\''+ list[i].file_path+'\',\''+list[i].file_name+'\');\" class=\"btn_del\" title=\"삭제\">';
				html2 +='</div>';
		}
			
			if(list[i].attach_file_id  != null && "cvtVolComAttach" == list[i].attach_file_id.substring(0,15) && type == "cvtVolCom" ){//				ttm 첨부
				++count;
				html2 +='<div id=\"'+list[i].attach_file_id+'\">';
				html2 +='<a href=\"#\" onclick=\"javascript:downloadFile(\''+list[i].file_name+'\',\''+list[i].name+'\',\''+list[i].file_path+'\'); return false;\">'+list[i].name+'</a>';
				html2 +='<input type=\"button\" onClick=\"javascript:delNewFile(\'' + list[i].attach_file_id +'\',\''+ $("#master_file_id").val() +'\',\''+ list[i].file_path+'\',\''+list[i].file_name+'\');\" class=\"btn_del\" title=\"삭제\">';
				html2 +='</div>';
		}
			
			
			if(list[i].attach_file_id  != null && "cvtNotiComAttach" == list[i].attach_file_id.substring(0,16) && type == "cvtNotiCom"){//				ttm 첨부
				++count;
				html2 +='<div id=\"'+list[i].attach_file_id+'\">';
				html2 +='<a href=\"#\" onclick=\"javascript:downloadFile(\''+list[i].file_name+'\',\''+list[i].name+'\',\''+list[i].file_path+'\'); return false;\">'+list[i].name+'</a>';
				html2 +='<input type=\"button\" onClick=\"javascript:delNewFile(\'' + list[i].attach_file_id +'\',\''+ $("#master_file_id").val() +'\',\''+ list[i].file_path+'\',\''+list[i].file_name+'\');\" class=\"btn_del\" title=\"삭제\">';
				html2 +='</div>';
		}
		}
		html = html2
	}
		
	return [html, count];
}


function attachList(){ //첨부 리스트
	$.ajax({
       type:"POST",  
       url:"/sys/system/SystemFileData_Ajax_Read2.do",  
	   data:{"master_file_id" : $("#master_file_id").val(), "type" : "CVT첨부","use_yn" : "Y"},
       success:function(data){
	        var html='';	
	        var html2='';
	        
	        var htmlcom1='';
	        var htmlcom2='';
	        var htmlcom3='';
	        
	        var flag1 = true;
	        var flag2 = true;
	        var flag3 = true;
	        var flag4 = true;
	        
	        for(var i=0; i < data.length; i++){
//// 	        	console.log(data);
				if(data[i].pId != 1000 && data[i].attach_file_id  == null){
					html += '<tr><th>'+data[i].name+'';
					if(data[i].required == "Y"){
						html += '<span class=\"txt_red\">*</span>';
					}
					html += '</th>';
					html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'\" name=\"id\" value=\"'+data[i].id+'\">';
					html +=	'<td>';
					var prantid = data[i].id ;
					var html3 =	attachfiles(data[i].id,data,"listfile");
					html +=	''+html3[0] ;
					if(html3[1] < data[i].maxlistsize ){
						html +=	'<input type=\"text\" name=\"'+data[i].eng_name+'\" id =\"dropzone'+(i+4)+'\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
						html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'_count\"'+' name='+data[i].maxlistsize+' value=\"'+html3[1]+'\" >';
// 						html += '<span class=\"btn_line_blue2 ml10 mt02\">';
						html += '<input type=\"file\" size=\"50\"  onchange=\"javascript:docfile_upload(this , \''+data[i].eng_name+'\');\"  >';
// 						html += '</span>';
					}
					html +=	'</td></tr>';	
					
		
				}else{
					if(data[i].attach_file_id  != null){
// 						if("cvtPComAttach1" == data[i].attach_file_id ){
						if("cvtPComAttach" == data[i].attach_file_id.substring(0,13) &&  flag1 ){
							var htmlttm =	attachfiles(data[i].id,data,"cvtPCom"); //	cvtcomment
							
							html2 +=	''+htmlttm[0] ;
							if(htmlttm[1] < 5 ){
								html2 += '<input name=\"cvtPComAttach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
								html2 += '<input type=\"hidden\" id=\"cvtPComAttach_count\"'+' name="5" value=\"'+htmlttm[1]+'\" >';
// 								html2 += '<span class=\"btn_line_blue2 ml10 mt02\">';
								html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'cvtPComAttach\' );\">';
// 								html2 += '</span>';
							}
							flag1 = false;
						}
						
						
						if("cvtBillComAttach" == data[i].attach_file_id.substring(0,16) && flag2 ){
							var htmlttm =	attachfiles(data[i].id,data,"cvtBillCom"); //	cvtcomment
							
							htmlcom1 +=	''+htmlttm[0] ;
							if(htmlttm[1] < 5 ){
								htmlcom1 += '<input name=\"cvtBillComAttach\" id=\"dropzone1\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" ">';
								htmlcom1 += '<input type=\"hidden\" id=\"cvtBillComAttach_count\"'+' name="5" value=\"'+htmlttm[1]+'\" >';
// 								htmlcom1 += '<span class=\"btn_line_blue2 ml10 mt02\">';
								htmlcom1 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'cvtBillComAttach\' );\">';
// 								htmlcom1 += '</span>';
							}
							flag2 = false;
						}						
						
						
						if("cvtVolComAttach" == data[i].attach_file_id.substring(0,15) && flag3 ){
							var htmlttm =	attachfiles(data[i].id,data,"cvtVolCom"); //	cvtcomment
							
							htmlcom2 +=	''+htmlttm[0] ;
							if(htmlttm[1] < 5 ){
								htmlcom2 += '<input name=\"cvtVolComAttach\" id=\"dropzone2\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" ">';
								htmlcom2 += '<input type=\"hidden\" id=\"cvtVolComAttach_count\"'+' name="5" value=\"'+htmlttm[1]+'\" >';
// 								htmlcom2 += '<span class=\"btn_line_blue2 ml10 mt02\">';
								htmlcom2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'cvtVolComAttach\' );\">';
// 								htmlcom2 += '</span>';
							}
							flag3 = false;
						}
						
						
						if("cvtNotiComAttach" == data[i].attach_file_id.substring(0,16)  && flag4 ){
							var htmlttm =	attachfiles(data[i].id,data,"cvtNotiCom"); //	cvtcomment
							
							htmlcom3 +=	''+htmlttm[0] ;
							if(htmlttm[1] < 5 ){
								htmlcom3 += '<input name=\"cvtNotiComAttach\" id=\"dropzone3\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\ placeholder=\"여기에 파일을 드래그해주세요\" ">';
								htmlcom3 += '<input type=\"hidden\" id=\"cvtNotiComAttach_count\"'+' name="5" value=\"'+htmlttm[1]+'\" >';
// 								htmlcom3 += '<span class=\"btn_line_blue2 ml10 mt02\">';
								htmlcom3 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'cvtNotiComAttach\' );\">';
// 								htmlcom3 += '</span>';
							}
							flag4 = false;
						}
						
						
						
					}
				}
			}
	 		
	 		if(html2.length == 0){
				html2 += '<input name=\"cvtPComAttach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
// 				html2 += '<span class=\"btn_line_blue2 ml10 mt02\">';
				html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'cvtPComAttach\' );\">';
// 				html2 += '</span>';
	 		}
			if(htmlcom1.length == 0){	
				htmlcom1 += '<input name=\"cvtBillComAttach\" id=\"dropzone1\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
// 				htmlcom1 += '<span class=\"btn_line_blue2 ml10 mt02\">';
				htmlcom1 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'cvtBillComAttach\' );\">';
// 				htmlcom1 += '</span>';
	 		}
			if(htmlcom2.length == 0){		
				htmlcom2 += '<input name=\"cvtVolComAttach\" id=\"dropzone2\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
// 				htmlcom2 += '<span class=\"btn_line_blue2 ml10 mt02\">';
				htmlcom2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'cvtVolComAttach\' );\">';
// 				htmlcom2 += '</span>';
	 		}
			if(htmlcom3.length == 0){		
				htmlcom3 += '<input name=\"cvtNotiComAttach\" id=\"dropzone3\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
// 				htmlcom3 += '<span class=\"btn_line_blue2 ml10 mt02\">';
				htmlcom3 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'cvtNotiComAttach\' );\">';
// 				htmlcom3 += '</span>';

			}
	 			 		
	 		$('#cvtPComdrop').empty();
	 		$('#cvtPComdrop').append(html2);

	 		$('#cvtBillComdrop').empty();
	 		$('#cvtBillComdrop').append(htmlcom1);
	 		
	 		$('#cvtVolComdrop').empty();
	 		$('#cvtVolComdrop').append(htmlcom2);
	 		
	 		$('#cvtNotiComdrop').empty();
	 		$('#cvtNotiComdrop').append(htmlcom3);
	 		
// 			$('#attachlist > tbody:').empty();
// 			$('#attachlist > tbody:last').append(html);
	 		
			$('#attachlist').empty();
			$('#attachlist').append(html);
			

			var dopzoncount=(data.length+4); // 리스트 제외하고 comment에 있는 첨부가 4개 이기때문에 +4
			for(k=0;k<dopzoncount;k++){
				obj = $("#dropzone"+k);
					drag(obj);
			}  
       },   
       error:function(e){  
           console.log(e.responseText);  
       }
   });
}



// bt_btn  // 버튼 클릭시 예외
// fn_save_101()
// 페이지 나갈때 저장한하면 업로드 파일 전부 del
$(window).on("beforeunload", function() {
	var pkg_seq = $("#pkg_seq").val();
	if(pkg_seq != null && pkg_seq.length != 0){
		
	}else{
	 pageOutdelFiles( $("#master_file_id").val()) ;		
	}
	
	 return;
});

// $('form').submit(function(){
//   $(window).unbind('beforeunload');
// });















function docfile_upload(file , prefix){
// 			alert(prefix);
		var value = file.value;
		var Extension = value.substring(value.lastIndexOf(".") + 1);
		var chk = Extension.toLowerCase(Extension);
		
		var filechk = ["xlsx","xlsm","xlsb","xls","mht","mhtml","xltx","xltm","xlt",
		         	  "csv","prn","dif","slk","xlam","xla","xps","docx","docm","doc",
		         	  "dotx","dotm","dot","rtf","wps","ppt","pptx","pptm","png",
		         	  "potx","potm","pot","thmx","ppsx","ppsm","pps","ppam","ppa","tif",
		         	  "wmf","emf","jpg","gif","bmp","pdf","txt","hwp","zip",
		         	  "xlss","xlsxx","docc","docxx","pptt","pptxx"];
		
		if(prefix == "cvtResult"){
			filechk =["xlsx"];
		}
		
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
//         for (var i = 0; i < files.length; i++) {
//            data.append('files', files[i]);
//         }
         data.append('files',file);
        var paidcall= prefix;
        data.append("prefix", paidcall );
        data.append('master_file_id', $("#master_file_id").val());
        
        //20181119 eryoon 파일 이름 변경
        var sysUserNm = "${Pkg21Model.system_user_name}";
        var pkgTitle = "${Pkg21Model.title}";
        data.append("sysUserNm", sysUserNm);
        data.append("pkgTitle", pkgTitle);
        
        if( paidcall != "cvtPComAttach" && paidcall != "cvtBillComAttach" && paidcall != "cvtVolComAttach" && paidcall != "cvtNotiComAttach"){
        data.append('parent_tree_id', $("#"+paidcall).val());
       }else{
    	data.append('parent_tree_id', '');
       }
        
 		 $.ajax({
       	 url:"/common/attachfile/new_file_add.do",
           data: data,
           type:'POST',
			data: data,
			async:false,
			cache:false,
			contentType:false,
			processData:false,
           success: function(res) {
           	if(res == "error"){
           		alert("파일을 확인해 주십시요.");
           		return;
           	}
        	$("#master_file_id").val(res);
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
<form:form commandName="Pkg21Model" method="post" onsubmit="return false;">
	<form:hidden path="pkg_seq" />
	<form:hidden path="select_status" />
	<form:hidden path="status" />
	<form:hidden path="status_dev" />
	<form:hidden path="read_gubun" />
	<form:hidden path="dev_yn" />
	<form:hidden path="system_seq" />
	<form:hidden path="content" />
	<form:hidden path="content_pn" />
	<form:hidden path="content_cr" />
	<form:hidden path="content_self" />
	<form:hidden path="content_invest" />
	<form:hidden path="save_status" />
	<form:hidden path="cha_yn" />
	<form:hidden path="vol_yn" />
	<form:hidden path="sec_yn" />
	<form:hidden path="ser_downtime" />
	<form:hidden path="impact_systems" />
	
	<form:hidden path="ord" />
	<form:hidden path="master_file_id" />
	<form:hidden path="title" />
<div class="tit">
${Pkg21Model.title}
</div>
<div class="new_con_Div32">
	<div class="sub_contents01 fl_wrap">
		<sec:authorize ifNotGranted="ROLE_ADMIN" >
			<c:set var="s_granted" value="N" />
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_ADMIN" >
			<c:set var="s_granted" value="Y" />
		</sec:authorize>
		<div class="sub_flow_wrap fl_left">
			<ul class="sub_flow_line01 fl_wrap">
				<li class="sub_flow"> 
					<c:choose>
						<c:when test="${registerFlag == '등록'}">
							<p class="on">
								SVT 계획수립
							</p>
						</c:when>
						<c:when test="${Pkg21Model.select_status == 101}">
							<p class="on_blue">
								SVT 계획수립
							</p>
						</c:when>
						<c:otherwise>
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_read('read', '${Pkg21Model.pkg_seq}');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing">SVT 계획수립</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status == 101 && Pkg21Model.select_status == 102}">
							<p class="on">
								SVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status == 101}">
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('102');">
								SVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 101 && Pkg21Model.select_status == 102}">
							<p class="on_blue">
								SVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 101}">
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('102');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing text_1">SVT 결과</span>
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">SVT 결과</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.dev_yn == 'N'}">
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">DVT 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status < 102}">
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">DVT 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status == 102 && Pkg21Model.select_status == 111}">
							<p class="on">
								DVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status == 102}">
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
								DVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.dev_yn == 'D'}">
							<c:choose>
								<c:when test="${Pkg21Model.status_dev < 114 && Pkg21Model.select_status == 111}">
									<p class="on">
										DVT 결과
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status_dev < 114}">
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
										DVT 결과
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status_dev == 114 && Pkg21Model.select_status == 111}">
									<p class="on_blue">
										DVT 결과
									</p>	
								</c:when>
								<c:otherwise>
									<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
										<img src="/images/ic_flow_blue.png">
										<span class="over_text_ing text_1">DVT 결과</span>
									</p>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${Pkg21Model.status < 114 && Pkg21Model.select_status == 111}">
									<p class="on">
										DVT 결과
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status < 114}">
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
										DVT 결과
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status > 113 && Pkg21Model.select_status == 111}">
									<p class="on_blue" >
										DVT 결과
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status > 113}">
									<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
										<img src="/images/ic_flow_blue.png">
										<span class="over_text_ing text_1">DVT 결과</span>
									</p>
								</c:when>
								<c:otherwise>								
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
										DVT 결과
									</p>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status < 102}">
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">CVT 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 123 && Pkg21Model.select_status == 121}">
							<p class="on_blue">
								CVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 123}">
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('121');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing text_1">CVT 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.dev_yn == 'Y'}">
							<c:choose>
								<c:when test="${Pkg21Model.status < 114}">
									<p>
										<img src="/images/ic_flow.png">
										<span class="over_text">CVT 결과</span>
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status > 113 && Pkg21Model.select_status == 121}">
									<p class="on">
										CVT 결과
									</p>
								</c:when>
								<c:otherwise>
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('121');">
										CVT 결과
									</p>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${Pkg21Model.status > 101 && Pkg21Model.select_status == 121}">
							<p class="on">
								CVT 결과
							</p>
						</c:when>
						<c:otherwise>
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('121');">
								CVT 결과
							</p>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
		</div>	
		<div class="sub_flow_wrap fl_left">	
			<ul class="sub_flow_line02 fl_wrap">
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.cha_yn == 'N'}">
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">과금검증</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.cha_yn == 'S' && Pkg21Model.select_status == 161}">
							<p class="on_blue">
								과금검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.cha_yn == 'S'}">
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('161');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing text_1">과금검증</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.dev_yn == 'D'}">
							<c:choose>
								<c:when test="${Pkg21Model.status_dev < 114 || Pkg21Model.status < 124}">
									<p>
										<img src="/images/ic_flow.png">
										<span class="over_text">과금검증</span>
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status > 123 && Pkg21Model.select_status == 161}">
									<p class="on">
										과금검증
									</p>
								</c:when>
								<c:otherwise>
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('161');">
										과금검증
									</p>
								</c:otherwise>
							</c:choose>	
						</c:when>
						<c:when test="${Pkg21Model.status > 123 && Pkg21Model.select_status == 161}">
							<p class="on">
								과금검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 123}">
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('161');">
								과금검증
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">과금검증</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.vol_yn == 'N'}">
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">용량검증</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.vol_yn == 'S' && Pkg21Model.select_status == 151}">
							<p class="on_blue">
								용량검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.vol_yn == 'S'}">
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('151');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing text_1">용량검증</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 101 && Pkg21Model.select_status == 151}">
							<p class="on">
								용량검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 101}">
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('151');">
								용량검증
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">용량검증</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow" >
					<c:choose>
						<c:when test="${Pkg21Model.sec_yn == 'S' && Pkg21Model.select_status == '171'}">
							<p class="on_blue">
								보안검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.sec_yn == 'S'}">
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('171');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing text_1">보안검증</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.select_status == 171}">
							<p class="on">
								보안검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 101}">
							<p style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('171');">
								<img src="/images/ic_flow.png">
								<span class="over_text">보안검증</span>
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">보안검증</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
			<ul class="sub_flow_line03 fl_wrap">
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status < 124}">
							<p class="w_long">
								<img src="/images/ic_flow.png">
								<span class="over_text">초도적용 계획수립</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 131 && Pkg21Model.select_status == 131}">
							<p class="on_blue">
								초도적용 계획수립
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 131}">
							<p class="ing w_long" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('131');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing">초도적용 계획수립</span>
							</p>
						</c:when>
						<c:when test="${(Pkg21Model.cha_yn == 'Y' || Pkg21Model.vol_yn == 'Y') && s_granted == 'N'}">
							<p class="w_long">
								<img src="/images/ic_flow.png">
								<span class="over_text">초도적용 계획수립</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.dev_yn == 'D'}">
							<c:choose>
								<c:when test="${Pkg21Model.status_dev < 114}">
									<p class="w_long">
										<img src="/images/ic_flow.png">
										<span class="over_text">초도적용 계획수립</span>
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status_dev > 113 && Pkg21Model.select_status == 131}">
									<p class="on">
										초도적용 계획수립
									</p>
								</c:when>
								<c:otherwise>
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('131');">
										초도적용 계획수립
									</p>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${Pkg21Model.status > 123 && Pkg21Model.select_status == 131}">
							<p class="on">
								초도적용 계획수립
							</p>
						</c:when>
						<c:otherwise>
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('131');">
								초도적용 계획수립
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status < 132}">
							<p class="w_long">
								<img src="/images/ic_flow.png">
								<span class="over_text">초도적용 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 133 && Pkg21Model.select_status == 133}">
							<p class="on_blue">
								초도적용 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 133}">
							<p class="ing w_long" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('133');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing">초도적용 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 131 && Pkg21Model.select_status == 133}">
							<p class="on">
								초도적용 결과
							</p>
						</c:when>
						<c:otherwise>
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('133');">
								초도적용 결과
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status < 134 || Pkg21Model.status == 139 || Pkg21Model.eq_cnt == 0}">
							<p class="w_long">
								<img src="/images/ic_flow.png">
								<span class="over_text">확대적용 계획수립</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 141 && Pkg21Model.select_status == 141}">
							<p class="on_blue">
								확대적용 계획수립
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 141}">
							<p class="ing w_long" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('141');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing">확대적용 계획수립</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 133 && Pkg21Model.select_status == 141}">
							<p class="on">
								확대적용 계획수립
							</p>
						</c:when>
						<c:otherwise>
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('141');">
								확대적용 계획수립
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status < 142 || Pkg21Model.eq_cnt == 0}">
							<p class="w_long">
								<img src="/images/ic_flow.png">
								<span class="over_text">확대적용 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 143 && Pkg21Model.select_status == 143}">
							<p class="on_blue">
								확대적용 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 143}">
							<p class="ing w_long" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('143');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing">확대적용 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 141 && Pkg21Model.select_status == 143}">
							<p class="on">
								확대적용 결과
							</p>
						</c:when>
						<c:otherwise>
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('143');">
								확대적용 결과
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status == 199}">
							<p class="on_blue">
								완료
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">완료</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
		</div>
	</div>
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">PKG요약</h2>
		<div class="sub_con_close2">
			<span id="con_open" style="cursor: pointer;" onclick="javascript:fn_open_and_close('0');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list" style="display: block">
		<div class="table_style03 fl_wrap">
			<table class="con_width100">
				<colgroup>
					<col width="15%">
					<col width="9%">
					
					<col width="19%">
					<col width="9%">
					
					<col width="19%">
					<col width="9%">
					
					<col width="19%">
				</colgroup>
				<thead>
					<tr>
						<th>시험항목</th>
						<th>항목수</th>
						<th>SVT결과<br/>(OK/NOK/COK/POK)</th>
						<th>항목수</th>
						<th>DVT결과<br/>(OK/NOK/COK/POK)</th>
						<th>항목수</th>
						<th>CVT결과<br/>(OK/NOK/COK/POK)</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${Pkg21Model.pkg21FileModelList}" varStatus="status">
						<c:if test="${result.test_item != 'ABN' && result.test_item != 'REG' && result.test_item != 'RM'}">
							<tr>
								<td class="td_center txt_red">
									<c:choose>
										<c:when test="${result.test_item == 'NEW'}">
											신규
										</c:when>
										<c:when test="${result.test_item == 'ABN'}">
											Abnoramal 항목
										</c:when>
										<c:when test="${result.test_item == 'OP'}">
											운용투자
										</c:when>
										<c:when test="${result.test_item == 'REG'}">
											Regression 항목
										</c:when>
										<c:when test="${result.test_item == 'RM'}">
											RM 항목
										</c:when>
										<c:when test="${result.test_item == 'SELF'}">
											자체보완
										</c:when>
										<c:otherwise>
											${result.test_item}
										</c:otherwise>
									</c:choose>
								</td>
								<td class="td_center">${result.svt_cnt}</td>
								<td class="td_center">
									${result.svt_ok} / ${result.svt_nok} / ${result.svt_cok} / ${result.svt_pok}
								</td>
								<td class="td_center">${result.dvt_cnt}</td>
								<td class="td_center">
									${result.dvt_ok} / ${result.dvt_nok} / ${result.dvt_cok} / ${result.dvt_pok}
								</td>
								<td class="td_center">${result.cvt_cnt}</td>
								<td class="td_center">
									${result.cvt_ok} / ${result.cvt_nok} / ${result.cvt_cok} / ${result.cvt_pok}
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>		
		</div>
		
		<h3>주요보완내역</h3>
		<div class="table_style01">
			<table class="con_width100">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tbody>
					<tr>
						<th>신규</th>
						<td><pre>${Pkg21Model.content}</pre></td>
					</tr>
					<tr>
						<th>운용투자</th>
						<td><pre>${Pkg21Model.content_invest}</pre></td>
					</tr>
					<tr>
						<th>PN</th>
						<td><pre>${Pkg21Model.content_pn}</pre></td>
					</tr>
					<tr>
						<th>CR</th>
						<td><pre>${Pkg21Model.content_cr}</pre></td>
					</tr>
					<tr>
						<th>자체보완</th>
						<td><pre>${Pkg21Model.content_self}</pre></td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<!-- <div class="write_info2 fl_wrap">
			<ul>
				<li class="">등록</li>
				<li class="name2">고길동 (2018-03-03 13:12:25)</li>
				<li class="">수정</li>
				<li class="name2">고길동 (2018-03-04 15:05:32)</li>
			</ul>
		</div> -->
		<c:if test="${Pkg21Model.dev_yn == 'Y' ? (Pkg21Model.status == 114) : (Pkg21Model.status == 102)}">
			<div class="bt_btn2">
				<span class="btn_org2">
				<!-- 
					20181115 eryoon 수정 
					AS-IS: <a href="javascript:fn_reject_121();">DVT 결과 반려</a>
				-->
					<a href="javascript:fn_reject_121();">SVT결과 반려</a>
				</span>
			</div>
		</c:if>
	</div>
	<!---- /PKG요약 ---->
	
	
	<!---- CVT 검증계획 ---->
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">CVT 검증계획</h2>
		<div class="sub_con_close2">
			<span id="con_open1" style="cursor: pointer;" onclick="javascript:fn_open_and_close('1');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list1" style="display: block">
		
		<div class="table_style01">
			<table class="con_width100">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tbody>
					<tr>
						<th>CVT 검증 일자 <span class="txt_red">*</span></th>
						<td>
							<form:input path="col1" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col2" class="new_inp fl_left" readOnly="true" />
						</td>
					</tr>
					<tr>
						<th>Comment</th>
						<td>
							<form:textarea path="col3" rows="5" class="textarea_w95" maxlength="550" />
							<div id="cvtPComdrop" class="mt03">
								<input type="text" class="new_inp new_inp_w3 fl_left"> 
								<span class="btn_line_blue2 mg02">
									<a href="#">파일첨부</a>
								</span>
							</div>
						</td>
					</tr>
				</tbody>
			</table>		
		</div>
		
		<div class="table_style03 mt20">
			<table class="con_width100">
				<colgroup>
					<col width="5%">
					<col width="5%">
					<col width="10%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<thead>
					<tr>
						<th>선택</th>
						<th>승인</th>
						<th>이름</th>
						<th>부서</th>
						<th>전화번호</th>
						<th>이메일</th>
						<th>결과</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${Pkg21Model.dev_yn == 'Y' ? (Pkg21Model.status == 114) : (Pkg21Model.status == 102)}">
						<c:forEach var="result" items="${Pkg21Model.systemUserModelList}" varStatus="status">
							<tr>
								<td class="td_center">
									<c:choose>
										<c:when test="${empty result.user_id}">
											<input type="checkbox" name="check_seqs" value="${result.user_id}" />
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="check_seqs" value="${result.user_id}" checked/>
										</c:otherwise>
									</c:choose>
								</td>
								<td class="td_center">${status.count} 차</td>
								<td class="td_center">${result.user_name}</td>
								<td class="td_center">${result.sosok}</td>
								<td class="td_center">${result.user_phone}</td>
								<td class="td_center">${result.user_email}</td>
								<td class="td_center">미요청</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${Pkg21Model.dev_yn == 'Y' ? (Pkg21Model.status > 114) : (Pkg21Model.status > 102)}">
						<c:forEach var="result" items="${Pkg21Model.pkgUserModelList}" varStatus="status">
							<tr>
								<td class="td_center">
								</td>
								<td class="td_center">${status.count} 차</td>
								<td class="td_center">${result.user_name}</td>
								<td class="td_center">${result.sosok}</td>
								<td class="td_center">${result.user_phone}</td>
								<td class="td_center">${result.user_email}</td>
								<td class="td_center">
									<c:choose>
										<c:when test="${result.status == 'F'}">
											<font color="blue">승인</font>
											<c:if test="${not empty result.update_date}">
												(${result.update_date})
											</c:if>
										</c:when>
										<c:otherwise>
											미승인
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<div class="mt10 mb10 txt_888">
				<p>* 시스템에 등록된 상용승인 담당자 정보를 현행화하세요.</p>
			</div>
		</div>
		<div class="write_info2 fl_wrap">
			검증계획 승인요청
			<span class="name2">
				<c:if test="${Pkg21Model.dev_yn == 'Y' ? (Pkg21Model.status > 114) : (Pkg21Model.status > 102)}">
					${Pkg21Model.reg_plan_user} (${Pkg21Model.reg_date_plan})
				</c:if>
			</span>
		</div>
		<c:if test="${Pkg21Model.dev_yn == 'Y' ? (Pkg21Model.status == 114) : (Pkg21Model.status == 102)}">
			<c:if test="${not empty Pkg21Model.systemUserModelList}">
				<div class="bt_btn2">
					<span class="btn_org2">
						<a href="javascript:fn_save_121();">계획 승인 요청</a>
					</span>
				</div>
			</c:if>
		</c:if>
	</div>
	<!---- /CVT 검증계획 ---->
	
	<!---- CVT검증계획 승인 ---->
	<c:if test="${Pkg21Model.dev_yn == 'Y' ? (Pkg21Model.status > 114) : (Pkg21Model.status > 102)}">
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">CVT검증계획 승인</h2>
		<div class="sub_con_close2">
			<span id="con_open2" style="cursor: pointer;" onclick="javascript:fn_open_and_close('2');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list2" style="display: block">
		
		<div class="table_style03">
			<table class="con_width100">
				<colgroup>
					<col width="15%">
					<col width="5%">
					<col width="10%">
					<col width="70%">
				</colgroup>
				<thead>
					<tr>
						<th>상태</th>
						<th>승인</th>
						<th>이름</th>
						<th>Comment <span class="txt_red">*</span></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${Pkg21Model.pkgUserModelList}" varStatus="status">
						<tr>
							<td class="td_center">
								<c:choose>
									<c:when test="${result.status == 'F'}">
										<font color="blue">승인</font>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
												<span class="btn_wrap">
													<a href="javascript:fn_save_122('${result.ord}');" class="btn btn_red">승인</a>
												</span>
											</c:when>
											<c:otherwise>
												미승인
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="td_center">${status.count} 차</td>
							<td class="td_center">${result.user_name}</td>
							<td class="td_center">
								<c:choose>
									<c:when test="${result.status == 'F'}">
										${result.au_comment}
									</c:when>
									<c:otherwise>
										<c:if test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
											<form:input path="au_comment" maxlength="100" class="new_inp inp_w90" />
										</c:if>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="mt10 mb10 txt_888">
				<p>* 승인자 선택 후 계획승인 요청 시 1차 승인자에게 SMS/E-Mail이 전송됩니다.</p>
			</div>
		</div>
	</div>
	</c:if>
	<!---- CVT검증계획 승인 ---->
	
	<c:if test="${Pkg21Model.status > 121}">
	<!---- CVT 검증결과 ---->
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">CVT 검증결과</h2>
		<div class="sub_con_close2">
			<span id="con_open3" style="cursor: pointer;" onclick="javascript:fn_open_and_close('3');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list3" style="display: block">
		
<!-- 		<div class="table_style01"> -->
<!-- 			<table class="con_width100"> -->
<%-- 				<colgroup> --%>
<%-- 					<col width="20%"> --%>
<%-- 					<col width="80%"> --%>
<%-- 				</colgroup> --%>
<!-- 				<tbody> -->
<!-- 					<tr> -->
<!-- 						<th>CVT 검증 일자 <span class="txt_red">*</span></th> -->
<!-- 						<td> -->
<%-- 							<form:input path="col4" class="new_inp fl_left" readOnly="true" /> --%>
<!-- 							<span class="fl_left mg05"> ~ </span> -->
<%-- 							<form:input path="col5" class="new_inp fl_left" readOnly="true" /> --%>
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 				</tbody> -->
<!-- 			</table> -->
<!-- 		</div> -->
		
		<div class="table_style01 mt10">
			<table class="con_width100">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tbody>
					<tr>
						<th>검증결과 <span class="txt_red">*</span></th>
						<td class="sysc_inp">
							<form:select path="col11" items="${Pkg21Model.verify_result_3List}" itemLabel="codeName" itemValue="code" />
						</td>
					</tr>
				</tbody>
				<tbody id ="attachlist">
					<tr>
						<th>요구사항 명세서</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left"> 
							<span class="btn_line_blue2 mg02"><a href="#">파일첨부</a></span>
						</td>
					</tr>
					<tr>
						<th>보완내역서</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left"> 
							<span class="btn_line_blue2 mg02"><a href="#">파일첨부</a></span>
						</td>
					</tr>
					<tr>
						<th>CVT 결과서 <span class="txt_red">*</span><br>(설계Review 내용 포함)</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left"> 
							<span class="btn_line_blue2 mg02"><a href="#">파일첨부</a></span>
						</td>
					</tr>
					<tr>
						<th>정적분석 결과서</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left"> 
							<span class="btn_line_blue2 mg02"><a href="#">파일첨부</a></span>
						</td>
					</tr>
					<tr>
						<th>IT 요소기술 Check List</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left"> 
							<span class="btn_line_blue2 mg02"><a href="#">파일첨부</a></span>
						</td>
					</tr>
					<tr>
						<th>절차서</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left"> 
							<span class="btn_line_blue2 mg02"><a href="#">파일첨부</a></span>
						</td>
					</tr>
					<tr>
						<th>Check List</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left"> 
							<span class="btn_line_blue2 mg02"><a href="#">파일첨부</a></span>
						</td>
					</tr>
					</tbody>
					<tbody>
					<tr>
						<th>
							과금검증
							<c:if test="${Pkg21Model.cha_yn != 'N'}">
								<span class="txt_red">*</span>
							</c:if>
							<br/>Comment
						</th>
						<td>
							<form:textarea path="col6" rows="5" class="textarea_w95" maxlength="550" />
							<div id="cvtBillComdrop" class="mt10">
								<input type="text" class="new_inp new_inp_w3 fl_left"> 
								<span class="btn_line_blue2 mg02"><a href="#">첨부파일</a></span>
							</div>
						</td>
					</tr>
					<tr>
						<th>
							용량검증
							<c:if test="${Pkg21Model.vol_yn != 'N'}">
								<span class="txt_red">*</span>
							</c:if>
							<br>Comment
						</th>
						<td>
							<form:textarea path="col7" rows="5" class="textarea_w95" maxlength="550" />
							<div id="cvtVolComdrop" class="mt10">
								<input type="text" class="new_inp new_inp_w3 fl_left"> 
								<span class="btn_line_blue2 mg02"><a href="#">첨부파일</a></span>
							</div>
						</td>
					</tr>
					<tr>
						<th>
							상용적용시 유의사항<br>(RM/CEM Feature 등)
							<p class="txt_blue txt_11 mt10">- COD, POD 변경내역</p>
							<p class="txt_blue txt_11">- 팀 내 Cross check 결과</p>
							<p class="txt_blue txt_11">- Alarm fault status 변경내역</p>
						</th>
						<td>
							<form:textarea path="col8" rows="5" class="textarea_w95" maxlength="550" />
							<div id="cvtNotiComdrop" class="mt10">
								<input type="text" class="new_inp new_inp_w3 fl_left"> 
								<span class="btn_line_blue2 mg02"><a href="#">첨부파일</a></span>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
<!-- 		<div class="table_style01 mt10">	 -->
<!-- 			<table class="con_width100 mt10"> -->
<%-- 				<colgroup> --%>
<%-- 					<col width="20%"> --%>
<%-- 					<col width="80%"> --%>
<%-- 				</colgroup> --%>
<!-- 				<tbody> -->
<!-- 					<tr> -->
<!-- 						<th>초도적용 예정일정</th> -->
<!-- 						<td> -->
<%-- 							<form:input path="col9" class="new_inp fl_left" readOnly="true" /> --%>
<!-- 							<span class="fl_left mg05"> ~ </span> -->
<%-- 							<form:input path="col10" class="new_inp fl_left" readOnly="true" /> --%>
<!-- 							<span class="mt10 mb10 txt_888"> -->
<!-- 								* 초도 적용일 3일 전까지 과금 검증, 용량 검증이 완료되어야 합니다 -->
<!-- 							</span> -->
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 				</tbody> -->
<!-- 			</table> -->
<!-- 		</div> -->
		
		<h3>CVT검증결과 승인</h3>
		
		<div class="table_style03">
			<table class="con_width100">
				<colgroup>
					<col width="5%">
					<col width="5%">
					<col width="10%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<thead>
					<tr>
						<th>선택</th>
						<th>승인</th>
						<th>이름</th>
						<th>부서</th>
						<th>전화번호</th>
						<th>이메일</th>
						<th>결과</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${Pkg21Model.status == 122}">
						<c:forEach var="result" items="${Pkg21Model.systemUserModelList}" varStatus="status">
							<tr>
								<td class="td_center">
									<c:choose>
										<c:when test="${empty result.user_id}">
											<input type="checkbox" name="check_seqs" value="${result.user_id}" />
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="check_seqs" value="${result.user_id}" checked/>
										</c:otherwise>
									</c:choose>
								</td>
								<td class="td_center">${status.count} 차</td>
								<td class="td_center">${result.user_name}</td>
								<td class="td_center">${result.sosok}</td>
								<td class="td_center">${result.user_phone}</td>
								<td class="td_center">${result.user_email}</td>
								<td class="td_center">미요청</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${Pkg21Model.status > 122}">
						<c:forEach var="result" items="${Pkg21Model.pkgUserModelList2}" varStatus="status">
							<tr>
								<td class="td_center">
									<c:if test="${result.status == 'R'}">
										<c:if test="${result.ord == Pkg21Model.user_active_status2 and result.user_id == Pkg21Model.session_user_id}">
											<span class="btn_wrap">
												<a href="javascript:fn_save_124('${result.ord}');" class="btn btn_red">승인</a>
											</span>
										</c:if>
									</c:if>
								</td>
								<td class="td_center">${status.count} 차</td>
								<td class="td_center">${result.user_name}</td>
								<td class="td_center">${result.sosok}</td>
								<td class="td_center">${result.user_phone}</td>
								<td class="td_center">${result.user_email}</td>
								<td class="td_center">
									<c:choose>
										<c:when test="${result.status == 'F'}">
											<font color="blue">승인</font>
											<c:if test="${not empty result.update_date}">
												(${result.update_date})
											</c:if>
										</c:when>
										<c:otherwise>
											미승인
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<div class="mt10 mb10 txt_888">
				<p>* 시스템에 등록된 개발승인 담당자 정보를 현행화하세요.</p>
				<p>! 승인자 선택 후 계획승인 요청 시 1차 승인자에게 SMS/E-Mail이 전송됩니다.
				<p>! 최종 승인 담당자의 승인 완료 시 용량검증, 과금검증 담당자에게 결과 등록 요청 SMS/E-Mail이 전송됩니다.</p>
			</div>
		</div>
		
		<div class="write_info2 fl_wrap">
			검증결과 승인요청
			<span class="name2">
				<c:if test="${Pkg21Model.status > 122}">
					${Pkg21Model.reg_result_user} (${Pkg21Model.reg_date_result})
				</c:if>
			</span>
		</div>
		<c:if test="${Pkg21Model.status == 122}">
			<div class="bt_btn2">
				<span class="btn_org2">
					<a href="javascript:fn_save_123();">결과 승인요청</a>
				</span>
			</div>
		</c:if>
	</div>
	</c:if>
</div>
</form:form>
</body>
</html>
