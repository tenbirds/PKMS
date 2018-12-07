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
<script type="text/javascript" src="/js/jquery/ui.js"></script>
<script type='text/javascript' src='/js/pkgmg/pkgmg.pkg21.js'></script>

<script type="text/javaScript" defer="defer">
	$(document).ready(function($) {
		$('textarea[maxlength]').keydown(function(){
		    var max = parseInt($(this).attr('maxlength'));
		    var str = $(this).val();
			if(str.length > max) {
			    $(this).val(str.substr(0, max));
				alert("최대 [" + max + " 자]까지 입력 가능합니다.");
			}
		});
		
		attachList();//첨부
		
	});






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
    }
    
    var paidcall= obj.attr("name");
    data.append("prefix", obj.attr("name") );
    data.append('master_file_id', $("#master_file_id").val());
    
    //20181119 eryoon 파일 이름 변경
    var sysUserNm = "${Pkg21Model.system_user_name}";
    var pkgTitle = "${Pkg21Model.title}";
    data.append("sysUserNm", sysUserNm);
    data.append("pkgTitle", pkgTitle);
    
    if( paidcall != "lastSecurityResultAttach"){
    	data.append('parent_tree_id', $("#"+ obj.attr("name")).val());
    }else{
    	data.append('parent_tree_id', '');
    }
    
    $.ajax({
    	url:"/common/attachfile/new_file_add.do",
       	data: data,
       	type:'POST',
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
//	console.log(file_name);
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
	        	 attachList();
	        	$("#master_file_id").val(master_file_id);
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
			html1 += '<tr>';
			html1 += '<td><a href=\"#\" onclick=\"javascript:downloadFile(\''+list[i].file_name+'\',\''+list[i].name+'\',\''+list[i].file_path+'\'); return false;\">'+list[i].name+'</a></td>';
			html1 += '<td class=\"td_center\">'+list[i].reg_user+'</td>';
			html1 += '<td class=\"td_center\">'+list[i].reg_date+'</td>';
			html1 += '<td class=\"td_center\">';
			html1 += '<span class=\"btn_wrap\"><a href=\"#\" onClick=\"javascript:delNewFile(\'' + list[i].attach_file_id +'\',\''+ $("#master_file_id").val() +'\',\''+ list[i].file_path+'\',\''+list[i].file_name+'\');\" class=\"btn btn_red\">삭제</a></span>';
			html1 += '</td></tr>';
		}
	}
	html = html1;
}else{
	for(var i=0; i < list.length; i++){
		if(list[i].attach_file_id  != null && "lastSecurityResultAttach" == list[i].attach_file_id.substring(0,24)){//				ttm 첨부
				++count;
				
				html2 += '<tr>';
				html2 += '<td><a href=\"#\" onclick=\"javascript:downloadFile(\''+list[i].file_name+'\',\''+list[i].name+'\',\''+list[i].file_path+'\'); return false;\">'+list[i].name+'</a></td>';
				html2 += '<td class=\"td_center\">'+list[i].reg_user+'</td>';
				html2 += '<td class=\"td_center\">'+list[i].reg_date+'</td>';
				html2 += '<td class=\"td_center\">';
				html2 += '<span class=\"btn_wrap\"><a href=\"#\" onClick=\"javascript:delNewFile(\'' + list[i].attach_file_id +'\',\''+ $("#master_file_id").val() +'\',\''+ list[i].file_path+'\',\''+list[i].file_name+'\');\" class=\"btn btn_red\">삭제</a></span>';
				html2 += '</td></tr>';
			
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
   data:{"master_file_id" : $("#master_file_id").val(), "type" : "보안검증","use_yn" : "Y"},
   success:function(data){
        var html='';	
        var html2='';
        var flag = true;
	        	console.log(data);
        for(var i=0; i < data.length; i++){
			if(data[i].pId != 1000 && data[i].attach_file_id  == null){

				html +=	'<div  name=\"'+data[i].eng_name+'\" id =\"dropzone'+i+'\">';
				html +=	'<p class=\"mg10 fl_wrap\">';
				html +=	'<span class=\"mt05 fl_left\">검증결과  파일 업로드는 드래그앤드랍으로도 가능합니다. </span>'; 
				html +=	'<span class=\"fl_right\" style=\"width:50%;line-height:30px;text-align:right;\">';
				html += '파일 업로드는 최대 '+data[i].maxlistsize+'개입니다. &nbsp;<input type=\"file\" size=\"50\" class=\"fl_right\" onchange=\"javascript:docfile_upload(this , \''+data[i].eng_name+'\');\"  >';
				html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'\" name=\"id\" value=\"'+data[i].id+'\">';
				html += '</span><p><table  class=\"con_width100\"><colgroup>';
				html += '<col width=\"70%\"><col width=\"10%\"><col width=\"10%\"><col width=\"10%\">';
				html += '</colgroup>';
				html += '<thead><tr>';
				html += '<th>검증 결과 파일</th>	<th>등록자</th><th>등록일</th><th>삭제</th>';
				html += '</tr></thead><tbody >';

				var prantid = data[i].id ;
				var html3 =	attachfiles(data[i].id,data,"listfile");  // file list 생성
				if(html3[1] >0){
					html +=	''+html3[0] ;					
				}else{
					html += '<tr><td colspan =\'4\' ><span class=\"mt05 fl_left\">등록된 파일이 없습니다.</span></td></tr>';
				}


				html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'_count\"'+' name='+data[i].maxlistsize+' value=\"'+html3[1]+'\" >';
				html += '</tbody></table></div>';
			
	
			}else{
				if(data[i].attach_file_id  != null){
					if("lastSecurityResultAttach" == data[i].attach_file_id.substring(0,24) && flag ){
						var htmlttm =	attachfiles(data[i].id,data,"lastSecurityResultAttach"); //	ttm 첨부
						html2 +=	htmlttm[0] ;
						html2 += '<input type=\"hidden\" id=\"lastSecurityResultAttach_count\"'+' name="5" value=\"'+htmlttm[1]+'\" >';
						flag = false;
						
					}
				}
			}
		}
 		
 		if(html2.length == 0){
 			html2 += '<tr><td colspan =\'4\' ><span class=\"mt05 fl_left\">등록된 파일이 없습니다.</span></td></tr>';
		}

		$('#lastSecurityResultdrop').empty();
		$('#lastSecurityResultdrop').append(html);

		drag($("#dropzone0"));
		
   },   
   error:function(e){  
       console.log(e.responseText);  
   }
});
}



//bt_btn  // 버튼 클릭시 예외
//fn_save_101()
//페이지 나갈때 저장한하면 업로드 파일 전부 del
$(window).on("beforeunload", function() {
var pkg_seq = $("#pkg_seq").val();
if(pkg_seq != null && pkg_seq.length != 0){
	
}else{
 pageOutdelFiles( $("#master_file_id").val()) ;		
}

 return;
});

//$('form').submit(function(){
//$(window).unbind('beforeunload');
//});





function docfile_upload(file , prefix){
//			alert(prefix);
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
		
		
        //20181119 eryoon 파일 이름 변경
        var sysUserNm = "${Pkg21Model.system_user_name}";
        var pkgTitle = "${Pkg21Model.title}";
        data.append("sysUserNm", sysUserNm);
        data.append("pkgTitle", pkgTitle);
		
	if( paidcall != "lastSecurityResultAttach"){
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
							<p>
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
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('133');">
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
		<h2 class="fl_left">보안검증 결과</h2>
		<div class="sub_con_close2">
			<span id="con_open" style="cursor: pointer;" onclick="javascript:fn_open_and_close('0');">접기</span>
		</div>
	</div>
	<div class="sub_contents01"  id="con_list" style="display: block;">
		
		<div name="lastSecurityResultdrop" id="lastSecurityResultdrop"  class="table_style03 mt10 line_dotte">
			<p class="mg10 fl_wrap">
				<span class="mt05 fl_left">검증결과  파일 업로드는 드래그앤드랍으로도 가능합니다. 파일 업로드는 최대 5개입니다.</span> 
				<span class="fl_right btn_line_blue2"><a href="#">파일찾기</a></span>
			<p>
			<table class="con_width100">
				<colgroup>
					<col width="70%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
				</colgroup>
				<thead>
					<tr>
						<th>검증 결과 파일</th>
						<th>등록자</th>
						<th>등록일</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><a href="#">Check_List.xlxs</a></td>
						<td class="td_center">고길동</td>
						<td class="td_center">2018-05-10</td>
						<td class="td_center">
							<span class="btn_wrap"><a href="#" class="btn btn_red">삭제</a></span>
						</td>
					</tr>
					<tr>
						<td><a href="#">PKG적용결과서.doc</a></td>
						<td class="td_center">고길동</td>
						<td class="td_center">2018-05-10</td>
						<td class="td_center">
							<span class="btn_wrap"><a href="#" class="btn btn_red">삭제</a></span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="table_style01 mt20">
			<table class="con_width100">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tbody>
					<tr>
						<th>
							보안검증 <span class="txt_red">*</span>
							<br>
							Comment
						</th>
						<td>
							<form:textarea path="col1" class="new_inp textarea_w100" maxlength="550" />
						</td>
					</tr>
				</tbody>
			</table>		
		</div>
		<div class="write_info2">
			<c:if test="${Pkg21Model.col1 != ''}">
				보안검증 완료
				<span class="name2">
					${Pkg21Model.reg_user_name} (${Pkg21Model.reg_date})
				</span>
			</c:if>
		</div>
		<c:if test="${Pkg21Model.col1 == ''}">
			<div class="bt_btn">
				<span class="btn_org2">
					<a href="javascript:fn_save_171();">완료</a>
				</span>
			</div>
		</c:if>
	</div>
</div>
</form:form>
</body>
</html>
