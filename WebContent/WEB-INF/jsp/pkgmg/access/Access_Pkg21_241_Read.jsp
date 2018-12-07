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
<script type='text/javascript' src='/js/pkgmg/pkgmg.access.pkg21.js'></script>

<script type="text/javaScript" defer="defer">
$(document).ready(
	function($) {
		// Calendar Init
		doCalendar("col5");
		doCalendar("col6");
		chk_value();
		
		$('textarea[maxlength]').keydown(function(){
	        var max = parseInt($(this).attr('maxlength'));
	        var str = $(this).val();
			if(str.length > max) {
			    $(this).val(str.substr(0, max));
				alert("최대 [" + max + " 자]까지 입력 가능합니다.");
			}
		});
		
		attachList();
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
  		}

  	    if(!boo){
  			if ($.browser.msie) {

  			} else {

  			}
  	     	alert("파일형식이 금지된 파일이 포함되어 있습니다.");
  	     	return;
  	    }

       	    
        var data = new FormData();
        for (var i = 0; i < files.length; i++) {
           data.append('files', files[i]);
        }
        
        var paidcall= obj.attr("name");
        data.append("prefix", obj.attr("name") );
        data.append('master_file_id', $("#master_file_id").val());
        
        //20181119 eryoon 파일명 변경
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
	   data:{"master_file_id" : $("#master_file_id").val(), "type" : "AccessCVT","use_yn" : "Y"},
       success:function(data){
	        var html='';	
	        var html2='';
	        var flag = true;
	        var td_cnt = 0;
	        
	        for(var i=0; i < data.length; i++){
// 	        	console.log(data);
				if(data[i].pId != 1000 && data[i].attach_file_id  == null){
					td_cnt++;
					if(td_cnt == 4){
						html += '<tr><th style=\"height:30px;border-bottom:1px solid #999;\">'+data[i].name+'';
					}else{						
						html += '<tr><th>'+data[i].name+'';
					}
					if(data[i].required == "Y"){
						html += '<span class=\"txt_red\">*</span>';
					}
					html += '</th>';
					html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'\" name=\"id\" value=\"'+data[i].id+'\">';
					if(td_cnt == 4){
						html +=	'<td style=\"height:30px;border-bottom:1px solid #999;\">';
					}else{					
						html +=	'<td>';
					}
					var prantid = data[i].id ;
					var html3 =	attachfiles(data[i].id,data,"listfile");
					html +=	''+html3[0] ;
					if(html3[1] < data[i].maxlistsize ){
						html +=	'<input type=\"text\" name=\"'+data[i].eng_name+'\" id =\"dropzone'+(i+1)+'\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
						html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'_count\"'+' name='+data[i].maxlistsize+' value=\"'+html3[1]+'\" >';
						html += '<span class=\"mt02\">';
						html += '<input type=\"file\"  size=\"50\"  onchange=\"javascript:docfile_upload(this , \''+data[i].eng_name+'\');\"  >';
						html += '</span>';
					}
					html +=	'</td></tr>';	
		
				}else{
					if(data[i].attach_file_id  != null){
						if("ttmattach" == data[i].attach_file_id.substring(0,9) && flag ){
							var htmlttm =	attachfiles(data[i].id,data,"ttm"); //	ttm 첨부
							
							html2 +=	''+htmlttm[0] ;
							if(htmlttm[1] < 5 ){
								html2 += '<input name=\"ttmattach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
								html2 += '<input type=\"hidden\" id=\"ttmattach_count\"'+' name="5" value=\"'+htmlttm[1]+'\" >';
								html2 += '<span class=\"mt02\">';
								html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'ttmattach\' );\">';
								html2 += '</span>';
							}
							flag = false;
						}
					}
				}
			}
	 		
	 		if(html2.length == 0){
				html2 += '<input name=\"ttmattach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
				html2 += '<span class=\"mt02\">';
				html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'ttmattach\' );\">';
				html2 += '</span>';
			}
	 			 		
	 		$('#ttmdrop').empty();
	 		$('#ttmdrop').append(html2);
	 		
			$('#attachlist > tbody').empty();
			$('#attachlist > tbody:last').append(html);

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

$(window).on("beforeunload", function() {
	var pkg_seq = $("#pkg_seq").val();
	if(pkg_seq != null && pkg_seq.length != 0){
		
	}else{
	 pageOutdelFiles( $("#master_file_id").val()) ;		
	}
	
	 return;
});

function docfile_upload(file , prefix){
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

	var data = new FormData();
	file = file.files[0]; //파일로 변환입력
	data.append('files',file);
	var paidcall= prefix;
	data.append("prefix", paidcall );
	data.append('master_file_id', $("#master_file_id").val());
		
    //20181119 eryoon 파일명 변경
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

function chk_value(){

	 $("#chk_seqs").val('');
	 $("#chk_results").val('');

	var rowCount =  $("#radioBoxcount > tbody").children().length;

	var chk_seqs =" ";
	var charge_result =" ";
	
	for(var i=0;i<rowCount;i++){
		var seq = $(":input:radio[name='chk_results"+i+"']:checked").attr('id');
		if(i == (rowCount-1)){
			chk_seqs += seq; // chk_seq
			charge_result += $(":input:radio[name='chk_results"+i+"']:checked").val(); // 1,2,3 
	
		}else{
			chk_seqs += seq+ ",";
			charge_result += $(":input:radio[name='chk_results"+i+"']:checked").val() + ",";
		}
	}
	$("#chk_seqs").val(chk_seqs);
	$("#chk_results").val(charge_result);
}
</script>

</head>
<body>
<form:form commandName="Pkg21Model" method="post" onsubmit="return false;">
	<form:hidden path="pkg_seq" />
	<form:hidden path="select_status" />
	<form:hidden path="status" />
	<form:hidden path="read_gubun" />
	<form:hidden path="system_seq" />
	<form:hidden path="content" />
	<form:hidden path="content_pn" />
	<form:hidden path="content_cr" />
	<form:hidden path="content_self" />
	<form:hidden path="save_status" />
	<form:hidden path="ser_downtime" />
	<form:hidden path="impact_systems" />
	
	<form:hidden path="ord" />
	<form:hidden path="del_file_id" />
	<form:hidden path="master_file_id" />
	<form:hidden path="title" />
	
	<form:hidden path="col24" />
	<form:hidden path="col25" />
	<!-- 현황 예제1 -->
	<div class="sub_contents01">
		<div class="mg_c fl_wrap" style="width:77%;">
			<div class="sub_flow_wrap fl_left" style="width:30%;">
				<ul class="sub_flow_line01 fl_wrap">
					<li class="sub_flow" style="width:50%;">
						<c:choose>
							<c:when test="${registerFlag == '등록'}">
								<p class="on">
									DVT
								</p>
							</c:when>
							<c:when test="${Pkg21Model.select_status == 201}">
								<c:choose>
									<c:when test="${Pkg21Model.status < 203}">
										<p class="on">
											DVT
										</p>
									</c:when>
									<c:otherwise>
										<p class="on_blue">
											DVT
										</p>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<p class="ing" style="cursor: pointer;" onclick="javascript:fn_access_read('read', '${Pkg21Model.pkg_seq}');">
									<img src="/images/ic_flow_blue.png">
									<span class="over_text_ing">
										DVT
									</span>
								</p>
							</c:otherwise>
						</c:choose>
					</li>
					<li class="sub_flow" style="width:50%;">
						<c:choose>
							<c:when test="${Pkg21Model.select_status == 211}">
								<c:choose>
									<c:when test="${Pkg21Model.status < 212}">
										<p class="on">
											1차초도적용
										</p>
									</c:when>
									<c:otherwise>
										<p class="on_blue">
											1차초도적용
										</p>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${Pkg21Model.status < 203}">
								<p>
									<img src="/images/ic_flow.png">
									<span class="over_text">1차초도적용</span>
								</p>
							</c:when>
							<c:when test="${Pkg21Model.status > 211}">
								<p class="ing" style="cursor: pointer;" onclick="javascript:fn_access_status_read('211');">
									<img src="/images/ic_flow_blue.png">
									<span class="over_text_ing">1차초도적용</span>
								</p>
							</c:when>
							<c:otherwise>
								<p class="on" style="cursor: pointer;" onclick="javascript:fn_access_status_read('211');">
									1차초도적용
								</p>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</div>	
			<div class="sub_flow_wrap fl_left" style="width:60%;">	
				<ul class="sub_flow_line02 fl_wrap">
					<li class="sub_flow">
						<c:choose>
							<c:when test="${Pkg21Model.select_status == 221}">
								<c:choose>
									<c:when test="${Pkg21Model.status < 222}">
										<p class="on">
											2차초도적용
										</p>
									</c:when>
									<c:otherwise>
										<p class="on_blue">
											2차초도적용
										</p>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${Pkg21Model.status < 212}">
								<p>
									<img src="/images/ic_flow.png">
									<span class="over_text">2차초도적용</span>
								</p>
							</c:when>
							<c:when test="${Pkg21Model.status > 221}">
								<p class="ing" style="cursor: pointer;" onclick="javascript:fn_access_status_read('221');">
									<img src="/images/ic_flow_blue.png">
									<span class="over_text_ing">2차초도적용</span>
								</p>
							</c:when>
							<c:otherwise>
								<p class="on" style="cursor: pointer;" onclick="javascript:fn_access_status_read('221');">
									2차초도적용
								</p>
							</c:otherwise>
						</c:choose>
					</li>
					<li class="sub_flow">
						<c:choose>
							<c:when test="${Pkg21Model.select_status == 231}">
								<c:choose>
									<c:when test="${Pkg21Model.status < 233}">
										<p class="on">
											3차초도적용
										</p>
									</c:when>
									<c:otherwise>
										<p class="on_blue">
											3차초도적용
										</p>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${Pkg21Model.status < 222}">
								<p>
									<img src="/images/ic_flow.png">
									<span class="over_text">3차초도적용</span>
								</p>
							</c:when>
							<c:when test="${Pkg21Model.status > 232}">
								<p class="ing" style="cursor: pointer;" onclick="javascript:fn_access_status_read('231');">
									<img src="/images/ic_flow_blue.png">
									<span class="over_text_ing">3차초도적용</span>
								</p>
							</c:when>
							<c:otherwise>
								<p class="on" style="cursor: pointer;" onclick="javascript:fn_access_status_read('231');">
									3차초도적용
								</p>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
				<ul class="sub_flow_line03 fl_wrap">
					<li class="sub_flow">
						<c:choose>
							<c:when test="${Pkg21Model.select_status == 241}">
								<c:choose>
									<c:when test="${Pkg21Model.col24 == 'end'}">
										<p class="on_blue">
											CVT
										</p>
									</c:when>
									<c:otherwise>
										<p class="on">
											CVT
										</p>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${Pkg21Model.status < 212}">
								<p class="w_long">
									<img src="/images/ic_flow.png">
									<span class="over_text">CVT</span>
								</p>
							</c:when>
							<c:when test="${Pkg21Model.col24 == 'end'}">
								<p class="ing" style="cursor: pointer;" onclick="javascript:fn_access_status_read('241');">
									<img src="/images/ic_flow_blue.png">
									<span class="over_text_ing">CVT</span>
								</p>
							</c:when>
							<c:otherwise>
								<p class="on" style="cursor: pointer;" onclick="javascript:fn_access_status_read('241');">
									CVT
								</p>
							</c:otherwise>
						</c:choose>
					</li>
					<li class="sub_flow">
						<c:choose>
							<c:when test="${Pkg21Model.select_status == 251}">
								<c:choose>
									<c:when test="${Pkg21Model.col25 == 'end'}">
										<p class="on_blue">
											1차상용적용
										</p>
									</c:when>
									<c:otherwise>
										<p class="on">
											1차상용적용
										</p>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${Pkg21Model.status < 222}">
								<p class="w_long">
									<img src="/images/ic_flow.png">
									<span class="over_text">1차상용적용</span>
								</p>
							</c:when>
							<c:when test="${Pkg21Model.col25 == 'end'}">
								<p class="ing" style="cursor: pointer;" onclick="javascript:fn_access_status_read('251');">
									<img src="/images/ic_flow_blue.png">
									<span class="over_text_ing">1차상용적용</span>
								</p>
							</c:when>
							<c:otherwise>
								<p class="on" style="cursor: pointer;" onclick="javascript:fn_access_status_read('251');">
									1차상용적용
								</p>
							</c:otherwise>
						</c:choose>
					</li>
					<li class="sub_flow">
						<c:choose>
							<c:when test="${Pkg21Model.select_status == 261}">
								<c:choose>
									<c:when test="${Pkg21Model.status > 262}">
										<p class="on_blue">
											2차상용적용
										</p>
									</c:when>
									<c:otherwise>
										<p class="on">
											2차상용적용
										</p>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${Pkg21Model.status < 233 || Pkg21Model.col25 != 'end'}">
								<p class="w_long">
									<img src="/images/ic_flow.png">
									<span class="over_text">2차상용적용</span>
								</p>
							</c:when>
							<c:when test="${Pkg21Model.status > 262}">
								<p class="ing" style="cursor: pointer;" onclick="javascript:fn_access_status_read('261');">
									<img src="/images/ic_flow_blue.png">
									<span class="over_text_ing">2차상용적용</span>
								</p>
							</c:when>
							<c:otherwise>
								<p class="on" style="cursor: pointer;" onclick="javascript:fn_access_status_read('261');">
									2차상용적용
								</p>
							</c:otherwise>
						</c:choose>
					</li>
					<li class="sub_flow">
						<c:choose>
							<c:when test="${Pkg21Model.select_status == 271}">
								<c:choose>
									<c:when test="${Pkg21Model.status > 272}">
										<p class="on_blue">
											확대적용
										</p>
									</c:when>
									<c:otherwise>
										<p class="on">
											확대적용
										</p>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:when test="${Pkg21Model.status < 263}">
								<p class="w_long">
									<img src="/images/ic_flow.png">
									<span class="over_text">확대적용</span>
								</p>
							</c:when>
							<c:when test="${Pkg21Model.status > 272}">
								<p class="ing" style="cursor: pointer;" onclick="javascript:fn_access_status_read('271');">
									<img src="/images/ic_flow_blue.png">
									<span class="over_text_ing">확대적용</span>
								</p>
							</c:when>
							<c:otherwise>
								<p class="on" style="cursor: pointer;" onclick="javascript:fn_access_status_read('271');">
									확대적용
								</p>
							</c:otherwise>
						</c:choose>
					</li>
					<li class="sub_flow">
						<c:choose>
							<c:when test="${Pkg21Model.status == 299}">
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
	</div>
	
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">PKG요약</h2>
		<div class="sub_con_close2">
			<span id="con_open" style="cursor: pointer;" onclick="javascript:fn_open_and_close('0');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list" style="display: block">
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
						<th>PN</th>
						<td><pre>${Pkg21Model.content_pn}</pre></td>
					</tr>
					<tr>
						<th>CR</th>
						<td><pre>${Pkg21Model.content_pn}</pre></td>
					</tr>
					<tr>
						<th>자체보완</th>
						<td><pre>${Pkg21Model.content_self}</pre></td>
					</tr>
				</tbody>
			</table>
		</div>
		<c:if test="${Pkg21Model.col43 == 'Y'}">
			<div class="bt_btn_wrap">
				<a href="javascript:fn_reject_241();" class="bt_btn1 bt_btn_orang2">재 검토</a>
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
							<form:input path="col5" class="new_inp fl_left inp_w100px ml03" readOnly="true" />
							<span class="fl_left mg03"> ~ </span>
							<form:input path="col6" class="new_inp fl_left inp_w100px ml03" readOnly="true" />
						</td>
					</tr>
				</tbody>
			</table>		
		</div>
		<div class="write_info2 fl_wrap">
			등록
			<span class="name2">
				<c:if test="${Pkg21Model.col43 != 'Y'}">
				${Pkg21Model.reg_plan_user} (${Pkg21Model.reg_date_plan})
				</c:if>
			</span>
		</div>

		<c:if test="${Pkg21Model.col43 == 'Y'}">
			<div class="bt_btn_wrap">
				<a href="javascript:fn_save_241();" class="bt_btn1 bt_btn_orang2">CVT계획 완료</a>
			</div>
		</c:if>
	</div>
	
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">CVT 검증결과</h2>
		<div class="sub_con_close2">
			<span id="con_open2" style="cursor: pointer;" onclick="javascript:fn_open_and_close('2');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list2" style="display: block">
		
		<h3>검증결과</h3>
		<div class="table_style03">
			<input type="hidden" name="chk_results" id="chk_results"  />
			<input type="hidden" name="chk_seqs" id="chk_seqs"  />
			<table id="radioBoxcount" class="con_width100">
				<colgroup>
					<col width="10%">
					<col width="60%">
					<col width="30%">
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>항목</th>
						<th>결과</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty Pkg21Model.pkg21ModelList}">
							<c:forEach var="result" items="${Pkg21Model.pkg21ModelList}" varStatus="status1">
								<tr>
									<td style="display: none;">
										<input type="checkbox" name="chk_seqs_val" value="${result.chk_seq}" checked/>
									</td>
									<td class="td_center">
										${result.vol_no}
									</td>
									<td>${result.title}</td>
									<td class="td_center">
										<c:forEach var="result2" items="${Pkg21Model.ok_nok_list}" varStatus="status">
											<c:choose>
												<c:when test="${result2.code == result.chk_result}">
													<input type="radio" id="${result.chk_seq}" name="chk_results${status1.index}" value ="<c:out value='${result2.code}' />" checked="checked"  onclick="javascript:chk_value();" >
													<span class="mg03 ml05 mr15 fl_left" >${result2.codeName}</span>
												</c:when>
												<c:otherwise>
													<input type="radio" id="${result.chk_seq}" name="chk_results${status1.index}" value ="<c:out value='${result2.code}' />"   onclick="javascript:chk_value();"  >
													<span class="mg03 ml05 mr15 fl_left" >${result2.codeName}</span>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="3" class="td_center">
									검증계획이 우선입니다.
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		
		
		<h3>첨부</h3>
		<div class="table_style01">
			<table id ="attachlist" class="con_width100">
				<colgroup>
					<col width="22%">
					<col width="78%">
				</colgroup>
				<tbody>
					<tr>
						<th>Pkg기초성능 검증 결과서</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left mb05">
							<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
						</td>
					</tr>
					<tr>
						<th>상용 Traffic재현 통계 검증 결과서</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
							<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
						</td>
					</tr>
					<tr>
						<th>형상/SW/PLD 변경 검증 결과서</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
							<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
						</td>
					</tr>
					<tr>
						<th>KPI변화분석 검증 결과서</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left mb05">
							<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
						</td>
					</tr>
					<tr>
						<th>PKG Description</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
							<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
						</td>
					</tr>
					<tr>
						<th>최적화 파라미터 검증 결과</th>
						<td>
							<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
							<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
						</td>
					</tr>
				</tbody>
			</table>		
		</div>
		
		<div class="table_style03 mt20">
			<table class="con_width100">
				<colgroup>
					<col width="10%">
					<col width="10%">
					<col width="15%">
					<col width="20%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
				</colgroup>
				<thead>
					<tr>
						<th>선택</th>
						<th>승인</th>
						<th>이름</th>
						<th>부서</th>
						<th>전화번호</th>
						<th>이메일</th>
						<th>승인결과</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty Pkg21Model.pkgUserModelList}">
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
						</c:when>
						<c:otherwise>
							<c:forEach var="result" items="${Pkg21Model.pkgUserModelList}" varStatus="status">
								<tr>
									<td class="td_center">
										<c:if test="${result.status == 'R'}">
											<c:if test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
												<span class="btn_wrap">
													<a href="javascript:fn_save_243('${result.ord}');" class="btn btn_red">승인</a>
													<a href="javascript:fn_reject_243();" class="btn btn_blue">반려</a>
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
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
			<div class="mt10 mb10 txt_888">
				<p>* 시스템에 등록된 승인 담당자 정보를 현행화하세요.</p>
			</div>
		<div class="write_info2 fl_wrap">
			등록
			<span class="name2">
				<c:if test="${not empty Pkg21Model.pkgUserModelList}">
					${Pkg21Model.reg_result_user} (${Pkg21Model.reg_date_result})
				</c:if>
			</span>
		</div>
		<c:if test="${not empty Pkg21Model.systemUserModelList}">
			<div class="bt_btn_wrap">
				<a href="javascript:fn_save_242();" class="bt_btn1 bt_btn_orang2">결과 승인 요청</a>
			</div>
		</c:if>
	</div>
</form:form>
</body>
</html>