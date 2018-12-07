<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javascript" src="/js/jquery/ui.js"></script>
<script type="text/javascript"  src="/js/pkgmg/pkgmg.wired.pkg21.js"></script>

<script type="text/javaScript" defer="defer">

$(document).ready(
		function($) {
			status_navi('6');//navi
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
   if( paidcall != "wiredExpResultAttach"){
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
		if(list[i].attach_file_id  != null && "wiredExpResultAttach" == list[i].attach_file_id.substring(0,20)){//				ttm 첨부
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
   data:{"master_file_id" : $("#master_file_id").val(), "type" : "확대적용","use_yn" : "Y"},
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
					html += '<tr><td colspan =\'4\' style=\"text-align:center;\" >등록된 파일이 없습니다.</td></tr>';
				}

				html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'_count\"'+' name='+data[i].maxlistsize+' value=\"'+html3[1]+'\" >';
				html += '</tbody></table></div>';
	
			}else{
				if(data[i].attach_file_id  != null){
					if("wiredExpResultAttach" == data[i].attach_file_id.substring(0,20) && flag ){
						var htmlttm =	attachfiles(data[i].id,data,"wiredExpResultAttach"); //	ttm 첨부
						html2 +=	htmlttm[0] ;
						html2 += '<input type=\"hidden\" id=\"wiredExpResultAttach_count\"'+' name="5" value=\"'+htmlttm[1]+'\" >';
						flag = false;
					}
				}
			}
		}
 		
 		if(html2.length == 0){
 			html2 += '<tr><td colspan =\'4\' ><span class=\"mt05 fl_left\">등록된 파일이 없습니다.</span></td></tr>';
		}
 			 		
		$('#wiredExpResultdrop').empty();
		$('#wiredExpResultdrop').append(html);
		
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
		if(chk == filechk[i]){	j++;	}
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
		
   if( paidcall != "wiredExpResultAttach"){
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
	
	<!-- 	navi -->
	<form:hidden path="col43" />
	
<!-- <div class="tit"> -->
<%-- ${Pkg21Model.title} --%>
<!-- </div> -->
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
		<h2 class="fl_left">확대적용 결과</h2>
		<div class="sub_con_close2">
			<span id="con_open" style="cursor: pointer;" onclick="javascript:fn_open_and_close('0');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list" style="display: block;">
<%-- 		status:	${Pkg21Model.status}   a:	${Pkg21Model.select_status}  b:${Pkg21Model.pkg_user_all_yn} --%>
		<div class="table_style03 mt10">
			<table class="con_width100">
				<colgroup>
					<col width="13%">
					<col width="17%">
					<col width="17%">
					<col width="30%">
					<col width="15%">
					<col width="8%">
				</colgroup>
				<thead>
					<tr>
						<th style="display: none;"></th>
						<th>국사</th>
						<th>장비명</th>
						<th>서비스지역</th>
						<th>적용일시</th>
						<th>적용결과</th>
						<th>확인</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${Pkg21Model.pkgEquipmentModelList}" varStatus="status">
						<tr>
							<td style="display: none;">
								<c:choose>
									<c:when test="${empty result.start_date}">
										<input type="checkbox" name="check_seqs_e" value="${result.equipment_seq}" />
									</c:when>
									<c:when test="${not empty result.work_result}">
										<input type="checkbox" name="check_seqs_e" value="${result.equipment_seq}" />
									</c:when>
									<c:otherwise>
										<input type="checkbox" name="check_seqs_e" value="${result.equipment_seq}" checked/>
									</c:otherwise>
								</c:choose>								
							</td>
							<td class="td_center">
								${result.team_name}
							</td>
							<td class="td_center">
								${result.equipment_name}
							</td>
							<td class="td_center">
								${result.service_area}
							</td>
							<td class="td_center sysc_inp">
								<input type="hidden" name="ampms" value="${result.ampm}"/>
								<input type="hidden" name="start_dates" value="${result.start_date}"/>
								<input type="hidden" name="end_dates" value="${result.end_date}"/>
								<input type="hidden" name= "start_times1" value="${result.start_time1}"/>
								<input type="hidden" name= "start_times2" value="${result.start_time2}"/>
								<input type="hidden" name= "end_times1" value="${result.end_time1}"/>
								<input type="hidden" name= "end_times2" value="${result.end_time2}"/>
								<c:choose>
									<c:when test="${empty result.start_date}">
										확대일정 미수립
									</c:when>
									<c:otherwise>
										${result.start_date} ${result.start_time1}:${result.start_time2} ~ ${result.end_date} ${result.end_time1}:${result.end_time2}	
									</c:otherwise>
								</c:choose>	
							</td>
							<td class="td_center sysc_inp">
								<c:choose>
									<c:when test="${empty result.work_result}">
										<c:choose>
											<c:when test="${empty result.start_date}">
												-
											</c:when>
											<c:otherwise>
												<select name="work_result" class="mb03">
													<c:forEach var="result2" items="${Pkg21Model.s_result_3List}" varStatus="status">
														<c:choose>
															<c:when test="${result2.code == result.work_result}">
																<option value="<c:out value='${result2.code}' />" selected><c:out value="${result2.codeName}" /></option>
															</c:when>
															<c:otherwise>
																<option value="<c:out value='${result2.code}' />"><c:out value="${result2.codeName}" /></option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>	
											</c:otherwise>
										</c:choose>		
									</c:when>
									<c:otherwise>
										<select style="display: none;" name= "work_result">
											<option value="${result.work_result}">${result.work_result}</option>
										</select>
										${result.work_result}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="td_center">
								${result.reg_user_name}
							</td>
						</tr>
						<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_seqs_count"/></c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div   name="wiredExpResultdrop" id="wiredExpResultdrop"  class="table_style03 mt10 line_dotte">
			<p class="mg10 fl_wrap">
				<span class="mt05 fl_left">검증결과  파일 업로드는 드래그앤드랍으로도 가능합니다. 파일 업로드는 최대 5개입니다.</span> 
				<span class="fl_right btn_line_blue2"><a href="#" class="btn btn_red">파일찾기</a></span>
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
						<td class="td_center"><span class="btn_wrap"><a href="#" class="btn btn_red">삭제</a></span></td>
					</tr>
					<tr>
						<td><a href="#">PKG적용결과서.doc</a></td>
						<td class="td_center">고길동</td>
						<td class="td_center">2018-05-10</td>
						<td class="td_center"><span class="btn_wrap"><a href="#" class="btn btn_red">삭제</a></span></td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="write_info2 fl_wrap">
<%-- 			<c:choose> --%>
<%-- 				<c:when test="${Pkg21Model.select_status == 149}"> --%>
<!-- 					PKG반려 -->
<!-- 					<span class="name2"> -->
<%-- 						${Pkg21Model.reg_result_user} (${Pkg21Model.reg_date_result}) --%>
<!-- 					</span> -->
<%-- 				</c:when> --%>
<%-- 				<c:otherwise> --%>
					<c:if test="${Pkg21Model.select_status > 343}">
						결과등록
						<span class="name2">
							${Pkg21Model.reg_plan_user} (${Pkg21Model.reg_date_plan})
						</span>
					</c:if>
					<c:if test="${Pkg21Model.select_status == 399}">
						PKG완료
						<span class="name2">
							${Pkg21Model.reg_result_user} (${Pkg21Model.reg_date_result})
						</span>
					</c:if>
<%-- 				</c:otherwise> --%>
<%-- 			</c:choose> --%>
		</div>
		<div class="fl_wrap mg_c con_width35">
			<c:if test="${Pkg21Model.pkg_user_all_yn == 'Y'}">
				<c:if test="${Pkg21Model.select_status == 345}">
<!-- 				결과 등록 -->
					<span class="btn_org3 fl_left mr05">
						<a href="javascript:fn_save_350();">등록</a>
					</span>
				</c:if>
				<c:if test="${Pkg21Model.select_status == 350 and Pkg21Model.status != 399}">
					<c:if test="${Pkg21Model.col42 != 'Y'}">
						<span class="btn_org3 fl_left mr05">
							<a href="javascript:fn_save_351();">저장</a>
						</span>
					</c:if>
					<c:if test="${Pkg21Model.col42 == 'Y'}">
						<span class="btn_org3 fl_left mr05">
							<a href="javascript:fn_save_399();">PKG완료</a>
						</span>
					</c:if>
				</c:if>
				
			</c:if>
		</div>
		<div class="mg20 txt_888">
			<p>			
				&nbsp;&nbsp;&nbsp;<span class="txt_red">*</span> 과금검증 및 용량검증을 완료해야 PKG완료를 할 수 있습니다.
			</p>
			<p>
				&nbsp;&nbsp;&nbsp;<span class="txt_red">*</span> 확대일정의 승인이 모두 완료되어야 결과등록 및 PKG완료를 할 수 있습니다.
			</p>
			
		</div>
	</div>
</form:form>
</body>
</html>
