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
			
			doCalendar("col3");
			doCalendar("col4");
			
			doCalendar("col5");
			doCalendar("col6");
			
			doCalendar("col7");
			doCalendar("col8");
			
			doCalendar("col9");
			doCalendar("col10");
			
			doCalendar("col11");
			doCalendar("col12");
			
			doCalendar("col13");
			doCalendar("col14");
			
			doCalendar("col15");
			doCalendar("col16");
			
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
			//20181115 eryoon 추가
			doDivSH("show", "th_devsysUserVerifyId", 0);
			doDivSH("show", "div_devsysUserVerifyId", 0);
		}else{
			$("#col17").val("N");
			doDivSH("hide", "cell_view", 0);
			//20181115 eryoon 추가
			doDivSH("hide", "th_devsysUserVerifyId", 0);
			doDivSH("hide", "div_devsysUserVerifyId", 0);
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
  			
			if(obj.attr("name") == "svtResult"){
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
        
        if( paidcall != "ttmattach"){
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
			if(list[i].attach_file_id  != null && "ttmattach" == list[i].attach_file_id.substring(0,9)){//				ttm 첨부
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
	   data:{"master_file_id" : $("#master_file_id").val(), "type" : "SVT결과 첨부","use_yn" : "Y"},
       success:function(data){
	        var html='';	
	        var html2='';
	        for(var i=0; i < data.length; i++){
// 	        	console.log(data);
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
						html +=	'<input type=\"text\" name=\"'+data[i].eng_name+'\" id =\"dropzone'+(i+1)+'\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
						html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'_count\"'+' name='+data[i].maxlistsize+' value=\"'+html3[1]+'\" >';
// 						html += '<span class=\"mt02\">';
						html += '<input type=\"file\"  size=\"50\"  onchange=\"javascript:docfile_upload(this , \''+data[i].eng_name+'\');\"  >';
// 						html += '</span>';
					}
					html +=	'</td></tr>';	
		
				}else{
					if(data[i].attach_file_id  != null){
						if("ttmattach1" == data[i].attach_file_id ){
							var htmlttm =	attachfiles(data[i].id,data,"ttm"); //	ttm 첨부
							
							html2 +=	''+htmlttm[0] ;
							if(htmlttm[1] < 5 ){
								html2 += '<input name=\"ttmattach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
								html2 += '<input type=\"hidden\" id=\"ttmattach_count\"'+' name="5" value=\"'+htmlttm[1]+'\" >';
// 								html2 += '<span class=\"mt02\">';
								html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'ttmattach\' );\">';
// 								html2 += '</span>';
							}
						}
					}
				}
			}
	 		
	 		if(html2.length == 0){

				html2 += '<input name=\"ttmattach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
// 				html2 += '<span class=\"mt02\">';
				html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'ttmattach\' );\">';
// 				html2 += '</span>';

			}
	 			 		
	 		$('#ttmdrop').empty();
	 		$('#ttmdrop').append(html2);	
	 		
			$('#attachlist').empty();
			$('#attachlist').append(html);
			

			var dopzoncount=(data.length+1);
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
		
		
		if(prefix == "svtResult"){
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
        
       if( paidcall != "ttmattach"){
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
/**
 * 20181115 eryoon 추가
 * Cell 참여 인력 선택 팝업
 */
function fn_systemUser_read_popup(sel_id) {
	$("#sel_id").val(sel_id);
	$("#" + sel_id).attr("checked", true);
	
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

	$("#sel_ids").val(sel_ids);
	$("#sel_names").val(sel_names);
	
	var sys_seq = $("#system_seq").val();
	
	var url = "/sys/system/System_Popup_SKTUser.do";
	if (sel_id.match("^sysUserBpId")) {
		url = "/sys/system/System_Popup_Bp.do";
	}
	
	var option = "width=1000px, height=550px, scrollbars=no, resizable=no, statusbar=no";
	var form = document.getElementById("Pkg21Model");
	var sWin = window.open("", "Pkg21Model", option);
	
	form.target = "Pkg21Model";
	form.action = url;
	form.submit();
	sWin.focus();
}


function fn_callback_select_user_popup(sel_ids, sel_names,select_user){
	var sel_id = $("#sel_id").val();
	var selectbox = $("#sel_" + sel_id).get(0);
	var sel_ids_real = sel_ids.split(",");
	var sel_names_real = sel_names.split(",");

	$("#div_"+sel_id).empty(); 
	var html_val="";
	for(var i = 0; i<sel_ids_real.length; i++){
		if(sel_ids_real[i] == select_user){
			html_val =  "<input type=\"hidden\" id=\"sel_"+sel_id+"_"+(i+1)+"\"       name=\"sel_"+sel_id+"\" value='"+sel_ids_real[i]+"' />";
			html_val += "<input type=\"hidden\" id=\"sel_"+sel_id+"_Names_"+(i+1)+"\" value='"+sel_names_real[i]+"' />";
			html_val += "<input type=\"hidden\" id=\"select_user_"+(i+1)+ "			  name=\"select_user\" value=\"Y\" />";
			html_val += "<span class=\"txt_red\">"+"대표"+"</span>"+"<span class=\"txt_name\">"+sel_names_real[i]+"</span>";
		}else{				
			html_val =  "<input type=\"hidden\"	id=\"sel_"+sel_id+"_"+(i+1)+"\"			name=\"sel_"+sel_id+"\" value='"+sel_ids_real[i]+"' />";
			html_val += "<input type=\"hidden\" id=\"sel_"+sel_id+"_Names_"+(i+1)+"\" value='"+sel_names_real[i]+"' />";
			html_val += "<input type=\"hidden\" id=\"select_user_"+(i+1)+ "			  	name=\"select_user\" value=\"N\" />";
			html_val += "<span class=\"txt_name\">"+sel_names_real[i]+"</span>"; 
		}
		if(i == (sel_ids_real.length-1)){
			html_val += "<input type=\"hidden\" id=\""+sel_id+"_cnt\" value='"+sel_ids_real.length+"' />";
		}
			$("#div_"+sel_id).append(html_val);
	}
	
	if(sel_id == 'devsysUserVerifyId'){
		$("#dev_system_user_id").val(select_user);
	}else if(sel_id == 'sysUserVerifyId'){
		$("#system_user_id").val(select_user);
	}else if(sel_id == 'sysUserBpId'){
		$("#bp_system_user_id").val(select_user);
	}
	$("#select_system_user_id").val(null);
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
	<form:hidden path="content_invest" />
	<form:hidden path="cha_yn" />
	<form:hidden path="vol_yn" />
	<form:hidden path="sec_yn" />
	<form:hidden path="ser_downtime" />
	<form:hidden path="impact_systems" />
	<form:hidden path="master_file_id" />
	<form:hidden path="title" />
	
	<input type="hidden" id="sel_id" 				name="sel_id"/>
	<input type="hidden" id="sel_ids" 				name="sel_ids"/>
	<input type="hidden" id="sel_names" 			name="sel_names"/>
	<input type="hidden" id="select_system_user_id" name="select_system_user_id"/>
	
	<%-- <input type="hidden" id="master_file_id" 		name="master_file_id" 		value="${Pkg21Model.master_file_id}" /> --%>
	<%-- <input type="hidden" id="deleteList" 			name="deleteList" 			value="${Pkg21Model.deleteList}" /> --%>
	<input type="hidden" id="system_user_id" 		name="system_user_id" 		value="${Pkg21Model.system_user_id}" />
	<input type="hidden" id="dev_system_user_id" 	name="dev_system_user_id" 	value="${Pkg21Model.dev_system_user_id}" />
	<input type="hidden" id="bp_system_user_id" 	name="bp_system_user_id" 	value="${Pkg21Model.bp_system_user_id}" />
	<input type="hidden" id="retUrl"				name="retUrl" />
	
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
				<%-- ${Pkg21Model.eq_cnt } --%>
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
						<c:when test="${Pkg21Model.status < 142  || Pkg21Model.eq_cnt == 0}">
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
		<h2 class="fl_left">일정</h2>
		<div class="sub_con_close2">
			<span id="con_open" style="cursor: pointer;" onclick="javascript:fn_open_and_close('0');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list" style="display: block">
		<div class="table_style01">
			<table class="con_width100">
				<colgroup>
					<col width="7%">
					<col width="13%">
					<col width="80%">
				</colgroup>
				<tbody>
					<tr>
						<th colspan="2">개발기간</th>
						<td>
							<form:input path="col1" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col2" class="new_inp fl_left" readOnly="true" />
							
							<!-- <input type="text" class="new_inp fl_left">
							<span class="btn_blueline2 mg01 ml02">
								<a href="#">달력</a>
							</span>
							<span class="fl_left mg05"> ~ </span>
							<input type="text" class="new_inp fl_left ml03">
							<span class="btn_blueline2 mg01 ml02">
								<a href="#">달력</a>
							</span> -->
						</td>
					</tr>
					<tr>
						<th rowspan="4" class="bgnone">검증</th>
						<th>DVT <span class="txt_red">*</span></th>
						<td>
							<form:input path="col3" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col4" class="new_inp fl_left" readOnly="true" />
						</td>
					</tr>
					<tr>
						<th>CVT <span class="txt_red">*</span></th>
						<td>							
							<form:input path="col5" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col6" class="new_inp fl_left" readOnly="true" />
						</td>
					</tr>
					<tr>
						<th>과금검증</th>
						<td>
							<form:input path="col7" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col8" class="new_inp fl_left" readOnly="true" />
						</td>
					</tr>
					<tr>
						<th>용량시험</th>
						<td>
							<form:input path="col9" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col10" class="new_inp fl_left" readOnly="true" />
						</td>
					</tr>
					<tr>
						<th colspan="2">적용예정기간 <span class="txt_red">*</span></th>
						<td>
							<form:input path="col11" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col12" class="new_inp fl_left" readOnly="true" />
						</td>
					</tr>
					<tr>
						<th colspan="2">IT 요소기술 Review 일정</th>
						<td>
							<form:input path="col13" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col14" class="new_inp fl_left" readOnly="true" />
						</td>
					</tr>
					<tr>
						<th colspan="2">Cell 참여 일정</th>
						<td>
							<span id="cell_view" style="display: none;">
								<form:input path="col15" class="new_inp fl_left" readOnly="true" />
								<span class="fl_left mg05"> ~ </span>
								<form:input path="col16" class="new_inp fl_left" readOnly="true" />
								&nbsp;
							</span>
							<span class="mg03 ml05 mr25">
							<c:choose>
								<c:when test="${Pkg21Model.col17 == 'Y'}">
									<input type="checkbox" class="fl_left" id="col17" name="col17" value="${Pkg21Model.col17}" onchange="fn_cell_view_change()" checked="checked" />
								</c:when>
								<c:otherwise>
									<input type="checkbox" class="fl_left" id="col17" name="col17" value="${Pkg21Model.col17}" onchange="fn_cell_view_change()" />
								</c:otherwise>
							</c:choose>
							<label for="col17">Cell 조직 참여</label>
							</span>
							<!--
								<span onclick="javascript:fn_systemUser_read_popup('devsysUserVerifyId')" id="th_devsysUserVerifyId" class="fl_left mt00 btn_line_blue">
									<span id="devsysUserVerifyId">참여인력 선택</span>
								</span>
							-->
							<br>
							<span id="div_devsysUserVerifyId" style="width:100%;">
								<c:if test="${Pkg21Model.col17 == 'Y'}">
									<c:forEach var="result" items="${Pkg21Model.readCellUserList}" varStatus="loop">
										<input type="hidden" id="sel_devsysUserVerifyId_${loop.count}"			name="sel_devsysUserVerifyId" value="${result.cell_user_id}" />
										<input type="hidden" id="sel_devsysUserVerifyId_Names_${loop.count}"	name="sel_devsysUserVerifyNm" value="${result.cell_user_nm} [${result.cell_sosok}]" select_user="${result.select_user}"/>
										<%-- <input type="hidden" id="select_user_${loop.count}"						name="select_user" 			  value="${result.select_user}" /> --%>
										<c:if test="${result.select_user eq 'Y'}"><span class="txt_red">대표</span></c:if><span class="txt_name"><c:out value="${result.cell_user_nm}" /> [${result.cell_sosok}]</span>
										<c:if test="${loop.last}">
											<input type="hidden" value="${loop.count}" id="devsysUserVerifyId_cnt">
										</c:if>
									</c:forEach>
								</c:if>
							</span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>	
	</div>
	
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">첨부</h2>
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
				<tbody id ="attachlist">
					<tr>
						<th>요구사항 명세서(요건서/명세서)</th>
						<td><input type="text" class="new_inp new_inp_w3 fl_left"> <span class="btn_blueline2"><a href="#">파일찾기</a></span></td>
					</tr>
					<tr>
						<th>보완내역서</th>
						<td><input type="text" class="new_inp new_inp_w3 fl_left"> <span class="btn_blueline2"><a href="#">파일찾기</a></span></td>
					</tr>
					<tr>
						<th>SVT 결과서<span class="txt_red">*</span><br>(설계Review 내용 포함)</th>
						<td><input type="text" class="new_inp new_inp_w3 fl_left"> <span class="btn_blueline2"><a href="#">파일찾기</a></span></td>
					</tr>
					<tr>
						<th>정적분석 결과서</th>
						<td><input type="text" class="new_inp new_inp_w3 fl_left"> <span class="btn_blueline2"><a href="#">파일찾기</a></span></td>
					</tr>
					<tr>
						<th>IT 요소기술 Check List</th>
						<td><input type="text" class="new_inp new_inp_w3 fl_left"> <span class="btn_blueline2"><a href="#">파일찾기</a></span></td>
					</tr>
					<tr>
						<th>Binary</th>
						<td><input type="text" class="new_inp new_inp_w3 fl_left"> <span class="btn_blueline2"><a href="#">파일찾기</a></span></td>
					</tr>
					<tr>
						<th>적용 Script</th>
						<td><input type="text" class="new_inp new_inp_w3 fl_left"><a href="#">파일찾기</a></td>
					</tr>
					</tbody>
					<tbody>
					<tr>
						<th>Comment</th>
						<td>
							<form:textarea path="col18" rows="5" class="textarea_w95" maxlength="550" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
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
			<c:if test="${Pkg21Model.status < 110}">
				<a href="javascript:fn_save_102();">저 장</a>
			</c:if>
		</span>
	</div>
</div>
</form:form>
</body>
</html>
