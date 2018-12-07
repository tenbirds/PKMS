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
	$(document).ready(function($) {
		doCalendar("col3");
		
		if ($.trim($('#col3').val()) == "") { //최종결과 등록 예정일 이 빈값일경우 금일 +7일
			var today = new Date();
			today.setDate(today.getDate() + 7); //7일 더하여 setting

			var year = today.getFullYear();
			var month = today.getMonth() + 1;
			var day = today.getDate();
			if(day<10) {day='0'+day	} 
			if(month<10) {month='0'+month} 

			today = year+'-'+month+'-'+day;
			$('#col3').val(today); // 최종결과 등록 예정일 : today+7
		}
		
		
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
    
	if( paidcall != "ttmattach"){
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
   data:{"master_file_id" : $("#master_file_id").val(), "type" : "초도적용", "use_yn" : "Y"},
   success:function(data){
        	
        var html1='';
        var html2='';
        for(var i=0; i < data.length; i++){
//	        	console.log(data);
			if(data[i].pId != 1000 && data[i].attach_file_id  == null){
				var html='';
				html += '<th>'+data[i].name+'';
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
// 					html += '<span class=\"btn_line_blue2 ml10 mt02\">';
					html += '<input type=\"file\"  size=\"50\"  onchange=\"javascript:docfile_upload(this , \''+data[i].eng_name+'\');\"  >';
// 					html += '</span>';
				}
				html +=	'</td>';	
				
 				//초도적용 결과 [당일] 
				if(data[i].eng_name == "firstDayattach"){	html1 = html;	}
				//초도적용 결과 [최종]
				if(data[i].eng_name == "firstFinalattach"){	html2 = html;} 
				
				
				
			}else{
				
			}
		}

		$('#fileAttach_first').empty();
		$('#fileAttach_first').append(html1);
		
		
		
		$('#fileAttach_last').empty();
		$('#fileAttach_last').append(html2);

		

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

	
	file = file.files[0]; //파일로 변환입력

    var data = new FormData();
//     for (var i = 0; i < files.length; i++) {
//        data.append('files', files[i]);
//     }
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
				<%-- dev_yn: ${Pkg21Model.dev_yn} <br>
				cha_yn: ${Pkg21Model.cha_yn} <br>
				vol_yn: ${Pkg21Model.vol_yn} <br>
				s_granted: ${s_granted} <br>
				status: ${Pkg21Model.status} <br>
				select_status: ${Pkg21Model.select_status} --%>
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
		<h2 class="fl_left">초도적용 결과 [당일]</h2>
		<div class="sub_con_close2">
			<span id="con_open" style="cursor: pointer;" onclick="javascript:fn_open_and_close('0');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list" style="display: block">
		
		<div class="table_style01">
			<table class="con_width100">
				<colgroup>
					<col width="20%">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>당일결과 <span class="txt_red">*</span></th>
						<td class="sysc_inp">
							<form:select path="col1" items="${Pkg21Model.verify_result_3List}" itemLabel="codeName" itemValue="code" />
						</td>
					</tr>
					<tr id="fileAttach_first" >
						<th>Check List</th>
						<td><span class="btn_line_blue2"><a href="#">파일선택</a></span></td>
					</tr>
					<tr>
						<th>Comment <span class="txt_red">*</span> </th>
						<td>
							<form:textarea path="col2" rows="5" class="new_inp textarea_w95" maxlength="550" />
						</td>
					</tr>
					<tr>
						<th>최종결과 등록예정일 <span class="txt_red">*</span></th>
						<td>
							<form:input path="col3" class="new_inp inp_w20 fl_left" readOnly="true" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="write_info2 fl_wrap">
				<c:if test="${Pkg21Model.status > 132 && Pkg21Model.status != 139}">
					당일등록
					<span class="name2">
						${Pkg21Model.reg_plan_user} (${Pkg21Model.reg_date_plan})
					</span>
				</c:if>
				<c:if test="${Pkg21Model.status == 139}">
					PKG반려
					<span class="name2">
						${Pkg21Model.reg_result_user} (${Pkg21Model.reg_date_result})
					</span>
				</c:if>
		</div>
		<c:if test="${Pkg21Model.status == 132}">
			<div class="fl_wrap mg_c con_width35">
				<span class="btn_org3 fl_left mr05">
					<a href="javascript:fn_save_133();">초도적용결과 당일등록</a>
				</span>
			</div>
		</c:if>
	</div>
	<!---- /초도적용 결과 - 당일 ---->
	<!---- 초도적용 결과 - 최종 ---->
	<c:if test="${Pkg21Model.status > 132}">
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">초도적용 결과 [최종]</h2>
		<div class="sub_con_close2">
			<span id="con_open1" style="cursor: pointer;" onclick="javascript:fn_open_and_close('1');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list1" style="display: block">
		
		<div class="table_style01">
			<table class="con_width100">
				<colgroup>
					<col width="20%">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>최종결과 <span class="txt_red">*</span></th>
						<td class="sysc_inp">
							<form:select path="col4" items="${Pkg21Model.verify_result_3List}" itemLabel="codeName" itemValue="code" />
						</td>
					</tr>
					<tr id="fileAttach_last" >
						<th>Check List</th>
						<td><span class="btn_line_blue2"><a href="#">파일선택</a></span></td>
					</tr>
					<tr>
						<th>Comment</th>
						<td>
							<form:textarea path="col5" rows="5" class="new_inp textarea_w95" maxlength="550" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="write_info2 fl_wrap">
			<c:if test="${Pkg21Model.status > 133}">
				<c:choose>
					<c:when test="${Pkg21Model.status == 139}">
						PKG반려
					</c:when>
					<c:otherwise>
						최종등록
					</c:otherwise>
				</c:choose>
				<span class="name2">
					${Pkg21Model.reg_result_user} (${Pkg21Model.reg_date_result})
				</span>
			</c:if>
		</div>
		<c:if test="${Pkg21Model.status == 133}">
			<div class="fl_wrap mg_c con_width35">
				<c:if test="${Pkg21Model.eq_cnt_left > 0}">
					<span class="btn_org3 fl_left mr05">
						<a href="javascript:fn_save_134();">초도적용결과 최종등록</a>
					</span>
				</c:if>
				<c:if test="${Pkg21Model.eq_cnt_left == 0}">
					<span class="btn_org3 fl_left mr05">
					<a href="javascript:alert('모든 장비가 일정수립 완료되어 PKG완료를 해야합니다.');">모든장비 일정수립 완료</a>
					</span>
				</c:if>
				<span class="btn_org3 fl_left mr05">
					<a href="javascript:fn_save_199();">PKG완료</a>
				</span>
				
			</div>
		</c:if>
	</div>
	</c:if>
</div>
</form:form>
</body>
</html>
