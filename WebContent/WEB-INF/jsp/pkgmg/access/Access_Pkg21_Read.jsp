<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="registerFlag" value="${empty Pkg21Model.pkg_seq ? '등록' : '수정'}" />

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>

<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javascript" src="/js/jquery/ui.core.js"></script>
<script type='text/javascript' src="/js/pkgmg/pkgmg.access.pkg21.js"></script>

<script type="text/javaScript" defer="defer">
	$(document).ready(
		function($) {
/* 			
			var paramPkgSeq = "${param.pkg_seq}";
			var paramReadGubun = "${param.read_gubun}";
			
			if (paramPkgSeq == '' && paramReadGubun == "") {
				alert('비정상적인 접근방식 입니다.');
				location.href="/";
			}
 */			
			init_system_popup();
			
			doCalendar("col1");
			doCalendar("col2");
			
			doCalendar("col3");
			doCalendar("col4");
			
			doCalendar("col5");
			doCalendar("col6");
			
			doCalendar("col7");
			doCalendar("col8");
			
			$('textarea[maxlength]').keydown(function(){
		        var max = parseInt($(this).attr('maxlength'));
		        var str = $(this).val();
				if(str.length > max) {
				    $(this).val(str.substr(0, max));
					alert("최대 [" + max + " 자]까지 입력 가능합니다.");
				}
			});
			
			attachList();
			
			//2018.11.14 eryoon 페키지 타이틀 처리
			var Pkg21ModelTitle = "${Pkg21Model.title}";
			if (Pkg21ModelTitle != "")  $(".tit").text(Pkg21ModelTitle);
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
	   data:{"master_file_id" : $("#master_file_id").val(), "type" : "AccessDVT","use_yn" : "Y"},
       success:function(data){
	        var html='';	
	        var html2='';
	        var flag = true;
	        var td_cnt = 0;
	        for(var i=0; i < data.length; i++){
// 	        	console.log(data);
				if(data[i].pId != 1000 && data[i].attach_file_id  == null){
					td_cnt++;
					if(td_cnt == 6 || td_cnt == 9){
						html += '<tr><th style=\"height:30px;border-bottom:1px solid #999;\">'+data[i].name+'';
					}else{
						html += '<tr><th>'+data[i].name+'';	
					}

					if(data[i].required == "Y"){
						html += '<span class=\"txt_red\">*</span>';
					}
					html += '</th>';
					html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'\" name=\"id\" value=\"'+data[i].id+'\">';
					if(td_cnt == 6 || td_cnt == 9){
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

var fnLoadList = function () {
	$("#Pkg21Model").attr("action","/pkgmg/access/Access_Pkg21_ReadList.do"); 
	$("#Pkg21Model").submit();
}
</script>

</head>
<body>
<form:form commandName="Pkg21Model" method="post">
	<form:hidden path="pkg_seq" />
	<form:hidden path="select_status" />
	<form:hidden path="status" />
	<form:hidden path="save_status" />
	<form:hidden path="read_gubun" />
	<form:hidden path="ord" />
	<form:hidden path="impact_systems" />
	
	<form:hidden path="master_file_id" />
	<form:hidden path="col24" />
	<form:hidden path="col25" />
	
	<form:hidden path="pageIndex"		value="${param.pageIndex }"/>
	<form:hidden path="date_start"		value="${param.date_start }"/>
	<form:hidden path="date_end"   		value="${param.date_end }"/>
	<form:hidden path="statusCondition"	value="${param.statusCondition }"/>
	<form:hidden path="userCondition"	value="${param.userCondition }"/>
	<form:hidden path="group1Condition"	value="${param.group1Condition }"/>
	<form:hidden path="group2Condition"	value="${param.group2Condition }"/>
	<form:hidden path="searchKeyword"	value="${param.searchKeyword }"/>

<!-- SVT 계획수립 -->
<div class="new_con_Div32">
	<!-- 현황 예제1 -->
	<div class="sub_contents01">
		<div class="mg_c fl_wrap" style="width:77%;">
			<div class="sub_flow_wrap fl_left" style="width:30%;">
				<ul class="sub_flow_line01 fl_wrap">
					<li class="sub_flow" style="width:50%;">
						<c:choose>
							<c:when test="${registerFlag == '등록'}">
								<p class="on">DVT</p>
							</c:when>
							<c:when test="${Pkg21Model.select_status == 201}">
								<c:choose>
									<c:when test="${Pkg21Model.status < 203}">
										<p class="on">DVT</p>
									</c:when>
									<c:otherwise>
										<p class="on_blue">DVT</p>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<p class="ing" style="cursor: pointer;" onclick="javascript:fn_access_read('read', '${Pkg21Model.pkg_seq}');">
									<img src="/images/ic_flow_blue.png">
									<span class="over_text_ing">DVT</span>
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
	
	<div id="pkg21_status_content">
		<div class="sub_title fl_wrap">
			<h2 class="fl_left">기본정보</h2>
			<div class="sub_con_close2">
				<span id="con_open" style="cursor: pointer;" onclick="javascript:fn_open_and_close('0');">접기</span>
			</div>
		</div>
		<div class="sub_contents02"  id="con_list" style="display: block">
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
								<form:hidden path="system_seq" />
								<form:input path="system_name" maxlength="50" class="new_inp inp_w50 fl_left" readonly="true" />
								
								<c:if test="${registerFlag == '등록'}">
									<span class="btn_line_blue2 ml03" style="margin-top:1px;">
										<img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup"/>
									</span>
								</c:if>
							</td>
						</tr>
						<tr>
							<th>제목 <span class="txt_red">*</span></th>
							<td colspan="3">
								<form:input path="title" maxlength="100" class="new_inp" />
							</td>
						</tr>
						<tr>
							<th>
								작업난이도 <span class="txt_red">*</span>
							</th>
							<td class="sysc_inp">
								<form:select path="work_level" items="${Pkg21Model.work_level_list}" itemLabel="codeName" itemValue="code" />
							</td>
							<th>
								중요도 <span class="txt_red">*</span>
							</th>
							<td class="sysc_inp">
								<form:select path="important" items="${Pkg21Model.important_list}" itemLabel="codeName" itemValue="code" />
							</td>
							<!-- <th>작업단위 </th>
							<td class="sysc_inp">
								추후결정
							</td> -->
						</tr>
						<tr>
							<th>PKG버전 <span class="txt_red">*</span></th>
							<td>
								<form:input path="ver" class="new_inp" maxlength="10" />
							</td>
							<th>버전구분 <span class="txt_red">*</span></th>
							<td>			
								<form:radiobuttons path="ver_gubun" items="${Pkg21Model.ver_gubun_access_list}" itemLabel="codeName" itemValue="code" />
							</td>
						</tr>
						<tr>
							<th>신규내역</th>
							<td colspan="3">
								<form:textarea path="content" rows="5" class="textarea_w95" maxlength="550" />
							</td>
						</tr>
						<tr>
							<th>주요보완내역 (PN)</th>
							<td colspan="3">
								<form:textarea path="content_pn" rows="5" class="textarea_w95" maxlength="550" />
							</td>
						</tr>
						<tr>
							<th>주요개선내역 (CR)</th>
							<td colspan="3">
								<form:textarea path="content_cr" rows="5" class="textarea_w95" maxlength="550" />
							</td>
						</tr>
						<tr>
							<th>자체개선내역</th>
							<td colspan="3">
								<form:textarea path="content_self" rows="5" class="textarea_w95" maxlength="550" />
							</td>
						</tr>
						<tr>
							<th>TTM</th>
							<td colspan="3">
								<form:textarea path="ttm" rows="5" class="textarea_w95 mb05" maxlength="550" />
								
								<div id="ttmdrop" name="ttmdrop">
									페이지 로딩시에 스크립트에서 다시 내려받는다.
									<input name="ttmattach" id="dropzone0" type="text" class="new_inp new_inp_w3 fl_left mb05">
									<span class="btn_line_blue2 ml10 mt02">
										<a href="#">첨부파일</a>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<th>서비스 중단시간</th>
							<td colspan="3">
								<form:input path="ser_downtime" class="new_inp inp_w100px" maxlength="8" onkeydown="javascript:fn_numbersonly(event);" />
								분
							</td>
						</tr>
						<tr>
							<th>개발기간</th>
							<td colspan="3">
								<form:input path="col1" class="new_inp fl_left" readOnly="true" />
								<span class="fl_left mg05"> ~ </span>
								<form:input path="col2" class="new_inp fl_left" readOnly="true" />
							</td>
						</tr>
						<tr>
							<th>DVT</th>
							<td colspan="3">
								<form:input path="col3" class="new_inp fl_left" readOnly="true" />
								<span class="fl_left mg05"> ~ </span>
								<form:input path="col4" class="new_inp fl_left" readOnly="true" />
							</td>
						</tr>
						<tr>
							<th>CVT</th>
							<td colspan="3">							
								<form:input path="col5" class="new_inp fl_left" readOnly="true" />
								<span class="fl_left mg05"> ~ </span>
								<form:input path="col6" class="new_inp fl_left" readOnly="true" />
							</td>
						</tr>
						<tr>
							<th>적용예정기간 <span class="txt_red">*</span></th>
							<td colspan="3">
								<form:input path="col7" class="new_inp fl_left" readOnly="true" />
								<span class="fl_left mg05"> ~ </span>
								<form:input path="col8" class="new_inp fl_left" readOnly="true" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>	
			<div class="write_info2 fl_wrap">
				<c:if test="${registerFlag == '수정'}">
					등 록
					<span class="name2">
						${Pkg21Model.reg_user_name} (${Pkg21Model.reg_date})
					</span>
					수 정
					<span class="name2">
						${Pkg21Model.update_user_name} (${Pkg21Model.update_date})
					</span>
				</c:if>
			</div>
		</div>
			<div class="bt_btn_wrap fl_wrap">
				<a href="javascript:fnLoadList();" class="bt_btn1 btn_blue">목록</a>
				<a href="javascript:fn_save_201();" class="bt_btn1 bt_btn_orang3">저장</a>
				<c:if test="${Pkg21Model.status == 201}">
					<a href="javascript:fn_save_202();" class="bt_btn1 bt_btn_orang3">검증접수</a>
				</c:if>
			</div>
		</div>

		<div class="sub_title fl_wrap">
			<h2 class="fl_left">DVT 검증결과</h2>
			<div class="sub_con_close2">
				<span id="con_open1" style="cursor: pointer;" onclick="javascript:fn_open_and_close('1');">접기</span>
			</div>
		</div>
		<div class="sub_contents02"  id="con_list1" style="display: block">
			<div class="table_style03 mt10">
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
							<c:when test="${Pkg21Model.status > 201}">
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
										검증접수가 우선입니다.
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
							<th>요구사항 명세서(요건서 or 명세서)</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05">
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>설계 Review결과서</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>규격서</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>SVT결과서</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05">
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>Regression Test 결과</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>기타(교육자료 등)</th>
							
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>보완내역서</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05">
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>SW변경내역서</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>PLD변경내역서</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>DVT기능검증결과</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05">
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>PKG표준적용절차서</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>CVT기능검증계획</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div class="mt10 mb10 txt_888">
				<p>* 결과등록 시 1차 초도적용을 진행할 수 있습니다.</p>
			</div>
		</div>
		
		<div class="bt_btn">
			<span class="btn_org2">
				<c:if test="${Pkg21Model.status == 202}">
					<a href="javascript:fn_save_203();">결과등록</a>
				</c:if>
			</span>
		</div>
	</div>
</div>
</form:form>
</body>
</html>
