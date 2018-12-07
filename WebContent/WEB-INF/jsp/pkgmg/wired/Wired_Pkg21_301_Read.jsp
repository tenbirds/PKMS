<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/ui.js"></script>
<script type="text/javascript"  src="/js/pkgmg/pkgmg.wired.pkg21.js"></script>

<script type="text/javaScript" defer="defer">
	$(document).ready(
		function($) {
			status_navi('2');//navi
			// Calendar Init
			doCalendar("col1");
			doCalendar("col2");
			
			doCalendar("col4");
			doCalendar("col5");
			
			
			fn_cell_view_change();
			
			$('textarea[maxlength]').keydown(function(){
		        var max = parseInt($(this).attr('maxlength'));
		        var str = $(this).val();
				if(str.length > max) {
				    $(this).val(str.substr(0, max));
					alert("최대 [" + max + " 자]까지 입력 가능합니다.");
				}
			});
			
			attachList();//첨부
			
			
		}
	);
	
	function fn_cell_view_change(){
		var col17 = $("#col17");

		if($(col17).is(":checked")){
			$("#col17").val("Y");
			doDivSH("show", "cell_view", 0);
		}else{
			$("#col17").val("N");
			doDivSH("hide", "cell_view", 0);
		}
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
  			

  			
// 			if(obj.attr("name") == "cvtResult"){
// 	  			var chvalue = files[i].name;
// 				var Extension = chvalue.substring(chvalue.lastIndexOf(".") + 1);
// 				var chk = Extension.toLowerCase(Extension);
				
// 				var filechk =["xlsx"];
				
// 				var j = 0;
// 				for(var i = 0; i < filechk.length; i++){
// 					if(chk == filechk[i]){
// 						j++;
// 					}
// 				}
// 				if(j==0) {
// 					boo = false;
// 				}
// 			}
  			
  			
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
       if( paidcall != "wiredDevResultAttach"   ){
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
			if(list[i].attach_file_id  != null && "wiredDevResultAttach" == list[i].attach_file_id.substring(0,20)  && type == "wiredDevResult"){//				ttm 첨부
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
	   data:{"master_file_id" : $("#master_file_id").val(), "type" : "유선PKG검증결과","use_yn" : "Y"},
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
// 						if("cvtPComAttach1" == data[i].attach_file_id ){wiredResultdrop
						if("wiredDevResultAttach" == data[i].attach_file_id.substring(0,20) &&  flag1 ){
							var htmlttm =	attachfiles(data[i].id,data,"wiredDevResult"); //	cvtcomment
							
							html2 +=	''+htmlttm[0] ;
							if(htmlttm[1] < 5 ){
								html2 += '<input name=\"wiredDevResultAttach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
								html2 += '<input type=\"hidden\" id=\"wiredDevResultAttach_count\"'+' name="5" value=\"'+htmlttm[1]+'\" >';
// 								html2 += '<span class=\"btn_line_blue2 ml10 mt02\">';
								html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'wiredDevResultAttach\' );\">';
// 								html2 += '</span>';
							}
							flag1 = false;
						}
						
						
						
					}
				}
			}
	 		
	 		if(html2.length == 0){
				html2 += '<input name=\"wiredDevResultAttach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
// 				html2 += '<span class=\"btn_line_blue2 ml10 mt02\">';
				html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'wiredDevResultAttach\' );\">';
// 				html2 += '</span>';
	 		}

	 		
	 		$('#wiredDevResultdrop').empty();
	 		$('#wiredDevResultdrop').append(html2);

	 		
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
        if( paidcall != "wiredDevResultAttach"){
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
	<form:hidden path="save_status" />
	<form:hidden path="status_dev" />
	<form:hidden path="read_gubun" />
	<form:hidden path="dev_yn" />
	<form:hidden path="system_seq" />
	<form:hidden path="content" />
	<form:hidden path="content_pn" />
	<form:hidden path="content_cr" />
	<form:hidden path="content_self" />
	<form:hidden path="reg_user" />
	<form:hidden path="reg_user_gubun" />
	<form:hidden path="title" />
	
	<form:hidden path="ord" />
	<form:hidden path="master_file_id" />
	
<!-- 	navi -->
	<form:hidden path="col43" />
		
	<!-- 현황 예제1 -->
	<div class="sub_contents01 fl_wrap">
		<div class="sub_flow_wrap_100">
						<ul class="sub_flow_line01 fl_wrap">
				<li id="status_navi1" class="sub_flow">
					<p id="status_navi_fn1" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_read('read', '${Pkg21Model.pkg_seq}');">
						PKG개발 결과
					</p>
					<p id="status_navi_ing1" class="ing" style="cursor: pointer; display:none; " onclick="javascript:fn_pkg21_read('read', '${Pkg21Model.pkg_seq}');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">	PKG개발 결과</span>
					</p>
					<p id="status_navi_now1" class="on_blue" >
						PKG개발 결과
					</p>
					<p id="status_navi_non1" class="w_long"  style=" display:none; " >
						<img src="/images/ic_flow.png">
						<span class="over_text">PKG개발 결과</span>
					</p>
				</li>
				
				<li id="status_navi2" class="sub_flow">
					<p id="status_navi_fn2" class="on" style="cursor: pointer; display:none; " onclick="javascript:fn_pkg21_status_read('301');">
						개발검증
					</p>
					<p id="status_navi_ing2" class="ing" style="cursor: pointer; display:none; " onclick="javascript:fn_pkg21_status_read('301');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">개발검증</span>
					</p>
					<p id="status_navi_now2" class="on_blue" style="display:none;" >
						개발검증
					</p>
					<p id="status_navi_non2" class="w_long" style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">개발검증</span>
					</p>
				</li>
				
				<li id="status_navi3" class="sub_flow">
					<p id="status_navi_fn3"  class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('331');">
						초도적용계획수립
					</p>
					<p id="status_navi_ing3"  class="ing" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('331');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">초도적용계획수립</span>
					</p>
					<p id="status_navi_now3" class="on_blue" style="display:none;" >
						초도적용계획수립
					</p>
					<p id="status_navi_non3" class="w_long" style=" " >
						<img src="/images/ic_flow.png">
						<span class="over_text">초도적용계획수립</span>
					</p>
				</li>
				<li id="status_navi4" class="sub_flow">
					<p id="status_navi_fn4" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('336');">
						초도적용 결과
					</p>
					<p id="status_navi_ing4" class="ing"  style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('336');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">초도적용 결과</span>
					</p>
					<p id="status_navi_now4" class="on_blue" style="display:none;" >
						초도적용 결과
					</p>
					<p id="status_navi_non4" class="w_long"  style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">초도적용 결과</span>
					</p>
				</li>
				<li id="status_navi5" class="sub_flow">
					<p id="status_navi_fn5" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('341');">
						확대적용계획수립
					</p>
					<p id="status_navi_ing5" class="ing" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('341');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">확대적용계획수립</span>
					</p>
					<p id="status_navi_now5" class="on_blue" style="display:none;" >
						확대적용계획수립
					</p>
					<p id="status_navi_non5" class="w_long"  style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">확대적용계획수립</span>
					</p>
				</li>
				<li id="status_navi6" class="sub_flow">
					<p id="status_navi_fn6" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('343');">
						확대적용결과
					</p>
					<p id="status_navi_ing6" class="ing" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('343');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">확대적용결과</span>
					</p>
					<p id="status_navi_now6" class="on_blue" style="display:none;" >
						확대적용결과
					</p>	
					<p id="status_navi_non6" class="w_long"  style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">확대적용결과</span>
					</p>
				</li>
				
				<li id="status_navi7" class="sub_flow">
					<p id="status_navi_fn7" class="on" style="cursor: pointer;display:none;  ">
						완료
					</p>
					<p id="status_navi_non7" class="w_long"  style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">완료</span>
					</p>
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
	<div class="sub_contents01 fl_wrap mb20"  id="con_list" style="display: block">
		
		<h3>주요보완내역</h3>
		<div class="table_style01">
			<table class="con_width100">
				<colgroup>
					<col width="12%">
					<col width="30%">
					<col width="10%">
					<col width="48%">
				</colgroup>
				<tbody>
					<tr>
						<th>시스템</th>
						<td colspan="3">
<%-- 							<form:hidden path="system_seq" /> --%>
							<span class="fl_left">
								<form:input path="system_name" maxlength="50" style="width:500px" readonly="true" />
							</span>
<%-- 							<c:if test="${registerFlag == '등록'}"> --%>
<!-- 								<span class="btn_line_blue2 ml03 mt05"> -->
<!-- 									<img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup"/> -->
<!-- 								</span> -->
<%-- 							</c:if> --%>
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>

									<span class="mg03 ml05 mr25">${Pkg21Model2.title}</span>
<%-- 									<form:hidden path="title" />									 --%>

						</td>
						<th>작업난이도 <span class="txt_red">*</span></th>
						<td class="sysc_inp">
							<form:select path="work_level" items="${Pkg21Model2.work_level_list}" itemLabel="codeName" itemValue="code" />
						</td>
					</tr>
					<tr>
						<th>중요도 <span class="txt_red">*</span></th>
						<td class="sysc_inp">
							<form:select path="important" items="${Pkg21Model2.important_list}" itemLabel="codeName" itemValue="code" />
						</td>
						<th>로밍영향도 </th>
						<td class="sysc_inp">
						<form:radiobuttons path="roaming_link" items="${Pkg21Model.roaming_link_list}" itemLabel="codeName" itemValue="code" class="fl_left" />
<!-- 								추후결정 -->
						</td>
					</tr>
					<tr>
						<th>PKG버전 <span class="txt_red">*</span></th>
						<td>
							<form:input path="ver" class="new_inp" maxlength="10" />
						</td>
						
						<th>버전구분 <span class="txt_red">*</span></th>
						<td>
									<input type="hidden"  id="patch_yn" name="patch_yn" value="${Pkg21Model2.patch_yn}" />
									<c:choose>
											<c:when test="${Pkg21Model.patch_yn == 'Y'}">
												<span>
													<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun1" disabled="yes" value="F"/> 
													<label for="ver_gubun1">Full</label>
												</span>
												<span>	
													<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun2" disabled="yes"  value="P"/> 
													<label for="ver_gubun2">Partial</label>
												</span>
												<span>	
													<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun3" checked="checked" value="C"/> 
													<label for="ver_gubun3">Patch</label>
												</span>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${Pkg21Model.ver_gubun == 'F'}">
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun1" checked="checked" value="F"/> 
															<label for="ver_gubun1">Full</label>
														</span>
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun2" disabled="yes" value="P"/> 
															<label for="ver_gubun2">Partial</label>
														</span>
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun3" disabled="yes" value="C"/> 
															<label for="ver_gubun3">Patch</label>
														</span>
													</c:when>
													<c:otherwise>
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun1" disabled="yes" value="F"/> 
															<label for="ver_gubun1">Full</label>
														</span>
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun2" checked="checked" value="P"/> 
															<label for="ver_gubun2">Partial</label>
														</span>
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun3" disabled="yes" value="C" /> 
															<label for="ver_gubun3">Patch</label>
														</span>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>

						</td>
					</tr>
				</tbody>
			</table>
			<br>
			<table class="tbl_type22">
				<colgroup>
					<col width="12%">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>신규</th>
						<td><pre>${Pkg21Model2.content}</pre></td>
					</tr>
					<tr>
						<th>PN</th>
						<td><pre>${Pkg21Model2.content_pn}</pre></td>
					</tr>
					<tr>
						<th>CR</th>
						<td><pre>${Pkg21Model2.content_cr}</pre></td>
					</tr>
					<tr>
						<th>자체보완</th>
						<td><pre>${Pkg21Model2.content_self}</pre></td>
					</tr>
				</tbody>
			</table>
		</div>
		
		
	<div class="bt_btn">
		<span class="btn_org2">
			<c:if test="${Pkg21Model.status < 302}">
<!-- 				<a href="javascript:fn_reject_rollback();">반려</a> -->
				<a href="javascript:fn_reject_302();">반 려</a>
<%-- 			</c:if> --%>
<%-- 			<c:if test="${Pkg21Model.select_status > 305 and Pkg21Model.select_status < 308}"> --%>
				<a href="javascript:fn_save_302();">저 장</a>
			</c:if>
		</span>
	</div>
<%-- 					status:	${Pkg21Model.status}   a:	${Pkg21Model.select_status} --%>
		
	</div>
	<!---- /PKG요약 ---->
	
	
	
	
	
	
	
	
	
	
	
	<c:if test="${Pkg21Model.status > '301'  }">
	
	
	<!---- 검증계획 ---->
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">검증계획</h2>
		<div class="sub_con_close2">
			<span id="con_open1" style="cursor: pointer;" onclick="javascript:fn_open_and_close('1');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list1" style="display: block">
		
		<div class="table_style01">
			<table class="con_width100">
				<colgroup>
					<col width="12%">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>검증 일자 <span class="txt_red">*</span></th>
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
<!-- 							<div id = "cvtPComdrop"  name = "cvtPComdrop" class="mt03"> -->
<!-- 								<input type="text" class="new_inp new_inp_w3 fl_left">  -->
<!-- 								<span class="btn_line_blue2 mg02"> -->
<!-- 									<a href="#">파일첨부</a> -->
<!-- 								</span> -->
<!-- 							</div> -->
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
<%-- 					<col width="15%"> --%>
					<col width="15%">
					<col width="35%">
				</colgroup>
				<thead>
					<tr>
						<th>선택</th>
						<th>승인</th>
						<th>이름</th>
						<th>부서</th>
<!-- 						<th>전화번호</th> -->
<!-- 						<th>이메일</th> -->
						<th>결과</th>
						<th>Comment <span class="txt_red">*</span></th>
					</tr>
				</thead>
				<tbody>
<%-- 					<c:if test="${Pkg21Model.dev_yn == 'Y' ? (Pkg21Model.status == 305) : (Pkg21Model.status == 301)}"> --%>
<!-- 314상태는 반려상태 -->
					<c:if test="${Pkg21Model.select_status eq '302' and (Pkg21Model.status eq '311' or Pkg21Model.status eq '314') }">
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
<%-- 								<td class="td_center">${result.user_phone}</td> --%>
<%-- 								<td class="td_center">${result.user_email}</td> --%>
								<td class="td_center">미요청</td>
								<td class="td_center"></td>
							</tr>
						</c:forEach>
					</c:if>
<%-- 					<c:if test="${Pkg21Model.dev_yn == 'Y' ? (Pkg21Model.status > 305) : (Pkg21Model.status > 301)}"> --%>
					<c:if test="${Pkg21Model.select_status > '302' and Pkg21Model.status > '311'}">
						<c:forEach var="result" items="${Pkg21Model.pkgUserModelList}" varStatus="status">
							<tr>
								<td class="td_center">
									<c:if test="${result.status == 'R' and Pkg21Model.status eq '312'}"> 
										<c:if test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
											<span class="btn_wrap">
												<a href="javascript:fn_save_306('${result.ord}');" class="btn btn_red">승인</a>
												<a href="javascript:fn_reject_306();" class="btn btn_blue">반려</a>
											</span>
										</c:if>
									</c:if>
								</td>
								<td class="td_center">${status.count} 차</td>
								<td class="td_center">${result.user_name}</td>
								<td class="td_center">${result.sosok}</td>
<%-- 								<td class="td_center">${result.user_phone}</td> --%>
<%-- 								<td class="td_center">${result.user_email}</td> --%>
								<td class="td_center">
									<c:choose>
										<c:when test="${result.status == 'F'  and Pkg21Model.select_status > '305' }">
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
					</c:if>
				</tbody>
			</table>
			<div class="mt10 mb10 txt_888">
				<p>* 시스템에 등록된 상용승인 담당자 정보를 현행화하세요.</p>
<%-- 				${Pkg21Model.status} --%>
<%-- 				${Pkg21Model.select_status} --%>
			</div>
		</div>
<!-- 		<div class="write_info2 fl_wrap"> -->
<!-- 			검증계획 승인요청 -->
<!-- 			<span class="name2"> -->
<%-- 				<c:if test="${Pkg21Model.status > 301}"> --%>
<%-- 					${Pkg21Model.reg_plan_user} (${Pkg21Model.reg_date_plan}) --%>
<%-- 				</c:if> --%>
<!-- 			</span> -->
<!-- 		</div> -->
<%-- 		<c:if test="${Pkg21Model.status == 305}"> --%>
<%-- 			<c:if test="${not empty Pkg21Model.systemUserModelList}"> --%>
<!-- 				<div class="bt_btn2"> -->
<!-- 					<span class="btn_org2"> -->
<!-- 						<a href="javascript:fn_save_121();">계획 승인 요청</a> -->
<!-- 					</span> -->
<!-- 				</div> -->
<%-- 			</c:if> --%>
<%-- 		</c:if> --%>
	</div>
	<!---- /CVT 검증계획 ---->
	

</c:if>











	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<c:if test="${Pkg21Model.select_status > 305   }">
	<!---- CVT 검증결과 ---->
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">검증결과</h2>
		<div class="sub_con_close2">
			<span id="con_open3" style="cursor: pointer;" onclick="javascript:fn_open_and_close('3');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list3" style="display: block">
		
		
		<div class="table_style01 mt10">
			<table class="con_width100">
				<colgroup>
					<col width="20%">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>검증 일자 <span class="txt_red">*</span></th>
						<td>
							<form:input path="col4" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col5" class="new_inp fl_left" readOnly="true" />
						</td>
					</tr>
					<tr>
						<th>검증결과 <span class="txt_red">*</span></th>
						<td class="sysc_inp">
							<form:select path="col11" items="${Pkg21Model.verify_result_3List}" itemLabel="codeName" itemValue="code" />
						</td>
					</tr>
				</tbody>
<!-- 				<tbody id ="attachlist"> -->
				<tbody >
					<tr>
						<th>
							검증 결과서  <span class="txt_red">*</span>
						</th>
						<td>
<%-- 							<form:textarea path="col8" rows="5" class="textarea_w95" maxlength="550" /> --%>
<!-- 							<div id = "cvtNotiComdrop"  name = "cvtNotiComdrop"  class="mt10"> -->
							<div id = "wiredDevResultdrop"  name = "wiredDevResultdrop"  class="mt10">
								<input type="text" class="new_inp new_inp_w3 fl_left"> 
								<span class="btn_line_blue2 mg02"><a href="#">첨부파일</a></span>
							</div>
						</td>
					</tr>
				</tbody>
				<tbody>
					<tr>
						<th>
							상용적용시 유의사항<br>(RM/CEM Feature 등)
							<p class="txt_blue txt_11 mt10">- COD, POD 변경내역</p>
							<p class="txt_blue txt_11">- 팀 내 Cross check 결과</p>
							<p class="txt_blue txt_11">- Alarm fault status 변경내역</p>
						</th>
						<td>
							<form:textarea path="col6" rows="5" class="textarea_w95" maxlength="550" />
<!-- 							<div id = "cvtNotiComdrop"  name = "cvtNotiComdrop"  class="mt10"> -->
<!-- 								<input type="text" class="new_inp new_inp_w3 fl_left">  -->
<!-- 								<span class="btn_line_blue2 mg02"><a href="#">첨부파일</a></span> -->
<!-- 							</div> -->
						</td>
					</tr>
				</tbody>
			</table>
		</div>















		
		<h3>검증결과 승인</h3>
		
		<div class="table_style03">
			<table class="con_width100">
				<colgroup>
<%-- 					<col width="5%"> --%>
<%-- 					<col width="5%"> --%>
<%-- 					<col width="10%"> --%>
<%-- 					<col width="15%"> --%>
<%-- 					<col width="15%"> --%>
<%-- 					<col width="15%"> --%>
<%-- 					<col width="35%"> --%>
					
					<col width="5%">
					<col width="5%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
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
						<th>Comment <span class="txt_red">*</span></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${Pkg21Model.select_status == 306}">
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
								<td class="td_center"></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${Pkg21Model.select_status > 306}">
						<c:forEach var="result" items="${Pkg21Model.pkgUserModelList2}" varStatus="status">
							<tr>
								<td class="td_center">
									<c:if test="${result.status == 'R' and Pkg21Model.select_status eq '307'}">
										<c:if test="${result.ord == Pkg21Model.user_active_status2 and result.user_id == Pkg21Model.session_user_id}">
											<span class="btn_wrap">
												<a href="javascript:fn_save_308('${result.ord}');" class="btn btn_red">승인</a>
												<a href="javascript:fn_reject_308();" class="btn btn_blue">반려</a>
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
										<c:when test="${result.status == 'F' or Pkg21Model.select_status > '307' }">
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
								<td class="td_center">
									<c:choose>
										<c:when test="${result.status == 'F'}">
											${result.au_comment}
										</c:when>
										<c:otherwise>
											<c:if test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
												<form:input path="col15" maxlength="100" class="new_inp inp_w90" />
											</c:if>
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
				<p>* 승인자 선택 후 계획승인 요청 시 1차 승인자에게 SMS/E-Mail이 전송됩니다.
				<p>* 최종 승인 담당자의 승인 완료 시 용량검증, 과금검증 담당자에게 결과 등록 요청 SMS/E-Mail이 전송됩니다.</p>
			</div>
		</div>
		
		<div class="write_info2">
			검증결과 승인요청
			<span class="name2">
				<c:if test="${Pkg21Model.status > 310}">
					${Pkg21Model.reg_result_user} (${Pkg21Model.reg_date_result})
				</c:if>
			</span>
		</div>
		<c:if test="${Pkg21Model.status == 310}">
			<div class="bt_btn2">
				<span class="btn_org2">
					<a href="javascript:fn_save_123();">결과 승인요청</a>
				</span>
			</div>
		</c:if>
	</div>
	</c:if>
	
	
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	<div class="sub_contents01">
		<div class="write_info fl_wrap">
			등 록
			<span class="name2">
				${Pkg21Model.reg_user_name} (${Pkg21Model.reg_date})
			</span>
			수 정
			<span class="name2">
				${Pkg21Model.update_user_name} (${Pkg21Model.update_date})
			</span>
		</div>
	</div>
	<div class="bt_btn">
		<span class="btn_org2">
			<c:if test="${Pkg21Model.select_status > 301 and Pkg21Model.select_status < 305 }">
				<a href="javascript:fn_save_305();">저 장</a>
			</c:if>

			<c:if test="${Pkg21Model.select_status > 305 and Pkg21Model.select_status < 307}">
				<a href="javascript:fn_save_307();">저 장</a>
			</c:if>
			
		</span>
	</div>
</form:form>
</body>
</html>